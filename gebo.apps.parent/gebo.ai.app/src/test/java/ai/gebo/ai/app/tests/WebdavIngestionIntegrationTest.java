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
import org.testcontainers.utility.MountableFile;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.webdavcms.handler.GWebdavContentManagementSystem;
import ai.gebo.webdavcms.handler.GWebdavProjectEndpoint;
import ai.gebo.webdavcms.handler.WebdavVersion;
import ai.gebo.webdavcms.handler.impl.WebdavNavigationUtil;
import ai.gebo.webdavcms.handler.repositories.WebdavContentManagementSystemRepository;

/**
 * Full integration test of the WebDAV ingestion workflow.
 * <p>
 * Like {@link MCPServerIngestionIntegrationTest}, it mocks all LLMs (chat / embedding /
 * knowledge-extraction) but runs the <b>real</b> ingestion &rarr; chunking &rarr; embedding
 * pipeline end-to-end, backed by the Testcontainers MongoDB / Neo4j / OpenSearch stack
 * inherited from the base class.
 * <p>
 * The data source is a real WebDAV server, started here as a Docker container
 * ({@code bytemark/webdav}, a small Apache/mod_dav image configured with HTTP Basic
 * authentication) with a couple of test files copied into its served directory. The test
 * wires together the {@code gebo.webdav-cms.handler} module: a {@link GWebdavContentManagementSystem}
 * (holding the server's base URI, auth type and credentials secret) plus a
 * {@link GWebdavProjectEndpoint} referencing it and selecting the whole share as a single
 * root {@link VFilesystemReference}, mirroring how the "+Add data source" UI wires the two
 * objects together.
 */
public class WebdavIngestionIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {

	/**
	 * A WebDAV server (Apache/mod_dav) requiring HTTP Basic authentication, matching the
	 * auth type exercised by {@code WebdavConnectionFactory}.
	 */
	@Container
	static final GenericContainer<?> WEBDAV_SERVER = new GenericContainer<>(DockerImageName.parse("bytemark/webdav"))
			.withExposedPorts(80).withEnv("USERNAME", "geboadmin").withEnv("PASSWORD", "GeboWebdavTest123!")
			.withEnv("AUTH_TYPE", "Basic").withStartupAttempts(2).waitingFor(Wait.forListeningPort());

	private static final String WEBDAV_DATA_DIR = "/var/lib/dav/data/";
	private static final String WEBDAV_SYSTEM_CONTEXT_CODE = "test-webdav-system";

	@Autowired
	WebdavContentManagementSystemRepository webdavSystemsRepository;

	/**
	 * Ingests every file dropped on a WebDAV share and verifies the full ingestion
	 * pipeline runs to completion producing embedded, metadata-enriched documents.
	 *
	 * @throws InstantiationException   if the endpoint cannot be created
	 * @throws IllegalAccessException   if the endpoint cannot be accessed
	 * @throws GeboPersistenceException on persistence errors
	 * @throws GeboJobServiceException  on job service errors
	 * @throws InterruptedException     if the test thread is interrupted
	 * @throws GeboCryptSecretException on secret storage errors
	 */
	@Test
	public void testWebdavIngestion() throws InstantiationException, IllegalAccessException,
			GeboPersistenceException, GeboJobServiceException, InterruptedException, GeboCryptSecretException {
		if (!WEBDAV_SERVER.isRunning()) {
			WEBDAV_SERVER.start();
		}
		WEBDAV_SERVER.copyFileToContainer(MountableFile.forClasspathResource(TEST_001_PDF_FILE),
				WEBDAV_DATA_DIR + "v4man.pdf");
		WEBDAV_SERVER.copyFileToContainer(MountableFile.forClasspathResource(TEST_001_DOCX_FILE),
				WEBDAV_DATA_DIR + "demo.docx");

		String baseUri = "http://" + WEBDAV_SERVER.getHost() + ":" + WEBDAV_SERVER.getMappedPort(80) + "/";
		LOGGER.info("WebDAV server reachable at " + baseUri);

		// 1) Store the Basic-auth credentials as a Gebo secret, exactly like the
		// WebDAV system-fast admin UI does.
		GeboUsernamePasswordContent credentials = new GeboUsernamePasswordContent();
		credentials.setUsername("geboadmin");
		credentials.setPassword("GeboWebdavTest123!");
		String secretId = secretsAccessService.storeSecret(credentials, "BASIC WebDAV system secret",
				WEBDAV_SYSTEM_CONTEXT_CODE);

		// 2) Persist a WebDAV content management system pointing at the containerized
		// server over HTTP Basic authentication.
		GWebdavContentManagementSystem system = new GWebdavContentManagementSystem();
		system.setDescription("Test WebDAV system");
		system.setBaseUri(baseUri);
		system.setWebdavAuthType(WebdavVersion.BASIC);
		system.setSecretCode(secretId);
		system = persistentObjectManager.insert(system);

		// 3) Create the WebDAV project endpoint referencing that system, selecting the
		// whole share (root reference with no sub-path) so every dropped file is
		// ingested.
		GWebdavProjectEndpoint endpoint = createAndPersist("WebDAV ingestion test", GWebdavProjectEndpoint.class);
		endpoint.setWebdavSystemCode(system.getCode());
		GVirtualFilesystemRoot serverRoot = WebdavNavigationUtil.encodeRoot(baseUri, "Test WebDAV share");
		VFilesystemReference wholeShare = new VFilesystemReference();
		wholeShare.root = serverRoot;
		endpoint.setPaths(List.of(wholeShare));
		endpoint = persistentObjectManager.update(endpoint);

		// 4) Run the real STANDARD INGESTION workflow (mocked LLMs) and verify the
		// vector store received embedded, metadata-enriched documents for both files.
		runAndWaitDoneCheckingResults(endpoint, 2, true);

		assertNotNull(webdavSystemsRepository.findById(system.getCode()).orElse(null),
				"The WebDAV system configuration must still be resolvable after ingestion");
		assertTrue(getTestVectorStore().getHandledGeboDocumentCodes().size() >= 2,
				"Both WebDAV files must have been vectorized");

		cleanPersistent(endpoint);
		persistentObjectManager.delete(system);
		secretsAccessService.deleteSecret(secretId);
	}
}
