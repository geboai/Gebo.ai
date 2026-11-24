/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTranscriptModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGImageModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import ai.gebo.llms.openai.controllers.OpenAIEmbeddingModelsConfigurationController;
import ai.gebo.llms.openai.controllers.OpenAIImageModelsConfigurationController;
import ai.gebo.llms.openai.controllers.OpenAITextToSpeechModelsConfigurationController;
import ai.gebo.llms.openai.controllers.OpenAITranscriptModelsConfigurationController;
import ai.gebo.llms.openai.model.GOpenAIChatModelConfig;
import ai.gebo.llms.openai.model.GOpenAIEmbeddingModelConfig;
import ai.gebo.llms.openai.model.GOpenAIImageModelConfig;
import ai.gebo.llms.openai.model.GOpenAITextToSpeechModelConfig;
import ai.gebo.llms.openai.model.GOpenAITranscriptModelConfig;
import ai.gebo.llms.openai.services.OpenAIEmbeddingModelConfigurationSupportService;
import ai.gebo.llms.openai.services.OpenAIImageModelConfigurationSupportService;
import ai.gebo.llms.openai.services.OpenAITextToSpeechModelConfigurationSupportService;
import ai.gebo.llms.openai.services.OpenAITranscriptModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.GeboTokenContent;

/**
 * Certifies what saving a default transcript, speech, image or embedding model does, through the
 * controllers the setup screens call: the previous default model of the same kind is no
 * longer the default, in its stored configuration and in the model the platform is
 * running, so the work goes to the new one at once; the default chat model keeps its
 * role.
 */
@TestPropertySource(properties = { "ai.gebo.llms.config.openAIEnabled=true" })
public class DefaultModelSwitchIntegrationTest extends AbstractBaseTestLLmsIntegrationTests {
	@Autowired
	private OpenAITranscriptModelsConfigurationController controller;
	@Autowired
	private OpenAITranscriptModelConfigurationSupportService supportService;
	@Autowired
	private IGTranscriptModelRuntimeConfigurationDao transcriptModelsDao;

	@Autowired
	private OpenAITextToSpeechModelsConfigurationController speechController;
	@Autowired
	private OpenAITextToSpeechModelConfigurationSupportService speechSupportService;
	@Autowired
	private IGTextToSpeechModelRuntimeConfigurationDao speechModelsDao;
	@Autowired
	private OpenAIImageModelsConfigurationController imageController;
	@Autowired
	private OpenAIImageModelConfigurationSupportService imageSupportService;
	@Autowired
	private IGImageModelRuntimeConfigurationDao imageModelsDao;
	@Autowired
	private OpenAIEmbeddingModelsConfigurationController embeddingController;
	@Autowired
	private OpenAIEmbeddingModelConfigurationSupportService embeddingSupportService;
	@Autowired
	private IGEmbeddingModelRuntimeConfigurationDao embeddingModelsDao;

	private final List<GOpenAITranscriptModelConfig> inserted = new ArrayList<>();

	private GOpenAITranscriptModelConfig aDefaultTranscriptModel(String description, String secret) {
		final GOpenAITranscriptModelConfig config = supportService.createBaseConfiguration("whisper-1");
		config.setDescription(description);
		config.setApiSecretCode(secret);
		config.setDefaultModel(true);
		final OperationStatus<GOpenAITranscriptModelConfig> status = controller
				.insertOpenAITranscriptModelConfig(config);
		assertFalse(status.isHasErrorMessages(), "the transcript model is saved: " + status.getMessages());
		inserted.add(status.getResult());
		return status.getResult();
	}

	private boolean storedDefault(GOpenAITranscriptModelConfig config) throws Exception {
		return Boolean.TRUE.equals(controller.findOpenAITranscriptModelConfigByCode(config.getCode()).getDefaultModel());
	}

	private boolean runningDefault(GOpenAITranscriptModelConfig config) {
		final IGConfigurableTranscriptModel<?> running = transcriptModelsDao.findByCode(config.getCode());
		assertNotNull(running, "the transcript model " + config.getCode() + " is running");
		return Boolean.TRUE.equals(running.getConfig().getDefaultModel());
	}

	@Test
	public void aNewDefaultTranscriptModelTakesTheDefaultFromThePreviousOneAndNotFromTheChatModel() throws Exception {
		final IGConfigurableChatModel<?> defaultChat = chatModelRuntimeDao.defaultHandler();
		assertNotNull(defaultChat, "the installation of the test has a default chat model");
		final String defaultChatCode = defaultChat.getCode();
		// a default chat model as the setup stores it: the one a start of the platform reads
		GOpenAIChatModelConfig storedChat = new GOpenAIChatModelConfig();
		storedChat.setDescription("Default transcript switch test chat model");
		storedChat.setDefaultModel(true);
		storedChat = persistentObjectManager.insert(storedChat);
		final List<String> storedDefaultChats = storedDefaultChatModels();
		assertTrue(storedDefaultChats.contains(storedChat.getCode()));

		final String secret = aSecret();

		try {
			final GOpenAITranscriptModelConfig first = aDefaultTranscriptModel("Default transcript switch test first",
					secret);
			assertTrue(storedDefault(first));
			assertEquals(first.getCode(), transcriptModelsDao.defaultHandler().getCode());

			final GOpenAITranscriptModelConfig second = aDefaultTranscriptModel("Default transcript switch test second",
					secret);

			// what is stored
			assertTrue(storedDefault(second), "the transcript model just saved is the default");
			assertFalse(storedDefault(first), "the previous default transcript model is stored as no longer the default");
			// what is running
			assertFalse(runningDefault(first), "the previous default transcript model, as it runs, is no longer the default");
			assertEquals(second.getCode(), transcriptModelsDao.defaultHandler().getCode(),
					"the transcriptions go to the default transcript model just saved");

			// the role given back to the first one, by an update of it
			final GOpenAITranscriptModelConfig again = controller.findOpenAITranscriptModelConfigByCode(first.getCode());
			again.setDefaultModel(true);
			final OperationStatus<GOpenAITranscriptModelConfig> updated = controller
					.updateOpenAITranscriptModelConfig(again);
			assertFalse(updated.isHasErrorMessages(), "the transcript model is updated: " + updated.getMessages());
			assertTrue(storedDefault(first));
			assertFalse(storedDefault(second));
			assertFalse(runningDefault(second));
			assertEquals(first.getCode(), transcriptModelsDao.defaultHandler().getCode());

			// the chat models are not touched
			assertNotNull(chatModelRuntimeDao.defaultHandler(), "the installation still has a default chat model");
			assertEquals(defaultChatCode, chatModelRuntimeDao.defaultHandler().getCode());
			assertEquals(storedDefaultChats, storedDefaultChatModels(), "the chat models stored as default are the same");
		} finally {
			for (GOpenAITranscriptModelConfig config : inserted) {
				controller.deleteOpenAITranscriptModelConfig(config);
			}
			inserted.clear();
			persistentObjectManager.delete(storedChat);
		}
	}

	private String aSecret() throws Exception {
		final GeboTokenContent token = new GeboTokenContent();
		token.setToken("sk-test-not-a-real-key");
		return secretsAccessService.storeSecret(token, "Default model switch test", "default-model-switch-test");
	}

	@Test
	public void aNewDefaultSpeechModelTakesTheDefaultFromThePreviousOne() throws Exception {
		final String secret = aSecret();
		final List<GOpenAITextToSpeechModelConfig> saved = new ArrayList<>();
		try {
			for (String description : List.of("Default speech switch test first", "Default speech switch test second")) {
				final GOpenAITextToSpeechModelConfig config = speechSupportService.createBaseConfiguration("tts-1");
				config.setDescription(description);
				config.setApiSecretCode(secret);
				config.setDefaultModel(true);
				final OperationStatus<GOpenAITextToSpeechModelConfig> status = speechController
						.insertOpenAITextToSpeechModelConfig(config);
				assertFalse(status.isHasErrorMessages(), "the speech model is saved: " + status.getMessages());
				saved.add(status.getResult());
			}
			final String first = saved.get(0).getCode(), second = saved.get(1).getCode();
			assertFalse(Boolean.TRUE.equals(
					speechController.findOpenAITextToSpeechModelConfigByCode(first).getDefaultModel()),
					"the previous default speech model is stored as no longer the default");
			assertFalse(Boolean.TRUE.equals(speechModelsDao.findByCode(first).getConfig().getDefaultModel()),
					"the previous default speech model, as it runs, is no longer the default");
			assertEquals(second, speechModelsDao.defaultHandler().getCode(),
					"the speech is made by the default speech model just saved");
		} finally {
			for (GOpenAITextToSpeechModelConfig config : saved) {
				speechController.deleteOpenAITextToSpeechModelConfig(config);
			}
		}
	}

	@Test
	public void aNewDefaultImageModelTakesTheDefaultFromThePreviousOne() throws Exception {
		final String secret = aSecret();
		final List<GOpenAIImageModelConfig> saved = new ArrayList<>();
		try {
			for (String description : List.of("Default image switch test first", "Default image switch test second")) {
				final GOpenAIImageModelConfig config = imageSupportService.createBaseConfiguration("gpt-image-1");
				config.setDescription(description);
				config.setApiSecretCode(secret);
				config.setDefaultModel(true);
				final OperationStatus<GOpenAIImageModelConfig> status = imageController
						.insertOpenAIImageModelConfig(config);
				assertFalse(status.isHasErrorMessages(), "the image model is saved: " + status.getMessages());
				saved.add(status.getResult());
			}
			final String first = saved.get(0).getCode(), second = saved.get(1).getCode();
			assertFalse(Boolean.TRUE.equals(imageController.findOpenAIImageModelConfigByCode(first).getDefaultModel()),
					"the previous default image model is stored as no longer the default");
			assertFalse(Boolean.TRUE.equals(imageModelsDao.findByCode(first).getConfig().getDefaultModel()),
					"the previous default image model, as it runs, is no longer the default");
			assertEquals(second, imageModelsDao.defaultHandler().getCode(),
					"the images are made by the default image model just saved");
		} finally {
			for (GOpenAIImageModelConfig config : saved) {
				imageController.deleteOpenAIImageModelConfig(config);
			}
		}
	}

	@Test
	public void aNewDefaultEmbeddingModelTakesTheDefaultFromThePreviousOne() throws Exception {
		final String secret = aSecret();
		final List<GOpenAIEmbeddingModelConfig> saved = new ArrayList<>();
		try {
			for (String description : List.of("Default embedding switch test first",
					"Default embedding switch test second")) {
				final GOpenAIEmbeddingModelConfig config = embeddingSupportService
						.createBaseConfiguration("text-embedding-3-small");
				config.setDescription(description);
				config.setApiSecretCode(secret);
				config.setDefaultModel(true);
				final OperationStatus<GOpenAIEmbeddingModelConfig> status = embeddingController
						.insertOpenAIEmbeddingModelConfig(config);
				assertFalse(status.isHasErrorMessages(), "the embedding model is saved: " + status.getMessages());
				saved.add(status.getResult());
			}
			final String first = saved.get(0).getCode(), second = saved.get(1).getCode();
			assertFalse(Boolean.TRUE.equals(
					embeddingController.findOpenAIEmbeddingModelConfigByCode(first).getDefaultModel()),
					"the previous default embedding model is stored as no longer the default");
			assertFalse(Boolean.TRUE.equals(embeddingModelsDao.findByCode(first).getConfig().getDefaultModel()),
					"the previous default embedding model, as it runs, is no longer the default");
			assertTrue(Boolean.TRUE.equals(embeddingModelsDao.findByCode(second).getConfig().getDefaultModel()),
					"the embedding model just saved, as it runs, is the default");
		} finally {
			for (GOpenAIEmbeddingModelConfig config : saved) {
				embeddingController.deleteOpenAIEmbeddingModelConfig(config);
			}
		}
	}

	private List<String> storedDefaultChatModels() throws Exception {
		return persistentObjectManager.findAllExtendingType(GBaseChatModelConfig.class).stream()
				.filter(chat -> Boolean.TRUE.equals(chat.getDefaultModel())).map(chat -> chat.getCode()).sorted().toList();
	}
}
