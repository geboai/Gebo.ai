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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.model.OperationStatus;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * 
 * REST controller responsible for handling prompt template wizard operations.
 * This controller provides endpoints for generating prompt templates and retrieving
 * template configurations.
 */
@RestController
@RequestMapping("api/users/PromptTemplateWizardController")
public class PromptTemplateWizardController {
	/** Data access object for chat model runtime configurations */
	@Autowired
	IGChatModelRuntimeConfigurationDao chatModelsDato;
	
	

	/**
	 * Default constructor for the controller
	 */
	public PromptTemplateWizardController() {

	}

	/**
	 * Parameter class for prompt template generation requests
	 */
	public static class PromptTemplateParam {
		/** The query text to generate a prompt template for */
		@NotNull
		public String query = null;
		
		/** Optional model code to specify which LLM to use */
		public String modelCode = null;
	}

	/**
	 * Response class for prompt template generation requests
	 */
	public static class PromptTemplateResponse {
		/** The generated prompt template response */
		public String response = null;
	}

	/**
	 * Endpoint to generate a prompt template based on the provided query
	 * 
	 * @param param The parameters containing the query and optional model code
	 * @return An operation status containing either the response or error details
	 */
	@PostMapping(value = "generatePromptTemplate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<PromptTemplateResponse> generatePromptTemplate(@RequestBody PromptTemplateParam param) {
		try {
			IGConfigurableChatModel usedModel = null;
			if (param.modelCode != null) {
				// Use the specified model if provided
				usedModel = chatModelsDato.findByCode(param.modelCode);
			} else {
				// Otherwise use the default model
				usedModel = chatModelsDato.defaultHandler();
			}
			PromptTemplateResponse response = new PromptTemplateResponse();
			response.response = usedModel.getChatModel().call(param.query);
			return OperationStatus.of(response);
		} catch (Throwable th) {
			OperationStatus<PromptTemplateResponse> status = OperationStatus.of(th);
			return status;
		}
	}

	
}