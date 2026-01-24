package ai.gebo.llms.chat.pipelines.service.impl;

import org.springframework.stereotype.Component;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@AllArgsConstructor
public class DefaultDeepSearchStreamingOutputChatPipelineStepServiceImpl
		implements IStreamingOutputChatPipelineService {
	IGDeepSearchService deepSearchService;
	public static final String DEFAULT_DEEPSEARCH_STREAMING = "default-deepsearch-streaming";

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return DEFAULT_DEEPSEARCH_STREAMING;
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel) throws ChatPipelineException {

		return deepSearchService
				.streamingExecute(null, runtimeData.getRequestResources(), chatModel,
						serviceModel)
				.map(x -> new GeboChatMessageEnvelope(x));

	}

}
