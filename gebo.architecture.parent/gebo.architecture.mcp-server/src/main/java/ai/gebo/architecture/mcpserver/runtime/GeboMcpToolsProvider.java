/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpserver.runtime;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import io.modelcontextprotocol.server.McpServerFeatures.SyncToolSpecification;
import lombok.AllArgsConstructor;

/**
 * Builds the MCP tool specifications exposed by an erogated MCP server from the
 * {@code enabledTools} of its {@link GeboMCPServerConfig}.
 * <p>
 * The tool names are resolved against the platform-wide tool repository
 * ({@link IGToolCallbackSourceRepositoryPattern}) and bridged to MCP tool
 * specifications with {@link McpToolUtils#toSyncToolSpecification(List)}. The
 * resulting tools are the very same {@link ToolCallback}s used by the chat
 * pipeline, so they execute under the SecurityContext of the calling (impersonated)
 * user and naturally honour that user's ACLs.
 */
@Service
@AllArgsConstructor
public class GeboMcpToolsProvider {

	private final IGToolCallbackSourceRepositoryPattern toolRepository;
	private final GeboMcpSecurityContextSupport securitySupport;

	/**
	 * Resolves the enabled tools of the given configuration to MCP tool
	 * specifications. Each tool is wrapped so it executes under the calling user's
	 * security context.
	 *
	 * @param config the MCP server configuration
	 * @return the tool specifications to register, never {@code null}
	 */
	public List<SyncToolSpecification> buildTools(GeboMCPServerConfig config) {
		List<String> names = config.getEnabledTools();
		if (names == null || names.isEmpty()) {
			return new ArrayList<>();
		}
		List<ToolCallback> callbacks = toolRepository.getTools(names);
		if (callbacks == null || callbacks.isEmpty()) {
			return new ArrayList<>();
		}
		List<ToolCallback> secured = new ArrayList<>();
		for (ToolCallback callback : callbacks) {
			secured.add(new GeboMcpSecurityAwareToolCallback(callback, securitySupport));
		}
		return McpToolUtils.toSyncToolSpecification(secured);
	}
}
