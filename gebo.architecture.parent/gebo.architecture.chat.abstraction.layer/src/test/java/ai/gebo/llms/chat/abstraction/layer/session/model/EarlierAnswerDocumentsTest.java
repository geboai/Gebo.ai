/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.session.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef.DocInternalRef;

/**
 * Pins the documents an answer rested on, kept with the chat's history: without their
 * fragments, and named after the answer for the model.
 */
class EarlierAnswerDocumentsTest {

	private static GResponseDocumentRef ref(String name) {
		final GResponseDocumentRef ref = new GResponseDocumentRef();
		ref.setName(name);
		ref.setDocumentCode("code-" + name);
		ref.getReferences().add(new DocInternalRef());
		return ref;
	}

	@Test
	void theDocumentsAreKeptWithoutTheirFragments() {
		final List<GResponseDocumentRef> kept = CSSSimplefiedInteraction
				.keptDocuments(List.of(ref("The-Secret-Doctrine-1-of-4.pdf")));

		assertEquals(1, kept.size());
		assertEquals("The-Secret-Doctrine-1-of-4.pdf", kept.get(0).getName());
		assertEquals("code-The-Secret-Doctrine-1-of-4.pdf", kept.get(0).getDocumentCode());
		assertTrue(kept.get(0).getReferences().isEmpty());
		assertNull(CSSSimplefiedInteraction.keptDocuments(List.of()));
		assertNull(CSSSimplefiedInteraction.keptDocuments(null));
	}

	@Test
	void theDocumentsFollowTheAnswerForTheModel() {
		final CSSSimplefiedInteraction interaction = new CSSSimplefiedInteraction();
		interaction.setDocumentsRef(CSSSimplefiedInteraction.keptDocuments(List.of(ref("a.pdf"), ref("b.pdf"))));

		assertEquals("\n\n" + CSSSimplefiedInteraction.DOCUMENTS_NOTE_HEAD + "a.pdf; b.pdf]",
				interaction.documentsNote());
		assertEquals("", new CSSSimplefiedInteraction().documentsNote(), "an interaction saved before: no note");
	}
}
