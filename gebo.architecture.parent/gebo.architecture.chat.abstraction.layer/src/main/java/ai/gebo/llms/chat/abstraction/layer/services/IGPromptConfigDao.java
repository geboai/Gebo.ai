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
 * Interface for accessing and managing prompt configurations in a chat application.
 */
public interface IGPromptConfigDao extends IGRuntimeConfigurationDao<GPromptConfig> {

    /**
     * Provides a default prompt configuration based on the specified chat model configuration
     * and an optional flag to enable a retrieval-augmented generation (RAG) prompt.
     *
     * @param chatConfiguration the base chat model configuration
     * @param ragPrompt         a boolean indicating whether a RAG prompt should be used
     * @return the default prompt configuration
     */
    GPromptConfig defaultPrompt(GBaseChatModelConfig chatConfiguration, Boolean ragPrompt);

    /**
     * Provides a default prompt configuration based on an optional RAG prompt flag.
     *
     * @param ragPrompt a boolean indicating whether a RAG prompt should be used
     * @return the default prompt configuration
     */
    GPromptConfig defaultPrompt(Boolean ragPrompt);
}