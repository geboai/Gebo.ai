/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.services.config.GeboDefaultLlmsServiceClientsProviderConfig;

/**
 * retry-config.max-attempts counts every call, the first one included, as the OkHttp
 * retry interceptor counts them and as the former Spring Retry template did; the core
 * retry policy counts only the retries after the first call, so the template it builds
 * must not add one more.
 */
class CoreRetryTemplateAttemptsTest {

	static GDefaultLlmsServiceClientsProviderImpl provider(int maxAttempts) {
		GeboDefaultLlmsServiceClientsProviderConfig config = new GeboDefaultLlmsServiceClientsProviderConfig();
		config.getRetryConfig().setMaxAttempts(maxAttempts);
		config.getRetryConfig().setBackoffInterval(1);
		GDefaultLlmsServiceClientsProviderImpl provider = new GDefaultLlmsServiceClientsProviderImpl();
		provider.defaultConfig = config;
		return provider;
	}

	static int callsUntilGivenUp(int maxAttempts) {
		AtomicInteger calls = new AtomicInteger();
		IllegalStateException failure = assertThrows(IllegalStateException.class,
				() -> provider(maxAttempts).getCoreRetryTemplate().invoke(() -> {
					calls.incrementAndGet();
					throw new IllegalStateException("provider down");
				}));
		assertEquals("provider down", failure.getMessage(), "the last failure, as it was");
		return calls.get();
	}

	@Test
	void maxAttemptsIsTheNumberOfCalls() {
		assertEquals(5, callsUntilGivenUp(5));
		assertEquals(3, callsUntilGivenUp(3));
	}

	@Test
	void oneAttemptIsNoRetry() {
		assertEquals(1, callsUntilGivenUp(1));
	}

	@Test
	void zeroIsStillOneCall() {
		assertEquals(1, callsUntilGivenUp(0));
	}
}
