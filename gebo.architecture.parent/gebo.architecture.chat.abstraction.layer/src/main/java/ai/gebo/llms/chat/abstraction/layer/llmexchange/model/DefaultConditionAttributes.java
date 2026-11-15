/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import static ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ConditionAttributesReading.flag;
import static ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ConditionAttributesReading.text;

import java.util.Map;

import ai.gebo.llms.chat.pipelines.service.impl.DefaultConditionTransportTranslator;
import ai.gebo.llms.chat.pipelines.service.impl.GAbstractConditionTransportTranslator;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * What a condition of the {@code default} type shows the client, typed: the same
 * as its attributes map, which stays for the clients that read it.
 */
@Data
@NoArgsConstructor
public class DefaultConditionAttributes {
	private String statement = null;
	private String explanation = null;
	private boolean mandatory = true;
	/** The class name of the value the answer is to be. */
	private String valueType = null;
	/** The class name of what the model chose when it asked. */
	private String optionsType = null;
	/** What the model chose when it asked. */
	private Object llmChosenOptions = null;

	/** The typed view of the attributes of a condition, {@code null} without them. */
	public static DefaultConditionAttributes of(Map<String, Object> attributes) {
		if (attributes == null) {
			return null;
		}
		final DefaultConditionAttributes typed = new DefaultConditionAttributes();
		typed.setStatement(text(attributes, GAbstractConditionTransportTranslator.STATEMENT));
		typed.setExplanation(text(attributes, GAbstractConditionTransportTranslator.EXPLANATION));
		typed.setMandatory(flag(attributes, GAbstractConditionTransportTranslator.MANDATORY, true));
		typed.setValueType(text(attributes, DefaultConditionTransportTranslator.VALUE_TYPE));
		typed.setOptionsType(text(attributes, DefaultConditionTransportTranslator.OPTIONS_TYPE));
		typed.setLlmChosenOptions(attributes.get(DefaultConditionTransportTranslator.LLM_CHOSEN_OPTIONS));
		return typed;
	}
}
