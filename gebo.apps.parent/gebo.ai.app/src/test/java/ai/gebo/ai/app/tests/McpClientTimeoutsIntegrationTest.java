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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.TomcatURLStreamHandlerFactory;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
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
import io.modelcontextprotocol.spec.HttpHeaders;
import io.modelcontextprotocol.spec.McpSchema;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Certifies the times an MCP server is waited for, over the real path: an MCP server
 * is registered through the administration controller, and its tool is called as an
 * agent calls it.
 * <p>
 * A request: a server that sets no time of its own takes the one of the installation
 * ({@code ai.gebo.mcp.clients.request-timeout-seconds}); a server that sets one is
 * given that time.
 * <p>
 * The handshake of a connection: it waits the time of the installation
 * ({@code ai.gebo.mcp.clients.handshake-timeout-seconds}), which is not the one of
 * the requests.
 */
@TestPropertySource(properties = { "ai.gebo.mcp.clients.request-timeout-seconds=2",
		"ai.gebo.mcp.clients.handshake-timeout-seconds=3" })
public class McpClientTimeoutsIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final String TOOL = "slow_answer";
	private static final String ANSWER = "GEBO_TEST_SLOW_TOOL_ANSWER: here it is.";
	/** How long the tool of the test server takes to answer. */
	private static final long TOOL_TIME_MILLIS = 5000;
	/** How long the handshake of the test server takes, when it is made slow. */
	private static final long SLOW_HANDSHAKE_MILLIS = 6000;

	@Autowired
	private McpClientConfigController controller;
	@Autowired
	private MCPToolsExporter toolsExporter;

	@Test
	public void aRequestWaitsTheTimeOfTheInstallationOrTheOneItsServerSets() throws Exception {
		final TestServer testServer = new TestServer();
		try {
			assertEquals(2, controller.getDefaultRequestTimeoutSeconds(),
					"the editor is told the time of the installation");

			// a server that sets no time of its own: the time of the installation
			final ToolCallback installationTime = toolOf(registered(testServer.baseUrl, "plain-", null));
			final String late = installationTime.call("{}");
			assertFalse(late.contains(ANSWER),
					"an answer coming after the time of the installation is not waited for: " + late);
			assertTrue(late.startsWith("Error calling tool"), "the agent is told the call failed: " + late);

			// a server that sets its own time: its requests are given that time
			final ToolCallback ownTime = toolOf(registered(testServer.baseUrl, "patient-", 30));
			assertEquals(ANSWER, ownTime.call("{}"), "the answer is waited for the time the server sets");
		} finally {
			testServer.stop();
		}
	}

	@Test
	public void theHandshakeOfAConnectionWaitsTheTimeOfTheInstallation() throws Exception {
		final TestServer testServer = new TestServer();
		try {
			// its requests are given a long time: what is measured is the handshake alone
			final MCPClientConfig config = configOf(testServer.baseUrl, "handshake-", 30);

			final OperationStatus<MCPClientConfig> prompt = controller.testAndDiscovery(config);
			assertNotNull(prompt.getResult(), "a server answering the handshake is reached: " + prompt.getMessages());

			testServer.handshakeMillis.set(SLOW_HANDSHAKE_MILLIS);
			final long begin = System.currentTimeMillis();
			final OperationStatus<MCPClientConfig> slow = controller.testAndDiscovery(config);
			final long waited = System.currentTimeMillis() - begin;
			assertNull(slow.getResult(),
					"a handshake longer than the time of the installation is not waited for: " + slow.getMessages());
			assertTrue(waited < SLOW_HANDSHAKE_MILLIS,
					"the connection is given up at the time of the installation, waited " + waited + " ms");
		} finally {
			testServer.stop();
		}
	}

	/**
	 * An MCP server of the SDK on a web server of its own, with a tool that takes its
	 * time and a handshake that can be made slow.
	 */
	private static final class TestServer {
		/** How long the handshake takes: the request opening a session, the only one naming none. */
		final AtomicLong handshakeMillis = new AtomicLong(0);
		final String baseUrl;
		private final McpSyncServer server;
		private final Tomcat tomcat;

		TestServer() throws Exception {
			final HttpServletStreamableServerTransportProvider transport = HttpServletStreamableServerTransportProvider
					.builder().mcpEndpoint("/mcp").build();
			server = McpServer.sync(transport).serverInfo("slow test server", "1.0")
					.capabilities(McpSchema.ServerCapabilities.builder().tools(true).build())
					.tools(List.of(SyncToolSpecification.builder()
							.tool(McpSchema.Tool.builder(TOOL, Map.of("type", "object", "properties", Map.of()))
									.description("Answers after a while").build())
							.callHandler((exchange, request) -> {
								pause(TOOL_TIME_MILLIS);
								return McpSchema.CallToolResult.builder().addTextContent(ANSWER).build();
							}).build()))
					.build();
			try {
				TomcatURLStreamHandlerFactory.disable();
			} catch (IllegalStateException alreadySet) {
				// the factory is already in place in this JVM
			}
			tomcat = new Tomcat();
			final String baseDir = Files.createTempDirectory("mcp-slow-server").toString();
			tomcat.setBaseDir(baseDir);
			tomcat.setPort(0);
			tomcat.getConnector();
			final Context context = tomcat.addContext("", baseDir);
			final Filter slowHandshake = (request, response, chain) -> {
				if (((HttpServletRequest) request).getHeader(HttpHeaders.MCP_SESSION_ID) == null) {
					pause(handshakeMillis.get());
				}
				chain.doFilter(request, response);
			};
			final FilterDef filter = new FilterDef();
			filter.setFilterName("slow-handshake");
			filter.setFilter(slowHandshake);
			filter.setAsyncSupported("true");
			context.addFilterDef(filter);
			final FilterMap mapping = new FilterMap();
			mapping.setFilterName("slow-handshake");
			mapping.addURLPattern("/*");
			context.addFilterMap(mapping);
			final Wrapper servlet = Tomcat.addServlet(context, "mcp", transport);
			servlet.setAsyncSupported(true);
			context.addServletMappingDecoded("/*", "mcp");
			tomcat.start();
			baseUrl = "http://localhost:" + tomcat.getConnector().getLocalPort();
		}

		void stop() throws Exception {
			server.close();
			tomcat.stop();
			tomcat.destroy();
		}

		private static void pause(long millis) {
			if (millis <= 0) {
				return;
			}
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	/** Registers the test server as an administrator does: discovery, then save. */
	private MCPClientConfig registered(String baseUrl, String prefix, Integer requestTimeoutSeconds) {
		final MCPClientConfig config = configOf(baseUrl, prefix, requestTimeoutSeconds);
		final OperationStatus<MCPClientConfig> discovered = controller.testAndDiscovery(config);
		assertNotNull(discovered.getResult(), "the server is reached: " + discovered.getMessages());
		final OperationStatus<MCPClientConfig> saved = controller.insertMCPClientConfig(discovered.getResult());
		assertNotNull(saved.getResult(), "the server is saved: " + saved.getMessages());
		assertEquals(requestTimeoutSeconds, saved.getResult().getRequestTimeoutSeconds());
		return saved.getResult();
	}

	/** The test server as an administrator describes it, not yet saved. */
	private static MCPClientConfig configOf(String baseUrl, String prefix, Integer requestTimeoutSeconds) {
		MCPClientConfig config = new MCPClientConfig();
		config.setDescription("Slow MCP test server " + prefix);
		config.setBaseUrl(baseUrl);
		config.setMcpEndpoint("/mcp");
		config.setTransportType(MCPTransportType.STREAMABLE_HTTP);
		config.setAuthMode(McpAuthMode.NONE);
		config.setExportingPrefix(prefix);
		config.setAccessibleToAll(true);
		config.setRequestTimeoutSeconds(requestTimeoutSeconds);
		return config;
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
