/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ai.decision.DecisionModel;
import org.springframework.ai.decision.DecisionPrompt;
import org.springframework.ai.decision.DecisionResponse;
import org.springframework.ai.decision.question.YesNoQuestion;

import ai.gebo.llms.abstraction.layer.model.GBaseDecisionModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseDecisionModelConfig;
import ai.gebo.llms.abstraction.layer.model.GDecisionModelType;
import io.micrometer.observation.ObservationRegistry;

/**
 * Pins the test call a decision model makes when a user sets it: one yes/no question,
 * and a provider refusal turned into a configuration error naming the model, so a
 * wrong key, url or model name is reported when the model is saved.
 */
class GAbstractConfigurableDecisionModelTest {

	static class ScriptedDecisionModel extends
			GAbstractConfigurableDecisionModel<GBaseDecisionModelConfig<GBaseDecisionModelChoice>, DecisionModel> {

		final DecisionModel provider;

		ScriptedDecisionModel(DecisionModel provider) {
			super(ObservationRegistry.NOOP);
			this.provider = provider;
		}

		@Override
		protected DecisionModel configureModel(GBaseDecisionModelConfig<GBaseDecisionModelChoice> config,
				GDecisionModelType type) {
			return provider;
		}
	}

	static ScriptedDecisionModel configured(DecisionModel provider) throws LLMConfigException {
		GBaseDecisionModelConfig<GBaseDecisionModelChoice> config = new GBaseDecisionModelConfig<>();
		config.setCode("decision-test-model");
		config.setChoosedModel(new GBaseDecisionModelChoice());
		config.getChoosedModel().setCode("d1-omni:600m");
		GDecisionModelType type = new GDecisionModelType();
		type.setCode("decision-ollama");
		ScriptedDecisionModel model = new ScriptedDecisionModel(provider);
		model.initialize(config, type);
		return model;
	}

	@Test
	void theTestCallAsksOneYesNoQuestion() throws Exception {
		DecisionModel provider = mock(DecisionModel.class);
		when(provider.call(any(DecisionPrompt.class))).thenReturn(mock(DecisionResponse.class));

		assertDoesNotThrow(() -> configured(provider).testCall());

		ArgumentCaptor<DecisionPrompt> asked = ArgumentCaptor.forClass(DecisionPrompt.class);
		verify(provider).call(asked.capture());
		assertEquals(Set.of("colour"), asked.getValue().getQuestions().keySet());
		assertTrue(asked.getValue().getQuestions().get("colour") instanceof YesNoQuestion);
	}

	@Test
	void aProviderRefusalBecomesAConfigurationErrorNamingTheModel() throws Exception {
		DecisionModel provider = mock(DecisionModel.class);
		RuntimeException refusal = new RuntimeException("model \"d1-omni:600m\" not found, try pulling it first");
		when(provider.call(any(DecisionPrompt.class))).thenThrow(refusal);
		ScriptedDecisionModel model = configured(provider);

		LLMConfigException error = assertThrows(LLMConfigException.class, model::testCall);

		assertTrue(error.getMessage().contains("decision-test-model"), error.getMessage());
		assertTrue(error.getMessage().contains("model=d1-omni:600m"), error.getMessage());
		assertTrue(error.getMessage().contains("try pulling it first"), "the provider's own words are kept");
		assertSame(refusal, error.getCause());
	}

	@Test
	void aModelNotConfiguredRefusesItsTestCall() throws Exception {
		LLMConfigException error = assertThrows(LLMConfigException.class, () -> configured(null).testCall());

		assertTrue(error.getMessage().contains("is not configured"), error.getMessage());
	}
}
