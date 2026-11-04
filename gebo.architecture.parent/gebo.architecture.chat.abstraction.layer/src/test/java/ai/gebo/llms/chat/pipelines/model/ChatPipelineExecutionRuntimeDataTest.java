/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;

/**
 * The runtime data of a request carries what the controlling software adds to
 * its model calls, when it was given one; made without, it is what it always was.
 */
class ChatPipelineExecutionRuntimeDataTest {

	@Test
	void aRequestMadeWithAToolCallingCarriesIt() {
		GeboToolCalling toolCalling = GeboToolCalling.builder().caller("chat:request-1").build();
		LLMChatRequestResources resources = new LLMChatRequestResources();
		GeboChatResponse response = new GeboChatResponse();

		ChatPipelineExecutionRuntimeData runtimeData = new ChatPipelineExecutionRuntimeData(null, 8192, resources,
				response, null, true, toolCalling);

		assertSame(toolCalling, runtimeData.getToolCalling());
		assertSame(resources, runtimeData.getRequestResources());
		assertSame(response, runtimeData.getChatResponse());
		assertEquals(8192, runtimeData.getContextWindowSize());
		assertEquals(8192, runtimeData.getRemainingTokens());
		assertTrue(runtimeData.isStreamingOutput());
	}

	@Test
	void aRequestMadeWithoutOneIsWhatItAlwaysWas() {
		ChatPipelineExecutionRuntimeData runtimeData = new ChatPipelineExecutionRuntimeData(null, 8192,
				new LLMChatRequestResources(), null, null, false);

		assertNull(runtimeData.getToolCalling());
		assertEquals(8192, runtimeData.getRemainingTokens());
		assertTrue(runtimeData.getExecutedSteps().isEmpty());
		assertTrue(runtimeData.getRoutingDecisions().isEmpty());
		assertTrue(runtimeData.getSharedEnvironment().isEmpty());
	}
}
