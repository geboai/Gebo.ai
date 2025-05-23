/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreProduct;
import ai.gebo.ragsystem.vectorstores.lucene.model.LuceneConfig;
import ai.gebo.ragsystem.vectorstores.mongoatlas.model.MongoConfig;
import ai.gebo.ragsystem.vectorstores.qdrant.model.QdrantConfig;
import ai.gebo.ragsystem.vectorstores.redis.model.RedisConfig;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * 
 * Represents a MongoDB document for storing vector store configuration.
 * This class contains configuration settings for different vector store products 
 * that can be used in the system, such as Qdrant, Lucene, MongoDB Atlas, and Redis.
 */
@Document
public class GeboMongoVectorStoreConfig {
	/** Constant defining the document ID for vector store configuration */
	public static final String VECTORSTORECONFIG = "VECTORSTORECONFIG";
	
	/** Document ID, defaults to the VECTORSTORECONFIG constant */
	@Id
	String id = VECTORSTORECONFIG;
	
	/** The vector store product type currently configured, must not be null */
	@NotNull
	VectorStoreProduct product = null;
	
	/** Configuration settings specific to Qdrant vector store */
	QdrantConfig qdrantConfig = null;
	
	/** Configuration settings specific to Lucene vector store */
	LuceneConfig luceneConfig=null;
	
	/** Configuration settings specific to MongoDB Atlas vector store */
	MongoConfig mongoConfig=null;
	
	/** Configuration settings specific to Redis vector store */
	RedisConfig redisConfig = null;
	
	/**
	 * Gets the configured vector store product
	 * @return The vector store product
	 */
	public VectorStoreProduct getProduct() {
		return product;
	}

	/**
	 * Sets the vector store product to use
	 * @param product The vector store product
	 */
	public void setProduct(VectorStoreProduct product) {
		this.product = product;
	}

	/**
	 * Gets the Qdrant-specific configuration
	 * @return The Qdrant configuration
	 */
	public QdrantConfig getQdrantConfig() {
		return qdrantConfig;
	}

	/**
	 * Sets the Qdrant-specific configuration
	 * @param qdrantConfig The Qdrant configuration
	 */
	public void setQdrantConfig(QdrantConfig qdrantConfig) {
		this.qdrantConfig = qdrantConfig;
	}

	/**
	 * Gets the document ID
	 * @return The document ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the document ID
	 * @param id The document ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the Lucene-specific configuration
	 * @return The Lucene configuration
	 */
	public LuceneConfig getLuceneConfig() {
		return luceneConfig;
	}

	/**
	 * Sets the Lucene-specific configuration
	 * @param luceneConfig The Lucene configuration
	 */
	public void setLuceneConfig(LuceneConfig luceneConfig) {
		this.luceneConfig = luceneConfig;
	}

	/**
	 * Gets the Redis-specific configuration
	 * @return The Redis configuration
	 */
	public RedisConfig getRedisConfig() {
		return redisConfig;
	}

	/**
	 * Sets the Redis-specific configuration
	 * @param redisConfig The Redis configuration
	 */
	public void setRedisConfig(RedisConfig redisConfig) {
		this.redisConfig = redisConfig;
	}

	/**
	 * Gets the MongoDB Atlas-specific configuration
	 * @return The MongoDB configuration
	 */
	public MongoConfig getMongoConfig() {
		return mongoConfig;
	}

	/**
	 * Sets the MongoDB Atlas-specific configuration
	 * @param mongoConfig The MongoDB configuration
	 */
	public void setMongoConfig(MongoConfig mongoConfig) {
		this.mongoConfig = mongoConfig;
	}
}