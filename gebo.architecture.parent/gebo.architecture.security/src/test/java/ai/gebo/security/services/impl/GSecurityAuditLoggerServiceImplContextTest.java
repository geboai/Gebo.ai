package ai.gebo.security.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import ai.gebo.architecture.environment.ArchitectureType;
import ai.gebo.architecture.environment.GeboApplicationArchitecture;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditConstraints;

/**
 * Where a new security event takes its context from: the request context in the
 * MDC, the trace and span in the MDC, and the user from the security context
 * when no caller put one in the MDC.
 */
class GSecurityAuditLoggerServiceImplContextTest {

	private final GSecurityAuditLoggerServiceImpl service = new GSecurityAuditLoggerServiceImpl(
			new GeboApplicationArchitecture(ArchitectureType.MONOLITHIC));

	@AfterEach
	void clear() {
		MDC.clear();
		SecurityContextHolder.clearContext();
	}

	@Test
	void anEventCarriesTheTraceAndSpanItHappenedIn() {
		MDC.put(SecurityAuditConstraints.TRACE_ID, "4bf92f3577b34da6a3ce929d0e0e4736");
		MDC.put(SecurityAuditConstraints.SPAN_ID, "00f067aa0ba902b7");
		MDC.put(SecurityAuditConstraints.CORRELATION_ID, "4bf92f3577b34da6a3ce929d0e0e4736");

		SecurityEvent event = service.newSecurityEvent();

		assertEquals("4bf92f3577b34da6a3ce929d0e0e4736", event.getTraceId());
		assertEquals("00f067aa0ba902b7", event.getSpanId());
		assertEquals("4bf92f3577b34da6a3ce929d0e0e4736", event.getCorrelationId());
	}

	@Test
	void offARequestTheCorrelationIdIsTheTraceId() {
		// a job or a message delivery: a trace, but no request filter ran
		MDC.put(SecurityAuditConstraints.TRACE_ID, "4bf92f3577b34da6a3ce929d0e0e4736");

		assertEquals("4bf92f3577b34da6a3ce929d0e0e4736", service.newSecurityEvent().getCorrelationId());
	}

	@Test
	void theUserComesFromTheSecurityContextWhenTheMdcHasNone() {
		SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken.authenticated(
				"alice@example.com", null, List.of(new SimpleGrantedAuthority("ROLE_USER"))));

		assertEquals("alice@example.com", service.newSecurityEvent().getUserId());
	}

	@Test
	void anAnonymousCallerHasNoUser() {
		SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken("key",
				"anonymousUser", List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))));

		assertNull(service.newSecurityEvent().getUserId());
	}

	@Test
	void aUserPutInTheMdcByTheCallerWins() {
		SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken.authenticated(
				"alice@example.com", null, List.of(new SimpleGrantedAuthority("ROLE_USER"))));
		MDC.put(SecurityAuditConstraints.USERID, "bob@example.com");

		assertEquals("bob@example.com", service.newSecurityEvent().getUserId());
	}

	@Test
	void withoutAnyContextTheEventHasNoCorrelation() {
		SecurityEvent event = service.newSecurityEvent();
		assertNull(event.getCorrelationId());
		assertNull(event.getTraceId());
		assertNull(event.getUserId());
	}
}
