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
import ai.gebo.git.content.handler.GGitProjectEndpoint;
import ai.gebo.git.content.handler.repositories.GitEndpointRepository;

/**
 * AI generated comments
 * 
 * A service that provides dynamic configuration for Git project endpoints.
 * This class extends the dynamic configuration source adapter for Git project endpoints
 * and implements the dynamic configuration source interface.
 * 
 * It serves as a bridge between Git endpoint repository data and the configuration system,
 * allowing the application to dynamically retrieve and manage Git project endpoint configurations.
 */
@Service
public class GitEndpointDynamicSource
		extends GDynamicConfigurationSourceAdapter<GGitProjectEndpoint, GitEndpointRepository>
		implements IGDynamicConfigurationSource<GGitProjectEndpoint> {

}