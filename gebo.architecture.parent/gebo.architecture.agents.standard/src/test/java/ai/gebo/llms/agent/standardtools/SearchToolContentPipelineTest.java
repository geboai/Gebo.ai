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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.document.Document;
import org.springframework.ai.tool.ToolCallback;
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
import ai.gebo.architecture.search.service.INativeQueryObject;
import ai.gebo.architecture.search.service.INativeSearchService;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.agent.standardtools.model.SearchQueryParam;
import ai.gebo.llms.agent.standardtools.model.SearchToolResult;
import ai.gebo.llms.agent.standardtools.model.SearchToolResult.Status;
import ai.gebo.llms.chat.abstraction.layer.services.IGRankerService;
import ai.gebo.llms.deepsearch.service.IGExternalSearchSecurityService;
import ai.gebo.model.DocumentMetaInfos;
import reactor.core.publisher.Flux;

/**
 * Pins the search tools: contents ranked against the search objective, fitted in
 * the requested size, a document never returned twice in the same request, the
 * user's permission checked, and a failing system not stopping the others.
 */
class SearchToolContentPipelineTest {
	private IDocumentsChunkService chunkingService;
	private IGRankerService ranker;
	private IGExternalSearchSecurityService security;
	private SearchToolContentPipeline pipeline;
	@SuppressWarnings("rawtypes")
	private ISearchService service;
	private SearchableSystemMetaData system;

	@SuppressWarnings("unchecked")
	private static <T> ObjectProvider<T> provider(T value) {
		ObjectProvider<T> provider = mock(ObjectProvider.class);
		when(provider.getObject()).thenReturn(value);
		when(provider.getIfAvailable()).thenReturn(value);
		return provider;
	}

	private static String words(int count, String prefix) {
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < count; i++) {
			text.append(prefix).append(i % 97).append(' ');
		}
		return text.toString();
	}

	private static SearchResult result(String url, String title) {
		SearchResult result = new SearchResult();
		result.setResultReference(new SearchResultReference());
		result.getResultReference().setUri(url);
		result.getResultReference().setName(title);
		result.setSystemConfigurationCode("web");
		return result;
	}

	private static ToolContext request(String requestId) {
		return new ToolContext(Map.of(ToolCallbackDeclarationUtil.REQUEST_ID_CONTEXT_KEY, requestId));
	}

	private static SearchQueryParam param(String query, String objective) {
		SearchQueryParam param = new SearchQueryParam();
		param.setQuery(query);
		param.setSearchObjective(objective);
		return param;
	}

	@SuppressWarnings("unchecked")
	@BeforeEach
	void setUp() throws Exception {
		chunkingService = mock(IDocumentsChunkService.class);
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
		ranker = mock(IGRankerService.class);
		when(ranker.isRankerConfigured()).thenReturn(true);
		when(ranker.call(anyList(), anyString(), anyInt())).thenAnswer(invocation -> invocation.getArgument(0));
		security = mock(IGExternalSearchSecurityService.class);
		when(security.isEnabledForCurrentUser(any())).thenReturn(true);
		pipeline = new SearchToolContentPipeline(provider(chunkingService), provider(ranker), provider(security),
				new SearchToolsRequestRegistry());
		service = mock(ISearchService.class);
		when(service.getId()).thenReturn("web-service");
		system = new SearchableSystemMetaData();
		system.setCode("web");
		when(service.getSearchableSystems()).thenReturn(List.of(system));
	}

	@Test
	void rankedAgainstTheObjectiveAndTheDiscardedOnesDropped() throws Exception {
		SearchResult kept = result("https://a.example/kept", "Kept");
		SearchResult discarded = result("https://a.example/noise", "Noise");
		// the ranker keeps only the first document: the other one does not serve the objective
		when(ranker.call(anyList(), eq("what the release changed"), eq(8)))
				.thenAnswer(invocation -> List.of(((List<Document>) invocation.getArgument(0)).get(0)));

		SearchToolResult result = pipeline.run(service, "searchWeb", "d", param("release notes", "what the release changed"),
				List.of(), (s, n) -> List.of(kept, discarded), request("r1"));

		assertEquals(Status.OK, result.getStatus());
		assertTrue(result.isRanked());
		assertEquals(1, result.getFragments().size());
		assertEquals("Kept", result.getFragments().get(0).getTitle());
		assertEquals("https://a.example/kept", result.getFragments().get(0).getSource());
		assertEquals(kept.getCode(), result.getFragments().get(0).getDocumentCode());
	}

	@Test
	void theReturnedDocumentsAreSharedWithTheCallingAgent() throws Exception {
		SearchResult kept = result("https://a.example/kept", "Kept");
		SearchResult discarded = result("https://a.example/noise", "Noise");
		when(ranker.call(anyList(), anyString(), anyInt()))
				.thenAnswer(invocation -> List.of(((List<Document>) invocation.getArgument(0)).get(0)));
		ToolsFoundDocuments collector = new ToolsFoundDocuments();
		ToolContext shared = new ToolContext(collector
				.sharedThrough(IChatRequestContext.builder().requestID("r1")
						.toolsContext(Map.of(ToolCallbackDeclarationUtil.REQUEST_ID_CONTEXT_KEY, "r1")).build())
				.getToolsContext());

		pipeline.run(service, "searchWeb", "d", param("release notes", "what changed"), List.of(),
				(s, n) -> List.of(kept, discarded), shared);

		// only the document whose content was returned, with its search result
		assertEquals(1, collector.getDocuments().size());
		assertEquals(kept.getCode(), collector.getDocuments().get(0).getDocumentCode());
		assertTrue(collector.getDocuments().get(0).getNestedSearchResult() != null);
	}

	@Test
	void theQueryIsTheObjectiveWhenNoneIsGiven() throws Exception {
		pipeline.run(service, "searchWeb", "d", param("release notes", null), List.of(),
				(s, n) -> List.of(result("https://a.example/1", "One")), request("r1"));

		verify(ranker).call(anyList(), eq("release notes"), anyInt());
	}

	@Test
	void aDocumentIsNeverReturnedTwiceInTheSameRequest() throws Exception {
		SearchResult first = result("https://a.example/1", "One");
		SearchResult second = result("https://a.example/2", "Two");
		SearchResult third = result("https://a.example/3", "Three");

		SearchToolResult firstCall = pipeline.run(service, "searchWeb", "d", param("q", "o"), List.of(),
				(s, n) -> List.of(first, second), request("r1"));
		assertEquals(2, firstCall.getFragments().size());

		SearchToolResult retry = pipeline.run(service, "searchWeb", "d", param("q refined", "o"), List.of(),
				(s, n) -> List.of(second, first), request("r1"));
		assertEquals(Status.NO_RESULTS, retry.getStatus());
		assertEquals(2, retry.getDocumentsAlreadyReturned());
		assertTrue(retry.getFragments().isEmpty());

		SearchToolResult widened = pipeline.run(service, "searchWeb", "d", param("q other", "o"), List.of(),
				(s, n) -> List.of(first, third), request("r1"));
		assertEquals(1, widened.getFragments().size());
		assertEquals(third.getCode(), widened.getFragments().get(0).getDocumentCode());
		assertEquals(1, widened.getDocumentsAlreadyReturned());

		// another request starts from scratch
		SearchToolResult otherRequest = pipeline.run(service, "searchWeb", "d", param("q", "o"), List.of(),
				(s, n) -> List.of(first, second), request("r2"));
		assertEquals(2, otherRequest.getFragments().size());
	}

	@Test
	void theSameResultFoundTwiceInOneCallIsReturnedOnce() throws Exception {
		SearchResult first = result("https://a.example/1", "One");
		SearchResult sameAgain = result("https://a.example/1", "One");

		SearchToolResult result = pipeline.run(service, "searchWeb", "d", param("q", "o"), List.of(),
				(s, n) -> List.of(first, sameAgain), request("r1"));

		assertEquals(1, result.getFragments().size());
	}

	@Test
	void theContentsFitTheRequestedSize() throws Exception {
		List<Document> big = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			big.add(new Document(words(600, "w" + i), Map.of(DocumentMetaInfos.CONTENT_CODE, "code" + i)));
		}

		SearchToolResult result = pipeline.fit(big, List.of(), 1000, "searchWeb");

		assertTrue(result.getTokens() <= 1000 + ITokensCountable.stringsTokensSize(" [...]"),
				"returned " + result.getTokens() + " tokens");
		assertFalse(result.getFragments().isEmpty());
		assertTrue(result.getFragments().size() < big.size());
	}

	@Test
	void aUserNotAllowedGetsNothingAndNoSearchRuns() throws Exception {
		when(security.isEnabledForCurrentUser(any())).thenReturn(false);
		List<String> searched = new ArrayList<>();

		SearchToolResult result = pipeline.run(service, "jiraNativeSearch", "d", param("q", "o"), List.of(),
				(s, n) -> {
					searched.add(s.getCode());
					return List.of(result("https://a.example/1", "One"));
				}, request("r1"));

		assertEquals(Status.NOT_ALLOWED, result.getStatus());
		assertTrue(searched.isEmpty());
		verify(chunkingService, never()).streamChunks(anyList(), any(), anyString(), anyInt());
	}

	@Test
	void aFailingSystemDoesNotStopTheOthers() throws Exception {
		SearchableSystemMetaData broken = new SearchableSystemMetaData();
		broken.setCode("broken");
		when(service.getSearchableSystems()).thenReturn(List.of(broken, system));

		SearchToolResult result = pipeline.run(service, "confluenceNativeSearch", "d", param("q", "o"), List.of(),
				(s, n) -> {
					if (s == broken) {
						throw new java.io.IOException("down");
					}
					return List.of(result("https://a.example/1", "One"));
				}, request("r1"));

		assertEquals(Status.PARTIAL, result.getStatus());
		assertEquals(1, result.getFragments().size());
	}

	@Test
	void withoutRankerTheContentsComeInSearchOrder() throws Exception {
		when(ranker.isRankerConfigured()).thenReturn(false);

		SearchToolResult result = pipeline.run(service, "searchWeb", "d", param("q", "o"), List.of(),
				(s, n) -> List.of(result("https://a.example/1", "One"), result("https://a.example/2", "Two")),
				request("r1"));

		assertFalse(result.isRanked());
		assertEquals("One", result.getFragments().get(0).getTitle());
		verify(ranker, never()).call(anyList(), anyString(), anyInt());
	}

	@SuppressWarnings("unchecked")
	@Test
	void thePlainToolParsesTheModelArguments() throws Exception {
		when(service.search(any(SearchQuery.class), any(SearchableSystemMetaData.class), anyInt()))
				.thenReturn(List.of(result("https://a.example/1", "One")));
		ToolCallback tool = new SearchServiceWrapperTool(pipeline, service, "searchWeb", "Search the web").toTool();

		String answer = tool.call("{\"query\":\"release notes\",\"searchObjective\":\"what changed\",\"topK\":3}",
				request("r1"));

		assertTrue(answer.contains("\"status\":\"OK\""), answer);
		assertTrue(answer.contains("https://a.example/1"), answer);
		verify(ranker).call(anyList(), eq("what changed"), eq(3));
	}

	/** A native query structure, as a native search service declares one. */
	public static class JqlQuery implements INativeQueryObject {
		private String jql;

		public String getJql() {
			return jql;
		}

		public void setJql(String jql) {
			this.jql = jql;
		}

		@Override
		public List<String> relevantKeywords() {
			return jql != null ? List.of(jql) : List.of();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	void theNativeToolSchemaAndArgumentsResolveTheNativeQueryType() throws Exception {
		INativeSearchService nativeService = mock(INativeSearchService.class);
		when(nativeService.getId()).thenReturn("jira-service");
		when(nativeService.getNativeSearchDataStructureType()).thenReturn(JqlQuery.class);
		when(nativeService.getSearchableSystems()).thenReturn(List.of(system));
		List<Object> received = new ArrayList<>();
		when(nativeService.nativeSearch(any(), any(), anyInt())).thenAnswer(invocation -> {
			received.add(invocation.getArgument(0));
			return List.of(result("https://jira.example/ISSUE-1", "ISSUE-1"));
		});
		ToolCallback tool = new NativeSearchServiceWrapperTool(pipeline, nativeService, "jiraNativeSearch",
				"Search Jira").toTool();

		String schema = tool.getToolDefinition().inputSchema();
		assertTrue(schema.contains("\"jql\""), schema);
		assertTrue(schema.contains("\"searchObjective\""), schema);
		assertTrue(schema.contains("\"maxTokens\""), schema);

		String answer = tool.call("{\"query\":{\"jql\":\"project = GEBO\"},\"searchObjective\":\"open bugs\"}",
				request("r1"));

		assertEquals(1, received.size());
		assertTrue(received.get(0) instanceof JqlQuery);
		assertEquals("project = GEBO", ((JqlQuery) received.get(0)).getJql());
		assertTrue(answer.contains("ISSUE-1"), answer);
	}
}
