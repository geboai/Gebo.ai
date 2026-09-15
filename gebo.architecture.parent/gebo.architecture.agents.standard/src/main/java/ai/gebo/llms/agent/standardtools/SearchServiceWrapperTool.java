package ai.gebo.llms.agent.standardtools;

import java.util.function.BiFunction;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.llms.agent.standardtools.model.SearchQueryParam;
import ai.gebo.llms.agent.standardtools.model.SearchResultSample.SearchResultSampleList;


public class SearchServiceWrapperTool extends AbstractSearchServiceWrapperTool {
	

	private static final String SEARCH_TOOL_DESCRIPTION = " search tool";
	private static final String SEARCH = "Search";
	private final ISearchService wrapped;
	public SearchServiceWrapperTool(IDocumentsChunkService chunkingService, ISearchService wrapped) {		
		super(chunkingService);
		this.wrapped = wrapped;
		
	}
	SearchResultSampleList search(SearchQueryParam param) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Search tool for product:" + wrapped.getProductId() + " invoked, topK:"
					+ (param != null ? param.getTopK() : null));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<SEARCH_TOOL_PARAM product=" + wrapped.getProductId() + ">");
			LOGGER.trace(String.valueOf(param));
			LOGGER.trace("</SEARCH_TOOL_PARAM>");
		}
		return new SearchResultSampleList();
	}

	@Override
	public ToolCallback toTool() {
		final BiFunction<SearchQueryParam, ToolContext, SearchResultSampleList> toolCall = (p, ctx) -> search(p);
		final String toolName = wrapped.getProductId() + SEARCH;
		final String toolDescription = wrapped.getProductId() + SEARCH_TOOL_DESCRIPTION;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Declaring search tool:" + toolName + " wrapping the search service of product:"
					+ wrapped.getProductId());
		}
		ToolCallback tool = ToolCallbackDeclarationUtil.declare(toolCall, toolName, toolDescription,
				SearchQueryParam.class, SearchResultSampleList.class);
		return tool;
	}

	@Override
	public ToolReference toToolReference() {
		return new ToolReference(toTool());
	}

}
