/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openrouter.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Pricing of a model. OpenRouter returns the amounts as decimal strings in USD
 * (per token unless the field name says otherwise), so they are kept as
 * {@link String} to avoid precision loss.
 *
 * Gebo.ai comment agent
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelPricing {

	/** USD per prompt (input) token. */
	private String prompt;

	/** USD per completion (output) token. */
	private String completion;

	/** USD per request, when charged. */
	private String request;

	/** USD per input image, when charged. */
	private String image;

	/** USD per web-search operation, when charged. */
	@JsonProperty("web_search")
	private String webSearch;

	/** USD for internal reasoning tokens, when charged. */
	@JsonProperty("internal_reasoning")
	private String internalReasoning;

	/** USD to read from the input cache, when applicable. */
	@JsonProperty("input_cache_read")
	private String inputCacheRead;

	/** USD to write to the input cache, when applicable. */
	@JsonProperty("input_cache_write")
	private String inputCacheWrite;
}
