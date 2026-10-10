/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.vectorstores;

import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder.Op;

import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.EmbedType;
import lombok.experimental.UtilityClass;

/**
 * The vector store filters on what a vector embeds (see
 * {@link DocumentMetaInfos#EMBED_TYPE}).
 *
 * <p>
 * The searches of contents exclude the file name and the title vectors rather than
 * asking for the {@link EmbedType#DOCUMENT} ones: the vectors written before the
 * file name and title ones existed have no embed type, and "is missing" cannot be
 * filtered on every vector store, while "is not" matches a vector without the field
 * on all of them.
 * </p>
 */
@UtilityClass
public class EmbedTypeFilters {

	/** Only the vectors of contents, as a filter expression in text. */
	public static final String CONTENTS_ONLY = DocumentMetaInfos.EMBED_TYPE + " != '" + EmbedType.FILE_NAME.name()
			+ "' && " + DocumentMetaInfos.EMBED_TYPE + " != '" + EmbedType.TITLE.name() + "'";

	/** Only the vectors of contents. */
	public static Op contentsOnly() {
		final FilterExpressionBuilder builder = new FilterExpressionBuilder();
		return builder.and(builder.ne(DocumentMetaInfos.EMBED_TYPE, EmbedType.FILE_NAME.name()),
				builder.ne(DocumentMetaInfos.EMBED_TYPE, EmbedType.TITLE.name()));
	}

	/** The vectors of contents when the type is null, else only those of that type. */
	public static Op of(EmbedType type) {
		if (type == null || type == EmbedType.DOCUMENT) {
			return contentsOnly();
		}
		return new FilterExpressionBuilder().eq(DocumentMetaInfos.EMBED_TYPE, type.name());
	}

	/** A text filter expression also restricted to the vectors of contents. */
	public static String contentsOnly(String filterExpression) {
		return filterExpression == null || filterExpression.isBlank() ? CONTENTS_ONLY
				: "(" + filterExpression + ") && " + CONTENTS_ONLY;
	}
}
