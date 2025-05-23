/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.mongoatlas;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactory;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GBaseVectorStoreConfig;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreProduct;
import ai.gebo.ragsystem.vectorstores.config.GeboAIVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.model.GeboMongoVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.mongoatlas.model.MongoConfig;
import ai.gebo.ragsystem.vectorstores.services.IGVectorStoreFactoryBuilderWithTesting;

/**
 * AI generated comments
 * This service builds MongoDB Atlas vector store factories for the RAG system.
 * It implements the IGVectorStoreFactoryBuilderWithTesting interface to support
 * vector storage operations with MongoDB Atlas.
 */
@Service
public class MongoDBAtlasVectorStoreFactoryBuilder implements IGVectorStoreFactoryBuilderWithTesting {

	/** The MongoDB template for database operations */
	final MongoTemplate mongoTemplate;

	/**
	 * Constructor that initializes the MongoDB template.
	 * 
	 * @param mongoTemplate The MongoDB template to use for database operations
	 */
	public MongoDBAtlasVectorStoreFactoryBuilder(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * Returns the vector store product type that this factory builder supports.
	 * 
	 * @return The MongoDB vector store product enum value
	 */
	@Override
	public VectorStoreProduct getProduct() {
		return VectorStoreProduct.MONGO;
	}

	/**
	 * Builds a MongoDB Atlas vector store factory with the provided configuration.
	 * 
	 * @param config The vector store configuration
	 * @return A new MongoDB Atlas vector store factory
	 * @throws LLMConfigException If there is an issue with the configuration
	 */
	@Override
	public <T extends GBaseVectorStoreConfig> IGVectorStoreFactory<T> build(T config) throws LLMConfigException {
		return new MongoDBAtlasVectorStoreFactory(mongoTemplate);
	}

	/**
	 * Extracts vector store configuration from the provided YAML configuration.
	 * 
	 * @param ymlConfiguration The YAML configuration to extract from
	 * @return A MongoDB configuration object
	 * @throws LLMConfigException If there is an issue with the configuration extraction
	 */
	@Override
	public <T extends GBaseVectorStoreConfig, O> T extractConfiguration(O ymlConfiguration) throws LLMConfigException {
		return (T) new MongoConfig();
	}

	/**
	 * Converts a YAML configuration to a MongoDB configuration.
	 * 
	 * @param config The YAML configuration to convert
	 * @return A MongoDB vector store configuration
	 * @throws LLMConfigException If there is an issue with the configuration conversion
	 */
	@Override
	public <T, O> T yml2mongoConfig(O config) throws LLMConfigException {
		GeboAIVectorStoreConfig inputCfg=(GeboAIVectorStoreConfig) config;
		GeboMongoVectorStoreConfig _config = new GeboMongoVectorStoreConfig();
		_config.setMongoConfig(new MongoConfig());
		_config.setProduct(VectorStoreProduct.MONGO);
		return (T) _config;
	}

	/**
	 * Converts a MongoDB configuration to a YAML configuration.
	 * 
	 * @param config The MongoDB configuration to convert
	 * @return A YAML vector store configuration
	 * @throws LLMConfigException If there is an issue with the configuration conversion
	 */
	@Override
	public <T, O> O mongo2ymlConfig(T config) throws LLMConfigException {
		GeboMongoVectorStoreConfig _config = (GeboMongoVectorStoreConfig) config;
		GeboAIVectorStoreConfig yconfig = new GeboAIVectorStoreConfig();
		yconfig.setUse(VectorStoreProduct.MONGO.name());
		yconfig.setMongoConfig(new MongoConfig());
		return (O) yconfig;
	}
}