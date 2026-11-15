/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ranker.standard.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.retry.support.RetryTemplate;

import com.sun.net.httpserver.HttpServer;

import ai.gebo.ranker.model.RankingInput;
import ai.gebo.ranker.model.RankingOutput;

/**
 * Pins how the standard rerank client authenticates: with its key as a bearer token,
 * and without any Authorization header when the provider declares optional
 * authentication and no key is configured - it used to refuse to be built at all.
 */
class GeboStandardRankerClientAuthenticationTest {

	HttpServer server;
	final AtomicReference<String> authorization = new AtomicReference<>();
	final AtomicReference<String> path = new AtomicReference<>();

	@BeforeEach
	void startRerankServer() throws IOException {
		server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
		server.createContext("/", exchange -> {
			authorization.set(exchange.getRequestHeaders().getFirst("Authorization"));
			path.set(exchange.getRequestURI().getPath());
			byte[] body = "{\"results\":[{\"index\":1,\"relevance_score\":0.9},{\"index\":0,\"relevance_score\":0.2}]}"
					.getBytes(StandardCharsets.UTF_8);
			exchange.getResponseHeaders().add("Content-Type", "application/json");
			exchange.sendResponseHeaders(200, body.length);
			try (OutputStream out = exchange.getResponseBody()) {
				out.write(body);
			}
		});
		server.start();
	}

	@AfterEach
	void stopRerankServer() {
		server.stop(0);
	}

	GeboStandardRankerClient client(String apiKey) {
		return new GeboStandardRankerClient(apiKey,
				"http://127.0.0.1:" + server.getAddress().getPort() + "/v1/rerank", "lexical", null,
				RetryTemplate.builder().maxAttempts(1).build(), null, null, null, null);
	}

	static RankingInput input() {
		return new RankingInput("which fragment", List.of(new Document("first fragment"), new Document("second fragment")),
				2);
	}

	@Test
	void withoutAKeyNoAuthorizationHeaderIsSent() {
		RankingOutput output = client(null).call(input());

		assertNull(authorization.get());
		assertEquals("/v1/rerank", path.get());
		assertEquals("second fragment", output.getRanked().get(0).getDocument().getText());
	}

	@Test
	void aBlankKeyIsNoKey() {
		client("  ").call(input());

		assertNull(authorization.get());
	}

	@Test
	void aKeyIsSentAsABearerToken() {
		client("rerank-key").call(input());

		assertEquals("Bearer rerank-key", authorization.get());
	}
}
