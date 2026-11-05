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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ToolContext;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.ClarificationAnswer;
import ai.gebo.architecture.agents.model.ClarificationOptions;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCallingManagerFactory;

/**
 * The turn of an agent able to wait: the wait loaded beside what the run carries,
 * the answers told to the model on a respawn, the run suspended for what the
 * tools declared.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class AgentSuspensionTurnTest {
	private static final GeboToolCalling RUN_TOOL_CALLING = GeboToolCalling.builder().caller("test:the-run").build();
	private final IGNetworkOfAgentsSuspensionHandler handler = mock(IGNetworkOfAgentsSuspensionHandler.class);
	private final AgentNetworkParticipant me = new AgentNetworkParticipant();
	private final GAgentsNetwork network = new GAgentsNetwork();
	private final GAgentConfig config = new GAgentConfig();
	private final AgentPrivateSessionContext<String, String> privateContext = new AgentPrivateSessionContext<String, String>();

	private AgentSuspensionTurn turn() {
		return AgentSuspensionTurn.of("The agent", RUN_TOOL_CALLING, handler, me);
	}

	private static AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions> condition(String statement,
			ClarificationAnswer answer) {
		return new AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions>(ClarificationAnswer.class,
				statement, "it matters", answer, true, ClarificationOptions.class, new ClarificationOptions());
	}

	/** A tool of the turn's model call declares a condition, finding the wait in its tool context. */
	private static void aToolDeclares(AgentSuspensionTurn turn, AgentSuspendedWaitingCondition<?, ?> condition) {
		GeboToolCallingManagerFactory
				.control(new ToolContext(turn.toolCalling().toolsContext(Map.of())), IGWaitExternalCondition.class)
				.waitFor(condition);
	}

	@Test
	void theWaitIsLoadedBesideWhatTheRunCarries() {
		GeboToolCalling toolCalling = turn().toolCalling();

		assertNotNull(toolCalling.getProvider().get(IGWaitExternalCondition.class));
		assertEquals("test:the-run", toolCalling.getProvider().caller());
	}

	@Test
	void anAgentNotRespawnedKeepsItsInput() {
		when(handler.takeResolvedConditionsOf(me)).thenReturn(List.of());

		assertEquals("What is new?", turn().toldInput("What is new?"));
	}

	@Test
	void aRunThatCannotSuspendKeepsTheInputAndDoesNotWait() throws Exception {
		AgentSuspensionTurn turn = AgentSuspensionTurn.of("The agent", RUN_TOOL_CALLING, null, me);

		assertEquals("What is new?", turn.toldInput("What is new?"));
		assertFalse(turn.suspendIfWaiting(network, config, privateContext));
	}

	@Test
	void aRespawnedAgentTellsTheAnswersBeforeTheMessage() {
		when(handler.takeResolvedConditionsOf(me)).thenReturn(
				List.<AgentSuspendedWaitingCondition>of(condition("Which year?", ClarificationAnswer.option("2024"))));

		assertEquals("You asked the user: Which year?\nThe user chose: 2024\n\nWhat is new?",
				turn().toldInput("What is new?"));
	}

	@Test
	void aRespawnedAgentsContextAsksWhatItWasToldNotJustTheAnswer() {
		when(handler.takeResolvedConditionsOf(me)).thenReturn(
				List.<AgentSuspendedWaitingCondition>of(condition("Which year?", ClarificationAnswer.option("2024"))));
		IChatRequestContext context = IChatRequestContext.of("2024");

		assertEquals("told", turn().toldContext(context, "told").getActualUserRequest());
		assertSame(context, turn().toldContext(context, new Object()), "an input that is not text changes nothing");
		assertEquals("2024", context.getActualUserRequest(), "the run's own context stays as it is");
	}

	@Test
	void anAgentNotRespawnedKeepsItsContext() {
		when(handler.takeResolvedConditionsOf(me)).thenReturn(List.of());
		IChatRequestContext context = IChatRequestContext.of("What is new?");

		assertSame(context, turn().toldContext(context, "What is new?"));
	}

	@Test
	void anInputThatIsNotTextStaysAsItIs() {
		when(handler.takeResolvedConditionsOf(me)).thenReturn(
				List.<AgentSuspendedWaitingCondition>of(condition("Which year?", ClarificationAnswer.option("2024"))));
		Object command = new Object();

		assertSame(command, turn().toldInput(command));
	}

	@Test
	void nothingDeclaredSuspendsNothing() throws Exception {
		assertFalse(turn().suspendIfWaiting(network, config, privateContext));

		verify(handler, never()).enterSuspensionState(any(), any(), any(), any(), any());
	}

	@Test
	void everyDeclaredConditionSuspendsTheRun() throws Exception {
		AgentSuspensionTurn turn = turn();
		aToolDeclares(turn, condition("Which year?", null));
		aToolDeclares(turn, condition("Which country?", null));

		assertTrue(turn.suspendIfWaiting(network, config, privateContext));

		verify(handler, times(2)).enterSuspensionState(any(), any(), any(), any(), any());
	}

	@Test
	void aConditionInARunWithNoHandlerFailsTheAgent() {
		AgentSuspensionTurn turn = AgentSuspensionTurn.of("The agent", RUN_TOOL_CALLING, null, me);
		aToolDeclares(turn, condition("Which year?", null));

		AgentException failure = assertThrows(AgentException.class,
				() -> turn.suspendIfWaiting(network, config, privateContext));

		assertEquals("The agent must wait for 1 condition(s) but the run has no suspension handler",
				failure.getMessage());
	}
}
