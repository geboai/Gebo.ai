/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.services;

import java.util.Optional;

import ai.gebo.architecture.agents.conditionevents.ConditionCorrelationId;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEvent;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventAck;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventCaller;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventView;

/**
 * The one entry of the events that solve the conditions suspended networks of
 * agents wait for, whatever the source and the channel: every adapter (REST, the
 * message broker...) delivers here.
 */
public interface IConditionEventsService {
	/**
	 * Keeps the event for its condition and matches it to the block of its session
	 * that waits for it; the respawn of the block follows, apart from the delivery,
	 * once its events solve it.
	 */
	ConditionEventAck deliver(ConditionEvent event, ConditionEventCaller caller);

	/**
	 * Keeps the event as {@link #deliver} does, without evaluating its session
	 * apart: the caller takes it further itself, as the chat does when its answer
	 * solves the block and it runs the respawn.
	 */
	ConditionEventAck deliverHeld(ConditionEvent event, ConditionEventCaller caller);

	/** What became of the event kept for a condition; empty when none is, or the caller may not know. */
	Optional<ConditionEventView> status(ConditionCorrelationId correlation, ConditionEventCaller caller);

	/**
	 * Expires the pending events past their time to live, and evaluates again the
	 * sessions holding events not used yet or respawns due again.
	 */
	void sweep();
}
