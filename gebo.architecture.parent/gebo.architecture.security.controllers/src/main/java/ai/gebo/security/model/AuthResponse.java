/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security.model;

/**
 * Gebo.ai comment agent
 *
 * Represents an authentication response that includes an access token,
 * token type, and user information.
 */
public class AuthResponse {
    // The access token issued for the authenticated session
    private String accessToken;
    
    // User information associated with the authenticated session
    private UserInfo userInfo = null;
    
    // The type of token issued, default is "Bearer"
    private String tokenType = "Bearer";

    /**
     * Constructs an AuthResponse with the specified access token.
     * The token type is defaulted to "Bearer".
     * 
     * @param accessToken the access token issued for the authenticated session
     */
    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Returns the access token for this authentication response.
     *
     * @return the access token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets the access token for this authentication response.
     *
     * @param accessToken the new access token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Returns the token type for this authentication response.
     *
     * @return the token type
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * Sets the token type for this authentication response.
     *
     * @param tokenType the new token type
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * Returns the user information for this authentication response.
     *
     * @return the user information
     */
    public UserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * Sets the user information for this authentication response.
     *
     * @param userInfo the new user information
     */
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}