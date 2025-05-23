/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.services;

import java.util.List;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.model.ChatProfileRuntimeEnvironment;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;

/**
 * Gebo.ai comment agent
 * 
 * Interface for managing chat profile configurations and environments.
 * Provides methods to create and retrieve chat environments and profiles.
 */
public interface IGChatProfileManagementService {

	/**
	 * Creates a chat environment using the provided chat profile configuration.
	 *
	 * @param configuration the configuration for the chat profile
	 * @return the created ChatProfileRuntimeEnvironment
	 * @throws LLMConfigException if there is an error in the configuration
	 */
	public ChatProfileRuntimeEnvironment createChatEnvironment(GChatProfileConfiguration configuration)
			throws LLMConfigException;

	/**
	 * Retrieves a list of chat profile configurations that the user has access to.
	 *
	 * @return a list of accessible GChatProfileConfiguration instances
	 * @throws LLMConfigException if there is an error retrieving the profiles
	 */
	public List<GChatProfileConfiguration> getAccessibleChatprofiles() throws LLMConfigException;

	/**
	 * Retrieves the default chat profile configuration. If it does not exist,
	 * it creates one.
	 *
	 * @return the default GChatProfileConfiguration
	 * @throws LLMConfigException if there is an error in creating or retrieving the default profile
	 */
	public GChatProfileConfiguration getOrCreateDefaultChatProfile() throws LLMConfigException;

}