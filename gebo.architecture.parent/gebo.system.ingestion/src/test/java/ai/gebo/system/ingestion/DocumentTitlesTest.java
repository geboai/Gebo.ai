/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.system.ingestion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.odftoolkit.odfdom.doc.OdfSpreadsheetDocument;

import ai.gebo.model.DocumentMetaInfos;

/**
 * Pins how a document's title and subtitle are found in its file: a title already in
 * the metadata is kept, a blank one is never set, the more reliable sources of each
 * format come first.
 */
class DocumentTitlesTest {

	private static Map<String, Object> meta() {
		final Map<String, Object> meta = new HashMap<>();
		meta.put(DocumentMetaInfos.CONTENT_CODE, "test-document");
		return meta;
	}

	private static Object title(Map<String, Object> meta) {
		return meta.get(DocumentMetaInfos.TITLE);
	}

	private static Object subtitle(Map<String, Object> meta) {
		return meta.get(DocumentMetaInfos.SUBTITLE);
	}

	private static Object author(Map<String, Object> meta) {
		return meta.get(DocumentMetaInfos.AUTHOR);
	}

	@Test
	void anAuthorAlreadyThereIsKeptNoneIsSetWithoutOne() {
		final Map<String, Object> meta = meta();
		meta.put(DocumentMetaInfos.AUTHOR, "From the content handler");
		assertFalse(DocumentTitles.putAuthor(meta, "From the file", "test"));
		assertEquals("From the content handler", author(meta));

		final Map<String, Object> none = meta();
		assertFalse(DocumentTitles.putAuthor(none, " ", "test"));
		assertFalse(DocumentTitles.hasAuthor(none));
		assertFalse(none.containsKey(DocumentMetaInfos.AUTHOR));
	}

	@Test
	void aTitleAlreadyThereIsKeptABlankOneIsReplacedAndTheWhiteSpaceIsCollapsed() {
		final Map<String, Object> meta = meta();
		meta.put(DocumentMetaInfos.TITLE, "From the content handler");
		assertFalse(DocumentTitles.putTitle(meta, "From the file", "test"));
		assertEquals("From the content handler", title(meta));

		final Map<String, Object> blank = meta();
		blank.put(DocumentMetaInfos.TITLE, "  ");
		assertTrue(DocumentTitles.putTitle(blank, "  The\n\t Secret   Doctrine ", "test"));
		assertEquals("The Secret Doctrine", title(blank));
	}

	@Test
	void aBlankCandidateIsNeverSet() {
		final Map<String, Object> meta = meta();
		assertFalse(DocumentTitles.putTitle(meta, " \n ", "test"));
		assertFalse(DocumentTitles.putSubtitle(meta, null, "test"));
		assertFalse(DocumentTitles.hasTitle(meta));
		assertFalse(DocumentTitles.hasSubtitle(meta));
	}

	@Test
	void aPdfInfoTitleLosesWhatTheOfficePrintingAddedAndASingleCharacterIsNoTitle() {
		assertEquals("Frammenti di un insegnamento sconosciuto",
				DocumentTitles.pdfInfoTitle("Microsoft Word - Frammenti di un insegnamento sconosciuto.doc"));
		assertEquals("I dodici sensi", DocumentTitles.pdfInfoTitle("microsoft  word -  I dodici sensi.DOCX"));
		assertEquals("La struttura antroposofica - Nicola Rosti - febbraio 2014 rev 2",
				DocumentTitles.pdfInfoTitle("La struttura antroposofica - Nicola Rosti - febbraio 2014 rev 2_docx"));
		assertEquals("Slides", DocumentTitles.pdfInfoTitle("Microsoft PowerPoint - Slides.pptx"));
		assertEquals("The Secret Doctrine, Vol. 1 of 4", DocumentTitles.pdfInfoTitle("The Secret Doctrine, Vol. 1 of 4"));
		assertNull(DocumentTitles.pdfInfoTitle("I"));
		assertNull(DocumentTitles.pdfInfoTitle("Microsoft Word - .doc"));
		assertNull(DocumentTitles.pdfInfoTitle(" "));
		assertNull(DocumentTitles.pdfInfoTitle(null));
	}

	@Test
	void anHtmlPageTitleComesFromItsTitleElementFirst() {
		final Map<String, Object> meta = meta();
		DocumentTitles.fromHtml(Jsoup.parse("<html><head><title> Isis Unveiled </title>"
				+ "<meta property=\"og:title\" content=\"Og title\">"
				+ "<meta name=\"description\" content=\"A master key to the mysteries\">"
				+ "<meta name=\"author\" content=\"H. P. Blavatsky\"></head>"
				+ "<body><h1>Heading</h1><p>text</p></body></html>"), meta);
		assertEquals("Isis Unveiled", title(meta));
		assertEquals("A master key to the mysteries", subtitle(meta));
		assertEquals("H. P. Blavatsky", author(meta));
	}

	@Test
	void anHtmlPageWithoutTitleElementUsesItsOgTitleElseItsFirstHeading() {
		final Map<String, Object> og = meta();
		DocumentTitles.fromHtml(Jsoup.parse("<html><head><meta property=\"og:title\" content=\"Og title\"></head>"
				+ "<body><h1>Heading</h1></body></html>"), og);
		assertEquals("Og title", title(og));

		final Map<String, Object> h1 = meta();
		DocumentTitles.fromHtml(Jsoup.parse("<html><body><p>intro</p><h1> The <b>Heading</b></h1><h1>Second</h1></body></html>"), h1);
		assertEquals("The Heading", title(h1));
		assertNull(subtitle(h1));
	}

	@Test
	void aMarkdownTitleComesFromItsFrontMatterFirst() {
		final Map<String, Object> meta = meta();
		DocumentTitles.fromMarkdown("---\nlayout: post\ntitle: \"The Voice of the Silence\"\nauthor: H. P. Blavatsky\n"
				+ "description: 'Fragments'\n---\n# Heading\n## Second\ntext", meta);
		assertEquals("The Voice of the Silence", title(meta));
		assertEquals("Fragments", subtitle(meta));
		assertEquals("H. P. Blavatsky", author(meta));
	}

	@Test
	void aMarkdownTitleWithoutFrontMatterIsItsFirstLevelOneHeadingOutsideCode() {
		final Map<String, Object> meta = meta();
		DocumentTitles.fromMarkdown("```\n# not a heading\n```\nintro\n\n## The Stanzas ##\n# Book of Dzyan\n# Later",
				meta);
		assertEquals("Book of Dzyan", title(meta));
		assertEquals("The Stanzas", subtitle(meta));
	}

	@Test
	void aMarkdownUnderlinedHeadingIsATitleAndAnUnclosedRuleIsNoFrontMatter() {
		final Map<String, Object> setext = meta();
		DocumentTitles.fromMarkdown("﻿Key to Theosophy\n=================\ntext", setext);
		assertEquals("Key to Theosophy", title(setext));

		final Map<String, Object> rule = meta();
		DocumentTitles.fromMarkdown("---\ntitle: not a front matter\n# The Heading", rule);
		assertEquals("The Heading", title(rule));
	}

	@Test
	void aMarkdownTextWithoutHeadingsHasNoTitle() {
		final Map<String, Object> meta = meta();
		DocumentTitles.fromMarkdown("just some text\nover two lines", meta);
		assertNull(title(meta));
		assertNull(author(meta));
	}

	@Test
	void anXlsxTitleAndSubjectComeFromItsProperties() throws Exception {
		final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			workbook.createSheet("data");
			workbook.getProperties().getCoreProperties().setTitle("Planetary chains");
			workbook.getProperties().getCoreProperties().setSubjectProperty("Rounds and races");
			workbook.getProperties().getCoreProperties().setCreator("A. P. Sinnett");
			workbook.write(bytes);
		}
		final Map<String, Object> meta = meta();
		try (Workbook read = WorkbookFactory.create(new ByteArrayInputStream(bytes.toByteArray()))) {
			DocumentTitles.fromWorkbook(read, meta);
		}
		assertEquals("Planetary chains", title(meta));
		assertEquals("Rounds and races", subtitle(meta));
		assertEquals("A. P. Sinnett", author(meta));
	}

	@Test
	void anXlsTitleAndSubjectComeFromItsSummaryInformation() throws Exception {
		final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		try (HSSFWorkbook workbook = new HSSFWorkbook()) {
			workbook.createSheet("data");
			workbook.createInformationProperties();
			workbook.getSummaryInformation().setTitle("Cosmic cycles");
			workbook.getSummaryInformation().setSubject("Manvantaras");
			workbook.getSummaryInformation().setAuthor("G. de Purucker");
			workbook.write(bytes);
		}
		final Map<String, Object> meta = meta();
		try (Workbook read = WorkbookFactory.create(new ByteArrayInputStream(bytes.toByteArray()))) {
			DocumentTitles.fromWorkbook(read, meta);
		}
		assertEquals("Cosmic cycles", title(meta));
		assertEquals("Manvantaras", subtitle(meta));
		assertEquals("G. de Purucker", author(meta));
	}

	@Test
	void aSpreadsheetWithoutPropertiesHasNoTitle() throws Exception {
		final Map<String, Object> meta = meta();
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			DocumentTitles.fromWorkbook(workbook, meta);
		}
		assertNull(title(meta));
	}

	@Test
	void anOpenDocumentTitleAndSubjectComeFromItsMeta() throws Exception {
		final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		final OdfSpreadsheetDocument document = OdfSpreadsheetDocument.newSpreadsheetDocument();
		document.getOfficeMetadata().setTitle("Root races");
		document.getOfficeMetadata().setSubject("Chronology");
		document.getOfficeMetadata().setInitialCreator("R. Steiner");
		document.getOfficeMetadata().setCreator("Last editor");
		document.save(bytes);
		document.close();
		final Map<String, Object> meta = meta();
		final OdfSpreadsheetDocument read = OdfSpreadsheetDocument
				.loadDocument(new ByteArrayInputStream(bytes.toByteArray()));
		DocumentTitles.fromOdfDocument(read, meta, "ods meta");
		read.close();
		assertEquals("Root races", title(meta));
		assertEquals("Chronology", subtitle(meta));
		assertEquals("R. Steiner", author(meta));
	}
}
