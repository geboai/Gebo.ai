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

	/** A network exported with a chat profile runs in a conversation and answers with the files of its run. */
	@Test
	void aNetworkWithAChatProfileAnswersWithTheFilesOfItsRun() throws Exception {
		runningInPlace();
		IAgentsNetworkDao networks = mock(IAgentsNetworkDao.class);
		when(networks.findByCode("the-network")).thenReturn(new GAgentsNetwork());
		GeboMCPAgentsNetworkTool export = new GeboMCPAgentsNetworkTool();
		export.setAgentsNetworkCode("the-network");
		export.setToolName("askSales");
		export.setChatProfileCode("sales-profile");
		GeboMCPServerConfig config = server();
		config.setAgentNetworkAsTools(List.of(export));

		ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse response = new ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse();
		response.setQueryResponse("Here is the chart.");
		ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner.ChatEnvironmentRun run = mock(
				ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner.ChatEnvironmentRun.class);
		when(run.getFrames()).thenReturn(reactor.core.publisher.Flux.empty());
		when(run.getResponse()).thenReturn(response);
		when(run.getChatContextCode()).thenReturn("chat-1");
		ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner chatRunner = mock(
				ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner.class);
		ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest request = new ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest();
		when(chatRunner.requestOf("draw the sales", "sales-profile", null)).thenReturn(request);
		when(chatRunner.run(eq("the-network"), eq(request), any(), any(), any())).thenReturn(run);
		ai.gebo.llms.agent.standard.services.ChatRunFiles runFiles = mock(
				ai.gebo.llms.agent.standard.services.ChatRunFiles.class);
		when(runFiles.of(response)).thenReturn(List.of(
				new ai.gebo.llms.agent.standard.services.ChatRunFiles.RunFile("chart.png", "image/png", new byte[] { 1, 2 }),
				new ai.gebo.llms.agent.standard.services.ChatRunFiles.RunFile("report.docx",
						"application/vnd.openxmlformats-officedocument.wordprocessingml.document", new byte[] { 3 })));

		GeboMCPAgentsNetworkAsToolsProvider provider = new GeboMCPAgentsNetworkAsToolsProvider(networks, runner,
				security, environment, factory);
		provider.setChatRunner(chatRunner);
		provider.setRunFiles(runFiles);
		var specification = provider.buildTools(config).get(0);
		assertEquals("askSales", specification.tool().name());
		var result = specification.callHandler().apply(null,
				new io.modelcontextprotocol.spec.McpSchema.CallToolRequest("askSales", Map.of("request", "draw the sales")));

		assertEquals(3, result.content().size());
		assertEquals("Here is the chart.",
				((io.modelcontextprotocol.spec.McpSchema.TextContent) result.content().get(0)).text());
		var picture = (io.modelcontextprotocol.spec.McpSchema.ImageContent) result.content().get(1);
		assertEquals("image/png", picture.mimeType());
		assertEquals(java.util.Base64.getEncoder().encodeToString(new byte[] { 1, 2 }), picture.data());
		var document = (io.modelcontextprotocol.spec.McpSchema.EmbeddedResource) result.content().get(2);
		assertEquals("gebo://conversation/chat-1/report.docx", document.resource().uri());
		verify(run).dispose();
		// the text runner was not asked: the run was the conversation's
		verify(runner, times(0)).runNetwork(any(String.class), any(), any(), any());

		// without the conversation runner, as where it is not deployed, the tool is the plain one
		provider.setChatRunner(null);
		assertEquals("askSales", provider.buildTools(config).get(0).tool().name());
	}
}
