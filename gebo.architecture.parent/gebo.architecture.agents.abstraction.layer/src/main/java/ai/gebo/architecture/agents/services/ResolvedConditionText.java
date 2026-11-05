/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.services;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.tools.GAbstractAskUserChoiceQuestionTool;

/**
 * What a respawned agent tells its model about the conditions it waited for: the
 * answer, for a question the user answered (see
 * {@link GAbstractAskUserChoiceQuestionTool#answered}); for any other condition, what was
 * waited for and the value that came; and, for a condition the user declined,
 * that the work goes on without it. The notes the agent wrote to itself when it
 * stopped follow, whichever of these it is.
 */
public final class ResolvedConditionText {
	private ResolvedConditionText() {
	}

	/** The text the model is given before the message it is re-executed on; null for no condition. */
	public static String render(AgentSuspendedWaitingCondition<?, ?> condition) {
		if (condition == null) {
			return null;
		}
		final String notes = condition.getNotesForLater();
		return notes != null && !notes.isBlank()
				? outcomeOf(condition) + "\nYour notes from before you stopped: " + notes.strip()
				: outcomeOf(condition);
	}

	private static String outcomeOf(AgentSuspendedWaitingCondition<?, ?> condition) {
		if (condition.getInstanceValue() == null) {
			return "You were waiting for: " + condition.getWaitingConditionStatement()
					+ "\nThe user chose not to answer: go on without it, saying what you assumed.";
		}
		final String clarification = GAbstractAskUserChoiceQuestionTool.answered(condition);
		if (clarification != null) {
			return clarification;
		}
		return "You were waiting for: " + condition.getWaitingConditionStatement() + "\nIt came: "
				+ condition.getInstanceValue();
	}

	/** The message a respawned agent is re-executed on, the resolved conditions told first. */
	@SuppressWarnings("rawtypes")
	public static String prepend(List<AgentSuspendedWaitingCondition> conditions, String message) {
		final List<String> rendered = new ArrayList<String>();
		if (conditions != null) {
			for (AgentSuspendedWaitingCondition<?, ?> condition : conditions) {
				final String told = render(condition);
				if (told != null) {
					rendered.add(told);
				}
			}
		}
		if (rendered.isEmpty()) {
			return message;
		}
		final String told = String.join("\n\n", rendered);
		return message != null && !message.isBlank() ? told + "\n\n" + message : told;
	}
}
