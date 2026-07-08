/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.documents.cache.controllers;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.architecture.documents.cache.model.DocumentChunkingResponse;
import ai.gebo.architecture.documents.cache.model.IDocumentChunkWithRef;
import ai.gebo.architecture.documents.cache.service.DocumentCacheAccessException;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.documents.cache.service.impl.DocumentsChunkServiceImpl;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import ai.gebo.system.ingestion.GeboIngestionException;
import reactor.core.publisher.Flux;

/**
 * REST controller that exposes exactly the {@link IDocumentsChunkService}
 * interface, directly bound to its {@link DocumentsChunkServiceImpl}
 * implementation. Each endpoint mirrors one interface method and delegates to
 * the concrete service.
 * <p>
 * Streaming methods returning a {@code Flux}/{@code ParallelFlux} are exposed as
 * newline-delimited JSON ({@code application/x-ndjson}); the
 * {@link IDocumentsChunkService#streamChunks(org.reactivestreams.Publisher, ChunkingParams, String, int)}
 * reactive-source overload accepts the document batches materialized in the
 * request body and re-publishes them to the underlying service.
 */
@RestController
@RequestMapping("api/DocumentsChunkServiceController")
public class DocumentsChunkServiceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentsChunkServiceController.class);

	private final DocumentsChunkServiceImpl documentsChunkService;

	public DocumentsChunkServiceController(DocumentsChunkServiceImpl documentsChunkService) {
		this.documentsChunkService = documentsChunkService;
	}

	/**
	 * Mirrors
	 * {@link IDocumentsChunkService#prepareChunks(IGComponentOriginatedDocument, ChunkingParams, String)}.
	 */
	@PostMapping(value = "prepareChunks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DocumentChunkingResponse prepareChunks(@RequestBody PrepareChunksRequest request)
			throws DocumentCacheAccessException, IOException, GeboContentHandlerSystemException, GeboIngestionException,
			SearchServiceException {
		return documentsChunkService.prepareChunks(request.document, request.chunkingSpecs, request.chunkingSessionId);
	}

	/**
	 * Mirrors
	 * {@link IDocumentsChunkService#getCachedChunkSet(IGComponentOriginatedDocument, String)}.
	 */
	@PostMapping(value = "getCachedChunkSet", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DocumentChunkingResponse getCachedChunkSet(@RequestBody GetCachedChunkSetRequest request)
			throws DocumentCacheAccessException, IOException, GeboContentHandlerSystemException, GeboIngestionException {
		return documentsChunkService.getCachedChunkSet(request.document, request.chunkSessionId);
	}

	/**
	 * Mirrors
	 * {@link IDocumentsChunkService#getChunkSet(IGComponentOriginatedDocument, ChunkingParams, String)}.
	 */
	@PostMapping(value = "getChunkSet", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DocumentChunkingResponse getChunkSet(@RequestBody GetChunkSetRequest request)
			throws DocumentCacheAccessException, IOException, GeboContentHandlerSystemException, GeboIngestionException,
			SearchServiceException {
		return documentsChunkService.getChunkSet(request.document, request.chunkingSpecs, request.chunkSessionId);
	}

	/**
	 * Mirrors
	 * {@link IDocumentsChunkService#getNextChunkSet(IGComponentOriginatedDocument, String, String, String)}.
	 */
	@PostMapping(value = "getNextChunkSet", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DocumentChunkingResponse getNextChunkSet(@RequestBody GetNextChunkSetRequest request)
			throws DocumentCacheAccessException, IOException {
		return documentsChunkService.getNextChunkSet(request.document, request.chunkRequestId, request.nextChunkId,
				request.chunkSessionId);
	}

	/**
	 * Mirrors
	 * {@link IDocumentsChunkService#streamChunks(IGComponentOriginatedDocument, ChunkingParams, String)}.
	 */
	@PostMapping(value = "streamChunks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Flux<IDocumentChunkWithRef> streamChunks(@RequestBody StreamChunksRequest request) {
		return documentsChunkService.streamChunks(request.document, request.chunkingSpecs, request.chunkSessionId);
	}

	/**
	 * Mirrors
	 * {@link IDocumentsChunkService#streamChunks(List, ChunkingParams, String, int)}.
	 * The underlying {@code ParallelFlux} is flattened to a sequential
	 * {@code Flux} for the HTTP response.
	 */
	@PostMapping(value = "streamChunksBatch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Flux<IDocumentChunkWithRef> streamChunksBatch(@RequestBody StreamChunksBatchRequest request) {
		return documentsChunkService
				.streamChunks(request.documents, request.chunkingSpecs, request.chunkSessionId, request.docConcurrency)
				.sequential();
	}

	/**
	 * Mirrors
	 * {@link IDocumentsChunkService#streamChunks(org.reactivestreams.Publisher, ChunkingParams, String, int)}.
	 * The document batches are received materialized in the request body and
	 * re-published as a {@code Flux} to the reactive-source service overload. The
	 * underlying {@code ParallelFlux} is flattened to a sequential {@code Flux} for
	 * the HTTP response.
	 */
	@PostMapping(value = "streamChunksReactive", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Flux<IDocumentChunkWithRef> streamChunksReactive(@RequestBody StreamChunksReactiveRequest request) {
		Flux<List<? extends IGComponentOriginatedDocument>> documentsPublisher = Flux.fromIterable(request.batches)
				.map(batch -> batch.documents);
		return documentsChunkService
				.streamChunks(documentsPublisher, request.chunkingSpecs, request.chunkSessionId, request.docConcurrency)
				.sequential();
	}

	/**
	 * Mirrors {@link IDocumentsChunkService#createChunkingSession(String)}.
	 */
	@PostMapping(value = "createChunkingSession", produces = MediaType.APPLICATION_JSON_VALUE)
	public String createChunkingSession(@RequestParam("reference") String reference) {
		return documentsChunkService.createChunkingSession(reference);
	}

	/**
	 * Mirrors {@link IDocumentsChunkService#retrieveChunkingSession(String)}.
	 */
	@GetMapping(value = "retrieveChunkingSession", produces = MediaType.APPLICATION_JSON_VALUE)
	public String retrieveChunkingSession(@RequestParam("reference") String reference) {
		return documentsChunkService.retrieveChunkingSession(reference);
	}

	/**
	 * Mirrors {@link IDocumentsChunkService#disposeChunkingSession(String)}.
	 */
	@PostMapping(value = "disposeChunkingSession")
	public void disposeChunkingSession(@RequestParam("chunkSessionId") String chunkSessionId) {
		documentsChunkService.disposeChunkingSession(chunkSessionId);
	}

	/**
	 * A single batch of documents, used by the reactive-source streaming overload.
	 * The polymorphic element typing is resolved by deduction between the two
	 * concrete {@link IGComponentOriginatedDocument} implementations.
	 */
	public static class DocumentBatch {
		@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
		@JsonSubTypes({ @JsonSubTypes.Type(GDocumentReference.class), @JsonSubTypes.Type(SearchResult.class) })
		public List<IGComponentOriginatedDocument> documents;
	}

	/** Request body for {@link #prepareChunks(PrepareChunksRequest)}. */
	public static class PrepareChunksRequest {
		@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
		@JsonSubTypes({ @JsonSubTypes.Type(GDocumentReference.class), @JsonSubTypes.Type(SearchResult.class) })
		public IGComponentOriginatedDocument document;
		public ChunkingParams chunkingSpecs;
		public String chunkingSessionId;
	}

	/** Request body for {@link #getCachedChunkSet(GetCachedChunkSetRequest)}. */
	public static class GetCachedChunkSetRequest {
		@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
		@JsonSubTypes({ @JsonSubTypes.Type(GDocumentReference.class), @JsonSubTypes.Type(SearchResult.class) })
		public IGComponentOriginatedDocument document;
		public String chunkSessionId;
	}

	/** Request body for {@link #getChunkSet(GetChunkSetRequest)}. */
	public static class GetChunkSetRequest {
		@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
		@JsonSubTypes({ @JsonSubTypes.Type(GDocumentReference.class), @JsonSubTypes.Type(SearchResult.class) })
		public IGComponentOriginatedDocument document;
		public ChunkingParams chunkingSpecs;
		public String chunkSessionId;
	}

	/** Request body for {@link #getNextChunkSet(GetNextChunkSetRequest)}. */
	public static class GetNextChunkSetRequest {
		@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
		@JsonSubTypes({ @JsonSubTypes.Type(GDocumentReference.class), @JsonSubTypes.Type(SearchResult.class) })
		public IGComponentOriginatedDocument document;
		public String chunkRequestId;
		public String nextChunkId;
		public String chunkSessionId;
	}

	/** Request body for {@link #streamChunks(StreamChunksRequest)}. */
	public static class StreamChunksRequest {
		@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
		@JsonSubTypes({ @JsonSubTypes.Type(GDocumentReference.class), @JsonSubTypes.Type(SearchResult.class) })
		public IGComponentOriginatedDocument document;
		public ChunkingParams chunkingSpecs;
		public String chunkSessionId;
	}

	/** Request body for {@link #streamChunksBatch(StreamChunksBatchRequest)}. */
	public static class StreamChunksBatchRequest {
		@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
		@JsonSubTypes({ @JsonSubTypes.Type(GDocumentReference.class), @JsonSubTypes.Type(SearchResult.class) })
		public List<IGComponentOriginatedDocument> documents;
		public ChunkingParams chunkingSpecs;
		public String chunkSessionId;
		public int docConcurrency;
	}

	/** Request body for {@link #streamChunksReactive(StreamChunksReactiveRequest)}. */
	public static class StreamChunksReactiveRequest {
		public List<DocumentBatch> batches;
		public ChunkingParams chunkingSpecs;
		public String chunkSessionId;
		public int docConcurrency;
	}
}
