/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.handler;

import ai.gebo.atlassian.confluence.handler.impl.model.ConfluenceResourceReference;
import ai.gebo.systems.abstraction.layer.IGRemoteVirtualFilesystemConsumingService;

/**
 * AI generated comments
 * 
 * This interface defines a Confluence-specific virtual filesystem consuming service.
 * It extends the generic remote virtual filesystem service interface, specializing it
 * for Confluence systems by specifying the Confluence-related type parameters.
 * 
 * The interface allows services to consume Confluence resources as if they were
 * part of a virtual filesystem, enabling standardized access to Confluence content.
 */
public interface IGConfluenceVirtualFilesystemConsumingService extends
		IGRemoteVirtualFilesystemConsumingService<GConfluenceSystem, GConfluenceProjectEndpoint, ConfluenceResourceReference> {

}