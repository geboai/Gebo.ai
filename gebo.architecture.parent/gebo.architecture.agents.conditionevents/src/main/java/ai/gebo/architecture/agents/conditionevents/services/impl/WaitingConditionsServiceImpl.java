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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.conditionevents.ConditionCorrelationId;
import ai.gebo.architecture.agents.conditionevents.model.ConditionEventStatus;
import ai.gebo.architecture.agents.conditionevents.model.ConditionSources;
import ai.gebo.architecture.agents.conditionevents.model.GConditionDispatch;
import ai.gebo.architecture.agents.conditionevents.model.GConditionEvent;
import ai.gebo.architecture.agents.conditionevents.model.WaitingBlockView;
import ai.gebo.architecture.agents.conditionevents.model.WaitingConditionView;
import ai.gebo.architecture.agents.conditionevents.model.WaitingConditionsActionAck;
import ai.gebo.architecture.agents.conditionevents.model.WaitingConditionsActionAck.Outcome;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventsStore;
import ai.gebo.architecture.agents.conditionevents.services.IWaitingConditionsService;
import ai.gebo.architecture.agents.model.ConditionSource;
import ai.gebo.architecture.agents.model.ConditionSource.Kind;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.WaitingCondition;
import ai.gebo.architecture.agents.model.UnansweredBecause;
import ai.gebo.architecture.agents.model.WaitingConditionDropReason;
import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.chat.pipelines.service.impl.NetworkOfAgentsSuspensionTransport;

/**
 * The suspended runs waiting for a human in the loop or an external condition,
 * for the panel that lists them and for the sweep:
 * <ul>
 * <li>going on without a condition, and the expiry of one whose expiry lets the
 * run go on, are events of the platform's own, accepted for the block and marked
 * {@link UnansweredBecause}: the dispatcher respawns the block as it does for any
 * event, as the conversation's owner, and the agent is told why it has no value
 * (see {@code UnansweredConditionText});</li>
 * <li>abandoning drops the block where it stands, which must still be open and
 * not being respawned;</li>
 * <li>the owner is told, once, of a run that suspended unwatched and of a
 * condition that expired without letting the run go on, and the messages are
 * taken back once the block closes.</li>
 * </ul>
 * A block is written whole by the run that respawns it, so the marks this service
 * sets on an open block may be lost: everything here does nothing when done
 * again, and the marks only spare the repeat.
 */
@Service
public class WaitingConditionsServiceImpl implements IWaitingConditionsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(WaitingConditionsServiceImpl.class);
	/** The system the platform's own events say they come from. */
	public static final String PLATFORM = "GEBO";
	/** The channel of the actions taken from the waiting conditions panel. */
	public static final String PANEL_CHANNEL = "PANEL";
	static final String NOT_THE_USERS = "The conversation of the block is not the user's";
	static final String RESPAWNING = "The block is being respawned: it cannot be abandoned now";
	static final String NOTHING_WAITING = "No condition of the block still waits";
	static final String NOT_WAITED = "The block does not wait for condition: ";
	static final String GONE_ON = "Gone on without %d condition(s): the run resumes";
	static final String ABANDONED = "The block is abandoned: the conversation is free";
	private static final Set<ConditionEventStatus> SOLVING = EnumSet.of(ConditionEventStatus.ACCEPTED,
			ConditionEventStatus.DISPATCHED, ConditionEventStatus.APPLIED);
	private final GNetworkOfAgentsSuspensionRepository blocks;
	private final GUserChatSessionRepository sessions;
	private final IConditionEventsStore store;
	private final ConditionEventMatcher matcher;
	private final ConditionEventDispatcher dispatcher;
	private final NetworkOfAgentsSuspensionTransport transport;
	private final WaitingConditionsNotifier notifier;
	private final ObjectProvider<MongoOperations> mongo;
	private final Executor evaluations;
	private final Clock clock;

	@Autowired
	public WaitingConditionsServiceImpl(GNetworkOfAgentsSuspensionRepository blocks, GUserChatSessionRepository sessions,
			IConditionEventsStore store, ConditionEventMatcher matcher, ConditionEventDispatcher dispatcher,
			NetworkOfAgentsSuspensionTransport transport, WaitingConditionsNotifier notifier,
			ObjectProvider<MongoOperations> mongo, ObjectProvider<IGeboThreadManager> threads) {
		this(blocks, sessions, store, matcher, dispatcher, transport, notifier, mongo,
				ConditionEventsServiceImpl.lazyExecutorOf(threads), Clock.systemUTC());
	}

	WaitingConditionsServiceImpl(GNetworkOfAgentsSuspensionRepository blocks, GUserChatSessionRepository sessions,
			IConditionEventsStore store, ConditionEventMatcher matcher, ConditionEventDispatcher dispatcher,
			NetworkOfAgentsSuspensionTransport transport, WaitingConditionsNotifier notifier,
			ObjectProvider<MongoOperations> mongo, Executor evaluations, Clock clock) {
		this.blocks = blocks;
		this.sessions = sessions;
		this.store = store;
		this.matcher = matcher;
		this.dispatcher = dispatcher;
		this.transport = transport;
		this.notifier = notifier;
		this.mongo = mongo;
		this.evaluations = evaluations;
		this.clock = clock;
	}

	@Override
	public List<WaitingBlockView> waitingFor(String username) {
		final List<WaitingBlockView> waiting = new ArrayList<WaitingBlockView>();
		if (username == null || username.isBlank()) {
			return waiting;
		}
		// the owner's own runs, and only the ones waiting for a human in the loop
		// request handled as a process: a clarification is the conversation's alone
		for (WaitingBlockView view : allWaiting()) {
			if (username.equals(view.getOwner()) && view.isHumanInTheLoopProcessTreatment()) {
				waiting.add(view);
			}
		}
		return waiting;
	}

	@Override
	public List<WaitingBlockView> allWaiting() {
		final Date now = new Date(clock.millis());
		final List<WaitingBlockView> waiting = new ArrayList<WaitingBlockView>();
		for (GNetworkOfAgentsSuspension block : blocks.findByRespawnedAtIsNullAndDroppedAtIsNull()) {
			if (block != null && block.isOpen()) {
				waiting.add(viewOf(block, ownerOf(block), now));
			}
		}
		waiting.sort(Comparator.comparing(WaitingBlockView::getSuspendedAt,
				Comparator.nullsLast(Comparator.<Date>naturalOrder())).reversed());
		return waiting;
	}

	/** The block as the panel shows it. */
	WaitingBlockView viewOf(GNetworkOfAgentsSuspension block, String owner, Date now) {
		final WaitingBlockView view = new WaitingBlockView();
		view.setBlockCode(block.getCode());
		view.setConversationCode(block.getConversationCode());
		view.setOwner(owner);
		view.setNetworkCode(block.getNetworkCode());
		view.setSuspendedAt(block.getSuspendedAt());
		view.setMandatory(block.isMandatory());
		view.setUnwatched(block.isUnwatched());
		view.setEarliestExpiryAt(
				block.getEarliestExpiryAt() != null ? block.getEarliestExpiryAt() : block.earliestExpiryOfConditions());
		final Map<String, GConditionEvent> solving = solvingEventsOf(block);
		for (WaitingCondition condition : block.conditions()) {
			final WaitingConditionView shown = new WaitingConditionView();
			shown.setConditionId(condition.getSuspendedConditionId());
			shown.setConditionType(condition.getConditionType());
			shown.setStatement(condition.getWaitingConditionStatement());
			shown.setMandatory(condition.isMandatory());
			shown.setExpiresAt(condition.getExpiresAt());
			shown.setExpiryUnblocks(condition.isExpiryUnblocks());
			final GConditionEvent solved = solving.get(condition.getSuspendedConditionId());
			shown.setSolved(solved != null);
			shown.setSolvedBy(solved != null ? solved.sourceOrKept().label() : null);
			shown.setExpired(solved == null && condition.isExpiredAt(now));
			shown.setHumanInTheLoopProcessTreatment(condition.isHumanInTheLoopProcessTreatment());
			view.setHumanInTheLoopProcessTreatment(
					view.isHumanInTheLoopProcessTreatment() || shown.isHumanInTheLoopProcessTreatment());
			view.setExpired(view.isExpired() || shown.isExpired());
			view.getConditions().add(shown);
		}
		return view;
	}

	@Override
	public WaitingConditionsActionAck goOnWithout(String blockCode, List<String> conditionIds, String username,
			boolean administrator) {
		final GNetworkOfAgentsSuspension block = openBlock(blockCode);
		if (block == null) {
			return WaitingConditionsActionAck.of(blockCode, Outcome.NOT_FOUND, null);
		}
		final String owner = ownerOf(block);
		if (!administrator && (owner == null || !owner.equals(username))) {
			return WaitingConditionsActionAck.of(blockCode, Outcome.REFUSED, NOT_THE_USERS);
		}
		final Map<String, GConditionEvent> solving = solvingEventsOf(block);
		final List<WaitingCondition> toGoOnWithout = new ArrayList<WaitingCondition>();
		if (conditionIds == null || conditionIds.isEmpty()) {
			for (WaitingCondition condition : block.conditions()) {
				if (!solving.containsKey(condition.getSuspendedConditionId())) {
					toGoOnWithout.add(condition);
				}
			}
		} else {
			for (String conditionId : new LinkedHashSet<String>(conditionIds)) {
				final WaitingCondition condition = ConditionEventMatcher.waitingFor(block, conditionId);
				if (condition == null) {
					return WaitingConditionsActionAck.of(blockCode, Outcome.REFUSED, NOT_WAITED + conditionId);
				}
				if (!solving.containsKey(conditionId)) {
					toGoOnWithout.add(condition);
				}
			}
		}
		if (toGoOnWithout.isEmpty()) {
			return WaitingConditionsActionAck.of(blockCode, Outcome.REFUSED, NOTHING_WAITING);
		}
		final ConditionSource chooser = username != null && username.equals(owner)
				? ConditionSources.forChatUser(username, null, block.getConversationCode())
				: administratorSource(username);
		final Date now = new Date(clock.millis());
		int done = 0;
		for (WaitingCondition condition : toGoOnWithout) {
			if (markUnanswered(block, condition, UnansweredBecause.GONE_ON_WITHOUT, chooser, username, now)) {
				done++;
			}
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Block:" + blockCode + " of conversation:" + block.getConversationCode() + ": " + username
					+ (administrator ? " (administrator)" : "") + " chose to go on without " + done + " condition(s)");
		}
		evaluateApart(block.getConversationCode());
		return WaitingConditionsActionAck.of(blockCode, Outcome.DONE, String.format(GONE_ON, done));
	}

	@Override
	public WaitingConditionsActionAck abandon(String blockCode, String username, boolean administrator) {
		final GNetworkOfAgentsSuspension block = openBlock(blockCode);
		if (block == null) {
			return WaitingConditionsActionAck.of(blockCode, Outcome.NOT_FOUND, null);
		}
		final String owner = ownerOf(block);
		if (!administrator && (owner == null || !owner.equals(username))) {
			return WaitingConditionsActionAck.of(blockCode, Outcome.REFUSED, NOT_THE_USERS);
		}
		final GConditionDispatch dispatch = store.findDispatch(blockCode).orElse(null);
		if (dispatch != null && dispatch.getState() == GConditionDispatch.State.RUNNING) {
			return WaitingConditionsActionAck.of(blockCode, Outcome.REFUSED, RESPAWNING);
		}
		final Date now = new Date(clock.millis());
		final boolean dropped = writeOpen(block, new Update().set("droppedAt", now)
				.set("droppedReason", WaitingConditionDropReason.ABANDONED).set("droppedBy", username)
				.set("dateModified", now), open -> {
					open.setDroppedAt(now);
					open.setDroppedReason(WaitingConditionDropReason.ABANDONED);
					open.setDroppedBy(username);
					open.setDateModified(now);
				});
		if (!dropped) {
			return WaitingConditionsActionAck.of(blockCode, Outcome.NOT_FOUND, null);
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Block:" + blockCode + " of conversation:" + block.getConversationCode() + " abandoned by "
					+ username + (administrator ? " (administrator)" : ""));
		}
		retractMessagesOf(block, now);
		return WaitingConditionsActionAck.of(blockCode, Outcome.DONE, ABANDONED);
	}

	@Override
	public void applyExpiries() {
		final Date now = new Date(clock.millis());
		final ConditionSource expiry = ConditionSource.of(Kind.SYSTEM, PLATFORM, "expiry", "the expiry of the condition");
		expiry.setVerified(true);
		for (GNetworkOfAgentsSuspension block : blocks
				.findByEarliestExpiryAtLessThanEqualAndRespawnedAtIsNullAndDroppedAtIsNull(now)) {
			if (block == null || !block.isOpen()) {
				continue;
			}
			final Map<String, GConditionEvent> solving = solvingEventsOf(block);
			final List<String> stuck = new ArrayList<String>();
			boolean unblocked = false;
			for (WaitingCondition condition : block.conditions()) {
				if (!condition.isExpiredAt(now) || solving.containsKey(condition.getSuspendedConditionId())) {
					continue;
				}
				if (condition.isExpiryUnblocks()) {
					unblocked |= markUnanswered(block, condition, UnansweredBecause.EXPIRED, expiry, null, now);
				} else {
					stuck.add(condition.getWaitingConditionStatement());
				}
			}
			if (unblocked) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Block:" + block.getCode() + " of conversation:" + block.getConversationCode()
							+ ": condition(s) expired, the run goes on without them");
				}
				evaluateApart(block.getConversationCode());
			}
			if (!stuck.isEmpty() && block.getExpiryNotifiedAt() == null) {
				notifier.expired(block.getCode(), ownerOf(block), block.getConversationCode(), stuck);
				mark(block, "expiryNotifiedAt", now, open -> open.setExpiryNotifiedAt(now));
			}
		}
	}

	@Override
	public void tellOwners() {
		final Date now = new Date(clock.millis());
		final Set<String> seen = new LinkedHashSet<String>();
		final List<GNetworkOfAgentsSuspension> told = new ArrayList<GNetworkOfAgentsSuspension>();
		told.addAll(blocks.findByUnwatchedTrueAndMessagesRetractedAtIsNull());
		told.addAll(blocks.findByEarliestExpiryAtIsNotNullAndMessagesRetractedAtIsNull());
		for (GNetworkOfAgentsSuspension block : told) {
			if (block == null || block.getCode() == null || !seen.add(block.getCode())) {
				continue;
			}
			if (!block.isOpen()) {
				retractMessagesOf(block, now);
			} else if (block.isUnwatched() && block.getUnwatchedNotifiedAt() == null) {
				final List<String> waitingFor = new ArrayList<String>();
				for (WaitingCondition condition : block.conditions()) {
					waitingFor.add(condition.getWaitingConditionStatement());
				}
				notifier.unwatched(block.getCode(), ownerOf(block), block.getConversationCode(), waitingFor);
				mark(block, "unwatchedNotifiedAt", now, open -> open.setUnwatchedNotifiedAt(now));
			}
		}
	}

	/**
	 * An event of the platform's own, accepted for the block, resolving the
	 * condition with no value; nothing when an event already solves it.
	 *
	 * @return whether the event was written
	 */
	private boolean markUnanswered(GNetworkOfAgentsSuspension block, WaitingCondition condition,
			UnansweredBecause because, ConditionSource source, String actor, Date now) {
		final ConditionCorrelationId id = new ConditionCorrelationId(block.getConversationCode(),
				condition.getSuspendedConditionId());
		GConditionEvent event = store.findEvent(id.composed()).orElse(null);
		if (event != null && !event.getStatus().isReplaceable()) {
			return false;
		}
		if (event == null) {
			event = new GConditionEvent();
			event.setCode(id.composed());
			event.setSessionCode(id.sessionCode());
			event.setConditionId(id.conditionId());
			event.setReceivedAt(now);
			event.setDateCreated(now);
			event.setUserCreated(actor);
		}
		event.setDeliveries(event.getDeliveries() + 1);
		event.setLastReceivedAt(now);
		event.setDateModified(now);
		event.setUserModified(actor);
		event.setConditionType(transport.translatorOf(condition.toCondition()).getId());
		event.setAttributes(new LinkedHashMap<String, Object>());
		event.setText(null);
		event.setSource(source);
		event.setSourceSystem(source != null ? source.getSystem() : PLATFORM);
		event.setSourceEventId(because.name().toLowerCase() + ":" + now.getTime());
		event.setSourceTimestamp(now);
		event.setActor(actor);
		event.setChannel(actor != null ? PANEL_CHANNEL : PLATFORM);
		event.setUnanswered(because);
		event.setStatus(ConditionEventStatus.PENDING);
		event.setAcceptedAt(null);
		event.setReason(null);
		matcher.match(event, block, now);
		store.saveEvent(event);
		if (event.getStatus() != ConditionEventStatus.ACCEPTED) {
			LOGGER.warn("Condition:" + id + " could not be resolved as " + because + ": " + event.getReason());
			return false;
		}
		return true;
	}

	private static ConditionSource administratorSource(String username) {
		final ConditionSource source = ConditionSource.of(Kind.PERSON, PLATFORM, username,
				"The administrator" + (username != null ? " " + username : ""));
		source.setChannel(PANEL_CHANNEL);
		source.setVerified(username != null && !username.isBlank());
		return source;
	}

	/** The events solving the block's conditions, by condition: accepted, dispatched or applied for it. */
	private Map<String, GConditionEvent> solvingEventsOf(GNetworkOfAgentsSuspension block) {
		final Map<String, GConditionEvent> solving = new LinkedHashMap<String, GConditionEvent>();
		if (block.getConversationCode() == null) {
			return solving;
		}
		for (GConditionEvent event : store.liveEventsOf(block.getConversationCode())) {
			if (SOLVING.contains(event.getStatus()) && Objects.equals(block.getCode(), event.getBlockCode())) {
				solving.put(event.getConditionId(), event);
			}
		}
		return solving;
	}

	private GNetworkOfAgentsSuspension openBlock(String blockCode) {
		if (blockCode == null || blockCode.isBlank()) {
			return null;
		}
		final GNetworkOfAgentsSuspension block = blocks.findById(blockCode).orElse(null);
		return block != null && block.isOpen() ? block : null;
	}

	/** The owner of the block's conversation: the account its respawn runs as. */
	private String ownerOf(GNetworkOfAgentsSuspension block) {
		if (block.getConversationCode() == null) {
			return null;
		}
		final GUserChatSession session = sessions.findById(block.getConversationCode()).orElse(null);
		return session != null ? session.getUsername() : null;
	}

	private void retractMessagesOf(GNetworkOfAgentsSuspension block, Date now) {
		notifier.settled(block.getCode());
		mark(block, "messagesRetractedAt", now, closed -> closed.setMessagesRetractedAt(now));
	}

	/**
	 * Sets one mark on the block, apart from the block's own writes when the
	 * database allows it; the mark spares a repeat and nothing depends on it.
	 */
	private void mark(GNetworkOfAgentsSuspension block, String field, Date value, Consumer<GNetworkOfAgentsSuspension> inMemory) {
		inMemory.accept(block);
		try {
			final MongoOperations operations = mongo != null ? mongo.getIfAvailable() : null;
			if (operations != null) {
				operations.updateFirst(new Query(Criteria.where("_id").is(block.getCode())), new Update().set(field, value),
						GNetworkOfAgentsSuspension.class);
			} else {
				blocks.save(block);
			}
		} catch (RuntimeException notMarked) {
			LOGGER.warn("Block:" + block.getCode() + " could not be marked " + field + ": " + notMarked.getMessage());
		}
	}

	/**
	 * Writes the block while it is still open, and only then: one write, so that a
	 * respawn ending at the same moment is not undone. Without the database's own
	 * operations the block is written whole, after a last look.
	 *
	 * @return whether the block was written
	 */
	private boolean writeOpen(GNetworkOfAgentsSuspension block, Update update, Consumer<GNetworkOfAgentsSuspension> inMemory) {
		final MongoOperations operations = mongo != null ? mongo.getIfAvailable() : null;
		if (operations != null) {
			final Query open = new Query(Criteria.where("_id").is(block.getCode()).and("respawnedAt").is(null)
					.and("droppedAt").is(null));
			final boolean written = operations.updateFirst(open, update, GNetworkOfAgentsSuspension.class)
					.getModifiedCount() > 0;
			if (written) {
				inMemory.accept(block);
			}
			return written;
		}
		final GNetworkOfAgentsSuspension last = blocks.findById(block.getCode()).orElse(null);
		if (last == null || !last.isOpen()) {
			return false;
		}
		inMemory.accept(last);
		blocks.save(last);
		inMemory.accept(block);
		return true;
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
}
