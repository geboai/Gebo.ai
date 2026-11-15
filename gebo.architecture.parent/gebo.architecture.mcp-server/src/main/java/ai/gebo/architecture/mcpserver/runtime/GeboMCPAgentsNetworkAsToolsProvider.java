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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.mcpserver.model.GeboMCPAgentsNetworkTool;
import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCallingManagerFactory;
import ai.gebo.llms.agent.standard.services.GTextAgentsNetworkRunner;
import ai.gebo.llms.agent.standard.services.UserKnowledgeBasesExecutionEnvironment;
import io.modelcontextprotocol.server.McpServerFeatures.SyncToolSpecification;
import lombok.AllArgsConstructor;

/**
 * Exports networks of agents as MCP tools: each configured network becomes a
 * tool that takes a request in natural language and answers with the text the
 * network produced, run as the caller the MCP request authenticated, over the
 * knowledge bases that caller can see.
 */
@Service
@AllArgsConstructor
public class GeboMCPAgentsNetworkAsToolsProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeboMCPAgentsNetworkAsToolsProvider.class);

	private final IAgentsNetworkDao agentsNetworkDao;
	private final GTextAgentsNetworkRunner runner;
	private final GeboMcpSecurityContextSupport securitySupport;
	private final UserKnowledgeBasesExecutionEnvironment userEnvironment;
	/** Builds what the MCP server adds to every model call of a tool's run (see {@link #toolCallingOf}). */
	private final GeboToolCallingManagerFactory toolCallingFactory;

	/** The caller an MCP tool's run names itself as: {@code mcp:<server url>/<tool>}. */
	public static final String MCP_CALLER_PREFIX = "mcp:";

	/**
	 * What the MCP server adds to every model call of a tool's run (see
	 * {@link GeboToolCalling}): the server and the tool as the caller, the platform's
	 * loop limits, no listener. One per call: a run's records are its own.
	 */
	GeboToolCalling toolCallingOf(GeboMCPServerConfig config, String toolName) {
		return toolCallingFactory.builder()
				.caller(MCP_CALLER_PREFIX + (config != null ? config.getExportedUniqueRelativeUrl() : null) + "/"
						+ toolName)
				.build();
	}

	public List<SyncToolSpecification> buildTools(GeboMCPServerConfig config) {
		return McpToolUtils.toSyncToolSpecification(callbacks(config));
	}

	/** The tools of the exports of the server, as tool callbacks. */
	List<ToolCallback> callbacks(GeboMCPServerConfig config) {
		List<GeboMCPAgentsNetworkTool> exports = config.getAgentNetworkAsTools();
		List<ToolCallback> callbacks = new ArrayList<>();
		if (exports == null || exports.isEmpty()) {
			return callbacks;
		}
		for (GeboMCPAgentsNetworkTool export : exports) {
			if (export == null || export.getAgentsNetworkCode() == null || export.getAgentsNetworkCode().isBlank()) {
				continue;
			}
			final String networkCode = export.getAgentsNetworkCode();
			GAgentsNetwork network = agentsNetworkDao.findByCode(networkCode);
			if (network == null) {
				LOGGER.warn("MCP server '{}' exports network '{}' as a tool, but the network does not exist",
						config.getExportedUniqueRelativeUrl(), networkCode);
				continue;
			}
			String toolName = export.getToolName() != null && !export.getToolName().isBlank() ? export.getToolName()
					: networkCode;
			String description = "Network of agents: "
					+ (network.getDescription() != null && !network.getDescription().isBlank()
							? network.getDescription()
							: networkCode);
			ToolCallback callback = ToolCallbackDeclarationUtil.declare((GeboMcpAgentToolRequest input, ToolContext ctx) -> {
				try {
					return runner.runNetwork(networkCode, input != null ? input.getRequest() : "", null,
							toolCallingOf(config, toolName));
				} catch (AgentException e) {
					LOGGER.error("MCP tool '" + toolName + "' (network " + networkCode + ") failed", e);
					throw new IllegalStateException("The network could not answer: " + e.getMessage(), e);
				}
			}, toolName, description, GeboMcpAgentToolRequest.class, String.class);
			callbacks.add(new GeboMcpSecurityAwareToolCallback(callback, securitySupport, userEnvironment));
		}
		return callbacks;
	}
}
