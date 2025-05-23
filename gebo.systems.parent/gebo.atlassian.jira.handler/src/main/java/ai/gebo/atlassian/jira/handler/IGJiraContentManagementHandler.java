/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.jira.handler;

import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;

/**
 * AI generated comments
 * 
 * Interface that extends the generic content management system handler for Jira specific operations.
 * This interface provides Jira-specific content management functionality by specializing
 * the parent interface with Jira system and project endpoint types.
 * 
 * It acts as a bridge between the generic content management abstraction layer
 * and the Jira-specific implementation.
 */
public interface IGJiraContentManagementHandler
		extends IGContentManagementSystemHandler<GJiraSystem, GJiraProjectEndpoint> {

}