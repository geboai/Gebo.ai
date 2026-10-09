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
 * Pins the chunk positions to the document: a chunk a matching policy leaves out
 * keeps its place, so consecutive positions are contiguous text and the count is
 * the document's.
 */
class ChunkPositionsTest {

	@TempDir
	Path workDirectory;

	private DocumentsChunkServiceImpl service;
	private IKeywordMatcherService matcher;
	private IGLanguageDetector languageDetector;
	private DocumentChunkOperationRepository operations;
	private GDocumentReference document;

	private DocumentChunkingResponse chunk(ChunkingPolicy policy) throws Exception {
		return chunk(policy, false);
	}

	/** A page as the ingestion reads it: its language detected and trusted. */
	private static Document page(String text, String language) {
		return new Document(text, Map.of(DocumentMetaInfos.LANGUAGE, language,
				DocumentMetaInfos.LANGUAGE_CONFIDENCE, 0.99d));
	}

	@org.junit.jupiter.api.BeforeEach
	void detectsTheKeywordsLanguage() throws Exception {
		languageDetector = mock(IGLanguageDetector.class);
		when(languageDetector.detect(anyString())).thenReturn(new IGLanguageDetector.DetectedLanguage("it", 0.9d));
	}

	/** Four pages, each one chunk; only the first and the third talk of the keyword. */
	private DocumentChunkingResponse chunk(ChunkingPolicy policy, boolean sampling) throws Exception {
		IDocumentsCacheService cacheService = mock(IDocumentsCacheService.class);
		when(cacheService.streamDocument(any(StreamingPurpose.class), any())).thenAnswer(call ->
				TypedInputStream.of(new ByteArrayInputStream("pages".getBytes()), "application/pdf", "pdf"));
		IGDocumentReferenceIngestionHandler ingestionHandler = mock(IGDocumentReferenceIngestionHandler.class);
		when(ingestionHandler.handleContent(any(GDocumentReference.class), any(TypedInputStream.class)))
				.thenAnswer(call -> {
					IngestionHandlerData pages = new IngestionHandlerData();
					pages.setStream(Stream.of(page("Page one speaks of the contract renewal terms.", "en"),
				page("Page two is about something else entirely.", "en"),
				page("Page three again speaks of the contract penalties.", "en"),
				page("Page four closes the document with signatures.", "en")));
					return pages;
				});
		matcher = mock(IKeywordMatcherService.class);
		when(matcher.isMatching(anyList(), anyString(), anyInt(), any()))
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
				mock(ChunkingSessionRepository.class), operations, matcher, languageDetector);

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
	void theWholeTextIsHashedAsItIsReadAndTheHashKeptWithTheChunks() throws Exception {
		DocumentChunkingResponse response = chunk(ChunkingPolicy.SPLIT_CHUNKS);

		final String expected = new ContentHash().add("Page one speaks of the contract renewal terms.")
				.add("Page two is about something else entirely.")
				.add("Page three again speaks of the contract penalties.")
				.add("Page four closes the document with signatures.").hex();
		assertEquals(expected, response.getContentHash(), "the hash of the pages' text, in reading order");
		ArgumentCaptor<DocumentChunkOperation> saved = ArgumentCaptor.forClass(DocumentChunkOperation.class);
		verify(operations).insert(saved.capture());
		assertEquals(expected, saved.getValue().getContentHash(), "kept on the record");
		when(operations.findById(saved.getValue().getId())).thenReturn(Optional.of(saved.getValue()));
		assertEquals(expected, service.getNextChunkSet(document, saved.getValue().getId(),
				saved.getValue().getChunkSetsList().get(0), null).getContentHash(), "given back when read again");
	}

	@Test
	void aSampleHasNoHashItsTextIsNotReadWhole() throws Exception {
		assertNull(chunk(ChunkingPolicy.ONLY_MATCHING_CHUNKS, true).getContentHash());
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	void eachChunkIsMatchedWithTheKeywordsAndItsOwnLanguage() throws Exception {
		ChunkingParams params = new ChunkingParams();
		params.setChunkingPolicy(ChunkingPolicy.ONLY_MATCHING_CHUNKS);
		// keywords long enough to be detected
		params.setMatchingKeywords(List.of("quali sono le penali del contratto di rinnovo"));
		params.setChunkingSpecs(List.of(TextChunkingSpecs.of(512, TextChunkingSpecs.MIN_CHUNKS_LENGTH_TO_EMBED, 100)));
		params.setTokensPerChunkSet(50000);
		chunk(ChunkingPolicy.ONLY_MATCHING_CHUNKS);
		org.mockito.ArgumentCaptor<java.util.Collection> languages = org.mockito.ArgumentCaptor
				.forClass(java.util.Collection.class);
		verify(matcher, org.mockito.Mockito.atLeastOnce()).isMatching(anyList(), anyString(), anyInt(),
				languages.capture());
		// the default test keywords ("contract") are too short to detect: only the page's
		assertEquals(List.of("en"), new java.util.ArrayList<>(languages.getValue()));
		verify(languageDetector, org.mockito.Mockito.never()).detect(anyString());

		org.mockito.Mockito.clearInvocations(matcher, languageDetector);
		service.getChunkSet(document, params, null);
		verify(matcher, org.mockito.Mockito.atLeastOnce()).isMatching(anyList(), anyString(), anyInt(),
				languages.capture());
		assertEquals(List.of("it", "en"), new java.util.ArrayList<>(languages.getValue()),
				"the keywords' language, detected once, and the page's");
		verify(languageDetector, org.mockito.Mockito.times(1)).detect(anyString());
	}

	@Test
	void anUntrustedLanguageIsNotUsed() {
		assertEquals(List.of(), DocumentsChunkServiceImpl.matchingLanguages(null,
				Map.of(DocumentMetaInfos.LANGUAGE, "en", DocumentMetaInfos.LANGUAGE_CONFIDENCE, 0.3d)),
				"the detector's own fallback, en at 0.3");
		assertEquals(List.of("fr"), DocumentsChunkServiceImpl.matchingLanguages(null,
				Map.of(DocumentMetaInfos.LANGUAGE, "fr", DocumentMetaInfos.LANGUAGE_CONFIDENCE, "0.97")));
		assertEquals(List.of("it"), DocumentsChunkServiceImpl.matchingLanguages("it", Map.of()));
	}
}
