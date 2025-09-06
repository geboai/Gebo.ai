/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.abstraction.layer.model;

import java.util.List;

import ai.gebo.model.IGObjectWithSecurity;

/**
 * AI generated comments The GBaseChatModelConfig class represents the
 * configuration for a chat model. It includes various configurable parameters
 * related to security, accessibility, and model behavior.
 *
 * @param <ModelChoice> A type parameter that extends GBaseChatModelChoice.
 */
public class GBaseChatModelConfig<ModelChoice extends GBaseChatModelChoice> extends GBaseModelConfig<ModelChoice>
		implements IGObjectWithSecurity {

	/**
	 * The probability threshold used for sampling, null by default.
	 */
	private Double topP = null;

	/**
	 * A list of group identifiers that have access to the model configuration.
	 */
	protected List<String> accessibleGroups = null;

	/**
	 * A list of user identifiers that have access to the model configuration.
	 */
	protected List<String> accessibleUsers = null;

	/**
	 * A boolean flag indicating if access is granted to all users.
	 */
	protected Boolean accessibleToAll = null;

	/**
	 * A list of functions that are enabled for direct chat interaction.
	 */
	protected List<String> enabledFunctions = null;

	/**
	 * The temperature setting of the model, which affects randomness in responses.
	 * Null by default.
	 */
	protected Double temperature = null;

	/**
	 * The context length setting of the model, null by default.
	 */
	protected Integer contextLength = null;

	/**
	 * The default prompt used when interacting with the model.
	 */
	protected String defaultModelPrompt = null;
	/***
	 * Signes the specific uses for wich the model can work and being a default
	 */
	protected List<ChatModelsUses> forUses = null;

	/**
	 * Default constructor for GBaseChatModelConfig.
	 */
	public GBaseChatModelConfig() {

	}

	/**
	 * Gets the probability threshold for sampling.
	 *
	 * @return The topP value.
	 */
	public Double getTopP() {
		return topP;
	}

	/**
	 * Sets the probability threshold for sampling.
	 *
	 * @param topP The topP value to set.
	 */
	public void setTopP(Double topP) {
		this.topP = topP;
	}

	/**
	 * Gets the list of accessible groups.
	 *
	 * @return The list of accessible groups.
	 */
	public List<String> getAccessibleGroups() {
		return accessibleGroups;
	}

	/**
	 * Sets the list of accessible groups.
	 *
	 * @param accessibleGroups The list of accessible groups to set.
	 */
	public void setAccessibleGroups(List<String> accessibleGroups) {
		this.accessibleGroups = accessibleGroups;
	}

	/**
	 * Gets the list of accessible users.
	 *
	 * @return The list of accessible users.
	 */
	public List<String> getAccessibleUsers() {
		return accessibleUsers;
	}

	/**
	 * Sets the list of accessible users.
	 *
	 * @param accessibleUsers The list of accessible users to set.
	 */
	public void setAccessibleUsers(List<String> accessibleUsers) {
		this.accessibleUsers = accessibleUsers;
	}

	/**
	 * Checks if the configuration is accessible to all users.
	 *
	 * @return True if accessible to all, otherwise false.
	 */
	public Boolean getAccessibleToAll() {
		return accessibleToAll;
	}

	/**
	 * Sets the access flag to indicate if the configuration is accessible to all
	 * users.
	 *
	 * @param accessibleToAll Boolean flag to set.
	 */
	public void setAccessibleToAll(Boolean accessibleToAll) {
		this.accessibleToAll = accessibleToAll;
	}

	/**
	 * Gets the list of enabled functions for direct chat.
	 *
	 * @return The list of enabled functions.
	 */
	public List<String> getEnabledFunctions() {
		return enabledFunctions;
	}

	/**
	 * Sets the list of enabled functions for direct chat.
	 *
	 * @param directChatEnabledFunctions The list of functions to enable.
	 */
	public void setEnabledFunctions(List<String> directChatEnabledFunctions) {
		this.enabledFunctions = directChatEnabledFunctions;
	}

	/**
	 * Gets the temperature setting of the model.
	 *
	 * @return The temperature value.
	 */
	public Double getTemperature() {
		return temperature;
	}

	/**
	 * Sets the temperature setting of the model.
	 *
	 * @param temperature The temperature value to set.
	 */
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	/**
	 * Gets the default model prompt.
	 *
	 * @return The default model prompt.
	 */
	public String getDefaultModelPrompt() {
		return defaultModelPrompt;
	}

	/**
	 * Sets the default model prompt.
	 *
	 * @param defaultModelPrompt The default model prompt to set.
	 */
	public void setDefaultModelPrompt(String defaultModelPrompt) {
		this.defaultModelPrompt = defaultModelPrompt;
	}

	/**
	 * Gets the context length setting of the model.
	 *
	 * @return The context length value.
	 */
	public Integer getContextLength() {
		return contextLength;
	}

	/**
	 * Sets the context length setting of the model.
	 *
	 * @param contextLength The context length value to set.
	 */
	public void setContextLength(Integer contextLength) {
		this.contextLength = contextLength;
	}

	public List<ChatModelsUses> getForUses() {
		return forUses;
	}

	public void setForUses(List<ChatModelsUses> forUses) {
		this.forUses = forUses;
	}

}