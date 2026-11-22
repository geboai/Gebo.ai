/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.system.ingestion;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
 * While images are taken, the pictures inside the documents being read are
 * given each as a document of its own holding the image as media; otherwise the
 * documents are read as before. No model is involved.
 */
class EmbeddedPicturesIngestionTest {
	private static ai.gebo.system.ingestion.config.ImagesIngestionConfig images(boolean enabled, int most) {
		final ai.gebo.system.ingestion.config.ImagesIngestionConfig config = new ai.gebo.system.ingestion.config.ImagesIngestionConfig();
		config.setIngestionEnabled(enabled);
		config.setMaxImagesPerDocument(most);
		return config;
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
		type.setExtensions(List.of(".docx", ".pptx", ".odt", ".html", ".png"));
		type.setContentTypes(List.of());
		final IngestionHandlerConfig config = new IngestionHandlerConfig();
		config.setFileTypes(List.of(type));
		final IGIngestionHandlerConfigDao dao = mock(IGIngestionHandlerConfigDao.class);
		when(dao.findByCode(anyString())).thenReturn(config);
		return dao;
	}

	private static <H extends GAbstractConfiguredHandler> H with(H handler,
			ai.gebo.system.ingestion.config.ImagesIngestionConfig config) {
		handler.setImagesConfig(config);
		return handler;
	}

	private static GDocumentReference reference(String name, String extension) {
		final GDocumentReference reference = new GDocumentReference();
		reference.setCode("doc-" + name);
		reference.setName(name);
		reference.setExtension(extension);
		return reference;
	}

	/** The image documents among what a reader gave: the ones holding media, not text. */
	private static List<Document> images(List<Document> documents) {
		return documents.stream().filter(d -> !d.isText()).toList();
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
	void thePicturesOfAWordDocumentAreGivenOnceEachAsImageDocuments() throws Exception {
		final HashMap<String, Object> metadata = new HashMap<>();
		metadata.put("gebo_test", "kept");
		final List<Document> documents = with(new MSWordIngestionHandler(dao()), images(true, 30))
				.handleContent(reference("sales.docx", ".docx"), new ByteArrayInputStream(docx()), metadata).toList();

		assertTrue(documents.get(0).getText().contains("The sales of the year."));
		assertEquals(2, images(documents).size(), "two different pictures; the repeated one and the dot are not");
		final Document image = images(documents).get(0);
		assertEquals("image/png", image.getMedia().getMimeType().toString());
		assertArrayEquals(png(1), image.getMedia().getDataAsByteArray(), "the image as it is");
		assertEquals("kept", image.getMetadata().get("gebo_test"), "the metadata of the document's texts");
		assertTrue(image.getMetadata().containsKey(ai.gebo.system.ingestion.config.ImagesIngestionConfig.IMAGE_NAME_METADATA));
	}

	@Test
	void withImagesNotTakenTheDocumentIsReadAsBefore() throws Exception {
		assertEquals(1, with(new MSWordIngestionHandler(dao()), images(false, 30))
				.handleContent(reference("sales.docx", ".docx"), new ByteArrayInputStream(docx()), new HashMap<>())
				.count());
		// and with no configuration at all
		assertEquals(1, new MSWordIngestionHandler(dao())
				.handleContent(reference("sales.docx", ".docx"), new ByteArrayInputStream(docx()), new HashMap<>())
				.count());
	}

	@Test
	void noMoreImagesThanTheLimitOfADocument() throws Exception {
		final List<Document> documents = with(new MSWordIngestionHandler(dao()), images(true, 1))
				.handleContent(reference("sales.docx", ".docx"), new ByteArrayInputStream(docx()), new HashMap<>())
				.toList();
		assertEquals(1, images(documents).size());
	}

	@Test
	void aPictureOfASlideCarriesItsSlideNumber() throws Exception {
		final byte[] pptx;
		try (XMLSlideShow show = new XMLSlideShow(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			show.createSlide().createTextBox().setText("Agenda");
			final XSLFSlide second = show.createSlide();
			second.createTextBox().setText("Sales");
			second.createPicture(show.addPicture(png(3), PictureType.PNG));
			show.write(out);
			pptx = out.toByteArray();
		}
		final List<Document> documents = with(new MSPowerPointIngestionHandler(dao()), images(true, 30))
				.handleContent(reference("deck.pptx", ".pptx"), new ByteArrayInputStream(pptx), new HashMap<>())
				.toList();

		assertEquals(1, images(documents).size());
		assertEquals("2", images(documents).get(0).getMetadata().get(DocumentMetaInfos.CONTENT_PAGE));
	}

	@Test
	void thePicturesOfAnOpenDocumentTextAreGiven() throws Exception {
		final byte[] odt;
		try (OdfTextDocument document = OdfTextDocument.newTextDocument();
				ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			document.newParagraph("The sales of the year.");
			document.getPackage().insert(png(4), "Pictures/chart.png", "image/png");
			document.save(out);
			odt = out.toByteArray();
		}
		final List<Document> documents = with(new GOdtIngestionHandlerImpl(dao()), images(true, 30))
				.handleContent(reference("sales.odt", ".odt"), new ByteArrayInputStream(odt), new HashMap<>())
				.toList();

		assertEquals(1, images(documents).size());
		assertEquals("chart.png", images(documents).get(0).getMetadata()
				.get(ai.gebo.system.ingestion.config.ImagesIngestionConfig.IMAGE_NAME_METADATA));
	}

	@Test
	void aPictureWrittenInsideAWebPageIsGivenOneNamedByAddressIsNot() throws Exception {
		final String html = "<html><head><title>Sales</title></head><body><p>The sales.</p>"
				+ "<img alt=\"chart\" src=\"data:image/png;base64," + Base64.getEncoder().encodeToString(png(5)) + "\">"
				+ "<img src=\"https://example.invalid/logo.png\"></body></html>";
		final List<Document> documents = with(new HtmlIngestionHandler(dao()), images(true, 30))
				.handleContent(reference("sales.html", ".html"),
						new ByteArrayInputStream(html.getBytes(java.nio.charset.StandardCharsets.UTF_8)),
						new HashMap<>())
				.toList();

		assertEquals(1, images(documents).size());
	}

	@Test
	void anImageFileIsReadOnlyWhileImagesAreTaken() throws Exception {
		final GDocumentReference file = reference("chart.png", ".png");
		final ai.gebo.system.ingestion.media.impl.ImageIngestionHandler off = with(
				new ai.gebo.system.ingestion.media.impl.ImageIngestionHandler(dao()), images(false, 30));
		org.junit.jupiter.api.Assertions.assertFalse(off.canManageContent(file), "a file no reader takes, as before");
		assertEquals(List.of(), off.getHandledExtensions());

		final ai.gebo.system.ingestion.media.impl.ImageIngestionHandler on = with(
				new ai.gebo.system.ingestion.media.impl.ImageIngestionHandler(dao()), images(true, 30));
		assertTrue(on.canManageContent(file));
		final List<Document> documents = on.handleContent(file, new ByteArrayInputStream(png(7)), new HashMap<>())
				.toList();
		assertEquals(1, documents.size());
		assertArrayEquals(png(7), documents.get(0).getMedia().getDataAsByteArray());
	}

	@Test
	void aRecordingAndAVideoAreTakenEachByItsOwnSwitch() throws Exception {
		final IngestionFileType type = new IngestionFileType();
		type.setExtensions(List.of(".mp3", ".mp4"));
		type.setContentTypes(List.of("audio/mpeg", "video/mp4"));
		final IngestionHandlerConfig config = new IngestionHandlerConfig();
		config.setFileTypes(List.of(type));
		final IGIngestionHandlerConfigDao dao = mock(IGIngestionHandlerConfigDao.class);
		when(dao.findByCode(anyString())).thenReturn(config);
		final ai.gebo.system.ingestion.media.impl.RecordingsAndVideosIngestionHandler handler = new ai.gebo.system.ingestion.media.impl.RecordingsAndVideosIngestionHandler(
				dao);
		final GDocumentReference talk = reference("talk.mp3", ".mp3");
		final GDocumentReference film = reference("film.mp4", ".mp4");
		org.junit.jupiter.api.Assertions.assertFalse(handler.canManageContent(talk), "off: a file no reader takes");
		org.junit.jupiter.api.Assertions.assertFalse(handler.canManageContent(film));

		handler.setSwitches(true, false);
		assertTrue(handler.canManageContent(talk));
		org.junit.jupiter.api.Assertions.assertFalse(handler.canManageContent(film), "videos have their own switch");
		assertEquals(List.of(".mp3"), handler.getHandledExtensions());
		final List<Document> documents = handler
				.handleContent(talk, new ByteArrayInputStream(new byte[] { 1, 2, 3 }), new HashMap<>()).toList();
		assertEquals(1, documents.size());
		assertEquals("audio/mpeg", documents.get(0).getMedia().getMimeType().toString());
		assertArrayEquals(new byte[] { 1, 2, 3 }, documents.get(0).getMedia().getDataAsByteArray());
	}

	@Test
	void whoReadsTextsGetsTextsWhoPassesImagesOnGetsBoth() throws Exception {
		final List<Document> all = with(new MSWordIngestionHandler(dao()), images(true, 30))
				.handleContent(reference("sales.docx", ".docx"), new ByteArrayInputStream(docx()), new HashMap<>())
				.toList();
		final IGDocumentReferenceIngestionHandler.IngestionHandlerData data = new IGDocumentReferenceIngestionHandler.IngestionHandlerData();
		data.setStream(all.stream());
		assertEquals(1, data.getStream().count());
		data.setStream(all.stream());
		assertEquals(3, data.getStreamWithMedia().count());
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
