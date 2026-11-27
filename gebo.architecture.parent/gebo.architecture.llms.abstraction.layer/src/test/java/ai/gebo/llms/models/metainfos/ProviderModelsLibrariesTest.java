/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.models.metainfos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.models.metainfos.ModelParameterRules.Sampling;
import ai.gebo.llms.models.metainfos.config.ModelsLibraryConfig;
import ai.gebo.llms.models.metainfos.impl.GModelsLibraryDaoImpl;

/**
 * Pins the models library DAO over the provider libraries the modules declare: the
 * installation's entries first, then every provider's, a rule applying to its own
 * provider only; and a provider yml bound as Spring binds its bean.
 */
class ProviderModelsLibrariesTest {

	private static ModelParameterRules rule(String providerId, String model, Sampling sampling) {
		ModelParameterRules rule = new ModelParameterRules();
		rule.setProviderId(providerId);
		rule.setModels(List.of(model));
		rule.setSampling(sampling);
		return rule;
	}

	private static GProviderModelsLibrary provider(ModelMetaInfo model, ModelParameterRules... rules) {
		GProviderModelsLibrary library = new GProviderModelsLibrary();
		if (model != null)
			library.setModelsInfo(List.of(model));
		library.setParameterRules(List.of(rules));
		return library;
	}

	@Test
	void everyProviderLibraryIsSearchedAfterTheInstallation() {
		ModelMetaInfo alphaModel = new ModelMetaInfo();
		alphaModel.setProviderId("alpha");
		alphaModel.setModelId("alpha-1");
		ModelParameterRules alphaRule = rule("alpha", "alpha-*", Sampling.REJECTED);
		ModelParameterRules betaRule = rule("beta", "beta-*", Sampling.ACCEPTED);
		ModelParameterRules installed = rule("alpha", "alpha-9", Sampling.ACCEPTED);
		ModelsLibraryConfig installation = new ModelsLibraryConfig();
		installation.setParameterRules(List.of(installed));

		GModelsLibraryDaoImpl library = new GModelsLibraryDaoImpl(installation,
				List.of(provider(alphaModel, alphaRule), provider(null, betaRule)));

		assertSame(alphaModel, library.findByProviderIdAndModelId("alpha", "alpha-1"));
		assertSame(alphaModel, library.findByModelId("alpha-1"));
		assertSame(alphaRule, library.findParameterRules("alpha", "alpha-2"));
		assertSame(betaRule, library.findParameterRules("beta", "beta-x"));
		assertSame(installed, library.findParameterRules("alpha", "alpha-9"), "the installation first");
		assertNull(library.findParameterRules("beta", "alpha-1"), "a rule is its provider's only");
		assertNull(new GModelsLibraryDaoImpl(new ModelsLibraryConfig(), List.of()).findByModelId("alpha-1"));
	}

	@Test
	void theApiOfAModelIsTheFirstRuleTellingItsAndHidesNoParameterRule() {
		ModelParameterRules family = rule("alpha", "alpha-*", Sampling.REJECTED);
		ModelParameterRules api = new ModelParameterRules();
		api.setProviderId("alpha");
		api.setModels(List.of("alpha-pro", "alpha-pro-20*"));
		api.setApi("responses");
		ModelParameterRules installed = new ModelParameterRules();
		installed.setProviderId("alpha");
		installed.setModels(List.of("alpha-pro-2026-01-01"));
		installed.setApi("chat-completions");
		ModelsLibraryConfig installation = new ModelsLibraryConfig();
		installation.setParameterRules(List.of(installed));

		GModelsLibraryDaoImpl library = new GModelsLibraryDaoImpl(installation, List.of(provider(null, api, family)));

		assertEquals("responses", library.findApi("alpha", "alpha-pro"));
		assertEquals("responses", library.findApi("alpha", "alpha-pro-2025-06-10"), "its dated variants with it");
		assertEquals("chat-completions", library.findApi("alpha", "alpha-pro-2026-01-01"), "the installation first");
		assertNull(library.findApi("alpha", "alpha-1"), "the family rule tells no api");
		assertNull(library.findApi("beta", "alpha-pro"), "a rule is its provider's only");
		assertFalse(api.statesParameters());
		assertSame(family, library.findParameterRules("alpha", "alpha-pro"), "the api rule is read apart");
	}

	@Test
	void theRangeOfAModelIsItsRulesElseItsProvidersAndHidesNoParameterRule() {
		ModelParameterRules family = rule("alpha", "alpha-*", Sampling.REJECTED);
		ModelParameterRules narrow = new ModelParameterRules();
		narrow.setProviderId("alpha");
		narrow.setModels(List.of("alpha-old*"));
		narrow.setTemperatureRange(new ModelParameterRules.ValueRange(0.0, 1.0));
		ModelParameterRules provider = new ModelParameterRules();
		provider.setProviderId("alpha");
		provider.setModels(List.of("*"));
		provider.setTemperatureRange(new ModelParameterRules.ValueRange(0.0, 2.0));
		provider.setTopPRange(new ModelParameterRules.ValueRange(0.0, 1.0));

		GModelsLibraryDaoImpl library = new GModelsLibraryDaoImpl(new ModelsLibraryConfig(),
				List.of(provider(null, narrow, family, provider)));

		assertEquals(1.0, library.findTemperatureRange("alpha", "alpha-old-1").getMax(), "the model's rule first");
		assertEquals(2.0, library.findTemperatureRange("alpha", "alpha-new").getMax(), "else the provider's");
		assertEquals(1.0, library.findTopPRange("alpha", "alpha-old-1").getMax(), "each range found apart");
		assertNull(library.findTemperatureRange("beta", "alpha-new"), "a rule is its provider's only");
		assertFalse(provider.statesParameters());
		assertSame(family, library.findParameterRules("alpha", "alpha-old-1"), "the range rules are read apart");
	}

	@Test
	void aProviderYmlBindsAsItsBeanDoes() throws Exception {
		GProviderModelsLibrary alpha = GProviderModelsLibrary.load("test-models-library/alpha.yml",
				"ai.gebo.models.providers.alpha", GProviderModelsLibrary.class);

		assertEquals(1, alpha.getModelsInfo().size());
		assertEquals(1000, alpha.getModelsInfo().get(0).getContextLength());
		assertEquals(Sampling.REJECTED, alpha.getParameterRules().get(0).getSampling());
		assertTrue(alpha.getParameterRules().get(0).acceptsEffort("none"), "none is a string, not a YAML null");
		assertEquals("passage", alpha.getParameterRules().get(1).getDocumentInputType());
	}
}
