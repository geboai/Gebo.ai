/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.sharepoint.handler;

import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;

/**
 * AI generated comments
 * 
 * This interface extends the general content management system handler interface 
 * specifically for Sharepoint implementations. It establishes a contract for handling 
 * Sharepoint content management systems and their project endpoints.
 * 
 * The interface doesn't define additional methods beyond those inherited from
 * the parent interface, but provides type parameters specific to Sharepoint implementations.
 */
public interface IGSharepointContentManagementSystemHandler
		extends IGContentManagementSystemHandler<GSharepointContentManagementSystem, GSharepointProjectEndpoint> {
	
}