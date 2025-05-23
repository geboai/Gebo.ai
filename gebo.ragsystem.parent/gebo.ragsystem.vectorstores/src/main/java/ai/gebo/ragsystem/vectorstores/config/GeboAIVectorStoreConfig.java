/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreProduct;
import ai.gebo.ragsystem.vectorstores.lucene.model.LuceneConfig;
import ai.gebo.ragsystem.vectorstores.mongoatlas.model.MongoConfig;
import ai.gebo.ragsystem.vectorstores.qdrant.model.QdrantConfig;
import ai.gebo.ragsystem.vectorstores.redis.model.RedisConfig;

/**
 * Configuration class for vector store settings in the Gebo AI RAG system.
 * This class maps properties from the application configuration with the prefix "ai.gebo.vectorstore".
 * It provides configuration options for different vector store implementations such as Qdrant, Lucene, Redis, and MongoDB.
 * 
 * AI generated comments
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.vectorstore")
public class GeboAIVectorStoreConfig {
	/** Defines which vector store implementation to use. Defaults to QDRANT. */
	String use = VectorStoreProduct.QDRANT.name();
	/** Configuration properties for Qdrant vector store */
	QdrantConfig qdrant = null;
	/** Configuration properties for Lucene vector store */
	LuceneConfig lucene = null;
	/** Configuration properties for Redis vector store */
	RedisConfig redis = null;
	/** Configuration properties for MongoDB vector store */
	MongoConfig mongoConfig=null;
	
	/**
	 * Default constructor for GeboAIVectorStoreConfig
	 */
	public GeboAIVectorStoreConfig() {

	}

	/**
	 * Gets the name of the vector store implementation to use
	 * @return the vector store implementation name
	 */
	public String getUse() {
		return use;
	}

	/**
	 * Sets the name of the vector store implementation to use
	 * @param use the vector store implementation name
	 */
	public void setUse(String use) {
		this.use = use;
	}

	/**
	 * Gets the Qdrant vector store configuration
	 * @return the Qdrant configuration object
	 */
	public QdrantConfig getQdrant() {
		return qdrant;
	}

	/**
	 * Sets the Qdrant vector store configuration
	 * @param qdrant the Qdrant configuration object
	 */
	public void setQdrant(QdrantConfig qdrant) {
		this.qdrant = qdrant;
	}

	/**
	 * Gets the Lucene vector store configuration
	 * @return the Lucene configuration object
	 */
	public LuceneConfig getLucene() {
		return lucene;
	}

	/**
	 * Sets the Lucene vector store configuration
	 * @param lucene the Lucene configuration object
	 */
	public void setLucene(LuceneConfig lucene) {
		this.lucene = lucene;
	}

	/**
	 * Gets the Redis vector store configuration
	 * @return the Redis configuration object
	 */
	public RedisConfig getRedis() {
		return redis;
	}

	/**
	 * Sets the Redis vector store configuration
	 * @param redis the Redis configuration object
	 */
	public void setRedis(RedisConfig redis) {
		this.redis = redis;
	}

	/**
	 * Gets the MongoDB vector store configuration
	 * @return the MongoDB configuration object
	 */
	public MongoConfig getMongoConfig() {
		return mongoConfig;
	}

	/**
	 * Sets the MongoDB vector store configuration
	 * @param mongoConfig the MongoDB configuration object
	 */
	public void setMongoConfig(MongoConfig mongoConfig) {
		this.mongoConfig = mongoConfig;
	}

}