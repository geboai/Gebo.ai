/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.anthropic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.anthropic.http.okhttp.AnthropicHttpClientBuilderCustomizer;
import org.springframework.ai.chat.client.ChatClient;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import ai.gebo.llms.abstraction.layer.model.GChatAnswer;
import ai.gebo.llms.abstraction.layer.model.GChatAnswerChunk;
import ai.gebo.llms.abstraction.layer.services.AnswerFirstGenerations;
import ai.gebo.llms.abstraction.layer.services.ThinkingNormalizationAdvisor;
import ai.gebo.llms.anthropic.http.AnthropicThinkingTap;

/**
 * Claude's thinking reaches the user before the answer, through Spring AI's
 * AnthropicChatModel and the Anthropic SDK over a local server streaming a recorded
 * Messages API stream: Spring AI alone gives its text only after the answer.
 */
class AnthropicLiveThinkingTest {

	private static final String STREAM = String.join("\n",
			"event: message_start",
			"data: {\"type\":\"message_start\",\"message\":{\"id\":\"msg_1\",\"type\":\"message\",\"role\":\"assistant\",\"model\":\"claude-test\",\"content\":[],\"stop_reason\":null,\"stop_sequence\":null,\"usage\":{\"input_tokens\":10,\"output_tokens\":1}}}",
			"",
			"event: content_block_start",
			"data: {\"type\":\"content_block_start\",\"index\":0,\"content_block\":{\"type\":\"thinking\",\"thinking\":\"\",\"signature\":\"\"}}",
			"",
			"event: content_block_delta",
			"data: {\"type\":\"content_block_delta\",\"index\":0,\"delta\":{\"type\":\"thinking_delta\",\"thinking\":\"The user asks \"}}",
			"",
			"event: content_block_delta",
			"data: {\"type\":\"content_block_delta\",\"index\":0,\"delta\":{\"type\":\"thinking_delta\",\"thinking\":\"for a sum: 2+2 \\u00e8 4.\"}}",
			"",
			"event: content_block_delta",
			"data: {\"type\":\"content_block_delta\",\"index\":0,\"delta\":{\"type\":\"signature_delta\",\"signature\":\"EqQBCkYIBxgC\"}}",
			"",
			"event: content_block_stop",
			"data: {\"type\":\"content_block_stop\",\"index\":0}",
			"",
			"event: content_block_start",
			"data: {\"type\":\"content_block_start\",\"index\":1,\"content_block\":{\"type\":\"text\",\"text\":\"\"}}",
			"",
			"event: content_block_delta",
			"data: {\"type\":\"content_block_delta\",\"index\":1,\"delta\":{\"type\":\"text_delta\",\"text\":\"The answer\"}}",
			"",
			"event: content_block_delta",
			"data: {\"type\":\"content_block_delta\",\"index\":1,\"delta\":{\"type\":\"text_delta\",\"text\":\" is 4.\"}}",
			"",
			"event: content_block_stop",
			"data: {\"type\":\"content_block_stop\",\"index\":1}",
			"",
			"event: message_delta",
			"data: {\"type\":\"message_delta\",\"delta\":{\"stop_reason\":\"end_turn\",\"stop_sequence\":null},\"usage\":{\"output_tokens\":20}}",
			"",
			"event: message_stop",
			"data: {\"type\":\"message_stop\"}",
			"", "");

	private static final String MESSAGE = "{\"id\":\"msg_2\",\"type\":\"message\",\"role\":\"assistant\",\"model\":\"claude-test\","
			+ "\"content\":[{\"type\":\"thinking\",\"thinking\":\"The user asks for a sum.\",\"signature\":\"EqQB\"},"
			+ "{\"type\":\"text\",\"text\":\"The answer is 4.\"}],\"stop_reason\":\"end_turn\",\"stop_sequence\":null,"
			+ "\"usage\":{\"input_tokens\":10,\"output_tokens\":20}}";

	private HttpServer server;
	private final List<String> tapHeadersReceived = new CopyOnWriteArrayList<>();

	@BeforeEach
	void serve() throws IOException {
		server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
		server.createContext("/", this::answer);
		server.start();
	}

	@AfterEach
	void stop() {
		server.stop(0);
	}

	private void answer(HttpExchange exchange) throws IOException {
		final String tap = exchange.getRequestHeaders().getFirst(AnthropicThinkingTap.HEADER);
		if (tap != null) {
			tapHeadersReceived.add(tap);
		}
		final String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
		final boolean stream = body.contains("\"stream\":true");
		exchange.getResponseHeaders().add("Content-Type", stream ? "text/event-stream" : "application/json");
		exchange.sendResponseHeaders(200, 0);
		try (OutputStream out = exchange.getResponseBody()) {
			if (!stream) {
				out.write(MESSAGE.getBytes(StandardCharsets.UTF_8));
				return;
			}
			// event by event, as Claude streams them
			for (String event : STREAM.split("\n\n")) {
				out.write((event + "\n\n").getBytes(StandardCharsets.UTF_8));
				out.flush();
			}
		}
	}

	private ChatClient client(AnthropicHttpClientBuilderCustomizer customizer) {
		final AnthropicChatModel model = AnthropicChatModel.builder()
				.options(AnthropicChatOptions.builder().apiKey("test-key")
						.baseUrl("http://127.0.0.1:" + server.getAddress().getPort()).model("claude-test").maxTokens(1000)
						.build())
				.httpClientBuilderCustomizer(customizer).build();
		// the advisors GAbstractConfigurableChatModel gives a Claude model's ChatClient
		return ChatClient.builder(model)
				.defaultAdvisors(new AnswerFirstGenerations.Advisor(AnthropicChatModelConfigurationSupportService::isThinkingBlock),
						new ThinkingNormalizationAdvisor(new AnthropicReasoningExtractor(), () -> false,
								AnthropicChatModelConfigurationSupportService::isThinkingBlock))
				.build();
	}

	private static String thinkingBeforeTheAnswer(List<GChatAnswerChunk> chunks) {
		final StringBuilder thinking = new StringBuilder();
		for (GChatAnswerChunk chunk : chunks) {
			thinking.append(chunk.thinking());
			if (!chunk.answer().isEmpty()) {
				break;
			}
		}
		return thinking.toString();
	}

	private static String all(List<GChatAnswerChunk> chunks, boolean thinking) {
		final StringBuilder out = new StringBuilder();
		chunks.forEach(c -> out.append(thinking ? c.thinking() : c.answer()));
		return out.toString();
	}

	@Test
	void theThinkingStreamsBeforeTheAnswerOnceAndTheMarkStaysHome() {
		final List<GChatAnswerChunk> chunks = client(b -> b.interceptor(AnthropicThinkingTap.INSTANCE)).prompt("2+2?")
				.stream().chatResponse().map(GChatAnswerChunk::of).collectList().block();

		assertEquals("The user asks for a sum: 2+2 è 4.", thinkingBeforeTheAnswer(chunks),
				"all the thinking, read off the stream, before the first piece of answer");
		assertEquals("The user asks for a sum: 2+2 è 4.", all(chunks, true), "once: not again at the turn's end");
		assertEquals("The answer is 4.", all(chunks, false));
		assertTrue(chunks.get(0).thinkingActive());
		assertTrue(tapHeadersReceived.isEmpty(), "the request's mark is taken off before it is sent");
	}

	@Test
	void withoutTheTapTheThinkingComesOnceAtTheTurnsEnd() {
		final List<GChatAnswerChunk> chunks = client(b -> {
		}).prompt("2+2?").stream().chatResponse().map(GChatAnswerChunk::of).collectList().block();

		assertEquals("", thinkingBeforeTheAnswer(chunks), "Spring AI keeps it until the turn ends");
		assertEquals("The user asks for a sum: 2+2 è 4.", all(chunks, true));
		assertEquals("The answer is 4.", all(chunks, false));
		assertFalse(tapHeadersReceived.isEmpty(), "with no tap the mark reaches the server: it is harmless");
	}

	@Test
	void aCallAnswersWithItsAnswerItsThinkingApart() {
		final GChatAnswer answer = GChatAnswer.of(
				client(b -> b.interceptor(AnthropicThinkingTap.INSTANCE)).prompt("2+2?").call().chatResponse());

		assertEquals("The answer is 4.", answer.answer());
		assertEquals("The user asks for a sum.", answer.thinking(), "once, from its thinking block");
	}
}
