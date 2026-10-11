/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai_compat.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.openai.integration.client.model.OpenAIApiConfig;

/**
 * Where the models of an OpenAI compatible provider are listed from: the base url
 * configured on the model when there is one, else the provider's own (its
 * {@code providers.yml} row).
 *
 * <p>
 * {@link OpenAIApiConfig} starts from OpenAI's url, so the services testing it for
 * null never fell back to the provider's url: a provider set up without a custom
 * url (NVIDIA, Groq) had its models listed from {@code api.openai.com}. A provider
 * with no url of its own and none configured (vLLM, generic) now gets no base url,
 * and the lookup reports it missing rather than asking OpenAI.
 * </p>
 */
final class ProviderModelsLookup {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProviderModelsLookup.class);

	private ProviderModelsLookup() {
	}

	/**
	 * The api configuration of a models lookup.
	 *
	 * @param config          the model configuration, carrying the key and any
	 *                        configured base url
	 * @param providerId      the provider id of the model type
	 * @param providerBaseUrl the provider's own base url, may be empty
	 * @return the configuration whose base path the lookup calls
	 */
	@SuppressWarnings("rawtypes")
	static OpenAIApiConfig apiConfigOf(GBaseModelConfig config, String providerId, String providerBaseUrl) {
		OpenAIApiConfig apiConfig = OpenAIApiConfig.of(config, false);
		apiConfig.setProviderId(providerId);
		final boolean configured = config.getBaseUrl() != null && !config.getBaseUrl().isBlank();
		if (!configured) {
			apiConfig.setBasePath(providerBaseUrl != null && !providerBaseUrl.isBlank() ? providerBaseUrl : null);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Models of provider " + providerId + " listed from "
					+ (configured ? "the configured url " : "the provider url ") + apiConfig.getBasePath());
		}
		return apiConfig;
	}
}
