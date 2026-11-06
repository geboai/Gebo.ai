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

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** The two models a chat pipeline step runs with, given the tools configured for it. */
public interface IPipelineStepModelsService {

	@AllArgsConstructor
	@Getter
	public static class StepModels {
		private final IGConfigurableChatModel chatModel;
		private final IGConfigurableChatModel serviceModel;
	}

	/**
	 * The models for a step run by a pipeline: each is a clone with the tools configured
	 * for its role, or the model given when that role has none. A null pipeline code is
	 * the default pipeline.
	 */
	public StepModels modelsFor(String pipelineCode, String stepId, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel) throws LLMConfigException;

	/** The functions configured for the chat model of a step run by a pipeline; empty when none. */
	public List<String> chatModelFunctions(String pipelineCode, String stepId);

	/** The functions configured for any model of any step of a pipeline; a null pipeline is the default one. */
	public List<String> pipelineFunctions(String pipelineCode);
}
