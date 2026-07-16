/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.setup.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableImageModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableRankerModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTextToSpeechModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTranscriptModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGImageModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGImageModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGImageModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatProfileManagementService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRuntimeChatProfileChatModelDao;
import ai.gebo.llms.setup.config.LLMSModelsPresets;
import ai.gebo.llms.setup.config.LLMSVendor;
import ai.gebo.llms.setup.config.LLMSVendorsSetupConfig;
import ai.gebo.llms.setup.config.ModelType;
import ai.gebo.llms.setup.model.ComponentLLMSStatus;
import ai.gebo.llms.setup.model.LLMAutoconfigureCreationData;
import ai.gebo.llms.setup.model.LLMCreateModelData;
import ai.gebo.llms.setup.model.LLMCredentialsCreationData;
import ai.gebo.llms.setup.model.LLMCredentialsVerificationData;
import ai.gebo.llms.setup.model.LLMExistingConfiguration;
import ai.gebo.llms.setup.model.LLMModelsLookupParameter;
import ai.gebo.llms.setup.model.LLMSModelsCreationResult;
import ai.gebo.llms.setup.model.LLMSModelsCreationResult.LLMUnresolvedModel;
import ai.gebo.llms.setup.model.LLMSSetupConfigurationData;
import ai.gebo.llms.setup.model.LLMSSetupConfigurationData.LLMSSetupConfiguration;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.services.IGSecurityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

/**
 * Service to handle fast setup for LLMS integration in Gebo platform. This
 * class contains methods for configuring chat and embedding models.
 * 
 * AI generated comments
 */
@Service
@AllArgsConstructor
public class GeboLLMSSetupService {
	private final IGSecurityService securityService;
	private final IGChatModelRuntimeConfigurationDao chatModelsConfigDao;
	private final IGChatModelConfigurationSupportServiceRepositoryPattern chatModelsSupportRepo;
	private final IGeboCryptingService cryptService;
	private final IGEmbeddingModelRuntimeConfigurationDao embeddingModelsConfigDao;
	private final IGEmbeddingModelConfigurationSupportServiceRepositoryPattern embedModelsSupportRepo;
	private final IGToolCallbackSourceRepositoryPattern functionsCallbackWrapper;
	private final IGPersistentObjectManager persistenceManager;
	private final IGeboSecretsAccessService secretService;
	private final IGChatProfileManagementService chatProfileManagementService;
	private final IGRuntimeChatProfileChatModelDao chatProfileChatModelDao;
	private final IGImageModelRuntimeConfigurationDao imageModelRuntimeDao;
	private final IGImageModelConfigurationSupportServiceRepositoryPattern imageModelsSupportRepo;
	private final IGRankerModelRuntimeConfigurationDao rankerModelsRuntimeDao;
	private final IGRankerModelConfigurationSupportServiceRepositoryPattern rankerModelsSupportRepo;
	private final IGTextToSpeechModelRuntimeConfigurationDao ttsModelsRuntimeDao;
	private final IGTextToSpeechModelConfigurationSupportServiceRepositoryPattern ttsModelsSupportRepo;
	private final IGTranscriptModelRuntimeConfigurationDao transcriptModelsRuntimeDao;
	private final IGTranscriptModelConfigurationSupportServiceRepositoryPattern transcriptModelsSupportRepo;
	private final LLMSVendorsSetupConfig vendorsSetupConfig;
	private final IGToolCallbackSourceRepositoryPattern toolsRepo;
	// Sample text for testing embedding model configurations.
	private static final String embeddingText4Test = "By default, the length of the embedding vector will be 1536 for text-embedding-3-small or 3072 for text-embedding-3-large. You can reduce the dimensions of the embedding by passing in the dimensions parameter without the embedding losing its concept-representing properties. We go into more detail on embedding dimensions in the embedding use case section.";
	// Logger instance to log messages.
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(GeboLLMSSetupService.class);

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
	 * Lightweight snapshot of which model "kinds" already have a suitable model
	 * configured. Presence is default-based (a model flagged as default), except the
	 * internal-services chat slot which is identified by its declared uses. This is
	 * the single source of truth shared by {@link #getActualConfiguration()},
	 * {@link #getLLMSSetupStatus()} and the wizard-only creation guards, so the
	 * various entry points can never disagree on what is already set up.
	 */
	private static class ModelKindPresence {
		boolean defaultChat;
		boolean internalServicesChat;
		boolean embedding;
		boolean ranker;
		boolean images;
		boolean tts;
		boolean transcript;
		// The model code of the current default (or, for the chat service slot, the
		// internal-services model) of each kind, for display in the expert UI.
		String defaultChatCode;
		String internalServicesChatCode;
		String embeddingCode;
		String rankerCode;
		String imagesCode;
		String ttsCode;
		String transcriptCode;
		// The provider (model type handler code, e.g. chatgpt-OpenAI / chatmodel-regolo.ai)
		// of each current default, so the expert UI can show which provider owns it.
		String defaultChatProviderId;
		String internalServicesChatProviderId;
		String embeddingProviderId;
		String rankerProviderId;
		String imagesProviderId;
		String ttsProviderId;
		String transcriptProviderId;
	}

	private static String modelCodeOf(GBaseModelConfig config) {
		// Show the actual model (the chosen model code), never the internal config code.
		return config != null && config.getChoosedModel() != null ? config.getChoosedModel().getCode() : null;
	}

	/**
	 * Resolves the provider id of a configured model by mapping its handler (model
	 * type) code back to the vendor that declares it in the fast-setup library
	 * ({@code library.yml}), so the UI shows e.g. "nvidia" rather than the internal
	 * "chatmodel-nvidia" handler code. Falls back to the handler code if the model
	 * comes from a provider not present in the library.
	 */
	private String providerIdOf(GModelType type) {
		if (type == null || type.getCode() == null) {
			return null;
		}
		String handlerCode = type.getCode();
		for (LLMSVendor vendor : vendorsSetupConfig.getVendors()) {
			if (vendor.getPresets() == null) {
				continue;
			}
			for (LLMSModelsPresets preset : vendor.getPresets()) {
				if (handlerCode.equals(preset.getServiceHandler()) && vendor.getVendorInfo() != null) {
					return vendor.getVendorInfo().getVendorId();
				}
			}
		}
		return handlerCode;
	}

	private ModelKindPresence computeModelKindPresence() {
		ModelKindPresence presence = new ModelKindPresence();
		for (IGConfigurableChatModel chatModel : chatModelsConfigDao.getConfigurations()) {
			if (chatModel.getConfig() instanceof GBaseChatModelConfig chatModelConfig) {
				if (chatModelConfig.getDefaultModel() != null && chatModelConfig.getDefaultModel()) {
					presence.defaultChat = true;
					presence.defaultChatCode = modelCodeOf(chatModelConfig);
					presence.defaultChatProviderId = providerIdOf(chatModel.getType());
				}
				if (chatModelConfig.getForUses() != null
						&& chatModelConfig.getForUses().contains(ChatModelsUses.INTERNAL_SERVICES)) {
					presence.internalServicesChat = true;
					presence.internalServicesChatCode = modelCodeOf(chatModelConfig);
					presence.internalServicesChatProviderId = providerIdOf(chatModel.getType());
				}
			}
		}
		for (IGConfigurableEmbeddingModel model : embeddingModelsConfigDao.getConfigurations()) {
			if (model.getConfig().getDefaultModel() != null && model.getConfig().getDefaultModel()) {
				presence.embedding = true;
				presence.embeddingCode = modelCodeOf(model.getConfig());
				presence.embeddingProviderId = providerIdOf(model.getType());
			}
		}
		for (IGConfigurableRankerModel model : rankerModelsRuntimeDao.getConfigurations()) {
			if (model.getConfig().getDefaultModel() != null && model.getConfig().getDefaultModel()) {
				presence.ranker = true;
				presence.rankerCode = modelCodeOf(model.getConfig());
				presence.rankerProviderId = providerIdOf(model.getType());
			}
		}
		for (IGConfigurableImageModel model : imageModelRuntimeDao.getConfigurations()) {
			if (model.getConfig().getDefaultModel() != null && model.getConfig().getDefaultModel()) {
				presence.images = true;
				presence.imagesCode = modelCodeOf(model.getConfig());
				presence.imagesProviderId = providerIdOf(model.getType());
			}
		}
		for (IGConfigurableTextToSpeechModel model : ttsModelsRuntimeDao.getConfigurations()) {
			if (model.getConfig().getDefaultModel() != null && model.getConfig().getDefaultModel()) {
				presence.tts = true;
				presence.ttsCode = modelCodeOf(model.getConfig());
				presence.ttsProviderId = providerIdOf(model.getType());
			}
		}
		for (IGConfigurableTranscriptModel model : transcriptModelsRuntimeDao.getConfigurations()) {
			if (model.getConfig().getDefaultModel() != null && model.getConfig().getDefaultModel()) {
				presence.transcript = true;
				presence.transcriptCode = modelCodeOf(model.getConfig());
				presence.transcriptProviderId = providerIdOf(model.getType());
			}
		}
		return presence;
	}

	public LLMSSetupConfigurationData getActualConfiguration() throws GeboCryptSecretException {
		LLMSSetupConfigurationData configData = new LLMSSetupConfigurationData();

		for (LLMSVendor vendor : vendorsSetupConfig.getVendors()) {
			LLMSSetupConfiguration vendorData = new LLMSSetupConfiguration();
			vendorData.setParentModel(vendor.getVendorInfo());
			vendorData.setLibraryModel(vendor.getPresets());
			vendorData.setRuntimeConfigs(new ArrayList<>());
			for (LLMSModelsPresets preset : vendor.getPresets()) {
				switch (preset.getType()) {
				case CHAT: {
					IGChatModelConfigurationSupportService handler = chatModelsSupportRepo
							.findByCode(preset.getServiceHandler());
					if (handler == null) {
						LOGGER.debug("The {} handler {} is not present or started up", preset.getType(),
								preset.getServiceHandler());
						continue;
					}
					GModelType modelProviderType = handler.getType();
					List<IGConfigurableChatModel> chatConfigurations = chatModelsConfigDao.getConfigurations();
					for (IGConfigurableChatModel chatModel : chatConfigurations) {
						if (chatModel.getType().getCode().equals(modelProviderType.getCode())) {
							LLMExistingConfiguration existingConfiguration = new LLMExistingConfiguration();
							existingConfiguration.setModelType(ModelType.CHAT);
							existingConfiguration.setExistingModelConfig(GObjectRef.of(chatModel.getConfig()));

							String secretCode = chatModel.getConfig().getApiSecretCode();
							if (secretCode != null) {
								SecretInfo infos = secretService.getSecretInfoById(secretCode);
								existingConfiguration.setSecretInfo(infos);
							}
							if (vendorData.getParentModel().isRequiresCustomUrl()) {
								existingConfiguration.setBaseUrl(chatModel.getConfig().getBaseUrl());
							}
							vendorData.getRuntimeConfigs().add(existingConfiguration);
						}
					}
				}
					break;
				case EMBEDDING: {
					IGEmbeddingModelConfigurationSupportService handler = embedModelsSupportRepo
							.findByCode(preset.getServiceHandler());
					if (handler == null) {
						LOGGER.debug("The {} handler {} is not present or started up", preset.getType(),
								preset.getServiceHandler());
						continue;
					}
					GModelType modelProviderType = handler.getType();
					List<IGConfigurableEmbeddingModel> embeddingConfigurations = embeddingModelsConfigDao
							.getConfigurations();
					for (IGConfigurableEmbeddingModel embeddingModel : embeddingConfigurations) {
						if (embeddingModel.getType().getCode().equals(modelProviderType.getCode())) {
							LLMExistingConfiguration existingConfiguration = new LLMExistingConfiguration();
							existingConfiguration.setModelType(ModelType.EMBEDDING);
							existingConfiguration.setExistingModelConfig(GObjectRef.of(embeddingModel.getConfig()));
							String secretCode = embeddingModel.getConfig().getApiSecretCode();
							if (secretCode != null) {
								SecretInfo infos = secretService.getSecretInfoById(secretCode);
								existingConfiguration.setSecretInfo(infos);
							}
							if (vendorData.getParentModel().isRequiresCustomUrl()) {
								existingConfiguration.setBaseUrl(embeddingModel.getConfig().getBaseUrl());
							}
							vendorData.getRuntimeConfigs().add(existingConfiguration);
						}
					}
				}
					break;
				case RANKING: {
					IGRankerModelConfigurationSupportService handler = rankerModelsSupportRepo
							.findByCode(preset.getServiceHandler());
					if (handler == null) {
						LOGGER.debug("The {} handler {} is not present or started up", preset.getType(),
								preset.getServiceHandler());
						continue;
					}
					GModelType modelProviderType = handler.getType();
					List<IGConfigurableRankerModel> configurations = this.rankerModelsRuntimeDao.getConfigurations();
					for (IGConfigurableRankerModel thisModel : configurations) {
						if (thisModel.getType().getCode().equals(modelProviderType.getCode())) {
							LLMExistingConfiguration existingConfiguration = new LLMExistingConfiguration();
							existingConfiguration.setModelType(ModelType.RANKING);
							existingConfiguration.setExistingModelConfig(GObjectRef.of(thisModel.getConfig()));
							String secretCode = thisModel.getConfig().getApiSecretCode();
							if (secretCode != null) {
								SecretInfo infos = secretService.getSecretInfoById(secretCode);
								existingConfiguration.setSecretInfo(infos);
							}
							if (vendorData.getParentModel().isRequiresCustomUrl()) {
								existingConfiguration.setBaseUrl(thisModel.getConfig().getBaseUrl());
							}
							vendorData.getRuntimeConfigs().add(existingConfiguration);
						}
					}
				}
					break;
				case IMAGESGEN: {
					IGImageModelConfigurationSupportService handler = imageModelsSupportRepo
							.findByCode(preset.getServiceHandler());
					if (handler == null) {
						LOGGER.debug("The {} handler {} is not present or started up", preset.getType(),
								preset.getServiceHandler());
						continue;
					}
					GModelType modelProviderType = handler.getType();
					List<IGConfigurableImageModel> configurations = this.imageModelRuntimeDao.getConfigurations();
					for (IGConfigurableImageModel thisModel : configurations) {
						if (thisModel.getType().getCode().equals(modelProviderType.getCode())) {
							LLMExistingConfiguration existingConfiguration = new LLMExistingConfiguration();
							existingConfiguration.setModelType(ModelType.IMAGESGEN);
							existingConfiguration.setExistingModelConfig(GObjectRef.of(thisModel.getConfig()));
							String secretCode = thisModel.getConfig().getApiSecretCode();
							if (secretCode != null) {
								SecretInfo infos = secretService.getSecretInfoById(secretCode);
								existingConfiguration.setSecretInfo(infos);
							}
							if (vendorData.getParentModel().isRequiresCustomUrl()) {
								existingConfiguration.setBaseUrl(thisModel.getConfig().getBaseUrl());
							}
							vendorData.getRuntimeConfigs().add(existingConfiguration);
						}
					}
				}
					break;
				case TRANSCRIPT: {
					IGTranscriptModelConfigurationSupportService handler = transcriptModelsSupportRepo
							.findByCode(preset.getServiceHandler());
					if (handler == null) {
						LOGGER.debug("The {} handler {} is not present or started up", preset.getType(),
								preset.getServiceHandler());
						continue;
					}
					GModelType modelProviderType = handler.getType();
					List<IGConfigurableTranscriptModel> configurations = this.transcriptModelsRuntimeDao
							.getConfigurations();
					for (IGConfigurableTranscriptModel thisModel : configurations) {
						if (thisModel.getType().getCode().equals(modelProviderType.getCode())) {
							LLMExistingConfiguration existingConfiguration = new LLMExistingConfiguration();
							existingConfiguration.setModelType(ModelType.TRANSCRIPT);
							existingConfiguration.setExistingModelConfig(GObjectRef.of(thisModel.getConfig()));
							String secretCode = thisModel.getConfig().getApiSecretCode();
							if (secretCode != null) {
								SecretInfo infos = secretService.getSecretInfoById(secretCode);
								existingConfiguration.setSecretInfo(infos);
							}
							if (vendorData.getParentModel().isRequiresCustomUrl()) {
								existingConfiguration.setBaseUrl(thisModel.getConfig().getBaseUrl());
							}
							vendorData.getRuntimeConfigs().add(existingConfiguration);
						}
					}
				}
					break;
				case TTS: {
					IGTextToSpeechModelConfigurationSupportService handler = ttsModelsSupportRepo
							.findByCode(preset.getServiceHandler());
					if (handler == null) {
						LOGGER.debug("The {} handler {} is not present or started up", preset.getType(),
								preset.getServiceHandler());
						continue;
					}
					GModelType modelProviderType = handler.getType();
					List<IGConfigurableTextToSpeechModel> configurations = this.ttsModelsRuntimeDao.getConfigurations();
					for (IGConfigurableTextToSpeechModel thisModel : configurations) {
						if (thisModel.getType().getCode().equals(modelProviderType.getCode())) {
							LLMExistingConfiguration existingConfiguration = new LLMExistingConfiguration();
							existingConfiguration.setModelType(ModelType.TTS);
							existingConfiguration.setExistingModelConfig(GObjectRef.of(thisModel.getConfig()));
							String secretCode = thisModel.getConfig().getApiSecretCode();
							if (secretCode != null) {
								SecretInfo infos = secretService.getSecretInfoById(secretCode);
								existingConfiguration.setSecretInfo(infos);
							}
							if (vendorData.getParentModel().isRequiresCustomUrl()) {
								existingConfiguration.setBaseUrl(thisModel.getConfig().getBaseUrl());
							}
							vendorData.getRuntimeConfigs().add(existingConfiguration);
						}
					}
				}
					break;
				}
			}
			configData.getConfigurations().add(vendorData);
		}

		ModelKindPresence presence = computeModelKindPresence();
		configData.setDefaultChatModelExists(presence.defaultChat);
		configData.setEmbeddingModelExists(presence.embedding);
		configData.setInternalServicesChatModelExists(presence.internalServicesChat);
		configData.setImagesModelExists(presence.images);
		configData.setRankerModelExists(presence.ranker);
		configData.setTranscriptModelExists(presence.transcript);
		configData.setTtsModelExists(presence.tts);
		configData.setCanRunAutoconfigure(!(presence.defaultChat && presence.embedding));
		return configData;
	}

	/**
	 * Get the current setup status of language models (LLMS).
	 *
	 * @return ComponentLLMSStatus indicating the setup status of chat and embedding
	 *         models.
	 */
	public ComponentLLMSStatus getLLMSSetupStatus() {
		ComponentLLMSStatus status = new ComponentLLMSStatus();
		ModelKindPresence presence = computeModelKindPresence();
		status.chatModelSetup = presence.defaultChat;
		status.internalServicesChatModelSetup = presence.internalServicesChat;
		status.embeddedModelSetup = presence.embedding;
		status.rankingModelSetup = presence.ranker;
		status.imagesModelSetup = presence.images;
		status.ttsModelSetup = presence.tts;
		status.transcriptModelSetup = presence.transcript;
		status.chatModelCode = presence.defaultChatCode;
		status.internalServicesChatModelCode = presence.internalServicesChatCode;
		status.embeddedModelCode = presence.embeddingCode;
		status.rankingModelCode = presence.rankerCode;
		status.imagesModelCode = presence.imagesCode;
		status.ttsModelCode = presence.ttsCode;
		status.transcriptModelCode = presence.transcriptCode;
		status.chatModelProviderId = presence.defaultChatProviderId;
		status.internalServicesChatModelProviderId = presence.internalServicesChatProviderId;
		status.embeddedModelProviderId = presence.embeddingProviderId;
		status.rankingModelProviderId = presence.rankerProviderId;
		status.imagesModelProviderId = presence.imagesProviderId;
		status.ttsModelProviderId = presence.ttsProviderId;
		status.transcriptModelProviderId = presence.transcriptProviderId;
		status.isSetup = status.chatModelSetup && status.embeddedModelSetup;
		return status;
	}

	public OperationStatus<SecretInfo> createLLMCredentials(@Valid @NotNull LLMCredentialsCreationData apiKeyData)
			throws GeboCryptSecretException {

		GeboTokenContent geboToken = new GeboTokenContent();
		geboToken.setToken(apiKeyData.getNewApiSecret());
		geboToken.setUser(apiKeyData.getNewUserName());
		switch (apiKeyData.getType()) {
		case CHAT: {
			IGChatModelConfigurationSupportService supportLogic = this.chatModelsSupportRepo
					.findByCode(apiKeyData.getServiceHandler());
			String secretId = secretService.storeSecret(geboToken, supportLogic.getType().getDescription() + " api key",
					apiKeyData.getApiKeySecretContext());
			GBaseModelConfig configuration = supportLogic.createBaseConfiguration(null);
			configuration.setApiSecretCode(secretId);
			configuration.setBaseUrl(apiKeyData.getBaseUrl());
			if (apiKeyData.getDoModelsLookup() != null && apiKeyData.getDoModelsLookup()) {
				OperationStatus modelsLookupStatus = supportLogic.getModelChoices(configuration);
				if (modelsLookupStatus.isHasErrorMessages()) {
					secretService.deleteSecret(secretId);
					return OperationStatus.ofError("Invalid credentials",
							"Cannot access provider service with entered credentials");
				}
			}
			return OperationStatus.of(secretService.getSecretInfoById(secretId));

		}

		case EMBEDDING: {
			IGEmbeddingModelConfigurationSupportService supportLogic = this.embedModelsSupportRepo
					.findByCode(apiKeyData.getServiceHandler());
			String secretId = secretService.storeSecret(geboToken, supportLogic.getType().getDescription() + " api key",
					apiKeyData.getApiKeySecretContext());
			GBaseModelConfig configuration = supportLogic.createBaseConfiguration(null);
			configuration.setApiSecretCode(secretId);
			configuration.setBaseUrl(apiKeyData.getBaseUrl());

			if (apiKeyData.getDoModelsLookup() != null && apiKeyData.getDoModelsLookup()) {
				OperationStatus modelsLookupStatus = supportLogic.getModelChoices(configuration);
				if (modelsLookupStatus.isHasErrorMessages()) {
					secretService.deleteSecret(secretId);
					return OperationStatus.ofError("Invalid credentials",
							"Cannot access provider service with entered credentials");
				}
			}
			return OperationStatus.of(secretService.getSecretInfoById(secretId));

		}
		case RANKING: {
			IGRankerModelConfigurationSupportService supportLogic = this.rankerModelsSupportRepo
					.findByCode(apiKeyData.getServiceHandler());
			String secretId = secretService.storeSecret(geboToken, supportLogic.getType().getDescription() + " api key",
					apiKeyData.getApiKeySecretContext());
			GBaseModelConfig configuration = supportLogic.createBaseConfiguration(null);
			configuration.setApiSecretCode(secretId);
			configuration.setBaseUrl(apiKeyData.getBaseUrl());

			if (apiKeyData.getDoModelsLookup() != null && apiKeyData.getDoModelsLookup()) {
				OperationStatus modelsLookupStatus = supportLogic.getModelChoices(configuration);
				if (modelsLookupStatus.isHasErrorMessages()) {
					secretService.deleteSecret(secretId);
					return OperationStatus.ofError("Invalid credentials",
							"Cannot access provider service with entered credentials");
				}
			}
			return OperationStatus.of(secretService.getSecretInfoById(secretId));

		}
		case IMAGESGEN: {
			IGImageModelConfigurationSupportService supportLogic = this.imageModelsSupportRepo
					.findByCode(apiKeyData.getServiceHandler());
			String secretId = secretService.storeSecret(geboToken, supportLogic.getType().getDescription() + " api key",
					apiKeyData.getApiKeySecretContext());
			GBaseModelConfig configuration = supportLogic.createBaseConfiguration(null);
			configuration.setApiSecretCode(secretId);
			configuration.setBaseUrl(apiKeyData.getBaseUrl());

			if (apiKeyData.getDoModelsLookup() != null && apiKeyData.getDoModelsLookup()) {
				OperationStatus modelsLookupStatus = supportLogic.getModelChoices(configuration);
				if (modelsLookupStatus.isHasErrorMessages()) {
					secretService.deleteSecret(secretId);
					return OperationStatus.ofError("Invalid credentials",
							"Cannot access provider service with entered credentials");
				}
			}
			return OperationStatus.of(secretService.getSecretInfoById(secretId));

		}
		case TRANSCRIPT: {
			IGTranscriptModelConfigurationSupportService supportLogic = this.transcriptModelsSupportRepo
					.findByCode(apiKeyData.getServiceHandler());
			String secretId = secretService.storeSecret(geboToken, supportLogic.getType().getDescription() + " api key",
					apiKeyData.getApiKeySecretContext());
			GBaseModelConfig configuration = supportLogic.createBaseConfiguration(null);
			configuration.setApiSecretCode(secretId);
			configuration.setBaseUrl(apiKeyData.getBaseUrl());

			if (apiKeyData.getDoModelsLookup() != null && apiKeyData.getDoModelsLookup()) {
				OperationStatus modelsLookupStatus = supportLogic.getModelChoices(configuration);
				if (modelsLookupStatus.isHasErrorMessages()) {
					secretService.deleteSecret(secretId);
					return OperationStatus.ofError("Invalid credentials",
							"Cannot access provider service with entered credentials");
				}
			}
			return OperationStatus.of(secretService.getSecretInfoById(secretId));

		}
		case TTS: {
			IGTextToSpeechModelConfigurationSupportService supportLogic = this.ttsModelsSupportRepo
					.findByCode(apiKeyData.getServiceHandler());
			String secretId = secretService.storeSecret(geboToken, supportLogic.getType().getDescription() + " api key",
					apiKeyData.getApiKeySecretContext());
			GBaseModelConfig configuration = supportLogic.createBaseConfiguration(null);
			configuration.setApiSecretCode(secretId);
			configuration.setBaseUrl(apiKeyData.getBaseUrl());

			if (apiKeyData.getDoModelsLookup() != null && apiKeyData.getDoModelsLookup()) {
				OperationStatus modelsLookupStatus = supportLogic.getModelChoices(configuration);
				if (modelsLookupStatus.isHasErrorMessages()) {
					secretService.deleteSecret(secretId);
					return OperationStatus.ofError("Invalid credentials",
							"Cannot access provider service with entered credentials");
				}
			}
			return OperationStatus.of(secretService.getSecretInfoById(secretId));

		}
		}
		throw new RuntimeException("This code zone has not to be reached");

	}

	public OperationStatus<List<GBaseModelConfig>> runAutoConfigure(
			@Valid @NotNull LLMAutoconfigureCreationData autoconfiguredata) throws GeboCryptSecretException {
		Optional<LLMSVendor> vendor = vendorsSetupConfig.getVendors().stream()
				.filter(x -> x.getVendorInfo().getVendorId().equals(autoconfiguredata.getVendorId())).findFirst();
		if (vendor.isPresent()) {
			LLMSVendor vendorData = vendor.get();
			String secretId = autoconfiguredata.getSecretId();
			if (secretId != null && secretId.trim().length() > 0) {

			} else {
				GeboTokenContent geboToken = new GeboTokenContent();
				geboToken.setToken(autoconfiguredata.getNewApiSecret());
				geboToken.setUser(autoconfiguredata.getNewUserName());
				secretId = secretService.storeSecret(geboToken, vendorData.getVendorInfo().getName() + " credentials",
						vendorData.getVendorInfo().getApiKeySecretContext());
			}
			List<LLMCreateModelData> configs = new ArrayList<LLMCreateModelData>();
			for (LLMSModelsPresets preset : vendorData.getPresets()) {
				switch (preset.getType()) {
				case CHAT: {
					// check if a default chat model is present and if not add one
					if (autoconfiguredata.getDefaultChatModel() != null
							&& autoconfiguredata.getDefaultChatModel().trim().length() > 0) {
						LLMCreateModelData modelData = createChatModel(secretId, vendorData,
								autoconfiguredata.getDefaultChatModel(), true, true, ChatModelsUses.CHAT);
						configs.add(modelData);
					}
					if (autoconfiguredata.getInternalServicesModel() != null
							&& autoconfiguredata.getInternalServicesModel().trim().length() > 0) {
						LLMCreateModelData modelData = createChatModel(secretId, vendorData,
								autoconfiguredata.getInternalServicesModel(), false, false,
								ChatModelsUses.INTERNAL_SERVICES);
						configs.add(modelData);
					}

				}
					break;

				case EMBEDDING: {
					// check if a default embedding model is present and if not add one
					if (autoconfiguredata.getEmbeddingModel() != null
							&& autoconfiguredata.getEmbeddingModel().trim().length() > 0) {
						LLMCreateModelData modelData = createEmbeddingModel(secretId, vendorData,
								autoconfiguredata.getEmbeddingModel());
						configs.add(modelData);
					}
				}
					break;
				case IMAGESGEN: {
					if (autoconfiguredata.getImagesModel() != null
							&& autoconfiguredata.getImagesModel().trim().length() > 0) {
						LLMCreateModelData modelData = this.createModel(secretId, vendorData,
								autoconfiguredata.getImagesModel(), preset.getType());
						configs.add(modelData);
					}
				}
					break;
				case RANKING: {
					if (autoconfiguredata.getRankerModel() != null
							&& autoconfiguredata.getRankerModel().trim().length() > 0) {
						LLMCreateModelData modelData = this.createModel(secretId, vendorData,
								autoconfiguredata.getRankerModel(), preset.getType());
						configs.add(modelData);
					}
				}
					break;
				case TRANSCRIPT: {
					if (autoconfiguredata.getTranscriptModel() != null
							&& autoconfiguredata.getTranscriptModel().trim().length() > 0) {
						LLMCreateModelData modelData = this.createModel(secretId, vendorData,
								autoconfiguredata.getTranscriptModel(), preset.getType());
						configs.add(modelData);
					}
				}
					break;
				case TTS: {
					if (autoconfiguredata.getTtsModel() != null
							&& autoconfiguredata.getTtsModel().trim().length() > 0) {
						LLMCreateModelData modelData = this.createModel(secretId, vendorData,
								autoconfiguredata.getTtsModel(), preset.getType());
						configs.add(modelData);
					}
				}
					break;
				}
			}
			OperationStatus<LLMSModelsCreationResult> creation = createLLMS(configs);
			OperationStatus<List<GBaseModelConfig>> out = new OperationStatus<>();
			out.getMessages().addAll(creation.getMessages());
			if (creation.getResult() != null) {
				out.setResult(creation.getResult().getCreated());
				for (LLMUnresolvedModel unresolved : creation.getResult().getUnresolved()) {
					out.getMessages()
							.add(GUserMessage.warnMessage("Model not available",
									"The " + unresolved.getType() + " model '" + unresolved.getRequestedModelCode()
											+ "' is no longer offered by the provider. Configure it from the LLMs admin screens."));
				}
			}
			return out;
		} else
			throw new RuntimeException("Vendor not found by data:" + autoconfiguredata);
	}

	private LLMCreateModelData createModel(String secretId, LLMSVendor vendorData, String model, ModelType type) {
		LLMCreateModelData md = new LLMCreateModelData();
		md.setEnableAllFunctions(false);
		md.setModelCode(model);
		md.setSecretId(secretId);
		LLMSModelsPresets preset = vendorData.getPresets().stream().filter(x -> x.getType() == type).toList().get(0);
		md.setServiceHandler(preset.getServiceHandler());
		md.setDoModelsLookup(preset.isDoModelsLookup());
		md.setSetAsDefaultModel(true);
		md.setType(type);
		return md;
	}

	private LLMCreateModelData createEmbeddingModel(String secretId, LLMSVendor vendorData, String embeddingModel) {
		LLMCreateModelData md = new LLMCreateModelData();
		md.setEnableAllFunctions(false);
		md.setModelCode(embeddingModel);
		md.setSecretId(secretId);
		LLMSModelsPresets embeddingPreset = vendorData.getPresets().stream()
				.filter(x -> x.getType() == ModelType.EMBEDDING).toList().get(0);
		md.setServiceHandler(embeddingPreset.getServiceHandler());
		md.setDoModelsLookup(embeddingPreset.isDoModelsLookup());
		md.setSetAsDefaultModel(true);
		md.setType(ModelType.EMBEDDING);

		return md;
	}

	private LLMCreateModelData createChatModel(String secretId, LLMSVendor vendorData, String chatModel,
			boolean defaultModel, boolean enableAllFunctions, ChatModelsUses use) {
		LLMCreateModelData md = new LLMCreateModelData();
		md.setEnableAllFunctions(enableAllFunctions);
		md.setModelCode(chatModel);
		md.setSecretId(secretId);
		LLMSModelsPresets chatPreset = vendorData.getPresets().stream().filter(x -> x.getType() == ModelType.CHAT)
				.toList().get(0);
		md.setServiceHandler(chatPreset.getServiceHandler());
		md.setDoModelsLookup(chatPreset.isDoModelsLookup());
		md.setSetAsDefaultModel(defaultModel);
		md.setType(ModelType.CHAT);
		md.setUses(List.of(use));
		// carry over preset-defined generation/thinking defaults for the chosen model
		chatPreset.getChoices().stream().filter(x -> chatModel.equals(x.getCode())).findFirst().ifPresent(choice -> {
			if (choice.getContextWindow() != null) {
				md.setContextWindow(choice.getContextWindow());
			}
			if (choice.getMaxGeneratedTokens() != null) {
				md.setMaxGeneratedTokens(choice.getMaxGeneratedTokens());
			}
			if (choice.getThinking() != null) {
				md.setThinking(choice.getThinking());
			}
		});
		return md;
	}

	public OperationStatus<List<GBaseModelChoice>> verifyCredentialsAndDownloadModels(
			@Valid @NotNull LLMCredentialsVerificationData data) {
		Optional<LLMSVendor> vendorOpt = this.vendorsSetupConfig.getVendors().stream()
				.filter(x -> x.getVendorInfo() != null && x.getVendorInfo().getVendorId() != null
						&& x.getVendorInfo().getVendorId().equals(data.getVendorId()))
				.findFirst();
		if (vendorOpt.isEmpty()) {
			return OperationStatus.ofError("Vendor unknown", "Vendor " + data.getVendorId() + " unknown");
		}
		LLMSVendor vendor = vendorOpt.get();
		if (!vendor.getPresets().isEmpty()) {
			LLMSModelsPresets preset = vendor.getPresets().get(0);
			LLMModelsLookupParameter credentials = new LLMModelsLookupParameter();
			credentials.setSecretId(data.getSecretId());
			credentials.setServiceHandler(preset.getServiceHandler());
			credentials.setBaseUrl(data.getBaseUrl());
			credentials.setType(preset.getType());
			return verifyCredentialsAndDownloadModels(credentials);
		} else {
			return OperationStatus.ofError("Vendor without presets",
					"Vendor " + data.getVendorId() + " has no presets");
		}
	}

	public OperationStatus<List<GBaseModelChoice>> verifyCredentialsAndDownloadModels(
			@Valid @NotNull LLMModelsLookupParameter credentials) {

		switch (credentials.getType()) {
		case CHAT: {
			IGChatModelConfigurationSupportService supportLogic = this.chatModelsSupportRepo
					.findByCode(credentials.getServiceHandler());

			GBaseModelConfig configuration = supportLogic.createBaseConfiguration(null);
			configuration.setApiSecretCode(credentials.getSecretId());
			configuration.setBaseUrl(credentials.getBaseUrl());
			return supportLogic.getModelChoices(configuration);

		}

		case EMBEDDING: {
			IGEmbeddingModelConfigurationSupportService supportLogic = this.embedModelsSupportRepo
					.findByCode(credentials.getServiceHandler());
			GBaseModelConfig configuration = supportLogic.createBaseConfiguration(null);
			configuration.setApiSecretCode(credentials.getSecretId());
			configuration.setBaseUrl(credentials.getBaseUrl());
			return supportLogic.getModelChoices(configuration);
		}
		case RANKING: {
			IGRankerModelConfigurationSupportService supportLogic = this.rankerModelsSupportRepo
					.findByCode(credentials.getServiceHandler());

			GBaseModelConfig configuration = supportLogic.createBaseConfiguration(null);
			configuration.setApiSecretCode(credentials.getSecretId());
			configuration.setBaseUrl(credentials.getBaseUrl());
			return supportLogic.getModelChoices(configuration);
		}
		case IMAGESGEN: {
			IGImageModelConfigurationSupportService supportLogic = this.imageModelsSupportRepo
					.findByCode(credentials.getServiceHandler());
			GBaseModelConfig configuration = supportLogic.createBaseConfiguration(null);
			configuration.setApiSecretCode(credentials.getSecretId());
			configuration.setBaseUrl(credentials.getBaseUrl());
			return supportLogic.getModelChoices(configuration);
		}
		case TRANSCRIPT: {
			IGTranscriptModelConfigurationSupportService supportLogic = this.transcriptModelsSupportRepo
					.findByCode(credentials.getServiceHandler());
			GBaseModelConfig configuration = supportLogic.createBaseConfiguration(null);
			configuration.setApiSecretCode(credentials.getSecretId());
			configuration.setBaseUrl(credentials.getBaseUrl());
			return supportLogic.getModelChoices(configuration);
		}
		case TTS: {
			IGTextToSpeechModelConfigurationSupportService supportLogic = this.ttsModelsSupportRepo
					.findByCode(credentials.getServiceHandler());
			GBaseModelConfig configuration = supportLogic.createBaseConfiguration(null);
			configuration.setApiSecretCode(credentials.getSecretId());
			configuration.setBaseUrl(credentials.getBaseUrl());
			return supportLogic.getModelChoices(configuration);
		}
		}
		throw new RuntimeException("This code zone has not to be reached");
	}

	/**
	 * Configures a model only after confirming its code is still offered by the
	 * provider. We ask the provider for its live model list; if the requested code
	 * (e.g. a preset from the vendor .yml) is present we create it, if the list is
	 * available but the code is missing we record it as unresolved together with the
	 * available choices so the UI can offer a replacement, and if the list cannot be
	 * obtained we fall back to the standard insert (which reports the provider's own
	 * messages).
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void configureModelValidating(IGModelConfigurationSupportService supportLogic,
			GBaseModelConfig configuration, LLMCreateModelData req,
			List<OperationStatus<GBaseModelConfig>> operationsOutput, LLMSModelsCreationResult result) {
		// The .yml preset declares doModelsLookup=false for model types the provider does
		// not enumerate through its models endpoint (typically image/tts/transcript). For
		// those, validating the requested code against getModelChoices produces false
		// negatives (the model is valid but simply not listed), so we trust the preset
		// code and create directly. Validation runs only when the preset opts into lookup.
		boolean doLookup = req.getDoModelsLookup() != null && req.getDoModelsLookup();
		if (!doLookup) {
			createDirect(supportLogic, configuration, operationsOutput);
			return;
		}
		OperationStatus choicesStatus = supportLogic.getModelChoices(configuration);
		List<GBaseModelChoice> choices = (List<GBaseModelChoice>) choicesStatus.getResult();
		boolean listAvailable = !choicesStatus.isHasErrorMessages() && choices != null && !choices.isEmpty();
		if (!listAvailable) {
			// The provider does not actually enumerate this model type (empty list or a
			// failed listing): trust the configured code and create it directly rather
			// than silently doing nothing.
			createDirect(supportLogic, configuration, operationsOutput);
			return;
		}
		boolean exists = choices.stream()
				.anyMatch(c -> c.getCode() != null && c.getCode().equalsIgnoreCase(req.getModelCode()));
		if (!exists) {
			LOGGER.warn("Requested model '{}' ({}) is not offered by the provider anymore", req.getModelCode(),
					req.getServiceHandler());
			LLMUnresolvedModel unresolved = new LLMUnresolvedModel();
			unresolved.setType(req.getType());
			unresolved.setUses(req.getUses());
			unresolved.setServiceHandler(req.getServiceHandler());
			unresolved.setRequestedModelCode(req.getModelCode());
			unresolved.setAvailableChoices(choices);
			result.getUnresolved().add(unresolved);
			return;
		}
		try {
			operationsOutput.add(supportLogic.insertAndConfigureModel(configuration, req.getModelCode()));
		} catch (Throwable th) {
			operationsOutput.add(OperationStatus.of(th));
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createDirect(IGModelConfigurationSupportService supportLogic, GBaseModelConfig configuration,
			List<OperationStatus<GBaseModelConfig>> operationsOutput) {
		try {
			operationsOutput.add(supportLogic.insertAndConfigure(configuration));
		} catch (Throwable th) {
			operationsOutput.add(OperationStatus.of(th));
		}
	}

	public OperationStatus<LLMSModelsCreationResult> createLLMS(List<LLMCreateModelData> configs) {

		LLMSModelsCreationResult result = new LLMSModelsCreationResult();
		List<OperationStatus<GBaseModelConfig>> operationsOutput = new ArrayList<>();
		for (LLMCreateModelData config : configs) {
			// The guided flows (easy tab / suggested presets) gate by existence in the UI,
			// so they never resend an already-configured kind. The expert Advanced tab may
			// deliberately add extra models and override the default, so the requested
			// setAsDefaultModel / uses are honoured as-is.
			switch (config.getType()) {
			case CHAT: {
				try {
					IGChatModelConfigurationSupportService supportLogic = this.chatModelsSupportRepo
							.findByCode(config.getServiceHandler());
					GBaseChatModelConfig configuration = (GBaseChatModelConfig) supportLogic
							.createBaseConfiguration(config.getModelCode());
					configuration.setApiSecretCode(config.getSecretId());
					configuration.setBaseUrl(config.getBaseUrl());
					configuration.setDefaultModel(config.getSetAsDefaultModel());
					configuration.setAccessibleToAll(true);
					configuration.setForUses(config.getUses());
					if (config.getContextWindow() != null) {
						configuration.setContextLength(config.getContextWindow());
					}
					if (config.getMaxGeneratedTokens() != null) {
						configuration.setMaxGeneratedTokens(config.getMaxGeneratedTokens());
					}
					if (config.getThinking() != null) {
						configuration.setThinking(config.getThinking());
					}
					if (config.getEnableAllFunctions() != null && config.getEnableAllFunctions()) {
						List<ToolCallback> tools = this.toolsRepo.getTools();
						List<String> names = tools.stream().map(x -> {
							return x.getToolDefinition().name();
						}).toList();
						configuration.setEnabledFunctions(names);
					}
					configureModelValidating(supportLogic, configuration, config, operationsOutput, result);
				} catch (Throwable th) {
					operationsOutput.add(OperationStatus.of(th));
				}

			}
				break;

			case EMBEDDING: {
				try {
					IGEmbeddingModelConfigurationSupportService supportLogic = this.embedModelsSupportRepo
							.findByCode(config.getServiceHandler());
					GBaseModelConfig configuration = supportLogic.createBaseConfiguration(config.getModelCode());
					configuration.setApiSecretCode(config.getSecretId());
					configuration.setBaseUrl(config.getBaseUrl());
					configuration.setDefaultModel(config.getSetAsDefaultModel());
					configureModelValidating(supportLogic, configuration, config, operationsOutput, result);
				} catch (Throwable th) {
					operationsOutput.add(OperationStatus.of(th));
				}
			}
				break;
			case RANKING: {
				try {
					IGRankerModelConfigurationSupportService supportLogic = this.rankerModelsSupportRepo
							.findByCode(config.getServiceHandler());
					GBaseModelConfig configuration = supportLogic.createBaseConfiguration(config.getModelCode());
					configuration.setApiSecretCode(config.getSecretId());
					configuration.setBaseUrl(config.getBaseUrl());
					configuration.setDefaultModel(config.getSetAsDefaultModel());
					configureModelValidating(supportLogic, configuration, config, operationsOutput, result);
				} catch (Throwable th) {
					operationsOutput.add(OperationStatus.of(th));
				}
			}
				break;
			case TRANSCRIPT: {
				try {
					IGTranscriptModelConfigurationSupportService supportLogic = this.transcriptModelsSupportRepo
							.findByCode(config.getServiceHandler());
					GBaseModelConfig configuration = supportLogic.createBaseConfiguration(config.getModelCode());
					configuration.setApiSecretCode(config.getSecretId());
					configuration.setBaseUrl(config.getBaseUrl());
					configuration.setDefaultModel(config.getSetAsDefaultModel());
					configureModelValidating(supportLogic, configuration, config, operationsOutput, result);
				} catch (Throwable th) {
					operationsOutput.add(OperationStatus.of(th));
				}
			}
				break;
			case TTS: {
				try {
					IGTextToSpeechModelConfigurationSupportService supportLogic = this.ttsModelsSupportRepo
							.findByCode(config.getServiceHandler());
					GBaseModelConfig configuration = supportLogic.createBaseConfiguration(config.getModelCode());
					configuration.setApiSecretCode(config.getSecretId());
					configuration.setBaseUrl(config.getBaseUrl());
					configuration.setDefaultModel(config.getSetAsDefaultModel());
					configureModelValidating(supportLogic, configuration, config, operationsOutput, result);
				} catch (Throwable th) {
					operationsOutput.add(OperationStatus.of(th));
				}
			}
				break;
			case IMAGESGEN: {
				try {
					IGImageModelConfigurationSupportService supportLogic = this.imageModelsSupportRepo
							.findByCode(config.getServiceHandler());
					GBaseModelConfig configuration = supportLogic.createBaseConfiguration(config.getModelCode());
					configuration.setApiSecretCode(config.getSecretId());
					configuration.setBaseUrl(config.getBaseUrl());
					configuration.setDefaultModel(config.getSetAsDefaultModel());
					configureModelValidating(supportLogic, configuration, config, operationsOutput, result);
				} catch (Throwable th) {
					operationsOutput.add(OperationStatus.of(th));
				}
			}
				break;
			}

		}
		OperationStatus<LLMSModelsCreationResult> out = new OperationStatus<>();
		out.setResult(result);
		for (OperationStatus<GBaseModelConfig> res : operationsOutput) {
			if (res.getResult() != null) {
				result.getCreated().add(res.getResult());
			}
			if (res.isHasErrorMessages()) {
				out.getMessages().addAll(res.getMessages());
			}
		}

		return out;
	}

}