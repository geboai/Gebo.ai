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
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.startupcheck.OneShotStartupCheckStrategy;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.MountableFile;
import org.yaml.snakeyaml.Yaml;

/**
 * The observability stacks this repository ships - the monolith's
 * {@code dockers/docker-compose-deploy/docker-compose.observability.yml} overlay, the
 * microservices compose and the Helm chart - keep working with the images they
 * pin:
 *
 * <ul>
 * <li>every observability image is pinned to an explicit version (an unpinned
 * {@code grafana/tempo} stopped starting when Tempo 3 dropped the
 * ingester/compactor sections of {@code tempo-config.yaml});</li>
 * <li>the pinned Tempo starts with the shipped {@code tempo-config.yaml}, the pinned
 * OpenTelemetry collector validates the shipped collector config, the pinned
 * Prometheus' {@code promtool} accepts both shipped {@code prometheus.yml} files,
 * and the pinned Grafana provisions the shipped datasources and dashboard;</li>
 * <li>the application is pointed at the collector with Spring Boot 4's
 * {@code MANAGEMENT_OPENTELEMETRY_TRACING_EXPORT_OTLP_ENDPOINT} (the old
 * {@code MANAGEMENT_OTLP_TRACING_ENDPOINT} is no longer read, so no trace would
 * leave), and Prometheus scrapes the management port the application is given.</li>
 * </ul>
 *
 * <p>
 * The file checks need no container. The container checks skip themselves, rather
 * than failing, when Docker is unavailable or with
 * {@code -Dgebo.observability.compatibility.test.skip=true}, to avoid pulling the
 * images. Raise an image version only together with this test.
 * </p>
 */
public class ObservabilityOverlayCompatibilityTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ObservabilityOverlayCompatibilityTest.class);

	private static final String OLD_OTLP_VARIABLE = "MANAGEMENT_OTLP_TRACING_ENDPOINT";
	private static final String OTLP_VARIABLE = "MANAGEMENT_OPENTELEMETRY_TRACING_EXPORT_OTLP_ENDPOINT";
	private static final List<String> OBSERVABILITY_IMAGES = List.of("otel/opentelemetry-collector-contrib",
			"prom/prometheus", "grafana/tempo", "grafana/grafana");

	/** The repository root: user.dir is gebo.apps.parent/gebo.ai.app during `mvn test`. */
	private static Path repositoryRoot() {
		String override = System.getProperty("gebo.repository.root");
		return (override != null ? new File(override) : new File(System.getProperty("user.dir"), "../.."))
				.toPath().normalize();
	}

	private static Path monolithDir() {
		return repositoryRoot().resolve("dockers/docker-compose-deploy");
	}

	private static Path microservicesDir() {
		return repositoryRoot().resolve("dockers/gebo.microservices");
	}

	private static Path helmChart() {
		return repositoryRoot().resolve("deploy/helm/gebo-microservices");
	}

	private static String read(Path file) throws IOException {
		assertTrue(Files.isRegularFile(file), () -> "Missing " + file);
		return Files.readString(file, StandardCharsets.UTF_8);
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> services(Path composeFile) throws IOException {
		Map<String, Object> compose = new Yaml().load(read(composeFile));
		return (Map<String, Object>) compose.get("services");
	}

	/** The image of a compose service, as written. */
	@SuppressWarnings("unchecked")
	private static String image(Map<String, Object> services, String service) {
		Map<String, Object> definition = (Map<String, Object>) services.get(service);
		assertNotNull(definition, () -> "No service " + service);
		return (String) definition.get("image");
	}

	/** The environment of a compose service, merged keys included. */
	@SuppressWarnings("unchecked")
	private static Map<String, Object> environment(Map<String, Object> services, String service) {
		Map<String, Object> definition = (Map<String, Object>) services.get(service);
		assertNotNull(definition, () -> "No service " + service);
		Object env = definition.get("environment");
		return env instanceof Map ? (Map<String, Object>) env : Map.of();
	}

	/** The pinned image of the observability service named by its repository, e.g. grafana/tempo. */
	private static String pinnedImage(String repository) throws IOException {
		Map<String, Object> services = services(monolithDir().resolve("docker-compose.observability.yml"));
		for (String service : services.keySet()) {
			String image = image(services, service);
			if (image != null && image.startsWith(repository + ":")) {
				return image;
			}
		}
		throw new AssertionError("No pinned " + repository + " image in the observability overlay");
	}

	private static void assertPinned(String where, String image) {
		assertNotNull(image, () -> where + " has no image");
		int colon = image.lastIndexOf(':');
		String tag = colon > image.lastIndexOf('/') ? image.substring(colon + 1) : "";
		assertFalse(tag.isEmpty() || tag.equals("latest"),
				() -> where + " runs " + image + ": pin it to the version this test verifies");
	}

	@Test
	public void everyObservabilityImageIsPinnedInEveryStack() throws IOException {
		for (Path compose : List.of(monolithDir().resolve("docker-compose.observability.yml"),
				microservicesDir().resolve("docker-compose.yml"))) {
			Map<String, Object> services = services(compose);
			for (String service : List.of("otel-collector", "prometheus", "tempo", "grafana")) {
				assertPinned(compose + " service " + service, image(services, service));
			}
		}
		String values = read(helmChart().resolve("values.yaml"));
		for (String repository : OBSERVABILITY_IMAGES) {
			Matcher m = Pattern.compile("image:\\s*(" + Pattern.quote(repository) + "\\S*)").matcher(values);
			assertTrue(m.find(), () -> "No " + repository + " image in the Helm values");
			assertPinned("Helm values " + repository, m.group(1));
		}
	}

	@Test
	public void theStacksUseSpringBoot4TracingEndpointAndTheManagementPort() throws IOException {
		for (Path file : List.of(monolithDir().resolve("docker-compose.observability.yml"),
				microservicesDir().resolve("docker-compose.yml"), helmChart().resolve("templates/_helpers.tpl"),
				helmChart().resolve("templates/edge.yaml"))) {
			String text = read(file);
			assertFalse(text.contains(OLD_OTLP_VARIABLE + ":") || text.contains("name: " + OLD_OTLP_VARIABLE),
					() -> file + " still sets " + OLD_OTLP_VARIABLE + ", which Spring Boot 4 ignores: use "
							+ OTLP_VARIABLE);
			assertTrue(text.contains(OTLP_VARIABLE), () -> file + " does not point the traces at the collector");
		}

		// the monolith: Prometheus scrapes the management port the overlay gives gebo.ai
		Map<String, Object> overlay = services(monolithDir().resolve("docker-compose.observability.yml"));
		Object managementPort = environment(overlay, "gebo.ai").get("MANAGEMENT_SERVER_PORT");
		assertNotNull(managementPort, "The overlay gives gebo.ai no management port to scrape");
		assertTrue(read(monolithDir().resolve("prometheus.yml")).contains("gebo.ai:" + managementPort),
				"prometheus.yml does not scrape gebo.ai on its management port " + managementPort);
		Map<String, Object> base = services(monolithDir().resolve("docker-compose.yml"));
		assertFalse(String.valueOf(base.get("gebo.ai")).contains(String.valueOf(managementPort)),
				"The management port must not be published by the base compose file");

		// the microservices: every scrape target is on the shared management port
		String microEnv = read(microservicesDir().resolve("docker-compose.yml"));
		Matcher port = Pattern.compile("MANAGEMENT_SERVER_PORT:\\s*\"(\\d+)\"").matcher(microEnv);
		assertTrue(port.find(), "The microservices get no management port");
		String microPort = port.group(1);
		List<String> targets = new ArrayList<>();
		Matcher t = Pattern.compile("targets: \\[\"([a-z0-9.-]+):(\\d+)\"\\]")
				.matcher(read(microservicesDir().resolve("prometheus.yml")));
		while (t.find()) {
			targets.add(t.group(1));
			assertEquals(microPort, t.group(2), "The scrape target " + t.group(1) + " is not on the management port");
		}
		assertTrue(targets.contains("gateway") && targets.size() > 10, () -> "Unexpected targets " + targets);
		assertFalse(microEnv.contains("\"" + microPort + ":" + microPort + "\""),
				"The management port must not be published");
	}

	private static void assumeContainers() {
		assumeTrue(!Boolean.getBoolean("gebo.observability.compatibility.test.skip"),
				"Skipped on request (-Dgebo.observability.compatibility.test.skip=true)");
		assumeTrue(DockerClientFactory.instance().isDockerAvailable(),
				"Docker is not available: cannot run the pinned observability images");
	}

	@Test
	public void thePinnedTempoStartsWithTheShippedConfig() throws Exception {
		assumeContainers();
		for (Path config : List.of(monolithDir().resolve("tempo-config.yaml"),
				microservicesDir().resolve("tempo-config.yaml"))) {
			try (GenericContainer<?> tempo = new GenericContainer<>(pinnedImage("grafana/tempo"))
					.withCopyFileToContainer(MountableFile.forHostPath(config, 0644), "/etc/tempo.yaml")
					.withCommand("-config.file=/etc/tempo.yaml").withExposedPorts(3200)
					.waitingFor(Wait.forHttp("/ready").forPort(3200).forStatusCode(200)
							.withStartupTimeout(Duration.ofMinutes(3)))
					.withLogConsumer(new Slf4jLogConsumer(LOGGER).withPrefix("tempo"))) {
				tempo.start();
				LOGGER.info("{} is ready with {}", tempo.getDockerImageName(), config);
			}
		}
	}

	@Test
	public void thePinnedCollectorValidatesTheShippedConfig() throws Exception {
		assumeContainers();
		for (Path config : List.of(monolithDir().resolve("otel-collector-config.yaml"),
				microservicesDir().resolve("otel-collector-config.yaml"))) {
			try (GenericContainer<?> collector = new GenericContainer<>(
					pinnedImage("otel/opentelemetry-collector-contrib"))
					.withCopyFileToContainer(MountableFile.forHostPath(config, 0644), "/etc/otelcol-contrib/config.yaml")
					.withCommand("validate", "--config=/etc/otelcol-contrib/config.yaml")
					.withStartupCheckStrategy(new OneShotStartupCheckStrategy().withTimeout(Duration.ofMinutes(2)))
					.withLogConsumer(new Slf4jLogConsumer(LOGGER).withPrefix("otel-collector"))) {
				// a non-zero exit of `validate` fails the start
				collector.start();
			}
		}
	}

	@Test
	public void thePinnedPromtoolAcceptsTheShippedScrapeConfigs() throws Exception {
		assumeContainers();
		for (Path config : List.of(monolithDir().resolve("prometheus.yml"),
				microservicesDir().resolve("prometheus.yml"))) {
			try (GenericContainer<?> promtool = new GenericContainer<>(pinnedImage("prom/prometheus"))
					.withCopyFileToContainer(MountableFile.forHostPath(config, 0644), "/etc/prometheus/prometheus.yml")
					.withCreateContainerCmdModifier(cmd -> cmd.withEntrypoint("/bin/promtool"))
					.withCommand("check", "config", "/etc/prometheus/prometheus.yml")
					.withStartupCheckStrategy(new OneShotStartupCheckStrategy().withTimeout(Duration.ofMinutes(2)))
					.withLogConsumer(new Slf4jLogConsumer(LOGGER).withPrefix("promtool"))) {
				promtool.start();
			}
		}
	}

	@Test
	public void thePinnedGrafanaProvisionsTheShippedDashboard() throws Exception {
		assumeContainers();
		Path provisioning = monolithDir().resolve("grafana/provisioning");
		try (GenericContainer<?> grafana = new GenericContainer<>(pinnedImage("grafana/grafana"))
				.withEnv("GF_AUTH_ANONYMOUS_ENABLED", "true").withEnv("GF_AUTH_ANONYMOUS_ORG_ROLE", "Viewer")
				.withCopyFileToContainer(MountableFile.forHostPath(provisioning), "/etc/grafana/provisioning")
				.withExposedPorts(3000)
				.waitingFor(Wait.forHttp("/api/health").forPort(3000).forStatusCode(200)
						.withStartupTimeout(Duration.ofMinutes(3)))
				.withLogConsumer(new Slf4jLogConsumer(LOGGER).withPrefix("grafana"))) {
			grafana.start();
			HttpClient http = HttpClient.newHttpClient();
			String base = "http://" + grafana.getHost() + ":" + grafana.getMappedPort(3000);
			String found = "[]";
			// the dashboard provisioner runs right after the HTTP server is up
			for (int i = 0; i < 40 && found.equals("[]"); i++) {
				found = http.send(HttpRequest.newBuilder(URI.create(base + "/api/search?query=Gebo")).build(),
						HttpResponse.BodyHandlers.ofString()).body().strip();
				if (found.equals("[]")) {
					Thread.sleep(500);
				}
			}
			String dashboards = found;
			assertTrue(dashboards.contains("Gebo.ai Overview"),
					() -> "Grafana did not provision the Gebo.ai dashboard: " + dashboards);
			String datasources = http.send(HttpRequest.newBuilder(URI.create(base + "/api/datasources")).build(),
					HttpResponse.BodyHandlers.ofString()).body();
			assertTrue(datasources.contains("gebo-prometheus") && datasources.contains("gebo-tempo"),
					() -> "Grafana did not provision the Prometheus and Tempo datasources: " + datasources);
		}
	}
}
