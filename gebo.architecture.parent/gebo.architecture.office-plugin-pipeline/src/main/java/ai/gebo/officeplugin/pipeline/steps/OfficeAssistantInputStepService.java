/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.officeplugin.pipeline.steps;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AdditionalContent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IChatPipelineStepRuntimeData;
import ai.gebo.llms.chat.pipelines.model.IStepContribution;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IInputChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.officeplugin.pipeline.OfficeAssistantConstants;
import ai.gebo.officeplugin.pipeline.agents.OfficeFragments;

/**
 * Input step of the office-assistant pipeline. Like the default input step it does
 * no LLM work; it normalises the {@link AdditionalContent} fragments the office
 * plugin attached to the request ({@code additionalContents} - the document the
 * user is editing) and publishes them into the pipeline shared environment under
 * {@link OfficeAssistantConstants#OFFICE_DOCUMENT_FRAGMENTS}, so the router, the
 * network streaming step and the network input node all see the same normalised
 * list.
 */
@ConditionalOnProperty(prefix = "ai.gebo.officeplugin", name = "enabled", havingValue = "true")
@Component
public class OfficeAssistantInputStepService implements IInputChatPipelineStepService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OfficeAssistantInputStepService.class);

	@Override
	public IChatPipelineStepRuntimeData execute(ChatPipelineExecutionRuntimeData input, ISinkUIEmitter emitter,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel) throws ChatPipelineException {
		GeboChatRequest request = input.getRequestResources().getCurrentRequest();
		final List<AdditionalContent> fragments = OfficeFragments
				.normalize(request != null ? request.getAdditionalContents() : null);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Office input step normalised {} document fragment(s)", fragments.size());
		}
		return new IChatPipelineStepRuntimeData() {

			@Override
			public String getStepId() {
				return OfficeAssistantConstants.OFFICE_INPUT_STEP;
			}

			@Override
			public List<IStepContribution> getContextEnrichingContribution() {
				return List.of();
			}

			@Override
			public Map<String, Object> getEnvironmentContributions() {
				return Map.of(OfficeAssistantConstants.OFFICE_DOCUMENT_FRAGMENTS, fragments);
			}
		};
	}

	@Override
	public StepExecutorType getExecutorType() {
		return StepExecutorType.PROGRAMMATIC;
	}

	@Override
	public String getStepId() {
		return OfficeAssistantConstants.OFFICE_INPUT_STEP;
	}

	@Override
	public String getPipelineId() {
		return OfficeAssistantConstants.OFFICE_ASSISTANT_PIPELINE;
	}
}
