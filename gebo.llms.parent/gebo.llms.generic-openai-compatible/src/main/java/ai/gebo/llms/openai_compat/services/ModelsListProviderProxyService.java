/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * AI generated comments
 * 
 * Service class that provides a proxy to access LLM model lists from different providers.
 * This service handles the authentication and retrieval of model information through
 * underlying provider-specific handlers.
 */
package ai.gebo.llms.openai_compat.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGModelsListProvider;
import ai.gebo.llms.abstraction.layer.services.IGModelsListProviderRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

@Service
public class ModelsListProviderProxyService {
	/**
	 * Repository containing all available model list providers
	 */
	@Autowired
	IGModelsListProviderRepositoryPattern modelsListProviders;
	
	/**
	 * Service to access secrets like API keys
	 */
	@Autowired
	IGeboSecretsAccessService secretService;

	/**
	 * Default constructor
	 */
	public ModelsListProviderProxyService() {

	}

	/**
	 * Retrieves a list of available models from a specified provider
	 * 
	 * @param <ModelChoice> The type of model choice
	 * @param <ModelConfig> The type of model configuration
	 * @param modelListProvider The identifier for the models list provider
	 * @param config The configuration for the model retrieval request
	 * @param choiceType The class type of the model choice
	 * @return An OperationStatus containing either the list of models or error information
	 * @throws RuntimeException if the specified model list provider is not found
	 */
	public <ModelChoice extends GBaseModelChoice, ModelConfig extends GBaseModelConfig<ModelChoice>> OperationStatus<List<ModelChoice>> geModels(
			String modelListProvider, ModelConfig config, Class<ModelChoice> choiceType) {
		// Find the appropriate handler for the requested provider
		IGModelsListProvider handler = modelsListProviders.findByCode(modelListProvider);
		if (handler == null)
			throw new RuntimeException("Models list provider with id=>" + modelListProvider + " is unkown");
		
		List<ModelChoice> out = new ArrayList<ModelChoice>();
		String clearApiKey = null;
		
		// Retrieve and decrypt API key if specified in the config
		if (config.getApiSecretCode() != null) {
			try {
				AbstractGeboSecretContent secretContent = secretService.getSecretContentById(config.getApiSecretCode());
				if (secretContent.type() == GeboSecretType.TOKEN) {
					GeboTokenContent content = (GeboTokenContent) secretContent;
					clearApiKey = content.getToken();
				}
			} catch (GeboCryptSecretException e) {
				return OperationStatus.ofError("Problem managing crypted api key", e.getLocalizedMessage());
			}
		}

		// Call the provider handler to get the list of models
		try {
			return handler.geModels(null, config, clearApiKey, choiceType);
		} catch (Throwable th) {
			return OperationStatus.ofError("Problem retrieving models list", th.getLocalizedMessage());
		}
	}
}