/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.documents.cache.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import ai.gebo.architecture.documents.cache.client.model.RemoteDocumentChunkWithRef;
import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.architecture.documents.cache.model.DocumentChunkingResponse;
import ai.gebo.architecture.documents.cache.model.IDocumentChunkWithRef;
import ai.gebo.architecture.documents.cache.service.DocumentCacheAccessException;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;

/**
 * {@link IDocumentsChunkService} client that calls the
 * {@code DocumentsChunkServiceController} REST endpoints on the chunker
 * microservice. Blocking methods issue a request and {@code block()} on the
 * response; the {@code Flux}/{@code ParallelFlux} streaming methods consume the
 * controller's newline-delimited JSON ({@code application/x-ndjson}) streams
 * lazily. The base URL, api-key header and any extra headers are carried by the
 * injected {@link WebClient} (see
 * {@link ai.gebo.architecture.documents.cache.client.config.DocumentsCacheMicroserviceClientConfiguration}).
 */
public class DocumentsChunkServiceRestClient implements IDocumentsChunkService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentsChunkServiceRestClient.class);

	static final String BASE_PATH = "/api/DocumentsChunkServiceController/";

	private final WebClient webClient;

	public DocumentsChunkServiceRestClient(WebClient webClient) {
		this.webClient = webClient;
	}

	// ---------------------------------------------------------------------
	// Blocking request/response methods
	// ---------------------------------------------------------------------

	@Override
	public DocumentChunkingResponse prepareChunks(IGComponentOriginatedDocument document, ChunkingParams chunkingSpecs,
			String chunkingSessionId) throws DocumentCacheAccessException, IOException {
		PrepareChunksRequest request = new PrepareChunksRequest();
		request.document = document;
		request.chunkingSpecs = chunkingSpecs;
		request.chunkingSessionId = chunkingSessionId;
		return postForObject("prepareChunks", request, DocumentChunkingResponse.class);
	}

	@Override
	public DocumentChunkingResponse getCachedChunkSet(IGComponentOriginatedDocument document, String chunkSessionId)
			throws DocumentCacheAccessException, IOException {
		GetCachedChunkSetRequest request = new GetCachedChunkSetRequest();
		request.document = document;
		request.chunkSessionId = chunkSessionId;
		return postForObject("getCachedChunkSet", request, DocumentChunkingResponse.class);
	}

	@Override
	public DocumentChunkingResponse getChunkSet(IGComponentOriginatedDocument document, ChunkingParams chunkingSpecs,
			String chunkSessionId) throws DocumentCacheAccessException, IOException {
		GetChunkSetRequest request = new GetChunkSetRequest();
		request.document = document;
		request.chunkingSpecs = chunkingSpecs;
		request.chunkSessionId = chunkSessionId;
		return postForObject("getChunkSet", request, DocumentChunkingResponse.class);
	}

	@Override
	public DocumentChunkingResponse getNextChunkSet(IGComponentOriginatedDocument document, String chunkRequestId,
			String nextChunkId, String chunkSessionId) throws DocumentCacheAccessException, IOException {
		GetNextChunkSetRequest request = new GetNextChunkSetRequest();
		request.document = document;
		request.chunkRequestId = chunkRequestId;
		request.nextChunkId = nextChunkId;
		request.chunkSessionId = chunkSessionId;
		return postForObject("getNextChunkSet", request, DocumentChunkingResponse.class);
	}

	@Override
	public String createChunkingSession(String reference) {
		return webClient.post()
				.uri(builder -> builder.path(BASE_PATH + "createChunkingSession").queryParam("reference", reference)
						.build())
				.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(String.class).block();
	}

	@Override
	public String retrieveChunkingSession(String reference) {
		return webClient.get()
				.uri(builder -> builder.path(BASE_PATH + "retrieveChunkingSession").queryParam("reference", reference)
						.build())
				.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(String.class).block();
	}

	@Override
	public void disposeChunkingSession(String chunkSessionId) {
		webClient.post().uri(builder -> builder.path(BASE_PATH + "disposeChunkingSession")
				.queryParam("chunkSessionId", chunkSessionId).build()).retrieve().toBodilessEntity().block();
	}

	// ---------------------------------------------------------------------
	// Streaming methods (application/x-ndjson)
	// ---------------------------------------------------------------------

	@Override
	public Flux<IDocumentChunkWithRef> streamChunks(IGComponentOriginatedDocument document,
			ChunkingParams chunkingSpecs, String chunkSessionId) {
		StreamChunksRequest request = new StreamChunksRequest();
		request.document = document;
		request.chunkingSpecs = chunkingSpecs;
		request.chunkSessionId = chunkSessionId;
		return streamChunks("streamChunks", request);
	}

	@Override
	public ParallelFlux<IDocumentChunkWithRef> streamChunks(List<? extends IGComponentOriginatedDocument> documents,
			ChunkingParams chunkingSpecs, String chunkSessionId, int docConcurrency) {
		StreamChunksBatchRequest request = new StreamChunksBatchRequest();
		request.documents = new ArrayList<>(documents);
		request.chunkingSpecs = chunkingSpecs;
		request.chunkSessionId = chunkSessionId;
		request.docConcurrency = docConcurrency;
		return streamChunks("streamChunksBatch", request).parallel();
	}

	@Override
	public ParallelFlux<IDocumentChunkWithRef> streamChunks(
			Publisher<List<? extends IGComponentOriginatedDocument>> documentsPublisher, ChunkingParams chunkingSpecs,
			String chunkSessionId, int docConcurrency) {
		// The controller's reactive-source endpoint expects the document batches
		// materialized in the request body, so collect the publisher first, then
		// stream the response back.
		Flux<IDocumentChunkWithRef> stream = Flux.from(documentsPublisher).collectList().flatMapMany(batches -> {
			StreamChunksReactiveRequest request = new StreamChunksReactiveRequest();
			request.batches = new ArrayList<>();
			for (List<? extends IGComponentOriginatedDocument> batch : batches) {
				DocumentBatch documentBatch = new DocumentBatch();
				documentBatch.documents = new ArrayList<>(batch);
				request.batches.add(documentBatch);
			}
			request.chunkingSpecs = chunkingSpecs;
			request.chunkSessionId = chunkSessionId;
			request.docConcurrency = docConcurrency;
			return streamChunks("streamChunksReactive", request);
		});
		return stream.parallel();
	}

	// ---------------------------------------------------------------------
	// Helpers
	// ---------------------------------------------------------------------

	private <T> T postForObject(String path, Object body, Class<T> responseType) throws DocumentCacheAccessException {
		try {
			return webClient.post().uri(BASE_PATH + path).contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON).bodyValue(body).retrieve().bodyToMono(responseType).block();
		} catch (WebClientResponseException ex) {
			throw new DocumentCacheAccessException(
					"Remote " + path + " failed: " + ex.getStatusCode() + " " + ex.getResponseBodyAsString(), ex);
		} catch (RuntimeException ex) {
			throw new DocumentCacheAccessException("Remote " + path + " failed", ex);
		}
	}

	private Flux<IDocumentChunkWithRef> streamChunks(String path, Object body) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST streaming " + path);
		}
		return webClient.post().uri(BASE_PATH + path).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_NDJSON).bodyValue(body).retrieve()
				.bodyToFlux(RemoteDocumentChunkWithRef.class).cast(IDocumentChunkWithRef.class);
	}

	// ---------------------------------------------------------------------
	// Request bodies — mirror DocumentsChunkServiceController's nested request
	// classes so the JSON is wire-compatible (polymorphic document references
	// resolved by deduction between GDocumentReference and SearchResult).
	// ---------------------------------------------------------------------

	/** A single batch of documents for the reactive-source streaming overload. */
	public static class DocumentBatch {
		@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
		@JsonSubTypes({ @JsonSubTypes.Type(GDocumentReference.class), @JsonSubTypes.Type(SearchResult.class) })
		public List<IGComponentOriginatedDocument> documents;
	}

	public static class PrepareChunksRequest {
		@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
		@JsonSubTypes({ @JsonSubTypes.Type(GDocumentReference.class), @JsonSubTypes.Type(SearchResult.class) })
		public IGComponentOriginatedDocument document;
		public ChunkingParams chunkingSpecs;
		public String chunkingSessionId;
	}

	public static class GetCachedChunkSetRequest {
		@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
		@JsonSubTypes({ @JsonSubTypes.Type(GDocumentReference.class), @JsonSubTypes.Type(SearchResult.class) })
		public IGComponentOriginatedDocument document;
		public String chunkSessionId;
	}

	public static class GetChunkSetRequest {
		@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
		@JsonSubTypes({ @JsonSubTypes.Type(GDocumentReference.class), @JsonSubTypes.Type(SearchResult.class) })
		public IGComponentOriginatedDocument document;
		public ChunkingParams chunkingSpecs;
		public String chunkSessionId;
	}

	public static class GetNextChunkSetRequest {
		@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
		@JsonSubTypes({ @JsonSubTypes.Type(GDocumentReference.class), @JsonSubTypes.Type(SearchResult.class) })
		public IGComponentOriginatedDocument document;
		public String chunkRequestId;
		public String nextChunkId;
		public String chunkSessionId;
	}

	public static class StreamChunksRequest {
		@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
		@JsonSubTypes({ @JsonSubTypes.Type(GDocumentReference.class), @JsonSubTypes.Type(SearchResult.class) })
		public IGComponentOriginatedDocument document;
		public ChunkingParams chunkingSpecs;
		public String chunkSessionId;
	}

	public static class StreamChunksBatchRequest {
		@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
		@JsonSubTypes({ @JsonSubTypes.Type(GDocumentReference.class), @JsonSubTypes.Type(SearchResult.class) })
		public List<IGComponentOriginatedDocument> documents;
		public ChunkingParams chunkingSpecs;
		public String chunkSessionId;
		public int docConcurrency;
	}

	public static class StreamChunksReactiveRequest {
		public List<DocumentBatch> batches;
		public ChunkingParams chunkingSpecs;
		public String chunkSessionId;
		public int docConcurrency;
	}
}
