/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpserver.runtime;

import org.springframework.stereotype.Service;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.ContentAccessPolicy;
import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.security.services.IGSecurityService;
import lombok.AllArgsConstructor;

/**
 * Centralizes the access decisions for the erogated MCP servers, reusing the
 * platform {@link IGSecurityService} so that ACL mode and the legacy
 * {@code accessible*} flags behave exactly as everywhere else in the product.
 */
@Service
@AllArgsConstructor
public class GeboMcpAccessChecker {

	private final IGSecurityService securityService;

	/**
	 * Whether the current (impersonated) user may use the MCP server described by
	 * the given configuration. The {@code GeboMCPServerConfig} node is itself the
	 * access grant: it carries the {@code accessible*} flags and the ACL aliases,
	 * so the decision is delegated to {@link IGSecurityService#isCanDo} with the
	 * {@link AclGrantType#EXECUTE} grant (mirroring the MCP client exporter).
	 * Administrators are not subject to the ACL and may always access the server
	 * ({@code adminCanDoAll = true}).
	 *
	 * @param config the MCP server configuration
	 * @return {@code true} if access is granted
	 */
	public boolean canAccessServer(GeboMCPServerConfig config) {
		if (config == null) {
			return false;
		}
		if (config.getEnabled() != null && !config.getEnabled()) {
			return false;
		}
		return securityService.isCanDo(config, true, AclGrantType.READ, AclGrantType.EXECUTE);
	}

	/**
	 * Whether the current user may read a specific exported object (knowledge base,
	 * project or project endpoint). Objects that carry the full security contract
	 * are checked with {@link IGSecurityService#isCanDo}; ACL-only objects (e.g.
	 * project endpoints) are checked against their ACLs when the platform runs in
	 * ACL mode and are otherwise allowed, since access to the server has already
	 * been granted.
	 *
	 * @param resource the exported object
	 * @return {@code true} if the object may be read
	 */
	public boolean canReadResource(Object resource) {
		if (resource == null) {
			return false;
		}
		if (resource instanceof IGObjectWithSecurity && resource instanceof IAclGrantedResource) {
			@SuppressWarnings("unchecked")
			IGObjectWithSecurity secured = (IGObjectWithSecurity) resource;
			return securityService.isCanDo(castSecured(secured), true, AclGrantType.READ);
		}
		if (resource instanceof IAclGrantedResource aclResource) {
			if (securityService.getPlatformContentAccessPolicy() == ContentAccessPolicy.ACL_BASED) {
				return securityService.isCanDoAction(aclResource, true, AclGrantType.READ);
			}
			return true;
		}
		// Unknown shape: deny by default.
		return false;
	}

	@SuppressWarnings("unchecked")
	private <T extends IGObjectWithSecurity & IAclGrantedResource> T castSecured(IGObjectWithSecurity secured) {
		return (T) secured;
	}
}
