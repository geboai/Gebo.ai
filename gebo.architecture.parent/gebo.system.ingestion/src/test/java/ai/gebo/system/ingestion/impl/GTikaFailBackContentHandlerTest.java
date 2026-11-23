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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.config.GeboContentReadingConfig;

/**
 * Pins what the Tika fallback reader (Spring AI's TikaDocumentReader on the platform's
 * Tika) reads of the common formats: the text of an HTML page and of OOXML documents
 * written with the platform's POI, and the metadata handed in kept on the output.
 */
class GTikaFailBackContentHandlerTest {

	private static String read(byte[] content, String extension) throws Exception {
		GDocumentReference reference = new GDocumentReference();
		reference.setCode("test-document");
		reference.setExtension(extension);
		List<Document> documents = new GTikaFailBackContentHandler(new GeboContentReadingConfig())
				.handleContent(reference, new ByteArrayInputStream(content), Map.of("origin", "test")).toList();
		assertEquals(1, documents.size(), extension);
		assertEquals("test", documents.get(0).getMetadata().get("origin"), extension);
		return documents.get(0).getText();
	}

	@Test
	void anHtmlPageIsReadAsItsText() throws Exception {
		String html = "<html><head><title>Report</title></head><body><h1>Quarterly report</h1>"
				+ "<p>Revenue grew in the third quarter.</p></body></html>";
		String text = read(html.getBytes(StandardCharsets.UTF_8), ".html");
		assertTrue(text.contains("Quarterly report"), text);
		assertTrue(text.contains("Revenue grew in the third quarter."), text);
	}

	@Test
	void aWordDocumentIsReadAsItsText() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (XWPFDocument document = new XWPFDocument()) {
			document.createParagraph().createRun().setText("First paragraph of the contract.");
			document.createParagraph().createRun().setText("Perchè costa 5 €.");
			document.write(out);
		}
		String text = read(out.toByteArray(), ".docx");
		assertTrue(text.contains("First paragraph of the contract."), text);
		assertTrue(text.contains("Perchè costa 5 €."), text);
	}

	@Test
	void aSpreadsheetIsReadAsItsCells() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			var row = workbook.createSheet("Budget").createRow(0);
			row.createCell(0).setCellValue("Travel");
			row.createCell(1).setCellValue(1250);
			workbook.write(out);
		}
		String text = read(out.toByteArray(), ".xlsx");
		assertTrue(text.contains("Budget"), text);
		assertTrue(text.contains("Travel"), text);
		assertTrue(text.contains("1250"), text);
	}

	@Test
	void aPresentationIsReadAsItsSlidesText() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (XMLSlideShow show = new XMLSlideShow()) {
			XSLFTextBox box = show.createSlide().createTextBox();
			box.setText("Roadmap for the next release");
			show.write(out);
		}
		String text = read(out.toByteArray(), ".pptx");
		assertTrue(text.contains("Roadmap for the next release"), text);
	}
}
