/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai_compat.services;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.GProviderKeyLimit;
import ai.gebo.llms.abstraction.layer.services.IGProviderKeyLimitsReader;
import ai.gebo.openrouter.client.OpenRouterAiClient;
import ai.gebo.openrouter.client.model.OpenRouterKeyInfo;

/**
 * Reads the spending limit of an OpenRouter.ai API key through
 * {@code GET /key}, which any key can call on itself: {@code limit} is the cap in
 * USD (null for an unlimited key) and {@code limit_reset} the period it resets on
 * ({@code daily}, {@code weekly}, {@code monthly}, or null for a cap that never
 * resets).
 */
@Service
public class OpenRouterKeyLimitsReader implements IGProviderKeyLimitsReader {
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenRouterKeyLimitsReader.class);
	/** The providerId OpenRouter's model types declare in providers.yml. */
	public static final String OPENROUTER_PROVIDER_ID = "openrouter.ai";
	/** OpenRouter's credits are in USD. */
	private static final String OPENROUTER_CURRENCY = "USD";

	@Override
	public String getProviderId() {
		return OPENROUTER_PROVIDER_ID;
	}

	@Override
	public GProviderKeyLimit readLimit(String clearApiKey) {
		OpenRouterKeyInfo key = newClient(clearApiKey).getCurrentKey();
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<OPENROUTER_KEY>" + key + "</OPENROUTER_KEY>");
		}
		if (key == null) {
			throw new IllegalStateException("OpenRouter returned no information about the API key");
		}
		GProviderKeyLimit limit = new GProviderKeyLimit(OPENROUTER_CURRENCY, key.getLimit(),
				toPeriod(key.getLimitReset()));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("OpenRouter key label=" + key.getLabel() + " limit=" + key.getLimit() + " limitReset="
					+ key.getLimitReset() + " => " + limit);
		}
		return limit;
	}

	/** The client for a key; overridable to stub the HTTP calls. */
	protected OpenRouterAiClient newClient(String clearApiKey) {
		return new OpenRouterAiClient(clearApiKey);
	}

	static GProviderKeyLimit.Period toPeriod(String limitReset) {
		if (limitReset == null || limitReset.isBlank()) {
			return GProviderKeyLimit.Period.TOTAL;
		}
		return switch (limitReset.trim().toLowerCase(Locale.ROOT)) {
		case "daily" -> GProviderKeyLimit.Period.DAILY;
		case "weekly" -> GProviderKeyLimit.Period.WEEKLY;
		case "monthly" -> GProviderKeyLimit.Period.MONTHLY;
		default -> throw new IllegalStateException("Unknown OpenRouter limit_reset=" + limitReset);
		};
	}
}
