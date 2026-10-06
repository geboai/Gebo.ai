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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.architecture.ai.service.ToolsTokenBudget;
import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.IDocumentChunkWithRef;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.INativeSearchService;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGProgressNotifier;
import ai.gebo.llms.agent.standardtools.AbstractDeepSearchTool.FoundDocument;
import ai.gebo.llms.agent.standardtools.model.DeepSearchCoverage;
import ai.gebo.llms.agent.standardtools.model.DeepSearchCoverage.SearchCoverage;
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
import ai.gebo.model.base.IGComponentOriginatedDocument;
import ai.gebo.llms.agent.standard.config.StandardAgentsConfig;
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
	static class TestDeepSearchTool extends AbstractDeepSearchTool<String> {
		final List<Document> fragments;
		boolean available = true;
		final AtomicReference<List<String>> searched = new AtomicReference<>();

		TestDeepSearchTool(DeepSearchToolsSupport support, List<Document> fragments) {
			super(support, String.class, "deepSearchTest", "test deep search");
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
				int fragmentsPerDocument, Map<String, FoundDocument> foundByFragmentId) {
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

	private static DeepSearchToolParam<String> param(String question, String... queries) {
		DeepSearchToolParam<String> param = new DeepSearchToolParam<>();
		param.setQuestion(question);
		param.setQueries(List.of(queries));
		return param;
	}

	private static ToolContext request(String requestId) {
		return new ToolContext(Map.of(ToolCallbackDeclarationUtil.REQUEST_ID_CONTEXT_KEY, requestId));
	}

	/** The tool context of a model call made through a context sharing the collector. */
	private static ToolContext request(String requestId, ToolsFoundDocuments collector) {
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
		}).when(analysis).analyze(any(), any(), any(), any(), anyString(), any(), any(), any(), any(), any());
		GeboRagSearchConfig ragSearchConfig = mock(GeboRagSearchConfig.class);
		when(ragSearchConfig.getDeepSearchGlobalTopK()).thenReturn(30);
		support = new DeepSearchToolsSupport(provider(analysis), provider(chunkingService), provider(chatModelsDao),
				provider(ragSearchConfig), provider(security), provider(new StandardAgentsConfig()),
				DeepSearchToolsSupport.DEFAULT_MAX_ANALYSIS_TOKENS);
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
				eq(serviceModel), any(), any(), any());
	}

	@Test
	void anAnalysisJudgingEveryFragmentIrrelevantKeepsTheDocumentsFound() {
		doAnswer(invocation -> {
			Flux<Document> documents = invocation.getArgument(0);
			Vector<String> discarded = invocation.getArgument(7);
			documents.collectList().block().forEach(x -> discarded.add(x.getId()));
			return Flux.just("An analysis citing the documents.");
		}).when(analysis).analyze(any(), any(), any(), any(), anyString(), any(), any(), any(), any(), any());
		TestDeepSearchTool tool = new TestDeepSearchTool(support,
				List.of(fragment("f1", "doc-a"), fragment("f2", "doc-b")));

		DeepSearchToolResult result = tool.deepSearch(param("agent question"), request("r1"));

		assertEquals(Status.OK, result.getStatus());
		assertEquals(List.of("doc-a", "doc-b"), result.getSources().stream().map(Source::getDocumentCode).toList());
	}

	@Test
	void tellsTheUserWhatItIsDoingThroughTheSharedNotifier() {
		TestDeepSearchTool tool = new TestDeepSearchTool(support,
				List.of(fragment("f1", "doc-a"), fragment("f2", "doc-b"), fragment("f3", "doc-a")));
		List<String> notified = new ArrayList<>();
		IGProgressNotifier notifier = new IGProgressNotifier() {
			@Override
			public void notifyProgress(String code, String message) {
				notified.add(message);
			}

			@Override
			public void notifyLLMProblems() {
			}
		};
		IChatRequestContext context = ToolsProgress.sharedThrough(IChatRequestContext.builder().requestID("r1")
				.toolsContext(Map.of(ToolCallbackDeclarationUtil.REQUEST_ID_CONTEXT_KEY, "r1")).build(), notifier);

		tool.deepSearch(param("agent question", "first search"), new ToolContext(context.getToolsContext()));

		assertEquals(List.of("Deep search in the test source: agent question",
				"Deep search in the test source: analysing 3 fragment(s) of 2 document(s)"), notified);
		// the analysis reports its progress to the same notifier
		verify(analysis).analyze(any(), any(), any(), any(), anyString(), any(), any(), any(), eq(notifier), any());
	}

	@Test
	void worksSilentlyWithoutANotifier() {
		TestDeepSearchTool tool = new TestDeepSearchTool(support, List.of(fragment("f1", "doc-a")));

		DeepSearchToolResult result = tool.deepSearch(param("agent question"), request("r1"));

		assertEquals(Status.OK, result.getStatus());
		verify(analysis).analyze(any(), any(), any(), any(), anyString(), any(), any(), any(),
				eq(IGProgressNotifier.NONE), any());
	}

	@Test
	void theDocumentsReliedOnAreSharedWithTheCallingAgent() {
		TestDeepSearchTool tool = new TestDeepSearchTool(support,
				List.of(fragment("f1", "doc-a"), fragment("f2", "doc-b"), fragment("f3", "doc-c")));
		ToolsFoundDocuments collector = new ToolsFoundDocuments();

		tool.deepSearch(param("question"), request("r1", collector));

		// the irrelevant fragment's document is not shared
		assertEquals(List.of("doc-a", "doc-c"),
				collector.getDocuments().stream().map(GResponseDocumentRef::getDocumentCode).toList());
		// without a collector the tool still answers
		assertEquals(Status.OK, tool.deepSearch(param("question"), request("r2")).getStatus());
	}

	@Test
	void theCollectorIsSharedWithoutChangingTheRestOfTheContext() {
		ToolsFoundDocuments collector = new ToolsFoundDocuments();
		IChatRequestContext original = IChatRequestContext.builder().requestID("r1").actualUserRequest("question")
				.toolsContext(Map.of(ToolCallbackDeclarationUtil.REQUEST_ID_CONTEXT_KEY, "r1")).build();

		IChatRequestContext shared = collector.sharedThrough(original);

		assertEquals("question", shared.getActualUserRequest());
		assertEquals("r1", shared.getToolsContext().get(ToolCallbackDeclarationUtil.REQUEST_ID_CONTEXT_KEY));
		assertSame(collector, ToolsFoundDocuments.from(new ToolContext(shared.getToolsContext())));
		assertEquals(null, original.getToolsContext().get(ToolsFoundDocuments.TOOLS_CONTEXT_KEY));
	}

	@Test
	void theAnswerDocumentsComeFirstThenTheToolsOnes() {
		ToolsFoundDocuments collector = new ToolsFoundDocuments();
		collector.add(List.of(new GResponseDocumentRef(fragment("f1", "doc-a")),
				new GResponseDocumentRef(fragment("f2", "doc-b"))));
		List<GResponseDocumentRef> answer = List.of(new GResponseDocumentRef(fragment("f3", "doc-b")));

		List<GResponseDocumentRef> merged = collector.mergeInto(answer);

		assertEquals(List.of("doc-b", "doc-a"), merged.stream().map(GResponseDocumentRef::getDocumentCode).toList());
		assertSame(answer.get(0), merged.get(0));
		assertSame(answer, new ToolsFoundDocuments().mergeInto(answer));
	}

	@Test
	void theAnalysisQuestionIsTheAgentOne() {
		TestDeepSearchTool tool = new TestDeepSearchTool(support, List.of(fragment("f1", "doc-a")));
		AtomicReference<IChatRequestContext> analysed = new AtomicReference<>();
		doAnswer(invocation -> {
			analysed.set(invocation.getArgument(1));
			return Flux.just("ok");
		}).when(analysis).analyze(any(), any(), any(), any(), anyString(), any(), any(), any(), any(), any());
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
		for (int i = 0; i < support.maxDeepSearchesPerRequest(); i++) {
			assertEquals(Status.OK, tool.deepSearch(param("question " + i), request("r1")).getStatus());
		}

		assertEquals(Status.NOT_ALLOWED, tool.deepSearch(param("one more"), request("r1")).getStatus());
		// another request has its own deep searches
		assertEquals(Status.OK, tool.deepSearch(param("question"), request("r2")).getStatus());
		verify(analysis, times(support.maxDeepSearchesPerRequest() + 1)).analyze(any(), any(), any(),
				any(), anyString(), any(), any(), any(), any(), any());
	}

	@Test
	void theDeepSearchesOfARequestAreConfigurableEightByDefault() {
		assertEquals(DeepSearchToolsSupport.DEFAULT_MAX_DEEP_SEARCHES_PER_REQUEST, support.maxDeepSearchesPerRequest());
		assertEquals(8, DeepSearchToolsSupport.DEFAULT_MAX_DEEP_SEARCHES_PER_REQUEST);
		support.setMaxDeepSearchesPerRequest(0);
		assertEquals(8, support.maxDeepSearchesPerRequest());

		support.setMaxDeepSearchesPerRequest(3);
		TestDeepSearchTool tool = new TestDeepSearchTool(support, List.of(fragment("f1", "doc-a")));
		for (int i = 0; i < 3; i++) {
			assertEquals(Status.OK, tool.deepSearch(param("question " + i), request("r1")).getStatus());
		}
		DeepSearchToolResult refused = tool.deepSearch(param("one more"), request("r1"));
		assertEquals(Status.NOT_ALLOWED, refused.getStatus());
		assertTrue(refused.getMessage().contains("(3)"), refused.getMessage());
	}

	@Test
	void answersWhenNothingIsFoundOrTheSourceIsNotAllowed() {
		TestDeepSearchTool empty = new TestDeepSearchTool(support, List.of());
		assertEquals(Status.NO_RESULTS, empty.deepSearch(param("question"), null).getStatus());

		TestDeepSearchTool denied = new TestDeepSearchTool(support, List.of(fragment("f1", "doc-a")));
		denied.available = false;
		assertEquals(Status.NOT_ALLOWED, denied.deepSearch(param("question"), null).getStatus());

		assertEquals(Status.NO_RESULTS, empty.deepSearch(param(" "), null).getStatus());
		verify(analysis, never()).analyze(any(), any(), any(), any(), anyString(), any(), any(), any(), any(), any());
	}

	@Test
	void aFailingAnalysisIsAnswered() {
		doReturn(Flux.error(new IllegalStateException("provider down"))).when(analysis).analyze(any(), any(), any(),
				any(), anyString(), any(), any(), any(), any(), any());
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
	void noAnalysisRunsWhenItsModelCallHasNoUsefulRoom() {
		TestDeepSearchTool tool = new TestDeepSearchTool(support, List.of(fragment("f1", "doc-a")));
		ToolContext full = new ToolContext(Map.of(ToolCallbackDeclarationUtil.REQUEST_ID_CONTEXT_KEY, "r1",
				ToolsTokenBudget.TOOLS_CONTEXT_KEY, new ToolsTokenBudget(ToolsTokenBudget.MIN_USEFUL_TOKENS - 1)));

		DeepSearchToolResult result = tool.deepSearch(param("question"), full);

		assertEquals(Status.NO_RESULTS, result.getStatus());
		verify(analysis, never()).analyze(any(), any(), any(), any(), anyString(), any(), any(), any(), any(), any());
		// not run, it is not one of the request's deep searches
		for (int i = 0; i < support.maxDeepSearchesPerRequest(); i++) {
			assertEquals(Status.OK, tool.deepSearch(param("question " + i), request("r1")).getStatus());
		}
	}

	@Test
	void theAnalysisIsAskedToFitItsRoomAndItsSourcesTakeTheirPart() {
		TestDeepSearchTool tool = new TestDeepSearchTool(support, List.of(fragment("f1", "doc-a")));
		ToolContext roomy = new ToolContext(Map.of(ToolCallbackDeclarationUtil.REQUEST_ID_CONTEXT_KEY, "r1",
				ToolsTokenBudget.TOOLS_CONTEXT_KEY, new ToolsTokenBudget(800)));

		tool.deepSearch(param("question"), roomy);

		// 800 tokens hold about 600 words, less than the 1000 a BROAD depth asks
		verify(analysis).analyze(any(), any(), any(), any(), argThat(note -> note.contains("600 words")), any(),
				any(), any(), any(), any());
		assertTrue(AbstractDeepSearchTool.lengthTarget(Depth.FOCUSED, 100_000).contains("400 words"));
		assertTrue(AbstractDeepSearchTool.lengthTarget(Depth.EXHAUSTIVE, 10).contains(
				AbstractDeepSearchTool.MIN_ANALYSIS_WORDS + " words"), "never shorter than the minimum");
	}

	@Test
	void theDepthAsksTheLengthOfTheAnalysis() {
		assertTrue(AbstractDeepSearchTool.lengthTarget(Depth.FOCUSED).contains("400 words"));
		assertTrue(AbstractDeepSearchTool.lengthTarget(null).contains("1000 words"));
		assertTrue(AbstractDeepSearchTool.lengthTarget(Depth.EXHAUSTIVE).contains("2500 words"));
	}

	@Test
	void atMostFiveSearchesRun() {
		List<String> queries = AbstractDeepSearchTool.queries(param("q", "1", "2", "3", "4", "5", "6"), String.class);
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
		when(chunkingService.streamChunks(any(IGComponentOriginatedDocument.class), any(), anyString()))
				.thenAnswer(invocation -> {
					SearchResult result = invocation.getArgument(0);
					DocumentChunk chunk = DocumentChunk.ofText(result.getCode(), "content of " + result.getCode(),
							Map.of());
					chunk.setChunkPosition(1l);
					return Flux.just(IDocumentChunkWithRef.of(chunk, result));
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
		when(service.search(any(SearchQuery.class), argThat((SearchableSystemMetaData x) -> x == failing), anyInt(), any())).thenThrow(new RuntimeException("down"));
		when(service.search(any(SearchQuery.class), argThat((SearchableSystemMetaData x) -> x == working), anyInt(), any())).thenAnswer(invocation -> {
			SearchQuery query = invocation.getArgument(0);
			return query.getQueryText().equals("first") ? List.of(a, b) : List.of(b);
		});
		SearchServiceDeepSearchTool<String> tool = new SearchServiceDeepSearchTool<>(support, service, String.class,
				"deepSearchWeb", "the web");
		ToolsFoundDocuments collector = new ToolsFoundDocuments();

		DeepSearchToolResult result = tool.deepSearch(param("question", "first", "second"), request("r1", collector));

		assertEquals(Status.OK, result.getStatus());
		assertEquals(2, result.getFragmentsAnalysed());
		// the chunks are loaded in parallel: the sources come in any order
		assertEquals(List.of("A", "B"), result.getSources().stream().map(Source::getTitle).sorted().toList());
		assertEquals(List.of("https://a.example/a", "https://a.example/b"),
				result.getSources().stream().map(Source::getSource).sorted().toList());
		verify(service, times(2)).search(any(SearchQuery.class), argThat((SearchableSystemMetaData x) -> x == working), anyInt(), any());
		verify(chunkingService).disposeChunkingSession("session");
		// the shared documents keep their search result, so the user can chat with them
		assertEquals(2, collector.getDocuments().size());
		assertTrue(collector.getDocuments().stream().allMatch(ref -> ref.getNestedSearchResult() != null));

		// (SearchableSystemMetaData equality ignores the code: the systems are matched by identity)
		// every search failing is a failed deep search, a denied user gets no search
		when(service.search(any(SearchQuery.class), argThat((SearchableSystemMetaData x) -> x == working), anyInt(), any())).thenThrow(new RuntimeException("down"));
		DeepSearchToolResult failed = tool.deepSearch(param("question", "first"), request("r2"));
		assertEquals(Status.FAILED, failed.getStatus());
		// the model is told which sources could not be searched, and why
		assertEquals(List.of("failing: failed", "working: failed"), failed.getUnavailableSources());
		when(security.isEnabledForCurrentUser(service)).thenReturn(false);
		assertEquals(Status.NOT_ALLOWED, tool.deepSearch(param("question", "first"), request("r3")).getStatus());
	}

	/** Every search result loads as one chunk carrying its text. */
	@SuppressWarnings("unchecked")
	private void everyResultIsOneChunk() {
		when(chunkingService.createChunkingSession(anyString())).thenReturn("session");
		when(chunkingService.streamChunks(any(IGComponentOriginatedDocument.class), any(), anyString()))
				.thenAnswer(invocation -> {
					SearchResult result = invocation.getArgument(0);
					DocumentChunk chunk = DocumentChunk.ofText(result.getCode(), "content of " + result.getCode(),
							Map.of());
					chunk.setChunkPosition(1l);
					return Flux.just(IDocumentChunkWithRef.of(chunk, result));
				});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private INativeSearchService jiraService(SearchableSystemMetaData system) throws Exception {
		INativeSearchService service = mock(INativeSearchService.class);
		when(service.getId()).thenReturn("jira-service");
		when(service.getNativeSearchDataStructureType()).thenReturn(SearchToolContentPipelineTest.JqlQuery.class);
		when(service.getSearchableSystems()).thenReturn(List.of(system));
		return service;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	void aNativeServiceIsDeepSearchedWithItsOwnQueries() throws Exception {
		everyResultIsOneChunk();
		SearchableSystemMetaData system = new SearchableSystemMetaData();
		INativeSearchService service = jiraService(system);
		List<String> received = new ArrayList<>();
		when(service.nativeSearch(any(), any(), anyInt(), any())).thenAnswer(invocation -> {
			received.add(((SearchToolContentPipelineTest.JqlQuery) invocation.getArgument(0)).getJql());
			return List.of(result("https://jira.example/ISSUE-1", "ISSUE-1"));
		});
		ToolCallback tool = SearchServiceDeepSearchTool.of(support, service, "deepSearchJira", "Jira").toTool();

		String schema = tool.getToolDefinition().inputSchema();
		assertTrue(schema.contains("\"jql\""), schema);
		assertTrue(schema.contains("\"queries\""), schema);
		assertTrue(schema.contains("\"depth\""), schema);

		String answer = tool.call("{\"queries\":[{\"jql\":\"project = GEBO\"},{\"jql\":\"type = Bug\"}],"
				+ "\"question\":\"which bugs are open?\"}", request("r1"));

		assertEquals(List.of("project = GEBO", "type = Bug"), received);
		assertTrue(answer.contains("\"status\":\"OK\""), answer);
		assertTrue(answer.contains("ISSUE-1"), answer);
		verify(service, never()).search(any(SearchQuery.class), any(SearchableSystemMetaData.class), anyInt(), any());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	void aRejectedNativeQueryIsSearchedAsText() throws Exception {
		everyResultIsOneChunk();
		SearchableSystemMetaData system = new SearchableSystemMetaData();
		INativeSearchService service = jiraService(system);
		when(service.nativeSearch(any(), any(), anyInt(), any())).thenThrow(new IllegalArgumentException("bad jql"));
		List<String> texts = new ArrayList<>();
		when(service.search(any(SearchQuery.class), any(SearchableSystemMetaData.class), anyInt(), any()))
				.thenAnswer(invocation -> {
					texts.add(((SearchQuery) invocation.getArgument(0)).getQueryText());
					return List.of(result("https://jira.example/ISSUE-2", "ISSUE-2"));
				});
		SearchServiceDeepSearchTool<SearchToolContentPipelineTest.JqlQuery> tool = (SearchServiceDeepSearchTool) SearchServiceDeepSearchTool
				.of(support, service, "deepSearchJira", "Jira");
		SearchToolContentPipelineTest.JqlQuery query = new SearchToolContentPipelineTest.JqlQuery();
		query.setJql("project = GEBO");
		DeepSearchToolParam<SearchToolContentPipelineTest.JqlQuery> param = new DeepSearchToolParam<>();
		param.setQuestion("which bugs are open?");
		param.setQueries(List.of(query));

		DeepSearchToolResult result = tool.deepSearch(param, request("r1"));

		assertEquals(Status.OK, result.getStatus());
		assertEquals(List.of("project = GEBO"), texts);

		// no native search given: the question is searched as text
		texts.clear();
		param.setQueries(List.of());
		assertEquals(Status.OK, tool.deepSearch(param, request("r2")).getStatus());
		assertEquals(List.of("which bugs are open?"), texts);
	}

	/** Every fragment found, by fragment id: {@code fragments} per document code. */
	private static Map<String, FoundDocument> foundFragments(Map<String, Integer> fragments) {
		Map<String, FoundDocument> found = new java.util.LinkedHashMap<>();
		int id = 0;
		for (Map.Entry<String, Integer> document : fragments.entrySet()) {
			for (int i = 0; i < document.getValue(); i++) {
				found.put("f" + (id++),
						new FoundDocument(new Source(document.getKey() + ".pdf", null, document.getKey()), null));
			}
		}
		return found;
	}

	/** The fragment ids of the given documents: the ones the analysis left unread. */
	private static Set<String> fragmentsOf(Map<String, FoundDocument> found, String... codes) {
		Set<String> ids = new java.util.HashSet<>();
		for (Map.Entry<String, FoundDocument> fragment : found.entrySet()) {
			if (List.of(codes).contains(fragment.getValue().source().getDocumentCode())) {
				ids.add(fragment.getKey());
			}
		}
		return ids;
	}

	private static List<FoundDocument> used(String... codes) {
		List<FoundDocument> used = new ArrayList<>();
		for (String code : codes) {
			used.add(new FoundDocument(new Source(code + ".pdf", null, code), null));
		}
		return used;
	}

	private static Map<String, Integer> documents(Object... codesAndFragments) {
		Map<String, Integer> fragments = new java.util.LinkedHashMap<>();
		for (int i = 0; i < codesAndFragments.length; i += 2) {
			fragments.put((String) codesAndFragments[i], (Integer) codesAndFragments[i + 1]);
		}
		return fragments;
	}

	private static final DeepSearchToolsSupport.CoverageRules DEFAULTS = DeepSearchToolsSupport.CoverageRules.DEFAULTS;

	@Test
	void documentsJudgedIrrelevantAreNotMissedAndTheSizeOfTheScopeIsOnlyTold() {
		// a large knowledge base: one contract holds the answer, the other documents found
		// were read and judged irrelevant
		Map<String, FoundDocument> found = foundFragments(documents("contract-a", 15, "policy-b", 4, "ticket-c", 1,
				"mail-d", 1, "manual-e", 2));

		DeepSearchCoverage coverage = AbstractDeepSearchTool.coverage(Depth.EXHAUSTIVE, found, Set.of(), Map.of(),
				used("contract-a"), null, 10000L, null, true, DEFAULTS);

		assertEquals(5, coverage.getDocumentsFound());
		assertEquals(1, coverage.getDocumentsUsed());
		assertEquals(0, coverage.getDocumentsUnread());
		assertEquals(9995, coverage.getNotReached(), "told");
		assertFalse(coverage.isCompletionRequired(), "neither the irrelevant documents nor the scope make it thin");
		assertNull(coverage.getNote());
	}

	@Test
	void documentsTheAnalysisLeftUnreadMakeTheCoverageThinAndAreNamed() {
		Map<String, FoundDocument> found = foundFragments(documents("contract-a", 5, "contract-b", 4, "annex-c", 3,
				"annex-d", 2));

		DeepSearchCoverage coverage = AbstractDeepSearchTool.coverage(Depth.EXHAUSTIVE, found,
				fragmentsOf(found, "annex-c", "annex-d"), Map.of(), used("contract-a", "contract-b"), null, null, null,
				false, DEFAULTS);

		assertEquals(2, coverage.getDocumentsUnread());
		assertTrue(coverage.isCompletionRequired());
		assertTrue(coverage.getNote().contains("2 of the 4 documents found were not read by the analysis: annex-c.pdf, "
				+ "annex-d.pdf"), coverage.getNote());
		assertFalse(coverage.getNote().contains("rests on"), "two documents used are enough");
		assertTrue(coverage.getDocuments().stream().anyMatch(d -> d.getName().equals("annex-c.pdf")
				&& d.getFragmentsAnalysed() == 0 && d.getFragmentsUnread() == 3 && !d.isUsedAsSource()));
	}

	@Test
	void sourcesReadInPartAreNamedForAFullReadWhereADocumentCanBeReadWhole() {
		Map<String, FoundDocument> found = foundFragments(documents("manual-a", 2, "contract-b", 1, "policy-c", 6,
				"faq-d", 1));
		Map<String, Long> lengths = Map.of("manual-a", 40L, "contract-b", 30L, "policy-c", 8L, "faq-d", 1L);
		List<FoundDocument> sources = used("manual-a", "contract-b", "policy-c", "faq-d");

		DeepSearchCoverage readable = AbstractDeepSearchTool.coverage(Depth.EXHAUSTIVE, found, Set.of(), lengths,
				sources, null, null, null, true, DEFAULTS);
		// 2 of 4 sources read in part is not more than half
		assertFalse(readable.isCompletionRequired(), readable.getNote());
		assertEquals(40, readable.getDocuments().get(0).getFragmentsInDocument());

		Map<String, Long> longer = Map.of("manual-a", 40L, "contract-b", 30L, "policy-c", 8L, "faq-d", 12L);
		DeepSearchCoverage inPart = AbstractDeepSearchTool.coverage(Depth.EXHAUSTIVE, found, Set.of(), longer, sources,
				null, null, null, true, DEFAULTS);
		assertTrue(inPart.isCompletionRequired());
		assertTrue(inPart.getNote().contains("3 of the 4 sources were read in part: manual-a.pdf (2 of 40 fragments), "
				+ "contract-b.pdf (1 of 30 fragments), faq-d.pdf (1 of 12 fragments)"), inPart.getNote());

		assertFalse(AbstractDeepSearchTool.coverage(Depth.EXHAUSTIVE, found, Set.of(), longer, sources, null, null, null,
				false, DEFAULTS).isCompletionRequired(), "a source that cannot be read whole is not judged");
		assertFalse(AbstractDeepSearchTool.coverage(Depth.EXHAUSTIVE, found, Set.of(), Map.of(), sources, null, null,
				null, true, DEFAULTS).isCompletionRequired(), "nor one whose length is not known");
	}

	@Test
	void anAnalysisRestingOnOneOfTheDocumentsItCouldUseIsThin() {
		Map<String, FoundDocument> found = foundFragments(documents("report-a", 6, "report-b", 3, "report-c", 3));

		DeepSearchCoverage oneOfThree = AbstractDeepSearchTool.coverage(Depth.BROAD, found,
				fragmentsOf(found, "report-b", "report-c"), Map.of(), used("report-a"), null, null, null, false,
				DEFAULTS);
		assertTrue(oneOfThree.getNote().contains("the analysis rests on 1 of the 3 documents it used or left unread"),
				oneOfThree.getNote());

		DeepSearchCoverage onlyOneRelevant = AbstractDeepSearchTool.coverage(Depth.BROAD, found, Set.of(), Map.of(),
				used("report-a"), null, null, null, false, DEFAULTS);
		assertFalse(onlyOneRelevant.isCompletionRequired(), "the others were read and judged irrelevant");
	}

	@Test
	void aPreciseAnswerIsNeverThinAndTheAnalysisVerdictCounts() {
		Map<String, FoundDocument> found = foundFragments(documents("page-1", 1, "page-2", 1, "page-3", 2));
		List<SearchCoverage> searches = List.of(new SearchCoverage("q1", 10, 2), new SearchCoverage("q2", 5, 0));

		DeepSearchCoverage focused = AbstractDeepSearchTool.coverage(Depth.FOCUSED, found,
				fragmentsOf(found, "page-2", "page-3"), Map.of(), used("page-1"), searches, null, "the figures", false,
				DEFAULTS);
		assertFalse(focused.isCompletionRequired());
		assertNull(focused.getNote());
		assertEquals(2, focused.getDocumentsUnread(), "the coverage is still reported");
		assertEquals(searches, focused.getSearches());

		DeepSearchCoverage missing = AbstractDeepSearchTool.coverage(Depth.BROAD, found, Set.of(), Map.of(),
				used("page-1", "page-2", "page-3"), searches, null, "the 2026 figures", false, DEFAULTS);
		assertTrue(missing.isCompletionRequired());
		assertTrue(missing.getNote().startsWith("The coverage of this deep search is thin: the analysis reports as "
				+ "missing: the 2026 figures."), missing.getNote());
	}

	@Test
	void manyDocumentsLeftUnreadAreNamedUpToFive() {
		Map<String, FoundDocument> found = foundFragments(documents("a", 2, "b", 1, "c", 1, "d", 1, "e", 1, "f", 1,
				"g", 1));

		DeepSearchCoverage coverage = AbstractDeepSearchTool.coverage(Depth.BROAD, found,
				fragmentsOf(found, "b", "c", "d", "e", "f", "g"), Map.of(), used("a"), null, null, null, false, DEFAULTS);

		assertTrue(coverage.getNote().contains("b.pdf, c.pdf, d.pdf, e.pdf, f.pdf and 1 more"), coverage.getNote());
	}

	@Test
	void withTheGateOffTheThinCoverageIsOnlyReportedAndTheRulesAreConfigurable() {
		Map<String, FoundDocument> found = foundFragments(documents("a", 6, "b", 1, "c", 1));
		Set<String> unread = fragmentsOf(found, "b", "c");
		DeepSearchToolsSupport.CoverageRules off = new DeepSearchToolsSupport.CoverageRules(false, 2, 3, 2, 0.5d);

		DeepSearchCoverage reported = AbstractDeepSearchTool.coverage(Depth.EXHAUSTIVE, found, unread, Map.of(),
				used("a"), null, null, null, false, off);
		assertFalse(reported.isCompletionRequired());
		assertTrue(reported.getNote() != null && reported.getNote().contains("rests on 1 of the 3"));

		DeepSearchToolsSupport.CoverageRules lenient = new DeepSearchToolsSupport.CoverageRules(true, 1, 3, 0, 0.5d);
		assertFalse(AbstractDeepSearchTool.coverage(Depth.EXHAUSTIVE, found, Set.of(), Map.of(), used("a"), null, 30L,
				null, true, lenient).isCompletionRequired(), "one document used is enough");

		support.setCoverageRules(true, -1, 3, 2, 7d);
		assertEquals(0, support.coverageRules().minDocumentsUsed());
		assertEquals(0.5d, support.coverageRules().barelyReadShare(), "a share out of 0..1 is the default");
	}

	@Test
	void theLengthOfADocumentIsReadFromItsFragments() {
		Document first = fragment("f1", "doc-a");
		first.getMetadata().put(DocumentMetaInfos.GEBO_CHUNKS_COUNT, 40L);
		Document second = fragment("f2", "doc-a");
		second.getMetadata().put(DocumentMetaInfos.GEBO_CHUNKS_COUNT, "40");
		Document unknown = fragment("f3", "doc-b");

		assertEquals(Map.of("doc-a", 40L), AbstractDeepSearchTool.documentLengths(List.of(first, second, unknown)));
	}

	@Test
	void theFragmentsTheAnalysisLeftUnreadAreNeverItsSources() {
		// the batch of doc-b failed: its fragments were never read
		doAnswer(invocation -> {
			Flux<Document> documents = invocation.getArgument(0);
			documents.collectList().block();
			DeepSearchAnalysisOutcome outcome = invocation.getArgument(9);
			outcome.getUnreadFragmentIds().add("f2");
			Vector<String> discarded = invocation.getArgument(7);
			discarded.add("f2");
			return Flux.just("The analysis.");
		}).when(analysis).analyze(any(), any(), any(), any(), anyString(), any(), any(), any(), any(), any());
		TestDeepSearchTool tool = new TestDeepSearchTool(support, List.of(fragment("f1", "doc-a"), fragment("f2", "doc-b")));

		DeepSearchToolResult result = tool.deepSearch(param("question"), request("r1"));

		assertEquals(Status.OK, result.getStatus());
		assertEquals(List.of("doc-a"), result.getSources().stream().map(Source::getDocumentCode).toList());
		assertEquals(1, result.getCoverage().getDocumentsUnread());
		assertTrue(result.getCoverage().isCompletionRequired(), result.getCoverage().getNote());
	}

	@Test
	void anAnalysisThatReadNothingIsAFailedDeepSearchWithoutSources() {
		// every batch failed: the text that came out is not an analysis of the documents
		doAnswer(invocation -> {
			Flux<Document> documents = invocation.getArgument(0);
			List<Document> read = documents.collectList().block();
			DeepSearchAnalysisOutcome outcome = invocation.getArgument(9);
			Vector<String> discarded = invocation.getArgument(7);
			for (Document document : read) {
				outcome.getUnreadFragmentIds().add(document.getId());
				discarded.add(document.getId());
			}
			return Flux.just("Written without documents.");
		}).when(analysis).analyze(any(), any(), any(), any(), anyString(), any(), any(), any(), any(), any());
		ToolsFoundDocuments collector = new ToolsFoundDocuments();
		TestDeepSearchTool tool = new TestDeepSearchTool(support, List.of(fragment("f1", "doc-a"), fragment("f3", "doc-a")));

		DeepSearchToolResult result = tool.deepSearch(param("question"), request("r1", collector));

		assertEquals(Status.FAILED, result.getStatus());
		assertEquals(AbstractDeepSearchTool.NOTHING_READ, result.getMessage());
		assertNull(result.getAnalysis());
		assertTrue(result.getSources().isEmpty());
		assertTrue(collector.getDocuments().isEmpty(), "no document shared as the answer's");
	}

	@Test
	void aDeepSearchRepeatingTheSearchesOfAnEarlierOneOfTheRequestIsRefusedWithoutCounting() {
		TestDeepSearchTool tool = new TestDeepSearchTool(support, List.of(fragment("f1", "doc-a")));
		assertEquals(Status.OK, tool.deepSearch(param("question", "open claims", "Claims  2025"), request("r1"))
				.getStatus());

		// the same searches, in another order, case and spacing
		DeepSearchToolResult repeated = tool.deepSearch(param("another question", "claims 2025", "OPEN claims"),
				request("r1"));
		assertEquals(Status.NOT_ALLOWED, repeated.getStatus());
		assertEquals(AbstractDeepSearchTool.REPEATED_SEARCHES, repeated.getMessage());
		verify(analysis, times(1)).analyze(any(), any(), any(), any(), anyString(), any(), any(), any(), any(), any());

		// other searches, another request, another tool run
		assertEquals(Status.OK, tool.deepSearch(param("question", "open claims"), request("r1")).getStatus());
		assertEquals(Status.OK, tool.deepSearch(param("question", "open claims", "claims 2025"), request("r2"))
				.getStatus());
		assertTrue(support.firstRunOf("r1", "deepSearchOther", List.of("claims 2025", "open claims")));
		assertTrue(support.firstRunOf(null, "deepSearchTest", List.of("open claims")), "an unknown request");
		// the refused one was not one of the request's deep searches
		for (int i = 2; i < support.maxDeepSearchesPerRequest(); i++) {
			assertEquals(Status.OK, tool.deepSearch(param("question " + i), request("r1")).getStatus());
		}
	}

	@Test
	void theCoverageComesBeforeTheAnalysisInTheResultTheModelReads() {
		DeepSearchToolResult result = new DeepSearchToolResult();
		result.setAnalysis("the analysis");
		result.setCoverage(new DeepSearchCoverage());
		String json = org.springframework.ai.util.json.JsonParser.toJson(result);

		assertTrue(json.indexOf("\"coverage\"") >= 0 && json.indexOf("\"coverage\"") < json.indexOf("\"analysis\""),
				json);
		assertTrue(json.indexOf("\"status\"") < json.indexOf("\"analysis\""), json);
		assertTrue(json.indexOf("\"completionRequired\"") < json.indexOf("\"documents\""), json);
	}
}
