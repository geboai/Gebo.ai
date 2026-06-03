package ai.gebo.llms.chat.agent.standardtools;

import java.util.List;
import java.util.function.BiFunction;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.llms.chat.agent.standardtools.model.SearchQueryParam;
import ai.gebo.llms.chat.agent.standardtools.model.SearchResultSample;
import ai.gebo.llms.chat.agent.standardtools.model.SearchResultSample.SearchResultSampleList;
import lombok.AllArgsConstructor;


public class SearchServiceWrapperTool extends AbstractSearchServiceWrapperTool {
	

	private static final String SEARCH_TOOL_DESCRIPTION = " search tool";
	private static final String SEARCH = "Search";
	private final ISearchService wrapped;
	public SearchServiceWrapperTool(IDocumentsChunkService chunkingService, ISearchService wrapped) {		
		super(chunkingService);
		this.wrapped = wrapped;
		
	}
	SearchResultSampleList search(SearchQueryParam param) {
		return new SearchResultSampleList();
	}

	@Override
	public ToolCallback toTool() {
		final BiFunction<SearchQueryParam, ToolContext, SearchResultSampleList> toolCall = (p, ctx) -> search(p);
		final String toolName = wrapped.getProductId() + SEARCH;
		final String toolDescription = wrapped.getProductId() + SEARCH_TOOL_DESCRIPTION;
		ToolCallback tool = ToolCallbackDeclarationUtil.declare(toolCall, toolName, toolDescription,
				SearchQueryParam.class, SearchResultSampleList.class);
		return tool;
	}

	@Override
	public ToolReference toToolReference() {
		return new ToolReference(toTool());
	}

}
