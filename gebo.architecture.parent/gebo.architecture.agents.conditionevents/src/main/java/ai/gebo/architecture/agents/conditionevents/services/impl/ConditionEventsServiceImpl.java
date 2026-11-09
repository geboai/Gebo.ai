/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.services.impl;

import java.time.Clock;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.conditionevents.ConditionCorrelationId;
import ai.gebo.architecture.agents.conditionevents.config.ConditionEventsConfig;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEvent;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventAck;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventAck.Outcome;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventCaller;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventStatus;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventView;
import ai.gebo.architecture.agents.conditionevents.model.GConditionDispatch;
import ai.gebo.architecture.agents.conditionevents.model.GConditionEvent;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventsAuthorization;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventsService;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventsStore;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;

/**
 * Keeps the events of the conditions, one per session and condition:
 * <ul>
 * <li>the condition is named in either form (see {@link ConditionCorrelationId});
 * the session must exist and the caller may deliver to it (see
 * {@link IConditionEventsAuthorization}), or nothing is kept;</li>
 * <li>a new delivery of a condition whose event was not used replaces it, unless
 * it is the same event of the source (a duplicate) or older than the one kept (by
 * the source's timestamps); one whose event was used is stale;</li>
 * <li>the event is matched to the block of its session (see
 * {@link ConditionEventMatcher}); accepted, the session is evaluated apart from
 * the delivery (see {@link ConditionEventDispatcher}), so the source is answered
 * at once.</li>
 * </ul>
 */
@Service
public class ConditionEventsServiceImpl implements IConditionEventsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConditionEventsServiceImpl.class);
	/**
	 * What a request context names as its session when it has no request
	 * ({@code LLMChatRequestResources.getSessionID()}): the blocks of every such run
	 * share it, so it addresses no session.
	 */
	static final String NO_CONTEXT_SESSION = "No current context code";
	static final String NO_EVENT = "No event";
	static final String NOT_A_SESSION = "The session code names no session: ";
	static final String NO_TYPE = "The event does not say the type of the condition it solves";
	static final String NO_SUCH_SESSION = "No such session: ";
	static final String ALREADY_USED = "The condition was already solved by an event the respawn used, or its block closed";
	static final String OLDER = "Older than the value kept for the condition";
	static final String SAME_EVENT = "The same event of the source was delivered before";
	private final IConditionEventsStore store;
	private final ConditionEventMatcher matcher;
	private final ConditionEventDispatcher dispatcher;
	private final GUserChatSessionRepository sessions;
	private final GNetworkOfAgentsSuspensionRepository blocks;
	private final IConditionEventsAuthorization authorization;
	private final ConditionEventsConfig config;
	private final Executor evaluations;
	private final Clock clock;

	@Autowired
	public ConditionEventsServiceImpl(IConditionEventsStore store, ConditionEventMatcher matcher,
			ConditionEventDispatcher dispatcher, GUserChatSessionRepository sessions,
			GNetworkOfAgentsSuspensionRepository blocks, IConditionEventsAuthorization authorization,
			ConditionEventsConfig config, ObjectProvider<IGeboThreadManager> threads) {
		this(store, matcher, dispatcher, sessions, blocks, authorization, config, lazyExecutorOf(threads),
				Clock.systemUTC());
	}

	ConditionEventsServiceImpl(IConditionEventsStore store, ConditionEventMatcher matcher,
			ConditionEventDispatcher dispatcher, GUserChatSessionRepository sessions,
			GNetworkOfAgentsSuspensionRepository blocks, IConditionEventsAuthorization authorization,
			ConditionEventsConfig config, Executor evaluations, Clock clock) {
		this.store = store;
		this.matcher = matcher;
		this.dispatcher = dispatcher;
		this.sessions = sessions;
		this.blocks = blocks;
		this.authorization = authorization;
		this.config = config;
		this.evaluations = evaluations;
		this.clock = clock;
	}

	/**
	 * The thread manager is looked up on each evaluation, never at construction:
	 * this service can be created while the thread manager itself is in creation.
	 */
	static Executor lazyExecutorOf(ObjectProvider<IGeboThreadManager> threads) {
		return task -> {
			final IGeboThreadManager manager = threads != null ? threads.getIfAvailable() : null;
			final Executor executor = manager != null ? manager.getExecutorService() : null;
			(executor != null ? executor : (Executor) Runnable::run).execute(task);
		};
	}

	@Override
	public ConditionEventAck deliver(ConditionEvent event, ConditionEventCaller caller) {
		return deliver(event, caller, true);
	}

	@Override
	public ConditionEventAck deliverHeld(ConditionEvent event, ConditionEventCaller caller) {
		return deliver(event, caller, false);
	}

	private ConditionEventAck deliver(ConditionEvent event, ConditionEventCaller caller, boolean evaluate) {
		if (event == null) {
			return ConditionEventAck.refused(NO_EVENT);
		}
		final ConditionCorrelationId id;
		try {
			id = ConditionCorrelationId.of(event.getCorrelationId(), event.getSessionCode(), event.getConditionId());
		} catch (IllegalArgumentException notNamed) {
			return ConditionEventAck.refused(notNamed.getMessage());
		}
		if (NO_CONTEXT_SESSION.equals(id.sessionCode())) {
			return refused(id, NOT_A_SESSION + id.sessionCode());
		}
		if (event.getConditionType() == null || event.getConditionType().isBlank()) {
			return refused(id, NO_TYPE);
		}
		final GUserChatSession session = sessions.findById(id.sessionCode()).orElse(null);
		if (session == null) {
			return refused(id, NO_SUCH_SESSION + id.sessionCode());
		}
		final String refusal = authorization.refusal(caller, session);
		if (refusal != null) {
			LOGGER.warn("Condition event:" + id + " refused to " + caller + ": " + refusal);
			return refused(id, refusal);
		}
		final Date now = new Date(clock.millis());
		GConditionEvent kept = store.findEvent(id.composed()).orElse(null);
		if (kept != null) {
			kept.setDeliveries(kept.getDeliveries() + 1);
			kept.setLastReceivedAt(now);
			kept.setDateModified(now);
			if (event.getSourceEventId() != null && event.getSourceEventId().equals(kept.getSourceEventId())) {
				store.saveEvent(kept);
				return ack(Outcome.DUPLICATE, kept, SAME_EVENT);
			}
			if (!kept.getStatus().isReplaceable()) {
				store.saveEvent(kept);
				return ack(Outcome.STALE, kept, ALREADY_USED);
			}
			if (isOlder(event, kept)) {
				store.saveEvent(kept);
				return ack(Outcome.SUPERSEDED, kept, OLDER);
			}
			if (kept.getStatus() == ConditionEventStatus.FAILED) {
				revive(kept.getDispatchCode(), now);
			}
		} else {
			kept = new GConditionEvent();
			kept.setCode(id.composed());
			kept.setSessionCode(id.sessionCode());
			kept.setConditionId(id.conditionId());
			kept.setReceivedAt(now);
			kept.setLastReceivedAt(now);
			kept.setDeliveries(1);
			kept.setDateCreated(now);
			kept.setDateModified(now);
			kept.setUserCreated(caller != null ? caller.getName() : null);
		}
		kept.setConditionType(event.getConditionType().strip());
		kept.setAttributes(event.getAttributes() != null ? new LinkedHashMap<String, Object>(event.getAttributes())
				: new LinkedHashMap<String, Object>());
		kept.setText(event.getText());
		kept.setSourceSystem(event.getSourceSystem() != null ? event.getSourceSystem()
				: caller != null ? caller.getChannelSource() : null);
		kept.setSourceEventId(event.getSourceEventId());
		kept.setSourceTimestamp(event.getSourceTimestamp());
		kept.setActor(caller != null ? caller.getName() : null);
		kept.setChannel(caller != null ? caller.getChannel() : null);
		kept.setUserModified(caller != null ? caller.getName() : null);
		// a new value is matched afresh: accepted from now on, if it fits
		kept.setStatus(ConditionEventStatus.PENDING);
		kept.setAcceptedAt(null);
		kept.setReason(null);
		final GNetworkOfAgentsSuspension open = blocks.findOpenByConversationCode(id.sessionCode()).orElse(null);
		matcher.match(kept, open, now);
		store.saveEvent(kept);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Condition event:" + id + " from " + caller + " " + kept.getStatus()
					+ (kept.getReason() != null ? ": " + kept.getReason() : ""));
		}
		// what the delivery did, before the evaluation, apart, takes the event further
		final ConditionEventAck delivered = ack(outcomeOf(kept.getStatus()), kept, kept.getReason());
		if (evaluate && kept.getStatus() == ConditionEventStatus.ACCEPTED) {
			evaluateApart(id.sessionCode());
		}
		return delivered;
	}

	@Override
	public Optional<ConditionEventView> status(ConditionCorrelationId correlation, ConditionEventCaller caller) {
		if (correlation == null) {
			return Optional.empty();
		}
		final GUserChatSession session = sessions.findById(correlation.sessionCode()).orElse(null);
		if (session == null || authorization.refusal(caller, session) != null) {
			return Optional.empty();
		}
		return store.findEvent(correlation.composed()).map(ConditionEventView::of);
	}

	@Override
	public void sweep() {
		final Date now = new Date(clock.millis());
		for (GConditionEvent pending : store
				.pendingLastReceivedBefore(new Date(now.getTime() - config.getPendingTimeToLiveMillis()))) {
			pending.setStatus(ConditionEventStatus.EXPIRED);
			pending.setReason(ConditionEventMatcher.EXPIRED);
			pending.setDateModified(now);
			store.saveEvent(pending);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Condition event:" + pending.getCode() + " expired: no block waited for it");
			}
		}
		final Set<String> toEvaluate = new LinkedHashSet<String>(store.sessionsWithLiveEvents());
		for (GConditionDispatch due : store.dispatchesDue(now)) {
			if (due.getState() == GConditionDispatch.State.RUNNING) {
				// the instance that claimed it went down: its events are accepted again,
				// and the block, still open, is claimed again
				for (GConditionEvent event : store.eventsOfDispatch(due.getCode())) {
					if (event.getStatus() == ConditionEventStatus.DISPATCHED) {
						event.setStatus(ConditionEventStatus.ACCEPTED);
						event.setReason("The respawn that used it did not end within its lease: tried again");
						event.setDateModified(now);
						store.saveEvent(event);
					}
				}
			}
			if (due.getSessionCode() != null) {
				toEvaluate.add(due.getSessionCode());
			}
		}
		for (String session : toEvaluate) {
			try {
				dispatcher.evaluate(session);
			} catch (RuntimeException e) {
				LOGGER.warn("Cannot evaluate the condition events of session:" + session + ": " + e.getMessage(), e);
			}
		}
	}

	/**
	 * A respawn that gave up is claimed afresh once one of its events is delivered
	 * again: its other events are accepted again with it.
	 */
	private void revive(String blockCode, Date now) {
		if (blockCode == null) {
			return;
		}
		final Optional<GConditionDispatch> dispatch = store.findDispatch(blockCode);
		if (dispatch.isPresent() && dispatch.get().getState() == GConditionDispatch.State.GAVE_UP) {
			store.removeDispatch(blockCode);
		}
		for (GConditionEvent event : store.eventsOfDispatch(blockCode)) {
			if (event.getStatus() == ConditionEventStatus.FAILED) {
				event.setStatus(ConditionEventStatus.ACCEPTED);
				event.setReason(null);
				event.setDateModified(now);
				store.saveEvent(event);
			}
		}
	}

	private void evaluateApart(String sessionCode) {
		try {
			evaluations.execute(() -> {
				try {
					dispatcher.evaluate(sessionCode);
				} catch (RuntimeException e) {
					LOGGER.warn("Cannot evaluate the condition events of session:" + sessionCode
							+ " (the sweeper tries again): " + e.getMessage(), e);
				}
			});
		} catch (RuntimeException notTaken) {
			LOGGER.warn("Cannot evaluate the condition events of session:" + sessionCode
					+ " now (the sweeper tries again): " + notTaken.getMessage());
		}
	}

	private static boolean isOlder(ConditionEvent event, GConditionEvent kept) {
		return event.getSourceTimestamp() != null && kept.getSourceTimestamp() != null
				&& event.getSourceTimestamp().before(kept.getSourceTimestamp());
	}

	private static Outcome outcomeOf(ConditionEventStatus status) {
		return switch (status) {
		case ACCEPTED -> Outcome.ACCEPTED;
		case PENDING, EXPIRED -> Outcome.PENDING;
		case STALE, APPLIED, DISPATCHED -> Outcome.STALE;
		default -> Outcome.REFUSED;
		};
	}

	private static ConditionEventAck ack(Outcome outcome, GConditionEvent kept, String reason) {
		return ConditionEventAck.of(outcome, kept.getCode(), kept.getSessionCode(), kept.getConditionId(),
				kept.getStatus(), reason);
	}

	private static ConditionEventAck refused(ConditionCorrelationId id, String reason) {
		return ConditionEventAck.of(Outcome.REFUSED, id.composed(), id.sessionCode(), id.conditionId(), null, reason);
	}
}
