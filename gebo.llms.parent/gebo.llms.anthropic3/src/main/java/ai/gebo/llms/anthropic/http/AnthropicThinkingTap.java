/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.anthropic.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

/**
 * Reads Claude's thinking off the HTTP stream as it comes.
 * <p>
 * Spring AI's AnthropicChatModel (2.0.1, and 2.1.0-M1) keeps the text of each
 * {@code thinking_delta} event and streams only a text-less chunk marked
 * {@code thinking=true}: the text comes all at once on the chunk ending the turn, after
 * the turn's answer. This interceptor, on the Anthropic OkHttp client, copies the text of
 * the {@code thinking_delta} events of a streamed request marked with {@link #HEADER} to
 * the {@link Listener} that marked it, as the SDK reads them: the listener holds it
 * before the SDK hands Spring AI the event, so the reading of the {@code thinking=true}
 * chunk finds it. The header is taken off the request sent to Anthropic.
 * <p>
 * It also logs, at DEBUG, the timeouts the SDK sends a request with
 * ({@code X-Stainless-Timeout}, {@code X-Stainless-Read-Timeout}) and how long a stream
 * lasted and how it ended.
 */
public final class AnthropicThinkingTap implements Interceptor {
	private static final Logger LOGGER = LoggerFactory.getLogger(AnthropicThinkingTap.class);
	/** The request header naming the listener of the request's thinking. */
	public static final String HEADER = "X-Gebo-Thinking-Tap";
	private static final ConcurrentHashMap<String, Listener> LISTENERS = new ConcurrentHashMap<>();
	private static final ObjectMapper JSON = new ObjectMapper();
	/** The one interceptor of the Anthropic clients */
	public static final AnthropicThinkingTap INSTANCE = new AnthropicThinkingTap();

	private AnthropicThinkingTap() {
	}

	/** The thinking a request's stream gave, not taken yet. */
	public static final class Listener implements AutoCloseable {
		private final String id;
		private final StringBuilder pending = new StringBuilder();
		private long received = 0;

		private Listener(String id) {
			this.id = id;
		}

		/** The value of {@link AnthropicThinkingTap#HEADER} marking the request. */
		public String id() {
			return id;
		}

		synchronized void add(String text) {
			pending.append(text);
			received += text.length();
		}

		/** The thinking given since it was last taken. */
		public synchronized String take() {
			final String out = pending.toString();
			pending.setLength(0);
			return out;
		}

		/** All the thinking the stream gave so far, taken or not. */
		public synchronized long received() {
			return received;
		}

		@Override
		public void close() {
			LISTENERS.remove(id, this);
		}
	}

	/** A new listener: its id marks the request whose thinking it receives. */
	public static Listener listen() {
		final Listener listener = new Listener(UUID.randomUUID().toString());
		LISTENERS.put(listener.id, listener);
		return listener;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		final Request request = chain.request();
		final String id = request.header(HEADER);
		final Request sent = id != null ? request.newBuilder().removeHeader(HEADER).build() : request;
		final long start = System.nanoTime();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Claude request " + sent.method() + " " + sent.url().encodedPath() + " X-Stainless-Timeout="
					+ sent.header("X-Stainless-Timeout") + " X-Stainless-Read-Timeout="
					+ sent.header("X-Stainless-Read-Timeout") + (id != null ? " thinking read off the stream" : ""));
		}
		final Response response = chain.proceed(sent);
		final ResponseBody body = response.body();
		if (body == null || !isEventStream(body.contentType())) {
			return response;
		}
		final Listener listener = id != null ? LISTENERS.get(id) : null;
		return response.newBuilder().body(new TappedBody(body, listener, start)).build();
	}

	private static boolean isEventStream(MediaType type) {
		return type != null && "text".equals(type.type()) && "event-stream".equals(type.subtype());
	}

	/** The thinking text of an SSE data line, null when it carries none. */
	static String thinkingOf(String data) {
		if (data == null || !data.contains("thinking_delta")) {
			return null;
		}
		try {
			final JsonNode event = JSON.readTree(data);
			final JsonNode delta = event.path("delta");
			if ("content_block_delta".equals(event.path("type").asText())
					&& "thinking_delta".equals(delta.path("type").asText()) && delta.path("thinking").isTextual()) {
				return delta.path("thinking").asText();
			}
		} catch (IOException | RuntimeException e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("An event of the Claude stream could not be read for its thinking: " + e.getMessage());
			}
		}
		return null;
	}

	/** The stream's body, its SSE lines read for thinking as the SDK reads them. */
	private static final class TappedBody extends ResponseBody {
		private final ResponseBody body;
		private final BufferedSource source;

		TappedBody(ResponseBody body, Listener listener, long start) {
			this.body = body;
			this.source = Okio.buffer(new TappedSource(body.source(), listener, start));
		}

		@Override
		public MediaType contentType() {
			return body.contentType();
		}

		@Override
		public long contentLength() {
			return body.contentLength();
		}

		@Override
		public BufferedSource source() {
			return source;
		}
	}

	private static final class TappedSource extends ForwardingSource {
		private final Listener listener;
		private final long start;
		private final ByteArrayOutputStream line = new ByteArrayOutputStream();
		private boolean ended = false;

		TappedSource(okio.Source delegate, Listener listener, long start) {
			super(delegate);
			this.listener = listener;
			this.start = start;
		}

		@Override
		public long read(Buffer sink, long byteCount) throws IOException {
			final long read;
			try {
				read = super.read(sink, byteCount);
			} catch (IOException | RuntimeException e) {
				ended("failed: " + e, false);
				throw e;
			}
			if (read < 0) {
				ended("ended", true);
				return read;
			}
			if (listener != null) {
				// the bytes just read are at the end of the sink
				final Buffer copy = new Buffer();
				sink.copyTo(copy, sink.size() - read, read);
				while (!copy.exhausted()) {
					final byte b = copy.readByte();
					if (b == '\n') {
						lineEnded();
					} else if (b != '\r') {
						line.write(b);
					}
				}
			}
			return read;
		}

		@Override
		public void close() throws IOException {
			ended("closed", true);
			super.close();
		}

		private void lineEnded() {
			final String text = line.toString(StandardCharsets.UTF_8);
			line.reset();
			if (text.startsWith("data:")) {
				final String thinking = thinkingOf(text.substring(5).trim());
				if (thinking != null && !thinking.isEmpty()) {
					listener.add(thinking);
				}
			}
		}

		private void ended(String how, boolean normally) {
			if (ended) {
				return;
			}
			ended = true;
			if (LOGGER.isDebugEnabled() || !normally) {
				final String message = "Claude stream " + how + " after " + (System.nanoTime() - start) / 1_000_000
						+ " ms" + (listener != null ? ", " + listener.received() + " thinking character(s) read" : "");
				if (normally) {
					LOGGER.debug(message);
				} else {
					LOGGER.warn(message);
				}
			}
		}
	}
}
