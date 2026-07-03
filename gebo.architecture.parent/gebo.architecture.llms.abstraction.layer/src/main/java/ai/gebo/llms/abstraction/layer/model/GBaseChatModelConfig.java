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

import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.model.IGObjectWithSecurity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * AI generated comments The GBaseChatModelConfig class represents the
 * configuration for a chat model. It includes various configurable parameters
 * related to security, accessibility, and model behavior.
 *
 * @param <ModelChoice> A type parameter that extends GBaseChatModelChoice.
 */
@Data
public class GBaseChatModelConfig<ModelChoice extends GBaseChatModelChoice> extends GBaseModelConfig<ModelChoice>
		implements IGObjectWithSecurity, IAclGrantedResource {
	@AllArgsConstructor
	@Getter
	public static enum ChatModelThinkingOption {
		NO_THINKING("Disable thinking"), LOW_THINKING("Low thinking"), MEDIUM_THINKING("Medium thinking"),
		HIGH_THINKING("Maximum thinking"), AUTO("Default thinking");

		private final String description;
	}

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
	 * ACL aliases granting access to this chat model configuration.
	 */
	private List<Integer> aclAliases = null;

	/**
	 * A list of functions that are enabled for direct chat interaction.
	 */
	protected List<String> enabledFunctions = null;

	/**
	 * The temperature setting of the model, which affects randomness in responses.
	 * Null by default.
	 */
	protected Double temperature = null;

	/***
	 * Signes the specific uses for wich the model can work and being a default
	 */
	protected List<ChatModelsUses> forUses = null;
	protected List<ChatModelFeatures> features = null;
	protected ChatModelThinkingOption thinking = null;
	protected Integer maxGeneratedTokens = null;
}