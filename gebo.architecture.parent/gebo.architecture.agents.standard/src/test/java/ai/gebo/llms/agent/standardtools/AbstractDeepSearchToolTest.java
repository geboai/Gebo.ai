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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.IDocumentChunkWithRef;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.agent.standardtools.AbstractDeepSearchTool.FoundDocument;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolParam;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolParam.Depth;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolResult;
import ai.gebo.llms.agent.standardtools.model.DeepSearchToolResult.Source;
import ai.gebo.llms.agent.standardtools.model.SearchToolResult.Status;
import ai.gebo.llms.chat.abstraction.layer.config.GeboRagSearchConfig;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.deepsearch.service.IGExternalSearchSecurityService;
import ai.gebo.model.DocumentMetaInfos;
import reactor.core.publisher.Flux;

/**
 * Pins the deep search tools: the agent's searches run directly on the source, the
 * fragments found are analysed against the agent's question, the sources are the
 * documents the analysis relies on (not the discarded ones), the deep searches of a
 * request are capped, and a failure or an empty search is answered, not thrown.
 */
class AbstractDeepSearchToolTest {
	private DeepSearchToolAnalysis analysis;
	private IDocumentsChunkService chunkingService;
	private IGChatModelRuntimeConfigurationDao chatModelsDao;
	private IGConfigurableChatModel chatModel;
	private IGConfigurableChatModel serviceModel;
	private IGExternalSearchSecurityService security;
	private DeepSearchToolsSupport support;

	@SuppressWarnings("unchecked")
	private static <T> ObjectProvider<T> provider(T value) {
		ObjectProvider<T> provider = mock(ObjectProvider.class);
		when(provider.getObject()).thenReturn(value);
		when(provider.getIfAvailable()).thenReturn(value);
		return provider;
	}

	/** A source returning the given fragments, each from the document of its code. */
	static class TestDeepSearchTool extends AbstractDeepSearchTool {
		final List<Document> fragments;
		boolean available = true;
		final AtomicReference<List<String>> searched = new AtomicReference<>();

		TestDeepSearchTool(DeepSearchToolsSupport support, List<Document> fragments) {
			super(support, "deepSearchTest", "test deep search");
			this.fragments = fragments;
		}

		@Override
		protected boolean isAvailable() {
			return available;
		}

		@Override
		protected String sourceDescription() {
			return "the test source";
		}

		@Override
		protected List<Document> searchDocuments(List<String> queries, String question, int maxDocuments,
				Map<String, FoundDocument> foundByFragmentId) {
			searched.set(queries);
			for (Document fragment : fragments) {
				String code = (String) fragment.getMetadata().get(DocumentMetaInfos.CONTENT_CODE);
				foundByFragmentId.put(fragment.getId(),
						new FoundDocument(new Source("title " + code, null, code), new GResponseDocumentRef(fragment)));
			}
			return fragments;
		}
	}

	private static Document fragment(String id, String documentCode) {
		Map<String, Object> metaData = new HashMap<>();
		metaData.put(DocumentMetaInfos.CONTENT_CODE, documentCode);
		return Document.builder().id(id).text("content of " + id).metadata(metaData).build();
	}

	private static DeepSearchToolParam param(String question, String... queries) {
		DeepSearchToolParam param = new DeepSearchToolParam();
		param.setQuestion(question);
		param.setQueries(List.of(queries));
		return param;
	}

	private static ToolContext request(String requestId) {
		return new ToolContext(Map.of(ToolCallbackDeclarationUtil.REQUEST_ID_CONTEXT_KEY, requestId));
	}

	/** The tool context of a model call made through a context sharing the collector. */
	private static ToolContext request(String requestId, DeepSearchToolDocuments collector) {
		IChatRequestContext context = collector.sharedThrough(IChatRequestContext.builder().requestID(requestId)
				.toolsContext(Map.of(ToolCallbackDeclarationUtil.REQUEST_ID_CONTEXT_KEY, requestId)).build());
		return new ToolContext(context.getToolsContext());
	}

	@BeforeEach
	void setUp() throws Exception {
		analysis = mock(DeepSearchToolAnalysis.class);
		chunkingService = mock(IDocumentsChunkService.class);
		chatModelsDao = mock(IGChatModelRuntimeConfigurationDao.class);
		chatModel = mock(IGConfigurableChatModel.class);
		serviceModel = mock(IGConfigurableChatModel.class);
		security = mock(IGExternalSearchSecurityService.class);
		when(security.isEnabledForCurrentUser(any())).thenReturn(true);
		when(chatModel.getCode()).thenReturn("chat");
		when(serviceModel.getCode()).thenReturn("service");
		when(chatModelsDao.defaultHandler()).thenReturn(chatModel);
		when(chatModelsDao.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES)).thenReturn(serviceModel);
		// the analysis reads every fragment, judges "f2" irrelevant and writes its analysis
		doAnswer(invocation -> {
			Flux<Document> documents = invocation.getArgument(0);
			List<Document> read = documents.collectList().block();
			Vector<String> discarded = invocation.getArgument(7);
			if (read != null && read.stream().anyMatch(x -> x.getId().equals("f2"))) {
				discarded.add("f2");
			}
			return Flux.just("The ", "analysis.");
		}).when(analysis).analyze(any(), any(), any(), any(), anyString(), any(), any(), any());
		GeboRagSearchConfig ragSearchConfig = mock(GeboRagSearchConfig.class);
		when(ragSearchConfig.getDeepSearchGlobalTopK()).thenReturn(30);
		support = new DeepSearchToolsSupport(provider(analysis), provider(chunkingService), provider(chatModelsDao),
				provider(ragSearchConfig), provider(security));
	}

	@Test
	void analysesTheFragmentsFoundAndGivesTheDocumentsReliedOn() {
		TestDeepSearchTool tool = new TestDeepSearchTool(support,
				List.of(fragment("f1", "doc-a"), fragment("f2", "doc-b"), fragment("f3", "doc-a")));
		DeepSearchToolParam param = param("agent question", "first search", " ", "second search", "first search");
		param.setSearchObjective("compare the offers");
		param.setDepth(Depth.EXHAUSTIVE);

		DeepSearchToolResult result = tool.deepSearch(param, request("r1"));

		assertEquals(Status.OK, result.getStatus());
		assertEquals("The analysis.", result.getAnalysis());
		assertEquals(3, result.getFragmentsAnalysed());
		// the agent's searches, blank and repeated ones dropped
		assertEquals(List.of("first search", "second search"), tool.searched.get());
		// the irrelevant fragment's document is not a source
		assertEquals(List.of("doc-a"), result.getSources().stream().map(Source::getDocumentCode).toList());
		verify(analysis).analyze(any(), any(), any(), eq(DeliverableIntent.ANALISYS), anyString(), eq(chatModel),
				eq(serviceModel), any());
	}

	@Test
	void theDocumentsReliedOnAreSharedWithTheCallingAgent() {
		TestDeepSearchTool tool = new TestDeepSearchTool(support,
				List.of(fragment("f1", "doc-a"), fragment("f2", "doc-b"), fragment("f3", "doc-c")));
		DeepSearchToolDocuments collector = new DeepSearchToolDocuments();

		tool.deepSearch(param("question"), request("r1", collector));

		// the irrelevant fragment's document is not shared
		assertEquals(List.of("doc-a", "doc-c"),
				collector.getDocuments().stream().map(GResponseDocumentRef::getDocumentCode).toList());
		// without a collector the tool still answers
		assertEquals(Status.OK, tool.deepSearch(param("question"), request("r2")).getStatus());
	}

	@Test
	void theCollectorIsSharedWithoutChangingTheRestOfTheContext() {
		DeepSearchToolDocuments collector = new DeepSearchToolDocuments();
		IChatRequestContext original = IChatRequestContext.builder().requestID("r1").actualUserRequest("question")
				.toolsContext(Map.of(ToolCallbackDeclarationUtil.REQUEST_ID_CONTEXT_KEY, "r1")).build();

		IChatRequestContext shared = collector.sharedThrough(original);

		assertEquals("question", shared.getActualUserRequest());
		assertEquals("r1", shared.getToolsContext().get(ToolCallbackDeclarationUtil.REQUEST_ID_CONTEXT_KEY));
		assertSame(collector, DeepSearchToolDocuments.from(new ToolContext(shared.getToolsContext())));
		assertEquals(null, original.getToolsContext().get(DeepSearchToolDocuments.TOOLS_CONTEXT_KEY));
	}

	@Test
	void theAnswerDocumentsComeFirstThenTheToolsOnes() {
		DeepSearchToolDocuments collector = new DeepSearchToolDocuments();
		collector.add(List.of(new GResponseDocumentRef(fragment("f1", "doc-a")),
				new GResponseDocumentRef(fragment("f2", "doc-b"))));
		List<GResponseDocumentRef> answer = List.of(new GResponseDocumentRef(fragment("f3", "doc-b")));

		List<GResponseDocumentRef> merged = collector.mergeInto(answer);

		assertEquals(List.of("doc-b", "doc-a"), merged.stream().map(GResponseDocumentRef::getDocumentCode).toList());
		assertSame(answer.get(0), merged.get(0));
		assertSame(answer, new DeepSearchToolDocuments().mergeInto(answer));
	}

	@Test
	void theAnalysisQuestionIsTheAgentOne() {
		TestDeepSearchTool tool = new TestDeepSearchTool(support, List.of(fragment("f1", "doc-a")));
		AtomicReference<IChatRequestContext> analysed = new AtomicReference<>();
		doAnswer(invocation -> {
			analysed.set(invocation.getArgument(1));
			return Flux.just("ok");
		}).when(analysis).analyze(any(), any(), any(), any(), anyString(), any(), any(), any());
		DeepSearchToolParam param = param("agent question");
		param.setSearchObjective("what for");

		tool.deepSearch(param, request("r1"));

		assertEquals("agent question\nwhat for", analysed.get().getActualUserRequest());
		assertEquals("r1", analysed.get().getRequestID());
		// no search given: the question is searched
		assertEquals(List.of("agent question"), tool.searched.get());
	}

	@Test
	void capsTheDeepSearchesOfARequest() {
		TestDeepSearchTool tool = new TestDeepSearchTool(support, List.of(fragment("f1", "doc-a")));
		for (int i = 0; i < AbstractDeepSearchTool.MAX_DEEP_SEARCHES_PER_REQUEST; i++) {
			assertEquals(Status.OK, tool.deepSearch(param("question " + i), request("r1")).getStatus());
		}

		assertEquals(Status.NOT_ALLOWED, tool.deepSearch(param("one more"), request("r1")).getStatus());
		// another request has its own deep searches
		assertEquals(Status.OK, tool.deepSearch(param("question"), request("r2")).getStatus());
		verify(analysis, times(AbstractDeepSearchTool.MAX_DEEP_SEARCHES_PER_REQUEST + 1)).analyze(any(), any(), any(),
				any(), anyString(), any(), any(), any());
	}

	@Test
	void answersWhenNothingIsFoundOrTheSourceIsNotAllowed() {
		TestDeepSearchTool empty = new TestDeepSearchTool(support, List.of());
		assertEquals(Status.NO_RESULTS, empty.deepSearch(param("question"), null).getStatus());

		TestDeepSearchTool denied = new TestDeepSearchTool(support, List.of(fragment("f1", "doc-a")));
		denied.available = false;
		assertEquals(Status.NOT_ALLOWED, denied.deepSearch(param("question"), null).getStatus());

		assertEquals(Status.NO_RESULTS, empty.deepSearch(param(" "), null).getStatus());
		verify(analysis, never()).analyze(any(), any(), any(), any(), anyString(), any(), any(), any());
	}

	@Test
	void aFailingAnalysisIsAnswered() {
		doReturn(Flux.error(new IllegalStateException("provider down"))).when(analysis).analyze(any(), any(), any(),
				any(), anyString(), any(), any(), any());
		TestDeepSearchTool tool = new TestDeepSearchTool(support, List.of(fragment("f1", "doc-a")));

		assertEquals(Status.FAILED, tool.deepSearch(param("question"), null).getStatus());
	}

	@Test
	void noChatModelMeansNoDeepSearch() {
		when(chatModelsDao.defaultHandler()).thenReturn(null);
		TestDeepSearchTool tool = new TestDeepSearchTool(support, List.of(fragment("f1", "doc-a")));

		assertEquals(Status.FAILED, tool.deepSearch(param("question"), null).getStatus());
		assertEquals(null, tool.searched.get());
	}

	@Test
	void theAnalysisIsFittedInTheRequestedSize() {
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < 3000; i++) {
			text.append("word").append(i).append(' ');
		}
		DeepSearchToolResult result = new DeepSearchToolResult();

		AbstractDeepSearchTool.fit(result, text.toString(), 1000);

		assertTrue(result.getAnalysis().endsWith("[...]"));
		assertTrue(ITokensCountable.stringsTokensSize(result.getAnalysis()) <= 1100);
		assertNotNull(result.getMessage());
	}

	@Test
	void theDepthSizesTheAnalysis() {
		assertEquals(DeliverableIntent.QA, AbstractDeepSearchTool.deliverable(Depth.FOCUSED));
		assertEquals(DeliverableIntent.SUMMARY, AbstractDeepSearchTool.deliverable(Depth.BROAD));
		assertEquals(DeliverableIntent.SUMMARY, AbstractDeepSearchTool.deliverable(null));
		assertEquals(DeliverableIntent.ANALISYS, AbstractDeepSearchTool.deliverable(Depth.EXHAUSTIVE));
	}

	@Test
	void atMostFiveSearchesRun() {
		List<String> queries = AbstractDeepSearchTool.queries(param("q", "1", "2", "3", "4", "5", "6"));
		assertEquals(AbstractDeepSearchTool.MAX_QUERIES, queries.size());
	}

	@Test
	void toolNamesFollowTheProduct() {
		assertEquals("deepSearchSharepoint", DeepSearchToolSource.toolName("sharepoint", "service"));
		assertEquals("deepSearchGoogleDrive", DeepSearchToolSource.toolName("google-drive", "service"));
		assertEquals("deepSearchServiceId", DeepSearchToolSource.toolName(null, "service.id"));
	}

	private static SearchResult result(String url, String title) {
		SearchResult result = new SearchResult();
		result.setResultReference(new SearchResultReference());
		result.getResultReference().setUri(url);
		result.getResultReference().setName(title);
		result.setSystemConfigurationCode("web");
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	void anExternalSourceIsSearchedWithEverySearchAndItsDocumentsRead() throws Exception {
		when(chunkingService.createChunkingSession(anyString())).thenReturn("session");
		// every search result becomes one chunk carrying its text
		when(chunkingService.streamChunks(anyList(), any(), anyString(), anyInt())).thenAnswer(invocation -> {
			List<SearchResult> results = invocation.getArgument(0);
			List<IDocumentChunkWithRef> chunks = new ArrayList<>();
			for (SearchResult result : results) {
				DocumentChunk chunk = DocumentChunk.ofText(result.getCode(), "content of " + result.getCode(),
						Map.of());
				chunk.setChunkPosition(1l);
				chunks.add(IDocumentChunkWithRef.of(chunk, result));
			}
			return Flux.fromIterable(chunks).parallel();
		});
		ISearchService service = mock(ISearchService.class);
		when(service.getId()).thenReturn("web-service");
		SearchableSystemMetaData working = new SearchableSystemMetaData();
		working.setCode("working");
		SearchableSystemMetaData failing = new SearchableSystemMetaData();
		failing.setCode("failing");
		when(service.getSearchableSystems()).thenReturn(List.of(failing, working));
		SearchResult a = result("https://a.example/a", "A");
		SearchResult b = result("https://a.example/b", "B");
		when(service.search(any(SearchQuery.class), argThat((SearchableSystemMetaData x) -> x == failing), anyInt())).thenThrow(new RuntimeException("down"));
		when(service.search(any(SearchQuery.class), argThat((SearchableSystemMetaData x) -> x == working), anyInt())).thenAnswer(invocation -> {
			SearchQuery query = invocation.getArgument(0);
			return query.getQueryText().equals("first") ? List.of(a, b) : List.of(b);
		});
		SearchServiceDeepSearchTool tool = new SearchServiceDeepSearchTool(support, service, "deepSearchWeb",
				"the web");
		DeepSearchToolDocuments collector = new DeepSearchToolDocuments();

		DeepSearchToolResult result = tool.deepSearch(param("question", "first", "second"), request("r1", collector));

		assertEquals(Status.OK, result.getStatus());
		assertEquals(2, result.getFragmentsAnalysed());
		// the chunks are loaded in parallel: the sources come in any order
		assertEquals(List.of("A", "B"), result.getSources().stream().map(Source::getTitle).sorted().toList());
		assertEquals(List.of("https://a.example/a", "https://a.example/b"),
				result.getSources().stream().map(Source::getSource).sorted().toList());
		verify(service, times(2)).search(any(SearchQuery.class), argThat((SearchableSystemMetaData x) -> x == working), anyInt());
		verify(chunkingService).disposeChunkingSession("session");
		// the shared documents keep their search result, so the user can chat with them
		assertEquals(2, collector.getDocuments().size());
		assertTrue(collector.getDocuments().stream().allMatch(ref -> ref.getNestedSearchResult() != null));

		// (SearchableSystemMetaData equality ignores the code: the systems are matched by identity)
		// every search failing is a failed deep search, a denied user gets no search
		when(service.search(any(SearchQuery.class), argThat((SearchableSystemMetaData x) -> x == working), anyInt())).thenThrow(new RuntimeException("down"));
		assertEquals(Status.FAILED, tool.deepSearch(param("question", "first"), request("r2")).getStatus());
		when(security.isEnabledForCurrentUser(service)).thenReturn(false);
		assertEquals(Status.NOT_ALLOWED, tool.deepSearch(param("question", "first"), request("r3")).getStatus());
	}
}
