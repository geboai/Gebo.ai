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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import ai.gebo.architecture.documents.access.StreamingPurpose;
import ai.gebo.architecture.documents.cache.service.DocumentCacheAccessException;
import ai.gebo.architecture.documents.cache.service.IDocumentsCacheService;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import ai.gebo.model.base.TypedInputStream;

/**
 * {@link IDocumentsCacheService} client that calls the
 * {@code DocumentsCacheServiceController} REST endpoint on the chunker
 * microservice. The base URL, api-key header and any extra headers are carried
 * by the injected {@link WebClient} (see
 * {@link ai.gebo.architecture.documents.cache.client.config.DocumentsCacheMicroserviceClientConfiguration}).
 */
public class DocumentsCacheServiceRestClient implements IDocumentsCacheService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentsCacheServiceRestClient.class);

	static final String BASE_PATH = "api/DocumentsCacheServiceController/";
	static final String EXTENSION_HEADER = "X-Gebo-Content-Extension";

	private final WebClient webClient;

	public DocumentsCacheServiceRestClient(WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public TypedInputStream streamDocument(StreamingPurpose streamingPurpose, IGComponentOriginatedDocument reference)
			throws DocumentCacheAccessException, IOException {
		StreamDocumentRequest request = new StreamDocumentRequest();
		request.streamingPurpose = streamingPurpose;
		request.reference = reference;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST streamDocument(" + (reference != null ? reference.getCode() : null) + ")");
		}
		try {
			ResponseEntity<byte[]> response = webClient.post().uri(BASE_PATH + "streamDocument")
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_OCTET_STREAM)
					.bodyValue(request).retrieve().toEntity(byte[].class).block();
			if (response == null || response.getStatusCode() == HttpStatus.NO_CONTENT || response.getBody() == null) {
				return null;
			}
			MediaType contentType = response.getHeaders().getContentType();
			String extension = response.getHeaders().getFirst(EXTENSION_HEADER);
			return TypedInputStream.of(new ByteArrayInputStream(response.getBody()),
					contentType != null ? contentType.toString() : null, extension);
		} catch (WebClientResponseException ex) {
			throw new DocumentCacheAccessException(
					"Remote streamDocument failed: " + ex.getStatusCode() + " " + ex.getResponseBodyAsString(), ex);
		} catch (RuntimeException ex) {
			throw new DocumentCacheAccessException("Remote streamDocument failed", ex);
		}
	}

	/**
	 * Request body for {@code streamDocument}, mirroring
	 * {@code DocumentsCacheServiceController.StreamDocumentRequest} so the JSON is
	 * wire-compatible (polymorphic {@code reference} resolved by deduction).
	 */
	public static class StreamDocumentRequest {
		public StreamingPurpose streamingPurpose;

		@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
		@JsonSubTypes({ @JsonSubTypes.Type(GDocumentReference.class), @JsonSubTypes.Type(SearchResult.class) })
		public IGComponentOriginatedDocument reference;
	}
}
