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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.KBContext;
import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentFragment;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.llms.agent.standardtools.InternalKnowledgeBaseSearchToolSource.KnowledgeBaseSearchParam;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.security.services.IGSecurityService;

/**
 * Pins the knowledge base search tool: scoped to the chat's knowledge bases, its
 * answer bounded, and a failure answered as text so the model can go on.
 */
class InternalKnowledgeBaseSearchToolSourceTest {

	private static final IGDocumentContentRendererProvider TEXT_RENDERER = new IGDocumentContentRendererProvider() {
		@SuppressWarnings("unchecked")
		@Override
		public <T> IGDocumentContentRenderer<T> get(T doc) {
			return new IGDocumentContentRenderer<T>() {
				public String getId() {
					return "text";
				}

				public Class<T> getRenderedType() {
					return (Class<T>) Object.class;
				}

				public boolean isCanRender(Object document) {
					return true;
				}

				public String render(T document) {
					return ((Document) document).getText();
				}
			};
		}
	};

	private static String words(int count, String prefix) {
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < count; i++) {
			text.append(prefix).append(i % 97).append(' ');
		}
		return text.toString();
	}

	@SuppressWarnings("unchecked")
	private static <T> org.springframework.beans.factory.ObjectProvider<T> provider(T value) {
		org.springframework.beans.factory.ObjectProvider<T> provider = mock(org.springframework.beans.factory.ObjectProvider.class);
		when(provider.getObject()).thenReturn(value);
		return provider;
	}

	private static KnowledgeBaseSearchParam query(String text) {
		KnowledgeBaseSearchParam param = new KnowledgeBaseSearchParam();
		param.setQuery(text);
		return param;
	}

	private static KBContext chatWithKnowledgeBases(String... codes) {
		KBContext context = new KBContext();
		context.getKnowledgeBasesCodes().addAll(List.of(codes));
		return context;
	}

	@Test
	void theAnswerIsBoundedWhateverTheDocumentsFound() throws Exception {
		IGDocumentsSearchService search = mock(IGDocumentsSearchService.class);
		List<Document> found = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			found.add(Document.builder().id("d" + i).text(words(3000, "fragment" + i)).build());
		}
		AIDocumentsSet set = mock(AIDocumentsSet.class);
		when(set.aiDocumentsList()).thenReturn(found);
		when(search.search(anyString(), anyList(), any(), anyList(), any(), anyString(), anyInt(), anyInt()))
				.thenReturn(set);
		IGSecurityService security = mock(IGSecurityService.class);
		InternalKnowledgeBaseSearchToolSource tool = new InternalKnowledgeBaseSearchToolSource(provider(search),
				provider(mock(IGKnowledgebaseVisibilityService.class)), security, TEXT_RENDERER);

		String answer = tool.search(query("anthroposophy"), chatWithKnowledgeBases("kb1"));

		assertTrue(answer.startsWith("10 fragment(s) found:"), answer.substring(0, 40));
		assertTrue(ITokensCountable.stringsTokensSize(answer) <= InternalKnowledgeBaseSearchToolSource.MAX_RESULT_TOKENS
				+ 300, "answer of " + ITokensCountable.stringsTokensSize(answer));
		assertTrue(answer.contains("fragment9"), "every document keeps its share");
	}

	@Test
	void theDocumentsFoundAreSharedWithTheCallingAgent() throws Exception {
		IGDocumentsSearchService search = mock(IGDocumentsSearchService.class);
		Document fragment = Document.builder().id("f1").text("content")
				.metadata(Map.of(DocumentMetaInfos.CONTENT_CODE, "doc-a")).build();
		AIDocumentFragment aiFragment = mock(AIDocumentFragment.class);
		when(aiFragment.toAIDocument()).thenReturn(fragment);
		AIDocumentReferenceItem item = mock(AIDocumentReferenceItem.class);
		when(item.getFragments()).thenReturn(List.of(aiFragment));
		AIDocumentsSet set = mock(AIDocumentsSet.class);
		when(set.aiDocumentsList()).thenReturn(List.of(fragment));
		when(set.getDocumentItems()).thenReturn(List.of(item));
		when(search.search(anyString(), anyList(), any(), anyList(), any(), anyString(), anyInt(), anyInt()))
				.thenReturn(set);
		InternalKnowledgeBaseSearchToolSource tool = new InternalKnowledgeBaseSearchToolSource(provider(search),
				provider(mock(IGKnowledgebaseVisibilityService.class)), mock(IGSecurityService.class), TEXT_RENDERER);
		ToolsFoundDocuments collector = new ToolsFoundDocuments();

		String answer = tool.search(query("topic"), chatWithKnowledgeBases("kb1"), collector);

		assertTrue(answer.startsWith("1 fragment(s) found:"));
		assertEquals(List.of("doc-a"), collector.getDocuments().stream().map(x -> x.getDocumentCode()).toList());

		// sharing never fails the search
		when(set.getDocumentItems()).thenReturn(null);
		assertTrue(tool.search(query("topic"), chatWithKnowledgeBases("kb1"), new ToolsFoundDocuments())
				.startsWith("1 fragment(s) found:"));
	}

	@Test
	void anEmptyQueryOrNoKnowledgeBaseIsAnsweredAsText() throws Exception {
		IGKnowledgebaseVisibilityService visibility = mock(IGKnowledgebaseVisibilityService.class);
		when(visibility.allVisibleKnowledgebases()).thenReturn(List.of());
		InternalKnowledgeBaseSearchToolSource tool = new InternalKnowledgeBaseSearchToolSource(
				provider(mock(IGDocumentsSearchService.class)), provider(visibility), mock(IGSecurityService.class), TEXT_RENDERER);

		assertEquals("No search done: the query is empty.", tool.search(query(" "), null));
		assertEquals("No internal knowledge base is available to the user.", tool.search(query("topic"), null));
	}

	@Test
	void aFailingSearchIsAnsweredAsText() throws Exception {
		IGDocumentsSearchService search = mock(IGDocumentsSearchService.class);
		when(search.search(anyString(), anyList(), any(), anyList(), any(), anyString(), anyInt(), anyInt()))
				.thenThrow(new IllegalStateException("vector store down"));
		InternalKnowledgeBaseSearchToolSource tool = new InternalKnowledgeBaseSearchToolSource(provider(search),
				provider(mock(IGKnowledgebaseVisibilityService.class)), mock(IGSecurityService.class), TEXT_RENDERER);

		assertEquals("The internal knowledge base search failed, go on without it.",
				tool.search(query("topic"), chatWithKnowledgeBases("kb1")));
	}

	@Test
	void theToolIsDeclaredForTheModels() {
		InternalKnowledgeBaseSearchToolSource tool = new InternalKnowledgeBaseSearchToolSource(
				provider(mock(IGDocumentsSearchService.class)), provider(mock(IGKnowledgebaseVisibilityService.class)),
				mock(IGSecurityService.class), TEXT_RENDERER);

		assertEquals(1, tool.getToolCallbacks().size());
		assertEquals(InternalKnowledgeBaseSearchToolSource.SEARCH_KNOWLEDGE_BASE_TOOL,
				tool.getToolCallbacks().get(0).getToolDefinition().name());
		assertTrue(tool.getToolCallbacks().get(0).getToolDefinition().inputSchema().contains("query"));
	}
}
