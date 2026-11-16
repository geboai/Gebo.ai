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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;

import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry;

/**
 * Pins the compressed chat history sent to the model: the consolidated history,
 * then every recent interaction once, in order.
 */
class CompressedChatHistoryTest {

	@SuppressWarnings("rawtypes")
	@Test
	void everyRecentInteractionIsSentOnceInOrder() {
		GAbstractConfigurableChatModel model = mock(GAbstractConfigurableChatModel.class,
				withSettings().defaultAnswer(Answers.CALLS_REAL_METHODS));
		IChatRequestContext context = mock(IChatRequestContext.class);
		when(context.getConsolidatedHistory()).thenReturn("the summary of the older turns");
		when(context.getInteractions()).thenReturn(List.of(
				IChatSessionEntry.builder().user("question one").assistant("answer one").build(),
				IChatSessionEntry.builder().user("question two").assistant("answer two").build(),
				IChatSessionEntry.builder().user("question three").assistant("answer three").build()));

		List<Message> messages = model.createCompressedHistory(context);

		assertEquals(6, messages.size());
		assertTrue(messages.get(0).getText().contains("the summary of the older turns"));
		assertTrue(messages.get(0).getText().contains("question one"));
		assertFalse(messages.get(0).getText().contains("\\r\\n"), "real line breaks, not escapes");
		assertEquals("answer one", messages.get(1).getText());
		assertEquals("question two", messages.get(2).getText());
		assertEquals("answer two", messages.get(3).getText());
		assertEquals("question three", messages.get(4).getText());
		assertEquals("answer three", messages.get(5).getText());
	}

	/**
	 * The history replayed to the model is made of whole turns: a user message and its
	 * answer, text only. The tool calls of a turn and their results live in the tool loop
	 * of that turn's call and are never part of it, so trimming the history (the session
	 * shrink, a window) can never keep a tool call without its result: the turn-aware
	 * compaction of Spring AI's session memory has nothing to fix here.
	 */
	@SuppressWarnings("rawtypes")
	@Test
	void theHistoryIsMadeOfWholeTurnsWithNoToolCall() {
		GAbstractConfigurableChatModel model = mock(GAbstractConfigurableChatModel.class,
				withSettings().defaultAnswer(Answers.CALLS_REAL_METHODS));
		IChatRequestContext context = mock(IChatRequestContext.class);
		when(context.getInteractions()).thenReturn(List.of(
				IChatSessionEntry.builder().user("look it up").assistant("It is 4, as the lookup said.").build(),
				IChatSessionEntry.builder().user("and then?").assistant("Then 5.").build()));

		for (String consolidated : new String[] { null, "the summary of the older turns" }) {
			when(context.getConsolidatedHistory()).thenReturn(consolidated);
			List<Message> messages = model.createHistoryMessages(context);
			assertEquals(4, messages.size());
			for (int i = 0; i < messages.size(); i++) {
				Message message = messages.get(i);
				assertEquals(i % 2 == 0 ? MessageType.USER : MessageType.ASSISTANT, message.getMessageType(),
						"a user message and its answer, turn after turn");
				if (message instanceof AssistantMessage answer) {
					assertFalse(answer.hasToolCalls());
				}
			}
		}
	}
}
