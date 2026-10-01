package ai.gebo.llms.agent.standardtools;

import java.util.List;
import java.util.function.BiFunction;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.llms.agent.standard.services.SearchResultsChunker;
import ai.gebo.llms.agent.standardtools.model.SearchQueryParam;
import ai.gebo.llms.agent.standardtools.model.SearchToolResult;

/**
 * A search service searched with a plain text query, e.g. the web search.
 */
public class SearchServiceWrapperTool extends AbstractSearchServiceWrapperTool {
	private final ISearchService<?> wrapped;

	public SearchServiceWrapperTool(SearchToolContentPipeline pipeline, ISearchService<?> wrapped, String toolName,
			String toolDescription) {
		super(pipeline, toolName, toolDescription);
		this.wrapped = wrapped;
	}

	@Override
	public ISearchService<?> getWrapped() {
		return wrapped;
	}

	SearchToolResult search(SearchQueryParam param, ToolContext toolContext) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Search tool:" + toolName + " invoked on service:" + wrapped.getId());
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<SEARCH_TOOL_PARAM tool=" + toolName + ">");
			LOGGER.trace(String.valueOf(param));
			LOGGER.trace("</SEARCH_TOOL_PARAM>");
		}
		final SearchQuery query = new SearchQuery();
		if (param != null) {
			query.setQueryText(param.getQuery());
			query.setRelevantKeywords(SearchResultsChunker.keywordsFromText(param.getQuery()));
		}
		final List<String> keywords = query.getRelevantKeywords() != null ? query.getRelevantKeywords() : List.of();
		return pipeline.run(wrapped, toolName, toolDescription, param, keywords,
				(system, nEntryLimit) -> wrapped.search(query, system, nEntryLimit), toolContext);
	}

	@Override
	public ToolCallback toTool() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Declaring search tool:" + toolName + " wrapping the search service:" + wrapped.getId());
		}
		final BiFunction<SearchQueryParam, ToolContext, SearchToolResult> toolCall = this::search;
		return ToolCallbackDeclarationUtil.declare(toolCall, toolName, toolDescription, SearchQueryParam.class,
				SearchToolResult.class);
	}
}
