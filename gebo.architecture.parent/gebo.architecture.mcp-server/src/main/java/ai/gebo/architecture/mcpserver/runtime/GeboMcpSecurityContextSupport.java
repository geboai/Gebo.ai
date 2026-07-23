/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpserver.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.modelcontextprotocol.common.McpTransportContext;

/**
 * Bridges the Spring Security context across the MCP request boundary.
 * <p>
 * The MCP SDK processes a request and then invokes the tool/resource/prompt handlers,
 * potentially on a different thread (servlet async dispatch), where the thread-local
 * {@link SecurityContext} established by the security filter chain is no longer visible.
 * This support captures the authenticated {@link Authentication} on the request thread
 * (via the transport's {@code contextExtractor}) into the {@link McpTransportContext},
 * and re-establishes it around every handler invocation so that tools, resource reads
 * and prompts run under the identity of the calling (impersonated) user and honour that
 * user's ACLs.
 */
@Service
public class GeboMcpSecurityContextSupport {

	static final String AUTHENTICATION_KEY = "geboAuthentication";

	/**
	 * Captures the current request's authentication into an MCP transport context. Run
	 * this on the request thread (from the transport {@code contextExtractor}).
	 *
	 * @return a transport context carrying the current authentication, if any
	 */
	public McpTransportContext capture() {
		Map<String, Object> values = new HashMap<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			values.put(AUTHENTICATION_KEY, authentication);
		}
		return McpTransportContext.create(values);
	}

	/**
	 * Runs the given action with the security context captured in the supplied transport
	 * context restored on the current thread, then restores the previous context.
	 *
	 * @param transportContext the MCP transport context carrying the captured authentication
	 * @param action           the action to run under the restored identity
	 * @return the action result
	 */
	public <T> T runAs(McpTransportContext transportContext, Supplier<T> action) {
		Object captured = transportContext != null ? transportContext.get(AUTHENTICATION_KEY) : null;
		if (!(captured instanceof Authentication)) {
			return action.get();
		}
		SecurityContext saved = SecurityContextHolder.getContext();
		try {
			SecurityContext restored = SecurityContextHolder.createEmptyContext();
			restored.setAuthentication((Authentication) captured);
			SecurityContextHolder.setContext(restored);
			return action.get();
		} finally {
			SecurityContextHolder.setContext(saved);
		}
	}
}
