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
 * How the results of the services searching an open network (the web, see
 * {@link ai.gebo.architecture.search.model.SearchResultsLoading#OPEN_NETWORK}) are
 * loaded: wide, each page and the whole loading within a deadline, grouped by host
 * with a per-host policy so no site is overloaded or blocks the caller. A value that
 * is not a positive number is the default.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.search.open-network-loading")
@Data
public class OpenNetworkLoadingConfig {
	public static final boolean DEFAULT_CAPPED = false;
	public static final int DEFAULT_CANDIDATES_CAP = 20;
	public static final int DEFAULT_PER_PAGE_DEADLINE_SECONDS = 60;
	public static final int DEFAULT_LOADING_PHASE_DEADLINE_SECONDS = 60;
	public static final int DEFAULT_PER_HOST_CONCURRENCY = 2;
	public static final long DEFAULT_PER_HOST_PAUSE_MILLIS = 500L;
	public static final int DEFAULT_PER_HOST_FAILURES_BEFORE_SKIP = 2;

	/**
	 * Whether the candidates loaded are capped at {@link #candidatesCap}; when not, every
	 * candidate of the pool is loaded.
	 */
	private boolean capped = DEFAULT_CAPPED;
	/** The candidates loaded when {@link #capped}, in the order the search gave them. */
	private int candidatesCap = DEFAULT_CANDIDATES_CAP;
	/** Most time one page may take to be loaded and read. */
	private int perPageDeadlineSeconds = DEFAULT_PER_PAGE_DEADLINE_SECONDS;
	/** Most time the whole loading may take: what has not arrived by then is not read. */
	private int loadingPhaseDeadlineSeconds = DEFAULT_LOADING_PHASE_DEADLINE_SECONDS;
	/** Pages of the same host loaded at the same time. */
	private int perHostConcurrency = DEFAULT_PER_HOST_CONCURRENCY;
	/** Pause between two requests to the same host (0 for none). */
	private long perHostPauseMillis = DEFAULT_PER_HOST_PAUSE_MILLIS;
	/**
	 * Pages of a host that failed (not answering, refused, unreadable) after which its
	 * other pages are not requested.
	 */
	private int perHostFailuresBeforeSkip = DEFAULT_PER_HOST_FAILURES_BEFORE_SKIP;

	/** The candidates loaded at most; none when not {@link #capped}. */
	public int effectiveCandidatesCap() {
		return candidatesCap > 0 ? candidatesCap : DEFAULT_CANDIDATES_CAP;
	}

	public Duration perPageDeadline() {
		return Duration.ofSeconds(perPageDeadlineSeconds > 0 ? perPageDeadlineSeconds : DEFAULT_PER_PAGE_DEADLINE_SECONDS);
	}

	public Duration loadingPhaseDeadline() {
		return Duration.ofSeconds(loadingPhaseDeadlineSeconds > 0 ? loadingPhaseDeadlineSeconds
				: DEFAULT_LOADING_PHASE_DEADLINE_SECONDS);
	}

	public int effectivePerHostConcurrency() {
		return perHostConcurrency > 0 ? perHostConcurrency : DEFAULT_PER_HOST_CONCURRENCY;
	}

	public Duration perHostPause() {
		return Duration.ofMillis(Math.max(0L, perHostPauseMillis));
	}

	public int effectivePerHostFailuresBeforeSkip() {
		return perHostFailuresBeforeSkip > 0 ? perHostFailuresBeforeSkip : DEFAULT_PER_HOST_FAILURES_BEFORE_SKIP;
	}
}
