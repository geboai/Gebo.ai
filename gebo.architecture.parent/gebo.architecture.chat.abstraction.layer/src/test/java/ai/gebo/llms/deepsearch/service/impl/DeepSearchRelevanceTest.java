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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import ai.gebo.model.DocumentMetaInfos;

/**
 * Pins the fragments a deep search finds relevant: the ones its analyses list, quote or
 * name the document of; a list running away or listing the whole batch says nothing.
 */
class DeepSearchRelevanceTest {

	private static Document fragment(String id, String fileName, String text) {
		final Map<String, Object> metadata = new HashMap<>();
		metadata.put(DocumentMetaInfos.GEBO_FILE_NAME, fileName);
		metadata.put(DocumentMetaInfos.CONTENT_CODE, "code-" + fileName);
		return Document.builder().id(id).text(text).metadata(metadata).build();
	}

	private static DeepSearchBatchTrace.NumberedBatch batch() {
		return DeepSearchBatchTrace.numbered(List.of(
				fragment("f-a", "Rudolf-Steiner-La-Scienza-Occulta.pdf", "The Guardian of the Threshold appears."),
				fragment("f-b", "pistis_sophia_svelato.pdf", "The archons hold the souls."),
				fragment("f-c", "Frammentidiuninsegnamentosconosciuto.pdf", "Ouspensky on the centres."),
				fragment("f-d", "dialoghi-su-ermetismo.pdf", "On the hermetic tradition.")));
	}

	@Test
	void theListedFragmentsAreRelevant() {
		final DeepSearchRelevance relevance = new DeepSearchRelevance();

		assertEquals(2, relevance.recordAnalysis("The analysis.\nRELEVANT_FRAGMENTS=1, 3, 9\n", batch(), false));

		assertTrue(relevance.isRelevant("f-a") && relevance.isRelevant("f-c"));
		assertFalse(relevance.isRelevant("f-b"), "not listed");
	}

	@Test
	void aListOfTheWholeBatchOrRunningAwaySaysNothing() {
		final DeepSearchRelevance all = new DeepSearchRelevance();
		all.recordAnalysis("The analysis.\nRELEVANT_FRAGMENTS=1,2,3,4\n", batch(), false);
		assertTrue(all.isEmpty(), "every fragment listed");

		final DeepSearchRelevance ranAway = new DeepSearchRelevance();
		ranAway.recordAnalysis("The analysis.\nRELEVANT_FRAGMENTS=1,1,1,1,1,1\n", batch(), true);
		assertTrue(ranAway.isEmpty());

		final DeepSearchRelevance none = new DeepSearchRelevance();
		none.recordAnalysis("The analysis, no list.", batch(), false);
		assertTrue(none.isEmpty(), "a missing list says nothing");
	}

	@Test
	void theDocumentsTheAnalysisNamesAreRelevant() {
		final DeepSearchRelevance relevance = new DeepSearchRelevance();

		relevance.recordAnalysis("In La Scienza Occulta Steiner describes it; the Pistis Sophia Svelato adds the archons; "
				+ "FrammentiDiUnInsegnamentoSconosciuto.PDF is cited.\nRELEVANT_FRAGMENTS=\n", batch(), false);

		assertTrue(relevance.isRelevant("f-b"), "file name without its extension, three words, any case");
		assertTrue(relevance.isRelevant("f-c"), "a one word file name with its extension");
		assertFalse(relevance.isRelevant("f-a"), "a part of the file name is not the name");
		assertFalse(relevance.isRelevant("f-d"));
	}

	@Test
	void aOneWordNameOrTitleDoesNotCount() {
		final Map<String, Object> metadata = new HashMap<>();
		metadata.put(DocumentMetaInfos.GEBO_FILE_NAME, "Introduzione");
		assertTrue(DeepSearchRelevance.namesOf(metadata).isEmpty());
		assertFalse(DeepSearchRelevance.mentioned(" l introduzione dice ", metadata));
		metadata.put(DocumentMetaInfos.GEBO_FILE_NAME, "Introduzione.docx");
		assertTrue(DeepSearchRelevance.mentioned(" " + DeepSearchRelevance.folded("Vedi Introduzione.docx.") + " ",
				metadata));
	}

	@Test
	void theFragmentsOfTheVerifiedQuotationsAreRelevant() {
		final DeepSearchBatchTrace.NumberedBatch batch = batch();
		final DeepSearchQuotations quotations = new DeepSearchQuotations();
		quotations.keepVerified("Steiner: ⟦q:1|The Guardian of the Threshold appears⟧. And ⟦q:2|made up words⟧.", batch);
		final DeepSearchRelevance relevance = new DeepSearchRelevance();

		relevance.recordQuotations(quotations);

		assertTrue(relevance.isRelevant("f-a"));
		assertFalse(relevance.isRelevant("f-b"), "a quotation its fragment does not have is not verified");
	}
}
