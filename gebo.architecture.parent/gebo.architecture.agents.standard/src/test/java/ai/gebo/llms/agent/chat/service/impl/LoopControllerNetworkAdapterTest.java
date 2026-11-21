/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.chat.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.architecture.agents.services.AgentAnswersText;
import ai.gebo.architecture.agents.services.IGAgentCommands;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGNetworkAgentService;
import ai.gebo.architecture.agents.services.IGNetworkOfAgentsSuspensionHandler;
import ai.gebo.architecture.agents.services.IGReactiveAgentService;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.agent.chat.service.ILoopControllerReactiveAgentService;
import ai.gebo.llms.agent.chat.service.IReportWriterReactiveAgentService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * Pins the network adapter of the loop controller: the controller's commands are
 * given to the network in place of its answer, with a message executing it again
 * after them; executed again it is told what the agents answered, over the same
 * run; an agent that commands nobody is adapted as the report writer is.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class LoopControllerNetworkAdapterTest {
	private static final GeboToolCalling RUN_TOOL_CALLING = GeboToolCalling.builder()
			.caller("test:LoopControllerNetworkAdapterTest").build();
	private static final String REQUEST = "A deck on the Q3 figures, please.";
	private final IGReactiveAgentService<String, GeboChatMessageEnvelope> agent = mock(IGReactiveAgentService.class);
	private final IGNetworkOfAgentsSuspensionHandler handler = mock(IGNetworkOfAgentsSuspensionHandler.class);
	private final Sinks.Many<IGPartialOperation<GeboChatMessageEnvelope>> sink = Sinks.many().unicast()
			.onBackpressureBuffer();
	private LoopControllerNetworkAdapter adapter = new LoopControllerNetworkAdapter(agent, sink);
	/** The controller's private session: the network records each of its turns in it, and a respawn restores it. */
	private final AgentPrivateSessionContext<String, GeboChatMessageEnvelope> mine = new AgentPrivateSessionContext<String, GeboChatMessageEnvelope>();
	private final AgentNetworkParticipant me = new AgentNetworkParticipant();
	private final GAgentConfig config = new GAgentConfig();
	private final GAgentsNetwork network = new GAgentsNetwork();
	private final AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext("session-1");
	private final Map<String, RuntimeAgentInfos> allocated = new LinkedHashMap<>();
	/** What the agent was executed with, turn after turn. */
	private final List<Object[]> executions = new ArrayList<>();

	private void peer(String name) {
		IGNetworkAgentService service = mock(IGNetworkAgentService.class);
		when(service.getInputType()).thenReturn(String.class);
		when(service.getAgentCapabilities(any())).thenReturn(new AgentCapabilities());
		GAgentConfig peerConfig = new GAgentConfig();
		peerConfig.setCode(name);
		peerConfig.setDescription("Writes " + name);
		AgentNetworkParticipant participant = new AgentNetworkParticipant();
		participant.setAgentConfigCode(name);
		allocated.put(name, new RuntimeAgentInfos(service, peerConfig, participant, 0, 0));
	}

	private void controllerReaching(String... agents) {
		me.setAgentConfigCode("loopController");
		me.setController(true);
		me.setOutputNode(true);
		me.setCommunicationList(List.of(agents));
		for (String name : agents) {
			peer(name);
		}
	}

	private static GeboChatMessageEnvelope answer(String text) {
		GeboChatResponse response = new GeboChatResponse();
		response.setQueryResponse(text);
		return new GeboChatMessageEnvelope(response);
	}

	/** The agent's execution: what its model's tools do, then the answer it streams. */
	private void theAgent(java.util.function.Function<GeboToolCalling, String> executes) throws Exception {
		when(handler.takeResolvedConditionsOf(any())).thenReturn(List.of());
		when(agent.execute(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
				.thenAnswer(invocation -> {
					executions.add(invocation.getArguments());
					final String text = executes.apply(invocation.getArgument(9, GeboToolCalling.class));
					return Flux.just(IGPartialOperation.of(answer(text), false));
				});
	}

	/** A turn of the controller, as the network runs it: what it sends is recorded in its private session. */
	private List<AgentsExchangeMessage<GeboChatMessageEnvelope>> turn(int contributionNr) throws Exception {
		final AgentsExchangeMessage<String> message = AgentsExchangeMessage.of(session, "the-input",
				me.getNetworkAgentName(), REQUEST, MessageSemantic.EXECUTE_AND_SHARE_RESULT);
		final List<AgentsExchangeMessage<GeboChatMessageEnvelope>> sent = adapter.onMessage(
				IChatRequestContext.of(REQUEST), config, message, contributionNr, network, me, state -> {
				}, session, mine, null, IGAgentsNetworkRuntimeDao.of(allocated), RUN_TOOL_CALLING, handler);
		for (AgentsExchangeMessage out : sent) {
			((AgentPrivateSessionContext) mine).addInteraction(message, contributionNr, out.getPayload());
		}
		return sent;
	}

	private GeboToolCalling toolCallingOf(int execution) {
		return (GeboToolCalling) executions.get(execution - 1)[9];
	}

	@Test
	void theControllersCommandsAreGivenToTheNetworkAndItIsExecutedAgainToldTheAnswers() throws Exception {
		controllerReaching("presentations", "spreadsheets");
		theAgent(toolCalling -> {
			if (executions.size() == 1) {
				IGAgentCommands commands = toolCalling.getProvider().get(IGAgentCommands.class);
				assertNull(commands.command("presentations", "A deck of three slides on Q3."));
				assertNull(commands.command("spreadsheets", "The Q3 figures as a workbook."));
				return "I asked the two agents.";
			}
			return "The deck q3.pptx and the workbook q3.xlsx are ready.";
		});

		List<AgentsExchangeMessage<GeboChatMessageEnvelope>> first = turn(3);

		assertEquals(3, first.size(), "the two commands and the controller's own next turn, no answer");
		assertEquals(List.of("presentations", "spreadsheets", "loopController"),
				first.stream().map(AgentsExchangeMessage::getToAgent).toList());
		assertEquals(List.of(1, 1, 2), first.stream().map(AgentsExchangeMessage::getExecutionOrder).toList(),
				"the agents commanded work together, then the controller is executed again");
		assertTrue(first.stream().allMatch(x -> x.getMessageSemantic() == MessageSemantic.EXECUTE_AND_SHARE_RESULT));
		assertTrue(first.stream().allMatch(x -> "loopController".equals(x.getFromAgent())));
		assertEquals("A deck of three slides on Q3.", ((AgentsExchangeMessage) first.get(0)).getPayload());
		assertEquals(REQUEST, ((AgentsExchangeMessage) first.get(2)).getPayload(),
				"executed again on the message of the turn");
		assertEquals(REQUEST, executions.get(0)[2], "nothing to tell at the first execution");
		assertEquals(List.of("askAgent_presentations", "askAgent_spreadsheets"), toolCallingOf(1).getMandatoryTools()
				.stream().map(x -> x.getToolDefinition().name()).toList(), "one tool for each agent it reaches");
		assertEquals("test:LoopControllerNetworkAdapterTest", toolCallingOf(1).getProvider().caller());

		// the network delivers the commands: the agents answer, one of them only
		session.addContribution(AgentsExchangeMessage.of(session, "presentations", "loopController",
				"q3.pptx written, three slides.", MessageSemantic.RESPONSE), 4);

		List<AgentsExchangeMessage<GeboChatMessageEnvelope>> second = turn(6);

		String told = "You asked the agent presentations: A deck of three slides on Q3.\n"
				+ "The agent presentations answered: q3.pptx written, three slides.\n\n"
				+ "You asked the agent spreadsheets: The Q3 figures as a workbook.\n"
				+ "The agent spreadsheets answered: (no answer: the agent failed, or is still waiting for something)\n\n"
				+ REQUEST;
		assertEquals(3, AgentAnswersText.firstTurnOf(mine), "the answers are read after the controller's first turn");
		assertEquals(told, executions.get(1)[2]);
		assertEquals(told, ((IChatRequestContext) executions.get(1)[0]).getActualUserRequest(),
				"the loop's model reads the request from the context");
		assertNotNull(toolCallingOf(2).getProvider().get(LoopControllerRun.Execution.class));
		assertSame(toolCallingOf(1).getProvider().get(LoopControllerRun.Execution.class).run(),
				toolCallingOf(2).getProvider().get(LoopControllerRun.Execution.class).run(),
				"the same run at every execution");
		assertFalse(toolCallingOf(2).getProvider().get(LoopControllerRun.Execution.class).commands().isCommanding(),
				"each execution has its own sink of commands");
		assertEquals(1, second.size(), "nothing commanded: the answer is the agent's message");
		assertEquals(MessageSemantic.RESPONSE, second.get(0).getMessageSemantic());
		assertEquals("The deck q3.pptx and the workbook q3.xlsx are ready.",
				((GeboChatResponse) second.get(0).getPayload().getContent()).getQueryResponse());
	}

	@Test
	void aRespawnedRunTellsTheControllerTheAnswersItsSessionsKeep() throws Exception {
		controllerReaching("presentations", "spreadsheets");
		// what an earlier run of the conversation left in the session answers nothing asked in this one
		session.addContribution(AgentsExchangeMessage.of(session, "presentations", "loopController",
				"An older deck, of another request.", MessageSemantic.RESPONSE), 1);
		theAgent(toolCalling -> {
			if (executions.size() == 1) {
				assertNull(toolCalling.getProvider().get(IGAgentCommands.class).command("presentations",
						"A deck of three slides on Q3."));
				return "I asked the presentations agent.";
			}
			return "The deck q3.pptx is ready.";
		});

		turn(3);

		// the presentations agent asks the user a question: the run is suspended, then respawned
		// in another service - another adapter, nothing in memory - with the shared session, where
		// the agent's answer is, and the controller's private session, where its turn is
		session.addContribution(AgentsExchangeMessage.of(session, "presentations", "loopController",
				"q3.pptx written, in the corporate template the user chose.", MessageSemantic.RESPONSE), 5);
		adapter = new LoopControllerNetworkAdapter(agent, Sinks.many().unicast().onBackpressureBuffer());

		List<AgentsExchangeMessage<GeboChatMessageEnvelope>> out = turn(6);

		String told = "The agent presentations answered: q3.pptx written, in the corporate template the user chose."
				+ "\n\n" + REQUEST;
		assertEquals(told, executions.get(1)[2], "the answer is told, though what was asked is not remembered");
		assertEquals(told, ((IChatRequestContext) executions.get(1)[0]).getActualUserRequest());
		LoopControllerRun respawned = toolCallingOf(2).getProvider().get(LoopControllerRun.Execution.class).run();
		assertEquals(1, respawned.loop().iterations(),
				"the loop goes on after the turn the private session keeps: its iterations are not counted again");
		assertEquals(1, out.size());
		assertEquals(MessageSemantic.RESPONSE, out.get(0).getMessageSemantic());
	}

	@Test
	void anAgentThatIsNotTheControllerOrReachesNobodyIsAdaptedAsTheReportWriter() throws Exception {
		controllerReaching("presentations");
		me.setController(false);
		theAgent(toolCalling -> "The answer.");

		List<AgentsExchangeMessage<GeboChatMessageEnvelope>> out = turn(1);

		assertEquals(1, out.size());
		assertEquals(MessageSemantic.RESPONSE, out.get(0).getMessageSemantic());
		assertNull(toolCallingOf(1).getProvider().get(IGAgentCommands.class), "no command to give");
		assertNull(toolCallingOf(1).getProvider().get(LoopControllerRun.Execution.class));
		assertTrue(toolCallingOf(1).getMandatoryTools().isEmpty());

		me.setController(true);
		me.setCommunicationList(List.of());
		turn(2);

		assertNull(toolCallingOf(2).getProvider().get(IGAgentCommands.class), "a controller reaching nobody");
	}

	@Test
	void aLoopControllerIsAdaptedByItsOwnFactoryAndAReportWriterByTheReportWritersOne() {
		IGReactiveAgentService controller = mock(ILoopControllerReactiveAgentService.class);
		IGReactiveAgentService writer = mock(IReportWriterReactiveAgentService.class);
		LoopControllerNetworkAdapterFactory own = new LoopControllerNetworkAdapterFactory();
		ReportWriterReactiveAgentServiceNetworkAdapterFactory writers = new ReportWriterReactiveAgentServiceNetworkAdapterFactory();

		assertTrue(own.canBeAdapted(controller));
		assertFalse(writers.canBeAdapted(controller), "no two factories for one agent");
		assertFalse(own.canBeAdapted(writer));
		assertTrue(writers.canBeAdapted(writer));
		assertTrue(own.create(controller).getAdapter() instanceof LoopControllerNetworkAdapter);
	}
}
