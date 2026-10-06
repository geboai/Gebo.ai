/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.deepsearch.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import ai.gebo.model.DocumentMetaInfos;

/** Pins the log description of a deep search batch by source document. */
class DeepSearchBatchTraceTest {

	@Test
	void batchIsDescribedBySourceLargestFirst() {
		Document small = new Document("short text", Map.of(DocumentMetaInfos.CONTENT_ORIGINAL_URL, "https://a.org/page"));
		Document big1 = new Document("a much longer text ".repeat(50),
				Map.of(DocumentMetaInfos.GEBO_FILE_NAME, "huge.pdf"));
		Document big2 = new Document("another long text ".repeat(50),
				Map.of(DocumentMetaInfos.GEBO_FILE_NAME, "huge.pdf"));
		Document anonymous = new Document("no source");

		String composition = DeepSearchBatchTrace.composition(List.of(small, big1, big2, anonymous));

		assertTrue(composition.startsWith("4 fragment(s) "), composition);
		assertTrue(composition.contains("from 3 document(s): [huge.pdf: 2 fragment(s) "), composition);
		assertTrue(composition.indexOf("huge.pdf") < composition.indexOf("https://a.org/page"), composition);
		assertTrue(composition.contains("unknown: 1 fragment(s)"), composition);
		assertEquals("no fragment", DeepSearchBatchTrace.composition(List.of()));
	}

	@Test
	void repeatedOrMadeUpIrrelevantIdsAreARunaway() {
		Document page = new Document("p1", "page text", Map.of(DocumentMetaInfos.CONTENT_ORIGINAL_URL, "https://a.org"));
		Document pdf = new Document("p2", "pdf text", Map.of(DocumentMetaInfos.GEBO_FILE_NAME, "huge.pdf"));
		List<Document> batch = List.of(page, pdf);

		assertNull(DeepSearchBatchTrace.runawayReport("analysis\nIRRILEVANT=p1", "IRRILEVANT", batch));
		assertNull(DeepSearchBatchTrace.runawayReport("analysis without the line", "IRRILEVANT", batch));

		String repeated = DeepSearchBatchTrace.runawayReport("analysis\nIRRILEVANT= p2, p1, p2, p2, made-up",
				"IRRILEVANT", batch);
		assertTrue(repeated.startsWith("5 irrelevant entr(ies) for 2 fragment(s), 3 distinct, 1 not in the batch"),
				repeated);
		assertTrue(repeated.contains("p2 x3 (huge.pdf)"), repeated);
		assertTrue(repeated.contains("made-up x1 (not in the batch)"), repeated);
	}

	@Test
	void aBatchIsNumberedForTheModelAndTheNumbersLeadBackToItsFragments() {
		Document first = Document.builder().id("3f2a9c1e-0000-4000-8000-000000000001").text("first")
				.metadata(Map.of(DocumentMetaInfos.GEBO_FILE_NAME, "contract.pdf")).build();
		Document second = Document.builder().id("3f2a9c1e-0000-4000-8000-000000000002").text("second").build();

		DeepSearchBatchTrace.NumberedBatch numbered = DeepSearchBatchTrace.numbered(List.of(first, second));

		assertEquals(List.of("1", "2"), numbered.documents().stream().map(Document::getId).toList());
		assertEquals("first", numbered.documents().get(0).getText());
		assertEquals("contract.pdf", numbered.documents().get(0).getMetadata().get(DocumentMetaInfos.GEBO_FILE_NAME));
		assertEquals(first.getId(), numbered.idOf("1"));
		assertEquals(second.getId(), numbered.idOf(" 2 "));
		assertNull(numbered.idOf("3"), "made up");
		assertEquals("3f2a9c1e-0000-4000-8000-000000000001", first.getId(), "the fragments keep their ids");
	}

	@Test
	void theWatchHoldsOnceTheListGivesMoreEntriesThanTheBatchHasFragments() {
		Predicate<CharSequence> watch = DeepSearchBatchTrace.runawayWatch("IRRILEVANT", 3);
		StringBuilder text = new StringBuilder();

		text.append("The analysis, with commas, many words, 1, 2, 3, 4, 5.\n");
		assertFalse(watch.test(text), "the analysis text does not count");
		text.append("IRRI");
		assertFalse(watch.test(text));
		text.append("LEVANT=1, 2,3");
		assertFalse(watch.test(text), "as many as the fragments");
		text.append(",1");
		assertTrue(watch.test(text), "one more than the fragments");

		Predicate<CharSequence> fine = DeepSearchBatchTrace.runawayWatch("IRRILEVANT", 3);
		assertFalse(fine.test("text\nirrilevant= 1 , 3\n<IS-COMPLETELY-SATISFACTORY/>, a, b, c, d"),
				"only the list line counts, in any case");
	}

	@Test
	void theListsOfARunawayAnalysisAreRemovedFromIt() {
		assertEquals("The analysis.\n\nend",
				DeepSearchBatchTrace.withoutIrrelevantLists("The analysis.\nIRRILEVANT=1,2,1,2,1\nend", "IRRILEVANT"));
		assertEquals("The analysis.\n",
				DeepSearchBatchTrace.withoutIrrelevantLists("The analysis.\nIRRILEVANT=1,2,1,2,1,2,1,2", "IRRILEVANT"));
		assertEquals("", DeepSearchBatchTrace.withoutIrrelevantLists(null, "IRRILEVANT"));
	}
}
