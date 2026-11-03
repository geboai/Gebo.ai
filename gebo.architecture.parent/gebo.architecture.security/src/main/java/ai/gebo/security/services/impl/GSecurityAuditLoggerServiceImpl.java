package ai.gebo.security.services.impl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.environment.GeboApplicationArchitecture;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.SecurityAuditConstraints;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import tools.jackson.databind.ObjectMapper;

@Service
public class GSecurityAuditLoggerServiceImpl implements IGSecurityAuditLoggerService{
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityAuditConstraints.SECURITY_LOG);
	private final GeboApplicationArchitecture applicationArchitecture;
	@Value("${spring.application.name:unknown}")
	private String applicationName;

	public GSecurityAuditLoggerServiceImpl(GeboApplicationArchitecture applicationArchitecture) {
		this.applicationArchitecture = applicationArchitecture;
	}


	@Override
	public void log(SecurityEvent event) {
		try {
			LOGGER.info(mapper.writeValueAsString(event));
		} catch (Exception e) {
			LOGGER.error("Cannot serialize security event", e);
		}
	}

	private static String getCallers() {
		List<String> stack = StackWalker.getInstance().walk(stream -> stream.skip(2).limit(4)
				.map(frame -> frame.getClassName() + "#" + frame.getMethodName()).toList());
		return stack.stream().collect(Collectors.joining(" <- "));
	}

	/**
	 * Builds an event from the context of the current thread: the request
	 * context {@code RequestAuditFilter} put in the MDC, the trace and span
	 * Spring Boot's tracing put there, and the authenticated user.
	 * <p>
	 * The correlation id falls back to the trace id when no request set one, so
	 * an event raised off a request (a job, a message delivery) still joins the
	 * logs and the trace it ran in. The user is the MDC's when a caller put one
	 * there, otherwise the security context's (anonymous excluded), read now
	 * rather than when the request entered, since the request filter runs before
	 * authentication.
	 * </p>
	 */
	@Override
	public SecurityEvent newSecurityEvent() {
		String traceId = MDC.get(SecurityAuditConstraints.TRACE_ID);
		String correlationId = MDC.get(SecurityAuditConstraints.CORRELATION_ID);
		if (correlationId == null) {
			correlationId = traceId;
		}
		String userId = MDC.get(SecurityAuditConstraints.USERID);
		if (userId == null) {
			userId = authenticatedUser();
		}
		SecurityEvent event = new SecurityEvent(applicationArchitecture.getArchitecture(), userId,
				MDC.get(SecurityAuditConstraints.CLIENT_IP), applicationName, correlationId, Instant.now().toString(),
				getCallers(), MDC.get(SecurityAuditConstraints.HTTP_METHOD),
				MDC.get(SecurityAuditConstraints.REQUEST_URI));
		event.setTraceId(traceId);
		event.setSpanId(MDC.get(SecurityAuditConstraints.SPAN_ID));
		return event;
	}

	private static String authenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return null;
		}
		return authentication.getName();
	}
}
