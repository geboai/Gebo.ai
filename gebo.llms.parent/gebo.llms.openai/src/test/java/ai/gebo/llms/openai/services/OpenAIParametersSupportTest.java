/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;
import ai.gebo.llms.abstraction.layer.model.GChatModelParametersSupport;
import ai.gebo.llms.abstraction.layer.model.GSamplingAvailability;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableChatModel;
import ai.gebo.llms.models.metainfos.GProviderModelsLibrary;
import ai.gebo.llms.models.metainfos.IGModelsLibraryDao;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.models.metainfos.config.ModelsLibraryConfig;
import ai.gebo.llms.models.metainfos.impl.GModelsLibraryDaoImpl;
import ai.gebo.llms.openai.config.OpenAIModelsLibrary;

/**
 * Pins what the OpenAI integration tells the editors of temperature, top_p and thinking:
 * the same decisions configureModel makes, from the models library.
 */
class OpenAIParametersSupportTest {

	/** The service with the models library only, every other collaborator left null. */
	static <T> T withLibrary(Class<T> type, IGModelsLibraryDao library) throws Exception {
		final java.lang.reflect.Constructor<?> constructor = java.util.Arrays.stream(type.getDeclaredConstructors())
				.max(java.util.Comparator.comparingInt(java.lang.reflect.Constructor::getParameterCount)).orElseThrow();
		final Object[] args = java.util.Arrays.stream(constructor.getParameterTypes())
				.map(p -> p == IGModelsLibraryDao.class ? library : null).toArray();
		constructor.setAccessible(true);
		return type.cast(constructor.newInstance(args));
	}

	private static GModelsLibraryDaoImpl library() throws Exception {
		return new GModelsLibraryDaoImpl(new ModelsLibraryConfig(), List.of(
				GProviderModelsLibrary.load(OpenAIModelsLibrary.YML, OpenAIModelsLibrary.PREFIX, OpenAIModelsLibrary.class)));
	}

	@Test
	void theEditorsAreToldWhatTheRequestsSend() throws Exception {
		final GModelsLibraryDaoImpl library = library();
		final OpenAIChatModelConfigurationSupportService service = withLibrary(
				OpenAIChatModelConfigurationSupportService.class, library);

		final GChatModelParametersSupport pro = service.parametersSupport("gpt-5.5-pro", null, null);
		assertTrue(pro.isKnown());
		assertEquals(GSamplingAvailability.NEVER, pro.getTemperature().getAvailability());
		assertEquals(GSamplingAvailability.NEVER, pro.getTopP().getAvailability());
		assertEquals("medium", pro.sentAs(ChatModelThinkingOption.NO_THINKING), "the least it takes");
		assertEquals("medium", pro.sentAs(ChatModelThinkingOption.LOW_THINKING));
		assertEquals("xhigh", pro.sentAs(ChatModelThinkingOption.HIGH_THINKING), "the most it takes");
		assertTrue(pro.offers(ChatModelThinkingOption.AUTO));
		assertNull(pro.sentAs(ChatModelThinkingOption.AUTO), "Automatic leaves the provider default");

		final GChatModelParametersSupport sol = service.parametersSupport("gpt-6-sol", null, null);
		assertEquals(GSamplingAvailability.WITHOUT_THINKING, sol.getTemperature().getAvailability());
		assertEquals(0.0, sol.getTemperature().getMin());
		assertEquals(2.0, sol.getTemperature().getMax());
		assertEquals(1.0, sol.getTopP().getMax());
		assertFalse(sol.getTemperature().isProviderDefaultWhenEmpty(), "Gebo's deterministic 0 when sent");
		assertEquals("none", sol.sentAs(ChatModelThinkingOption.NO_THINKING));
		assertEquals("max", sol.sentAs(ChatModelThinkingOption.HIGH_THINKING));

		// gpt-4.1 does not reason: no thinking reaches it, its sampling does
		final GChatModelParametersSupport gpt41 = service.parametersSupport("gpt-4.1", null, null);
		assertFalse(gpt41.isThinkingAvailable());
		assertTrue(gpt41.getThinkingOptions().isEmpty());
		assertEquals(GSamplingAvailability.ALWAYS, gpt41.getTemperature().getAvailability());
		final ModelMetaInfo told = new ModelMetaInfo();
		told.setSupportsReasoning(true);
		assertTrue(service.parametersSupport("gpt-4.1", told, null).isThinkingAvailable(),
				"the chosen model's metadata first");

		// efforts unknown: low, medium and high as asked, no thinking not offered
		final GChatModelParametersSupport mini = service.parametersSupport("gpt-5-mini", null, null);
		assertFalse(mini.offers(ChatModelThinkingOption.NO_THINKING));
		assertEquals("low", mini.sentAs(ChatModelThinkingOption.LOW_THINKING));
	}

	@Test
	void theEditorsAndTheRequestsAgreeOnEveryModelAndOption() throws Exception {
		final GModelsLibraryDaoImpl library = library();
		final OpenAIChatModelConfigurationSupportService service = withLibrary(
				OpenAIChatModelConfigurationSupportService.class, library);
		for (String id : List.of("gpt-6.1-sol", "gpt-6-luna", "gpt-6.2-sol", "gpt-5.6-terra", "gpt-5.5", "gpt-5.4",
				"gpt-5.4-pro", "gpt-5-pro", "gpt-5", "gpt-5-2025-08-07", "o3", "gpt-oss-20b", "gpt-5.3-codex")) {
			final GChatModelParametersSupport support = service.parametersSupport(id, null, null);
			for (ChatModelThinkingOption option : ChatModelThinkingOption.values()) {
				assertEquals(GAbstractConfigurableChatModel.reasoningEffortFor(option,
						library.findParameterRules("openai", id)), support.sentAs(option), id + " " + option);
			}
		}
	}
}
