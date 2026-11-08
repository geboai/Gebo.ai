/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.services.impl;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.conditionevents.config.ConditionEventsConfig;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventStatus;
import ai.gebo.architecture.agents.conditionevents.model.GConditionEvent;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventsStore;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.WaitingCondition;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;
import ai.gebo.llms.chat.pipelines.service.IAgentSuspendedWaitingConditionTransportTranslator;
import ai.gebo.llms.chat.pipelines.service.impl.NetworkOfAgentsSuspensionTransport;

/**
 * Matches an event to the block of its session that waits for its condition:
 * <ul>
 * <li>the open block waits for it: the value is read by the condition's own
 * translator, on a copy of the saved condition, as the respawn will read it, so
 * that a value that does not fit is refused when it arrives, not when the block
 * is respawned;</li>
 * <li>a block that waited for it is closed: the event is stale, or applied when
 * it was the respawn of this very event that closed it;</li>
 * <li>no block waits for it yet (the run that asked has not ended): pending, until
 * its time to live is over.</li>
 * </ul>
 * The blocks are read, never written.
 */
@Service
public class ConditionEventMatcher {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConditionEventMatcher.class);
	static final String NOT_WAITED_YET = "No block of the session waits for the condition yet";
	static final String EXPIRED = "No block of the session waited for the condition within its time to live";
	static final String BLOCK_RESPAWNED = "The block that waited for the condition was already respawned: ";
	static final String BLOCK_DROPPED = "The block that waited for the condition was dropped: ";
	static final String ANOTHER_TYPE = "The condition is of type %s, the event says: %s";
	private final IConditionEventsStore store;
	private final NetworkOfAgentsSuspensionTransport transport;
	private final ConditionEventsConfig config;

	public ConditionEventMatcher(IConditionEventsStore store, NetworkOfAgentsSuspensionTransport transport,
			ConditionEventsConfig config) {
		this.store = store;
		this.transport = transport;
		this.config = config;
	}

	/**
	 * Sets the event's status, its block and its reason.
	 *
	 * @param open the open block of the event's session, null when it has none
	 */
	public void match(GConditionEvent event, GNetworkOfAgentsSuspension open, Date now) {
		final WaitingCondition waiting = open != null ? waitingFor(open, event.getConditionId()) : null;
		if (waiting != null) {
			check(event, open, waiting, now);
			return;
		}
		final Optional<GNetworkOfAgentsSuspension> closed = store.closedBlockHolding(event.getSessionCode(),
				event.getConditionId());
		event.setAcceptedAt(null);
		if (closed.isPresent()) {
			final GNetworkOfAgentsSuspension block = closed.get();
			event.setBlockCode(block.getCode());
			if (block.getRespawnedAt() != null && block.getCode() != null
					&& block.getCode().equals(event.getDispatchCode())) {
				// the respawn of this event closed the block, and was not told back: an
				// instance went down between the two
				event.setStatus(ConditionEventStatus.APPLIED);
				event.setAppliedAt(block.getRespawnedAt());
				event.setRespawnedToBlockCode(block.getRespawnedToSuspensionCode());
				event.setReason(null);
				return;
			}
			event.setStatus(ConditionEventStatus.STALE);
			event.setReason((block.getRespawnedAt() != null ? BLOCK_RESPAWNED : BLOCK_DROPPED) + block.getCode());
			return;
		}
		event.setBlockCode(null);
		final Date last = event.getLastReceivedAt() != null ? event.getLastReceivedAt() : event.getReceivedAt();
		if (last != null && last.getTime() + config.getPendingTimeToLiveMillis() < now.getTime()) {
			event.setStatus(ConditionEventStatus.EXPIRED);
			event.setReason(EXPIRED);
		} else {
			event.setStatus(ConditionEventStatus.PENDING);
			event.setReason(NOT_WAITED_YET);
		}
	}

	/** The condition of the block with the id, null when the block does not wait for it. */
	static WaitingCondition waitingFor(GNetworkOfAgentsSuspension block, String conditionId) {
		if (conditionId == null) {
			return null;
		}
		for (WaitingCondition waiting : block.conditions()) {
			if (waiting != null && conditionId.equals(waiting.getSuspendedConditionId())) {
				return waiting;
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void check(GConditionEvent event, GNetworkOfAgentsSuspension open, WaitingCondition waiting, Date now) {
		event.setBlockCode(open.getCode());
		final AgentSuspendedWaitingCondition saved = waiting.toCondition();
		final IAgentSuspendedWaitingConditionTransportTranslator translator = transport.translatorOf(saved);
		final AgentSuspendedWaitingConditionReactionDto reaction = event.toReaction();
		if (!translator.canTranslateReaction(reaction)) {
			refuse(event, String.format(ANOTHER_TYPE, translator.getId(), event.getConditionType()));
			return;
		}
		try {
			translator.translateReaction(reaction, saved);
		} catch (IllegalArgumentException refused) {
			refuse(event, refused.getMessage());
			return;
		}
		if (event.getStatus() != ConditionEventStatus.ACCEPTED || event.getAcceptedAt() == null) {
			event.setAcceptedAt(now);
		}
		event.setStatus(ConditionEventStatus.ACCEPTED);
		event.setReason(null);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Condition event:" + event.getCode() + " accepted for block:" + open.getCode());
		}
	}

	private static void refuse(GConditionEvent event, String reason) {
		event.setStatus(ConditionEventStatus.REFUSED);
		event.setAcceptedAt(null);
		event.setReason(reason);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Condition event:" + event.getCode() + " refused: " + reason);
		}
	}
}
