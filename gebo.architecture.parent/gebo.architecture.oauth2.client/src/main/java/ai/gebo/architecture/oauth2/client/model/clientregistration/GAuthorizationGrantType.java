/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.oauth2.client.model.clientregistration;

/**
 * Gebo.ai comment agent
 *
 * An enumeration of OAuth 2.0 Authorization Grant Types. 
 * These constants represent the various methods a client can use 
 * to obtain an access token.
 */
public enum GAuthorizationGrantType {
    /**
     * Authorization Code Grant: Typically used by web and mobile 
     * apps to request authorization to access resources.
     */
    AUTHORIZATION_CODE, 
    
    /**
     * Refresh Token Grant: Used to obtain a new access token by 
     * exchanging a valid refresh token.
     */
    REFRESH_TOKEN, 
    
    /**
     * Client Credentials Grant: Used by applications to obtain 
     * an access token outside of the context of a user.
     */
    CLIENT_CREDENTIALS, 
    
    /**
     * Password Grant: Used to obtain an access token by directly 
     * using the resource owner's username and password.
     */
    PASSWORD, 
    
    /**
     * JWT Bearer Grant: Used to obtain an access token by 
     * exchanging a valid JWT.
     */
    JWT_BEARER, 
    
    /**
     * Device Code Grant: Used by devices that lack a browser or 
     * have limited input capabilities.
     */
    DEVICE_CODE, 
    
    /**
     * Token Exchange Grant: Used to exchange a security token 
     * for an access token.
     */
    TOKEN_EXCHANGE
}