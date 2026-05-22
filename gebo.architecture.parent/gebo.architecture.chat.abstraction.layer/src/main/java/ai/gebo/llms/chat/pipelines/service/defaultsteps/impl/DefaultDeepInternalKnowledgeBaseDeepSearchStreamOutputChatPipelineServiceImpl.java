package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import ai.gebo.model.GUserMessage;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class DefaultDeepInternalKnowledgeBaseDeepSearchStreamOutputChatPipelineServiceImpl
		extends BaseLLMSInvokingService implements IStreamingOutputChatPipelineService {
	public static final String DEFAULT_DEEPRAG_STREAMING = "default-deeprag-streaming";
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DefaultDeepInternalKnowledgeBaseDeepSearchStreamOutputChatPipelineServiceImpl.class);

	
	private final IGDeepSearchService deepSearchService;

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return DEFAULT_DEEPRAG_STREAMING;
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws ChatPipelineException, GeboChatSessionLifecycleException, LLMConfigException {
		try {

			return deepSearchService.streamDeepSearch(runtimeData, sinkUIEmitter, chatModel, serviceModel,
					List.of(DefaultRoutingChatPipelineStepServiceImpl.INTERNAL_KNOWLEDGE_BASE_SYSTEM_ID), 50, 50);
		} catch (Throwable e) {
			LOGGER.error("Error calling internal knowledge base executor", e);
			GUserMessage errorMessage = GUserMessage.errorMessage("Cannot execute internal knowledge base deep search",
					e);
			return Flux.just(new GeboChatMessageEnvelope(errorMessage));
		}

	}

	@Override
	public List<StepEnvironmentParameter> getRequiredParameters() {

		return List.of();
	}

}
