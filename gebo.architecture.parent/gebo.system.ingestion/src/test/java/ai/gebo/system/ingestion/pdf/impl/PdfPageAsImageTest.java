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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.config.ImagesIngestionConfig;
import ai.gebo.system.ingestion.model.IngestionFileType;
import ai.gebo.system.ingestion.model.IngestionHandlerConfig;

/**
 * A PDF page without text (a scan) is given as an image while images are taken, and
 * the PDF is opened for rendering with whichever PDFBox the application carries.
 */
class PdfPageAsImageTest {
	private static byte[] scan() throws Exception {
		final BufferedImage image = new BufferedImage(400, 300, BufferedImage.TYPE_INT_RGB);
		final Random random = new Random(3);
		for (int x = 0; x < 400; x++) {
			for (int y = 0; y < 300; y++) {
				image.setRGB(x, y, random.nextInt(0xFFFFFF));
			}
		}
		final ByteArrayOutputStream png = new ByteArrayOutputStream();
		ImageIO.write(image, "png", png);
		final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		final Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, bytes);
		document.open();
		document.add(new Paragraph("The first page has text."));
		document.newPage();
		document.add(Image.getInstance(png.toByteArray()));
		document.close();
		return bytes.toByteArray();
	}

	private static IGIngestionHandlerConfigDao dao() {
		final IngestionFileType type = new IngestionFileType();
		type.setExtensions(List.of(".pdf"));
		type.setContentTypes(List.of());
		final IngestionHandlerConfig config = new IngestionHandlerConfig();
		config.setFileTypes(List.of(type));
		final IGIngestionHandlerConfigDao dao = mock(IGIngestionHandlerConfigDao.class);
		when(dao.findByCode(anyString())).thenReturn(config);
		return dao;
	}

	private static GDocumentReference reference() {
		final GDocumentReference reference = new GDocumentReference();
		reference.setCode("doc-scan.pdf");
		reference.setName("scan.pdf");
		reference.setExtension(".pdf");
		return reference;
	}

	@Test
	void aPdfIsOpenedForRenderingByThePdfboxCarried() throws Exception {
		try (org.apache.pdfbox.pdmodel.PDDocument document = PdfIngestionHandler.loadForRendering(scan())) {
			assertEquals(2, document.getNumberOfPages());
		}
	}

	@Test
	void aPageWithoutTextIsGivenAsAnImageWhileImagesAreTaken() throws Exception {
		final ImagesIngestionConfig images = new ImagesIngestionConfig();
		images.setIngestionEnabled(true);
		final PdfIngestionHandler handler = new PdfIngestionHandler(dao(), null);
		handler.setImagesConfig(images);
		final List<org.springframework.ai.document.Document> documents = handler
				.handleContent(reference(), new ByteArrayInputStream(scan()), new HashMap<>()).toList();

		assertTrue(documents.get(0).getText().contains("The first page has text."));
		final List<org.springframework.ai.document.Document> pictures = documents.stream().filter(d -> !d.isText())
				.toList();
		assertEquals(1, pictures.size(), "the page without text, rendered");
		assertEquals("image/png", pictures.get(0).getMedia().getMimeType().toString());
		assertEquals("2", pictures.get(0).getMetadata().get(DocumentMetaInfos.CONTENT_PAGE));

		images.setIngestionEnabled(false);
		assertTrue(handler.handleContent(reference(), new ByteArrayInputStream(scan()), new HashMap<>())
				.allMatch(org.springframework.ai.document.Document::isText), "with images off nothing is rendered");
	}
}
