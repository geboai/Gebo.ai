/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.responses.OpenAiResponsesChatOptions;
import org.springframework.ai.tool.function.FunctionToolCallback;

/**
 * Pins the Responses options of a model the OpenAI handler configures for Chat
 * Completions: what Gebo set is what the Responses request carries.
 */
class OpenAiResponsesOptionsTest {

	@Test
	void theChatCompletionsConfigurationIsTheResponsesOne() {
		OpenAiChatOptions chat = OpenAiChatOptions.builder().model("gpt-6.1-sol").apiKey("sk-test")
				.temperature(0.0).topP(0.9).maxCompletionTokens(2000).reasoningEffort("low").user("gebo-installation")
				.timeout(Duration.ofSeconds(90)).parallelToolCalls(true)
				.toolCallbacks(List.of(FunctionToolCallback.builder("lookup", (String q) -> "found " + q)
						.description("Looks a place up").inputType(String.class).build()))
				.build();

		OpenAiResponsesChatOptions responses = OpenAiResponsesOptions.of(chat);

		assertEquals("gpt-6.1-sol", responses.getModel());
		assertEquals("sk-test", responses.getApiKey());
		assertEquals(0.0, responses.getTemperature());
		assertEquals(0.9, responses.getTopP());
		assertEquals(2000, responses.getMaxOutputTokens(), "the completion cap bounds the output, reasoning included");
		assertEquals("low", responses.getReasoningEffort());
		assertEquals(OpenAiResponsesOptions.SUMMARY, responses.getReasoningSummary(), "the thinking shown in the chat");
		assertEquals("gebo-installation", responses.getSafetyIdentifier(), "the key's user, as OpenAI replaced it");
		assertEquals(Duration.ofSeconds(90), responses.getTimeout());
		assertEquals(Boolean.TRUE, responses.getParallelToolCalls());
		assertEquals(1, responses.getToolCallbacks().size());
	}

	@Test
	void withoutToolsNoParallelToolCallsAndNothingUnset() {
		OpenAiChatOptions chat = OpenAiChatOptions.builder().model("gpt-6-luna").apiKey("sk-test").build();

		OpenAiResponsesChatOptions responses = OpenAiResponsesOptions.of(chat);

		assertNull(responses.getParallelToolCalls(), "OpenAI refuses parallel_tool_calls without tools");
		assertTrue(responses.getToolCallbacks() == null || responses.getToolCallbacks().isEmpty());
		assertNull(responses.getTemperature());
		assertNull(responses.getReasoningEffort());
		assertNull(responses.getSafetyIdentifier());
		assertNull(responses.getMaxOutputTokens());
	}
}
