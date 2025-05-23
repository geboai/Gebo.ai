/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.fastsetup.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.fastsetup.model.ComponentLLMSStatus;
import ai.gebo.fastsetup.model.FastLLMSSetupData;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatProfileChatModel;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatProfileManagementService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRuntimeChatProfileChatModelDao;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.services.IGSecurityService;

/**
 * Service to handle fast setup for LLMS integration in Gebo platform.
 * This class contains methods for configuring chat and embedding models.
 * 
 * AI generated comments
 */
@Service
public class GeboFastLLMSSetupService {
	@Autowired
	IGSecurityService securityService;

	/**
	 * Inner class representing a couple of configurations for language models (chat and embedding).
	 */
	static class LLMConfigCouple {
		ProvidersAndPresets chat = null, embedding = null;
	}

	/**
	 * Inner class representing a configuration provider and its presets for models.
	 */
	static class ProvidersAndPresets {
		String uiAlias = null;
		String provider = null;
		String presetModel = null;
		String description = null;

		// Constructor to initialize a provider's configuration details.
		public ProvidersAndPresets(String ui, String provider, String presetModel, String description) {
			this.uiAlias = ui;
			this.provider = provider;
			this.presetModel = presetModel;
			this.description = description;
		}
	}

	// Array defining available chat models with their configurations.
	static ProvidersAndPresets[] chatModels = new ProvidersAndPresets[] {
			new ProvidersAndPresets("openai", "chatgpt-OpenAI", "gpt-4o", "OpenAI gpt4o"),
			new ProvidersAndPresets("nvidia", "chatmodel-nvidia", "meta/llama-3.1-405b-instruct",
					"Nvidia provided meta llama 3.1 405b instruct model")
			/*
			 * new ProvidersAndPresets("groq", "chatmodel-groq", "llama-3.1-70b-versatile",
			 * "groq provideed llama-3.1-70b-versatile")
			 */
    };

	// Sample text for testing chat model configurations.
	static final String chatText4Test = "Hi how are you?";

	// Array defining available embedding models with their configurations.
	static ProvidersAndPresets[] embeddingModels = new ProvidersAndPresets[] {
			new ProvidersAndPresets("openai", "embedding-OpenAI", "text-embedding-3-large",
					"OpenAI text embedding 3 large"),
			new ProvidersAndPresets("nvidia", "embedding-nvidia", "nvidia/nv-embed-v1",
					"Nvidia provided nv-embed-v1 embedding model")
			/*
			 * new ProvidersAndPresets("groq", "embedding-groq", "llama-3.1-70b-versatile",
			 * "groq provideed llama-3.1-70b-versatile")
			 */
    };

	// Sample text for testing embedding model configurations.
	static final String embeddingText4Test = "By default, the length of the embedding vector will be 1536 for text-embedding-3-small or 3072 for text-embedding-3-large. You can reduce the dimensions of the embedding by passing in the dimensions parameter without the embedding losing its concept-representing properties. We go into more detail on embedding dimensions in the embedding use case section.";

	// Logger instance to log messages.
	static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(GeboFastInstallationSetupService.class);

	@Autowired
	IGChatModelRuntimeConfigurationDao chatModelsConfigDao;

	@Autowired
	IGChatModelConfigurationSupportServiceRepositoryPattern chatModelsSupportRepo;

	@Autowired
	IGeboCryptingService cryptService;

	@Autowired
	IGEmbeddingModelRuntimeConfigurationDao embeddingModelsConfigDao;

	@Autowired
	IGEmbeddingModelConfigurationSupportServiceRepositoryPattern embedModelsSupportRepo;

	@Autowired
	IGToolCallbackSourceRepositoryPattern functionsCallbackWrapper;

	@Autowired
	IGPersistentObjectManager persistenceManager;

	@Autowired
	IGeboSecretsAccessService secretService;

	@Autowired
	IGChatProfileManagementService chatProfileManagementService;

	@Autowired 
	IGRuntimeChatProfileChatModelDao chatProfileChatModelDao;

	/**
	 * Default constructor.
	 */
	public GeboFastLLMSSetupService() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Finds a couple of language model configurations (chat and embedding) by the UI provider alias.
	 *
	 * @param uiProvider The UI provider alias to look for.
	 * @return LLMConfigCouple containing both chat and embedding configurations, or null if not found.
	 */
	static LLMConfigCouple findCouple(String uiProvider) {
		ProvidersAndPresets chatModel = null, embeddingModel = null;
		for (ProvidersAndPresets c : chatModels) {
			if (c.uiAlias.equals(uiProvider)) {
				chatModel = c;
			}
		}
		for (ProvidersAndPresets c : embeddingModels) {
			if (c.uiAlias.equals(uiProvider)) {
				embeddingModel = c;
			}
		}
		if (chatModel != null && embeddingModel != null) {
			LLMConfigCouple c = new LLMConfigCouple();
			c.chat = chatModel;
			c.embedding = embeddingModel;
			return c;
		}
		return null;
	}

	/**
	 * Checks if the default language models (chat and embedding) are present.
	 *
	 * @return OperationStatus<Boolean> indicating the presence of default models.
	 */
	private OperationStatus<Boolean> checkLlmsDefaultModelsPresence() {
		IGConfigurableChatModel defaultChatModel = chatModelsConfigDao.defaultHandler();
		IGConfigurableEmbeddingModel defaultEmbeddingModel = embeddingModelsConfigDao.defaultHandler();
		OperationStatus<Boolean> value = null;
		if (defaultChatModel == null || defaultEmbeddingModel == null) {
			value = OperationStatus.of(false);
			value.getMessages().clear();
			value.getMessages().add(GUserMessage.warnMessage("LLMS default model not set",
					"The defaul llms model provider (openai chatgpt,google gemini or anthropic claude) is not set yet"));
		} else {
			value = OperationStatus.of(true);
			value.getMessages().clear();
			value.getMessages().add(GUserMessage.successMessage("LLMS default model set",
					"The defaul llms model provider  is not set"));
		}
		return value;
	}

	/**
	 * Clears the configurations related to LLMS for the provided setup data.
	 *
	 * @param data FastLLMSSetupData containing the setup details.
	 */
	private void clearLLms(FastLLMSSetupData data) {
		// Implementation for clearing LLMS data will be added here in the future.
	}

	/**
	 * Configures LLMS based on the provided setup data.
	 *
	 * @param data The FastLLMSSetupData containing the configuration details.
	 * @return OperationStatus<Boolean> indicating the result of configuration.
	 */
	public OperationStatus<Boolean> configureLLMS(FastLLMSSetupData data) {
		OperationStatus<Boolean> status = OperationStatus.of(false);
		status.getMessages().clear();

		// Find configurations for the specified provider.
		LLMConfigCouple c = findCouple(data.getProvider());
		if (c == null) {
			OperationStatus<Boolean> out = OperationStatus.of(false);
			out.getMessages().clear();
			out.getMessages().add(GUserMessage.errorMessage("Unknown provider:" + data.getProvider(),
					"Something goes wrong in the llm setting"));
			return out;
		}

		// Configuration support services for chat and embedding models.
		IGChatModelConfigurationSupportService chatModelSupport = chatModelsSupportRepo.findByCode(c.chat.provider);
		IGEmbeddingModelConfigurationSupportService embedModelSupport = embedModelsSupportRepo
				.findByCode(c.embedding.provider);

		// Create base configurations for chat and embedding models.
		GBaseChatModelConfig chatConfig = (GBaseChatModelConfig) chatModelSupport
				.createBaseConfiguration(c.chat.presetModel);
		GBaseEmbeddingModelConfig embedConfig = (GBaseEmbeddingModelConfig) embedModelSupport
				.createBaseConfiguration(c.embedding.presetModel);

		// Prepare token content for secret storage.
		GeboTokenContent content = new GeboTokenContent();
		content.setToken(data.getApiKey());
		content.setUser(data.getUser());
		String secretID = null;

		try {
			// Store secrets and configure chat and embedding models.
			secretID = secretService.storeSecret(content, "default " + data.getProvider() + " account", data.getProvider());
			chatConfig.setApiSecretCode(secretID);
			chatConfig.setAccessibleToAll(true);
			embedConfig.setApiSecretCode(secretID);
			chatConfig.setCode("temporary");
			embedConfig.setCode("temporary");

			// Create chat and embedding model instances.
			IGConfigurableChatModel chatModel = chatModelSupport.create(chatConfig);
			IGConfigurableEmbeddingModel embeddingModel = embedModelSupport.create(embedConfig);

			// Try to lookup for chat model configuration choices.
			OperationStatus<List<GBaseChatModelChoice>> chatChoices = chatModelSupport.getModelChoices(chatConfig);
			if (chatChoices.getResult() != null && !chatChoices.getResult().isEmpty()) {
				Optional<GBaseChatModelChoice> lookedUpOption = chatChoices.getResult().stream().filter(x -> {
					return x.getCode().equals(chatConfig.getChoosedModel().getCode());
				}).findFirst();
				if (lookedUpOption != null && lookedUpOption.isPresent()) {
					chatConfig.setChoosedModel(lookedUpOption.get());
				}
			}

			// Try to lookup for embedding model configuration choices.
			OperationStatus<List<GBaseEmbeddingModelChoice>> embedChoices = embedModelSupport
					.getModelChoices(embedConfig);
			if (embedChoices.getResult() != null && !embedChoices.getResult().isEmpty()) {
				Optional<GBaseEmbeddingModelChoice> lookedUpOption = embedChoices.getResult().stream().filter(x -> {
					return x.getCode().equals(embedConfig.getChoosedModel().getCode());
				}).findFirst();
				if (lookedUpOption != null && lookedUpOption.isPresent()) {
					embedConfig.setChoosedModel(lookedUpOption.get());
				}
			}

			// Reset configuration codes and test with provided text.
			chatConfig.setCode(null);
			embedConfig.setCode(null);
			String salutationResponse = chatModel.getChatModel().call(chatText4Test);
			Object vector = embeddingModel.getEmbeddingModel().embed(embeddingText4Test);
			
			// Delete temporary models after testing.
			chatModel.delete();
			embeddingModel.delete();

			// Set configurations as default models.
			chatConfig.setDefaultModel(true);
			embedConfig.setDefaultModel(true);

			// Associate functions with chat configuration.
			List<ToolCallback> functions = functionsCallbackWrapper.getTools();
			List<String> functionsList = functions.stream().map(x -> {
				return x.getToolDefinition().name();
			}).toList();
			chatConfig.setEnabledFunctions(functionsList);

			// Persist configurations and add runtime associations.
			persistenceManager.insert(chatConfig);
			persistenceManager.insert(embedConfig);
			chatModelsConfigDao.addRuntimeByConfig(chatConfig);
			embeddingModelsConfigDao.addRuntimeByConfig(embedConfig);

			// Get or create default chat profile and link to current chat model.
			GChatProfileConfiguration chatProfile = chatProfileManagementService.getOrCreateDefaultChatProfile();
			IGChatProfileChatModel chatProfileChatModel = chatProfileChatModelDao.getChatModel(chatProfile);
			
			// Update operation status to success.
			status = OperationStatus.of(true);
			status.getMessages().clear();
			status.getMessages().add(GUserMessage.successMessage("Default embedding & chat models settings OK!", ""));
		} catch (GeboPersistenceException e) {
			// Handle persistence exception and log it.
			status.getMessages().add(GUserMessage.errorMessage("Save settings to mongodb gone wrong", e));
			LOGGER.error("Save settings to mongodb gone wrong!?", e);
		} catch (LLMConfigException e) {
			// Handle configuration exception and log it.
			status.getMessages()
					.add(GUserMessage.errorMessage("Default embedding & chat models settings gone wrong", e));
			LOGGER.error("Default embedding & chat models settings gone wrong!?", e);
		} catch (GeboCryptSecretException e) {
			// Handle crypting exception for API key storage and log it.
			status.getMessages()
					.add(GUserMessage.errorMessage("Error in the crypting subsystem for the API key storage", e));
			LOGGER.error("Error in the crypting subsystem for the API key storage!?", e);
		} catch (Throwable th) {
			// Handle generic exceptions and log them.
			status.getMessages()
					.add(GUserMessage.errorMessage(
							"Default embedding & chat models settings gone wrong. Try to reinsert the API key correctly",
							th.getMessage()));
			LOGGER.error("Default embedding & chat models settings gone wrong!?", th);
		}
		return status;
	}

	/**
	 * Get the current setup status of language models (LLMS).
	 *
	 * @return ComponentLLMSStatus indicating the setup status of chat and embedding models.
	 */
	public ComponentLLMSStatus getLLMSSetupStatus() {
		ComponentLLMSStatus status = new ComponentLLMSStatus();
		status.chatModelSetup = !chatModelsConfigDao.getConfigurations().isEmpty();
		status.embeddedModelSetup = !embeddingModelsConfigDao.getConfigurations().isEmpty();
		status.isSetup = status.chatModelSetup && status.embeddedModelSetup;
		return status;
	}
}