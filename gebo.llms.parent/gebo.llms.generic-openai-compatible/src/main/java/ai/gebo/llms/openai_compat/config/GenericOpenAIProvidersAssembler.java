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
 * Configuration class responsible for assembling and registering OpenAI-compatible
 * chat and embedding model providers to their respective repositories.
 * This class handles the configuration and setup of both generic OpenAI API chat models
 * and embedding models based on configuration provided.
 */
package ai.gebo.llms.openai_compat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import ai.gebo.architecture.ai.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryProvider;
import ai.gebo.llms.openai.api.utils.IGOpenAIApiUtil;
import ai.gebo.llms.openai_compat.model.GenericOpenAIChatModelTypeConfig;
import ai.gebo.llms.openai_compat.model.GenericOpenAIEmbeddingModelTypeConfig;
import ai.gebo.llms.openai_compat.services.GenericOpenAIAPIChatModelConfigurationSupportService;
import ai.gebo.llms.openai_compat.services.GenericOpenAIAPIEmbeddingModelConfigurationSupportService;
import ai.gebo.llms.openai_compat.services.ModelsListProviderProxyService;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import jakarta.annotation.PostConstruct;

@Configuration
public class GenericOpenAIProvidersAssembler {
	/**
	 * Configuration containing OpenAI-compatible providers settings
	 */
	@Autowired
	GenericOpenAICompatibleProvidersConfig config;
	
	/**
	 * Repository for chat model configuration support services
	 */
	@Autowired
	IGChatModelConfigurationSupportServiceRepositoryPattern chatModelProvidersRepo;
	
	/**
	 * Repository for embedding model configuration support services
	 */
	@Autowired
	IGEmbeddingModelConfigurationSupportServiceRepositoryPattern embeddingModelProvidersRepo;
	
	/**
	 * Service for accessing secrets required by model providers
	 */
	@Autowired
	IGeboSecretsAccessService secretService;
	
	/**
	 * Utility for interacting with OpenAI API
	 */
	@Autowired
	IGOpenAIApiUtil openaiApiUtil;
	
	/**
	 * Repository for tool/function callbacks
	 */
	@Autowired
	IGToolCallbackSourceRepositoryPattern functionsRepo;
	
	/**
	 * Provider for vector store factories
	 */
	@Autowired
	IGVectorStoreFactoryProvider storeFactoryProvider;
	
	/**
	 * Service that provides model listing capabilities
	 */
	@Autowired
	ModelsListProviderProxyService modelsListProxyService;
	
	/**
	 * Factory for creating LLM service clients
	 */
	@Autowired
	IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;

	/**
	 * Initializes and registers all configured OpenAI-compatible providers.
	 * This method creates provider instances for both chat and embedding models
	 * and adds them to their respective repositories.
	 */
	@PostConstruct
	public void assemble() {
		// Initialize chat model providers if configured
		if (config.getChatModelProviders() != null) {
			for (GenericOpenAIChatModelTypeConfig pc : config.getChatModelProviders()) {
				GenericOpenAIAPIChatModelConfigurationSupportService provider = new GenericOpenAIAPIChatModelConfigurationSupportService(
						pc, secretService, openaiApiUtil, functionsRepo, modelsListProxyService,
						serviceClientsProviderFactory);
				chatModelProvidersRepo.addImplementation(provider);
			}
		}
		
		// Initialize embedding model providers if configured
		if (config.getEmbeddingModelProviders() != null) {
			for (GenericOpenAIEmbeddingModelTypeConfig pc : config.getEmbeddingModelProviders()) {
				GenericOpenAIAPIEmbeddingModelConfigurationSupportService provider = new GenericOpenAIAPIEmbeddingModelConfigurationSupportService(
						pc, secretService, openaiApiUtil, functionsRepo, storeFactoryProvider, modelsListProxyService,
						serviceClientsProviderFactory);
				embeddingModelProvidersRepo.addImplementation(provider);
			}
		}
	}
}