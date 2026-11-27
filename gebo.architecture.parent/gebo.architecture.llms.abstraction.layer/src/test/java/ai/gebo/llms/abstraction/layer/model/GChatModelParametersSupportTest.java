/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;
import ai.gebo.llms.models.metainfos.ModelParameterRules;
import ai.gebo.llms.models.metainfos.ModelParameterRules.Sampling;

/** Pins how the parameters support of a chat model reads its parameter rules. */
class GChatModelParametersSupportTest {

	private static ModelParameterRules rules(Sampling sampling, List<String> efforts) {
		final ModelParameterRules rules = new ModelParameterRules();
		rules.setProviderId("p");
		rules.setModels(List.of("m"));
		rules.setSampling(sampling);
		rules.setReasoningEfforts(efforts);
		return rules;
	}

	@Test
	void theSamplingGoesAsTheRulesLetIt() {
		assertEquals(GSamplingAvailability.ALWAYS, GChatModelParametersSupport.samplingAvailabilityOf(null));
		assertEquals(GSamplingAvailability.ALWAYS,
				GChatModelParametersSupport.samplingAvailabilityOf(rules(Sampling.PROVIDER_DEFAULT, List.of())));
		assertEquals(GSamplingAvailability.NEVER,
				GChatModelParametersSupport.samplingAvailabilityOf(rules(Sampling.REJECTED, List.of())));
		assertEquals(GSamplingAvailability.NEVER,
				GChatModelParametersSupport.samplingAvailabilityOf(rules(Sampling.IGNORED, List.of())));
		assertEquals(GSamplingAvailability.WITHOUT_THINKING, GChatModelParametersSupport
				.samplingAvailabilityOf(rules(Sampling.WITHOUT_REASONING, List.of("none", "low"))));
		assertEquals(GSamplingAvailability.NEVER, GChatModelParametersSupport
				.samplingAvailabilityOf(rules(Sampling.WITHOUT_REASONING, List.of("low"))),
				"without effort none the thinking never goes off");
		assertFalse(rules(Sampling.IGNORED, List.of()).samplingAllowed(true), "ignored ones are not sent");
	}

	@Test
	void theOptionsOfferedAreTheOnesSendingSomething() {
		final GChatModelParametersSupport support = GChatModelParametersSupport.of("p", "m",
				rules(Sampling.PROVIDER_DEFAULT, List.of()), new ModelParameterRules.ValueRange(0.0, 2.0), null, true,
				option -> option == ChatModelThinkingOption.HIGH_THINKING ? "max" : null);
		assertTrue(support.isKnown());
		assertTrue(support.getTemperature().isProviderDefaultWhenEmpty());
		assertEquals(2.0, support.getTemperature().getMax());
		assertEquals(List.of(ChatModelThinkingOption.AUTO, ChatModelThinkingOption.HIGH_THINKING),
				support.getThinkingOptions().stream().map(GThinkingOptionSupport::getOption).toList());

		final GChatModelParametersSupport nothingSent = GChatModelParametersSupport.of("p", "m", null, null, null, true,
				option -> null);
		assertFalse(nothingSent.isThinkingAvailable(), "no option reaches the model");

		final GChatModelParametersSupport unknown = GChatModelParametersSupport.unknown("p", "m");
		assertFalse(unknown.isKnown());
		assertEquals(5, unknown.getThinkingOptions().size(), "every option, as without it");
	}
}
