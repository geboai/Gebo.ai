/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.git.content.handler.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.architecture.persistence.GDynamicConfigurationSourceAdapter;
import ai.gebo.git.content.handler.GGitContentManagementSystem;
import ai.gebo.git.content.handler.repositories.GitSystemsRepository;

/**
 * AI generated comments
 * 
 * A service that provides dynamic configuration for Git content management systems.
 * This class extends the generic dynamic configuration source adapter and implements
 * the dynamic configuration source interface specifically for Git content management systems.
 * 
 * It acts as a bridge between the Git systems repository and the configuration system,
 * allowing the application to dynamically load and manage Git content management system
 * configurations at runtime.
 */
@Service
public class GitSystemDynamicSource extends
		GDynamicConfigurationSourceAdapter<GGitContentManagementSystem, GitSystemsRepository>
		implements IGDynamicConfigurationSource<GGitContentManagementSystem> {

}