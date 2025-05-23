/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

// Gebo.ai comment agent
package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.architecture.patterns.IGRuntimeModuleComponent;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;

/**
 * Interface representing the DAO (Data Access Object) for runtime 
 * configuration of chat models. It extends the generic runtime model 
 * configuration DAO for configurable chat models and also includes 
 * runtime module components.
 *
 * Provides the ability to add a runtime configuration based on a 
 * provided base chat model configuration.
 */
public interface IGChatModelRuntimeConfigurationDao
        extends IGRuntimeModelConfigurationDao<IGConfigurableChatModel>, IGRuntimeModuleComponent {

    /**
     * Adds a runtime configuration based on the specified base chat 
     * model configuration.
     *
     * @param config The base chat model configuration to be used for 
     *               adding a runtime configuration.
     * @throws LLMConfigException if the configuration cannot be added 
     *                            due to an invalid or unsupported 
     *                            configuration.
     */
    public void addRuntimeByConfig(GBaseChatModelConfig config) throws LLMConfigException;
}