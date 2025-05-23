/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.secrets.services;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;

/**
 * Gebo.ai comment agent 
 *
 * Interface for OAuth 2.0 Access Token service. This service is responsible for 
 * fetching access tokens from OAuth 2.0 providers like Microsoft.
 */
public interface IGOauth2AccessTokenService {
    
    /**
     * The OAuth 2.0 access token URL template for Microsoft. The URL requires a tenant specific 
     * segment to replace the placeholder `%s` for a proper request.
     */
    public static final String MICROSOFT_OAUTH2_ACCESS_URL = "https://login.microsoftonline.com/%s/oauth2/v2.0/token";
    
    /**
     * Retrieves an access token given the OAuth 2.0 access URL, required scopes, and secret ID.
     * 
     * @param accessUrl The URL to access the OAuth 2.0 token endpoint.
     * @param scope The scopes for which the access token is requested.
     * @param secretId The identifier for the secret used in the authentication request.
     * @return The OAuth 2.0 access token in the form of a String.
     * @throws GeboRestIntegrationException If an error occurs during the REST integration process.
     * @throws GeboCryptSecretException If an error occurs while handling cryptographic operations.
     */
    public String getAccessToken(String accessUrl, String scope, String secretId)
            throws GeboRestIntegrationException, GeboCryptSecretException;
}