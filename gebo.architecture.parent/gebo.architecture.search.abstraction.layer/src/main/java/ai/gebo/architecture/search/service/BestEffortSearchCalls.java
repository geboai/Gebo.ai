/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.search.service;

import java.io.IOException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import ai.gebo.architecture.search.config.SearchCallsConfig;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.model.SystemSearchOutcome;
import ai.gebo.architecture.search.model.SystemSearchOutcome.Unavailability;
import ai.gebo.restintegration.abstraction.layer.GeboInvalidAccessException;
import ai.gebo.restintegration.abstraction.layer.GeboRemoteBackendErrorException;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.security.services.ReactiveIdentityUtil;
import jakarta.annotation.PreDestroy;

/**
 * Calls a search service on one system best effort, for every caller of the search
 * services (the agents' tools and search agents, the deep search, the search
 * controllers): the search runs as the calling user, waits at most the configured
 * timeout (coherent with a sloppy network), is tried again only when configured and
 * only for a failure that can pass, and never throws: what it gives is the results,
 * or why the system could not be searched, for the caller to tell the model or the
 * user.
 *
 * <p>
 * A search that does not answer within the timeout is left running on its own thread
 * (a blocking call cannot be interrupted): the web search providers' HTTP clients have
 * their own timeouts (see {@link SearchCallsConfig}), so their threads end.
 * </p>
 */
@Service
public class BestEffortSearchCalls {
	private static final Logger LOGGER = LoggerFactory.getLogger(BestEffortSearchCalls.class);
	/** Most causes looked through to tell why a search failed. */
	static final int MAX_CAUSES = 10;
	/** The HTTP status a REST integration error without cause starts its message with. */
	private static final Pattern LEADING_STATUS = Pattern.compile("^\\s*(\\d{3})\\b");

	private final SearchCallsConfig config;
	private final ExecutorService executor;

	public BestEffortSearchCalls(SearchCallsConfig config) {
		this.config = config;
		final AtomicInteger threads = new AtomicInteger();
		final ThreadFactory factory = runnable -> {
			final Thread thread = new Thread(runnable, "gebo-search-call-" + threads.incrementAndGet());
			thread.setDaemon(true);
			return thread;
		};
		this.executor = Executors.newCachedThreadPool(factory);
	}

	@PreDestroy
	void shutdown() {
		executor.shutdownNow();
	}

	/**
	 * Runs the search on the system as the current user, best effort.
	 *
	 * @param system   the system searched
	 * @param callerId who searches, for the logs
	 * @param search   the search on the system
	 */
	public SystemSearchOutcome search(SearchableSystemMetaData<?, ?> system, String callerId,
			Callable<List<SearchResult>> search) {
		final String code = system != null ? system.getCode() : null;
		final String name = system != null && system.getDescription() != null && !system.getDescription().isBlank()
				? system.getDescription()
				: code;
		return search(code, name, callerId, search);
	}

	/** Runs the search, the system given by its code and name. */
	public SystemSearchOutcome search(String systemCode, String systemName, String callerId,
			Callable<List<SearchResult>> search) {
		final Duration timeout = config.timeout();
		final int attempts = 1 + config.effectiveRetries();
		final ReactiveIdentityUtil asTheUser = ReactiveIdentityUtil.create();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin search(...) caller:" + callerId + " system:" + systemCode + " timeout:"
					+ timeout.toSeconds() + " s attempts:" + attempts);
		}
		SystemSearchOutcome outcome = null;
		for (int attempt = 1; attempt <= attempts; attempt++) {
			outcome = attempt(systemCode, systemName, callerId, asTheUser.wrap(search), timeout);
			if (outcome.available() || !canPass(outcome.unavailability()) || attempt == attempts) {
				break;
			}
			LOGGER.warn("Caller:" + callerId + " search on system:" + systemCode + " " + outcome.unavailability().said()
					+ " (attempt " + attempt + " of " + attempts + "), trying it again");
			if (!pause()) {
				break;
			}
		}
		if (outcome.available()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End search(...) caller:" + callerId + " system:" + systemCode + " "
						+ outcome.results().size() + " result(s)");
			}
		} else {
			LOGGER.warn("Caller:" + callerId + " could not search system:" + systemCode + ": "
					+ outcome.unavailability().said() + " - " + outcome.detail());
		}
		return outcome;
	}

	private SystemSearchOutcome attempt(String systemCode, String systemName, String callerId,
			Callable<List<SearchResult>> search, Duration timeout) {
		final Future<List<SearchResult>> running;
		try {
			running = executor.submit(search);
		} catch (RuntimeException e) {
			return SystemSearchOutcome.unavailable(systemCode, systemName, Unavailability.FAILED,
					"the search could not be started: " + e);
		}
		try {
			return SystemSearchOutcome.found(systemCode, systemName,
					running.get(timeout.toMillis(), TimeUnit.MILLISECONDS));
		} catch (TimeoutException e) {
			running.cancel(true);
			return SystemSearchOutcome.unavailable(systemCode, systemName, Unavailability.NOT_RESPONDING,
					"no answer within " + timeout.toSeconds() + " s");
		} catch (ExecutionException e) {
			final Throwable failure = e.getCause() != null ? e.getCause() : e;
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Caller:" + callerId + " search on system:" + systemCode + " failed", failure);
			}
			return SystemSearchOutcome.unavailable(systemCode, systemName, unavailabilityOf(failure),
					String.valueOf(failure));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			running.cancel(true);
			return SystemSearchOutcome.unavailable(systemCode, systemName, Unavailability.FAILED, "interrupted");
		}
	}

	private boolean pause() {
		try {
			Thread.sleep(Math.max(0L, config.getRetryPauseMillis()));
			return true;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return false;
		}
	}

	/** Whether a search failing so can succeed when tried again: not responding, out of service. */
	public static boolean canPass(Unavailability unavailability) {
		return unavailability == Unavailability.NOT_RESPONDING || unavailability == Unavailability.OUT_OF_SERVICE;
	}

	/**
	 * Why a search failed, from its causes as the search providers report them (their
	 * REST integration errors wrapped in a search service error): not responding (a
	 * timeout), out of service (not reachable, a server error), access refused (401,
	 * 403), failed otherwise (quota or rate limit exceeded, a bad request, a failure
	 * that cannot be told).
	 */
	public static Unavailability unavailabilityOf(Throwable failure) {
		Throwable cause = failure;
		for (int depth = 0; cause != null && depth < MAX_CAUSES; depth++) {
			if (cause instanceof RestClientResponseException response) {
				return ofStatus(response.getStatusCode().value());
			}
			if (cause instanceof GeboInvalidAccessException) {
				return Unavailability.ACCESS_REFUSED;
			}
			if (cause instanceof GeboRemoteBackendErrorException) {
				return Unavailability.OUT_OF_SERVICE;
			}
			if (cause instanceof SocketTimeoutException || cause instanceof HttpTimeoutException) {
				return Unavailability.NOT_RESPONDING;
			}
			if (cause instanceof ConnectException || cause instanceof UnknownHostException
					|| cause instanceof NoRouteToHostException) {
				return Unavailability.OUT_OF_SERVICE;
			}
			if (cause instanceof GeboRestIntegrationException && cause.getCause() == null) {
				// a provider's error answer, its status leading the message
				final Integer status = leadingStatus(cause.getMessage());
				return status != null ? ofStatus(status) : Unavailability.FAILED;
			}
			if (cause.getCause() == null || cause.getCause() == cause) {
				// the innermost cause: an I/O failure not told apart above
				return cause instanceof IOException || cause instanceof ResourceAccessException
						? Unavailability.OUT_OF_SERVICE
						: Unavailability.FAILED;
			}
			cause = cause.getCause();
		}
		return Unavailability.FAILED;
	}

	private static Unavailability ofStatus(int status) {
		if (status >= 500) {
			return Unavailability.OUT_OF_SERVICE;
		}
		if (status == 401 || status == 403) {
			return Unavailability.ACCESS_REFUSED;
		}
		return Unavailability.FAILED;
	}

	private static Integer leadingStatus(String message) {
		if (message == null) {
			return null;
		}
		final Matcher matcher = LEADING_STATUS.matcher(message);
		return matcher.find() ? Integer.valueOf(matcher.group(1)) : null;
	}
}
