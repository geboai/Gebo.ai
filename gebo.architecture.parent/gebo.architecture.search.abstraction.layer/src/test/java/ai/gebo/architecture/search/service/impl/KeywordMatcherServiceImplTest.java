/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.search.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Pins the keyword gate of the chunks: the words of a question that tell nothing
 * (articles, prepositions) must not let every chunk through, and punctuation must
 * not hide a word.
 */
class KeywordMatcherServiceImplTest {

	private final KeywordMatcherServiceImpl matcher = new KeywordMatcherServiceImpl();

	/** The keywords of "quali sono le penali del contratto" as the question's words. */
	private static final List<String> QUESTION = List.of("quali", "sono", "le", "penali", "del", "contratto");

	@Test
	void aChunkSharingOnlyStopWordsWithTheQuestionIsLeftOut() {
		assertFalse(matcher.isMatching(QUESTION, "Il fornitore consegna le merci entro trenta giorni dal ordine.", 1));
		assertFalse(matcher.isMatching(List.of("the", "terms", "of", "renewal"),
				"The weather of the region is mild.", 1));
	}

	@Test
	void aChunkWithAnInformativeWordIsKept() {
		assertTrue(matcher.isMatching(QUESTION, "Le penali sono dovute in caso di ritardo.", 1));
	}

	@Test
	void punctuationDoesNotHideAWord() {
		assertTrue(matcher.isMatching(QUESTION, "Firmato il contratto, le parti concordano.", 1));
		assertTrue(matcher.isMatching(List.of("renewal"), "(renewal) is automatic.", 1));
	}

	@Test
	void accentsAndCaseAreFolded() {
		assertTrue(matcher.isMatching(List.of("qualità"), "La QUALITA del servizio.", 1));
	}

	@Test
	void keywordsMadeOnlyOfStopWordsDoNotFilter() {
		assertTrue(matcher.isMatching(List.of("il", "della", "the"), "Anything at all.", 1));
	}

	@Test
	void aPhraseNeedsItsInformativeWordsOnly() {
		assertTrue(matcher.isMatching(List.of("penali del contratto"), "Contratto: penali applicate.", 1));
		assertFalse(matcher.isMatching(List.of("penali del contratto"), "Penali applicate.", 1));
	}
}
