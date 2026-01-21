package ai.gebo.llms.chat.pipelines.service;

import java.util.List;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IChatPipelineStepRuntimeData;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface IRoutingChatPipelineStepService extends IChatPipelineStepService {
	@Override
	default StepType getStepType() {

		return StepType.ROUTING;
	}

	@AllArgsConstructor
	@Getter
	public final static class RoutingDecision {
		private final List<String> futureRoute;
		private final IChatPipelineStepRuntimeData processedOutput;
	}

	public RoutingDecision execute(ChatPipelineExecutionRuntimeData runtimeData, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel) throws ChatPipelineException;
}
