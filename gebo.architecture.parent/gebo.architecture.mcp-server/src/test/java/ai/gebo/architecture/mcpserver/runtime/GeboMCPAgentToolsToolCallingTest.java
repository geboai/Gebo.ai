/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpserver.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.mcpserver.model.GeboMCPAgentTool;
import ai.gebo.architecture.mcpserver.model.GeboMCPAgentsNetworkTool;
import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCallingManagerFactory;
import ai.gebo.llms.agent.standard.services.GTextAgentsNetworkRunner;
import ai.gebo.llms.agent.standard.services.UserKnowledgeBasesExecutionEnvironment;

/**
 * An agent or a network exported as an MCP tool runs with the server and the
 * tool named as the caller of every model call it makes, one container per call.
 */
class GeboMCPAgentToolsToolCallingTest {
	private final GTextAgentsNetworkRunner runner = mock(GTextAgentsNetworkRunner.class);
	private final GeboMcpSecurityContextSupport security = mock(GeboMcpSecurityContextSupport.class);
	private final UserKnowledgeBasesExecutionEnvironment environment = mock(
			UserKnowledgeBasesExecutionEnvironment.class);
	private final GeboToolCallingManagerFactory factory = new GeboToolCallingManagerFactory();

	@SuppressWarnings("unchecked")
	private void runningInPlace() {
		when(security.runAs(any(), any())).thenAnswer(i -> ((Supplier<Object>) i.getArgument(1)).get());
		when(environment.knowledgeBaseCodes()).thenReturn(List.of());
		when(environment.toolsContext(any(), any())).thenReturn(Map.of());
	}

	private static GeboMCPServerConfig server() {
		GeboMCPServerConfig config = new GeboMCPServerConfig();
		config.setExportedUniqueRelativeUrl("sales");
		return config;
	}

	private ToolCallback networkTool(String toolName) {
		IAgentsNetworkDao networks = mock(IAgentsNetworkDao.class);
		when(networks.findByCode("the-network")).thenReturn(new GAgentsNetwork());
		GeboMCPAgentsNetworkTool export = new GeboMCPAgentsNetworkTool();
		export.setAgentsNetworkCode("the-network");
		export.setToolName(toolName);
		GeboMCPServerConfig config = server();
		config.setAgentNetworkAsTools(List.of(export));
		return new GeboMCPAgentsNetworkAsToolsProvider(networks, runner, security, environment, factory)
				.callbacks(config).get(0);
	}

	@Test
	void aNetworkAsAToolNamesTheServerAndTheTool() throws Exception {
		runningInPlace();
		when(runner.runNetwork(any(String.class), any(), any(), any())).thenReturn("answered");

		String answer = networkTool("askSales").call("{\"request\":\"hi\"}", new ToolContext(Map.of()));

		assertTrue(answer.contains("answered"), "the network's text, as the tool gives it back: " + answer);
		ArgumentCaptor<GeboToolCalling> toolCalling = ArgumentCaptor.forClass(GeboToolCalling.class);
		verify(runner).runNetwork(eq("the-network"), eq("hi"), isNull(), toolCalling.capture());
		assertEquals("mcp:sales/askSales", toolCalling.getValue().getCaller());
		assertNull(toolCalling.getValue().getListener());
	}

	@Test
	void anAgentAsAToolNamesTheServerAndTheTool() throws Exception {
		runningInPlace();
		IAgentConfigDao agents = mock(IAgentConfigDao.class);
		when(agents.findByCode("the-agent")).thenReturn(new GAgentConfig());
		when(runner.runAgent(any(), any(), any(), any(), any())).thenReturn("answered");
		GeboMCPAgentTool export = new GeboMCPAgentTool();
		export.setAgentConfigCode("the-agent");
		GeboMCPServerConfig config = server();
		config.setAgentAsTools(List.of(export));

		List<ToolCallback> tools = new GeboMCPAgentAsToolProvider(agents, runner, security, environment, factory)
				.callbacks(config);
		tools.get(0).call("{\"request\":\"hi\"}", new ToolContext(Map.of()));

		ArgumentCaptor<GeboToolCalling> toolCalling = ArgumentCaptor.forClass(GeboToolCalling.class);
		verify(runner).runAgent(eq("the-agent"), any(), eq("hi"), isNull(), toolCalling.capture());
		assertEquals("mcp:sales/the-agent", toolCalling.getValue().getCaller(),
				"a tool without a name of its own is named after the agent");
	}

	@Test
	void eachCallGetsAContainerOfItsOwn() throws Exception {
		runningInPlace();
		when(runner.runNetwork(any(String.class), any(), any(), any())).thenReturn("answered");
		ToolCallback tool = networkTool(null);

		tool.call("{\"request\":\"one\"}", new ToolContext(Map.of()));
		tool.call("{\"request\":\"two\"}", new ToolContext(Map.of()));

		ArgumentCaptor<GeboToolCalling> toolCalling = ArgumentCaptor.forClass(GeboToolCalling.class);
		verify(runner, times(2)).runNetwork(eq("the-network"), any(), isNull(), toolCalling.capture());
		assertEquals("mcp:sales/the-network", toolCalling.getValue().getCaller(),
				"a tool without a name of its own is named after the network");
		assertNotSame(toolCalling.getAllValues().get(0), toolCalling.getAllValues().get(1),
				"a run's records are its own");
	}
}
