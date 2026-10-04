/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpclients.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.ai.model.ToolDataFlowTarget;
import ai.gebo.architecture.mcpclients.model.MCPClientConfig;
import ai.gebo.architecture.mcpclients.model.MCPTransportType;

/**
 * Pins what an MCP tool declares to the data-flow register: the server it is
 * exported from, reached where its transport says, never with the stdio arguments.
 */
class MCPToolsExporterDataFlowTest {

	private static MCPClientConfig config(MCPTransportType transport) {
		MCPClientConfig config = new MCPClientConfig();
		config.setCode("github");
		config.setTransportType(transport);
		config.setBaseUrl("https://mcp.example.com");
		config.setMcpEndpoint("/mcp");
		config.setSseEndpoint("/sse");
		config.setSecretCode("github-token");
		return config;
	}

	@Test
	void streamableServerIsReachedOnItsMcpEndpoint() {
		ToolDataFlowTarget target = MCPToolsExporterImpl.serverTarget(config(MCPTransportType.STREAMABLE_HTTP),
				"github_search");

		assertEquals(ToolDataFlowTarget.Kind.MCP_SERVER, target.kind());
		assertEquals("github", target.reference());
		assertEquals("MCP server github", target.product());
		assertEquals("https://mcp.example.com/mcp", target.locator());
		assertEquals("github-token", target.secretReference());
		assertFalse(target.personalData());
	}

	@Test
	void legacySseServerIsReachedOnItsSseEndpoint() {
		assertEquals("https://mcp.example.com/sse",
				MCPToolsExporterImpl.locatorOf(config(MCPTransportType.SSE_LEGACY)));
	}

	@Test
	void stdioServerShowsItsCommandButNotItsArguments() {
		MCPClientConfig config = config(MCPTransportType.STDIO);
		config.setStdioCommand("npx");
		config.setStdioArgs(List.of("-y", "server", "--token=secret"));

		assertEquals("stdio:npx", MCPToolsExporterImpl.locatorOf(config));
		config.setStdioCommand(null);
		assertEquals("stdio", MCPToolsExporterImpl.locatorOf(config));
	}
}
