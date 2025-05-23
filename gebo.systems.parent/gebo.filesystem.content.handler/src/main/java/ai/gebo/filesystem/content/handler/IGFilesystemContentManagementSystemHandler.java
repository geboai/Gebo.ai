/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.filesystem.content.handler;

import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;

/**
 * AI generated comments
 * 
 * This interface defines the contract for filesystem-based content management system handlers.
 * It extends the generic content management system handler interface, specifying the concrete
 * types for filesystem implementations (GFilesystemContentManagementSystem as the CMS type and
 * GFilesystemProjectEndpoint as the endpoint type).
 * 
 * This empty interface serves as a type-specific extension of the parent interface without
 * adding additional method requirements.
 */
public interface IGFilesystemContentManagementSystemHandler
		extends IGContentManagementSystemHandler<GFilesystemContentManagementSystem, GFilesystemProjectEndpoint> {

}