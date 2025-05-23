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

import ai.gebo.git.content.handler.GGitContentManagementSystem;

/**
 * AI generated comments
 * Configuration class for Git content management systems.
 * This class binds properties with the prefix "ai.gebo.git.config" from application properties
 * to the fields defined in this class.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.git.config")
public class GitSystemsConfig {

	/**
	 * List of Git content management systems configured in the application.
	 * Initialized as an empty ArrayList by default.
	 */
	private List<GGitContentManagementSystem>  systems=new ArrayList<GGitContentManagementSystem>();

	/**
	 * Default constructor for GitSystemsConfig.
	 */
	public GitSystemsConfig() {
		
	}

	/**
	 * Returns the list of configured Git content management systems.
	 * 
	 * @return list of Git content management systems
	 */
	public List<GGitContentManagementSystem> getSystems() {
		return systems;
	}

	/**
	 * Sets the list of Git content management systems.
	 * 
	 * @param systems the list of Git content management systems to set
	 */
	public void setSystems(List<GGitContentManagementSystem> systems) {
		this.systems = systems;
	}

}