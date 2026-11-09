/*
 * Copyright (c) 2025+ Gebo.ai
 * Licensed under the Mozilla Public License 2.0 (MPL 2.0) with Data Protection Clauses.
 * See LICENSE file in the project root for full license information.
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 */
package ai.gebo.llms.chat.pipelines.service.impl;

import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.WaitingCondition;
import ai.gebo.llms.chat.pipelines.service.RecordedConditionReaction;
import ai.gebo.llms.chat.pipelines.service.RecordedConditionReaction.Answer;

/**
 * What the chat answers when the user's answers to a suspended network were
 * recorded and nothing runs: the answers taken, those that were not and why, and
 * what the network still waits for.
 */
public final class ConditionReactionReplies {
	static final String RECORDED = "Your answer is recorded.";
	static final String NOTHING_RECORDED = "No answer was recorded.";
	static final String NOT_TAKEN = "Not taken";
	static final String STILL_WAITING = "The agents still wait for:";
	static final String RESUMING_APART = "Every condition is answered: the agents are already resuming, their answer will appear in this conversation.";
	static final String UNNAMED = "an answer naming no condition";

	private ConditionReactionReplies() {
	}

	/** The reply to the recorded answers, in the block's words. */
	public static String replyOf(GNetworkOfAgentsSuspension block, RecordedConditionReaction recorded) {
		final StringBuilder text = new StringBuilder();
		final boolean anyTaken = recorded.answers().stream().anyMatch(answer -> !answer.isRefused());
		text.append(anyTaken ? RECORDED : NOTHING_RECORDED);
		for (Answer answer : recorded.answers()) {
			if (answer.isRefused()) {
				text.append("\n- ").append(NOT_TAKEN).append(" (").append(statementOf(block, answer.conditionId()))
						.append(")").append(answer.reason() != null ? ": " + answer.reason() : "");
			}
		}
		if (recorded.next() == RecordedConditionReaction.Next.RESPAWNING_APART) {
			text.append("\n\n").append(RESUMING_APART);
		} else if (!recorded.waitingFor().isEmpty()) {
			text.append("\n\n").append(STILL_WAITING);
			for (String statement : recorded.waitingFor()) {
				text.append("\n- ").append(statement);
			}
		}
		return text.toString();
	}

	private static String statementOf(GNetworkOfAgentsSuspension block, String conditionId) {
		if (conditionId == null) {
			return UNNAMED;
		}
		if (block != null) {
			for (WaitingCondition condition : block.conditions()) {
				if (conditionId.equals(condition.getSuspendedConditionId())
						&& condition.getWaitingConditionStatement() != null) {
					return condition.getWaitingConditionStatement();
				}
			}
		}
		return conditionId;
	}
}
