/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.services;

import static ai.gebo.llms.abstraction.layer.model.GModelInputModality.AUDIO;
import static ai.gebo.llms.abstraction.layer.model.GModelInputModality.IMAGE;
import static ai.gebo.llms.abstraction.layer.model.GModelInputModality.TEXT;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;

import ai.gebo.architecture.ai.model.ContextContentRequired;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.model.GModelCarriedMediaTypes;
import ai.gebo.llms.abstraction.layer.model.GModelInputModality;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatRequestMedia;
import io.micrometer.observation.ObservationRegistry;

/**
 * The files of a request reach a model in its last user message only when its
 * configuration declares their modality and its integration carries their type; the
 * calls working on the request's text get none.
 */
class ChatRequestMediaAttachmentTest {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	static class MediaModel extends GAbstractConfigurableChatModel<GBaseChatModelConfig, ChatModel> {
		private final GModelCarriedMediaTypes carried;

		MediaModel(List<GModelInputModality> declared, GModelCarriedMediaTypes carried) {
			super(null, null, usage(), ObservationRegistry.NOOP, null);
			this.carried = carried;
			this.config = new GBaseChatModelConfig();
			this.config.setCode("media-model");
			this.config.setInputModalities(declared);
			this.chatClient = ChatClient.builder(mock(ChatModel.class)).build();
		}

		@Override
		public GModelCarriedMediaTypes getCarriedMediaTypes() {
			return carried;
		}

		@Override
		protected IGConfigurableChatModel cloneMeWithInjection() {
			return this;
		}

		@Override
		protected ChatModel configureModel(GBaseChatModelConfig config, GChatModelType type) {
			return mock(ChatModel.class);
		}
	}

	private static IChatModelUsageAdvisorFactory usage() {
		final IChatModelUsageAdvisor advisor = mock(IChatModelUsageAdvisor.class);
		when(advisor.getName()).thenReturn("usage");
		final IChatModelUsageAdvisorFactory factory = mock(IChatModelUsageAdvisorFactory.class);
		when(factory.create(any(), any(), any())).thenReturn(advisor);
		return factory;
	}

	static final List<String> read = new ArrayList<>();

	static IChatRequestMedia file(String name, String mimeType, byte[] bytes) {
		return new IChatRequestMedia() {
			@Override
			public String getName() {
				return name;
			}

			@Override
			public String getMimeType() {
				return mimeType;
			}

			@Override
			public long getSize() {
				return bytes.length;
			}

			@Override
			public byte[] readBytes() throws IOException {
				read.add(name);
				return bytes;
			}
		};
	}

	static IChatRequestContext context(IChatRequestMedia... media) {
		final IChatRequestContext plain = IChatRequestContext.of("what is in the picture?");
		final List<IChatRequestMedia> files = List.of(media);
		return new ai.gebo.llms.abstraction.layer.model.ForwardingChatRequestContext(plain) {
			@Override
			public List<IChatRequestMedia> getMedia() {
				return files;
			}
		};
	}

	static GPromptTemplateConfig prompt() {
		final GPromptTemplateConfig prompt = new GPromptTemplateConfig();
		prompt.setUserPromptTemplate("{question}");
		prompt.setContextDocuments(ContextContentRequired.NOT_REQUIRED);
		return prompt;
	}

	static final GModelCarriedMediaTypes PICTURES_AND_PDF = GModelCarriedMediaTypes.none()
			.images("image/png", "image/jpeg").documents("application/pdf");

	@Test
	void aPictureGoesInTheLastUserMessageOfAModelThatTakesIt() {
		read.clear();
		final MediaModel model = new MediaModel(List.of(TEXT, IMAGE), PICTURES_AND_PDF);
		final UserMessage message = model.createLastUserMessage(prompt(), Map.of(),
				context(file("chart 1.png", "image/png", new byte[] { 1, 2, 3 }),
						file("scan.pdf", "application/pdf", new byte[] { 4 }),
						file("photo.tiff", "image/tiff", new byte[] { 5 })));

		assertEquals("what is in the picture?", message.getText());
		assertEquals(1, message.getMedia().size(), "the PDF is not declared, the tiff is not carried");
		assertEquals("image/png", message.getMedia().get(0).getMimeType().toString());
		assertArrayEquals(new byte[] { 1, 2, 3 }, message.getMedia().get(0).getDataAsByteArray());
		assertEquals(List.of("chart 1.png"), read, "a file that stays out is never read");
	}

	@Test
	void aTextOnlyModelGetsAPlainMessage() {
		read.clear();
		for (List<GModelInputModality> declared : new ArrayList<List<GModelInputModality>>(
				java.util.Arrays.asList(null, List.of(), List.of(TEXT)))) {
			final UserMessage message = new MediaModel(declared, PICTURES_AND_PDF).createLastUserMessage(prompt(),
					Map.of(), context(file("chart.png", "image/png", new byte[] { 1 })));
			assertTrue(message.getMedia().isEmpty());
		}
		assertTrue(read.isEmpty());
	}

	@Test
	void anIntegrationThatCarriesNothingGetsNothingWhateverTheModelDeclares() {
		final UserMessage message = new MediaModel(List.of(TEXT, IMAGE), GModelCarriedMediaTypes.none())
				.createLastUserMessage(prompt(), Map.of(), context(file("chart.png", "image/png", new byte[] { 1 })));
		assertTrue(message.getMedia().isEmpty());
	}

	@Test
	void theCallsOnTheRequestsTextGetNoFile() {
		final MediaModel model = new MediaModel(List.of(TEXT, IMAGE), PICTURES_AND_PDF);
		final IChatRequestContext withPicture = context(file("chart.png", "image/png", new byte[] { 1 }));
		assertEquals(1, withPicture.withRulesToFollow(List.of("a rule")).getMedia().size(),
				"the views of a context keep its files");
		assertTrue(model.createLastUserMessage(prompt(), Map.of(), withPicture.withoutMedia()).getMedia().isEmpty());
	}

	@Test
	void mp3AudioIsSentWithTheTypeTheOpenAIIntegrationReads() {
		final MediaModel model = new MediaModel(List.of(TEXT, AUDIO),
				GModelCarriedMediaTypes.none().audio("audio/mp3", "audio/mpeg", "audio/wav"));
		final UserMessage message = model.createLastUserMessage(prompt(), Map.of(),
				context(file("voice.mp3", "audio/mpeg", new byte[] { 9 })));
		assertEquals("audio/mp3", message.getMedia().get(0).getMimeType().toString());
		assertEquals("file-1", GAbstractConfigurableChatModel.mediaName("", 0));
		assertEquals("chart 1-png", GAbstractConfigurableChatModel.mediaName("chart 1.png", 0));
	}
}
