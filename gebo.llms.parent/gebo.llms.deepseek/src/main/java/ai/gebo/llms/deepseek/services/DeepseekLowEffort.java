/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.deepseek.services;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.reactive.ClientHttpRequestDecorator;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import reactor.core.publisher.Mono;

/**
 * Sends DeepSeek the reasoning effort {@code low}, which Spring AI's client cannot
 * name: its {@code ReasoningEffort} knows only HIGH and MAX. DeepSeek takes low as a
 * level of its own (api-docs.deepseek.com/guides/thinking_mode, 2026-10-11: low stays
 * low, medium and xhigh run as high, the default is high). The request is built with
 * HIGH, and its {@code "reasoning_effort":"high"} is rewritten to low on the way out,
 * by the RestClient interceptor for the blocking calls and by the WebClient filter for
 * the streamed ones. Nothing else in the body changes.
 */
final class DeepseekLowEffort {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeepseekLowEffort.class);
	/** The effort the client writes, as Jackson writes it (no spaces) or reformatted. */
	private static final Pattern HIGH_EFFORT = Pattern.compile("\"reasoning_effort\"\\s*:\\s*\"high\"");
	static final String LOW = "low";

	private DeepseekLowEffort() {
	}

	/** The body with its reasoning effort high turned into low; the same bytes otherwise. */
	static byte[] rewrite(byte[] body) {
		if (body == null || body.length == 0)
			return body;
		String json = new String(body, StandardCharsets.UTF_8);
		String rewritten = HIGH_EFFORT.matcher(json).replaceFirst("\"reasoning_effort\":\"" + LOW + "\"");
		if (rewritten.equals(json))
			return body;
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Deepseek request sent with reasoning_effort low: {}", rewritten);
		}
		return rewritten.getBytes(StandardCharsets.UTF_8);
	}

	/** The blocking calls' rewrite. */
	static ClientHttpRequestInterceptor interceptor() {
		return (request, body, execution) -> {
			byte[] out = rewrite(body);
			if (out != body) {
				request.getHeaders().setContentLength(out.length);
			}
			return execution.execute(request, out);
		};
	}

	/** The streamed calls' rewrite: the body is joined, rewritten and written in one piece. */
	static ExchangeFilterFunction filter() {
		return (request, next) -> next.exchange(ClientRequest.from(request)
				.body((outputMessage, context) -> request.body().insert(new ClientHttpRequestDecorator(outputMessage) {
					@Override
					public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
						return DataBufferUtils.join(body).flatMap(buffer -> {
							byte[] bytes = new byte[buffer.readableByteCount()];
							buffer.read(bytes);
							DataBufferUtils.release(buffer);
							byte[] out = rewrite(bytes);
							getHeaders().setContentLength(out.length);
							return super.writeWith(Mono.just(outputMessage.bufferFactory().wrap(out)));
						});
					}
				}, context)).build());
	}
}
