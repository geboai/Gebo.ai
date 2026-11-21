/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.architecture.agents.services.GAgentCommandsSink;
import ai.gebo.architecture.agents.services.IGAgentCommands;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGNetworkAgentService;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;

/**
 * Pins the tools an agent hands work to the agents of its communication list with:
 * one for each agent it reaches that takes a text, described by what that agent
 * does; a command taken ends the model's call, one refused leaves it going on.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class AgentCommandToolsTest {
	private final Map<String, RuntimeAgentInfos> allocated = new LinkedHashMap<>();

	private void agent(String name, Class<?> inputType, String description, String... capabilities) {
		IGNetworkAgentService service = mock(IGNetworkAgentService.class);
		when(service.getInputType()).thenReturn(inputType);
		when(service.getId()).thenReturn(name + "Service");
		AgentCapabilities told = new AgentCapabilities();
		for (String capability : capabilities) {
			told.addCapability(capability);
		}
		when(service.getAgentCapabilities(any())).thenReturn(told);
		GAgentConfig config = new GAgentConfig();
		config.setCode(name);
		config.setDescription(description);
		AgentNetworkParticipant participant = new AgentNetworkParticipant();
		participant.setAgentConfigCode(name);
		allocated.put(name, new RuntimeAgentInfos(service, config, participant, 0, 0));
	}

	private AgentNetworkParticipant controllerReaching(String... agents) {
		AgentNetworkParticipant controller = new AgentNetworkParticipant();
		controller.setAgentConfigCode("the-controller");
		controller.setController(true);
		controller.setCommunicationList(new ArrayList<>(List.of(agents)));
		return controller;
	}

	private IGAgentsNetworkRuntimeDao network() {
		return IGAgentsNetworkRuntimeDao.of(allocated);
	}

	private static ToolContext inACallWith(IGAgentCommands loaded) {
		return new ToolContext(GeboToolCalling.builder().provide(IGAgentCommands.class, loaded).build()
				.toolsContext(Map.of()));
	}

	private static AgentCommandTools.Param request(String text) {
		AgentCommandTools.Param param = new AgentCommandTools.Param();
		param.setRequest(text);
		return param;
	}

	@Test
	void anAgentIsGivenOneToolForEachAgentItReachesThatTakesAText() {
		agent("presentations", String.class, "Writes presentations", "Create and edit pptx, ppt and odp files");
		agent("searcher", List.class, "Searches with a structured command");
		AgentNetworkParticipant controller = controllerReaching("presentations", "searcher", "not-allocated",
				"presentations");

		assertEquals(List.of("presentations"), AgentCommandTools.reachable(controller, network()));
		List<ToolCallback> tools = AgentCommandTools.of(controller, network());

		assertEquals(List.of("askAgent_presentations"), tools.stream().map(x -> x.getToolDefinition().name()).toList());
		String description = tools.get(0).getToolDefinition().description();
		assertTrue(description.startsWith("Hands a piece of work to the agent presentations: Writes presentations."
				+ " It can: Create and edit pptx, ppt and odp files."), description);
		assertTrue(description.endsWith(AgentCommandTools.HOW_TO_ASK), "the model is told what the agent needs");
		assertTrue(description.contains("Ask only for what the user asked"),
				"and to add nothing of its own to what the user asked");
		assertTrue(tools.get(0).getToolDefinition().inputSchema().contains("request"));
	}

	@Test
	void anAgentReachingNobodyHasNoTool() {
		agent("presentations", String.class, "Writes presentations");
		AgentNetworkParticipant alone = controllerReaching();

		assertEquals(List.of(), AgentCommandTools.of(alone, network()));
		assertEquals(List.of(), AgentCommandTools.of(null, network()));
		assertEquals(List.of(), AgentCommandTools.of(controllerReaching("presentations"), null));
	}

	@Test
	void theToolNamesAreOnesAModelTakesAndNoTwoAreTheSame() {
		String longName = "an-agent-with-a-very-long-contextual-name-that-goes-well-beyond-what-a-tool-name-may-be";
		Map<String, String> names = AgentCommandTools.toolNames(List.of("text documents", "text.documents", longName));

		assertEquals(List.of("askAgent_text_documents", "askAgent_text_documents_2"),
				new ArrayList<>(names.keySet()).subList(0, 2));
		assertEquals("text.documents", names.get("askAgent_text_documents_2"));
		String cut = new ArrayList<>(names.keySet()).get(2);
		assertEquals(64, cut.length());
		assertEquals(longName, names.get(cut));
	}

	@Test
	void aCommandTakenEndsTheCallAndIsKeptForTheAgent() {
		GAgentCommandsSink commands = new GAgentCommandsSink(List.of("presentations", "spreadsheets"));

		assertFalse(commands.endsTheCall(), "nothing commanded yet");
		assertEquals(AgentCommandTools.HANDED_OVER,
				AgentCommandTools.ask("presentations", request("  A deck of three slides on Q3.  "), inACallWith(commands)));
		assertEquals(AgentCommandTools.HANDED_OVER,
				AgentCommandTools.ask("spreadsheets", request("The Q3 figures as a workbook."), inACallWith(commands)));

		assertTrue(commands.endsTheCall(), "the model is not called again: the network delivers the commands");
		assertEquals(List.of(new IGAgentCommands.Command("presentations", "A deck of three slides on Q3."),
				new IGAgentCommands.Command("spreadsheets", "The Q3 figures as a workbook.")), commands.commanded());
	}

	@Test
	void aCommandNotTakenLeavesTheCallGoingOnAndTellsTheModelWhy() {
		GAgentCommandsSink commands = new GAgentCommandsSink(List.of("presentations"));

		assertEquals(AgentCommandTools.NO_REQUEST,
				AgentCommandTools.ask("presentations", request("  "), inACallWith(commands)));
		assertEquals(AgentCommandTools.NO_REQUEST, AgentCommandTools.ask("presentations", null, inACallWith(commands)));
		assertTrue(AgentCommandTools.ask("spreadsheets", request("A workbook."), inACallWith(commands))
				.startsWith("This agent cannot be reached from here"), "an agent out of the communication list");
		commands.refuseWith("This is your last step.");
		assertEquals("This is your last step.",
				AgentCommandTools.ask("presentations", request("A deck."), inACallWith(commands)));

		assertFalse(commands.endsTheCall());
		assertEquals(List.of(), commands.commanded());

		commands.refuseWith(null);
		assertEquals(AgentCommandTools.HANDED_OVER,
				AgentCommandTools.ask("presentations", request("A deck."), inACallWith(commands)));
		assertTrue(commands.endsTheCall(), "taken again once the refusal is lifted");
	}

	@Test
	void whereNoAgentCanBeCommandedTheToolSaysSo() {
		assertEquals(AgentCommandTools.NOT_AVAILABLE,
				AgentCommandTools.ask("presentations", request("A deck."), new ToolContext(Map.of())));
	}

	@Test
	void theToolGivenToTheModelCommandsItsAgent() {
		agent("presentations", String.class, "Writes presentations");
		GAgentCommandsSink commands = new GAgentCommandsSink(List.of("presentations"));
		ToolCallback tool = AgentCommandTools.of(controllerReaching("presentations"), network()).get(0);

		String told = tool.call("{\"request\":\"A deck of three slides on Q3.\"}", inACallWith(commands));

		assertTrue(told.contains("handed to the agent"), told);
		assertEquals(List.of(new IGAgentCommands.Command("presentations", "A deck of three slides on Q3.")),
				commands.commanded());
	}
}
