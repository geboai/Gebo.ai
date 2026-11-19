/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel.UseChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTranscriptModel;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.config.IngestionMediaConfig;

/**
 * Pictures are described by a chat model that reads them and recordings transcribed
 * by the default transcription model, only when the installation turned that on.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class GMediaTextExtractorImplTest {
	private final IGChatModelRuntimeConfigurationDao chatModels = mock(IGChatModelRuntimeConfigurationDao.class);
	private final IGTranscriptModelRuntimeConfigurationDao transcriptModels = mock(
			IGTranscriptModelRuntimeConfigurationDao.class);
	private final IngestionMediaConfig config = new IngestionMediaConfig();

	private static <T> ObjectProvider<T> providing(T bean) {
		final ObjectProvider<T> provider = mock(ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(bean);
		return provider;
	}

	private GMediaTextExtractorImpl extractor() {
		return new GMediaTextExtractorImpl(config, providing(chatModels), providing(transcriptModels));
	}

	private static IGConfigurableChatModel model(String code, boolean seesPictures) {
		final IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
		when(model.getCode()).thenReturn(code);
		when(model.acceptsMedia(any())).thenReturn(seesPictures);
		return model;
	}

	@Test
	void nothingIsReadWithAModelUntilTheInstallationTurnsItOn() {
		final IGConfigurableChatModel seeing = model("seeing", true);
		when(chatModels.defaultHandler()).thenReturn(seeing);
		when(transcriptModels.defaultHandler()).thenReturn(mock(IGConfigurableTranscriptModel.class));
		assertFalse(extractor().describesPictures());
		assertFalse(extractor().transcribes());
		config.setDescribePictures(true);
		config.setTranscribeRecordings(true);
		assertTrue(extractor().describesPictures());
		assertTrue(extractor().transcribes());
	}

	@Test
	void thePicturesGoToTheNamedModelElseTheDefaultElseTheFirstThatReadsThem() {
		final IGConfigurableChatModel blind = model("blind", false);
		final IGConfigurableChatModel seeing = model("seeing", true);
		when(chatModels.defaultHandler()).thenReturn(blind);
		when(chatModels.getConfigurations()).thenReturn(List.of(blind, seeing));
		when(chatModels.findByCode("seeing")).thenReturn(seeing);
		when(chatModels.findByCode("blind")).thenReturn(blind);
		assertSame(seeing, extractor().pictureModel(), "the default does not read pictures");
		when(chatModels.defaultHandler()).thenReturn(seeing);
		assertSame(seeing, extractor().pictureModel());
		config.setPictureModelCode("blind");
		assertNull(extractor().pictureModel(), "the named model does not read pictures: nobody does");
		config.setDescribePictures(true);
		assertFalse(extractor().describesPictures());
	}

	@Test
	void aPictureIsSentToTheModelAndItsAnswerIsTheDescription() throws Exception {
		final IGConfigurableChatModel seeing = model("seeing", true);
		when(chatModels.defaultHandler()).thenReturn(seeing);
		final Prompt[] sent = new Prompt[1];
		final ChatModel provider = mock(ChatModel.class);
		when(provider.call(any(Prompt.class))).thenAnswer(call -> {
			sent[0] = call.getArgument(0);
			return new ChatResponse(List.of(new Generation(new AssistantMessage("Sales by quarter: Q1 10, Q2 12."))));
		});
		when(seeing.doWithChatModel(any())).thenAnswer(call -> ((UseChatModel) call.getArgument(0)).call(provider));

		assertEquals("Sales by quarter: Q1 10, Q2 12.",
				extractor().describePicture("chart.png", "image/png", new byte[] { 1, 2, 3 }));
		final UserMessage user = (UserMessage) sent[0].getInstructions().get(1);
		assertEquals(1, user.getMedia().size());
		assertEquals("image/png", user.getMedia().get(0).getMimeType().toString());
	}

	@Test
	void aRecordingIsTranscribedByTheDefaultTranscriptionModel() throws Exception {
		final IGConfigurableTranscriptModel model = mock(IGConfigurableTranscriptModel.class);
		when(model.call(any())).thenReturn("Good morning everybody.");
		when(transcriptModels.defaultHandler()).thenReturn(model);
		assertEquals("Good morning everybody.",
				extractor().transcribe("talk.mp3", "audio/mpeg", new ByteArrayInputStream(new byte[] { 1 })));
	}
}
