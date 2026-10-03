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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.architecture.fulltext.model.FullTextSearchMetaDataFilter;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolParam.Depth;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.security.services.IGSecurityService;

/**
 * Pins how much of each document the knowledge base deep search reads: the full
 * text search keeps only the best chunks of each document, as many as the depth
 * asks, so a knowledge base of a few documents does not give a detailed report a
 * handful of fragments.
 */
class KnowledgeBaseDeepSearchToolTest {

	private static int chunksPerDocumentSearchedFor(Depth depth) throws Exception {
		IGDocumentsSearchService search = mock(IGDocumentsSearchService.class);
		AIDocumentsSet found = mock(AIDocumentsSet.class);
		when(found.aiDocumentsList()).thenReturn(List.of());
		when(search.search(anyString(), anyList(), any(), anyList(), any(), anyString(), anyInt(), anyInt()))
				.thenReturn(found);
		IGKnowledgebaseVisibilityService visibility = mock(IGKnowledgebaseVisibilityService.class);
		GKnowledgeBase knowledgeBase = new GKnowledgeBase();
		knowledgeBase.setCode("kb");
		when(visibility.allVisibleKnowledgebases()).thenReturn(List.of(knowledgeBase));
		KnowledgeBaseDeepSearchTool tool = new KnowledgeBaseDeepSearchTool(mock(DeepSearchToolsSupport.class), search,
				visibility, mock(IGSecurityService.class));

		tool.searchDocuments(List.of("query"), "question", 30, AbstractDeepSearchTool.fragmentsPerDocument(depth),
				new HashMap<>());

		ArgumentCaptor<FullTextSearchMetaDataFilter> filter = ArgumentCaptor.forClass(FullTextSearchMetaDataFilter.class);
		verify(search).search(anyString(), anyList(), any(), anyList(), filter.capture(), anyString(), anyInt(),
				anyInt());
		assertEquals(List.of("kb"), filter.getValue().getKnowledgebaseCodes());
		return filter.getValue().getPerDocumentInnerHits();
	}

	@Test
	void theDeeperTheSearchTheMoreOfEachDocumentIsRead() throws Exception {
		assertEquals(3, chunksPerDocumentSearchedFor(Depth.FOCUSED));
		assertEquals(6, chunksPerDocumentSearchedFor(Depth.BROAD));
		assertEquals(6, chunksPerDocumentSearchedFor(null));
		assertEquals(10, chunksPerDocumentSearchedFor(Depth.EXHAUSTIVE));
	}
}
