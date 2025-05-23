/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services;

/**
 * AI generated comments
 * This interface defines a factory for creating clients to interact with different LLM service providers.
 * It provides a method to obtain a service client provider based on a given provider ID.
 */
public interface IGLlmsServiceClientsProviderFactory {

    /**
     * Retrieves an instance of IGLlmsServiceClientsProvider for the specified provider ID.
     *
     * @param providerId The ID of the service provider for which a client provider is needed.
     * @return An instance of IGLlmsServiceClientsProvider appropriate for the given provider ID.
     */
    public IGLlmsServiceClientsProvider get(String providerId);
}