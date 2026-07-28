/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.scheduling.services.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.environment.conditional.ConditionalOnMicroservices;
import ai.gebo.architecture.scheduling.model.GSchedulerLeaderLease;

/**
 * Elects a single leader among tyr's replicas to run the central scheduler's
 * tick. See {@link GSchedulerLeaderLease}.
 *
 * <p>
 * {@link #tryAcquireOrRenew()} is a two-step, upsert-free-on-the-contended-path
 * claim: first it unconditionally ensures the single lease document exists (a
 * plain {@code _id}-only upsert, which can never collide since {@code _id} is
 * always the same document), then attempts a plain (non-upsert)
 * {@code findAndModify} that only matches if this instance already holds the
 * lease or it has expired - avoiding the duplicate-key race an upsert
 * combined with an {@code $or} filter would otherwise risk when a different
 * replica currently holds a still-valid lease.
 * </p>
 */
@Component
@ConditionalOnMicroservices
public class SchedulerLeaderLeaseService {

	private static final String LEASE_ID = "central-scheduler";

	private final MongoTemplate mongoTemplate;
	private final long leaseDurationMs;
	private final String instanceId = UUID.randomUUID().toString();

	public SchedulerLeaderLeaseService(MongoTemplate mongoTemplate,
			@Value("${ai.gebo.scheduling.leader-lease-duration-ms:30000}") long leaseDurationMs) {
		this.mongoTemplate = mongoTemplate;
		this.leaseDurationMs = leaseDurationMs;
	}

	public boolean tryAcquireOrRenew() {
		Date now = new Date();
		Date newExpiry = new Date(now.getTime() + leaseDurationMs);

		Query ensureExists = Query.query(Criteria.where("_id").is(LEASE_ID));
		Update createIfAbsent = new Update().setOnInsert("holder", "").setOnInsert("expiresAt", new Date(0));
		mongoTemplate.upsert(ensureExists, createIfAbsent, GSchedulerLeaderLease.class);

		Query claim = Query.query(Criteria.where("_id").is(LEASE_ID)
				.orOperator(Criteria.where("holder").is(instanceId), Criteria.where("expiresAt").lt(now)));
		Update claimUpdate = new Update().set("holder", instanceId).set("expiresAt", newExpiry);
		GSchedulerLeaderLease updated = mongoTemplate.findAndModify(claim, claimUpdate,
				FindAndModifyOptions.options().returnNew(true), GSchedulerLeaderLease.class);
		return updated != null && instanceId.equals(updated.getHolder());
	}

}
