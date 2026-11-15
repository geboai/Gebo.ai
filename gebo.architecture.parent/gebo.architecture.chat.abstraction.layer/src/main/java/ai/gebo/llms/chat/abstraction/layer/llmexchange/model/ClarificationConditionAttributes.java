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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import ai.gebo.architecture.agents.model.ClarificationOption;
import ai.gebo.llms.chat.pipelines.service.impl.ClarificationConditionTransportTranslator;
import ai.gebo.llms.chat.pipelines.service.impl.GAbstractConditionTransportTranslator;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * What a condition of the {@code clarification} type shows the client, typed: the
 * same as its attributes map, which stays for the clients that read it.
 */
@Data
@NoArgsConstructor
public class ClarificationConditionAttributes {
	private String statement = null;
	private String explanation = null;
	private boolean mandatory = true;
	/** The question as the user reads it. */
	private String question = null;
	/** The options offered, in order. */
	private List<ClarificationOption> options = new ArrayList<ClarificationOption>();
	/** Whether more than one option may be chosen. */
	private boolean multipleChoice = false;
	/** Whether an answer of the user's own words is taken. */
	private boolean customAnswerAllowed = false;

	/** The typed view of the attributes of a condition, {@code null} without them. */
	public static ClarificationConditionAttributes of(Map<String, Object> attributes) {
		if (attributes == null) {
			return null;
		}
		final ClarificationConditionAttributes typed = new ClarificationConditionAttributes();
		typed.setStatement(text(attributes, GAbstractConditionTransportTranslator.STATEMENT));
		typed.setExplanation(text(attributes, GAbstractConditionTransportTranslator.EXPLANATION));
		typed.setMandatory(flag(attributes, GAbstractConditionTransportTranslator.MANDATORY, true));
		typed.setQuestion(text(attributes, ClarificationConditionTransportTranslator.QUESTION));
		typed.setOptions(optionsOf(attributes.get(ClarificationConditionTransportTranslator.OPTIONS)));
		typed.setMultipleChoice(flag(attributes, ClarificationConditionTransportTranslator.MULTIPLE_CHOICE, false));
		typed.setCustomAnswerAllowed(
				flag(attributes, ClarificationConditionTransportTranslator.CUSTOM_ANSWER_ALLOWED, false));
		return typed;
	}

	/** The options as the translator describes them, or as they came back as JSON. */
	private static List<ClarificationOption> optionsOf(Object described) {
		final List<ClarificationOption> options = new ArrayList<ClarificationOption>();
		if (!(described instanceof Collection<?> entries)) {
			return options;
		}
		for (Object entry : entries) {
			if (entry instanceof ClarificationOption option) {
				options.add(option);
			} else if (entry instanceof Map<?, ?> map) {
				final Object label = map.get(ClarificationConditionTransportTranslator.OPTION_LABEL);
				final Object description = map.get(ClarificationConditionTransportTranslator.OPTION_DESCRIPTION);
				options.add(ClarificationOption.of(label != null ? String.valueOf(label) : null,
						description != null ? String.valueOf(description) : null));
			}
		}
		return options;
	}
}
