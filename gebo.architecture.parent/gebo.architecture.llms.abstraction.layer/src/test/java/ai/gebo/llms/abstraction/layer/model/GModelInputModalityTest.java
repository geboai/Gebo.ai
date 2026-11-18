/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.model;

import static ai.gebo.llms.abstraction.layer.model.GModelInputModality.AUDIO;
import static ai.gebo.llms.abstraction.layer.model.GModelInputModality.DOCUMENT;
import static ai.gebo.llms.abstraction.layer.model.GModelInputModality.IMAGE;
import static ai.gebo.llms.abstraction.layer.model.GModelInputModality.TEXT;
import static ai.gebo.llms.abstraction.layer.model.GModelInputModality.VIDEO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Answers;

import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.model.OperationStatus;

/** What a chat model accepts as input, as the providers name it and as a new configuration takes it. */
class GModelInputModalityTest {

	@Test
	void theProvidersNamesAreReadTextAlwaysAmongThem() {
		assertEquals(List.of(TEXT, IMAGE, AUDIO, VIDEO, DOCUMENT),
				GModelInputModality.fromProvider(List.of("video", "FILE", "image", "speech", "embedding")));
		assertEquals(List.of(TEXT, IMAGE, DOCUMENT), GModelInputModality.fromProvider(List.of("vision", "pdf")));
		assertEquals(List.of(TEXT), GModelInputModality.fromProvider(List.of("completion", "tools", "thinking")));
		assertEquals(List.of(TEXT), GModelInputModality.fromProvider((List<String>) null));
		assertNull(GModelInputModality.fromProvider("embedding"));
		assertEquals(List.of(TEXT, IMAGE), GModelInputModality.textAndImages(true));
		assertEquals(List.of(TEXT), GModelInputModality.textAndImages(null));
	}

	@Test
	void theMetaInfoKnowsItsModalitiesElseItsVision() {
		final ModelMetaInfo meta = new ModelMetaInfo();
		assertNull(meta.knownInputModalities(), "nothing known");
		meta.setSupportsVision(true);
		assertEquals(List.of(TEXT, IMAGE), meta.knownInputModalities(), "only vision known");
		meta.setInputModalities(List.of(TEXT, IMAGE, AUDIO));
		assertEquals(List.of(TEXT, IMAGE, AUDIO), meta.knownInputModalities());
		assertEquals(List.of(TEXT, IMAGE, AUDIO), meta.copy().getInputModalities());

		final ModelMetaInfo live = new ModelMetaInfo();
		live.fillMissingFrom(meta);
		assertEquals(List.of(TEXT, IMAGE, AUDIO), live.getInputModalities(), "the library fills what the api does not tell");
		final ModelMetaInfo told = new ModelMetaInfo();
		told.setInputModalities(List.of(TEXT));
		told.fillMissingFrom(meta);
		assertEquals(List.of(TEXT), told.getInputModalities(), "what the api tells wins");
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	void aNewConfigurationTakesTheChosenModelsModalitiesUnlessItSaysItsOwn() throws Exception {
		final GBaseChatModelChoice choice = new GBaseChatModelChoice();
		choice.setCode("vision-model");
		final ModelMetaInfo meta = new ModelMetaInfo();
		meta.setInputModalities(List.of(TEXT, IMAGE));
		choice.setMetaInfos(meta);
		final IGChatModelConfigurationSupportService service = mock(IGChatModelConfigurationSupportService.class,
				withSettings().defaultAnswer(Answers.CALLS_REAL_METHODS));
		doReturn(OperationStatus.of(List.of(choice))).when(service).getModelChoices(any());
		doReturn(OperationStatus.of("inserted")).when(service).insertAndConfigure(any());

		final GBaseChatModelConfig unset = new GBaseChatModelConfig();
		service.insertAndConfigureModel(unset, "VISION-MODEL");
		assertSame(choice, unset.getChoosedModel());
		assertEquals(List.of(TEXT, IMAGE), unset.getInputModalities(), "from the model's metadata");

		final GBaseChatModelConfig textOnly = new GBaseChatModelConfig();
		textOnly.setInputModalities(List.of());
		service.insertAndConfigureModel(textOnly, "vision-model");
		assertEquals(List.of(), textOnly.getInputModalities(), "a configuration saying its own keeps it");

		choice.setMetaInfos(null);
		final GBaseChatModelConfig unknown = new GBaseChatModelConfig();
		service.insertAndConfigureModel(unknown, "vision-model");
		assertNull(unknown.getInputModalities(), "metadata not knowing: unset, text only");
	}
}
