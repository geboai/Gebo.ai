/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.setup.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;
import ai.gebo.llms.abstraction.layer.model.GChatModelParametersSupport;

/** Pins the platform's thinking for a chat model its preset leaves without one. */
class SetupThinkingDefaultsTest {

	private static GChatModelParametersSupport offering(ChatModelThinkingOption... sent) {
		final List<ChatModelThinkingOption> options = List.of(sent);
		return GChatModelParametersSupport.of("p", "m", null, null, null, true,
				option -> options.contains(option) ? option.name().toLowerCase() : null);
	}

	@Test
	void aServiceModelDoesNotThinkWhereItCanElseThinksLow() {
		assertEquals(ChatModelThinkingOption.NO_THINKING, SetupThinkingDefaults.forUses(
				offering(ChatModelThinkingOption.NO_THINKING, ChatModelThinkingOption.LOW_THINKING),
				List.of(ChatModelsUses.INTERNAL_SERVICES)));
		assertEquals(ChatModelThinkingOption.LOW_THINKING,
				SetupThinkingDefaults.forUses(offering(ChatModelThinkingOption.LOW_THINKING,
						ChatModelThinkingOption.MEDIUM_THINKING), List.of(ChatModelsUses.INTERNAL_SERVICES)),
				"it cannot stop thinking");
		assertNull(SetupThinkingDefaults.forUses(offering(ChatModelThinkingOption.HIGH_THINKING),
				List.of(ChatModelsUses.INTERNAL_SERVICES)), "neither: the provider's default");
	}

	@Test
	void aChatModelThinksMedium() {
		assertEquals(ChatModelThinkingOption.MEDIUM_THINKING, SetupThinkingDefaults.forUses(
				offering(ChatModelThinkingOption.NO_THINKING, ChatModelThinkingOption.MEDIUM_THINKING),
				List.of(ChatModelsUses.CHAT)));
		assertEquals(ChatModelThinkingOption.MEDIUM_THINKING,
				SetupThinkingDefaults.forUses(offering(ChatModelThinkingOption.MEDIUM_THINKING), null));
		assertNull(SetupThinkingDefaults.forUses(offering(ChatModelThinkingOption.HIGH_THINKING),
				List.of(ChatModelsUses.CHAT)));
	}

	@Test
	void aModelWhoseIntegrationTellsNothingOrThatNoThinkingReachesKeepsItsDefault() {
		assertNull(SetupThinkingDefaults.forUses(GChatModelParametersSupport.unknown("p", "m"),
				List.of(ChatModelsUses.CHAT)));
		assertNull(SetupThinkingDefaults.forUses(GChatModelParametersSupport.of("p", "m", null, null, null, false, null),
				List.of(ChatModelsUses.INTERNAL_SERVICES)));
		assertNull(SetupThinkingDefaults.forUses(null, List.of(ChatModelsUses.CHAT)));
	}

	public static class DetailedChoice {
		public Map<String, Object> getModelDetails() {
			return Map.of("capabilities", List.of("thinking"));
		}
	}

	@Test
	void theProvidersDetailsAreReadOffTheChoicesHavingThem() {
		assertEquals(List.of("thinking"), SetupThinkingDefaults.modelDetailsOf(new DetailedChoice()).get("capabilities"));
		assertNull(SetupThinkingDefaults.modelDetailsOf("a choice without details"));
		assertNull(SetupThinkingDefaults.modelDetailsOf(null));
	}
}
