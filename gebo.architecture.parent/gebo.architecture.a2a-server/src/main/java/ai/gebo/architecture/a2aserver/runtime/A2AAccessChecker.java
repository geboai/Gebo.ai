package ai.gebo.architecture.a2aserver.runtime;

import org.springframework.stereotype.Service;

import ai.gebo.acl.AclGrantType;
import ai.gebo.architecture.a2aserver.model.A2AServerConfig;
import ai.gebo.security.services.IGSecurityService;
import lombok.AllArgsConstructor;

/**
 * Centralizes the inbound access decision for a published A2A endpoint, reusing
 * the platform {@link IGSecurityService} exactly like {@code GeboMcpAccessChecker}.
 * The caller's identity has already been established by the platform Spring
 * Security chain (self-issued JWT / API key, or OAuth2 resource server), so the
 * decision is made against that resolved local principal; the exported network is
 * then run impersonating it.
 */
@Service
@AllArgsConstructor
public class A2AAccessChecker {

	private final IGSecurityService securityService;

	/**
	 * Whether the current (impersonated) principal may use the endpoint. Disabled
	 * configs are never accessible; admins bypass the ACL.
	 */
	public boolean canAccessServer(A2AServerConfig config) {
		if (config == null) {
			return false;
		}
		if (config.getEnabled() == null || !config.getEnabled()) {
			return false;
		}
		return securityService.isCanDo(config, true, AclGrantType.READ, AclGrantType.EXECUTE);
	}
}
