/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.fulltext.model.FullTextSearchMetaDataFilter;

/**
 * Pins the filter of the lexical (full text) leg of the hybrid retrieval: the
 * caller's filter is honored (it was replaced by a default one, so the lexical leg
 * searched every visible knowledge base and kept 3 chunks per document whatever the
 * caller asked), within the searched knowledge bases and with the user's ACL.
 */
class LexicalFilterTest {

	@Test
	void theCallersFilterIsHonoredWithinTheSearchedKnowledgeBases() {
		FullTextSearchMetaDataFilter caller = new FullTextSearchMetaDataFilter();
		caller.setKnowledgebaseCodes(List.of("chat-kb", "not-searched-kb"));
		caller.setPerDocumentInnerHits(10);
		caller.setProjectCode("project");
		caller.setAclAliases(List.of(99));

		FullTextSearchMetaDataFilter filter = GDocumentsSearchServiceImpl.lexicalFilter(caller,
				List.of("chat-kb", "other-kb"), List.of(1, 2));

		assertEquals(List.of("chat-kb"), filter.getKnowledgebaseCodes(), "the caller's ones, within the searched ones");
		assertEquals(10, filter.getPerDocumentInnerHits());
		assertEquals("project", filter.getProjectCode());
		assertEquals(List.of(1, 2), filter.getAclAliases(), "the user's ACL, never the caller's");
		assertNotSame(caller, filter);
		assertEquals(List.of("chat-kb", "not-searched-kb"), caller.getKnowledgebaseCodes(), "the caller's is untouched");
	}

	@Test
	void withoutTheCallersKnowledgeBasesTheSearchedOnesAreUsed() {
		FullTextSearchMetaDataFilter filter = GDocumentsSearchServiceImpl.lexicalFilter(new FullTextSearchMetaDataFilter(),
				List.of("a", "b"), null);
		assertEquals(List.of("a", "b"), filter.getKnowledgebaseCodes());
		assertEquals(3, filter.getPerDocumentInnerHits(), "the default");
		assertNull(filter.getAclAliases());

		assertEquals(List.of("a"), GDocumentsSearchServiceImpl.lexicalFilter(null, List.of("a"), null)
				.getKnowledgebaseCodes());
	}

	@Test
	void noSearchedKnowledgeBaseOfTheCallerMeansNoLexicalSearch() {
		FullTextSearchMetaDataFilter caller = new FullTextSearchMetaDataFilter();
		caller.setKnowledgebaseCodes(List.of("elsewhere"));

		// an empty knowledge base list would filter nothing: the leg is skipped
		assertNull(GDocumentsSearchServiceImpl.lexicalFilter(caller, List.of("a"), null));
	}
}
