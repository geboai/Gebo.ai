/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.microservices.documents.access;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;

/**
 * The service reading the documents asks the one holding the models what it
 * reads of pictures and recordings and gives them to it; a JDK server stands
 * in for that service's IngestionMediaController.
 */
class GMicroserviceMediaTextExtractorClientTest {
	private HttpServer server;
	private String capabilities = "{\"describesPictures\":true,\"transcribes\":false,"
			+ "\"maxDescribedPagesPerDocument\":30,\"maxFileBytes\":1000}";
	private final List<String> calls = new ArrayList<>();

	@BeforeEach
	void start() throws IOException {
		server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
		server.createContext("/api/users/IngestionMediaController/getIngestionMediaCapabilities", exchange -> {
			calls.add("capabilities " + exchange.getRequestHeaders().getFirst("Authorization"));
			respond(exchange, capabilities);
		});
		server.createContext("/api/users/IngestionMediaController/describePicture", exchange -> {
			calls.add("describe " + exchange.getRequestURI().getQuery() + " "
					+ exchange.getRequestBody().readAllBytes().length);
			respond(exchange, "{\"text\":\"A bar chart.\"}");
		});
		server.createContext("/api/users/IngestionMediaController/transcribe", exchange -> {
			calls.add("transcribe " + exchange.getRequestBody().readAllBytes().length);
			respond(exchange, "{\"text\":\"Good morning.\"}");
		});
		server.start();
	}

	@AfterEach
	void stop() {
		server.stop(0);
	}

	private static void respond(HttpExchange exchange, String json) throws IOException {
		final byte[] body = json.getBytes(StandardCharsets.UTF_8);
		exchange.getResponseHeaders().add("Content-Type", "application/json");
		exchange.sendResponseHeaders(200, body.length);
		try (OutputStream out = exchange.getResponseBody()) {
			out.write(body);
		}
	}

	private GMicroserviceMediaTextExtractorClient client(String baseUrl) {
		return new GMicroserviceMediaTextExtractorClient(WebClient.builder().build(),
				GeboMicroserviceUrlResolver.direct(GeboMicroservicesTopology.defaults(), Map.of("brain.gebo.ai", baseUrl)),
				() -> "system-token", "brain.gebo.ai");
	}

	@Test
	void whatTheModelsServiceReadsIsAskedOnceAndThePictureIsGivenToIt() throws IOException {
		final GMicroserviceMediaTextExtractorClient client = client(
				"http://127.0.0.1:" + server.getAddress().getPort());
		assertTrue(client.describesPictures());
		assertFalse(client.transcribes());
		assertEquals(30, client.maxDescribedPagesPerDocument());
		assertEquals(1000, client.maxFileBytes());
		assertEquals(List.of("capabilities Bearer system-token"), calls, "asked once, with the caller's token");

		assertEquals("A bar chart.", client.describePicture("sales page 2", "image/png", new byte[] { 1, 2, 3 }));
		assertEquals("describe name=sales page 2&mimeType=image/png 3", calls.get(1));
		assertEquals("Good morning.", client.transcribe("talk.mp3", "audio/mpeg", new ByteArrayInputStream(new byte[10])));
		// a recording larger than the files a model is given is not sent
		assertNull(client.transcribe("long.mp3", "audio/mpeg", new ByteArrayInputStream(new byte[1001])));
		assertEquals(3, calls.size());
	}

	@Test
	void whileTheModelsServiceCannotBeReachedNothingIsRead() {
		final GMicroserviceMediaTextExtractorClient client = client("http://127.0.0.1:1");
		assertFalse(client.describesPictures());
		assertFalse(client.transcribes());
		assertEquals(0, client.maxFileBytes());
	}
}
