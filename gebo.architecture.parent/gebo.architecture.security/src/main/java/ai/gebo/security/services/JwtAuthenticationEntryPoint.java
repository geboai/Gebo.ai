/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security.services;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Gebo.ai comment agent
 *
 * Implementation of the {@link AuthenticationEntryPoint} interface.
 * This entry point is used to commence an authentication scheme when an 
 * unauthenticated client tries to access a secured resource.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * This method is called whenever an exception is thrown due to an 
     * unauthenticated user trying to access a resource that requires authentication.
     * 
     * @param request       the request that resulted in an AuthenticationException
     * @param response      the response to populate with the authentication status
     * @param authException the exception that caused the invocation
     * @throws IOException      if an input or output error occurs
     * @throws ServletException if the service fails to handle the request
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // Check if the request method is OPTIONS; typically used in CORS preflight requests
        if (request.getMethod() != null && request.getMethod().equals("OPTIONS")) {
            // If the method is OPTIONS, accept the request
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } else {
            // Otherwise, return an unauthorized status
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        // Write the error message to the response
        PrintWriter writer = response.getWriter();
        writer.println("Access Denied !! " + authException.getMessage());
    }
}