/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Gebo.ai comment agent
 * Custom exception class for handling OAuth2 authentication processing errors.
 * Extends the Spring Security AuthenticationException to provide additional
 * context for OAuth2-specific authentication issues.
 */
public class OAuth2AuthenticationProcessingException extends AuthenticationException {

    /**
     * Constructs a new OAuth2AuthenticationProcessingException with the specified detail
     * message and root cause.
     *
     * @param msg the detail message.
     * @param t the root cause of the exception.
     */
    public OAuth2AuthenticationProcessingException(String msg, Throwable t) {
        super(msg, t);
    }

    /**
     * Constructs a new OAuth2AuthenticationProcessingException with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public OAuth2AuthenticationProcessingException(String msg) {
        super(msg);
    }
}