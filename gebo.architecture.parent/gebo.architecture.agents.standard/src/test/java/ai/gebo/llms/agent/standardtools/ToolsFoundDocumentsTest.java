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
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;

/**
 * Pins the short ids of the documents of a request: one per document, in the order
 * they came, the same across the tools, and the way back from the ids an answer gives.
 */
class ToolsFoundDocumentsTest {

	private static GResponseDocumentRef document(String code) {
		GResponseDocumentRef ref = new GResponseDocumentRef();
		ref.setDocumentCode(code);
		ref.setName(code + ".pdf");
		return ref;
	}

	@Test
	void eachDocumentOfTheRequestGetsTheNextShortIdOnce() {
		ToolsFoundDocuments collector = new ToolsFoundDocuments();

		collector.add(List.of(document("a"), document("b")));
		collector.add(List.of(document("b"), document("c")));

		assertEquals("#1", collector.idOf("a"));
		assertEquals("#2", collector.idOf("b"), "the same document keeps its id across the tools");
		assertEquals("#3", collector.idOf("c"));
		assertNull(collector.idOf("never-returned"));
		assertNull(collector.idOf(null));
	}

	@Test
	void theIdsTheAnswerGivesLeadBackToItsDocuments() {
		ToolsFoundDocuments collector = new ToolsFoundDocuments();
		collector.add(List.of(document("a"), document("b"), document("c")));
		assertNull(collector.getAnswerDocumentIds(), "no marker given yet");

		collector.addAnswerDocumentIds(List.of("#3", "#1"));
		collector.addAnswerDocumentIds(List.of("#1", "#9"));
		List<String> unknown = new ArrayList<>();

		assertEquals(List.of("#3", "#1", "#9"), collector.getAnswerDocumentIds());
		assertEquals(List.of("c", "a"), collector.documentsOf(collector.getAnswerDocumentIds(), unknown).stream()
				.map(GResponseDocumentRef::getDocumentCode).toList());
		assertEquals(List.of("#9"), unknown);
	}
}
