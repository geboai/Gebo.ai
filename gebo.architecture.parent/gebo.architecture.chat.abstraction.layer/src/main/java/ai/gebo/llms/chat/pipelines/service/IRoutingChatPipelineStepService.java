package ai.gebo.llms.chat.pipelines.service;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.RoutingDecision;

public interface IRoutingChatPipelineStepService extends IChatPipelineStepService {
	@Override
	default StepType getStepType() {

		return StepType.ROUTING;
	}

	

	public RoutingDecision execute(ChatPipelineExecutionRuntimeData runtimeData, ISinkUIEmitter emitter,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel) throws ChatPipelineException;
}
