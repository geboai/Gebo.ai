/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.vectorstores.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreConfigurationProvider;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactory;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryBuilder;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryBuilderRepositoryPattern;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryProvider;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreRuntimeConfiguration;

/**
 * Gebo.ai comment agent
 * This class provides the implementation for the GVectorStoreFactoryProvider.
 * It retrieves and constructs vector store factories based on runtime configurations.
 * Annotated as a Spring service, it participates in dependency injection.
 */
@Service
public class GVectorStoreFactoryProviderImpl implements IGVectorStoreFactoryProvider {

    // Autowire the repository that provides access to factory builders based on product codes.
    @Autowired IGVectorStoreFactoryBuilderRepositoryPattern buildersRepository;
    
    // Autowire the configuration provider that fetches runtime configuration.
    @Autowired IGVectorStoreConfigurationProvider configurationProvider;

    /**
     * Retrieves the vector store factory using the default runtime configuration.
     * 
     * @return An instance of IGVectorStoreFactory.
     * @throws LLMConfigException if configuration retrieval or factory building fails.
     */
    @Override
    public IGVectorStoreFactory get() throws LLMConfigException {
        VectorStoreRuntimeConfiguration configuration = configurationProvider.get();
        return get(configuration);
    }

    /**
     * Retrieves the vector store factory using the provided runtime configuration.
     * 
     * @param configuration The runtime configuration to use for building the factory.
     * @return An instance of IGVectorStoreFactory built using the provided configuration.
     * @throws LLMConfigException if the factory builder is not found or if building the factory fails.
     */
    @Override
    public IGVectorStoreFactory get(VectorStoreRuntimeConfiguration configuration) throws LLMConfigException {
        IGVectorStoreFactoryBuilder builder = buildersRepository.findByCode(configuration.getProduct().name());
        return builder.build(configuration.getConfiguration());
    }
}