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
 * Enumeration representing the various OAuth 2.0 Client Authentication Methods.
 * These methods define how a client can authenticate itself to the authorization server.
 * 
 * @see <a href="https://tools.ietf.org/html/rfc6749">The OAuth 2.0 Authorization Framework</a>
 * 
 * AI generated comments
 */
public enum Oauth2ClientAuthMethod {
    /**
     * Client authentication method using HTTP Basic authentication scheme.
     */
    CLIENT_SECRET_BASIC,

    /**
     * Client authentication method using a client secret in the request body.
     */
    CLIENT_SECRET_POST,

    /**
     * Client authentication method using a JSON Web Token (JWT) signed with a client secret.
     */
    CLIENT_SECRET_JWT,

    /**
     * Client authentication method using a JSON Web Token (JWT) signed with a private key.
     */
    PRIVATE_KEY_JWT,

    /**
     * No client authentication.
     */
    NONE,

    /**
     * Client authentication method using Transport Layer Security (TLS) client certificate.
     */
    TLS_CLIENT_AUTH,

    /**
     * Client authentication method using a self-signed TLS client certificate.
     */
    SELF_SIGNED_TLS_CLIENT_AUTH
}