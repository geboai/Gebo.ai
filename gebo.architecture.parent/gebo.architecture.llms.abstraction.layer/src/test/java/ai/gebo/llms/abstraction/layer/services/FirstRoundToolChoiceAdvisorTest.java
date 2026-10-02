/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

/**
 * Pins the tool choice of a call whose first model round must call a tool: only that
 * round is asked, the rounds reading the tools' results are left free to answer.
 */
class FirstRoundToolChoiceAdvisorTest {

	private static final ToolCallingChatOptions REQUIRING = ToolCallingChatOptions.builder().build();
	private static final UnaryOperator<ToolCallingChatOptions> VENDOR = options -> REQUIRING;

	private static ToolCallingChatOptions withTools() {
		ToolCallback tool = mock(ToolCallback.class);
		when(tool.getToolDefinition())
				.thenReturn(ToolDefinition.builder().name("searchKnowledgeBase").description("search").inputSchema("{}").build());
		return ToolCallingChatOptions.builder().toolCallbacks(List.of(tool)).build();
	}

	private static ChatClientRequest request(List<Message> messages, ToolCallingChatOptions options) {
		return ChatClientRequest.builder().prompt(new Prompt(messages, options)).context(Map.of()).build();
	}

	@Test
	void theFirstRoundMustCallATool() {
		ChatClientRequest request = request(List.of(new UserMessage("a report on the contracts")), withTools());

		assertSame(REQUIRING, new FirstRoundToolChoiceAdvisor(VENDOR, "m").requireToolOnFirstRound(request).prompt()
				.getOptions());
	}

	@Test
	void theRoundsReadingTheToolsResultsAreFree() {
		ToolCallingChatOptions options = withTools();
		ToolResponseMessage results = ToolResponseMessage.builder()
				.responses(List.of(new ToolResponseMessage.ToolResponse("1", "searchKnowledgeBase", "found"))).build();
		ChatClientRequest request = request(
				List.of(new UserMessage("a report on the contracts"), AssistantMessage.builder().content("").build(), results),
				options);

		assertSame(request, new FirstRoundToolChoiceAdvisor(VENDOR, "m").requireToolOnFirstRound(request));
	}

	@Test
	void nothingIsAskedWithoutToolsOrOfAVendorThatCannotBeAsked() {
		ChatClientRequest noTools = request(List.of(new UserMessage("hi")), ToolCallingChatOptions.builder().build());
		assertSame(noTools, new FirstRoundToolChoiceAdvisor(VENDOR, "m").requireToolOnFirstRound(noTools));

		ChatClientRequest cannot = request(List.of(new UserMessage("hi")), withTools());
		assertSame(cannot, new FirstRoundToolChoiceAdvisor(options -> null, "m").requireToolOnFirstRound(cannot));
	}
}
