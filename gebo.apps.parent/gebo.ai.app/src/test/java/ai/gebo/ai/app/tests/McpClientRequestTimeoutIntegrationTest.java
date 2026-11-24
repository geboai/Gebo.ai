/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.TomcatURLStreamHandlerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.architecture.mcpclients.controllers.McpClientConfigController;
import ai.gebo.architecture.mcpclients.model.MCPClientConfig;
import ai.gebo.architecture.mcpclients.model.MCPTransportType;
import ai.gebo.architecture.mcpclients.model.McpAuthMode;
import ai.gebo.architecture.mcpclients.service.MCPToolsExporter;
import ai.gebo.model.OperationStatus;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures.SyncToolSpecification;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.HttpServletStreamableServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;

/**
 * Certifies the time a request to an MCP server waits for its answer, over the real
 * path: an MCP server with a tool that takes its time is registered through the
 * administration controller, and its tool is called as an agent calls it. A server
 * that sets no time of its own takes the one of the installation
 * ({@code ai.gebo.mcp.clients.request-timeout-seconds}); a server that sets one is
 * given that time.
 */
@TestPropertySource(properties = { "ai.gebo.mcp.clients.request-timeout-seconds=2" })
public class McpClientRequestTimeoutIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final String TOOL = "slow_answer";
	private static final String ANSWER = "GEBO_TEST_SLOW_TOOL_ANSWER: here it is.";
	/** How long the tool of the test server takes to answer. */
	private static final long TOOL_TIME_MILLIS = 5000;

	@Autowired
	private McpClientConfigController controller;
	@Autowired
	private MCPToolsExporter toolsExporter;

	@Test
	public void aRequestWaitsTheTimeOfTheInstallationOrTheOneItsServerSets() throws Exception {
		final HttpServletStreamableServerTransportProvider transport = HttpServletStreamableServerTransportProvider
				.builder().mcpEndpoint("/mcp").build();
		final McpSyncServer server = McpServer.sync(transport).serverInfo("slow test server", "1.0")
				.capabilities(McpSchema.ServerCapabilities.builder().tools(true).build())
				.tools(List.of(SyncToolSpecification.builder()
						.tool(McpSchema.Tool.builder(TOOL, Map.of("type", "object", "properties", Map.of()))
								.description("Answers after a while").build())
						.callHandler((exchange, request) -> {
							try {
								Thread.sleep(TOOL_TIME_MILLIS);
							} catch (InterruptedException e) {
								Thread.currentThread().interrupt();
							}
							return McpSchema.CallToolResult.builder().addTextContent(ANSWER).build();
						}).build()))
				.build();
		try {
			TomcatURLStreamHandlerFactory.disable();
		} catch (IllegalStateException alreadySet) {
			// the factory is already in place in this JVM
		}
		final Tomcat tomcat = new Tomcat();
		final String baseDir = Files.createTempDirectory("mcp-slow-server").toString();
		tomcat.setBaseDir(baseDir);
		tomcat.setPort(0);
		tomcat.getConnector();
		final Context context = tomcat.addContext("", baseDir);
		final Wrapper servlet = Tomcat.addServlet(context, "mcp", transport);
		servlet.setAsyncSupported(true);
		context.addServletMappingDecoded("/*", "mcp");
		tomcat.start();
		try {
			final String baseUrl = "http://localhost:" + tomcat.getConnector().getLocalPort();

			assertEquals(2, controller.getDefaultRequestTimeoutSeconds(),
					"the editor is told the time of the installation");

			// a server that sets no time of its own: the time of the installation
			final ToolCallback installationTime = toolOf(registered(baseUrl, "plain-", null));
			final String late = installationTime.call("{}");
			assertFalse(late.contains(ANSWER),
					"an answer coming after the time of the installation is not waited for: " + late);
			assertTrue(late.startsWith("Error calling tool"), "the agent is told the call failed: " + late);

			// a server that sets its own time: its requests are given that time
			final ToolCallback ownTime = toolOf(registered(baseUrl, "patient-", 30));
			assertEquals(ANSWER, ownTime.call("{}"), "the answer is waited for the time the server sets");
		} finally {
			server.close();
			tomcat.stop();
			tomcat.destroy();
		}
	}

	/** Registers the test server as an administrator does: discovery, then save. */
	private MCPClientConfig registered(String baseUrl, String prefix, Integer requestTimeoutSeconds) {
		MCPClientConfig config = new MCPClientConfig();
		config.setDescription("Slow MCP test server " + prefix);
		config.setBaseUrl(baseUrl);
		config.setMcpEndpoint("/mcp");
		config.setTransportType(MCPTransportType.STREAMABLE_HTTP);
		config.setAuthMode(McpAuthMode.NONE);
		config.setExportingPrefix(prefix);
		config.setAccessibleToAll(true);
		config.setRequestTimeoutSeconds(requestTimeoutSeconds);
		final OperationStatus<MCPClientConfig> discovered = controller.testAndDiscovery(config);
		assertNotNull(discovered.getResult(), "the server is reached: " + discovered.getMessages());
		final OperationStatus<MCPClientConfig> saved = controller.insertMCPClientConfig(discovered.getResult());
		assertNotNull(saved.getResult(), "the server is saved: " + saved.getMessages());
		assertEquals(requestTimeoutSeconds, saved.getResult().getRequestTimeoutSeconds());
		return saved.getResult();
	}

	/** The tool of the test server as the agents are given it. */
	private ToolCallback toolOf(MCPClientConfig config) {
		final String name = config.getExportingPrefix() + TOOL;
		for (ToolCallback callback : toolsExporter.getToolCallbacks()) {
			if (name.equals(callback.getToolDefinition().name())) {
				return callback;
			}
		}
		throw new AssertionError("the tool " + name + " is given to the agents");
	}
}
