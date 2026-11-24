/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import org.a2aproject.sdk.spec.EventKind;
import org.a2aproject.sdk.spec.Message;
import org.a2aproject.sdk.spec.Part;
import org.a2aproject.sdk.spec.StreamingEventKind;
import org.a2aproject.sdk.spec.TextPart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.architecture.a2aclients.controllers.A2AClientConfigController;
import ai.gebo.architecture.a2aclients.model.A2AAuthMode;
import ai.gebo.architecture.a2aclients.model.A2ARemoteAgentConfig;
import ai.gebo.architecture.a2aclients.service.impl.A2AClientConnector;
import ai.gebo.architecture.a2aserver.controllers.GeboA2AServerAdminController;
import ai.gebo.architecture.a2aserver.model.A2AExportedAgent;
import ai.gebo.architecture.a2aserver.model.A2AServerConfig;
import ai.gebo.llms.abstraction.layer.tests.TestChatModel;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.monolithic.app.Main;
import ai.gebo.model.OperationStatus;

/**
 * Certifies that a Gebo.ai installation calls the network another one exports over
 * A2A, over the real path: an administrator publishes an A2A server exporting one
 * network, another registers it as a remote agent by the address of its card, and
 * the client of the platform calls it over HTTP as the person asking, waiting for
 * the answer and reading it as it is written.
 */
@SpringBootTest(classes = Main.class, webEnvironment = WebEnvironment.RANDOM_PORT)
// the key the tokens of this installation are signed with: the test settings carry none,
// and the call is made with a token of the person asking
@TestPropertySource(properties = { "ai.gebo.agents.standard.enabled=true",
		"ai.gebo.agents.standard.default-chat-network-of-agents=AGENTIC_LOOP_AGENTS_NETWORK",
		"ai.gebo.security.auth.tokenSecret=" + A2AGeboCallsGeboIntegrationTest.TEST_TOKEN_SECRET })
public class A2AGeboCallsGeboIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	static final String TEST_TOKEN_SECRET = "7465737474657374746573747465737474657374746573747465737474657374"
			+ "7465737474657374746573747465737474657374746573747465737474657374";
	private static final String NETWORK = "AGENTIC_LOOP_AGENTS_NETWORK";
	private static final String REQUEST = "GEBO_TEST_A2A_GEBO_REQUEST: say what the weather is.";
	private static final String ANSWER = "GEBO_TEST_A2A_GEBO_ANSWER: it is sunny.";

	@LocalServerPort
	private int serverPort;
	@Autowired
	private GeboA2AServerAdminController serverController;
	@Autowired
	private A2AClientConfigController clientController;
	@Autowired
	private A2AClientConnector connector;

	/** The code of the chat profile of the test, given when it is stored. */
	private String profileCode;

	@Override
	protected void beforeEachCallback() throws Exception {
		final GChatProfileConfiguration profile = new GChatProfileConfiguration();
		// a profile of its own for each test: a conversation takes its code from its profile's name
		profile.setDescription("A2A Gebo to Gebo test profile " + UUID.randomUUID().toString().substring(0, 8));
		profile.setDefaultChatNetworkOfAgents(NETWORK);
		profileCode = persistentObjectManager.insert(profile).getCode();
		TestChatModel.setGlobalResponseLogic(prompt -> prompt != null && prompt.contains(REQUEST) ? ANSWER : "");
	}

	@Override
	protected void afterEachCallback() throws Exception {
		TestChatModel.clearGlobalResponseLogic();
	}

	@Test
	public void theClientOfThePlatformCallsANetworkAnotherInstallationExports() throws Exception {
		// the exporting side: an A2A server with one network
		final String url = "test-gebo-to-gebo-" + UUID.randomUUID().toString().substring(0, 8);
		final A2AExportedAgent exported = new A2AExportedAgent();
		exported.setKind(A2AExportedAgent.Kind.NETWORK);
		exported.setNetworkCode(NETWORK);
		exported.setSkillName("the_loop");
		exported.setChatProfileCode(profileCode);
		final A2AServerConfig server = new A2AServerConfig();
		server.setDescription("Gebo to Gebo test server");
		server.setExportedRelativeUrl(url);
		server.setEnabled(true);
		server.setAccessibleToAll(true);
		server.setExportedAgents(List.of(exported));
		final OperationStatus<A2AServerConfig> published = serverController.insertA2AServer(server);
		assertNotNull(published.getResult(), "the server is published: " + published.getMessages());

		// the calling side: the server registered by the address of its card, called as the person asking
		final A2ARemoteAgentConfig remote = new A2ARemoteAgentConfig();
		remote.setDescription("Gebo to Gebo test remote agent");
		remote.setAgentCardUrl("http://localhost:" + serverPort + "/a2a/" + url + "/.well-known/agent-card.json");
		remote.setAuthMode(A2AAuthMode.TOKEN_EXCHANGE);
		remote.setExportingPrefix("gebo-peer-");
		remote.setEnabled(true);
		remote.setAccessibleToAll(true);
		final OperationStatus<A2ARemoteAgentConfig> discovered = clientController
				.testAndDiscoveryA2AClientConfig(remote);
		assertNotNull(discovered.getResult(), "the card of the server is read: " + discovered.getMessages());
		assertEquals(1, discovered.getResult().getSkills().size(), "it describes the one thing it exports");

		// waiting for the answer
		final EventKind answer = connector.sendMessage(discovered.getResult(), REQUEST, null);
		assertTrue(textOf(answer).contains(ANSWER), "the exported network answers: " + textOf(answer));

		// reading the answer as it is written
		final List<StreamingEventKind> frames = connector.streamMessage(discovered.getResult(), REQUEST, null)
				.collectList().block(Duration.ofSeconds(120));
		assertNotNull(frames);
		assertFalse(frames.isEmpty(), "the answer is streamed");
		final StringBuilder streamed = new StringBuilder();
		for (StreamingEventKind frame : frames) {
			streamed.append(textOf(frame));
		}
		assertTrue(streamed.toString().contains(ANSWER), "and carries the answer: " + streamed);
	}

	private static String textOf(Object event) {
		final StringBuilder text = new StringBuilder();
		if (event instanceof Message message) {
			for (Part<?> part : message.parts()) {
				if (part instanceof TextPart textPart) {
					text.append(textPart.text());
				}
			}
		}
		return text.toString();
	}
}
