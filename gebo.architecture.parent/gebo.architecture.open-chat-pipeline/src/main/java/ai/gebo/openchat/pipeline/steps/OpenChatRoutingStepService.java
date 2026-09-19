/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openchat.pipeline.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IChatPipelineStepRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IStepContribution;
import ai.gebo.llms.chat.pipelines.model.RoutingDecision;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineStepServiceRepositoryPattern;
import ai.gebo.llms.chat.pipelines.service.IDataSourcesCatalogsService;
import ai.gebo.llms.chat.pipelines.service.IRoutingChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.DefaultDeepSearchStreamingOutputChatPipelineStepServiceImpl;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.DefaultRoutingChatPipelineStepServiceImpl;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.DefaultStreamingOutputChatPipelineServiceImpl;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.model.RespondingWith;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceMetaInfos;
import ai.gebo.openchat.pipeline.OpenChatConstants;
import lombok.AllArgsConstructor;

/**
 * Router of the open-chat pipeline. Unlike the default router it never selects the
 * internal knowledge base: there is no RAG route and no {@code IKB_SYSTEM} deep
 * search. It makes a three-way, purely programmatic decision (no LLM call):
 * <ol>
 * <li><b>forced heavy documents</b> - when the attached/uploaded documents exceed
 * the size threshold, answer over the files ({@code CHAT_WITH_FILES}), exactly like
 * the default router's resource guard;</li>
 * <li><b>explicit menu route</b> - a non-blank {@code chatPipelineProcessId} is the
 * name of a {@link RespondingWith}, whitelisted to {@code PURE_LLM_RESPONSE},
 * {@code DEEP_SEARCH_RESPONSE} and {@code CHAT_WITH_FILES}; {@code RAG_LLM_RESPONSE}
 * and anything else degrade to {@code PURE_LLM_RESPONSE};</li>
 * <li><b>agentic chat</b> - a blank {@code chatPipelineProcessId} routes to the
 * open-chat KB-free network of agents (or {@code PURE_LLM_RESPONSE} when the agents
 * network is disabled).</li>
 * </ol>
 * For any deep-search route it forces {@code deepSearchedSystems} to the enabled
 * <em>external</em> (non-{@code IKB_SYSTEM}) sources, never leaving it empty (an
 * empty list would make the shared deep-search handler fall back to the internal
 * knowledge base).
 */
@ConditionalOnProperty(prefix = "ai.gebo.openchat", name = "enabled", havingValue = "true", matchIfMissing = true)
@Component
@AllArgsConstructor
public class OpenChatRoutingStepService implements IRoutingChatPipelineStepService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OpenChatRoutingStepService.class);

	private final ChatPipelinesConfiguration chatPipelinesConfig;
	private final IDataSourcesCatalogsService deepSearchDataSourcesCatalogsService;
	// Resolved lazily to avoid the constructor cycle: the step repository aggregates
	// every IChatPipelineStepService (this router included), so constructor-injecting
	// it here would be a self-referential cycle. The default router uses the same
	// runtime binder for the same reason.
	private final IGRuntimeBinder runtimeBinder;

	@Override
	public StepExecutorType getExecutorType() {
		return StepExecutorType.PROGRAMMATIC;
	}

	@Override
	public String getStepId() {
		return OpenChatConstants.OPEN_CHAT_ROUTING_STEP;
	}

	@Override
	public String getPipelineId() {
		return OpenChatConstants.OPEN_CHAT_PIPELINE;
	}

	@Override
	public RoutingDecision execute(ChatPipelineExecutionRuntimeData runtimeData, ISinkUIEmitter emitter,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel) throws ChatPipelineException {
		try {
			// The open-chat router does no LLM rewrite, so no deliverable intent is inferred.
			// Downstream (the network writer) expects one; default it to QA (a direct answer),
			// which keeps the assistant free-form rather than driving any report structure.
			if (runtimeData.getRequestResources().getCurrentRequest().getUserIntent() == null) {
				runtimeData.getRequestResources().getCurrentRequest().setUserIntent(DeliverableIntent.QA);
			}
			// 1) Resource guard first (mirrors the default router): oversized attachments
			// are answered over the files, never routed elsewhere.
			int forcedDocumentsTotal = runtimeData.getRequestResources().getChatWithDocuments().getTokensSize()
					+ runtimeData.getRequestResources().getUploadedDocuments().getTokensSize();
			if (forcedDocumentsTotal >= heavyDocumentsThreshold(chatModel)) {
				return fixedRoute(OpenChatConstants.DEFAULT_CHAT_WITH_DOCS_STREAMING, RespondingWith.CHAT_WITH_FILES.name(),
						Map.of());
			}

			String processId = runtimeData.getRequestResources().getCurrentRequest().getChatPipelineProcessId();
			if (processId != null && processId.trim().length() > 0) {
				// 2) Explicit menu route.
				return doHandleUserRequestedRouting(runtimeData, processId.trim());
			}
			// 3) Agentic chat: the KB-free network of agents, or pure LLM when it is off.
			return doDecideAgentic(runtimeData);
		} catch (Throwable th) {
			LOGGER.error("Exception in open-chat pipeline routing, falling back to PURE_LLM_RESPONSE", th);
			return fixedRoute(DefaultStreamingOutputChatPipelineServiceImpl.DEFAULT_STREAMING_OUTPUT,
					RespondingWith.PURE_LLM_RESPONSE.name(), Map.of());
		}
	}

	private RoutingDecision doDecideAgentic(ChatPipelineExecutionRuntimeData runtimeData) {
		if (isAgentsNetworkAvailable()) {
			return fixedRoute(OpenChatConstants.OPEN_CHAT_NETWORK_STREAMING_STEP, OpenChatConstants.OPEN_CHAT_AGENTIC_ANSWER,
					Map.of());
		}
		// Agents network disabled: the safest KB-free fallback is a plain LLM answer.
		return fixedRoute(DefaultStreamingOutputChatPipelineServiceImpl.DEFAULT_STREAMING_OUTPUT,
				RespondingWith.PURE_LLM_RESPONSE.name(), Map.of());
	}

	/**
	 * @return true when the open-chat agents-network streaming step is actually
	 *         registered. That step is a bean of {@code OpenChatAgentsInitialization},
	 *         gated by both {@code ai.gebo.openchat.enabled} and
	 *         {@code ai.gebo.agents.standard.enabled}; when the standard agents are off
	 *         the step is absent, so the router must not route to it (the executor's
	 *         {@code getNextStep} would fail outside this router's try/catch). We check
	 *         the step repository so the agentic branch degrades cleanly to a plain LLM
	 *         answer instead.
	 */
	private boolean isAgentsNetworkAvailable() {
		IChatPipelineStepServiceRepositoryPattern repo = runtimeBinder
				.getImplementationOf(IChatPipelineStepServiceRepositoryPattern.class);
		return repo != null && repo.findByCode(OpenChatConstants.OPEN_CHAT_NETWORK_STREAMING_STEP) != null;
	}

	private RoutingDecision doHandleUserRequestedRouting(ChatPipelineExecutionRuntimeData runtimeData, String processId) {
		RespondingWith requested;
		try {
			requested = RespondingWith.valueOf(processId);
		} catch (IllegalArgumentException ex) {
			requested = RespondingWith.PURE_LLM_RESPONSE;
		}
		switch (requested) {
		case DEEP_SEARCH_RESPONSE: {
			List<String> externalSources = resolveExternalDeepSearchSources(runtimeData);
			if (externalSources.isEmpty()) {
				// No external source available: never fall through to IKB, answer with the LLM.
				return fixedRoute(DefaultStreamingOutputChatPipelineServiceImpl.DEFAULT_STREAMING_OUTPUT,
						RespondingWith.PURE_LLM_RESPONSE.name(), Map.of());
			}
			Map<String, Object> env = Map.of(OpenChatConstants.DEEP_SEARCHED_SYSTEMS, externalSources);
			return routeWithEnvironment(DefaultDeepSearchStreamingOutputChatPipelineStepServiceImpl.DEFAULT_DEEPSEARCH_STREAMING,
					RespondingWith.DEEP_SEARCH_RESPONSE.name(), env);
		}
		case CHAT_WITH_FILES: {
			return fixedRoute(OpenChatConstants.DEFAULT_CHAT_WITH_DOCS_STREAMING, RespondingWith.CHAT_WITH_FILES.name(),
					Map.of());
		}
		case PURE_LLM_RESPONSE:
		case RAG_LLM_RESPONSE: // no internal KB in the open-chat pipeline: degrade to a plain answer
		default:
			return fixedRoute(DefaultStreamingOutputChatPipelineServiceImpl.DEFAULT_STREAMING_OUTPUT,
					RespondingWith.PURE_LLM_RESPONSE.name(), Map.of());
		}
	}

	/**
	 * The enabled external deep-search sources, i.e. every active data source except
	 * the internal knowledge base. Starts from whatever the request already carries in
	 * {@code deepSearchedSystems} (the sources the user picked), strips
	 * {@code IKB_SYSTEM}, and - if that leaves nothing - falls back to all enabled
	 * external sources so the deep-search handler never defaults to the internal KB.
	 */
	private List<String> resolveExternalDeepSearchSources(ChatPipelineExecutionRuntimeData runtimeData) {
		List<String> allExternal = new ArrayList<>();
		for (DeepSearchDataSourceMetaInfos ds : deepSearchDataSourcesCatalogsService
				.getActiveDeepSearchDataSourceMetaInfos()) {
			if (ds.getHandlerId() != null
					&& !DefaultRoutingChatPipelineStepServiceImpl.INTERNAL_KNOWLEDGE_BASE_SYSTEM_ID
							.equals(ds.getHandlerId())) {
				allExternal.add(ds.getHandlerId());
			}
		}
		Object requested = runtimeData.getSharedEnvironment().get(OpenChatConstants.DEEP_SEARCHED_SYSTEMS);
		if (requested instanceof List<?> list && !list.isEmpty()) {
			List<String> cleaned = new ArrayList<>();
			for (Object o : list) {
				String id = String.valueOf(o);
				if (!DefaultRoutingChatPipelineStepServiceImpl.INTERNAL_KNOWLEDGE_BASE_SYSTEM_ID.equals(id)
						&& allExternal.contains(id)) {
					cleaned.add(id);
				}
			}
			if (!cleaned.isEmpty()) {
				return cleaned;
			}
		}
		return allExternal;
	}

	private int heavyDocumentsThreshold(IGConfigurableChatModel chatModel) {
		double contextWindow = chatModel.getContextLength();
		double limit = chatPipelinesConfig != null
				&& chatPipelinesConfig.getFixedDocumentsRequestRoutesDeepSearchTokenThreashold() != null
						? chatPipelinesConfig.getFixedDocumentsRequestRoutesDeepSearchTokenThreashold().doubleValue()
						: 0.0;
		if (chatPipelinesConfig != null
				&& chatPipelinesConfig.getFixedDocumentsRequestRoutesDeepSearchTreasholdContextWindowCoeff() != null) {
			double threshold = chatPipelinesConfig.getFixedDocumentsRequestRoutesDeepSearchTreasholdContextWindowCoeff()
					* contextWindow;
			limit = limit > 0.0 ? Math.min(threshold, limit) : threshold;
		}
		return (int) limit;
	}

	private RoutingDecision fixedRoute(String outputStepId, String decisionCode, Map<String, Object> environment) {
		return routeWithEnvironment(outputStepId, decisionCode, environment);
	}

	private RoutingDecision routeWithEnvironment(String outputStepId, String decisionCode,
			Map<String, Object> environment) {
		final Map<String, Object> env = environment != null ? environment : Map.of();
		IChatPipelineStepRuntimeData runtimeEntry = new IChatPipelineStepRuntimeData() {

			@Override
			public String getStepId() {
				return OpenChatConstants.OPEN_CHAT_ROUTING_STEP;
			}

			@Override
			public List<IStepContribution> getContextEnrichingContribution() {
				return List.of();
			}

			@Override
			public Map<String, Object> getEnvironmentContributions() {
				return env;
			}
		};
		return new RoutingDecision(List.of(outputStepId), runtimeEntry, decisionCode, new java.util.HashMap<>(env));
	}
}
