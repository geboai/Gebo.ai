/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.ai.document.Document;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.ContentAccessPolicy;
import ai.gebo.architecture.agents.services.GAbstractGenericalAgentService;
import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolDataFlowTarget;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.fulltext.model.FullTextSearchMetaDataFilter;
import ai.gebo.architecture.fulltext.service.IGFullTextSearchService;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.SemanticSearchMetaDataFilter;
import ai.gebo.architecture.ai.service.ToolsTokenBudget;
import ai.gebo.llms.deepsearch.service.DocumentNamesShown;
import ai.gebo.llms.agent.standard.config.StandardAgentsConfig;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRankerService;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.security.services.IGSecurityService;
import lombok.Data;

/**
 * The internal knowledge base search as a tool, for an agent that operates its own
 * tools, such as the single agent working in a loop. It searches the knowledge
 * bases of the chat the tool is called for, as its chat profile gives them (see
 * {@link ToolCallbackDeclarationUtil#chatKnowledgeBases(ToolContext)}): none when the
 * chat has none. The user's ACL filter applies when the platform access policy is
 * ACL based.
 * <p>
 * When the calling model call shares the room it leaves to its tools' results (see
 * {@link ToolsTokenBudget}), the answer takes at most that room divided by
 * {@code ai.gebo.agents.standard.knowledge-base-search-room-divisor}
 * ({@value #DEFAULT_ROOM_DIVISOR} by default), shared equally among the documents
 * found; the tool wrapper takes what it returns out of the room. A model call sharing no room
 * leaves the answer bounded by the documents asked only (topK).
 * <p>
 * topK counts documents: the fragments retrieved are ranked against the query by the
 * ranker model only ({@link IGRankerService#rank(List, String, int)}, no irrelevance
 * filter: the model reading them judges), the documents rated by their best fragment,
 * and the topK best kept with all their fragments, in reading order (see
 * {@link RankedDocuments}). Each document carries its short id of the request
 * ({@link ToolsFoundDocuments#idOf(String)}), the one the answer gives back.
 */
@ConditionalOnProperty(prefix = "ai.gebo.agents.standard", name = "enabled", havingValue = "true", matchIfMissing = true)
@Service
public class InternalKnowledgeBaseSearchToolSource implements IGToolCallbackSource {
	private static final Logger LOGGER = LoggerFactory.getLogger(InternalKnowledgeBaseSearchToolSource.class);
	/** The id of this tool source, kept out of the default network's automatic mounting. */
	public static final String INTERNAL_KNOWLEDGE_BASE_SEARCH_TOOL_SOURCE = "internal-knowledge-base-search-tool-source";
	public static final String SEARCH_KNOWLEDGE_BASE_TOOL = "searchKnowledgeBase";
	private static final String SEARCH_KNOWLEDGE_BASE_DESCRIPTION = "Search the company's internal knowledge base documents "
			+ "visible to the user. Give a precise question or topic, and optionally a few alternative phrasings; returns "
			+ "the most relevant document fragments with their titles and sources.";
	/** The share of the room left to the tools an answer may take: a third by default. */
	public static final double DEFAULT_ROOM_DIVISOR = 3.0d;
	/** The fittings of the fragments tried to bring the whole answer in its room. */
	static final int MAX_FIT_ATTEMPTS = 4;
	static final int DEFAULT_TOP_K = 10;
	static final int MAX_TOP_K = 30;
	/** With a ranker configured, the fragments retrieved for it to choose the best topK documents of. */
	static final int RANKING_RETRIEVAL_FACTOR = 2;
	private static final String NEWLINE = "\r\n";

	// Resolved on use: the tool sources are collected while the chat models are built,
	// and the search reaches back to the chat models, so injecting it directly would be
	// a dependency cycle at startup.
	private final ObjectProvider<IGDocumentsSearchService> documentsSearchService;
	private final IGSecurityService securityService;
	private final IGDocumentContentRendererProvider rendererFactory;
	private final ObjectProvider<StandardAgentsConfig> agentsConfig;
	// resolved on use as well: the ranker service reaches back to the chat models
	private final ObjectProvider<IGRankerService> rankerService;

	public InternalKnowledgeBaseSearchToolSource(ObjectProvider<IGDocumentsSearchService> documentsSearchService,
			IGSecurityService securityService, IGDocumentContentRendererProvider rendererFactory,
			ObjectProvider<StandardAgentsConfig> agentsConfig, ObjectProvider<IGRankerService> rankerService) {
		this.documentsSearchService = documentsSearchService;
		this.securityService = securityService;
		this.rendererFactory = rendererFactory;
		this.agentsConfig = agentsConfig;
		this.rankerService = rankerService;
	}

	/**
	 * The configured divisor of the room left to the tools, the default one when not
	 * configured or not a positive number.
	 */
	double roomDivisor() {
		final StandardAgentsConfig config = agentsConfig != null ? agentsConfig.getIfAvailable() : null;
		if (config == null) {
			return DEFAULT_ROOM_DIVISOR;
		}
		final double divisor = config.getKnowledgeBaseSearchRoomDivisor();
		if (!(divisor > 0) || Double.isInfinite(divisor)) {
			LOGGER.warn("ai.gebo.agents.standard.knowledge-base-search-room-divisor " + divisor
					+ " is not a positive number, " + DEFAULT_ROOM_DIVISOR + " is used");
			return DEFAULT_ROOM_DIVISOR;
		}
		return divisor;
	}

	/**
	 * The tokens the answer may take: the room its model call leaves to the tools
	 * divided by {@link #roomDivisor()}, never more than the room; unbounded when the
	 * model call shares no room.
	 */
	int maxResultTokens(ToolsTokenBudget callBudget) {
		if (callBudget == null) {
			return Integer.MAX_VALUE;
		}
		final double divisor = roomDivisor();
		final int maxTokens = callBudget.grant((int) Math.min(Integer.MAX_VALUE, Math.floor(callBudget.left() / divisor)));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("maxResultTokens(...) room left:" + callBudget.left() + " (tok) divisor:" + divisor
					+ " answer at most:" + maxTokens + " (tok)");
		}
		return maxTokens;
	}

	@Data
	@JsonClassDescription("An internal knowledge base search")
	public static class KnowledgeBaseSearchParam {
		@JsonPropertyDescription("The question or topic to search, as precise as possible")
		private String query;
		/**
		 * One string, the phrasings separated by commas: a list was given by some models as
		 * a single string, and its conversion failed the whole call.
		 */
		@JsonPropertyDescription("Optional alternative phrasings of the query, to widen the semantic search: one string, "
				+ "the phrasings separated by commas (e.g. \"first phrasing, second phrasing\")")
		private String alternativeQueries;
		@JsonPropertyDescription("Optional maximum number of documents to return, with their fragments found, 10 when not given")
		private Integer topK;
	}

	/** The alternative phrasings of a search: the comma separated ones given, trimmed, blanks left out. */
	static List<String> alternativeQueries(String commaSeparated) {
		final List<String> queries = new ArrayList<>();
		if (commaSeparated != null) {
			for (String query : commaSeparated.split(",")) {
				if (!query.isBlank()) {
					queries.add(query.strip());
				}
			}
		}
		return queries;
	}

	/**
	 * The search with its keywords for the full-text leg: the parameter of the tool
	 * when that leg exists (see {@link KnowledgeBaseKeywords}).
	 */
	@Data
	@lombok.EqualsAndHashCode(callSuper = true)
	@lombok.ToString(callSuper = true)
	@JsonClassDescription("An internal knowledge base search")
	public static class KnowledgeBaseKeywordsSearchParam extends KnowledgeBaseSearchParam {
		@JsonPropertyDescription(KnowledgeBaseKeywords.KEYWORDS_DESCRIPTION)
		private List<String> keywords;
	}

	/** The full-text search, when configured: its presence gives the tool its keywords. */
	private ObjectProvider<IGFullTextSearchService> fullTextSearchService = null;

	@Autowired(required = false)
	public void setFullTextSearchService(ObjectProvider<IGFullTextSearchService> fullTextSearchService) {
		this.fullTextSearchService = fullTextSearchService;
	}

	@Override
	public String getId() {
		return INTERNAL_KNOWLEDGE_BASE_SEARCH_TOOL_SOURCE;
	}

	@Override
	public ToolsCategory getToolCategory() {
		return ToolsCategory.KNOWLEDGE_BASE_VARIOUS_SEARCHES;
	}

	@Override
	public List<ToolReference> getFullToolReferences() {
		ToolReference reference = new ToolReference();
		reference.setName(SEARCH_KNOWLEDGE_BASE_TOOL);
		reference.setDescription(SEARCH_KNOWLEDGE_BASE_DESCRIPTION);
		return List.of(reference);
	}

	/**
	 * The knowledge base search reads the knowledge bases' stores with the embedded
	 * query, and has the ranker score what it found.
	 */
	@Override
	public List<ToolDataFlowTarget> getDataFlowTargets(String toolName) {
		return SEARCH_KNOWLEDGE_BASE_TOOL.equals(toolName) ? knowledgeBaseSearchTargets("Knowledge base search")
				: List.of();
	}

	/** What a search of the knowledge bases reaches, for the data-flow register. */
	static List<ToolDataFlowTarget> knowledgeBaseSearchTargets(String what) {
		return List.of(ToolDataFlowTarget.of(ToolDataFlowTarget.Kind.EMBEDDING_MODEL, what + ": query embedding"),
				ToolDataFlowTarget.of(ToolDataFlowTarget.Kind.KNOWLEDGE_BASE_VECTOR_STORE, what + ": semantic retrieval"),
				ToolDataFlowTarget.of(ToolDataFlowTarget.Kind.KNOWLEDGE_BASE_FULLTEXT_INDEX, what + ": full-text retrieval"),
				ToolDataFlowTarget.of(ToolDataFlowTarget.Kind.KNOWLEDGE_BASE_GRAPH_STORE, what + ": graph retrieval"),
				ToolDataFlowTarget.of(ToolDataFlowTarget.Kind.RANKER_MODEL, what + ": ranking of the fragments found"));
	}

	@Override
	public List<ToolCallback> getToolCallbacks() {
		// the call is recorded for the request by the tool wrapper (RunAsToolCallback)
		BiFunction<KnowledgeBaseSearchParam, ToolContext, String> search = (param, toolContext) -> {
			final List<String> chatKnowledgeBases = ToolCallbackDeclarationUtil.chatKnowledgeBases(toolContext);
			if (param != null && param.getQuery() != null && !param.getQuery().isBlank()) {
				ToolsProgress.notify(toolContext,
						"Searching the knowledge base: " + ToolsProgress.shown(param.getQuery()));
			}
			// what was found, the documents named, told to the user
			return search(param, chatKnowledgeBases, ToolsFoundDocuments.from(toolContext),
					ToolsTokenBudget.from(toolContext), found -> ToolsProgress.notify(toolContext, found));
		};
		if (KnowledgeBaseKeywords.enabled(fullTextSearchService)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Declaring tool:" + SEARCH_KNOWLEDGE_BASE_TOOL + " with keywords for the full-text search");
			}
			final BiFunction<KnowledgeBaseKeywordsSearchParam, ToolContext, String> searchWithKeywords = search::apply;
			return List.of(ToolCallbackDeclarationUtil.declare(searchWithKeywords, SEARCH_KNOWLEDGE_BASE_TOOL,
					SEARCH_KNOWLEDGE_BASE_DESCRIPTION, KnowledgeBaseKeywordsSearchParam.class, String.class));
		}
		return List.of(ToolCallbackDeclarationUtil.declare(search, SEARCH_KNOWLEDGE_BASE_TOOL,
				SEARCH_KNOWLEDGE_BASE_DESCRIPTION, KnowledgeBaseSearchParam.class, String.class));
	}

	/**
	 * Runs the search and renders the fragments found, outside any model call's room
	 * (bounded by topK only). A failure is answered as text, so the model can go on
	 * without this search.
	 */
	String search(KnowledgeBaseSearchParam param, List<String> chatKnowledgeBases) {
		return search(param, chatKnowledgeBases, null, null);
	}

	/**
	 * Runs the search, sharing the documents found with the calling agent when it
	 * collects them (see {@link ToolsFoundDocuments}), the answer fitted in its share
	 * of the room the model call leaves to its tools when it shares one (see
	 * {@link #maxResultTokens(ToolsTokenBudget)}).
	 */
	String search(KnowledgeBaseSearchParam param, List<String> chatKnowledgeBases, ToolsFoundDocuments collector,
			ToolsTokenBudget callBudget) {
		return search(param, chatKnowledgeBases, collector, callBudget, null);
	}

	/**
	 * The same, telling what was found to {@code foundProgress} (may be null): the
	 * names of the documents and how many fragments of them.
	 */
	String search(KnowledgeBaseSearchParam param, List<String> chatKnowledgeBases, ToolsFoundDocuments collector,
			ToolsTokenBudget callBudget, Consumer<String> foundProgress) {
		if (param == null || param.getQuery() == null || param.getQuery().isBlank()) {
			return "No search done: the query is empty.";
		}
		final int maxTokens = maxResultTokens(callBudget);
		if (callBudget != null && maxTokens < ToolsTokenBudget.MIN_USEFUL_TOKENS) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("search(...) knowledge base tool not run: " + maxTokens + " (tok) of room for its answer");
			}
			return "No room is left in the context for more contents: answer with the contents already found.";
		}
		try {
			final List<String> kbCodes = chatKnowledgeBases != null ? chatKnowledgeBases : List.of();
			if (kbCodes.isEmpty()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("search(...) knowledge base tool not run: the chat has no knowledge base");
				}
				return "This chat has no knowledge base to search.";
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("search(...) knowledge base tool over the chat's knowledge base(s): " + kbCodes);
			}
			SemanticSearchMetaDataFilter semanticFilter = new SemanticSearchMetaDataFilter();
			semanticFilter.setKnowledgeBasesCodes(kbCodes);
			FullTextSearchMetaDataFilter fullTextFilter = new FullTextSearchMetaDataFilter();
			fullTextFilter.setKnowledgebaseCodes(kbCodes);
			if (securityService.getPlatformContentAccessPolicy() == ContentAccessPolicy.ACL_BASED
					&& !securityService.isCurrentUserAdmin()) {
				List<Integer> aclAliases = securityService.getCurrentAclGrantedAccessor(AclGrantType.READ)
						.getAllOwnedAclAliases();
				semanticFilter.setAclAliases(aclAliases);
				fullTextFilter.setAclAliases(aclAliases);
			}
			List<String> semanticQueries = new ArrayList<>();
			semanticQueries.add(param.getQuery());
			alternativeQueries(param.getAlternativeQueries()).stream().limit(4).forEach(semanticQueries::add);
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("search(...) knowledge base tool alternative queries:'" + param.getAlternativeQueries()
						+ "' searched:" + semanticQueries);
			}
			int topK = param.getTopK() != null ? Math.max(1, Math.min(MAX_TOP_K, param.getTopK())) : DEFAULT_TOP_K;
			// topK documents: their fragments retrieved, twice as many with a ranker to choose
			// the best documents from
			final IGRankerService ranker = rankerService != null ? rankerService.getIfAvailable() : null;
			final boolean ranking = ranker != null && ranker.isRankerConfigured();
			final int retrievalTopK = topK * KnowledgeBaseDeepSearchTool.FRAGMENTS_PER_DOCUMENT
					* (ranking ? RANKING_RETRIEVAL_FACTOR : 1);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Begin search(...) knowledge base tool over " + kbCodes.size() + " knowledge base(s) with "
						+ semanticQueries.size() + " quer(ies) topK:" + topK + " retrieved:" + retrievalTopK
						+ " ranking:" + ranking + " maxTokens:"
						+ (callBudget != null ? String.valueOf(maxTokens) : "none (no room shared, topK only)"));
			}
			// twice the answer's room retrieved, so that fitting it keeps every document,
			// as many times more as the fragments retrieved for the ranker; no room shared,
			// the fragments asked only bound the retrieval
			final int retrievalTokens = callBudget != null
					? (int) Math.min(Integer.MAX_VALUE, maxTokens * 2l * (retrievalTopK / topK))
					: Integer.MAX_VALUE;
			// the full-text leg searches the keywords when given, else the queries
			final List<String> fullTextQueries = KnowledgeBaseKeywords.fullTextQueries(
					param instanceof KnowledgeBaseKeywordsSearchParam withKeywords ? withKeywords.getKeywords() : null,
					semanticQueries);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("search(...) knowledge base tool full-text queries:" + fullTextQueries);
			}
			AIDocumentsSet found = documentsSearchService.getObject().search(param.getQuery(), semanticQueries, semanticFilter,
					fullTextQueries, fullTextFilter, param.getQuery(), retrievalTopK, retrievalTokens);
			List<Document> documents = found != null ? found.aiDocumentsList() : List.of();
			if (documents.isEmpty()) {
				return "No document found in the internal knowledge base for: " + param.getQuery();
			}
			final List<Document> retrieved = documents;
			documents = ranking ? rank(ranker, documents, param.getQuery(), topK)
					: RankedDocuments.top(documents, topK, true);
			if (collector != null) {
				// the documents found become the calling agent's answer documents, only the
				// ones the ranker kept when it ranked them; sharing them never fails the search
				try {
					List<GResponseDocumentRef> refs = GResponseDocumentRef
							.from(documents == retrieved ? found : AIDocumentsSet.from(documents));
					collector.add(refs);
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Knowledge base tool shared " + refs.size()
								+ " document(s) with the calling agent's answer");
					}
				} catch (Throwable th) {
					LOGGER.warn("Knowledge base tool could not share its documents with the calling agent", th);
				}
			}
			List<String> rendered = new ArrayList<>();
			for (Document document : documents) {
				IGDocumentContentRenderer<Object> renderer = rendererFactory.get(document);
				final String id = idOf(collector, document);
				// the short id of its document in the request, the one the answer gives back
				rendered.add((id != null ? "doc: " + id + NEWLINE : "")
						+ (renderer != null ? renderer.render(document) : document.getText()));
			}
			// the documents these fragments come from, the only evidence of this search
			final String heading = documents.size() + " fragment(s) of " + RankedDocuments.documentsIn(documents)
					+ " document(s) found:" + NEWLINE + documentsLine(documents, collector) + NEWLINE;
			if (foundProgress != null) {
				final String names = DocumentNamesShown.ofFragments(documents);
				foundProgress.accept("Knowledge base: " + (names != null ? names + " (" : "") + documents.size()
						+ " fragment(s) of " + RankedDocuments.documentsIn(documents) + " document(s)"
						+ (names != null ? ")" : ""));
			}
			String answer = heading + fragmentsText(rendered);
			if (callBudget != null) {
				// the fragments share equally what the heading leaves of the answer's room;
				// the truncation marks and the line ends are outside that count, so the
				// fragments are fitted again until the whole answer is in the room
				int fragmentsRoom = maxTokens - ITokensCountable.stringsTokensSize(heading);
				int answerSize = ITokensCountable.stringsTokensSize(answer);
				for (int attempt = 0; attempt < MAX_FIT_ATTEMPTS && answerSize > maxTokens && fragmentsRoom > 0; attempt++) {
					answer = heading + fragmentsText(GAbstractGenericalAgentService.fitEqually(rendered, fragmentsRoom));
					final int overshoot = ITokensCountable.stringsTokensSize(answer) - maxTokens;
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("search(...) knowledge base tool fitted its fragments in " + fragmentsRoom
								+ " (tok), answer over its room by " + overshoot + " (tok)");
					}
					answerSize = maxTokens + overshoot;
					fragmentsRoom -= Math.max(overshoot, 0);
				}
			}
			// the answer is taken out of the room by the tool wrapper (RunAsToolCallback)
			final int answerTokens = ITokensCountable.stringsTokensSize(answer);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End search(...) knowledge base tool found " + documents.size() + " fragment(s), answer of "
						+ answer.length() + " character(s) " + answerTokens + " (tok)");
			}
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("<KNOWLEDGE_BASE_TOOL_ANSWER>");
				LOGGER.trace(answer.toString());
				LOGGER.trace("</KNOWLEDGE_BASE_TOOL_ANSWER>");
			}
			return answer;
		} catch (Throwable e) {
			LOGGER.error("Knowledge base search tool failed for query:" + param.getQuery(), e);
			return "The internal knowledge base search failed, go on without it.";
		}
	}

	/**
	 * The best {@code topK} of the retrieved fragments, best first, ranked against the
	 * query by the ranker model only (no irrelevance filter). When ranking fails, the
	 * first {@code topK} in retrieval order.
	 */
	List<Document> rank(IGRankerService ranker, List<Document> retrieved, String query, int topK) {
		try {
			// every fragment ranked: the documents are chosen by their best one
			final List<Document> ranked = ranker.rank(retrieved, query, retrieved.size());
			final List<Document> kept = ranked != null ? RankedDocuments.top(ranked, topK, true) : List.of();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("rank(...) knowledge base tool kept " + kept.size() + " of " + retrieved.size()
						+ " retrieved fragment(s), from the " + RankedDocuments.documentsIn(kept) + " best of "
						+ RankedDocuments.documentsIn(retrieved) + " document(s), topK:" + topK);
			}
			return kept;
		} catch (Throwable th) {
			LOGGER.warn("Knowledge base tool ranking failed, the first " + topK + " of "
					+ RankedDocuments.documentsIn(retrieved) + " document(s) are kept in retrieval order", th);
			return RankedDocuments.top(retrieved, topK, true);
		}
	}

	/** The short id of the document of a fragment in the request, null without a collector. */
	private static String idOf(ToolsFoundDocuments collector, Document fragment) {
		if (collector == null || fragment.getMetadata() == null) {
			return null;
		}
		final Object code = fragment.getMetadata().get(DocumentMetaInfos.CONTENT_CODE);
		return code != null ? collector.idOf(code.toString()) : null;
	}

	/** The fragments, one after the other. */
	private static String fragmentsText(List<String> fragments) {
		final StringBuilder text = new StringBuilder();
		for (String fragment : fragments) {
			text.append(fragment).append(NEWLINE);
		}
		return text.toString();
	}

	/**
	 * The documents the fragments come from, with their fragments: what this search
	 * found is evidence of these documents only.
	 */
	static String documentsLine(List<Document> documents) {
		return documentsLine(documents, null);
	}

	/** The same, each document with its short id of the request when known. */
	static String documentsLine(List<Document> documents, ToolsFoundDocuments collector) {
		final Map<String, Integer> fragmentsByDocument = new LinkedHashMap<>();
		for (Document document : documents) {
			final Map<String, Object> metaData = document.getMetadata();
			Object name = metaData != null ? metaData.get(DocumentMetaInfos.GEBO_FILE_NAME) : null;
			if (name == null && metaData != null) {
				name = metaData.get(DocumentMetaInfos.CONTENT_CODE);
			}
			final String id = idOf(collector, document);
			fragmentsByDocument.merge((id != null ? id + " " : "") + (name != null ? String.valueOf(name) : "unnamed document"),
					1, Integer::sum);
		}
		final StringBuilder line = new StringBuilder("Documents of these fragments (the only ones this search found): ");
		boolean first = true;
		for (Map.Entry<String, Integer> entry : fragmentsByDocument.entrySet()) {
			if (!first) {
				line.append(", ");
			}
			line.append(entry.getKey()).append(" (").append(entry.getValue()).append(")");
			first = false;
		}
		return line.append(".").toString();
	}

}
