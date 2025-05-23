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
 * OAuth authentication class that implements the Authentication interface.
 * Handles OAuth authentication for API calls by adding authorization headers
 * with a Bearer token.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")
public class OAuth implements Authentication {
    /**
     * The OAuth access token used for authentication.
     */
    private String accessToken;

    /**
     * Gets the current OAuth access token.
     * 
     * @return The access token value as a String
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets the OAuth access token to use for authentication.
     * 
     * @param accessToken The access token value to set
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Applies the OAuth authentication to the request parameters.
     * Adds the Authorization header with the Bearer token if an access token is available.
     * 
     * @param queryParams The query parameters for the request
     * @param headerParams The header parameters for the request
     */
    @Override
    public void applyToParams(MultiValueMap<String, String> queryParams, HttpHeaders headerParams) {
        if (accessToken != null) {
            // Add Bearer token to Authorization header
            headerParams.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        }
    }
}