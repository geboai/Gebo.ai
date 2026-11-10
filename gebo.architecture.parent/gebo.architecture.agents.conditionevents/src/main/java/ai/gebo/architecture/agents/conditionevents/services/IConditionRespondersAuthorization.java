/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.services;

import ai.gebo.architecture.agents.conditionevents.model.ConditionResponder;
import ai.gebo.architecture.agents.model.ConditionTarget;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;

/**
 * Who may answer a condition, by its target (see {@link ConditionTarget}):
 * whoever answers it, in the chat, in the agents processes area or with an
 * event.
 */
public interface IConditionRespondersAuthorization {

	/**
	 * @param target    the condition's target; null or empty when it has none
	 * @param session   the conversation the condition waits in
	 * @param responder who answers
	 * @return null when the responder may answer; why not otherwise
	 */
	String refusal(ConditionTarget target, GUserChatSession session, ConditionResponder responder);

	/** Whether the responder may answer. */
	default boolean mayAnswer(ConditionTarget target, GUserChatSession session, ConditionResponder responder) {
		return refusal(target, session, responder) == null;
	}
}
