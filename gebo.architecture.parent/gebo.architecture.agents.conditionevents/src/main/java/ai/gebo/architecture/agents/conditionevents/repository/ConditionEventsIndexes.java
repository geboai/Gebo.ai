/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.agents.conditionevents.model.GConditionDispatch;
import ai.gebo.architecture.agents.conditionevents.model.GConditionEvent;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import jakarta.annotation.PostConstruct;

/**
 * The indexes of the events layer: the second form of an event's identity, the
 * pair session code and condition id, unique like the merged correlation id that
 * is the event's code; and what the sweeper and the dispatcher look events and
 * respawns up by.
 */
@Component
public class ConditionEventsIndexes {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConditionEventsIndexes.class);
	static final String SESSION_CONDITION_UNIQUE = "sessionCode_conditionId_unique";
	private final ObjectProvider<MongoOperations> mongo;

	public ConditionEventsIndexes(ObjectProvider<MongoOperations> mongo) {
		this.mongo = mongo;
	}

	@PostConstruct
	public void createIndexes() {
		final MongoOperations operations = mongo.getIfAvailable();
		if (operations == null) {
			LOGGER.warn("No MongoDB operations: the condition events indexes are not created");
			return;
		}
		try {
			operations.indexOps(GConditionEvent.class).createIndex(new Index().on("sessionCode", Direction.ASC)
					.on("conditionId", Direction.ASC).unique().named(SESSION_CONDITION_UNIQUE));
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot make the condition events unique by session and condition: " + e.getMessage());
		}
		try {
			operations.indexOps(GConditionEvent.class)
					.createIndex(new Index().on("status", Direction.ASC).on("lastReceivedAt", Direction.ASC));
			operations.indexOps(GConditionEvent.class).createIndex(new Index().on("dispatchCode", Direction.ASC));
			operations.indexOps(GConditionDispatch.class)
					.createIndex(new Index().on("state", Direction.ASC).on("nextAttemptAt", Direction.ASC));
			// the blocks due by the earliest expiry of their conditions, for the sweep
			operations.indexOps(GNetworkOfAgentsSuspension.class)
					.createIndex(new Index().on("earliestExpiryAt", Direction.ASC).sparse());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Condition events indexed by status, by dispatch, respawns by state, blocks by expiry");
			}
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot index the condition events: " + e.getMessage());
		}
	}
}
