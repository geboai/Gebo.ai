/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Reads the values of an attributes map the way the translators do: the map may
 * come from a client as JSON, so a flag may be a string and a list any collection.
 */
final class ConditionAttributesReading {
	private ConditionAttributesReading() {
	}

	static String text(Map<String, Object> attributes, String name) {
		final Object value = attributes.get(name);
		return value != null ? String.valueOf(value) : null;
	}

	/** The flag, {@code null} when absent. */
	static Boolean flag(Map<String, Object> attributes, String name) {
		final Object value = attributes.get(name);
		if (value == null) {
			return null;
		}
		return value instanceof Boolean b ? b : Boolean.parseBoolean(String.valueOf(value));
	}

	static boolean flag(Map<String, Object> attributes, String name, boolean otherwise) {
		final Boolean value = flag(attributes, name);
		return value != null ? value : otherwise;
	}

	/** The texts of the list, {@code null} when absent or not a list. */
	static List<String> texts(Map<String, Object> attributes, String name) {
		if (!(attributes.get(name) instanceof Collection<?> values)) {
			return null;
		}
		final List<String> texts = new ArrayList<String>();
		for (Object value : values) {
			if (value != null) {
				texts.add(String.valueOf(value));
			}
		}
		return texts;
	}
}
