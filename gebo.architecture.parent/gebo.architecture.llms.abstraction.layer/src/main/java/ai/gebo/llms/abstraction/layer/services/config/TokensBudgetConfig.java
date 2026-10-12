/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import jakarta.annotation.PostConstruct;
import lombok.Data;

/**
 * The share of the room a model call leaves (its context length less its prompt, the
 * values filling it and the consolidation it carries) its documents may fill, the rest
 * left to the estimation error of the token counts and to what the model writes: the
 * factor of {@link BaseLLMSInvokingService#computeFragmentBudget}, the one budget
 * formula of the consolidations and of the deep searches. Set via
 * ai.gebo.llms.tokens-budget.factor, 0 &lt; factor &lt;= 1.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.llms.tokens-budget")
@Data
public class TokensBudgetConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(TokensBudgetConfig.class);
	public static final double DEFAULT_FACTOR = 0.7d;
	private double factor = DEFAULT_FACTOR;

	@PostConstruct
	public void apply() {
		if (factor > 0d && factor <= 1d) {
			BaseLLMSInvokingService.ERRONEUS_TOKEN_LENGTH_ERROR_COEFF = factor;
		} else {
			LOGGER.warn("ai.gebo.llms.tokens-budget.factor=" + factor + " is not in (0, 1]: " + DEFAULT_FACTOR
					+ " is used");
			BaseLLMSInvokingService.ERRONEUS_TOKEN_LENGTH_ERROR_COEFF = DEFAULT_FACTOR;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("apply() tokens budget factor:" + BaseLLMSInvokingService.ERRONEUS_TOKEN_LENGTH_ERROR_COEFF);
		}
	}
}
