/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.system.ingestion.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.config.GeboContentReadingConfig;
import ai.gebo.system.ingestion.model.IngestionFileType;
import ai.gebo.system.ingestion.model.IngestionHandlerConfig;

/**
 * The file type, so the cataloging header, of a document: by its extension, else
 * by its content type.
 */
public class GAIDocumentCatalogingEnricherFileTypeTest {

	static IngestionFileType fileType(String description, String contentType, String extension) {
		IngestionFileType type = new IngestionFileType();
		type.setDescription(description);
		type.setContentTypes(List.of(contentType));
		type.setExtensions(List.of(extension));
		type.setEnrichWithCatalogInfos(true);
		return type;
	}

	static GAIDocumentCatalogingEnricherImpl enricher(IngestionFileType... types) {
		IngestionHandlerConfig handler = new IngestionHandlerConfig();
		handler.setId("TestHandler");
		handler.setFileTypes(List.of(types));
		GeboContentReadingConfig config = new GeboContentReadingConfig();
		config.setHandlers(List.of(handler));
		GAIDocumentCatalogingEnricherImpl enricher = new GAIDocumentCatalogingEnricherImpl();
		enricher.contentsReadingConfig = config;
		return enricher;
	}

	static GDocumentReference reference(String extension, String contentType) {
		GDocumentReference reference = new GDocumentReference();
		reference.setCode("kb/pj/relay" + extension);
		reference.setExtension(extension);
		reference.setContentType(contentType);
		return reference;
	}

	final IngestionFileType text = fileType("Plain text", "text/plain", ".txt");
	final IngestionFileType markdown = fileType("Markdown", "text/markdown", ".md");
	final GAIDocumentCatalogingEnricherImpl enricher = enricher(text, markdown);

	@Test
	public void theExtensionWinsOverTheContentType() {
		assertEquals(markdown, enricher.getFileType(reference(".md", "text/plain")));
	}

	@Test
	public void anUnknownExtensionFallsBackToTheContentType() {
		assertEquals(text, enricher.getFileType(reference(".log", "text/plain")));
		assertEquals(text, enricher.getFileType(reference(".log", "Text/Plain; charset=UTF-8")));
		assertEquals(text, enricher.getFileType(reference(null, "text/plain")));
	}

	@Test
	public void unknownExtensionAndContentTypeHaveNoFileType() {
		assertNull(enricher.getFileType(reference(".bin", "application/octet-stream")));
		assertNull(enricher.getFileType(reference(".log", null)));
		assertNull(enricher.getFileType(reference(".log", " ")));
	}
}
