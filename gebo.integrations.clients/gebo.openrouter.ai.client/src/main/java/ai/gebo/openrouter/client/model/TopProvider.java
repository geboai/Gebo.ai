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
 * Properties of the top provider serving a model.
 *
 * Gebo.ai comment agent
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopProvider {

	/** Whether the provider moderates content. */
	@JsonProperty("is_moderated")
	private Boolean moderated;

	/** Provider-specific maximum context length in tokens. */
	@JsonProperty("context_length")
	private Long contextLength;

	/** Provider-specific maximum number of completion tokens. */
	@JsonProperty("max_completion_tokens")
	private Long maxCompletionTokens;
}
