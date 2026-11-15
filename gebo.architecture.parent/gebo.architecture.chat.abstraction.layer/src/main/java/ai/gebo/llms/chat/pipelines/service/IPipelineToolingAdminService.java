/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.service;

import java.util.List;

import ai.gebo.llms.chat.pipelines.model.PipelineStepToolingInfo;
import ai.gebo.llms.chat.pipelines.model.PipelineStepToolingUpdate;
import ai.gebo.llms.chat.pipelines.model.PipelineToolingInfo;

/** The tools of the steps of the chat pipelines, as the admin sees and sets them. */
public interface IPipelineToolingAdminService {

	/** Every pipeline with the registered steps it can run and their tools. */
	public List<PipelineToolingInfo> getPipelinesTooling();

	/**
	 * Sets the tools of a step of a pipeline: a model the step does not use gets none,
	 * and a step left with no tools keeps no configuration.
	 *
	 * @return the step as it is now
	 * @throws IllegalArgumentException when the pipeline cannot run that step
	 */
	public PipelineStepToolingInfo saveStepTooling(PipelineStepToolingUpdate update);
}
