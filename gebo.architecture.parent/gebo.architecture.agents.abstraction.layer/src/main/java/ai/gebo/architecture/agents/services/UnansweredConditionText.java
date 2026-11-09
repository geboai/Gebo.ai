/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.services;

import java.util.Date;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.tools.GAbstractAskUserChoiceQuestionTool;

/**
 * What a respawned agent is told of a condition the run went on without, even a
 * mandatory one: it expired, or someone chose to go on without its answer.
 */
public final class UnansweredConditionText {
	/** What the agent is asked to do in both cases. */
	public static final String GO_ON = "go on without it, saying what you assumed.";

	private UnansweredConditionText() {
	}

	/** The condition expired, and its expiry lets the run go on. */
	public static String expired(AgentSuspendedWaitingCondition<?, ?> condition, Date expiresAt) {
		return waitingFor(condition) + expiredLine(expiresAt);
	}

	/** The line after the statement of a condition that expired, its moment from the condition. */
	public static String expiredLine(AgentSuspendedWaitingCondition<?, ?> condition) {
		return expiredLine(condition != null && condition.getExpiry() != null ? condition.getExpiry().getExpiresAt() : null);
	}

	private static String expiredLine(Date expiresAt) {
		return "No answer came before " + (expiresAt != null ? expiresAt.toInstant().toString() : "its expiry") + ": "
				+ GO_ON;
	}

	/**
	 * Someone chose to go on without its answer: the conversation's user, or who
	 * {@code solvedBy} names.
	 */
	public static String goneOnWithout(AgentSuspendedWaitingCondition<?, ?> condition) {
		return waitingFor(condition) + goneOnWithoutLine(condition);
	}

	/** The line after the statement of a condition someone chose to go on without. */
	public static String goneOnWithoutLine(AgentSuspendedWaitingCondition<?, ?> condition) {
		return GAbstractAskUserChoiceQuestionTool.whoAnswered(condition) + " chose to go on without an answer: " + GO_ON;
	}

	private static String waitingFor(AgentSuspendedWaitingCondition<?, ?> condition) {
		return "You were waiting for: " + (condition != null ? condition.getWaitingConditionStatement() : null) + "\n";
	}
}
