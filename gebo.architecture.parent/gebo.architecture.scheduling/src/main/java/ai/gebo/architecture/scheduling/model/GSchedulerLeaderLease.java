/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.scheduling.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * A single-document Mongo lease used to elect exactly one leader among tyr's
 * replicas (under microservices) to run {@code AbstractCentralSchedulingService}'s
 * {@code @Scheduled} tick. tyr has no other clustering/leader-election
 * primitive today, and every replica already shares the same Mongo, so a
 * short-lived lease document there is the smallest correct fix rather than
 * introducing a new infrastructure dependency.
 */
@Data
@Document(collection = "scheduler_leader_lease")
public class GSchedulerLeaderLease {

	/** Fixed id: exactly one lease document ever exists. */
	@Id
	private String id = "central-scheduler";

	/** Opaque id of the JVM instance currently holding the lease. */
	private String holder = "";

	/** When the current hold expires and becomes stealable. */
	private Date expiresAt = new Date(0);

}
