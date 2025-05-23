/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jira.cloud.client.invoker.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

/**
 * AI generated comments
 * Authentication interface defines how authentication should be applied to API requests.
 * Implementing classes will provide specific authentication mechanisms such as
 * OAuth, Basic Auth, API Keys, etc. for Jira Cloud API requests.
 */
public interface Authentication {
    /** 
     * Apply authentication settings to header and / or query parameters.
     * @param queryParams The query parameters for the request 
     * @param headerParams The header parameters for the request
     */
    public void applyToParams(MultiValueMap<String, String> queryParams, HttpHeaders headerParams);
}