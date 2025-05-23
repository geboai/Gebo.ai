/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.qdrant;

import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactory;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GBaseVectorStoreConfig;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreProduct;
import ai.gebo.ragsystem.vectorstores.config.GeboAIVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.model.GeboMongoVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.qdrant.model.QdrantConfig;
import ai.gebo.ragsystem.vectorstores.services.IGVectorStoreFactoryBuilderWithTesting;

/**
 * AI generated comments
 * Implementation of the IGVectorStoreFactoryBuilderWithTesting interface for Qdrant vector store.
 * This class provides functionality to build and configure Qdrant vector store factories
 * and handles configuration conversion between YAML and MongoDB formats.
 */
@Service
public class QdrantVectorStoreFactoryBuilderImpl implements IGVectorStoreFactoryBuilderWithTesting {

	/**
	 * Default constructor for QdrantVectorStoreFactoryBuilderImpl.
	 */
	public QdrantVectorStoreFactoryBuilderImpl() {

	}

	/**
	 * Builds and returns a Qdrant vector store factory using the provided configuration.
	 * 
	 * @param <T> Type extending GBaseVectorStoreConfig
	 * @param config The configuration to use for building the factory
	 * @return A new QdrantVectorStoreFactory instance
	 * @throws LLMConfigException If there is an error in the configuration
	 */
	@Override
	public <T extends GBaseVectorStoreConfig> IGVectorStoreFactory<T> build(T config) throws LLMConfigException {

		return new QdrantVectorStoreFactory((QdrantConfig) config);

	}

	/**
	 * Returns the vector store product type for this factory builder.
	 * 
	 * @return The QDRANT vector store product type
	 */
	@Override
	public VectorStoreProduct getProduct() {
		return VectorStoreProduct.QDRANT;
	}

	/**
	 * Extracts Qdrant-specific configuration from a YAML configuration object.
	 * 
	 * @param <T> The target configuration type
	 * @param <O> The source configuration type
	 * @param ymlConfiguration The YAML configuration to extract from
	 * @return The extracted Qdrant configuration
	 * @throws LLMConfigException If there is an error extracting the configuration
	 */
	@Override
	public <T extends GBaseVectorStoreConfig, O> T extractConfiguration(O ymlConfiguration) throws LLMConfigException {
		GeboAIVectorStoreConfig cfg=(GeboAIVectorStoreConfig) ymlConfiguration;
		
		return (T) cfg.getQdrant();
	}

	/**
	 * Converts a YAML configuration to a MongoDB configuration.
	 * 
	 * @param <T> The target MongoDB configuration type
	 * @param <O> The source YAML configuration type
	 * @param ymlConfiguration The YAML configuration to convert
	 * @return The MongoDB configuration
	 * @throws LLMConfigException If there is an error during conversion
	 */
	@Override
	public <T, O> T yml2mongoConfig(O ymlConfiguration) throws LLMConfigException {
		GeboAIVectorStoreConfig cfg=(GeboAIVectorStoreConfig) ymlConfiguration;
		GeboMongoVectorStoreConfig out=new GeboMongoVectorStoreConfig();
		out.setProduct(getProduct());
		out.setQdrantConfig(cfg.getQdrant());
		return (T) out;
	}

	/**
	 * Converts a MongoDB configuration to a YAML configuration.
	 * 
	 * @param <T> The source MongoDB configuration type
	 * @param <O> The target YAML configuration type
	 * @param config The MongoDB configuration to convert
	 * @return The YAML configuration
	 * @throws LLMConfigException If there is an error during conversion
	 */
	@Override
	public <T, O> O mongo2ymlConfig(T config) throws LLMConfigException {
		GeboMongoVectorStoreConfig in=(GeboMongoVectorStoreConfig) config;
		GeboAIVectorStoreConfig cfg=new GeboAIVectorStoreConfig();
		cfg.setUse(getProduct().name());
		cfg.setQdrant(in.getQdrantConfig());
		return (O) cfg;
	}

}