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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.springframework.ai.document.Document;

import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.documents.access.StreamingPurpose;
import ai.gebo.architecture.documents.cache.config.GeboDocumentsCacheConfig;
import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.architecture.documents.cache.model.ChunkingPolicy;
import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.DocumentChunkingResponse;
import ai.gebo.architecture.documents.cache.model.TextChunkingSpecs;
import ai.gebo.architecture.documents.cache.repository.ChunkingSessionRepository;
import ai.gebo.architecture.documents.cache.repository.DocumentChunkOperationRepository;
import ai.gebo.architecture.documents.cache.service.IDocumentsCacheService;
import ai.gebo.architecture.documents.cache.service.impl.model.DocumentChunkOperation;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.search.service.IKeywordMatcherService;
import ai.gebo.config.service.IGGeboConfigService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.base.TypedInputStream;
import ai.gebo.system.ingestion.IGAIDocumentMetaDataEnricher;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler.IngestionHandlerData;
import reactor.core.scheduler.Schedulers;

/**
 * Pins the chunk positions to the document: a chunk a matching policy leaves out
 * keeps its place, so consecutive positions are contiguous text and the count is
 * the document's.
 */
class ChunkPositionsTest {

	@TempDir
	Path workDirectory;

	private DocumentsChunkServiceImpl service;
	private DocumentChunkOperationRepository operations;
	private GDocumentReference document;

	private DocumentChunkingResponse chunk(ChunkingPolicy policy) throws Exception {
		return chunk(policy, false);
	}

	/** Four pages, each one chunk; only the first and the third talk of the keyword. */
	private DocumentChunkingResponse chunk(ChunkingPolicy policy, boolean sampling) throws Exception {
		IDocumentsCacheService cacheService = mock(IDocumentsCacheService.class);
		when(cacheService.streamDocument(any(StreamingPurpose.class), any())).thenReturn(
				TypedInputStream.of(new ByteArrayInputStream("pages".getBytes()), "application/pdf", "pdf"));
		IGDocumentReferenceIngestionHandler ingestionHandler = mock(IGDocumentReferenceIngestionHandler.class);
		IngestionHandlerData pages = new IngestionHandlerData();
		pages.setStream(Stream.of(new Document("Page one speaks of the contract renewal terms."),
				new Document("Page two is about something else entirely."),
				new Document("Page three again speaks of the contract penalties."),
				new Document("Page four closes the document with signatures.")));
		when(ingestionHandler.handleContent(any(GDocumentReference.class), any(TypedInputStream.class))).thenReturn(pages);
		IKeywordMatcherService matcher = mock(IKeywordMatcherService.class);
		when(matcher.isMatching(anyList(), anyString(), anyInt()))
				.thenAnswer(call -> ((String) call.getArgument(1)).contains("contract"));
		IGGeboConfigService configService = mock(IGGeboConfigService.class);
		when(configService.getGeboWorkDirectory()).thenReturn(workDirectory.toString());
		IGeboThreadManager threads = mock(IGeboThreadManager.class);
		when(threads.getScheduler()).thenReturn(Schedulers.immediate());
		operations = mock(DocumentChunkOperationRepository.class);
		when(operations.findByOriginalDocumentCode(anyString())).thenReturn(List.of());

		service = new DocumentsChunkServiceImpl(cacheService, configService, operations,
				mock(IGAIDocumentMetaDataEnricher.class), ingestionHandler, mock(IGDocumentReferenceFactory.class),
				threads, mock(IGPersistentObjectManager.class), mock(GeboDocumentsCacheConfig.class),
				mock(ChunkingSessionRepository.class), operations, matcher);

		ChunkingParams params = new ChunkingParams();
		params.setChunkingPolicy(policy);
		params.setMatchingKeywords(List.of("contract"));
		params.setChunkingSpecs(List.of(TextChunkingSpecs.of(512, TextChunkingSpecs.MIN_CHUNKS_LENGTH_TO_EMBED, 100)));
		params.setTokensPerChunkSet(50000);
		if (sampling) {
			params.setSamplingMode(true);
			params.setSampledTokens(512);
		}
		document = new GDocumentReference();
		document.setCode("contracts.pdf");
		return service.getChunkSet(document, params, null);
	}

	private static List<Long> positions(DocumentChunkingResponse response) {
		return response.getCurrentChunkSet().getChunks().stream().map(DocumentChunk::getChunkPosition).toList();
	}

	@Test
	void aChunkLeftOutByTheKeywordsKeepsItsPlace() throws Exception {
		DocumentChunkingResponse response = chunk(ChunkingPolicy.ONLY_MATCHING_CHUNKS);

		assertEquals(List.of(1l, 3l), positions(response), "pages one and three, not one and two");
		for (DocumentChunk chunk : response.getCurrentChunkSet().getChunks()) {
			assertEquals(4l, chunk.getChunksCount(), "the count of the document's chunks");
			assertEquals(4l, chunk.getMetaData().get(DocumentMetaInfos.GEBO_CHUNKS_COUNT));
		}
	}

	@Test
	void everyChunkKeptHasTheSamePositionsAsBefore() throws Exception {
		DocumentChunkingResponse response = chunk(ChunkingPolicy.SPLIT_CHUNKS);

		assertEquals(List.of(1l, 2l, 3l, 4l), positions(response), "the indexing policy is unchanged");
		assertEquals(4l, response.getCurrentChunkSet().getChunks().get(0).getChunksCount());
	}

	@Test
	void aSampleIsOneChunkOfOneWhenReturnedAndWhenReadAgain() throws Exception {
		// as the deep search pure search samples the documents found
		DocumentChunkingResponse response = chunk(ChunkingPolicy.ONLY_MATCHING_CHUNKS, true);

		assertEquals(List.of(1l), positions(response));
		assertEquals(1l, response.getCurrentChunkSet().getChunks().get(0).getChunksCount());

		ArgumentCaptor<DocumentChunkOperation> saved = ArgumentCaptor.forClass(DocumentChunkOperation.class);
		verify(operations).insert(saved.capture());
		when(operations.findById(saved.getValue().getId())).thenReturn(Optional.of(saved.getValue()));
		DocumentChunkingResponse again = service.getNextChunkSet(document, saved.getValue().getId(),
				saved.getValue().getChunkSetsList().get(0), null);

		assertEquals(1l, again.getCurrentChunkSet().getChunks().get(0).getChunksCount(),
				"the cached sample is still one chunk of one");
	}
}
