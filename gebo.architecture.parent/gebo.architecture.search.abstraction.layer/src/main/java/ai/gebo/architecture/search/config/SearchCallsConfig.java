/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.search.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * How the search services are called: best effort, each search on a system bounded
 * by a timeout coherent with a sloppy network, not tried again unless configured.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.search.calls")
@Data
public class SearchCallsConfig {
	public static final int DEFAULT_RETRIES = 0;
	public static final int DEFAULT_TIMEOUT_SECONDS = 60;
	public static final long DEFAULT_RETRY_PAUSE_MILLIS = 1000L;
	public static final int DEFAULT_HTTP_CONNECT_TIMEOUT_SECONDS = 15;
	public static final int DEFAULT_HTTP_READ_TIMEOUT_SECONDS = 60;

	/**
	 * Times a search failing for a reason that can pass (the system not responding,
	 * a network failure, a server error) is tried again; none by default.
	 */
	private int retries = DEFAULT_RETRIES;
	/** Most time a search on one system may take before it is reported as not responding. */
	private int timeoutSeconds = DEFAULT_TIMEOUT_SECONDS;
	/** Pause before a search is tried again. */
	private long retryPauseMillis = DEFAULT_RETRY_PAUSE_MILLIS;
	/** Time the web search providers' HTTP clients may take to connect. */
	private int httpConnectTimeoutSeconds = DEFAULT_HTTP_CONNECT_TIMEOUT_SECONDS;
	/** Time the web search providers' HTTP clients may wait for an answer. */
	private int httpReadTimeoutSeconds = DEFAULT_HTTP_READ_TIMEOUT_SECONDS;

	/** The timeout of a search on one system, the default one when not a positive number. */
	public Duration timeout() {
		return Duration.ofSeconds(timeoutSeconds > 0 ? timeoutSeconds : DEFAULT_TIMEOUT_SECONDS);
	}

	/** The retries of a search, none when not a positive number. */
	public int effectiveRetries() {
		return Math.max(0, retries);
	}

	public Duration httpConnectTimeout() {
		return Duration.ofSeconds(
				httpConnectTimeoutSeconds > 0 ? httpConnectTimeoutSeconds : DEFAULT_HTTP_CONNECT_TIMEOUT_SECONDS);
	}

	public Duration httpReadTimeout() {
		return Duration.ofSeconds(httpReadTimeoutSeconds > 0 ? httpReadTimeoutSeconds : DEFAULT_HTTP_READ_TIMEOUT_SECONDS);
	}
}
