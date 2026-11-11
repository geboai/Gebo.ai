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
import ai.gebo.architecture.agents.model.UnansweredBecause;
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
	/**
	 * The attribute of a chat answer asking to go on without the condition, even a
	 * mandatory one: read by the chat for the conversation's own user only, and
	 * refused anywhere else it turns up.
	 */
	public static final String GO_ON_WITHOUT = "goOnWithout";
	static final String DECLINED_MANDATORY = "The condition is mandatory and cannot be declined: ";
	public static final String GO_ON_WITHOUT_NOT_HERE = "Only the owner of the conversation, from the chat, or an administrator,"
			+ " from the waiting conditions panel, may go on without a condition: ";
	static final String NOT_THE_SAVED_CONDITION = "The reaction is to another condition: ";

	@Override
	public AgentSuspendedWaitingConditionDto translate(AgentSuspendedWaitingCondition condition) {
		final AgentSuspendedWaitingConditionDto dto = new AgentSuspendedWaitingConditionDto();
		dto.setConditionType(getId());
		dto.setConditionId(condition.getSuspendedConditionId());
		dto.setHumanInTheLoopProcessTreatment(condition.isHumanInTheLoopProcessTreatment());
		dto.setTarget(condition.getTarget() != null ? condition.getTarget().copy() : null);
		final LinkedHashMap<String, Object> attributes = new LinkedHashMap<String, Object>();
		attributes.put(STATEMENT, condition.getWaitingConditionStatement());
		attributes.put(EXPLANATION, condition.getWaitedConditionInstanceLLMExplanation());
		attributes.put(MANDATORY, condition.isMandatory());
		describe(condition, attributes);
		dto.setAttributes(attributes);
		typed(dto, attributes);
		return dto;
	}

	/** Adds to the DTO's attributes what this kind of condition shows the client. */
	protected abstract void describe(AgentSuspendedWaitingCondition condition, LinkedHashMap<String, Object> attributes);

	/**
	 * Gives the DTO the typed attributes of this type of condition, made of its
	 * attributes; a type without typed attributes gives none.
	 */
	protected void typed(AgentSuspendedWaitingConditionDto dto, LinkedHashMap<String, Object> attributes) {
	}

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
		// an answer given typed is read as its attributes
		reaction.mergeTypedAttributes();
		if (reaction.getConditionId() != null && savedCondition.getSuspendedConditionId() != null
				&& !reaction.getConditionId().equals(savedCondition.getSuspendedConditionId())) {
			throw new IllegalArgumentException(NOT_THE_SAVED_CONDITION + reaction.getConditionId() + " is not "
					+ savedCondition.getSuspendedConditionId());
		}
		if (isTrue(attribute(reaction, GO_ON_WITHOUT))) {
			throw new IllegalArgumentException(GO_ON_WITHOUT_NOT_HERE + savedCondition.getWaitingConditionStatement());
		}
		if (reaction.getUnanswered() != null && reaction.getUnanswered() != UnansweredBecause.DECLINED) {
			// the platform's own: the run goes on without the condition, mandatory or not
			savedCondition.setInstanceValue(null);
			savedCondition.setUnansweredBecause(reaction.getUnanswered());
			return savedCondition;
		}
		if (isDeclined(reaction)) {
			if (savedCondition.isMandatory()) {
				throw new IllegalArgumentException(DECLINED_MANDATORY + savedCondition.getWaitingConditionStatement());
			}
			savedCondition.setInstanceValue(null);
			savedCondition.setUnansweredBecause(UnansweredBecause.DECLINED);
			return savedCondition;
		}
		resolve(reaction, savedCondition);
		savedCondition.setUnansweredBecause(null);
		return savedCondition;
	}

	/** Fills the saved condition's instance value with what the reaction's attributes say. */
	protected abstract void resolve(AgentSuspendedWaitingConditionReactionDto reaction,
			AgentSuspendedWaitingCondition savedCondition);

	protected static boolean isDeclined(AgentSuspendedWaitingConditionReactionDto reaction) {
		return isTrue(attribute(reaction, DECLINED));
	}

	/** Whether an attribute says yes: a boolean, or a text that reads as one. */
	public static boolean isTrue(Object attribute) {
		return attribute instanceof Boolean b ? b : attribute != null && Boolean.parseBoolean(String.valueOf(attribute));
	}

	protected static Object attribute(AgentSuspendedWaitingConditionReactionDto reaction, String name) {
		return reaction != null && reaction.getAttributes() != null ? reaction.getAttributes().get(name) : null;
	}

	protected static String text(AgentSuspendedWaitingConditionReactionDto reaction, String name) {
		final Object value = attribute(reaction, name);
		return value != null && !String.valueOf(value).isBlank() ? String.valueOf(value).strip() : null;
	}
}
