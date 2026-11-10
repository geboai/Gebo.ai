/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.model;

import java.util.Set;

import ai.gebo.architecture.agents.model.ConditionSource;
import ai.gebo.architecture.agents.model.ConditionSource.Kind;

/**
 * Who answers a condition, as the check of who may answer it reads them: the
 * account that answers or delivers the answer, whether it is an administrator,
 * whether it stands for others (a trusted channel, or an account holding the
 * application authority), and the source the answer is from, as delivered.
 *
 * @param account        the account answering or delivering; null for a trusted
 *                       channel with no sender
 * @param administrator  whether the account is an administrator
 * @param standsForOthers whether it delivers for others: a trusted channel or
 *                       an application account, whose source is then verified
 * @param source         the source of the answer; null when none is known
 */
public record ConditionResponder(String account, boolean administrator, boolean standsForOthers,
		ConditionSource source) {
	/** The authority of the administrators, as Spring Security names it. */
	public static final String ADMIN_AUTHORITY = "ROLE_ADMIN";

	/**
	 * A person answering with their own account: in the chat, or in the agents
	 * processes area.
	 */
	public static ConditionResponder person(String username, boolean administrator) {
		final ConditionSource source = ConditionSource.of(Kind.PERSON, null, username, null);
		source.setVerified(username != null && !username.isBlank());
		return new ConditionResponder(username, administrator, false, source);
	}

	/**
	 * The one who delivered an event, with the source it was kept with.
	 *
	 * @param caller               who delivered it
	 * @param source               its source, as {@link ConditionSources#ofDelivered}
	 *                             made it
	 * @param applicationAuthority the authority of the accounts that deliver for
	 *                             others
	 */
	public static ConditionResponder of(ConditionEventCaller caller, ConditionSource source,
			String applicationAuthority) {
		if (caller == null) {
			return new ConditionResponder(null, false, false, source);
		}
		return new ConditionResponder(caller.getName(), isAdministrator(caller.getAuthorities()),
				caller.isTrustedChannel() || caller.holds(applicationAuthority), source);
	}

	/** The one who delivered a kept event, as the event kept them, for a check made once it is matched. */
	public static ConditionResponder ofKept(GConditionEvent event) {
		return new ConditionResponder(event.getActor(), event.isDeliveredByAdministrator(),
				event.isDeliveredForOthers(), event.sourceOrKept());
	}

	/** Whether the authorities are an administrator's. */
	public static boolean isAdministrator(Set<String> authorities) {
		if (authorities == null) {
			return false;
		}
		for (String authority : authorities) {
			if (authority != null && (ADMIN_AUTHORITY.equals(authority.strip()) || "ADMIN".equals(authority.strip()))) {
				return true;
			}
		}
		return false;
	}
}
