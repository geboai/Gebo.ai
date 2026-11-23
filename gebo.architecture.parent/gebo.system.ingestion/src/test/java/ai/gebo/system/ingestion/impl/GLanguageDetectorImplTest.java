/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.system.ingestion.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.system.ingestion.IGLanguageDetector.DetectedLanguage;

/**
 * Pins the language the ingestion tags documents with, as Tika's default language
 * detector (Optimaize) finds it.
 */
class GLanguageDetectorImplTest {

	final GLanguageDetectorImpl detector = new GLanguageDetectorImpl();

	@Test
	void italianAndEnglishAreTold() throws Exception {
		DetectedLanguage italian = detector.detect(
				"La rassegna stampa di oggi raccoglie gli articoli dei principali quotidiani nazionali "
						+ "sulla riforma delle pensioni e sul bilancio dello Stato.");
		DetectedLanguage english = detector.detect(
				"Today's press review collects the articles of the main national newspapers "
						+ "on the pension reform and on the state budget.");
		assertEquals("it", italian.getLanguage());
		assertEquals("en", english.getLanguage());
		assertTrue(italian.getConfidence() > 0, "a confidence comes with the language");
	}

	@Test
	void aDocumentIsTaggedWithItsLanguage() throws Exception {
		Document document = new Document("Der Bericht beschreibt die Ergebnisse des dritten Quartals "
				+ "und die Pläne für das kommende Jahr.", new HashMap<>());
		detector.addLanguageMetaData(document);
		assertEquals("de", document.getMetadata().get(DocumentMetaInfos.LANGUAGE));
	}
}
