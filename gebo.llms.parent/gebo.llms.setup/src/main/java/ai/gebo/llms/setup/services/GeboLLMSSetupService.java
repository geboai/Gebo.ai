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

	public LLMSSetupConfigurationData getActualConfiguration() throws GeboCryptSecretException {
		LLMSSetupConfigurationData configData = new LLMSSetupConfigurationData();
		boolean canRunAutoconfigure = false;
		boolean embeddingModelExists = false;
		boolean rankerModelExists = false;
		boolean imagesModelExists = false;
		boolean ttsModelExists = false;
		boolean transcriptModelExists = false;
		boolean defaultChatModelExists = false;
		boolean internalServicesChatModelExists = false;

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
						LOGGER.warn("The CHAT Handler " + preset.getServiceHandler() + " is not present or started up");
						continue;
					}
					GModelType modelProviderType = handler.getType();
					List<IGConfigurableChatModel> chatConfigurations = chatModelsConfigDao.getConfigurations();
					for (IGConfigurableChatModel chatModel : chatConfigurations) {

						if (chatModel.getConfig() instanceof GBaseChatModelConfig chatModelConfig) {
							if (chatModelConfig.getDefaultModel() != null && chatModelConfig.getDefaultModel()) {
								defaultChatModelExists = true;
							}
							if (chatModelConfig.getForUses() != null
									&& chatModelConfig.getForUses().contains(ChatModelsUses.INTERNAL_SERVICES)) {
								internalServicesChatModelExists = true;
							}
						}
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
						LOGGER.warn("The EMBEDDING Handler " + preset.getServiceHandler()
								+ " is not present or started up");
						continue;
					}
					GModelType modelProviderType = handler.getType();
					List<IGConfigurableEmbeddingModel> embeddingConfigurations = embeddingModelsConfigDao
							.getConfigurations();
					for (IGConfigurableEmbeddingModel embeddingModel : embeddingConfigurations) {
						if (embeddingModel.getConfig().getDefaultModel() != null
								&& embeddingModel.getConfig().getDefaultModel()) {
							embeddingModelExists = true;
						}
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
						LOGGER.warn("The EMBEDDING Handler " + preset.getServiceHandler()
								+ " is not present or started up");
						continue;
					}
					GModelType modelProviderType = handler.getType();
					List<IGConfigurableRankerModel> configurations = this.rankerModelsRuntimeDao.getConfigurations();
					for (IGConfigurableRankerModel thisModel : configurations) {
						if (thisModel.getConfig().getDefaultModel() != null
								&& thisModel.getConfig().getDefaultModel()) {
							rankerModelExists = true;
						}
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
						LOGGER.warn("The EMBEDDING Handler " + preset.getServiceHandler()
								+ " is not present or started up");
						continue;
					}
					GModelType modelProviderType = handler.getType();
					List<IGConfigurableImageModel> configurations = this.imageModelRuntimeDao.getConfigurations();
					for (IGConfigurableImageModel thisModel : configurations) {
						if (thisModel.getConfig().getDefaultModel() != null
								&& thisModel.getConfig().getDefaultModel()) {
							imagesModelExists = true;
						}
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
						LOGGER.warn("The EMBEDDING Handler " + preset.getServiceHandler()
								+ " is not present or started up");
						continue;
					}
					GModelType modelProviderType = handler.getType();
					List<IGConfigurableTranscriptModel> configurations = this.transcriptModelsRuntimeDao
							.getConfigurations();
					for (IGConfigurableTranscriptModel thisModel : configurations) {
						if (thisModel.getConfig().getDefaultModel() != null
								&& thisModel.getConfig().getDefaultModel()) {
							transcriptModelExists = true;
						}
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
						LOGGER.warn("The EMBEDDING Handler " + preset.getServiceHandler()
								+ " is not present or started up");
						continue;
					}
					GModelType modelProviderType = handler.getType();
					List<IGConfigurableTextToSpeechModel> configurations = this.ttsModelsRuntimeDao.getConfigurations();
					for (IGConfigurableTextToSpeechModel thisModel : configurations) {
						if (thisModel.getConfig().getDefaultModel() != null
								&& thisModel.getConfig().getDefaultModel()) {
							ttsModelExists = true;
						}
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

		configData.setDefaultChatModelExists(defaultChatModelExists);
		configData.setEmbeddingModelExists(embeddingModelExists);
		configData.setInternalServicesChatModelExists(internalServicesChatModelExists);
		configData.setImagesModelExists(imagesModelExists);
		configData.setRankerModelExists(rankerModelExists);
		configData.setTranscriptModelExists(transcriptModelExists);
		configData.setTtsModelExists(ttsModelExists);
		canRunAutoconfigure = !(defaultChatModelExists && embeddingModelExists);
		configData.setCanRunAutoconfigure(canRunAutoconfigure);
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
		status.chatModelSetup = !chatModelsConfigDao.getConfigurations().isEmpty();
		status.embeddedModelSetup = !embeddingModelsConfigDao.getConfigurations().isEmpty();
		status.rankingModelSetup = !rankerModelsRuntimeDao.getConfigurations().isEmpty();
		status.ttsModelSetup = !ttsModelsRuntimeDao.getConfigurations().isEmpty();
		status.transcriptModelSetup = !transcriptModelsRuntimeDao.getConfigurations().isEmpty();
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
			return createLLMS(configs);
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

	public OperationStatus<List<GBaseModelConfig>> createLLMS(List<LLMCreateModelData> configs) {

		List<OperationStatus<GBaseModelConfig>> operationsOutput = new ArrayList<>();
		for (LLMCreateModelData config : configs) {
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
					operationsOutput.add(supportLogic.insertAndConfigureModel(configuration, config.getModelCode()));
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
					operationsOutput.add(supportLogic.insertAndConfigureModel(configuration, config.getModelCode()));
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
					operationsOutput.add(supportLogic.insertAndConfigureModel(configuration, config.getModelCode()));
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
					operationsOutput.add(supportLogic.insertAndConfigureModel(configuration, config.getModelCode()));
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
					operationsOutput.add(supportLogic.insertAndConfigureModel(configuration, config.getModelCode()));
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
					operationsOutput.add(supportLogic.insertAndConfigureModel(configuration, config.getModelCode()));
				} catch (Throwable th) {
					operationsOutput.add(OperationStatus.of(th));
				}
			}
				break;
			}

		}
		OperationStatus<List<GBaseModelConfig>> out = new OperationStatus<>();
		for (OperationStatus<GBaseModelConfig> res : operationsOutput) {
			if (res.getResult() != null) {
				if (out.getResult() == null) {
					out.setResult(new ArrayList<>());
				}
				out.getResult().add(res.getResult());
			}
			if (res.isHasErrorMessages()) {
				out.getMessages().addAll(res.getMessages());
			}
		}

		return out;
	}

}