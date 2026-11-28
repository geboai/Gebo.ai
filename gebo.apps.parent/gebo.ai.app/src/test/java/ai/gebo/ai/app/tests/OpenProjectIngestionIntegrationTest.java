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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Stream;
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

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.systems.GSystemRole;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.openproject.handler.GOpenProjectProjectEndpoint;
import ai.gebo.openproject.handler.GOpenProjectSystem;
import ai.gebo.openproject.handler.impl.OpenProjectApiClientFactory;
import ai.gebo.openproject.handler.impl.OpenProjectContentManagementHandlerImpl;
import ai.gebo.openproject.handler.impl.OpenProjectNavigationUtil;
import ai.gebo.openproject.handler.model.OpenProjectNativePositionObject;
import ai.gebo.openproject.handler.model.OpenProjectPathComponent;
import ai.gebo.openproject.handler.model.OpenProjectPathNodeType;
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
 * package tree with a comment, an internal comment, a relation and an
 * attachment, a closed work package, a document, a meeting with its agenda and an
 * attachment, a news. A data source selecting the project and its sub-project must
 * ingest every one of them as its own document, with the activity, the agenda and
 * the attachments' text in the vectors and nothing of the third project.
 * <p>
 * The internal comment must be a document of its own, flagged internal with its
 * author, and its text must never reach the work package's document. OpenProject
 * lists internal comments among a work package's activities only where its
 * Enterprise edition enables them, which this community container does not: the
 * data source selects the internal comment by its path too, so that its own
 * document is read through {@code /api/v3/activities/{id}}, which shows it to the
 * admin account.
 * </p>
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
	 * (with its attachment and its internal comment), its child and grandchild and
	 * the PDF export, the document and its attachment, the meeting and its
	 * attachment, the news; in gebo-demo-sub its work package.
	 */
	static final int EXPECTED_DOCUMENTS = 12;
	/** The text of the internal comment of the SMTP work package, in the seed. */
	static final String INTERNAL_COMMENT_TEXT = "the customer is still on the old contract";

	@Test
	public void testOpenProjectIngestion() throws Exception {
		if (!OPENPROJECT.isRunning()) {
			OPENPROJECT.start();
		}
		String baseUri = "http://localhost:" + OPENPROJECT.getMappedPort(80);
		LOGGER.info("OpenProject reachable at " + baseUri);
		Seeded seeded = seed();
		String apiKey = seeded.apiKey();

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
		// the internal comment by its path: this community OpenProject does not list it
		paths.add(OpenProjectNavigationUtil.toReference("gebo-demo", "gebo-demo",
				List.of(new OpenProjectPathComponent(OpenProjectPathNodeType.SECTION, "WORK_PACKAGES"),
						new OpenProjectPathComponent(OpenProjectPathNodeType.WORK_PACKAGE, seeded.smtpWorkPackage()),
						new OpenProjectPathComponent(OpenProjectPathNodeType.INTERNAL_COMMENT,
								seeded.internalComment())),
				null));
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
		assertInternalCommentApart(endpoint, seeded, stored);
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

	private static boolean holds(Document document, String text) {
		return document.getText() != null && document.getText().contains(text);
	}

	private static Object codeOf(Document document) {
		return document.getMetadata() != null ? document.getMetadata().get(DocumentMetaInfos.CONTENT_CODE) : null;
	}

	/**
	 * The internal comment of the SMTP work package is a document of its own,
	 * flagged internal with its activity and its author in the meta infos the
	 * enterprise ACL import reads, and its text is in its own vectors only: not in
	 * the work package's document, which keeps the public comment.
	 */
	private void assertInternalCommentApart(GOpenProjectProjectEndpoint endpoint, Seeded seeded, List<Document> stored) {
		List<GDocumentReference> documents;
		try (Stream<GDocumentReference> all = documentReferenceRepository
				.findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCode(endpoint.getClass().getName(),
						endpoint.getCode())) {
			documents = all.toList();
		}
		List<GDocumentReference> internal = documents.stream()
				.filter(d -> d.getCustomMetaInfos() != null && OpenProjectPathNodeType.INTERNAL_COMMENT.name()
						.equals(d.getCustomMetaInfos().get(OpenProjectNativePositionObject.OPENPROJECT_OBJECT_TYPE)))
				.toList();
		assertEquals(1, internal.size(), "The internal comment must be one document of its own: " + internal);
		GDocumentReference comment = internal.get(0);
		assertEquals("true",
				comment.getCustomMetaInfos().get(OpenProjectNativePositionObject.OPENPROJECT_INTERNAL_COMMENT));
		assertEquals(seeded.internalComment(),
				comment.getCustomMetaInfos().get(OpenProjectNativePositionObject.OPENPROJECT_ACTIVITY_ID));
		assertEquals(seeded.smtpWorkPackage(),
				comment.getCustomMetaInfos().get(OpenProjectNativePositionObject.OPENPROJECT_WORK_PACKAGE_ID));
		assertEquals(seeded.adminId(),
				comment.getCustomMetaInfos().get(OpenProjectNativePositionObject.OPENPROJECT_COMMENT_AUTHOR_ID));
		assertEquals("gebo-demo", comment.getCustomMetaInfos().get(OpenProjectNativePositionObject.OPENPROJECT_PROJECT_ID));
		GDocumentReference smtp = documents.stream()
				.filter(d -> d.getCustomMetaInfos() != null
						&& OpenProjectPathNodeType.WORK_PACKAGE_CONTENT.name()
								.equals(d.getCustomMetaInfos().get(OpenProjectNativePositionObject.OPENPROJECT_OBJECT_TYPE))
						&& seeded.smtpWorkPackage().equals(
								d.getCustomMetaInfos().get(OpenProjectNativePositionObject.OPENPROJECT_WORK_PACKAGE_ID)))
				.findFirst().orElse(null);
		assertNotNull(smtp, "The SMTP work package must be ingested: " + documents);

		List<Document> commentVectors = stored.stream().filter(d -> comment.getCode().equals(codeOf(d))).toList();
		List<Document> smtpVectors = stored.stream().filter(d -> smtp.getCode().equals(codeOf(d))).toList();
		assertTrue(commentVectors.stream().anyMatch(d -> holds(d, INTERNAL_COMMENT_TEXT)),
				"The internal comment's own vectors must hold its text: " + commentVectors);
		assertTrue(smtpVectors.stream().anyMatch(d -> holds(d, "Confirmed on production too")),
				"The work package's vectors keep its public comment: " + smtpVectors);
		assertFalse(smtpVectors.stream().anyMatch(d -> holds(d, INTERNAL_COMMENT_TEXT)),
				"The work package's vectors must not hold its internal comment");
		assertFalse(
				stored.stream().anyMatch(d -> !comment.getCode().equals(codeOf(d)) && holds(d, INTERNAL_COMMENT_TEXT)),
				"No vector but the internal comment's own may hold its text");
	}

	private record Seeded(String apiKey, String adminId, String smtpWorkPackage, String internalComment) {
	}

	private static String printed(ExecResult result, String name) {
		Matcher m = Pattern.compile(name + "=(\\S+)").matcher(result.getStdout());
		assertTrue(m.find(), "The OpenProject seed must print " + name + ", stderr: " + result.getStderr());
		return m.group(1);
	}

	/**
	 * Runs the seed script inside the container and returns what it prints: the
	 * admin API key and id, the SMTP work package and its internal comment.
	 */
	private Seeded seed() throws Exception {
		OPENPROJECT.copyFileToContainer(MountableFile.forClasspathResource("OPENPROJECT-IT/openproject-seed.rb"),
				"/tmp/openproject-seed.rb");
		ExecResult result = OPENPROJECT.execInContainer("bash", "-lc",
				"cd /app && bundle exec rails runner /tmp/openproject-seed.rb");
		Seeded seeded = new Seeded(printed(result, "ADMIN_KEY"), printed(result, "ADMIN_ID"),
				printed(result, "SMTP_WORK_PACKAGE"), printed(result, "INTERNAL_COMMENT"));
		LOGGER.info("OpenProject seeded: projects gebo-demo, gebo-demo-sub and gebo-secret, work package "
				+ seeded.smtpWorkPackage() + " with the internal comment " + seeded.internalComment());
		return seeded;
	}
}
