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

import ai.gebo.architecture.agents.model.ConditionSource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ClarificationReactionAttributes;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DefaultReactionAttributes;
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
	/**
	 * What solves it, as the condition's translator reads it ({@code value},
	 * {@code chosenOption}, {@code declined}...). May be left out when the answer is
	 * given typed; custom types answer here only.
	 */
	private LinkedHashMap<String, Object> attributes = null;
	/**
	 * The answer typed, to a condition of the {@code clarification} type: it stands
	 * for the attributes it covers (see {@link ClarificationReactionAttributes#COVERED}).
	 */
	private ClarificationReactionAttributes clarificationAttributes = null;
	/**
	 * The answer typed, to a condition of the {@code default} type: it stands for the
	 * attributes it covers (see {@link DefaultReactionAttributes#COVERED}).
	 */
	private DefaultReactionAttributes defaultAttributes = null;
	/**
	 * What the source says of it, for the conversation's history; when no event of
	 * a respawn carries one the text is made from the solved conditions.
	 */
	private String text = null;
	/** The system the event comes from, as it names itself. */
	private String sourceSystem = null;
	/**
	 * Who or what solved the condition: a person, an agent or a system, with the
	 * details and the session of a person or an agent. Kept as told, and verified
	 * only by who delivers it (see {@link ConditionSources}); with none, the event
	 * is told by its deliverer.
	 */
	private ConditionSource source = null;
	/** The source's own id of the event: the same id delivered again is a duplicate. */
	private String sourceEventId = null;
	/**
	 * When the source says the condition changed: of two values of the same
	 * condition the later one is kept. Null orders them by arrival.
	 */
	private Date sourceTimestamp = null;

	/**
	 * A copy of the attributes, with the answer given typed in place of what they
	 * said of the attributes it covers.
	 */
	public LinkedHashMap<String, Object> mergedAttributes() {
		LinkedHashMap<String, Object> merged = attributes != null ? new LinkedHashMap<String, Object>(attributes)
				: new LinkedHashMap<String, Object>();
		if (clarificationAttributes != null) {
			merged = clarificationAttributes.mergeInto(merged);
		}
		if (defaultAttributes != null) {
			merged = defaultAttributes.mergeInto(merged);
		}
		return merged;
	}
}
