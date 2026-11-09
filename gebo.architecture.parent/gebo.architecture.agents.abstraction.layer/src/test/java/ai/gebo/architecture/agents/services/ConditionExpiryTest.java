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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.config.ConditionsExpiryConfig;
import ai.gebo.architecture.agents.model.AgentConditionsExpiry;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.ConditionExpiry;
import ai.gebo.architecture.agents.model.ConditionSource;
import ai.gebo.architecture.agents.services.impl.WaitingConditionExpiryPolicyImpl;

/**
 * Pins where the expiry of a condition comes from, software only: the waiting
 * tool's own, else the agent configuration's, else the platform's default, which
 * is off unless configured; and what the agent is told of a condition the run
 * went on without.
 */
class ConditionExpiryTest {
	private static final Date ASKED = new Date(1_700_000_000_000L);
	private static final long MINUTE = 60_000L;

	private static AgentConditionsExpiry agent(Long minutes, Boolean unblocks) {
		final AgentConditionsExpiry expiry = new AgentConditionsExpiry();
		expiry.setExpiryMinutes(minutes);
		expiry.setExpiryUnblocks(unblocks);
		return expiry;
	}

	private static ConditionsExpiryConfig platform(long minutes, boolean unblocks) {
		final ConditionsExpiryConfig config = new ConditionsExpiryConfig();
		config.setDefaultExpiryMinutes(minutes);
		config.setDefaultExpiryUnblocks(unblocks);
		return config;
	}

	@Test
	void theToolsExpiryWinsThenTheAgentsThenThePlatforms() {
		final IWaitingConditionExpiryPolicy policy = new WaitingConditionExpiryPolicyImpl(platform(240, true));
		final ConditionExpiry ofTheTool = new ConditionExpiry(new Date(ASKED.getTime() + 5 * MINUTE), false);

		assertSame(ofTheTool, policy.expiryOf(ofTheTool, agent(30L, true), ASKED));

		final ConditionExpiry ofTheAgent = policy.expiryOf(null, agent(30L, null), ASKED);
		assertEquals(new Date(ASKED.getTime() + 30 * MINUTE), ofTheAgent.getExpiresAt());
		assertFalse(ofTheAgent.isExpiryUnblocks(), "unless the configuration says so the expiry does not unblock");

		final ConditionExpiry ofThePlatform = policy.expiryOf(new ConditionExpiry(), agent(0L, true), ASKED);
		assertEquals(new Date(ASKED.getTime() + 240 * MINUTE), ofThePlatform.getExpiresAt(),
				"a tool's expiry with no moment and an agent's with no minutes give none");
		assertTrue(ofThePlatform.isExpiryUnblocks());
	}

	@Test
	void withNothingConfiguredThereIsNoExpiry() {
		assertNull(new WaitingConditionExpiryPolicyImpl(new ConditionsExpiryConfig()).expiryOf(null, null, ASKED),
				"the platform's default is off");
		assertNull(new WaitingConditionExpiryPolicyImpl(null).expiryOf(null, agent(-5L, true), ASKED));
		assertNull(ConditionExpiry.afterMinutes(null, 10L, true));
		assertNull(ConditionExpiry.afterMinutes(ASKED, null, true));
	}

	@Test
	void anExpiryIsDueFromItsMomentOn() {
		final ConditionExpiry expiry = ConditionExpiry.afterMinutes(ASKED, 10L, false);

		assertFalse(expiry.isDue(new Date(ASKED.getTime() + 10 * MINUTE - 1)));
		assertTrue(expiry.isDue(new Date(ASKED.getTime() + 10 * MINUTE)));
		assertFalse(expiry.isDue(null));
		assertFalse(new ConditionExpiry().isDue(ASKED), "no moment, never due");
	}

	@Test
	void theAgentIsToldTheRunWentOnWithoutTheAnswerAndWhy() {
		final AgentSuspendedWaitingCondition<String, Object> condition = new AgentSuspendedWaitingCondition<String, Object>(
				String.class, "the invoice number", "needed to look it up", null, true);

		assertEquals("You were waiting for: the invoice number\nNo answer came before 2023-11-14T22:13:20Z: "
				+ UnansweredConditionText.GO_ON, UnansweredConditionText.expired(condition, ASKED));
		assertEquals("You were waiting for: the invoice number\nThe user chose to go on without an answer: "
				+ UnansweredConditionText.GO_ON, UnansweredConditionText.goneOnWithout(condition));

		final ConditionSource admin = ConditionSource.of(ConditionSource.Kind.PERSON, "GEBO", "admin@gebo.ai",
				"The administrator");
		admin.setVerified(true);
		condition.setSolvedBy(admin);
		assertEquals("You were waiting for: the invoice number\nThe administrator (GEBO) chose to go on without an answer: "
				+ UnansweredConditionText.GO_ON, UnansweredConditionText.goneOnWithout(condition));
	}
}
