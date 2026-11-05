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

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;

/**
 * Carries any waiting condition no other translator knows: down, the types by
 * name of the value waited for and of what the model chose, and what the model
 * chose as it is; up, the value the reaction gives ({@value #VALUE}), converted
 * to the type the condition waits for. Ordered first among the translators
 * because the repository keeps the last translator that can translate: a
 * translator made for a kind of condition, declared after this one, wins over it.
 */
@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DefaultConditionTransportTranslator extends GAbstractConditionTransportTranslator {
	public static final String ID = "default";
	/** The attributes of the DTO. */
	public static final String VALUE_TYPE = "valueType";
	public static final String OPTIONS_TYPE = "optionsType";
	public static final String LLM_CHOSEN_OPTIONS = "llmChosenOptions";
	/** The attribute of the reaction: the value waited for, as the client gives it. */
	public static final String VALUE = "value";
	static final String NO_VALUE = "The reaction gives no value for the condition: ";
	static final String VALUE_OF_ANOTHER_TYPE = "The value given cannot be read as what the condition waits for: ";
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public boolean canTranslate(AgentSuspendedWaitingCondition condition) {
		return condition != null;
	}

	@Override
	protected void describe(AgentSuspendedWaitingCondition condition, LinkedHashMap<String, Object> attributes) {
		attributes.put(VALUE_TYPE, condition.getType() != null ? condition.getType().getName() : null);
		attributes.put(OPTIONS_TYPE, condition.getOptionsType() != null ? condition.getOptionsType().getName() : null);
		attributes.put(LLM_CHOSEN_OPTIONS, condition.getLlmChosenOptions());
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void resolve(AgentSuspendedWaitingConditionReactionDto reaction,
			AgentSuspendedWaitingCondition savedCondition) {
		final Object value = attribute(reaction, VALUE);
		if (value == null) {
			throw new IllegalArgumentException(NO_VALUE + savedCondition.getWaitingConditionStatement());
		}
		final Class<?> type = savedCondition.getType() != null ? savedCondition.getType() : Object.class;
		try {
			savedCondition.setInstanceValue(type.isInstance(value) ? value : MAPPER.convertValue(value, type));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(VALUE_OF_ANOTHER_TYPE + type.getName(), e);
		}
	}
}
