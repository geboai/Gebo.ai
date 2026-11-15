/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.AssistantMessage.ToolCall;
import org.springframework.ai.chat.messages.part.MessagePart;
import org.springframework.ai.chat.messages.part.StreamingParts;
import org.springframework.ai.chat.messages.part.TextPart;
import org.springframework.ai.chat.messages.part.ToolCallPart;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;

import ai.gebo.llms.abstraction.layer.model.GChatAnswerChunk.ToolCallProgress;

/**
 * Pins how a streamed chunk gives the tool calls the model is writing: the pieces of
 * their arguments (Spring AI 2.1 partial tool call parts), the calls complete as the
 * ThinkingNormalizationAdvisor announces them, nothing on a chunk with neither.
 */
class GChatAnswerChunkTest {

	static ChatResponse chunk(List<MessagePart> parts) {
		return new ChatResponse(List.of(new Generation(AssistantMessage.builder().parts(parts).build())));
	}

	static MessagePart argumentsPiece(String piece) {
		return StreamingParts.partial(ToolCallPart.of(new ToolCall("call_1", "function", "searchKnowledgeBase", piece)),
				1);
	}

	@Test
	void thePiecesOfTheArgumentsComeWithTheText() {
		GChatAnswerChunk read = GChatAnswerChunk
				.of(chunk(List.of(StreamingParts.partial(TextPart.of("Let me look."), 0), argumentsPiece("{\"que"),
						argumentsPiece("ry\":\"qdrant\"}"))));

		assertEquals("Let me look.", read.answer());
		assertEquals(2, read.toolCalls().size());
		ToolCallProgress first = read.toolCalls().get(0);
		assertEquals("call_1", first.id());
		assertEquals(1, first.index());
		assertEquals("searchKnowledgeBase", first.name());
		assertEquals("{\"que", first.arguments());
		assertFalse(first.complete());
		assertEquals("{\"que" + "ry\":\"qdrant\"}",
				read.toolCalls().stream().map(ToolCallProgress::arguments).reduce("", String::concat));
	}

	@Test
	void theAnnouncedCompleteCallsComeComplete() {
		ToolCallProgress call = ToolCallProgress.of((ToolCallPart) StreamingParts.complete(
				ToolCallPart.of(new ToolCall("call_1", "function", "searchKnowledgeBase", "{\"query\":\"qdrant\"}")), 1));
		ChatResponse announced = ChatResponse.builder()
				.generations(List.of(new Generation(new AssistantMessage(""))))
				.metadata(GChatAnswerChunk.TOOL_CALLS_COMPLETE_METADATA, List.of(call)).build();

		GChatAnswerChunk read = GChatAnswerChunk.of(announced);

		assertEquals(List.of(call), read.toolCalls());
		assertTrue(read.toolCalls().get(0).complete());
		assertEquals("{\"query\":\"qdrant\"}", read.toolCalls().get(0).arguments());
		assertEquals("", read.answer());
	}

	@Test
	void aCompleteCallInsideAChunkIsNotProgress() {
		// the chunk carrying the calls is run by the tool loop, never read as progress
		GChatAnswerChunk read = GChatAnswerChunk.of(chunk(List.of(
				ToolCallPart.of(new ToolCall("call_1", "function", "searchKnowledgeBase", "{\"query\":\"x\"}")))));

		assertTrue(read.toolCalls().isEmpty());
	}

	@Test
	void aChunkWithNoToolCallHasNone() {
		assertTrue(GChatAnswerChunk.of(chunk(List.of(TextPart.of("The answer")))).toolCalls().isEmpty());
		assertTrue(GChatAnswerChunk.of(null).toolCalls().isEmpty());
	}
}
