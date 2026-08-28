package ai.gebo.a2a.integration.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;

import com.sun.net.httpserver.HttpServer;

import ai.gebo.architecture.integration.tests.AbstractVendorSetupAndUseTest;
import ai.gebo.monolithic.api.client.api.A2AClientConfigControllerApi;
import ai.gebo.monolithic.api.client.api.AuthControllerApi;
import ai.gebo.monolithic.api.client.api.GeboA2AServerAdminControllerApi;
import ai.gebo.monolithic.api.client.api.GeboFastInstallationSetupControllerApi;
import ai.gebo.monolithic.api.client.invoker.ApiClient;
import ai.gebo.monolithic.api.client.model.A2AExportedAgent;
import ai.gebo.monolithic.api.client.model.A2ARemoteAgentConfig;
import ai.gebo.monolithic.api.client.model.A2AServerConfig;
import ai.gebo.monolithic.api.client.model.FastInstallationSetupData;
import ai.gebo.monolithic.api.client.model.LoginRequest;
import ai.gebo.monolithic.api.client.model.OperationStatusA2ARemoteAgentConfig;
import ai.gebo.monolithic.api.client.model.OperationStatusA2AServerConfig;
import ai.gebo.monolithic.api.client.model.OperationStatusAuthResponse;
import ai.gebo.monolithic.api.client.model.OperationStatusBoolean;
import ai.gebo.monolithic.app.Main;
import org.a2aproject.sdk.jsonrpc.common.json.JsonUtil;
import org.a2aproject.sdk.spec.AgentCapabilities;
import org.a2aproject.sdk.spec.AgentCard;
import org.a2aproject.sdk.spec.AgentSkill;

/**
 * Integration coverage for the A2A modules, grounded in the monolith
 * fake-LLMS integration-test architecture: it boots the real application
 * ({@link Main}) on a random port with the standard fake-LLMS admin setup, then
 * drives the A2A admin controllers through the generated REST client
 * ({@code ai.gebo.monolithic.api.client}).
 * <ul>
 * <li><b>Export:</b> registers and enables an {@link A2AServerConfig} exporting the
 * default agents network; a successful insert exercises the admin controller,
 * persistence, ACLs, and the {@code A2AServerRegistry} building the live endpoint
 * (Agent Card + JSON-RPC router) for the config.</li>
 * <li><b>Import:</b> stands up a mock A2A peer (a JDK {@link HttpServer} serving an
 * Agent Card), registers an {@link A2ARemoteAgentConfig} pointing at it, and calls
 * {@code testAndDiscovery} — exercising the connector, card fetch and skill
 * discovery through the running app.</li>
 * </ul>
 */
@SpringBootTest(classes = Main.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "ai.gebo.agents.standard.enabled=true")
public class A2AExportImportIntegrationTest extends AbstractVendorSetupAndUseTest {

	private static final String DEFAULT_AGENTS_NETWORK = "DEFAULT_AGENTS_NETWORK";

	private static final String ADMIN_USER = "admin";
	private static final String ADMIN_PASSWORD = "Admin.Password123!";

	@Test
	void exportsAServerConfigAndDiscoversARemoteAgent() throws Exception {
		// Minimal admin setup: register the first admin and log in (no LLM/vendor
		// setup needed for A2A), so the suite is self-contained and doesn't require
		// the cloud-only FullSetupSecret env configuration.
		ApiClient apiClient = authedAdminClient();

		// --- Export: register + enable an A2A server exporting the default network ---
		GeboA2AServerAdminControllerApi serverAdmin = new GeboA2AServerAdminControllerApi(apiClient);
		A2AExportedAgent exported = new A2AExportedAgent().kind(A2AExportedAgent.KindEnum.NETWORK)
				.networkCode(DEFAULT_AGENTS_NETWORK).skillName("default");
		A2AServerConfig serverConfig = new A2AServerConfig().exportedRelativeUrl("itest-" + shortId()).enabled(true)
				.accessibleToAll(true).exportedAgents(List.of(exported));
		OperationStatusA2AServerConfig serverStatus = serverAdmin.insert(serverConfig);
		assertNotNull(serverStatus, "insert must return a status");
		assertFalse(Boolean.TRUE.equals(serverStatus.isHasErrorMessages()),
				"exporting the default network must not error: " + serverStatus.getMessages());
		assertNotNull(serverStatus.getResult(), "the persisted A2A server config must be returned");

		// --- Import: discover a mock remote A2A agent ---
		HttpServer mockPeer = startMockPeer();
		try {
			String peerBaseUrl = "http://127.0.0.1:" + mockPeer.getAddress().getPort();
			A2AClientConfigControllerApi clientAdmin = new A2AClientConfigControllerApi(apiClient);
			A2ARemoteAgentConfig remote = new A2ARemoteAgentConfig().baseUrl(peerBaseUrl)
					.agentCardPath("/.well-known/agent-card.json").rpcEndpoint("/")
					.transportType(A2ARemoteAgentConfig.TransportTypeEnum.JSONRPC)
					.authMode(A2ARemoteAgentConfig.AuthModeEnum.NONE).exportingPrefix("itest-peer");
			OperationStatusA2ARemoteAgentConfig discovery = clientAdmin.testAndDiscovery1(remote);
			assertNotNull(discovery, "testAndDiscovery must return a status");
			assertFalse(Boolean.TRUE.equals(discovery.isHasErrorMessages()),
					"discovery of the mock peer must not error: " + discovery.getMessages());
			assertNotNull(discovery.getResult(), "the discovered config must be returned");
			assertNotNull(discovery.getResult().getSkills(), "the discovered config must carry skills");
			assertTrue(discovery.getResult().getSkills().stream().anyMatch(s -> "echo".equals(s.getId())),
					"the mock peer's 'echo' skill must be discovered");
		} finally {
			mockPeer.stop(0);
		}
	}

	// ---------------------------------------------------------------------

	/** Registers the first admin and logs in, returning a token-authenticated client. */
	private ApiClient authedAdminClient() {
		ApiClient client = createApiClient("localhost", localServerPort, null);
		client.setBasePath("http://localhost:" + localServerPort);

		GeboFastInstallationSetupControllerApi fastSetup = new GeboFastInstallationSetupControllerApi(client);
		FastInstallationSetupData setupData = new FastInstallationSetupData();
		setupData.setLang("en");
		setupData.setLicenceAgreement("agreed");
		setupData.setUsername(ADMIN_USER);
		setupData.setPassword(ADMIN_PASSWORD);
		setupData.setPasswordC(ADMIN_PASSWORD);
		OperationStatusBoolean setupResult = fastSetup.createSetup(setupData);
		assertFalse(Boolean.TRUE.equals(setupResult.isHasErrorMessages()),
				"admin setup must not error: " + setupResult.getMessages());

		AuthControllerApi auth = new AuthControllerApi(client);
		LoginRequest login = new LoginRequest();
		login.setUsername(ADMIN_USER);
		login.setPassword(ADMIN_PASSWORD);
		OperationStatusAuthResponse authResult = auth.authenticateUser(login);
		assertFalse(Boolean.TRUE.equals(authResult.isHasErrorMessages()),
				"admin login must not error: " + authResult.getMessages());
		client.setApiKey(authResult.getResult().getSecurityHeaderData().getToken());
		return client;
	}

	private HttpServer startMockPeer() throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
		server.createContext("/.well-known/agent-card.json", exchange -> {
			try {
				byte[] body = JsonUtil.toJson(buildPeerCard()).getBytes(StandardCharsets.UTF_8);
				exchange.getResponseHeaders().add("Content-Type", "application/json");
				exchange.sendResponseHeaders(200, body.length);
				try (OutputStream os = exchange.getResponseBody()) {
					os.write(body);
				}
			} catch (Exception e) {
				throw new IOException(e);
			}
		});
		server.start();
		return server;
	}

	private AgentCard buildPeerCard() {
		AgentSkill skill = AgentSkill.builder().id("echo").name("Echo").description("Echoes input")
				.tags(List.of("test")).examples(List.of()).inputModes(List.of("text/plain"))
				.outputModes(List.of("text/plain")).securityRequirements(List.of()).build();
		return AgentCard.builder().name("Mock A2A Peer").description("mock").version("1.0.0")
				.url("http://127.0.0.1").preferredTransport("JSONRPC")
				.capabilities(AgentCapabilities.builder().streaming(true).pushNotifications(false)
						.extendedAgentCard(false).extensions(List.of()).build())
				.defaultInputModes(List.of("text/plain")).defaultOutputModes(List.of("text/plain"))
				.skills(List.of(skill)).securitySchemes(java.util.Map.of()).securityRequirements(List.of())
				.supportedInterfaces(List.of()).signatures(List.of()).additionalInterfaces(List.of()).build();
	}

	private static String shortId() {
		return UUID.randomUUID().toString().substring(0, 8);
	}
}
