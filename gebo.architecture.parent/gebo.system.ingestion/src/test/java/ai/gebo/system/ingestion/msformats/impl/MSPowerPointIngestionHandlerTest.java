/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.system.ingestion.msformats.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTable;
import org.apache.poi.hslf.usermodel.HSLFTextBox;
import org.apache.poi.sl.usermodel.Placeholder;
import org.apache.poi.xslf.usermodel.SlideLayout;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTable;
import org.apache.poi.xslf.usermodel.XSLFTableRow;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.model.IngestionFileType;
import ai.gebo.system.ingestion.model.IngestionHandlerConfig;

/**
 * Pins what the PowerPoint handler reads of a .pptx and of a .ppt presentation:
 * one document per slide with some text, in order, each with its slide number,
 * the title first, then the text shapes and the tables, the speaker notes last
 * and marked.
 */
class MSPowerPointIngestionHandlerTest {
	private static final String PPTX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.presentationml.presentation";

	private static IGIngestionHandlerConfigDao dao() {
		final IngestionFileType type = new IngestionFileType();
		type.setExtensions(List.of(".ppt", ".pptx"));
		final IngestionHandlerConfig config = new IngestionHandlerConfig();
		config.setFileTypes(List.of(type));
		final IGIngestionHandlerConfigDao dao = mock(IGIngestionHandlerConfigDao.class);
		when(dao.findByCode(anyString())).thenReturn(config);
		return dao;
	}

	private static GDocumentReference reference(String extension) {
		final GDocumentReference reference = new GDocumentReference();
		reference.setCode("test-presentation");
		reference.setExtension(extension);
		return reference;
	}

	private static List<Document> read(GDocumentReference reference, byte[] bytes, Map<String, Object> meta)
			throws Exception {
		return new MSPowerPointIngestionHandler(dao()).handleContent(reference, new ByteArrayInputStream(bytes), meta)
				.toList();
	}

	/**
	 * A .pptx of three slides: a title and content slide with a grouped text, a
	 * table and speaker notes; a slide without any text; a slide whose title shape
	 * comes after its body.
	 */
	private static byte[] pptx(String propertiesTitle) throws Exception {
		try (XMLSlideShow show = new XMLSlideShow(); ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {
			if (propertiesTitle != null) {
				show.getProperties().getCoreProperties().setTitle(propertiesTitle);
				show.getProperties().getCoreProperties().setCreator("H. P. Blavatsky");
			}
			final XSLFSlide first = show
					.createSlide(show.getSlideMasters().get(0).getLayout(SlideLayout.TITLE_AND_CONTENT));
			first.getPlaceholder(0).setText("The Secret Doctrine");
			first.getPlaceholder(1).setText("Cosmogenesis\nAnthropogenesis");
			first.createGroup().createTextBox().setText("Grouped text");
			final XSLFTable table = first.createTable();
			final XSLFTableRow header = table.addRow();
			header.addCell().setText("Volume");
			header.addCell().setText("Year");
			final XSLFTableRow row = table.addRow();
			row.addCell().setText("One");
			row.addCell().setText("1888");
			for (XSLFTextShape shape : show.getNotesSlide(first).getPlaceholders()) {
				if (shape.getTextType() == Placeholder.BODY) {
					shape.setText("Say it slowly");
				}
			}

			show.createSlide();

			final XSLFSlide third = show.createSlide();
			third.createTextBox().setText("Body before the title");
			final XSLFTextBox title = third.createTextBox();
			title.setPlaceholder(Placeholder.TITLE);
			title.setText("Isis Unveiled");
			show.write(bytes);
			return bytes.toByteArray();
		}
	}

	/**
	 * A .ppt of two slides, with its title in the summary information: one whose
	 * title is added after its body and its table, one of a single text.
	 */
	private static byte[] ppt() throws Exception {
		try (HSLFSlideShow show = new HSLFSlideShow(); ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {
			show.getSummaryInformation().setTitle("The old deck");
			final HSLFSlide first = show.createSlide();
			final HSLFTextBox body = first.createTextBox();
			body.setText("Cosmogenesis");
			body.setAnchor(new Rectangle(50, 150, 400, 50));
			final HSLFTable table = first.createTable(2, 2);
			table.getCell(0, 0).setText("Volume");
			table.getCell(0, 1).setText("Year");
			table.getCell(1, 0).setText("One");
			table.getCell(1, 1).setText("1888");
			first.addTitle().setText("The Secret Doctrine");

			final HSLFSlide second = show.createSlide();
			final HSLFTextBox text = second.createTextBox();
			text.setText("Second slide");
			text.setAnchor(new Rectangle(50, 150, 400, 50));
			show.write(bytes);
			return bytes.toByteArray();
		}
	}

	@Test
	void aPptxGivesOneDocumentPerSlideWithTextInOrderWithItsSlideNumber() throws Exception {
		final Map<String, Object> meta = new HashMap<>();
		final List<Document> documents = read(reference(".pptx"), pptx("The deck"), meta);
		assertEquals(2, documents.size());
		assertEquals("The Secret Doctrine\nCosmogenesis\nAnthropogenesis\nGrouped text\nVolume | Year\nOne | 1888\n"
				+ MSPowerPointIngestionHandler.NOTES_MARKER + "\nSay it slowly", documents.get(0).getText());
		assertEquals("1", documents.get(0).getMetadata().get(DocumentMetaInfos.CONTENT_PAGE));
		// the slide without text gives no document and keeps its number
		assertEquals("Isis Unveiled\nBody before the title", documents.get(1).getText());
		assertEquals("3", documents.get(1).getMetadata().get(DocumentMetaInfos.CONTENT_PAGE));
		assertEquals("The deck", documents.get(1).getMetadata().get(DocumentMetaInfos.TITLE));
		assertEquals("H. P. Blavatsky", documents.get(1).getMetadata().get(DocumentMetaInfos.AUTHOR));
	}

	@Test
	void withoutPropertiesTheTitleOfTheFirstSlideIsTheTitleOfThePresentation() throws Exception {
		final Map<String, Object> meta = new HashMap<>();
		read(reference(".pptx"), pptx(null), meta);
		assertEquals("The Secret Doctrine", meta.get(DocumentMetaInfos.TITLE));
	}

	@Test
	void aPptxWithoutExtensionIsReadByItsContentType() throws Exception {
		final GDocumentReference reference = reference(null);
		reference.setContentType(PPTX_CONTENT_TYPE);
		assertEquals(2, read(reference, pptx(null), new HashMap<>()).size());
	}

	@Test
	void aPptGivesOneDocumentPerSlideInOrderWithItsSlideNumberTheTitleFirst() throws Exception {
		final Map<String, Object> meta = new HashMap<>();
		final List<Document> documents = read(reference(".ppt"), ppt(), meta);
		assertEquals(2, documents.size());
		assertEquals("The Secret Doctrine\nCosmogenesis\nVolume | Year\nOne | 1888", documents.get(0).getText());
		assertEquals("1", documents.get(0).getMetadata().get(DocumentMetaInfos.CONTENT_PAGE));
		assertEquals("Second slide", documents.get(1).getText());
		assertEquals("2", documents.get(1).getMetadata().get(DocumentMetaInfos.CONTENT_PAGE));
		assertEquals("The old deck", documents.get(1).getMetadata().get(DocumentMetaInfos.TITLE));
	}

	@Test
	void aFileThatIsNotAPresentationIsRefused() {
		final byte[] text = "not a presentation".getBytes(StandardCharsets.UTF_8);
		assertThrows(GeboIngestionException.class, () -> read(reference(".pptx"), text, new HashMap<>()));
		assertThrows(Exception.class, () -> read(reference(".ppt"), text, new HashMap<>()));
	}

	@Test
	void theReadersRegistryDeclaresTheHandlerWithItsExtensionsAndContentTypes() {
		final YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
		yaml.setResources(new ClassPathResource("bundle-readers/readers.yml"));
		final Properties registry = yaml.getObject();
		final String handler = registry.stringPropertyNames().stream()
				.filter(key -> key.endsWith(".id") && "MSPowerPointIngestionHandler".equals(registry.getProperty(key)))
				.map(key -> key.substring(0, key.length() - ".id".length())).findFirst().orElseThrow();
		assertEquals(".ppt", registry.getProperty(handler + ".fileTypes[0].extensions[0]"));
		assertEquals("application/vnd.ms-powerpoint", registry.getProperty(handler + ".fileTypes[0].contentTypes"));
		assertEquals(".pptx", registry.getProperty(handler + ".fileTypes[1].extensions[0]"));
		assertEquals(PPTX_CONTENT_TYPE, registry.getProperty(handler + ".fileTypes[1].contentTypes"));
	}
}
