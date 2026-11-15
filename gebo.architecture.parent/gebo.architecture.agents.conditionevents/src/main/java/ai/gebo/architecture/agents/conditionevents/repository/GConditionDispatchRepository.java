/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.repository;

import java.util.Date;
import java.util.List;

import ai.gebo.architecture.agents.conditionevents.model.GConditionDispatch;
import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;

/** The respawns of blocks by the events that solved them, by block code. */
public interface GConditionDispatchRepository extends IGBaseMongoDBRepository<GConditionDispatch> {
	@Override
	default Class<GConditionDispatch> getManagedType() {
		return GConditionDispatch.class;
	}

	List<GConditionDispatch> findByStateAndNextAttemptAtBefore(GConditionDispatch.State state, Date before);

	List<GConditionDispatch> findByStateAndLeaseUntilBefore(GConditionDispatch.State state, Date before);
}
