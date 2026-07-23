/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpclients.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import ai.gebo.acl.AclGrantType;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.architecture.mcpclients.model.MCPClientConfig;
import ai.gebo.architecture.mcpclients.model.MCPTool;
import ai.gebo.architecture.mcpclients.repository.McpClientConfigRepository;
import ai.gebo.architecture.mcpclients.service.MCPToolsExporter;
import ai.gebo.security.services.IGSecurityService;
import lombok.AllArgsConstructor;

/**
 * Exposes the tools of the configured MCP servers to the Gebo tool pipeline as
 * {@link ToolCallback}s, scoped to the current user.
 * <p>
 * For every {@link MCPClientConfig} the current user is allowed to use, this
 * exporter walks the tools captured for that server and returns a callback for
 * each tool that (a) still exists remotely (is not flagged
 * {@code deletedOnMCPServer}) and (b) is visible to the current user. Visibility
 * is enforced with {@link IGSecurityService#filterCanDoAction} using the
 * {@link AclGrantType#EXECUTE} grant, both at the server level and at the
 * individual tool level. The returned callbacks are
 * {@link McpRemoteToolCallback}s, which connect to the server lazily on
 * invocation, so building this list performs no network I/O.
 */
@Service
@AllArgsConstructor
public class MCPToolsExporterImpl implements MCPToolsExporter {

	private static final String MCP_SERVERS_TOOLS = "MCP Servers tools";

	private static final String ALLOWED_MCP_TOOLS = "ALLOWED_MCP_TOOLS";

	private static final String ERROR_EXPORTING_MCP_TOOLS = "Error exporting MCP tools";

	private static final String ERROR_EXPORTING_TOOLS_FOR_MCP_CLIENT = "Error exporting tools for MCP client '";

	private static final Logger LOGGER = LoggerFactory.getLogger(MCPToolsExporterImpl.class);

	private static final String MCP_TOOLS_EXPORTER_IMPL = "MCPToolsExporterImpl";
	private static final ToolsCategory category = new ToolsCategory();
	static {
		category.setCode(ALLOWED_MCP_TOOLS);
		category.setDescription(MCP_SERVERS_TOOLS);
		category.setKnowledgeBaseRelative(false);
	}

	private final McpClientConfigRepository repository;
	private final IGSecurityService securityService;
	private final McpClientPool clientPool;

	@Override
	public String getId() {

		return MCP_TOOLS_EXPORTER_IMPL;
	}

	@Override
	public ToolsCategory getToolCategory() {

		return category;
	}

	@Override
	public List<ToolReference> getFullToolReferences() {

		return getToolCallbacks().stream().map(x -> new ToolReference(x)).toList();
	}

	@Override
	public List<ToolCallback> getToolCallbacks() {
		List<ToolCallback> callbacks = new ArrayList<>();
		try {
			List<MCPClientConfig> allConfigs = repository.findAll();
			// Only the MCP servers the current user is allowed to use.
			List<MCPClientConfig> accessibleConfigs = securityService.filterCanDoAction(allConfigs, true,
					AclGrantType.EXECUTE);
			for (MCPClientConfig config : accessibleConfigs) {
				try {
					addVisibleToolsOf(config, callbacks);
				} catch (Throwable t) {
					// A single misbehaving server must not break the whole tool pipeline.
					LOGGER.error(ERROR_EXPORTING_TOOLS_FOR_MCP_CLIENT + safeCode(config) + "'", t);
				}
			}
		} catch (Throwable t) {
			LOGGER.error(ERROR_EXPORTING_MCP_TOOLS, t);
		}
		return callbacks;
	}

	private void addVisibleToolsOf(MCPClientConfig config, List<ToolCallback> callbacks) {
		if (config == null || config.getTools() == null || config.getTools().isEmpty()) {
			return;
		}
		// Keep only tools that still exist remotely.
		List<MCPTool> liveTools = new ArrayList<>();
		for (MCPTool tool : config.getTools()) {
			if (tool != null && tool.getName() != null && !Boolean.TRUE.equals(tool.getDeletedOnMCPServer())) {
				liveTools.add(tool);
			}
		}
		// Then keep only the tools visible to the current user.
		List<MCPTool> visibleTools = securityService.filterCanDoAction(liveTools, true, AclGrantType.EXECUTE);
		String prefix = config.getExportingPrefix() != null ? config.getExportingPrefix() : "";
		for (MCPTool tool : visibleTools) {
			String exposedName = prefix + tool.getName();
			callbacks.add(new McpRemoteToolCallback(clientPool, config, exposedName, tool.getName(),
					tool.getDescription(), tool.getInputSchema()));
		}
	}

	private static String safeCode(MCPClientConfig config) {
		return config != null ? config.getCode() : null;
	}

}
