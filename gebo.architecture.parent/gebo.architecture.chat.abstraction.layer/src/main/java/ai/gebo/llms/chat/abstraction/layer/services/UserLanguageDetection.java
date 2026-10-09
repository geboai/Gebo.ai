/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services;

import java.io.IOException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.system.ingestion.IGLanguageDetector;

/**
 * The language a user's message is written in, as the platform's language detector
 * (the one the ingestion tags the documents with) tells it: named in English (e.g.
 * "English", "Italian") so that the prompts say it outright instead of asking the
 * model to deduce it, which a model fails to do once documents or tools' results in
 * another language fill its context.
 */
public final class UserLanguageDetection {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserLanguageDetection.class);
	/** Below this length the detection is not tried (as for the keywords' language). */
	public static final int MIN_DETECTION_CHARS = 30;
	/** Below this confidence the detection is not trusted (as for the keywords' language). */
	public static final double MIN_CONFIDENCE = 0.5;

	private UserLanguageDetection() {
	}

	/**
	 * The English name of the language of {@code text}; null when there is no
	 * detector, the text is too short or the detection is not trusted.
	 */
	public static String of(IGLanguageDetector detector, String text) {
		if (detector == null || text == null) {
			return null;
		}
		final String trimmed = text.strip();
		if (trimmed.length() < MIN_DETECTION_CHARS) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("of(...) " + trimmed.length() + " character(s), too short to detect the user's language");
			}
			return null;
		}
		try {
			final IGLanguageDetector.DetectedLanguage detected = detector.detect(trimmed);
			final String name = detected != null && detected.getConfidence() >= MIN_CONFIDENCE
					? englishName(detected.getLanguage())
					: null;
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("of(...) detected:" + detected + " user's language:" + name);
			}
			return name;
		} catch (IOException | RuntimeException e) {
			LOGGER.warn("Cannot detect the language of the user's message, the prompts ask the model to deduce it", e);
			return null;
		}
	}

	/** The English name of a language code ("it" gives "Italian"); null for no code. */
	public static String englishName(String code) {
		if (code == null || code.isBlank()) {
			return null;
		}
		final String name = Locale.forLanguageTag(code.strip()).getDisplayLanguage(Locale.ENGLISH);
		return name != null && !name.isBlank() ? name : code.strip();
	}
}
