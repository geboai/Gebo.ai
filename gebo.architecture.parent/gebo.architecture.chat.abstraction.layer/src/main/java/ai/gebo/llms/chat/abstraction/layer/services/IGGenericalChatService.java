/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.ToolCategoriesTree;
import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatUserInfo;
import reactor.core.publisher.Flux;

/**
 * AI generated comments Interface defining the general contract for a generic
 * chat service.
 */
public interface IGGenericalChatService {

	/**
	 * Represents the capabilities of a model provider.
	 */
	public static class ModelProviderCapabilities {

		/** The configuration code identifying this provider's configuration. */
		public final String configurationCode;

		/** Indicates if the model supports transcripts of chat sessions. */
		public final boolean supportsTranscript;

		/** Indicates if the model supports speech synthesis. */
		public final boolean supportsSpeech;

		/** Indicates if the model supports structured output formats. */
		public final boolean supportsStructuredOutput;

		/** Indicates if the model supports function calls. */
		public final boolean supportsFunctionCalls;

		/** The list of available tools categorized in a tree structure. */
		public final List<ToolCategoriesTree> functionsTreeList;

		/**
		 * Constructs a ModelProviderCapabilities instance with specified
		 * characteristics.
		 *
		 * @param configurationCode        The code for the configuration.
		 * @param supportsTranscript       Does the model support transcripts?
		 * @param supportsSpeech           Does the model support speech synthesis?
		 * @param supportsStructuredOutput Does the model support structured outputs?
		 * @param supportsFunctionCalls    Does the model support function calls?
		 * @param functionsTreeList        List of function categories.
		 */
		public ModelProviderCapabilities(String configurationCode, boolean supportsTranscript, boolean supportsSpeech,
				boolean supportsStructuredOutput, boolean supportsFunctionCalls,
				List<ToolCategoriesTree> functionsTreeList) {
			this.configurationCode = configurationCode;
			this.supportsFunctionCalls = supportsFunctionCalls;
			this.supportsSpeech = supportsSpeech;
			this.supportsTranscript = supportsTranscript;
			this.functionsTreeList = functionsTreeList;
			this.supportsStructuredOutput = supportsStructuredOutput;
		}

	}

	/**
	 * Streams chat messages as a reactive Flux sequence.
	 *
	 * @param request The chat request containing necessary parameters for the
	 *                interaction.
	 * @return A Flux of chat message envelopes.
	 * @throws GeboChatException        If there is an error processing the chat.
	 * @throws LLMConfigException       If there is an error with the LLM
	 *                                  configuration.
	 * @throws GeboPersistenceException
	 * @throws IOException
	 * @throws FullTextException
	 */
	public Flux<GeboChatMessageEnvelope> streamChat(GeboChatRequest request)
			throws GeboChatException, LLMConfigException, GeboPersistenceException, IOException, FullTextException;

	/**
	 * Initiates a chat interaction based on the provided request.
	 *
	 * @param request The chat request containing chat parameters.
	 * @return A response from the chat model.
	 * @throws GeboChatException        If chat processing encounters an error.
	 * @throws LLMConfigException       If there are configuration errors detected.
	 * @throws GeboPersistenceException
	 * @throws IOException
	 * @throws FullTextException
	 */
	public GeboChatResponse chat(GeboChatRequest request)
			throws GeboChatException, LLMConfigException, GeboPersistenceException, IOException, FullTextException;

	/**
	 * Retrieves user information for a specific chat model.
	 *
	 * @param modelCode The code representing the model for which info is retrieved.
	 * @return User information related to the chat model.
	 * @throws GeboChatException  If the request fails due to chat errors.
	 * @throws LLMConfigException If configuration issues prevent information
	 *                            retrieval.
	 */
	public GeboChatUserInfo getChatModelUserInfo(String modelCode) throws GeboChatException, LLMConfigException;

	public GeboChatResponse chat(GPromptTemplateConfig overriddenPrompt, LLMChatRequestResources requestResources,
			GeboChatResponse response, IGConfigurableChatModel chatModel) throws GeboChatException, LLMConfigException;

	public Flux<GeboChatMessageEnvelope> streamChat(GPromptTemplateConfig prompt,
			Map<String, Object> params, LLMChatRequestResources requestResources, GeboChatResponse response, IGConfigurableChatModel chatModel)
			throws GeboChatException, LLMConfigException;
}