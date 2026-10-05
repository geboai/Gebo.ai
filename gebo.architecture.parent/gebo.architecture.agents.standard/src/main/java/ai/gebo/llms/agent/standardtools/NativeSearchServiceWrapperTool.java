package ai.gebo.llms.agent.standardtools;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.core.ResolvableType;

import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.service.INativeQueryObject;
import ai.gebo.architecture.search.service.INativeSearchService;
import ai.gebo.llms.agent.standard.services.SearchResultsChunker;
import ai.gebo.llms.agent.standardtools.model.NativeSearchParam;
import ai.gebo.llms.agent.standardtools.model.SearchToolResult;

/**
 * A search service searched with its own native query structure (e.g. a JQL or
 * CQL based filter, a web search provider's query). The tool parameter is
 * {@code NativeSearchParam<N>}, N being the service's native query type: the
 * parameterized type is handed to the tool declaration as it is, so the input
 * schema and the parsing of the model arguments both resolve N without generating
 * any class.
 * <p>
 * When the native search fails on a system, the system is searched with the query's
 * text instead, so a native query the system rejects does not lose the search.
 */
public class NativeSearchServiceWrapperTool<N extends INativeQueryObject> extends AbstractSearchServiceWrapperTool {
	private final INativeSearchService<?, N> wrapped;

	public NativeSearchServiceWrapperTool(SearchToolContentPipeline pipeline, INativeSearchService<?, N> wrapped,
			String toolName, String toolDescription) {
		super(pipeline, toolName, toolDescription);
		this.wrapped = wrapped;
	}

	@Override
	public INativeSearchService<?, N> getWrapped() {
		return wrapped;
	}

	SearchToolResult search(NativeSearchParam<N> param, ToolContext toolContext) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Native search tool:" + toolName + " invoked on service:" + wrapped.getId());
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<NATIVE_SEARCH_TOOL_PARAM tool=" + toolName + ">");
			LOGGER.trace(String.valueOf(param));
			LOGGER.trace("</NATIVE_SEARCH_TOOL_PARAM>");
		}
		final N query = param != null ? param.getQuery() : null;
		final List<String> keywords = new ArrayList<>();
		if (query != null && query.relevantKeywords() != null) {
			for (String keyword : query.relevantKeywords()) {
				keywords.addAll(SearchResultsChunker.keywordsFromText(keyword));
			}
		}
		final String queryText = param != null ? param.queryText() : null;
		return pipeline.run(wrapped, toolName, toolDescription, param, keywords, (system, nEntryLimit, parameters) -> {
			if (query != null) {
				try {
					return wrapped.nativeSearch(query, system, nEntryLimit, parameters);
				} catch (Exception e) {
					LOGGER.warn("Native search tool:" + toolName + " failed on system:" + system.getCode()
							+ ", searching it with the query text instead", e);
				}
			}
			return wrapped.search(textQuery(queryText, keywords), system, nEntryLimit, parameters);
		}, toolContext);
	}

	/** The plain text search equivalent to a native query. */
	static SearchQuery textQuery(String queryText, List<String> keywords) {
		final SearchQuery searchQuery = new SearchQuery();
		searchQuery.setQueryText(queryText);
		searchQuery.setRelevantKeywords(
				keywords != null && !keywords.isEmpty() ? keywords : SearchResultsChunker.keywordsFromText(queryText));
		return searchQuery;
	}

	@Override
	public ToolCallback toTool() {
		final Class<N> queryType = wrapped.getNativeSearchDataStructureType();
		final Type paramType = ResolvableType.forClassWithGenerics(NativeSearchParam.class, queryType).getType();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Declaring native search tool:" + toolName + " with the query type:" + queryType.getName());
		}
		final BiFunction<NativeSearchParam<N>, ToolContext, SearchToolResult> toolCall = this::search;
		return ToolCallbackDeclarationUtil.declare(toolCall, toolName, toolDescription, paramType);
	}
}
