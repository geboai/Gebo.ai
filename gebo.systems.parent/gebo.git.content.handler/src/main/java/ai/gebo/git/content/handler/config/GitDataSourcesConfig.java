/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.git.content.handler.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.git.content.handler.GGitProjectEndpoint;

/**
 * Configuration class for Git data sources.
 * This class binds properties from the application configuration with prefix "ai.gebo.git" to fields.
 * AI generated comments
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.git")
public class GitDataSourcesConfig {
	
	/**
	 * List of Git project endpoints to be configured from application properties.
	 * Initialized as an empty ArrayList.
	 */
	private List<GGitProjectEndpoint>  endpoints=new ArrayList<GGitProjectEndpoint>();
	
	/**
	 * Default constructor for GitDataSourcesConfig.
	 */
	public GitDataSourcesConfig() {
		
	}
	
	/**
	 * Gets the list of configured Git project endpoints.
	 * 
	 * @return The list of Git project endpoints
	 */
	public List<GGitProjectEndpoint> getEndpoints() {
		return endpoints;
	}
	
	/**
	 * Sets the list of Git project endpoints.
	 * 
	 * @param endpoints The list of Git project endpoints to set
	 */
	public void setEndpoints(List<GGitProjectEndpoint> endpoints) {
		this.endpoints = endpoints;
	}
	
	
	

}