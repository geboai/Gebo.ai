package ai.gebo.llms.chat.pipelines.service;

public interface IEnrichingChatPipelineStepService extends IIntermediateProcessingChatPipelineStepService {
	@Override
	default StepType getStepType() {
		return StepType.ENRICHING;
	}
}
