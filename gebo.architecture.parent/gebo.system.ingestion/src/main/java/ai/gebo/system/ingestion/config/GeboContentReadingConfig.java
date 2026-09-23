/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ai.gebo.architecture.utils.GeboYamlPropertySourceFactory;
import ai.gebo.system.ingestion.model.IngestionHandlerConfig;

/**
 * AI generated comments
 * 
 * Configuration class responsible for loading ingestion handlers from YAML configuration.
 * This class maps properties from the 'ai.gebo.contents.reading.library' configuration section
 * in the 'readers.yml' file to a list of ingestion handler configurations.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.contents.reading.library")
@PropertySource(value = "classpath:/bundle-readers/readers.yml", factory = GeboYamlPropertySourceFactory.class)
public class GeboContentReadingConfig {
	// List to store ingestion handler configurations loaded from the YAML file
	List<IngestionHandlerConfig> handlers=new ArrayList<IngestionHandlerConfig>();
	
	/**
	 * Default constructor for GeboContentReadingConfig.
	 */
	public GeboContentReadingConfig() {
		
	}
	
	/**
	 * Retrieves the list of ingestion handler configurations.
	 * 
	 * @return a list of IngestionHandlerConfig objects
	 */
	public List<IngestionHandlerConfig> getHandlers() {
		return handlers;
	}
	
	/**
	 * Sets the list of ingestion handler configurations.
	 * 
	 * @param handlers the list of IngestionHandlerConfig objects to set
	 */
	public void setHandlers(List<IngestionHandlerConfig> handlers) {
		this.handlers = handlers;
	}

}