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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.Test;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

/**
 * Pins the column-aware reading of a PDF page: a page in columns is read one column
 * after the other, whatever the direction its text runs in; any other page is read
 * exactly as the position-ordered extraction reads it.
 */
class ColumnAwarePdfTextExtractorTest {

	private static final int LINES = 20;

	/** A page of text lines written at the given places: {x, y} of each column's first line. */
	private static PdfReader page(int rotation, float[][] columnStarts, String... columnNames) throws Exception {
		final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		final Document document = new Document(PageSize.A4);
		final PdfWriter writer = PdfWriter.getInstance(document, bytes);
		document.open();
		document.add(new Paragraph(" "));
		final PdfContentByte content = writer.getDirectContent();
		final BaseFont font = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, false);
		content.beginText();
		content.setFontAndSize(font, 10);
		for (int c = 0; c < columnStarts.length; c++) {
			for (int line = 1; line <= LINES; line++) {
				final String text = String.format("%s line %02d of the text", columnNames[c], line);
				// each next line one line below: down the page, or across it for a rotated text
				final float x = rotation == 0 ? columnStarts[c][0] : columnStarts[c][0] + line * 12;
				final float y = rotation == 0 ? columnStarts[c][1] - line * 12 : columnStarts[c][1];
				content.showTextAligned(Element.ALIGN_LEFT, text, x, y, rotation);
			}
		}
		content.endText();
		document.close();
		return new PdfReader(bytes.toByteArray());
	}

	@Test
	void aPageInOneColumnIsReadAsBefore() throws Exception {
		final PdfReader reader = page(0, new float[][] { { 50, 780 } }, "Only");

		assertEquals(PdfTextExtractor.getTextFromPage(reader, 1), ColumnAwarePdfTextExtractor.textOfPage(reader, 1));
	}

	@Test
	void aPageInTwoColumnsIsReadOneColumnAfterTheOther() throws Exception {
		final PdfReader reader = page(0, new float[][] { { 50, 780 }, { 320, 780 } }, "Left", "Right");

		final String before = PdfTextExtractor.getTextFromPage(reader, 1);
		assertTrue(before.contains("Left line 01 of the text Right line 01 of the text")
				|| before.contains("Left line 01 of the textRight line 01 of the text"),
				"the position-ordered extraction joins the columns' lines: " + before);
		final String text = ColumnAwarePdfTextExtractor.textOfPage(reader, 1);
		assertTrue(text.indexOf("Left line 20") < text.indexOf("Right line 01"), text);
		for (int line = 1; line < LINES; line++) {
			assertTrue(text.indexOf(String.format("Left line %02d", line)) < text
					.indexOf(String.format("Left line %02d", line + 1)), "the lines of a column in order");
		}
	}

	@Test
	void aRotatedPageInTwoColumnsIsReadOneColumnAfterTheOther() throws Exception {
		// the text runs up the page; its two columns are one above the other
		final PdfReader reader = page(90, new float[][] { { 50, 60 }, { 50, 480 } }, "First", "Second");

		final String text = ColumnAwarePdfTextExtractor.textOfPage(reader, 1);
		assertTrue(text.contains("First line 05 of the text"), text);
		assertTrue(text.indexOf("First line 20") < text.indexOf("Second line 01"), text);
	}
}
