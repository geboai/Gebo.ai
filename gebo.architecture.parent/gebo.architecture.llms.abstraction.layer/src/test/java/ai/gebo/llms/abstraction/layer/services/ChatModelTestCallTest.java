/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingManager;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import io.micrometer.observation.ObservationRegistry;

/**
 * Pins the test call a chat model makes when a user sets it: one request made the way
 * the model makes its own (the configured options, tools included, go with it), a
 * refusal reported with the provider's own words, and a tool call in the answer taken
 * as an answer, never run.
 */
class ChatModelTestCallTest {

	/** A model answering through the given provider model. */
	@SuppressWarnings({ "rawtypes" })
	static class ScriptedModel extends GAbstractConfigurableChatModel<GBaseChatModelConfig, ChatModel> {

		ScriptedModel(ChatModel provider) {
			super(null, null, recordingAsIs(), ObservationRegistry.NOOP);
			this.model = provider;
			this.config = new GBaseChatModelConfig();
			this.config.setCode("chat-test-model");
		}

		@Override
		protected IGConfigurableChatModel cloneMeWithInjection() {
			throw new UnsupportedOperationException();
		}

		@Override
		protected ChatModel configureModel(GBaseChatModelConfig config, GChatModelType type,
				ToolCallingManager toolsCallsManager) {
			return model;
		}
	}

	/** The usage recording leaves the provider model as it is. */
	private static IChatModelUsageAdvisorFactory recordingAsIs() {
		IChatModelUsageAdvisorFactory usage = mock(IChatModelUsageAdvisorFactory.class);
		when(usage.recording(any(), any(), any(), any())).thenAnswer(call -> call.getArgument(0));
		return usage;
	}

	private static ChatResponse answer(AssistantMessage message) {
		return new ChatResponse(List.of(new Generation(message)));
	}

	@Test
	void theTestCallSendsOneUserMessageWithTheModelsOwnOptions() throws Exception {
		ChatModel provider = mock(ChatModel.class);
		when(provider.call(any(Prompt.class))).thenReturn(answer(new AssistantMessage("OK")));

		new ScriptedModel(provider).testCall();

		ArgumentCaptor<Prompt> sent = ArgumentCaptor.forClass(Prompt.class);
		verify(provider).call(sent.capture());
		// no options of its own: the provider model sends its configured ones, sampling,
		// thinking, output limit and tool definitions alike
		assertNull(sent.getValue().getOptions());
		assertEquals(1, sent.getValue().getInstructions().size());
		assertEquals(MessageType.USER, sent.getValue().getInstructions().get(0).getMessageType());
		assertEquals(GAbstractConfigurableChatModel.TEST_CALL_PROMPT,
				sent.getValue().getInstructions().get(0).getText());
	}

	@Test
	void aRefusedRequestIsReportedWithTheProvidersOwnWords() {
		ChatModel provider = mock(ChatModel.class);
		when(provider.call(any(Prompt.class))).thenThrow(new IllegalStateException("retries exhausted",
				new IllegalArgumentException("400: Unsupported value: 'temperature' does not support 0")));

		LLMConfigException refused = assertThrows(LLMConfigException.class,
				() -> new ScriptedModel(provider).testCall());

		assertTrue(refused.getMessage().contains("chat-test-model"), refused.getMessage());
		assertTrue(refused.getMessage().endsWith("400: Unsupported value: 'temperature' does not support 0"),
				refused.getMessage());
	}

	@Test
	void noResponseAtAllIsARefusal() {
		ChatModel provider = mock(ChatModel.class);
		when(provider.call(any(Prompt.class))).thenReturn(null);

		assertThrows(LLMConfigException.class, () -> new ScriptedModel(provider).testCall());
	}

	@Test
	void aToolCallInTheAnswerIsAnAnswer() {
		ChatModel provider = mock(ChatModel.class);
		AssistantMessage toolCall = AssistantMessage.builder().content("")
				.toolCalls(List.of(new AssistantMessage.ToolCall("call-1", "function", "searchWeb", "{}"))).build();
		when(provider.call(any(Prompt.class))).thenReturn(answer(toolCall));

		assertDoesNotThrow(() -> new ScriptedModel(provider).testCall());
	}

	@Test
	void theInnermostMessageIsTheProvidersOne() {
		assertEquals("the provider's words", GAbstractConfigurableChatModel.innermostMessage(
				new RuntimeException("wrapper", new RuntimeException("the provider's words"))));
		assertEquals("only one", GAbstractConfigurableChatModel
				.innermostMessage(new RuntimeException("only one", new RuntimeException((String) null))));
		assertEquals("IllegalStateException",
				GAbstractConfigurableChatModel.innermostMessage(new IllegalStateException()));
	}
}
