/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standard.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.IDocumentChunkWithRef;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import reactor.core.publisher.Flux;

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
}
