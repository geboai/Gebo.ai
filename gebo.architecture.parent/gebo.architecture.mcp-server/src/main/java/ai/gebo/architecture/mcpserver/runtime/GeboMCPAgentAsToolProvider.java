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

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.mcpserver.model.GeboMCPAgentTool;
import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.llms.agent.standard.services.GTextAgentsNetworkRunner;
import ai.gebo.llms.agent.standard.services.UserKnowledgeBasesExecutionEnvironment;
import io.modelcontextprotocol.server.McpServerFeatures.SyncToolSpecification;
import lombok.AllArgsConstructor;

/**
 * Exports single agents as MCP tools: each configured agent becomes a tool
 * that takes a request in natural language and answers with the agent's text,
 * run as the caller the MCP request authenticated, over the knowledge bases
 * that caller can see.
 */
@Service
@AllArgsConstructor
public class GeboMCPAgentAsToolProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeboMCPAgentAsToolProvider.class);
	private static final String SINGLE_AGENT_CODE_PREFIX = "mcp-single-";

	private final IAgentConfigDao agentConfigDao;
	private final GTextAgentsNetworkRunner runner;
	private final GeboMcpSecurityContextSupport securitySupport;
	private final UserKnowledgeBasesExecutionEnvironment userEnvironment;

	public List<SyncToolSpecification> buildTools(GeboMCPServerConfig config) {
		List<GeboMCPAgentTool> exports = config.getAgentAsTools();
		if (exports == null || exports.isEmpty()) {
			return new ArrayList<>();
		}
		List<ToolCallback> callbacks = new ArrayList<>();
		for (GeboMCPAgentTool export : exports) {
			if (export == null || export.getAgentConfigCode() == null || export.getAgentConfigCode().isBlank()) {
				continue;
			}
			final String agentCode = export.getAgentConfigCode();
			GAgentConfig agent = agentConfigDao.findByCode(agentCode);
			if (agent == null) {
				LOGGER.warn("MCP server '{}' exports agent '{}' as a tool, but the agent does not exist",
						config.getExportedUniqueRelativeUrl(), agentCode);
				continue;
			}
			String toolName = export.getToolName() != null && !export.getToolName().isBlank() ? export.getToolName()
					: agentCode;
			String description = "Agent: "
					+ (agent.getDescription() != null && !agent.getDescription().isBlank() ? agent.getDescription()
							: agentCode);
			ToolCallback callback = ToolCallbackDeclarationUtil.declare((GeboMcpAgentToolRequest input, ToolContext ctx) -> {
				try {
					return runner.runAgent(agentCode, SINGLE_AGENT_CODE_PREFIX,
							input != null ? input.getRequest() : "", null);
				} catch (AgentException e) {
					LOGGER.error("MCP tool '" + toolName + "' (agent " + agentCode + ") failed", e);
					throw new IllegalStateException("The agent could not answer: " + e.getMessage(), e);
				}
			}, toolName, description, GeboMcpAgentToolRequest.class, String.class);
			callbacks.add(new GeboMcpSecurityAwareToolCallback(callback, securitySupport, userEnvironment));
		}
		return McpToolUtils.toSyncToolSpecification(callbacks);
	}
}
