/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security.config;

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
     * Retrieves the Auth instance for authentication configuration.
     * 
     * @return the Auth instance.
     */
    public Auth getAuth() {
        return auth;
    }

    
}