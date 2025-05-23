/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.openai_compat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.controllers.AbstractBaseChatModelsConfigurationCRUDController;
import ai.gebo.llms.abstraction.layer.controllers.BaseChatModelsConfigurationController;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportService;
import ai.gebo.llms.openai_compat.config.GenericOpenAICompatibleProvidersConfig;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIChatModelChoice;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIChatModelConfig;
import ai.gebo.llms.openai_compat.model.GenericOpenAIChatModelTypeConfig;
import ai.gebo.llms.openai_compat.services.GenericOpenAIAPIChatModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * 
 * Controller for managing OpenAI-compatible chat model configurations.
 * This REST controller provides endpoints for CRUD operations on
 * GenericOpenAIAPIChatModelConfig objects and related functionality.
 * Access restricted to users with ADMIN role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/GenericOpenAIAPIChatModelsConfigurationController")
public class GenericOpenAIAPIChatModelsConfigurationController extends
		AbstractBaseChatModelsConfigurationCRUDController<GenericOpenAIAPIChatModelConfig, GenericOpenAIAPIChatModelChoice> {
	/**
	 * Configuration for generic OpenAI-compatible providers
	 */
	@Autowired
	GenericOpenAICompatibleProvidersConfig config;
	
	/**
	 * Repository pattern for accessing chat model configuration support services
	 */
	@Autowired IGChatModelConfigurationSupportServiceRepositoryPattern modelsRepoPattern;
	
	/**
	 * Constructor initializing the controller with appropriate model configuration class
	 */
	public GenericOpenAIAPIChatModelsConfigurationController() {
		super(GenericOpenAIAPIChatModelConfig.class);
	}
	
	/**
	 * Retrieves the list of available OpenAI-compatible chat model types
	 * @return List of chat model type configurations
	 */
	@GetMapping(value ="getGenericOpenAIChatModelTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GenericOpenAIChatModelTypeConfig> getGenericOpenAIChatModelTypes() {
		return config.getChatModelProviders();
	}

	/**
	 * Creates a new OpenAI-compatible chat model configuration
	 * @param config The configuration to insert
	 * @return Operation status with the inserted configuration
	 */
	@PostMapping(value = "insertGenericOpenAIAPIChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GenericOpenAIAPIChatModelConfig> insertGenericOpenAIAPIChatModelConfig(
			@RequestBody GenericOpenAIAPIChatModelConfig config) {
		return super.insert(config);

	}

	/**
	 * Updates an existing OpenAI-compatible chat model configuration
	 * @param config The configuration to update
	 * @return Operation status with the updated configuration
	 */
	@PostMapping(value = "updateGenericOpenAIAPIChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GenericOpenAIAPIChatModelConfig> updateGenericOpenAIAPIChatModelConfig(
			@RequestBody GenericOpenAIAPIChatModelConfig config) {

		return super.update(config);
	}

	/**
	 * Deletes an OpenAI-compatible chat model configuration
	 * @param config The configuration to delete
	 * @return Operation status indicating success or failure
	 */
	@PostMapping(value = "deleteGenericOpenAIAPIChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteGenericOpenAIAPIChatModelConfig(
			@RequestBody GenericOpenAIAPIChatModelConfig config) {

		return super.delete(config);
	}

	/**
	 * Finds an OpenAI-compatible chat model configuration by its code
	 * @param code The unique code identifier for the configuration
	 * @return The matching configuration if found
	 * @throws GeboPersistenceException If there's an issue retrieving the configuration
	 */
	@GetMapping(value = "findGenericOpenAIAPIChatModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericOpenAIAPIChatModelConfig findGenericOpenAIAPIChatModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}

	/**
	 * Retrieves available chat models based on the provided configuration
	 * @param config The configuration to use as filter
	 * @return Operation status with list of available chat model choices
	 */
	@PostMapping(value = "getGenericOpenAIAPIChatModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GenericOpenAIAPIChatModelChoice>> getGenericOpenAIAPIChatModels(
			@RequestBody GenericOpenAIAPIChatModelConfig config) {
		return getModelChoices(config);
	}

	/**
	 * Overrides the parent method to retrieve model choices based on model type
	 * @param type The configuration containing the model type to look up
	 * @return Operation status with list of matching model choices
	 * @throws RuntimeException If modelTypeCode is null or no matching provider is found
	 */
	@Override
	protected OperationStatus<List<GenericOpenAIAPIChatModelChoice>> getModelChoices(
			GenericOpenAIAPIChatModelConfig type) {
		if (type.getModelTypeCode() == null)
			throw new RuntimeException("modelTypeCode cannot be null");
		IGChatModelConfigurationSupportService handler = modelsRepoPattern.findByCode(type.getModelTypeCode());
		if (handler == null)
			throw new RuntimeException(
					"modelTypeCode=>" + type.getModelTypeCode() + " with no corresponding model provider");

		return handler.getModelChoices(type);
	}
}