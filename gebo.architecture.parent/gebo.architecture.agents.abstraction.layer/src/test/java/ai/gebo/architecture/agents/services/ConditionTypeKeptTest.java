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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.WaitingCondition;
import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;

/**
 * Pins that the type of a condition, the id of the translator that shows it, is
 * kept with it when the run is suspended: told by the resolver, unless the
 * condition has one; none without a resolver, as before; and through the saved
 * block and back.
 */
class ConditionTypeKeptTest {
	private final GNetworkOfAgentsSuspensionRepository repository = mock(GNetworkOfAgentsSuspensionRepository.class);
	private final GAgentsNetwork network = new GAgentsNetwork();
	private final GAgentConfig config = new GAgentConfig();
	private final AgentNetworkParticipant participant = new AgentNetworkParticipant();
	private final AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext("session-1");
	private final AgentPrivateSessionContext<String, String> privateContext = new AgentPrivateSessionContext<String, String>();

	private GMongoNetworkOfAgentsSuspensionHandler handler(IWaitingConditionTypeResolver resolver) throws Exception {
		network.setCode("the-network");
		config.setCode("the-agent-config");
		participant.setAgentConfigCode("the-agent-config");
		participant.setAgentContextualName("the-searcher");
		privateContext.setCollaborationContextId(session.getId());
		when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
		final GMongoNetworkOfAgentsSuspensionHandler handler = new GMongoNetworkOfAgentsSuspensionHandler(repository,
				resolver);
		handler.initialize(network, "What is new?",
				IChatRequestContext.builder().actualUserRequest("What is new?").sessionID("conv-1").requestID("req-1")
						.build());
		return handler;
	}

	private static AgentSuspendedWaitingCondition<String, Object> condition(String statement) {
		return new AgentSuspendedWaitingCondition<String, Object>(String.class, statement, "the user's answer", null,
				true);
	}

	private GNetworkOfAgentsSuspension saved(GMongoNetworkOfAgentsSuspensionHandler handler) throws Exception {
		handler.networkExecutionFinished();
		final ArgumentCaptor<GNetworkOfAgentsSuspension> written = ArgumentCaptor
				.forClass(GNetworkOfAgentsSuspension.class);
		verify(repository).save(written.capture());
		return written.getValue();
	}

	@Test
	void theResolverTellsTheTypeKeptWithEveryConditionOfTheBlock() throws Exception {
		final AtomicInteger asked = new AtomicInteger();
		final GMongoNetworkOfAgentsSuspensionHandler handler = handler(condition -> {
			asked.incrementAndGet();
			return "clarification";
		});
		final AgentSuspendedWaitingCondition<String, Object> first = condition("which year?");
		final AgentSuspendedWaitingCondition<String, Object> second = condition("which country?");

		handler.enterSuspensionState(network, config, participant, privateContext, first);
		handler.enterSuspensionState(network, config, participant, privateContext, second);

		assertEquals("clarification", first.getConditionType(), "the condition shown to the client has it too");
		assertEquals("clarification", handler.suspensionConditions().get(1).getConditionType());
		final List<WaitingCondition> kept = saved(handler).getSuspendedAgents().get(0).getWaitingConditions();
		assertEquals(2, kept.size());
		assertEquals("clarification", kept.get(0).getConditionType());
		assertEquals("clarification", kept.get(1).getConditionType());
		assertEquals(2, asked.get());
	}

	@Test
	void aTypeTheConditionHasIsKept() throws Exception {
		final GMongoNetworkOfAgentsSuspensionHandler handler = handler(condition -> "default");
		final AgentSuspendedWaitingCondition<String, Object> typed = condition("which year?");
		typed.setConditionType("erp-approval");

		handler.enterSuspensionState(network, config, participant, privateContext, typed);

		assertEquals("erp-approval",
				saved(handler).getSuspendedAgents().get(0).getWaitingConditions().get(0).getConditionType());
	}

	@Test
	void withoutAResolverOrWhenItFailsNoTypeIsKept() throws Exception {
		final GMongoNetworkOfAgentsSuspensionHandler plain = handler(null);
		final AgentSuspendedWaitingCondition<String, Object> untyped = condition("which year?");
		plain.enterSuspensionState(network, config, participant, privateContext, untyped);
		assertNull(untyped.getConditionType());

		final GMongoNetworkOfAgentsSuspensionHandler failing = new GMongoNetworkOfAgentsSuspensionHandler(repository,
				condition -> {
					throw new IllegalStateException("no translators");
				});
		failing.initialize(network, "What is new?", IChatRequestContext.builder().actualUserRequest("What is new?")
				.sessionID("conv-2").requestID("req-2").build());
		final AgentSuspendedWaitingCondition<String, Object> notTold = condition("which country?");
		failing.enterSuspensionState(network, config, participant, privateContext, notTold);
		assertNull(notTold.getConditionType(), "the run is suspended all the same");
	}

	@Test
	void theTypeGoesThroughTheSavedBlockAndBack() {
		final AgentSuspendedWaitingCondition<String, Object> typed = condition("which year?");
		typed.setConditionType("clarification");

		assertEquals("clarification", WaitingCondition.of(typed).toCondition().getConditionType());
		assertNull(WaitingCondition.of(condition("old")).toCondition().getConditionType(),
				"a block kept before has none");
	}

	@Test
	void theHumanInTheLoopProcessTreatmentIsKeptWithTheConditionOfTheBlock() {
		final AgentSuspendedWaitingCondition<String, Object> request = condition("which year?");
		request.setHumanInTheLoopProcessTreatment(true);

		assertTrue(WaitingCondition.of(request).isHumanInTheLoopProcessTreatment());
		assertTrue(WaitingCondition.of(request).toCondition().isHumanInTheLoopProcessTreatment());
		assertFalse(WaitingCondition.of(condition("old")).toCondition().isHumanInTheLoopProcessTreatment(),
				"a clarification, and a block kept before, is the conversation's alone");
	}
}
