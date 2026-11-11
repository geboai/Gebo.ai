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

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.part.MessagePart;
import org.springframework.ai.chat.messages.part.OpaquePayload;
import org.springframework.ai.chat.messages.part.ReasoningPart;
import org.springframework.ai.chat.messages.part.StreamingParts;
import org.springframework.ai.chat.messages.part.TextPart;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;

import ai.gebo.llms.abstraction.layer.model.GChatAnswer;
import ai.gebo.llms.abstraction.layer.model.GChatAnswerChunk;
import ai.gebo.llms.abstraction.layer.services.IGReasoningExtractor.Reasoning;
import reactor.core.publisher.Flux;

/**
 * The reading by default of every provider: the reasoning parts of a message as Spring AI
 * 2.1's models give them, a streamed chunk's parts the pieces it adds (Claude's indexed
 * thinking deltas and signature, DeepSeek's and Ollama's plain parts alike), a blocking
 * call's parts the whole; a message with no reasoning part read the legacy way.
 */
class PartsReasoningExtractorTest {

	private static Generation of(MessagePart... parts) {
		return new Generation(AssistantMessage.builder().parts(List.of(parts)).build());
	}

	private static ReasoningPart signed(String text, String signature) {
		return new ReasoningPart(text, null, new OpaquePayload("anthropic", "signature", signature), Map.of());
	}

	@Test
	void eachChunksPartsAreThePiecesItAdds() {
		final IGReasoningExtractor.Round round = IGReasoningExtractor.PARTS.round();
		assertEquals(new Reasoning("The user ", "", true),
				round.of(of(StreamingParts.partial(ReasoningPart.of("The user "), 0))));
		assertEquals(new Reasoning("The user asks.", "", true),
				round.of(of(StreamingParts.partial(ReasoningPart.of("The user asks."), 0))),
				"a piece, even one starting as the reasoning so far");
		assertEquals(new Reasoning("", "", true), round.of(of(StreamingParts.partial(signed("", "EqQB"), 0))),
				"a signature: no text, the model is reasoning");
		assertEquals(new Reasoning("", "The answer", false),
				round.of(of(StreamingParts.partial(TextPart.of("The answer"), 1))));
	}

	@Test
	void aCallsPartsAreTheWholeReasoningApartFromTheAnswer() {
		final Generation call = of(signed("Let me think.", "EqQB"), TextPart.of("The answer."));
		assertEquals(new Reasoning("Let me think.", "The answer.", true), IGReasoningExtractor.PARTS.round().of(call));
		assertEquals("The answer.", call.getOutput().getText(), "the text of a message is its text parts only");
	}

	@Test
	void aRedactedBlockHasNoTextAndStillSaysTheModelIsReasoning() {
		final ReasoningPart redacted = new ReasoningPart(null, null,
				new OpaquePayload("anthropic", "redacted_thinking", "EmwK"), Map.of());
		assertEquals(new Reasoning("", "", true), IGReasoningExtractor.PARTS.round().of(of(redacted)));
	}

	@Test
	void aMessageWithNoReasoningPartIsReadTheLegacyWay() {
		final IGReasoningExtractor.Round round = IGReasoningExtractor.PARTS.round();
		final Generation first = new Generation(AssistantMessage.builder().content("")
				.properties(Map.of(IGReasoningExtractor.REASONING_CONTENT_METADATA, "I need ")).build());
		final Generation second = new Generation(AssistantMessage.builder().content("")
				.properties(Map.of(IGReasoningExtractor.REASONING_CONTENT_METADATA, "I need the sum.")).build());
		assertEquals(new Reasoning("I need ", "", false), round.of(first));
		assertEquals(new Reasoning("the sum.", "", false), round.of(second), "all of it so far: the piece it grew by");
		assertEquals(new Reasoning("", "plain", false), round.of(new Generation(new AssistantMessage("plain"))));
	}

	/** A model streaming Claude's shape: indexed thinking deltas, a signature, then the text. */
	private static ChatModel claude() {
		return new ChatModel() {
			@Override
			public ChatResponse call(Prompt prompt) {
				return new ChatResponse(List.of(of(signed("The user asks for a sum.", "EqQB"),
						TextPart.of("The answer is 4."))));
			}

			@Override
			public Flux<ChatResponse> stream(Prompt prompt) {
				return Flux.just(new ChatResponse(List.of(of(StreamingParts.partial(ReasoningPart.of("The user asks "), 0)))),
						new ChatResponse(List.of(of(StreamingParts.partial(ReasoningPart.of("for a sum."), 0)))),
						new ChatResponse(List.of(of(StreamingParts.partial(signed("", "EqQB"), 0)))),
						new ChatResponse(List.of(of(StreamingParts.partial(TextPart.of("The answer"), 1)))),
						new ChatResponse(List.of(of(StreamingParts.partial(TextPart.of(" is 4."), 1)))));
			}
		};
	}

	private static ChatClient client(ChatModel model) {
		return ChatClient.builder(model)
				.defaultAdvisors(new ThinkingNormalizationAdvisor(IGReasoningExtractor.PARTS, () -> false, g -> false))
				.build();
	}

	@Test
	void theThinkingStreamsBeforeTheAnswerAndACallGivesItApart() {
		final List<GChatAnswerChunk> chunks = client(claude()).prompt("2+2?").stream().chatResponse()
				.map(GChatAnswerChunk::of).collectList().block();
		final StringBuilder thinking = new StringBuilder();
		final StringBuilder answer = new StringBuilder();
		chunks.forEach(c -> {
			thinking.append(c.thinking());
			answer.append(c.answer());
		});
		assertEquals("The user asks for a sum.", thinking.toString());
		assertEquals("The answer is 4.", answer.toString());
		assertTrue(chunks.get(0).thinkingActive());
		assertTrue(chunks.get(2).thinkingActive(), "the signature chunk: still reasoning");
		assertFalse(chunks.get(3).thinkingActive());

		final GChatAnswer call = GChatAnswer.of(client(claude()).prompt("2+2?").call().chatResponse());
		assertEquals("The answer is 4.", call.answer());
		assertEquals("The user asks for a sum.", call.thinking());
		assertEquals("The answer is 4.", client(claude()).prompt("2+2?").call().content(),
				"content() reads the answer only");
	}
}
