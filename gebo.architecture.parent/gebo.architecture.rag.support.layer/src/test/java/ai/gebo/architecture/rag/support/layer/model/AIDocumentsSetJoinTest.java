/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.rag.support.layer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import ai.gebo.model.DocumentMetaInfos;

/**
 * Pins how the documents found by several searches are put together: documents
 * grouped by their code, fragments told apart by their chunk id (the same in the
 * vector store and in the full-text index), never by the code of their document.
 */
class AIDocumentsSetJoinTest {

	private static Document chunk(String id, String document) {
		return new Document(id, "text of " + id, Map.of(DocumentMetaInfos.CONTENT_CODE, document));
	}

	private static Set<String> ids(AIDocumentsSet set) {
		return set.aiDocumentsList().stream().map(Document::getId).collect(Collectors.toSet());
	}

	private static AIDocumentReferenceItem document(AIDocumentsSet set, String code) {
		return set.getDocumentItems().stream().filter(x -> x.getCode().equals(code)).findFirst().orElseThrow();
	}

	@Test
	void everyFragmentOfADocumentFoundByTwoSearchesIsKept() {
		AIDocumentsSet semantic = AIDocumentsSet
				.from(List.of(chunk("s1", "docA"), chunk("s2", "docA"), chunk("s3", "docA"), chunk("s4", "docB")));
		AIDocumentsSet lexical = AIDocumentsSet.from(List.of(chunk("l1", "docA")));

		AIDocumentsSet joined = AIDocumentsSet.join(lexical, semantic);

		assertEquals(Set.of("s1", "s2", "s3", "s4", "l1"), ids(joined));
		assertEquals(4, document(joined, "docA").countFragments());
		assertEquals(2, joined.getDocumentItems().size());
		assertEquals(ids(joined), ids(AIDocumentsSet.join(semantic, lexical)), "the order of the sets changes nothing");
	}

	@Test
	void aFragmentTwoSearchesFoundIsKeptOnce() {
		AIDocumentsSet semantic = AIDocumentsSet.from(List.of(chunk("c1", "docA"), chunk("c2", "docA")));
		AIDocumentsSet lexical = AIDocumentsSet.from(List.of(chunk("c2", "docA"), chunk("c3", "docA")));

		AIDocumentsSet joined = AIDocumentsSet.join(semantic, lexical);

		assertEquals(3, joined.countFragments());
		assertEquals(Set.of("c1", "c2", "c3"), ids(joined));
	}

	@Test
	void theSetsJoinedAreLeftAsTheyAre() {
		AIDocumentsSet first = AIDocumentsSet.from(List.of(chunk("a1", "docA")));
		AIDocumentsSet second = AIDocumentsSet.from(List.of(chunk("a2", "docA"), chunk("a3", "docA")));

		AIDocumentsSet joined = AIDocumentsSet.join(first, second);
		document(joined, "docA").getFragments().get(0).setRankIndex(99);

		assertEquals(1, first.countFragments(), "the first set did not get the fragments of the second");
		assertEquals(2, second.countFragments());
		assertEquals(3, joined.countFragments());
		assertTrue(first.getDocumentItems().get(0).getFragments().get(0).getRankIndex() == null,
				"the fragments of the result are copies");
	}

	@Test
	void aSetMadeOfChunksHoldsEachChunkOnce() {
		AIDocumentsSet set = AIDocumentsSet
				.from(List.of(chunk("c1", "docA"), chunk("c1", "docA"), chunk("c2", "docA"), chunk("c3", "docB")));

		assertEquals(3, set.countFragments());
		assertEquals(2, set.getDocumentItems().size());
		assertEquals(3, set.aiDocumentsList().size());
	}

	@Test
	void joiningReferencesOfOneDocumentKeepsEachFragmentOnceInDocumentOrder() {
		AIDocumentsSet set = AIDocumentsSet.from(List.of(chunk("c1", "docA"), chunk("c2", "docA")));
		AIDocumentsSet other = AIDocumentsSet.from(List.of(chunk("c2", "docA"), chunk("c3", "docA")));
		long position = 3;
		for (AIDocumentFragment fragment : set.getDocumentItems().get(0).getFragments()) {
			fragment.setChunkPosition(position--);
		}

		AIDocumentReferenceItem joined = AIDocumentReferenceItem.join(set.getDocumentItems().get(0),
				other.getDocumentItems().get(0));

		assertEquals(3, joined.countFragments());
		assertEquals(Set.of("c1", "c2", "c3"),
				joined.aiDocumentsList().stream().map(Document::getId).collect(Collectors.toSet()));
		assertEquals("c2", joined.getFragments().get(0).getDocumentId(), "the fragment at position 2 comes first");
	}

	@Test
	void aFragmentIsIdentifiedByItsChunkIdNotByItsDocument() {
		AIDocumentsSet set = AIDocumentsSet.from(List.of(chunk("c1", "docA"), chunk("c2", "docA")));
		AIDocumentFragment first = set.getDocumentItems().get(0).getFragments().get(0);
		AIDocumentFragment second = set.getDocumentItems().get(0).getFragments().get(1);

		assertEquals(first.getCode(), second.getCode(), "the code is the document's");
		assertFalse(first.sameFragmentAs(second));
		assertTrue(first.sameFragmentAs(first.copy()));
	}
}
