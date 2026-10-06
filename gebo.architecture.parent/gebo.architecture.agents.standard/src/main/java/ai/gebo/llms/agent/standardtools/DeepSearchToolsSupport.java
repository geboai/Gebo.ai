/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.core.contents.security.services.VirtualFilesystemQuery;
import ai.gebo.architecture.search.config.OpenNetworkLoadingConfig;
import ai.gebo.architecture.search.config.SearchCallsConfig;
import ai.gebo.architecture.search.service.BestEffortSearchCalls;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.config.GeboRagSearchConfig;
import ai.gebo.llms.deepsearch.service.IGExternalSearchSecurityService;
import ai.gebo.llms.agent.standard.config.StandardAgentsConfig;
import ai.gebo.architecture.fulltext.service.IGFullTextSearchService;
import ai.gebo.llms.agent.standard.services.SearchResultsChunker;

/**
 * What every deep search tool shares: its collaborators, resolved on use (the tool
 * sources are collected while the chat models are built, and the deep search
 * reaches back to the chat models), and the count of the deep searches of each user
 * request.
 * <p>
 * The counts live in memory: all the tool calls of a request run on the node
 * serving it. Requests idle for more than {@link #IDLE_TTL_MILLIS} are forgotten.
 */
@Service
public class DeepSearchToolsSupport {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeepSearchToolsSupport.class);
	/** A request is long over after an hour without deep searches. */
	static final long IDLE_TTL_MILLIS = 60L * 60L * 1000L;
	/** Property capping the returned analysis, in tokens. */
	public static final String MAX_ANALYSIS_TOKENS_PROPERTY = "ai.gebo.agents.standard.deep-search-tools.max-analysis-tokens";
	public static final int DEFAULT_MAX_ANALYSIS_TOKENS = 16000;
	/** Property setting the deep searches a single user request can make, whatever the sources. */
	public static final String MAX_DEEP_SEARCHES_PER_REQUEST_PROPERTY = "ai.gebo.agents.standard.deep-search-tools.max-deep-searches-per-request";
	public static final int DEFAULT_MAX_DEEP_SEARCHES_PER_REQUEST = 8;
	private final ObjectProvider<DeepSearchToolAnalysis> analysis;
	private final ObjectProvider<IDocumentsChunkService> chunkingService;
	private final ObjectProvider<IGChatModelRuntimeConfigurationDao> chatModelsDao;
	private final ObjectProvider<GeboRagSearchConfig> ragSearchConfig;
	private final ObjectProvider<IGExternalSearchSecurityService> externalSearchSecurityService;
	private final ObjectProvider<StandardAgentsConfig> agentsConfig;
	private final int maxAnalysisTokens;
	private int maxDeepSearchesPerRequest = DEFAULT_MAX_DEEP_SEARCHES_PER_REQUEST;

	private static final class RequestCount {
		final AtomicInteger deepSearches = new AtomicInteger(0);
		/** The searches of the deep searches already run, by tool (see {@link #firstRunOf}). */
		final Set<String> searches = ConcurrentHashMap.newKeySet();
		volatile long lastAccess = System.currentTimeMillis();
	}

	private final Map<String, RequestCount> requests = new ConcurrentHashMap<>();

	public DeepSearchToolsSupport(ObjectProvider<DeepSearchToolAnalysis> analysis,
			ObjectProvider<IDocumentsChunkService> chunkingService,
			ObjectProvider<IGChatModelRuntimeConfigurationDao> chatModelsDao,
			ObjectProvider<GeboRagSearchConfig> ragSearchConfig,
			ObjectProvider<IGExternalSearchSecurityService> externalSearchSecurityService,
			ObjectProvider<StandardAgentsConfig> agentsConfig,
			@Value("${" + MAX_ANALYSIS_TOKENS_PROPERTY + ":" + DEFAULT_MAX_ANALYSIS_TOKENS + "}") int maxAnalysisTokens) {
		this.analysis = analysis;
		this.chunkingService = chunkingService;
		this.chatModelsDao = chatModelsDao;
		this.ragSearchConfig = ragSearchConfig;
		this.externalSearchSecurityService = externalSearchSecurityService;
		this.agentsConfig = agentsConfig;
		this.maxAnalysisTokens = maxAnalysisTokens > 0 ? maxAnalysisTokens : DEFAULT_MAX_ANALYSIS_TOKENS;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Deep search tools analysis capped at " + this.maxAnalysisTokens + " token(s)");
		}
	}

	/**
	 * The deep searches a single user request can make, whatever the sources
	 * ({@value #MAX_DEEP_SEARCHES_PER_REQUEST_PROPERTY},
	 * {@value #DEFAULT_MAX_DEEP_SEARCHES_PER_REQUEST} by default, also when not a
	 * positive number).
	 */
	@Value("${" + MAX_DEEP_SEARCHES_PER_REQUEST_PROPERTY + ":" + DEFAULT_MAX_DEEP_SEARCHES_PER_REQUEST + "}")
	public void setMaxDeepSearchesPerRequest(int maxDeepSearchesPerRequest) {
		this.maxDeepSearchesPerRequest = maxDeepSearchesPerRequest > 0 ? maxDeepSearchesPerRequest
				: DEFAULT_MAX_DEEP_SEARCHES_PER_REQUEST;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Deep search tools: at most " + this.maxDeepSearchesPerRequest + " deep search(es) per request"
					+ (maxDeepSearchesPerRequest > 0 ? "" : " (default, " + maxDeepSearchesPerRequest + " not valid)"));
		}
	}

	/** The deep searches a single user request can make, whatever the sources. */
	public int maxDeepSearchesPerRequest() {
		return maxDeepSearchesPerRequest;
	}

	/** Prefix of the properties of the deep searches' coverage. */
	public static final String COVERAGE_PROPERTIES = "ai.gebo.agents.standard.deep-search-tools.coverage";

	/**
	 * When the coverage of a deep search is thin and whether the agent must complete it
	 * before answering (see {@link ai.gebo.llms.agent.standardtools.model.DeepSearchCoverage}).
	 *
	 * @param gateEnabled           whether a thin coverage requires its completion
	 *                              (the agentic loop holds an answer that does not
	 *                              complete it); when off the coverage is only reported
	 * @param minDocumentsUsed      fewer documents used as sources than this is thin...
	 * @param minDocumentsFound     ...when at least this many documents were used or
	 *                              left unread (the documents judged irrelevant do not
	 *                              count)
	 * @param barelyReadFragments   a source read in at most this many fragments of a
	 *                              longer document is read in part (only where a
	 *                              document can be read whole and its length is known)
	 * @param barelyReadShare       more than this share of the sources read in part is
	 *                              thin
	 */
	public record CoverageRules(boolean gateEnabled, int minDocumentsUsed, int minDocumentsFound,
			int barelyReadFragments, double barelyReadShare) {
		public static final CoverageRules DEFAULTS = new CoverageRules(true, 2, 3, 2, 0.5d);
	}

	private CoverageRules coverageRules = CoverageRules.DEFAULTS;

	@Autowired
	public void setCoverageRules(@Value("${" + COVERAGE_PROPERTIES + ".gate-enabled:true}") boolean gateEnabled,
			@Value("${" + COVERAGE_PROPERTIES + ".min-documents-used:2}") int minDocumentsUsed,
			@Value("${" + COVERAGE_PROPERTIES + ".min-documents-found:3}") int minDocumentsFound,
			@Value("${" + COVERAGE_PROPERTIES + ".barely-read-fragments:2}") int barelyReadFragments,
			@Value("${" + COVERAGE_PROPERTIES + ".barely-read-share:0.5}") double barelyReadShare) {
		this.coverageRules = new CoverageRules(gateEnabled, Math.max(0, minDocumentsUsed),
				Math.max(0, minDocumentsFound), Math.max(0, barelyReadFragments),
				barelyReadShare >= 0 && barelyReadShare <= 1 ? barelyReadShare : CoverageRules.DEFAULTS.barelyReadShare());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Deep search tools coverage rules: " + this.coverageRules);
		}
	}

	/** When the coverage of a deep search is thin, and whether its completion is required. */
	public CoverageRules coverageRules() {
		return coverageRules;
	}

	/** How the results of the services searching an open network are loaded, when configured. */
	private ObjectProvider<OpenNetworkLoadingConfig> openNetworkLoading = null;

	@Autowired(required = false)
	public void setOpenNetworkLoading(ObjectProvider<OpenNetworkLoadingConfig> openNetworkLoading) {
		this.openNetworkLoading = openNetworkLoading;
	}

	/** The open network loading settings: the configured ones, else the defaults. */
	public OpenNetworkLoadingConfig openNetworkLoading() {
		final OpenNetworkLoadingConfig config = openNetworkLoading != null ? openNetworkLoading.getIfAvailable() : null;
		return config != null ? config : new OpenNetworkLoadingConfig();
	}

	/** The visibility of the knowledge bases' contents, when configured. */
	private ObjectProvider<IGKnowledgebaseVisibilityService> visibilityService = null;

	@Autowired(required = false)
	public void setVisibilityService(ObjectProvider<IGKnowledgebaseVisibilityService> visibilityService) {
		this.visibilityService = visibilityService;
	}

	/**
	 * The documents of the given knowledge bases the current user can see, or null when
	 * they cannot be counted (no knowledge base, no visibility service, a failure).
	 */
	public Long countVisibleDocuments(java.util.List<String> knowledgeBaseCodes) {
		if (knowledgeBaseCodes == null || knowledgeBaseCodes.isEmpty() || visibilityService == null) {
			return null;
		}
		try {
			final IGKnowledgebaseVisibilityService visibility = visibilityService.getIfAvailable();
			if (visibility == null) {
				return null;
			}
			final long count = visibility.countVisibleDocuments(
					VirtualFilesystemQuery.builder().knowledgeBaseCodes(knowledgeBaseCodes).build());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("countVisibleDocuments(" + knowledgeBaseCodes + ") " + count + " document(s)");
			}
			return count;
		} catch (RuntimeException e) {
			LOGGER.warn("The visible documents of the knowledge bases " + knowledgeBaseCodes
					+ " could not be counted: the deep search coverage reports no documents not reached", e);
			return null;
		}
	}

	/** The full-text search, when configured: its presence gives the knowledge base deep search its keywords. */
	private ObjectProvider<IGFullTextSearchService> fullTextSearchService = null;

	@Autowired(required = false)
	public void setFullTextSearchService(ObjectProvider<IGFullTextSearchService> fullTextSearchService) {
		this.fullTextSearchService = fullTextSearchService;
	}

	/** Whether the knowledge base searches have a full-text leg, so they take keywords. */
	private BestEffortSearchCalls searchCalls = null;

	@Autowired
	public void setSearchCalls(BestEffortSearchCalls searchCalls) {
		this.searchCalls = searchCalls;
	}

	/** The best effort calls of the search services, the defaults when none was given. */
	public synchronized BestEffortSearchCalls searchCalls() {
		if (searchCalls == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("No search calls configured, using the default ones");
			}
			searchCalls = new BestEffortSearchCalls(new SearchCallsConfig());
		}
		return searchCalls;
	}

	public boolean knowledgeBaseKeywordsEnabled() {
		return KnowledgeBaseKeywords.enabled(fullTextSearchService);
	}

	public DeepSearchToolAnalysis analysis() {
		return analysis.getObject();
	}

	public IDocumentsChunkService chunkingService() {
		return chunkingService.getObject();
	}

	public IGChatModelRuntimeConfigurationDao chatModelsDao() {
		return chatModelsDao.getObject();
	}

	/** The access check every external search shares (search tools, agents, deep search). */
	public IGExternalSearchSecurityService externalSearchSecurityService() {
		return externalSearchSecurityService.getObject();
	}

	/**
	 * Safety cap of the returned analysis, in tokens: its length is asked to the model
	 * by the depth, this only stops a runaway one ({@value #MAX_ANALYSIS_TOKENS_PROPERTY},
	 * {@value #DEFAULT_MAX_ANALYSIS_TOKENS} by default).
	 */
	/** The documents found loaded and chunked at the same time. */
	public int documentsParallelism() {
		final StandardAgentsConfig config = agentsConfig.getIfAvailable();
		return config != null ? config.getSearchDocumentsParallelism()
				: SearchResultsChunker.DEFAULT_DOCUMENTS_PARALLELISM;
	}

	public int maxAnalysisTokens() {
		return maxAnalysisTokens;
	}

	/** Most documents a deep search reads, as the deep search pipelines do. */
	public int searchTopK() {
		GeboRagSearchConfig config = ragSearchConfig.getIfAvailable();
		return config != null && config.getDeepSearchGlobalTopK() > 0 ? config.getDeepSearchGlobalTopK() : 30;
	}

	/**
	 * Counts a deep search of the request, returning how many the request made with
	 * this one; 1 when the request is unknown.
	 */
	public int countDeepSearch(String requestId) {
		if (requestId == null) {
			return 1;
		}
		evictIdle();
		RequestCount count = requests.computeIfAbsent(requestId, id -> new RequestCount());
		count.lastAccess = System.currentTimeMillis();
		final int calls = count.deepSearches.incrementAndGet();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("countDeepSearch(...) request:" + requestId + " deep search " + calls + ", "
					+ requests.size() + " request(s) tracked");
		}
		return calls;
	}

	/**
	 * Records the searches of a deep search of the request, returning false when a deep
	 * search of the same tool already ran the same searches in it: it would find the same
	 * documents again. Always true when the request is unknown.
	 *
	 * @param searches the searches, as text: compared regardless of order, case and spaces
	 */
	public boolean firstRunOf(String requestId, String toolName, Collection<String> searches) {
		if (requestId == null || searches == null || searches.isEmpty()) {
			return true;
		}
		evictIdle();
		final RequestCount count = requests.computeIfAbsent(requestId, id -> new RequestCount());
		count.lastAccess = System.currentTimeMillis();
		final String signature = toolName + "\n" + String.join("\n", new TreeSet<>(searches.stream()
				.filter(search -> search != null)
				.map(search -> search.trim().replaceAll("\\s+", " ").toLowerCase(Locale.ROOT)).toList()));
		final boolean first = count.searches.add(signature);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("firstRunOf(...) request:" + requestId + " tool:" + toolName + " " + searches.size()
					+ " search(es) " + (first ? "not run yet" : "already run by a deep search of the request"));
		}
		return first;
	}

	private void evictIdle() {
		final long threshold = System.currentTimeMillis() - IDLE_TTL_MILLIS;
		int evicted = 0;
		for (Iterator<Map.Entry<String, RequestCount>> iterator = requests.entrySet().iterator(); iterator
				.hasNext();) {
			if (iterator.next().getValue().lastAccess < threshold) {
				iterator.remove();
				evicted++;
			}
		}
		if (evicted > 0 && LOGGER.isDebugEnabled()) {
			LOGGER.debug("Evicted " + evicted + " idle request(s) from the deep search tools counts");
		}
	}
}
