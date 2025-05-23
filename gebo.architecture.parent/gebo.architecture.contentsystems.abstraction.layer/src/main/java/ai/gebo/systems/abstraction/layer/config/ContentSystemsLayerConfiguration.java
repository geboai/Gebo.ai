/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Gebo.ai comment agent
 * 
 * Configuration class for the content systems layer.
 * This class is used to bind properties with the prefix "ai.gebo.systems"
 * from external configuration sources.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.systems")
public class ContentSystemsLayerConfiguration {

	/**
 	 * Enumeration representing the available streaming strategies.
 	 */
	public static enum StreamingStrategy {
		DIRECT_STREAMING, // Represents direct streaming strategy.
		LAZY_STREAMING    // Represents lazy streaming strategy.
	};

	// Default streaming strategy set to LAZY_STREAMING.
	private StreamingStrategy strategy = StreamingStrategy.LAZY_STREAMING;

	/**
	 * Default constructor for ContentSystemsLayerConfiguration.
	 */
	public ContentSystemsLayerConfiguration() {

	}

	/**
	 * Retrieves the current streaming strategy.
	 * 
	 * @return the streaming strategy currently set.
	 */
	public StreamingStrategy getStrategy() {
		return strategy;
	}

	/**
	 * Sets the streaming strategy.
	 * 
	 * @param strategy the streaming strategy to be set.
	 */
	public void setStrategy(StreamingStrategy strategy) {
		this.strategy = strategy;
	}

}