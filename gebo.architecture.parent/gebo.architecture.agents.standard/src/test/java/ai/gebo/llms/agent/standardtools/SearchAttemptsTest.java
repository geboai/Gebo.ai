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

/** Pins the best effort search of a system: a failed attempt is tried again once. */
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
}
