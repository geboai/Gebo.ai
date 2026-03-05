/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.client.rest.controllers;

import java.io.IOException;
import java.util.List;

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

import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatUserInfo;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.IGGenericalChatService.ModelProviderCapabilities;
import ai.gebo.llms.chat.abstraction.layer.services.IGRagChatService;
import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;
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
	 * @throws FullTextException 
	 * @throws IOException 
	 * @throws GeboPersistenceException 
	 */
	@PostMapping(value = "streamRagResponse", produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Flux<ServerSentEvent<String>> streamRagResponse(@RequestBody GeboChatRequest request)
			throws GeboChatException, LLMConfigException, GeboPersistenceException, IOException, FullTextException {

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
	 * @throws FullTextException 
	 * @throws IOException 
	 * @throws GeboPersistenceException 
	 */
	@PostMapping(value = "ragChat", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeboChatResponse ragChat(@RequestBody GeboChatRequest request) throws GeboChatException, LLMConfigException, GeboPersistenceException, IOException, FullTextException {

		return chatService.chat(request);
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

	

	@GetMapping(value = "getVisibleKnowledgeBasesByProfileCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GBaseObject> getVisibleKnowledgeBasesByProfileCode(
			@RequestParam("profileCode") @NotNull String profileCode)
			throws GeboPersistenceException, LLMConfigException {
		List<GKnowledgeBase> visibles = chatService.getVisibleKnowledgeBasesByProfileCode(profileCode);
		return visibles.stream().map(x -> new GBaseObject(x)).toList();
	}
}