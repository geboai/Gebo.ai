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
 * Enum representing various types of vector store products that can be 
 * used within the application. Each enum constant corresponds to a 
 * specific vector store technology.
 */
public enum VectorStoreProduct {
    // Represents a MongoDB-based vector store
    MONGO, 
    
    // Represents a Qdrant-based vector store
    QDRANT, 
    
    // Represents a Lucene-based vector store
    LUCENE, 
    
    // Represents a Redis-based vector store
    REDIS,

    // Represents a test vector store, possibly used for testing purposes
    TEST
}