/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

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
import org.testcontainers.containers.startupcheck.OneShotStartupCheckStrategy;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import ai.gebo.knlowledgebase.model.systems.GSystemRole;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.zammad.handler.GZammadProjectEndpoint;
import ai.gebo.zammad.handler.GZammadSystem;
import ai.gebo.zammad.handler.impl.ZammadContentManagementHandlerImpl;
import ai.gebo.zammad.handler.impl.ZammadNavigationUtil;
import ai.gebo.zammad.handler.model.ZammadPathNodeType;
import ai.gebo.zammad.handler.repositories.ZammadSystemRepository;

/**
 * Full integration test of the Zammad ingestion through the
 * {@code gebo.zammad.handler} module: the LLMs are the fake ones of the base
 * class, the ingestion, chunking and embedding pipeline is the real one.
 * <p>
 * The data source is a real Zammad 7.3 (its PostgreSQL, Redis and Memcached,
 * the one-shot database init and the rails server, without Elasticsearch)
 * seeded by a rails runner script:
 * <ul>
 * <li>the group "Gebo Support", in the data source, with an open ticket holding
 * a customer message (carrying relay.log), an internal note and a phone reply,
 * and a closed ticket;</li>
 * <li>the group "Gebo Secret", out of the data source, with its ticket;</li>
 * <li>a knowledge base with a category and a sub-category, a published answer
 * with an attachment, a second published answer, an internal answer, a draft
 * and an archived answer.</li>
 * </ul>
 * A data source selecting the support group and the knowledge base must ingest
 * every ticket conversation, attachment and listed answer as its own document,
 * and nothing of the secret group, of the draft or of the archived answer.
 */
public class ZammadIngestionIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final String ZAMMAD_IMAGE = "ghcr.io/zammad/zammad:7.3.0-0001";
	private static final Network NETWORK = Network.newNetwork();

	static final GenericContainer<?> POSTGRES = new GenericContainer<>(DockerImageName.parse("postgres:17.12-alpine"))
			.withNetwork(NETWORK).withNetworkAliases("zammad-postgresql").withEnv("POSTGRES_USER", "zammad")
			.withEnv("POSTGRES_PASSWORD", "zammad").withEnv("POSTGRES_DB", "zammad_production")
			// the init of the image restarts the server once: ready twice
			.waitingFor(Wait.forLogMessage(".*database system is ready to accept connections.*", 2)
					.withStartupTimeout(Duration.ofMinutes(3)));
	static final GenericContainer<?> REDIS = new GenericContainer<>(DockerImageName.parse("redis:8.12-alpine"))
			.withNetwork(NETWORK).withNetworkAliases("zammad-redis");
	static final GenericContainer<?> MEMCACHED = new GenericContainer<>(
			DockerImageName.parse("memcached:1.6.45-alpine")).withNetwork(NETWORK)
			.withNetworkAliases("zammad-memcached").withCommand("memcached", "-m", "128M");

	private static GenericContainer<?> zammad(String command) {
		return new GenericContainer<>(DockerImageName.parse(ZAMMAD_IMAGE)).withNetwork(NETWORK)
				.withEnv("MEMCACHE_SERVERS", "zammad-memcached:11211").withEnv("POSTGRESQL_DB", "zammad_production")
				.withEnv("POSTGRESQL_HOST", "zammad-postgresql").withEnv("POSTGRESQL_USER", "zammad")
				.withEnv("POSTGRESQL_PASS", "zammad").withEnv("POSTGRESQL_PORT", "5432")
				.withEnv("POSTGRESQL_DB_CREATE", "false").withEnv("REDIS_URL", "redis://zammad-redis:6379")
				.withEnv("ELASTICSEARCH_ENABLED", "false").withCommand(command);
	}

	/** Creates and migrates the database, then exits. */
	static final GenericContainer<?> ZAMMAD_INIT = zammad("zammad-init").dependsOn(POSTGRES, REDIS, MEMCACHED)
			.withCreateContainerCmdModifier(cmd -> cmd.withUser("0:0"))
			.withStartupCheckStrategy(new OneShotStartupCheckStrategy().withTimeout(Duration.ofMinutes(15)));

	static final GenericContainer<?> ZAMMAD = zammad("zammad-railsserver").dependsOn(ZAMMAD_INIT)
			.withExposedPorts(3000)
			.waitingFor(Wait.forHttp("/api/v1/getting_started").forPort(3000)
					.forStatusCodeMatching(code -> code == 200 || code == 401 || code == 403)
					.withStartupTimeout(Duration.ofMinutes(10)));

	@Autowired
	ZammadSystemRepository zammadSystemsRepository;

	/**
	 * The documents of the seeded data: in the support group the open ticket's
	 * conversation and relay.log, the closed ticket's conversation; in the
	 * knowledge base the install guide and its checklist, the proxy settings and
	 * the internal answer.
	 */
	static final int EXPECTED_DOCUMENTS = 7;

	@Test
	public void testZammadIngestion() throws Exception {
		if (!ZAMMAD.isRunning()) {
			ZAMMAD.start();
		}
		try {
			String baseUri = "http://localhost:" + ZAMMAD.getMappedPort(3000);
			LOGGER.info("Zammad reachable at " + baseUri);
			Seeded seeded = seed();

			// the credentials as the fast setup stores them: a TOKEN secret with the access token
			GeboTokenContent token = new GeboTokenContent();
			token.setToken(seeded.token);
			String secretId = secretsAccessService.storeSecret(token, "Zammad integration test account",
					ZammadContentManagementHandlerImpl.ZAMMAD);

			GZammadSystem system = new GZammadSystem();
			system.setDescription("Test Zammad system");
			system.setBaseUri(baseUri);
			system.setSecretCode(secretId);
			system.setUsedCapabilities(List.of(GSystemRole.DOCUMENTS_MANAGEMENT));
			system.setCreationDate(new Date());
			system.setModificationDate(new Date());
			system.setContentManagementSystemType(ZammadContentManagementHandlerImpl.ZAMMAD);
			system = persistentObjectManager.insert(system);

			GZammadProjectEndpoint endpoint = createAndPersist("Zammad ingestion test", GZammadProjectEndpoint.class);
			endpoint.setZammadSystemCode(system.getCode());
			VFilesystemReference group = new VFilesystemReference();
			group.root = ZammadNavigationUtil.toRoot(ZammadPathNodeType.GROUP, seeded.supportGroup,
					"Tickets of Gebo Support", null);
			VFilesystemReference knowledgeBase = new VFilesystemReference();
			knowledgeBase.root = ZammadNavigationUtil.toRoot(ZammadPathNodeType.KNOWLEDGE_BASE, seeded.knowledgeBase,
					"Gebo help center", null);
			endpoint.setPaths(List.of(group, knowledgeBase));
			endpoint = persistentObjectManager.update(endpoint);

			runAndWaitDoneCheckingResults(endpoint, EXPECTED_DOCUMENTS, true);

			List<Document> stored = awaitSettledVectorStoreContent(getTestVectorStore(), x -> false);
			assertContains(stored, "Mails over 10MB bounce since Monday", "the customer message of the open ticket");
			assertContains(stored, "Confirmed on production too", "the internal note of the open ticket");
			assertContains(stored, "a fix is planned for", "the phone reply of the open ticket");
			assertContains(stored, "message too large (12MB)", "the attachment of the open ticket");
			assertContains(stored, "Write the mail limits page", "the closed ticket");
			assertContains(stored, "open the port 8080", "the published install guide");
			assertContains(stored, "open the browser on the gebo host", "the attachment of the install guide");
			assertContains(stored, "Set the proxy for the relay", "the answer of the sub-category");
			assertContains(stored, "The relay credentials are in the vault", "the internal answer");
			assertAbsent(stored, "nobody outside the secret group", "The group outside the data source");
			assertAbsent(stored, "Draft text never published", "The draft answer");
			assertAbsent(stored, "Archived text of an old release", "The archived answer");
			assertNotNull(zammadSystemsRepository.findById(system.getCode()).orElse(null),
					"The Zammad system must still be resolvable after ingestion");

			cleanPersistent(endpoint);
			persistentObjectManager.delete(system);
			secretsAccessService.deleteSecret(secretId);
		} finally {
			ZAMMAD.stop();
			ZAMMAD_INIT.stop();
			MEMCACHED.stop();
			REDIS.stop();
			POSTGRES.stop();
		}
	}

	private static void assertContains(List<Document> stored, String text, String what) {
		assertTrue(stored.stream().anyMatch(d -> d.getText() != null && d.getText().contains(text)),
				"The vectors must hold " + what + " (\"" + text + "\")");
	}

	private static void assertAbsent(List<Document> stored, String text, String what) {
		assertFalse(stored.stream().anyMatch(d -> d.getText() != null && d.getText().contains(text)),
				what + " must not be ingested (\"" + text + "\")");
	}

	private record Seeded(String token, String supportGroup, String knowledgeBase) {
	}

	/**
	 * Runs the seed script inside the rails server and reads the access token and
	 * the ids it prints.
	 */
	private Seeded seed() throws Exception {
		ZAMMAD.copyFileToContainer(MountableFile.forClasspathResource("ZAMMAD-IT/zammad-seed.rb"),
				"/tmp/zammad-seed.rb");
		ExecResult result = ZAMMAD.execInContainer("bash", "-lc",
				"cd /opt/zammad && bundle exec rails runner /tmp/zammad-seed.rb");
		Matcher token = Pattern.compile("ADMIN_KEY=(\\S+)").matcher(result.getStdout());
		assertTrue(token.find(), "The Zammad seed must print the access token, stderr: " + result.getStderr());
		Matcher ids = Pattern.compile("IDS support=(\\d+) secret=\\d+ kb=(\\d+)").matcher(result.getStdout());
		assertTrue(ids.find(), "The Zammad seed must print the ids, stdout: " + result.getStdout());
		LOGGER.info("Zammad seeded: groups Gebo Support (" + ids.group(1) + ") and Gebo Secret, knowledge base "
				+ ids.group(2));
		return new Seeded(token.group(1), ids.group(1), ids.group(2));
	}
}
