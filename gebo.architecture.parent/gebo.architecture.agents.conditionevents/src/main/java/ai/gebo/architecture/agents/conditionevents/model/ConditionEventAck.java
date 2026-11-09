/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/** What the delivery of an event did, as the source is told at once. */
@Data
@NoArgsConstructor
public class ConditionEventAck {
	public static enum Outcome {
		/** Kept and matched to the open block of the session: the respawn follows once the block is solved. */
		ACCEPTED,
		/** Kept: no block of the session waits for the condition yet; it is matched when one does. */
		PENDING,
		/** The same event of the source again: nothing changed. */
		DUPLICATE,
		/** Older than the value kept for the same condition: nothing changed. */
		SUPERSEDED,
		/** Not kept, or kept as refused: the reason says why. */
		REFUSED,
		/** The condition was already solved by an event used in a respawn, or its block is closed. */
		STALE
	}

	private Outcome outcome = null;
	private String correlationId = null;
	private String sessionCode = null;
	private String conditionId = null;
	/** The status of the event kept for the condition, when one is kept. */
	private ConditionEventStatus status = null;
	private String reason = null;

	public static ConditionEventAck of(Outcome outcome, String correlationId, String sessionCode, String conditionId,
			ConditionEventStatus status, String reason) {
		final ConditionEventAck ack = new ConditionEventAck();
		ack.setOutcome(outcome);
		ack.setCorrelationId(correlationId);
		ack.setSessionCode(sessionCode);
		ack.setConditionId(conditionId);
		ack.setStatus(status);
		ack.setReason(reason);
		return ack;
	}

	/** A refusal of an event that names no condition, or names it in a way not kept. */
	public static ConditionEventAck refused(String reason) {
		return of(Outcome.REFUSED, null, null, null, null, reason);
	}
}
