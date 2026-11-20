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
import static org.junit.jupiter.api.Assertions.assertNull;
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
import java.util.Map;

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
import ai.gebo.system.ingestion.ContentHash;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler.IngestionHandlerData;
import ai.gebo.system.ingestion.IGLanguageDetector;
import reactor.core.scheduler.Schedulers;

/**
 * The media of a document the readers give (an image, as a document holding it)
 * are returned by the chunking as they are, whole, each a chunk of its kind,
 * only when that kind was asked beside the texts; the texts are chunked as
 * always.
 */
class MediaChunksTest {

	@TempDir
	Path workDirectory;

	private static final byte[] IMAGE = new byte[] { 1, 2, 3, 4, 5 };

	private DocumentChunkingResponse chunk(List<ai.gebo.architecture.documents.cache.model.AbstractChunkingSpecs> specs,
			ChunkingPolicy policy) throws Exception {
		IDocumentsCacheService cacheService = mock(IDocumentsCacheService.class);
		when(cacheService.streamDocument(any(StreamingPurpose.class), any())).thenAnswer(call ->
				TypedInputStream.of(new ByteArrayInputStream("pages".getBytes()), "application/pdf", "pdf"));
		IGDocumentReferenceIngestionHandler ingestionHandler = mock(IGDocumentReferenceIngestionHandler.class);
		when(ingestionHandler.handleContent(any(GDocumentReference.class), any(TypedInputStream.class)))
				.thenAnswer(call -> {
					IngestionHandlerData pages = new IngestionHandlerData();
					pages.setStream(Stream.of(
							new Document("Page one speaks of the contract renewal terms.", new java.util.HashMap<>(Map.of("k", "v"))),
							Document.builder()
									.media(org.springframework.ai.content.Media.builder()
											.mimeType(org.springframework.util.MimeTypeUtils.IMAGE_PNG).data(IMAGE)
											.name("image").build())
									.metadata(new java.util.HashMap<>(Map.of("k", "v", DocumentMetaInfos.CONTENT_PAGE, "2")))
									.build(),
							new Document("Page three closes the document with signatures.", new java.util.HashMap<>(Map.of("k", "v")))));
					return pages;
				});
		IKeywordMatcherService matcher = mock(IKeywordMatcherService.class);
		when(matcher.isMatching(anyList(), anyString(), anyInt(), any())).thenReturn(true);
		IGLanguageDetector languageDetector = mock(IGLanguageDetector.class);
		when(languageDetector.detect(anyString())).thenReturn(new IGLanguageDetector.DetectedLanguage("en", 0.9d));
		IGGeboConfigService configService = mock(IGGeboConfigService.class);
		when(configService.getGeboWorkDirectory()).thenReturn(workDirectory.toString());
		IGeboThreadManager threads = mock(IGeboThreadManager.class);
		when(threads.getScheduler()).thenReturn(Schedulers.immediate());
		DocumentChunkOperationRepository operations = mock(DocumentChunkOperationRepository.class);
		when(operations.findByOriginalDocumentCode(anyString())).thenReturn(List.of());

		DocumentsChunkServiceImpl service = new DocumentsChunkServiceImpl(cacheService, configService, operations,
				mock(IGAIDocumentMetaDataEnricher.class), ingestionHandler, mock(IGDocumentReferenceFactory.class),
				threads, mock(IGPersistentObjectManager.class), mock(GeboDocumentsCacheConfig.class),
				mock(ChunkingSessionRepository.class), operations, matcher, languageDetector);

		ChunkingParams params = new ChunkingParams();
		params.setChunkingPolicy(policy);
		params.setMatchingKeywords(List.of("contract"));
		params.setChunkingSpecs(specs);
		params.setTokensPerChunkSet(50000);
		GDocumentReference document = new GDocumentReference();
		document.setCode("contracts.pdf");
		return service.getChunkSet(document, params, null);
	}

	private static final TextChunkingSpecs TEXT = TextChunkingSpecs.of(512, TextChunkingSpecs.MIN_CHUNKS_LENGTH_TO_EMBED,
			100);
	private static final ai.gebo.architecture.documents.cache.model.AbstractChunkingSpecs IMAGES = new ai.gebo.architecture.documents.cache.model.AbstractChunkingSpecs(
			ai.gebo.architecture.documents.cache.model.DocumentChunkType.IMAGE);

	@Test
	void anImageAskedIsReturnedAsItIsAmongTheTextChunks() throws Exception {
		List<DocumentChunk> chunks = chunk(List.of(TEXT, IMAGES), ChunkingPolicy.SPLIT_CHUNKS).getCurrentChunkSet()
				.getChunks();

		assertEquals(3, chunks.size());
		DocumentChunk image = chunks.get(1);
		assertEquals(ai.gebo.architecture.documents.cache.model.DocumentChunkType.IMAGE, image.getChunkType());
		assertEquals("image/png", image.getMimeType());
		org.junit.jupiter.api.Assertions.assertArrayEquals(IMAGE, image.mediaBytes(), "the image itself, whole");
		assertEquals("v", image.getMetaData().get("k"), "the metadata of the document's texts");
		assertEquals("2", image.getMetaData().get(DocumentMetaInfos.CONTENT_PAGE));
		assertEquals(2l, image.getChunkPosition(), "in its place among the chunks");
		assertEquals(0l, image.getTokensSize());
		assertEquals(3l, image.getChunksCount());
		org.junit.jupiter.api.Assertions.assertTrue(chunks.get(0).holdsText() && chunks.get(2).holdsText());
	}

	@Test
	void withNoImageAskedTheChunksAreTheTextsAsAlways() throws Exception {
		List<DocumentChunk> chunks = chunk(List.of(TEXT), ChunkingPolicy.SPLIT_CHUNKS).getCurrentChunkSet().getChunks();
		assertEquals(2, chunks.size());
		org.junit.jupiter.api.Assertions.assertTrue(chunks.stream().allMatch(DocumentChunk::holdsText));
		assertEquals(List.of(1l, 2l), chunks.stream().map(DocumentChunk::getChunkPosition).toList());
	}

	@Test
	void theChunksPickedByKeywordsAreTextOnly() throws Exception {
		List<DocumentChunk> chunks = chunk(List.of(TEXT, IMAGES), ChunkingPolicy.ONLY_MATCHING_CHUNKS)
				.getCurrentChunkSet().getChunks();
		org.junit.jupiter.api.Assertions.assertTrue(chunks.stream().allMatch(DocumentChunk::holdsText));
	}
}
