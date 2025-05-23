/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.lucene;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.lucene.model.InternalLuceneServerConfig;
import ai.gebo.architecture.lucene.services.IGInternalLuceneServerFactory;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactory;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GBaseVectorStoreConfig;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreProduct;
import ai.gebo.ragsystem.vectorstores.config.GeboAIVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.lucene.model.LuceneConfig;
import ai.gebo.ragsystem.vectorstores.model.GeboMongoVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.services.IGVectorStoreFactoryBuilderWithTesting;

/**
 * AI generated comments
 * Implementation of a vector store factory builder for Lucene-based vector storage.
 * This service handles the construction of Lucene vector store factories based on configuration
 * and provides methods for converting between YAML and MongoDB configuration formats.
 */
@Service
public class LuceneVectorStoreFactoryBuilderImpl implements IGVectorStoreFactoryBuilderWithTesting {
	/**
	 * Gebo configuration service for accessing application settings
	 */
	@Autowired
	IGGeboConfigService geboConfig;

	/**
	 * Factory for creating Lucene server instances
	 */
	@Autowired
	IGInternalLuceneServerFactory lueceneServerFactory;

	/**
	 * Builds a vector store factory based on the provided configuration.
	 * 
	 * @param <T> The type of vector store configuration
	 * @param config The configuration to use for building the factory
	 * @return A configured Lucene vector store factory
	 * @throws LLMConfigException If there's an issue with the configuration
	 */
	@Override
	public <T extends GBaseVectorStoreConfig> IGVectorStoreFactory<T> build(T config) throws LLMConfigException {
		LuceneConfig lConfig = (LuceneConfig) config;
		InternalLuceneServerConfig serverConfig = new InternalLuceneServerConfig();
		serverConfig.setSaveQueueMaxLength(lConfig.getDocumentsQueueMax());
		return new LuceneVectorStoreFactory(geboConfig, lueceneServerFactory, serverConfig);
	}

	/**
	 * Returns the vector store product type for this builder.
	 * 
	 * @return LUCENE as the vector store product
	 */
	@Override
	public VectorStoreProduct getProduct() {
		return VectorStoreProduct.LUCENE;
	}

	/**
	 * Extracts Lucene-specific configuration from the general vector store configuration.
	 * 
	 * @param <T> The type of vector store configuration to return
	 * @param <O> The type of the input configuration
	 * @param ymlConfiguration The YAML-based configuration to extract from
	 * @return The extracted Lucene configuration
	 * @throws LLMConfigException If there's an issue with the configuration extraction
	 */
	@Override
	public <T extends GBaseVectorStoreConfig, O> T extractConfiguration(O ymlConfiguration) throws LLMConfigException {
		GeboAIVectorStoreConfig inputCfg = (GeboAIVectorStoreConfig) ymlConfiguration;
		return (T) inputCfg.getLucene();
	}

	/**
	 * Converts YAML configuration to MongoDB configuration format.
	 * 
	 * @param <T> The type of the output MongoDB configuration
	 * @param <O> The type of the input YAML configuration
	 * @param ymlConfiguration The YAML configuration to convert
	 * @return MongoDB vector store configuration
	 * @throws LLMConfigException If there's an issue with the configuration conversion
	 */
	@Override
	public <T, O> T yml2mongoConfig(O ymlConfiguration) throws LLMConfigException {
		GeboAIVectorStoreConfig inputCfg = (GeboAIVectorStoreConfig) ymlConfiguration;
		GeboMongoVectorStoreConfig config = new GeboMongoVectorStoreConfig();
		config.setProduct(getProduct());
		config.setLuceneConfig(inputCfg.getLucene());
		return (T) config;
	}

	/**
	 * Converts MongoDB configuration to YAML configuration format.
	 * 
	 * @param <T> The type of the input MongoDB configuration
	 * @param <O> The type of the output YAML configuration
	 * @param config The MongoDB configuration to convert
	 * @return YAML vector store configuration
	 * @throws LLMConfigException If there's an issue with the configuration conversion
	 */
	@Override
	public <T, O> O mongo2ymlConfig(T config) throws LLMConfigException {
		GeboMongoVectorStoreConfig mconfig = (GeboMongoVectorStoreConfig) config;
		GeboAIVectorStoreConfig cfg = new GeboAIVectorStoreConfig();
		cfg.setUse(getProduct().name());
		cfg.setLucene(mconfig.getLuceneConfig());
		return (O) cfg;
	}

}