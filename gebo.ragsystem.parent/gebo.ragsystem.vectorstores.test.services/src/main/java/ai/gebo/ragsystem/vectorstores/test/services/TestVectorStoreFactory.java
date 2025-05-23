/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.test.services;

import org.springframework.ai.embedding.EmbeddingModel;

import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.IGExtendedVectorStore;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactory;

/**
 * AI generated comments
 * 
 * A factory class for creating instances of TestVectorStore.
 * This factory implements the IGVectorStoreFactory interface
 * to provide a standardized way of creating vector stores for testing purposes.
 */
public class TestVectorStoreFactory implements IGVectorStoreFactory {
	// Configuration for the test vector store
	TestVectorStoreConfig vectorStoreConfig=null; 
	
	/**
	 * Constructor that initializes the factory with a specific vector store configuration.
	 * 
	 * @param vectorStoreConfig Configuration to be used when creating vector stores
	 */
	public TestVectorStoreFactory(TestVectorStoreConfig vectorStoreConfig) {
		this.vectorStoreConfig=vectorStoreConfig;
	}

	/**
	 * Creates a new instance of TestVectorStore with the configured settings.
	 * 
	 * @param embeddingConfiguration Configuration for the embedding model
	 * @param embeddingModel The embedding model to use
	 * @return A new TestVectorStore instance
	 * @throws LLMConfigException If there's an issue with the configuration
	 */
	@Override
	public IGExtendedVectorStore create(GBaseEmbeddingModelConfig embeddingConfiguration,
			EmbeddingModel embeddingModel) throws LLMConfigException {

		return new TestVectorStore(vectorStoreConfig);
	}

}