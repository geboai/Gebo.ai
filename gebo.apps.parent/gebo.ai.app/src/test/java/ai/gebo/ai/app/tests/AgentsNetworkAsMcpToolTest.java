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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.mcpserver.model.GeboMCPAgentTool;
import ai.gebo.architecture.mcpserver.model.GeboMCPAgentsNetworkTool;
import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.architecture.mcpserver.runtime.GeboMCPAgentAsToolProvider;
import ai.gebo.architecture.mcpserver.runtime.GeboMCPAgentsNetworkAsToolsProvider;
import ai.gebo.llms.abstraction.layer.tests.TestChatModel;
import ai.gebo.llms.agent.chat.service.impl.ReportWriterReactiveAgentServiceImpl;
import ai.gebo.llms.agent.standard.services.GTextAgentsNetworkRunner;
import io.modelcontextprotocol.server.McpServerFeatures.SyncToolSpecification;

/**
 * A network of agents, or a single agent, is served outside a chat as text in,
 * text out: the runner answers with the text the network wrote, and the MCP
 * providers export networks and agents as tools taking a request in natural
 * language.
 * <p>
 * Like {@link DefaultAgentsNetworkTest} the fake model drives the standard
 * default network through the markers of its prompts: the coordinator routes to
 * the report writer, the writer answers with a marker.
 */
@TestPropertySource(properties = { "ai.gebo.agents.standard.enabled=true",
		"ai.gebo.agents.standard.default-chat-network-of-agents=DEFAULT_AGENTS_NETWORK" })
public class AgentsNetworkAsMcpToolTest extends AbstractBaseTestLLmsIntegrationTests {

	private static final String DEFAULT_NETWORK = "DEFAULT_AGENTS_NETWORK";
	private static final String ANSWER_MARKER = "GEBO_TEST_TEXT_NETWORK_ANSWER";
	private static final String WRITING_INSTRUCTION = "Write a concise direct answer to the request.";

	@Autowired
	private GTextAgentsNetworkRunner runner;
	@Autowired
	private IAgentsNetworkDao networkDao;
	@Autowired
	private GeboMCPAgentsNetworkAsToolsProvider networkToolsProvider;
	@Autowired
	private GeboMCPAgentAsToolProvider agentToolsProvider;

	@Override
	protected void beforeEachCallback() throws Exception {
		TestChatModel.setGlobalResponseLogic(prompt -> {
			if (prompt == null) {
				return "";
			}
			if (prompt.contains("coordinator-controller")) {
				return "{\n" //
						+ "  \"" + ReportWriterReactiveAgentServiceImpl.REPORT_WRITER_NETWORK_AGENT_SERVICE + "\": {\n" //
						+ "    \"deliveryOrder\": 1,\n" //
						+ "    \"concurrency\": \"SERIAL\",\n" //
						+ "    \"commandData\": \"" + WRITING_INSTRUCTION + "\"\n" //
						+ "  }\n" //
						+ "}";
			}
			if (prompt.contains("Writer/Reporter")) {
				return ANSWER_MARKER + ": the capital of France is Paris.";
			}
			return "";
		});
	}

	@Override
	protected void afterEachCallback() throws Exception {
		TestChatModel.clearGlobalResponseLogic();
	}

	@Test
	public void testTheRunnerAnswersWithTheTextTheNetworkWrote() throws Exception {
		GAgentsNetwork network = networkDao.findByCode(DEFAULT_NETWORK);
		assertNotNull(network, "The standard default network is installed");

		String answer = runner.runNetwork(DEFAULT_NETWORK, "What is the capital of France?", null);

		assertNotNull(answer, "The network answers");
		assertTrue(answer.contains(ANSWER_MARKER), "The answer is the text the network's writer produced: " + answer);
	}

	@Test
	public void testASingleAgentRunsAsANetworkOfItsOwn() throws Exception {
		GAgentsNetwork network = networkDao.findByCode(DEFAULT_NETWORK);
		String writerAgent = network.getAgents().stream()
				.map(participant -> participant.getAgentConfigCode())
				.filter(code -> code != null && code.toLowerCase().contains("writer")).findFirst().orElse(null);
		assertNotNull(writerAgent, "The default network has a writer agent, found among " + network.getAgents().stream()
				.map(participant -> participant.getAgentConfigCode()).toList());

		String answer = runner.runAgent(writerAgent, "test-single-", "What is the capital of France?", null);

		assertNotNull(answer, "The agent answers");
		assertTrue(answer.contains(ANSWER_MARKER), "The answer is the text the writer produced: " + answer);
	}

	@Test
	public void testNetworksAndAgentsAreExportedAsMcpTools() {
		GeboMCPServerConfig config = new GeboMCPServerConfig();
		config.setExportedUniqueRelativeUrl("test-server");
		GeboMCPAgentsNetworkTool networkTool = new GeboMCPAgentsNetworkTool();
		networkTool.setAgentsNetworkCode(DEFAULT_NETWORK);
		networkTool.setToolName("ask_default_network");
		GeboMCPAgentsNetworkTool missingNetwork = new GeboMCPAgentsNetworkTool();
		missingNetwork.setAgentsNetworkCode("NO_SUCH_NETWORK");
		config.setAgentNetworkAsTools(List.of(networkTool, missingNetwork));

		GAgentsNetwork network = networkDao.findByCode(DEFAULT_NETWORK);
		String anyAgent = network.getAgents().get(0).getAgentConfigCode();
		GeboMCPAgentTool agentTool = new GeboMCPAgentTool();
		agentTool.setAgentConfigCode(anyAgent);
		GeboMCPAgentTool missingAgent = new GeboMCPAgentTool();
		missingAgent.setAgentConfigCode("NO_SUCH_AGENT");
		config.setAgentAsTools(List.of(agentTool, missingAgent));

		List<SyncToolSpecification> networkTools = networkToolsProvider.buildTools(config);
		assertEquals(1, networkTools.size(), "The existing network is exported, the missing one is skipped");
		assertEquals("ask_default_network", networkTools.get(0).tool().name(), "The tool has the configured name");
		assertTrue(networkTools.get(0).tool().description().contains("Network of agents"));
		assertTrue(String.valueOf(networkTools.get(0).tool().inputSchema()).contains("request"),
				"The tool takes a request in natural language");

		List<SyncToolSpecification> agentTools = agentToolsProvider.buildTools(config);
		assertEquals(1, agentTools.size(), "The existing agent is exported, the missing one is skipped");
		assertEquals(anyAgent, agentTools.get(0).tool().name(), "Without a name the tool is named after the agent");
		assertTrue(agentTools.get(0).tool().description().startsWith("Agent: "));
	}
}
