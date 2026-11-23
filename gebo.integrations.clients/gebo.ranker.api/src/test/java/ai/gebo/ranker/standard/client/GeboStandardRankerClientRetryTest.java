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
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.core.retry.RetryPolicy;
import org.springframework.core.retry.RetryTemplate;
import org.springframework.web.client.HttpServerErrorException;

import com.sun.net.httpserver.HttpServer;

import ai.gebo.ranker.model.RankingInput;
import ai.gebo.ranker.model.RankingOutput;

/**
 * Pins the retry the rerank client does with Spring Framework's own retry template,
 * which replaced Spring Retry: a failing call is tried again, and when the attempts run
 * out the caller gets the last failure as it was, not wrapped.
 */
class GeboStandardRankerClientRetryTest {

	HttpServer server;
	final AtomicInteger calls = new AtomicInteger();

	/** A rerank server that answers 503 to the first {@code failures} calls. */
	void startRerankServer(int failures) throws IOException {
		server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
		server.createContext("/", exchange -> {
			exchange.getRequestBody().readAllBytes();
			if (calls.incrementAndGet() <= failures) {
				exchange.sendResponseHeaders(503, -1);
				exchange.close();
				return;
			}
			byte[] body = "{\"results\":[{\"index\":0,\"relevance_score\":0.7}]}".getBytes(StandardCharsets.UTF_8);
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
		if (server != null) {
			server.stop(0);
		}
	}

	GeboStandardRankerClient client(int retries) {
		RetryTemplate retryTemplate = new RetryTemplate(
				RetryPolicy.builder().maxRetries(retries).delay(Duration.ofMillis(1)).build());
		return new GeboStandardRankerClient(null, "http://127.0.0.1:" + server.getAddress().getPort() + "/v1/rerank",
				"lexical", null, retryTemplate, null, null, null, null);
	}

	static RankingInput input() {
		return new RankingInput("which fragment", List.of(new Document("only fragment")), 1);
	}

	@Test
	void aFailingCallIsTriedAgain() throws IOException {
		startRerankServer(2);

		RankingOutput output = client(2).call(input());

		assertEquals(3, calls.get(), "two failures, then the call that answers");
		assertEquals("only fragment", output.getRanked().get(0).getDocument().getText());
	}

	@Test
	void whenTheAttemptsRunOutTheLastFailureIsThrownAsItWas() throws IOException {
		startRerankServer(Integer.MAX_VALUE);

		HttpServerErrorException failure = assertThrows(HttpServerErrorException.class, () -> client(2).call(input()));

		assertEquals(503, failure.getStatusCode().value());
		assertEquals(3, calls.get(), "the first call and two retries");
	}
}
