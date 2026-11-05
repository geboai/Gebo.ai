/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.service.impl;

import java.util.LinkedHashMap;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;
import ai.gebo.llms.chat.pipelines.service.IAgentSuspendedWaitingConditionTransportTranslator;

/**
 * What every translator of a waiting condition shares: the attributes every DTO
 * carries, the statement, the explanation and whether the condition is
 * mandatory, and the way a reaction declines a condition the user may leave
 * unanswered ({@value #DECLINED}: the condition is resolved with no value, so the
 * agent goes on without it). A declined mandatory condition is refused.
 */
public abstract class GAbstractConditionTransportTranslator
		implements IAgentSuspendedWaitingConditionTransportTranslator {
	/** The attributes of every condition DTO. */
	public static final String STATEMENT = "statement";
	public static final String EXPLANATION = "explanation";
	public static final String MANDATORY = "mandatory";
	/** The attribute of a reaction declining a condition that is not mandatory. */
	public static final String DECLINED = "declined";
	static final String DECLINED_MANDATORY = "The condition is mandatory and cannot be declined: ";
	static final String NOT_THE_SAVED_CONDITION = "The reaction is to another condition: ";

	@Override
	public AgentSuspendedWaitingConditionDto translate(AgentSuspendedWaitingCondition condition) {
		final AgentSuspendedWaitingConditionDto dto = new AgentSuspendedWaitingConditionDto();
		dto.setConditionType(getId());
		dto.setConditionId(condition.getSuspendedConditionId());
		final LinkedHashMap<String, Object> attributes = new LinkedHashMap<String, Object>();
		attributes.put(STATEMENT, condition.getWaitingConditionStatement());
		attributes.put(EXPLANATION, condition.getWaitedConditionInstanceLLMExplanation());
		attributes.put(MANDATORY, condition.isMandatory());
		describe(condition, attributes);
		dto.setAttributes(attributes);
		return dto;
	}

	/** Adds to the DTO's attributes what this kind of condition shows the client. */
	protected abstract void describe(AgentSuspendedWaitingCondition condition, LinkedHashMap<String, Object> attributes);

	@Override
	public boolean canTranslateReaction(AgentSuspendedWaitingConditionReactionDto reaction) {
		return reaction != null && getId().equals(reaction.getConditionType());
	}

	@Override
	public AgentSuspendedWaitingCondition translateReaction(AgentSuspendedWaitingConditionReactionDto reaction,
			AgentSuspendedWaitingCondition savedCondition) {
		if (reaction == null || savedCondition == null) {
			throw new IllegalArgumentException("A reaction and the condition it is to are both needed");
		}
		if (reaction.getConditionId() != null && savedCondition.getSuspendedConditionId() != null
				&& !reaction.getConditionId().equals(savedCondition.getSuspendedConditionId())) {
			throw new IllegalArgumentException(NOT_THE_SAVED_CONDITION + reaction.getConditionId() + " is not "
					+ savedCondition.getSuspendedConditionId());
		}
		if (isDeclined(reaction)) {
			if (savedCondition.isMandatory()) {
				throw new IllegalArgumentException(DECLINED_MANDATORY + savedCondition.getWaitingConditionStatement());
			}
			savedCondition.setInstanceValue(null);
			return savedCondition;
		}
		resolve(reaction, savedCondition);
		return savedCondition;
	}

	/** Fills the saved condition's instance value with what the reaction's attributes say. */
	protected abstract void resolve(AgentSuspendedWaitingConditionReactionDto reaction,
			AgentSuspendedWaitingCondition savedCondition);

	protected static boolean isDeclined(AgentSuspendedWaitingConditionReactionDto reaction) {
		final Object declined = attribute(reaction, DECLINED);
		return declined instanceof Boolean b ? b : declined != null && Boolean.parseBoolean(String.valueOf(declined));
	}

	protected static Object attribute(AgentSuspendedWaitingConditionReactionDto reaction, String name) {
		return reaction != null && reaction.getAttributes() != null ? reaction.getAttributes().get(name) : null;
	}

	protected static String text(AgentSuspendedWaitingConditionReactionDto reaction, String name) {
		final Object value = attribute(reaction, name);
		return value != null && !String.valueOf(value).isBlank() ? String.valueOf(value).strip() : null;
	}
}
