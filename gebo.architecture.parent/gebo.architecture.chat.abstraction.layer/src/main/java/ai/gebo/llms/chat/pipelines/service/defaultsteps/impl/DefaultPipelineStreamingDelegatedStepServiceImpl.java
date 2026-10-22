package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service

public class DefaultPipelineStreamingDelegatedStepServiceImpl implements IStreamingOutputChatPipelineService {
	public static final String DEFAULT_CHAT_PIPELINE_STEP_SERVICE = "DEFAULT_CHAT_PIPELINE_SERVICE";
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPipelineStreamingDelegatedStepServiceImpl.class);
	private final IStreamingOutputChatPipelineService delegatedService;
	private final IGChatSessionLifeCycleService sessionLifecycleService;

	public DefaultPipelineStreamingDelegatedStepServiceImpl(IGChatSessionLifeCycleService sessionLifecycleService,
			@Autowired(required = false) @Qualifier(DEFAULT_PIPELINE_SERVICE) IStreamingOutputChatPipelineService delegatedService) {
		this.delegatedService = delegatedService;
		this.sessionLifecycleService = sessionLifecycleService;
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
		ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		final GeboChatRequest request = runtimeData.getRequestResources().getCurrentRequest();
		Flux<GeboChatMessageEnvelope> outFlux = delegatedService.execute(runtimeData, sinkUIEmitter, chatModel,
				serviceModel);

		// The interaction is saved before the last message leaves, as every other
		// streaming handler does: the client asks for the chat title as soon as it
		// receives that message, and a title written from a session saved without the
		// answer overwrote it. A stream ending without a last message is saved on
		// completion.
		final AtomicBoolean ended = new AtomicBoolean(false);
		return outFlux.publishOn(runAs.wrap(Schedulers.boundedElastic())).doOnNext(envelope -> {
			if (envelope != null && envelope.isLastMessage() && ended.compareAndSet(false, true)) {
				runAs.doAs(() -> endRequest(request, runtimeData));
			}
		}).doOnComplete(() -> {
			runAs.doAs(() -> {
				if (ended.compareAndSet(false, true)) {
					endRequest(request, runtimeData);
				}
				try {
					sessionLifecycleService.chatRequestCompleted(request, chatModel);
				} catch (Throwable e) {
					LOGGER.error("Error completing request", e);
				}

			});

		});

	}

	private void endRequest(GeboChatRequest request, ChatPipelineExecutionRuntimeData runtimeData) {
		try {
			sessionLifecycleService.endRequest(request, runtimeData.getChatResponse());
		} catch (Throwable e) {
			LOGGER.error("Error ending request", e);
		}
	}

	public boolean isDefaultPipelineStreamingDefined() {
		return this.delegatedService != null;
	}

}
