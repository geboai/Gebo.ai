/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.ragsystem.content.vectorizator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.content.Media;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.util.MimeTypeUtils;

import ai.gebo.architecture.documents.cache.model.DocumentChunkType;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import ai.gebo.ragsystem.content.vectorizator.config.VectorizatorMediaConfig;

/**
 * The media documents of a document being vectorized become text documents made
 * by the models of this service, only while the switch of their kind is on and a
 * model for it is configured; the text documents are not touched.
 */
class VectorizatorMediaReaderTest {
	@SuppressWarnings("unchecked")
	private static <T> ObjectProvider<T> providing(T value) {
		final ObjectProvider<T> provider = mock(ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(value);
		return provider;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static IGConfigurableChatModel seeing(String answer) throws Exception {
		final IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
		when(model.acceptsMedia(anyString())).thenReturn(true);
		when(model.doWithChatModel(any()))
				.thenReturn(new ChatResponse(List.of(new Generation(new AssistantMessage(answer)))));
		return model;
	}

	private static Document image() {
		final Map<String, Object> metadata = new HashMap<>();
		metadata.put("gebo_page", "2");
		return Document.builder().id("image-1")
				.media(Media.builder().mimeType(MimeTypeUtils.IMAGE_PNG).data(new byte[] { 1, 2, 3 }).name("media").build())
				.metadata(metadata).build();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static VectorizatorMediaReader reader(boolean embedImages, IGConfigurableChatModel model) {
		final VectorizatorMediaConfig config = new VectorizatorMediaConfig();
		config.setEmbedImages(embedImages);
		final IGChatModelRuntimeConfigurationDao chatModels = mock(IGChatModelRuntimeConfigurationDao.class);
		when(chatModels.defaultHandler()).thenReturn(model);
		return new VectorizatorMediaReader(config, providing(chatModels),
				providing((IGTranscriptModelRuntimeConfigurationDao) null));
	}

	@SuppressWarnings("rawtypes")
	@Test
	void anImageBecomesItsDescriptionInItsPlaceWithItsMetadata() throws Exception {
		final IGConfigurableChatModel model = seeing("A bar chart of the sales by quarter.");
		final Document before = new Document("text-1", "The sales of the year.", new HashMap<>());
		final Document after = new Document("text-2", "Signatures.", new HashMap<>());

		final List<Document> out = reader(true, model).withMediaAsText("sales.docx", List.of(before, image(), after));

		assertEquals(3, out.size());
		assertSame(before, out.get(0), "a text is not touched");
		assertSame(after, out.get(2));
		assertTrue(out.get(1).isText());
		assertEquals("A bar chart of the sales by quarter.", out.get(1).getText());
		assertEquals("2", out.get(1).getMetadata().get("gebo_page"), "the image's metadata");
		assertEquals("description", out.get(1).getMetadata().get(VectorizatorMediaReader.MODEL_WRITTEN_METADATA));
		assertTrue(out.get(1).getId() != null && !out.get(1).getId().isBlank(), "an id, recorded as a vector's");
	}

	@SuppressWarnings("rawtypes")
	@Test
	void withTheSwitchOffOrNoModelThatSeesTheImageIsLeftOut() throws Exception {
		final IGConfigurableChatModel model = seeing("A chart.");
		final Document text = new Document("text-1", "The sales.", new HashMap<>());

		final VectorizatorMediaReader off = reader(false, model);
		assertFalse(off.reads(DocumentChunkType.IMAGE));
		assertEquals(List.of(text), off.withMediaAsText("sales.docx", List.of(text, image())));
		verify(model, never()).doWithChatModel(any());

		final IGConfigurableChatModel blind = mock(IGConfigurableChatModel.class);
		when(blind.acceptsMedia(anyString())).thenReturn(false);
		final VectorizatorMediaReader noModel = reader(true, blind);
		assertFalse(noModel.reads(DocumentChunkType.IMAGE));
		assertEquals(List.of(text), noModel.withMediaAsText("sales.docx", List.of(text, image())));

		// recordings have a switch of their own, off here
		assertFalse(reader(true, model).reads(DocumentChunkType.AUDIO));
	}

	@SuppressWarnings("rawtypes")
	@Test
	void aListOfTextsIsReturnedItself() throws Exception {
		final List<Document> texts = List.of(new Document("text-1", "The sales.", new HashMap<>()));
		assertSame(texts, reader(true, seeing("x")).withMediaAsText("sales.docx", texts));
	}
}
