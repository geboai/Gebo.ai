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
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * A single model entry returned by the OpenRouter models endpoint, together with
 * its properties. Unknown/extra fields are ignored so the client keeps working as
 * OpenRouter extends its schema.
 *
 * Gebo.ai comment agent
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenRouterModel {

	/** Fully qualified model id (e.g. {@code openai/gpt-4o}). */
	private String id;

	/** Canonical, stable slug for the model. */
	@JsonProperty("canonical_slug")
	private String canonicalSlug;

	/** Human-readable model name. */
	private String name;

	/** Creation time as a unix epoch (seconds). */
	private Long created;

	/** Free-text description of the model. */
	private String description;

	/** Maximum context length in tokens, when advertised. */
	@JsonProperty("context_length")
	private Long contextLength;

	/** Modalities and tokenizer information. */
	private ModelArchitecture architecture;

	/** Per-token / per-request pricing (values are decimal strings in USD). */
	private ModelPricing pricing;

	/** Properties of the top provider serving the model. */
	@JsonProperty("top_provider")
	private TopProvider topProvider;

	/** Optional per-request token limits. */
	@JsonProperty("per_request_limits")
	private PerRequestLimits perRequestLimits;

	/** Request parameters the model supports (e.g. {@code tools}, {@code temperature}). */
	@JsonProperty("supported_parameters")
	private List<String> supportedParameters;

	/** Associated Hugging Face model id, when applicable. */
	@JsonProperty("hugging_face_id")
	private String huggingFaceId;
}
