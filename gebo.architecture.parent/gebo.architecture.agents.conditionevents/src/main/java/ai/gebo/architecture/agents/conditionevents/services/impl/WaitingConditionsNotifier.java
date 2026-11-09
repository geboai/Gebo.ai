/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.model.GUserMessage.MsgServerity;
import ai.gebo.system.messages.services.IGSystemMessagesService;
import ai.gebo.system.messages.services.SystemMessagePublication;

/**
 * Tells the owner of a suspended run, by a system message for that person only,
 * that the run waits for someone: when it suspended with nobody watching it, and
 * when a condition expired without letting the run go on. One message of each
 * kind per block, taken back once the block is respawned or dropped. Without
 * the system messages service it tells nobody.
 */
@Service
public class WaitingConditionsNotifier {
	private static final Logger LOGGER = LoggerFactory.getLogger(WaitingConditionsNotifier.class);
	/** The source of the messages, as the system messages service keys them. */
	public static final String SOURCE = "AGENTS-WAITING-CONDITIONS";
	static final String UNWATCHED = "unwatched:";
	static final String EXPIRED = "expired:";
	private final IGSystemMessagesService messages;

	public WaitingConditionsNotifier(@Autowired(required = false) IGSystemMessagesService messages) {
		this.messages = messages;
	}

	/** The run suspended with nobody watching it: it waits for the listed conditions. */
	public void unwatched(String blockCode, String owner, String conversationCode, List<String> waitingFor) {
		publish(UNWATCHED + blockCode, MsgServerity.info, owner,
				"A network of agents waits for an answer",
				"The run of conversation " + conversationCode + " stopped, with nobody watching it, waiting for: "
						+ listOf(waitingFor) + ". Answer from the conversation or from the waiting conditions panel.");
	}

	/** A condition expired and keeps waiting: someone has to go on without it or abandon the run. */
	public void expired(String blockCode, String owner, String conversationCode, List<String> expired) {
		publish(EXPIRED + blockCode, MsgServerity.warn, owner,
				"A network of agents is stuck on an expired condition",
				"The run of conversation " + conversationCode + " still waits for: " + listOf(expired)
						+ ", past its expiry. Answer it, go on without it or abandon the run, from the conversation or from the waiting conditions panel.");
	}

	/** The block is respawned or dropped: what was told of it is taken back. */
	public void settled(String blockCode) {
		if (messages == null || blockCode == null) {
			return;
		}
		for (String kind : List.of(UNWATCHED, EXPIRED)) {
			try {
				messages.retract(SOURCE, kind + blockCode);
			} catch (RuntimeException e) {
				LOGGER.warn("Cannot take back the message " + kind + blockCode + ": " + e.getMessage(), e);
			}
		}
	}

	private void publish(String key, MsgServerity severity, String owner, String summary, String detail) {
		if (messages == null || owner == null || owner.isBlank()) {
			return;
		}
		try {
			messages.publish(
					SystemMessagePublication.forPeople(SOURCE, key, severity, summary, detail, true, null, List.of(owner)));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Told " + owner + ": " + key);
			}
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot tell " + owner + " of " + key + ": " + e.getMessage(), e);
		}
	}

	private static String listOf(List<String> statements) {
		final String listed = statements == null ? ""
				: String.join("; ", statements.stream().filter(s -> s != null && !s.isBlank()).toList());
		return listed.isEmpty() ? "a condition" : listed;
	}
}
