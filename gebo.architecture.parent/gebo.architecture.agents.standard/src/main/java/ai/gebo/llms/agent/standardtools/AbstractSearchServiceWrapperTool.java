package ai.gebo.llms.agent.standardtools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.search.service.ISearchService;

/**
 * A search service exposed to the model as a tool that answers with well formed
 * contents: the work common to every search tool is done by the
 * {@link SearchToolContentPipeline}, the subclasses only declare the tool and
 * turn its query into searches of the wrapped service.
 */
public abstract class AbstractSearchServiceWrapperTool {
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	protected final SearchToolContentPipeline pipeline;
	protected final String toolName;
	protected final String toolDescription;

	protected AbstractSearchServiceWrapperTool(SearchToolContentPipeline pipeline, String toolName,
			String toolDescription) {
		this.pipeline = pipeline;
		this.toolName = toolName;
		this.toolDescription = toolDescription;
	}

	public abstract ISearchService<?> getWrapped();

	public abstract ToolCallback toTool();

	public ToolReference toToolReference() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Building the tool reference of the search tool:" + toolName);
		}
		return new ToolReference(toTool());
	}

	public String getToolName() {
		return toolName;
	}
}
