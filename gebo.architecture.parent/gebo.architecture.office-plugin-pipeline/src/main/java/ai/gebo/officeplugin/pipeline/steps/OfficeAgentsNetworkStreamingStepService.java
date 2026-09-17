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
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
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
import reactor.core.publisher.SignalType;
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
		// doFinally, not doOnComplete: this office terminal step owns its own session
		// finalisation (every terminal streaming step does - the office router reaches this
		// step directly, exactly as the default router reaches the pure-search and
		// image-generation steps). doOnComplete fires ONLY on normal completion, so a
		// network that errored mid-cycle, or a user who closed the editor mid-stream, would
		// never run endRequest / chatRequestCompleted and the interaction would go
		// unpersisted - and a multi-cycle run, being long, is exactly where an error or
		// cancel is most likely to land. doFinally runs exactly once on whichever terminal
		// signal ends the stream, so the outcome is recorded rather than silently dropped.
		return outFlux.publishOn(runAs.wrap(Schedulers.boundedElastic())).doFinally(signalType -> {
			// doFinally reports which terminal signal ended the stream, so the three cases
			// are handled distinctly rather than lumped together:
			//   ON_COMPLETE - the network finished normally;
			//   ON_ERROR    - it failed mid-run (the failure itself is surfaced elsewhere);
			//   CANCEL      - the user abandoned the stream, e.g. closed the editor.
			// The gap this replaces was a plain doOnComplete, which fired on ON_COMPLETE
			// alone and left an errored or cancelled request unpersisted.
			final boolean cancelled = signalType == SignalType.CANCEL;
			final GeboChatResponse chatResponse = runtimeData.getChatResponse();
			final boolean hasContent = chatResponse != null && chatResponse.getQueryResponse() != null
					&& !chatResponse.getQueryResponse().isBlank();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Finalising office assistant request on terminal signal:" + signalType + " cancelled:"
						+ cancelled + " hasContent:" + hasContent);
			}
			// A cancel that happened before any text was produced has no interaction worth
			// recording: persisting it would leave an empty turn in the session history.
			// Every other outcome - normal completion, an error, or a cancel that already
			// produced partial text the user saw - is persisted so the interaction is not
			// silently lost.
			if (cancelled && !hasContent) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Office assistant request cancelled before producing any content: nothing to persist");
				}
				return;
			}
			runAs.doAs(() -> {
				try {
					lifeCycleService.endRequest(request, chatResponse);
				} catch (Throwable e) {
					LOGGER.error("Error ending office assistant request", e);
				}
				// Session maintenance (the shrink check) is a completion concern; a cancelled
				// turn has not added a full round-trip worth compacting for, so it is skipped
				// while the partial interaction is still recorded above.
				if (!cancelled) {
					try {
						lifeCycleService.chatRequestCompleted(request, chatModel);
					} catch (Throwable e) {
						LOGGER.error("Error completing office assistant request", e);
					}
				}
			});
		});
	}
}
