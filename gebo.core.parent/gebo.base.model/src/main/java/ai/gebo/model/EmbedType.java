/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.model;

/**
 * What a vector of a document embeds, in its {@link DocumentMetaInfos#EMBED_TYPE}
 * metadata. Every vector of a document carries the same metadata but this one.
 */
public enum EmbedType {
	/** A part of the document's contents: what the semantic searches of contents find. */
	DOCUMENT,
	/** The document's file name, as its content source names it. */
	FILE_NAME,
	/** The document's title, as its file tells it (see {@link DocumentMetaInfos#TITLE}). */
	TITLE,
	/**
	 * The document's author, as its content source or its file tells it (see
	 * {@link DocumentMetaInfos#AUTHOR}).
	 */
	AUTHOR
}
