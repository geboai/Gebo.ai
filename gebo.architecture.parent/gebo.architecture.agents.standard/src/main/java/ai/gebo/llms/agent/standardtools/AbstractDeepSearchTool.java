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
import java.util.List;
import java.util.Map;
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
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolParam;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolParam.Depth;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolResult;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolResult.Source;
import ai.gebo.llms.agent.standardtools.model.SearchToolResult.Status;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;

/**
 * The common ancestor of the deep search tools: a deep search runs the agent's
 * searches directly on a source, reads the documents found as LLM-sized fragments,
 * then analyses every fragment against the agent's question batch by batch and
 * reduces the partial analyses into one (see {@link DeepSearchToolAnalysis}). The
 * agent gets the analysis and its sources, not the documents, so a deep search
 * reads far more than a search tool could return.
 * <p>
 * Everything runs in the tool call: no model plans the searches, they are the
 * agent's ones. A deep search is expensive: a request can make at most
 * {@value #MAX_DEEP_SEARCHES_PER_REQUEST} of them.
 * <p>
 * The subclasses only say whether their source can be searched and search it.
 */
public abstract class AbstractDeepSearchTool<Q> {
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	/** Deep searches a single user request can make, whatever the sources. */
	public static final int MAX_DEEP_SEARCHES_PER_REQUEST = 2;
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
		final int calls = support.countDeepSearch(requestId);
		if (calls > MAX_DEEP_SEARCHES_PER_REQUEST) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Tool:" + toolName + " denied: deep search " + calls + " of request:" + requestId
						+ " beyond the limit of " + MAX_DEEP_SEARCHES_PER_REQUEST);
			}
			return DeepSearchToolResult.of(Status.NOT_ALLOWED, "The deep searches allowed for this request ("
					+ MAX_DEEP_SEARCHES_PER_REQUEST
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
			final List<Document> fragments = searchDocuments(param, queries, question, support.searchTopK(),
					fragmentsPerDocument(param.getDepth()), foundByFragmentId, toolContext, unavailableSources);
			if (fragments == null || fragments.isEmpty()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("End deepSearch(...) tool:" + toolName + " found no document, "
							+ unavailableSources.size() + " source(s) not searched");
				}
				final DeepSearchToolResult none = unavailableSources.isEmpty()
						? DeepSearchToolResult.of(Status.NO_RESULTS,
								"No document found in " + sourceDescription() + " for these searches.")
						: DeepSearchToolResult.of(Status.NO_RESULTS, "No document found in " + sourceDescription()
								+ ": some sources could not be searched (" + String.join("; ", unavailableSources)
								+ "). Go on without them.");
				none.setUnavailableSources(unavailableSources.isEmpty() ? null : unavailableSources);
				return none;
			}
			final DeliverableIntent deliverable = deliverable(param.getDepth());
			final Vector<String> discardedFragmentIds = new Vector<>();
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
					.analyze(Flux.fromIterable(fragments), analysisContext(question, requestId),
							ReactiveIdentityUtil.create(), deliverable,
							TOOL_COMPLETENESS_NOTE + lengthTarget(param.getDepth(), roomForAnalysis), chatModel,
							serviceModel,
							discardedFragmentIds, ToolsProgress.from(toolContext))
					.reduce(new StringBuilder(), StringBuilder::append).map(StringBuilder::toString)
					.block(DEEP_SEARCH_TIMEOUT);
			if (analysis != null && !analysis.isBlank()
					&& discardedFragmentIds.containsAll(foundByFragmentId.keySet())) {
				// every fragment judged irrelevant, yet analysed: the judgement contradicts the
				// analysis, the documents found stay its sources
				LOGGER.warn("Tool:" + toolName + " analysis judged all the " + foundByFragmentId.size()
						+ " fragment(s) irrelevant while analysing them: keeping their documents as sources");
			} else {
				for (String fragmentId : discardedFragmentIds) {
					foundByFragmentId.remove(fragmentId);
				}
			}
			final List<FoundDocument> reliedOn = distinctByDocument(foundByFragmentId.values());
			final DeepSearchToolResult result = new DeepSearchToolResult();
			result.setFragmentsAnalysed(fragments.size());
			result.setUnavailableSources(unavailableSources.isEmpty() ? null : unavailableSources);
			for (FoundDocument found : reliedOn) {
				result.getSources().add(found.source());
			}
			// the length is asked by the depth and the room (lengthTarget): this only stops a
			// runaway analysis, the sources listed with it taking their part of the room left
			final int sourcesTokens = ITokensCountable.stringsTokensSize(JsonParser.toJson(result.getSources()));
			fit(result, analysis,
					Math.max(0, ToolsTokenBudget.grantFor(toolContext, support.maxAnalysisTokens()) - sourcesTokens));
			// the agent sharing a collector gives these documents as its answer's ones
			final ToolsFoundDocuments collector = ToolsFoundDocuments.from(toolContext);
			if (collector != null) {
				collector.add(reliedOn.stream().map(FoundDocument::ref).filter(ref -> ref != null).toList());
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

	/** The context of the analysis: the agent's question, without chat history. */
	static IChatRequestContext analysisContext(String question, String requestId) {
		final Map<String, Object> toolsContext = new HashMap<>();
		if (requestId != null) {
			toolsContext.put(ToolCallbackDeclarationUtil.REQUEST_ID_CONTEXT_KEY, requestId);
		}
		return IChatRequestContext.builder().actualUserRequest(question).requestID(requestId).consolidatedHistory("")
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
