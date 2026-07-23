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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.documents.access.IGDocumentContentStreamer;
import ai.gebo.architecture.documents.access.StreamingPurpose;
import ai.gebo.architecture.documents.cache.service.DocumentCacheAccessException;
import ai.gebo.architecture.documents.cache.service.impl.DocumentsCacheServiceImpl;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import ai.gebo.model.base.TypedInputStream;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Cache-backed twin of the content-handler
 * {@code DocumentContentStreamerController}: it exposes the very same
 * {@code streamDocumentReference} / {@code streamSearchResult} endpoints, but
 * serves the bytes through {@link DocumentsCacheServiceImpl} instead of reading
 * them from the owning content system on every call.
 *
 * <p>
 * The cache service resolves the document with the chunker's own
 * {@link IGDocumentContentStreamer} (the microservices one, which fetches from
 * the content-handler microservice owning the document), stores the payload in
 * the chunker's local file cache and streams it back. Subsequent requests for
 * the same document are served from that local copy until the source's
 * modification date moves past it, so callers that repeatedly need a remote
 * document's content pay the cross-service transfer only once.
 */
@RestController
@RequestMapping("api/DocumentContentStreamerWithCacheController")
public class DocumentContentStreamerWithCacheController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentContentStreamerWithCacheController.class);

	static final String EXTENSION_HEADER = "X-Gebo-Content-Extension";

	private final DocumentsCacheServiceImpl documentsCacheService;

	public DocumentContentStreamerWithCacheController(DocumentsCacheServiceImpl documentsCacheService) {
		this.documentsCacheService = documentsCacheService;
	}

	@Data
	public static class GDocumentReferenceStreamRequest {
		@NotNull
		public StreamingPurpose streamingPurpose;
		@NotNull
		public GDocumentReference reference;
	}

	@Data
	public static class SearchResultStreamRequest {
		@NotNull
		public StreamingPurpose streamingPurpose;
		@NotNull
		public SearchResult reference;
	}

	@PostMapping(value = "streamDocumentReference", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> streamDocumentReference(@RequestBody GDocumentReferenceStreamRequest document)
			throws DocumentCacheAccessException, IOException {
		return stream(document.streamingPurpose, document.reference);
	}

	@PostMapping(value = "streamSearchResult", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> streamSearchResult(@RequestBody SearchResultStreamRequest document)
			throws DocumentCacheAccessException, IOException {
		return stream(document.streamingPurpose, document.reference);
	}

	private ResponseEntity<Resource> stream(StreamingPurpose streamingPurpose, IGComponentOriginatedDocument reference)
			throws DocumentCacheAccessException, IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST streamContentWithCache(" + (reference != null ? reference.getCode() : null) + ")");
		}
		TypedInputStream tis = documentsCacheService.streamDocument(streamingPurpose, reference);
		if (tis == null || tis.getInputStream() == null) {
			return ResponseEntity.noContent().build();
		}
		MediaType contentType = MediaType.APPLICATION_OCTET_STREAM;
		if (tis.getContentType() != null) {
			try {
				contentType = MediaType.parseMediaType(tis.getContentType());
			} catch (Exception ex) {
				LOGGER.warn("Unparseable content type '" + tis.getContentType() + "', falling back to octet-stream");
			}
		}
		ResponseEntity.BodyBuilder builder = ResponseEntity.ok().contentType(contentType);
		if (tis.getExtension() != null) {
			builder.header(EXTENSION_HEADER, tis.getExtension());
		}
		return builder.body(new InputStreamResource(tis.getInputStream()));
	}
}
