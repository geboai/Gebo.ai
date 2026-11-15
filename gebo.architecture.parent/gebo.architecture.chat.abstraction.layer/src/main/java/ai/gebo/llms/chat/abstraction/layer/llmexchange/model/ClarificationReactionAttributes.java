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
import static ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ConditionAttributesReading.texts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ai.gebo.llms.chat.pipelines.service.impl.ClarificationConditionTransportTranslator;
import ai.gebo.llms.chat.pipelines.service.impl.GAbstractConditionTransportTranslator;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The answer to a condition of the {@code clarification} type, typed. When an
 * answer carries it, it stands for the attributes it covers: those of the map are
 * dropped, so a chosen option left in the map is not added to the typed ones; the
 * others are kept for the custom extensions.
 */
@Data
@NoArgsConstructor
public class ClarificationReactionAttributes implements Serializable {
	private static final long serialVersionUID = 1L;
	/** The attributes this answer covers. */
	public static final List<String> COVERED = List.of(GAbstractConditionTransportTranslator.DECLINED,
			ClarificationConditionTransportTranslator.CHOSEN_OPTION,
			ClarificationConditionTransportTranslator.CHOSEN_OPTIONS,
			ClarificationConditionTransportTranslator.CUSTOM_EXPLANATION);
	/** {@code true} when the question is not answered; only an optional one may be. */
	private Boolean declined = null;
	/** The label of the option chosen. */
	private String chosenOption = null;
	/** The labels of the options chosen, when more than one may be. */
	private List<String> chosenOptions = null;
	/** The answer in the user's own words, when the question takes one. */
	private String customExplanation = null;

	/** The typed view of the attributes of an answer, {@code null} without them. */
	public static ClarificationReactionAttributes of(Map<String, Object> attributes) {
		if (attributes == null) {
			return null;
		}
		final ClarificationReactionAttributes typed = new ClarificationReactionAttributes();
		typed.setDeclined(flag(attributes, GAbstractConditionTransportTranslator.DECLINED));
		typed.setChosenOption(text(attributes, ClarificationConditionTransportTranslator.CHOSEN_OPTION));
		typed.setChosenOptions(texts(attributes, ClarificationConditionTransportTranslator.CHOSEN_OPTIONS));
		typed.setCustomExplanation(text(attributes, ClarificationConditionTransportTranslator.CUSTOM_EXPLANATION));
		return typed;
	}

	/**
	 * Puts this answer in the attributes, in place of what they said of the
	 * attributes it covers.
	 */
	public LinkedHashMap<String, Object> mergeInto(LinkedHashMap<String, Object> attributes) {
		final LinkedHashMap<String, Object> merged = attributes != null ? attributes
				: new LinkedHashMap<String, Object>();
		merged.keySet().removeAll(COVERED);
		if (declined != null) {
			merged.put(GAbstractConditionTransportTranslator.DECLINED, declined);
		}
		if (chosenOption != null) {
			merged.put(ClarificationConditionTransportTranslator.CHOSEN_OPTION, chosenOption);
		}
		if (chosenOptions != null) {
			merged.put(ClarificationConditionTransportTranslator.CHOSEN_OPTIONS, new ArrayList<String>(chosenOptions));
		}
		if (customExplanation != null) {
			merged.put(ClarificationConditionTransportTranslator.CUSTOM_EXPLANATION, customExplanation);
		}
		return merged;
	}
}
