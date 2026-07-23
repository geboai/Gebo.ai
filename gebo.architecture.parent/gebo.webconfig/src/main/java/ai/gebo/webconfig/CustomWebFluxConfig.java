/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.webconfig;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.JacksonJsonEncoder;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import tools.jackson.databind.json.JsonMapper;

// Gebo.ai comment agent

/**
 * Custom configuration class for Spring WebFlux.
 * <p>
 * This class customizes the default message codecs provided by WebFlux, particularly with respect to JSON encoding and 
 * Server-Sent Events (SSE) handling.
 * </p>
 */
@Configuration
public class CustomWebFluxConfig implements WebFluxConfigurer {

    // Instance of JsonMapper used for JSON serialization and deserialization
    JsonMapper objectMapper = JsonMapper.builder().build();

    /**
     * Default constructor for CustomWebFluxConfig.
     */
    public CustomWebFluxConfig() {
    }

    /**
     * Configure the HTTP message codecs for the application.
     * <p>
     * This method removes the default Server-Sent Event (SSE) message writers to avoid unwanted "data:" prefixes in messages,
     * and sets the JSON encoder to a custom Jackson2JsonEncoder using the specified ObjectMapper.
     * </p>
     *
     * @param configurer - the codec configurer to customize
     */
    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        // Retrieve the current list of HTTP message writers
        List<HttpMessageWriter<?>> writers = configurer.getWriters();
        
        // Remove SSE writers to prevent "data:" prefixes in SSE messages
        writers.removeIf(writer -> writer.getClass().getName().contains("ServerSentEventHttpMessageWriter"));

        // Set a custom JSON encoder using Jackson2JsonEncoder
        configurer.defaultCodecs().jacksonJsonEncoder(new JacksonJsonEncoder(objectMapper));
    }
}