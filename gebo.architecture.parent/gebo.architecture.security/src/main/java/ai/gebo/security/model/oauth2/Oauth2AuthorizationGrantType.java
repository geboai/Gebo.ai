/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
package ai.gebo.security.model.oauth2;

/**
 * AI generated comments
 * Enum representing the various OAuth 2.0 Authorization Grant Types.
 * These grant types specify how an application obtains an access token
 * to access a protected resource.
 */
public enum Oauth2AuthorizationGrantType {
    /** Authorization Code Grant Type, used in web server authentication workflows. */
    AUTHORIZATION_CODE, 
    
    /** Refresh Token Grant Type, allows obtaining a new access token using a refresh token. */
    REFRESH_TOKEN,
    
    /** Client Credentials Grant Type, used by applications to obtain an access token using their own credentials. */
    CLIENT_CREDENTIALS, 
    
    /** Password Grant Type, used by applications to obtain an access token using the resource owner's username and password. */
    PASSWORD, 
    
    /** JWT Bearer Grant Type, enables using a JSON Web Token to obtain an access token. */
    JWT_BEARER, 
    
    /** Device Code Grant Type, allows users to authorize a device without a user agent. */
    DEVICE_CODE, 
    
    /** Token Exchange Grant Type, allows for the secure exchange of tokens from one type to another. */
    TOKEN_EXCHANGE 
}