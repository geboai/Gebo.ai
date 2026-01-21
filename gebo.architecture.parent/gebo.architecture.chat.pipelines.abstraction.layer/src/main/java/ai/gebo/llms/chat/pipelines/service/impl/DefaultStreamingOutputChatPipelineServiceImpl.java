package ai.gebo.llms.chat.pipelines.service.impl;

import org.springframework.stereotype.Component;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import reactor.core.publisher.Flux;
@Component
public class DefaultStreamingOutputChatPipelineServiceImpl implements IStreamingOutputChatPipelineService {

	public static final String DEFAULT_STREAMING_OUTPUT = "default-streaming-output";

	public DefaultStreamingOutputChatPipelineServiceImpl() {
		
	}

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return DEFAULT_STREAMING_OUTPUT;
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel) throws ChatPipelineException {
		// TODO Auto-generated method stub
		return null;
	}

}
