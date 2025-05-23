/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.client.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptConfigDao;
import ai.gebo.model.base.GObjectRef;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * 
 * REST controller for managing prompt templates.
 * This controller provides endpoints to retrieve default prompts based on various parameters.
 * Access is restricted to users with the ADMIN role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/PromptTemplatesController")
public class PromptTemplatesController {
	/** Data access object for prompt configurations */
	@Autowired
	IGPromptConfigDao promptConfigDao;
	
	/** Manager for persistent objects */
	@Autowired
	IGPersistentObjectManager persistentObjectManager;

	/**
	 * Default constructor
	 */
	public PromptTemplatesController() {

	}

	/**
	 * Parameter class for getting default prompts based on a chat model configuration
	 */
	public static class DefaultPromptForChatModelParam {
		/** The chat model configuration to get a default prompt for */
		public @NotNull GBaseChatModelConfig chatModelConfig = null;
		
		/** Flag indicating whether this is for a RAG prompt */
		public @NotNull Boolean ragPrompt = null;
	}

	/**
	 * Retrieves the default prompt for a specific chat model configuration
	 * 
	 * @param param Object containing the chat model config and RAG prompt flag
	 * @return The default prompt configuration
	 */
	@PostMapping(value = "getDefaultPromptForChatModel", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GPromptConfig getDefaultPromptForChatModel(@RequestBody DefaultPromptForChatModelParam param) {
		return promptConfigDao.defaultPrompt(param.chatModelConfig, param.ragPrompt);
	}

	/**
	 * Parameter class for getting default prompts based on a chat model reference
	 */
	public static class DefaultPromptForChatModelReferenceParam {
		/** Reference to the chat model configuration to get a default prompt for */
		public @NotNull GObjectRef<GBaseChatModelConfig> chatModelConfigReference = null;
		
		/** Flag indicating whether this is for a RAG prompt */
		public @NotNull Boolean ragPrompt = null;
	}

	/**
	 * Retrieves the default prompt for a chat model referenced by a GObjectRef
	 * 
	 * @param param Object containing the chat model reference and RAG prompt flag
	 * @return The default prompt configuration
	 * @throws GeboPersistenceException If there's an error retrieving the referenced object
	 */
	@PostMapping(value = "getDefaultPromptForChatModelReference", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GPromptConfig getDefaultPromptForChatModelReference(
			@RequestBody DefaultPromptForChatModelReferenceParam param) throws GeboPersistenceException {
		GBaseChatModelConfig chatModelConfig = persistentObjectManager.findByReference(param.chatModelConfigReference,
				GBaseChatModelConfig.class);
		if (chatModelConfig != null)
			return promptConfigDao.defaultPrompt(chatModelConfig, param.ragPrompt);
		else
			return promptConfigDao.defaultPrompt(param.ragPrompt);
	}

	/**
	 * Retrieves the general default prompt based only on the RAG prompt flag
	 * 
	 * @param ragPrompt Flag indicating whether this is for a RAG prompt
	 * @return The default prompt configuration
	 */
	@GetMapping(value = "getDefaultPrompt", produces = MediaType.APPLICATION_JSON_VALUE)
	public GPromptConfig getDefaultPrompt(@RequestParam("ragPrompt") Boolean ragPrompt) {
		return promptConfigDao.defaultPrompt(ragPrompt);
	}

}