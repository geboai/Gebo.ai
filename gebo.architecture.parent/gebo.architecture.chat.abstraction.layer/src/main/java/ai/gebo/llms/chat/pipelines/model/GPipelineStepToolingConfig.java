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

import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.model.base.GBaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The tools the two models of a chat pipeline step can call, as the admin set them.
 * The key is the pipeline running the step and the step: a step shared by two
 * pipelines has one configuration under each, so the document's code is made of both
 * (see {@link #codeOf(String, String)}). No configuration, or no functions for a
 * model, means that model calls no tools in that step.
 */
@Document
@Data
@EqualsAndHashCode(callSuper = true)
public class GPipelineStepToolingConfig extends GBaseObject {
	private static final String KEY_SEPARATOR = "::";

	/** The code of the pipeline running the step. */
	@HashIndexed
	private String pipelineCode = null;
	/** The id of the step, as {@code IChatPipelineStepService#getStepId()} tells it. */
	private String pipelineStepId = null;
	/** The functions the step's chat model can call. */
	private List<String> chatModelFunctions = null;
	/** The functions the step's service model can call. */
	private List<String> serviceModelFunctions = null;

	/** The code of a step's configuration; a null pipeline is the default one. */
	public static String codeOf(String pipelineCode, String pipelineStepId) {
		return pipelineCodeOf(pipelineCode) + KEY_SEPARATOR + pipelineStepId;
	}

	/** The pipeline a null pipeline code stands for: the default one. */
	public static String pipelineCodeOf(String pipelineCode) {
		return pipelineCode != null ? pipelineCode : ChatPipelinesConfiguration.DEFAULT_PIPELINE;
	}

	/** An empty configuration of a step, with its key filled. */
	public static GPipelineStepToolingConfig of(String pipelineCode, String pipelineStepId) {
		final GPipelineStepToolingConfig config = new GPipelineStepToolingConfig();
		config.setCode(codeOf(pipelineCode, pipelineStepId));
		config.setPipelineCode(pipelineCodeOf(pipelineCode));
		config.setPipelineStepId(pipelineStepId);
		return config;
	}
}
