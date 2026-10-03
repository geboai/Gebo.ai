/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Runs a search on a system best effort: a failed attempt (e.g. a web search
 * provider that cannot be reached for a moment) is tried again once after a short
 * pause; a search still failing is left to the caller, which goes on with the other
 * systems.
 */
final class SearchAttempts {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchAttempts.class);
	/** Attempts of a search on a system. */
	static final int ATTEMPTS = 2;
	/** Pause before trying a failed search again. */
	static long retryPauseMillis = 1000L;

	/** A search on a system. */
	@FunctionalInterface
	interface Attempt<T> {
		T run() throws Exception;
	}

	private SearchAttempts() {
	}

	/** Runs the search, trying it again once when it fails. */
	static <T> T run(Attempt<T> attempt, String toolName, String systemCode) throws Exception {
		Exception last = null;
		for (int i = 1; i <= ATTEMPTS; i++) {
			try {
				return attempt.run();
			} catch (Exception e) {
				last = e;
				if (i < ATTEMPTS) {
					LOGGER.warn("Tool:" + toolName + " search on system:" + systemCode + " failed (attempt " + i + " of "
							+ ATTEMPTS + "), trying it again: " + e);
					try {
						Thread.sleep(retryPauseMillis);
					} catch (InterruptedException ie) {
						Thread.currentThread().interrupt();
						break;
					}
				}
			}
		}
		throw last;
	}
}
