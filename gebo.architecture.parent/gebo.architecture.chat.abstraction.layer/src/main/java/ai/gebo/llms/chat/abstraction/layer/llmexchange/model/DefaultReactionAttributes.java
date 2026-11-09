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

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ai.gebo.llms.chat.pipelines.service.impl.DefaultConditionTransportTranslator;
import ai.gebo.llms.chat.pipelines.service.impl.GAbstractConditionTransportTranslator;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The answer to a condition of the {@code default} type, typed. When an answer
 * carries it, it stands for the attributes it covers: those of the map are
 * dropped, the others are kept for the custom extensions.
 */
@Data
@NoArgsConstructor
public class DefaultReactionAttributes implements Serializable {
	private static final long serialVersionUID = 1L;
	/** The attributes this answer covers. */
	public static final List<String> COVERED = List.of(GAbstractConditionTransportTranslator.DECLINED,
			DefaultConditionTransportTranslator.VALUE);
	/** {@code true} when the condition is not answered; only an optional one may be. */
	private Boolean declined = null;
	/** The value, of the condition's value type or convertible to it. */
	private Object value = null;

	/** The typed view of the attributes of an answer, {@code null} without them. */
	public static DefaultReactionAttributes of(Map<String, Object> attributes) {
		if (attributes == null) {
			return null;
		}
		final DefaultReactionAttributes typed = new DefaultReactionAttributes();
		typed.setDeclined(flag(attributes, GAbstractConditionTransportTranslator.DECLINED));
		typed.setValue(attributes.get(DefaultConditionTransportTranslator.VALUE));
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
		if (value != null) {
			merged.put(DefaultConditionTransportTranslator.VALUE, value);
		}
		return merged;
	}
}
