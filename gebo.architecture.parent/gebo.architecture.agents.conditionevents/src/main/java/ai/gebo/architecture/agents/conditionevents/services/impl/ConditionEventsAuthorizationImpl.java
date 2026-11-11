/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.conditionevents.config.ConditionEventsConfig;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventCaller;
import ai.gebo.architecture.agents.conditionevents.model.ConditionResponder;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventsAuthorization;
import ai.gebo.architecture.agents.conditionevents.services.IConditionRespondersAuthorization;
import ai.gebo.architecture.agents.model.ConditionSource;
import ai.gebo.architecture.agents.model.ConditionTarget;
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
	static final String NOT_ALLOWED = "The caller may not deliver events to this session: it is not its owner, has no access to it,"
			+ " and is neither an administrator nor an application account";
	private final ConditionEventsConfig config;
	private final IConditionRespondersAuthorization responders;

	/** Groups not taken into account: the responders' check has no directory of the users. */
	public ConditionEventsAuthorizationImpl(ConditionEventsConfig config) {
		this(config, new ConditionRespondersAuthorizationImpl(null));
	}

	@Autowired
	public ConditionEventsAuthorizationImpl(ConditionEventsConfig config, IConditionRespondersAuthorization responders) {
		this.config = config;
		this.responders = responders;
	}

	/**
	 * The usual rule, for an event whose condition is not known, or has no
	 * target: its owner, anyone with access to the conversation, an
	 * administrator, a trusted channel, an application account.
	 */
	@Override
	public String refusal(ConditionEventCaller caller, GUserChatSession session) {
		return refusal(caller, session, null, null);
	}

	@Override
	public String refusal(ConditionEventCaller caller, GUserChatSession session, ConditionTarget target,
			ConditionSource source) {
		if (caller == null) {
			return NO_CALLER;
		}
		final String refusal = responders.refusal(target, session,
				ConditionResponder.of(caller, source, config != null ? config.getApplicationAuthority() : null));
		if (refusal == null) {
			return null;
		}
		if (target != null && target.isSet()) {
			return refusal;
		}
		return caller.getName() == null ? NO_CALLER : NOT_ALLOWED;
	}
}
