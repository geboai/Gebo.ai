package ai.gebo.llms.chat.pipelines.service;

import java.util.List;

import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter;
import ai.gebo.llms.chat.pipelines.model.ui.PipelineChatMenu;

public interface ICommonOutputPipelineService {
	public PipelineChatMenu getUIMenu();
	public List<StepEnvironmentParameter> getRequiredParameters();
}
