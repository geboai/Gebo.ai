/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.services;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.tools.AskUserClarificationTool;

/**
 * What a respawned agent tells its model about the condition it waited for: the
 * answer, for a clarification the user gave (see
 * {@link AskUserClarificationTool#answered}); for any other condition, what was
 * waited for and the value that came; and, for a condition the user declined,
 * that the work goes on without it.
 */
public final class ResolvedConditionText {
	private ResolvedConditionText() {
	}

	/** The text the model is given before the message it is re-executed on; null for no condition. */
	public static String render(AgentSuspendedWaitingCondition<?, ?> condition) {
		if (condition == null) {
			return null;
		}
		if (condition.getInstanceValue() == null) {
			return "You were waiting for: " + condition.getWaitingConditionStatement()
					+ "\nThe user chose not to answer: go on without it, saying what you assumed.";
		}
		final String clarification = AskUserClarificationTool.answered(condition);
		if (clarification != null) {
			return clarification;
		}
		return "You were waiting for: " + condition.getWaitingConditionStatement() + "\nIt came: "
				+ condition.getInstanceValue();
	}

	/** The message a respawned agent is re-executed on, the resolved condition told first. */
	public static String prepend(AgentSuspendedWaitingCondition<?, ?> condition, String message) {
		final String rendered = render(condition);
		if (rendered == null) {
			return message;
		}
		return message != null && !message.isBlank() ? rendered + "\n\n" + message : rendered;
	}
}
