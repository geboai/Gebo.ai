/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.text.Normalizer;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Whether a question names a language for its answer: the language the request
 * understanding reports as asked is one only when the question has one of its names,
 * in any language the Java platform knows ("in inglese", "auf Deutsch", "en français",
 * "in English"). A model reporting the language the question is written in, a language
 * no word of the question names, is not followed.
 */
final class AskedLanguage {
	private static final Map<String, Set<String>> NAMES = new ConcurrentHashMap<>();

	private AskedLanguage() {
	}

	/**
	 * Whether the text names the language, given by its English name as the request
	 * understanding writes it ("Italian"); false for a name no language has.
	 */
	static boolean namedIn(String englishName, String text) {
		if (englishName == null || englishName.isBlank() || text == null) {
			return false;
		}
		final Set<String> names = NAMES.computeIfAbsent(folded(englishName), AskedLanguage::namesOf);
		final String foldedText = " " + folded(text) + " ";
		for (String name : names) {
			if (foldedText.contains(" " + name + " ")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * The names of the language in every language the platform knows, folded; empty when
	 * no language has that English name.
	 */
	static Set<String> namesOf(String foldedEnglishName) {
		final Set<String> names = new LinkedHashSet<>();
		final Locale[] available = Locale.getAvailableLocales();
		for (String code : Locale.getISOLanguages()) {
			final Locale language = Locale.forLanguageTag(code);
			if (!folded(language.getDisplayLanguage(Locale.ENGLISH)).equals(foldedEnglishName)) {
				continue;
			}
			for (Locale in : available) {
				final String name = folded(language.getDisplayLanguage(in));
				// a locale with no name for it gives the code back
				if (!name.isEmpty() && !name.equals(code)) {
					names.add(name);
				}
			}
		}
		return names;
	}

	/** Lower case, no accents, words separated by one space. */
	static String folded(String text) {
		if (text == null) {
			return "";
		}
		final String noAccents = Normalizer.normalize(text, Normalizer.Form.NFKD).replaceAll("\\p{M}+", "");
		return noAccents.toLowerCase(Locale.ROOT).replaceAll("[^\\p{L}\\p{N}]+", " ").trim();
	}
}
