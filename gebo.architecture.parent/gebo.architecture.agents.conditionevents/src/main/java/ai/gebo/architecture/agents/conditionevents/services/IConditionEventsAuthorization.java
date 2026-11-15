/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.services;

import ai.gebo.architecture.agents.conditionevents.model.ConditionEventCaller;
import ai.gebo.architecture.agents.model.ConditionSource;
import ai.gebo.architecture.agents.model.ConditionTarget;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;

/** Who may deliver events to a session, and read what became of them. */
public interface IConditionEventsAuthorization {
	/**
	 * @return null when the caller may; why not otherwise
	 */
	String refusal(ConditionEventCaller caller, GUserChatSession session);

	/**
	 * Who may answer a condition with an event: by the condition's target, when
	 * the condition is known and has one (see {@link ConditionTarget}); as
	 * {@link #refusal(ConditionEventCaller, GUserChatSession)} otherwise.
	 *
	 * @param target the target of the condition the event answers; null when the
	 *               condition is not known yet, or has none
	 * @param source the source of the event, as it is kept
	 * @return null when the caller may; why not otherwise
	 */
	default String refusal(ConditionEventCaller caller, GUserChatSession session, ConditionTarget target,
			ConditionSource source) {
		return target != null && target.isSet() ? "The condition is waited from " + target.label() + " only"
				: refusal(caller, session);
	}
}
