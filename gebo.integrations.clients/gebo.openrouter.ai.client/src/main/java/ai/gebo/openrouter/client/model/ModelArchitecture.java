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
 * Architecture information of a model: its supported input/output modalities,
 * instruct format and tokenizer family.
 *
 * Gebo.ai comment agent
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelArchitecture {

	/** Combined modality descriptor (e.g. {@code text->text}). */
	private String modality;

	/** Accepted input modalities (e.g. {@code text}, {@code image}, {@code file}). */
	@JsonProperty("input_modalities")
	private List<String> inputModalities;

	/** Produced output modalities (e.g. {@code text}). */
	@JsonProperty("output_modalities")
	private List<String> outputModalities;

	/** Instruct/prompt template type, when applicable. */
	@JsonProperty("instruct_type")
	private String instructType;

	/** Tokenizer family used by the model. */
	private String tokenizer;
}
