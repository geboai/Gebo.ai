/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.ai.model.ContextContentRequired;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import io.micrometer.observation.ObservationRegistry;
import reactor.core.publisher.Flux;

/**
 * A call requiring a tool call, through a real chat client and its tool calling loop:
 * the vendor is asked to call a tool on the first round only, the tool runs, and the
 * round reading its result answers.
 */
class RequiredToolCallStreamTest {

	/** The temperature marking the options of a round asked to call a tool. */
	private static final double REQUIRING_MARK = 0.123;

	/** A vendor that calls the tool when nothing was read yet, and answers after. */
	static class ScriptedVendor implements ChatModel {
		final List<Prompt> rounds = new ArrayList<>();

		@Override
		public ChatResponse call(Prompt prompt) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ChatOptions getOptions() {
			return ToolCallingChatOptions.builder().build();
		}

		@Override
		public Flux<ChatResponse> stream(Prompt prompt) {
			rounds.add(prompt);
			boolean read = prompt.getInstructions().stream().anyMatch(ToolResponseMessage.class::isInstance);
			AssistantMessage output = read ? AssistantMessage.builder().content("From the documents.").build()
					: AssistantMessage.builder().content("")
							.toolCalls(List.of(new AssistantMessage.ToolCall("1", "function", "searchKnowledgeBase", "{}")))
							.build();
			return Flux.just(new ChatResponse(List.of(new Generation(output))));
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	static class VendorModel extends GAbstractConfigurableChatModel<GBaseChatModelConfig, ChatModel> {
		VendorModel(IGToolCallbackSourceRepositoryPattern repository, ScriptedVendor vendor) {
			super(null, repository, mock(IChatModelUsageAdvisorFactory.class), ObservationRegistry.NOOP);
			this.config = new GBaseChatModelConfig();
			this.config.setEnabledFunctions(List.of("searchKnowledgeBase"));
			this.chatClient = ChatClient.builder(vendor).build();
		}

		@Override
		protected ToolCallingChatOptions requireToolCall(ToolCallingChatOptions options) {
			return (ToolCallingChatOptions) options.mutate().temperature(REQUIRING_MARK).build();
		}

		@Override
		protected IGConfigurableChatModel cloneMeWithInjection() {
			throw new UnsupportedOperationException();
		}

		@Override
		protected ChatModel configureModel(GBaseChatModelConfig config, GChatModelType type,
				ToolCallingManager toolsCallsManager) {
			throw new UnsupportedOperationException();
		}
	}

	private static ToolCallback searchTool(List<String> calls) {
		return new ToolCallback() {
			@Override
			public ToolDefinition getToolDefinition() {
				return ToolDefinition.builder().name("searchKnowledgeBase").description("search").inputSchema("{}")
						.build();
			}

			@Override
			public String call(String toolInput) {
				calls.add(toolInput);
				return "found";
			}
		};
	}

	private static String stream(ScriptedVendor vendor, List<String> calls, boolean toolCallRequired) throws Exception {
		IGToolCallbackSourceRepositoryPattern repository = mock(IGToolCallbackSourceRepositoryPattern.class);
		when(repository.getTools(anyList())).thenReturn(List.of(searchTool(calls)));
		GPromptTemplateConfig prompt = new GPromptTemplateConfig();
		prompt.setSystemPromptTemplate("Answer from the documents.");
		prompt.setUserPromptTemplate("{question}");
		prompt.setChatHistory(ContextContentRequired.NOT_REQUIRED);
		prompt.setContextDocuments(ContextContentRequired.NOT_REQUIRED);
		prompt.setToolsCalling(ContextContentRequired.REQUIRED);
		IChatRequestContext context = IChatRequestContext.builder().actualUserRequest("a report on the contracts")
				.toolCallListener(new ToolCallsListener()).build();
		return String.join("", new VendorModel(repository, vendor)
				.streamStringResponse(prompt, Map.of(), context, toolCallRequired).collectList().block());
	}

	@Test
	void onlyTheFirstRoundIsAskedToCallATool() throws Exception {
		ScriptedVendor vendor = new ScriptedVendor();
		List<String> calls = new ArrayList<>();

		assertEquals("From the documents.", stream(vendor, calls, true));

		assertEquals(1, calls.size(), "the tool ran once");
		assertEquals(2, vendor.rounds.size());
		assertEquals(REQUIRING_MARK, vendor.rounds.get(0).getOptions().getTemperature(),
				"the first round is asked to call a tool");
		assertNull(vendor.rounds.get(1).getOptions().getTemperature(), "the round reading the results is free");
	}

	@Test
	void aCallNotRequiringAToolIsLeftAsItIs() throws Exception {
		ScriptedVendor vendor = new ScriptedVendor();
		List<String> calls = new ArrayList<>();

		assertEquals("From the documents.", stream(vendor, calls, false));

		assertEquals(1, calls.size(), "the chat client's own tool loop still runs the tool");
		assertNull(vendor.rounds.get(0).getOptions().getTemperature());
	}
}
