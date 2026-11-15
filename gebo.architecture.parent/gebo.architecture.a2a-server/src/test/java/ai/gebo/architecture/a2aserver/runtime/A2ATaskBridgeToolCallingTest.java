/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.a2aserver.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.architecture.a2aserver.model.A2AExportedAgent;
import ai.gebo.architecture.a2aserver.model.A2AServerConfig;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCallingManagerFactory;
import ai.gebo.llms.agent.standard.services.GTextAgentsNetworkRunner;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;

/**
 * An A2A skill run names the server and the skill as the caller of every model
 * call it makes, on both the plain run and the run in a chat environment.
 */
class A2ATaskBridgeToolCallingTest {
	private final GTextAgentsNetworkRunner runner = mock(GTextAgentsNetworkRunner.class);
	private final A2AChatEnvironmentRunner chatEnvironment = mock(A2AChatEnvironmentRunner.class);
	private final A2ATaskBridge bridge = new A2ATaskBridge(runner, chatEnvironment,
			new GeboToolCallingManagerFactory());

	private static A2AServerConfig server(A2AExportedAgent.Kind kind, String code) {
		A2AExportedAgent exported = new A2AExportedAgent();
		exported.setKind(kind);
		exported.setSkillName("ask");
		exported.setChatProfileCode("profile-1");
		if (kind == A2AExportedAgent.Kind.NETWORK) {
			exported.setNetworkCode(code);
		} else {
			exported.setAgentConfigCode(code);
		}
		A2AServerConfig server = new A2AServerConfig();
		server.setExportedRelativeUrl("sales");
		server.setExportedAgents(List.of(exported));
		return server;
	}

	@Test
	void aNetworkSkillRunNamesTheServerAndTheSkill() throws Exception {
		when(runner.runNetwork(any(String.class), any(), any(), any())).thenReturn("answered");

		assertEquals("answered", bridge.run(server(A2AExportedAgent.Kind.NETWORK, "the-network"), "ask", "hi", null));

		ArgumentCaptor<GeboToolCalling> toolCalling = ArgumentCaptor.forClass(GeboToolCalling.class);
		verify(runner).runNetwork(eq("the-network"), eq("hi"), isNull(), toolCalling.capture());
		assertEquals("a2a:sales/ask", toolCalling.getValue().getCaller());
		assertNull(toolCalling.getValue().getListener());
	}

	@Test
	void anAgentSkillRunNamesTheServerAndTheSkill() throws Exception {
		when(runner.runAgent(any(), any(), any(), any(), any())).thenReturn("answered");

		bridge.run(server(A2AExportedAgent.Kind.AGENT, "the-agent"), "ask", "hi", null);

		ArgumentCaptor<GeboToolCalling> toolCalling = ArgumentCaptor.forClass(GeboToolCalling.class);
		verify(runner).runAgent(eq("the-agent"), any(), eq("hi"), isNull(), toolCalling.capture());
		assertEquals("a2a:sales/ask", toolCalling.getValue().getCaller());
	}

	@Test
	void aRunInAChatEnvironmentNamesTheServerAndTheSkillToo() throws Exception {
		GeboChatRequest request = new GeboChatRequest();
		when(chatEnvironment.requestOf(any(), any(), any())).thenReturn(request);
		A2AInboundResources inbound = mock(A2AInboundResources.class);
		when(inbound.getText()).thenReturn("hi");

		bridge.runInChatEnvironment(server(A2AExportedAgent.Kind.NETWORK, "the-network"), "ask", inbound, null, null);

		ArgumentCaptor<GeboToolCalling> toolCalling = ArgumentCaptor.forClass(GeboToolCalling.class);
		verify(chatEnvironment).run(eq("the-network"), eq(request), eq(inbound), toolCalling.capture(), isNull());
		assertEquals("a2a:sales/ask", toolCalling.getValue().getCaller());
	}
}
