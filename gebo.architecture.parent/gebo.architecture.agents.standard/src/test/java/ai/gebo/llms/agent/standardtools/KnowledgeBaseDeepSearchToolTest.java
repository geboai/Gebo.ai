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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ai.chat.model.ToolContext;

import ai.gebo.llms.agent.standardtools.model.DeepSearchToolParam;
import ai.gebo.llms.agent.standardtools.model.KnowledgeBaseDeepSearchToolParam;
import ai.gebo.architecture.fulltext.model.FullTextSearchMetaDataFilter;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolParam.Depth;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;

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
		KnowledgeBaseDeepSearchTool tool = new KnowledgeBaseDeepSearchTool(mock(DeepSearchToolsSupport.class), search,
				mock(IGSecurityService.class));

		tool.searchDocuments(new DeepSearchToolParam<>(), List.of("query"), "question", 30,
				AbstractDeepSearchTool.fragmentsPerDocument(depth), new HashMap<>(), chat("kb"), new ArrayList<>());

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

	/** The context of a call for a chat whose knowledge bases are these. */
	private static ToolContext chat(String... knowledgeBases) {
		return new ToolContext(
				Map.of(ToolCallbackDeclarationUtil.CHAT_KNOWLEDGE_BASES_CONTEXT_KEY, List.of(knowledgeBases)));
	}

	private static KnowledgeBaseDeepSearchTool toolOn(IGDocumentsSearchService search, DeepSearchToolsSupport support) {
		return new KnowledgeBaseDeepSearchTool(support, search, mock(IGSecurityService.class));
	}

	@Test
	void aChatWithoutKnowledgeBasesSearchesNone() throws Exception {
		IGDocumentsSearchService search = mock(IGDocumentsSearchService.class);
		KnowledgeBaseDeepSearchTool tool = toolOn(search, mock(DeepSearchToolsSupport.class));

		assertTrue(tool.searchDocuments(new DeepSearchToolParam<>(), List.of("query"), "question", 10, 3,
				new HashMap<>(), new ToolContext(Map.of()), new ArrayList<>()).isEmpty());
		assertTrue(tool.searchDocuments(new DeepSearchToolParam<>(), List.of("query"), "question", 10, 3,
				new HashMap<>(), chat(), new ArrayList<>()).isEmpty());
		// without the context of a call there is no chat either
		assertTrue(tool.searchDocuments(List.of("query"), "question", 10, 3, new HashMap<>()).isEmpty());
		verify(search, never()).search(anyString(), anyList(), any(), anyList(), any(), anyString(), anyInt(),
				anyInt());
	}

	@Test
	void theKeywordsAreDeclaredOnlyWithAFullTextLeg() {
		DeepSearchToolsSupport support = mock(DeepSearchToolsSupport.class);
		KnowledgeBaseDeepSearchTool tool = toolOn(mock(IGDocumentsSearchService.class), support);
		when(support.knowledgeBaseKeywordsEnabled()).thenReturn(false);
		assertFalse(tool.toTool().getToolDefinition().inputSchema().contains("keywords"));

		when(support.knowledgeBaseKeywordsEnabled()).thenReturn(true);
		String schema = tool.toTool().getToolDefinition().inputSchema();
		assertTrue(schema.contains("\"keywords\""), schema);
		assertTrue(schema.contains("\"queries\""), schema);
	}

	@SuppressWarnings("unchecked")
	@Test
	void theFullTextLegSearchesTheKeywordsElseTheQueries() throws Exception {
		IGDocumentsSearchService search = mock(IGDocumentsSearchService.class);
		AIDocumentsSet found = mock(AIDocumentsSet.class);
		when(found.aiDocumentsList()).thenReturn(List.of());
		when(search.search(anyString(), anyList(), any(), anyList(), any(), anyString(), anyInt(), anyInt()))
				.thenReturn(found);
		KnowledgeBaseDeepSearchTool tool = toolOn(search, mock(DeepSearchToolsSupport.class));
		KnowledgeBaseDeepSearchToolParam withKeywords = new KnowledgeBaseDeepSearchToolParam();
		withKeywords.setKeywords(List.of("Fohat"));
		DeepSearchToolParam<String> plain = new DeepSearchToolParam<>();

		tool.searchDocuments(withKeywords, List.of("cosmic electricity"), "question", 10, 3, new HashMap<>(), chat("kb"), new ArrayList<>());
		tool.searchDocuments(plain, List.of("cosmic electricity"), "question", 10, 3, new HashMap<>(), chat("kb"), new ArrayList<>());

		ArgumentCaptor<List<String>> semantic = ArgumentCaptor.forClass(List.class);
		ArgumentCaptor<List<String>> fullText = ArgumentCaptor.forClass(List.class);
		verify(search, org.mockito.Mockito.times(2)).search(anyString(), semantic.capture(), any(), fullText.capture(),
				any(), anyString(), anyInt(), anyInt());
		assertEquals(List.of("cosmic electricity"), semantic.getAllValues().get(0));
		assertEquals(List.of("Fohat"), fullText.getAllValues().get(0));
		assertEquals(List.of("cosmic electricity"), fullText.getAllValues().get(1));
	}
}
