/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.abstraction.layer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GModelInputModality;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.models.metainfos.IGModelsLibraryDao;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;

/**
 * A configuration made before the input modalities were recorded knows them from the
 * models library: the model's entry, else a rule covering it.
 */
class GChatModelInputModalitiesResolverTest {
	@SuppressWarnings("unchecked")
	private static GChatModelInputModalitiesResolver resolver(IGModelsLibraryDao library) {
		final GChatModelType type = new GChatModelType();
		type.setCode("chatgpt-OpenAI");
		type.setProviderId("openai");
		final IGChatModelConfigurationSupportService service = mock(IGChatModelConfigurationSupportService.class);
		when(service.getType()).thenReturn(type);
		final IGChatModelConfigurationSupportServiceRepositoryPattern types = mock(
				IGChatModelConfigurationSupportServiceRepositoryPattern.class);
		when(types.findByCode("chatgpt-OpenAI")).thenReturn(service);
		final ObjectProvider<IGModelsLibraryDao> libraries = mock(ObjectProvider.class);
		when(libraries.getIfAvailable()).thenReturn(library);
		final ObjectProvider<IGChatModelConfigurationSupportServiceRepositoryPattern> repository = mock(
				ObjectProvider.class);
		when(repository.getIfAvailable()).thenReturn(types);
		return new GChatModelInputModalitiesResolver(libraries, repository);
	}

	private static GBaseChatModelChoice choice(String code, List<GModelInputModality> stored) {
		final GBaseChatModelChoice choice = new GBaseChatModelChoice();
		choice.setCode(code);
		if (stored != null) {
			final ModelMetaInfo meta = new ModelMetaInfo();
			meta.setInputModalities(stored);
			choice.setMetaInfos(meta);
		}
		return choice;
	}

	@Test
	void theStoredMetadataFirstThenTheLibraryEntryThenItsRules() {
		final IGModelsLibraryDao library = mock(IGModelsLibraryDao.class);
		final ModelMetaInfo entry = new ModelMetaInfo();
		entry.setInputModalities(List.of(GModelInputModality.TEXT, GModelInputModality.IMAGE, GModelInputModality.DOCUMENT));
		when(library.findByProviderIdAndModelId("openai", "gpt-6.1-sol")).thenReturn(entry);
		when(library.findInputModalities("openai", "gpt-6.1-sol-2026-10-01"))
				.thenReturn(List.of(GModelInputModality.TEXT, GModelInputModality.IMAGE));
		final GChatModelInputModalitiesResolver resolver = resolver(library);

		assertEquals(List.of(GModelInputModality.TEXT), resolver.of("chatgpt-OpenAI",
				choice("gpt-6.1-sol", List.of(GModelInputModality.TEXT))), "what the stored metadata tell wins");
		assertEquals(List.of(GModelInputModality.TEXT, GModelInputModality.IMAGE, GModelInputModality.DOCUMENT),
				resolver.of("chatgpt-OpenAI", choice("gpt-6.1-sol", null)), "a choice stored without them: the entry");
		assertEquals(List.of(GModelInputModality.TEXT, GModelInputModality.IMAGE),
				resolver.of("chatgpt-OpenAI", choice("gpt-6.1-sol-2026-10-01", null)), "no entry: a rule");
		assertNull(resolver.of("chatgpt-OpenAI", choice("unknown-model", null)), "nobody knows: text only");
		assertNull(resolver.of("unknown-type", choice("gpt-6.1-sol", null)), "a type nobody declares");
	}
}
