package ai.gebo.llms.chat.pipelines.service;

import java.io.IOException;
import java.util.List;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter;
import reactor.core.publisher.Flux;

public interface IStreamingOutputChatPipelineService extends IChatPipelineStepService {
	public static final String DEFAULT_PIPELINE_SERVICE = "DEFAULT_PIPELINE_SERVICE";

	@Override
	default StepType getStepType() {
		return StepType.OUTPUT;
	}

	public List<StepEnvironmentParameter> getRequiredParameters();

	/**
	 * The step's answer stream. The step fills the runtime data's chat response and
	 * emits it as the content of an envelope flagged as the last message; it never
	 * ends the request itself: the pipelines executor ends every streamed request the
	 * same way (see {@link ai.gebo.llms.chat.abstraction.layer.services.ChatRequestEnding}),
	 * saving the interaction as that last message passes, before the client gets it,
	 * or however the stream ends otherwise.
	 */
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws ChatPipelineException, GeboChatSessionLifecycleException, LLMConfigException, GeboChatException, IOException;
}
