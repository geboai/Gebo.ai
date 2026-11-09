/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.model;

import java.util.LinkedHashMap;

import ai.gebo.architecture.agents.model.ConditionSource;
import ai.gebo.architecture.agents.model.ConditionSource.Kind;

/**
 * The source an event is kept with, from what it claims and from who delivered
 * it. A claimed source is kept as claimed; it is verified only when the chat
 * built it, when it is the delivering account itself, when it came through a
 * channel trusted as infrastructure or when the caller holds the application
 * authority. An event claiming none is told by who delivered it.
 */
public final class ConditionSources {
	/** The system the chat's answers come from. */
	public static final String CHAT_SYSTEM = "GEBO-CHAT";

	private ConditionSources() {
	}

	/**
	 * The conversation's own user, answering in the chat: built here, never
	 * claimed, so verified.
	 *
	 * @param username    the account answering
	 * @param displayName its name to show; the username when null
	 * @param sessionCode the conversation
	 */
	public static ConditionSource forChatUser(String username, String displayName, String sessionCode) {
		final ConditionSource source = ConditionSource.of(Kind.PERSON, CHAT_SYSTEM, username, displayName);
		source.setSessionCode(sessionCode);
		source.setChannel(ConditionSource.CHAT_CHANNEL);
		source.setVerified(username != null && !username.isBlank());
		return source;
	}

	/**
	 * The source of a delivered event.
	 *
	 * @param claimed              the source the event says it comes from; may be
	 *                             null
	 * @param sourceSystem         the system the event names, the older way to
	 *                             say where it comes from; may be null
	 * @param caller               who delivered it; may be null
	 * @param applicationAuthority the authority of the accounts that deliver for
	 *                             others
	 * @return a copy, never the claimed instance
	 */
	public static ConditionSource ofDelivered(ConditionSource claimed, String sourceSystem, ConditionEventCaller caller,
			String applicationAuthority) {
		final ConditionSource source = claimed != null ? copyOf(claimed) : told(sourceSystem, caller);
		if (source.getSystem() == null && sourceSystem != null && !sourceSystem.isBlank()) {
			source.setSystem(sourceSystem.strip());
		}
		if (caller != null) {
			source.setChannel(caller.getChannel());
		}
		source.setVerified(verifies(source, caller, applicationAuthority));
		return source;
	}

	/**
	 * The source of an event kept before events carried one, from what it kept of
	 * where it came from: verified as its delivering account.
	 */
	public static ConditionSource ofKept(GConditionEvent event) {
		final ConditionSource source = ConditionSource.of(Kind.UNKNOWN, event.getSourceSystem(), event.getActor(),
				null);
		source.setChannel(event.getChannel());
		source.setVerified(source.getId() != null);
		return source;
	}

	/** An event claiming no source is told by who delivered it, the account being the source's id. */
	private static ConditionSource told(String sourceSystem, ConditionEventCaller caller) {
		return ConditionSource.of(Kind.UNKNOWN,
				sourceSystem != null && !sourceSystem.isBlank() ? sourceSystem
						: caller != null ? caller.getChannelSource() : null,
				caller != null ? caller.getName() : null, null);
	}

	private static boolean verifies(ConditionSource source, ConditionEventCaller caller, String applicationAuthority) {
		if (caller == null) {
			return false;
		}
		if (caller.isTrustedChannel() || caller.holds(applicationAuthority)) {
			return true;
		}
		return source.getId() != null && caller.getName() != null && source.getId().equals(caller.getName().strip());
	}

	private static ConditionSource copyOf(ConditionSource claimed) {
		final ConditionSource source = ConditionSource.of(claimed.getKind(), claimed.getSystem(), claimed.getId(),
				claimed.getDisplayName());
		source.setSessionCode(claimed.getSessionCode());
		source.setAgentCode(claimed.getAgentCode());
		source.setNetworkCode(claimed.getNetworkCode());
		source.setDetails(claimed.getDetails() != null ? new LinkedHashMap<String, Object>(claimed.getDetails()) : null);
		return source;
	}
}
