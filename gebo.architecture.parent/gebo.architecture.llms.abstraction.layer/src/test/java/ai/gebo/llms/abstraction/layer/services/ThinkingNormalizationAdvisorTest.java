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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

import ai.gebo.llms.abstraction.layer.model.GChatAnswer;
import ai.gebo.llms.abstraction.layer.model.GChatAnswerChunk;
import reactor.core.publisher.Flux;

/**
 * Every provider's answer the same shape: the text of a chunk is its answer only, its
 * reasoning in the response metadata, read per model round of a tool calling loop.
 */
class ThinkingNormalizationAdvisorTest {

	/** A chunk with its text, its message metadata, and its tool calls. */
	private static ChatResponse chunk(String text, Map<String, Object> properties, List<AssistantMessage.ToolCall> calls,
			String finish) {
		final AssistantMessage message = AssistantMessage.builder().content(text).properties(properties)
				.toolCalls(calls).build();
		return new ChatResponse(List.of(new Generation(message,
				finish != null ? ChatGenerationMetadata.builder().finishReason(finish).build()
						: ChatGenerationMetadata.NULL)));
	}

	private static ChatResponse text(String text) {
		return chunk(text, Map.of(), List.of(), null);
	}

	/** The OpenAI client's shape: all the reasoning so far on each chunk of a round. */
	private static ChatResponse reasoning(String soFar, String text) {
		return chunk(text, Map.of(IGReasoningExtractor.REASONING_CONTENT_METADATA, soFar), List.of(), null);
	}

	/** A model streaming what the function gives for the prompt. */
	private static ChatModel streaming(Function<Prompt, Flux<ChatResponse>> chunks) {
		return new ChatModel() {
			@Override
			public ChatResponse call(Prompt prompt) {
				throw new UnsupportedOperationException();
			}

			@Override
			public Flux<ChatResponse> stream(Prompt prompt) {
				return chunks.apply(prompt);
			}

			@Override
			public ChatOptions getOptions() {
				return ToolCallingChatOptions.builder().build();
			}
		};
	}

	private static ChatModel answering(ChatResponse response) {
		return new ChatModel() {
			@Override
			public ChatResponse call(Prompt prompt) {
				return response;
			}

			@Override
			public ChatOptions getOptions() {
				return ToolCallingChatOptions.builder().build();
			}
		};
	}

	private static ChatClient client(ChatModel model, IGReasoningExtractor extractor, boolean inlineTags) {
		return ChatClient.builder(model)
				.defaultAdvisors(new AnswerFirstGenerations.Advisor(g -> g.getOutput().getMetadata().containsKey("signature")),
						new ThinkingNormalizationAdvisor(extractor, () -> inlineTags,
								g -> g.getOutput().getMetadata().containsKey("signature")))
				.build();
	}

	private static List<GChatAnswerChunk> streamed(ChatClient client) {
		return client.prompt("q").stream().chatResponse().map(GChatAnswerChunk::of).collectList().block();
	}

	private static String answers(List<GChatAnswerChunk> chunks) {
		return String.join("", chunks.stream().map(GChatAnswerChunk::answer).toList());
	}

	private static String thoughts(List<GChatAnswerChunk> chunks) {
		return String.join("", chunks.stream().map(GChatAnswerChunk::thinking).toList());
	}

	@Test
	void theReasoningGrownOnEachChunkComesAsThePiecesItAdds() {
		final List<GChatAnswerChunk> chunks = streamed(client(streaming(prompt -> Flux.just(reasoning("Let", ""),
				reasoning("Let me", ""), reasoning("Let me think.", ""), reasoning("Let me think.", "The answer"),
				chunk(" is 4.", Map.of(), List.of(), "STOP"))), IGReasoningExtractor.OPENAI, false));

		assertEquals(List.of("Let", " me", " think.", "", ""), chunks.stream().map(GChatAnswerChunk::thinking).toList());
		assertEquals("The answer is 4.", answers(chunks));
		assertEquals(List.of(true, true, true, false, false),
				chunks.stream().map(GChatAnswerChunk::thinkingActive).toList());
		assertEquals("STOP", chunks.get(chunks.size() - 1).finishReason());
	}

	@Test
	void theReasoningBetweenTagsIsTakenOutOfTheText() {
		final List<GChatAnswerChunk> chunks = streamed(client(
				streaming(prompt -> Flux.just(text("<think>re"), text("ason</th"), text("ink>An"), text("swer <"))),
				IGReasoningExtractor.OPENAI, true));

		assertEquals("Answer <", answers(chunks), "the '<' held is given when the round ends");
		assertEquals("reason", thoughts(chunks));
		assertFalse(answers(chunks).contains("think"));
		assertTrue(chunks.get(0).thinkingActive());
	}

	@Test
	void aModelWritingNoTagsIsLeftAsItIs() {
		final List<GChatAnswerChunk> chunks = streamed(
				client(streaming(prompt -> Flux.just(text("The answer"), text(" is 4."))), IGReasoningExtractor.OPENAI,
						true));

		assertEquals(List.of("The answer", " is 4."), chunks.stream().map(GChatAnswerChunk::answer).toList());
		assertEquals("", thoughts(chunks));
	}

	@Test
	void theReasoningOfARoundCallingToolsReachesTheUserEachRoundOnItsOwn() {
		final AtomicInteger toolRuns = new AtomicInteger();
		final ToolCallback lookup = new ToolCallback() {
			@Override
			public ToolDefinition getToolDefinition() {
				return ToolDefinition.builder().name("lookup").description("lookup").inputSchema("{}").build();
			}

			@Override
			public String call(String toolInput) {
				toolRuns.incrementAndGet();
				return "found";
			}
		};
		final List<Prompt> prompts = new ArrayList<>();
		final ChatModel model = streaming(prompt -> {
			prompts.add(prompt);
			final boolean afterTool = prompt.getInstructions().stream().anyMatch(ToolResponseMessage.class::isInstance);
			if (!afterTool) {
				// the reasoning, then the tool call on a chunk carrying the rest of it
				return Flux.just(reasoning("I need ", ""), chunk("", Map.of(IGReasoningExtractor.REASONING_CONTENT_METADATA,
						"I need to look it up."), List.of(new AssistantMessage.ToolCall("1", "function", "lookup", "{}")),
						"TOOL_CALLS"));
			}
			// the second round: its own reasoning, from the start
			return Flux.just(reasoning("Found it.", ""), chunk("It is 4.", Map.of(), List.of(), "STOP"));
		});

		final List<GChatAnswerChunk> chunks = client(model, IGReasoningExtractor.OPENAI, false).prompt("q")
				.toolCallbacks(lookup).stream().chatResponse().map(GChatAnswerChunk::of).collectList().block();

		assertEquals(1, toolRuns.get());
		assertEquals(2, prompts.size());
		assertEquals("I need to look it up.Found it.", thoughts(chunks),
				"the reasoning on the tool call chunk too, the second round read from its start");
		assertEquals("It is 4.", answers(chunks));
		final Message replayed = prompts.get(1).getInstructions().stream().filter(AssistantMessage.class::isInstance)
				.reduce((a, b) -> b).orElseThrow();
		assertFalse(replayed.getMetadata().containsKey(GChatAnswerChunk.THINKING_DELTA_METADATA),
				"the assistant message the tool round replays carries nothing of the normalization");
	}

	@Test
	void aBlockingCallsReasoningIsApartFromItsAnswer() {
		final ChatResponse claude = new ChatResponse(List.of(
				new Generation(AssistantMessage.builder().content("Let me think.").properties(Map.of("signature", "s")).build()),
				new Generation(AssistantMessage.builder().content("The answer.").properties(Map.of()).build())));

		final ChatResponse response = client(answering(claude), IGReasoningExtractor.NONE, false).prompt("q").call()
				.chatResponse();
		final GChatAnswer answer = GChatAnswer.of(response);

		assertEquals("The answer.", answer.answer());
		assertEquals("Let me think.", answer.thinking());
	}

	@Test
	void aBlockingCallsTagsAndFieldAreTakenOut() {
		final ChatResponse tagged = new ChatResponse(List.of(new Generation(AssistantMessage.builder()
				.content("<think>step</think>\n{\"a\":1}")
				.properties(Map.of(IGReasoningExtractor.REASONING_CONTENT_METADATA, "field ")).build())));

		final ChatClient client = client(answering(tagged), IGReasoningExtractor.OPENAI, true);
		final GChatAnswer answer = GChatAnswer.of(client.prompt("q").call().chatResponse());

		assertEquals("\n{\"a\":1}", answer.answer());
		assertEquals("field step", answer.thinking());
		assertEquals("\n{\"a\":1}", client.prompt("q").call().content(), "content() reads the answer only");
	}
}
