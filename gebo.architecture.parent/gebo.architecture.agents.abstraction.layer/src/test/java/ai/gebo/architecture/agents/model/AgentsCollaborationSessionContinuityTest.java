/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext.IAgentsSessionContributionListener;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;

/**
 * Pins what a collaboration session is when someone outside owns it: the identity
 * it is given, the past it is resumed with, the turn numbers it goes on handing
 * out, and what it tells a listener while the agents are still contributing.
 */
class AgentsCollaborationSessionContinuityTest {

	private static void contribute(AgentsCollaborationSessionContext session, String fromAgent, String payload,
			int contributionNr) {
		AgentsExchangeMessage<String> message = AgentsExchangeMessage.of(session, "reader", payload,
				MessageSemantic.RESPONSE);
		message.setFromAgent(fromAgent);
		session.addContribution(message, contributionNr);
	}

	@Test
	void aSessionOfItsOwnHasAFreshIdentityAndNoPast() {
		AgentsCollaborationSessionContext first = new AgentsCollaborationSessionContext();
		AgentsCollaborationSessionContext second = new AgentsCollaborationSessionContext();
		assertNotNull(first.getId());
		assertNotEquals(first.getId(), second.getId(), "every session of its own has an identity of its own");
		assertTrue(first.getSampledContributions().isEmpty());
		assertEquals(0, first.getContributionCounter(), "its turns start at the beginning");
	}

	@Test
	void aSessionTakesTheIdentityItIsGiven() {
		AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext("a-session-of-its-own-42");
		assertEquals("a-session-of-its-own-42", session.getId());
		assertEquals(0, session.getContributionCounter());
	}

	@Test
	void anIdentityThatSaysNothingIsNoIdentity() {
		assertNotNull(new AgentsCollaborationSessionContext(null).getId());
		assertFalse(new AgentsCollaborationSessionContext("   ").getId().isBlank(),
				"a blank identity is replaced by a fresh one, never kept as it is");
	}

	@Test
	void aResumedSessionReadsAsThoughItHadNeverStopped() {
		List<AgentProducedSessionContribution> past = new ArrayList<>();
		past.add(new AgentProducedSessionContribution(0, "searcher", "what was found yesterday"));
		past.add(new AgentProducedSessionContribution(1, "writer", "what was written yesterday"));
		AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext("session-1", past, 2);

		List<AgentProducedSessionContribution> sampled = session.getSampledContributions();
		assertEquals(2, sampled.size(), "the agents read the past in their shared context");
		assertEquals("searcher", sampled.get(0).getAgentName());
		assertEquals("writer", sampled.get(1).getAgentName());
		assertEquals(2, session.getContributionCounter(), "the session goes on from where it stopped");
		assertEquals(2, session.getAndIncrementContributionNr(), "the next turn continues the numbering");
	}

	@Test
	void theTurnsOfAResumedSessionNeverCollideWithItsPast() {
		List<AgentProducedSessionContribution> past = List
				.of(new AgentProducedSessionContribution(7, "searcher", "a turn from much earlier"));
		// Asked to start at 0, which would hand out turn numbers the past already used.
		AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext("session-2", past, 0);
		assertEquals(8, session.getContributionCounter(),
				"the counter is raised past the replayed turns rather than colliding with them");
	}

	@Test
	void aPastWithNothingInItIsNoPast() {
		AgentsCollaborationSessionContext nulls = new AgentsCollaborationSessionContext("session-3",
				java.util.Arrays.asList((AgentProducedSessionContribution) null), 0);
		assertTrue(nulls.getSampledContributions().isEmpty());
		assertEquals(0, nulls.getContributionCounter());
	}

	@Test
	void theListenerIsToldOfEveryContributionAsItIsRecorded() {
		AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext("session-4");
		List<String> heard = new ArrayList<>();
		List<AgentsCollaborationSessionContext> heardFrom = new ArrayList<>();
		session.setContributionListener((told, contribution) -> {
			heardFrom.add(told);
			heard.add(contribution.getContributionUniqueNr() + ":" + contribution.getAgentName() + ":"
					+ contribution.getData());
		});

		contribute(session, "searcher", "found", 0);
		contribute(session, "writer", "written", 1);

		assertEquals(List.of("0:searcher:found", "1:writer:written"), heard,
				"the listener hears each contribution, in the order they were recorded");
		assertSame(session, heardFrom.get(0), "and is told which session it is hearing about");
	}

	@Test
	void nothingIsHeardOnceNobodyIsListening() {
		AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext("session-5");
		List<String> heard = new ArrayList<>();
		IAgentsSessionContributionListener listener = (told, contribution) -> heard.add(contribution.getAgentName());
		session.setContributionListener(listener);
		contribute(session, "searcher", "found", 0);
		session.setContributionListener(null);
		contribute(session, "writer", "written", 1);
		assertEquals(List.of("searcher"), heard);
	}

	@Test
	void aListenerThatFailsNeverBreaksTheCollaboration() {
		AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext("session-6");
		session.setContributionListener((told, contribution) -> {
			throw new IllegalStateException("the memory store is down");
		});

		contribute(session, "searcher", "found", 0);

		assertEquals(1, session.getSampledContributions().size(),
				"the contribution is recorded even though telling anyone about it failed");
		assertEquals("found", session.getSampledContributions().get(0).getData());
	}
}
