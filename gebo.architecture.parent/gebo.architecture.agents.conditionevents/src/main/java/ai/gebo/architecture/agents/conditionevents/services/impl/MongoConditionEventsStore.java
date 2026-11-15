/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.conditionevents.model.ConditionEventStatus;
import ai.gebo.architecture.agents.conditionevents.model.GConditionDispatch;
import ai.gebo.architecture.agents.conditionevents.model.GConditionEvent;
import ai.gebo.architecture.agents.conditionevents.repository.GConditionDispatchRepository;
import ai.gebo.architecture.agents.conditionevents.repository.GConditionEventRepository;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventsStore;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;

/**
 * The events layer's store on Mongo: its two collections through their
 * repositories, the claim of a respawn as an insert or a conditional update, and
 * a read of the blocks that projects only what tells whether they are closed.
 */
@Service
public class MongoConditionEventsStore implements IConditionEventsStore {
	private static final Logger LOGGER = LoggerFactory.getLogger(MongoConditionEventsStore.class);
	static final List<ConditionEventStatus> LIVE = List.of(ConditionEventStatus.PENDING, ConditionEventStatus.ACCEPTED);
	private final GConditionEventRepository events;
	private final GConditionDispatchRepository dispatches;
	private final ObjectProvider<MongoOperations> mongo;

	public MongoConditionEventsStore(GConditionEventRepository events, GConditionDispatchRepository dispatches,
			ObjectProvider<MongoOperations> mongo) {
		this.events = events;
		this.dispatches = dispatches;
		this.mongo = mongo;
	}

	@Override
	public Optional<GConditionEvent> findEvent(String correlationId) {
		return correlationId != null ? events.findById(correlationId) : Optional.empty();
	}

	@Override
	public GConditionEvent saveEvent(GConditionEvent event) {
		return events.save(event);
	}

	@Override
	public List<GConditionEvent> liveEventsOf(String sessionCode) {
		return events.findBySessionCodeAndStatusIn(sessionCode, LIVE);
	}

	@Override
	public List<GConditionEvent> eventsOfDispatch(String blockCode) {
		return events.findByDispatchCode(blockCode);
	}

	@Override
	public List<GConditionEvent> pendingLastReceivedBefore(Date before) {
		return events.findByStatusAndLastReceivedAtBefore(ConditionEventStatus.PENDING, before);
	}

	@Override
	public Set<String> sessionsWithLiveEvents() {
		final Set<String> sessions = new LinkedHashSet<String>();
		for (GConditionEvent event : events.findByStatusIn(LIVE)) {
			if (event.getSessionCode() != null) {
				sessions.add(event.getSessionCode());
			}
		}
		return sessions;
	}

	@Override
	public Optional<GConditionDispatch> claim(String blockCode, String sessionCode, Date now, long leaseMillis) {
		final MongoOperations operations = requireMongo();
		final Date leaseUntil = new Date(now.getTime() + leaseMillis);
		final GConditionDispatch first = new GConditionDispatch();
		first.setCode(blockCode);
		first.setSessionCode(sessionCode);
		first.setState(GConditionDispatch.State.RUNNING);
		first.setAttempts(1);
		first.setClaimedAt(now);
		first.setLeaseUntil(leaseUntil);
		first.setDateCreated(now);
		first.setDateModified(now);
		try {
			return Optional.of(operations.insert(first));
		} catch (DuplicateKeyException claimedBefore) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The respawn of block:" + blockCode + " was claimed before: claimed again if it is due");
			}
		}
		final Query due = new Query(Criteria.where("_id").is(blockCode).orOperator(
				Criteria.where("state").is(GConditionDispatch.State.FAILED.name()).and("nextAttemptAt").lte(now),
				Criteria.where("state").is(GConditionDispatch.State.RUNNING.name()).and("leaseUntil").lte(now)));
		final Update claim = new Update().set("state", GConditionDispatch.State.RUNNING.name()).inc("attempts", 1)
				.set("claimedAt", now).set("leaseUntil", leaseUntil).set("dateModified", now);
		return Optional.ofNullable(operations.findAndModify(due, claim, FindAndModifyOptions.options().returnNew(true),
				GConditionDispatch.class));
	}

	@Override
	public GConditionDispatch saveDispatch(GConditionDispatch dispatch) {
		return dispatches.save(dispatch);
	}

	@Override
	public Optional<GConditionDispatch> findDispatch(String blockCode) {
		return blockCode != null ? dispatches.findById(blockCode) : Optional.empty();
	}

	@Override
	public void removeDispatch(String blockCode) {
		if (blockCode != null) {
			dispatches.deleteById(blockCode);
		}
	}

	@Override
	public List<GConditionDispatch> dispatchesDue(Date now) {
		final List<GConditionDispatch> due = new ArrayList<GConditionDispatch>(
				dispatches.findByStateAndNextAttemptAtBefore(GConditionDispatch.State.FAILED, now));
		due.addAll(dispatches.findByStateAndLeaseUntilBefore(GConditionDispatch.State.RUNNING, now));
		return due;
	}

	@Override
	public Optional<GNetworkOfAgentsSuspension> closedBlockHolding(String sessionCode, String conditionId) {
		final Query holding = new Query(Criteria.where("conversationCode").is(sessionCode)
				.and("suspendedAgents.waitingConditions.suspendedConditionId").is(conditionId))
				.with(Sort.by(Sort.Direction.DESC, "suspendedAt")).limit(1);
		holding.fields().include("conversationCode").include("suspendedAt").include("respawnedAt")
				.include("respawnedToSuspensionCode").include("droppedAt");
		final List<GNetworkOfAgentsSuspension> found = requireMongo().find(holding, GNetworkOfAgentsSuspension.class);
		return found.stream().filter(block -> block != null && !block.isOpen()).findFirst();
	}

	private MongoOperations requireMongo() {
		final MongoOperations operations = mongo.getIfAvailable();
		if (operations == null) {
			throw new IllegalStateException("No MongoDB in this application: the condition events cannot be kept");
		}
		return operations;
	}
}
