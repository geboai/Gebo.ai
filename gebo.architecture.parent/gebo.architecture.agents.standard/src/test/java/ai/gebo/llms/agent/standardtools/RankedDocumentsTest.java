/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import ai.gebo.model.DocumentMetaInfos;

/**
 * Pins the documents a ranking of fragments keeps: rated by their best fragment, the
 * topK best kept with all their fragments, the others left.
 */
class RankedDocumentsTest {

	private static Document fragment(String id, String document, long position) {
		return Document.builder().id(id).text(id)
				.metadata(Map.of(DocumentMetaInfos.CONTENT_CODE, document, DocumentMetaInfos.GEBO_CHUNK_POSITION, position))
				.build();
	}

	private static List<String> ids(List<Document> documents) {
		return documents.stream().map(Document::getId).toList();
	}

	@Test
	void theTopKBestDocumentsAreKeptWithAllTheirFragments() {
		// ranked: b's best, a's best, c, then the rest of a and b
		List<Document> ranked = List.of(fragment("b2", "b", 2), fragment("a5", "a", 5), fragment("c1", "c", 1),
				fragment("a1", "a", 1), fragment("b1", "b", 1));

		assertEquals(List.of("b2", "b1", "a5", "a1"), ids(RankedDocuments.top(ranked, 2, false)),
				"documents by their best fragment, the fragments in ranking order, c left");
		assertEquals(List.of("b1", "b2", "a1", "a5"), ids(RankedDocuments.top(ranked, 2, true)),
				"each document in reading order");
		assertEquals(5, RankedDocuments.top(ranked, 10, false).size());
		assertEquals(3, RankedDocuments.documentsIn(ranked));
	}

	@Test
	void aFragmentWithoutADocumentIsADocumentOfItsOwn() {
		List<Document> ranked = List.of(Document.builder().id("x").text("x").build(),
				Document.builder().id("y").text("y").build(), fragment("a1", "a", 1));

		assertEquals(List.of("x", "y"), ids(RankedDocuments.top(ranked, 2, true)));
	}
}
