/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.model;

import java.io.Serializable;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Who or what solved a condition a suspended network of agents waited for: a
 * person, an agent or a system, named generically, with the details and the
 * session of a person or an agent. It says who the source is, as told or as
 * known; who delivered the answer is recorded apart, and {@link #isVerified()}
 * says whether the two were found to agree.
 */
@Data
@NoArgsConstructor
public class ConditionSource implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The channel of the chat, where the conversation's own user answers. */
	public static final String CHAT_CHANNEL = "CHAT";
	/**
	 * The channel of an answer given in the chat by someone other than its owner,
	 * someone it is shared with: they are named, not told as the conversation's user.
	 */
	public static final String SHARED_CHAT_CHANNEL = "CHAT_SHARED";

	public enum Kind {
		/** A human being: a user of the platform or of another system. */
		PERSON,
		/** An agent, of this platform or of another. */
		AGENT,
		/** A system, a workflow, a robot or any other software. */
		SYSTEM,
		/** Not said. */
		UNKNOWN
	}

	private Kind kind = Kind.UNKNOWN;
	/** The system the source belongs to or is, as it names itself ({@code ERP}, {@code WORKFLOW}...). */
	private String system = null;
	/** The source's id in its system: a username, an agent's code, a system's id. */
	private String id = null;
	/** The name to show of it. */
	private String displayName = null;
	/** The session of the person or the agent, in its system, when it has one. */
	private String sessionCode = null;
	/** The agent's code, and the network it runs in, for an agent. */
	private String agentCode = null;
	private String networkCode = null;
	/** The channel the answer came through ({@code CHAT}, {@code REST}, {@code MESSAGE}...). */
	private String channel = null;
	/** Anything else the source tells of itself. */
	private LinkedHashMap<String, Object> details = null;
	/**
	 * Whether who delivered the answer was found to be, or to stand for, the
	 * source: what it says of itself is otherwise as it was told.
	 */
	private boolean verified = false;

	/** A source of the kind, named in its system. */
	public static ConditionSource of(Kind kind, String system, String id, String displayName) {
		final ConditionSource source = new ConditionSource();
		source.setKind(kind != null ? kind : Kind.UNKNOWN);
		source.setSystem(blankToNull(system));
		source.setId(blankToNull(id));
		source.setDisplayName(blankToNull(displayName));
		return source;
	}

	/** Whether it is the conversation's own user answering in the chat. */
	@JsonIgnore
	public boolean isConversationUser() {
		return verified && kind == Kind.PERSON && CHAT_CHANNEL.equals(channel);
	}

	/**
	 * How it is named to people and to agents: its name, the kind of an agent, its
	 * system when the name is not the system's, and whether it is unverified.
	 */
	public String label() {
		final String name = firstOf(displayName, id, system);
		if (name == null) {
			return "an unknown source" + (verified ? "" : UNVERIFIED);
		}
		final StringBuilder label = new StringBuilder();
		if (kind == Kind.AGENT) {
			label.append("agent ");
		}
		label.append(name);
		if (system != null && !system.isBlank() && !system.strip().equals(name)) {
			label.append(" (").append(system.strip()).append(")");
		}
		if (!verified) {
			label.append(UNVERIFIED);
		}
		return label.toString();
	}

	static final String UNVERIFIED = ", unverified";

	private static String firstOf(String... names) {
		for (String name : names) {
			if (name != null && !name.isBlank()) {
				return name.strip();
			}
		}
		return null;
	}

	private static String blankToNull(String value) {
		return value != null && !value.isBlank() ? value.strip() : null;
	}

	@Override
	public String toString() {
		return kind + ":" + (system != null ? system + "/" : "") + id + (verified ? "" : " (unverified)");
	}
}
