/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.mongoatlas;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.mongodb.atlas.MongoDBAtlasVectorStore;
import org.springframework.ai.vectorstore.mongodb.atlas.MongoDBAtlasVectorStore.Builder;
import org.springframework.data.mongodb.core.MongoTemplate;

import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.GExtendedVectorStoreWrapper;
import ai.gebo.llms.abstraction.layer.vectorstores.IGExtendedVectorStore;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactory;
import ai.gebo.model.DocumentMetaInfos;

/**
 * AI generated comments
 * 
 * Factory implementation for creating MongoDB Atlas Vector Store instances.
 * This factory creates vector stores that are compatible with the 
 * abstraction layer for vector stores in the system.
 */
public class MongoDBAtlasVectorStoreFactory implements IGVectorStoreFactory {
	/** MongoDB template for interacting with MongoDB database */
	final MongoTemplate mongoTemplate;

	/**
	 * Constructor that initializes the factory with a MongoDB template.
	 * 
	 * @param mongoTemplate The MongoDB template for database operations
	 */
	public MongoDBAtlasVectorStoreFactory(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * Creates a MongoDB Atlas vector store instance with the specified embedding configuration.
	 * 
	 * @param embeddingConfiguration Configuration for the embedding model
	 * @param embeddingModel The embedding model to use for generating embeddings
	 * @return An extended vector store interface wrapped around the MongoDB Atlas vector store
	 * @throws LLMConfigException If there's an error in the configuration
	 */
	@Override
	public IGExtendedVectorStore create(GBaseEmbeddingModelConfig embeddingConfiguration, EmbeddingModel embeddingModel)
			throws LLMConfigException {
		// Use configuration code as the base name for vector store components
		String vectorStoreName = embeddingConfiguration.getCode();
		String vectorStoreIndex = vectorStoreName + "-index";
		String embeddingPath = vectorStoreName + "-embed-path";
		
		// Configure the MongoDB Atlas vector store
		Builder builder = MongoDBAtlasVectorStore.builder(mongoTemplate, embeddingModel);
		builder = builder.vectorIndexName(vectorStoreIndex);
		builder = builder.pathName(embeddingPath);
		builder = builder.metadataFieldsToFilter(DocumentMetaInfos.ALL_ATTRIBUTES);
		builder = builder.collectionName(vectorStoreName);
		// builder=builder.batchingStrategy(Defaultbatch)
		MongoDBAtlasVectorStore vectorStore = builder.build();
		
		// Wrap the store in the extended interface wrapper
		return GExtendedVectorStoreWrapper.of(vectorStore);
	}
}