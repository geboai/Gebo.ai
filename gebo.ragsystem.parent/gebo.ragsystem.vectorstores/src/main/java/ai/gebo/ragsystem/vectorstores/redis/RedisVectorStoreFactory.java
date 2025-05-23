/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.redis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore.Algorithm;
import org.springframework.ai.vectorstore.redis.RedisVectorStore.Builder;
import org.springframework.ai.vectorstore.redis.RedisVectorStore.MetadataField;

import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.GExtendedVectorStoreWrapper;
import ai.gebo.llms.abstraction.layer.vectorstores.IGExtendedVectorStore;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactory;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.ragsystem.vectorstores.redis.model.RedisConfig;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.search.Schema.FieldType;

/**
 * AI generated comments
 * Factory implementation for creating Redis vector stores.
 * This class implements the IGVectorStoreFactory interface for Redis configurations,
 * allowing for the creation of Redis-backed vector stores for embeddings.
 */
public class RedisVectorStoreFactory implements IGVectorStoreFactory<RedisConfig> {
	/** The Redis configuration containing connection details */
	final RedisConfig redisConfig;

	/**
	 * Constructor that initializes the factory with Redis configuration.
	 * 
	 * @param redisConfig The configuration for connecting to Redis
	 */
	public RedisVectorStoreFactory(RedisConfig redisConfig) {
		this.redisConfig = redisConfig;
	}

	/**
	 * Creates and configures a Redis vector store using the provided embedding model and configuration.
	 * 
	 * @param embeddingConfiguration Configuration for the embedding model
	 * @param embeddingModel The embedding model to use for vector embeddings
	 * @return A wrapped Redis vector store implementing the IGExtendedVectorStore interface
	 * @throws LLMConfigException If there's an issue with the configuration
	 */
	@Override
	public IGExtendedVectorStore create(GBaseEmbeddingModelConfig embeddingConfiguration, EmbeddingModel embeddingModel)
			throws LLMConfigException {

		// Create metadata fields for document attributes
		List<MetadataField> metas = new ArrayList<RedisVectorStore.MetadataField>();
		List<String> allMetas = DocumentMetaInfos.ALL_ATTRIBUTES;
		for (String field : allMetas) {
			MetadataField meta = new MetadataField(field, FieldType.TEXT);
			metas.add(meta);
		}
		
		// Extract Redis connection parameters
		final String host = redisConfig.getHost();
		final int port = redisConfig.getPort();
		final String user = redisConfig.getUsername();
		final String password = redisConfig.getPassword();
		
		// Initialize Redis connection pool
		JedisPooled jedisPooled = new JedisPooled(host, port, user, password);
		
		// Configure the Redis vector store with appropriate settings
		Builder builder = RedisVectorStore.builder(jedisPooled, embeddingModel);
		
		builder=builder.prefix(embeddingConfiguration.getCode());
		builder=builder.contentFieldName("gebocontent");
		builder=builder.embeddingFieldName("geboembedding");
		builder=builder.indexName("geboidx");
		builder=builder.metadataFields(metas.toArray(new MetadataField[0]));
		builder=builder.batchingStrategy(new TokenCountBatchingStrategy());
		builder=builder.initializeSchema(true);
		builder=builder.vectorAlgorithm(Algorithm.HSNW);
		
		// Build the Redis vector store
		final RedisVectorStore redisVectorStore = builder.build();
		// redisVectorStore.afterPropertiesSet();
		
		// Wrap the Redis vector store to make it compatible with the IGExtendedVectorStore interface
		final GExtendedVectorStoreWrapper<RedisVectorStore> wrapper = new GExtendedVectorStoreWrapper<RedisVectorStore>(
				redisVectorStore);
		return wrapper;
	}
}