/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import ai.gebo.restintegration.abstraction.layer.GeboRemoteBackendErrorException;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;

/**
 * Runs a search on a system best effort: an attempt failing for a reason that can
 * pass (the provider cannot be reached for a moment, it answers with a server error)
 * is tried again once after a short pause; any other failure (refused credentials,
 * quota or rate limit exceeded, a bad request, a failure it cannot tell) is not,
 * since the same call would fail again and a paid provider would bill it twice. A
 * search still failing is left to the caller, which goes on with the other systems.
 */
final class SearchAttempts {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchAttempts.class);
	/** Attempts of a search on a system. */
	static final int ATTEMPTS = 2;
	/** Pause before trying a failed search again. */
	static long retryPauseMillis = 1000L;
	/** Most causes looked through to tell why a search failed. */
	static final int MAX_CAUSES = 10;
	/** The HTTP status a REST integration error without cause starts its message with. */
	private static final Pattern LEADING_STATUS = Pattern.compile("^\\s*(\\d{3})\\b");

	/** A search on a system. */
	@FunctionalInterface
	interface Attempt<T> {
		T run() throws Exception;
	}

	private SearchAttempts() {
	}

	/** Runs the search, trying it again once when it fails for a reason that can pass. */
	static <T> T run(Attempt<T> attempt, String toolName, String systemCode) throws Exception {
		Exception last = null;
		for (int i = 1; i <= ATTEMPTS; i++) {
			try {
				return attempt.run();
			} catch (Exception e) {
				last = e;
				if (!transientFailure(e)) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Tool:" + toolName + " search on system:" + systemCode + " failed (attempt " + i
								+ " of " + ATTEMPTS + ") for a reason a new attempt would not change, not tried again: "
								+ e);
					}
					break;
				}
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

	/**
	 * Whether a search failed for a reason that can pass: the provider could not be
	 * reached (a network failure, a timeout) or answered with a server error (5xx),
	 * anywhere in the causes. A client error answer (4xx: refused credentials, quota or
	 * rate limit exceeded, a bad request), and any failure that cannot be told, is not.
	 */
	static boolean transientFailure(Throwable failure) {
		Throwable cause = failure;
		for (int depth = 0; cause != null && depth < MAX_CAUSES; depth++) {
			if (cause instanceof RestClientResponseException response) {
				return response.getStatusCode().is5xxServerError();
			}
			if (cause instanceof GeboRemoteBackendErrorException || cause instanceof ResourceAccessException
					|| cause instanceof IOException) {
				return true;
			}
			if (cause instanceof GeboRestIntegrationException && cause.getCause() == null) {
				// a provider's error answer, its status leading the message (401-403 and 404
				// have their own exceptions, neither transient)
				final Integer status = leadingStatus(cause.getMessage());
				return status != null && status >= 500;
			}
			if (cause.getCause() == cause) {
				break;
			}
			cause = cause.getCause();
		}
		return false;
	}

	private static Integer leadingStatus(String message) {
		if (message == null) {
			return null;
		}
		final Matcher matcher = LEADING_STATUS.matcher(message);
		return matcher.find() ? Integer.valueOf(matcher.group(1)) : null;
	}
}
