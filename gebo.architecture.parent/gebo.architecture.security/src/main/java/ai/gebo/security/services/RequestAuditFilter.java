package ai.gebo.security.services;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.boot.web.servlet.FilterRegistration;
import org.springframework.stereotype.Component;

// @FilterRegistration only customizes registration metadata for a bean that
// already exists - it has no @Component meta-annotation, so without an
// explicit @Component here this class is never picked up by component
// scanning and GeboAISecurityConfig.securityFilterChain(..., RequestAuditFilter)
// fails to autowire (verified: app context refused to start with
// "No qualifying bean of type 'ai.gebo.security.services.RequestAuditFilter'").
@Component
@FilterRegistration
public class RequestAuditFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest http = (HttpServletRequest) request;

		String correlationId = UUID.randomUUID().toString();

		try {

			MDC.put(SecurityAuditConstraints.CORRELATION_ID, correlationId);

			MDC.put(SecurityAuditConstraints.CLIENT_IP, extractClientIp(http));

			MDC.put(SecurityAuditConstraints.HTTP_METHOD, http.getMethod());
			MDC.put(SecurityAuditConstraints.REQUEST_URI, http.getRequestURI());

			if (http.getUserPrincipal() != null) {
				MDC.put(SecurityAuditConstraints.USERID, http.getUserPrincipal().getName());
			}

			chain.doFilter(request, response);

		} finally {

			MDC.remove(SecurityAuditConstraints.CORRELATION_ID);
			MDC.remove(SecurityAuditConstraints.CLIENT_IP);
			MDC.remove(SecurityAuditConstraints.HTTP_METHOD);
			MDC.remove(SecurityAuditConstraints.REQUEST_URI);
			MDC.remove(SecurityAuditConstraints.USERID);
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
