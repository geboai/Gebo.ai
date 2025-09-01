/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security.services.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Gebo.ai comment agent
 * 
 * Implementation of AuthenticationEntryPoint that sends a 401 Unauthorized response
 * whenever an unauthenticated user requests a protected HTTP resource.
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // Logger to log authentication errors
    private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

    /**
     * This method is triggered anytime an unauthenticated user requests a secured HTTP resource 
     * and an AuthenticationException is thrown.
     *
     * @param request       the HttpServletRequest
     * @param response      the HttpServletResponse
     * @param authException the exception that caused the invocation
     * @throws IOException if an input or output error is detected
     * @throws jakarta.servlet.ServletException if a servlet-specific error occurs
     */
    @Override
    public void commence(jakarta.servlet.http.HttpServletRequest request,
                         jakarta.servlet.http.HttpServletResponse response, AuthenticationException authException)
            throws IOException, jakarta.servlet.ServletException {
        // Log the authentication error with its message
        logger.error("Responding with unauthorized error. Message - {}", authException);
        // Respond with a 401 Unauthorized status code and the exception message
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getLocalizedMessage());
    }
}