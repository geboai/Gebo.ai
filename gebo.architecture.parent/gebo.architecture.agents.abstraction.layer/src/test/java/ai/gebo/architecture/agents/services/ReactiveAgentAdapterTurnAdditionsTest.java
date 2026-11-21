/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import java.util.Map;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.services.AgentAnswersText.AgentAnswer;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * Pins what an adapter may add to a turn of its reactive agent - more loaded in the
 * tool calling, more told before the request, messages in place of the response -
 * and what an agent that handed work to other agents is told of their answers.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class ReactiveAgentAdapterTurnAdditionsTest {
	private static final GeboToolCalling RUN_TOOL_CALLING = GeboToolCalling.builder()
			.caller("test:ReactiveAgentAdapterTurnAdditionsTest").build();
	private final IGReactiveAgentService<String, String> agent = mock(IGReactiveAgentService.class);
	private final IGNetworkOfAgentsSuspensionHandler handler = mock(IGNetworkOfAgentsSuspensionHandler.class);
	private final Sinks.Many<IGPartialOperation<String>> sink = Sinks.many().unicast().onBackpressureBuffer();
	private final AgentNetworkParticipant me = new AgentNetworkParticipant();
	private final GAgentConfig config = new GAgentConfig();
	private final GAgentsNetwork network = new GAgentsNetwork();
	private final AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext("session-1");

	/** An adapter adding to the turn: a sink of commands, something told first, the commands as messages. */
	private final class CommandingAdapter extends AbstractReactiveAgentServiceNetworkAdapter<String, String> {
		private final GAgentCommandsSink commands = new GAgentCommandsSink(List.of("the-writer"));

		private CommandingAdapter() {
			super(agent, String.class, String.class, sink);
		}

		@Override
		protected String extractResponse(List<IGPartialOperation<String>> buffered) {
			return buffered.isEmpty() ? null : buffered.get(buffered.size() - 1).getData();
		}

		@Override
		protected TurnAdditions<String, String> turnAdditions(AgentsExchangeMessage<String> msg,
				int actualContributionNr, GAgentsNetwork network, AgentNetworkParticipant contextAgentPersona,
				AgentsCollaborationSessionContext session, AgentPrivateSessionContext<String, String> mySessionContext,
				IGAgentsNetworkRuntimeDao agentsDao) {
			return new TurnAdditions<String, String>() {
				@Override
				public GeboToolCalling toolCalling(GeboToolCalling ofTheTurn) {
					return GeboToolCalling.merged(
							GeboToolCalling.builder().provide(IGAgentCommands.class, commands).build(), ofTheTurn);
				}

				@Override
				public String request(String ofTheTurn) {
					return "Told first.\n\n" + ofTheTurn;
				}

				@Override
				public IChatRequestContext context(IChatRequestContext ofTheTurn, String request) {
					return ofTheTurn.withActualUserRequest(request);
				}

				@Override
				public List<AgentsExchangeMessage<String>> messages(String response) {
					if (!commands.isCommanding()) {
						return null;
					}
					return commands.commanded().stream()
							.map(command -> AgentsExchangeMessage.of(session, "the-agent", command.toAgent(),
									(String) command.commandData(), MessageSemantic.EXECUTE_AND_SHARE_RESULT))
							.toList();
				}
			};
		}
	}

	private List<AgentsExchangeMessage<String>> run(AbstractReactiveAgentServiceNetworkAdapter<String, String> adapter)
			throws Exception {
		me.setAgentConfigCode("the-agent");
		config.setCode("the-agent");
		network.setCode("the-network");
		when(handler.takeResolvedConditionsOf(any())).thenReturn(List.of());
		return adapter.onMessage(IChatRequestContext.of("What is new?"), config,
				AgentsExchangeMessage.of(session, "the-router", "the-agent", "What is new?",
						MessageSemantic.EXECUTE_AND_SHARE_RESULT),
				0, network, me, state -> {
				}, session, new AgentPrivateSessionContext<String, String>(), null,
				mock(IGAgentsNetworkRuntimeDao.class), RUN_TOOL_CALLING, handler);
	}

	@Test
	void whatTheAdapterAddsReachesTheAgentBesideWhatTheTurnCarries() throws Exception {
		when(agent.execute(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
				.thenReturn(Flux.just(IGPartialOperation.of("the answer", true)));

		List<AgentsExchangeMessage<String>> out = run(new CommandingAdapter());

		ArgumentCaptor<IChatRequestContext> context = ArgumentCaptor.forClass(IChatRequestContext.class);
		ArgumentCaptor<GeboToolCalling> toolCalling = ArgumentCaptor.forClass(GeboToolCalling.class);
		verify(agent).execute(context.capture(), any(), eq("Told first.\n\nWhat is new?"), any(), any(), any(), any(),
				any(), any(), toolCalling.capture());
		assertEquals("Told first.\n\nWhat is new?", context.getValue().getActualUserRequest());
		assertNotNull(toolCalling.getValue().getProvider().get(IGAgentCommands.class), "what the adapter loaded");
		assertNotNull(toolCalling.getValue().getProvider().get(IGWaitExternalCondition.class), "what the turn loaded");
		assertEquals("test:ReactiveAgentAdapterTurnAdditionsTest", toolCalling.getValue().getProvider().caller());
		assertEquals(1, out.size(), "nothing commanded: the response is the agent's message");
		assertEquals("the answer", out.get(0).getPayload());
		assertEquals(MessageSemantic.RESPONSE, out.get(0).getMessageSemantic());
		assertEquals("the-router", out.get(0).getToAgent());
	}

	@Test
	void theMessagesTheAdapterGivesTakeThePlaceOfTheResponse() throws Exception {
		when(agent.execute(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
				.thenAnswer(invocation -> {
					// the agent's tool, in the agent's stream, hands work to another agent
					GeboToolCalling toolCalling = invocation.getArgument(9, GeboToolCalling.class);
					assertNull(toolCalling.getProvider().get(IGAgentCommands.class).command("the-writer",
							"Write the report."));
					return Flux.just(IGPartialOperation.of("I asked the writer.", true));
				});

		List<AgentsExchangeMessage<String>> out = run(new CommandingAdapter());

		assertEquals(1, out.size());
		assertEquals("the-writer", out.get(0).getToAgent());
		assertEquals("Write the report.", out.get(0).getPayload());
		assertEquals(MessageSemantic.EXECUTE_AND_SHARE_RESULT, out.get(0).getMessageSemantic());
	}

	@Test
	void theAnswersAreReadInTheSharedSessionFromTheFirstTurnThePrivateSessionKeeps() {
		// the agent's first turn of this run is turn 3: the session remembers an earlier run of
		// the conversation, whose contributions answer nothing asked in this one
		session.addContribution(AgentsExchangeMessage.of(session, "the-writer", "the-agent", "An older report.",
				MessageSemantic.RESPONSE), 2);
		session.addContribution(AgentsExchangeMessage.of(session, "the-writer", "the-agent", "report.docx written.",
				MessageSemantic.RESPONSE), 4);
		session.addContribution(AgentsExchangeMessage.of(session, "someone-else", "the-agent", "Not asked by me.",
				MessageSemantic.RESPONSE), 4);
		session.addContribution(AgentsExchangeMessage.of(session, "the-writer", "the-agent", "summary.docx written.",
				MessageSemantic.RESPONSE), 5);
		AgentPrivateSessionContext<String, String> mine = new AgentPrivateSessionContext<String, String>();
		assertNull(AgentAnswersText.firstTurnOf(mine), "no turn made yet");
		assertEquals(List.of(), AgentAnswersText.answersIn(session, AgentAnswersText.firstTurnOf(mine),
				List.of("the-writer", "the-painter"), Map.of()), "an agent that asked nothing has no answer");
		// the network records each message of a turn in the agent's private session
		mine.addInteraction(null, 3, "Write the report.");
		mine.addInteraction(null, 3, "Draw the cover.");
		mine.addInteraction(null, 3, "Write the summary.");
		assertEquals(3, AgentAnswersText.firstTurnOf(mine));
		assertEquals(1, AgentAnswersText.turnsOf(mine));

		// what the agent asked is known in the run that asked it
		List<AgentAnswer> answers = AgentAnswersText.answersIn(session, AgentAnswersText.firstTurnOf(mine),
				List.of("the-writer", "the-painter"), Map.of("the-writer",
						List.of("Write the report.", "Write the summary."), "the-painter", List.of("Draw the cover.")));

		assertEquals(List.of(new AgentAnswer("the-writer", "Write the report.", "report.docx written."),
				new AgentAnswer("the-writer", "Write the summary.", "summary.docx written."),
				new AgentAnswer("the-painter", "Draw the cover.", null)), answers);
		assertEquals(
				"You asked the agent the-writer: Write the report.\nThe agent the-writer said: report.docx written."
						+ "\n\nYou asked the agent the-writer: Write the summary.\nThe agent the-writer said: "
						+ "summary.docx written."
						+ "\n\nYou asked the agent the-painter: Draw the cover.\nThe agent the-painter"
						+ AgentAnswersText.NO_ANSWER + "\n\nWhat is new?",
				AgentAnswersText.prepend(answers, "What is new?"));
		assertEquals("What is new?", AgentAnswersText.prepend(List.of(), "What is new?"));

		// a respawned run has the two sessions back, not what the agent asked: the answers are told all the same
		List<AgentAnswer> respawned = AgentAnswersText.answersIn(session, AgentAnswersText.firstTurnOf(mine),
				List.of("the-writer", "the-painter"), null);

		assertEquals(List.of(new AgentAnswer("the-writer", null, "report.docx written."),
				new AgentAnswer("the-writer", null, "summary.docx written.")), respawned);
		assertEquals("The agent the-writer said: report.docx written.\n\n"
				+ "The agent the-writer said: summary.docx written.\n\nWhat is new?",
				AgentAnswersText.prepend(respawned, "What is new?"));
	}
}
