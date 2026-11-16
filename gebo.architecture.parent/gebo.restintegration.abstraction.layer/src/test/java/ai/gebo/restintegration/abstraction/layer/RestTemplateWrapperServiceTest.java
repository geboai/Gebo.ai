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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

/**
 * Pins the wrapper the integrations call through, now on Spring's RestClient: the
 * status mapped to Gebo's exceptions, the headers and body of an HttpEntity sent, the
 * url variables expanded, a redirection followed, a download read as it comes with its
 * headers sent.
 */
class RestTemplateWrapperServiceTest {

	public static class Echo {
		public String method;
		public String path;
		public String authorization;
		public String body;
	}

	HttpServer server;
	String base;
	final List<String> requests = new CopyOnWriteArrayList<>();

	@BeforeEach
	void start() throws IOException {
		server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
		server.createContext("/echo", exchange -> {
			String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
			String auth = exchange.getRequestHeaders().getFirst("Authorization");
			requests.add(exchange.getRequestMethod() + " " + exchange.getRequestURI());
			send(exchange, 200, "{\"method\":\"" + exchange.getRequestMethod() + "\",\"path\":\""
					+ exchange.getRequestURI() + "\",\"authorization\":" + (auth != null ? "\"" + auth + "\"" : "null")
					+ ",\"body\":" + (body.isEmpty() ? "null" : "\"" + body.replace("\"", "\\\"") + "\"") + "}");
		});
		server.createContext("/status/", exchange -> send(exchange,
				Integer.parseInt(exchange.getRequestURI().getPath().substring("/status/".length())), "{}"));
		server.createContext("/moved", exchange -> {
			exchange.getResponseHeaders().add("Location", base + "/echo?moved=true");
			exchange.sendResponseHeaders(302, -1);
			exchange.close();
		});
		server.createContext("/file", exchange -> {
			if (!"Bearer sk-test".equals(exchange.getRequestHeaders().getFirst("Authorization"))) {
				send(exchange, 401, "{}");
				return;
			}
			send(exchange, 200, "the file content");
		});
		server.start();
		base = "http://127.0.0.1:" + server.getAddress().getPort();
	}

	static void send(HttpExchange exchange, int status, String body) throws IOException {
		byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
		exchange.getResponseHeaders().add("Content-Type", "application/json");
		exchange.sendResponseHeaders(status, bytes.length);
		try (OutputStream out = exchange.getResponseBody()) {
			out.write(bytes);
		}
	}

	@AfterEach
	void stop() {
		server.stop(0);
	}

	final RestTemplateWrapperService wrapper = new RestTemplateWrapperService();

	static HttpHeaders bearer() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("sk-test");
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	@Test
	void anExchangeSendsTheEntityHeadersAndBody() throws Exception {
		Echo echo = wrapper.exchangeAndReturn(base + "/echo", HttpMethod.POST,
				new HttpEntity<>(Map.of("q", "x"), bearer()), Echo.class);

		assertEquals("POST", echo.method);
		assertEquals("Bearer sk-test", echo.authorization);
		assertEquals("{\"q\":\"x\"}", echo.body);
	}

	@Test
	void theUrlVariablesAreExpandedAndEncodedAsRestTemplateDid() throws Exception {
		ResponseEntity<Echo> got = wrapper.getForEntity(base + "/echo?name={name}", Echo.class, "a b");
		assertEquals("/echo?name=a%20b", got.getBody().path);

		ResponseEntity<Echo> mapped = wrapper.getForEntity(base + "/echo?id={id}", Echo.class, Map.of("id", "42"));
		assertEquals("/echo?id=42", mapped.getBody().path);
	}

	@Test
	void aPostOfAnEntitySendsItsHeaders() throws Exception {
		ResponseEntity<Echo> posted = wrapper.postForEntity(URI.create(base + "/echo"),
				new HttpEntity<>("{\"a\":1}", bearer()), Echo.class);

		assertEquals("Bearer sk-test", posted.getBody().authorization);
		assertEquals("{\"a\":1}", posted.getBody().body);
	}

	@Test
	void theStatusIsMappedToGebosExceptions() {
		assertThrows(GeboNotFoundException.class,
				() -> wrapper.exchange(base + "/status/404", HttpMethod.GET, null, String.class));
		assertThrows(GeboInvalidAccessException.class,
				() -> wrapper.getForEntity(URI.create(base + "/status/401"), String.class));
		assertThrows(GeboInvalidAccessException.class,
				() -> wrapper.postForEntity(URI.create(base + "/status/403"), "x", String.class));
		assertThrows(GeboRemoteBackendErrorException.class,
				() -> wrapper.exchange(base + "/status/503", HttpMethod.GET, null, String.class),
				"a 5xx says the remote system is in trouble (RestTemplate's wrapper reported it as a generic error)");
		GeboRestIntegrationException other = assertThrows(GeboRestIntegrationException.class,
				() -> wrapper.exchange(base + "/status/409", HttpMethod.GET, null, String.class));
		assertEquals(GeboRestIntegrationException.class, other.getClass());
	}

	@Test
	void aRedirectionIsFollowed() throws Exception {
		Echo echo = wrapper.exchangeAndReturn(base + "/moved", HttpMethod.GET, null, Echo.class);

		assertTrue(echo.path.startsWith("/echo?moved=true"), echo.path);
	}

	@Test
	void aDownloadIsReadAsItComesWithItsHeaders() throws Exception {
		String content = wrapper.read(base + "/file", HttpMethod.GET, bearer(),
				response -> StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8));
		assertEquals("the file content", content);

		assertThrows(GeboInvalidAccessException.class,
				() -> wrapper.read(base + "/file", HttpMethod.GET, null, response -> "never read"));
	}

	@Test
	void aWrapperWithTimeoutsCallsTheSame() throws Exception {
		RestTemplateWrapperService timed = RestTemplateWrapperService.withTimeouts(Duration.ofSeconds(2),
				Duration.ofSeconds(2));
		Echo echo = timed.exchangeAndReturn(base + "/echo", HttpMethod.GET, null, Echo.class);

		assertEquals("GET", echo.method);
		assertNull(echo.authorization);
	}
}
