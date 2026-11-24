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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.a2aproject.sdk.spec.Part;
import org.a2aproject.sdk.spec.TextPart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.architecture.a2aserver.model.A2AExportedAgent;
import ai.gebo.architecture.a2aserver.model.A2AServerConfig;
import ai.gebo.architecture.a2aserver.runtime.A2AInboundResources;
import ai.gebo.architecture.a2aserver.runtime.A2ATaskBridge;
import ai.gebo.architecture.mcpserver.model.GeboMCPAgentsNetworkTool;
import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.architecture.mcpserver.runtime.GeboMCPAgentsNetworkAsToolsProvider;
import ai.gebo.llms.abstraction.layer.tests.TestChatModel;
import ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner.ChatEnvironmentRun;
import ai.gebo.llms.agent.standard.services.GTextAgentsNetworkRunner;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import io.modelcontextprotocol.server.McpServerFeatures.SyncToolSpecification;
import io.modelcontextprotocol.spec.McpSchema;

/**
 * Certifies that a network exported with no chat profile works with the default
 * one, over the real path: the network is exported over A2A and as an MCP tool
 * stating no chat profile, it is called, and the conversation its run happens in
 * is of the default chat profile of the installation.
 */
@TestPropertySource(properties = { "ai.gebo.agents.standard.enabled=true",
		"ai.gebo.agents.standard.default-chat-network-of-agents=AGENTIC_LOOP_AGENTS_NETWORK" })
public class ExportsWithoutChatProfileIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	private static final String NETWORK = "AGENTIC_LOOP_AGENTS_NETWORK";
	private static final String REQUEST = "GEBO_TEST_NO_PROFILE_REQUEST: say what the weather is.";
	private static final String ANSWER = "GEBO_TEST_NO_PROFILE_ANSWER: it is sunny.";

	@Autowired
	private A2ATaskBridge taskBridge;
	@Autowired
	private GeboMCPAgentsNetworkAsToolsProvider networkToolsProvider;
	@Autowired
	private GUserChatSessionRepository conversations;
	@Autowired
	private IGChatSessionLifeCycleService conversationsLifeCycle;

	@Override
	protected void beforeEachCallback() throws Exception {
		TestChatModel.setGlobalResponseLogic(prompt -> prompt != null && prompt.contains(REQUEST) ? ANSWER : "");
	}

	@Override
	protected void afterEachCallback() throws Exception {
		TestChatModel.clearGlobalResponseLogic();
	}

	/**
	 * Removes the conversations of the default profile as the platform removes a
	 * conversation, with what it stored: the next one is given the same code.
	 */
	private void removeTheConversationsOfTheDefaultProfile() throws Exception {
		for (String code : conversationsOfTheDefaultProfile()) {
			conversationsLifeCycle.removeChatSession(code);
		}
	}

	@Test
	public void anA2AExportStatingNoChatProfileWorksWithTheDefaultOne() throws Exception {
		A2AExportedAgent exported = new A2AExportedAgent();
		exported.setKind(A2AExportedAgent.Kind.NETWORK);
		exported.setNetworkCode(NETWORK);
		exported.setSkillName("the_loop");
		A2AServerConfig server = new A2AServerConfig();
		server.setExportedRelativeUrl("test-no-profile");
		server.setExportedAgents(List.of(exported));

		final ChatEnvironmentRun run = taskBridge.runInChatEnvironment(server, null,
				A2AInboundResources.of(List.<Part<?>>of(new TextPart(REQUEST))), null,
				GTextAgentsNetworkRunner.LOGGING_SINK);
		assertNotNull(run, "the export is a network: it runs in a conversation");
		try {
			final GUserChatSession conversation = conversations.findById(run.getChatContextCode()).orElse(null);
			assertNotNull(conversation, "the run happened in a conversation");
			assertEquals(GChatProfileConfiguration.DEFAULT_CHAT_PROFILE_CODE, conversation.getChatProfileCode(),
					"of the default chat profile");
		} finally {
			run.dispose();
			removeTheConversationsOfTheDefaultProfile();
		}
	}

	@Test
	public void anMcpToolStatingNoChatProfileWorksWithTheDefaultOne() throws Exception {
		GeboMCPAgentsNetworkTool tool = new GeboMCPAgentsNetworkTool();
		tool.setAgentsNetworkCode(NETWORK);
		tool.setToolName("ask_the_loop");
		GeboMCPServerConfig config = new GeboMCPServerConfig();
		config.setExportedUniqueRelativeUrl("test-no-profile");
		config.setAgentNetworkAsTools(List.of(tool));
		final SyncToolSpecification specification = networkToolsProvider.buildTools(config).get(0);

		final Set<String> before = conversationsOfTheDefaultProfile();
		try {
			final McpSchema.CallToolResult result = specification.callHandler().apply(null,
					new McpSchema.CallToolRequest("ask_the_loop", Map.of("request", REQUEST)));

			assertFalse(Boolean.TRUE.equals(result.isError()), "the tool answers: " + result.content());
			assertTrue(result.content().toString().contains(ANSWER),
					"with the answer of the network: " + result.content());
			// a second call, the conversation of the first still there: one of its own again
			final McpSchema.CallToolResult again = specification.callHandler().apply(null,
					new McpSchema.CallToolRequest("ask_the_loop", Map.of("request", REQUEST)));
			assertFalse(Boolean.TRUE.equals(again.isError()), "the tool answers again: " + again.content());
			final Set<String> opened = conversationsOfTheDefaultProfile();
			opened.removeAll(before);
			assertEquals(2, opened.size(), "each call ran in a conversation of the default chat profile");
		} finally {
			removeTheConversationsOfTheDefaultProfile();
		}
	}

	private Set<String> conversationsOfTheDefaultProfile() {
		final Set<String> codes = new HashSet<>();
		for (GUserChatSession conversation : conversations.findAll()) {
			if (GChatProfileConfiguration.DEFAULT_CHAT_PROFILE_CODE.equals(conversation.getChatProfileCode())) {
				codes.add(conversation.getCode());
			}
		}
		return codes;
	}
}
