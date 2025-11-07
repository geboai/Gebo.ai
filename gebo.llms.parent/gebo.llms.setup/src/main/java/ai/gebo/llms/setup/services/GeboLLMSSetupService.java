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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.crypting.services.IGeboCryptingService;
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
import ai.gebo.llms.setup.model.LLMExistingConfiguration;
import ai.gebo.llms.setup.model.LLMSSetupConfigurationData;
import ai.gebo.llms.setup.model.LLMSSetupConfigurationData.LLMSSetupConfiguration;
import ai.gebo.llms.setup.model.LLMSSetupConfigurationModificationData;
import ai.gebo.llms.setup.model.LLMSSetupModificationResult;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.services.IGSecurityService;
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

	public OperationStatus<LLMSSetupModificationResult> applyModification(
			LLMSSetupConfigurationModificationData modification) {
		return null;
	}

	public LLMSVendorsSetupConfig getSetupPresets() {
		return null;
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
}