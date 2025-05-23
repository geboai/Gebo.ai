/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * AI generated comments
 * Service to authenticate requests using a JWT token.
 * Extends the OncePerRequestFilter to ensure it runs once per request dispatch.
 */
@Service
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Repository to save and retrieve the security context associated with the request.
     */
    private RequestAttributeSecurityContextRepository repository = new RequestAttributeSecurityContextRepository();

    /**
     * Service to provide methods for JWT token operations.
     */
    @Autowired
    private TokenProvider tokenProvider;

    /**
     * Service to load user-specific data.
     */
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Logger for logging actions within the filter.
     */
    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    /**
     * Method to filter each request to authenticate the user using JWT token.
     *
     * @param request the request made to the server
     * @param response the response for the request
     * @param filterChain provides a mechanism for filtering requests
     * @throws ServletException if any servlet related exception occurs
     * @throws IOException if any IO exception occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            jakarta.servlet.FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request); // Extracts JWT token from request
            if (logger.isDebugEnabled()) {
                logger.debug("Requested url=>" + request.getRequestURI() + " with token:" + jwt);
            }
            // Validates the extracted JWT token
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                // Retrieves user ID from validated token
                String userId = tokenProvider.getUserIdFromToken(jwt);

                // Loads UserDetails using the user ID
                UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                // Creates an authentication token with user details
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Sets authentication in the SecurityContext
                if (SecurityContextHolder.getContext() == null) {
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    context.setAuthentication(authentication);
                    SecurityContextHolder.setContext(context);
                } else {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                // Saves the security context using the repository
                this.repository.saveContext(SecurityContextHolder.getContext(), request, response);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        // Continues the filter chain
        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the request's Authorization header.
     *
     * @param request the request made to the server
     * @return the JWT token as a string or null if not found
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        // Retrieve Authorization header
        String bearerToken = request.getHeader("Authorization");
        // Validate and parse the JWT token from header
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}