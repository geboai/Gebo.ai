/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.ollama.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;

import ai.gebo.llms.abstraction.layer.model.GChatAnswerChunk;
import ai.gebo.llms.abstraction.layer.services.IGReasoningExtractor;
import ai.gebo.llms.abstraction.layer.services.ThinkingNormalizationAdvisor;
import reactor.core.publisher.Flux;

/**
 * Ollama's thinking as Spring AI's OllamaChatModel (2.0.1) streams it, the piece each
 * chunk adds in the "thinking" metadata, and the thinking tags of a model run without the
 * think api: the reasoning apart, the answer as it streams.
 */
class OllamaReasoningTest {

	private static ChatResponse chunk(String thinking, String text) {
		return new ChatResponse(List.of(new Generation(AssistantMessage.builder().content(text)
				.properties(thinking != null ? Map.of("thinking", thinking) : Map.of()).build())));
	}

	/** The chunks as an Ollama model's ChatClient streams them: markup handled. */
	private static List<GChatAnswerChunk> streamed(ChatResponse... chunks) {
		final ChatModel model = new ChatModel() {
			@Override
			public ChatResponse call(Prompt prompt) {
				throw new UnsupportedOperationException();
			}

			@Override
			public Flux<ChatResponse> stream(Prompt prompt) {
				return Flux.just(chunks);
			}
		};
		return ChatClient.builder(model)
				.defaultAdvisors(new ThinkingNormalizationAdvisor(OllamaChatModelConfigurationSupportService.REASONING,
						() -> true, g -> false))
				.build().prompt("q").stream().chatResponse().map(GChatAnswerChunk::of).collectList().block();
	}

	private static String join(List<GChatAnswerChunk> chunks, boolean thinking) {
		final StringBuilder out = new StringBuilder();
		chunks.forEach(c -> out.append(thinking ? c.thinking() : c.answer()));
		return out.toString();
	}

	@Test
	void theThinkApisPiecesApartFromTheAnswer() {
		final List<GChatAnswerChunk> chunks = streamed(chunk("Okay, ", ""), chunk("the sum.", ""), chunk(null, "It is 4."));

		assertEquals("Okay, the sum.", join(chunks, true));
		assertEquals("It is 4.", join(chunks, false));
		assertEquals(new IGReasoningExtractor.Reasoning("", "x", false),
				OllamaChatModelConfigurationSupportService.REASONING.round()
						.of(new Generation(new AssistantMessage("x"))));
	}

	@Test
	void aModelWritingTagsHasThemTakenOutOneWritingNoneAnswersAsItStreams() {
		final List<GChatAnswerChunk> tagged = streamed(chunk(null, "<think>\nthe sum"), chunk(null, "</think>\n\nIt is 4."));
		final List<GChatAnswerChunk> plain = streamed(chunk(null, "It is"), chunk(null, " 4."));

		assertEquals("\nthe sum", join(tagged, true));
		assertEquals("\n\nIt is 4.", join(tagged, false));
		assertEquals(List.of("It is", " 4."), plain.stream().map(GChatAnswerChunk::answer).toList());
	}
}
