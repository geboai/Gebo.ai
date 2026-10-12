/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.DefaultUsage;
import org.springframework.ai.chat.metadata.EmptyUsage;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.observation.ChatModelObservationContext;
import org.springframework.ai.chat.observation.ChatModelObservationConvention;
import org.springframework.ai.chat.observation.ChatModelObservationDocumentation;
import org.springframework.ai.chat.observation.DefaultChatModelObservationConvention;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.observation.conventions.AiProvider;
import org.springframework.ai.openai.OpenAiChatModel.ResponseFormat;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.tool.definition.ToolDefinition;

import com.fasterxml.jackson.core.type.TypeReference;
import com.openai.client.OpenAIClient;
import com.openai.core.JsonValue;
import com.openai.core.ObjectMappers;
import com.openai.core.RequestOptions;
import com.openai.core.http.StreamResponse;
import com.openai.errors.BadRequestException;
import com.openai.models.Reasoning;
import com.openai.models.ReasoningEffort;
import com.openai.models.responses.EasyInputMessage;
import com.openai.models.responses.FunctionTool;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseFormatTextConfig;
import com.openai.models.responses.ResponseFormatTextJsonSchemaConfig;
import com.openai.models.responses.ResponseFunctionToolCall;
import com.openai.models.responses.ResponseIncludable;
import com.openai.models.responses.ResponseInputContent;
import com.openai.models.responses.ResponseInputImage;
import com.openai.models.responses.ResponseInputItem;
import com.openai.models.responses.ResponseInputText;
import com.openai.models.responses.ResponseOutputItem;
import com.openai.models.responses.ResponseReasoningItem;
import com.openai.models.responses.ResponseStreamEvent;
import com.openai.models.responses.ResponseTextConfig;
import com.openai.models.responses.ResponseUsage;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * A chat model on OpenAI's Responses API, for the models whose tool calls the Chat
 * Completions API does not carry: OpenAI's reasoning guide
 * (developers.openai.com/api/docs/guides/reasoning, 2026-10-11) says "Chat Completions
 * does not support function calling with GPT-6 Astra or GPT-6.1 Sol", and the GPT-6 Sol
 * and Luna pages "Chat Completions supports function calling only with reasoning_effort
 * set to none". The OpenAI handler gives it the models of version 6 and later
 * ({@link #usesResponsesApi(String)}), the others keep Spring AI's Chat Completions model.
 * <p>
 * Like the Chat Completions model it takes {@link OpenAiChatOptions} and never runs the
 * tools itself: it answers with the tool calls, the ChatClient's tool calling advisor runs
 * them and calls again with their results. Nothing is kept at OpenAI ({@code store=false}):
 * the whole conversation is sent each round, and the reasoning items of a round (with their
 * encrypted content) travel back in the assistant message's metadata
 * ({@link #OUTPUT_ITEMS_METADATA}), as the guide asks: "pass back all reasoning items,
 * function call items, and function call output items, since the last user message". The
 * reasoning summaries are kept where Gebo reads the reasoning of the OpenAI models
 * ({@code reasoningContent}).
 */
public class OpenAiResponsesChatModel implements ChatModel {
	/** The metadata the output items of a round travel back in, as json. */
	public static final String OUTPUT_ITEMS_METADATA = "openai.responses.output_items";
	/** Where Gebo reads the reasoning of the OpenAI models (IGReasoningExtractor.OPENAI). */
	static final String REASONING_CONTENT_METADATA = "reasoningContent";
	/** The first version of the models on this api. */
	static final double FIRST_RESPONSES_VERSION = 6.0;
	private static final Pattern VERSION = Pattern.compile("\\d+(?:\\.\\d+)*");
	private static final ChatModelObservationConvention DEFAULT_OBSERVATION_CONVENTION = new DefaultChatModelObservationConvention();
	private static final TypeReference<Map<String, Object>> MAP = new TypeReference<>() {
	};

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private final OpenAIClient client;
	private final OpenAiChatOptions options;
	private final ToolCallingManager toolCallingManager;
	private final ObservationRegistry observationRegistry;
	/**
	 * Reasoning summaries "may require organization verification first" (the reasoning
	 * guide): once an organization is refused them, they are no longer asked.
	 */
	private final AtomicBoolean summariesRefused = new AtomicBoolean(false);

	public OpenAiResponsesChatModel(OpenAIClient client, OpenAiChatOptions options, ToolCallingManager toolCallingManager,
			ObservationRegistry observationRegistry) {
		this.client = client;
		this.options = options;
		this.toolCallingManager = toolCallingManager;
		this.observationRegistry = observationRegistry != null ? observationRegistry : ObservationRegistry.NOOP;
	}

	/**
	 * Whether a model goes through the Responses API: the first run of digits and dots
	 * of its id read as a number ({@code gpt-6.1-sol} 6.1, {@code gpt-5.5} 5.5,
	 * {@code gpt-4o} 4, {@code o3} 3) is 6 or more.
	 */
	public static boolean usesResponsesApi(String modelId) {
		final Double version = versionOf(modelId);
		return version != null && version >= FIRST_RESPONSES_VERSION;
	}

	/** The version a model id carries, its first digits and dots; null when it has none. */
	static Double versionOf(String modelId) {
		if (modelId == null)
			return null;
		final Matcher matcher = VERSION.matcher(modelId);
		if (!matcher.find())
			return null;
		final String[] parts = matcher.group().split("\\.");
		try {
			return Double.valueOf(parts.length > 1 ? parts[0] + "." + parts[1] : parts[0]);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	public ChatOptions getOptions() {
		return options;
	}

	@Override
	@Deprecated
	@SuppressWarnings("removal")
	public ChatOptions getDefaultOptions() {
		return options;
	}

	@Override
	public ChatResponse call(Prompt prompt) {
		final Prompt request = requestPrompt(prompt);
		final OpenAiChatOptions requestOptions = (OpenAiChatOptions) request.getOptions();
		final ChatModelObservationContext observationContext = ChatModelObservationContext.builder().prompt(request)
				.provider(AiProvider.OPENAI.value()).build();
		return ChatModelObservationDocumentation.CHAT_MODEL_OPERATION
				.observation(DEFAULT_OBSERVATION_CONVENTION, DEFAULT_OBSERVATION_CONVENTION, () -> observationContext, observationRegistry)
				.observe(() -> {
					final Response response = withSummariesFallback(
							summaries -> client.responses().create(params(request, requestOptions, summaries),
									requestOptionsOf(requestOptions)));
					final ChatResponse chatResponse = toChatResponse(response, false, null);
					observationContext.setResponse(chatResponse);
					return chatResponse;
				});
	}

	@Override
	public Flux<ChatResponse> stream(Prompt prompt) {
		final Prompt request = requestPrompt(prompt);
		final OpenAiChatOptions requestOptions = (OpenAiChatOptions) request.getOptions();
		return Flux.defer(() -> {
			final ChatModelObservationContext observationContext = ChatModelObservationContext.builder().prompt(request)
					.provider(AiProvider.OPENAI.value()).streaming(true).build();
			final Observation observation = ChatModelObservationDocumentation.CHAT_MODEL_OPERATION.observation(DEFAULT_OBSERVATION_CONVENTION,
					DEFAULT_OBSERVATION_CONVENTION, () -> observationContext, observationRegistry);
			observation.start();
			final StringBuilder reasoning = new StringBuilder();
			final StreamResponse<ResponseStreamEvent> events = withSummariesFallback(
					summaries -> client.responses().createStreaming(params(request, requestOptions, summaries),
							requestOptionsOf(requestOptions)));
			return Flux.fromStream(events.stream()).<ChatResponse>handle((event, sink) -> {
				if (event.outputTextDelta().isPresent()) {
					sink.next(chunk(event.outputTextDelta().get().delta(), Map.of()));
				} else if (event.reasoningSummaryTextDelta().isPresent()) {
					reasoning.append(event.reasoningSummaryTextDelta().get().delta());
					// all of it so far, as Gebo's OpenAI reading takes it
					sink.next(chunk("", Map.of(REASONING_CONTENT_METADATA, reasoning.toString())));
				} else if (event.completed().isPresent()) {
					final ChatResponse last = toChatResponse(event.completed().get().response(), true, reasoning.toString());
					observationContext.setResponse(last);
					sink.next(last);
				} else if (event.incomplete().isPresent()) {
					final ChatResponse last = toChatResponse(event.incomplete().get().response(), true, reasoning.toString());
					observationContext.setResponse(last);
					sink.next(last);
				} else if (event.failed().isPresent()) {
					final Response failed = event.failed().get().response();
					sink.error(new IllegalStateException("OpenAI response " + failed.id() + " failed: "
							+ failed.error().map(e -> e.code() + " " + e.message()).orElse("no detail")));
				} else if (event.error().isPresent()) {
					sink.error(new IllegalStateException("OpenAI streaming error " + event.error().get().code().orElse("")
							+ ": " + event.error().get().message()));
				}
			}).doOnError(observation::error).doFinally(signal -> {
				events.close();
				observation.stop();
			});
		}).subscribeOn(Schedulers.boundedElastic());
	}

	/** The prompt with the model's options when it carries none, as Spring AI's model does. */
	private Prompt requestPrompt(Prompt prompt) {
		if (prompt.getOptions() == null) {
			return prompt.mutate().chatOptions(this.options).build();
		}
		if (!(prompt.getOptions() instanceof OpenAiChatOptions)) {
			throw new IllegalArgumentException(
					"Prompt options must be OpenAiChatOptions, not " + prompt.getOptions().getClass().getName());
		}
		return prompt;
	}

	private RequestOptions requestOptionsOf(OpenAiChatOptions requestOptions) {
		final RequestOptions.Builder builder = RequestOptions.builder();
		if (requestOptions.getTimeout() != null) {
			builder.timeout(requestOptions.getTimeout());
		}
		return builder.build();
	}

	/**
	 * Runs a request asking for reasoning summaries, and again without them when the
	 * organization is refused them (then never asking them again).
	 */
	private <T> T withSummariesFallback(java.util.function.Function<Boolean, T> request) {
		final boolean summaries = !summariesRefused.get();
		try {
			return request.apply(summaries);
		} catch (BadRequestException e) {
			final String message = String.valueOf(e.getMessage()).toLowerCase();
			if (summaries && (message.contains("summary") || message.contains("verif"))) {
				summariesRefused.set(true);
				LOGGER.warn("OpenAI refused the reasoning summaries ({}): the requests no longer ask them",
						e.getMessage());
				return request.apply(false);
			}
			throw e;
		}
	}

	/** The Responses request of a prompt. */
	ResponseCreateParams params(Prompt prompt, OpenAiChatOptions requestOptions, boolean summaries) {
		final ResponseCreateParams.Builder builder = ResponseCreateParams.builder().model(requestOptions.getModel())
				.store(false).addInclude(ResponseIncludable.REASONING_ENCRYPTED_CONTENT);
		final List<String> instructions = new ArrayList<>();
		final List<ResponseInputItem> input = new ArrayList<>();
		for (Message message : prompt.getInstructions()) {
			if (message.getMessageType() == MessageType.SYSTEM) {
				if (message.getText() != null && !message.getText().isBlank())
					instructions.add(message.getText());
			} else {
				input.addAll(inputItems(message));
			}
		}
		if (!instructions.isEmpty()) {
			builder.instructions(String.join("\n\n", instructions));
		}
		builder.input(ResponseCreateParams.Input.ofResponse(input));
		if (requestOptions.getMaxCompletionTokens() != null) {
			builder.maxOutputTokens(requestOptions.getMaxCompletionTokens().longValue());
		} else if (requestOptions.getMaxTokens() != null) {
			builder.maxOutputTokens(requestOptions.getMaxTokens().longValue());
		}
		if (requestOptions.getTemperature() != null) {
			builder.temperature(requestOptions.getTemperature());
		}
		if (requestOptions.getTopP() != null) {
			builder.topP(requestOptions.getTopP());
		}
		if (requestOptions.getUser() != null) {
			builder.user(requestOptions.getUser());
		}
		final Reasoning.Builder reasoning = Reasoning.builder();
		boolean anyReasoning = false;
		if (requestOptions.getReasoningEffort() != null) {
			reasoning.effort(ReasoningEffort.of(requestOptions.getReasoningEffort().toLowerCase()));
			anyReasoning = true;
		}
		if (summaries) {
			reasoning.summary(Reasoning.Summary.AUTO);
			anyReasoning = true;
		}
		if (anyReasoning) {
			builder.reasoning(reasoning.build());
		}
		final List<ToolDefinition> tools = toolCallingManager.resolveToolDefinitions(requestOptions);
		if (tools != null && !tools.isEmpty()) {
			tools.forEach(tool -> builder.addTool(functionTool(tool, Boolean.TRUE.equals(requestOptions.getStrict()))));
			// only with tools: the parameter without tools is a 400 on OpenAI
			if (requestOptions.getParallelToolCalls() != null) {
				builder.parallelToolCalls(requestOptions.getParallelToolCalls());
			}
		}
		final ResponseTextConfig text = textConfig(requestOptions.getResponseFormat());
		if (text != null) {
			builder.text(text);
		}
		final ResponseCreateParams params = builder.build();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Responses request model={} input items={} tools={} effort={} summaries={}",
					requestOptions.getModel(), input.size(), tools != null ? tools.size() : 0,
					requestOptions.getReasoningEffort(), summaries);
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Responses request: {}", params);
		}
		return params;
	}

	/** The input items of a message of the conversation. */
	List<ResponseInputItem> inputItems(Message message) {
		final List<ResponseInputItem> items = new ArrayList<>();
		switch (message.getMessageType()) {
		case USER: {
			items.add(ResponseInputItem.ofEasyInputMessage(userMessage(message)));
		}
			break;
		case ASSISTANT: {
			final AssistantMessage assistant = (AssistantMessage) message;
			final List<ResponseInputItem> replayed = replayedOutputItems(assistant);
			if (!replayed.isEmpty()) {
				// the round as the model gave it: reasoning, message and function calls
				items.addAll(replayed);
			} else {
				if (assistant.getText() != null && !assistant.getText().isEmpty()) {
					items.add(ResponseInputItem.ofEasyInputMessage(EasyInputMessage.builder()
							.role(EasyInputMessage.Role.ASSISTANT).content(assistant.getText()).build()));
				}
				for (AssistantMessage.ToolCall call : assistant.getToolCalls()) {
					items.add(ResponseInputItem.ofFunctionCall(ResponseFunctionToolCall.builder().callId(call.id())
							.name(call.name()).arguments(call.arguments()).build()));
				}
			}
		}
			break;
		case TOOL: {
			for (ToolResponseMessage.ToolResponse response : ((ToolResponseMessage) message).getResponses()) {
				items.add(ResponseInputItem.ofFunctionCallOutput(ResponseInputItem.FunctionCallOutput.builder()
						.callId(response.id()).output(response.responseData() != null ? response.responseData() : "")
						.build()));
			}
		}
			break;
		default:
			throw new IllegalArgumentException("Unsupported message type: " + message.getMessageType());
		}
		return items;
	}

	/** A user message, with its images. */
	private EasyInputMessage userMessage(Message message) {
		final EasyInputMessage.Builder builder = EasyInputMessage.builder().role(EasyInputMessage.Role.USER);
		final String text = message.getText() != null ? message.getText() : "";
		if (message instanceof UserMessage user && user.getMedia() != null && !user.getMedia().isEmpty()) {
			final List<ResponseInputContent> parts = new ArrayList<>();
			if (!text.isEmpty()) {
				parts.add(ResponseInputContent.ofInputText(ResponseInputText.builder().text(text).build()));
			}
			user.getMedia().forEach(media -> {
				final String mimeType = media.getMimeType().toString();
				final String url = media.getData() instanceof byte[] bytes
						? "data:" + mimeType + ";base64," + java.util.Base64.getEncoder().encodeToString(bytes)
						: String.valueOf(media.getData());
				if (mimeType.startsWith("image/")) {
					parts.add(ResponseInputContent.ofInputImage(
							ResponseInputImage.builder().imageUrl(url).detail(ResponseInputImage.Detail.AUTO).build()));
				} else {
					LOGGER.warn("Media of type {} is not sent to the Responses api", mimeType);
				}
			});
			builder.content(EasyInputMessage.Content.ofResponseInputMessageContentList(parts));
		} else {
			builder.content(text);
		}
		return builder.build();
	}

	/** The output items a previous round kept in its message, as input items. */
	@SuppressWarnings("unchecked")
	private List<ResponseInputItem> replayedOutputItems(AssistantMessage assistant) {
		final List<ResponseInputItem> items = new ArrayList<>();
		final Object kept = assistant.getMetadata() != null ? assistant.getMetadata().get(OUTPUT_ITEMS_METADATA) : null;
		if (!(kept instanceof List<?> list))
			return items;
		for (Object json : list) {
			try {
				final ResponseOutputItem item = ObjectMappers.jsonMapper().readValue(String.valueOf(json),
						ResponseOutputItem.class);
				if (item.reasoning().isPresent()) {
					items.add(ResponseInputItem.ofReasoning(item.reasoning().get()));
				} else if (item.message().isPresent()) {
					items.add(ResponseInputItem.ofResponseOutputMessage(item.message().get()));
				} else if (item.functionCall().isPresent()) {
					items.add(ResponseInputItem.ofFunctionCall(item.functionCall().get()));
				}
			} catch (Exception e) {
				LOGGER.error("Cannot read back an output item of a previous round, it is not sent again", e);
			}
		}
		return items;
	}

	/** A Gebo tool as a Responses function tool, its parameters its input schema. */
	FunctionTool functionTool(ToolDefinition tool, boolean strict) {
		final FunctionTool.Parameters.Builder parameters = FunctionTool.Parameters.builder();
		if (tool.inputSchema() != null && !tool.inputSchema().isBlank()) {
			try {
				ObjectMappers.jsonMapper().readValue(tool.inputSchema(), MAP)
						.forEach((key, value) -> parameters.putAdditionalProperty(key, JsonValue.from(value)));
			} catch (Exception e) {
				throw new IllegalArgumentException("The input schema of tool " + tool.name() + " is not json", e);
			}
		}
		final FunctionTool.Builder builder = FunctionTool.builder().name(tool.name()).parameters(parameters.build())
				.strict(strict);
		if (tool.description() != null) {
			builder.description(tool.description());
		}
		return builder.build();
	}

	/** The text format a response format asks; null for plain text. */
	ResponseTextConfig textConfig(ResponseFormat format) {
		if (format == null || format.getType() == null || format.getType() == ResponseFormat.Type.TEXT)
			return null;
		if (format.getType() == ResponseFormat.Type.JSON_OBJECT) {
			return ResponseTextConfig.builder()
					.format(ResponseFormatTextConfig
							.ofJsonObject(com.openai.models.ResponseFormatJsonObject.builder().build()))
					.build();
		}
		final ResponseFormatTextJsonSchemaConfig.Schema.Builder schema = ResponseFormatTextJsonSchemaConfig.Schema
				.builder();
		try {
			ObjectMappers.jsonMapper().readValue(format.getJsonSchema() != null ? format.getJsonSchema() : "{}", MAP)
					.forEach((key, value) -> schema.putAdditionalProperty(key, JsonValue.from(value)));
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to parse JSON schema: " + format.getJsonSchema(), e);
		}
		return ResponseTextConfig.builder().format(ResponseFormatTextJsonSchemaConfig.builder().name("json_schema")
				.schema(schema.build()).strict(format.getStrict() != null ? format.getStrict() : true).build()).build();
	}

	/** A streamed piece: text and metadata, nothing else. */
	private ChatResponse chunk(String text, Map<String, Object> metadata) {
		return new ChatResponse(List.of(new Generation(
				AssistantMessage.builder().content(text).properties(new LinkedHashMap<>(metadata)).build())));
	}

	/**
	 * A response as Spring AI's: the answer text (left out when it was streamed already),
	 * the tool calls, the reasoning summaries, the output items to send back, the finish
	 * reason and the usage.
	 */
	ChatResponse toChatResponse(Response response, boolean streamed, String streamedReasoning) {
		final StringBuilder text = new StringBuilder();
		final StringBuilder reasoning = new StringBuilder();
		final List<AssistantMessage.ToolCall> toolCalls = new ArrayList<>();
		final List<String> outputItems = new ArrayList<>();
		for (ResponseOutputItem item : response.output()) {
			if (item.message().isPresent()) {
				item.message().get().content().forEach(content -> content.outputText()
						.ifPresent(outputText -> text.append(outputText.text())));
				outputItems.add(json(item));
			} else if (item.functionCall().isPresent()) {
				final ResponseFunctionToolCall call = item.functionCall().get();
				toolCalls.add(new AssistantMessage.ToolCall(call.callId(), "function", call.name(), call.arguments()));
				outputItems.add(json(item));
			} else if (item.reasoning().isPresent()) {
				final ResponseReasoningItem reasoningItem = item.reasoning().get();
				reasoningItem.summary().forEach(summary -> {
					if (reasoning.length() > 0)
						reasoning.append("\n\n");
					reasoning.append(summary.text());
				});
				outputItems.add(json(item));
			}
		}
		final Map<String, Object> metadata = new LinkedHashMap<>();
		metadata.put("id", response.id());
		final String allReasoning = streamed && streamedReasoning != null && !streamedReasoning.isEmpty()
				? streamedReasoning
				: reasoning.toString();
		metadata.put(REASONING_CONTENT_METADATA, allReasoning);
		metadata.put(OUTPUT_ITEMS_METADATA, outputItems);
		final AssistantMessage message = AssistantMessage.builder().content(streamed ? "" : text.toString())
				.properties(metadata).toolCalls(toolCalls).build();
		final Generation generation = new Generation(message,
				ChatGenerationMetadata.builder().finishReason(finishReason(response, toolCalls)).build());
		final Usage usage = response.usage().map(OpenAiResponsesChatModel::usageOf).orElse(new EmptyUsage());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Responses answer id={} status={} tool calls={} output items={} usage={}", response.id(),
					response.status().map(s -> s.asString()).orElse("?"), toolCalls.size(), outputItems.size(), usage);
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Responses answer text: {} reasoning: {}", text, allReasoning);
		}
		return new ChatResponse(List.of(generation), ChatResponseMetadata.builder().id(response.id())
				.model(options.getModel()).usage(usage).build());
	}

	/** The finish reason in Spring AI's OpenAI spelling. */
	static String finishReason(Response response, List<AssistantMessage.ToolCall> toolCalls) {
		if (!toolCalls.isEmpty())
			return "TOOL_CALLS";
		if (response.incompleteDetails().flatMap(details -> details.reason()).isPresent()) {
			final Response.IncompleteDetails.Reason reason = response.incompleteDetails().get().reason().get();
			if (Response.IncompleteDetails.Reason.MAX_OUTPUT_TOKENS.equals(reason))
				return "LENGTH";
			if (Response.IncompleteDetails.Reason.CONTENT_FILTER.equals(reason))
				return "CONTENT_FILTER";
		}
		return "STOP";
	}

	/** The usage, the cached input tokens as the cache reads. */
	static Usage usageOf(ResponseUsage usage) {
		return new DefaultUsage(Math.toIntExact(usage.inputTokens()), Math.toIntExact(usage.outputTokens()),
				Math.toIntExact(usage.totalTokens()), usage, usage.inputTokensDetails().cachedTokens(), null);
	}

	private String json(ResponseOutputItem item) {
		try {
			return ObjectMappers.jsonMapper().writeValueAsString(item);
		} catch (Exception e) {
			throw new IllegalStateException("Cannot keep an output item of the response", e);
		}
	}
}
