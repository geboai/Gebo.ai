/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.search.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import ai.gebo.architecture.search.config.SearchCallsConfig;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SystemSearchOutcome;
import ai.gebo.architecture.search.model.SystemSearchOutcome.Unavailability;
import ai.gebo.restintegration.abstraction.layer.GeboInvalidAccessException;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;

/**
 * Pins the best effort calls of the search services: as the calling user, bounded
 * by the timeout, never throwing, not tried again by default, tried again when
 * configured only for a failure that can pass, every failure told by its reason.
 */
class BestEffortSearchCallsTest {

	private BestEffortSearchCalls calls;

	private BestEffortSearchCalls calls(int retries, int timeoutSeconds) {
		SearchCallsConfig config = new SearchCallsConfig();
		config.setRetries(retries);
		config.setTimeoutSeconds(timeoutSeconds);
		config.setRetryPauseMillis(0L);
		calls = new BestEffortSearchCalls(config);
		return calls;
	}

	@AfterEach
	void tearDown() {
		if (calls != null) {
			calls.shutdown();
		}
		SecurityContextHolder.clearContext();
	}

	private static SearchResult result(String code) {
		SearchResult result = new SearchResult();
		result.setId(code);
		return result;
	}

	@Test
	void theResultsOfASystemAreGivenBack() {
		SystemSearchOutcome outcome = calls(0, 60).search("web", "The web", "searchWeb",
				() -> List.of(result("r1"), result("r2")));

		assertTrue(outcome.available());
		assertEquals(2, outcome.results().size());
		assertNull(outcome.unavailableNotice());
	}

	@Test
	void aSystemNotAnsweringWithinTheTimeoutIsReportedAsNotResponding() {
		CountDownLatch never = new CountDownLatch(1);

		SystemSearchOutcome outcome = calls(0, 1).search("web", "The web", "searchWeb", () -> {
			never.await(30, TimeUnit.SECONDS);
			return List.of(result("late"));
		});
		never.countDown();

		assertFalse(outcome.available());
		assertEquals(Unavailability.NOT_RESPONDING, outcome.unavailability());
		assertEquals("The web: not responding (no answer within 1 s)", outcome.unavailableNotice());
	}

	@Test
	void aFailedSearchIsNotTriedAgainByDefault() {
		AtomicInteger attempts = new AtomicInteger();

		SystemSearchOutcome outcome = calls(0, 60).search("web", "The web", "searchWeb", () -> {
			attempts.incrementAndGet();
			throw new SearchServiceException("down", new IOException("connection reset"));
		});

		assertEquals(1, attempts.get());
		assertEquals(Unavailability.OUT_OF_SERVICE, outcome.unavailability());
		assertEquals("The web: out of service", outcome.unavailableNotice());
	}

	@Test
	void whenConfiguredOnlyAFailureThatCanPassIsTriedAgain() {
		AtomicInteger attempts = new AtomicInteger();
		SystemSearchOutcome recovered = calls(1, 60).search("web", "The web", "searchWeb", () -> {
			if (attempts.incrementAndGet() == 1) {
				throw new SearchServiceException("down",
						new GeboRestIntegrationException("x", new HttpServerErrorException(HttpStatus.BAD_GATEWAY)));
			}
			return List.of(result("r1"));
		});
		assertTrue(recovered.available());
		assertEquals(2, attempts.get());

		AtomicInteger quota = new AtomicInteger();
		SystemSearchOutcome refused = calls.search("web", "The web", "searchWeb", () -> {
			quota.incrementAndGet();
			throw new SearchServiceException("quota", new GeboRestIntegrationException("429 TOO_MANY_REQUESTS"));
		});
		assertEquals(1, quota.get());
		assertEquals(Unavailability.FAILED, refused.unavailability());
	}

	@Test
	void theSearchRunsAsTheCallingUser() {
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("alice", "n/a", List.of()));
		AtomicReference<String> user = new AtomicReference<>();

		calls(0, 60).search("web", "The web", "searchWeb", () -> {
			user.set(SecurityContextHolder.getContext().getAuthentication().getName());
			return List.of();
		});

		assertEquals("alice", user.get());
	}

	@Test
	void everyFailureIsToldByItsReason() {
		assertEquals(Unavailability.NOT_RESPONDING, BestEffortSearchCalls.unavailabilityOf(new SearchServiceException(
				"x", new GeboRestIntegrationException("x", new ResourceAccessException("t", new SocketTimeoutException())))));
		assertEquals(Unavailability.OUT_OF_SERVICE,
				BestEffortSearchCalls.unavailabilityOf(new ResourceAccessException("x", new ConnectException("refused"))));
		assertEquals(Unavailability.OUT_OF_SERVICE,
				BestEffortSearchCalls.unavailabilityOf(new GeboRestIntegrationException("503 SERVICE_UNAVAILABLE")));
		assertEquals(Unavailability.ACCESS_REFUSED, BestEffortSearchCalls
				.unavailabilityOf(new SearchServiceException("x", new GeboInvalidAccessException("401 UNAUTHORIZED"))));
		assertEquals(Unavailability.ACCESS_REFUSED,
				BestEffortSearchCalls.unavailabilityOf(new HttpClientErrorException(HttpStatus.FORBIDDEN)));
		assertEquals(Unavailability.FAILED,
				BestEffortSearchCalls.unavailabilityOf(new HttpClientErrorException(HttpStatus.PAYMENT_REQUIRED)));
		assertEquals(Unavailability.FAILED,
				BestEffortSearchCalls.unavailabilityOf(new SearchServiceException("Brave credentials of the wrong format")));
		assertEquals(Unavailability.FAILED, BestEffortSearchCalls.unavailabilityOf(new IllegalStateException("?")));
	}
}
