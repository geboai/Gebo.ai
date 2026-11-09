/*
 * Copyright (c) 2025+ Gebo.ai
 * Licensed under the Mozilla Public License 2.0 (MPL 2.0) with Data Protection Clauses.
 * See LICENSE file in the project root for full license information.
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 */
package ai.gebo.llms.chat.pipelines.service;

import java.util.List;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedStateReaction;

/**
 * What a chat reaction recorded one condition at a time did: each answer's
 * outcome, the mandatory conditions the block still waits for, and what the
 * request does next.
 *
 * @param answers    the outcome of each answer, in the reaction's order
 * @param waitingFor the statements of the mandatory conditions still unanswered
 * @param next       what the request does next
 * @param reaction   with {@link Next#RESPAWN}, the reaction the request respawns
 *                   the network with: the answers of every source, the optional
 *                   conditions none answered declined
 */
public record RecordedConditionReaction(List<Answer> answers, List<String> waitingFor, Next next,
		NetworkOfAgentsSuspendedStateReaction reaction) {
	/** The outcome of an answer the condition did not take. */
	public static final String REFUSED = "REFUSED";

	/** What the chat request does once its answers are recorded. */
	public enum Next {
		/** The block still waits for some mandatory condition: nothing runs. */
		WAIT,
		/** The block is solved and its respawn claimed for the request, which runs it. */
		RESPAWN,
		/** The block is solved, but another already runs its respawn. */
		RESPAWNING_APART
	}

	public RecordedConditionReaction {
		answers = answers != null ? List.copyOf(answers) : List.of();
		waitingFor = waitingFor != null ? List.copyOf(waitingFor) : List.of();
		next = next != null ? next : Next.WAIT;
	}

	/**
	 * The outcome of one answer.
	 *
	 * @param conditionId the condition answered, null when the answer named none
	 * @param outcome     {@code ACCEPTED}, {@code DUPLICATE}, {@code STALE},
	 *                    {@value RecordedConditionReaction#REFUSED}...
	 * @param reason      why, when it was not accepted
	 */
	public record Answer(String conditionId, String outcome, String reason) {
		public boolean isRefused() {
			return REFUSED.equals(outcome);
		}
	}

	/** Whether the request respawns the network itself. */
	public boolean resumes() {
		return next == Next.RESPAWN;
	}

	/** Whether some answer was refused: the reason of each says why. */
	public boolean anyRefused() {
		return answers.stream().anyMatch(Answer::isRefused);
	}
}
