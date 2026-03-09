package ai.gebo.llms.chat.pipelines.service;

import java.util.List;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter;
import ai.gebo.llms.chat.pipelines.model.ui.PipelineChatMenu;
import reactor.core.publisher.Flux;

public interface IStreamingOutputChatPipelineService extends IChatPipelineStepService {
	@Override
	default StepType getStepType() {
		return StepType.OUTPUT;
	}

	public List<StepEnvironmentParameter> getRequiredParameters();

	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws ChatPipelineException, GeboChatSessionLifecycleException, LLMConfigException;
}
