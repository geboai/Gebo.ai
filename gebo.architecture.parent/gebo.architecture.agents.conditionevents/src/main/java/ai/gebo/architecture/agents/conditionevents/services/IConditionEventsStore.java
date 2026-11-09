/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import ai.gebo.architecture.agents.conditionevents.model.GConditionDispatch;
import ai.gebo.architecture.agents.conditionevents.model.GConditionEvent;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;

/**
 * What the events layer keeps, and the one read it makes of the blocks beyond
 * the open one: the closed block that waited for a condition.
 */
public interface IConditionEventsStore {
	Optional<GConditionEvent> findEvent(String correlationId);

	GConditionEvent saveEvent(GConditionEvent event);

	/** The events of a session not used yet: pending or accepted. */
	List<GConditionEvent> liveEventsOf(String sessionCode);

	/** The events that were part of the respawn of a block. */
	List<GConditionEvent> eventsOfDispatch(String blockCode);

	/** The pending events last delivered before the date. */
	List<GConditionEvent> pendingLastReceivedBefore(Date before);

	/** The sessions holding events not used yet. */
	Set<String> sessionsWithLiveEvents();

	/**
	 * Claims the respawn of a block, atomically among instances: written the first
	 * time, claimed again when it failed and its backoff is over, or when its lease
	 * is over. Empty when another holds it, it waits for its backoff, or it is over.
	 */
	Optional<GConditionDispatch> claim(String blockCode, String sessionCode, Date now, long leaseMillis);

	GConditionDispatch saveDispatch(GConditionDispatch dispatch);

	Optional<GConditionDispatch> findDispatch(String blockCode);

	/** Forgets the respawn of a block, so that it is claimed afresh. */
	void removeDispatch(String blockCode);

	/** The respawns to claim again: failed ones whose backoff is over, running ones whose lease is. */
	List<GConditionDispatch> dispatchesDue(Date now);

	/**
	 * The newest block of the session that waited for the condition, when it is no
	 * longer open: respawned or dropped.
	 */
	Optional<GNetworkOfAgentsSuspension> closedBlockHolding(String sessionCode, String conditionId);
}
