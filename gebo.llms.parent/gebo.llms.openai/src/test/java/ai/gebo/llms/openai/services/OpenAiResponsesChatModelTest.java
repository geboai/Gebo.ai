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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.tool.function.FunctionToolCallback;

import com.openai.client.OpenAIClient;
import com.openai.core.ObjectMappers;
import com.openai.core.RequestOptions;
import com.openai.core.http.StreamResponse;
import com.openai.models.ReasoningEffort;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseIncludable;
import com.openai.models.responses.ResponseInputItem;
import com.openai.models.responses.ResponseStreamEvent;
import com.openai.services.blocking.ResponseService;

import io.micrometer.observation.ObservationRegistry;

/**
 * The Responses chat model, fed with the shapes of the Responses API: the models it
 * takes, the request a conversation makes, the response read back, the reasoning items
 * sent again in the next round, a streamed round.
 */
class OpenAiResponsesChatModelTest {

	static final String REASONING = "{\"type\":\"reasoning\",\"id\":\"rs_1\",\"summary\":[{\"type\":\"summary_text\",\"text\":\"Looking it up\"}],\"encrypted_content\":\"enc-1\"}";
	static final String MESSAGE = "{\"type\":\"message\",\"id\":\"msg_1\",\"role\":\"assistant\",\"status\":\"completed\",\"content\":[{\"type\":\"output_text\",\"text\":\"Let me check\",\"annotations\":[]}]}";
	static final String CALL = "{\"type\":\"function_call\",\"id\":\"fc_1\",\"call_id\":\"call_1\",\"name\":\"lookup\",\"arguments\":\"{\\\"q\\\":\\\"rome\\\"}\",\"status\":\"completed\"}";
	static final String USAGE = "{\"input_tokens\":100,\"input_tokens_details\":{\"cached_tokens\":40,\"cache_write_tokens\":0},\"output_tokens\":30,\"output_tokens_details\":{\"reasoning_tokens\":12},\"total_tokens\":130}";

	static String response(String status, String... items) {
		return "{\"id\":\"resp_1\",\"object\":\"response\",\"created_at\":1,\"model\":\"gpt-6.1-sol\",\"status\":\""
				+ status + "\",\"output\":[" + String.join(",", items) + "],\"usage\":" + USAGE
				+ ",\"parallel_tool_calls\":true,\"tool_choice\":\"auto\",\"tools\":[],\"temperature\":null,\"top_p\":null,\"error\":null,\"incomplete_details\":null,\"instructions\":null,\"metadata\":null}";
	}

	static Response parse(String json) throws Exception {
		return ObjectMappers.jsonMapper().readValue(json, Response.class);
	}

	static OpenAiChatOptions options() {
		return OpenAiChatOptions.builder().model("gpt-6.1-sol").reasoningEffort("high").maxCompletionTokens(2000)
				.parallelToolCalls(true)
				.toolCallbacks(List.of(FunctionToolCallback.builder("lookup", (String q) -> "found " + q)
						.description("Looks a place up").inputType(String.class).build()))
				.build();
	}

	static OpenAiResponsesChatModel model(OpenAIClient client) {
		return new OpenAiResponsesChatModel(client, options(), ToolCallingManager.builder().build(),
				ObservationRegistry.NOOP);
	}

	@Test
	void theModelsOfVersionSixAndLaterGoThroughTheResponsesApi() {
		for (String id : List.of("gpt-6.1-sol", "gpt-6-astra", "gpt-6-sol", "gpt-6-luna", "gpt-6", "gpt-10-x"))
			assertTrue(OpenAiResponsesChatModel.usesResponsesApi(id), id);
		for (String id : List.of("gpt-5.5", "gpt-5.6-sol", "gpt-5", "gpt-4.1-mini-2025-04-14", "gpt-4o", "o3",
				"o4-mini", "chatgpt-4o-latest"))
			assertFalse(OpenAiResponsesChatModel.usesResponsesApi(id), id);
		assertEquals(6.1, OpenAiResponsesChatModel.versionOf("gpt-6.1-sol"));
		assertEquals(4.1, OpenAiResponsesChatModel.versionOf("gpt-4.1-mini-2025-04-14"));
		assertNull(OpenAiResponsesChatModel.versionOf("davinci"));
		assertFalse(OpenAiResponsesChatModel.usesResponsesApi(null));
	}

	@Test
	void aConversationBecomesItsItemsStatelessly() {
		OpenAiResponsesChatModel model = model(mock(OpenAIClient.class));
		List<Message> messages = List.of(new SystemMessage("Be brief"), new UserMessage("Where is Rome?"),
				AssistantMessage.builder().content("")
						.toolCalls(List.of(new AssistantMessage.ToolCall("call_1", "function", "lookup", "{\"q\":\"rome\"}")))
						.build(),
				ToolResponseMessage.builder()
						.responses(List.of(new ToolResponseMessage.ToolResponse("call_1", "lookup", "Italy"))).build());
		ResponseCreateParams params = model.params(new Prompt(messages, options()), options(), true);

		assertEquals("Be brief", params.instructions().orElseThrow());
		assertFalse(params.store().orElseThrow(), "nothing kept at OpenAI");
		assertTrue(params.include().orElseThrow().contains(ResponseIncludable.REASONING_ENCRYPTED_CONTENT));
		assertEquals(ReasoningEffort.HIGH, params.reasoning().orElseThrow().effort().orElseThrow());
		assertEquals(2000L, params.maxOutputTokens().orElseThrow());
		assertEquals(1, params.tools().orElseThrow().size());
		assertTrue(params.parallelToolCalls().orElseThrow(), "only with tools");
		List<ResponseInputItem> input = params.input().orElseThrow().asResponse();
		assertEquals(3, input.size(), "the user message, the function call and its output");
		assertTrue(input.get(0).isEasyInputMessage());
		assertEquals("call_1", input.get(1).asFunctionCall().callId());
		assertEquals("Italy", input.get(2).asFunctionCallOutput().output().asString());
	}

	@Test
	void theAnswerCarriesItsToolCallsReasoningAndUsageAndItsReasoningGoesBack() throws Exception {
		OpenAiResponsesChatModel model = model(mock(OpenAIClient.class));
		ChatResponse answer = model.toChatResponse(parse(response("completed", REASONING, MESSAGE, CALL)), false, null);

		AssistantMessage output = answer.getResult().getOutput();
		assertEquals("Let me check", output.getText());
		assertEquals(List.of(new AssistantMessage.ToolCall("call_1", "function", "lookup", "{\"q\":\"rome\"}")),
				output.getToolCalls());
		assertEquals("Looking it up", output.getMetadata().get("reasoningContent"));
		assertEquals("TOOL_CALLS", answer.getResult().getMetadata().getFinishReason());
		assertEquals(100, answer.getMetadata().getUsage().getPromptTokens());
		assertEquals(30, answer.getMetadata().getUsage().getCompletionTokens());

		// the next round sends the round back as the model gave it, reasoning first
		List<ResponseInputItem> replayed = model.inputItems(output);
		assertEquals(3, replayed.size());
		assertEquals("enc-1", replayed.get(0).asReasoning().encryptedContent().orElseThrow());
		assertTrue(replayed.get(1).isResponseOutputMessage());
		assertEquals("call_1", replayed.get(2).asFunctionCall().callId());
	}

	@Test
	void aCutAnswerSaysLength() throws Exception {
		Response cut = parse(response("incomplete", MESSAGE).replace("\"incomplete_details\":null",
				"\"incomplete_details\":{\"reason\":\"max_output_tokens\"}"));
		assertEquals("LENGTH", OpenAiResponsesChatModel.finishReason(cut, List.of()));
		assertEquals("STOP", OpenAiResponsesChatModel.finishReason(parse(response("completed", MESSAGE)), List.of()));
	}

	@Test
	@SuppressWarnings("unchecked")
	void aStreamedRoundGivesItsPiecesThenItsCallsAndUsage() throws Exception {
		OpenAIClient client = mock(OpenAIClient.class);
		ResponseService responses = mock(ResponseService.class);
		StreamResponse<ResponseStreamEvent> stream = mock(StreamResponse.class);
		when(client.responses()).thenReturn(responses);
		when(responses.createStreaming(any(ResponseCreateParams.class), any(RequestOptions.class))).thenReturn(stream);
		List<ResponseStreamEvent> events = List.of(
				event("{\"type\":\"response.reasoning_summary_text.delta\",\"item_id\":\"rs_1\",\"output_index\":0,\"summary_index\":0,\"delta\":\"Looking \",\"sequence_number\":1}"),
				event("{\"type\":\"response.reasoning_summary_text.delta\",\"item_id\":\"rs_1\",\"output_index\":0,\"summary_index\":0,\"delta\":\"it up\",\"sequence_number\":2}"),
				event("{\"type\":\"response.output_text.delta\",\"item_id\":\"msg_1\",\"output_index\":1,\"content_index\":0,\"delta\":\"Let me \",\"sequence_number\":3,\"logprobs\":[]}"),
				event("{\"type\":\"response.output_text.delta\",\"item_id\":\"msg_1\",\"output_index\":1,\"content_index\":0,\"delta\":\"check\",\"sequence_number\":4,\"logprobs\":[]}"),
				event("{\"type\":\"response.completed\",\"sequence_number\":5,\"response\":"
						+ response("completed", REASONING, MESSAGE, CALL) + "}"));
		when(stream.stream()).thenReturn(events.stream());

		List<ChatResponse> chunks = model(client).stream(new Prompt(List.of(new UserMessage("Where is Rome?")), options()))
				.collectList().block();

		assertEquals(5, chunks.size());
		assertEquals("Looking it up", chunks.get(1).getResult().getOutput().getMetadata().get("reasoningContent"),
				"all of the reasoning so far");
		assertEquals("Let me check", chunks.stream().map(c -> c.getResult().getOutput().getText())
				.reduce("", String::concat), "the text once, the last chunk adding none");
		ChatResponse last = chunks.get(4);
		assertEquals(1, last.getResult().getOutput().getToolCalls().size());
		assertEquals(3, ((List<?>) last.getResult().getOutput().getMetadata()
				.get(OpenAiResponsesChatModel.OUTPUT_ITEMS_METADATA)).size());
		assertEquals(130, last.getMetadata().getUsage().getTotalTokens());
	}

	static ResponseStreamEvent event(String json) throws Exception {
		return ObjectMappers.jsonMapper().readValue(json, ResponseStreamEvent.class);
	}

	@Test
	void aBlockingCallGoesThroughTheResponsesService() throws Exception {
		OpenAIClient client = mock(OpenAIClient.class);
		ResponseService responses = mock(ResponseService.class);
		when(client.responses()).thenReturn(responses);
		when(responses.create(any(ResponseCreateParams.class), any(RequestOptions.class)))
				.thenReturn(parse(response("completed", MESSAGE)));
		ChatResponse answer = model(client).call(new Prompt(List.of(new UserMessage("hi")), options()));
		assertEquals("Let me check", answer.getResult().getOutput().getText());
		assertEquals("STOP", answer.getResult().getMetadata().getFinishReason());
		assertTrue(Map.class.isInstance(answer.getResult().getOutput().getMetadata()));
	}
}
