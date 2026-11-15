/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import ai.gebo.architecture.agents.conditionevents.model.ConditionEventStatus;
import ai.gebo.architecture.agents.conditionevents.model.GConditionEvent;
import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;

/** The events kept for the conditions of the sessions, by correlation id. */
public interface GConditionEventRepository extends IGBaseMongoDBRepository<GConditionEvent> {
	@Override
	default Class<GConditionEvent> getManagedType() {
		return GConditionEvent.class;
	}

	List<GConditionEvent> findBySessionCodeAndStatusIn(String sessionCode, Collection<ConditionEventStatus> statuses);

	List<GConditionEvent> findByStatusIn(Collection<ConditionEventStatus> statuses);

	List<GConditionEvent> findByStatusAndLastReceivedAtBefore(ConditionEventStatus status, Date before);

	List<GConditionEvent> findByDispatchCode(String dispatchCode);
}
