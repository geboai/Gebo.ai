/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.config;

/**
 * Transport-agnostic snapshot of timeout and retry settings loaded from application.yml.
 * LLM modules that use native SDK HTTP clients (e.g. OkHttp-based OpenAI/Anthropic modules)
 * read these values and build their own vendor-specific client customizer inside their own module.
 */
public final class GeboLlmsClientConfig {

    private final long connectTimeoutMs;
    private final long readTimeoutMs;
    private final long writeTimeoutMs;
    private final long retryBackoffMs;
    private final int maxRetryAttempts;

    public GeboLlmsClientConfig(long connectTimeoutMs, long readTimeoutMs, long writeTimeoutMs,
            long retryBackoffMs, int maxRetryAttempts) {
        this.connectTimeoutMs = connectTimeoutMs;
        this.readTimeoutMs = readTimeoutMs;
        this.writeTimeoutMs = writeTimeoutMs;
        this.retryBackoffMs = retryBackoffMs;
        this.maxRetryAttempts = maxRetryAttempts;
    }

    public long getConnectTimeoutMs()  { return connectTimeoutMs; }
    public long getReadTimeoutMs()     { return readTimeoutMs; }
    public long getWriteTimeoutMs()    { return writeTimeoutMs; }
    public long getRetryBackoffMs()    { return retryBackoffMs; }
    public int  getMaxRetryAttempts()  { return maxRetryAttempts; }
}
