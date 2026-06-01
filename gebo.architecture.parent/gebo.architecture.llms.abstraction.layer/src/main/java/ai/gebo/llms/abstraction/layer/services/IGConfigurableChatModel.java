/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.abstraction.layer.services;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.ChatClientRequestSpec;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.converter.BeanOutputConverter;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import reactor.core.publisher.Flux;

/**
 * Gebo.ai comment agent
 * 
 * Interface for a configurable chat model that defines the methods required for
 * configuring and interacting with chat models in the system.
 *
 * @param <ModelConfig> The type of the model configuration, extending
 *                      GBaseChatModelConfig.
 */
public interface IGConfigurableChatModel<ModelConfig extends GBaseChatModelConfig>
		extends IGConfigurableModel<ModelConfig, GChatModelType> {

	/**
	 * Gets the length of the context maintained by the chat model.
	 *
	 * @return The context length.
	 */
	public int getContextLength();

	/**
	 * Checks if the chat model supports function calls.
	 *
	 * @return True if supports functions, false otherwise.
	 */
	public default boolean isSupportsFunctionsCall() {
		return false;
	}

	/**
	 * Checks if the chat model supports structured output.
	 *
	 * @return True if supports structured output, false otherwise.
	 */
	public default boolean isSupportsStructuredOutput() {
		return false;
	}

	/**
	 * Checks if the chat model supports generating transcripts.
	 *
	 * @return True if supports transcripts, false otherwise.
	 */
	public default boolean isSupportsTranscript() {
		return false;
	}

	/**
	 * Provides a model for generating transcripts.
	 * 
	 * @return An instance of IGConfigurableTranscriptModel.
	 * @throws LLMConfigException if transcript functionalities are not implemented.
	 */
	public default IGConfigurableTranscriptModel getTranscriptModel() throws LLMConfigException {
		throw new LLMConfigException("This provider does not implement transcript functionalities");
	}

	/**
	 * Checks if the chat model supports speech output.
	 *
	 * @return True if supports speech, false otherwise.
	 */
	public default boolean isSupportsSpeech() {
		return false;
	}

	/**
	 * Provides a model for text-to-speech conversion.
	 * 
	 * @return An instance of IGConfigurableTextToSpeechModel.
	 * @throws LLMConfigException if speech functionalities are not implemented.
	 */
	public default IGConfigurableTextToSpeechModel getSpeechModel() throws LLMConfigException {
		throw new LLMConfigException("This provider does not implement speech functionalities");
	}

	/*********************************************************************************
	 * Adaptes streaming response to specific infrastructure element calling
	 * requirement
	 * 
	 * @param promptTemplate
	 * @param params         TODO
	 * @param chatContext
	 * @return
	 * @throws LLMConfigException
	 */
	public Flux<ChatResponse> streamResponse(GPromptTemplateConfig promptTemplate, Map<String, Object> params,
			IChatRequestContext chatContext) throws LLMConfigException;

	public Flux<String> streamStringResponse(GPromptTemplateConfig promptTemplate, Map<String, Object> params,
			IChatRequestContext chatContext) throws LLMConfigException;

	/***********************************************************************************************
	 * Adaptes response to specific infrastructure element calling requirement
	 * 
	 * @param promptTemplate
	 * @param params         TODO
	 * @param chatContext
	 * @return
	 * @throws LLMConfigException
	 */
	public ChatResponse response(GPromptTemplateConfig promptTemplate, Map<String, Object> params,
			IChatRequestContext chatContext) throws LLMConfigException;

	public String textResponse(GPromptTemplateConfig promptTemplate, Map<String, Object> params,
			IChatRequestContext chatContext) throws LLMConfigException;

	public <ResponseType> ResponseType structuredResponse(GPromptTemplateConfig promptTemplate,
			Map<String, Object> params, IChatRequestContext chatContext, Class<ResponseType> rt)
			throws LLMConfigException;

	public <ResponseType> ResponseType structuredResponse(GPromptTemplateConfig promptTemplate,
			Map<String, Object> params, IChatRequestContext chatContext, Class<ResponseType> rt,
			BeanOutputConverter<ResponseType> outputConverter) throws LLMConfigException;

	public default boolean isApplyThinkingMarkupHandling() {
		return false;
	}

	public default <T> BeanOutputConverter<T> createConverter(Class<T> type) {
		return new BeanOutputConverter<T>(type);
	}

	@AllArgsConstructor
	@Getter
	public static class RequestSpec {
		private final ChatClientRequestSpec requestSpec;
		private final long tokensCount;
		private final String modelCode;
	}

	public RequestSpec prepareCall(GPromptTemplateConfig prompt, Map<String, Object> params,
			IChatRequestContext chatContext, ReactiveIdentityUtil runAs) throws LLMConfigException;

	@FunctionalInterface
	public static interface UseChatModel<T> {
		public T call(ChatModel client);
	}

	public <T> T doWithChatModel(UseChatModel<T> chatModelCalling) throws LLMConfigException;

	@FunctionalInterface
	public static interface UseChatClient<T> {
		public T call(ChatClient client);
	}

	public <T> T doWithChatClient(UseChatClient<T> chatClientCalling) throws LLMConfigException;

	public IGConfigurableChatModel<ModelConfig> cloneWithTools(List<String> toolsName, String codePrefix) throws LLMConfigException;
}