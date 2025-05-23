/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jira.cloud.client.invoker.auth;

/**
 * Enumeration of OAuth flow types supported by the authentication mechanism.
 * AI generated comments
 * 
 * <p>This enum defines the different OAuth flow types that can be used for authentication:
 * <ul>
 *   <li>accessCode - Authorization code flow, where server exchanges auth code for access token</li>
 *   <li>implicit - Client-side flow without secret key exchange</li>
 *   <li>password - Resource owner password credentials flow</li>
 *   <li>application - Client credentials flow for server-to-server authentication</li>
 * </ul>
 * </p>
 */
public enum OAuthFlow {
    accessCode, implicit, password, application
}