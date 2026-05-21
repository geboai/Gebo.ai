/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.ToolCategoriesTree;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboRagSearchConfig;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatProfileManagementService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRuntimeChatProfileChatModelDao;
import ai.gebo.security.services.IGSecurityService;
import lombok.AllArgsConstructor;

/**
 * AI generated comments Implementation of IGChatProfileManagementService that
 * handles the management and configuration of chat profiles in the system.
 */
@Service
@AllArgsConstructor
public class GChatProfileManagementServiceImpl implements IGChatProfileManagementService {

	protected final IGRuntimeChatProfileChatModelDao chatProfileModelsDao;
	protected final IGEmbeddingModelRuntimeConfigurationDao embeddingModelsDao;
	protected final ChatProfilesRepository chatProfilesRepository;
	protected final IGSecurityService securityService;
	protected final IGPromptConfigDao promptConfigDao;
	protected final IGToolCallbackSourceRepositoryPattern functionsCallbackWrapper;
	protected final GeboRagSearchConfig ragConfig;


	/**
	 * Retrieves all accessible chat profiles filtered by security permissions.
	 * 
	 * @return A list of GChatProfileConfiguration objects that are accessible.
	 * @throws LLMConfigException If there is an issue retrieving the profiles.
	 */
	@Override
	public List<GChatProfileConfiguration> getAccessibleChatprofiles() throws LLMConfigException {
		// Fetch all chat profiles
		List<GChatProfileConfiguration> profiles = chatProfilesRepository.findAll();

		// Filter profiles by accessibility according to security service
		return securityService.filterAccessible(profiles, true);
	}

	/**
	 * Retrieves the default chat profile or creates one if it does not exist.
	 * 
	 * @return The default GChatProfileConfiguration.
	 * @throws LLMConfigException If there is an issue during configuration or
	 *                            retrieval.
	 */
	@Override
	public GChatProfileConfiguration getOrCreateDefaultChatProfile() throws LLMConfigException {
		// Attempt to retrieve the existing default chat profile
		Optional<GChatProfileConfiguration> existing = this.chatProfilesRepository
				.findById(GChatProfileConfiguration.DEFAULT_CHAT_PROFILE_CODE);
		if (existing.isPresent())
			return existing.get();

		// Retrieve or create default prompt configuration
		GPromptTemplateConfig prompt = promptConfigDao.defaultChatPrompt(true);
		if (prompt == null)
			throw new LLMConfigException("Default prompt is not configured in this system");

		// Create and configure a new default chat profile
		GChatProfileConfiguration configuration = new GChatProfileConfiguration();
		configuration.setPrompt(prompt.getUserPromptTemplate());
		configuration.setUserChoosesKnowledgeBases(true);
		configuration.setAccessibleToAll(true);
		configuration.setChatModelReference(null);
		configuration.setEmbeddingModelReference(null);
		configuration.setDescription("Chat with knowledge bases");
		configuration.setTopK(ragConfig.getDefaultTopK());
		configuration.setSimilaritySearchThreshold(ragConfig.getDefaultSimilarityThreshold());
		configuration.setOtherSearchSimilarityThreshold(ragConfig.getDefaultSimilarityThreshold());
		// Add enabled functions to the configuration
		List<ToolCategoriesTree> functionsTrees = functionsCallbackWrapper.getAllToolsTree(true);
		List<String> functions = new ArrayList<String>();
		for (ToolCategoriesTree functionCategoriesTree : functionsTrees) {
			if (functionCategoriesTree.getToolsReference() != null) {
				// Add each tool reference name to the list of functions
				for (ToolReference fr : functionCategoriesTree.getToolsReference()) {
					functions.add(fr.getName());
				}
			}
		}
		configuration.setEnabledFunctions(functions);
		configuration.setCode(GChatProfileConfiguration.DEFAULT_CHAT_PROFILE_CODE);

		// Save the new default chat profile to the repository
		this.chatProfilesRepository.insert(configuration);
		return configuration;
	}
}