/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.test.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.testing.AbstractTestingBusinessLogic;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactory;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryBuilder;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GBaseVectorStoreConfig;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreProduct;
import ai.gebo.ragsystem.vectorstores.config.GeboAIVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.model.GeboMongoVectorStoreConfig;

/**
 * AI generated comments
 * Implementation of a test vector store factory builder that implements the IGVectorStoreFactoryBuilder interface.
 * This service is used for testing purposes and creates test vector store factories with test configurations.
 * It extends AbstractTestingBusinessLogic to utilize testing infrastructure.
 */
@Service
public class TestVectorStoreFactoryBuilderImpl extends AbstractTestingBusinessLogic
		implements IGVectorStoreFactoryBuilder {
	/**
	 * Configuration for the test vector store, autowired but not required.
	 */
	@Autowired(required = false)
	TestVectorStoreConfig vectorStoreConfig;
	
	/**
	 * JSON object mapper for serialization/deserialization.
	 */
	final static ObjectMapper mapper = new ObjectMapper();
	static {
	}

	/**
	 * Returns the vector store product type for this builder.
	 * 
	 * @return VectorStoreProduct.TEST as the product identifier
	 */
	@Override
	public VectorStoreProduct getProduct() {
		return VectorStoreProduct.TEST;
	}

	/**
	 * Builds a test vector store factory with the provided configuration.
	 * 
	 * @param config The vector store configuration
	 * @return A new TestVectorStoreFactory instance
	 * @throws LLMConfigException If there's an issue with the configuration
	 */
	@Override
	public <T extends GBaseVectorStoreConfig> IGVectorStoreFactory<T> build(T config) throws LLMConfigException {
		return new TestVectorStoreFactory(vectorStoreConfig);
	}

	/**
	 * Extracts a configuration object from a YAML configuration.
	 * For testing purposes, this simply returns a new TestVectorStoreConfig.
	 * 
	 * @param ymlConfiguration The YAML configuration object
	 * @return A new TestVectorStoreConfig instance
	 * @throws LLMConfigException If there's an issue with the configuration extraction
	 */
	@Override
	public <T extends GBaseVectorStoreConfig, O> T extractConfiguration(O ymlConfiguration) throws LLMConfigException {
		return (T) new TestVectorStoreConfig();
	}

	/**
	 * Converts a YAML configuration to a MongoDB vector store configuration.
	 * 
	 * @param config The YAML configuration to convert
	 * @return A MongoDB vector store configuration with the test product set
	 * @throws LLMConfigException If there's an issue with the configuration conversion
	 */
	@Override
	public <T, O> T yml2mongoConfig(O config) throws LLMConfigException {
		GeboMongoVectorStoreConfig mconfig = new GeboMongoVectorStoreConfig();
		mconfig.setProduct(getProduct());

		return (T) mconfig;
	}

	/**
	 * Converts a MongoDB vector store configuration to a YAML configuration.
	 * 
	 * @param config The MongoDB configuration to convert
	 * @return A YAML configuration with the product name set
	 * @throws LLMConfigException If there's an issue with the configuration conversion
	 */
	@Override
	public <T, O> O mongo2ymlConfig(T config) throws LLMConfigException {
		GeboAIVectorStoreConfig c = new GeboAIVectorStoreConfig();
		c.setUse(getProduct().name());
		return (O) c;
	}
}