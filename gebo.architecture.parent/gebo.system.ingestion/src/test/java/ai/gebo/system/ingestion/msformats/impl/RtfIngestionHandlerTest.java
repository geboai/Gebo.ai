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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.model.IngestionFileType;
import ai.gebo.system.ingestion.model.IngestionHandlerConfig;

/**
 * Pins what the RTF handler reads of a .rtf document: its plain text in order,
 * without the control words, with the characters of its code page and its
 * unicode escapes decoded, and the title of its information group.
 */
class RtfIngestionHandlerTest {

	private static IGIngestionHandlerConfigDao dao() {
		final IngestionFileType type = new IngestionFileType();
		type.setExtensions(List.of(".rtf"));
		final IngestionHandlerConfig config = new IngestionHandlerConfig();
		config.setFileTypes(List.of(type));
		final IGIngestionHandlerConfigDao dao = mock(IGIngestionHandlerConfigDao.class);
		when(dao.findByCode(anyString())).thenReturn(config);
		return dao;
	}

	private static GDocumentReference reference() {
		final GDocumentReference reference = new GDocumentReference();
		reference.setCode("test-document");
		reference.setExtension(".rtf");
		return reference;
	}

	private static List<Document> read(String rtf, Map<String, Object> meta) throws Exception {
		return new RtfIngestionHandler(dao())
				.handleContent(reference(), new ByteArrayInputStream(rtf.getBytes(StandardCharsets.US_ASCII)), meta)
				.toList();
	}

	@Test
	void anRtfDocumentIsIndexedByItsPlainTextInOrderWithTheTitleOfItsInformation() throws Exception {
		final String rtf = "{\\rtf1\\ansi\\ansicpg1252\\deff0{\\fonttbl{\\f0 Times New Roman;}}"
				+ "{\\info{\\title The Secret Doctrine}{\\subject Cosmogenesis}{\\author H. P. Blavatsky}}"
				+ "\\f0\\fs24 First paragraph with {\\b bold} text.\\par "
				+ "Perch\\'e8 costa 5 \\u8364?\\par Last paragraph.\\par}";
		final Map<String, Object> meta = new HashMap<>();
		final List<Document> documents = read(rtf, meta);
		assertEquals(1, documents.size());
		final String text = documents.get(0).getText();
		assertEquals(List.of("First paragraph with bold text.", "Perchè costa 5 €", "Last paragraph."),
				text.lines().map(String::trim).filter(line -> !line.isEmpty()).toList());
		assertFalse(text.contains("\\"), text);
		assertFalse(text.contains("Times New Roman"), text);
		assertEquals("The Secret Doctrine", documents.get(0).getMetadata().get(DocumentMetaInfos.TITLE));
		assertEquals("Cosmogenesis", documents.get(0).getMetadata().get(DocumentMetaInfos.SUBTITLE));
		assertEquals("H. P. Blavatsky", documents.get(0).getMetadata().get(DocumentMetaInfos.AUTHOR));
	}

	@Test
	void anRtfDocumentWithoutInformationHasNoTitleAndOneWithoutTextGivesNoDocument() throws Exception {
		final Map<String, Object> meta = new HashMap<>();
		final List<Document> documents = read("{\\rtf1\\ansi Only a line.\\par}", meta);
		assertEquals("Only a line.", documents.get(0).getText());
		assertNull(meta.get(DocumentMetaInfos.TITLE));

		assertTrue(read("{\\rtf1\\ansi\\par}", new HashMap<>()).isEmpty());
	}

	@Test
	void theReadersRegistryDeclaresTheHandlerWithItsExtensionAndContentTypes() {
		final YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
		yaml.setResources(new ClassPathResource("bundle-readers/readers.yml"));
		final Properties registry = yaml.getObject();
		final String handler = registry.stringPropertyNames().stream()
				.filter(key -> key.endsWith(".id") && "RtfIngestionHandler".equals(registry.getProperty(key)))
				.map(key -> key.substring(0, key.length() - ".id".length())).findFirst().orElseThrow();
		assertEquals(".rtf", registry.getProperty(handler + ".fileTypes[0].extensions[0]"));
		assertEquals("application/rtf", registry.getProperty(handler + ".fileTypes[0].contentTypes[0]"));
		assertEquals("text/rtf", registry.getProperty(handler + ".fileTypes[0].contentTypes[1]"));
	}
}
