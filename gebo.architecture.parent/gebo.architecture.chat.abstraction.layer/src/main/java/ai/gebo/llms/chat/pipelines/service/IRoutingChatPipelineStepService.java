package ai.gebo.llms.chat.pipelines.service;

import java.util.List;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.RoutingDecision;

public interface IRoutingChatPipelineStepService extends IChatPipelineStepService {
	@Override
	default StepType getStepType() {

		return StepType.ROUTING;
	}

	

	/**
	 * The ids of the steps this router can route to: the routes are decided in its code,
	 * so this is how the steps of a pipeline are known outside a run.
	 */
	public List<String> getReachableStepIds();

	public RoutingDecision execute(ChatPipelineExecutionRuntimeData runtimeData, ISinkUIEmitter emitter,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel) throws ChatPipelineException;
}
