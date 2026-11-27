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

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.Container.ExecResult;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import ai.gebo.glpi.handler.GGlpiProjectEndpoint;
import ai.gebo.glpi.handler.GGlpiSystem;
import ai.gebo.glpi.handler.controllers.GlpiSystemsController;
import ai.gebo.glpi.handler.impl.GlpiContentManagementHandlerImpl;
import ai.gebo.glpi.handler.impl.GlpiNavigationUtil;
import ai.gebo.glpi.handler.impl.GlpiSystemsTestService;
import ai.gebo.glpi.handler.repositories.GlpiSystemRepository;
import ai.gebo.knlowledgebase.model.systems.GSystemRole;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;

/**
 * Ingestion of a GLPI entity through the {@code gebo.glpi.handler} module: the
 * LLMs are the fake ones of the base class, the ingestion, chunking and
 * embedding pipeline is the real one.
 * <p>
 * The data source is a real GLPI 11 ({@code glpi/glpi} image, auto-installed
 * on a MySQL container) seeded by {@code GLPI-IT/seed-glpi.sh}, run inside the
 * GLPI container: the APIs on, an OAuth client for the password grant, an
 * entity with two tickets (follow-ups, a task, a solution and two attached
 * files, one of them a PDF) and two knowledge base articles. A data source
 * selecting the entity must ingest every ticket body, timeline item, attached
 * file and article as its own document, with their text in the vectors.
 */
public class GlpiIngestionIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	/** The default Super-Admin account of a fresh GLPI install */
	private static final String GLPI_USER = "glpi";
	private static final String GLPI_PASSWORD = "glpi";
	private static final String OAUTH_CLIENT_SECRET = "gebo-glpi-it-only-client-secret-0123456789";
	private static final String SEED_DIR = "/tmp/gebo-seed/";
	private static final String DB_PASSWORD = "gebo-glpi-it-db";

	private static final Network NETWORK = Network.newNetwork();

	@Container
	static final GenericContainer<?> GLPI_DB = new GenericContainer<>(DockerImageName.parse("mysql:8.4"))
			.withNetwork(NETWORK).withNetworkAliases("db").withEnv("MYSQL_RANDOM_ROOT_PASSWORD", "yes")
			.withEnv("MYSQL_DATABASE", "glpi").withEnv("MYSQL_USER", "glpi").withEnv("MYSQL_PASSWORD", DB_PASSWORD)
			.waitingFor(Wait.forLogMessage(".*ready for connections.*port: 3306.*", 1)
					.withStartupTimeout(Duration.ofMinutes(3)));

	/**
	 * GLPI installs itself on first start (GLPI_DB_* all set); Apache starts
	 * once the installation is done.
	 */
	@Container
	static final GenericContainer<?> GLPI = new GenericContainer<>(DockerImageName.parse("glpi/glpi:11.0.13"))
			.withNetwork(NETWORK).dependsOn(GLPI_DB).withExposedPorts(80).withEnv("GLPI_DB_HOST", "db")
			.withEnv("GLPI_DB_PORT", "3306").withEnv("GLPI_DB_NAME", "glpi").withEnv("GLPI_DB_USER", "glpi")
			.withEnv("GLPI_DB_PASSWORD", DB_PASSWORD).waitingFor(Wait
					.forLogMessage(".*resuming normal operations.*", 1).withStartupTimeout(Duration.ofMinutes(6)));

	/**
	 * Ticket "VPN drops every evening": body, 2 follow-ups, a task, a solution
	 * and the firewall log file. Ticket "New laptop for the warehouse manager":
	 * body, a follow-up and the PDF. The 2 knowledge base articles.
	 */
	static final int EXPECTED_DOCUMENTS = 11;

	@Autowired
	GlpiSystemRepository glpiSystemsRepository;
	@Autowired
	GlpiSystemsTestService glpiSystemsTestService;

	@Test
	public void testGlpiIngestion() throws Exception {
		if (!GLPI.isRunning()) {
			GLPI.start();
		}
		String baseUri = "http://" + GLPI.getHost() + ":" + GLPI.getMappedPort(80);
		LOGGER.info("GLPI reachable at " + baseUri);
		Seeded seeded = seed();

		// the credentials as the GLPI fast setup stores them: the OAuth client and
		// the GLPI user, two secrets
		GeboOauth2SecretContent client = new GeboOauth2SecretContent();
		client.setProviderName(GlpiSystemsController.GLPI_OAUTH2_PROVIDER_NAME);
		client.setClientId(seeded.clientId);
		client.setSecret(OAUTH_CLIENT_SECRET);
		String clientSecretId = secretsAccessService.storeSecret(client, "GLPI integration test OAuth client",
				GlpiContentManagementHandlerImpl.GLPI_SYSTEM_TYPE);
		GeboUsernamePasswordContent user = new GeboUsernamePasswordContent();
		user.setUsername(GLPI_USER);
		user.setPassword(GLPI_PASSWORD);
		String userSecretId = secretsAccessService.storeSecret(user, "GLPI integration test user",
				GlpiContentManagementHandlerImpl.GLPI_SYSTEM_TYPE);

		GGlpiSystem system = new GGlpiSystem();
		system.setDescription("Test GLPI system");
		system.setBaseUri(baseUri);
		system.setOauthClientSecretCode(clientSecretId);
		system.setSecretCode(userSecretId);
		system.setUsedCapabilities(List.of(GSystemRole.DOCUMENTS_MANAGEMENT));
		system.setCreationDate(new Date());
		system.setModificationDate(new Date());
		system.setContentManagementSystemType(GlpiContentManagementHandlerImpl.GLPI_SYSTEM_TYPE);
		OperationStatus<GGlpiSystem> check = glpiSystemsTestService.testGlpiSystem(system);
		assertFalse(check.isHasErrorMessages(), "The GLPI system test must pass: " + check.getMessages());
		system = persistentObjectManager.insert(system);

		GGlpiProjectEndpoint endpoint = createAndPersist("GLPI ingestion test", GGlpiProjectEndpoint.class);
		endpoint.setGlpiSystemCode(system.getCode());
		VFilesystemReference entity = new VFilesystemReference();
		entity.root = GlpiNavigationUtil.toRoot(seeded.entityId, "Gebo IT test entity");
		endpoint.setPaths(List.of(entity));
		endpoint = persistentObjectManager.update(endpoint);

		runAndWaitDoneCheckingResults(endpoint, EXPECTED_DOCUMENTS, true);

		List<Document> stored = awaitSettledVectorStoreContent(getTestVectorStore(), x -> false);
		assertContains(stored, "drops every evening around 18:00", "the body of the VPN ticket");
		assertContains(stored, "Rekey lifetime set to 8 hours", "a follow-up of the VPN ticket");
		assertContains(stored, "Check the VPN tunnel again tomorrow", "the task of the VPN ticket");
		assertContains(stored, "Aligning the IKE rekey lifetime", "the solution of the VPN ticket");
		assertContains(stored, "IKE SA rekey failed: lifetime mismatch", "the file attached to the VPN ticket");
		assertContains(stored, "Bell Telephone Laboratories", "the PDF attached to the laptop ticket");
		assertContains(stored, "Hold the reset button for ten seconds", "a knowledge base article");
		assertNotNull(glpiSystemsRepository.findById(system.getCode()).orElse(null),
				"The GLPI system must still be resolvable after ingestion");

		cleanPersistent(endpoint);
		persistentObjectManager.delete(system);
		secretsAccessService.deleteSecret(clientSecretId);
		secretsAccessService.deleteSecret(userSecretId);
	}

	private static void assertContains(List<Document> stored, String text, String what) {
		assertTrue(stored.stream().anyMatch(d -> d.getText() != null && d.getText().contains(text)),
				"The vectors must hold " + what + " (\"" + text + "\")");
	}

	private record Seeded(long entityId, String clientId) {
	}

	/**
	 * Copies the seed script and the files to attach into the GLPI container and
	 * runs it there; returns the entity and the OAuth client id it prints.
	 */
	private Seeded seed() throws Exception {
		GLPI.copyFileToContainer(MountableFile.forClasspathResource("GLPI-IT/seed-glpi.sh"),
				SEED_DIR + "seed-glpi.sh");
		GLPI.copyFileToContainer(MountableFile.forClasspathResource("GLPI-IT/vpn-firewall-log.txt"),
				SEED_DIR + "vpn-firewall-log.txt");
		GLPI.copyFileToContainer(MountableFile.forClasspathResource(TEST_001_PDF_FILE), SEED_DIR + "v4man.pdf");
		ExecResult result = GLPI.execInContainer("bash", SEED_DIR + "seed-glpi.sh", GLPI_USER, GLPI_PASSWORD,
				OAUTH_CLIENT_SECRET, SEED_DIR);
		assertEquals(0, result.getExitCode(), "The GLPI seed must succeed, stderr: " + result.getStderr());
		LOGGER.info("GLPI seeded: " + result.getStdout().replaceAll("CLIENT_ID=\\S+", "CLIENT_ID=<hidden>"));
		Matcher entity = Pattern.compile("ENTITY_ID=(\\d+)").matcher(result.getStdout());
		Matcher clientId = Pattern.compile("CLIENT_ID=(\\S+)").matcher(result.getStdout());
		assertTrue(entity.find() && clientId.find(), "The GLPI seed must print the entity and the OAuth client id");
		return new Seeded(Long.parseLong(entity.group(1)), clientId.group(1));
	}
}
