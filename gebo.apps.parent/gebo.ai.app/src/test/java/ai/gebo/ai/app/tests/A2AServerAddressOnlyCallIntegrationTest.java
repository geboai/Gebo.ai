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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.a2aproject.sdk.jsonrpc.common.json.JsonUtil;
import org.a2aproject.sdk.jsonrpc.common.wrappers.SendMessageRequest;
import org.a2aproject.sdk.jsonrpc.common.wrappers.SendMessageResponse;
import org.a2aproject.sdk.spec.Message;
import org.a2aproject.sdk.spec.MessageSendParams;
import org.a2aproject.sdk.spec.Part;
import org.a2aproject.sdk.spec.TextPart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import ai.gebo.architecture.a2aserver.model.A2AExportedAgent;
import ai.gebo.architecture.a2aserver.model.A2AServerConfig;
import ai.gebo.architecture.a2aserver.runtime.A2AServerBuilder;
import ai.gebo.llms.abstraction.layer.tests.TestChatModel;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;

/**
 * Certifies that an A2A server exporting one agent or network is called by its
 * address alone, over the real path: the request a client of the protocol sends,
 * which names no skill, is given to the endpoint of the server and the exported
 * network answers it. A server exporting several keeps asking which one is meant.
 */
@TestPropertySource(properties = { "ai.gebo.agents.standard.enabled=true",
		"ai.gebo.agents.standard.default-chat-network-of-agents=AGENTIC_LOOP_AGENTS_NETWORK" })
public class A2AServerAddressOnlyCallIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final String NETWORK = "AGENTIC_LOOP_AGENTS_NETWORK";
	private static final String REQUEST = "GEBO_TEST_A2A_ADDRESS_REQUEST: say what the weather is.";
	private static final String ANSWER = "GEBO_TEST_A2A_ADDRESS_ANSWER: it is sunny.";

	@Autowired
	private A2AServerBuilder serverBuilder;

	/** The code of the chat profile of the test, given when it is stored. */
	private String profileCode;

	@Override
	protected void beforeEachCallback() throws Exception {
		final GChatProfileConfiguration profile = new GChatProfileConfiguration();
		// a profile of its own for each test: a conversation takes its code from its profile's name
		profile.setDescription("A2A address test profile " + UUID.randomUUID().toString().substring(0, 8));
		profile.setDefaultChatNetworkOfAgents(NETWORK);
		profileCode = persistentObjectManager.insert(profile).getCode();
		TestChatModel.setGlobalResponseLogic(prompt -> prompt != null && prompt.contains(REQUEST) ? ANSWER : "");
	}

	@Override
	protected void afterEachCallback() throws Exception {
		TestChatModel.clearGlobalResponseLogic();
	}

	@Test
	public void aServerExportingOneNetworkIsCalledByItsAddressAlone() throws Exception {
		final A2AServerConfig server = serverOf("test-address-only", exportOf("the_loop"));

		final SendMessageResponse answer = send(server, null);

		assertNull(answer.getError(),
				"a call naming no skill is answered: " + (answer.getError() != null ? answer.getError().getMessage() : ""));
		assertTrue(textOf(answer).contains(ANSWER), "by the exported network: " + textOf(answer));
	}

	@Test
	public void aServerExportingSeveralAsksWhichOneIsMeant() throws Exception {
		final A2AServerConfig server = serverOf("test-address-several", exportOf("the_loop"),
				exportOf("the_other_loop"));

		final SendMessageResponse unnamed = send(server, null);
		assertNotNull(unnamed.getError(), "a call naming no skill is not answered by one of the two");

		final SendMessageResponse named = send(server, "the_other_loop");
		assertNull(named.getError(),
				"a call naming its skill is answered: " + (named.getError() != null ? named.getError().getMessage() : ""));
		assertTrue(textOf(named).contains(ANSWER), "by the export it names: " + textOf(named));
	}

	private A2AServerConfig serverOf(String url, A2AExportedAgent... exports) {
		A2AServerConfig server = new A2AServerConfig();
		server.setExportedRelativeUrl(url);
		server.setEnabled(true);
		server.setAccessibleToAll(true);
		server.setExportedAgents(List.of(exports));
		return server;
	}

	private A2AExportedAgent exportOf(String skillName) {
		A2AExportedAgent exported = new A2AExportedAgent();
		exported.setKind(A2AExportedAgent.Kind.NETWORK);
		exported.setNetworkCode(NETWORK);
		exported.setSkillName(skillName);
		exported.setChatProfileCode(profileCode);
		return exported;
	}

	/**
	 * Sends the request to the endpoint of the server as a client does: the message
	 * the protocol describes, with the skill in its metadata only when one is named.
	 */
	private SendMessageResponse send(A2AServerConfig server, String skill) throws Exception {
		final Message.Builder message = Message.builder().role(Message.Role.ROLE_USER)
				.messageId(UUID.randomUUID().toString()).parts(List.<Part<?>>of(new TextPart(REQUEST)));
		if (skill != null) {
			message.metadata(Map.of("skillId", skill));
		}
		final JsonObject written = JsonParser.parseString(JsonUtil.toJson(new SendMessageRequest(
				UUID.randomUUID().toString(), MessageSendParams.builder().message(message.build()).build())))
				.getAsJsonObject();
		// the name of the method the server answers to
		written.addProperty("method", "message/send");
		final String body = written.toString();

		final MockHttpServletRequest http = new MockHttpServletRequest("POST",
				"/a2a/" + server.getExportedRelativeUrl());
		http.setContentType("application/json");
		http.setContent(body.getBytes(StandardCharsets.UTF_8));
		final ServerRequest request = ServerRequest.create(http,
				List.<HttpMessageConverter<?>>of(new StringHttpMessageConverter(StandardCharsets.UTF_8)));

		final Optional<HandlerFunction<ServerResponse>> handler = serverBuilder.build(server).getRouterFunction()
				.route(request);
		assertTrue(handler.isPresent(), "the server answers on its address");
		final ServerResponse response = handler.get().handle(request);
		assertEquals(200, response.statusCode().value(), "the caller is let in");
		return JsonUtil.fromJson((String) ((EntityResponse<?>) response).entity(), SendMessageResponse.class);
	}

	private static String textOf(SendMessageResponse answer) {
		final StringBuilder text = new StringBuilder();
		if (answer.getResult() instanceof Message message) {
			for (Part<?> part : message.parts()) {
				if (part instanceof TextPart textPart) {
					text.append(textPart.text());
				}
			}
		}
		return text.toString();
	}
}
