/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.documents.cache.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.ai.document.Document;

import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.documents.access.StreamingPurpose;
import ai.gebo.architecture.documents.cache.config.GeboDocumentsCacheConfig;
import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.architecture.documents.cache.model.ChunkingPolicy;
import ai.gebo.architecture.documents.cache.model.DocumentChunkingResponse;
import ai.gebo.architecture.documents.cache.model.TextChunkingSpecs;
import ai.gebo.architecture.documents.cache.repository.ChunkingSessionRepository;
import ai.gebo.architecture.documents.cache.repository.DocumentChunkOperationRepository;
import ai.gebo.architecture.documents.cache.service.IDocumentsCacheService;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.search.service.IKeywordMatcherService;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.base.TypedInputStream;
import ai.gebo.system.ingestion.IGAIDocumentMetaDataEnricher;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler.IngestionHandlerData;
import ai.gebo.system.ingestion.IGLanguageDetector;
import reactor.core.scheduler.Schedulers;

/**
 * Pins when a chunked document is empty: when none of its pages gave a chunk, not when
 * its last page gave none (a page number alone, under the least chunk length): such a
 * document was taken for an empty file and never indexed.
 */
class DocumentEmptinessTest {

	@TempDir
	Path workDirectory;

	private DocumentChunkingResponse chunk(String... pages) throws Exception {
		IDocumentsCacheService cacheService = mock(IDocumentsCacheService.class);
		when(cacheService.streamDocument(any(StreamingPurpose.class), any())).thenAnswer(
				call -> TypedInputStream.of(new ByteArrayInputStream("pages".getBytes()), "application/pdf", "pdf"));
		IGDocumentReferenceIngestionHandler ingestionHandler = mock(IGDocumentReferenceIngestionHandler.class);
		when(ingestionHandler.handleContent(any(GDocumentReference.class), any(TypedInputStream.class)))
				.thenAnswer(call -> {
					IngestionHandlerData data = new IngestionHandlerData();
					data.setStream(Stream.of(pages).map(Document::new));
					return data;
				});
		IGGeboConfigService configService = mock(IGGeboConfigService.class);
		when(configService.getGeboWorkDirectory()).thenReturn(workDirectory.toString());
		IGeboThreadManager threads = mock(IGeboThreadManager.class);
		when(threads.getScheduler()).thenReturn(Schedulers.immediate());
		DocumentChunkOperationRepository operations = mock(DocumentChunkOperationRepository.class);
		when(operations.findByOriginalDocumentCode(anyString())).thenReturn(List.of());
		DocumentsChunkServiceImpl service = new DocumentsChunkServiceImpl(cacheService, configService, operations,
				mock(IGAIDocumentMetaDataEnricher.class), ingestionHandler, mock(IGDocumentReferenceFactory.class),
				threads, mock(IGPersistentObjectManager.class), mock(GeboDocumentsCacheConfig.class),
				mock(ChunkingSessionRepository.class), operations, mock(IKeywordMatcherService.class),
				mock(IGLanguageDetector.class));
		ChunkingParams params = new ChunkingParams();
		params.setChunkingPolicy(ChunkingPolicy.SPLIT_CHUNKS);
		params.setChunkingSpecs(List.of(TextChunkingSpecs.of(512, TextChunkingSpecs.MIN_CHUNKS_LENGTH_TO_EMBED, 100)));
		params.setTokensPerChunkSet(50000);
		GDocumentReference document = new GDocumentReference();
		document.setCode("book.pdf");
		return service.getChunkSet(document, params, null);
	}

	@Test
	void aLastPageGivingNoChunkDoesNotEmptyTheDocument() throws Exception {
		final DocumentChunkingResponse response = chunk(
				"The first page of the book speaks at length of reincarnation and of the christian view of it.",
				"The second page goes on with the same subject, quoting the conferences of the author.", "7");

		assertFalse(response.isEmpty(), "two pages gave their chunks");
		assertEquals(2, response.getCurrentChunkSet().getChunks().size());
	}

	@Test
	void aDocumentWhosePagesGiveNoChunkIsEmpty() throws Exception {
		assertTrue(chunk("7", "8").isEmpty());
	}
}
