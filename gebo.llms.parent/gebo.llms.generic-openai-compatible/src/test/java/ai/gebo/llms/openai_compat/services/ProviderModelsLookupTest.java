/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai_compat.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIChatModelConfig;
import ai.gebo.openai.integration.client.model.OpenAIApiConfig;

/**
 * Pins where an OpenAI compatible provider's models are listed from: the configured
 * url, else the provider's own - never OpenAI's, which is where a provider set up
 * without a custom url (NVIDIA, Groq) was being asked before.
 */
class ProviderModelsLookupTest {

	private static GenericOpenAIAPIChatModelConfig configWith(String baseUrl) {
		GenericOpenAIAPIChatModelConfig config = new GenericOpenAIAPIChatModelConfig();
		config.setBaseUrl(baseUrl);
		return config;
	}

	@Test
	void aProviderWithoutACustomUrlIsListedFromItsOwnUrl() {
		OpenAIApiConfig lookup = ProviderModelsLookup.apiConfigOf(configWith(null), "nvidia",
				"https://integrate.api.nvidia.com");

		assertEquals("https://integrate.api.nvidia.com", lookup.getBasePath());
		assertEquals("nvidia", lookup.getProviderId());
	}

	@Test
	void theConfiguredUrlWins() {
		assertEquals("http://localhost:8000/", ProviderModelsLookup
				.apiConfigOf(configWith("http://localhost:8000/"), "vllm", null).getBasePath());
		assertEquals("https://integrate.api.nvidia.com", ProviderModelsLookup
				.apiConfigOf(configWith(" "), "nvidia", "https://integrate.api.nvidia.com").getBasePath(),
				"a blank url is no url");
	}

	@Test
	void aProviderWithNoUrlAtAllIsNotListedFromOpenAi() {
		assertNull(ProviderModelsLookup.apiConfigOf(configWith(null), "generic-openai", "").getBasePath());
	}
}
