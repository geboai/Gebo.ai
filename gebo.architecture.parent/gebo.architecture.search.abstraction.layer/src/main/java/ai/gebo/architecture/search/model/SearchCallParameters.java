/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.search.model;

import java.time.Duration;

import ai.gebo.architecture.search.config.SearchCallsConfig;

/**
 * How a search service is called during a search or the download of a search
 * result: the timeouts its client software applies to the connection and to the
 * answer, and the times a failure that can pass is tried again. A service applies
 * them in its own client where it can, and only on the search calls: the clients of
 * the content integration keep their own settings.
 *
 * @param connectTimeout time a call may take to connect
 * @param readTimeout    time a call may wait for the answer
 * @param retries        times a failure that can pass is tried again (0: never)
 */
public record SearchCallParameters(Duration connectTimeout, Duration readTimeout, int retries) {
	/** The request parameter a remote search is given the connect timeout with, in milliseconds. */
	public static final String CONNECT_TIMEOUT_MILLIS_PARAM = "connectTimeoutMillis";
	/** The request parameter a remote search is given the read timeout with, in milliseconds. */
	public static final String READ_TIMEOUT_MILLIS_PARAM = "readTimeoutMillis";
	/** The request parameter a remote search is given the retries with. */
	public static final String RETRIES_PARAM = "retries";

	/** The parameters configured for the search calls (see {@link SearchCallsConfig}). */
	public static SearchCallParameters of(SearchCallsConfig config) {
		final SearchCallsConfig source = config != null ? config : new SearchCallsConfig();
		return new SearchCallParameters(source.httpConnectTimeout(), source.httpReadTimeout(),
				source.effectiveRetries());
	}

	/**
	 * These parameters with those a remote caller sent in place of them: a timeout
	 * not given or not positive, or retries not given, keep these.
	 */
	public SearchCallParameters overriddenBy(Integer connectTimeoutMillis, Integer readTimeoutMillis,
			Integer retries) {
		return new SearchCallParameters(
				connectTimeoutMillis != null && connectTimeoutMillis > 0 ? Duration.ofMillis(connectTimeoutMillis)
						: connectTimeout,
				readTimeoutMillis != null && readTimeoutMillis > 0 ? Duration.ofMillis(readTimeoutMillis) : readTimeout,
				retries != null ? Math.max(0, retries) : this.retries);
	}

	/** These parameters with other retries (0: never tried again). */
	public SearchCallParameters withRetries(int retries) {
		return new SearchCallParameters(connectTimeout, readTimeout, Math.max(0, retries));
	}

	/** The connect timeout in milliseconds, as most clients take it. */
	public int connectTimeoutMillis() {
		return (int) Math.min(Integer.MAX_VALUE, connectTimeout.toMillis());
	}

	/** The read timeout in milliseconds, as most clients take it. */
	public int readTimeoutMillis() {
		return (int) Math.min(Integer.MAX_VALUE, readTimeout.toMillis());
	}
}
