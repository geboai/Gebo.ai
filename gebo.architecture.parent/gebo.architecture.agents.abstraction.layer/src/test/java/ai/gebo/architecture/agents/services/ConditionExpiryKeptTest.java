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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.architecture.agents.config.ConditionsExpiryConfig;
import ai.gebo.architecture.agents.model.AgentConditionsExpiry;
import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.ConditionExpiry;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.WaitingCondition;
import ai.gebo.architecture.agents.model.UnansweredBecause;
import ai.gebo.architecture.agents.model.WaitingConditionDropReason;
import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.architecture.agents.services.impl.WaitingConditionExpiryPolicyImpl;
import ai.gebo.llms.abstraction.layer.model.GChatRunInfo;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;

/**
 * Pins what the handler keeps in the block for the waiting conditions panel and
 * the sweep: the expiry of every condition, from the tool's code or the agent's
 * configuration, the earliest of them on the block, whether the run was watched,
 * why a block was dropped; and what a respawned agent reads of a condition the
 * run went on without.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
class ConditionExpiryKeptTest {
	private static final long MINUTE = 60_000L;
	private final GNetworkOfAgentsSuspensionRepository repository = mock(GNetworkOfAgentsSuspensionRepository.class);
	private final GAgentsNetwork network = new GAgentsNetwork();
	private final GAgentConfig config = new GAgentConfig();
	private final AgentNetworkParticipant participant = new AgentNetworkParticipant();
	private final AgentPrivateSessionContext<String, String> privateContext = new AgentPrivateSessionContext<String, String>();

	private GMongoNetworkOfAgentsSuspensionHandler handler(IWaitingConditionExpiryPolicy policy, Boolean attended)
			throws Exception {
		network.setCode("the-network");
		config.setCode("the-agent-config");
		participant.setAgentConfigCode("the-agent-config");
		participant.setAgentContextualName("the-searcher");
		privateContext.setCollaborationContextId("session-1");
		when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
		final GMongoNetworkOfAgentsSuspensionHandler handler = new GMongoNetworkOfAgentsSuspensionHandler(repository,
				null, policy);
		IChatRequestContext context = IChatRequestContext.builder().actualUserRequest("What is new?").sessionID("conv-1")
				.requestID("req-1").build();
		if (attended != null) {
			context = context.withRunInfo(GChatRunInfo.of("A2A", attended));
		}
		handler.initialize(network, "What is new?", context);
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
	void theAgentsConfigurationGivesTheConditionsTheirExpiryAndTheBlockTheEarliest() throws Exception {
		final AgentConditionsExpiry ofTheAgent = new AgentConditionsExpiry();
		ofTheAgent.setExpiryMinutes(30L);
		ofTheAgent.setExpiryUnblocks(true);
		config.setConditionsExpiry(ofTheAgent);
		final GMongoNetworkOfAgentsSuspensionHandler handler = handler(
				new WaitingConditionExpiryPolicyImpl(new ConditionsExpiryConfig()), null);
		final AgentSuspendedWaitingCondition<String, Object> byTheAgent = condition("the year");
		final AgentSuspendedWaitingCondition<String, Object> byTheTool = condition("the invoice");
		byTheTool.setExpiry(ConditionExpiry.afterMinutes(new Date(), 5L, false));
		final Date before = new Date();

		handler.enterSuspensionState(network, config, participant, privateContext, byTheAgent);
		handler.enterSuspensionState(network, config, participant, privateContext, byTheTool);
		final GNetworkOfAgentsSuspension block = saved(handler);

		final List<WaitingCondition> kept = block.conditions();
		assertEquals(2, kept.size());
		assertTrue(kept.get(0).getExpiresAt().getTime() >= before.getTime() + 30 * MINUTE,
				"the agent's configuration gives the condition its expiry");
		assertTrue(kept.get(0).isExpiryUnblocks());
		assertEquals(byTheTool.getExpiry().getExpiresAt(), kept.get(1).getExpiresAt(), "the tool's own is kept");
		assertFalse(kept.get(1).isExpiryUnblocks());
		assertEquals(kept.get(1).getExpiresAt(), block.getEarliestExpiryAt(), "the block carries the earliest");
		assertFalse(block.isUnwatched(), "a request saying nothing of its run is watched");
	}

	@Test
	void withoutAPolicyOrAnythingConfiguredNothingExpiresAndARunNobodyWatchesIsSaidSo() throws Exception {
		final GMongoNetworkOfAgentsSuspensionHandler handler = handler(null, Boolean.FALSE);

		handler.enterSuspensionState(network, config, participant, privateContext, condition("the year"));
		final GNetworkOfAgentsSuspension block = saved(handler);

		assertNull(block.conditions().get(0).getExpiresAt());
		assertNull(block.getEarliestExpiryAt());
		assertTrue(block.isUnwatched(), "the request says nobody attends the run");
	}

	@Test
	void aBlockDroppedByANewSuspensionOfItsConversationSaysSo() throws Exception {
		final GNetworkOfAgentsSuspension older = new GNetworkOfAgentsSuspension();
		older.setCode("older");
		older.setConversationCode("conv-1");
		when(repository.findByConversationCodeAndRespawnedAtIsNullAndDroppedAtIsNull("conv-1")).thenReturn(List.of(older));
		final GMongoNetworkOfAgentsSuspensionHandler handler = handler(null, null);

		handler.enterSuspensionState(network, config, participant, privateContext, condition("the year"));
		handler.networkExecutionFinished();

		verify(repository, times(2)).save(any());
		assertEquals(WaitingConditionDropReason.SUSPENDED_AGAIN, older.getDroppedReason());
		assertNull(older.getDroppedBy(), "no person chose it");
	}

	@Test
	void theExpiryAndWhyAConditionHasNoValueGoThroughTheSavedBlockAndBack() {
		final AgentSuspendedWaitingCondition<String, Object> condition = condition("the year");
		condition.setExpiry(new ConditionExpiry(new Date(1_700_000_000_000L), true));
		condition.setUnansweredBecause(UnansweredBecause.EXPIRED);

		final WaitingCondition kept = WaitingCondition.of(condition);
		final AgentSuspendedWaitingCondition<?, ?> back = kept.toCondition();

		assertEquals(new Date(1_700_000_000_000L), kept.getExpiresAt());
		assertTrue(kept.isExpiryUnblocks());
		assertTrue(kept.isExpiredAt(new Date(1_700_000_000_000L)));
		assertFalse(kept.isExpiredAt(new Date(1_699_999_999_999L)));
		assertEquals(condition.getExpiry(), back.getExpiry());
		assertEquals(UnansweredBecause.EXPIRED, back.getUnansweredBecause());
		assertNull(WaitingCondition.of(condition("plain")).toCondition().getExpiry(), "none kept, none back");
	}

	@Test
	void theAgentReadsWhyTheRunWentOnWithoutTheCondition() {
		final AgentSuspendedWaitingCondition<String, Object> expired = condition("the year");
		expired.setExpiry(new ConditionExpiry(new Date(1_700_000_000_000L), true));
		expired.setUnansweredBecause(UnansweredBecause.EXPIRED);
		final AgentSuspendedWaitingCondition<String, Object> goneOnWithout = condition("the invoice");
		goneOnWithout.setUnansweredBecause(UnansweredBecause.GONE_ON_WITHOUT);
		final AgentSuspendedWaitingCondition<String, Object> declined = condition("the colour");

		assertEquals("You were waiting for: the year\nNo answer came before 2023-11-14T22:13:20Z: "
				+ UnansweredConditionText.GO_ON, ResolvedConditionText.render(expired));
		assertEquals("You were waiting for: the invoice\nThe user chose to go on without an answer: "
				+ UnansweredConditionText.GO_ON, ResolvedConditionText.render(goneOnWithout));
		assertEquals("You were waiting for: the colour\nThe user chose not to answer: " + UnansweredConditionText.GO_ON,
				ResolvedConditionText.render(declined), "as before for a condition declined");
		assertTrue(ResolvedConditionText.renderForPeers("the-searcher", expired).contains("No answer came before"));
	}
}
