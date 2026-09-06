/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.officeplugin.pipeline.steps;

import java.io.IOException;
import java.util.Map;

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
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.officeplugin.pipeline.OfficeAssistantConstants;
import ai.gebo.officeplugin.pipeline.agents.OfficeFragments;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Office-assistant streaming output step. It reuses the whole reactive
 * agents-network streaming machinery of
 * {@link ReactiveChatAgentsNetworkStreamingOutputChatPipelineService}, changing
 * only two things:
 * <ul>
 * <li>it enriches the network session environment with the document fragments the
 * user is editing (so the coordinator and writer prompts are aware of them);</li>
 * <li>it closes the chat session on completion - the office router shortcuts
 * straight here without the default network's delegated wrapper, so this step must
 * itself run {@code endRequest} / {@code chatRequestCompleted}.</li>
 * </ul>
 */
public class OfficeAgentsNetworkStreamingStepService
		extends ReactiveChatAgentsNetworkStreamingOutputChatPipelineService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OfficeAgentsNetworkStreamingStepService.class);

	public OfficeAgentsNetworkStreamingStepService(
			IGAgentsNetworkServiceFactory<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, IGReactiveChatAgentsNetworkService> factory,
			IDynamicAgentsNetworkDataSource agentsNetworkDataSource, IGChatSessionLifeCycleService lifeCycleService) {
		super(factory, agentsNetworkDataSource, lifeCycleService, OfficeAssistantConstants.OFFICE_NETWORK_STREAMING_STEP);
	}

	@Override
	protected Map<String, Object> buildNetworkEnvironment(ChatPipelineExecutionRuntimeData runtimeData)
			throws LLMConfigException, GeboChatSessionLifecycleException {
		Map<String, Object> environment = super.buildNetworkEnvironment(runtimeData);
		environment.put(OfficeAssistantConstants.OFFICE_DOCUMENT_FRAGMENTS,
				OfficeFragments.fromEnvironment(runtimeData.getSharedEnvironment()));
		return environment;
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws ChatPipelineException, GeboChatSessionLifecycleException, LLMConfigException, GeboChatException,
			IOException {
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		final GeboChatRequest request = runtimeData.getRequestResources().getCurrentRequest();
		Flux<GeboChatMessageEnvelope> outFlux = super.execute(runtimeData, sinkUIEmitter, chatModel, serviceModel);
		return outFlux.publishOn(runAs.wrap(Schedulers.boundedElastic())).doOnComplete(() -> runAs.doAs(() -> {
			try {
				lifeCycleService.endRequest(request, runtimeData.getChatResponse());
			} catch (Throwable e) {
				LOGGER.error("Error ending office assistant request", e);
			}
			try {
				lifeCycleService.chatRequestCompleted(request, chatModel);
			} catch (Throwable e) {
				LOGGER.error("Error completing office assistant request", e);
			}
		}));
	}
}
