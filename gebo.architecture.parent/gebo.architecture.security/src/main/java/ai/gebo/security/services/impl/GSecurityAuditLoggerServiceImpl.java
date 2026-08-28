package ai.gebo.security.services.impl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
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
	@Override
	public SecurityEvent newSecurityEvent() {

		return new SecurityEvent(applicationArchitecture.getArchitecture(), MDC.get(SecurityAuditConstraints.USERID),
				MDC.get(SecurityAuditConstraints.CLIENT_IP), applicationName,
				MDC.get(SecurityAuditConstraints.CORRELATION_ID), Instant.now().toString(), getCallers(),
				MDC.get(SecurityAuditConstraints.HTTP_METHOD), MDC.get(SecurityAuditConstraints.REQUEST_URI));
	}
}
