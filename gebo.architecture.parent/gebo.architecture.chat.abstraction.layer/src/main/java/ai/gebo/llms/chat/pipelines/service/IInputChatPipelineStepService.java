package ai.gebo.llms.chat.pipelines.service;

import ai.gebo.llms.chat.pipelines.model.ui.PipelineChatMenu;

public interface IInputChatPipelineStepService extends IIntermediateProcessingChatPipelineStepService {
	@Override
	default StepType getStepType() {

		return StepType.INPUT;
	}
	
}
