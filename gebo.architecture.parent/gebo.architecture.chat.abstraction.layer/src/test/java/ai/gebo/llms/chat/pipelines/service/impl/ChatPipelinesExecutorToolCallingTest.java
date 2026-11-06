/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCallingManagerFactory;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatFullSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.service.IPipelineStepModelsService;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineStepServiceRepositoryPattern;
import ai.gebo.llms.chat.pipelines.service.IGRequestUnderstandingService;

/**
 * What a chat request adds to every model call made for it: the chat as the
 * caller, the platform's loop limits, no listener of its own.
 */
class ChatPipelinesExecutorToolCallingTest {

	private static ChatPipelinesExecutorImpl executor() {
		return new ChatPipelinesExecutorImpl(mock(IChatPipelineStepServiceRepositoryPattern.class),
				mock(ChatPipelinesConfiguration.class), mock(IGChatFullSessionStateService.class),
				mock(IGChatSessionLifeCycleService.class), mock(ChatProfilesRepository.class),
				mock(IGeboThreadManager.class), mock(IGRequestUnderstandingService.class),
				new GeboToolCallingManagerFactory(), mock(IPipelineStepModelsService.class));
	}

	@Test
	void theRequestNamesTheChatAsTheCallerAndKeepsTheRequestsRecorder() {
		GeboChatRequest request = new GeboChatRequest();
		request.setId("request-1");

		GeboToolCalling toolCalling = executor().toolCallingOf(request);

		assertEquals("chat:request-1", toolCalling.getCaller());
		assertNull(toolCalling.getListener(), "the request's recorder is the one recording, chained from the agents'");
		assertTrue(toolCalling.getMandatoryTools().isEmpty());
		assertEquals(GeboToolCalling.Limits.SPRING_DEFAULTS, toolCalling.getLimits(),
				"nothing configured, the loop runs as it always has");
	}
}
