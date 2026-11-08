/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * An event solving a condition a suspended network of agents waits for, as any
 * source sends it. It names the condition by its correlation id
 * ({@code <sessionCode>:<conditionId>}), by the session code and the condition
 * id, or by both; and it solves it as a reaction does (see
 * {@code AgentSuspendedWaitingConditionReactionDto}): the type of the condition
 * and the attributes its translator reads.
 */
@Data
public class ConditionEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	/** The merged form: {@code <sessionCode>:<conditionId>}. */
	private String correlationId = null;
	/** The chat session the suspended network serves. */
	private String sessionCode = null;
	/** The id of the condition solved. */
	private String conditionId = null;
	/** The type of the condition, as the pending state names it ({@code clarification}, {@code default}...). */
	@NotNull
	private String conditionType = null;
	/** What solves it, as the condition's translator reads it ({@code value}, {@code chosenOption}, {@code declined}...). */
	@NotNull
	private LinkedHashMap<String, Object> attributes = null;
	/**
	 * What the source says of it, for the conversation's history; when no event of
	 * a respawn carries one the text is made from the solved conditions.
	 */
	private String text = null;
	/** The system the event comes from, as it names itself. */
	private String sourceSystem = null;
	/** The source's own id of the event: the same id delivered again is a duplicate. */
	private String sourceEventId = null;
	/**
	 * When the source says the condition changed: of two values of the same
	 * condition the later one is kept. Null orders them by arrival.
	 */
	private Date sourceTimestamp = null;
}
