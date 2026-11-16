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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.tool.function.FunctionToolCallback;

import com.sun.net.httpserver.HttpServer;

import ai.gebo.core.messages.LLMCallOutcome;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGReasoningExtractor;
import ai.gebo.llms.abstraction.layer.services.impl.LLMUsageRecorder;
import ai.gebo.llms.abstraction.layer.services.impl.UsageAdvisorFactoryImpl.GeboChatModelUsageAdvisor;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.model.ModelType;
import io.micrometer.observation.ObservationRegistry;

/**
 * The usage monitoring of the chat models on the Responses api: Spring AI's Responses
 * model, wired as the OpenAI handler wires it, called through a ChatClient carrying
 * Gebo's usage advisor and Gebo's tool calling advisor, against a local server answering
 * as OpenAI's Responses api does (json, server sent events). One usage record per model
 * round trip, with the tokens OpenAI reported for it: a blocking call, a stream (from its
 * last event), each round of a tool calling loop blocking or streamed (the tool calling
 * advisor runs outside the usage advisor, as for every chat model: the rounds add up, none
 * counted twice), a failed response, a request repeated without the refused reasoning
 * summaries (accounted once).
 */
class OpenAiResponsesUsageTest {

	record Q(String q) {
	}

	static String usage(int in, int out) {
		return "{\"input_tokens\":" + in + ",\"input_tokens_details\":{\"cached_tokens\":0},\"output_tokens\":" + out
				+ ",\"output_tokens_details\":{\"reasoning_tokens\":0},\"total_tokens\":" + (in + out) + "}";
	}

	static final String MESSAGE = "{\"type\":\"message\",\"id\":\"msg_1\",\"role\":\"assistant\",\"status\":\"completed\",\"content\":[{\"type\":\"output_text\",\"text\":\"Rome found\",\"annotations\":[]}]}";
	static final String CALL = "{\"type\":\"function_call\",\"id\":\"fc_1\",\"call_id\":\"call_1\",\"name\":\"lookup\",\"arguments\":\"{\\\"q\\\":\\\"rome\\\"}\",\"status\":\"completed\"}";

	static String response(String status, String usage, String... items) {
		return "{\"id\":\"resp_1\",\"object\":\"response\",\"created_at\":1,\"model\":\"gpt-6.1-sol\",\"status\":\""
				+ status + "\",\"output\":[" + String.join(",", items) + "],\"usage\":" + usage
				+ ",\"parallel_tool_calls\":true,\"tool_choice\":\"auto\",\"tools\":[],\"temperature\":null,\"top_p\":null,"
				+ "\"error\":" + ("failed".equals(status)
						? "{\"code\":\"server_error\",\"message\":\"The model failed\"}"
						: "null")
				+ ",\"incomplete_details\":null,\"instructions\":null,\"metadata\":null}";
	}

	static String event(String type, int sequence, String fields) {
		return "event: " + type + "\ndata: {\"type\":\"" + type + "\",\"sequence_number\":" + sequence
				+ (fields.isEmpty() ? "" : "," + fields) + "}\n\n";
	}

	/** A streamed answer: text pieces, then the completed response with the usage. */
	static String streamedText(String usage) {
		return event("response.created", 0, "\"response\":" + response("in_progress", "null"))
				+ event("response.output_text.delta", 1,
						"\"item_id\":\"msg_1\",\"output_index\":0,\"content_index\":0,\"delta\":\"Rome \",\"logprobs\":[]")
				+ event("response.output_text.delta", 2,
						"\"item_id\":\"msg_1\",\"output_index\":0,\"content_index\":0,\"delta\":\"found\",\"logprobs\":[]")
				+ event("response.completed", 3, "\"response\":" + response("completed", usage, MESSAGE));
	}

	/** A streamed tool call: its arguments in pieces, then the completed response with the usage. */
	static String streamedCall(String usage) {
		String pending = CALL.replace("\"arguments\":\"{\\\"q\\\":\\\"rome\\\"}\",\"status\":\"completed\"",
				"\"arguments\":\"\",\"status\":\"in_progress\"");
		return event("response.created", 0, "\"response\":" + response("in_progress", "null"))
				+ event("response.output_item.added", 1, "\"output_index\":0,\"item\":" + pending)
				+ event("response.function_call_arguments.delta", 2,
						"\"item_id\":\"fc_1\",\"output_index\":0,\"delta\":\"{\\\"q\\\":\"")
				+ event("response.function_call_arguments.delta", 3,
						"\"item_id\":\"fc_1\",\"output_index\":0,\"delta\":\"\\\"rome\\\"}\"")
				+ event("response.function_call_arguments.done", 4,
						"\"item_id\":\"fc_1\",\"output_index\":0,\"name\":\"lookup\",\"arguments\":\"{\\\"q\\\":\\\"rome\\\"}\"")
				+ event("response.output_item.done", 5, "\"output_index\":0,\"item\":" + CALL)
				+ event("response.completed", 6, "\"response\":" + response("completed", usage, CALL));
	}

	record Reply(int status, String contentType, String body) {
		static Reply json(String body) {
			return new Reply(200, "application/json", body);
		}

		static Reply sse(String body) {
			return new Reply(200, "text/event-stream", body);
		}
	}

	HttpServer server;
	String base;
	final Deque<Reply> replies = new ArrayDeque<>();
	final List<String> requests = new CopyOnWriteArrayList<>();
	LLMUsageRecorder recorder;
	GBaseChatModelConfig config;

	@BeforeEach
	void start() throws IOException {
		server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
		server.createContext("/v1/responses", exchange -> {
			requests.add(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
			Reply reply;
			synchronized (replies) {
				reply = replies.poll();
			}
			if (reply == null) {
				reply = new Reply(500, "application/json", "{\"error\":{\"message\":\"no reply scripted\"}}");
			}
			byte[] bytes = reply.body().getBytes(StandardCharsets.UTF_8);
			exchange.getResponseHeaders().add("Content-Type", reply.contentType());
			exchange.sendResponseHeaders(reply.status(), bytes.length);
			try (OutputStream out = exchange.getResponseBody()) {
				out.write(bytes);
			}
		});
		server.start();
		base = "http://127.0.0.1:" + server.getAddress().getPort() + "/v1";
		recorder = mock(LLMUsageRecorder.class);
		config = mock(GBaseChatModelConfig.class);
	}

	@AfterEach
	void stop() {
		server.stop(0);
	}

	void script(Reply... scripted) {
		synchronized (replies) {
			replies.addAll(List.of(scripted));
		}
	}

	/** The chat client of a gpt-6 model, as the OpenAI handler and the chat model wire them. */
	ChatClient client() {
		OpenAiChatOptions chat = OpenAiChatOptions.builder().model("gpt-6.1-sol").apiKey("sk-test").baseUrl(base)
				.maxRetries(0).reasoningEffort("low").parallelToolCalls(true)
				.toolCallbacks(List.of(FunctionToolCallback.builder("lookup", (Q in) -> "found " + in.q())
						.description("Looks a place up").inputType(Q.class).build()))
				.build();
		ChatModel model = OpenAIChatModelConfigurationSupportService.responsesModel(OpenAiResponsesOptions.of(chat),
				ToolCallingManager.builder().build(), ObservationRegistry.NOOP, null);
		return ChatClient.builder(model)
				.defaultAdvisors(new GeboChatModelUsageAdvisor(config, recorder, () -> null, () -> "openai")).build();
	}

	ChatClient.ChatClientRequestSpec ask(ChatClient client) {
		return client.prompt("Where is Rome?").advisors(GeboToolCalling.builder().build().advisor(ObservationRegistry.NOOP));
	}

	void verifyRecorded(long in, long out, LLMCallOutcome outcome) {
		verify(recorder, timeout(5000).times(1)).record(eq(config), eq("openai"), eq(ModelType.CHAT), any(), any(),
				any(), anyLong(), any(), eq(in), eq(out), eq(in + out), eq(outcome));
	}

	@Test
	void aBlockingCallIsRecordedOnceWithItsTokens() {
		script(Reply.json(response("completed", usage(100, 30), MESSAGE)));

		String answer = ask(client()).call().content();

		assertEquals("Rome found", answer);
		verifyRecorded(100, 30, LLMCallOutcome.SUCCESS);
		verifyRecordCount(1);
	}

	@Test
	void aBlockingToolLoopIsRecordedOncePerRound() {
		script(Reply.json(response("completed", usage(100, 30), CALL)),
				Reply.json(response("completed", usage(150, 20), MESSAGE)));

		String answer = ask(client()).call().content();

		assertEquals("Rome found", answer);
		assertEquals(2, requests.size(), "the tool ran and the model was called again");
		assertTrue(requests.get(1).contains("found rome"), "the second round carries the tool result");
		verifyRecorded(100, 30, LLMCallOutcome.SUCCESS);
		verifyRecorded(150, 20, LLMCallOutcome.SUCCESS);
		verifyRecordCount(2);
	}

	void verifyRecordCount(int calls) {
		verify(recorder, timeout(5000).times(calls)).record(any(), any(), any(), any(), any(), any(), anyLong(), any(),
				anyLong(), anyLong(), anyLong(), any());
	}

	@Test
	void aStreamIsRecordedOnceFromItsLastEvent() {
		script(Reply.sse(streamedText(usage(100, 30))));

		String answer = String.join("", ask(client()).stream().content().collectList().block());

		assertEquals("Rome found", answer);
		verifyRecorded(100, 30, LLMCallOutcome.SUCCESS);
		verifyRecordCount(1);
	}

	@Test
	void aStreamedToolLoopIsRecordedOncePerRound() {
		script(Reply.sse(streamedCall(usage(100, 30))), Reply.sse(streamedText(usage(150, 20))));

		String answer = String.join("", ask(client()).stream().content().collectList().block());

		assertEquals("Rome found", answer);
		assertEquals(2, requests.size(), "the streamed call was run and the model called again");
		assertTrue(requests.get(1).contains("found rome"), "the second round carries the tool result");
		verifyRecorded(100, 30, LLMCallOutcome.SUCCESS);
		verifyRecorded(150, 20, LLMCallOutcome.SUCCESS);
		verifyRecordCount(2);
	}

	@Test
	void theStreamedReasoningSummaryIsTheThinkingShownBeforeTheAnswer() {
		String summaryDelta = "\"item_id\":\"rs_1\",\"output_index\":0,\"summary_index\":0,\"delta\":";
		script(Reply.sse(event("response.created", 0, "\"response\":" + response("in_progress", "null"))
				+ event("response.reasoning_summary_text.delta", 1, summaryDelta + "\"Looking \"")
				+ event("response.reasoning_summary_text.delta", 2, summaryDelta + "\"Rome up\"")
				+ event("response.output_text.delta", 3,
						"\"item_id\":\"msg_1\",\"output_index\":1,\"content_index\":0,\"delta\":\"Rome found\",\"logprobs\":[]")
				+ event("response.completed", 4, "\"response\":" + response("completed", usage(100, 30), MESSAGE))));
		ChatModel model = OpenAIChatModelConfigurationSupportService.responsesModel(
				OpenAiResponsesOptions.of(OpenAiChatOptions.builder().model("gpt-6.1-sol").apiKey("sk-test")
						.baseUrl(base).maxRetries(0).build()),
				ToolCallingManager.builder().build(), ObservationRegistry.NOOP, null);

		StringBuilder thinking = new StringBuilder();
		StringBuilder answer = new StringBuilder();
		IGReasoningExtractor.Round round = IGReasoningExtractor.PARTS.round();
		for (var chunk : model.stream(new org.springframework.ai.chat.prompt.Prompt("Where is Rome?")).collectList()
				.block()) {
			for (var generation : chunk.getResults()) {
				IGReasoningExtractor.Reasoning read = round.of(generation);
				if (!read.thinking().isEmpty()) {
					assertTrue(answer.isEmpty(), "the thinking comes before the answer");
					thinking.append(read.thinking());
				}
				answer.append(read.answer());
			}
		}

		assertEquals("Looking Rome up", thinking.toString(), "the summary pieces, as Gebo's PARTS reading takes them");
		assertTrue(answer.toString().startsWith("Rome found"), answer.toString());
		assertTrue(requests.get(0).contains("\"summary\":\"auto\""), "the summaries asked");
	}

	@Test
	void aFailedResponseIsRecordedAsAnError() {
		script(Reply.json(response("failed", usage(80, 0))));

		assertThrows(RuntimeException.class, () -> ask(client()).call().content());

		verify(recorder, timeout(5000).times(1)).record(eq(config), eq("openai"), eq(ModelType.CHAT), any(), any(),
				any(), anyLong(), any(), anyLong(), anyLong(), anyLong(), eq(LLMCallOutcome.ERROR));
		verifyRecordCount(1);
	}

	@Test
	void aRequestRepeatedWithoutTheRefusedSummariesIsRecordedOnce() {
		script(new Reply(400, "application/json",
				"{\"error\":{\"message\":\"Your organization must be verified to generate reasoning summaries.\","
						+ "\"type\":\"invalid_request_error\",\"param\":\"reasoning.summary\",\"code\":\"unsupported_value\"}}"),
				Reply.json(response("completed", usage(100, 30), MESSAGE)));

		String answer = ask(client()).call().content();

		assertEquals("Rome found", answer);
		assertEquals(2, requests.size());
		assertTrue(requests.get(0).contains("\"summary\":\"auto\""), "first asked: " + requests.get(0));
		assertFalse(requests.get(1).contains("\"summary\""), "then not: " + requests.get(1));
		verifyRecorded(100, 30, LLMCallOutcome.SUCCESS);
		verifyRecordCount(1);
	}
}
