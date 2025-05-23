/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.jira.handler;

import ai.gebo.atlassian.jira.handler.impl.model.JiraResourceReference;
import ai.gebo.systems.abstraction.layer.IGRemoteVirtualFilesystemConsumingService;

/**
 * AI generated comments
 * 
 * Interface that defines a service capable of consuming Jira's virtual filesystem.
 * This interface extends the general remote virtual filesystem consuming service
 * but specializes it for Jira-specific components, including:
 * - GJiraSystem as the system type
 * - GJiraProjectEndpoint as the endpoint type
 * - JiraResourceReference as the resource reference type
 * 
 * Implementations of this interface should provide functionality to work with
 * Jira resources through a virtual filesystem abstraction.
 */
public interface IGJiraVirtualFilesystemConsumingService
		extends IGRemoteVirtualFilesystemConsumingService<GJiraSystem, GJiraProjectEndpoint, JiraResourceReference> {

}