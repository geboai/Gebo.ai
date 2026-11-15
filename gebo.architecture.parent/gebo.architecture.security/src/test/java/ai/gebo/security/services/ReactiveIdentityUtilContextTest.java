package ai.gebo.security.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Wherever ReactiveIdentityUtil carries the identity onto another thread it
 * carries the request's MDC (correlation id, trace) too, with the user it runs
 * as, and gives the thread its own back afterwards.
 */
class ReactiveIdentityUtilContextTest {

	@AfterEach
	void clear() {
		MDC.clear();
		SecurityContextHolder.clearContext();
	}

	private static ReactiveIdentityUtil createOnARequestThread() {
		SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken
				.authenticated("alice@example.com", null, List.of(new SimpleGrantedAuthority("ROLE_USER"))));
		MDC.put(SecurityAuditConstraints.CORRELATION_ID, "4bf92f3577b34da6a3ce929d0e0e4736");
		MDC.put(SecurityAuditConstraints.TRACE_ID, "4bf92f3577b34da6a3ce929d0e0e4736");
		MDC.put(SecurityAuditConstraints.CLIENT_IP, "203.0.113.7");
		try {
			return ReactiveIdentityUtil.create();
		} finally {
			MDC.clear();
			SecurityContextHolder.clearContext();
		}
	}

	@Test
	void workRunAsTheUserOnAnotherThreadHasTheRequestsMdcAndTheUser() throws Exception {
		ReactiveIdentityUtil identity = createOnARequestThread();
		AtomicReference<String> correlationId = new AtomicReference<>();
		AtomicReference<String> clientIp = new AtomicReference<>();
		AtomicReference<String> user = new AtomicReference<>();
		AtomicReference<String> leftOver = new AtomicReference<>("not read");
		Thread worker = new Thread(() -> {
			MDC.put("workerOwn", "kept");
			identity.doAs(() -> {
				correlationId.set(MDC.get(SecurityAuditConstraints.CORRELATION_ID));
				clientIp.set(MDC.get(SecurityAuditConstraints.CLIENT_IP));
				user.set(MDC.get(SecurityAuditConstraints.USERID));
			});
			leftOver.set(MDC.get(SecurityAuditConstraints.CORRELATION_ID) + "|" + MDC.get(SecurityAuditConstraints.USERID)
					+ "|" + MDC.get("workerOwn"));
		});
		worker.start();
		worker.join();

		assertEquals("4bf92f3577b34da6a3ce929d0e0e4736", correlationId.get());
		assertEquals("203.0.113.7", clientIp.get());
		assertEquals("alice@example.com", user.get());
		assertEquals("null|null|kept", leftOver.get(), "the worker's own MDC must come back");
	}

	@Test
	void signalsDeliveredOnAWrappedSchedulerHaveTheRequestsMdc() throws Exception {
		ReactiveIdentityUtil identity = createOnARequestThread();
		AtomicReference<String> seenAtTheEnd = new AtomicReference<>();
		CountDownLatch ended = new CountDownLatch(1);
		Flux.just(1, 2, 3).publishOn(identity.wrap(Schedulers.single())).doFinally(signal -> {
			seenAtTheEnd.set(MDC.get(SecurityAuditConstraints.CORRELATION_ID));
			ended.countDown();
		}).blockLast();
		assertTrue(ended.await(10, TimeUnit.SECONDS));
		assertEquals("4bf92f3577b34da6a3ce929d0e0e4736", seenAtTheEnd.get());
		// and the scheduler's thread is given back clean
		assertNull(Flux.just(1).publishOn(Schedulers.single())
				.map(i -> String.valueOf(MDC.get(SecurityAuditConstraints.CORRELATION_ID))).filter(v -> !"null".equals(v))
				.blockFirst());
	}
}
