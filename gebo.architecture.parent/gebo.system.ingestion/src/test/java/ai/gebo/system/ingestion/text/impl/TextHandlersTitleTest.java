/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.system.ingestion.text.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.model.IngestionFileType;
import ai.gebo.system.ingestion.model.IngestionHandlerConfig;

/**
 * Pins that the html and the markdown handlers give their documents the title the
 * file tells, the text indexed staying the same.
 */
class TextHandlersTitleTest {

	private static IGIngestionHandlerConfigDao dao(String... extensions) {
		final IngestionFileType type = new IngestionFileType();
		type.setExtensions(List.of(extensions));
		final IngestionHandlerConfig config = new IngestionHandlerConfig();
		config.setFileTypes(List.of(type));
		final IGIngestionHandlerConfigDao dao = mock(IGIngestionHandlerConfigDao.class);
		when(dao.findByCode(anyString())).thenReturn(config);
		return dao;
	}

	private static GDocumentReference reference(String extension) {
		final GDocumentReference reference = new GDocumentReference();
		reference.setCode("test-document");
		reference.setExtension(extension);
		return reference;
	}

	private static ByteArrayInputStream stream(String text) {
		return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
	}

	@Test
	void anHtmlPageIsIndexedByItsBodyWithItsTitle() throws Exception {
		final Map<String, Object> meta = new HashMap<>();
		final List<Document> documents = new HtmlIngestionHandler(dao(".html")).handleContent(reference(".html"),
				stream("<html><head><title>Isis Unveiled</title></head><body><p>The text</p></body></html>"), meta)
				.toList();
		assertEquals(1, documents.size());
		assertEquals("The text", documents.get(0).getText());
		assertEquals("Isis Unveiled", documents.get(0).getMetadata().get(DocumentMetaInfos.TITLE));
	}

	@Test
	void aMarkdownTextHasTheTitleOfItsFirstHeadingAPlainTextNone() throws Exception {
		final String text = "# The Secret Doctrine\nThe text";
		final Map<String, Object> markdown = new HashMap<>();
		final List<Document> documents = new TextPlainIngestionHandler(dao(".md", ".txt"))
				.handleContent(reference(".md"), stream(text), markdown).toList();
		assertEquals(text, documents.get(0).getText());
		assertEquals("The Secret Doctrine", documents.get(0).getMetadata().get(DocumentMetaInfos.TITLE));

		final Map<String, Object> plain = new HashMap<>();
		new TextPlainIngestionHandler(dao(".md", ".txt")).handleContent(reference(".txt"), stream(text), plain).toList();
		assertNull(plain.get(DocumentMetaInfos.TITLE));
	}
}
