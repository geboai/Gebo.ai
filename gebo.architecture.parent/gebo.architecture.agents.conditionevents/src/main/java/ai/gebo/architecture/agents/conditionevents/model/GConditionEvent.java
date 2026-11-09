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
import java.util.LinkedHashMap;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.architecture.agents.conditionevents.ConditionCorrelationId;
import ai.gebo.architecture.agents.model.ConditionSource;
import ai.gebo.architecture.agents.model.UnansweredBecause;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;
import ai.gebo.model.base.GBaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The event kept for a condition of a session: one per session and condition,
 * tracked in both forms, its code being the merged correlation id
 * ({@code <sessionCode>:<conditionId>}) and the pair being unique too. A new
 * delivery of the same condition updates it: the later value replaces the one
 * kept while it was not used.
 */
@Document
@Data
@EqualsAndHashCode(callSuper = true)
public class GConditionEvent extends GBaseObject {
	private static final long serialVersionUID = 1L;
	private String sessionCode = null;
	private String conditionId = null;
	private String conditionType = null;
	private LinkedHashMap<String, Object> attributes = null;
	/** What the source said of it, for the history. */
	private String text = null;
	private String sourceSystem = null;
	/** Who or what solved the condition, as kept on delivery; null on events kept before sources. */
	private ConditionSource source = null;
	/**
	 * Why the condition is resolved with no value although it may be mandatory: the
	 * platform's own, on the events it makes when a condition expires, and on the
	 * chat's when the conversation's user, or an administrator from the panel,
	 * chooses to go on without it. Never from a delivered event.
	 */
	private UnansweredBecause unanswered = null;
	private String sourceEventId = null;
	private Date sourceTimestamp = null;
	/** Who delivered it and through which channel. */
	private String actor = null;
	private String channel = null;
	private ConditionEventStatus status = ConditionEventStatus.PENDING;
	/** Why it is refused, stale, expired or failed; or the last failure of the respawn it waits for. */
	private String reason = null;
	/** The block it was matched to. */
	private String blockCode = null;
	private Date receivedAt = null;
	private Date lastReceivedAt = null;
	private int deliveries = 0;
	/** When it was matched to the block it is accepted for. */
	private Date acceptedAt = null;
	/** The block of the respawn it was part of, and the request of that respawn. */
	private String dispatchCode = null;
	private String dispatchedRequestId = null;
	/** When the respawn it was part of ended, and the block that respawn suspended into, if it did. */
	private Date appliedAt = null;
	private String respawnedToBlockCode = null;

	public ConditionCorrelationId correlation() {
		return new ConditionCorrelationId(sessionCode, conditionId);
	}

	/** Its source, or for an event kept before sources the one told by what it kept. */
	public ConditionSource sourceOrKept() {
		return source != null ? source : ConditionSources.ofKept(this);
	}

	/** The reaction this event stands for, with its source. */
	public AgentSuspendedWaitingConditionReactionDto toReaction() {
		final AgentSuspendedWaitingConditionReactionDto reaction = new AgentSuspendedWaitingConditionReactionDto();
		reaction.setConditionType(conditionType);
		reaction.setConditionId(conditionId);
		reaction.setAttributes(attributes != null ? new LinkedHashMap<String, Object>(attributes)
				: new LinkedHashMap<String, Object>());
		reaction.setSource(sourceOrKept());
		reaction.setUnanswered(unanswered);
		return reaction;
	}
}
