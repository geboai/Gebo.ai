/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.system.ingestion.pdf.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import ai.gebo.model.DocumentMetaInfos;

/**
 * Pins where a PDF's title comes from: its document information, else the first
 * lines of its first page.
 */
class PdfIngestionHandlerTitleTest {

	private static PdfReader pdf(String title, String subject, String... lines) throws Exception {
		final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		final Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, bytes);
		if (title != null) {
			document.addTitle(title);
		}
		if (subject != null) {
			document.addSubject(subject);
			document.addAuthor("H. P. Blavatsky");
		}
		document.open();
		for (String line : lines) {
			document.add(new Paragraph(line));
		}
		document.close();
		return new PdfReader(bytes.toByteArray());
	}

	@Test
	void theTitleAndSubjectOfTheDocumentInformationComeFirst() throws Exception {
		final Map<String, Object> meta = new HashMap<>();
		PdfIngestionHandler.enrichPdfMetadata(pdf("The Secret Doctrine", "Cosmogenesis", "Page header", "Second line"),
				meta);
		assertEquals("The Secret Doctrine", meta.get(DocumentMetaInfos.TITLE));
		assertEquals("Cosmogenesis", meta.get(DocumentMetaInfos.SUBTITLE));
		assertEquals("H. P. Blavatsky", meta.get(DocumentMetaInfos.AUTHOR));
	}

	@Test
	void anInformationTitleCleanedOfTheOfficePrintingIsUsedASingleCharacterOneIsNot() throws Exception {
		final Map<String, Object> printed = new HashMap<>();
		PdfIngestionHandler.enrichPdfMetadata(pdf("Microsoft Word - I dodici sensi.doc", null, "Page header"), printed);
		assertEquals("I dodici sensi", printed.get(DocumentMetaInfos.TITLE));

		final Map<String, Object> single = new HashMap<>();
		PdfIngestionHandler.enrichPdfMetadata(pdf("I", null, "La Scienza Occulta", "Rudolf Steiner"), single);
		assertEquals("La Scienza Occulta", single.get(DocumentMetaInfos.TITLE));
	}

	@Test
	void withoutDocumentInformationTheFirstLinesOfTheFirstPageAreUsed() throws Exception {
		final Map<String, Object> meta = new HashMap<>();
		PdfIngestionHandler.enrichPdfMetadata(pdf(null, null, "Isis Unveiled", "Volume one"), meta);
		assertEquals("Isis Unveiled", meta.get(DocumentMetaInfos.TITLE));
		assertEquals("Volume one", meta.get(DocumentMetaInfos.SUBTITLE));
		assertNull(meta.get(DocumentMetaInfos.AUTHOR));
	}
}
