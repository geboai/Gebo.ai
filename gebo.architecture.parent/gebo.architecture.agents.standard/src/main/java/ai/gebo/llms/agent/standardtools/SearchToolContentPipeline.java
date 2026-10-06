/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.util.json.JsonParser;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.ToolsTokenBudget;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.search.config.OpenNetworkLoadingConfig;
import ai.gebo.architecture.search.model.SearchCallParameters;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SystemSearchOutcome;
import ai.gebo.architecture.search.service.BestEffortSearchCalls;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.llms.agent.standard.config.StandardAgentsConfig;
import ai.gebo.llms.agent.standard.services.SearchResultsChunker;
import ai.gebo.llms.agent.standard.services.SearchResultsChunker.LoadedResults;
import ai.gebo.llms.agent.standard.services.SearchResultsChunker.NotLoaded;
import ai.gebo.llms.agent.standardtools.model.AbstractSearchToolParam;
import ai.gebo.llms.agent.standardtools.model.SearchToolResult;
import ai.gebo.llms.agent.standardtools.model.DocumentNotRead;
import ai.gebo.llms.agent.standardtools.model.SearchToolResult.Fragment;
import ai.gebo.llms.agent.standardtools.model.SearchToolResult.Status;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.services.IGRankerService;
import ai.gebo.llms.deepsearch.service.IGExternalSearchSecurityService;
import ai.gebo.model.DocumentMetaInfos;

/**
 * The work shared by every search tool: it turns the search results of an
 * {@link ISearchService} into well formed contents for the model.
 * <ol>
 * <li>checks the current user may search the source;</li>
 * <li>searches each of its systems, a failing system not stopping the others;</li>
 * <li>drops the results already returned for the same user request (see
 * {@link SearchToolsRequestRegistry});</li>
 * <li>loads the results as LLM-sized chunks ({@link SearchResultsChunker});</li>
 * <li>ranks the chunks against the search objective with the
 * {@link IGRankerService}, which also throws away the ones that do not serve
 * it;</li>
 * <li>fits the best ones in the requested size.</li>
 * </ol>
 * The collaborators are resolved on use: the tool sources are collected while the
 * chat models are built, and ranking reaches back to the chat models.
 */
@Service
public class SearchToolContentPipeline {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchToolContentPipeline.class);
	static final int DEFAULT_TOP_K = 8;
	static final int MAX_TOP_K = 30;
	static final int DEFAULT_MAX_TOKENS = 4000;
	static final int MIN_MAX_TOKENS = ToolsTokenBudget.MIN_USEFUL_TOKENS;
	/** The fittings tried to bring a whole result in its room. */
	static final int MAX_ROOM_FIT_ATTEMPTS = 3;
	static final int MAX_MAX_TOKENS = 16000;
	/** Longest search objective handed to the ranker, in tokens. */
	static final int MAX_OBJECTIVE_TOKENS = 300;
	/** Results asked to each searched system, for each fragment wanted. */
	private static final int RETRIEVAL_FACTOR = 2;
	/** Most results asked to a searched system in one call. */
	private static final int MAX_RETRIEVAL = 20;
	/** Most chunks of a single document offered to the ranker. */
	private static final int MAX_CHUNKS_PER_DOCUMENT = 6;
	/** A fragment is cut to fit the size left only when at least this many tokens are left. */
	private static final int MIN_TRUNCATED_FRAGMENT_TOKENS = 100;
	private static final String TRUNCATION_MARK = " [...]";
	private static final String PASSAGE_SEPARATOR = "\n";
	/** The last chunk position of a passage re-joined from contiguous chunks. */
	static final String PASSAGE_LAST_POSITION = "geboPassageLastPosition";

	/**
	 * Runs the search of a tool on one of the systems of its search service, with the
	 * call parameters its client software applies (see {@link SearchCallParameters}).
	 */
	@FunctionalInterface
	public interface SystemSearch {
		List<SearchResult> search(SearchableSystemMetaData system, int nEntryLimit, SearchCallParameters parameters)
				throws IOException, SearchServiceException;
	}

	private final ObjectProvider<IDocumentsChunkService> chunkingService;
	private final ObjectProvider<IGRankerService> rankerService;
	private final ObjectProvider<IGExternalSearchSecurityService> externalSearchSecurityService;
	private final SearchToolsRequestRegistry requestRegistry;
	private final ObjectProvider<StandardAgentsConfig> agentsConfig;
	private final ObjectProvider<BestEffortSearchCalls> searchCalls;

	public SearchToolContentPipeline(ObjectProvider<IDocumentsChunkService> chunkingService,
			ObjectProvider<IGRankerService> rankerService,
			ObjectProvider<IGExternalSearchSecurityService> externalSearchSecurityService,
			SearchToolsRequestRegistry requestRegistry, ObjectProvider<StandardAgentsConfig> agentsConfig,
			ObjectProvider<BestEffortSearchCalls> searchCalls) {
		this.chunkingService = chunkingService;
		this.rankerService = rankerService;
		this.externalSearchSecurityService = externalSearchSecurityService;
		this.requestRegistry = requestRegistry;
		this.agentsConfig = agentsConfig;
		this.searchCalls = searchCalls;
	}

	/** How the results of the services searching an open network are loaded, when configured. */
	private ObjectProvider<OpenNetworkLoadingConfig> openNetworkLoading = null;

	@Autowired(required = false)
	public void setOpenNetworkLoading(ObjectProvider<OpenNetworkLoadingConfig> openNetworkLoading) {
		this.openNetworkLoading = openNetworkLoading;
	}

	/** The open network loading settings: the configured ones, else the defaults. */
	OpenNetworkLoadingConfig openNetworkLoading() {
		final OpenNetworkLoadingConfig config = openNetworkLoading != null ? openNetworkLoading.getIfAvailable() : null;
		return config != null ? config : new OpenNetworkLoadingConfig();
	}

	/** The documents found loaded and chunked at the same time. */
	int documentsParallelism() {
		final StandardAgentsConfig config = agentsConfig.getIfAvailable();
		return config != null ? config.getSearchDocumentsParallelism()
				: SearchResultsChunker.DEFAULT_DOCUMENTS_PARALLELISM;
	}

	/**
	 * Runs a tool search and returns its contents. Never throws: a failure is
	 * answered with a {@link Status#FAILED} result the model can read.
	 */
	public SearchToolResult run(ISearchService<?> service, String toolName, String toolDescription,
			AbstractSearchToolParam param, List<String> keywords, SystemSearch systemSearch,
			ToolContext toolContext) {
		final String queryText = param != null ? param.queryText() : null;
		if (param == null || queryText == null || queryText.isBlank()) {
			return SearchToolResult.of(Status.NO_RESULTS, "No search done: the query is empty.");
		}
		final int topK = topK(param);
		// never more than what the calling model call has left for its tools' results
		final ToolsTokenBudget callBudget = ToolsTokenBudget.from(toolContext);
		final int maxTokens = callBudget != null ? callBudget.grant(maxTokens(param)) : maxTokens(param);
		final String objective = objective(param, queryText);
		final String requestId = ToolCallbackDeclarationUtil.requestId(toolContext);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin run(...) tool:" + toolName + " service:" + service.getId() + " topK:" + topK
					+ " maxTokens:" + maxTokens + " (model call budget:"
					+ (callBudget != null ? callBudget.left() + " left" : "none") + ") request:" + requestId);
		}
		if (callBudget != null && maxTokens < MIN_MAX_TOKENS) {
			return SearchToolResult.of(Status.NO_RESULTS,
					"No room is left in the context for more contents: answer with the contents already found.");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<SEARCH_TOOL_OBJECTIVE tool=" + toolName + ">");
			LOGGER.trace(objective);
			LOGGER.trace("</SEARCH_TOOL_OBJECTIVE>");
		}
		try {
			if (!externalSearchSecurityService.getObject().isEnabledForCurrentUser(service)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Tool:" + toolName + " denied: the current user cannot search " + service.getId());
				}
				return SearchToolResult.of(Status.NOT_ALLOWED,
						"The user is not allowed to search this source, go on without it.");
			}
			ToolsProgress.notify(toolContext, "Searching (" + toolName + "): " + ToolsProgress.shown(queryText));
			// search every system, a failing one does not stop the others
			final List<SearchableSystemMetaData> systems = service.getSearchableSystems();
			final int nEntryLimit = Math.min(MAX_RETRIEVAL, topK * RETRIEVAL_FACTOR);
			final Map<String, SearchResult> found = new LinkedHashMap<>();
			// best effort: a system out of service or not responding within the timeout is told
			// to the model, the others are searched
			final List<String> unavailable = new ArrayList<>();
			int searchedSystems = 0;
			if (systems != null) {
				for (SearchableSystemMetaData system : systems) {
					if (system == null) {
						continue;
					}
					searchedSystems++;
					final SystemSearchOutcome outcome = searchCalls.getObject().search(system, toolName,
							service.appliesRetries(), parameters -> systemSearch.search(system, nEntryLimit, parameters));
					if (!outcome.available()) {
						unavailable.add(outcome.unavailableNotice());
						continue;
					}
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Tool:" + toolName + " system:" + system.getCode() + " returned "
								+ outcome.results().size() + " result(s)");
					}
					service.setOriginOn(outcome.results());
					for (SearchResult result : outcome.results()) {
						if (result != null) {
							found.putIfAbsent(result.getCode(), result);
						}
					}
				}
			}
			if (searchedSystems > 0 && unavailable.size() == searchedSystems) {
				final SearchToolResult result = SearchToolResult.of(Status.FAILED,
						"No source could be searched (" + String.join("; ", unavailable) + "): go on without it.");
				result.setUnavailableSources(unavailable);
				return result;
			}
			// drop what the previous calls of the same request already returned
			final Set<String> alreadyReturned = requestRegistry.returnedCodes(requestId);
			final List<SearchResult> fresh = new ArrayList<>();
			int skipped = 0;
			for (SearchResult result : found.values()) {
				if (alreadyReturned.contains(result.getCode())) {
					skipped++;
				} else {
					fresh.add(result);
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Tool:" + toolName + " found " + found.size() + " distinct result(s), " + skipped
						+ " already returned for request:" + requestId);
			}
			if (fresh.isEmpty()) {
				SearchToolResult result = SearchToolResult.of(Status.NO_RESULTS,
						skipped > 0
								? "The documents found were all already returned by previous searches of this request: "
										+ "use those contents, or search something else."
								: "No document found.");
				result.setDocumentsFound(found.size());
				result.setDocumentsAlreadyReturned(skipped);
				result.setUnavailableSources(unavailable.isEmpty() ? null : unavailable);
				return result;
			}
			// load, rank against the objective, fit
			final int perDocumentBudget = Math.max(SearchResultsChunker.LLM_CHUNK_TOKENS, maxTokens);
			final int maxNumChunks = Math.min(MAX_CHUNKS_PER_DOCUMENT, Math
					.max(1, (int) Math.ceil((perDocumentBudget * 2.0) / SearchResultsChunker.LLM_CHUNK_TOKENS)));
			final ChunkingParams chunkingParams = SearchResultsChunker.buildChunkingParams(perDocumentBudget,
					maxNumChunks, keywords);
			ToolsProgress.notify(toolContext, "Reading " + fresh.size() + " document(s) found (" + toolName + ")");
			// loaded as the service says: an open network (the web) wide, within deadlines
			final LoadedResults loaded = SearchResultsChunker.load(chunkingService.getObject(), fresh, chunkingParams,
					maxNumChunks, toolName, documentsParallelism(), service.resultsLoading(), openNetworkLoading());
			final List<Document> chunks = loaded.documents();
			final RankingOutcome ranking = rank(chunks, objective, topK, toolName);
			SearchToolResult result = fit(ranking.documents(), fresh, maxTokens, toolName);
			tellNotRead(result, loaded, chunks, ranking.documents(), fresh, toolName);
			// told before fitting the room: what could not be searched is part of the answer
			result.setUnavailableSources(unavailable.isEmpty() ? null : unavailable);
			if (callBudget != null) {
				// the room holds the result as the model reads it (its JSON): the contents are
				// fitted again by what their titles, sources and the JSON framing add
				int contentsTokens = maxTokens;
				for (int attempt = 0; attempt < MAX_ROOM_FIT_ATTEMPTS; attempt++) {
					final int overshoot = ITokensCountable.stringsTokensSize(JsonParser.toJson(result)) - maxTokens;
					if (overshoot <= 0 || contentsTokens - overshoot <= 0) {
						break;
					}
					contentsTokens -= overshoot;
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Tool:" + toolName + " result over its room by " + overshoot
								+ " (tok), contents fitted again in " + contentsTokens + " (tok)");
					}
					result = fit(ranking.documents(), fresh, contentsTokens, toolName);
					tellNotRead(result, loaded, chunks, ranking.documents(), fresh, toolName);
				}
			}
			result.setRanked(ranking.ranked());
			result.setDocumentsFound(found.size());
			result.setDocumentsAlreadyReturned(skipped);
			if (result.getFragments().isEmpty()) {
				result.setStatus(Status.NO_RESULTS);
				result.setMessage(chunks.isEmpty() ? "The documents found have no readable content."
						: "None of the contents found serves the search objective.");
			} else if (!unavailable.isEmpty()) {
				result.setStatus(Status.PARTIAL);
				result.setMessage(unavailable.size() + " of the " + searchedSystems
						+ " systems could not be searched (" + String.join("; ", unavailable)
						+ "), the contents come from the other ones.");
			}
			if (!ranking.ranked() && ranking.note() != null && result.getMessage() == null) {
				result.setMessage(ranking.note());
			}
			final Set<String> returnedCodes = new LinkedHashSet<>();
			for (Fragment fragment : result.getFragments()) {
				if (fragment.getDocumentCode() != null) {
					returnedCodes.add(fragment.getDocumentCode());
				}
			}
			requestRegistry.markReturned(requestId, returnedCodes);
			shareFoundDocuments(toolContext, fresh, returnedCodes, toolName);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End run(...) tool:" + toolName + " status:" + result.getStatus() + " returns "
						+ result.getFragments().size() + " fragment(s) from " + returnedCodes.size()
						+ " document(s), " + result.getTokens() + " token(s), ranked:" + result.isRanked());
			}
			return result;
		} catch (Throwable th) {
			LOGGER.error("Tool:" + toolName + " failed for query:" + queryText, th);
			return SearchToolResult.of(Status.FAILED, "The search failed, go on without it.");
		}
	}

	/**
	 * Shares the documents the returned fragments come from with the calling agent,
	 * when it collects them (see {@link ToolsFoundDocuments}): they become its answer's
	 * documents. The refs keep the search results, so the user can chat with them.
	 */
	static void shareFoundDocuments(ToolContext toolContext, List<SearchResult> sources, Set<String> returnedCodes,
			String toolName) {
		final ToolsFoundDocuments collector = ToolsFoundDocuments.from(toolContext);
		if (collector == null || returnedCodes.isEmpty()) {
			return;
		}
		final List<GResponseDocumentRef> refs = new ArrayList<>();
		for (SearchResult source : sources) {
			if (returnedCodes.contains(source.getCode())) {
				refs.add(new GResponseDocumentRef(source));
			}
		}
		collector.add(refs);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Tool:" + toolName + " shared " + refs.size() + " document(s) with the calling agent's answer");
		}
	}

	record RankingOutcome(List<Document> documents, boolean ranked, String note) {
	}

	/** Why a document read is not in the results: no passage of it serves the objective. */
	static final String NOT_RELEVANT_TO_THE_OBJECTIVE = "read, no passage serves the search objective";
	/** Why a document read is not in the results: no room was left for it. */
	static final String LEFT_OUT_FOR_ROOM = "read, left out: no room left in the context for it";

	/**
	 * Tells in the result the documents found that give nothing to it, with why: the ones
	 * not loaded, then the ones read whose passages are not among the results (none
	 * serving the objective, or no room left).
	 */
	static void tellNotRead(SearchToolResult result, LoadedResults loaded, List<Document> chunks,
			List<Document> ranked, List<SearchResult> sources, String toolName) {
		final List<DocumentNotRead> notRead = new ArrayList<>();
		for (NotLoaded missing : loaded.notLoaded()) {
			notRead.add(new DocumentNotRead(titleOf(missing.result(), Map.of()), sourceOf(missing.result(), Map.of()),
					missing.reason()));
		}
		final Set<String> returned = new LinkedHashSet<>();
		for (Fragment fragment : result.getFragments()) {
			if (fragment.getDocumentCode() != null) {
				returned.add(fragment.getDocumentCode());
			}
		}
		final Set<String> read = codesOf(chunks);
		final Set<String> rankedCodes = codesOf(ranked);
		for (SearchResult source : sources) {
			final String code = source.getCode();
			if (code != null && read.contains(code) && !returned.contains(code)) {
				notRead.add(new DocumentNotRead(titleOf(source, Map.of()), sourceOf(source, Map.of()),
						rankedCodes.contains(code) ? LEFT_OUT_FOR_ROOM : NOT_RELEVANT_TO_THE_OBJECTIVE));
			}
		}
		result.setDocumentsNotRead(notRead.isEmpty() ? null : notRead);
		if (LOGGER.isDebugEnabled() && !notRead.isEmpty()) {
			LOGGER.debug("Tool:" + toolName + " tells " + notRead.size() + " document(s) found that give nothing: "
					+ loaded.notLoaded().size() + " not loaded");
		}
	}

	private static Set<String> codesOf(List<Document> documents) {
		final Set<String> codes = new LinkedHashSet<>();
		for (Document document : documents) {
			final Object code = document.getMetadata().get(DocumentMetaInfos.CONTENT_CODE);
			if (code != null) {
				codes.add(code.toString());
			}
		}
		return codes;
	}

	/**
	 * Ranks the chunks against the objective, the ranker service also dropping the
	 * ones that do not serve it. Without a ranker, or when ranking fails, the chunks
	 * are kept in their retrieval order.
	 */
	RankingOutcome rank(List<Document> chunks, String objective, int topK, String toolName) {
		if (chunks.isEmpty()) {
			return new RankingOutcome(chunks, false, null);
		}
		final IGRankerService ranker = rankerService.getIfAvailable();
		if (ranker == null || !ranker.isRankerConfigured()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Tool:" + toolName + " has no ranker configured, " + chunks.size()
						+ " chunk(s) kept in retrieval order");
			}
			return new RankingOutcome(limit(chunks, topK), false,
					"No ranker is configured: the contents are in search order, not ranked.");
		}
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Tool:" + toolName + " ranking " + chunks.size() + " chunk(s) topK:" + topK);
			}
			final List<Document> ranked = ranker.rankAndRemoveIrrelevant(chunks, objective, topK);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Tool:" + toolName + " ranking kept " + (ranked != null ? ranked.size() : 0) + " of "
						+ chunks.size() + " chunk(s)");
			}
			return new RankingOutcome(limit(ranked != null ? ranked : List.of(), topK), true, null);
		} catch (Throwable th) {
			LOGGER.warn("Tool:" + toolName + " ranking failed, the chunks are kept in retrieval order", th);
			return new RankingOutcome(limit(chunks, topK), false,
					"Ranking failed: the contents are in search order, not ranked.");
		}
	}

	/**
	 * The ranked chunks as the documents they come from: the documents in the order of
	 * their best chunk (the ranking chooses the documents), each one's chunks in
	 * reading order, the contiguous ones re-joined into a single passage so the model
	 * reads the text as it is written rather than in pieces.
	 */
	static List<Document> passagesByDocument(List<Document> ranked, String toolName) {
		final Map<String, List<Document>> byDocument = new LinkedHashMap<>();
		for (Document chunk : ranked) {
			final String code = stringOf(chunk.getMetadata().get(DocumentMetaInfos.CONTENT_CODE));
			byDocument.computeIfAbsent(code != null ? code : chunk.getId(), key -> new ArrayList<>()).add(chunk);
		}
		final List<Document> passages = new ArrayList<>();
		int joined = 0;
		for (List<Document> chunks : byDocument.values()) {
			final boolean positioned = chunks.stream().allMatch(chunk -> positionOf(chunk) != null);
			if (!positioned) {
				// without positions the reading order is unknown: the ranking order is kept
				passages.addAll(chunks);
				continue;
			}
			final List<Document> inReadingOrder = new ArrayList<>(chunks);
			inReadingOrder.sort((a, b) -> Long.compare(positionOf(a), positionOf(b)));
			Document passage = null;
			long last = -1;
			StringBuilder text = null;
			for (Document chunk : inReadingOrder) {
				final long position = positionOf(chunk);
				if (passage != null && position == last + 1) {
					text.append(PASSAGE_SEPARATOR).append(chunk.getText());
					last = position;
					joined++;
					continue;
				}
				if (passage != null) {
					passages.add(passageOf(passage, text, last));
				}
				passage = chunk;
				text = new StringBuilder(chunk.getText() != null ? chunk.getText() : "");
				last = position;
			}
			if (passage != null) {
				passages.add(passageOf(passage, text, last));
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("passagesByDocument(...) tool:" + toolName + " " + ranked.size() + " chunk(s) of "
					+ byDocument.size() + " document(s) into " + passages.size() + " passage(s), " + joined
					+ " chunk(s) joined to the one before");
		}
		return passages;
	}

	private static Document passageOf(Document first, StringBuilder text, long lastPosition) {
		final Map<String, Object> metaData = new HashMap<>(first.getMetadata());
		final Long firstPosition = positionOf(first);
		if (firstPosition != null && lastPosition != firstPosition) {
			metaData.put(PASSAGE_LAST_POSITION, lastPosition);
		}
		return new Document(text.toString(), metaData);
	}

	private static Long positionOf(Document chunk) {
		final Object position = chunk.getMetadata().get(DocumentMetaInfos.GEBO_CHUNK_POSITION);
		if (position instanceof Number number) {
			return number.longValue();
		}
		try {
			return position != null ? Long.valueOf(position.toString()) : null;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Takes the documents' passages (see {@link #passagesByDocument(List, String)}) in
	 * order while they fit in {@code maxTokens}, cutting the last one to the size left
	 * when that is still worth reading.
	 */
	SearchToolResult fit(List<Document> ranked, List<SearchResult> sources, int maxTokens, String toolName) {
		final Map<String, SearchResult> byCode = new LinkedHashMap<>();
		for (SearchResult source : sources) {
			byCode.put(source.getCode(), source);
		}
		final List<Document> documents = passagesByDocument(ranked, toolName);
		final SearchToolResult result = new SearchToolResult();
		int used = 0;
		int ref = 1;
		for (Document document : documents) {
			final String text = document.getText();
			if (text == null || text.isBlank()) {
				continue;
			}
			final int tokens = ITokensCountable.stringsTokensSize(text);
			final int left = maxTokens - used;
			String content = text;
			int contentTokens = tokens;
			if (tokens > left) {
				if (left < MIN_TRUNCATED_FRAGMENT_TOKENS) {
					break;
				}
				// cut by the ratio of characters to tokens, then shrink until the cut, its mark
				// included, really fits: the ratio is not the same along the whole text
				int chars = (int) Math.max(1, ((long) text.length() * left) / Math.max(1, tokens));
				content = text.substring(0, Math.min(text.length(), chars)) + TRUNCATION_MARK;
				contentTokens = ITokensCountable.stringsTokensSize(content);
				while (contentTokens > left && chars > 1) {
					chars = (int) Math.max(1, Math.min(chars - 1, ((long) chars * left) / Math.max(1, contentTokens)));
					content = text.substring(0, Math.min(text.length(), chars)) + TRUNCATION_MARK;
					contentTokens = ITokensCountable.stringsTokensSize(content);
				}
			}
			final Map<String, Object> metaData = document.getMetadata();
			final String code = stringOf(metaData.get(DocumentMetaInfos.CONTENT_CODE));
			final SearchResult source = code != null ? byCode.get(code) : null;
			result.getFragments().add(new Fragment(ref++, titleOf(source, metaData), sourceOf(source, metaData), code,
					chunkOf(metaData), content));
			used += contentTokens;
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("<SEARCH_TOOL_FRAGMENT tool=" + toolName + " ref=" + (ref - 1) + " document=" + code
						+ ">");
				LOGGER.trace(content);
				LOGGER.trace("</SEARCH_TOOL_FRAGMENT>");
			}
			if (content != text) {
				break;
			}
		}
		result.setTokens(used);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("fit(...) tool:" + toolName + " kept " + result.getFragments().size() + " of "
					+ documents.size() + " fragment(s) in " + used + " of " + maxTokens + " token(s)");
		}
		return result;
	}

	private static List<Document> limit(List<Document> documents, int topK) {
		return documents.size() > topK ? new ArrayList<>(documents.subList(0, topK)) : documents;
	}

	static int topK(AbstractSearchToolParam param) {
		return param.getTopK() != null ? Math.max(1, Math.min(MAX_TOP_K, param.getTopK())) : DEFAULT_TOP_K;
	}

	static int maxTokens(AbstractSearchToolParam param) {
		return param.getMaxTokens() != null ? Math.max(MIN_MAX_TOKENS, Math.min(MAX_MAX_TOKENS, param.getMaxTokens()))
				: DEFAULT_MAX_TOKENS;
	}

	/** The objective to rank against, the query when the model gave none, bounded in size. */
	static String objective(AbstractSearchToolParam param, String queryText) {
		String objective = param.getSearchObjective() != null && !param.getSearchObjective().isBlank()
				? param.getSearchObjective().trim()
				: queryText;
		final int tokens = ITokensCountable.stringsTokensSize(objective);
		if (tokens > MAX_OBJECTIVE_TOKENS) {
			objective = objective.substring(0,
					(int) Math.max(1, ((long) objective.length() * MAX_OBJECTIVE_TOKENS) / tokens));
		}
		return objective;
	}

	private static String titleOf(SearchResult source, Map<String, Object> metaData) {
		if (source != null) {
			if (source.getResultReference() != null) {
				if (notBlank(source.getResultReference().getName())) {
					return source.getResultReference().getName();
				}
				if (notBlank(source.getResultReference().getTitle())) {
					return source.getResultReference().getTitle();
				}
			}
			if (source.getNavigationReference() != null && source.getNavigationReference().path != null
					&& notBlank(source.getNavigationReference().path.name)) {
				return source.getNavigationReference().path.name;
			}
		}
		return stringOf(metaData.get(DocumentMetaInfos.GEBO_FILE_NAME));
	}

	private static String sourceOf(SearchResult source, Map<String, Object> metaData) {
		if (source != null) {
			if (source.getResultReference() != null && notBlank(source.getResultReference().getUri())) {
				return source.getResultReference().getUri();
			}
			if (source.getNavigationReference() != null && source.getNavigationReference().path != null
					&& notBlank(source.getNavigationReference().path.absolutePath)) {
				return source.getNavigationReference().path.absolutePath;
			}
		}
		return stringOf(metaData.get(DocumentMetaInfos.CONTENT_ORIGINAL_URL));
	}

	private static String chunkOf(Map<String, Object> metaData) {
		String position = stringOf(metaData.get(DocumentMetaInfos.GEBO_CHUNK_POSITION));
		final String count = stringOf(metaData.get(DocumentMetaInfos.GEBO_CHUNKS_COUNT));
		if (position == null) {
			return null;
		}
		final String lastPosition = stringOf(metaData.get(PASSAGE_LAST_POSITION));
		if (lastPosition != null) {
			position = position + "-" + lastPosition;
		}
		return count != null ? position + "/" + count : position;
	}

	private static String stringOf(Object value) {
		return value != null ? String.valueOf(value) : null;
	}

	private static boolean notBlank(String value) {
		return value != null && !value.isBlank();
	}

}
