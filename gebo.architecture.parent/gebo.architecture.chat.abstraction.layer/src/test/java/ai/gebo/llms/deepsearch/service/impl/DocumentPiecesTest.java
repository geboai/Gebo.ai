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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetCalculator;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.model.DocumentMetaInfos;

/**
 * Pins how a document read whole is analysed: in pieces sized on the room of the
 * analysis, each weighing its own size in a batch's budget.
 */
class DocumentPiecesTest {

	private static Document book(String text) {
		final Map<String, Object> metadata = new HashMap<>();
		metadata.put(DocumentMetaInfos.CONTENT_CODE, "kb/scienza-occulta.pdf");
		metadata.put(DocumentMetaInfos.GEBO_FILE_NAME, "scienza-occulta.pdf");
		// the documents cache gives the whole document's size
		metadata.put(DocumentMetaInfos.GEBO_TOKEN_LENGTH, ITokensCountable.stringsTokensSize(text));
		return new Document("the-book", text, metadata);
	}

	@Test
	void aDocumentLongerThanAPieceIsSplitEachPieceWeighingItsOwnSize() {
		final String text = "Il Guardiano della Soglia si presenta all'anima quando essa varca il confine. ".repeat(3000);
		final int pieceTokens = 4000;

		final List<Document> pieces = DocumentPieces.of(book(text), pieceTokens);

		assertTrue(pieces.size() > 1);
		long total = 0;
		for (Document piece : pieces) {
			final int tokens = ((Number) piece.getMetadata().get(DocumentMetaInfos.GEBO_TOKEN_LENGTH)).intValue();
			assertEquals(ITokensCountable.stringsTokensSize(piece.getText()), tokens, "its own size, not the book's");
			assertTrue(tokens <= pieceTokens + 2, "a piece holds in its size: " + tokens);
			assertEquals("kb/scienza-occulta.pdf", piece.getMetadata().get(DocumentMetaInfos.CONTENT_CODE),
					"the document's metadata");
			total += tokens;
		}
		assertTrue(!TokensBudgetCalculator.higherThanBudget(List.of(pieces.get(0)), pieceTokens * 2l),
				"a piece is weighed by its own size in a batch's budget");
		assertTrue(total >= ITokensCountable.stringsTokensSize(text) * 9 / 10, "the whole text is kept");
	}

	@Test
	void aDocumentHoldingInAPieceIsLeftAsItIs() {
		final Document small = book("Fohat is the steed and Thought is the rider.");

		assertSame(small, DocumentPieces.of(small, 4000).get(0));
	}

	@Test
	void aPieceFillsItsShareOfABatch() {
		final DeepSearchDefaultConfig config = new DeepSearchDefaultConfig();

		assertEquals(15500, config.chunkTokens(62000), "the filling factor: a quarter of a batch");

		config.setChunkFillingFactor(0d);
		assertEquals(15500, config.chunkTokens(62000), "a factor out of (0, 1) falls back to its default");
		assertEquals(1, config.chunkTokens(-5), "never below 1");
	}
}
