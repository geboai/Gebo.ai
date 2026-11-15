/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.model;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/** The tools the admin sets for the two models of a step of a pipeline. */
@Data
public class PipelineStepToolingUpdate {
	@NotNull
	private String pipelineCode = null;
	@NotNull
	private String stepId = null;
	private List<String> chatModelFunctions = List.of();
	private List<String> serviceModelFunctions = List.of();
}
