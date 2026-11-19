/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.system.ingestion.media.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.ObjectProvider;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.IGMediaTextExtractor;
import ai.gebo.system.ingestion.model.IngestionFileType;
import ai.gebo.system.ingestion.model.IngestionHandlerConfig;
import ai.gebo.system.ingestion.pdf.impl.PdfIngestionHandlerMediaAccess;

/**
 * Pictures are described and recordings transcribed by a model only while the
 * installation turned that on; off, the files are ones no reader takes, as before.
 */
class MediaIngestionHandlerTest {
	static class Extractor implements IGMediaTextExtractor {
		boolean pictures;
		boolean recordings;
		final List<String> asked = new ArrayList<>();

		@Override
		public boolean describesPictures() {
			return pictures;
		}

		@Override
		public String describePicture(String name, String mimeType, byte[] data) {
			asked.add("describe " + name + " " + mimeType + " " + data.length);
			return "A bar chart of the sales by quarter.";
		}

		@Override
		public int maxDescribedPagesPerDocument() {
			return 1;
		}

		@Override
		public long maxFileBytes() {
			return 1000;
		}

		@Override
		public boolean transcribes() {
			return recordings;
		}

		@Override
		public String transcribe(String name, String mimeType, InputStream content) throws IOException {
			asked.add("transcribe " + name + " " + mimeType + " " + content.readAllBytes().length);
			return "Good morning everybody.";
		}
	}

	@SuppressWarnings("unchecked")
	static ObjectProvider<IGMediaTextExtractor> providing(IGMediaTextExtractor extractor) {
		final ObjectProvider<IGMediaTextExtractor> provider = mock(ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(extractor);
		return provider;
	}

	private static IGIngestionHandlerConfigDao dao(List<String> extensions, List<String> types) {
		final IngestionFileType type = new IngestionFileType();
		type.setExtensions(extensions);
		type.setContentTypes(types);
		final IngestionHandlerConfig config = new IngestionHandlerConfig();
		config.setFileTypes(List.of(type));
		final IGIngestionHandlerConfigDao dao = mock(IGIngestionHandlerConfigDao.class);
		when(dao.findByCode(anyString())).thenReturn(config);
		return dao;
	}

	private static GDocumentReference reference(String name, String extension, String type) {
		final GDocumentReference reference = new GDocumentReference();
		reference.setCode("doc-" + name);
		reference.setName(name);
		reference.setExtension(extension);
		reference.setContentType(type);
		return reference;
	}

	private static MediaIngestionHandler handler(Extractor extractor) {
		return new MediaIngestionHandler(dao(List.of(".png", ".mp3"), List.of("image/png", "audio/mpeg")),
				providing(extractor));
	}

	@Test
	void aFileIsTakenOnlyWhileItsCapabilityIsOn() {
		final Extractor extractor = new Extractor();
		final MediaIngestionHandler handler = handler(extractor);
		assertFalse(handler.canManageContent(reference("chart.png", ".png", "image/png")), "off: nobody reads it");
		assertFalse(handler.canManageContent(reference("talk.mp3", ".mp3", "audio/mpeg")));
		assertEquals(List.of(), handler.getHandledExtensions());

		extractor.pictures = true;
		assertTrue(handler.canManageContent(reference("chart.png", ".png", "image/png")));
		assertFalse(handler.canManageContent(reference("talk.mp3", ".mp3", "audio/mpeg")));
		assertEquals(List.of(".png"), handler.getHandledExtensions());

		extractor.recordings = true;
		assertTrue(handler.canManageContent(reference("talk.mp3", ".mp3", "audio/mpeg")));
		assertFalse(new MediaIngestionHandler(dao(List.of(".png"), List.of("image/png")), providing(null))
				.canManageContent(reference("chart.png", ".png", "image/png")), "no model deployed here");
	}

	@Test
	void aPictureGivesItsDescriptionAndARecordingItsTranscriptMarkedAsWrittenByAModel() throws Exception {
		final Extractor extractor = new Extractor();
		extractor.pictures = true;
		extractor.recordings = true;
		final MediaIngestionHandler handler = handler(extractor);

		final Map<String, Object> pictureMeta = new HashMap<>();
		final List<Document> picture = handler.handleContent(reference("chart.png", ".png", "image/png"),
				new ByteArrayInputStream(new byte[] { 1, 2, 3 }), pictureMeta).toList();
		assertEquals(1, picture.size());
		assertEquals("A bar chart of the sales by quarter.", picture.get(0).getText());
		assertEquals("description", picture.get(0).getMetadata().get(IGMediaTextExtractor.MODEL_WRITTEN_METADATA));

		final List<Document> recording = handler.handleContent(reference("talk.mp3", ".mp3", "audio/mpeg"),
				new ByteArrayInputStream(new byte[] { 1, 2 }), new HashMap<>()).toList();
		assertEquals("Good morning everybody.", recording.get(0).getText());
		assertEquals("transcript", recording.get(0).getMetadata().get(IGMediaTextExtractor.MODEL_WRITTEN_METADATA));
		assertEquals(List.of("describe chart.png image/png 3", "transcribe talk.mp3 audio/mpeg 2"), extractor.asked);

		// a picture larger than a model is given is left unread
		assertTrue(handler.handleContent(reference("huge.png", ".png", "image/png"),
				new ByteArrayInputStream(new byte[2000]), new HashMap<>()).toList().isEmpty());
	}

	@Test
	void aPdfPageWithoutTextIsDescribedWithinTheNumberOfPagesAllowed() throws Exception {
		final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		final com.itextpdf.text.Document pdf = new com.itextpdf.text.Document(PageSize.A4);
		final PdfWriter writer = PdfWriter.getInstance(pdf, bytes);
		pdf.open();
		pdf.add(new Paragraph("A page with its own text."));
		pdf.newPage();
		writer.setPageEmpty(false);
		pdf.newPage();
		writer.setPageEmpty(false);
		pdf.close();

		final Extractor extractor = new Extractor();
		final GDocumentReference reference = reference("scan.pdf", ".pdf", "application/pdf");
		final List<Document> off = PdfIngestionHandlerMediaAccess.read(reference, bytes.toByteArray(), providing(extractor));
		assertEquals(3, off.size());
		assertTrue(off.get(1).getText() == null || off.get(1).getText().isBlank(), "off: an empty page stays empty");

		extractor.pictures = true;
		final List<Document> on = PdfIngestionHandlerMediaAccess.read(reference, bytes.toByteArray(), providing(extractor));
		assertTrue(on.get(0).getText().contains("A page with its own text."));
		assertEquals("A bar chart of the sales by quarter.", on.get(1).getText());
		assertEquals("description", on.get(1).getMetadata().get(IGMediaTextExtractor.MODEL_WRITTEN_METADATA));
		assertEquals("2", on.get(1).getMetadata().get(DocumentMetaInfos.CONTENT_PAGE));
		assertTrue(on.get(2).getText() == null || on.get(2).getText().isBlank(), "one page a document is allowed here");
		assertEquals(1, extractor.asked.size());
		assertTrue(extractor.asked.get(0).startsWith("describe scan.pdf page 2 image/png "));
	}
}
