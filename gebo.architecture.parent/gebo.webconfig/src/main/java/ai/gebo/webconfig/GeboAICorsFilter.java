/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.webconfig;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Gebo.ai Commentor signature - Gebo.ai comment agent
 * <p>
 * GeboAICorsFilter is a filter implementation that adds CORS (Cross-Origin Resource Sharing) headers
 * to HTTP responses to handle cross-origin requests in a Spring application.
 * </p>
 */
//@Service
public class GeboAICorsFilter extends OncePerRequestFilter {

    /**
     * Adds necessary CORS headers to the response and handles OPTIONS pre-flight requests.
     *
     * @param httpServletRequest  the HttpServletRequest object
     * @param httpServletResponse the HttpServletResponse object
     * @param filterChain         the FilterChain object to pass request and response along the filter chain
     * @throws ServletException if an error occurs during the filtering process
     * @throws IOException      if an input or output error occurs during the processing
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Set CORS headers to allow all origins and credentials.
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST, DELETE");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin, Accept, Authorization, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");

        // Handle CORS pre-flight request (OPTIONS method check).
        if (httpServletRequest.getMethod().equals("OPTIONS")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
        }
        // Pass the request and response along the filter chain.
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}