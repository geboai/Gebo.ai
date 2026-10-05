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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ai.document.Document;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentFragment;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.SemanticSearchMetaDataFilter;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.fulltext.service.IGFullTextSearchService;
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
		when(provider.getIfAvailable()).thenReturn(value);
		return provider;
	}

	private static KnowledgeBaseSearchParam query(String text) {
		KnowledgeBaseSearchParam param = new KnowledgeBaseSearchParam();
		param.setQuery(text);
		return param;
	}

	private static List<String> chatWithKnowledgeBases(String... codes) {
		return List.of(codes);
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
				mock(IGSecurityService.class), TEXT_RENDERER,
				config, null);
	}

	@SuppressWarnings("unchecked")
	private static InternalKnowledgeBaseSearchToolSource rankingTool(IGDocumentsSearchService search,
			IGRankerService ranker) {
		org.springframework.beans.factory.ObjectProvider<IGRankerService> rankers = mock(
				org.springframework.beans.factory.ObjectProvider.class);
		when(rankers.getIfAvailable()).thenReturn(ranker);
		return new InternalKnowledgeBaseSearchToolSource(provider(search),
				mock(IGSecurityService.class), TEXT_RENDERER, null,
				rankers);
	}

	/** Each fragment its own document, as the search returns them. */
	/** A passage with text enough not to be near-empty. */
	private static final String PASSAGE = "a passage of the document with enough text to answer something about the subject asked";

	private static IGDocumentsSearchService searchFindingDocuments(int documents) throws Exception {
		IGDocumentsSearchService search = mock(IGDocumentsSearchService.class);
		List<Document> found = new ArrayList<>();
		for (int i = 0; i < documents; i++) {
			found.add(Document.builder().id("d" + i).text("fragment" + i + " " + PASSAGE)
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
		Document fragment = Document.builder().id("f1").text(PASSAGE)
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
				mock(IGSecurityService.class), TEXT_RENDERER, null, null);
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
	void anEmptyQueryOrAChatWithoutKnowledgeBasesIsAnsweredAsText() throws Exception {
		IGDocumentsSearchService search = mock(IGDocumentsSearchService.class);
		InternalKnowledgeBaseSearchToolSource tool = new InternalKnowledgeBaseSearchToolSource(provider(search),
				mock(IGSecurityService.class), TEXT_RENDERER, null, null);

		assertEquals("No search done: the query is empty.", tool.search(query(" "), chatWithKnowledgeBases("kb1")));
		// a chat without knowledge bases searches none, never all the visible ones
		assertEquals("This chat has no knowledge base to search.", tool.search(query("topic"), List.of()));
		assertEquals("This chat has no knowledge base to search.", tool.search(query("topic"), null));
		verify(search, never()).search(anyString(), anyList(), any(), anyList(), any(), anyString(), anyInt(), anyInt());
	}

	@Test
	void theToolSearchesTheKnowledgeBasesOfTheChatItIsCalledFor() throws Exception {
		IGDocumentsSearchService search = mock(IGDocumentsSearchService.class);
		when(search.search(anyString(), anyList(), any(), anyList(), any(), anyString(), anyInt(), anyInt()))
				.thenReturn(new AIDocumentsSet());
		InternalKnowledgeBaseSearchToolSource tool = new InternalKnowledgeBaseSearchToolSource(provider(search),
				mock(IGSecurityService.class), TEXT_RENDERER, null, null);
		org.springframework.ai.tool.ToolCallback callback = tool.getToolCallbacks().get(0);

		callback.call("{\"query\":\"topic\"}", new org.springframework.ai.chat.model.ToolContext(Map.of(
				ToolCallbackDeclarationUtil.CHAT_KNOWLEDGE_BASES_CONTEXT_KEY, List.of("kb-of-the-profile"))));

		ArgumentCaptor<SemanticSearchMetaDataFilter> semantic = ArgumentCaptor.forClass(SemanticSearchMetaDataFilter.class);
		verify(search).search(anyString(), anyList(), semantic.capture(), anyList(), any(), anyString(), anyInt(),
				anyInt());
		assertEquals(List.of("kb-of-the-profile"), semantic.getValue().getKnowledgeBasesCodes());

		// called outside a chat: no knowledge base, no search
		final String answer = callback.call("{\"query\":\"topic\"}",
				new org.springframework.ai.chat.model.ToolContext(Map.of()));
		assertTrue(answer.contains("no knowledge base"), answer);
		verify(search, org.mockito.Mockito.times(1)).search(anyString(), anyList(), any(), anyList(), any(),
				anyString(), anyInt(), anyInt());
	}

	@Test
	void aFailingSearchIsAnsweredAsText() throws Exception {
		IGDocumentsSearchService search = mock(IGDocumentsSearchService.class);
		when(search.search(anyString(), anyList(), any(), anyList(), any(), anyString(), anyInt(), anyInt()))
				.thenThrow(new IllegalStateException("vector store down"));
		InternalKnowledgeBaseSearchToolSource tool = new InternalKnowledgeBaseSearchToolSource(provider(search),
				mock(IGSecurityService.class), TEXT_RENDERER, null, null);

		assertEquals("The internal knowledge base search failed, go on without it.",
				tool.search(query("topic"), chatWithKnowledgeBases("kb1")));
	}

	@Test
	void theToolIsDeclaredForTheModels() {
		InternalKnowledgeBaseSearchToolSource tool = new InternalKnowledgeBaseSearchToolSource(
				provider(mock(IGDocumentsSearchService.class)),
				mock(IGSecurityService.class), TEXT_RENDERER, null, null);

		assertEquals(1, tool.getToolCallbacks().size());
		assertEquals(InternalKnowledgeBaseSearchToolSource.SEARCH_KNOWLEDGE_BASE_TOOL,
				tool.getToolCallbacks().get(0).getToolDefinition().name());
		assertTrue(tool.getToolCallbacks().get(0).getToolDefinition().inputSchema().contains("query"));
	}

	@SuppressWarnings("unchecked")
	@Test
	void theKeywordsAreDeclaredOnlyWithAFullTextLeg() {
		InternalKnowledgeBaseSearchToolSource tool = new InternalKnowledgeBaseSearchToolSource(
				provider(mock(IGDocumentsSearchService.class)),
				mock(IGSecurityService.class), TEXT_RENDERER, null, null);
		assertFalse(tool.getToolCallbacks().get(0).getToolDefinition().inputSchema().contains("keywords"));

		org.springframework.beans.factory.ObjectProvider<IGFullTextSearchService> noFullText = mock(
				org.springframework.beans.factory.ObjectProvider.class);
		tool.setFullTextSearchService(noFullText);
		assertFalse(tool.getToolCallbacks().get(0).getToolDefinition().inputSchema().contains("keywords"));

		tool.setFullTextSearchService(provider(mock(IGFullTextSearchService.class)));
		String schema = tool.getToolCallbacks().get(0).getToolDefinition().inputSchema();
		assertTrue(schema.contains("\"keywords\""), schema);
		assertTrue(schema.contains("\"query\""), schema);
	}

	@SuppressWarnings("unchecked")
	@Test
	void theFullTextLegSearchesTheKeywordsElseTheQueries() throws Exception {
		IGDocumentsSearchService search = searchFindingDocuments(3);
		InternalKnowledgeBaseSearchToolSource tool = tool(search, null);
		InternalKnowledgeBaseSearchToolSource.KnowledgeBaseKeywordsSearchParam withKeywords = new InternalKnowledgeBaseSearchToolSource.KnowledgeBaseKeywordsSearchParam();
		withKeywords.setQuery("what is the cosmic substance");
		withKeywords.setKeywords(List.of("Svâbhâvat", " ", "Dhyân Chohans", "Svâbhâvat"));
		KnowledgeBaseSearchParam plain = query("topic");
		plain.setAlternativeQueries(List.of("other phrasing"));

		tool.search(withKeywords, chatWithKnowledgeBases("kb1"));
		tool.search(plain, chatWithKnowledgeBases("kb1"));

		org.mockito.ArgumentCaptor<List<String>> fullText = org.mockito.ArgumentCaptor.forClass(List.class);
		verify(search, org.mockito.Mockito.times(2)).search(anyString(), anyList(), any(), fullText.capture(), any(),
				anyString(), anyInt(), anyInt());
		assertEquals(List.of("Svâbhâvat", "Dhyân Chohans"), fullText.getAllValues().get(0));
		assertEquals(List.of("topic", "other phrasing"), fullText.getAllValues().get(1));
	}
	@SuppressWarnings("unchecked")
	private static org.springframework.beans.factory.ObjectProvider<StandardAgentsConfig> minFragmentChars(int chars) {
		StandardAgentsConfig config = new StandardAgentsConfig();
		config.setKnowledgeBaseSearchMinFragmentChars(chars);
		org.springframework.beans.factory.ObjectProvider<StandardAgentsConfig> provider = mock(
				org.springframework.beans.factory.ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(config);
		return provider;
	}

	private static IGDocumentsSearchService searchFindingTexts(String... texts) throws Exception {
		IGDocumentsSearchService search = mock(IGDocumentsSearchService.class);
		List<Document> found = new ArrayList<>();
		for (int i = 0; i < texts.length; i++) {
			found.add(Document.builder().id("d" + i).text(texts[i])
					.metadata(Map.of(DocumentMetaInfos.CONTENT_CODE, "doc-" + i)).build());
		}
		AIDocumentsSet set = mock(AIDocumentsSet.class);
		when(set.aiDocumentsList()).thenReturn(found);
		when(search.search(anyString(), anyList(), any(), anyList(), any(), anyString(), anyInt(), anyInt()))
				.thenReturn(set);
		return search;
	}

	private static final String HEADER = "META-TITLE:The Secret Doctrine, Vol. 1 of 4\nMETA-SUBTITLE:by Helena Petrovna Blavatsky\n\n\n";

	@Test
	void theNearEmptyFragmentsAreKeptOutBeforeTheRanking() throws Exception {
		assertEquals(34, InternalKnowledgeBaseSearchToolSource.textChars(
				Document.builder().id("x").text(HEADER + "complete explanation of this fact?").build()),
				"the META- header lines are not text");
		IGDocumentsSearchService search = searchFindingTexts(HEADER + "complete explanation of this fact?",
				HEADER + "314.\n511\nIsis Unveiled, I. 341.", HEADER + "Footnotes",
				HEADER + "Fohat is thus the dynamic energy of Cosmic Ideation; or, regarded from the other side, "
						+ "it is the intelligent medium.");
		IGRankerService ranker = mock(IGRankerService.class);
		when(ranker.isRankerConfigured()).thenReturn(true);
		when(ranker.rank(anyList(), anyString(), anyInt())).thenAnswer(invocation -> invocation.getArgument(0));
		ToolsFoundDocuments collector = new ToolsFoundDocuments();
		InternalKnowledgeBaseSearchToolSource tool = new InternalKnowledgeBaseSearchToolSource(provider(search),
				mock(IGSecurityService.class), TEXT_RENDERER, minFragmentChars(70), provider(ranker));

		String answer = tool.search(query("Fohat"), chatWithKnowledgeBases("kb1"), collector, null);

		assertTrue(answer.startsWith("1 fragment(s) found:"), answer.substring(0, 40));
		assertTrue(answer.contains("dynamic energy of Cosmic Ideation"));
		assertFalse(answer.contains("Footnotes") || answer.contains("Isis Unveiled"), answer);
		org.mockito.ArgumentCaptor<List<Document>> ranked = org.mockito.ArgumentCaptor.forClass(List.class);
		verify(ranker).rank(ranked.capture(), anyString(), anyInt());
		assertEquals(1, ranked.getValue().size(), "the ranker chooses among the fragments with text only");
		assertEquals(List.of("doc-3"), collector.getDocuments().stream().map(x -> x.getDocumentCode()).toList());
	}

	@Test
	void onlyNearEmptyFragmentsAskTheModelToSearchAgainAndZeroKeepsThemAll() throws Exception {
		IGDocumentsSearchService search = searchFindingTexts(HEADER + "Footnotes", HEADER + "cit., pp. 366-8.");

		String answer = new InternalKnowledgeBaseSearchToolSource(provider(search), mock(IGSecurityService.class),
				TEXT_RENDERER, minFragmentChars(70), null).search(query("Svabhavat"), chatWithKnowledgeBases("kb1"));

		assertTrue(answer.startsWith("Only near-empty fragments"), answer);
		assertTrue(answer.contains("search again"), answer);

		String all = new InternalKnowledgeBaseSearchToolSource(provider(search), mock(IGSecurityService.class),
				TEXT_RENDERER, minFragmentChars(0), null).search(query("Svabhavat"), chatWithKnowledgeBases("kb1"));
		assertTrue(all.startsWith("2 fragment(s) found:"), all);
		assertEquals(InternalKnowledgeBaseSearchToolSource.DEFAULT_MIN_FRAGMENT_CHARS,
				new InternalKnowledgeBaseSearchToolSource(provider(search), mock(IGSecurityService.class),
						TEXT_RENDERER, minFragmentChars(-1), null).minFragmentChars(),
				"a negative value is not used");
		assertEquals(70, InternalKnowledgeBaseSearchToolSource.DEFAULT_MIN_FRAGMENT_CHARS);
	}
}
