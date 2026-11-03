/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.multithreading;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import reactor.core.scheduler.Schedulers;

/**
 * A task run by the thread manager takes the submitting thread's trace and MDC
 * with it, and nothing else - in particular not its security context.
 */
class GeboThreadManagerContextTest {

	private GeboThreadManagerImpl manager;

	@BeforeEach
	void createManager() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setThreadNamePrefix("test-tpool-");
		executor.initialize();
		manager = new GeboThreadManagerImpl(executor, Schedulers.immediate(), Executors.newSingleThreadExecutor(),
				Schedulers.immediate());
	}

	@AfterEach
	void clear() {
		MDC.clear();
		SecurityContextHolder.clearContext();
	}

	@Test
	void aTaskRunsWithTheSubmittersObservationAndMdcButNotItsSecurityContext() throws Exception {
		ObservationRegistry registry = ObservationRegistry.create();
		registry.observationConfig().observationHandler(context -> true);
		Observation request = Observation.start("http.server.requests", registry);

		AtomicReference<String> correlationId = new AtomicReference<>();
		AtomicReference<Observation> observation = new AtomicReference<>();
		AtomicReference<Object> authentication = new AtomicReference<>("not read");
		AtomicReference<String> thread = new AtomicReference<>();
		CountDownLatch done = new CountDownLatch(1);
		IGRunnable task = new TestTask(() -> {
			thread.set(Thread.currentThread().getName());
			correlationId.set(MDC.get("correlationId"));
			observation.set(registry.getCurrentObservation());
			authentication.set(SecurityContextHolder.getContext().getAuthentication());
			done.countDown();
		});

		try (Observation.Scope scope = request.openScope()) {
			MDC.put("correlationId", "4bf92f3577b34da6a3ce929d0e0e4736");
			SecurityContextHolder.getContext()
					.setAuthentication(UsernamePasswordAuthenticationToken.authenticated("alice", null, null));
			manager.run(task);
		} finally {
			request.stop();
		}
		assertTrue(done.await(10, TimeUnit.SECONDS), "the task never ran");

		assertTrue(thread.get().startsWith("test-tpool-"), "ran on " + thread.get());
		assertEquals("4bf92f3577b34da6a3ce929d0e0e4736", correlationId.get());
		assertSame(request, observation.get());
		assertNull(authentication.get(), "the security context must not follow the task");
	}

	@Test
	void aTaskSubmittedWithoutContextRunsWithout() throws Exception {
		AtomicReference<String> correlationId = new AtomicReference<>("not read");
		CountDownLatch done = new CountDownLatch(1);
		manager.run(new TestTask(() -> {
			correlationId.set(MDC.get("correlationId"));
			done.countDown();
		}));
		assertTrue(done.await(10, TimeUnit.SECONDS), "the task never ran");
		assertNull(correlationId.get());
	}

	private static class TestTask implements IGRunnable {
		private final Runnable body;

		TestTask(Runnable body) {
			this.body = body;
		}

		@Override
		public void run() {
			body.run();
		}

		@Override
		public void startShutdown() {
		}

		@Override
		public boolean isRunning() {
			return false;
		}

		@Override
		public void shutdown() {
		}
	}
}
