/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;


import ai.gebo.llms.abstraction.layer.services.config.GeboLlmsClientConfig;
import okhttp3.Interceptor;

/**
 * Provides all transport clients and configuration required by LLM model builders.
 * Spring-transport providers (Mistral, Ollama, Google GenAI) consume the WebClient/RestClient
 * builders and the core RetryTemplate. OkHttp-based providers (OpenAI, Anthropic) read the raw
 * connection and retry parameters from getClientConfig() and construct their own HTTP client
 * customizer inside their own module — keeping this interface free of any vendor-specific types.
 * All values are driven by application.yml via GeboDefaultLlmsServiceClientsProviderConfig.
 */
public interface IGLlmsServiceClientsProvider {

    /**
     * Pre-configured WebClient builder (Reactor Netty, response timeout applied).
     * Used by Spring-transport LLM providers such as Mistral, Ollama, Google GenAI.
     */
    org.springframework.web.reactive.function.client.WebClient.Builder getWebClientBuilder();

    /**
     * Pre-configured RestClient builder (Reactor Netty, response timeout applied).
     * Used by Spring-transport LLM providers such as Mistral, Ollama, Google GenAI.
     */
    org.springframework.web.client.RestClient.Builder getRestClientBuilder();

    /**
     * Spring Framework core RetryTemplate ({@code org.springframework.core.retry.RetryTemplate})
     * configured with the backoff interval and max-attempts from application.yml: max-attempts
     * counts every call, the first one included, as the OkHttp interceptor counts them. The
     * retry type Spring AI 2 model builders (Mistral, Deepseek, Vertex) take, and the one our
     * own clients (the standard ranker client) use: Spring Retry is no longer a dependency.
     */
    org.springframework.core.retry.RetryTemplate getCoreRetryTemplate();

    /**
     * Raw connection and retry parameters from application.yml.
     * OkHttp-based LLM modules (OpenAI, Anthropic) read these values and build their own
     * vendor-specific HTTP client customizer inside their own module.
     */
    GeboLlmsClientConfig getClientConfig();

    /**
     * Pre-built OkHttp Interceptor that retries on HTTP 429/502/503/504 with fixed backoff.
     * Retry attempts and backoff interval come from application.yml via GeboDefaultLlmsServiceClientsProviderConfig.
     * OkHttp is a general-purpose HTTP client, not specific to any LLM vendor.
     * LLM modules add this interceptor to their vendor-specific HTTP client builder.
     */
    Interceptor getOkHttpRetryInterceptor();
}
