/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.architecture.patterns.IGRuntimeModuleComponent;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;

/**
 * Gebo.ai comment agent
 *
 * Interface for embedding model runtime configuration data access object (DAO).
 * This interface provides methods to manage the runtime configuration of embedding models
 * and to interact with other runtime module components.
 */
public interface IGEmbeddingModelRuntimeConfigurationDao
        extends IGRuntimeModelConfigurationDao<IGConfigurableEmbeddingModel>, IGRuntimeModuleComponent {

    /**
     * Adds a runtime configuration by using the provided configuration information.
     *
     * @param config The base embedding model configuration to be added.
     * @throws LLMConfigException If there is an exception during the configuration process.
     */
    public void addRuntimeByConfig(GBaseEmbeddingModelConfig config) throws LLMConfigException;
}