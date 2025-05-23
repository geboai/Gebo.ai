/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Configuration class for Gebo Default LLMS Service Clients Provider.
 * This class loads configuration properties prefixed with "ai.gebo.llms.default.clients.config".
 * It includes nested configurations for retry and web client settings.
 * 
 * AI generated comments
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.llms.default.clients.config")
@Data
public class GeboDefaultLlmsServiceClientsProviderConfig {


    /**
     * Nested configuration class for retry settings.
     * Includes parameters such as retry timeout, backoff interval, and maximum attempts.
     */
    @Data
    public static class RetryConfig {
        long retryTimeout = 80000; // Timeout in milliseconds before a retry is attempted
        long backoffInterval = 5000; // Interval in milliseconds between retry attempts
        int maxAttempts = 5; // Maximum number of retry attempts
    }

    /**
     * Nested configuration class for web client settings.
     * Includes parameters such as response timeout.
     */
    @Data
    public static class WebClientConfig {
        long responseTimeout = 80000; // Timeout in milliseconds to wait for a web client response
    }

    // Instance of RetryConfig with default configuration values
    private RetryConfig retryConfig = new RetryConfig();
    // Instance of WebClientConfig with default configuration values
    private WebClientConfig webClientConfig = new WebClientConfig();

    /**
     * Default constructor for GeboDefaultLlmsServiceClientsProviderConfig.
     * Initializes the retry and web client configurations with default values.
     */
    public GeboDefaultLlmsServiceClientsProviderConfig() {

    }

}

