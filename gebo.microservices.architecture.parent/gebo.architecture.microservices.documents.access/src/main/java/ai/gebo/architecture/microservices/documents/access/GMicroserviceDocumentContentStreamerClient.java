/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.microservices.documents.access;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import ai.gebo.architecture.documents.access.DocumentContentStreamerException;
import ai.gebo.architecture.documents.access.IGDocumentContentStreamer;
import ai.gebo.architecture.documents.access.StreamingPurpose;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.model.base.GeboComponentInfo;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import ai.gebo.model.base.TypedInputStream;

/**
 * Microservices {@link IGDocumentContentStreamer} that streams a document's
 * content from the content-handler microservice that owns it.
 *
 * <p>
 * The owning service is resolved <b>per document</b>: the document's
 * {@link GeboComponentInfo#getMessagingModuleId() origin messagingModuleId} is
 * passed to {@link GeboMicroserviceUrlResolver#baseUrlForMessagingModuleId(String)}
 * (a module belongs to exactly one microservice), and the corresponding
 * {@code DocumentContentStreamerController} endpoint is called — {@code streamDocumentReference}
 * for a {@link GDocumentReference}, {@code streamSearchResult} for a
 * {@link SearchResult}. The octet-stream response body (plus its content type and
 * {@code X-Gebo-Content-Extension}) is returned as a {@link TypedInputStream}.
 */
public class GMicroserviceDocumentContentStreamerClient implements IGDocumentContentStreamer {

	private static final Logger LOGGER = LoggerFactory.getLogger(GMicroserviceDocumentContentStreamerClient.class);

	static final String CONTROLLER_PATH = "/api/users/DocumentContentStreamerController/";
	static final String STREAM_DOCUMENT_REFERENCE = "streamDocumentReference";
	static final String STREAM_SEARCH_RESULT = "streamSearchResult";
	static final String EXTENSION_HEADER = "X-Gebo-Content-Extension";

	private final WebClient webClient;
	private final GeboMicroserviceUrlResolver urlResolver;

	public GMicroserviceDocumentContentStreamerClient(WebClient webClient, GeboMicroserviceUrlResolver urlResolver) {
		this.webClient = webClient;
		this.urlResolver = urlResolver;
	}

	@Override
	public TypedInputStream streamContent(StreamingPurpose purpose, IGComponentOriginatedDocument document)
			throws DocumentContentStreamerException, IOException {
		if (document == null) {
			throw new DocumentContentStreamerException("Cannot stream a null document");
		}
		String messagingModuleId = resolveMessagingModuleId(document);
		String baseUrl = urlResolver.baseUrlForMessagingModuleId(messagingModuleId)
				.orElseThrow(() -> new DocumentContentStreamerException("No microservice base url for messagingModuleId '"
						+ messagingModuleId + "' (document " + document.getCode() + ")"));

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
					.accept(MediaType.APPLICATION_OCTET_STREAM).bodyValue(requestBody).retrieve().toEntity(byte[].class)
					.block();
			if (response == null || response.getStatusCode() == HttpStatus.NO_CONTENT || response.getBody() == null) {
				return null;
			}
			MediaType contentType = response.getHeaders().getContentType();
			String extension = response.getHeaders().getFirst(EXTENSION_HEADER);
			return TypedInputStream.of(new ByteArrayInputStream(response.getBody()),
					contentType != null ? contentType.toString() : null, extension);
		} catch (WebClientResponseException ex) {
			throw new DocumentContentStreamerException(
					"Remote streamContent failed: " + ex.getStatusCode() + " " + ex.getResponseBodyAsString(), ex);
		} catch (RuntimeException ex) {
			throw new DocumentContentStreamerException("Remote streamContent failed", ex);
		}
	}

	private String resolveMessagingModuleId(IGComponentOriginatedDocument document)
			throws DocumentContentStreamerException {
		GeboComponentInfo origin = document.getOriginComponent();
		if (origin == null || origin.getMessagingModuleId() == null || origin.getMessagingModuleId().isBlank()) {
			throw new DocumentContentStreamerException(
					"Document " + document.getCode() + " has no origin messagingModuleId to route the stream request");
		}
		return origin.getMessagingModuleId();
	}

	/**
	 * Request body for {@code streamDocumentReference}, mirroring
	 * {@code DocumentContentStreamerController.GDocumentReferenceStreamRequest}.
	 */
	public static class GDocumentReferenceStreamRequest {
		public StreamingPurpose streamingPurpose;
		public GDocumentReference reference;
	}

	/**
	 * Request body for {@code streamSearchResult}, mirroring
	 * {@code DocumentContentStreamerController.SearchResultStreamRequest}.
	 */
	public static class SearchResultStreamRequest {
		public StreamingPurpose streamingPurpose;
		public SearchResult reference;
	}
}
