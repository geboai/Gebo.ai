/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.lucene;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.ai.embedding.EmbeddingModel;

import ai.gebo.architecture.lucene.model.InternalLuceneServerConfig;
import ai.gebo.architecture.lucene.services.IGInternalLuceneServerFactory;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.IGExtendedVectorStore;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactory;

/**
 * AI generated comments
 * Factory class for creating Lucene-based vector stores.
 * This implementation of IGVectorStoreFactory creates vector stores
 * that use Lucene for storing and retrieving vector embeddings.
 */
public class LuceneVectorStoreFactory implements IGVectorStoreFactory {
	/**
	 * Configuration service for accessing Gebo system configuration.
	 */
	final IGGeboConfigService config;

	/**
	 * Factory for creating internal Lucene server instances.
	 */
	final IGInternalLuceneServerFactory luceneServerFactory;
	
	/**
	 * Configuration for the internal Lucene server.
	 */
	final InternalLuceneServerConfig serverConfig;

	/**
	 * Constructs a new LuceneVectorStoreFactory with the specified dependencies.
	 * 
	 * @param config Configuration service for system-wide settings
	 * @param luceneServerFactory Factory for creating Lucene server instances
	 * @param serverConfig Configuration for the Lucene server
	 */
	public LuceneVectorStoreFactory(IGGeboConfigService config, IGInternalLuceneServerFactory luceneServerFactory,
			InternalLuceneServerConfig serverConfig) {
		this.config = config;
		this.luceneServerFactory = luceneServerFactory;
		this.serverConfig = serverConfig;
	}

	/**
	 * Creates a new Lucene-based vector store with the specified embedding configuration and model.
	 * The vector store is created in a directory structure based on the embedding model code within
	 * the Gebo work directory.
	 *
	 * @param embeddingConfiguration The configuration for the embedding model
	 * @param embeddingModel The embedding model to use for creating and querying embeddings
	 * @return A new vector store instance
	 * @throws LLMConfigException If there is an error accessing or creating the vector store
	 */
	@Override
	public IGExtendedVectorStore create(GBaseEmbeddingModelConfig embeddingConfiguration, EmbeddingModel embeddingModel)
			throws LLMConfigException {
		// Create the base path for the Lucene index based on the embedding model code
		Path basePath = Path.of(config.getGeboWorkDirectory(), "LuceneIndexing", "vectorStores",
				embeddingConfiguration.getCode());
		try {
			// Create and return a new LuceneVectorStore instance
			LuceneVectorStore vectorStore = new LuceneVectorStore(embeddingConfiguration, embeddingModel,
					luceneServerFactory.create(basePath, serverConfig));

			return vectorStore;
		} catch (IOException e) {
			throw new LLMConfigException("Accessing to VectorStore with IO errors", e);
		}
	}

}