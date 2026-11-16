/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.restintegration.abstraction.layer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import com.sun.net.httpserver.HttpServer;

import io.micrometer.common.KeyValue;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import io.micrometer.observation.ObservationRegistry;

/**
 * Pins what the HTTP clients Gebo builds itself record of their calls: an
 * {@code http.client.requests} observation per call; urls recorded without their query
 * (where integrations put keys) nor user info; the uri tag from a url template with
 * variables only for the integrations' calls, from the path template for the clients
 * calling paths of their own; the wrappers derived from the shared one observed as it
 * is; nothing done where there is no registry.
 */
class GeboHttpClientObservationsTest {

	HttpServer server;
	String base;
	final List<Observation.Context> observed = new CopyOnWriteArrayList<>();
	ObservationRegistry registry;

	@BeforeEach
	void start() throws IOException {
		server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
		server.createContext("/", exchange -> {
			final int status = exchange.getRequestURI().getPath().startsWith("/missing") ? 404 : 200;
			final byte[] bytes = "{\"ok\":true}".getBytes(StandardCharsets.UTF_8);
			exchange.getResponseHeaders().add("Content-Type", "application/json");
			exchange.sendResponseHeaders(status, bytes.length);
			try (OutputStream out = exchange.getResponseBody()) {
				out.write(bytes);
			}
		});
		server.start();
		base = "http://127.0.0.1:" + server.getAddress().getPort();
		registry = ObservationRegistry.create();
		registry.observationConfig().observationHandler(new ObservationHandler<Observation.Context>() {
			@Override
			public boolean supportsContext(Observation.Context context) {
				return true;
			}

			@Override
			public void onStop(Observation.Context context) {
				observed.add(context);
			}
		});
	}

	@AfterEach
	void stop() {
		server.stop(0);
	}

	static String low(Observation.Context context, String key) {
		KeyValue value = context.getLowCardinalityKeyValue(key);
		return value != null ? value.getValue() : null;
	}

	static String high(Observation.Context context, String key) {
		KeyValue value = context.getHighCardinalityKeyValue(key);
		return value != null ? value.getValue() : null;
	}

	RestTemplateWrapperService observedWrapper() {
		return new RestTemplateWrapperService(RestClient.builder().requestFactory(new SimpleClientHttpRequestFactory()),
				registry);
	}

	@Test
	void anIntegrationCallWithATemplateIsMeasuredByItsPathAndRecordedWithoutItsQuery() throws Exception {
		observedWrapper().getForEntity(base + "/rest/api/content/{id}?key={key}", String.class, "42", "s3cret");

		assertEquals(1, observed.size());
		Observation.Context call = observed.get(0);
		assertEquals("http.client.requests", call.getName());
		assertEquals("/rest/api/content/{id}", low(call, "uri"), "the template's path, never its query");
		assertEquals("GET", low(call, "method"));
		assertEquals("200", low(call, "status"));
		assertEquals(base + "/rest/api/content/42", high(call, "http.url"), "no query, no key");
	}

	@Test
	void anIntegrationCallOnAConcatenatedUrlIsNotMeasuredByIt() {
		// a url built by concatenation would make one measure per id: "none"
		assertThrows(GeboNotFoundException.class, () -> observedWrapper()
				.exchange(base + "/missing/12345?api_key=s3cret", HttpMethod.GET, null, String.class));

		Observation.Context call = observed.get(0);
		assertEquals("none", low(call, "uri"));
		assertEquals("404", low(call, "status"));
		assertEquals(base + "/missing/12345", high(call, "http.url"));
		assertFalse(call.getAllKeyValues().stream().anyMatch(k -> k.getValue().contains("s3cret")),
				"the key appears nowhere: " + call.getAllKeyValues());
	}

	@Test
	void aWrapperDerivedWithTimeoutsIsObservedAsTheSharedOne() throws Exception {
		observedWrapper().timingOut(Duration.ofSeconds(2), Duration.ofSeconds(2)).getForEntity(URI.create(base + "/a"),
				String.class);

		assertEquals(1, observed.size());
		assertEquals("none", low(observed.get(0), "uri"), "a URI carries no template");
	}

	@Test
	void theGeneratedClientsRestTemplateIsObserved() {
		RestTemplate restTemplate = GeboHttpClientObservations.observed(new RestTemplate(), registry);

		restTemplate.getForObject(base + "/rest/api/3/search?jql=project%3DKEY", String.class);

		Observation.Context call = observed.get(0);
		assertEquals("none", low(call, "uri"));
		assertEquals(base + "/rest/api/3/search", high(call, "http.url"), "the search query stays out");
	}

	@Test
	void aClientCallingPathsOfItsOwnIsMeasuredByThePath() {
		RestClient client = GeboHttpClientObservations
				.observed(RestClient.builder().baseUrl(base), registry, GeboHttpClientObservations.PATH_TEMPLATES)
				.build();

		client.post().uri("/v1/chat/completions").body("{}").retrieve().toBodilessEntity();

		assertEquals("/v1/chat/completions", low(observed.get(0), "uri"));
	}

	@Test
	void withoutARegistryNothingChanges() throws Exception {
		RestClient.Builder builder = RestClient.builder();
		assertSame(builder, GeboHttpClientObservations.observed(builder, ObservationRegistry.NOOP));
		assertSame(builder, GeboHttpClientObservations.observed(builder, null));
		assertSame(ObservationRegistry.NOOP, GeboHttpClientObservations.registryOf(null));

		new RestTemplateWrapperService().getForEntity(URI.create(base + "/a"), String.class);

		assertTrue(observed.isEmpty());
	}

	@Test
	void aRecordedUrlHasNoUserInfoNorQuery() {
		assertEquals("https://host:8443/p/a%20b",
				GeboHttpClientObservations.recordable(URI.create("https://user:pw@host:8443/p/a%20b?key=x#f")));
		assertEquals("/only/path", GeboHttpClientObservations.recordable(URI.create("/only/path?x=1")));
		assertEquals("/p", GeboHttpClientObservations.withoutQuery("/p?key={key}"));
	}
}
