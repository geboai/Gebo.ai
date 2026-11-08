/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.services.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.conditionevents.config.ConditionEventsConfig;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventCaller;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventsAuthorization;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;

/**
 * The session's owner may deliver to its own sessions; an account holding the
 * application authority ({@code ai.gebo.agents.conditionevents.applicationAuthority})
 * may deliver to any; a channel trusted as infrastructure (the message broker)
 * may too. The respawn runs as the session's owner whoever delivered.
 */
@Service
public class ConditionEventsAuthorizationImpl implements IConditionEventsAuthorization {
	static final String NO_CALLER = "Nobody is behind the event";
	static final String NOT_ALLOWED = "The caller may not deliver events to this session: it is neither its owner nor an application account";
	private final ConditionEventsConfig config;

	public ConditionEventsAuthorizationImpl(ConditionEventsConfig config) {
		this.config = config;
	}

	@Override
	public String refusal(ConditionEventCaller caller, GUserChatSession session) {
		if (caller == null) {
			return NO_CALLER;
		}
		if (caller.isTrustedChannel()) {
			return null;
		}
		if (caller.getName() != null && session != null && caller.getName().equals(session.getUsername())) {
			return null;
		}
		if (caller.holds(config.getApplicationAuthority())) {
			return null;
		}
		return caller.getName() == null ? NO_CALLER : NOT_ALLOWED;
	}
}
