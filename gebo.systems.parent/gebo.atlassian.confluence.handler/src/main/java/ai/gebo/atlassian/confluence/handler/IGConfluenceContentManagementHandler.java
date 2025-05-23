/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.handler;

import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;

/**
 * AI generated comments
 * 
 * Interface that extends the generic Content Management System Handler for Confluence-specific
 * operations. This interface establishes a contract for handlers that work specifically with
 * Confluence systems and endpoints, inheriting all the generic CMS handler capabilities while
 * providing type safety for Confluence implementations.
 * 
 * The interface doesn't add any additional methods beyond what's provided by the parent interface,
 * but specializes the generic type parameters to work with Confluence-specific system and endpoint types.
 */
public interface IGConfluenceContentManagementHandler
		extends IGContentManagementSystemHandler<GConfluenceSystem, GConfluenceProjectEndpoint> {

}