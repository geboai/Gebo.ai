package ai.gebo.ollama.integration.tests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ai.gebo.architecture.integration.tests.preconditions.IntegrationTestConfig;
import ai.gebo.architecture.integration.tests.preconditions.PreconditionCheck;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 * Checks that the Ollama server the suite is configured for answers and has the
 * chat and embedding models pulled, so a missing model is reported as such
 * rather than surfacing as a failed setup minutes into the run.
 * <p>
 * Settings and defaults mirror {@code ai.gebo.sysinit.llms.config} in this
 * suite's {@code application.yml}; keep the two in sync.
 */
public class OllamaModelsAvailable implements PreconditionCheck {

	static final String URL = "OLLAMA_URL";
	static final String DEFAULT_URL = "http://localhost:11434";
	static final String CHAT_MODEL = "OLLAMA_CHAT_MODEL";
	static final String DEFAULT_CHAT_MODEL = "qwen3:14b";
	static final String EMBEDDING_MODEL = "OLLAMA_EMBEDDING_MODEL";
	static final String DEFAULT_EMBEDDING_MODEL = "mxbai-embed-large:latest";
	/** First release supporting the qwen3 architecture (release notes of v0.6.7). */
	static final String QWEN3_MINIMUM_VERSION = "0.6.7";

	@Override
	public List<String> missing() {
		String url = IntegrationTestConfig.get(URL, DEFAULT_URL);
		String tagsUrl = url.replaceAll("/+$", "") + "/api/tags";
		HttpResponse<String> response;
		try {
			response = client().send(HttpRequest.newBuilder(URI.create(tagsUrl)).timeout(Duration.ofSeconds(5)).build(),
					HttpResponse.BodyHandlers.ofString());
		} catch (IOException | IllegalArgumentException e) {
			return List.of("Ollama is not reachable at " + url + " (" + e + "): start it, or point " + URL
					+ " at a running server");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return List.of("interrupted while contacting Ollama at " + url);
		}
		if (response.statusCode() != 200) {
			return List.of("Ollama at " + url + " answered HTTP " + response.statusCode() + " to " + tagsUrl);
		}
		ObjectMapper mapper = new ObjectMapper();
		Set<String> pulled = new HashSet<>();
		for (JsonNode model : mapper.readTree(response.body()).path("models")) {
			pulled.add(withTag(model.path("name").asString()));
		}
		List<String> missing = new ArrayList<>();
		String chatModel = IntegrationTestConfig.get(CHAT_MODEL, DEFAULT_CHAT_MODEL);
		// A server too old for the model downloads it fine and only fails when loading
		// it, so this has to be told apart from a model that is merely not pulled.
		String version = chatModel.startsWith("qwen3") ? serverVersion(client(), url, mapper) : null;
		if (version != null && compareVersions(version, QWEN3_MINIMUM_VERSION) < 0) {
			missing.add("Ollama at " + url + " is version " + version + ", older than " + QWEN3_MINIMUM_VERSION
					+ ", the first release able to run " + chatModel
					+ ": upgrade Ollama (models already pulled are kept)");
		}
		for (String[] model : new String[][] { { CHAT_MODEL, chatModel },
				{ EMBEDDING_MODEL, IntegrationTestConfig.get(EMBEDDING_MODEL, DEFAULT_EMBEDDING_MODEL) } }) {
			if (!pulled.contains(withTag(model[1]))) {
				missing.add("Ollama model " + model[1] + " is not pulled on " + url + ": run 'ollama pull " + model[1]
						+ "', or set " + model[0] + " to a model that is");
			}
		}
		return missing;
	}

	private static HttpClient client() {
		return HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();
	}

	/** @return the server version, or {@code null} when it cannot be told */
	private static String serverVersion(HttpClient client, String url, ObjectMapper mapper) {
		try {
			HttpResponse<String> response = client.send(
					HttpRequest.newBuilder(URI.create(url.replaceAll("/+$", "") + "/api/version"))
							.timeout(Duration.ofSeconds(5)).build(),
					HttpResponse.BodyHandlers.ofString());
			String version = response.statusCode() == 200
					? mapper.readTree(response.body()).path("version").asString()
					: "";
			return version.matches("\\d+\\.\\d+\\.\\d+.*") ? version : null;
		} catch (IOException | RuntimeException e) {
			return null;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return null;
		}
	}

	/** Compares the numeric major.minor.patch parts, ignoring suffixes like -rc1. */
	static int compareVersions(String left, String right) {
		String[] l = left.split("[^0-9]+", 4);
		String[] r = right.split("[^0-9]+", 4);
		for (int i = 0; i < 3; i++) {
			int difference = Integer.parseInt(l[i]) - Integer.parseInt(r[i]);
			if (difference != 0) {
				return difference;
			}
		}
		return 0;
	}

	/** Ollama names an untagged model {@code name:latest}. */
	private static String withTag(String model) {
		return model.contains(":") ? model : model + ":latest";
	}
}
