/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.oauth2.client.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

/**
 * Gebo.ai comment agent
 * The Oauth2SaveModel class serves as a data model for storing information related to OAuth2
 * authentication. It extends PolimorphicJsonType and holds various fields such as client registration,
 * principal name, access token, refresh token, and authentication principal.
 */
public class Oauth2SaveModel extends PolimorphicJsonType {

    // Stores the client registration information for OAuth2 client
    private ClientRegistration clientRegistration = null;

    // Name of the principal (user or system) authenticated
    private String principalName = null;

    // Access token obtained after successful OAuth2 authentication
    private OAuth2AccessToken accessToken = null;

    // Refresh token to be used for obtaining a new access token once it expires
    private OAuth2RefreshToken refreshToken = null;

    // Stores the authentication principal which holds authentication details
    private Authentication authenticationPrincipal = null;

    /**
     * Retrieves the ClientRegistration object.
     *
     * @return the client registration
     */
    public ClientRegistration getClientRegistration() {
        return clientRegistration;
    }

    /**
     * Sets the ClientRegistration object.
     *
     * @param clientRegistration the client registration to set
     */
    public void setClientRegistration(ClientRegistration clientRegistration) {
        this.clientRegistration = clientRegistration;
    }

    /**
     * Retrieves the principal name.
     *
     * @return the principal name
     */
    public String getPrincipalName() {
        return principalName;
    }

    /**
     * Sets the principal name.
     *
     * @param principalName the principal name to set
     */
    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    /**
     * Retrieves the OAuth2 access token.
     *
     * @return the access token
     */
    public OAuth2AccessToken getAccessToken() {
        return accessToken;
    }

    /**
     * Sets the OAuth2 access token.
     *
     * @param accessToken the access token to set
     */
    public void setAccessToken(OAuth2AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Retrieves the OAuth2 refresh token.
     *
     * @return the refresh token
     */
    public OAuth2RefreshToken getRefreshToken() {
        return refreshToken;
    }

    /**
     * Sets the OAuth2 refresh token.
     *
     * @param refreshToken the refresh token to set
     */
    public void setRefreshToken(OAuth2RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Retrieves the authentication principal.
     *
     * @return the authentication principal
     */
    public Authentication getAuthenticationPrincipal() {
        return authenticationPrincipal;
    }

    /**
     * Sets the authentication principal.
     *
     * @param authenticationPrincipal the authentication principal to set
     */
    public void setAuthenticationPrincipal(Authentication authenticationPrincipal) {
        this.authenticationPrincipal = authenticationPrincipal;
    }
}