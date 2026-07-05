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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * Top-level response of the OpenRouter {@code GET /api/v1/models} endpoint, which
 * wraps the list of available models under a {@code data} property.
 *
 * Gebo.ai comment agent
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenRouterModelsResponse {

	/** The list of models and their properties. */
	private List<OpenRouterModel> data;
}
