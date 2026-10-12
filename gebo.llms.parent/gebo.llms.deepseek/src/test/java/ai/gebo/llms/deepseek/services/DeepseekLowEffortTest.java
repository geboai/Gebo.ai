/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.deepseek.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.mock.http.client.reactive.MockClientHttpRequest;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;

import com.sun.net.httpserver.HttpServer;

import reactor.core.publisher.Mono;

/**
 * The effort low the Spring AI client cannot name reaches DeepSeek, on the blocking
 * and on the streamed calls, and nothing else in the body changes.
 */
class DeepseekLowEffortTest {

	static final String BODY = "{\"model\":\"deepseek-v4\",\"thinking\":{\"type\":\"enabled\"},\"reasoning_effort\":\"high\",\"stream\":false}";

	@Test
	void onlyTheHighEffortIsRewritten() {
		assertEquals(BODY.replace("\"high\"", "\"low\""),
				new String(DeepseekLowEffort.rewrite(BODY.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
		byte[] untouched = "{\"model\":\"deepseek-v4\",\"reasoning_effort\":\"max\"}".getBytes(StandardCharsets.UTF_8);
		assertSame(untouched, DeepseekLowEffort.rewrite(untouched));
		assertTrue(DeepseekChatModelConfigurationSupportService.acceptsLowEffort(null), "levels unknown");
		assertTrue(DeepseekChatModelConfigurationSupportService
				.acceptsLowEffort(Map.of(DeepseekModelsLookupService.EFFORT_LEVELS, List.of("low", "high", "max"))));
		assertFalse(DeepseekChatModelConfigurationSupportService
				.acceptsLowEffort(Map.of(DeepseekModelsLookupService.EFFORT_LEVELS, List.of("high", "max"))));
	}

	@Test
	void theBlockingCallsSendLow() throws Exception {
		AtomicReference<String> received = new AtomicReference<>();
		HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
		server.createContext("/chat/completions", exchange -> {
			received.set(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
			byte[] answer = "{}".getBytes(StandardCharsets.UTF_8);
			exchange.sendResponseHeaders(200, answer.length);
			exchange.getResponseBody().write(answer);
			exchange.close();
		});
		server.start();
		try {
			RestClient client = RestClient.builder().requestInterceptor(DeepseekLowEffort.interceptor())
					.baseUrl("http://127.0.0.1:" + server.getAddress().getPort()).build();
			client.post().uri("/chat/completions").contentType(MediaType.APPLICATION_JSON).body(BODY).retrieve()
					.toBodilessEntity();
			assertEquals(BODY.replace("\"high\"", "\"low\""), received.get());
		} finally {
			server.stop(0);
		}
	}

	@Test
	void theStreamedCallsSendLow() {
		AtomicReference<String> received = new AtomicReference<>();
		ExchangeStrategies strategies = ExchangeStrategies.withDefaults();
		BodyInserter.Context context = new BodyInserter.Context() {
			@Override
			public List<HttpMessageWriter<?>> messageWriters() {
				return strategies.messageWriters();
			}

			@Override
			public Optional<ServerHttpRequest> serverRequest() {
				return Optional.empty();
			}

			@Override
			public Map<String, Object> hints() {
				return Map.of();
			}
		};
		ExchangeFunction capture = request -> {
			MockClientHttpRequest out = new MockClientHttpRequest(request.method(), request.url());
			return request.body().insert(out, context)
					.then(Mono.defer(() -> out.getBodyAsString().doOnNext(received::set)))
					.then(Mono.just(ClientResponse.create(HttpStatus.OK).build()));
		};
		ClientRequest request = ClientRequest.create(HttpMethod.POST, URI.create("http://localhost/chat/completions"))
				.body(BodyInserters.fromValue(BODY)).build();
		DeepseekLowEffort.filter().filter(request, capture).block();
		assertEquals(BODY.replace("\"high\"", "\"low\""), received.get());
	}
}
