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

	@Test
	void theHitsAskedNeverExceedTheInformativeKeywords() {
		// the deep search pure search asks two hits of more than three keywords: here only
		// one of them tells something, so one hit is enough
		assertTrue(matcher.isMatching(List.of("the", "of", "and", "renewal"), "The renewal is automatic.", 2));
		assertFalse(matcher.isMatching(List.of("the", "of", "and", "renewal"), "The weather of the region.", 2));
		// with enough informative keywords two hits are still asked
		assertFalse(matcher.isMatching(List.of("contract", "renewal", "penalty", "term"), "The renewal is automatic.", 2));
		assertTrue(matcher.isMatching(List.of("contract", "renewal", "penalty", "term"), "The contract renewal.", 2));
	}

	@Test
	void theStopWordsAreTheOnesOfTheLanguagesNamed() {
		// French keywords: with French named, "les" and "du" say nothing
		List<String> french = List.of("les pénalités du contrat");
		assertFalse(matcher.isMatching(List.of("les", "contrat"), "La société paie les frais.", 1, List.of("fr")));
		assertTrue(matcher.isMatching(french, "Les pénalités du contrat sont dues.", 1, List.of("fr")));
		// without a language the fallback (it, en) does not know "les": it lets the chunk through
		assertTrue(matcher.isMatching(List.of("les", "contrat"), "La société paie les frais.", 1));
	}

	@Test
	void aWordThatIsAStopWordInTheChunkLanguageTellsNothingOfIt() {
		// "die" is an English word, and the German article: in a German chunk it says nothing
		assertFalse(matcher.isMatching(List.of("die", "renewal"), "Die Firma zahlt die Kosten.", 1, List.of("en", "de")));
		assertTrue(matcher.isMatching(List.of("die", "renewal"), "Die Firma zahlt die Kosten.", 1, List.of("en")));
	}

	@Test
	void unknownLanguagesFallBackToTheConfiguredOnes() {
		assertFalse(matcher.isMatching(List.of("the", "renewal"), "The weather of the region.", 1, List.of("xx", "")));
		KeywordMatcherServiceImpl frenchFallback = new KeywordMatcherServiceImpl(List.of("fr"));
		assertFalse(frenchFallback.isMatching(List.of("les", "contrat"), "La société paie les frais.", 1));
		assertTrue(matcher.isMatching(List.of("penali"), "Le penali.", 1, List.of("zh-CN", "IT")),
				"region and case of the detected codes are understood");
	}
}
