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

	private final A2AAgentCardBuilder cardBuilder;
	private final A2ATaskBridge taskBridge;
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
		String text = renderParts(inbound != null ? inbound.parts() : null);
		String contextId = inbound != null ? inbound.contextId() : null;
		String skillId = extractSkillId(inbound);

		if ("message/stream".equals(method)) {
			return handleStream(config, id, skillId, text, contextId);
		}
		if ("message/send".equals(method)) {
			return handleSend(config, id, skillId, text, contextId);
		}
		A2AError error = new A2AError(JSONRPC_METHOD_NOT_FOUND, "Method not supported: " + method, null);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(JsonUtil.toJson(new SendMessageResponse(id, error)));
	}

	private ServerResponse handleSend(A2AServerConfig config, Object id, String skillId, String text, String contextId)
			throws Exception {
		try {
			A2ANotificationCollector sink = new A2ANotificationCollector();
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
	 * SSE variant. The run is executed synchronously and its result emitted as a
	 * single terminal streaming frame; live partial streaming of agent progress is a
	 * planned follow-up (the transport contract — SSE frames of
	 * SendStreamingMessageResponse — is already honoured here).
	 */
	private ServerResponse handleStream(A2AServerConfig config, Object id, String skillId, String text,
			String contextId) {
		final String output;
		final A2AError error;
		String out = null;
		A2AError err = null;
		try {
			A2ANotificationCollector sink = new A2ANotificationCollector();
			out = taskBridge.run(config, skillId, text, sink);
		} catch (Throwable t) {
			LOGGER.error("A2A message/stream failed", t);
			err = new A2AError(JSONRPC_INTERNAL_ERROR, t.getMessage(), null);
		}
		output = out;
		error = err;
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
