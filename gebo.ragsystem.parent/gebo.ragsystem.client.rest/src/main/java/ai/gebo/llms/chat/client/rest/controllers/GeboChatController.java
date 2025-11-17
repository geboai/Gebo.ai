/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.client.rest.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatUserInfo;
import ai.gebo.llms.chat.abstraction.layer.model.GeboTemplatedChatResponse;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatContextRepository.GUserChatInfo;
import ai.gebo.llms.chat.abstraction.layer.richresponse.model.RichResponse;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatService;
import ai.gebo.llms.chat.abstraction.layer.services.IGGenericalChatService.ModelProviderCapabilities;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Flux;

/**
 * REST controller that exposes endpoints for chat-related functionality. AI
 * generated comments This controller provides APIs for interacting with LLM
 * models including chat responses, streaming, speech-to-text, and
 * text-to-speech conversions.
 */
@RestController
@RequestMapping(path = "api/users/GeboDirectModelChatController")
public class GeboChatController {

	/** Logger for this class */
	static Logger LOGGER = LoggerFactory.getLogger(GeboChatController.class);

	/** Service that handles chat functionality */
	@Autowired
	IGChatService chatService;

	/** JSON mapper for serialization/deserialization */
	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * Default constructor
	 */
	public GeboChatController() {

	}

	/**
	 * Retrieves meta information about a specific chat model
	 * 
	 * @param modelCode The code identifying the model
	 * @return Meta information about the requested model
	 */
	@GetMapping(value = "getChatModelMetaInfos", produces = MediaType.APPLICATION_JSON_VALUE)
	public GBaseChatModelChoice getChatModelMetaInfos(@RequestParam("modelCode") String modelCode) {
		return chatService.getChatModelMetaInfo(modelCode);
	}

	/**
	 * Endpoint for standard chat interaction with an LLM
	 * 
	 * @param request The chat request containing messages and parameters
	 * @return A response from the chat model
	 * @throws GeboChatException  If there's a problem with the chat service
	 * @throws LLMConfigException If there's a configuration issue with the model
	 */
	@PostMapping(value = "chat", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeboChatResponse chat(@RequestBody GeboChatRequest request) throws GeboChatException, LLMConfigException {

		return chatService.chat(request);

	}

	/**
	 * Endpoint for rich chat interaction that returns templated responses
	 * 
	 * @param request The chat request containing messages and parameters
	 * @return A templated response with rich content
	 * @throws GeboChatException  If there's a problem with the chat service
	 * @throws LLMConfigException If there's a configuration issue with the model
	 */
	@PostMapping(value = "richChat", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GeboTemplatedChatResponse<RichResponse> richChat(@RequestBody GeboChatRequest request)
			throws GeboChatException, LLMConfigException {
		return chatService.templatedChat(request, RichResponse.class);
	}

	/**
	 * Retrieves the capabilities of a specific model provider
	 * 
	 * @param modelCode The code identifying the model
	 * @return The capabilities of the requested model provider
	 */
	@GetMapping(value = "getProviderCapabilities", produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelProviderCapabilities getProviderCapabilities(@RequestParam("modelCode") String modelCode) {
		return chatService.getProviderCapabilities(modelCode);
	}

	/**
	 * Simple response class for transcript text
	 */
	public static class TranscriptResponse {
		public TranscriptResponse(String s) {
			this.text = s;
		}

		public final String text;
	}

	/**
	 * Transcribes audio input to text
	 * 
	 * @param request   The HTTP request containing the audio stream
	 * @param modelCode The code identifying the model to use for transcription
	 * @return A response containing the transcribed text
	 * @throws LLMConfigException If there's a configuration issue with the model
	 * @throws IOException        If there's an issue reading the input stream
	 */
	@PostMapping(value = "transcriptText", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public TranscriptResponse transcriptText(HttpServletRequest request, @RequestParam("modelCode") String modelCode)
			throws LLMConfigException, IOException {
		InputStream is = request.getInputStream();

		return new TranscriptResponse(chatService.transcript(is, modelCode));
	}

	/**
	 * Request class for text-to-speech conversion
	 */
	public static class SpeechRequest {
		@NotNull
		public String text = null;
	}

	/**
	 * Converts text to speech audio
	 * 
	 * @param sr        The request containing the text to convert
	 * @param modelCode The code identifying the model to use for speech synthesis
	 * @return An input stream resource containing the audio data
	 * @throws LLMConfigException If there's a configuration issue with the model
	 * @throws IOException        If there's an issue with the input/output
	 *                            operations
	 */
	@PostMapping(value = "speechText", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public InputStreamResource speechText(@RequestBody @Valid SpeechRequest sr,
			@RequestParam("modelCode") String modelCode) throws LLMConfigException, IOException {
		InputStream is = chatService.speech(sr.text, modelCode);
		InputStreamResource resource = new InputStreamResource(is);
		return resource;
	}

	/**
	 * Retrieves user information related to a specific chat model
	 * 
	 * @param modelCode The code identifying the model
	 * @return User information for the requested model
	 * @throws GeboChatException  If there's a problem with the chat service
	 * @throws LLMConfigException If there's a configuration issue with the model
	 */
	@GetMapping(value = "getChatModelUserInfo", produces = MediaType.APPLICATION_JSON_VALUE)
	public GeboChatUserInfo getChatModelUserInfo(@RequestParam("modelCode") String modelCode)
			throws GeboChatException, LLMConfigException {
		return chatService.getChatModelUserInfo(modelCode);
	}

	/**
	 * Streams a chat response using Server-Sent Events
	 * 
	 * @param request The chat request containing messages and parameters
	 * @return A flux of server-sent events containing chat response chunks
	 * @throws GeboChatException  If there's a problem with the chat service
	 * @throws LLMConfigException If there's a configuration issue with the model
	 */
	@PostMapping(value = "streamResponse", produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Flux<ServerSentEvent<String>> streamResponse(@RequestBody GeboChatRequest request)
			throws GeboChatException, LLMConfigException {
		return chatService.streamChat(request).map(StreamUtil.mappingFunction)
				.map(sequence -> ServerSentEvent.<String>builder().data(sequence).build());
	}

	@GetMapping(value = "suggestChatDescription", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public GUserChatInfo suggestChatDescription(@RequestParam("id") @NotNull String id) throws GeboChatException, LLMConfigException {
		return chatService.suggestChatDescription(id);
	}
}