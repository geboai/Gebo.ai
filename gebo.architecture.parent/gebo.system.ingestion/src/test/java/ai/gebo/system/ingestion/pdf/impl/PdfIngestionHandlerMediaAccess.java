/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.system.ingestion.pdf.impl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.IGMediaTextExtractor;
import ai.gebo.system.ingestion.model.IngestionFileType;
import ai.gebo.system.ingestion.model.IngestionHandlerConfig;

/** Reads a PDF with the PDF handler given a media extractor, for the tests of the other packages. */
public final class PdfIngestionHandlerMediaAccess {
	private PdfIngestionHandlerMediaAccess() {
	}

	public static List<Document> read(GDocumentReference reference, byte[] pdf,
			ObjectProvider<IGMediaTextExtractor> extractor) throws Exception {
		final IngestionFileType type = new IngestionFileType();
		type.setExtensions(List.of(".pdf"));
		type.setContentTypes(List.of("application/pdf"));
		final IngestionHandlerConfig config = new IngestionHandlerConfig();
		config.setFileTypes(List.of(type));
		final IGIngestionHandlerConfigDao dao = mock(IGIngestionHandlerConfigDao.class);
		when(dao.findByCode(anyString())).thenReturn(config);
		final PdfIngestionHandler handler = new PdfIngestionHandler(dao, null);
		handler.setMediaExtractor(extractor);
		return handler.handleContent(reference, new ByteArrayInputStream(pdf), new HashMap<>()).toList();
	}
}
