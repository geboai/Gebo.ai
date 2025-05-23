/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.uploads.content.handler;

import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;

/**
 * AI generated comments
 * 
 * Interface for handling upload-specific content management system operations.
 * This interface extends the base content management system handler interface
 * with specialized types for uploads functionality.
 * 
 * It inherits all methods from the parent interface while specifying the concrete
 * types for the uploads content management system and project endpoint.
 */
public interface IGUploadsContentManagementSystemHandler
		extends IGContentManagementSystemHandler<GUploadsContentManagementSystem, GUploadsProjectEndpoint> {

}