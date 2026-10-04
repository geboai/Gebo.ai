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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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
import ai.gebo.architecture.ai.service.ToolsTokenBudget;
import ai.gebo.llms.agent.standard.config.StandardAgentsConfig;
import ai.gebo.llms.agent.standardtools.InternalKnowledgeBaseSearchToolSource.KnowledgeBaseSearchParam;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRankerService;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.security.services.IGSecurityService;

/**
 * Pins the knowledge base search tool: scoped to the chat's knowledge bases, its
 * answer sized on the room its model call leaves to the tools, and a failure answered as text so the model can go on.
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
	void theAnswerNamesTheDocumentsItsFragmentsComeFrom() {
		List<Document> fragments = List.of(
				Document.builder().id("f1").text("one").metadata(Map.of(DocumentMetaInfos.GEBO_FILE_NAME, "a.pdf")).build(),
				Document.builder().id("f2").text("two").metadata(Map.of(DocumentMetaInfos.GEBO_FILE_NAME, "b.pdf")).build(),
				Document.builder().id("f3").text("three").metadata(Map.of(DocumentMetaInfos.GEBO_FILE_NAME, "a.pdf")).build(),
				Document.builder().id("f4").text("four").metadata(Map.of(DocumentMetaInfos.CONTENT_CODE, "kb/c.txt")).build());

		assertEquals("Documents of these fragments (the only ones this search found): a.pdf (2), b.pdf (1), kb/c.txt (1).",
				InternalKnowledgeBaseSearchToolSource.documentsLine(fragments));
	}

	private static IGDocumentsSearchService searchFinding(int documents, int wordsEach) throws Exception {
		IGDocumentsSearchService search = mock(IGDocumentsSearchService.class);
		List<Document> found = new ArrayList<>();
		for (int i = 0; i < documents; i++) {
			found.add(Document.builder().id("d" + i).text(words(wordsEach, "fragment" + i)).build());
		}
		AIDocumentsSet set = mock(AIDocumentsSet.class);
		when(set.aiDocumentsList()).thenReturn(found);
		when(search.search(anyString(), anyList(), any(), anyList(), any(), anyString(), anyInt(), anyInt()))
				.thenReturn(set);
		return search;
	}

	@SuppressWarnings("unchecked")
	private static org.springframework.beans.factory.ObjectProvider<StandardAgentsConfig> configured(double divisor) {
		StandardAgentsConfig config = new StandardAgentsConfig();
		config.setKnowledgeBaseSearchRoomDivisor(divisor);
		org.springframework.beans.factory.ObjectProvider<StandardAgentsConfig> provider = mock(
				org.springframework.beans.factory.ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(config);
		return provider;
	}

	private static InternalKnowledgeBaseSearchToolSource tool(IGDocumentsSearchService search,
			org.springframework.beans.factory.ObjectProvider<StandardAgentsConfig> config) {
		return new InternalKnowledgeBaseSearchToolSource(provider(search),
				provider(mock(IGKnowledgebaseVisibilityService.class)), mock(IGSecurityService.class), TEXT_RENDERER,
				config, null);
	}

	@SuppressWarnings("unchecked")
	private static InternalKnowledgeBaseSearchToolSource rankingTool(IGDocumentsSearchService search,
			IGRankerService ranker) {
		org.springframework.beans.factory.ObjectProvider<IGRankerService> rankers = mock(
				org.springframework.beans.factory.ObjectProvider.class);
		when(rankers.getIfAvailable()).thenReturn(ranker);
		return new InternalKnowledgeBaseSearchToolSource(provider(search),
				provider(mock(IGKnowledgebaseVisibilityService.class)), mock(IGSecurityService.class), TEXT_RENDERER, null,
				rankers);
	}

	/** Each fragment its own document, as the search returns them. */
	private static IGDocumentsSearchService searchFindingDocuments(int documents) throws Exception {
		IGDocumentsSearchService search = mock(IGDocumentsSearchService.class);
		List<Document> found = new ArrayList<>();
		for (int i = 0; i < documents; i++) {
			found.add(Document.builder().id("d" + i).text("fragment" + i)
					.metadata(Map.of(DocumentMetaInfos.CONTENT_CODE, "doc-" + i)).build());
		}
		AIDocumentsSet set = mock(AIDocumentsSet.class);
		when(set.aiDocumentsList()).thenReturn(found);
		when(search.search(anyString(), anyList(), any(), anyList(), any(), anyString(), anyInt(), anyInt()))
				.thenReturn(set);
		return search;
	}

	@Test
	void aConfiguredRankerKeepsTheBestTopKOfTwiceTheFragmentsRetrieved() throws Exception {
		IGDocumentsSearchService search = searchFindingDocuments(20);
		IGRankerService ranker = mock(IGRankerService.class);
		when(ranker.isRankerConfigured()).thenReturn(true);
		// the ranker puts the last retrieved first
		when(ranker.rank(anyList(), anyString(), anyInt())).thenAnswer(invocation -> {
			List<Document> ranked = new ArrayList<>(invocation.<List<Document>>getArgument(0));
			java.util.Collections.reverse(ranked);
			return ranked.subList(0, invocation.<Integer>getArgument(2));
		});
		ToolsFoundDocuments collector = new ToolsFoundDocuments();

		String answer = rankingTool(search, ranker).search(query("anthroposophy"), chatWithKnowledgeBases("kb1"),
				collector, new ToolsTokenBudget(30000));

		verify(search).search(anyString(), anyList(), any(), anyList(), any(), anyString(), eq(20), eq(40000));
		verify(ranker).rank(anyList(), eq("anthroposophy"), eq(10));
		verify(ranker, never()).rankAndRemoveIrrelevant(anyList(), anyString(), anyInt());
		assertTrue(answer.startsWith("10 fragment(s) found:"), answer.substring(0, 40));
		assertTrue(answer.indexOf("fragment19") < answer.indexOf("fragment18"), "best ranked first");
		assertTrue(answer.contains("doc-10 (1)") && !answer.contains("doc-9 ("),
				"the fragments the ranker left out are not returned");
		assertEquals(10, collector.getDocuments().size(), "only the ranked documents are the answer's");
	}

	@Test
	void noRankerConfiguredRetrievesTheFragmentsAskedOnly() throws Exception {
		IGDocumentsSearchService search = searchFindingDocuments(10);
		IGRankerService ranker = mock(IGRankerService.class);
		when(ranker.isRankerConfigured()).thenReturn(false);

		String answer = rankingTool(search, ranker).search(query("anthroposophy"), chatWithKnowledgeBases("kb1"));

		verify(search).search(anyString(), anyList(), any(), anyList(), any(), anyString(), eq(10),
				eq(Integer.MAX_VALUE));
		verify(ranker, never()).rank(anyList(), anyString(), anyInt());
		assertTrue(answer.startsWith("10 fragment(s) found:"));
	}

	@Test
	void aFailingRankerKeepsTheFirstTopKInRetrievalOrder() throws Exception {
		IGDocumentsSearchService search = searchFindingDocuments(20);
		IGRankerService ranker = mock(IGRankerService.class);
		when(ranker.isRankerConfigured()).thenReturn(true);
		when(ranker.rank(anyList(), anyString(), anyInt())).thenThrow(new IllegalStateException("ranker down"));

		String answer = rankingTool(search, ranker).search(query("anthroposophy"), chatWithKnowledgeBases("kb1"));

		assertTrue(answer.startsWith("10 fragment(s) found:"), answer.substring(0, 40));
		assertTrue(answer.contains("fragment9") && !answer.contains("fragment10"), "the first 10 retrieved");
	}

	@Test
	void theAnswerTakesAThirdOfTheRoomLeftByDefaultAndConsumesIt() throws Exception {
		IGDocumentsSearchService search = searchFinding(10, 3000);
		ToolsTokenBudget room = new ToolsTokenBudget(30000);

		String answer = tool(search, null).search(query("anthroposophy"), chatWithKnowledgeBases("kb1"), null, room);

		int answerTokens = ITokensCountable.stringsTokensSize(answer);
		assertTrue(answer.startsWith("10 fragment(s) found:"), answer.substring(0, 40));
		assertTrue(answerTokens <= 10000, "answer of " + answerTokens);
		assertTrue(answerTokens > 9000, "the room is used, answer of " + answerTokens);
		assertTrue(answer.contains("fragment9"), "every document keeps its share");
		assertEquals(30000, room.left(), "the tool wrapper takes the answer out of the room, not the tool");
		verify(search).search(anyString(), anyList(), any(), anyList(), any(), anyString(), eq(10), eq(20000));
	}

	@Test
	void theConfiguredDivisorSizesTheAnswer() throws Exception {
		IGDocumentsSearchService search = searchFinding(10, 3000);

		String answer = tool(search, configured(6.0d)).search(query("anthroposophy"), chatWithKnowledgeBases("kb1"),
				null, new ToolsTokenBudget(30000));

		assertTrue(ITokensCountable.stringsTokensSize(answer) <= 5000,
				"answer of " + ITokensCountable.stringsTokensSize(answer));
		verify(search).search(anyString(), anyList(), any(), anyList(), any(), anyString(), eq(10), eq(10000));
		assertEquals(InternalKnowledgeBaseSearchToolSource.DEFAULT_ROOM_DIVISOR,
				tool(search, configured(0d)).roomDivisor(), "a divisor not positive is not used");
		assertEquals(InternalKnowledgeBaseSearchToolSource.DEFAULT_ROOM_DIVISOR,
				tool(search, configured(Double.NaN)).roomDivisor());
		assertEquals(1000, tool(search, configured(0.5d)).maxResultTokens(new ToolsTokenBudget(1000)),
				"never more than the room");
	}

	@Test
	void noRoomSharedBoundsTheAnswerByTheFragmentsAskedOnly() throws Exception {
		IGDocumentsSearchService search = searchFinding(10, 3000);

		String answer = tool(search, null).search(query("anthroposophy"), chatWithKnowledgeBases("kb1"));

		assertTrue(ITokensCountable.stringsTokensSize(answer) > 10 * ITokensCountable.stringsTokensSize(words(2990, "fragment0")),
				"the fragments are returned whole");
		verify(search).search(anyString(), anyList(), any(), anyList(), any(), anyString(), eq(10),
				eq(Integer.MAX_VALUE));
	}

	@Test
	void tooLittleRoomRunsNoSearch() throws Exception {
		IGDocumentsSearchService search = searchFinding(10, 3000);
		ToolsTokenBudget room = new ToolsTokenBudget(1200);

		assertEquals("No room is left in the context for more contents: answer with the contents already found.",
				tool(search, null).search(query("anthroposophy"), chatWithKnowledgeBases("kb1"), null, room));
		verify(search, never()).search(anyString(), anyList(), any(), anyList(), any(), anyString(), anyInt(),
				anyInt());
		assertEquals(1200, room.left());
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
				provider(mock(IGKnowledgebaseVisibilityService.class)), mock(IGSecurityService.class), TEXT_RENDERER, null, null);
		ToolsFoundDocuments collector = new ToolsFoundDocuments();

		String answer = tool.search(query("topic"), chatWithKnowledgeBases("kb1"), collector, null);

		assertTrue(answer.startsWith("1 fragment(s) found:"));
		assertEquals(List.of("doc-a"), collector.getDocuments().stream().map(x -> x.getDocumentCode()).toList());

		// sharing never fails the search
		when(set.getDocumentItems()).thenReturn(null);
		assertTrue(tool.search(query("topic"), chatWithKnowledgeBases("kb1"), new ToolsFoundDocuments(), null)
				.startsWith("1 fragment(s) found:"));
	}

	@Test
	void anEmptyQueryOrNoKnowledgeBaseIsAnsweredAsText() throws Exception {
		IGKnowledgebaseVisibilityService visibility = mock(IGKnowledgebaseVisibilityService.class);
		when(visibility.allVisibleKnowledgebases()).thenReturn(List.of());
		InternalKnowledgeBaseSearchToolSource tool = new InternalKnowledgeBaseSearchToolSource(
				provider(mock(IGDocumentsSearchService.class)), provider(visibility), mock(IGSecurityService.class), TEXT_RENDERER, null, null);

		assertEquals("No search done: the query is empty.", tool.search(query(" "), null));
		assertEquals("No internal knowledge base is available to the user.", tool.search(query("topic"), null));
	}

	@Test
	void aFailingSearchIsAnsweredAsText() throws Exception {
		IGDocumentsSearchService search = mock(IGDocumentsSearchService.class);
		when(search.search(anyString(), anyList(), any(), anyList(), any(), anyString(), anyInt(), anyInt()))
				.thenThrow(new IllegalStateException("vector store down"));
		InternalKnowledgeBaseSearchToolSource tool = new InternalKnowledgeBaseSearchToolSource(provider(search),
				provider(mock(IGKnowledgebaseVisibilityService.class)), mock(IGSecurityService.class), TEXT_RENDERER, null, null);

		assertEquals("The internal knowledge base search failed, go on without it.",
				tool.search(query("topic"), chatWithKnowledgeBases("kb1")));
	}

	@Test
	void theToolIsDeclaredForTheModels() {
		InternalKnowledgeBaseSearchToolSource tool = new InternalKnowledgeBaseSearchToolSource(
				provider(mock(IGDocumentsSearchService.class)), provider(mock(IGKnowledgebaseVisibilityService.class)),
				mock(IGSecurityService.class), TEXT_RENDERER, null, null);

		assertEquals(1, tool.getToolCallbacks().size());
		assertEquals(InternalKnowledgeBaseSearchToolSource.SEARCH_KNOWLEDGE_BASE_TOOL,
				tool.getToolCallbacks().get(0).getToolDefinition().name());
		assertTrue(tool.getToolCallbacks().get(0).getToolDefinition().inputSchema().contains("query"));
	}
}
