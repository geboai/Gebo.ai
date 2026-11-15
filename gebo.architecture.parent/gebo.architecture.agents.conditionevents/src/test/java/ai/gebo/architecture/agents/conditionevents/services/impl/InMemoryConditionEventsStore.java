package ai.gebo.architecture.agents.conditionevents.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import ai.gebo.architecture.agents.conditionevents.model.ConditionEventStatus;
import ai.gebo.architecture.agents.conditionevents.model.GConditionDispatch;
import ai.gebo.architecture.agents.conditionevents.model.GConditionEvent;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventsStore;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;

/**
 * The store as Mongo keeps it, in memory: the claim is written once, then taken
 * again only when its backoff or its lease is over, as the conditional update
 * does.
 */
class InMemoryConditionEventsStore implements IConditionEventsStore {
	final Map<String, GConditionEvent> events = new LinkedHashMap<String, GConditionEvent>();
	final Map<String, GConditionDispatch> dispatches = new LinkedHashMap<String, GConditionDispatch>();
	private final Supplier<Collection<GNetworkOfAgentsSuspension>> blocks;

	InMemoryConditionEventsStore(Supplier<Collection<GNetworkOfAgentsSuspension>> blocks) {
		this.blocks = blocks;
	}

	@Override
	public Optional<GConditionEvent> findEvent(String correlationId) {
		return Optional.ofNullable(events.get(correlationId));
	}

	@Override
	public GConditionEvent saveEvent(GConditionEvent event) {
		events.put(event.getCode(), event);
		return event;
	}

	@Override
	public List<GConditionEvent> liveEventsOf(String sessionCode) {
		return events.values().stream().filter(e -> sessionCode.equals(e.getSessionCode()))
				.filter(e -> MongoConditionEventsStore.LIVE.contains(e.getStatus())).toList();
	}

	@Override
	public List<GConditionEvent> eventsOfDispatch(String blockCode) {
		return events.values().stream().filter(e -> blockCode.equals(e.getDispatchCode())).toList();
	}

	@Override
	public List<GConditionEvent> pendingLastReceivedBefore(Date before) {
		return events.values().stream().filter(e -> e.getStatus() == ConditionEventStatus.PENDING)
				.filter(e -> e.getLastReceivedAt().before(before)).toList();
	}

	@Override
	public Set<String> sessionsWithLiveEvents() {
		final Set<String> sessions = new LinkedHashSet<String>();
		events.values().stream().filter(e -> MongoConditionEventsStore.LIVE.contains(e.getStatus()))
				.forEach(e -> sessions.add(e.getSessionCode()));
		return sessions;
	}

	@Override
	public Optional<GConditionDispatch> claim(String blockCode, String sessionCode, Date now, long leaseMillis) {
		final Date leaseUntil = new Date(now.getTime() + leaseMillis);
		final GConditionDispatch kept = dispatches.get(blockCode);
		if (kept == null) {
			final GConditionDispatch first = new GConditionDispatch();
			first.setCode(blockCode);
			first.setSessionCode(sessionCode);
			first.setState(GConditionDispatch.State.RUNNING);
			first.setAttempts(1);
			first.setClaimedAt(now);
			first.setLeaseUntil(leaseUntil);
			dispatches.put(blockCode, first);
			return Optional.of(first);
		}
		final boolean due = (kept.getState() == GConditionDispatch.State.FAILED && !kept.getNextAttemptAt().after(now))
				|| (kept.getState() == GConditionDispatch.State.RUNNING && !kept.getLeaseUntil().after(now));
		if (!due) {
			return Optional.empty();
		}
		kept.setState(GConditionDispatch.State.RUNNING);
		kept.setAttempts(kept.getAttempts() + 1);
		kept.setClaimedAt(now);
		kept.setLeaseUntil(leaseUntil);
		return Optional.of(kept);
	}

	@Override
	public GConditionDispatch saveDispatch(GConditionDispatch dispatch) {
		dispatches.put(dispatch.getCode(), dispatch);
		return dispatch;
	}

	@Override
	public Optional<GConditionDispatch> findDispatch(String blockCode) {
		return Optional.ofNullable(dispatches.get(blockCode));
	}

	@Override
	public void removeDispatch(String blockCode) {
		dispatches.remove(blockCode);
	}

	@Override
	public List<GConditionDispatch> dispatchesDue(Date now) {
		final List<GConditionDispatch> due = new ArrayList<GConditionDispatch>();
		for (GConditionDispatch dispatch : dispatches.values()) {
			if ((dispatch.getState() == GConditionDispatch.State.FAILED && dispatch.getNextAttemptAt().before(now))
					|| (dispatch.getState() == GConditionDispatch.State.RUNNING && dispatch.getLeaseUntil().before(now))) {
				due.add(dispatch);
			}
		}
		return due;
	}

	@Override
	public Optional<GNetworkOfAgentsSuspension> closedBlockHolding(String sessionCode, String conditionId) {
		return blocks.get().stream().filter(b -> sessionCode.equals(b.getConversationCode()))
				.filter(b -> ConditionEventMatcher.waitingFor(b, conditionId) != null)
				.max(Comparator.comparing(GNetworkOfAgentsSuspension::getSuspendedAt)).filter(b -> !b.isOpen());
	}
}
