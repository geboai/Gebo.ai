/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openchat.pipeline.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineConfiguration;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.DefaultInputChatPipelineStepServiceImpl;
import ai.gebo.openchat.pipeline.OpenChatConstants;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

/**
 * Registers the {@code open-chat} pipeline into the shared
 * {@link ChatPipelinesConfiguration}. The pipeline reuses the default input step
 * (no special input normalisation is needed) and supplies a KB-free router; it is
 * selected with {@code pipelineCode=open-chat}.
 */
@ConditionalOnProperty(prefix = "ai.gebo.openchat", name = "enabled", havingValue = "true", matchIfMissing = true)
@Component
@AllArgsConstructor
public class OpenChatPipelineConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(OpenChatPipelineConfig.class);
	private final ChatPipelinesConfiguration chatPipelinesConfiguration;

	@PostConstruct
	public void registerOpenChatPipeline() {
		boolean alreadyRegistered = chatPipelinesConfiguration.getPipelines().stream()
				.anyMatch(p -> OpenChatConstants.OPEN_CHAT_PIPELINE.equals(p.getCode()));
		if (alreadyRegistered) {
			return;
		}
		ChatPipelineConfiguration pipeline = new ChatPipelineConfiguration();
		pipeline.setCode(OpenChatConstants.OPEN_CHAT_PIPELINE);
		pipeline.setDescription("Open chat pipeline (no internal knowledge base RAG; external search + chat with files)");
		pipeline.setDefaultPipeline(false);
		pipeline.setStepInputId(DefaultInputChatPipelineStepServiceImpl.DEFAULT_INPUT_STEP);
		pipeline.setStepRouterId(OpenChatConstants.OPEN_CHAT_ROUTING_STEP);
		chatPipelinesConfiguration.getPipelines().add(pipeline);
		LOGGER.info("Registered chat pipeline '{}' (input step '{}', router step '{}')",
				OpenChatConstants.OPEN_CHAT_PIPELINE, DefaultInputChatPipelineStepServiceImpl.DEFAULT_INPUT_STEP,
				OpenChatConstants.OPEN_CHAT_ROUTING_STEP);
	}
}
