/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.ollama.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;
import ai.gebo.llms.abstraction.layer.model.GChatModelParametersSupport;
import ai.gebo.llms.abstraction.layer.model.GSamplingAvailability;
import ai.gebo.llms.models.metainfos.IGModelsLibraryDao;
import ai.gebo.llms.models.metainfos.config.ModelsLibraryConfig;
import ai.gebo.llms.models.metainfos.impl.GModelsLibraryDaoImpl;

/** Pins what the Ollama integration tells the editors: the think its /api/show details allow. */
class OllamaParametersSupportTest {

	/** The service with the models library only, every other collaborator left null. */
	static <T> T withLibrary(Class<T> type, IGModelsLibraryDao library) throws Exception {
		final java.lang.reflect.Constructor<?> constructor = java.util.Arrays.stream(type.getDeclaredConstructors())
				.max(java.util.Comparator.comparingInt(java.lang.reflect.Constructor::getParameterCount)).orElseThrow();
		final Object[] args = java.util.Arrays.stream(constructor.getParameterTypes())
				.map(p -> p == IGModelsLibraryDao.class ? library : null).toArray();
		constructor.setAccessible(true);
		return type.cast(constructor.newInstance(args));
	}

	@Test
	void theThinkAsTheRequestsSendIt() throws Exception {
		final OllamaChatModelConfigurationSupportService service = withLibrary(
				OllamaChatModelConfigurationSupportService.class,
				new GModelsLibraryDaoImpl(new ModelsLibraryConfig(), List.of()));

		final Map<String, Object> gptOss = Map.of("capabilities", List.of("completion", "thinking"), "thinking",
				Map.of("values", List.of("low", "medium", "high")));
		final GChatModelParametersSupport levels = service.parametersSupport("gpt-oss:20b", null, gptOss);
		assertTrue(levels.isKnown());
		assertEquals(GSamplingAvailability.ALWAYS, levels.getTemperature().getAvailability());
		assertEquals("think: false", levels.sentAs(ChatModelThinkingOption.NO_THINKING));
		assertEquals("think: low", levels.sentAs(ChatModelThinkingOption.LOW_THINKING));
		assertNull(levels.sentAs(ChatModelThinkingOption.AUTO));

		final Map<String, Object> qwen = Map.of("capabilities", List.of("completion", "thinking"));
		assertEquals("think: true", service.parametersSupport("qwen3:8b", null, qwen)
				.sentAs(ChatModelThinkingOption.HIGH_THINKING), "no levels: the thinking just switched on");

		final Map<String, Object> plain = Map.of("capabilities", List.of("completion"));
		assertFalse(service.parametersSupport("llama3.2", null, plain).isThinkingAvailable());
		assertNull(OllamaChatModelConfigurationSupportService.thinkOf(ChatModelThinkingOption.LOW_THINKING, false,
				List.of()));
	}
}
