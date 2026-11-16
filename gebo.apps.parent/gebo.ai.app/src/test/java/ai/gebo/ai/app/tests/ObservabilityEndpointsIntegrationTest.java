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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;

import com.sun.net.httpserver.HttpServer;

import ai.gebo.llms.abstraction.layer.services.impl.GDefaultLlmsServiceClientsProviderImpl;
import ai.gebo.monolithic.app.Main;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import io.micrometer.observation.ObservationView;

/**
 * What the observability deployments rely on, on the real application:
 *
 * <ul>
 * <li>with a separate management port, Prometheus reads {@code /actuator/prometheus}
 * there without a login, and the health probe {@code /actuator/health} too;</li>
 * <li>on the application port the same endpoints still need an authenticated user,
 * and on the management port every other actuator endpoint ({@code metrics}) does
 * as well;</li>
 * <li>with an OTLP endpoint configured, the spans of the requests leave the
 * application: Spring Boot 4 creates the tracer and the OTLP exporter only with
 * its own tracing modules on the classpath, which the telemetry module carries (a
 * missing module exports nothing, silently).</li>
 * </ul>
 *
 * <p>
 * The OTLP collector is a plain JDK HTTP server counting the trace exports it
 * receives, so the test needs no container.
 * </p>
 */
// The test application.yml carries no management section: the exposure and the
// Prometheus export the shipped application.yml sets are repeated here.
@SpringBootTest(classes = Main.class, webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
		"management.server.port=0", "management.endpoints.web.exposure.include=health,prometheus,metrics",
		"management.prometheus.metrics.export.enabled=true", "management.tracing.sampling.probability=1.0" })
public class ObservabilityEndpointsIntegrationTest extends AbstractBaseIntegrationTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ObservabilityEndpointsIntegrationTest.class);

	private static final AtomicInteger traceExports = new AtomicInteger();
	private static final HttpServer collector = startCollector();

	private final HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();

	@LocalServerPort
	private int serverPort;

	@LocalManagementPort
	private int managementPort;

	private static HttpServer startCollector() {
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
			server.createContext("/v1/traces", exchange -> {
				try (InputStream body = exchange.getRequestBody()) {
					body.readAllBytes();
				}
				traceExports.incrementAndGet();
				// An empty ExportTraceServiceResponse: a full success for the exporter.
				exchange.getResponseHeaders().add("Content-Type", "application/x-protobuf");
				exchange.sendResponseHeaders(200, -1);
				exchange.close();
			});
			server.start();
			return server;
		} catch (IOException e) {
			throw new IllegalStateException("Cannot start the test OTLP collector", e);
		}
	}

	@DynamicPropertySource
	static void otlpEndpoint(DynamicPropertyRegistry registry) {
		registry.add("management.opentelemetry.tracing.export.otlp.endpoint",
				() -> "http://127.0.0.1:" + collector.getAddress().getPort() + "/v1/traces");
	}

	@AfterAll
	static void stopCollector() {
		collector.stop(0);
	}

	private HttpResponse<String> get(int port, String path) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + path))
				.timeout(Duration.ofSeconds(20)).GET().build();
		HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GET :" + port + path + " -> " + response.statusCode());
		}
		return response;
	}

	@Test
	public void prometheusAndHealthAnswerAnonymouslyOnTheManagementPort() throws Exception {
		assertTrue(managementPort != serverPort, "The management server must run on its own port");
		// the HTTP server timer exists once a request has been served on the application port
		get(serverPort, "/ui/");
		HttpResponse<String> scrape = get(managementPort, "/actuator/prometheus");
		assertEquals(200, scrape.statusCode(), "Prometheus scrapes without a login on the management port");
		assertTrue(scrape.body().contains("jvm_memory_used_bytes"),
				"The scrape returns the Prometheus exposition format with the JVM metrics");
		assertTrue(scrape.body().contains("http_server_requests_seconds"),
				"The HTTP server metrics the Grafana overview dashboard plots are exposed");
		assertEquals(200, get(managementPort, "/actuator/health").statusCode(),
				"The health probe answers without a login on the management port");
	}

	@Test
	public void theActuatorStaysBehindAuthenticationElsewhere() throws Exception {
		assertEquals(401, get(serverPort, "/actuator/prometheus").statusCode(),
				"On the application port the scrape needs an authenticated user");
		assertEquals(401, get(serverPort, "/actuator/health").statusCode(),
				"On the application port the health endpoint needs an authenticated user");
		assertEquals(401, get(managementPort, "/actuator/metrics").statusCode(),
				"On the management port only health and prometheus are anonymous");
	}

	/**
	 * Every observation started while the test runs, as "name" or
	 * "name < parent name".
	 */
	@TestConfiguration
	static class StartedObservations {
		static final List<String> started = new CopyOnWriteArrayList<>();

		@Bean
		ObservationHandler<Observation.Context> startedObservationsRecorder() {
			return new ObservationHandler<Observation.Context>() {
				@Override
				public boolean supportsContext(Observation.Context context) {
					return true;
				}

				@Override
				public void onStart(Observation.Context context) {
					ObservationView parent = context.getParentObservation();
					started.add(parent == null ? context.getName()
							: context.getName() + " < " + parent.getContextView().getName());
				}
			};
		}
	}

	private static List<String> securityObservationsStartedBy(Callable<?> request) throws Exception {
		StartedObservations.started.clear();
		request.call();
		return StartedObservations.started.stream().filter(s -> s.startsWith("spring.security.")).toList();
	}

	@Test
	public void theScrapeLeavesNoTraceOfItsOwn() throws Exception {
		// The management port has no request observation, so a security observation
		// there would be a root span: a trace made of nothing else, twice per scrape.
		List<String> scrape = securityObservationsStartedBy(() -> get(managementPort, "/actuator/prometheus"));
		assertTrue(scrape.isEmpty(), "A scrape on the management port starts no security observation, got " + scrape);
		List<String> health = securityObservationsStartedBy(() -> get(managementPort, "/actuator/health"));
		assertTrue(health.isEmpty(), "A health probe starts no security observation, got " + health);

		// On the application port the security spans stay, inside the request's span.
		List<String> page = securityObservationsStartedBy(() -> get(serverPort, "/ui/"));
		assertTrue(page.contains("spring.security.filterchains < http.server.requests"),
				"A request on the application port keeps its security spans inside its trace, got " + page);
		assertTrue(page.stream().allMatch(s -> s.contains(" < ")),
				"No security observation on the application port is a root, got " + page);
	}

	@Autowired
	private RestTemplateWrapperService integrationsClient;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private GDefaultLlmsServiceClientsProviderImpl llmsClients;

	/**
	 * The HTTP clients Gebo builds itself are observed (GeboHttpClientObservations): the
	 * integrations' shared client, the RestTemplate bean, the LLM providers' clients.
	 * Each call sends the trace to the server called and is measured, the uri tag never
	 * an id nor a query, the key carried in a query nowhere in the scrape.
	 */
	@Test
	public void theCallsOfGebosOwnHttpClientsAreTracedAndMeasured() throws Exception {
		final List<String> traceparents = new CopyOnWriteArrayList<>();
		final HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
		server.createContext("/", exchange -> {
			traceparents.add(String.valueOf(exchange.getRequestHeaders().getFirst("traceparent")));
			final byte[] body = "{}".getBytes(StandardCharsets.UTF_8);
			exchange.getResponseHeaders().add("Content-Type", "application/json");
			exchange.sendResponseHeaders(200, body.length);
			try (OutputStream out = exchange.getResponseBody()) {
				out.write(body);
			}
		});
		server.start();
		try {
			final String base = "http://127.0.0.1:" + server.getAddress().getPort();
			integrationsClient.getForEntity(base + "/items/{id}?key={key}", String.class, "7", "s3cret-key");
			integrationsClient.timingOut(Duration.ofSeconds(5), Duration.ofSeconds(5))
					.getForEntity(base + "/plain/42?key=s3cret-key", String.class);
			// the bean reads JSON with its Jackson converter first: an object, not a String
			restTemplate.getForObject(base + "/bean/call", java.util.Map.class);
			llmsClients.getRestClientBuilder().baseUrl(base).build().get().uri("/v1/models").retrieve()
					.toBodilessEntity();
		} finally {
			server.stop(0);
		}

		assertEquals(4, traceparents.size());
		// W3C trace context: version, trace id, span id, flags (sampled, and random in level 2)
		assertTrue(traceparents.stream().allMatch(t -> t.matches("00-[0-9a-f]{32}-[0-9a-f]{16}-[0-9a-f]{2}")),
				"Every call sends its trace: " + traceparents);
		final String scrape = get(managementPort, "/actuator/prometheus").body();
		assertTrue(scrape.contains("http_client_requests_seconds_count"), "The client calls are measured");
		assertTrue(scrape.contains("uri=\"/items/{id}\""), "A template's path names the integration call");
		assertTrue(scrape.contains("uri=\"/v1/models\""), "A provider's own path names its call");
		assertTrue(scrape.contains("uri=\"none\""), "A url without template is not split by its ids");
		assertFalse(scrape.contains("s3cret-key"), "A key carried in a query never reaches the measures");
	}

	@Test
	public void theSpansOfTheRequestsAreExportedOverOtlp() throws Exception {
		int before = traceExports.get();
		for (int i = 0; i < 3; i++) {
			get(serverPort, "/ui/");
		}
		// The batch span processor exports every few seconds.
		long deadline = System.currentTimeMillis() + 30_000;
		while (traceExports.get() == before && System.currentTimeMillis() < deadline) {
			Thread.sleep(250);
		}
		assertTrue(traceExports.get() > before,
				"No trace export reached the OTLP endpoint: the tracer or its OTLP exporter is missing");
	}
}
