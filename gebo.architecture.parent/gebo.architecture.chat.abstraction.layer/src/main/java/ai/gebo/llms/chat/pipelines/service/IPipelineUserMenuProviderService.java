package ai.gebo.llms.chat.pipelines.service;

import java.util.List;

import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter;
import ai.gebo.llms.chat.pipelines.model.ui.PipelineChatMenu;

public interface IPipelineUserMenuProviderService {
	public String getPipelineId();

	public List<PipelineChatMenu> getUIMenu(String chatProfileCode);

}
