/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.model;

import java.util.Date;

import ai.gebo.architecture.agents.model.ConditionSource;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Where the event kept for a condition is, as its source may ask. */
@Data
@NoArgsConstructor
public class ConditionEventView {
	private String correlationId = null;
	private String sessionCode = null;
	private String conditionId = null;
	private String conditionType = null;
	private ConditionEventStatus status = null;
	private String reason = null;
	private String blockCode = null;
	private String sourceSystem = null;
	/** Who or what solved the condition. */
	private ConditionSource source = null;
	private String sourceEventId = null;
	private Date sourceTimestamp = null;
	private Date receivedAt = null;
	private Date lastReceivedAt = null;
	private int deliveries = 0;
	private Date acceptedAt = null;
	private Date appliedAt = null;
	private String respawnedToBlockCode = null;

	public static ConditionEventView of(GConditionEvent event) {
		if (event == null) {
			return null;
		}
		final ConditionEventView view = new ConditionEventView();
		view.setCorrelationId(event.getCode());
		view.setSessionCode(event.getSessionCode());
		view.setConditionId(event.getConditionId());
		view.setConditionType(event.getConditionType());
		view.setStatus(event.getStatus());
		view.setReason(event.getReason());
		view.setBlockCode(event.getBlockCode());
		view.setSourceSystem(event.getSourceSystem());
		view.setSource(event.sourceOrKept());
		view.setSourceEventId(event.getSourceEventId());
		view.setSourceTimestamp(event.getSourceTimestamp());
		view.setReceivedAt(event.getReceivedAt());
		view.setLastReceivedAt(event.getLastReceivedAt());
		view.setDeliveries(event.getDeliveries());
		view.setAcceptedAt(event.getAcceptedAt());
		view.setAppliedAt(event.getAppliedAt());
		view.setRespawnedToBlockCode(event.getRespawnedToBlockCode());
		return view;
	}
}
