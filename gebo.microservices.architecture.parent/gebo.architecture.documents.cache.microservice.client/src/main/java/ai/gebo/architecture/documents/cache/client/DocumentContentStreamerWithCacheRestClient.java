/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.documents.cache.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import ai.gebo.architecture.documents.access.DocumentContentStreamerException;
import ai.gebo.architecture.documents.access.IGDocumentContentStreamer;
import ai.gebo.architecture.documents.access.StreamingPurpose;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import ai.gebo.model.base.TypedInputStream;

/**
 * {@link IGDocumentContentStreamer} that streams a document's content
 * <b>through the chunker microservice's document cache</b>, by calling its
 * {@code DocumentContentStreamerWithCacheController}.
 *
 * <p>
 * Unlike
 * {@code ai.gebo.architecture.microservices.documents.access.GMicroserviceDocumentContentStreamerClient},
 * which goes straight to the content-handler microservice owning each document,
 * this client always targets a single service — the chunker — resolved by
 * {@link GeboMicroserviceUrlResolver#baseUrlForMicroserviceId(String)} from the
 * configured microservice id ({@code chunker_gebo_ai} by default). The chunker
 * fetches the document from its owning content handler once, keeps the bytes in
 * its local file cache and serves them from there on subsequent calls, so a
 * consumer (e.g. brain.gebo.ai) that reads the same document repeatedly pays the
 * content-handler transfer only once.
 *
 * <p>
 * {@code streamDocumentReference} is called for a {@link GDocumentReference},
 * {@code streamSearchResult} for a {@link SearchResult}; the octet-stream
 * response body, its content type and the {@code X-Gebo-Content-Extension}
 * header are returned as a {@link TypedInputStream}.
 */
public class DocumentContentStreamerWithCacheRestClient implements IGDocumentContentStreamer {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentContentStreamerWithCacheRestClient.class);

	static final String CONTROLLER_PATH = "/api/DocumentContentStreamerWithCacheController/";
	static final String STREAM_DOCUMENT_REFERENCE = "streamDocumentReference";
	static final String STREAM_SEARCH_RESULT = "streamSearchResult";
	static final String EXTENSION_HEADER = "X-Gebo-Content-Extension";

	private final WebClient webClient;
	private final GeboMicroserviceUrlResolver urlResolver;
	private final String microserviceId;
	private final IGeboCallerTokenPropagator tokenPropagator;

	/**
	 * @param webClient the WebClient carrying the api-key / extra headers; its base
	 *            url is irrelevant because every call targets an absolute uri
	 * @param urlResolver resolves the caching microservice's base url
	 * @param microserviceId id of the microservice hosting the cache controller
	 *            (the chunker)
	 * @param tokenPropagator forwards the caller's (or a system-identity) bearer
	 *            token, required by the cache controller's own security config
	 */
	public DocumentContentStreamerWithCacheRestClient(WebClient webClient, GeboMicroserviceUrlResolver urlResolver,
			String microserviceId, IGeboCallerTokenPropagator tokenPropagator) {
		this.webClient = webClient;
		this.urlResolver = urlResolver;
		this.microserviceId = microserviceId;
		this.tokenPropagator = tokenPropagator;
	}

	@Override
	public TypedInputStream streamContent(StreamingPurpose purpose, IGComponentOriginatedDocument document)
			throws DocumentContentStreamerException, IOException {
		if (document == null) {
			throw new DocumentContentStreamerException("Cannot stream a null document");
		}
		String baseUrl = urlResolver.baseUrlForMicroserviceId(microserviceId)
				.orElseThrow(() -> new DocumentContentStreamerException("No microservice base url for '" + microserviceId
						+ "', the document cache cannot be reached (document " + document.getCode() + ")"));

		String endpoint;
		Object requestBody;
		if (document instanceof GDocumentReference reference) {
			endpoint = STREAM_DOCUMENT_REFERENCE;
			GDocumentReferenceStreamRequest request = new GDocumentReferenceStreamRequest();
			request.streamingPurpose = purpose;
			request.reference = reference;
			requestBody = request;
		} else if (document instanceof SearchResult searchResult) {
			endpoint = STREAM_SEARCH_RESULT;
			SearchResultStreamRequest request = new SearchResultStreamRequest();
			request.streamingPurpose = purpose;
			request.reference = searchResult;
			requestBody = request;
		} else {
			throw new DocumentContentStreamerException(
					"Unsupported document type for streaming: " + document.getClass().getName());
		}

		URI uri = URI.create(baseUrl + CONTROLLER_PATH + endpoint);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST streamContent(" + document.getCode() + ") -> " + uri);
		}
		try {
			ResponseEntity<byte[]> response = webClient.post().uri(uri).contentType(MediaType.APPLICATION_JSON)
					.headers(this::applyCallerToken).accept(MediaType.APPLICATION_OCTET_STREAM).bodyValue(requestBody)
					.retrieve().toEntity(byte[].class).block();
			if (response == null || response.getStatusCode() == HttpStatus.NO_CONTENT || response.getBody() == null) {
				return null;
			}
			MediaType contentType = response.getHeaders().getContentType();
			String extension = response.getHeaders().getFirst(EXTENSION_HEADER);
			return TypedInputStream.of(new ByteArrayInputStream(response.getBody()),
					contentType != null ? contentType.toString() : null, extension);
		} catch (WebClientResponseException ex) {
			throw new DocumentContentStreamerException(
					"Remote cached streamContent failed: " + ex.getStatusCode() + " " + ex.getResponseBodyAsString(), ex);
		} catch (RuntimeException ex) {
			throw new DocumentContentStreamerException("Remote cached streamContent failed", ex);
		}
	}

	/** Adds the caller's (or, off a request thread, a system-identity) bearer token. */
	private void applyCallerToken(HttpHeaders headers) {
		String token = tokenPropagator.currentToken();
		if (StringUtils.hasText(token)) {
			headers.setBearerAuth(token);
		} else {
			LOGGER.debug("No caller token to forward to the chunker microservice; the call goes out unauthenticated");
		}
	}

	/**
	 * Request body for {@code streamDocumentReference}, mirroring
	 * {@code DocumentContentStreamerWithCacheController.GDocumentReferenceStreamRequest}.
	 */
	public static class GDocumentReferenceStreamRequest {
		public StreamingPurpose streamingPurpose;
		public GDocumentReference reference;
	}

	/**
	 * Request body for {@code streamSearchResult}, mirroring
	 * {@code DocumentContentStreamerWithCacheController.SearchResultStreamRequest}.
	 */
	public static class SearchResultStreamRequest {
		public StreamingPurpose streamingPurpose;
		public SearchResult reference;
	}
}
