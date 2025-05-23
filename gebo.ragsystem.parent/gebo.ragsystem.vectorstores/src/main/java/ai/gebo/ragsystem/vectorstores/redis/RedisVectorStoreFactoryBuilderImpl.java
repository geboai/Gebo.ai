/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.redis;

import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactory;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GBaseVectorStoreConfig;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreProduct;
import ai.gebo.ragsystem.vectorstores.config.GeboAIVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.model.GeboMongoVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.redis.model.RedisConfig;
import ai.gebo.ragsystem.vectorstores.services.IGVectorStoreFactoryBuilderWithTesting;

/**
 * AI generated comments
 * 
 * This service implementation provides functionality to build Redis vector store factories.
 * It handles the configuration and creation of Redis vector stores for the RAG system.
 */
@Service
public class RedisVectorStoreFactoryBuilderImpl implements IGVectorStoreFactoryBuilderWithTesting {

	/**
	 * Default constructor for the Redis vector store factory builder.
	 */
	public RedisVectorStoreFactoryBuilderImpl() {

	}

	/**
	 * Returns the vector store product type for this factory builder.
	 * 
	 * @return The REDIS vector store product enum value
	 */
	@Override
	public VectorStoreProduct getProduct() {
		return VectorStoreProduct.REDIS;
	}

	/**
	 * Builds a Redis vector store factory from the provided configuration.
	 * 
	 * @param config The configuration for the Redis vector store
	 * @return A vector store factory initialized with the given configuration
	 * @throws LLMConfigException If there is an issue with the configuration
	 */
	@Override
	public <T extends GBaseVectorStoreConfig> IGVectorStoreFactory<T> build(T config) throws LLMConfigException {
		IGVectorStoreFactory factory = new RedisVectorStoreFactory((RedisConfig) config);
		return factory;
	}

	/**
	 * Extracts Redis-specific configuration from the provided YAML configuration.
	 * 
	 * @param ymlConfiguration The YAML configuration object
	 * @return Extracted Redis configuration
	 * @throws LLMConfigException If there is an issue with the configuration extraction
	 */
	@Override
	public <T extends GBaseVectorStoreConfig, O> T extractConfiguration(O ymlConfiguration) throws LLMConfigException {
		GeboAIVectorStoreConfig config=(GeboAIVectorStoreConfig) ymlConfiguration;
		return (T) config.getRedis();
	}

	/**
	 * Converts YAML configuration to MongoDB configuration for Redis vector store.
	 * 
	 * @param ymlConfiguration The YAML configuration to convert
	 * @return MongoDB configuration for Redis vector store
	 * @throws LLMConfigException If there is an issue with the configuration conversion
	 */
	@Override
	public <T, O> T yml2mongoConfig(O ymlConfiguration) throws LLMConfigException {
		GeboAIVectorStoreConfig config=(GeboAIVectorStoreConfig) ymlConfiguration;
		GeboMongoVectorStoreConfig out=new GeboMongoVectorStoreConfig();
		out.setProduct(getProduct());
		out.setRedisConfig(config.getRedis());
		return (T) out;
	}

	/**
	 * Converts MongoDB configuration to YAML configuration for Redis vector store.
	 * 
	 * @param config The MongoDB configuration to convert
	 * @return YAML configuration for Redis vector store
	 * @throws LLMConfigException If there is an issue with the configuration conversion
	 */
	@Override
	public <T, O> O mongo2ymlConfig(T config) throws LLMConfigException {
		GeboMongoVectorStoreConfig in=(GeboMongoVectorStoreConfig) config;
		GeboAIVectorStoreConfig out=new GeboAIVectorStoreConfig();
		out.setUse(getProduct().name()); 
		out.setRedis(in.getRedisConfig());
		return (O) out;
	}

}