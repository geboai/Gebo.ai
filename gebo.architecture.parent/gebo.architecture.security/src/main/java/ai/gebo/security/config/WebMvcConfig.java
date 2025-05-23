/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Gebo.ai comment agent
 * Configuration class to set up web-specific settings like CORS and view controllers.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // Maximum age (in seconds) for CORS preflight requests
    private final long MAX_AGE_SECS = 3600;

    // Array of allowed origins for CORS, injected from application properties
    @Value("${ai.gebo.security.cors.allowedOrigins}")
    private String[] allowedOrigins;

    /**
     * Configure CORS mappings to allow cross-origin requests.
     * 
     * @param registry the registry where CORS configurations are added.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(allowedOrigins) // Allow specified origins
                .allowedMethods("GET", "OPTIONS", "HEAD", "PUT", "POST", "DELETE") // Specified HTTP methods
                .allowCredentials(true) // Allow credentials to be included in requests
                .allowedHeaders("Access-Control-Allow-Headers", "Origin", "Accept", 
                                "Authorization", "X-Requested-With",
                                "Content-Type", "Access-Control-Request-Method", 
                                "Access-Control-Request-Headers") // Allowed headers
                .maxAge(MAX_AGE_SECS); // Set maximum age for CORS preflight
    }

    /**
     * Configure view controllers to direct to specific view names without needing
     * an explicit controller.
     *
     * @param registry the registry where view controllers are added.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Map root and UI paths to the main index.html view
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.addViewController("/ui/").setViewName("forward:/index.html");
        registry.addViewController("/ui/**").setViewName("forward:/index.html");
    }
}