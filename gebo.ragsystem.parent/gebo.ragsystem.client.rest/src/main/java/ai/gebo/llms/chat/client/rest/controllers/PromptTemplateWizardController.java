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


	
}