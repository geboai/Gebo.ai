/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.anthropic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;
import ai.gebo.llms.abstraction.layer.model.GChatModelParametersSupport;
import ai.gebo.llms.abstraction.layer.model.GSamplingAvailability;
import ai.gebo.llms.anthropic.config.AnthropicModelsLibrary;
import ai.gebo.llms.anthropic.model.AnthropicThinkingSupport;
import ai.gebo.llms.models.metainfos.GProviderModelsLibrary;
import ai.gebo.llms.models.metainfos.IGModelsLibraryDao;
import ai.gebo.llms.models.metainfos.config.ModelsLibraryConfig;
import ai.gebo.llms.models.metainfos.impl.GModelsLibraryDaoImpl;

/**
 * Pins what the Anthropic integration tells the editors: no sampling ever, the thinking
 * as thinkingFor asks it, from the Models API capabilities on the choice or the library.
 */
class AnthropicParametersSupportTest {

	/** The service with the models library only, every other collaborator left null. */
	static <T> T withLibrary(Class<T> type, IGModelsLibraryDao library) throws Exception {
		final java.lang.reflect.Constructor<?> constructor = java.util.Arrays.stream(type.getDeclaredConstructors())
				.max(java.util.Comparator.comparingInt(java.lang.reflect.Constructor::getParameterCount)).orElseThrow();
		final Object[] args = java.util.Arrays.stream(constructor.getParameterTypes())
				.map(p -> p == IGModelsLibraryDao.class ? library : null).toArray();
		constructor.setAccessible(true);
		return type.cast(constructor.newInstance(args));
	}

	private static AnthropicChatModelConfigurationSupportService service() throws Exception {
		return withLibrary(AnthropicChatModelConfigurationSupportService.class,
				new GModelsLibraryDaoImpl(new ModelsLibraryConfig(), List.of(GProviderModelsLibrary
						.load(AnthropicModelsLibrary.YML, AnthropicModelsLibrary.PREFIX, AnthropicModelsLibrary.class))));
	}

	@Test
	void theModelsApiCapabilitiesOnTheChoiceDecide() throws Exception {
		final Map<String, Object> details = new HashMap<>();
		new AnthropicThinkingSupport(true, false, false, Set.of("low", "medium", "high", "xhigh", "max")).writeTo(details);
		final GChatModelParametersSupport opus = service().parametersSupport("claude-opus-5-5", null, details);

		assertTrue(opus.isKnown());
		assertEquals(GSamplingAvailability.NEVER, opus.getTemperature().getAvailability(), "no sampling is sent");
		assertEquals(GSamplingAvailability.NEVER, opus.getTopP().getAvailability());
		assertFalse(opus.offers(ChatModelThinkingOption.NO_THINKING), "the model cannot disable its thinking");
		assertEquals("adaptive thinking", opus.sentAs(ChatModelThinkingOption.AUTO));
		assertEquals("adaptive thinking, effort low", opus.sentAs(ChatModelThinkingOption.LOW_THINKING));
		assertEquals("adaptive thinking, effort max", opus.sentAs(ChatModelThinkingOption.HIGH_THINKING),
				"the highest level the model takes");
		for (ChatModelThinkingOption option : ChatModelThinkingOption.values()) {
			assertEquals(AnthropicChatModelConfigurationSupportService.describe(
					AnthropicChatModelConfigurationSupportService.thinkingFor(option,
							AnthropicThinkingSupport.readFrom(details), "claude-opus-5-5", null)),
					opus.sentAs(option), option.name());
		}
	}

	@Test
	void withoutCapabilitiesTheLibraryAndTheModelCodeDecide() throws Exception {
		final GChatModelParametersSupport sonnet = service().parametersSupport("claude-sonnet-5-5", null, null);
		assertFalse(sonnet.offers(ChatModelThinkingOption.NO_THINKING),
				"the library lists no none: no thinking would be refused");
		assertEquals("adaptive thinking, effort max", sonnet.sentAs(ChatModelThinkingOption.HIGH_THINKING));

		// the 4.5 generation takes a token budget only: nothing is sent, the thinking never reaches it
		final GChatModelParametersSupport older = service().parametersSupport("claude-sonnet-4-5", null, null);
		assertFalse(older.isThinkingAvailable());
		assertNull(older.sentAs(ChatModelThinkingOption.HIGH_THINKING));
	}
}
