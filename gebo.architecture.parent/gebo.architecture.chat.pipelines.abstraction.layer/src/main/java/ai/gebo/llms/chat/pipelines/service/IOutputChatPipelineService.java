package ai.gebo.llms.chat.pipelines.service;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatResponse;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;

public interface IOutputChatPipelineService extends IChatPipelineStepService {
	@Override
	default StepType getStepType() {
		return StepType.OUTPUT;
	}

	public GeboChatResponse execute(ChatPipelineExecutionRuntimeData runtimeData, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel) throws ChatPipelineException;
}
