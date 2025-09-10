/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.documents.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.application.messaging.GAbstractTimedOutMessageReceiverFactory.TimedOutMessageReceiverFactoryConfig;
import lombok.Data;

/**
 * AI generated comments
 * 
 * Configuration class for the Gebo document cache and chunking system.
 * This class defines properties that control the behavior of the vectorization process,
 * including message size limits and receiver configurations.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.vectorizator.config")
@Data
public class GeboDocumentsCacheConfig {
	/** Maximum cumulative size of messages in bytes before processing (default: 1MB) */
	long maximumMessagesCumulatedBytesThreshold = 1024 * 1024;
	
	
	
	/** Configuration for the vectorization message receiver with timeout capabilities */
	TimedOutMessageReceiverFactoryConfig documentChunkerReceiverConfig = new TimedOutMessageReceiverFactoryConfig();

	/**
	 * Default constructor that initializes the configuration objects with default values.
	 * Sets up pool cardinality, threading behavior, timeout values, and flush thresholds.
	 */
	public GeboDocumentsCacheConfig() {
		
		documentChunkerReceiverConfig.setPoolCardinality(1);
		documentChunkerReceiverConfig.setUseSenderThread(true);
		documentChunkerReceiverConfig.setTimeout(5000l);
		documentChunkerReceiverConfig.setFlushThreshold(10);
	}

	

}