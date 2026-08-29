package ai.gebo.architecture.a2aclients.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import ai.gebo.architecture.a2aclients.model.A2AAuthMode;
import ai.gebo.architecture.a2aclients.model.A2ARemoteAgentConfig;
import org.a2aproject.sdk.jsonrpc.common.json.JsonUtil;
import org.a2aproject.sdk.jsonrpc.common.wrappers.SendMessageRequest;
import org.a2aproject.sdk.jsonrpc.common.wrappers.SendMessageResponse;
import org.a2aproject.sdk.spec.AgentCapabilities;
import org.a2aproject.sdk.spec.AgentCard;
import org.a2aproject.sdk.spec.AgentSkill;
import org.a2aproject.sdk.spec.EventKind;
import org.a2aproject.sdk.spec.Message;
import org.a2aproject.sdk.spec.MessageSendParams;
import org.a2aproject.sdk.spec.Part;
import org.a2aproject.sdk.spec.TextPart;

/**
 * Self-contained integration test for {@link A2AClientConnector}: a JDK
 * {@link HttpServer} stands in for a remote A2A agent, serving an Agent Card and a
 * JSON-RPC {@code message/send} reply — both produced with the A2A SDK's own
 * {@link JsonUtil} (Gson). Exercises the full client transport round-trip (WebClient
 * request framing, Gson serialization/deserialization, card parsing, JSON-RPC
 * response parsing and text extraction) without any network or LLM dependency.
 */
class A2AClientConnectorIntegrationTest {

	private static final String AGENT_NAME = "Mock A2A Agent";
	private static final String SKILL_ID = "echo";
	private static final String AGENT_REPLY = "Hello from the mock A2A agent";

	private HttpServer server;
	private String baseUrl;
	private String lastRequestBody;

	@BeforeEach
	void startServer() throws IOException {
		server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);

		server.createContext("/.well-known/agent-card.json", exchange -> {
			try {
				byte[] body = JsonUtil.toJson(buildCard()).getBytes(StandardCharsets.UTF_8);
				respond(exchange, 200, "application/json", body);
			} catch (Exception e) {
				throw new IOException(e);
			}
		});

		server.createContext("/", exchange -> {
			try {
				lastRequestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
				// Echo the inbound JSON-RPC id back on the response.
				SendMessageRequest inbound = JsonUtil.fromJson(lastRequestBody, SendMessageRequest.class);
				Message reply = Message.builder().role(Message.Role.ROLE_AGENT).messageId(UUID.randomUUID().toString())
						.parts(new TextPart(AGENT_REPLY)).build();
				SendMessageResponse response = new SendMessageResponse(inbound.getId(), reply);
				byte[] body = JsonUtil.toJson(response).getBytes(StandardCharsets.UTF_8);
				respond(exchange, 200, "application/json", body);
			} catch (Exception e) {
				throw new IOException(e);
			}
		});

		server.start();
		baseUrl = "http://127.0.0.1:" + server.getAddress().getPort();
	}

	@AfterEach
	void stopServer() {
		if (server != null) {
			server.stop(0);
		}
	}

	@Test
	void fetchesAndParsesTheAgentCard() throws Exception {
		A2AClientConnector connector = new A2AClientConnector(null, null, null, null, null, null);
		AgentCard card = connector.fetchAgentCard(config());

		assertNotNull(card);
		assertEquals(AGENT_NAME, card.name());
		assertNotNull(card.skills());
		assertEquals(1, card.skills().size());
		assertEquals(SKILL_ID, card.skills().get(0).id());
		assertTrue(card.capabilities().streaming());
	}

	@Test
	void sendsMessageAndParsesTheReply() throws Exception {
		A2AClientConnector connector = new A2AClientConnector(null, null, null, null, null, null);
		EventKind result = connector.sendMessage(config(), "ping", null);

		// The reply is an agent Message carrying the mock's text.
		Message message = assertInstanceOf(Message.class, result);
		assertEquals(AGENT_REPLY, firstText(message));

		// And the request the connector actually sent was a well-formed message/send.
		SendMessageRequest sent = JsonUtil.fromJson(lastRequestBody, SendMessageRequest.class);
		MessageSendParams params = sent.getParams();
		assertNotNull(params);
		assertEquals("ping", firstText(params.message()));
		assertEquals(Message.Role.ROLE_USER, params.message().role());
	}

	// ---------------------------------------------------------------------

	private A2ARemoteAgentConfig config() {
		A2ARemoteAgentConfig config = new A2ARemoteAgentConfig();
		config.setAgentCardUrl(baseUrl + "/.well-known/agent-card.json");
		// No rpcEndpoint override: exercise card-derived endpoint resolution. The card's
		// url is baseUrl, which the mock serves the JSON-RPC handler at ("/").
		config.setAuthMode(A2AAuthMode.NONE);
		config.setExportingPrefix("mock");
		return config;
	}

	private AgentCard buildCard() {
		AgentSkill skill = AgentSkill.builder().id(SKILL_ID).name("Echo").description("Echoes the input")
				.tags(List.of("test")).examples(List.of()).inputModes(List.of("text/plain"))
				.outputModes(List.of("text/plain")).securityRequirements(List.of()).build();
		return AgentCard.builder().name(AGENT_NAME).description("A mock agent for tests").version("1.0.0")
				.url(baseUrl).preferredTransport("JSONRPC")
				.capabilities(AgentCapabilities.builder().streaming(true).pushNotifications(false)
						.extendedAgentCard(false).extensions(List.of()).build())
				.defaultInputModes(List.of("text/plain")).defaultOutputModes(List.of("text/plain"))
				.skills(List.of(skill)).securitySchemes(Map.of()).securityRequirements(List.of())
				.supportedInterfaces(List.of()).signatures(List.of()).additionalInterfaces(List.of()).build();
	}

	private static String firstText(Message message) {
		for (Part<?> part : message.parts()) {
			if (part instanceof TextPart textPart) {
				return textPart.text();
			}
		}
		return null;
	}

	private static void respond(HttpExchange exchange, int status, String contentType, byte[] body) throws IOException {
		exchange.getResponseHeaders().add("Content-Type", contentType);
		exchange.sendResponseHeaders(status, body.length);
		try (OutputStream os = exchange.getResponseBody()) {
			os.write(body);
		}
	}
}
