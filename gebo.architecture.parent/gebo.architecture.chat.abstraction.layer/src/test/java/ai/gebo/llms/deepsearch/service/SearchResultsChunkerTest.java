/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.deepsearch.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.IDocumentChunkWithRef;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.search.config.OpenNetworkLoadingConfig;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.architecture.search.model.SearchResultsLoading;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Pins the best effort loading of the search results: a document that cannot be
 * loaded (a site refusing the connection, failing before streaming, or not
 * answering) is skipped, the others are kept.
 */
class SearchResultsChunkerTest {

	private static SearchResult result(String url) {
		SearchResult result = new SearchResult();
		result.setResultReference(new SearchResultReference());
		result.getResultReference().setUri(url);
		result.setSystemConfigurationCode("web");
		return result;
	}

	private static Flux<IDocumentChunkWithRef> oneChunk(SearchResult result) {
		DocumentChunk chunk = DocumentChunk.ofText(result.getCode(), "content of " + result.getCode(), Map.of());
		chunk.setChunkPosition(1l);
		return Flux.just(IDocumentChunkWithRef.of(chunk, result));
	}

	@Test
	void everyDocumentIsLoadedAsTheUser() {
		List<SearchResult> results = new java.util.ArrayList<>();
		for (int i = 0; i < 10; i++) {
			results.add(result("https://a.example/" + i));
		}
		IDocumentsChunkService chunkingService = mock(IDocumentsChunkService.class);
		when(chunkingService.createChunkingSession(anyString())).thenReturn("session");
		List<String> loadedAs = java.util.Collections.synchronizedList(new java.util.ArrayList<>());
		when(chunkingService.streamChunks(any(IGComponentOriginatedDocument.class), any(), anyString()))
				.thenAnswer(invocation -> {
					Authentication current = SecurityContextHolder.getContext().getAuthentication();
					loadedAs.add(current != null ? current.getName() : "nobody");
					// the document completes on another thread, as the chunking scheduler does
					return oneChunk(invocation.getArgument(0)).delayElements(Duration.ofMillis(20),
							Schedulers.boundedElastic());
				});
		ai.gebo.architecture.documents.cache.model.ChunkingParams params = SearchResultsChunker
				.buildChunkingParams(4096, 4, List.of());
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("user", "", List.of()));
		try {
			List<Document> documents = SearchResultsChunker.chunkToDocuments(chunkingService, results, params, 4,
					"test");

			assertEquals(10, documents.size());
			assertEquals(java.util.Collections.nCopies(10, "user"), loadedAs);
		} finally {
			SecurityContextHolder.clearContext();
		}
	}

	@Test
	void aDocumentThatCannotBeLoadedIsSkipped() {
		SearchResult good = result("https://a.example/good");
		SearchResult refused = result("https://a.example/refused");
		SearchResult thrown = result("https://a.example/thrown");
		SearchResult hanging = result("https://a.example/hanging");
		IDocumentsChunkService chunkingService = mock(IDocumentsChunkService.class);
		when(chunkingService.createChunkingSession(anyString())).thenReturn("session");
		when(chunkingService.streamChunks(any(IGComponentOriginatedDocument.class), any(), anyString()))
				.thenAnswer(invocation -> {
					SearchResult result = invocation.getArgument(0);
					if (result == refused) {
						return Flux.error(new java.net.ConnectException("Connection refused"));
					}
					if (result == thrown) {
						throw new IllegalStateException("no such session");
					}
					if (result == hanging) {
						return Flux.never();
					}
					return oneChunk(result);
				});
		ai.gebo.architecture.documents.cache.model.ChunkingParams params = SearchResultsChunker
				.buildChunkingParams(4096, 4, List.of());

		List<Document> documents = SearchResultsChunker.chunkToDocuments(chunkingService,
				List.of(refused, good, thrown, hanging), params, 4, "test", Duration.ofMillis(300));

		assertEquals(1, documents.size());
		assertEquals(good.getCode(), documents.get(0).getMetadata().get(DocumentMetaInfos.CONTENT_CODE));
		verify(chunkingService).disposeChunkingSession("session");
	}

	private static OpenNetworkLoadingConfig openNetwork(int perPageSeconds, int phaseSeconds, int perHost, long pauseMillis,
			int failuresBeforeSkip) {
		OpenNetworkLoadingConfig config = new OpenNetworkLoadingConfig();
		config.setPerPageDeadlineSeconds(perPageSeconds);
		config.setLoadingPhaseDeadlineSeconds(phaseSeconds);
		config.setPerHostConcurrency(perHost);
		config.setPerHostPauseMillis(pauseMillis);
		config.setPerHostFailuresBeforeSkip(failuresBeforeSkip);
		return config;
	}

	private static Map<String, String> reasons(SearchResultsChunker.LoadedResults loaded) {
		Map<String, String> reasons = new java.util.LinkedHashMap<>();
		for (SearchResultsChunker.NotLoaded missing : loaded.notLoaded()) {
			reasons.put(missing.result().getResultReference().getUri(), missing.reason());
		}
		return reasons;
	}

	@Test
	void anOpenNetworkTellsEveryPageNotLoadedWithWhyAndStopsAskingAFailingSite() {
		SearchResult refused = result("https://a.example/refused");
		SearchResult thrown = result("https://a.example/thrown");
		SearchResult skipped = result("https://a.example/skipped");
		SearchResult hanging = result("https://b.example/hanging");
		SearchResult broken = result("https://c.example/broken");
		SearchResult empty = result("https://c.example/empty");
		SearchResult good = result("https://d.example/good");
		List<SearchResult> requested = java.util.Collections.synchronizedList(new java.util.ArrayList<>());
		IDocumentsChunkService chunkingService = mock(IDocumentsChunkService.class);
		when(chunkingService.createChunkingSession(anyString())).thenReturn("session");
		when(chunkingService.streamChunks(any(IGComponentOriginatedDocument.class), any(), anyString()))
				.thenAnswer(invocation -> {
					SearchResult result = invocation.getArgument(0);
					requested.add(result);
					if (result == refused) {
						return Flux.error(new java.net.ConnectException("Connection refused"));
					}
					if (result == thrown) {
						throw new IllegalStateException("no such session");
					}
					if (result == hanging) {
						return Flux.never();
					}
					if (result == broken) {
						return Flux.just(IDocumentChunkWithRef.ofError(result, "Error while loading resource",
								new java.io.IOException("HTTP 403")));
					}
					if (result == empty) {
						return Flux.empty();
					}
					return oneChunk(result);
				});
		ai.gebo.architecture.documents.cache.model.ChunkingParams params = SearchResultsChunker
				.buildChunkingParams(4096, 4, List.of());

		SearchResultsChunker.LoadedResults loaded = SearchResultsChunker.load(chunkingService,
				List.of(refused, thrown, skipped, hanging, broken, empty, good), params, 4, "test", 2,
				SearchResultsLoading.OPEN_NETWORK, openNetwork(1, 5, 1, 0, 2));

		assertEquals(List.of(good.getCode()),
				loaded.documents().stream().map(d -> d.getMetadata().get(DocumentMetaInfos.CONTENT_CODE)).toList());
		Map<String, String> reasons = reasons(loaded);
		assertEquals(6, reasons.size(), reasons.toString());
		assertEquals("could not be loaded: Connection refused", reasons.get("https://a.example/refused"));
		assertEquals("could not be loaded: no such session", reasons.get("https://a.example/thrown"));
		assertEquals("not requested: its site a.example did not answer for 2 of its pages",
				reasons.get("https://a.example/skipped"));
		assertEquals("not loaded within 1 s", reasons.get("https://b.example/hanging"));
		assertTrue(reasons.get("https://c.example/broken").startsWith("could not be loaded: Error while loading"),
				reasons.get("https://c.example/broken"));
		assertEquals(SearchResultsChunker.NO_READABLE_CONTENT, reasons.get("https://c.example/empty"));
		assertFalse(requested.contains(skipped), "a site failing twice is not asked again");
		verify(chunkingService).disposeChunkingSession("session");
	}

	@Test
	void anOpenNetworkLoadsTheCappedCandidatesAndLeavesWhatTheLoadingPhaseDidNotReach() {
		SearchResult slow = result("https://a.example/slow");
		SearchResult queued = result("https://a.example/queued");
		SearchResult fast = result("https://b.example/fast");
		SearchResult beyond = result("https://c.example/beyond");
		IDocumentsChunkService chunkingService = mock(IDocumentsChunkService.class);
		when(chunkingService.createChunkingSession(anyString())).thenReturn("session");
		when(chunkingService.streamChunks(any(IGComponentOriginatedDocument.class), any(), anyString()))
				.thenAnswer(invocation -> {
					SearchResult result = invocation.getArgument(0);
					return result == fast ? oneChunk(result) : Flux.never();
				});
		OpenNetworkLoadingConfig config = openNetwork(10, 1, 1, 0, 2);
		config.setCapped(true);
		config.setCandidatesCap(3);

		long start = System.currentTimeMillis();
		SearchResultsChunker.LoadedResults loaded = SearchResultsChunker.load(chunkingService,
				List.of(slow, queued, fast, beyond), SearchResultsChunker.buildChunkingParams(4096, 4, List.of()), 4,
				"test", 2, SearchResultsLoading.OPEN_NETWORK, config);
		long elapsed = System.currentTimeMillis() - start;

		assertEquals(1, loaded.documents().size());
		Map<String, String> reasons = reasons(loaded);
		assertEquals("not loaded: still loading when the loading phase ended after 1 s",
				reasons.get("https://a.example/slow"));
		assertEquals("not requested: the loading phase ended after 1 s", reasons.get("https://a.example/queued"));
		assertEquals("not loaded: only the first 3 results found are loaded", reasons.get("https://c.example/beyond"));
		assertTrue(elapsed < 5000, "the loading phase ends the loading: " + elapsed + " ms");
	}

	@Test
	void theRequestsToTheSameSiteArePausedAndTheSitesLoadedTogether() {
		List<SearchResult> results = List.of(result("https://a.example/1"), result("https://a.example/2"),
				result("https://a.example/3"), result("https://b.example/1"));
		Map<String, Long> startedAt = new java.util.concurrent.ConcurrentHashMap<>();
		IDocumentsChunkService chunkingService = mock(IDocumentsChunkService.class);
		when(chunkingService.createChunkingSession(anyString())).thenReturn("session");
		when(chunkingService.streamChunks(any(IGComponentOriginatedDocument.class), any(), anyString()))
				.thenAnswer(invocation -> {
					SearchResult result = invocation.getArgument(0);
					startedAt.put(result.getResultReference().getUri(), System.currentTimeMillis());
					return oneChunk(result);
				});

		SearchResultsChunker.LoadedResults loaded = SearchResultsChunker.load(chunkingService, results,
				SearchResultsChunker.buildChunkingParams(4096, 4, List.of()), 4, "test", 2,
				SearchResultsLoading.OPEN_NETWORK, openNetwork(5, 10, 2, 300, 2));

		assertEquals(4, loaded.documents().size());
		assertTrue(loaded.notLoaded().isEmpty());
		long a1 = startedAt.get("https://a.example/1");
		assertTrue(startedAt.get("https://a.example/2") - a1 >= 250, "paused: " + startedAt);
		assertTrue(startedAt.get("https://a.example/3") - startedAt.get("https://a.example/2") >= 250,
				"paused: " + startedAt);
		assertTrue(Math.abs(startedAt.get("https://b.example/1") - a1) < 250, "the other site at once: " + startedAt);
	}

	@Test
	void theReliableLoadingAlsoTellsWhyADocumentGaveNothing() {
		SearchResult good = result("https://a.example/good");
		SearchResult refused = result("https://a.example/refused");
		SearchResult empty = result("https://a.example/empty");
		IDocumentsChunkService chunkingService = mock(IDocumentsChunkService.class);
		when(chunkingService.createChunkingSession(anyString())).thenReturn("session");
		when(chunkingService.streamChunks(any(IGComponentOriginatedDocument.class), any(), anyString()))
				.thenAnswer(invocation -> {
					SearchResult result = invocation.getArgument(0);
					if (result == refused) {
						return Flux.error(new java.net.ConnectException("Connection refused"));
					}
					return result == empty ? Flux.empty() : oneChunk(result);
				});

		SearchResultsChunker.LoadedResults loaded = SearchResultsChunker.load(chunkingService,
				List.of(good, refused, empty), SearchResultsChunker.buildChunkingParams(4096, 4, List.of()), 4, "test",
				2, SearchResultsLoading.RELIABLE, null);

		assertEquals(1, loaded.documents().size());
		assertEquals(Map.of("https://a.example/refused", "could not be loaded: Connection refused",
				"https://a.example/empty", SearchResultsChunker.NO_READABLE_CONTENT), reasons(loaded));
	}

	@Test
	void theHostOfAResultIsReadFromItsAddress() {
		assertEquals("www.example.org", SearchResultsChunker.hostOf(result("https://WWW.Example.org/a/b?c=d")));
		assertEquals("", SearchResultsChunker.hostOf(result("not an address")));
		assertEquals("", SearchResultsChunker.hostOf(new SearchResult()));
	}
}
