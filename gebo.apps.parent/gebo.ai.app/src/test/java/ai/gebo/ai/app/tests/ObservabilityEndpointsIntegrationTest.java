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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import com.sun.net.httpserver.HttpServer;

import ai.gebo.monolithic.app.Main;

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
