/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.huggingface.services;

import java.util.List;

import org.springframework.ai.huggingface.HuggingfaceChatModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.huggingface.model.GHuggingfaceChatModelChoice;
import ai.gebo.llms.huggingface.model.GHuggingfaceChatModelConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import jakarta.el.MethodNotFoundException;
import lombok.AllArgsConstructor;

/**
 * AI generated comments
 * 
 * Service responsible for configuring and supporting HuggingFace chat models.
 * Only enabled when the huggingfaceEnabled property is set to true.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "huggingfaceEnabled", havingValue = "true")
@Service
@AllArgsConstructor
public class HuggingfaceChatModelConfigurationSupportService
		implements IGChatModelConfigurationSupportService<GHuggingfaceChatModelChoice, GHuggingfaceChatModelConfig> {
	/**
	 * Static definition of the HuggingFace chat model type.
	 */
	static final GChatModelType type = new GChatModelType();
	static {
		type.setCode("chat-huggingface");
		type.setDescription("Chat models hosted on HuggingFace");
		type.setModelConfigurationClass(GHuggingfaceChatModelConfig.class.getName());
	}
	/**
	 * Service for looking up available HuggingFace models.
	 */
	final HuggingfaceModelsLookupService modelsService;
	/**
	 * List of available chat model choices.
	 */
	static final List<GBaseChatModelChoice> choices = List.of();

	/**
	 * Service for accessing secrets, used to retrieve API keys.
	 */
	final IGeboSecretsAccessService secretService;
	final ModelRuntimeConfigureHandler configureHandler;

	/**
	 * Inner class implementing a HuggingFace configurable chat model.
	 */
	class HuggingfaceConfigurableChatModel
			extends GAbstractConfigurableChatModel<GHuggingfaceChatModelConfig, HuggingfaceChatModel> {

		/**
		 * Configures a HuggingFace chat model instance based on the provided
		 * configuration.
		 * 
		 * @param config The configuration for the HuggingFace model
		 * @param type   The type of chat model
		 * @return A configured HuggingFace chat model
		 * @throws LLMConfigException If configuration fails or required elements are
		 *                            missing
		 */
		@Override
		protected HuggingfaceChatModel configureModel(GHuggingfaceChatModelConfig config, GChatModelType type)
				throws LLMConfigException {
			String apiKey = null;
			String user = null;
			if (config.getApiSecretCode() == null || config.getApiSecretCode().trim().length() == 0)
				throw new LLMConfigException("HuggingFace api cannot work without needed api key configuration");
			try {
				AbstractGeboSecretContent secret = secretService.getSecretContentById(config.getApiSecretCode());
				if (secret.type() == GeboSecretType.TOKEN) {
					GeboTokenContent token = (GeboTokenContent) secret;
					apiKey = token.getToken();
					user = token.getUser();
				} else {
					throw new LLMConfigException("HuggingFace api can work only with an api key of type TOKEN");
				}
			} catch (GeboCryptSecretException e) {
				throw new LLMConfigException("HuggingFace api  key configuration gone wrong ", e);
			}

			HuggingfaceChatModel model = new HuggingfaceChatModel(apiKey, config.getBaseUrl());
			return model;
		}

	};

	/**
	 * Returns the type of chat model this service supports.
	 * 
	 * @return The HuggingFace chat model type
	 */
	@Override
	public GChatModelType getType() {

		return type;
	}

	/**
	 * Creates a configurable chat model instance with the provided configuration.
	 * 
	 * @param config The configuration for the HuggingFace model
	 * @return A configured chat model instance
	 * @throws LLMConfigException If the model cannot be configured properly
	 */
	@Override
	public IGConfigurableChatModel<GHuggingfaceChatModelConfig> create(GHuggingfaceChatModelConfig config)
			throws LLMConfigException {
		HuggingfaceConfigurableChatModel model = new HuggingfaceConfigurableChatModel();
		model.initialize(config, type);
		return model;
	}

	/**
	 * Retrieves the available chat model choices based on the provided
	 * configuration.
	 * 
	 * @param config The configuration used to look up available models
	 * @return Status containing the list of available HuggingFace chat model
	 *         choices
	 */
	@Override
	public OperationStatus<List<GHuggingfaceChatModelChoice>> getModelChoices(GHuggingfaceChatModelConfig config) {

		return modelsService.getChatModels(config);
	}

	/**
	 * Creates a base configuration for a specified model preset. Not implemented
	 * for HuggingFace models.
	 * 
	 * @param presetModel The preset model identifier
	 * @return A base configuration (not implemented)
	 * @throws MethodNotFoundException Always thrown as this method is not
	 *                                 implemented
	 */
	@Override
	public GHuggingfaceChatModelConfig createBaseConfiguration(String presetModel) {
		GHuggingfaceChatModelConfig clean = new GHuggingfaceChatModelConfig();
		clean.setModelTypeCode(getType().getCode());
		clean.setChoosedModel(new GHuggingfaceChatModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("chat model " + presetModel);
		clean.setDescription("Huggingface AI chat model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;
	}

	@Override
	public OperationStatus<GHuggingfaceChatModelConfig> insertAndConfigure(GHuggingfaceChatModelConfig config) {
		// TODO Auto-generated method stub
		return null;
	}

}