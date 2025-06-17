package ai.gebo.security.model;

/**
 * This enumeration represents the different types of authentication providers supported.
 * 
 *  - LOCAL_JWT: Authentication using JSON Web Tokens for local users.
 *  - OAUTH2: Authentication using OAuth2 protocol.
 *  - LDAP: Authentication using LDAP (Lightweight Directory Access Protocol).
 * 
 * AI generated comments
 */
public enum AuthProviderType {
    LOCAL_JWT, OAUTH2, LDAP
}