/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.vectorstores.model;

/**
 * Gebo.ai comment agent
 * 
 * Represents the runtime configuration for a vector store, including
 * the specific product and its configuration details.
 */
public class VectorStoreRuntimeConfiguration {

    // The product associated with this vector store configuration.
    private final VectorStoreProduct product;
    // The configuration settings for the vector store.
    private final GBaseVectorStoreConfig configuration;

    /**
     * Constructs a new VectorStoreRuntimeConfiguration with the specified product 
     * and configuration.
     *
     * @param product the vector store product
     * @param configuration the configuration settings for the vector store
     */
    public VectorStoreRuntimeConfiguration(VectorStoreProduct product, GBaseVectorStoreConfig configuration) {
        this.product = product;
        this.configuration = configuration;
    }

    /**
     * Gets the vector store product associated with this configuration.
     *
     * @return the vector store product
     */
    public VectorStoreProduct getProduct() {
        return product;
    }

    /**
     * Gets the configuration settings for the vector store.
     *
     * @return the vector store configuration
     */
    public GBaseVectorStoreConfig getConfiguration() {
        return configuration;
    }
}