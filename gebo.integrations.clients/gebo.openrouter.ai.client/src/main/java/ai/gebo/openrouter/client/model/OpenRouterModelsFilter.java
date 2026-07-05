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

import lombok.Data;

/**
 * Server-side filter for the OpenRouter {@code GET /models} endpoint. Every field
 * left {@code null} (or empty, for the list fields) is simply omitted from the
 * request.
 *
 * <p>
 * There is no dedicated "model type" parameter (chat / embedding / reranking).
 * The closest server-side selector is {@link #outputModalities}: {@code text}
 * matches chat/completion models, {@code embeddings} matches embedding models,
 * and so on. Finer types the server does not expose as a query value — such as
 * {@code rerank} — can still be selected by filtering the returned models on
 * {@link ModelArchitecture#getOutputModalities()} (see
 * {@code OpenRouterAiClient.OutputModality}).
 * </p>
 *
 * Gebo.ai comment agent
 */
@Data
public class OpenRouterModelsFilter {

	/**
	 * Use-case category, e.g. {@code programming}, {@code roleplay},
	 * {@code translation}, {@code legal}, {@code finance}, {@code health}...
	 */
	private String category;

	/**
	 * Output modalities to keep. Sent as the {@code output_modalities} query param
	 * (comma-separated). Valid server values: {@code text}, {@code image},
	 * {@code audio}, {@code embeddings} or {@code all}.
	 */
	private List<String> outputModalities;

	/**
	 * Required input modalities. Sent as {@code input_modalities}. Values:
	 * {@code text}, {@code image}, {@code audio}, {@code file}.
	 */
	private List<String> inputModalities;

	/** Free-text search over model name/slug ({@code q}). */
	private String query;

	/** Server-side ordering ({@code sort}), e.g. {@code pricing}, {@code context}, {@code popularity}. */
	private String sort;

	/** Required request parameters/capabilities ({@code supported_parameters}), e.g. {@code tools}, {@code temperature}. */
	private List<String> supportedParameters;

	/** Architecture family filter ({@code arch}), e.g. {@code GPT}, {@code Claude}, {@code Gemini}, {@code Llama}. */
	private String arch;

	/** Hosting providers to keep ({@code providers}). */
	private List<String> providers;

	/** Minimum context length in tokens ({@code context}). */
	private Long minContextLength;

	/** Minimum price filter ({@code min_price}). */
	private Double minPrice;

	/** Maximum price filter ({@code max_price}). */
	private Double maxPrice;
}
