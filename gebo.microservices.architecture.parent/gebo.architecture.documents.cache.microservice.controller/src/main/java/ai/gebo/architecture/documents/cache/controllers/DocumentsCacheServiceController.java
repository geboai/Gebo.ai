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

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.service.DocumentCacheAccessException;
import ai.gebo.architecture.documents.cache.service.IDocumentsCacheService;
import ai.gebo.architecture.documents.cache.service.impl.DocumentsCacheServiceImpl;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import ai.gebo.model.base.TypedInputStream;
import ai.gebo.systems.abstraction.layer.model.StreamingPurpose;

/**
 * REST controller that exposes exactly the {@link IDocumentsCacheService}
 * interface, directly bound to its {@link DocumentsCacheServiceImpl}
 * implementation. Each endpoint mirrors one interface method and delegates to
 * the concrete service without any additional business logic.
 */
@RestController
@RequestMapping("api/DocumentsCacheServiceController")
public class DocumentsCacheServiceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentsCacheServiceController.class);

	private final DocumentsCacheServiceImpl documentsCacheService;

	public DocumentsCacheServiceController(DocumentsCacheServiceImpl documentsCacheService) {
		this.documentsCacheService = documentsCacheService;
	}

	/**
	 * Mirrors
	 * {@link IDocumentsCacheService#streamDocument(StreamingPurpose, IGComponentOriginatedDocument)}.
	 * The {@link TypedInputStream} returned by the service is streamed back to the
	 * caller as the response body, carrying the resolved content type and
	 * extension.
	 */
	@PostMapping(value = "streamDocument", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> streamDocument(@RequestBody StreamDocumentRequest request)
			throws DocumentCacheAccessException, GeboContentHandlerSystemException, IOException, SearchServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST streamDocument(" + (request.reference != null ? request.reference.getCode() : null) + ")");
		}
		TypedInputStream tis = documentsCacheService.streamDocument(request.streamingPurpose, request.reference);
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
			builder.header("X-Gebo-Content-Extension", tis.getExtension());
		}
		return builder.body(new InputStreamResource(tis.getInputStream()));
	}

	/**
	 * Request body for
	 * {@link #streamDocument(DocumentsCacheServiceController.StreamDocumentRequest)}.
	 */
	public static class StreamDocumentRequest {
		public StreamingPurpose streamingPurpose;
		@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
		@JsonSubTypes({ @JsonSubTypes.Type(GDocumentReference.class), @JsonSubTypes.Type(SearchResult.class) })
		public IGComponentOriginatedDocument reference;
	}
}
