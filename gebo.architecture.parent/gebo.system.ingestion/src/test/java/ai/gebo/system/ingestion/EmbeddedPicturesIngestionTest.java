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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.poi.sl.usermodel.PictureData.PictureType;
import org.apache.poi.util.Units;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.system.ingestion.impl.GAbstractConfiguredHandler;
import ai.gebo.system.ingestion.model.IngestionFileType;
import ai.gebo.system.ingestion.model.IngestionHandlerConfig;
import ai.gebo.system.ingestion.msformats.impl.MSPowerPointIngestionHandler;
import ai.gebo.system.ingestion.msformats.impl.MSWordIngestionHandler;
import ai.gebo.system.ingestion.odformats.impl.GOdtIngestionHandlerImpl;
import ai.gebo.system.ingestion.text.impl.HtmlIngestionHandler;

/**
 * The pictures inside the documents being read are described by a model, each
 * as a document of its own, when picture descriptions are on; with them off the
 * documents are read as before.
 */
class EmbeddedPicturesIngestionTest {
	static class Extractor implements IGMediaTextExtractor {
		boolean pictures = true;
		int most = 30;
		final List<String> asked = new ArrayList<>();

		@Override
		public boolean describesPictures() {
			return pictures;
		}

		@Override
		public String describePicture(String name, String mimeType, byte[] data) {
			asked.add(name + " " + mimeType);
			return "A bar chart of the sales by quarter.";
		}

		@Override
		public int maxDescribedPagesPerDocument() {
			return most;
		}

		@Override
		public long maxFileBytes() {
			return 20L * 1024 * 1024;
		}

		@Override
		public boolean transcribes() {
			return false;
		}

		@Override
		public String transcribe(String name, String mimeType, InputStream content) {
			return null;
		}
	}

	/** A picture of noise: large enough not to be taken for a decoration. */
	private static byte[] png(long seed) throws IOException {
		final BufferedImage image = new BufferedImage(60, 60, BufferedImage.TYPE_INT_RGB);
		final Random random = new Random(seed);
		for (int x = 0; x < 60; x++) {
			for (int y = 0; y < 60; y++) {
				image.setRGB(x, y, random.nextInt(0xFFFFFF));
			}
		}
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "png", out);
		return out.toByteArray();
	}

	/** A dot of one colour: a decoration. */
	private static byte[] dot() throws IOException {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(new BufferedImage(4, 4, BufferedImage.TYPE_INT_RGB), "png", out);
		return out.toByteArray();
	}

	private static IGIngestionHandlerConfigDao dao() {
		final IngestionFileType type = new IngestionFileType();
		type.setExtensions(List.of(".docx", ".pptx", ".odt", ".html"));
		type.setContentTypes(List.of());
		final IngestionHandlerConfig config = new IngestionHandlerConfig();
		config.setFileTypes(List.of(type));
		final IGIngestionHandlerConfigDao dao = mock(IGIngestionHandlerConfigDao.class);
		when(dao.findByCode(anyString())).thenReturn(config);
		return dao;
	}

	@SuppressWarnings("unchecked")
	private static <H extends GAbstractConfiguredHandler> H with(H handler, Extractor extractor) {
		final ObjectProvider<IGMediaTextExtractor> provider = mock(ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(extractor);
		handler.setEmbeddedPicturesExtractor(provider);
		return handler;
	}

	private static GDocumentReference reference(String name, String extension) {
		final GDocumentReference reference = new GDocumentReference();
		reference.setCode("doc-" + name);
		reference.setName(name);
		reference.setExtension(extension);
		return reference;
	}

	private static List<Document> described(List<Document> documents) {
		return documents.stream()
				.filter(d -> "description".equals(d.getMetadata().get(IGMediaTextExtractor.MODEL_WRITTEN_METADATA)))
				.toList();
	}

	private static byte[] docx() throws Exception {
		try (XWPFDocument document = new XWPFDocument(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			document.createParagraph().createRun().setText("The sales of the year.");
			final byte[] chart = png(1);
			// the same picture twice (a logo on every page), another one, and a decoration
			for (byte[] picture : List.of(chart, chart, png(2), dot())) {
				document.createParagraph().createRun().addPicture(new ByteArrayInputStream(picture),
						XWPFDocument.PICTURE_TYPE_PNG, "chart.png", Units.toEMU(60), Units.toEMU(60));
			}
			document.write(out);
			return out.toByteArray();
		}
	}

	@Test
	void thePicturesOfAWordDocumentAreDescribedOnceEach() throws Exception {
		final Extractor extractor = new Extractor();
		final List<Document> documents = with(new MSWordIngestionHandler(dao()), extractor)
				.handleContent(reference("sales.docx", ".docx"), new ByteArrayInputStream(docx()), new HashMap<>())
				.toList();

		assertTrue(documents.get(0).getText().contains("The sales of the year."));
		assertEquals(2, described(documents).size(), "two different pictures; the repeated one and the dot are not");
		assertEquals("A bar chart of the sales by quarter.", described(documents).get(0).getText());
		assertTrue(extractor.asked.stream().allMatch(a -> a.startsWith("sales.docx picture ") && a.endsWith("image/png")),
				extractor.asked.toString());
	}

	@Test
	void withDescriptionsOffTheDocumentIsReadAsBefore() throws Exception {
		final Extractor extractor = new Extractor();
		extractor.pictures = false;
		final List<Document> documents = with(new MSWordIngestionHandler(dao()), extractor)
				.handleContent(reference("sales.docx", ".docx"), new ByteArrayInputStream(docx()), new HashMap<>())
				.toList();
		assertEquals(1, documents.size());
		assertTrue(extractor.asked.isEmpty());
		// and with no extractor at all, as where no model is deployed
		assertEquals(1, new MSWordIngestionHandler(dao())
				.handleContent(reference("sales.docx", ".docx"), new ByteArrayInputStream(docx()), new HashMap<>())
				.count());
	}

	@Test
	void noMorePicturesThanTheLimitOfADocument() throws Exception {
		final Extractor extractor = new Extractor();
		extractor.most = 1;
		final List<Document> documents = with(new MSWordIngestionHandler(dao()), extractor)
				.handleContent(reference("sales.docx", ".docx"), new ByteArrayInputStream(docx()), new HashMap<>())
				.toList();
		assertEquals(1, described(documents).size());
		assertEquals(1, extractor.asked.size());
	}

	@Test
	void aPictureOfASlideIsDescribedWithItsSlideNumber() throws Exception {
		final byte[] pptx;
		try (XMLSlideShow show = new XMLSlideShow(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			show.createSlide().createTextBox().setText("Agenda");
			final XSLFSlide second = show.createSlide();
			second.createTextBox().setText("Sales");
			second.createPicture(show.addPicture(png(3), PictureType.PNG));
			show.write(out);
			pptx = out.toByteArray();
		}
		final Extractor extractor = new Extractor();
		final List<Document> documents = with(new MSPowerPointIngestionHandler(dao()), extractor)
				.handleContent(reference("deck.pptx", ".pptx"), new ByteArrayInputStream(pptx), new HashMap<>())
				.toList();

		assertEquals(1, described(documents).size());
		assertEquals("2", described(documents).get(0).getMetadata().get(DocumentMetaInfos.CONTENT_PAGE));
		assertTrue(extractor.asked.get(0).startsWith("deck.pptx page 2 picture "), extractor.asked.toString());
	}

	@Test
	void thePicturesOfAnOpenDocumentTextAreDescribed() throws Exception {
		final byte[] odt;
		try (OdfTextDocument document = OdfTextDocument.newTextDocument();
				ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			document.newParagraph("The sales of the year.");
			document.getPackage().insert(png(4), "Pictures/chart.png", "image/png");
			document.save(out);
			odt = out.toByteArray();
		}
		final Extractor extractor = new Extractor();
		final List<Document> documents = with(new GOdtIngestionHandlerImpl(dao()), extractor)
				.handleContent(reference("sales.odt", ".odt"), new ByteArrayInputStream(odt), new HashMap<>())
				.toList();

		assertEquals(1, described(documents).size());
		assertEquals(List.of("sales.odt picture chart.png image/png"), extractor.asked);
	}

	@Test
	void aPictureWrittenInsideAWebPageIsDescribedOneNamedByAddressIsNot() throws Exception {
		final String html = "<html><head><title>Sales</title></head><body><p>The sales.</p>"
				+ "<img alt=\"chart\" src=\"data:image/png;base64," + Base64.getEncoder().encodeToString(png(5)) + "\">"
				+ "<img src=\"https://example.invalid/logo.png\"></body></html>";
		final Extractor extractor = new Extractor();
		final List<Document> documents = with(new HtmlIngestionHandler(dao()), extractor)
				.handleContent(reference("sales.html", ".html"),
						new ByteArrayInputStream(html.getBytes(java.nio.charset.StandardCharsets.UTF_8)),
						new HashMap<>())
				.toList();

		assertEquals(1, described(documents).size());
		assertEquals(List.of("sales.html picture chart image/png"), extractor.asked);
	}

	@Test
	void onlyTheTypesAModelReadsAsPicturesAreTaken() {
		assertEquals("image/jpeg", new EmbeddedPicture("a.jpg", null, new byte[0]).readableType());
		assertEquals("image/jpeg", new EmbeddedPicture(null, "image/jpg", new byte[0]).readableType());
		assertEquals("image/png", new EmbeddedPicture("a.bin", "IMAGE/PNG", new byte[0]).readableType());
		assertEquals(null, new EmbeddedPicture("drawing.emf", "image/x-emf", new byte[0]).readableType());
		assertEquals(null, new EmbeddedPicture("drawing.svg", "image/svg+xml", new byte[0]).readableType());
		assertEquals(null, new EmbeddedPicture(null, null, new byte[0]).readableType());
	}

	/** Kept so that the workbook writer stays on the test classpath the Excel handler's pictures need. */
	@Test
	void aWorkbookTellsItsPictures() throws Exception {
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			workbook.createSheet("sales");
			workbook.addPicture(png(6), org.apache.poi.ss.usermodel.Workbook.PICTURE_TYPE_PNG);
			assertEquals("image/png", workbook.getAllPictures().get(0).getMimeType());
		}
	}
}
