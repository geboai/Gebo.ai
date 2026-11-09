/*
 * Copyright (c) 2025+ Gebo.ai
 * Licensed under the Mozilla Public License 2.0 (MPL 2.0) with Data Protection Clauses.
 * See LICENSE file in the project root for full license information.
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 */
package ai.gebo.llms.chat.pipelines.service;

import java.util.List;

/**
 * What a chat reaction recorded one condition at a time did: each answer's
 * outcome, the mandatory conditions the block still waits for, and whether the
 * network resumes.
 *
 * @param answers    the outcome of each answer, in the reaction's order
 * @param waitingFor the statements of the mandatory conditions still unanswered
 * @param resumes    whether the block is solved and the network resumes
 */
public record RecordedConditionReaction(List<Answer> answers, List<String> waitingFor, boolean resumes) {
	/** The outcome of an answer the condition did not take. */
	public static final String REFUSED = "REFUSED";

	public RecordedConditionReaction {
		answers = answers != null ? List.copyOf(answers) : List.of();
		waitingFor = waitingFor != null ? List.copyOf(waitingFor) : List.of();
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

	/** Whether some answer was refused: the reason of each says why. */
	public boolean anyRefused() {
		return answers.stream().anyMatch(Answer::isRefused);
	}
}
