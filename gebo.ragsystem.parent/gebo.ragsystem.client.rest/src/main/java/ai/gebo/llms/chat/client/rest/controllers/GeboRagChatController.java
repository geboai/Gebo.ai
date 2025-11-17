/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.client.rest.controllers;

import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatUserInfo;
import ai.gebo.llms.chat.abstraction.layer.model.GeboTemplatedChatResponse;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatContextRepository.GUserChatInfo;
import ai.gebo.llms.chat.abstraction.layer.richresponse.model.RichResponse;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.IGGenericalChatService.ModelProviderCapabilities;
import jakarta.validation.constraints.NotNull;
import ai.gebo.llms.chat.abstraction.layer.services.IGRagChatService;
import reactor.core.publisher.Flux;

/**
 * AI generated comments
 * 
 * REST controller for handling RAG (Retrieval-Augmented Generation)
 * chat-related operations in the Gebo chat system. Provides endpoints for
 * streaming responses, retrieving chat profiles, and handling various chat
 * interactions.
 */
@RestController
@RequestMapping(path = "api/users/GeboChatController")
public class GeboRagChatController {
	/** Logger for this class */
	final static Logger LOGGER = LoggerFactory.getLogger(GeboRagChatController.class);

	/** Service for handling RAG chat operations */
	@Autowired
	IGRagChatService chatService;

	/**
	 * Default constructor for the controller
	 */
	public GeboRagChatController() {

	}

	/**
	 * Endpoint for streaming RAG-based chat responses
	 * 
	 * @param request The chat request containing messages and parameters
	 * @return A flux of server-sent events containing the streaming response
	 * @throws GeboChatException  If there's an error processing the chat
	 * @throws LLMConfigException If there's a configuration error with the LLM
	 */
	@PostMapping(value = "streamRagResponse", produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Flux<ServerSentEvent<String>> streamRagResponse(@RequestBody GeboChatRequest request)
			throws GeboChatException, LLMConfigException {

		return chatService.streamChat(request).map(StreamUtil.mappingFunction)
				.map(sequence -> ServerSentEvent.<String>builder().data(sequence).build());
	}

	/**
	 * Retrieves metadata about the chat profile model
	 * 
	 * @param chatProfileCode The code identifying the chat profile
	 * @return Model metadata information for the specified profile
	 */
	@GetMapping(value = "getChatProfileModelMetaInfos", produces = MediaType.APPLICATION_JSON_VALUE)
	public GBaseChatModelChoice getChatProfileModelMetaInfos(@RequestParam("chatProfileCode") String chatProfileCode) {
		return chatService.getChatProfileModelMetaInfos(chatProfileCode);
	}

	/**
	 * Gets the capabilities of the provider model for a specific chat profile
	 * 
	 * @param chatProfileCode The code identifying the chat profile
	 * @return The capabilities of the provider model
	 * @throws LLMConfigException If there's a configuration error
	 */
	@GetMapping(value = "getProfileProviderModelCapabilities", produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelProviderCapabilities getProfileProviderModelCapabilities(
			@RequestParam("chatProfileCode") String chatProfileCode) throws LLMConfigException {
		return chatService.getProfileProviderModelCapabilities(chatProfileCode);
	}

	/**
	 * Retrieves all available chat profiles
	 * 
	 * @return A list of chat profile configurations
	 */
	@GetMapping("profiles")
	public List<GChatProfileConfiguration> getChatProfiles() {

		return chatService.getChatProfiles();
	}

	/**
	 * Endpoint for RAG-based chat interactions
	 * 
	 * @param request The chat request containing messages and parameters
	 * @return The chat response from the LLM with RAG augmentation
	 * @throws GeboChatException  If there's an error processing the chat
	 * @throws LLMConfigException If there's a configuration error with the LLM
	 */
	@PostMapping(value = "ragChat", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeboChatResponse ragChat(@RequestBody GeboChatRequest request) throws GeboChatException, LLMConfigException {

		return chatService.chat(request);
	}

	/**
	 * Endpoint for rich RAG-based chat interactions that returns a templated
	 * response
	 * 
	 * @param request The chat request containing messages and parameters
	 * @return A templated chat response with rich formatted content
	 * @throws GeboChatException  If there's an error processing the chat
	 * @throws LLMConfigException If there's a configuration error with the LLM
	 */
	@PostMapping(value = "richRagChat", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeboTemplatedChatResponse<RichResponse> richRagChat(@RequestBody GeboChatRequest request)
			throws GeboChatException, LLMConfigException {
		return chatService.templatedChat(request, RichResponse.class);
	}

	/**
	 * Retrieves user info for a specific chat profile
	 * 
	 * @param chatProfileCode The code identifying the chat profile
	 * @return User information associated with the chat profile
	 * @throws GeboChatException        If there's an error processing the request
	 * @throws LLMConfigException       If there's a configuration error with the
	 *                                  LLM
	 * @throws GeboPersistenceException If there's an error accessing persistent
	 *                                  data
	 */
	@GetMapping(value = "getChatModelUserInfoByChatProfileCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GeboChatUserInfo getChatModelUserInfoByChatProfileCode(
			@RequestParam("chatProfileCode") String chatProfileCode)
			throws GeboChatException, LLMConfigException, GeboPersistenceException {
		return chatService.getChatModelUserInfoByChatProfileCode(chatProfileCode);
	}

	@GetMapping(value = "suggestRagChatDescription", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public GUserChatInfo suggestRagChatDescription(@RequestParam("id") @NotNull String id)
			throws GeboChatException, LLMConfigException {
		return chatService.suggestChatDescription(id);
	}
}