package ai.gebo.llms.chat.agent.standardtools;

import java.util.List;

import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.llms.chat.agent.standardtools.model.SearchResultSample;
import ai.gebo.llms.chat.agent.standardtools.model.SearchResultSample.SearchResultSampleList;

public abstract class AbstractSearchServiceWrapperTool {

	public abstract ToolCallback toTool();

	public abstract ToolReference toToolReference();

	protected SearchResultSampleList loadSamples(List<SearchResult> results, Integer textSampleTokens) {

		return new SearchResultSampleList();
	}
}
