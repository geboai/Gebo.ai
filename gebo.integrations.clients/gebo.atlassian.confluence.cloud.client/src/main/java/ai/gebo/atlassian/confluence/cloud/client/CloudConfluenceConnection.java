/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.cloud.client;

import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;

import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import lombok.Getter;
import lombok.Setter;

/**
 * AI generated comments
 * 
 * Represents a connection to a Confluence Cloud instance.
 * This class provides the necessary configurations to connect
 * to a Confluence Cloud instance using the provided credentials and base URL.
 * It also includes a method to generate HTTP headers required for authentication.
 */
public class CloudConfluenceConnection {
    
    /** Username for authentication with the Confluence Cloud instance */
    @Getter
    @Setter
    String username = null;

    /** Password for authentication with the Confluence Cloud instance */
    @Getter
    @Setter
    String password = null;

    /** Base URL of the Confluence Cloud instance */
    @Getter
    @Setter
    String baseUrl = null;

    /** Wrapper service for REST templates to facilitate API interactions */
    final RestTemplateWrapperService restTemplate;

    /**
     * Constructs a CloudConfluenceConnection with the specified RestTemplateWrapperService.
     *
     * @param templateWrapperService the RestTemplateWrapperService used for making API calls
     */
    public CloudConfluenceConnection(RestTemplateWrapperService templateWrapperService) {
        this.restTemplate = templateWrapperService;
    }

    /**
     * Creates the HTTP headers required for basic authentication.
     *
     * @return HttpHeaders containing the Authorization header with encoded credentials
     */
    public HttpHeaders createHeaders() {
        return new HttpHeaders() {
            {
                // Concatenate username and password with a colon
                String auth = username + ":" + password;
                
                // Encode the authentication string using Base64
                byte[] encodedAuth = org.apache.tomcat.util.codec.binary.Base64
                        .encodeBase64(auth.getBytes(Charset.forName("US-ASCII")), false);
                
                // Create the Authorization header using Basic authentication scheme
                String authHeader = "Basic " + new String(encodedAuth);
                
                // Set the Authorization header
                set("Authorization", authHeader);
            }
        };
    }

    /**
     * Initializes the connection to the Confluence Cloud instance.
     * This placeholder method can be expanded to include initialization logic.
     */
    public void initialize() {
        // Initialization logic can be added here
    }
}