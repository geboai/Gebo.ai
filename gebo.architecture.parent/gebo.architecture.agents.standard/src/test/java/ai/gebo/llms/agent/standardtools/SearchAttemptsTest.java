/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.restintegration.abstraction.layer.GeboInvalidAccessException;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;

/**
 * Pins the best effort search of a system: an attempt failing for a reason that can
 * pass is tried again once, any other failure is not.
 */
class SearchAttemptsTest {

	@BeforeEach
	void noPause() {
		SearchAttempts.retryPauseMillis = 0L;
	}

	@Test
	void aFailedSearchIsTriedAgainOnce() throws Exception {
		AtomicInteger calls = new AtomicInteger();

		String result = SearchAttempts.run(() -> {
			if (calls.incrementAndGet() == 1) {
				throw new IOException("connection reset");
			}
			return "found";
		}, "searchWeb", "web");

		assertEquals("found", result);
		assertEquals(2, calls.get());
	}

	@Test
	void aSearchStillFailingIsLeftToTheCaller() {
		AtomicInteger calls = new AtomicInteger();

		IOException failure = assertThrows(IOException.class, () -> SearchAttempts.run(() -> {
			calls.incrementAndGet();
			throw new IOException("unreachable");
		}, "searchWeb", "web"));

		assertEquals("unreachable", failure.getMessage());
		assertEquals(SearchAttempts.ATTEMPTS, calls.get());
	}

	/** How many times a search failing always with the given failure is run. */
	private static int attemptsFailingWith(Exception failure) {
		AtomicInteger calls = new AtomicInteger();
		assertThrows(Exception.class, () -> SearchAttempts.run(() -> {
			calls.incrementAndGet();
			throw failure;
		}, "searchWeb", "web"));
		return calls.get();
	}

	@Test
	void aFailureThatCanPassIsTriedAgain() {
		// a server error, wrapped as the web searchers wrap it
		assertEquals(2, attemptsFailingWith(new SearchServiceException("Error accessing brave searches",
				new GeboRestIntegrationException("Error working with url:x",
						new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE)))));
		assertEquals(2, attemptsFailingWith(new SearchServiceException("down",
				new GeboRestIntegrationException("Error working with url:x", new ResourceAccessException("timeout")))));
		assertEquals(2, attemptsFailingWith(new GeboRestIntegrationException("503 SERVICE_UNAVAILABLE")));
	}

	@Test
	void aFailureTheSameCallWouldRepeatIsNotTriedAgain() {
		// quota and rate limit: the REST wrapper reports their status in the message
		assertEquals(1, attemptsFailingWith(new SearchServiceException("Error accessing brave searches",
				new GeboRestIntegrationException("429 TOO_MANY_REQUESTS"))));
		assertEquals(1, attemptsFailingWith(new SearchServiceException("Error accessing brave searches",
				new GeboRestIntegrationException("Exception accessing => x",
						new HttpClientErrorException(HttpStatus.PAYMENT_REQUIRED)))));
		// refused credentials
		assertEquals(1, attemptsFailingWith(new SearchServiceException("Error accessing brave searches",
				new GeboInvalidAccessException("401 UNAUTHORIZED"))));
		// a configuration error, or anything that cannot be told
		assertEquals(1, attemptsFailingWith(new SearchServiceException("Brave credentials of the wrong format")));
		assertEquals(1, attemptsFailingWith(new IllegalStateException("unexpected")));
	}
}
