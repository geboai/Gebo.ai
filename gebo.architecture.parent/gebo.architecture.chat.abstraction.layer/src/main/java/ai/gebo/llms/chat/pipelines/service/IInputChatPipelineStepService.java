package ai.gebo.llms.chat.pipelines.service;

public interface IInputChatPipelineStepService extends IIntermediateProcessingChatPipelineStepService {
	@Override
	default StepType getStepType() {

		return StepType.INPUT;
	}
	
}
