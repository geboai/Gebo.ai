/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.services;

import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;

/**
 * Gebo.ai comment agent
 *
 * Interface for accessing and managing prompt configurations in an application
 * that rely in llms for a lot of different uses application.
 */
public interface IGPromptConfigDao extends IGRuntimeConfigurationDao<GPromptConfig> {

	/**
	 * Provides a default prompt configuration based on the specified chat model
	 * configuration and an optional flag to enable a retrieval-augmented generation
	 * (RAG) prompt.
	 *
	 * @param chatConfiguration the base chat model configuration
	 * @param ragPrompt         a boolean indicating whether a RAG prompt should be
	 *                          used
	 * @return the default prompt configuration
	 */
	GPromptConfig defaultChatPrompt(GBaseChatModelConfig chatConfiguration, Boolean ragPrompt);

	/**
	 * Provides a default prompt configuration based on an optional RAG prompt flag.
	 *
	 * @param ragPrompt a boolean indicating whether a RAG prompt should be used
	 * @return the default prompt configuration
	 */
	GPromptConfig defaultChatPrompt(Boolean ragPrompt);

	/****************************************************************************
	 * Get a prompt by its specific use, preferring eventual customized by user one
	 * 
	 * @param promptUse
	 * @return
	 */
	default GPromptConfig findByPromptUse(String promptUse) {
		return this.findByPromptUse(promptUse, "en", null, null);
	}

	public GPromptConfig exactFindByPromptUse(String promptUse, String langCode, String modelProvider,
			String modelCode);
 
	/************************************************************************************
	 * Gets a prompt by its specific use, language (failing back to english) and llm
	 * code hierarchically falling back if no custom specific is declared
	 * 
	 * @param promptUse
	 * @param langCode
	 * @param config
	 * @return
	 */
	default GPromptConfig findByPromptUse(String promptUse, String langCode, GBaseChatModelConfig config) {
		return this.findByPromptUse(promptUse, langCode, null,
				config.getChoosedModel() != null ? config.getChoosedModel().getCode() : null);
	}

	/**********************************************************************************
	 * Gets a prompt by its specific use, for a specific llm in english,
	 * hierarchically falling back if no custom specific is declared
	 * 
	 * @param promptUse
	 * @param config
	 * @return
	 */
	default GPromptConfig findByPromptUse(String promptUse, GBaseChatModelConfig config) {
		return this.findByPromptUse(promptUse, "en", null,
				config.getChoosedModel() != null ? config.getChoosedModel().getCode() : null);
	}

	GPromptConfig findByPromptUse(String promptUse, String langCode, String modelProvider, String modelCode);

	public GPromptConfig insert(GPromptConfig config);

	public GPromptConfig update(GPromptConfig config);

	public void delete(GPromptConfig config);
}