/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.content.vectorizator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.application.messaging.GAbstractMessageReceiverFactory.MessageReceiverFactoryConfig;
import ai.gebo.application.messaging.GAbstractTimedOutMessageReceiverFactory.TimedOutMessageReceiverFactoryConfig;

/**
 * AI generated comments
 * 
 * Configuration class for the Gebo vectorization system.
 * This class defines properties that control the behavior of the vectorization process,
 * including message size limits and receiver configurations.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.docscache.config")
public class GeboVectorizatorConfig {
	/** Maximum cumulative size of messages in bytes before processing (default: 1MB) */
	long maximumMessagesCumulatedBytesThreshold = 1024 * 1024;
	
	/** Configuration for the message disposer */
	MessageReceiverFactoryConfig disposerConfig = new MessageReceiverFactoryConfig();
	
	/** Configuration for the vectorization message receiver with timeout capabilities */
	TimedOutMessageReceiverFactoryConfig vectorizatorReceiverConfig = new TimedOutMessageReceiverFactoryConfig();

	/**
	 * Default constructor that initializes the configuration objects with default values.
	 * Sets up pool cardinality, threading behavior, timeout values, and flush thresholds.
	 */
	public GeboVectorizatorConfig() {
		disposerConfig.setPoolCardinality(1);
		disposerConfig.setUseSenderThread(false);
		vectorizatorReceiverConfig.setPoolCardinality(1);
		vectorizatorReceiverConfig.setUseSenderThread(true);
		vectorizatorReceiverConfig.setTimeout(5000l);
		vectorizatorReceiverConfig.setFlushThreshold(10);
	}

	/**
	 * Returns the configuration for the message disposer.
	 * 
	 * @return The disposer configuration object
	 */
	public MessageReceiverFactoryConfig getDisposerConfig() {
		return disposerConfig;
	}

	/**
	 * Sets the configuration for the message disposer.
	 * 
	 * @param disposerConfig The disposer configuration to set
	 */
	public void setDisposerConfig(MessageReceiverFactoryConfig disposerConfig) {
		this.disposerConfig = disposerConfig;
	}

	/**
	 * Returns the configuration for the vectorization message receiver.
	 * 
	 * @return The vectorization receiver configuration object
	 */
	public TimedOutMessageReceiverFactoryConfig getVectorizatorReceiverConfig() {
		return vectorizatorReceiverConfig;
	}

	/**
	 * Sets the configuration for the vectorization message receiver.
	 * 
	 * @param vectorizatorReceiverConfig The vectorization receiver configuration to set
	 */
	public void setVectorizatorReceiverConfig(TimedOutMessageReceiverFactoryConfig vectorizatorReceiverConfig) {
		this.vectorizatorReceiverConfig = vectorizatorReceiverConfig;
	}

	/**
	 * Returns the maximum cumulative size threshold for messages in bytes.
	 * 
	 * @return The maximum size threshold in bytes
	 */
	public long getMaximumMessagesCumulatedBytesThreshold() {
		return maximumMessagesCumulatedBytesThreshold;
	}

	/**
	 * Sets the maximum cumulative size threshold for messages in bytes.
	 * 
	 * @param maximumMessagesCumulatedBytesThreshold The maximum size threshold to set in bytes
	 */
	public void setMaximumMessagesCumulatedBytesThreshold(long maximumMessagesCumulatedBytesThreshold) {
		this.maximumMessagesCumulatedBytesThreshold = maximumMessagesCumulatedBytesThreshold;
	}

}