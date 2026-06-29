/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpclients.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

import ai.gebo.architecture.mcpclients.model.MCPClientConfig;
import io.modelcontextprotocol.json.McpJsonDefaults;
import io.modelcontextprotocol.spec.McpSchema;

/**
 * A {@link ToolCallback} that exposes a single remote MCP tool to the Gebo tool
 * pipeline.
 * <p>
 * The tool definition (name/description/inputSchema) is built up-front from the
 * configuration that was captured at discovery time, so listing the callbacks
 * does not require any network call. The connection to the MCP server is obtained
 * lazily, only when the tool is actually invoked, from the {@link McpClientPool}
 * (which reuses a live, already-authenticated connection across calls).
 */
class McpRemoteToolCallback implements ToolCallback {

	private static final Logger LOGGER = LoggerFactory.getLogger(McpRemoteToolCallback.class);

	private static final String EMPTY_OBJECT_SCHEMA = "{\"type\":\"object\",\"properties\":{}}";

	private final McpClientPool clientPool;
	private final MCPClientConfig config;
	/** The tool name as known by the remote server (without the exporting prefix). */
	private final String remoteToolName;
	private final ToolDefinition toolDefinition;

	McpRemoteToolCallback(McpClientPool clientPool, MCPClientConfig config, String exposedName, String remoteToolName,
			String description, String inputSchema) {
		this.clientPool = clientPool;
		this.config = config;
		this.remoteToolName = remoteToolName;
		this.toolDefinition = ToolDefinition.builder().name(exposedName)
				.description(description != null ? description : exposedName)
				.inputSchema(isBlank(inputSchema) ? EMPTY_OBJECT_SCHEMA : inputSchema).build();
	}

	@Override
	public ToolDefinition getToolDefinition() {
		return toolDefinition;
	}

	@Override
	public String call(String toolInput) {
		return call(toolInput, null);
	}

	@Override
	public String call(String toolInput, ToolContext toolContext) {
		try {
			return clientPool.execute(config, client -> {
				McpSchema.CallToolRequest.Builder requestBuilder = McpSchema.CallToolRequest.builder(remoteToolName);
				if (!isBlank(toolInput)) {
					// Let the MCP json mapper parse the raw JSON arguments produced by the LLM.
					requestBuilder.arguments(McpJsonDefaults.getMapper(), toolInput);
				}
				return renderResult(client.callTool(requestBuilder.build()));
			});
		} catch (Throwable t) {
			LOGGER.error("Error calling MCP tool '" + remoteToolName + "' on client '" + config.getCode() + "'", t);
			return "Error calling tool '" + remoteToolName + "': " + t.getMessage();
		}
	}

	/**
	 * Flattens the MCP {@link McpSchema.CallToolResult} into a plain string for the
	 * LLM: concatenated text contents, with non-text contents described by type, and
	 * an explicit error marker when the server flagged the call as failed.
	 */
	private String renderResult(McpSchema.CallToolResult result) {
		if (result == null) {
			return "(no result)";
		}
		StringBuilder sb = new StringBuilder();
		if (result.content() != null) {
			for (McpSchema.Content content : result.content()) {
				if (content instanceof McpSchema.TextContent textContent) {
					sb.append(textContent.text());
				} else if (content != null) {
					sb.append("[").append(content.getClass().getSimpleName()).append(" content]");
				}
			}
		}
		String rendered = sb.length() > 0 ? sb.toString() : "(no content)";
		if (Boolean.TRUE.equals(result.isError())) {
			return "ERROR: " + rendered;
		}
		return rendered;
	}

	private static boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
}
