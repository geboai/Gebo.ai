/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.model;

/** Where the event solving a condition is, from its arrival to the respawn that used it. */
public enum ConditionEventStatus {
	/** No block of the session waits for the condition yet: the run that asked has not ended. */
	PENDING,
	/** The open block of the session waits for the condition and the value fits it. */
	ACCEPTED,
	/** Part of the reaction of a respawn now running. */
	DISPATCHED,
	/** Part of the reaction of a respawn that ended or suspended again: the agent was told. */
	APPLIED,
	/** The value does not fit the condition (an option not offered, another type of condition...). */
	REFUSED,
	/** The block that waited for the condition was respawned or dropped before the event was used. */
	STALE,
	/** Pending for longer than its time to live: no block ever waited for it. */
	EXPIRED,
	/** The respawn it was part of failed as many times as it may: nobody tries it again by itself. */
	FAILED;

	/**
	 * Whether a new delivery of the same condition replaces the value kept, and is
	 * matched again: the event was not used.
	 */
	public boolean isReplaceable() {
		return this == PENDING || this == ACCEPTED || this == REFUSED || this == EXPIRED || this == FAILED;
	}
}
