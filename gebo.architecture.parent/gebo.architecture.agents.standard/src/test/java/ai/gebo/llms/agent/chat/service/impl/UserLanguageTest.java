/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.chat.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import ai.gebo.system.ingestion.IGLanguageDetector;
import ai.gebo.system.ingestion.IGLanguageDetector.DetectedLanguage;

/**
 * Pins the naming of the user's language for the agents' prompts: detected on the
 * user's message by the platform's detector, trusted with the same guards as the
 * keywords' language, named in English.
 */
class UserLanguageTest {

	private static final String QUESTION = "Write a detailed analysis of how the figure of Christ is presented.";

	private static IGLanguageDetector detecting(String code, double confidence) throws IOException {
		IGLanguageDetector detector = mock(IGLanguageDetector.class);
		when(detector.detect(anyString())).thenReturn(new DetectedLanguage(code, confidence));
		return detector;
	}

	@Test
	void aTrustedDetectionIsNamedInEnglish() throws IOException {
		assertEquals("English", UserLanguage.of(detecting("en", 0.99), QUESTION));
		assertEquals("Italian", UserLanguage.of(detecting("it", 0.9), "Scrivi un'analisi dettagliata della figura del Cristo."));
	}

	@Test
	void anUntrustedOrImpossibleDetectionNamesNothing() throws IOException {
		assertNull(UserLanguage.of(null, QUESTION), "no detector deployed");
		assertNull(UserLanguage.of(detecting("en", 0.2), QUESTION), "a low confidence is not trusted");
		assertNull(UserLanguage.of(detecting("en", 0.99), null));

		IGLanguageDetector shortText = detecting("en", 0.99);
		assertNull(UserLanguage.of(shortText, "Who is Fohat?"), "too short to detect");
		verify(shortText, never()).detect(anyString());

		IGLanguageDetector failing = mock(IGLanguageDetector.class);
		when(failing.detect(anyString())).thenThrow(new IOException("models not loaded"));
		assertNull(UserLanguage.of(failing, QUESTION), "a failing detection leaves the language to the model");
	}

	@Test
	void aCodeIsNamedInEnglish() {
		assertEquals("German", UserLanguage.englishName("de"));
		assertEquals("Portuguese", UserLanguage.englishName(" pt "));
		assertNull(UserLanguage.englishName(" "));
	}
}
