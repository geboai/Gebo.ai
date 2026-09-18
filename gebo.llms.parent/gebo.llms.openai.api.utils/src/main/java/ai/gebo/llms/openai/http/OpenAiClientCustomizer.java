/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai.http;

import java.time.Duration;

import org.springframework.ai.openai.http.okhttp.OpenAiHttpClientBuilderCustomizer;

import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.llms.abstraction.layer.services.config.GeboLlmsClientConfig;

/**
 * Thin adapter: maps the transport-agnostic objects provided by {@link IGLlmsServiceClientsProvider}
 * (timeouts from {@link GeboLlmsClientConfig}, retry interceptor from {@code getOkHttpRetryInterceptor()})
 * to the OpenAI-specific {@link OpenAiHttpClientBuilderCustomizer}.
 * Lives in the shared OpenAI utils module so both the OpenAI module and the
 * OpenAI-compatible module reuse one adapter. All OpenAI SDK types are confined here.
 */
public final class OpenAiClientCustomizer {

    private OpenAiClientCustomizer() {}

    public static OpenAiHttpClientBuilderCustomizer from(IGLlmsServiceClientsProvider provider) {
        GeboLlmsClientConfig cfg = provider.getClientConfig();
        return builder -> builder
                .timeout(Duration.ofMillis(cfg.getReadTimeoutMs()))
                .interceptor(provider.getOkHttpRetryInterceptor());
    }

    /**
     * The configured request timeout, to be set on the model's {@code Options} builder
     * as well as on the HTTP client.
     * <p>
     * Setting it on the client alone is not enough: Spring AI's
     * {@code AbstractOpenAiOptions.DEFAULT_TIMEOUT} is 60 seconds, every OpenAI options
     * class inherits it, and {@code OpenAiSetup.setupSyncClient/setupAsyncClient} apply
     * {@code options.getTimeout()} to the SDK {@code ClientOptions} AFTER running the
     * {@link OpenAiHttpClientBuilderCustomizer}. The customizer's value is therefore
     * overridden per request, and long generations were cut at 60s mid-stream while
     * {@code ai.gebo.llms.default.clients.config.web-client-config.response-timeout}
     * said otherwise. Pass this to {@code Options.builder().timeout(..)} so both layers
     * carry the same configured value.
     */
    public static Duration requestTimeout(IGLlmsServiceClientsProvider provider) {
        return Duration.ofMillis(provider.getClientConfig().getReadTimeoutMs());
    }
}
