/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.mcpclients.model.MCPClientConfig;
import ai.gebo.architecture.mcpclients.model.McpAuthMode;
import ai.gebo.architecture.mcpclients.model.MCPTransportType;
import ai.gebo.architecture.mcpclients.repository.McpClientConfigRepository;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.mcpclient.content.handler.IGMCPClientContentManagementHandler;
import ai.gebo.mcpclient.content.handler.MCPClientProjectEndpoint;
import ai.gebo.mcpclient.content.handler.impl.MCPClientNavigationUtil;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.VFilesystemReference;

/**
 * Full integration test of the MCP server ingestion workflow.
 * <p>
 * Like {@link SharedFilesystemIntegrationTest}, it mocks all LLMs (chat /
 * embedding / knowledge-extraction) but runs the <b>real</b>
 * ingestion → chunking → embedding pipeline end-to-end, backed by the
 * Testcontainers MongoDB / Neo4j / OpenSearch stack inherited from the base
 * class.
 * <p>
 * The data source is a real MCP server: the Model Context Protocol reference
 * "everything" server, started here as a Docker container over the SSE transport
 * ({@code tzolov/mcp-everything-server:v3}, the image purpose-built to exercise
 * the MCP Java SDK). The test wires together <b>both</b>:
 * <ul>
 * <li>the {@code gebo.architecture.mcp-clients} module — an
 * {@link MCPClientConfig} is persisted and the live connection is obtained
 * through its {@code McpClientPool};</li>
 * <li>the {@code gebo.mcl-client.content.handler} infrastructure — an
 * {@link MCPClientProjectEndpoint} exposes the server as a virtual drive whose
 * resources are browsed, streamed and ingested.</li>
 * </ul>
 * The endpoint points at the whole server (a root {@link VFilesystemReference}
 * with no sub-path), so the handler lists every resource the server exposes and
 * ingests it. The assertion verifies that the workflow completes and that the
 * (fake) vector store received embedded, metadata-enriched documents — proving
 * the MCP resources were read and vectorized. An exact resource count is
 * intentionally not pinned, since the reference server's resource set is
 * version-dependent.
 */
public class MCPServerIngestionIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {

	/**
	 * The MCP reference "everything" server, run over the SSE transport on its
	 * default port 3001. Not managed by the {@code @Testcontainers} extension (the
	 * suite starts its containers manually, see the base class); it is started
	 * explicitly by the test.
	 */
	@Container
	static final GenericContainer<?> MCP_EVERYTHING = new GenericContainer<>(
			DockerImageName.parse("tzolov/mcp-everything-server:v3")).withExposedPorts(3001)
			.withCommand("node", "dist/index.js", "sse").withStartupAttempts(2)
			.waitingFor(Wait.forListeningPort());

	private static final int MCP_SSE_PORT = 3001;
	private static final String MCP_CONFIG_CODE = "test-mcp-everything-server";

	@Autowired
	IGMCPClientContentManagementHandler mcpHandler;

	@Autowired
	McpClientConfigRepository mcpConfigRepository;

	/**
	 * Ingests every resource an MCP server exposes and verifies the full ingestion
	 * pipeline runs to completion producing embedded documents.
	 *
	 * @throws InstantiationException            if the endpoint cannot be created
	 * @throws IllegalAccessException            if the endpoint cannot be accessed
	 * @throws GeboPersistenceException          on persistence errors
	 * @throws GeboContentHandlerSystemException on content handler errors
	 * @throws GeboJobServiceException           on job service errors
	 * @throws InterruptedException              if the test thread is interrupted
	 */
	@Test
	public void testMCPServerIngestion()
			throws InstantiationException, IllegalAccessException, GeboPersistenceException,
			GeboContentHandlerSystemException, GeboJobServiceException, InterruptedException {
		if (!MCP_EVERYTHING.isRunning()) {
			MCP_EVERYTHING.start();
		}
		String baseUrl = "http://" + MCP_EVERYTHING.getHost() + ":" + MCP_EVERYTHING.getMappedPort(MCP_SSE_PORT);
		LOGGER.info("MCP everything server reachable at " + baseUrl);

		// 1) Persist an MCP client configuration (gebo.architecture.mcp-clients),
		// pointing at the containerized server over the SSE transport with no auth.
		MCPClientConfig config = new MCPClientConfig();
		config.setCode(MCP_CONFIG_CODE);
		config.setDescription("Everything MCP test server");
		config.setBaseUrl(baseUrl);
		config.setTransportType(MCPTransportType.SSE_LEGACY);
		config.setAuthMode(McpAuthMode.NONE);
		config.setExportingPrefix("everything");
		mcpConfigRepository.insert(config);

		// 2) Create the MCP virtual-drive endpoint referencing that configuration,
		// selecting the whole server (root reference with no sub-path) so every
		// exposed resource is integrated.
		MCPClientProjectEndpoint endpoint = createAndPersist("MCP server ingestion test",
				MCPClientProjectEndpoint.class);
		assertTrue(mcpHandler.isManagedEndpoint(endpoint), "The MCP handler must manage the MCP endpoint");
		endpoint.setMcpClientConfigCode(MCP_CONFIG_CODE);

		GVirtualFilesystemRoot serverRoot = MCPClientNavigationUtil.toRoot(config.getCode(), config.getDescription(),
				config.getBaseUrl());
		VFilesystemReference wholeServer = new VFilesystemReference();
		wholeServer.root = serverRoot;
		endpoint.setPaths(List.of(wholeServer));
		endpoint = persistentObjectManager.update(endpoint);

		// Sanity: the singleton MCP system is resolvable for this endpoint.
		assertNotNull(mcpHandler.getSystem(endpoint), "The singleton MCP system must be resolvable");

		// 3) Run the real STANDARD INGESTION workflow (mocked LLMs) and verify the
		// vector store received embedded, metadata-enriched documents. The exact
		// resource count is not asserted (reference-server dependent), so the
		// boolean overload is used.
		runAndWaitDoneCheckingResults(endpoint, true, true, 30);

		cleanPersistent(endpoint);
		mcpConfigRepository.deleteById(MCP_CONFIG_CODE);
	}
}
