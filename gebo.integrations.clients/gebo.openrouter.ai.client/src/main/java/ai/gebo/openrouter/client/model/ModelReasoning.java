/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openrouter.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * How a model reasons, as the OpenRouter models endpoint tells it (e.g.
 * {@code {"mandatory":true,"default_enabled":true,"supported_efforts":["high","medium","low"],"default_effort":"medium"}}).
 * A mandatory one rejects the effort {@code none}.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelReasoning {

	/** Whether the reasoning cannot be turned off. */
	private Boolean mandatory;

	/** Whether the model reasons when the request says nothing. */
	@JsonProperty("default_enabled")
	@JsonAlias("defaultEnabled")
	private Boolean defaultEnabled;

	/** The reasoning.effort values the model takes. */
	@JsonProperty("supported_efforts")
	@JsonAlias("supportedEfforts")
	private List<String> supportedEfforts;

	/** The effort the model reasons at when the request names none. */
	@JsonProperty("default_effort")
	@JsonAlias("defaultEffort")
	private String defaultEffort;
}
