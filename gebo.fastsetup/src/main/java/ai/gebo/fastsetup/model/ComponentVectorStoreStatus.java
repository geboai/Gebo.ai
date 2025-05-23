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

/**
 * AI generated comments
 * Represents the status of a component vector store setup.
 * Contains configuration and product information for vector stores.
 */
public class ComponentVectorStoreStatus extends ComponentSetupStatus {

    // The product associated with the vector store.
    public VectorStoreProduct product = null;

    // Configuration settings for Qdrant vector store.
    public QdrantConfig qdrantConfig = null;

    // Configuration settings for Redis vector store.
    public RedisConfig redisConfig = null;

}