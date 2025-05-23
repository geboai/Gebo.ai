/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.fastsetup.model;

import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreProduct;
import ai.gebo.ragsystem.vectorstores.qdrant.model.QdrantConfig;
import ai.gebo.ragsystem.vectorstores.redis.model.RedisConfig;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * Represents the setup data required for configuring a vector store system.
 * This class holds configuration data for different types of vector stores.
 */
public class FastVectorStoreSetupData {
    // Represents the product type of the vector store. Must not be null.
    @NotNull
    VectorStoreProduct product = null;
    // Configuration details specific to Qdrant vector stores.
    QdrantConfig qdrantConfig = null;
    // Configuration details specific to Redis vector stores.
    RedisConfig redisConfig = null;

    /**
     * Returns the product type of the vector store.
     * @return the product type.
     */
    public VectorStoreProduct getProduct() {
        return product;
    }

    /**
     * Sets the product type of the vector store.
     * @param product the product type to set.
     */
    public void setProduct(VectorStoreProduct product) {
        this.product = product;
    }

    /**
     * Returns the Qdrant configuration.
     * @return the Qdrant configuration.
     */
    public QdrantConfig getQdrantConfig() {
        return qdrantConfig;
    }

    /**
     * Sets the Qdrant configuration.
     * @param qdrantConfig the Qdrant configuration to set.
     */
    public void setQdrantConfig(QdrantConfig qdrantConfig) {
        this.qdrantConfig = qdrantConfig;
    }

    /**
     * Returns the Redis configuration.
     * @return the Redis configuration.
     */
    public RedisConfig getRedisConfig() {
        return redisConfig;
    }

    /**
     * Sets the Redis configuration.
     * @param redisConfig the Redis configuration to set.
     */
    public void setRedisConfig(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }
}