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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.Container.ExecResult;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import ai.gebo.knlowledgebase.model.systems.GSystemRole;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.redmine.handler.GRedmineProjectEndpoint;
import ai.gebo.redmine.handler.GRedmineSystem;
import ai.gebo.redmine.handler.impl.RedmineContentManagementHandlerImpl;
import ai.gebo.redmine.handler.impl.RedmineNavigationUtil;
import ai.gebo.redmine.handler.repositories.RedmineSystemRepository;
import ai.gebo.secrets.model.GeboTokenContent;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

/**
 * Full integration test of the Redmine ingestion through the
 * {@code gebo.redmine.handler} module: the LLMs are the fake ones of the base
 * class, the ingestion, chunking and embedding pipeline is the real one.
 * <p>
 * The data source is a real Redmine ({@code redmine} image on its default
 * SQLite database) started as a container, once per supported release line, bootstrapped with a rails runner
 * script (default data, REST API on, the admin API key) and filled through its
 * REST API: two projects, a parent with a sub-issue, a closed issue, issue notes
 * and attachments, a wiki tree with an attachment and a project file. A data
 * source selecting both projects must ingest every one of them as its own
 * document, with the issue history and the attachments' text in the vectors.
 */
public class RedmineIngestionIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final String SECRET_KEY_BASE = "gebo-redmine-it-only-secret-key-base-0123456789abcdef";

	/**
	 * Every Redmine release line the handler supports: the 7.0 line the client
	 * spec describes and the 6.1 line still widely installed.
	 */
	static final String[] REDMINE_IMAGES = { "redmine:6.1.6", "redmine:7.0.3" };

	private static GenericContainer<?> redmineContainer(String image) {
		return new GenericContainer<>(DockerImageName.parse(image)).withExposedPorts(3000)
				.withEnv("REDMINE_SECRET_KEY_BASE", SECRET_KEY_BASE).withStartupAttempts(2)
				.waitingFor(Wait.forHttp("/").forStatusCode(200).withStartupTimeout(Duration.ofMinutes(5)));
	}

	private GenericContainer<?> redmine = null;

	private static final JsonMapper JSON = JsonMapper.builder().build();
	private final HttpClient http = HttpClient.newHttpClient();
	private String baseUri = null;
	private String apiKey = null;

	@Autowired
	RedmineSystemRepository redmineSystemsRepository;

	/**
	 * The documents of the seeded data: issues #1 (with its attachment), #2 (sub-issue
	 * of #1) and #3 (closed) of gebo-demo, #4 of gebo-demo-sub, the wiki pages Wiki,
	 * Install_guide (with its attachment) and Linux_setup, and the project file.
	 */
	static final int EXPECTED_DOCUMENTS = 10;

	@ParameterizedTest(name = "{0}")
	@FieldSource("REDMINE_IMAGES")
	public void testRedmineIngestion(String image) throws Exception {
		try (GenericContainer<?> container = redmineContainer(image)) {
			container.start();
			redmine = container;
			ingest(image);
		} finally {
			redmine = null;
		}
	}

	private void ingest(String image) throws Exception {
		baseUri = "http://" + redmine.getHost() + ":" + redmine.getMappedPort(3000);
		LOGGER.info(image + " reachable at " + baseUri);
		apiKey = bootstrap();
		seed();

		// the credentials as the fast setup stores them: a TOKEN secret with the API key
		GeboTokenContent token = new GeboTokenContent();
		token.setToken(apiKey);
		String secretId = secretsAccessService.storeSecret(token, "Redmine integration test account",
				RedmineContentManagementHandlerImpl.REDMINE);

		GRedmineSystem system = new GRedmineSystem();
		system.setDescription("Test Redmine system " + image);
		system.setBaseUri(baseUri);
		system.setSecretCode(secretId);
		system.setUsedCapabilities(List.of(GSystemRole.DOCUMENTS_MANAGEMENT));
		system.setCreationDate(new Date());
		system.setModificationDate(new Date());
		system.setContentManagementSystemType(RedmineContentManagementHandlerImpl.REDMINE);
		system = persistentObjectManager.insert(system);

		GRedmineProjectEndpoint endpoint = createAndPersist("Redmine ingestion test " + image.replace(':', ' '), GRedmineProjectEndpoint.class);
		endpoint.setRedmineSystemCode(system.getCode());
		List<VFilesystemReference> paths = new ArrayList<VFilesystemReference>();
		for (String project : List.of("gebo-demo", "gebo-demo-sub")) {
			VFilesystemReference reference = new VFilesystemReference();
			reference.root = RedmineNavigationUtil.toRoot(project, project, baseUri + "/projects/" + project);
			paths.add(reference);
		}
		endpoint.setPaths(paths);
		endpoint = persistentObjectManager.update(endpoint);

		runAndWaitDoneCheckingResults(endpoint, EXPECTED_DOCUMENTS, true);

		List<Document> stored = awaitSettledVectorStoreContent(getTestVectorStore(), x -> false);
		assertContains(stored, "Reproduced on version 6.0", "the notes of issue #1");
		assertContains(stored, "NullPointerException at Exporter.save", "the attachment of issue #1");
		assertContains(stored, "configure the mail server", "the Install_guide wiki page");
		assertContains(stored, "first public release with the PDF export", "the project file");
		assertContains(stored, "How do we share the wiki", "the issue of the subproject");
		assertNotNull(redmineSystemsRepository.findById(system.getCode()).orElse(null),
				"The Redmine system must still be resolvable after ingestion");

		cleanPersistent(endpoint);
		persistentObjectManager.delete(system);
		secretsAccessService.deleteSecret(secretId);
	}

	private static void assertContains(List<Document> stored, String text, String what) {
		assertTrue(stored.stream().anyMatch(d -> d.getText() != null && d.getText().contains(text)),
				"The vectors must hold " + what + " (\"" + text + "\")");
	}

	/**
	 * Runs the bootstrap script inside the container and returns the admin API key
	 * it prints.
	 */
	private String bootstrap() throws Exception {
		redmine.copyFileToContainer(MountableFile.forClasspathResource("REDMINE-IT/redmine-bootstrap.rb"),
				"/tmp/redmine-bootstrap.rb");
		ExecResult result = redmine.execInContainer("bash", "-c", "cd /usr/src/redmine && SECRET_KEY_BASE="
				+ SECRET_KEY_BASE + " bin/rails runner -e production /tmp/redmine-bootstrap.rb");
		Matcher m = Pattern.compile("ADMIN_KEY=(\\w+)").matcher(result.getStdout());
		assertTrue(m.find(), "The Redmine bootstrap must print the admin API key, stderr: " + result.getStderr());
		return m.group(1);
	}

	private JsonNode call(String method, String path, Object body) throws Exception {
		HttpRequest.Builder request = HttpRequest.newBuilder(URI.create(baseUri + path))
				.header("X-Redmine-API-Key", apiKey).header("Content-Type", "application/json");
		request.method(method, body != null ? HttpRequest.BodyPublishers.ofString(JSON.writeValueAsString(body))
				: HttpRequest.BodyPublishers.noBody());
		HttpResponse<String> response = http.send(request.build(), HttpResponse.BodyHandlers.ofString());
		assertTrue(response.statusCode() < 300,
				method + " " + path + " answered " + response.statusCode() + ": " + response.body());
		return response.body() == null || response.body().isBlank() ? null : JSON.readTree(response.body());
	}

	private Map<String, Object> upload(String name, String text) throws Exception {
		HttpRequest request = HttpRequest
				.newBuilder(URI.create(
						baseUri + "/uploads.json?filename=" + URLEncoder.encode(name, StandardCharsets.UTF_8)))
				.header("X-Redmine-API-Key", apiKey).header("Content-Type", "application/octet-stream")
				.POST(HttpRequest.BodyPublishers.ofString(text)).build();
		HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
		assertEquals(201, response.statusCode(), "upload of " + name + ": " + response.body());
		return Map.of("token", JSON.readTree(response.body()).path("upload").path("token").asString(), "filename",
				name, "content_type", "text/plain");
	}

	private int project(String identifier, String name, Integer parent) throws Exception {
		Map<String, Object> project = new LinkedHashMap<String, Object>();
		project.put("name", name);
		project.put("identifier", identifier);
		project.put("is_public", false);
		project.put("enabled_module_names", List.of("issue_tracking", "wiki", "files"));
		project.put("tracker_ids", List.of(1, 2, 3));
		if (parent != null) {
			project.put("parent_id", parent);
		}
		return call("POST", "/projects.json", Map.of("project", project)).path("project").path("id").asInt();
	}

	private int issue(int projectId, int tracker, String subject, String description, Integer parent,
			List<Map<String, Object>> uploads, Integer status) throws Exception {
		Map<String, Object> issue = new LinkedHashMap<String, Object>();
		issue.put("project_id", projectId);
		issue.put("tracker_id", tracker);
		issue.put("subject", subject);
		issue.put("description", description);
		if (parent != null) {
			issue.put("parent_issue_id", parent);
		}
		if (uploads != null) {
			issue.put("uploads", uploads);
		}
		int id = call("POST", "/issues.json", Map.of("issue", issue)).path("issue").path("id").asInt();
		if (status != null) {
			call("PUT", "/issues/" + id + ".json", Map.of("issue", Map.of("status_id", status)));
		}
		return id;
	}

	private void wiki(String project, String title, String text, String parent, List<Map<String, Object>> uploads)
			throws Exception {
		Map<String, Object> page = new LinkedHashMap<String, Object>();
		page.put("text", text);
		if (parent != null) {
			page.put("parent_title", parent);
		}
		if (uploads != null) {
			page.put("uploads", uploads);
		}
		call("PUT", "/projects/" + project + "/wiki/" + title + ".json", Map.of("wiki_page", page));
	}

	/**
	 * The contents of the test, through the Redmine REST API: trackers 1 to 3 are
	 * the default Bug, Feature and Support, status 5 is Closed.
	 */
	private void seed() throws Exception {
		int demo = project("gebo-demo", "Gebo demo", null);
		int sub = project("gebo-demo-sub", "Gebo demo subproject", demo);
		project("gebo-secret", "Gebo secret", null);
		int crash = issue(demo, 1, "Crash on save when the title contains a slash",
				"Saving a document whose title contains '/' throws a NullPointerException in the exporter.", null,
				List.of(upload("crash-log.txt", "java.lang.NullPointerException at Exporter.save(Exporter.java:42)\n")),
				null);
		call("PUT", "/issues/" + crash + ".json",
				Map.of("issue", Map.of("notes", "Reproduced on version 6.0 with the title 'Q1/Q2 report'.")));
		issue(demo, 2, "Add a regression test for slashes in titles",
				"Cover titles with '/', '\\' and ':' in the exporter test suite.", crash, null, null);
		issue(demo, 2, "Export issues to PDF", "Users want a PDF export of the issue list.", null, null, 5);
		issue(sub, 3, "Question about the subproject setup", "How do we share the wiki with the parent project?",
				null, null, null);
		wiki("gebo-demo", "Wiki", "h1. Gebo demo\n\nStart page of the demo project. See [[Install_guide]].", null,
				null);
		wiki("gebo-demo", "Install_guide", "h1. Install guide\n\nRun the installer, then configure the mail server.",
				"Wiki", List.of(upload("install-checklist.txt", "1. download\n2. install\n3. configure mail\n")));
		wiki("gebo-demo", "Linux_setup", "h1. Linux setup\n\nUse the tarball and systemd unit.", "Install_guide",
				null);
		Map<String, Object> file = new LinkedHashMap<String, Object>(
				upload("release-notes.txt", "Release 1.0: first public release with the PDF export.\n"));
		file.remove("content_type");
		file.put("description", "Release notes of version 1.0");
		call("POST", "/projects/gebo-demo/files.json", Map.of("file", file));
		LOGGER.info("Redmine seeded: projects gebo-demo, gebo-demo-sub and gebo-secret");
	}
}
