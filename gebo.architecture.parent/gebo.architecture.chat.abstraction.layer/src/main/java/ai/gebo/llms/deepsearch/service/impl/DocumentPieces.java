/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.deepsearch.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.documents.cache.model.TextChunkingSpecs;
import ai.gebo.model.DocumentMetaInfos;

/**
 * The pieces a document read whole is analysed in: a document selected for the chat
 * comes as one text, which no analysis batch may hold when the document is longer than
 * the batch. It is split with the token splitter the documents chunker uses, each piece
 * carrying the document's metadata and its own size (the document's would make every
 * piece weigh as the whole document in a batch's budget).
 */
final class DocumentPieces {
	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentPieces.class);

	private DocumentPieces() {
	}

	/** The document when it holds in {@code pieceTokens}, else its pieces of {@code pieceTokens} at most. */
	static List<Document> of(Document document, int pieceTokens) {
		if (document == null) {
			return List.of();
		}
		if (!document.isText() || document.getText() == null) {
			return List.of(document);
		}
		final int tokens = ITokensCountable.stringsTokensSize(document.getText());
		if (tokens <= pieceTokens) {
			return List.of(document);
		}
		final TokenTextSplitter splitter = TokenTextSplitter.builder().withChunkSize(Math.max(1, pieceTokens))
				.withMinChunkSizeChars(TextChunkingSpecs.MIN_CHUNKS_SIZE_CHARS)
				.withMinChunkLengthToEmbed(TextChunkingSpecs.MIN_CHUNKS_LENGTH_TO_EMBED)
				.withMaxNumChunks(TextChunkingSpecs.MAX_CHUNKS_NUMBERS).withKeepSeparator(true).build();
		final List<Document> split = splitter.split(document);
		final List<Document> pieces = new ArrayList<>(split.size());
		for (Document piece : split) {
			final String text = piece.getText() != null ? piece.getText() : "";
			final Map<String, Object> metadata = document.getMetadata() != null
					? new HashMap<>(document.getMetadata())
					: new HashMap<>();
			metadata.put(DocumentMetaInfos.GEBO_TOKEN_LENGTH, ITokensCountable.stringsTokensSize(text));
			metadata.put(DocumentMetaInfos.GEBO_BYTES_LENGTH, text.getBytes(StandardCharsets.UTF_8).length);
			pieces.add(new Document(piece.getId(), text, metadata));
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("of(...) document:" + document.getId() + " of " + tokens + " (tok) split in " + pieces.size()
					+ " piece(s) of " + pieceTokens + " (tok) at most");
		}
		return pieces;
	}
}
