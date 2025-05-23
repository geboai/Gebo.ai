/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Gebo.ai comment agent
 * Configuration properties for the Gebo application security settings.
 * These properties are prefixed with "ai.gebo.security" in the configuration files.
 */
@Configuration
@ConfigurationProperties(prefix = "ai.gebo.security")
public class GeboAppSecurityProperties {

    // Nested Auth class instance for authentication properties.
    private final Auth auth = new Auth();

    // Nested OAuth2 class instance for OAuth2 properties.
    private final OAuth2 oauth2 = new OAuth2();

    /**
     * Represents configuration properties related to authentication.
     * Includes token secret and expiration settings.
     */
    public static class Auth {
        private String tokenSecret;
        private long tokenExpirationMsec;

        /**
         * Retrieves the token secret used for authentication.
         * 
         * @return the token secret.
         */
        public String getTokenSecret() {
            return tokenSecret;
        }

        /**
         * Sets the token secret used for authentication.
         * 
         * @param tokenSecret the token secret.
         */
        public void setTokenSecret(String tokenSecret) {
            this.tokenSecret = tokenSecret;
        }

        /**
         * Retrieves the token expiration time in milliseconds.
         * 
         * @return the token expiration time in milliseconds.
         */
        public long getTokenExpirationMsec() {
            return tokenExpirationMsec;
        }

        /**
         * Sets the token expiration time in milliseconds.
         * 
         * @param tokenExpirationMsec the token expiration time in milliseconds.
         */
        public void setTokenExpirationMsec(long tokenExpirationMsec) {
            this.tokenExpirationMsec = tokenExpirationMsec;
        }
    }

    /**
     * Represents configuration properties related to OAuth2.
     * Includes authorized redirect URIs settings.
     */
    public static final class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();

        /**
         * Retrieves the list of authorized redirect URIs for OAuth2 authentication.
         * 
         * @return the list of authorized redirect URIs.
         */
        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }

        /**
         * Sets the list of authorized redirect URIs for OAuth2 authentication.
         * Allows method chaining by returning the current OAuth2 instance.
         * 
         * @param authorizedRedirectUris the list of authorized redirect URIs.
         * @return the current OAuth2 instance.
         */
        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }

    /**
     * Retrieves the Auth instance for authentication configuration.
     * 
     * @return the Auth instance.
     */
    public Auth getAuth() {
        return auth;
    }

    /**
     * Retrieves the OAuth2 instance for OAuth2 configuration.
     * 
     * @return the OAuth2 instance.
     */
    public OAuth2 getOauth2() {
        return oauth2;
    }
}