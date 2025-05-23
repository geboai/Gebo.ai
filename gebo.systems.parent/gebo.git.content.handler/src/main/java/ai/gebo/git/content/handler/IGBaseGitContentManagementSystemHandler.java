/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.git.content.handler;

import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;

/**
 * AI generated comments
 * 
 * Base interface for Git content management system handlers. This interface extends the general
 * content management system handler interface with specific Git integration functionality.
 * It uses generic types to specify the Git system configuration and endpoint configuration.
 * 
 * @param <SystemIntegrationConfigType> The type of Git content management system configuration
 * @param <EndpointConfigType> The type of Git project endpoint configuration
 */
public interface IGBaseGitContentManagementSystemHandler<SystemIntegrationConfigType extends GGitContentManagementSystem, EndpointConfigType extends GGitProjectEndpoint> extends IGContentManagementSystemHandler<SystemIntegrationConfigType, EndpointConfigType> {

}