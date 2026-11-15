package ai.gebo.security.services;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.boot.security.autoconfigure.web.servlet.SecurityFilterProperties;
import org.springframework.boot.web.servlet.FilterRegistration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Puts the request's audit context - correlation id, client address, method and
 * path - in the MDC for {@link IGSecurityAuditLoggerService#newSecurityEvent()}
 * to read, and returns the correlation id to the caller in the
 * {@value SecurityAuditConstraints#CORRELATION_ID_HEADER} response header.
 * <p>
 * It runs once per request as a servlet filter placed just before Spring
 * Security's filter chain, so the events raised while a request is being
 * authenticated (bearer token conversion, OAuth2 login success and failure,
 * resource-server provisioning) carry the same context as those raised by the
 * controllers. It used to sit inside the chain after the anonymous filter,
 * where every authentication event found an empty MDC, and it was registered a
 * second time by Spring Boot as a plain servlet filter, giving each request
 * two correlation ids.
 * </p>
 * <p>
 * The correlation id is the request's trace id whenever tracing has one: Spring
 * Boot's server observation filter runs earlier and has put it in the MDC by
 * the time this filter runs, so one id joins the security log, the application
 * log and the trace. Without tracing a random UUID is used.
 * </p>
 * <p>
 * The user is not put in the MDC here - nobody is authenticated yet at this
 * point; the audit logger reads it from the security context when an event is
 * created.
 * </p>
 */
// @FilterRegistration only customizes registration metadata for a bean that
// already exists - it has no @Component meta-annotation, so without an
// explicit @Component here this class is never picked up by component
// scanning and never registered.
@Component
@FilterRegistration(name = "geboRequestAuditFilter", order = RequestAuditFilter.ORDER)
public class RequestAuditFilter extends OncePerRequestFilter {

	/**
	 * Just before Spring Security's filter chain, and after Spring Boot's server
	 * observation filter (registered at {@code Ordered.HIGHEST_PRECEDENCE + 1}).
	 */
	public static final int ORDER = SecurityFilterProperties.DEFAULT_FILTER_ORDER - 1;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String traceId = MDC.get(SecurityAuditConstraints.TRACE_ID);
		String correlationId = StringUtils.hasText(traceId) ? traceId : UUID.randomUUID().toString();
		try {
			MDC.put(SecurityAuditConstraints.CORRELATION_ID, correlationId);
			MDC.put(SecurityAuditConstraints.CLIENT_IP, extractClientIp(request));
			MDC.put(SecurityAuditConstraints.HTTP_METHOD, request.getMethod());
			MDC.put(SecurityAuditConstraints.REQUEST_URI, request.getRequestURI());
			response.setHeader(SecurityAuditConstraints.CORRELATION_ID_HEADER, correlationId);
			chain.doFilter(request, response);
		} finally {
			MDC.remove(SecurityAuditConstraints.CORRELATION_ID);
			MDC.remove(SecurityAuditConstraints.CLIENT_IP);
			MDC.remove(SecurityAuditConstraints.HTTP_METHOD);
			MDC.remove(SecurityAuditConstraints.REQUEST_URI);
		}
	}

	private String extractClientIp(HttpServletRequest req) {
		String forwarded = req.getHeader("X-Forwarded-For");
		if (forwarded != null && !forwarded.isBlank()) {
			return forwarded.split(",")[0].trim();
		}
		return req.getRemoteAddr();
	}
}
