/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.anthropic.http;

import java.time.Duration;

import org.springframework.ai.anthropic.http.okhttp.AnthropicHttpClientBuilderCustomizer;

import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.llms.abstraction.layer.services.config.GeboLlmsClientConfig;

/**
 * Thin adapter: maps the transport-agnostic objects provided by {@link IGLlmsServiceClientsProvider}
 * (timeouts from {@link GeboLlmsClientConfig}, retry interceptor from {@code getOkHttpRetryInterceptor()})
 * to the Anthropic-specific {@link AnthropicHttpClientBuilderCustomizer}.
 * All Anthropic SDK types are confined to this module.
 */
public final class AnthropicClientCustomizer {

    private AnthropicClientCustomizer() {}

    public static AnthropicHttpClientBuilderCustomizer from(IGLlmsServiceClientsProvider provider) {
        GeboLlmsClientConfig cfg = provider.getClientConfig();
        // the thinking tap first: it sees the request and the stream the retries end with
        return builder -> builder
                .timeout(Duration.ofMillis(cfg.getReadTimeoutMs()))
                .interceptor(AnthropicThinkingTap.INSTANCE)
                .interceptor(provider.getOkHttpRetryInterceptor());
    }

    /**
     * The configured request timeout, to be set on the model's {@code AnthropicChatOptions}
     * as well as on the HTTP client.
     * <p>
     * Setting it on the client alone is not enough: when the options carry no timeout,
     * Spring AI's {@code AnthropicSetup} applies its own 60 seconds to the SDK client,
     * as the whole call's limit, after running the customizer. Claude answers longer
     * than a minute were therefore cancelled mid-stream (a CANCEL stream reset at
     * 60.0 s) while {@code ai.gebo.llms.default.clients.config.web-client-config.response-timeout}
     * said otherwise - the same trap {@code OpenAiClientCustomizer.requestTimeout}
     * documents for the OpenAI options.
     */
    public static Duration requestTimeout(IGLlmsServiceClientsProvider provider) {
        return Duration.ofMillis(provider.getClientConfig().getReadTimeoutMs());
    }
}
