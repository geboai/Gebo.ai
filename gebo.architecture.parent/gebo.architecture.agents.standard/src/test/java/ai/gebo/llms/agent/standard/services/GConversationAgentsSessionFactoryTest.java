/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standard.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.AgentsSessionCreationContext;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.agent.standard.memory.GConversationNetworkMemory;
import ai.gebo.llms.agent.standard.memory.GConversationNetworkMemoryRepository;

class GConversationAgentsSessionFactoryTest {
	private final GConversationNetworkMemoryRepository memories = mock(GConversationNetworkMemoryRepository.class);
	private final GConversationAgentsSessionFactory factory = new GConversationAgentsSessionFactory(memories);

	private static AgentsSessionCreationContext creation(String conversation) {
		GAgentsNetwork network = new GAgentsNetwork();
		network.setCode("the-network");
		IChatRequestContext context = IChatRequestContext.builder().actualUserRequest("q").sessionID(conversation)
				.build();
		return AgentsSessionCreationContext.builder().network(network).chatRequestContext(context).build();
	}

	@Test
	void aRunWithNoConversationLeavesTheSessionToTheNetwork() {
		assertNull(factory.createSessionContext(creation(null)));
	}

	@Test
	void whatWasRememberedSeedsTheSharedContextOfTheNextRun() {
		GConversationNetworkMemory remembered = new GConversationNetworkMemory();
		remembered.setDigest("the-writer: Paris is the capital of France.");
		when(memories.findByConversationCodeAndNetworkCode("conv-1", "the-network"))
				.thenReturn(Optional.of(remembered));

		AgentsCollaborationSessionContext session = factory.createSessionContext(creation("conv-1"));

		assertEquals(1, session.getSampledContributions().size());
		assertEquals(GConversationAgentsSessionFactory.MEMORY_AGENT_NAME,
				session.getSampledContributions().get(0).getAgentName());
		assertTrue(String.valueOf(session.getSampledContributions().get(0).getData()).contains("Paris"));
		assertEquals(1, session.getContributionCounter(), "the run's own contributions come after the memory");
	}

	@Test
	void whatTheAgentsContributedIsDigestedWhenTheRunEndsTheMemoryItselfLeftOut() {
		when(memories.findByConversationCodeAndNetworkCode("conv-1", "the-network")).thenReturn(Optional.empty());
		AgentsCollaborationSessionContext session = factory.createSessionContext(creation("conv-1"));
		session.addContribution(AgentsExchangeMessage.of(session, "the-searcher", "the-controller",
				"Found three documents.", MessageSemantic.RESPONSE), session.getAndIncrementContributionNr());
		session.addContribution(AgentsExchangeMessage.of(session, "the-writer", "the-controller", "Paris.",
				MessageSemantic.RESPONSE), session.getAndIncrementContributionNr());

		factory.sessionCompleted(session, creation("conv-1"), null, null);

		ArgumentCaptor<GConversationNetworkMemory> saved = ArgumentCaptor.forClass(GConversationNetworkMemory.class);
		verify(memories).save(saved.capture());
		assertEquals("conv-1", saved.getValue().getConversationCode());
		assertEquals("the-network", saved.getValue().getNetworkCode());
		assertEquals("the-searcher: Found three documents.\nthe-writer: Paris.", saved.getValue().getDigest());
		assertEquals(1, saved.getValue().getRuns());
	}

	@Test
	void whatIsRememberedGoesWithTheConversation() {
		factory.chatSessionRemoved("conv-1");

		verify(memories).deleteByConversationCode("conv-1");
	}
}
