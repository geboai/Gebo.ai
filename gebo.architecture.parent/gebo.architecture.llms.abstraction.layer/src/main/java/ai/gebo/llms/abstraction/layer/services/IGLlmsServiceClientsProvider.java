/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services;

import org.springframework.retry.support.RetryTemplate;

/**
 * AI generated comments
 * Interface that provides various service clients necessary for handling HTTP requests.
 */
public interface IGLlmsServiceClientsProvider {

    /**
     * Provides a builder for creating instances of WebClient, 
     * which is used to perform asynchronous HTTP requests.
     * 
     * @return a WebClient.Builder instance
     */
    org.springframework.web.reactive.function.client.WebClient.Builder getWebClientBuilder();

    /**
     * Provides a builder for creating instances of RestClient, 
     * which is used to perform synchronous HTTP requests.
     * 
     * @return a RestClient.Builder instance
     */
    org.springframework.web.client.RestClient.Builder getRestClientBuilder();

    /**
     * Provides a RetryTemplate, which is used for retrying operations
     * in the event of failures.
     * 
     * @return a RetryTemplate instance
     */
    RetryTemplate getRetryTemplate();

}