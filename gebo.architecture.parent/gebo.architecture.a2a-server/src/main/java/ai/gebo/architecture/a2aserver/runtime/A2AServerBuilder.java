package ai.gebo.architecture.a2aserver.runtime;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.concurrent.atomic.AtomicInteger;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.architecture.a2aserver.model.A2AServerConfig;
import lombok.AllArgsConstructor;
import org.a2aproject.sdk.jsonrpc.common.json.JsonUtil;
import org.a2aproject.sdk.jsonrpc.common.wrappers.SendMessageRequest;
import org.a2aproject.sdk.jsonrpc.common.wrappers.SendMessageResponse;
import org.a2aproject.sdk.jsonrpc.common.wrappers.SendStreamingMessageResponse;
import org.a2aproject.sdk.spec.A2AError;
import org.a2aproject.sdk.spec.AgentCard;
import org.a2aproject.sdk.spec.Message;
import org.a2aproject.sdk.spec.MessageSendParams;
import org.a2aproject.sdk.spec.Part;
import org.a2aproject.sdk.spec.TextPart;

/**
 * Builds the {@link RouterFunction} that serves one published {@link A2AServerConfig}:
 * <ul>
 * <li>{@code GET <base>/.well-known/agent-card.json} &rarr; the Agent Card, and</li>
 * <li>{@code POST <base>} &rarr; the JSON-RPC endpoint ({@code message/send} and
 * {@code message/stream} over SSE).</li>
 * </ul>
 * Both are wrapped in an access filter that rejects callers not granted on the
 * config (HTTP 403) — mirroring {@code GeboMcpServerBuilder}. Serialization uses
 * the A2A SDK's Gson via {@link JsonUtil} (not Spring's Jackson).
 */
@Service
@AllArgsConstructor
public class A2AServerBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(A2AServerBuilder.class);

	private static final String ENDPOINT_PREFIX = "/a2a/";
	private static final String CARD_SUFFIX = "/.well-known/agent-card.json";
	private static final int JSONRPC_INTERNAL_ERROR = -32603;
	private static final int JSONRPC_METHOD_NOT_FOUND = -32601;
	/** How long the frames of a waiting caller's run are read for. */
	private static final java.time.Duration FRAMES_COLLECTION_TIMEOUT = java.time.Duration.ofMinutes(10);
	/** How many frames are held while the caller is being written to. */
	private static final int STREAMED_FRAMES_BUFFER = 64;

	private final A2AAgentCardBuilder cardBuilder;
	private final A2ATaskBridge taskBridge;
	/** Turns what the platform answers into what an A2A caller receives. */
	private final A2AResultAdapter resultAdapter;
	private final A2AAccessChecker accessChecker;

	public A2AServerInstance build(A2AServerConfig config) {
		final String base = ENDPOINT_PREFIX + config.getExportedRelativeUrl();
		RouterFunction<ServerResponse> router = RouterFunctions.route()
				.GET(base + CARD_SUFFIX, request -> serveCard(config, absoluteBase(request, base)))
				.POST(base, request -> handleRpc(config, request)).build()
				.filter((request, next) -> accessChecker.canAccessServer(config) ? next.handle(request)
						: ServerResponse.status(403).build());
		return new A2AServerInstance(config, router);
	}

	// ---------------------------------------------------------------------
	// Agent Card
	// ---------------------------------------------------------------------

	private ServerResponse serveCard(A2AServerConfig config, String cardUrl) throws Exception {
		AgentCard card = cardBuilder.build(config, cardUrl);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(JsonUtil.toJson(card));
	}

	// ---------------------------------------------------------------------
	// JSON-RPC
	// ---------------------------------------------------------------------

	private ServerResponse handleRpc(A2AServerConfig config, ServerRequest request) throws Exception {
		String body = request.body(String.class);
		JsonObject root = JsonParser.parseString(body).getAsJsonObject();
		String method = root.has("method") ? root.get("method").getAsString() : null;
		// Parse into a send request (both send and stream carry MessageSendParams + id).
		SendMessageRequest parsed = JsonUtil.fromJson(body, SendMessageRequest.class);
		Object id = parsed.getId();
		MessageSendParams params = parsed.getParams();
		Message inbound = params != null ? params.message() : null;
		A2AInboundResources received = A2AInboundResources.of(inbound != null ? inbound.parts() : null);
		String text = received.getText();
		if (!received.getNotTaken().isEmpty()) {
			LOGGER.warn("The caller sent things this export cannot take in: " + received.getNotTaken());
		}
		String contextId = inbound != null ? inbound.contextId() : null;
		String skillId = extractSkillId(inbound);

		if ("message/stream".equals(method)) {
			return handleStream(config, id, skillId, received, contextId);
		}
		if ("message/send".equals(method)) {
			return handleSend(config, id, skillId, received, contextId);
		}
		A2AError error = new A2AError(JSONRPC_METHOD_NOT_FOUND, "Method not supported: " + method, null);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(JsonUtil.toJson(new SendMessageResponse(id, error)));
	}

	private ServerResponse handleSend(A2AServerConfig config, Object id, String skillId,
			A2AInboundResources received, String contextId) throws Exception {
		final String text = received.getText();
		try {
			A2ANotificationCollector sink = new A2ANotificationCollector();
			// A network exported with a chat profile runs in that profile's environment,
			// and the caller waiting for one answer simply reads the frame marked as the
			// last one: the same run, read the other way.
			A2AChatEnvironmentRunner.ChatEnvironmentRun run = taskBridge.runInChatEnvironment(config, skillId,
					received, contextId, sink);
			if (run != null) {
				try {
					GeboChatResponse response = lastResponseOf(run);
					Message result = resultAdapter.messageOf(response, null,
							contextId != null ? contextId : chatContextOf(response));
					return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
							.body(JsonUtil.toJson(new SendMessageResponse(id, result)));
				} finally {
					run.dispose();
				}
			}
			String output = taskBridge.run(config, skillId, text, sink);
			Message result = agentMessage(output, contextId);
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
					.body(JsonUtil.toJson(new SendMessageResponse(id, result)));
		} catch (Throwable t) {
			LOGGER.error("A2A message/send failed", t);
			A2AError error = new A2AError(JSONRPC_INTERNAL_ERROR, t.getMessage(), null);
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
					.body(JsonUtil.toJson(new SendMessageResponse(id, error)));
		}
	}

	/**
	 * The response of a run: the one the frame marked as the last carries, else the
	 * one the run filled in.
	 */
	private GeboChatResponse lastResponseOf(A2AChatEnvironmentRunner.ChatEnvironmentRun run) {
		GeboChatResponse fromFrames = null;
		List<GeboChatMessageEnvelope> frames = run.getFrames().collectList().block(FRAMES_COLLECTION_TIMEOUT);
		if (frames != null) {
			for (GeboChatMessageEnvelope frame : frames) {
				GeboChatResponse carried = resultAdapter.responseOf(frame);
				if (carried != null) {
					fromFrames = carried;
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("A2A message/send read " + frames.size() + " frame(s) of the run");
			}
		}
		return fromFrames != null ? fromFrames : run.getResponse();
	}

	private String chatContextOf(GeboChatResponse response) {
		return response != null ? response.getUserChatContextCode() : null;
	}

	/**
	 * SSE variant. The run is executed synchronously and its result emitted as a
	 * single terminal streaming frame; live partial streaming of agent progress is a
	 * planned follow-up (the transport contract — SSE frames of
	 * SendStreamingMessageResponse — is already honoured here).
	 */
	private ServerResponse handleStream(A2AServerConfig config, Object id, String skillId,
			A2AInboundResources received, String contextId) {
		final String text = received.getText();
		final A2ANotificationCollector sink = new A2ANotificationCollector();
		A2AChatEnvironmentRunner.ChatEnvironmentRun streamed = null;
		A2AError prepareError = null;
		try {
			streamed = taskBridge.runInChatEnvironment(config, skillId, received, contextId, sink);
		} catch (Throwable t) {
			LOGGER.error("A2A message/stream failed", t);
			prepareError = new A2AError(JSONRPC_INTERNAL_ERROR, t.getMessage(), null);
		}
		if (prepareError == null && streamed != null) {
			return streamFrames(id, contextId, streamed);
		}
		if (prepareError != null) {
			return singleFrame(id, prepareError, null, contextId);
		}
		// Not a network with an environment of its own: run it the plain way and say
		// so in one frame, as before.
		String out = null;
		A2AError err = null;
		try {
			out = taskBridge.run(config, skillId, text, sink);
		} catch (Throwable t) {
			LOGGER.error("A2A message/stream failed", t);
			err = new A2AError(JSONRPC_INTERNAL_ERROR, t.getMessage(), null);
		}
		return singleFrame(id, err, out, contextId);
	}

	/**
	 * The frames of a run, as they are produced: every chunk the network writes is
	 * a frame of its own, and the frame it marks as the last carries the answer
	 * with the documents it rests on.
	 */
	private ServerResponse streamFrames(Object id, String contextId,
			A2AChatEnvironmentRunner.ChatEnvironmentRun run) {
		return ServerResponse.sse(sse -> {
			final AtomicInteger written = new AtomicInteger();
			try {
				run.getFrames().toStream(STREAMED_FRAMES_BUFFER).forEach(frame -> {
					try {
						GeboChatResponse carried = resultAdapter.responseOf(frame);
						if (carried != null) {
							Message message = resultAdapter.messageOf(carried, null,
									contextId != null ? contextId : carried.getUserChatContextCode());
							sse.data(JsonUtil.toJson(new SendStreamingMessageResponse(id, message)));
							written.incrementAndGet();
							return;
						}
						String chunk = resultAdapter.chunkOf(frame);
						if (chunk != null && !chunk.isEmpty()) {
							sse.data(JsonUtil.toJson(
									new SendStreamingMessageResponse(id, agentMessage(chunk, contextId))));
							written.incrementAndGet();
						}
					} catch (Exception frameFailure) {
						throw new IllegalStateException(frameFailure);
					}
				});
				if (written.get() == 0) {
					// A run that produced no frame still owes the caller an answer.
					GeboChatResponse response = run.getResponse();
					sse.data(JsonUtil.toJson(new SendStreamingMessageResponse(id,
							resultAdapter.messageOf(response, null, contextId))));
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("A2A message/stream wrote " + written.get() + " frame(s)");
				}
				sse.complete();
			} catch (Exception e) {
				LOGGER.error("A2A message/stream failed while writing the frames", e);
				sse.error(e);
			} finally {
				run.dispose();
			}
		});
	}

	/** One terminal frame: an error, or an answer that has no frames of its own. */
	private ServerResponse singleFrame(Object id, A2AError error, String output, String contextId) {
		return ServerResponse.sse(sse -> {
			try {
				SendStreamingMessageResponse frame = error != null ? new SendStreamingMessageResponse(id, error)
						: new SendStreamingMessageResponse(id, agentMessage(output, contextId));
				sse.data(JsonUtil.toJson(frame));
				sse.complete();
			} catch (Exception e) {
				sse.error(e);
			}
		});
	}

	// ---------------------------------------------------------------------
	// helpers
	// ---------------------------------------------------------------------

	private Message agentMessage(String text, String contextId) {
		Message.Builder builder = Message.builder().role(Message.Role.ROLE_AGENT)
				.messageId(UUID.randomUUID().toString()).parts(new TextPart(text != null ? text : ""));
		if (contextId != null && !contextId.isBlank()) {
			builder.contextId(contextId);
		}
		return builder.build();
	}

	private String extractSkillId(Message inbound) {
		if (inbound == null || inbound.metadata() == null) {
			return null;
		}
		Map<String, Object> metadata = inbound.metadata();
		Object skill = metadata.get("skillId");
		return skill != null ? skill.toString() : null;
	}

	private String renderParts(List<Part<?>> parts) {
		if (parts == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Part<?> part : parts) {
			if (part instanceof TextPart textPart) {
				if (sb.length() > 0) {
					sb.append("\n");
				}
				sb.append(textPart.text());
			}
		}
		return sb.toString();
	}

	private String absoluteBase(ServerRequest request, String base) {
		URI uri = request.uri();
		String authority = uri.getAuthority();
		String scheme = uri.getScheme();
		if (authority == null || scheme == null) {
			return base;
		}
		return scheme + "://" + authority + base;
	}
}
