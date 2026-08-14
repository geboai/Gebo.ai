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
