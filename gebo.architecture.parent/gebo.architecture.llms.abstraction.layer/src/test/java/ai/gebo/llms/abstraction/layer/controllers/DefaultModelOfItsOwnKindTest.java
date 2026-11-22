/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseImageModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseTextToSpeachModelConfig;

/**
 * Making a speech or image model the default takes the default from the other models
 * of the same kind only: it took it from the chat models, and after the next start the
 * installation had no default chat model (the knowledge base chats failed). Found live.
 */
class DefaultModelOfItsOwnKindTest {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T extends ai.gebo.llms.abstraction.layer.model.GBaseModelConfig> T config(T config, String code) {
		config.setCode(code);
		config.setDefaultModel(true);
		return config;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	void aDefaultSpeechModelTakesTheDefaultFromSpeechModelsOnly() throws Exception {
		final IGPersistentObjectManager persistence = mock(IGPersistentObjectManager.class);
		final GBaseTextToSpeachModelConfig previous = config(new GBaseTextToSpeachModelConfig(), "tts-old");
		final GBaseChatModelConfig chat = config(new GBaseChatModelConfig(), "chat-default");
		when(persistence.findAllExtendingType(GBaseTextToSpeachModelConfig.class)).thenReturn(List.of(previous));
		when(persistence.findAllExtendingType(GBaseChatModelConfig.class)).thenReturn(List.of(chat));
		final BaseTextToSpeechModelsConfigurationCRUDController controller = new BaseTextToSpeechModelsConfigurationCRUDController(
				persistence, null, GBaseTextToSpeachModelConfig.class, null, null);

		controller.handleDefaultModel(config(new GBaseTextToSpeachModelConfig(), "tts-new"));

		assertEquals(false, previous.getDefaultModel(), "the previous default speech model is no longer");
		verify(persistence).update(previous);
		assertEquals(true, chat.getDefaultModel(), "the default chat model stays");
		verify(persistence, never()).update(chat);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	void aDefaultImageModelTakesTheDefaultFromImageModelsOnly() throws Exception {
		final IGPersistentObjectManager persistence = mock(IGPersistentObjectManager.class);
		final GBaseImageModelConfig previous = config(new GBaseImageModelConfig(), "image-old");
		final GBaseChatModelConfig chat = config(new GBaseChatModelConfig(), "chat-default");
		when(persistence.findAllExtendingType(GBaseImageModelConfig.class)).thenReturn(List.of(previous));
		when(persistence.findAllExtendingType(GBaseChatModelConfig.class)).thenReturn(List.of(chat));
		final BaseImageModelsConfigurationCRUDController controller = new BaseImageModelsConfigurationCRUDController(
				persistence, null, GBaseImageModelConfig.class, null, null);

		controller.handleDefaultModel(config(new GBaseImageModelConfig(), "image-new"));

		assertEquals(false, previous.getDefaultModel());
		verify(persistence).update(previous);
		assertEquals(true, chat.getDefaultModel(), "the default chat model stays");
		verify(persistence, never()).update(chat);
	}
}
