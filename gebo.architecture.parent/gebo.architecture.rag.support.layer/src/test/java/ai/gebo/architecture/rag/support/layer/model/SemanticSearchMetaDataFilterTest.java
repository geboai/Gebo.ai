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

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.ai.vectorstore.filter.FilterExpressionTextParser;

import ai.gebo.llms.abstraction.layer.vectorstores.EmbedTypeFilters;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.EmbedType;

/**
 * Pins that the semantic searches see the vectors of the contents only - those
 * marked as such and those written before the marking, which have no embed type -
 * unless they search file names or titles.
 */
class SemanticSearchMetaDataFilterTest {

	@Test
	void withoutConditionsTheContentsOnlyAreSearched() {
		assertEquals(EmbedTypeFilters.contentsOnly().build(), new SemanticSearchMetaDataFilter().build());
	}

	@Test
	void theConditionsAreAndedWithTheContentsOnly() {
		SemanticSearchMetaDataFilter filter = new SemanticSearchMetaDataFilter();
		filter.setKnowledgeBasesCodes(List.of("kb1"));
		filter.setAclAliases(List.of(3));
		FilterExpressionBuilder b = new FilterExpressionBuilder();
		Filter.Expression expected = b.and(
				b.and(b.in(DocumentMetaInfos.GEBO_ACL_ALIASES, List.of(3)), b.in(DocumentMetaInfos.KNOWLEDGEBASE_CODE, List.of("kb1"))),
				EmbedTypeFilters.contentsOnly()).build();
		assertEquals(expected, filter.build());
	}

	@Test
	void fileNamesOrTitlesAreSearchedOnlyWhenAsked() {
		SemanticSearchMetaDataFilter filter = new SemanticSearchMetaDataFilter();
		filter.setKnowledgeBasesCodes(List.of("kb1"));
		filter.setEmbedType(EmbedType.TITLE);
		FilterExpressionBuilder b = new FilterExpressionBuilder();
		assertEquals(b.and(b.in(DocumentMetaInfos.KNOWLEDGEBASE_CODE, List.of("kb1")),
				b.eq(DocumentMetaInfos.EMBED_TYPE, "TITLE")).build(), filter.build());
		filter.setEmbedType(EmbedType.DOCUMENT);
		assertEquals(b.and(b.in(DocumentMetaInfos.KNOWLEDGEBASE_CODE, List.of("kb1")), EmbedTypeFilters.contentsOnly())
				.build(), filter.build());
	}

	@Test
	void theTextFilterIsTheSameAsTheBuiltOne() {
		FilterExpressionTextParser parser = new FilterExpressionTextParser();
		assertEquals(EmbedTypeFilters.contentsOnly().build(), parser.parse(EmbedTypeFilters.CONTENTS_ONLY));
		FilterExpressionBuilder b = new FilterExpressionBuilder();
		// the condition given, in parentheses, then the contents only
		assertEquals(
				b.and(b.and(b.and(b.group(b.gt(DocumentMetaInfos.GEBO_TOKEN_LENGTH, 10)),
						b.ne(DocumentMetaInfos.EMBED_TYPE, "FILE_NAME")), b.ne(DocumentMetaInfos.EMBED_TYPE, "TITLE")),
						b.ne(DocumentMetaInfos.EMBED_TYPE, "AUTHOR")).build(),
				parser.parse(EmbedTypeFilters.contentsOnly(DocumentMetaInfos.GEBO_TOKEN_LENGTH + " > 10")));
	}
}
