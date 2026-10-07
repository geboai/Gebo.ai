/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import java.lang.reflect.Type;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.document.Document;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.util.json.JsonParser;
import org.springframework.core.ResolvableType;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.ai.service.ToolsTokenBudget;
import ai.gebo.architecture.search.service.INativeQueryObject;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.agent.standardtools.DeepSearchToolsSupport.CoverageRules;
import ai.gebo.llms.agent.standardtools.model.DeepSearchCoverage;
import ai.gebo.llms.agent.standardtools.model.DeepSearchCoverage.DocumentCoverage;
import ai.gebo.llms.agent.standardtools.model.DeepSearchCoverage.SearchCoverage;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolParam;
import ai.gebo.llms.agent.standardtools.model.DocumentNotRead;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolParam.Depth;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolResult;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolResult.Source;
import ai.gebo.llms.agent.standardtools.model.SearchToolResult.Status;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.deepsearch.service.impl.DeepSearchQuotations;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;

/**
 * The common ancestor of the deep search tools: a deep search runs the agent's
 * searches directly on a source, reads the documents found as LLM-sized fragments,
 * then analyses every fragment against the agent's question batch by batch and
 * reduces the partial analyses into one (see {@link DeepSearchToolAnalysis}). The
 * agent gets the analysis and its sources, not the documents, so a deep search
 * reads far more than a search tool could return. The sources are every document
 * the analysis read; the agent's answer says which of them it rests on.
 * <p>
 * Everything runs in the tool call: no model plans the searches, they are the
 * agent's ones. A deep search is expensive: a request can make at most
 * {@link DeepSearchToolsSupport#maxDeepSearchesPerRequest()} of them.
 * <p>
 * The subclasses only say whether their source can be searched and search it.
 */
public abstract class AbstractDeepSearchTool<Q> {
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	/** Most searches run by a deep search. */
	static final int MAX_QUERIES = 5;
	/** The shortest final analysis asked for, whatever the room. */
	static final int MIN_ANALYSIS_WORDS = 100;
	/** Longest time a deep search analysis is waited for. */
	static final Duration DEEP_SEARCH_TIMEOUT = Duration.ofMinutes(10);
	private static final String TRUNCATION_MARK = " [...]";
	/** Who reads the analysis, added to the completeness the depth asks. */
	static final String TOOL_COMPLETENESS_NOTE = " The analysis is handed to an assistant that writes the final "
			+ "answer from it: keep every relevant fact, figure and date, and the document each one comes from.";
	/**
	 * A document found by a deep search: as the model sees it, and as the user sees
	 * it among the documents of the answer.
	 */
	public record FoundDocument(Source source, GResponseDocumentRef ref) {
	}

	protected final DeepSearchToolsSupport support;
	protected final String toolName;
	protected final String toolDescription;

	/** The type of a search: String for plain text, else the source's native query type. */
	protected final Class<Q> queryType;

	protected AbstractDeepSearchTool(DeepSearchToolsSupport support, Class<Q> queryType, String toolName,
			String toolDescription) {
		this.support = support;
		this.queryType = queryType;
		this.toolName = toolName;
		this.toolDescription = toolDescription;
	}

	public String getToolName() {
		return toolName;
	}

	/** Whether the current user can deep search this source. */
	protected abstract boolean isAvailable() throws Exception;

	/** What is deep searched, for the logs and the answers to the model. */
	protected abstract String sourceDescription();

	/**
	 * Runs the searches on the source and returns the fragments of at most
	 * {@code maxDocuments} documents found, up to {@code fragmentsPerDocument} of each
	 * where the source can tell, registering the document each fragment comes from by
	 * the fragment id. Runs in the tool call. The searches are never empty for plain
	 * text ones (the question is searched when the agent gave none); native ones can
	 * be, the source then searches the question as text.
	 */
	protected abstract List<Document> searchDocuments(List<Q> queries, String question, int maxDocuments,
			int fragmentsPerDocument, Map<String, FoundDocument> foundByFragmentId) throws Exception;

	/**
	 * The type of the tool's parameter: the parameterized one, which resolves the
	 * searches' schema and parsing to Q; a source may declare a subclass of it.
	 */
	protected Type paramType() {
		return ResolvableType.forClassWithGenerics(DeepSearchToolParam.class, queryType).getType();
	}

	/**
	 * Runs the searches of the call: what {@link #searchDocuments(List, String, int, int, Map)}
	 * does by default; a source reading more of its parameter, or of the context of the
	 * call (such as the knowledge bases of the chat), overrides it, adding to
	 * {@code unavailableSources} each system it could not search and why.
	 */
	protected List<Document> searchDocuments(DeepSearchToolParam<Q> param, List<Q> queries, String question,
			int maxDocuments, int fragmentsPerDocument, Map<String, FoundDocument> foundByFragmentId,
			ToolContext toolContext, List<String> unavailableSources) throws Exception {
		return searchDocuments(queries, question, maxDocuments, fragmentsPerDocument, foundByFragmentId);
	}

	/**
	 * Runs the searches of the call, recording the yield of each search in
	 * {@code searches} when the source runs them one by one: by default as
	 * {@link #searchDocuments(DeepSearchToolParam, List, String, int, int, Map, ToolContext, List)},
	 * recording none.
	 */
	protected List<Document> searchDocuments(DeepSearchToolParam<Q> param, List<Q> queries, String question,
			int maxDocuments, int fragmentsPerDocument, Map<String, FoundDocument> foundByFragmentId,
			ToolContext toolContext, List<String> unavailableSources, List<SearchCoverage> searches) throws Exception {
		return searchDocuments(param, queries, question, maxDocuments, fragmentsPerDocument, foundByFragmentId,
				toolContext, unavailableSources);
	}

	/**
	 * The same, telling in {@code notLoaded} the documents found that could not be
	 * loaded, with why: by default as
	 * {@link #searchDocuments(DeepSearchToolParam, List, String, int, int, Map, ToolContext, List, List)},
	 * telling none.
	 */
	protected List<Document> searchDocuments(DeepSearchToolParam<Q> param, List<Q> queries, String question,
			int maxDocuments, int fragmentsPerDocument, Map<String, FoundDocument> foundByFragmentId,
			ToolContext toolContext, List<String> unavailableSources, List<SearchCoverage> searches,
			List<DocumentNotRead> notLoaded) throws Exception {
		return searchDocuments(param, queries, question, maxDocuments, fragmentsPerDocument, foundByFragmentId,
				toolContext, unavailableSources, searches);
	}

	/**
	 * The documents of the deep search's scope the user can see (the chat's knowledge
	 * bases), for the coverage to tell those no search reached; null when the source
	 * cannot tell (the web, the external systems).
	 */
	protected Long documentsInScope(ToolContext toolContext) {
		return null;
	}

	/**
	 * Whether the agent can read a document of this source whole (a knowledge base
	 * document, with the browsing tools): the coverage then names the sources read in
	 * part. False by default.
	 */
	protected boolean documentsReadableWhole() {
		return false;
	}

	/**
	 * The searches of a call as text, to tell a deep search repeating the searches of
	 * an earlier one of the same request: the searches, or the question when it gives
	 * none.
	 */
	protected List<String> searchesOf(DeepSearchToolParam<Q> param, List<Q> queries, String question) {
		final List<String> searches = new ArrayList<>();
		for (Q query : queries) {
			searches.add(queryText(query));
		}
		if (searches.isEmpty()) {
			searches.add(question);
		}
		return searches;
	}

	public ToolCallback toTool() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Declaring deep search tool:" + toolName + " over " + sourceDescription());
		}
		final Type paramType = paramType();
		final BiFunction<DeepSearchToolParam<Q>, ToolContext, DeepSearchToolResult> toolCall = this::deepSearch;
		return ToolCallbackDeclarationUtil.declare(toolCall, toolName, toolDescription, paramType);
	}

	public ToolReference toToolReference() {
		return new ToolReference(toTool());
	}

	/**
	 * Runs the deep search and returns its analysis. Never throws: a failure is
	 * answered with a {@link Status#FAILED} result the model can read.
	 */
	DeepSearchToolResult deepSearch(DeepSearchToolParam<Q> param, ToolContext toolContext) {
		if (param == null || param.getQuestion() == null || param.getQuestion().isBlank()) {
			return DeepSearchToolResult.of(Status.NO_RESULTS, "No deep search done: the question is empty.");
		}
		final String requestId = ToolCallbackDeclarationUtil.requestId(toolContext);
		final List<Q> queries = queries(param, queryType);
		final String question = question(param);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin deepSearch(...) tool:" + toolName + " over " + sourceDescription() + " with "
					+ queries.size() + " search(es) depth:" + param.getDepth() + " request:" + requestId);
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<DEEP_SEARCH_TOOL_PARAM tool=" + toolName + ">");
			LOGGER.trace(String.valueOf(param));
			LOGGER.trace("</DEEP_SEARCH_TOOL_PARAM>");
		}
		// an analysis takes minutes: none is run when its model call has no room for it,
		// and it does not count as one of the request's deep searches
		if (ToolsTokenBudget.noUsefulRoom(toolContext)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Tool:" + toolName + " not run: " + ToolsTokenBudget.from(toolContext).left()
						+ " (tok) left in its model call's context");
			}
			return DeepSearchToolResult.of(Status.NO_RESULTS,
					"No room is left in the context for more contents: answer with the contents already found.");
		}
		// the same searches find the same documents: the coverage of the first deep search
		// is completed with other searches, not by running it again
		if (!support.firstRunOf(requestId, toolName, searchesOf(param, queries, question))) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Tool:" + toolName + " denied: the same searches already ran in a deep search of request:"
						+ requestId);
			}
			return DeepSearchToolResult.of(Status.NOT_ALLOWED, REPEATED_SEARCHES);
		}
		final int calls = support.countDeepSearch(requestId);
		final int maxDeepSearches = support.maxDeepSearchesPerRequest();
		if (calls > maxDeepSearches) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Tool:" + toolName + " denied: deep search " + calls + " of request:" + requestId
						+ " beyond the limit of " + maxDeepSearches);
			}
			return DeepSearchToolResult.of(Status.NOT_ALLOWED, "The deep searches allowed for this request ("
					+ maxDeepSearches
					+ ") are used up: answer with the contents you already have, or use the search tools.");
		}
		try {
			if (!isAvailable()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Tool:" + toolName + " denied: the current user cannot search " + sourceDescription());
				}
				return DeepSearchToolResult.of(Status.NOT_ALLOWED,
						"The user is not allowed to search this source, go on without it.");
			}
			final IGConfigurableChatModel chatModel = support.chatModelsDao().defaultHandler();
			final IGConfigurableChatModel serviceModel = support.chatModelsDao()
					.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
			if (chatModel == null || serviceModel == null) {
				LOGGER.warn("Tool:" + toolName + " has no chat model to analyse with");
				return DeepSearchToolResult.of(Status.FAILED, "The deep search is not available, go on without it.");
			}
			ToolsProgress.notify(toolContext,
					"Deep search in " + sourceDescription() + ": " + ToolsProgress.shown(question));
			final Map<String, FoundDocument> foundByFragmentId = new LinkedHashMap<>();
			// best effort: the systems out of service or not responding are told to the model
			final List<String> unavailableSources = new ArrayList<>();
			// the yield of each search, when the source runs them one by one
			final List<SearchCoverage> searchYields = new ArrayList<>();
			// the documents found that could not be loaded, with why
			final List<DocumentNotRead> notLoaded = new ArrayList<>();
			final List<Document> fragments = searchDocuments(param, queries, question, support.searchTopK(),
					fragmentsPerDocument(param.getDepth()), foundByFragmentId, toolContext, unavailableSources,
					searchYields, notLoaded);
			if (fragments == null || fragments.isEmpty()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("End deepSearch(...) tool:" + toolName + " found no document, "
							+ unavailableSources.size() + " source(s) not searched, " + notLoaded.size()
							+ " document(s) found not loaded");
				}
				final DeepSearchToolResult none = !notLoaded.isEmpty()
						? DeepSearchToolResult.of(Status.NO_RESULTS, "None of the " + notLoaded.size()
								+ " documents found in " + sourceDescription()
								+ " could be loaded (see documentsNotRead): search something else, or go on without them.")
						: unavailableSources.isEmpty()
								? DeepSearchToolResult.of(Status.NO_RESULTS,
										"No document found in " + sourceDescription() + " for these searches.")
								: DeepSearchToolResult.of(Status.NO_RESULTS, "No document found in "
										+ sourceDescription() + ": some sources could not be searched ("
										+ String.join("; ", unavailableSources) + "). Go on without them.");
				none.setUnavailableSources(unavailableSources.isEmpty() ? null : unavailableSources);
				none.setDocumentsNotRead(notLoaded.isEmpty() ? null : notLoaded);
				return none;
			}
			final DeliverableIntent deliverable = deliverable(param.getDepth());
			final Vector<String> discardedFragmentIds = new Vector<>();
			// every document found, before the analysis drops the ones it judged irrelevant:
			// what the coverage is measured on
			final Map<String, FoundDocument> allFound = new LinkedHashMap<>(foundByFragmentId);
			// what the analysis reports as missing and the fragments it left unread
			final DeepSearchAnalysisOutcome analysisOutcome = new DeepSearchAnalysisOutcome();
			ToolsProgress.notify(toolContext, "Deep search in " + sourceDescription() + ": analysing "
					+ fragments.size() + " fragment(s) of " + distinctByDocument(foundByFragmentId.values()).size()
					+ " document(s)");
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Tool:" + toolName + " analysing " + fragments.size() + " fragment(s) for deliverable:"
						+ deliverable + " with chatModel:" + chatModel.getCode() + " serviceModel:"
						+ serviceModel.getCode());
			}
			// the analysis is asked to fit the room its model call leaves (see lengthTarget)
			final int roomForAnalysis = ToolsTokenBudget.grantFor(toolContext, support.maxAnalysisTokens());
			final String analysis = support.analysis()
					.analyze(Flux.fromIterable(fragments),
							analysisContext(question, requestId, ToolCallbackDeclarationUtil.userLanguage(toolContext)),
							ReactiveIdentityUtil.create(), deliverable,
							TOOL_COMPLETENESS_NOTE + lengthTarget(param.getDepth(), roomForAnalysis), chatModel,
							serviceModel,
							discardedFragmentIds, ToolsProgress.from(toolContext), analysisOutcome)
					.reduce(new StringBuilder(), StringBuilder::append).map(StringBuilder::toString)
					.block(DEEP_SEARCH_TIMEOUT);
			// the fragments the analysis left unread are never its sources
			final Set<String> unreadFragmentIds = analysisOutcome.getUnreadFragmentIds();
			final Set<String> readFragmentIds = new LinkedHashSet<>(foundByFragmentId.keySet());
			readFragmentIds.removeAll(unreadFragmentIds);
			if (readFragmentIds.isEmpty()) {
				// every batch failed or none ran: whatever text came out is not an analysis of
				// the documents found
				LOGGER.warn("Tool:" + toolName + " analysis read none of the " + foundByFragmentId.size()
						+ " fragment(s) found: failed");
				final DeepSearchToolResult failed = DeepSearchToolResult.of(Status.FAILED, NOTHING_READ);
				failed.setUnavailableSources(unavailableSources.isEmpty() ? null : unavailableSources);
				failed.setDocumentsNotRead(notLoaded.isEmpty() ? null : notLoaded);
				return failed;
			}
			// every document read is a source: the partial analyses' lists of irrelevant
			// fragments are not reliable (on large batches a model lists every fragment while
			// analysing them) and decide nothing; the agent reading the analysis says which
			// documents its answer rests on (ANSWER-DOCUMENTS)
			foundByFragmentId.keySet().removeAll(unreadFragmentIds);
			if (LOGGER.isDebugEnabled()) {
				final long judged = discardedFragmentIds.stream().filter(id -> !unreadFragmentIds.contains(id)).count();
				LOGGER.debug("Tool:" + toolName + " " + readFragmentIds.size() + " fragment(s) read, " + judged
						+ " of them listed as irrelevant by the analysis (not used to choose the sources), "
						+ unreadFragmentIds.size() + " left unread");
			}
			final List<FoundDocument> reliedOn = distinctByDocument(foundByFragmentId.values());
			final DeepSearchToolResult result = new DeepSearchToolResult();
			result.setFragmentsAnalysed(fragments.size());
			result.setUnavailableSources(unavailableSources.isEmpty() ? null : unavailableSources);
			for (FoundDocument found : reliedOn) {
				result.getSources().add(found.source());
			}
			final List<DocumentNotRead> notRead = documentsNotRead(notLoaded, allFound, unreadFragmentIds, reliedOn);
			result.setDocumentsNotRead(notRead.isEmpty() ? null : notRead);
			if (LOGGER.isDebugEnabled() && !notRead.isEmpty()) {
				LOGGER.debug("Tool:" + toolName + " tells " + notRead.size() + " document(s) found that give nothing: "
						+ notLoaded.size() + " not loaded, " + (notRead.size() - notLoaded.size())
						+ " not read by the analysis or judged not relevant");
			}
			// how much of what was found the analysis covers, measured here, not judged by a model
			final DeepSearchCoverage coverage = coverage(param.getDepth(), allFound,
					analysisOutcome.getUnreadFragmentIds(), documentLengths(fragments), reliedOn,
					searchYields.isEmpty() ? null : searchYields, documentsInScope(toolContext),
					analysisOutcome.getNotCovered(), documentsReadableWhole(), support.coverageRules());
			result.setCoverage(coverage);
			result.setQuotes(quotesOf(analysis, analysisOutcome, allFound));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Tool:" + toolName + " coverage: " + coverage.getDocumentsUsed() + " of "
						+ coverage.getDocumentsFound() + " document(s) found used, "
						+ analysisOutcome.getUnreadFragmentIds().size() + " fragment(s) of "
						+ coverage.getDocumentsUnread() + " document(s) left unread, not reached:"
						+ coverage.getNotReached() + " not covered:" + coverage.getNotCovered()
						+ " completion required:" + coverage.isCompletionRequired()
						+ (coverage.getNote() != null ? " note:" + coverage.getNote() : ""));
			}
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("<DEEP_SEARCH_TOOL_COVERAGE tool=" + toolName + ">");
				LOGGER.trace(JsonParser.toJson(coverage));
				LOGGER.trace("</DEEP_SEARCH_TOOL_COVERAGE>");
			}
			// the length is asked by the depth and the room (lengthTarget): this only stops a
			// runaway analysis, the sources and the coverage listed with it taking their part
			// of the room left
			final int sourcesTokens = ITokensCountable.stringsTokensSize(JsonParser.toJson(result.getSources()))
					+ ITokensCountable.stringsTokensSize(JsonParser.toJson(coverage))
					+ (notRead.isEmpty() ? 0 : ITokensCountable.stringsTokensSize(JsonParser.toJson(notRead)));
			// the links of the analysis that are no address of the documents found, nor in
			// their contents, are made up: removed before the agent reads them
			final String checkedAnalysis = withoutMadeUpLinks(analysis, allFound, fragments);
			fit(result, checkedAnalysis,
					Math.max(0, ToolsTokenBudget.grantFor(toolContext, support.maxAnalysisTokens()) - sourcesTokens));
			// the agent sharing a collector gives these documents as its answer's ones
			final ToolsFoundDocuments collector = ToolsFoundDocuments.from(toolContext);
			if (collector != null) {
				collector.add(reliedOn.stream().map(FoundDocument::ref).filter(ref -> ref != null).toList());
				// each source carries its short id of the request, the one the answer gives back
				for (FoundDocument found : reliedOn) {
					if (found.ref() != null && found.source() != null) {
						found.source().setDoc(collector.idOf(found.ref().getDocumentCode()));
					}
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Tool:" + toolName + " shared " + reliedOn.size()
							+ " document(s) with the calling agent's answer");
				}
			}
			if (result.getAnalysis() == null || result.getAnalysis().isBlank()) {
				result.setStatus(Status.NO_RESULTS);
				result.setMessage("The documents found gave no analysis for this question.");
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End deepSearch(...) tool:" + toolName + " status:" + result.getStatus() + " analysed "
						+ fragments.size() + " fragment(s), " + discardedFragmentIds.size() + " discarded, "
						+ result.getSources().size() + " source(s), " + result.getTokens() + " token(s)");
			}
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("<DEEP_SEARCH_TOOL_ANALYSIS tool=" + toolName + ">");
				LOGGER.trace(result.getAnalysis());
				LOGGER.trace("</DEEP_SEARCH_TOOL_ANALYSIS>");
			}
			return result;
		} catch (NoSourceSearchedException e) {
			LOGGER.warn("Tool:" + toolName + " could not search any source: " + e.getMessage());
			final DeepSearchToolResult failed = DeepSearchToolResult.of(Status.FAILED, "No source could be searched ("
					+ String.join("; ", e.unavailableSources) + "): go on without it.");
			failed.setUnavailableSources(e.unavailableSources);
			return failed;
		} catch (Throwable th) {
			LOGGER.error("Tool:" + toolName + " failed for question:" + param.getQuestion(), th);
			return DeepSearchToolResult.of(Status.FAILED, "The deep search failed, go on without it.");
		}
	}

	/**
	 * Raised by a source none of whose systems could be searched (out of service, not
	 * responding...): the deep search fails, telling the model why.
	 */
	protected static final class NoSourceSearchedException extends Exception {
		private static final long serialVersionUID = 1L;
		final List<String> unavailableSources;

		protected NoSourceSearchedException(List<String> unavailableSources) {
			super(String.join("; ", unavailableSources));
			this.unavailableSources = List.copyOf(unavailableSources);
		}
	}

	/**
	 * The quotations the analysis kept, checked against their fragments, that its final
	 * text gives: each with the short id of its document; null when none.
	 */
	protected List<DeepSearchToolResult.Quote> quotesOf(String analysis, DeepSearchAnalysisOutcome outcome,
			Map<String, FoundDocument> found) {
		if (analysis == null || outcome == null) {
			return null;
		}
		final List<DeepSearchToolResult.Quote> quotes = new ArrayList<>();
		for (DeepSearchQuotations.Quote quote : outcome.getQuotations().quotes()) {
			if (!analysis.contains(quote.text())) {
				// left out by the consolidation
				continue;
			}
			final FoundDocument document = quote.fragmentId() != null && found != null ? found.get(quote.fragmentId())
					: null;
			final Source source = document != null ? document.source() : null;
			quotes.add(new DeepSearchToolResult.Quote(source != null ? source.getDoc() : null,
					source != null && source.getTitle() != null ? source.getTitle() : quote.title(), quote.text()));
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("quotesOf(...) " + quotes.size() + " of " + outcome.getQuotations().quotes().size()
					+ " verified quotation(s) in the final analysis");
		}
		return quotes.isEmpty() ? null : quotes;
	}

	/**
	 * The context of the analysis: the agent's question, without chat history, and the
	 * language of the user's message, the analysis' language (the question may be
	 * written in the documents' one).
	 */
	static IChatRequestContext analysisContext(String question, String requestId, String userLanguage) {
		final Map<String, Object> toolsContext = new HashMap<>();
		if (requestId != null) {
			toolsContext.put(ToolCallbackDeclarationUtil.REQUEST_ID_CONTEXT_KEY, requestId);
		}
		return IChatRequestContext.builder().actualUserRequest(question).requestID(requestId).userLanguage(userLanguage)
				.consolidatedHistory("")
				.interactions(new ArrayList<>()).documents(new ArrayList<>()).toolsContext(toolsContext)
				.pipelineInfos(new HashMap<>()).build();
	}

	/**
	 * The searches to run: the agent's ones, at most {@value #MAX_QUERIES}, blank and
	 * repeated text ones dropped; for plain text searches, the question when the agent
	 * gave none.
	 */
	@SuppressWarnings("unchecked")
	static <Q> List<Q> queries(DeepSearchToolParam<Q> param, Class<Q> queryType) {
		final List<Q> queries = new ArrayList<>();
		if (param.getQueries() != null) {
			param.getQueries().stream().filter(query -> query != null)
					.map(query -> query instanceof String text ? (Q) text.trim() : query)
					.filter(query -> !(query instanceof String text) || !text.isEmpty()).distinct().limit(MAX_QUERIES)
					.forEach(queries::add);
		}
		if (queries.isEmpty() && queryType == String.class) {
			queries.add((Q) param.getQuestion().trim());
		}
		return queries;
	}

	/** A search as text, for the logs and when it is run as a text search. */
	static String queryText(Object query) {
		if (query instanceof INativeQueryObject nativeQuery) {
			final List<String> keywords = nativeQuery.relevantKeywords();
			if (keywords != null && !keywords.isEmpty()) {
				return String.join(" ", keywords);
			}
		}
		return String.valueOf(query);
	}

	/** The question, completed with what the analysis is for when the model said it. */
	static String question(DeepSearchToolParam<?> param) {
		final String question = param.getQuestion().trim();
		final String objective = param.getSearchObjective();
		return objective != null && !objective.isBlank() ? question + "\n" + objective.trim() : question;
	}

	/** The deliverable the analysis is sized for, from the depth the agent asked. */
	static DeliverableIntent deliverable(Depth depth) {
		if (depth == null) {
			return DeliverableIntent.SUMMARY;
		}
		switch (depth) {
		case FOCUSED:
			return DeliverableIntent.QA;
		case EXHAUSTIVE:
			return DeliverableIntent.ANALISYS;
		default:
			return DeliverableIntent.SUMMARY;
		}
	}

	/**
	 * The fragments read of each document found, from the depth: a few for a precise
	 * answer, many for a detailed report, which needs more than the passages that
	 * matched best.
	 */
	static int fragmentsPerDocument(Depth depth) {
		if (depth == Depth.FOCUSED) {
			return 3;
		} else if (depth == Depth.EXHAUSTIVE) {
			return 10;
		}
		return 6;
	}

	/**
	 * The length the final analysis is asked to keep, from the depth: the model writes
	 * to size, instead of the analysis being cut and losing its conclusions.
	 */
	static String lengthTarget(Depth depth) {
		return lengthTarget(depth, Integer.MAX_VALUE);
	}

	/**
	 * The same, never longer than what {@code maxTokens} can hold (about three words in
	 * four tokens), so an analysis written for a small room is not cut afterwards.
	 */
	static String lengthTarget(Depth depth, int maxTokens) {
		final int words = Math.min(depthWords(depth), Math.max(MIN_ANALYSIS_WORDS, (int) (maxTokens * 3l / 4)));
		return " Keep the final analysis within about " + words + " words.";
	}

	/** The final analysis length a depth asks for, in words. */
	static int depthWords(Depth depth) {
		final int words;
		if (depth == Depth.FOCUSED) {
			words = 400;
		} else if (depth == Depth.EXHAUSTIVE) {
			words = 2500;
		} else {
			words = 1000;
		}
		return words;
	}

	/** Puts the analysis in the result, cut to {@code maxTokens} when longer. */
	static void fit(DeepSearchToolResult result, String analysis, int maxTokens) {
		if (analysis == null) {
			return;
		}
		final String text = analysis.trim();
		final int tokens = ITokensCountable.stringsTokensSize(text);
		if (tokens <= maxTokens) {
			result.setAnalysis(text);
			result.setTokens(tokens);
			return;
		}
		final int chars = (int) Math.max(1, ((long) text.length() * maxTokens) / Math.max(1, tokens));
		final String cut = text.substring(0, Math.min(text.length(), chars)) + TRUNCATION_MARK;
		result.setAnalysis(cut);
		result.setTokens(ITokensCountable.stringsTokensSize(cut));
		result.setMessage("The analysis was cut to the requested size.");
	}

	/** What the agent is told to do when the coverage of an analysis is thin. */
	static final String COMPLETION_ADVICE = "If the question needs more than this, complete it before answering: "
			+ "focused searches, the documents named read whole when they can be, or a deep search with other searches "
			+ "aimed at what is missing (for example by the title or the author of the documents not used).";

	/** What the agent is told when a deep search repeats the searches of an earlier one. */
	static final String REPEATED_SEARCHES = "A deep search of this request already ran these searches: it would "
			+ "find the same documents, its result is the one you have. To complete its coverage use other "
			+ "searches (other terms, the titles or authors of the documents not used), read whole the documents "
			+ "it names, or answer with what you have.";

	/** Why a document found is not among the sources: the analysis stopped before it. */
	static final String NOT_READ_BY_THE_ANALYSIS = "found, not read by the analysis (it stopped before its fragments)";
	/** Why a document found is not among the sources: read and judged not relevant. */
	static final String JUDGED_NOT_RELEVANT = "read, judged not relevant to the question";

	/**
	 * The documents found that give nothing to the analysis, with why: the ones not
	 * loaded, then each document found that is not a source: not read by the analysis
	 * when none of its fragments was read, else judged not relevant.
	 */
	static List<DocumentNotRead> documentsNotRead(List<DocumentNotRead> notLoaded, Map<String, FoundDocument> found,
			Set<String> unreadFragmentIds, List<FoundDocument> reliedOn) {
		final List<DocumentNotRead> notRead = new ArrayList<>(notLoaded);
		final Set<String> sources = new LinkedHashSet<>();
		for (FoundDocument document : reliedOn) {
			if (document != null && document.source() != null && document.source().getDocumentCode() != null) {
				sources.add(document.source().getDocumentCode());
			}
		}
		final Map<String, Source> byCode = new LinkedHashMap<>();
		final Set<String> read = new LinkedHashSet<>();
		for (Map.Entry<String, FoundDocument> fragment : found.entrySet()) {
			final FoundDocument document = fragment.getValue();
			if (document == null || document.source() == null || document.source().getDocumentCode() == null) {
				continue;
			}
			final String code = document.source().getDocumentCode();
			byCode.putIfAbsent(code, document.source());
			if (unreadFragmentIds == null || !unreadFragmentIds.contains(fragment.getKey())) {
				read.add(code);
			}
		}
		for (Map.Entry<String, Source> document : byCode.entrySet()) {
			if (!sources.contains(document.getKey())) {
				notRead.add(new DocumentNotRead(document.getValue().getTitle(), document.getValue().getSource(),
						read.contains(document.getKey()) ? JUDGED_NOT_RELEVANT : NOT_READ_BY_THE_ANALYSIS));
			}
		}
		return notRead;
	}

	/**
	 * The analysis without the links that are no address of the documents found nor
	 * appear in the fragments read: a partial analysis can build them from a document's
	 * path or code. The text of a link stays.
	 */
	String withoutMadeUpLinks(String analysis, Map<String, FoundDocument> found, List<Document> fragments) {
		if (analysis == null || analysis.isBlank()) {
			return analysis;
		}
		final Set<String> known = new LinkedHashSet<>();
		for (FoundDocument document : found.values()) {
			if (document != null && document.source() != null && document.source().getSource() != null) {
				CitedAddresses.addresses(List.of(document.source().getSource()), known);
			}
		}
		for (Document fragment : fragments) {
			if (fragment == null) {
				continue;
			}
			CitedAddresses.addressesIn(fragment.getText(), known);
			if (fragment.getMetadata() != null) {
				final Object url = fragment.getMetadata().get(DocumentMetaInfos.CONTENT_ORIGINAL_URL);
				if (url != null) {
					CitedAddresses.addresses(List.of(url.toString()), known);
				}
			}
		}
		final List<String> removed = new ArrayList<>();
		final String checked = CitedAddresses.withoutUnknown(analysis, address -> CitedAddresses.isKnown(address, known),
				removed::add);
		if (!removed.isEmpty()) {
			LOGGER.warn("Tool:" + toolName + " analysis gave " + removed.size()
					+ " address(es) no document found has, removed: " + removed);
		}
		return checked;
	}

	/** What the agent is told when the analysis read none of the documents found. */
	static final String NOTHING_READ = "The documents found could not be analysed (the analysis failed): there "
			+ "is no analysis of them. Use the search tools, or answer with what you have and say what could not "
			+ "be checked.";

	/** Most document names a coverage note lists for each reason. */
	static final int MAX_NAMED_DOCUMENTS = 5;

	/**
	 * The length of each document found, in fragments, by document code, when the
	 * fragments tell it.
	 */
	static Map<String, Long> documentLengths(List<Document> fragments) {
		final Map<String, Long> lengths = new HashMap<>();
		for (Document fragment : fragments) {
			if (fragment == null || fragment.getMetadata() == null) {
				continue;
			}
			final Object code = fragment.getMetadata().get(DocumentMetaInfos.CONTENT_CODE);
			final Long length = longOf(fragment.getMetadata().get(DocumentMetaInfos.GEBO_CHUNKS_COUNT));
			if (code != null && length != null && length > 0) {
				lengths.merge(code.toString(), length, Math::max);
			}
		}
		return lengths;
	}

	private static Long longOf(Object value) {
		if (value instanceof Number number) {
			return number.longValue();
		}
		if (value != null) {
			try {
				return Long.parseLong(value.toString().trim());
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * How much of what was found the analysis covers, and whether it is thin, by rules
	 * that hold for any kind of document. A precise answer (FOCUSED) is never thin; any
	 * other depth is when the analysis reports something missing, when documents found
	 * were left unread (the analysis stopped before their fragments), when more than
	 * {@code barelyReadShare} of its sources were read in at most
	 * {@code barelyReadFragments} fragments of a longer document (only where a document
	 * can be read whole and its length is known), or when it rests on fewer documents
	 * than {@code minDocumentsUsed} out of at least {@code minDocumentsFound} used or
	 * left unread. The documents judged irrelevant were read, not missed; the documents
	 * of the scope no search reached are told, not judged: their number says nothing of
	 * the question.
	 *
	 * @param found         every document found, by fragment id, before the analysis
	 * @param unread        the fragments the analysis left unread
	 * @param lengths       the length of each document in fragments, by document code,
	 *                      when known
	 * @param reliedOn      the documents the analysis relies on (its sources)
	 * @param searches      the yield of each search, null when not known
	 * @param inScope       the documents of the scope, null when not known
	 * @param notCovered    what the analysis reports as missing, null when nothing
	 * @param readableWhole whether a document of the source can be read whole
	 */
	static DeepSearchCoverage coverage(Depth depth, Map<String, FoundDocument> found, Set<String> unread,
			Map<String, Long> lengths, List<FoundDocument> reliedOn, List<SearchCoverage> searches, Long inScope,
			String notCovered, boolean readableWhole, CoverageRules rules) {
		final DeepSearchCoverage coverage = new DeepSearchCoverage();
		// fragments read and left unread, by document code
		final Map<String, int[]> fragmentsByDocument = new LinkedHashMap<>();
		final Map<String, String> names = new LinkedHashMap<>();
		for (Map.Entry<String, FoundDocument> fragment : found.entrySet()) {
			final FoundDocument document = fragment.getValue();
			if (document == null || document.source() == null || document.source().getDocumentCode() == null) {
				continue;
			}
			final String code = document.source().getDocumentCode();
			fragmentsByDocument.computeIfAbsent(code, key -> new int[2])[unread != null
					&& unread.contains(fragment.getKey()) ? 1 : 0]++;
			names.putIfAbsent(code, notBlank(document.source().getTitle()) ? document.source().getTitle() : code);
		}
		final Set<String> used = new LinkedHashSet<>();
		for (FoundDocument document : reliedOn) {
			if (document != null && document.source() != null && document.source().getDocumentCode() != null
					&& fragmentsByDocument.containsKey(document.source().getDocumentCode())) {
				used.add(document.source().getDocumentCode());
			}
		}
		final List<String> unreadDocuments = new ArrayList<>();
		final List<String> readInPart = new ArrayList<>();
		for (Map.Entry<String, int[]> document : fragmentsByDocument.entrySet()) {
			final String code = document.getKey();
			final int read = document.getValue()[0];
			final Long length = lengths != null ? lengths.get(code) : null;
			coverage.getDocuments().add(new DocumentCoverage(names.get(code), read, document.getValue()[1],
					length != null ? Integer.valueOf(length.intValue()) : null, used.contains(code)));
			if (read == 0) {
				unreadDocuments.add(names.get(code));
			} else if (readableWhole && used.contains(code) && length != null && length > read
					&& read <= rules.barelyReadFragments()) {
				readInPart.add(names.get(code) + " (" + read + " of " + length + " fragments)");
			}
		}
		final int documentsFound = fragmentsByDocument.size();
		coverage.setDocumentsFound(documentsFound);
		coverage.setDocumentsUsed(used.size());
		coverage.setDocumentsUnread(unreadDocuments.size());
		coverage.setNotReached(inScope != null ? (int) Math.max(0, inScope - documentsFound) : null);
		coverage.setNotCovered(notBlank(notCovered) ? notCovered.trim() : null);
		coverage.setSearches(searches);
		if (depth == Depth.FOCUSED) {
			// a precise answer may rest on a single document
			return coverage;
		}
		final List<String> reasons = new ArrayList<>();
		if (coverage.getNotCovered() != null) {
			reasons.add("the analysis reports as missing: " + coverage.getNotCovered());
		}
		if (!unreadDocuments.isEmpty()) {
			reasons.add(unreadDocuments.size() + " of the " + documentsFound
					+ " documents found were not read by the analysis: " + named(unreadDocuments));
		}
		if (!used.isEmpty() && readInPart.size() > used.size() * rules.barelyReadShare()) {
			reasons.add(readInPart.size() + " of the " + used.size() + " sources were read in part: "
					+ named(readInPart));
		}
		final int candidates = used.size() + unreadDocuments.size();
		if (candidates >= rules.minDocumentsFound() && used.size() < rules.minDocumentsUsed()) {
			reasons.add("the analysis rests on " + used.size() + " of the " + candidates
					+ " documents it used or left unread");
		}
		if (!reasons.isEmpty()) {
			coverage.setNote("The coverage of this deep search is thin: " + String.join("; ", reasons) + ". "
					+ COMPLETION_ADVICE);
			coverage.setCompletionRequired(rules.gateEnabled());
		}
		return coverage;
	}

	/** Names for a note, at most {@link #MAX_NAMED_DOCUMENTS}. */
	static String named(List<String> names) {
		if (names.size() <= MAX_NAMED_DOCUMENTS) {
			return String.join(", ", names);
		}
		return String.join(", ", names.subList(0, MAX_NAMED_DOCUMENTS)) + " and " + (names.size() - MAX_NAMED_DOCUMENTS)
				+ " more";
	}

	/** The documents relied on, once each. */
	static List<FoundDocument> distinctByDocument(Iterable<FoundDocument> found) {
		final Map<String, FoundDocument> byCode = new LinkedHashMap<>();
		for (FoundDocument document : found) {
			if (document != null && document.source() != null && document.source().getDocumentCode() != null) {
				byCode.putIfAbsent(document.source().getDocumentCode(), document);
			}
		}
		return new ArrayList<>(byCode.values());
	}

	static boolean notBlank(String value) {
		return value != null && !value.isBlank();
	}
}
