package ai.gebo.llms.chat.agent.standardtools;

import java.util.List;
import java.util.function.BiFunction;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.llms.chat.agent.standardtools.model.SearchQueryParam;
import ai.gebo.llms.chat.agent.standardtools.model.SearchResultSample;
import ai.gebo.llms.chat.agent.standardtools.model.SearchResultSample.SearchResultSampleList;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SearchServiceWrapperTool extends AbstractSearchServiceWrapperTool {
	private final ISearchService wrapped;

	SearchResultSampleList search(SearchQueryParam param) {
		return new SearchResultSampleList();
	}

	@Override
	public ToolCallback toTool() {
		final BiFunction<SearchQueryParam, ToolContext, SearchResultSampleList> toolCall = (p, ctx) -> search(p);
		final String toolName = wrapped.getProductId() + "Search";
		final String toolDescription = wrapped.getProductId() + " search tool";
		ToolCallback tool = ToolCallbackDeclarationUtil.declare(toolCall, toolName, toolDescription,
				SearchQueryParam.class, SearchResultSampleList.class);
		return tool;
	}

	@Override
	public ToolReference toToolReference() {
		return new ToolReference(toTool());
	}

}
