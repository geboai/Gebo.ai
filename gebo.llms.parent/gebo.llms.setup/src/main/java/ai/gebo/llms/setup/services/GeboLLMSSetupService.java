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

import org.slf4j.Logger;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.IGeboCryptingService;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatProfileManagementService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRuntimeChatProfileChatModelDao;
import ai.gebo.llms.setup.config.LLMSModelsPresets;
import ai.gebo.llms.setup.config.LLMSVendor;
import ai.gebo.llms.setup.config.LLMSVendorsSetupConfig;
import ai.gebo.llms.setup.config.ModelType;
import ai.gebo.llms.setup.model.ComponentLLMSStatus;
import ai.gebo.llms.setup.model.LLMCreateModelData;
import ai.gebo.llms.setup.model.LLMCredentialsCreationData;
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
	final IGSecurityService securityService;
	final IGChatModelRuntimeConfigurationDao chatModelsConfigDao;
	final IGChatModelConfigurationSupportServiceRepositoryPattern chatModelsSupportRepo;
	final IGeboCryptingService cryptService;
	final IGEmbeddingModelRuntimeConfigurationDao embeddingModelsConfigDao;
	final IGEmbeddingModelConfigurationSupportServiceRepositoryPattern embedModelsSupportRepo;
	final IGToolCallbackSourceRepositoryPattern functionsCallbackWrapper;
	final IGPersistentObjectManager persistenceManager;
	final IGeboSecretsAccessService secretService;
	final IGChatProfileManagementService chatProfileManagementService;
	final IGRuntimeChatProfileChatModelDao chatProfileChatModelDao;
	final LLMSVendorsSetupConfig vendorsSetupConfig;
	final IGToolCallbackSourceRepositoryPattern toolsRepo;
	// Sample text for testing embedding model configurations.
	static final String embeddingText4Test = "By default, the length of the embedding vector will be 1536 for text-embedding-3-small or 3072 for text-embedding-3-large. You can reduce the dimensions of the embedding by passing in the dimensions parameter without the embedding losing its concept-representing properties. We go into more detail on embedding dimensions in the embedding use case section.";
	// Logger instance to log messages.
	static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(GeboLLMSSetupService.class);

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
							vendorData.getRuntimeConfigs().add(existingConfiguration);
						}
					}
				}
					break;
				case EMBEDDING: {
					IGEmbeddingModelConfigurationSupportService handler = embedModelsSupportRepo
							.findByCode(preset.getServiceHandler());
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
							vendorData.getRuntimeConfigs().add(existingConfiguration);
						}
					}
				}
					break;
				}
			}
			configData.getConfigurations().add(vendorData);
		}
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

		}
		throw new RuntimeException("This code zone has not to be reached");

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
					GBaseChatModelConfig configuration =(GBaseChatModelConfig) supportLogic.createBaseConfiguration(config.getModelCode());
					configuration.setApiSecretCode(config.getSecretId());
					configuration.setBaseUrl(config.getBaseUrl());
					configuration.setDefaultModel(config.getSetAsDefaultModel());
					configuration.setAccessibleToAll(true);
					if (config.getEnableAllFunctions()!=null && config.getEnableAllFunctions()) {
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
			}

		}
		OperationStatus<List<GBaseModelConfig>> out=new OperationStatus<>();	
		for(OperationStatus<GBaseModelConfig> res:operationsOutput) {
			if (res.getResult()!=null) {
				if (out.getResult()==null) {
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