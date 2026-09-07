/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.officeplugin.pipeline.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineConfiguration;
import ai.gebo.officeplugin.pipeline.OfficeAssistantConstants;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

/**
 * Registers the {@code office-assistant} pipeline into the shared
 * {@link ChatPipelinesConfiguration}. The pipeline is a normal chat pipeline whose
 * input step normalises the document fragments and whose router shortcuts to the
 * office agents-network streaming step; it is selected with
 * {@code pipelineCode=office-assistant}.
 */
@ConditionalOnProperty(prefix = "ai.gebo.officeplugin", name = "enabled", havingValue = "true")
@Component
@AllArgsConstructor
public class OfficeAssistantPipelineConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(OfficeAssistantPipelineConfig.class);
	private final ChatPipelinesConfiguration chatPipelinesConfiguration;

	@PostConstruct
	public void registerOfficeAssistantPipeline() {
		boolean alreadyRegistered = chatPipelinesConfiguration.getPipelines().stream()
				.anyMatch(p -> OfficeAssistantConstants.OFFICE_ASSISTANT_PIPELINE.equals(p.getCode()));
		if (alreadyRegistered) {
			return;
		}
		ChatPipelineConfiguration pipeline = new ChatPipelineConfiguration();
		pipeline.setCode(OfficeAssistantConstants.OFFICE_ASSISTANT_PIPELINE);
		pipeline.setDescription("Office document assistant pipeline");
		pipeline.setDefaultPipeline(false);
		pipeline.setStepInputId(OfficeAssistantConstants.OFFICE_INPUT_STEP);
		pipeline.setStepRouterId(OfficeAssistantConstants.OFFICE_ROUTING_STEP);
		chatPipelinesConfiguration.getPipelines().add(pipeline);
		LOGGER.info("Registered chat pipeline '{}' (input step '{}', router step '{}')",
				OfficeAssistantConstants.OFFICE_ASSISTANT_PIPELINE, OfficeAssistantConstants.OFFICE_INPUT_STEP,
				OfficeAssistantConstants.OFFICE_ROUTING_STEP);
	}
}
