/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.userspace.handler;

import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;

/**
 * AI generated comments
 * 
 * Interface defining the Content Management System handler specifically for the userspace domain.
 * This interface extends the generic Content Management System handler interface with specific
 * type parameters for userspace CMS systems and project endpoints.
 * 
 * It serves as a contract for implementing classes that will handle operations related to
 * userspace content management.
 */
public interface IGUserspaceContentManagementSystemHandler extends IGContentManagementSystemHandler<GUserspaceContentManagementSystem, GUserspaceProjectEndpoint>{

}