/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.search.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * The best-effort size probe of the web search results (see
 * {@code WebResultSizeProbe}): after a search, a HEAD request per distinct result
 * URL fills the result's size from its declared Content-Length. Set via
 * {@code ai.gebo.websearch.size-probe.*} in application.yml.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.websearch.size-probe")
@Data
public class WebResultSizeProbeConfig {
	/** Default time a single HEAD request may take to connect, and then to answer. */
	public static final int DEFAULT_TIMEOUT_MS = 1500;
	/** Default HEAD requests running at the same time. */
	public static final int DEFAULT_PARALLELISM = 8;

	/** Whether the web search results are probed for their size. */
	private boolean enabled = true;
	/**
	 * Time a HEAD request may take to connect, and then to answer: kept low, the probe
	 * is best effort and the search waits for it.
	 */
	private int timeoutMs = DEFAULT_TIMEOUT_MS;
	/** HEAD requests running at the same time. */
	private int parallelism = DEFAULT_PARALLELISM;
}
