package ai.gebo.architecture.agents.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;

/**
 * Pins what the Mongo suspension handler keeps of a run: nothing while no agent
 * asks to suspend it; from the first asking on, everything that passes through
 * its calls, consolidated in memory and written as one block when the run is
 * declared finished.
 */
class GMongoNetworkOfAgentsSuspensionHandlerTest {
	private final GNetworkOfAgentsSuspensionRepository repository = mock(GNetworkOfAgentsSuspensionRepository.class);
	private final GMongoNetworkOfAgentsSuspensionHandler handler = new GMongoNetworkOfAgentsSuspensionHandler(
			repository);
	private final GAgentsNetwork network = new GAgentsNetwork();
	private final GAgentConfig config = new GAgentConfig();
	private final AgentNetworkParticipant participant = new AgentNetworkParticipant();
	private final AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext("session-1");
	private final AgentPrivateSessionContext<String, String> privateContext = new AgentPrivateSessionContext<String, String>();

	@BeforeEach
	void setUp() throws AgentException {
		network.setCode("the-network");
		config.setCode("the-agent-config");
		participant.setAgentConfigCode("the-agent-config");
		participant.setAgentContextualName("the-searcher");
		privateContext.setCollaborationContextId(session.getId());
		when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
		handler.initialize(network, "What is new?",
				IChatRequestContext.builder().actualUserRequest("What is new?").sessionID("conv-1").requestID("req-1")
						.build());
	}

	private AgentsExchangeMessage<String> message(String from, String to, String payload, MessageSemantic semantic) {
		return AgentsExchangeMessage.of(session, from, to, payload, semantic);
	}

	@SuppressWarnings("rawtypes")
	private AgentSuspendedWaitingCondition<String, List> condition(String statement) {
		return new AgentSuspendedWaitingCondition<String, List>(String.class, statement, "the user's answer", null,
				true, List.class, List.of("2023", "2024"));
	}

	@Test
	void aHandlerMustBeInitializedForANetwork() {
		assertThrows(AgentException.class, () -> new GMongoNetworkOfAgentsSuspensionHandler(repository)
				.initialize(null, null, null));
	}

	@Test
	void aRunNobodyAsksToSuspendKeepsNothing() throws AgentException {
		assertFalse(handler.isSuspensionState());

		handler.setSuspendingMessagesNotYetDelivered(network, config, participant, privateContext,
				List.of(message("a", "b", "hello", MessageSemantic.EXECUTE_AND_SHARE_RESULT)));
		handler.setSuspendingNetworkOfAgentsSharedState(session);
		handler.networkExecutionFinished();

		assertFalse(handler.isSuspensionState());
		assertNull(handler.suspensionId());
		assertTrue(handler.suspensionConditions().isEmpty());
		verify(repository, never()).save(any());
	}

	@Test
	void theCutIsConsolidatedInMemoryAndWrittenOnceWhenTheRunIsDeclaredFinished() throws AgentException {
		privateContext.addInteraction(message("in", "the-searcher", "What is new?", MessageSemantic.EXECUTE_AND_SHARE_RESULT),
				0, "I need to ask the user");
		AgentSuspendedWaitingCondition<String, List> waiting = condition("which year?");
		session.getEnvironment().put("KEY", "value");
		session.addContribution(message("the-searcher", "the-writer", "found this", MessageSemantic.RESPONSE),
				session.getAndIncrementContributionNr());
		AgentsExchangeMessage<String> undelivered = message("the-searcher", "the-writer", "found this",
				MessageSemantic.EXECUTE_AND_SHARE_RESULT);
		undelivered.setStatusNotices(List.of("one source was unreachable"));

		handler.enterSuspensionState(network, config, participant, privateContext, waiting);
		assertTrue(handler.isSuspensionState());
		assertNotNull(handler.suspensionId());
		assertEquals(List.of(waiting), handler.suspensionConditions());
		handler.setSuspendingMessagesNotYetDelivered(network, config, participant, privateContext,
				List.of(undelivered));
		handler.setSuspendingNetworkOfAgentsSharedState(session);
		verify(repository, never()).save(any());

		handler.networkExecutionFinished();

		ArgumentCaptor<GNetworkOfAgentsSuspension> written = ArgumentCaptor.forClass(GNetworkOfAgentsSuspension.class);
		verify(repository, times(1)).save(written.capture());
		GNetworkOfAgentsSuspension saved = written.getValue();
		// the run
		assertEquals(handler.suspensionId(), saved.getCode());
		assertNotNull(saved.getSuspendedAt());
		assertNotNull(saved.getFinishedAt());
		assertEquals("the-network", saved.getNetworkCode());
		assertSame(network, saved.getNetwork());
		assertEquals("What is new?", saved.getNetworkInput(), "the input of the run is part of the cut");
		assertEquals(String.class.getName(), saved.getNetworkInputClass());
		assertEquals(session.getId(), saved.getCollaborationSessionId());
		assertEquals("conv-1", saved.getConversationCode(), "bound to the conversation of the run");
		assertEquals("req-1", saved.getRequestId());
		assertTrue(saved.isOpen());
		assertTrue(saved.isMandatory());
		// the agent that asked
		assertEquals(1, saved.getSuspendedAgents().size());
		GNetworkOfAgentsSuspension.SuspendedAgentState agent = saved.getSuspendedAgents().get(0);
		assertEquals("the-agent-config", agent.getAgentConfigCode());
		assertSame(config, agent.getAgentConfig());
		assertSame(participant, agent.getParticipant());
		assertEquals(privateContext.getId(), agent.getPrivateState().getPrivateContextId());
		assertEquals(1, agent.getPrivateState().getInteractions().size());
		assertEquals("I need to ask the user", agent.getPrivateState().getInteractions().get(0).getOutput());
		assertEquals("What is new?", agent.getPrivateState().getInteractions().get(0).getInputMessage().getPayload());
		assertEquals(waiting.getSuspendedConditionId(), agent.getWaitingConditions().get(0).getSuspendedConditionId());
		assertEquals(String.class.getName(), agent.getWaitingConditions().get(0).getTypeName());
		assertEquals("which year?", agent.getWaitingConditions().get(0).getWaitingConditionStatement());
		assertTrue(agent.getWaitingConditions().get(0).isMandatory());
		assertTrue(agent.getWaitingConditions().get(0).toCondition().isMandatory());
		assertEquals(List.class.getName(), agent.getWaitingConditions().get(0).getOptionsTypeName());
		assertEquals(List.of("2023", "2024"), agent.getWaitingConditions().get(0).getLlmChosenOptions(),
				"what the model chose for the suspension");
		assertEquals(waiting.getSuspendedConditionId(),
				agent.getWaitingConditions().get(0).toCondition().getSuspendedConditionId(),
				"a condition read back from the block carries the saved id");
		// what was not delivered
		assertEquals(1, saved.getFrozenDeliveries().size());
		GNetworkOfAgentsSuspension.FrozenDeliveries frozen = saved.getFrozenDeliveries().get(0);
		assertEquals("the-agent-config", frozen.getAgentConfigCode());
		assertEquals(1, frozen.getMessages().size());
		assertEquals(undelivered.getId(), frozen.getMessages().get(0).getMessageId());
		assertEquals("the-writer", frozen.getMessages().get(0).getToAgent());
		assertEquals("found this", frozen.getMessages().get(0).getPayload());
		assertEquals(String.class.getName(), frozen.getMessages().get(0).getPayloadClass());
		assertEquals(MessageSemantic.EXECUTE_AND_SHARE_RESULT, frozen.getMessages().get(0).getMessageSemantic());
		assertEquals(List.of("one source was unreachable"), frozen.getMessages().get(0).getStatusNotices());
		// the shared state
		assertEquals(session.getId(), saved.getSharedState().getSessionId());
		assertEquals(1, saved.getSharedState().getContributionCounter());
		assertEquals(1, saved.getSharedState().getContributions().size());
		assertEquals("the-searcher", saved.getSharedState().getContributions().get(0).getAgentName());
		assertEquals("found this", saved.getSharedState().getContributions().get(0).getData());
		assertEquals(0, saved.getSharedState().getContributions().get(0).getContributionUniqueNr());
		assertEquals(Map.of("KEY", "value"), saved.getSharedState().getEnvironment());
		assertTrue(handler.isSuspensionState(), "the state of the run stays suspended for whoever reads it");
	}

	@Test
	void writingABlockDropsTheOpenOnesOfItsConversation() throws AgentException {
		GNetworkOfAgentsSuspension older = new GNetworkOfAgentsSuspension();
		older.setCode("older");
		older.setConversationCode("conv-1");
		GNetworkOfAgentsSuspension answered = new GNetworkOfAgentsSuspension();
		answered.setCode("answered");
		answered.setConversationCode("conv-1");
		answered.setRespawnedAt(new java.util.Date());
		when(repository.findByConversationCodeAndRespawnedAtIsNullAndDroppedAtIsNull("conv-1"))
				.thenReturn(List.of(older));
		handler.enterSuspensionState(network, config, participant, privateContext, condition("which year?"));

		handler.networkExecutionFinished();

		assertNotNull(older.getDroppedAt(), "the conversation holds one open block: the older is dropped");
		assertFalse(older.isOpen());
		assertNull(answered.getDroppedAt(), "a block already answered is not touched");
		ArgumentCaptor<GNetworkOfAgentsSuspension> written = ArgumentCaptor.forClass(GNetworkOfAgentsSuspension.class);
		verify(repository, times(2)).save(written.capture());
		assertEquals("older", written.getAllValues().get(0).getCode(), "dropped before the new block is written");
		assertEquals(handler.suspensionId(), written.getAllValues().get(1).getCode());
	}

	@Test
	void twoAgentsAskingInTheSameRunShareOneCut() throws AgentException {
		AgentNetworkParticipant other = new AgentNetworkParticipant();
		other.setAgentConfigCode("the-other-config");
		other.setAgentContextualName("the-other");
		GAgentConfig otherConfig = new GAgentConfig();
		otherConfig.setCode("the-other-config");

		handler.enterSuspensionState(network, config, participant, privateContext, condition("which year?"));
		String id = handler.suspensionId();
		handler.enterSuspensionState(network, otherConfig, other, new AgentPrivateSessionContext<String, String>(),
				condition("which country?"));
		handler.networkExecutionFinished();

		assertEquals(id, handler.suspensionId());
		ArgumentCaptor<GNetworkOfAgentsSuspension> written = ArgumentCaptor.forClass(GNetworkOfAgentsSuspension.class);
		verify(repository, times(1)).save(written.capture());
		assertEquals(2, written.getValue().getSuspendedAgents().size());
		assertEquals("the-other-config", written.getValue().getSuspendedAgents().get(1).getAgentConfigCode());
		assertEquals(2, handler.suspensionConditions().size());
	}

	@Test
	void anAgentAskingTwiceInItsTurnHasOneEntryWithBothConditionsAndItsTurn() throws AgentException {
		handler.enterSuspensionState(network, config, participant, privateContext, condition("which year?"));
		handler.enterSuspensionState(network, config, participant, privateContext, condition("which country?"));
		handler.setSuspendedAgentTurn(participant, message("router", "the-searcher", "What is new?",
				MessageSemantic.EXECUTE_AND_SHARE_RESULT), 3);
		handler.networkExecutionFinished();

		ArgumentCaptor<GNetworkOfAgentsSuspension> written = ArgumentCaptor.forClass(GNetworkOfAgentsSuspension.class);
		verify(repository, times(1)).save(written.capture());
		GNetworkOfAgentsSuspension block = written.getValue();
		assertEquals(1, block.getSuspendedAgents().size(), "one entry per agent");
		assertEquals(List.of("which year?", "which country?"), block.getSuspendedAgents().get(0).getWaitingConditions()
				.stream().map(GNetworkOfAgentsSuspension.WaitingCondition::getWaitingConditionStatement).toList());
		assertEquals("What is new?", block.getSuspendedAgents().get(0).getInputMessage().getPayload());
		assertEquals(3, block.getSuspendedAgents().get(0).getContributionNr());
		assertEquals(2, block.conditions().size());
		assertEquals(2, handler.suspensionConditions().size());
		assertTrue(block.getFrozenDeliveries().isEmpty());
	}

	@Test
	void anAgentAskingInAFurtherTurnHasThatMessageFrozenAndATurnThatDidNotAskIsNotKept() throws AgentException {
		handler.enterSuspensionState(network, config, participant, privateContext, condition("which year?"));
		handler.setSuspendedAgentTurn(participant, message("router", "the-searcher", "first",
				MessageSemantic.EXECUTE_AND_SHARE_RESULT), 1);
		handler.setSuspendedAgentTurn(participant, message("router", "the-searcher", "it did not ask in this one",
				MessageSemantic.EXECUTE_AND_SHARE_RESULT), 2);
		handler.enterSuspensionState(network, config, participant, privateContext, condition("which country?"));
		handler.setSuspendedAgentTurn(participant, message("router", "the-searcher", "second",
				MessageSemantic.EXECUTE_AND_SHARE_RESULT), 3);
		handler.networkExecutionFinished();

		ArgumentCaptor<GNetworkOfAgentsSuspension> written = ArgumentCaptor.forClass(GNetworkOfAgentsSuspension.class);
		verify(repository, times(1)).save(written.capture());
		GNetworkOfAgentsSuspension block = written.getValue();
		assertEquals(1, block.getSuspendedAgents().size());
		assertEquals("first", block.getSuspendedAgents().get(0).getInputMessage().getPayload());
		assertEquals(2, block.getSuspendedAgents().get(0).getWaitingConditions().size());
		assertEquals(1, block.getFrozenDeliveries().size());
		assertEquals("second", block.getFrozenDeliveries().get(0).getMessages().get(0).getPayload());
	}

	@Test
	void twoTurnsOfAnAgentAskingAtTheSameTimeKeepBothMessages() throws Exception {
		java.util.concurrent.ExecutorService otherTurn = java.util.concurrent.Executors.newSingleThreadExecutor();
		try {
			handler.enterSuspensionState(network, config, participant, privateContext, condition("which year?"));
			otherTurn.submit(() -> {
				handler.enterSuspensionState(network, config, participant, privateContext, condition("which country?"));
				return null;
			}).get();
			handler.setSuspendedAgentTurn(participant, message("router", "the-searcher", "first",
					MessageSemantic.EXECUTE_AND_SHARE_RESULT), 1);
			otherTurn.submit(() -> {
				handler.setSuspendedAgentTurn(participant, message("router", "the-searcher", "second",
						MessageSemantic.EXECUTE_AND_SHARE_RESULT), 2);
				return null;
			}).get();
		} finally {
			otherTurn.shutdownNow();
		}
		handler.networkExecutionFinished();

		ArgumentCaptor<GNetworkOfAgentsSuspension> written = ArgumentCaptor.forClass(GNetworkOfAgentsSuspension.class);
		verify(repository, times(1)).save(written.capture());
		GNetworkOfAgentsSuspension block = written.getValue();
		assertEquals(1, block.getSuspendedAgents().size());
		assertEquals(2, block.getSuspendedAgents().get(0).getWaitingConditions().size());
		assertEquals("first", block.getSuspendedAgents().get(0).getInputMessage().getPayload());
		assertEquals("second", block.getFrozenDeliveries().get(0).getMessages().get(0).getPayload());
	}

	@Test
	void aBlockThatCannotBeWrittenFailsTheRunDeclaredFinished() throws AgentException {
		when(repository.save(any())).thenThrow(new IllegalArgumentException("mongo is down"));
		handler.enterSuspensionState(network, config, participant, privateContext, condition("which year?"));

		IllegalStateException failure = assertThrows(IllegalStateException.class,
				() -> handler.networkExecutionFinished());

		assertTrue(failure.getMessage().contains("the-network"));
		assertSame(IllegalArgumentException.class, failure.getCause().getClass());
	}
}
