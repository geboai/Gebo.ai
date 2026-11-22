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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import com.sun.net.httpserver.HttpServer;

import ai.gebo.document.model.GeboDocument;
import ai.gebo.document.model.GeboImageDocumentFragment;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.config.ImagesIngestionConfig;
import ai.gebo.system.ingestion.config.RecordingsAndVideosIngestionConfig;
import ai.gebo.system.ingestion.impl.GAbstractConfiguredHandler;
import ai.gebo.system.ingestion.impl.GDocumentReferenceIngestionHandlerImpl;
import ai.gebo.system.ingestion.impl.SpecializedHandlerRepositoryPattern;
import ai.gebo.system.ingestion.model.IngestionFileType;
import ai.gebo.system.ingestion.model.IngestionHandlerConfig;
import ai.gebo.system.ingestion.msformats.impl.MSWordIngestionHandler;
import ai.gebo.system.ingestion.text.impl.HtmlIngestionHandler;

/**
 * A Gebo document is complete of its media while the installation ingests them: a
 * reader gives the images, the recordings and the videos of a source as fragments of
 * their kind, with their content type and their bytes in BASE64, and a Gebo document
 * holding such fragments is ingested with them. With the switches off it is the text
 * only document it always was. A media a web page names by an address that is not
 * reachable is omitted.
 */
class GeboDocumentMediaTest {
	private static ImagesIngestionConfig images(boolean enabled) {
		final ImagesIngestionConfig config = new ImagesIngestionConfig();
		config.setIngestionEnabled(enabled);
		return config;
	}

	private static RecordingsAndVideosIngestionConfig media(boolean recordings, boolean videos) {
		final RecordingsAndVideosIngestionConfig config = new RecordingsAndVideosIngestionConfig();
		config.setRecordingsEnabled(recordings);
		config.setVideosEnabled(videos);
		return config;
	}

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

	private static IGIngestionHandlerConfigDao dao() {
		final IngestionFileType type = new IngestionFileType();
		type.setExtensions(List.of(".docx", ".html"));
		type.setContentTypes(List.of());
		final IngestionHandlerConfig config = new IngestionHandlerConfig();
		config.setFileTypes(List.of(type));
		final IGIngestionHandlerConfigDao dao = mock(IGIngestionHandlerConfigDao.class);
		when(dao.findByCode(anyString())).thenReturn(config);
		return dao;
	}

	private static <H extends GAbstractConfiguredHandler> H with(H handler, ImagesIngestionConfig images,
			RecordingsAndVideosIngestionConfig media) {
		handler.setImagesConfig(images);
		handler.setMediaConfig(media);
		return handler;
	}

	private static GDocumentReference reference(String name, String extension) {
		final GDocumentReference reference = new GDocumentReference();
		reference.setCode("doc-" + name);
		reference.setName(name);
		reference.setExtension(extension);
		return reference;
	}

	private static byte[] docx() throws Exception {
		try (XWPFDocument document = new XWPFDocument(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			document.createParagraph().createRun().setText("The sales of the year.");
			for (byte[] picture : List.of(png(1), png(2))) {
				document.createParagraph().createRun().addPicture(new ByteArrayInputStream(picture),
						XWPFDocument.PICTURE_TYPE_PNG, "chart.png", Units.toEMU(60), Units.toEMU(60));
			}
			document.write(out);
			return out.toByteArray();
		}
	}

	@Test
	void aWordDocumentBecomesAGeboDocumentWithItsImages() throws Exception {
		final GeboDocument document = with(new MSWordIngestionHandler(dao()), images(true), media(false, false))
				.handleDocument(reference("sales.docx", ".docx"), new ByteArrayInputStream(docx()), new HashMap<>());

		assertTrue(document.getTexts().get(0).getContent().contains("The sales of the year."));
		assertEquals(2, document.getImages().size());
		final GeboImageDocumentFragment image = document.getImages().get(0);
		assertEquals("image/png", image.getContentType(), "the content type of the media");
		assertEquals("BASE64", image.getEncoding());
		assertArrayEquals(png(1), Base64.getDecoder().decode(image.getContent()), "its bytes, encoded");
		assertArrayEquals(png(1), image.streamContent().readAllBytes());
		assertNotNull(image.getName());
		assertTrue(document.getAudios().isEmpty());
		assertTrue(document.getVideos().isEmpty());
	}

	@Test
	void withTheSwitchesOffAGeboDocumentIsItsTextAsBefore() throws Exception {
		for (ImagesIngestionConfig config : new ImagesIngestionConfig[] { images(false), null }) {
			final GeboDocument document = with(new MSWordIngestionHandler(dao()), config, null).handleDocument(
					reference("sales.docx", ".docx"), new ByteArrayInputStream(docx()), new HashMap<>());
			assertEquals(1, document.getTexts().size());
			assertTrue(document.getImages().isEmpty());
		}
	}

	@Test
	void aWebPageGivesTheMediaItNamesAndOmitsTheOnesNotReachable() throws Exception {
		final byte[] chart = png(3);
		final byte[] second = png(4);
		final byte[] talk = new byte[4096];
		new Random(9).nextBytes(talk);
		final HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
		server.createContext("/", exchange -> {
			final String path = exchange.getRequestURI().getPath();
			final byte[] body = path.equals("/pics/chart.png") ? chart
					: path.equals("/site/pics/second.png") ? second : path.equals("/talk.mp3") ? talk : null;
			if (body == null) {
				exchange.sendResponseHeaders(404, -1);
			} else {
				exchange.getResponseHeaders().add("Content-Type",
						path.endsWith(".mp3") ? "audio/mpeg" : "image/png; charset=binary");
				exchange.sendResponseHeaders(200, body.length);
				exchange.getResponseBody().write(body);
			}
			exchange.close();
		});
		server.start();
		try {
			final String base = "http://127.0.0.1:" + server.getAddress().getPort();
			final String html = "<html><head><title>Sales</title></head><body><p>The sales.</p>"
					+ "<img alt=\"chart\" src=\"" + base + "/pics/chart.png\">"
					+ "<img src=\"pics/second.png\">"
					+ "<img src=\"" + base + "/pics/missing.png\">"
					+ "<img src=\"http://127.0.0.1:1/unreachable.png\">"
					+ "<audio src=\"" + base + "/talk.mp3\"></audio>"
					+ "<video><source src=\"" + base + "/talk.mp3\"></video></body></html>";
			final GDocumentReference reference = reference("sales.html", ".html");
			reference.setUri(base + "/site/sales.html");

			final List<Document> documents = with(new HtmlIngestionHandler(dao()), images(true), media(true, false))
					.handleContent(reference, new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8)),
							new HashMap<>())
					.toList();
			assertTrue(documents.get(0).getText().contains("The sales."), "the page is read all the same");
			final List<Document> media = documents.stream().filter(d -> !d.isText()).toList();
			assertEquals(3, media.size(), "two pictures and the recording; the missing and the unreachable are omitted");
			assertArrayEquals(chart, media.get(0).getMedia().getDataAsByteArray());
			assertArrayEquals(second, media.get(1).getMedia().getDataAsByteArray(),
					"an address relative to the page is resolved against the page's own");
			assertEquals("audio/mpeg", media.get(2).getMedia().getMimeType().toString());

			final GeboDocument document = with(new HtmlIngestionHandler(dao()), images(true), media(true, false))
					.handleDocument(reference, new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8)),
							new HashMap<>());
			assertEquals("text/html", document.getTexts().get(0).getContentType(), "the page keeps its own text form");
			assertEquals(2, document.getImages().size());
			assertEquals("chart", document.getImages().get(0).getName());
			assertEquals(1, document.getAudios().size());
			assertEquals("audio/mpeg", document.getAudios().get(0).getContentType());
			assertArrayEquals(talk, document.getAudios().get(0).streamContent().readAllBytes());
			assertTrue(document.getVideos().isEmpty(), "videos are not ingested here");

			final List<Document> off = with(new HtmlIngestionHandler(dao()), images(false), media(false, false))
					.handleContent(reference, new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8)),
							new HashMap<>())
					.toList();
			assertEquals(1, off.size(), "with the switches off nothing is fetched");
		} finally {
			server.stop(0);
		}
	}

	@Test
	void aGeboDocumentIsIngestedWithTheMediaOfTheKindsSwitchedOn() throws Exception {
		final GeboDocument document = GeboDocument.of("The sales of the year.");
		document.addMedia("chart.png", "image/png", png(5)).getCustomMetaData().put("slide", "2");
		document.addMedia("talk.mp3", "audio/mpeg", new byte[] { 1, 2, 3 });
		document.addMedia("clip.mp4", "video/mp4", new byte[] { 4, 5, 6 });

		final SpecializedHandlerRepositoryPattern repository = mock(SpecializedHandlerRepositoryPattern.class);
		when(repository.getImplementations()).thenReturn(List.of());
		final GDocumentReferenceIngestionHandlerImpl handler = new GDocumentReferenceIngestionHandlerImpl(repository,
				mock(IGLanguageDetector.class));
		final GDocumentReference reference = reference("sales.gebo", ".gebo");

		assertEquals(1, handler.handleContent(reference, document).getStreamWithMedia().count(),
				"nothing switched on: the texts only");

		handler.setMediaIngestion(images(true), media(true, false));
		final IGDocumentReferenceIngestionHandler.IngestionHandlerData data = handler.handleContent(reference, document);
		assertEquals(1, data.getStream().count(), "who reads texts gets texts");
		final List<Document> all = handler.handleContent(reference, document).getStreamWithMedia().toList();
		assertEquals(3, all.size(), "the text, the image and the recording; videos are off");
		assertEquals("image/png", all.get(1).getMedia().getMimeType().toString());
		assertArrayEquals(png(5), all.get(1).getMedia().getDataAsByteArray());
		assertEquals("2", all.get(1).getMetadata().get("slide"), "the fragment's own metadata");
		assertEquals("chart.png", all.get(1).getMetadata().get(ImagesIngestionConfig.IMAGE_NAME_METADATA));
		assertEquals("doc-sales.gebo", all.get(1).getMetadata().get(ai.gebo.model.DocumentMetaInfos.CONTENT_CODE));
		assertEquals("audio/mpeg", all.get(2).getMedia().getMimeType().toString());
	}
}
