package ai.gebo.security.services;

import java.util.HashMap;
import java.util.Map;

import ai.gebo.architecture.environment.GeboApplicationArchitecture.ArchitectureType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
/*****************************************************************************
 * Specific security logging events logging completely integrated with the RequestAuditFilter
 * to let the traceability of security events being complete
 */

public interface IGSecurityAuditLoggerService {
	@AllArgsConstructor
	@Getter
	public static class BasicSecurityEvent {
		private final ArchitectureType architectureType;
		private final String userId;
		private final String sourceIp;
		private final String application;
		private final String correlationId;
		private final String timestamp;
		private final String stackPoint;
	}

	@Data
	public static class SecurityEvent extends BasicSecurityEvent {
		public SecurityEvent(ArchitectureType architectureType, String userId, String sourceIp, String application,
				String correlationId, String timestamp, String stackPoint) {
			super(architectureType, userId, sourceIp, application, correlationId, timestamp, stackPoint);

		}

		private String eventType;
		private String category;
		private String severity;
		private String environment;
		private String tenantId;
		private String resourceType;
		private String resourceId;
		private String action;
		private String outcome;

		private Map<String, Object> details = new HashMap<String, Object>();
	}

	public void log(SecurityEvent event);

	public SecurityEvent newSecurityEvent();
}
