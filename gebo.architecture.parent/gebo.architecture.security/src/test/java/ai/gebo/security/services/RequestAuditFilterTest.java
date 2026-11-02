package ai.gebo.security.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.boot.security.autoconfigure.web.servlet.SecurityFilterProperties;
import org.springframework.boot.web.servlet.FilterRegistration;
import org.springframework.core.Ordered;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import jakarta.servlet.http.HttpServlet;

/**
 * The request audit context: what the filter puts in the MDC while the request
 * runs, what it returns to the caller, what it leaves behind, and where it is
 * registered.
 */
class RequestAuditFilterTest {

	@AfterEach
	void clearMdc() {
		MDC.clear();
	}

	@Test
	void theCorrelationIdIsTheTraceIdWhenTheRequestHasOne() throws Exception {
		MDC.put(SecurityAuditConstraints.TRACE_ID, "4bf92f3577b34da6a3ce929d0e0e4736");
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/users/doSomething");
		request.addHeader("X-Forwarded-For", "203.0.113.7, 10.0.0.1");
		MockHttpServletResponse response = new MockHttpServletResponse();
		Map<String, String> seen = runCapturingMdc(request, response);

		assertEquals("4bf92f3577b34da6a3ce929d0e0e4736", seen.get(SecurityAuditConstraints.CORRELATION_ID));
		assertEquals("203.0.113.7", seen.get(SecurityAuditConstraints.CLIENT_IP));
		assertEquals("POST", seen.get(SecurityAuditConstraints.HTTP_METHOD));
		assertEquals("/api/users/doSomething", seen.get(SecurityAuditConstraints.REQUEST_URI));
		assertEquals("4bf92f3577b34da6a3ce929d0e0e4736",
				response.getHeader(SecurityAuditConstraints.CORRELATION_ID_HEADER));
	}

	@Test
	void withoutATraceTheCorrelationIdIsAFreshUuid() throws Exception {
		MockHttpServletResponse first = new MockHttpServletResponse();
		MockHttpServletResponse second = new MockHttpServletResponse();
		String firstId = runCapturingMdc(new MockHttpServletRequest("GET", "/a"), first)
				.get(SecurityAuditConstraints.CORRELATION_ID);
		String secondId = runCapturingMdc(new MockHttpServletRequest("GET", "/b"), second)
				.get(SecurityAuditConstraints.CORRELATION_ID);

		assertNotNull(firstId);
		assertEquals(36, firstId.length(), "not a UUID: " + firstId);
		assertNotEquals(firstId, secondId);
		assertEquals(firstId, first.getHeader(SecurityAuditConstraints.CORRELATION_ID_HEADER));
	}

	@Test
	void theRequestContextIsRemovedAfterwardsButTheTraceIsLeftAlone() throws Exception {
		MDC.put(SecurityAuditConstraints.TRACE_ID, "4bf92f3577b34da6a3ce929d0e0e4736");
		runCapturingMdc(new MockHttpServletRequest("GET", "/a"), new MockHttpServletResponse());

		assertNull(MDC.get(SecurityAuditConstraints.CORRELATION_ID));
		assertNull(MDC.get(SecurityAuditConstraints.CLIENT_IP));
		assertNull(MDC.get(SecurityAuditConstraints.HTTP_METHOD));
		assertNull(MDC.get(SecurityAuditConstraints.REQUEST_URI));
		// the trace belongs to the observation filter, which removes it itself
		assertEquals("4bf92f3577b34da6a3ce929d0e0e4736", MDC.get(SecurityAuditConstraints.TRACE_ID));
	}

	@Test
	void itIsRegisteredBeforeTheSecurityChainAndAfterTheObservationFilter() {
		FilterRegistration registration = RequestAuditFilter.class.getAnnotation(FilterRegistration.class);
		assertNotNull(registration);
		assertTrue(registration.enabled());
		assertTrue(registration.order() < SecurityFilterProperties.DEFAULT_FILTER_ORDER,
				"must run before Spring Security's chain, so authentication events have the context");
		// Spring Boot's ServerHttpObservationFilter is registered at HIGHEST_PRECEDENCE + 1
		assertTrue(registration.order() > Ordered.HIGHEST_PRECEDENCE + 1,
				"must run after the observation filter, so the trace id is already in the MDC");
	}

	private Map<String, String> runCapturingMdc(MockHttpServletRequest request, MockHttpServletResponse response)
			throws Exception {
		Map<String, String> seen = new HashMap<>();
		HttpServlet capture = new HttpServlet() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void service(jakarta.servlet.http.HttpServletRequest req,
					jakarta.servlet.http.HttpServletResponse resp) {
				Map<String, String> context = MDC.getCopyOfContextMap();
				if (context != null) {
					seen.putAll(context);
				}
			}
		};
		new RequestAuditFilter().doFilter(request, response, new MockFilterChain(capture));
		return seen;
	}
}
