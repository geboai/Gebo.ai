/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ragsystem.content.vectorizator.impl;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.EmbedType;
import lombok.experimental.UtilityClass;

/**
 * The vectors embedding a document's file name, its title and its author, beside the
 * vectors of its contents, so the document can be found by them. A document without
 * title or author has no vector of them.
 *
 * <p>
 * They carry the metadata of the contents' vectors (knowledge base, project,
 * endpoint, code, uniqueId, access rights, file name, title, custom metadata...),
 * so every filter on a document applies to them alike, but for
 * {@link DocumentMetaInfos#EMBED_TYPE} and the fields telling a part of the contents:
 * its position and page are dropped, its token and byte lengths are those of the
 * text embedded. Their ids are new random UUIDs, as the vector stores need.
 * </p>
 */
@UtilityClass
public class DocumentIdentityVectors {
	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentIdentityVectors.class);

	/** The fields of the metadata telling a part of the contents, not the document. */
	private static final List<String> CONTENT_PART_FIELDS = List.of(DocumentMetaInfos.GEBO_CHUNK_POSITION,
			DocumentMetaInfos.CONTENT_PAGE);

	/** The file name, the title and the author vectors of a document, each null when there is none. */
	public record IdentityVectors(Document fileName, Document title, Document author) {
		public static final IdentityVectors NONE = new IdentityVectors(null, null, null);

		/** The vectors there are. */
		public List<Document> all() {
			final List<Document> all = new java.util.ArrayList<>();
			for (Document vector : new Document[] { fileName, title, author }) {
				if (vector != null) {
					all.add(vector);
				}
			}
			return all;
		}
	}

	/**
	 * The vectors of the file name, of the title and of the author of the document
	 * whose contents' vectors are given: none without contents, whose metadata they
	 * copy; the title and the author ones only when the contents have them.
	 *
	 * @param fileName the document's name, as its content source names it; the
	 *                 {@link DocumentMetaInfos#GEBO_FILE_NAME} of the contents when
	 *                 null
	 */
	public static IdentityVectors of(String fileName, List<Document> contents) {
		final Document first = contents != null && !contents.isEmpty() ? contents.get(0) : null;
		if (first == null || first.getMetadata() == null) {
			return IdentityVectors.NONE;
		}
		final Object code = first.getMetadata().get(DocumentMetaInfos.CONTENT_CODE);
		final String name = text(fileName != null ? fileName : first.getMetadata().get(DocumentMetaInfos.GEBO_FILE_NAME));
		final String title = firstOf(contents, DocumentMetaInfos.TITLE);
		final String author = firstOf(contents, DocumentMetaInfos.AUTHOR);
		final IdentityVectors vectors = new IdentityVectors(vector(first.getMetadata(), EmbedType.FILE_NAME, name),
				vector(first.getMetadata(), EmbedType.TITLE, title),
				vector(first.getMetadata(), EmbedType.AUTHOR, author));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("of(...) code:" + code + " file name vector:" + (vectors.fileName() != null)
					+ " title vector:" + (vectors.title() != null) + " author vector:" + (vectors.author() != null));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("of(...) code:" + code + " file name:" + name + " title:" + title + " author:" + author);
		}
		return vectors;
	}

	/**
	 * The vectors of the file name, of the title and of the author of a document
	 * vectorized before they existed, from the metadata of one vector of its contents:
	 * its {@link DocumentMetaInfos#GEBO_FILE_NAME}, {@link DocumentMetaInfos#TITLE} and
	 * {@link DocumentMetaInfos#AUTHOR}.
	 */
	public static IdentityVectors ofContentMetadata(Map<String, Object> contentMetadata) {
		if (contentMetadata == null) {
			return IdentityVectors.NONE;
		}
		final Map<String, Object> metadata = new HashMap<>(contentMetadata);
		// a vector of the contents, whatever its store gave back about what it embeds
		metadata.remove(DocumentMetaInfos.EMBED_TYPE);
		final String name = text(metadata.get(DocumentMetaInfos.GEBO_FILE_NAME));
		final String title = text(metadata.get(DocumentMetaInfos.TITLE));
		final String author = text(metadata.get(DocumentMetaInfos.AUTHOR));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("ofContentMetadata(...) code:" + metadata.get(DocumentMetaInfos.CONTENT_CODE)
					+ " file name:" + (name != null) + " title:" + (title != null) + " author:" + (author != null));
		}
		return new IdentityVectors(vector(metadata, EmbedType.FILE_NAME, name), vector(metadata, EmbedType.TITLE, title),
				vector(metadata, EmbedType.AUTHOR, author));
	}

	/**
	 * A vector embedding the text, with the metadata of a vector of the contents but
	 * for the embed type and the fields telling a part of the contents; null without
	 * text.
	 */
	public static Document vector(Map<String, Object> contentMetadata, EmbedType type, String text) {
		if (text == null || contentMetadata == null) {
			return null;
		}
		final Map<String, Object> metadata = new HashMap<>(contentMetadata);
		CONTENT_PART_FIELDS.forEach(metadata::remove);
		metadata.put(DocumentMetaInfos.EMBED_TYPE, type.name());
		metadata.put(DocumentMetaInfos.GEBO_TOKEN_LENGTH, ITokensCountable.stringsTokensSize(text));
		metadata.put(DocumentMetaInfos.GEBO_BYTES_LENGTH, text.getBytes(StandardCharsets.UTF_8).length);
		return new Document(UUID.randomUUID().toString(), text, metadata);
	}

	/** Marks the vectors of the contents as such. */
	public static void markContents(List<Document> contents) {
		if (contents == null) {
			return;
		}
		for (Document content : contents) {
			if (content.getMetadata() != null) {
				content.getMetadata().put(DocumentMetaInfos.EMBED_TYPE, EmbedType.DOCUMENT.name());
			}
		}
	}

	/** The first value of the field among the contents' vectors, null when none has one. */
	private static String firstOf(List<Document> contents, String field) {
		for (Document content : contents) {
			final String value = content.getMetadata() != null ? text(content.getMetadata().get(field)) : null;
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	/** The text as it is, null when blank. */
	private static String text(Object value) {
		if (value == null) {
			return null;
		}
		final String text = value.toString();
		return text.isBlank() ? null : text;
	}
}
