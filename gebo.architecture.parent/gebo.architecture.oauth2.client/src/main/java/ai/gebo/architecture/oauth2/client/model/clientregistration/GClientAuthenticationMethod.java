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
 * Enumeration representing various client authentication methods
 * used in OAuth2 client registration.
 */
public enum GClientAuthenticationMethod {
    /** Client authentication using HTTP Basic Authentication with a client secret */
    CLIENT_SECRET_BASIC,
    
    /** Client authentication using HTTP POST with a client secret */
    CLIENT_SECRET_POST,
    
    /** Client authentication using JSON Web Token with a client secret */
    CLIENT_SECRET_JWT,
    
    /** Client authentication using JSON Web Token with a private key */
    PRIVATE_KEY_JWT,
    
    /** No client authentication is required */
    NONE,
    
    /** Client authentication using mutual TLS certificate bound access tokens */
    TLS_CLIENT_AUTH,
    
    /** Client authentication using self-signed TLS certificates */
    SELF_SIGNED_TLS_CLIENT_AUTH
}