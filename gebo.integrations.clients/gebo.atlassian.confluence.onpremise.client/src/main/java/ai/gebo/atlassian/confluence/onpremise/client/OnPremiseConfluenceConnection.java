/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.onpremise.client;

import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;

import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * AI generated comments
 * Represents a connection to an on-premise Confluence server.
 * Allows for creating HTTP headers for requests and initializes the connection.
 */
public class OnPremiseConfluenceConnection {

    /** The username for authentication. */
    @Getter
    @Setter
    String username = null;

    /** The password for authentication. */
    @Getter
    @Setter
    String password = null;

    /** The base URL of the Confluence server. */
    @Getter
    @Setter
    String baseUrl = null;

    /** The RestTemplate wrapper service used for HTTP requests. */
    final RestTemplateWrapperService restTemplate;

    /**
     * Constructs a new OnPremiseConfluenceConnection with the specified RestTemplateWrapperService.
     * 
     * @param templateWrapperService the RestTemplateWrapperService to use for HTTP requests
     */
    public OnPremiseConfluenceConnection(RestTemplateWrapperService templateWrapperService) {
        this.restTemplate = templateWrapperService;
    }

    /**
     * Creates HTTP headers with Basic Authentication using the provided username and password.
     * 
     * @return HttpHeaders containing the Basic Authorization header
     */
    public HttpHeaders createHeaders() {
        return new HttpHeaders() {
            {
                // Concatenates username and password with a colon for authentication
                String auth = username + ":" + password;
                // Encodes the authentication string using Base64 encoding
                byte[] encodedAuth = org.apache.tomcat.util.codec.binary.Base64
                        .encodeBase64(auth.getBytes(Charset.forName("US-ASCII")), false);
                // Prepares the 'Authorization' header
                String authHeader = "Basic " + new String(encodedAuth);
                // Sets the 'Authorization' header
                set("Authorization", authHeader);
            }
        };
    }

    /**
     * Initializes the connection to the Confluence server.
     * This method can be extended to perform more complex initialization logic.
     */
    public void initialize() {

    }
}