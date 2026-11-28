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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.Container.ExecResult;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import ai.gebo.knlowledgebase.model.systems.GSystemRole;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.openproject.handler.GOpenProjectProjectEndpoint;
import ai.gebo.openproject.handler.GOpenProjectSystem;
import ai.gebo.openproject.handler.impl.OpenProjectApiClientFactory;
import ai.gebo.openproject.handler.impl.OpenProjectContentManagementHandlerImpl;
import ai.gebo.openproject.handler.impl.OpenProjectNavigationUtil;
import ai.gebo.openproject.handler.repositories.OpenProjectSystemRepository;
import ai.gebo.secrets.model.GeboTokenContent;

/**
 * Full integration test of the OpenProject ingestion through the
 * {@code gebo.openproject.handler} module: the LLMs are the fake ones of the base
 * class, the ingestion, chunking and embedding pipeline is the real one.
 * <p>
 * The data source is a real OpenProject (the all-in-one {@code openproject}
 * image, with its own PostgreSQL) started as a container and seeded by a rails
 * runner script, since the API v3 cannot create documents or meetings: a project
 * with a sub-project and a third project left out of the data source, a work
 * package tree with a comment, a relation and an attachment, a closed work
 * package, a document, a meeting with its agenda and an attachment, a news. A
 * data source selecting the project and its sub-project must ingest every one of
 * them as its own document, with the activity, the agenda and the attachments'
 * text in the vectors and nothing of the third project.
 */
public class OpenProjectIngestionIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final String SECRET_KEY_BASE = "0f1e2d3c4b5a69788796a5b4c3d2e1f00f1e2d3c4b5a69788796a5b4c3d2e1f0";

	/**
	 * A host name without port is allowed on any port, so the random port mapped
	 * by Testcontainers passes the OpenProject host check.
	 */
	@Container
	static final GenericContainer<?> OPENPROJECT = new GenericContainer<>(
			DockerImageName.parse("openproject/openproject:17")).withExposedPorts(80)
			.withEnv("SECRET_KEY_BASE", SECRET_KEY_BASE).withEnv("OPENPROJECT_SECRET_KEY_BASE", SECRET_KEY_BASE)
			.withEnv("OPENPROJECT_HOST__NAME", "localhost").withEnv("OPENPROJECT_HTTPS", "false")
			.withEnv("OPENPROJECT_DEFAULT__LANGUAGE", "en")
			.waitingFor(Wait.forHttp("/api/v3").forStatusCodeMatching(code -> code == 200 || code == 401)
					.withStartupTimeout(Duration.ofMinutes(15)));

	@Autowired
	OpenProjectSystemRepository openProjectSystemsRepository;

	/**
	 * The documents of the seeded data, in gebo-demo: the work packages SMTP relay
	 * (with its attachment), its child and grandchild and the PDF export, the
	 * document and its attachment, the meeting and its attachment, the news; in
	 * gebo-demo-sub its work package.
	 */
	static final int EXPECTED_DOCUMENTS = 11;

	@Test
	public void testOpenProjectIngestion() throws Exception {
		if (!OPENPROJECT.isRunning()) {
			OPENPROJECT.start();
		}
		String baseUri = "http://localhost:" + OPENPROJECT.getMappedPort(80);
		LOGGER.info("OpenProject reachable at " + baseUri);
		String apiKey = seed();

		// the credentials as the fast setup stores them: a TOKEN secret with the API key
		GeboTokenContent token = new GeboTokenContent();
		token.setUser(OpenProjectApiClientFactory.API_KEY_USER);
		token.setToken(apiKey);
		String secretId = secretsAccessService.storeSecret(token, "OpenProject integration test account",
				OpenProjectContentManagementHandlerImpl.OPENPROJECT);

		GOpenProjectSystem system = new GOpenProjectSystem();
		system.setDescription("Test OpenProject system");
		system.setBaseUri(baseUri);
		system.setSecretCode(secretId);
		system.setUsedCapabilities(List.of(GSystemRole.DOCUMENTS_MANAGEMENT));
		system.setCreationDate(new Date());
		system.setModificationDate(new Date());
		system.setContentManagementSystemType(OpenProjectContentManagementHandlerImpl.OPENPROJECT);
		system = persistentObjectManager.insert(system);

		GOpenProjectProjectEndpoint endpoint = createAndPersist("OpenProject ingestion test",
				GOpenProjectProjectEndpoint.class);
		endpoint.setOpenProjectSystemCode(system.getCode());
		List<VFilesystemReference> paths = new ArrayList<VFilesystemReference>();
		for (String project : List.of("gebo-demo", "gebo-demo-sub")) {
			VFilesystemReference reference = new VFilesystemReference();
			reference.root = OpenProjectNavigationUtil.toRoot(project, project, baseUri + "/projects/" + project);
			paths.add(reference);
		}
		endpoint.setPaths(paths);
		endpoint = persistentObjectManager.update(endpoint);

		runAndWaitDoneCheckingResults(endpoint, EXPECTED_DOCUMENTS, true);

		List<Document> stored = awaitSettledVectorStoreContent(getTestVectorStore(), x -> false);
		assertContains(stored, "Confirmed on production too", "the comment of the SMTP work package");
		assertContains(stored, "message too large (12MB)", "the attachment of the SMTP work package");
		assertContains(stored, "Document the mail limits", "the closed grandchild work package");
		assertContains(stored, "open the port 8080", "the document");
		assertContains(stored, "open the browser on the gebo host", "the attachment of the document");
		assertContains(stored, "Decide the new size limit", "the agenda of the meeting");
		assertContains(stored, "a 25 MB limit for the relay", "the attachment of the meeting");
		assertContains(stored, "live from today", "the news");
		assertContains(stored, "Slash commands in the chat window", "the work package of the sub-project");
		assertFalse(stored.stream().anyMatch(d -> d.getText() != null && d.getText().contains("Secret bug")),
				"The project outside the data source must not be ingested");
		assertNotNull(openProjectSystemsRepository.findById(system.getCode()).orElse(null),
				"The OpenProject system must still be resolvable after ingestion");

		cleanPersistent(endpoint);
		persistentObjectManager.delete(system);
		secretsAccessService.deleteSecret(secretId);
	}

	private static void assertContains(List<Document> stored, String text, String what) {
		assertTrue(stored.stream().anyMatch(d -> d.getText() != null && d.getText().contains(text)),
				"The vectors must hold " + what + " (\"" + text + "\")");
	}

	/**
	 * Runs the seed script inside the container and returns the admin API key it
	 * prints.
	 */
	private String seed() throws Exception {
		OPENPROJECT.copyFileToContainer(MountableFile.forClasspathResource("OPENPROJECT-IT/openproject-seed.rb"),
				"/tmp/openproject-seed.rb");
		ExecResult result = OPENPROJECT.execInContainer("bash", "-lc",
				"cd /app && bundle exec rails runner /tmp/openproject-seed.rb");
		Matcher m = Pattern.compile("ADMIN_KEY=(\\S+)").matcher(result.getStdout());
		assertTrue(m.find(), "The OpenProject seed must print the admin API key, stderr: " + result.getStderr());
		LOGGER.info("OpenProject seeded: projects gebo-demo, gebo-demo-sub and gebo-secret");
		return m.group(1);
	}
}
