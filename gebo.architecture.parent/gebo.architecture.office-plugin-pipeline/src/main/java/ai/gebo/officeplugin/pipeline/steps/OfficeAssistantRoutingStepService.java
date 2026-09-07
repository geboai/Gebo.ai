/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.officeplugin.pipeline.steps;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AdditionalContent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.services.CommonChatPromptParamsUtil;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IChatPipelineStepRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IStepContribution;
import ai.gebo.llms.chat.pipelines.model.RoutingDecision;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IRoutingChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.officeplugin.pipeline.OfficeAssistantConstants;
import ai.gebo.officeplugin.pipeline.agents.OfficeFragments;
import ai.gebo.officeplugin.pipeline.config.OfficePluginPromptsLibraryConfig;
import lombok.AllArgsConstructor;

/**
 * Router of the office-assistant pipeline. It performs a document-editor-aware
 * query rewrite - a custom prompt that sees the fragments the user is editing and
 * knows it is assisting inside an office document editor - then shortcuts, with a
 * fixed route, to the office agents-network streaming step. There is no LLM route
 * selection and no heavy-document branch: the office assistant always answers
 * through its network of agents.
 */
@ConditionalOnProperty(prefix = "ai.gebo.officeplugin", name = "enabled", havingValue = "true")
@Component
@AllArgsConstructor
public class OfficeAssistantRoutingStepService extends BaseLLMSInvokingService
		implements IRoutingChatPipelineStepService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OfficeAssistantRoutingStepService.class);
	private static final String NEWLINE = "\r\n";
	private static final String REWRITTEN_QUERY_FIELD = "rewrittenQuery";
	private static final String DELIVERABLE_FIELD = "deliverable";
	private static final String DELIVERABLE_TYPES_LIST_PARAM = "deliverableTypesList";
	private static final String OFFICE_DOCUMENT_FRAGMENTS_PARAM = "officeDocumentFragments";

	private final IGPromptConfigDao promptsDao;
	private final IGChatSessionLifeCycleService chatSessionLifecycleService;

	@Override
	public StepExecutorType getExecutorType() {
		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {
		return OfficeAssistantConstants.OFFICE_ROUTING_STEP;
	}

	@Override
	public String getPipelineId() {
		return OfficeAssistantConstants.OFFICE_ASSISTANT_PIPELINE;
	}

	@Override
	public RoutingDecision execute(ChatPipelineExecutionRuntimeData runtimeData, ISinkUIEmitter emitter,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel) throws ChatPipelineException {
		try {
			doOfficeRewrite(runtimeData, serviceModel);
			this.chatSessionLifecycleService.updateRequest(runtimeData.getRequestResources().getCurrentRequest());
		} catch (Throwable th) {
			// A rewrite failure must never block the assistant: keep the original query
			// and a safe default intent, then still route to the network.
			LOGGER.error("Office assistant query rewrite failed, continuing with the original query", th);
			if (runtimeData.getRequestResources().getCurrentRequest().getUserIntent() == null) {
				runtimeData.getRequestResources().getCurrentRequest().setUserIntent(DeliverableIntent.QA);
			}
		}
		runtimeData.getChatResponse().setPipelineRouterDecisionCode(OfficeAssistantConstants.OFFICE_AGENTIC_ANSWER);
		return new RoutingDecision(List.of(OfficeAssistantConstants.OFFICE_NETWORK_STREAMING_STEP),
				routingRuntimeData(), OfficeAssistantConstants.OFFICE_AGENTIC_ANSWER, Map.of());
	}

	private void doOfficeRewrite(ChatPipelineExecutionRuntimeData runtimeData, IGConfigurableChatModel serviceModel)
			throws Exception {
		Map<String, Object> params = CommonChatPromptParamsUtil
				.preparePromptParameters(runtimeData.getMinimalChatContext());
		params.put(DELIVERABLE_TYPES_LIST_PARAM, createDeliverableTypesList());
		params.put(OFFICE_DOCUMENT_FRAGMENTS_PARAM, renderFragments(runtimeData));
		GPromptTemplateConfig rewritePrompt = promptsDao
				.findByPromptUse(OfficePluginPromptsLibraryConfig.OFFICE_QUERY_REWRITING_PROMPT);
		IChatRequestContext context = runtimeData.getRequestResources().createChatRequestContext();
		Map<String, List<String>> data = callLLMRepeatableFieldEntryOutput(serviceModel, rewritePrompt, context, params,
				List.of(DELIVERABLE_FIELD, REWRITTEN_QUERY_FIELD));
		List<String> rewrittenQuery = data.get(REWRITTEN_QUERY_FIELD);
		String rewritten = rewrittenQuery != null && !rewrittenQuery.isEmpty() ? rewrittenQuery.get(0) : null;
		runtimeData.getRequestResources().getCurrentRequest().setRewrittenQuery(rewritten);
		runtimeData.getRequestResources().getCurrentRequest().setUserIntent(parseIntent(data.get(DELIVERABLE_FIELD)));
	}

	private DeliverableIntent parseIntent(List<String> deliverable) {
		DeliverableIntent userIntent = DeliverableIntent.QA;
		if (deliverable != null && !deliverable.isEmpty() && deliverable.get(0) != null) {
			Set<String> tokens = new HashSet<>();
			for (String token : deliverable.get(0).toLowerCase().split("[^a-z0-9_]+")) {
				if (!token.isEmpty()) {
					tokens.add(token);
				}
			}
			for (DeliverableIntent di : DeliverableIntent.values()) {
				if (tokens.contains(di.name().toLowerCase())) {
					userIntent = di;
					break;
				}
			}
		}
		// Image generation is not a deliverable of the office document assistant.
		if (userIntent == DeliverableIntent.IMAGE_GENERATION) {
			userIntent = DeliverableIntent.QA;
		}
		return userIntent;
	}

	private String renderFragments(ChatPipelineExecutionRuntimeData runtimeData) {
		List<AdditionalContent> fragments = OfficeFragments.fromEnvironment(runtimeData.getSharedEnvironment());
		if (fragments.isEmpty()) {
			return "(no document fragment currently selected)";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("OFFICE_DOCUMENT_FRAGMENTS_BEGIN").append(NEWLINE);
		for (AdditionalContent c : fragments) {
			sb.append("- fragment name: ").append(c.getName()).append(" (contentType: ").append(c.getContentType())
					.append(")").append(NEWLINE);
			sb.append(c.getContent()).append(NEWLINE);
		}
		sb.append("OFFICE_DOCUMENT_FRAGMENTS_END").append(NEWLINE);
		return sb.toString();
	}

	private String createDeliverableTypesList() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("DELIVERABLE_TYPES_CATALOG").append(NEWLINE);
		for (DeliverableIntent intent : DeliverableIntent.values()) {
			if (intent == DeliverableIntent.IMAGE_GENERATION) {
				continue;
			}
			buffer.append("intent-type: ").append(intent.name()).append(NEWLINE);
			buffer.append("selection-criteria: ").append(intent.getExplanation()).append(NEWLINE);
		}
		buffer.append("END_DELIVERABLE_TYPES_CATALOG").append(NEWLINE);
		return buffer.toString();
	}

	private IChatPipelineStepRuntimeData routingRuntimeData() {
		return new IChatPipelineStepRuntimeData() {

			@Override
			public String getStepId() {
				return OfficeAssistantConstants.OFFICE_ROUTING_STEP;
			}

			@Override
			public List<IStepContribution> getContextEnrichingContribution() {
				return List.of();
			}

			@Override
			public Map<String, Object> getEnvironmentContributions() {
				return Map.of();
			}
		};
	}
}
