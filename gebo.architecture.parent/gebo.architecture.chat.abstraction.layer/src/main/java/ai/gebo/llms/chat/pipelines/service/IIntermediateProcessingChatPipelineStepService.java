package ai.gebo.llms.chat.pipelines.service;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IChatPipelineStepRuntimeData;

public interface IIntermediateProcessingChatPipelineStepService extends IChatPipelineStepService {
	public IChatPipelineStepRuntimeData execute(ChatPipelineExecutionRuntimeData input,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel) throws ChatPipelineException;
}
