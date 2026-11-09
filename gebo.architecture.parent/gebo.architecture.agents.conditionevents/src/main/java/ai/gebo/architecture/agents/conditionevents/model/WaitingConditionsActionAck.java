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

/** What became of going on without some conditions of a suspended run, or of abandoning it. */
@Data
@NoArgsConstructor
public class WaitingConditionsActionAck {
	public enum Outcome {
		/** Done: the conditions are answered with no value, or the run is abandoned. */
		DONE,
		/** No open block has that code. */
		NOT_FOUND,
		/** Not done: the reason says why. */
		REFUSED
	}

	private String blockCode = null;
	private Outcome outcome = null;
	private String reason = null;

	public static WaitingConditionsActionAck of(String blockCode, Outcome outcome, String reason) {
		final WaitingConditionsActionAck ack = new WaitingConditionsActionAck();
		ack.setBlockCode(blockCode);
		ack.setOutcome(outcome);
		ack.setReason(reason);
		return ack;
	}
}
