/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.services;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreProduct;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreRuntimeConfiguration;
import ai.gebo.ragsystem.vectorstores.config.GeboAIVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.model.GeboMongoVectorStoreConfig;

/**
 * AI generated comments
 * Utility class for converting between different vector store configuration formats.
 * Provides methods to transform between YAML-based configurations and MongoDB-based configurations
 * for various vector store products like Lucene, Qdrant, Redis, and MongoDB.
 */
class VectorStoreConfigUtil {

    /**
     * Converts a YAML-based vector store configuration to a MongoDB-based configuration.
     * Detects which vector store product to use and transfers the appropriate configuration.
     * 
     * @param config The YAML-based vector store configuration to convert
     * @return MongoDB-based vector store configuration
     * @throws LLMConfigException If the configuration is invalid or missing
     */
	static GeboMongoVectorStoreConfig yml2Mongo(GeboAIVectorStoreConfig config) throws LLMConfigException {
		VectorStoreProduct product = VectorStoreProduct.LUCENE;
		GeboMongoVectorStoreConfig out = new GeboMongoVectorStoreConfig();
		if (config.getUse() == null && config.getQdrant() != null) {
			product = (VectorStoreProduct.QDRANT);

		}
		if (config.getUse() != null && config.getUse().trim().length() > 0) {
			try {
				product = VectorStoreProduct.valueOf(config.getUse());
			} catch (Throwable th) {
				throw new LLMConfigException(
						"Configuration of vector store product=" + config.getUse() + " is unknown");
			}
		}
		out.setProduct(product);
		switch (product) {
		case LUCENE: {
			if (config.getLucene() == null) {
				throw new LLMConfigException(
						"Configuration of vector store product=" + product.name() + " is not present in yml file");

			}
			out.setLuceneConfig(config.getLucene());
		}

			break;
		case QDRANT: {
			if (config.getQdrant() == null) {
				throw new LLMConfigException(
						"Configuration of vector store product=" + product.name() + " is not present in yml file");

			}
			out.setQdrantConfig(config.getQdrant());

		}
			break;
		case REDIS: {
			if (config.getRedis() == null)
				throw new LLMConfigException(
						"Configuration of vector store product=" + product.name() + " is not present in yml file");
			out.setRedisConfig(config.getRedis());
		}
		case MONGO: {
            // No specific configuration needed for MongoDB
		}
		}
		return out;
	}

    /**
     * Converts a MongoDB-based vector store configuration to a standard runtime configuration.
     * Maps the appropriate configuration based on the selected vector store product.
     * 
     * @param config The MongoDB-based vector store configuration to convert
     * @return Standardized vector store runtime configuration
     * @throws LLMConfigException If the configuration is invalid or missing
     */
	static VectorStoreRuntimeConfiguration mongo2Standart(GeboMongoVectorStoreConfig config) throws LLMConfigException {
		switch (config.getProduct()) {
		case LUCENE: {
			if (config.getLuceneConfig() == null) {
				throw new LLMConfigException("Configuration of vector store product=" + config.getProduct().name()
						+ " is not present in yml file");

			}
			return new VectorStoreRuntimeConfiguration(config.getProduct(), config.getLuceneConfig());
		}
		case QDRANT: {
			if (config.getQdrantConfig() == null) {
				throw new LLMConfigException("Configuration of vector store product=" + config.getProduct().name()
						+ " is not present in yml file");

			}
			return new VectorStoreRuntimeConfiguration(config.getProduct(), config.getQdrantConfig());
		}
		case MONGO: {
            // MongoDB doesn't require additional configuration
			return new VectorStoreRuntimeConfiguration(config.getProduct(), null);
		}
		case REDIS: {
			if (config.getRedisConfig() == null) {
				throw new LLMConfigException("Configuration of vector store product=" + config.getProduct().name()
						+ " is not present in yml file");

			}
			return new VectorStoreRuntimeConfiguration(config.getProduct(), config.getRedisConfig());
		}
		}
		throw new LLMConfigException("Unsupported product:" + config.getProduct());
	}

}