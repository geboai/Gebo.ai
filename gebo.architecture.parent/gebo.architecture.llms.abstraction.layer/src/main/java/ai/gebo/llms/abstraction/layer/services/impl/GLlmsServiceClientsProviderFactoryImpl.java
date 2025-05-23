/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services.impl;

import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;

/**
 * AI generated comments
 * Implementation of the {@link IGLlmsServiceClientsProviderFactory} interface
 * This class provides a method to get an instance of the service clients provider.
 * It utilizes a default implementation of the provider.
 */
@Service
public class GLlmsServiceClientsProviderFactoryImpl implements IGLlmsServiceClientsProviderFactory {
    
    // Default implementation of the service clients provider
    final GDefaultLlmsServiceClientsProviderImpl defaultImplementation;

    /**
     * Constructor to initialize the default implementation.
     *
     * @param defaultImplementation the default service clients provider implementation
     */
    public GLlmsServiceClientsProviderFactoryImpl(GDefaultLlmsServiceClientsProviderImpl defaultImplementation) {
        this.defaultImplementation = defaultImplementation;
    }

    /**
     * Retrieves the service clients provider based on the provider ID.
     * Currently, it returns the default implementation regardless of the input.
     *
     * @param providerId the identifier for the desired service clients provider
     * @return an instance of {@link IGLlmsServiceClientsProvider}
     */
    @Override
    public IGLlmsServiceClientsProvider get(String providerId) {
        // Currently returns the default implementation
        return defaultImplementation;
    }

}