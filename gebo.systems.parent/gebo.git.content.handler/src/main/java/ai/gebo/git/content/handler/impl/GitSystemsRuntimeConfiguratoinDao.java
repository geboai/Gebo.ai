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
import ai.gebo.git.content.handler.GGitContentManagementSystem;
import ai.gebo.git.content.handler.config.GitSystemsConfig;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemConfigurationDao;

/**
 * AI generated comments
 * 
 * Implementation of the content management system configuration DAO specifically for Git systems.
 * This service provides access to runtime configurations for Git-based content management systems.
 * It extends the abstract runtime configuration DAO and implements the content management
 * system configuration interface for Git systems.
 */
@Service
public class GitSystemsRuntimeConfiguratoinDao extends
		GAbstractRuntimeConfigurationDao<GGitContentManagementSystem>
		implements IGContentManagementSystemConfigurationDao<GGitContentManagementSystem> {
	
	/**
	 * Constructs a new GitSystemsRuntimeConfiguratoinDao with the specified configuration and optional
	 * dynamic configuration source.
	 *
	 * @param config The Git systems configuration containing predefined systems
	 * @param source Optional dynamic configuration source for Git content management systems
	 */
	public GitSystemsRuntimeConfiguratoinDao(@Autowired GitSystemsConfig config,
			@Autowired(required = false) IGDynamicConfigurationSource<GGitContentManagementSystem> source) {
		super(config.getSystems(), source);
	}

}