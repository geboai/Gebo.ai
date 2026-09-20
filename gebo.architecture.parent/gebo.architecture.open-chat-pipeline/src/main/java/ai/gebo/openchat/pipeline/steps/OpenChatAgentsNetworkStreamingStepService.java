/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openchat.pipeline.steps;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.agents.services.IDynamicAgentsNetworkDataSource;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkService;
import ai.gebo.llms.agent.chat.service.impl.ReactiveChatAgentsNetworkStreamingOutputChatPipelineService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.openchat.pipeline.OpenChatConstants;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;
import reactor.core.scheduler.Schedulers;

/**
 * Open-chat streaming output step. It reuses the reactive agents-network streaming
 * machinery of {@link ReactiveChatAgentsNetworkStreamingOutputChatPipelineService}
 * (with the open-chat KB-free network) and, like the office terminal step, owns its
 * own session finalisation: the open-chat router shortcuts straight here without the
 * default network's delegated wrapper, so this step must itself run
 * {@code endRequest} / {@code chatRequestCompleted}.
 */
public class OpenChatAgentsNetworkStreamingStepService
		extends ReactiveChatAgentsNetworkStreamingOutputChatPipelineService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OpenChatAgentsNetworkStreamingStepService.class);

	public OpenChatAgentsNetworkStreamingStepService(
			IGAgentsNetworkServiceFactory<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, IGReactiveChatAgentsNetworkService> factory,
			IDynamicAgentsNetworkDataSource agentsNetworkDataSource, IGChatSessionLifeCycleService lifeCycleService) {
		super(factory, agentsNetworkDataSource, lifeCycleService, OpenChatConstants.OPEN_CHAT_NETWORK_STREAMING_STEP);
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws ChatPipelineException, GeboChatSessionLifecycleException, LLMConfigException, GeboChatException,
			IOException {
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		final GeboChatRequest request = runtimeData.getRequestResources().getCurrentRequest();
		Flux<GeboChatMessageEnvelope> outFlux = super.execute(runtimeData, sinkUIEmitter, chatModel, serviceModel);
		// doFinally, not doOnComplete: this terminal step owns its own session finalisation.
		// doFinally runs exactly once on whichever terminal signal ends the stream (complete,
		// error or cancel), so an errored or cancelled multi-cycle run is still recorded.
		return outFlux.publishOn(runAs.wrap(Schedulers.boundedElastic())).doFinally(signalType -> {
			final boolean cancelled = signalType == SignalType.CANCEL;
			final GeboChatResponse chatResponse = runtimeData.getChatResponse();
			final boolean hasContent = chatResponse != null && chatResponse.getQueryResponse() != null
					&& !chatResponse.getQueryResponse().isBlank();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Finalising open-chat request on terminal signal:" + signalType + " cancelled:" + cancelled
						+ " hasContent:" + hasContent);
			}
			// A cancel before any text was produced has no interaction worth recording.
			if (cancelled && !hasContent) {
				return;
			}
			runAs.doAs(() -> {
				try {
					lifeCycleService.endRequest(request, chatResponse);
				} catch (Throwable e) {
					LOGGER.error("Error ending open-chat request", e);
				}
				if (!cancelled) {
					try {
						lifeCycleService.chatRequestCompleted(request, chatModel);
					} catch (Throwable e) {
						LOGGER.error("Error completing open-chat request", e);
					}
				}
			});
		});
	}
}
