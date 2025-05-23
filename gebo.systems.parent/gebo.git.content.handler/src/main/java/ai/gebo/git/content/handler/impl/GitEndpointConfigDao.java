/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.git.content.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.git.content.handler.GGitProjectEndpoint;
import ai.gebo.git.content.handler.config.GitDataSourcesConfig;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;

/**
 * AI generated comments
 * 
 * Implementation of a DAO (Data Access Object) for Git project endpoints.
 * This service provides runtime configuration management for Git project endpoints,
 * extending the abstract runtime configuration DAO and implementing the project 
 * endpoint runtime configuration interface specifically for Git endpoints.
 */
@Service
public class GitEndpointConfigDao extends GAbstractRuntimeConfigurationDao<GGitProjectEndpoint>
		implements IGProjectEndpointRuntimeConfigurationDao<GGitProjectEndpoint> {
	
	/**
	 * Constructs a new GitEndpointConfigDao with the specified configuration and optional
	 * dynamic configuration source.
	 * 
	 * @param config The Git data sources configuration containing endpoint information
	 * @param source Optional dynamic configuration source for Git project endpoints
	 */
	public GitEndpointConfigDao(GitDataSourcesConfig config,
			@Autowired(required = false) IGDynamicConfigurationSource<GGitProjectEndpoint> source) {
		super(config.getEndpoints(), source);
	}

}