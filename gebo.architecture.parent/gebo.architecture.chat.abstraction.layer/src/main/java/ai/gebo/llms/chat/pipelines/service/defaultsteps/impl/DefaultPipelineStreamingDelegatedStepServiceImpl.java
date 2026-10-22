package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import reactor.core.publisher.Flux;

/**
 * The default chat pipeline step: the streaming service wired as
 * {@link #DEFAULT_PIPELINE_SERVICE} (the agents network, when deployed) answers. The
 * request is ended by the pipelines executor, as for every streaming step.
 */
@Service
public class DefaultPipelineStreamingDelegatedStepServiceImpl implements IStreamingOutputChatPipelineService {
	public static final String DEFAULT_CHAT_PIPELINE_STEP_SERVICE = "DEFAULT_CHAT_PIPELINE_SERVICE";
	private final IStreamingOutputChatPipelineService delegatedService;

	public DefaultPipelineStreamingDelegatedStepServiceImpl(
			@Autowired(required = false) @Qualifier(DEFAULT_PIPELINE_SERVICE) IStreamingOutputChatPipelineService delegatedService) {
		this.delegatedService = delegatedService;
	}

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return DEFAULT_CHAT_PIPELINE_STEP_SERVICE;
	}

	@Override
	public List<StepEnvironmentParameter> getRequiredParameters() {

		return List.of();
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws ChatPipelineException, GeboChatSessionLifecycleException, LLMConfigException, GeboChatException,
			IOException {
		return delegatedService.execute(runtimeData, sinkUIEmitter, chatModel, serviceModel);
	}

	public boolean isDefaultPipelineStreamingDefined() {
		return this.delegatedService != null;
	}

}
