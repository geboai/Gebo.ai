/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GBaseTranscriptModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseTranscriptModelConfig;
import ai.gebo.llms.abstraction.layer.model.GTranscriptModelType;

/**
 * Gebo.ai comment agent
 *
 * This interface defines the contract for services that support configuration
 * of transcript models. It extends the generic model configuration support
 * service interface by specifying types related to transcript models.
 *
 * @param <ModelChoice> The type representing choices/configurations specific to the transcript model.
 * @param <ModelConfig> The type representing the configuration of a transcript model.
 */
public interface IGTranscriptModelConfigurationSupportService<ModelChoice extends GBaseTranscriptModelChoice, ModelConfig extends GBaseTranscriptModelConfig<ModelChoice>>
		extends IGModelConfigurationSupportService<GTranscriptModelType, ModelChoice, ModelConfig> {
	
	/**
	 * Creates an instance of a configurable transcript model using the specified configuration.
	 *
	 * @param config The configuration specific to the transcript model.
	 * @return An instance of a configurable transcript model.
	 * @throws LLMConfigException If there is an error during the creation of the transcript model.
	 */
	public IGConfigurableTranscriptModel<ModelConfig> create(ModelConfig config) throws LLMConfigException;

}