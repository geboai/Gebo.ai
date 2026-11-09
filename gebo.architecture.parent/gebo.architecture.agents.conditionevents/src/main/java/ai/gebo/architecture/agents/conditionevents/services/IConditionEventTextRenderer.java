/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.services;

import ai.gebo.architecture.agents.conditionevents.model.GConditionEvent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionDto;

/**
 * The line a solved condition leaves in the conversation's history, when its
 * event carries no text: one renderer per type of condition, as the pending
 * state names it.
 */
public interface IConditionEventTextRenderer {
	/** The type of condition rendered ({@code clarification}, {@code default}...). */
	String getConditionType();

	/**
	 * @param condition the condition as the pending state carries it
	 * @param event     the event that solved it
	 */
	String render(AgentSuspendedWaitingConditionDto condition, GConditionEvent event);
}
