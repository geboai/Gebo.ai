/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.service.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel.ChatModelConfigOptions;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.pipelines.model.GPipelineStepToolingConfig;
import ai.gebo.llms.chat.pipelines.repository.GPipelineStepToolingConfigRepository;
import ai.gebo.llms.chat.pipelines.service.IPipelineStepModelsService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PipelineStepModelsServiceImpl implements IPipelineStepModelsService {
	private final GPipelineStepToolingConfigRepository repository;

	@Override
	public StepModels modelsFor(String pipelineCode, String stepId, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel) throws LLMConfigException {
		final GPipelineStepToolingConfig config = repository
				.findById(GPipelineStepToolingConfig.codeOf(pipelineCode, stepId)).orElse(null);
		if (config == null) {
			return new StepModels(chatModel, serviceModel);
		}
		return new StepModels(withTools(chatModel, config.getChatModelFunctions()),
				withTools(serviceModel, config.getServiceModelFunctions()));
	}

	@Override
	public List<String> chatModelFunctions(String pipelineCode, String stepId) {
		final GPipelineStepToolingConfig config = repository
				.findById(GPipelineStepToolingConfig.codeOf(pipelineCode, stepId)).orElse(null);
		return config != null && config.getChatModelFunctions() != null ? config.getChatModelFunctions() : List.of();
	}

	@Override
	public List<String> pipelineFunctions(String pipelineCode) {
		final Set<String> functions = new LinkedHashSet<>();
		for (GPipelineStepToolingConfig config : repository
				.findByPipelineCode(GPipelineStepToolingConfig.pipelineCodeOf(pipelineCode))) {
			if (config.getChatModelFunctions() != null) {
				functions.addAll(config.getChatModelFunctions());
			}
			if (config.getServiceModelFunctions() != null) {
				functions.addAll(config.getServiceModelFunctions());
			}
		}
		return List.copyOf(functions);
	}

	/** The same code on the clone: its use is recorded on the model. */
	private static IGConfigurableChatModel withTools(IGConfigurableChatModel model, List<String> functions)
			throws LLMConfigException {
		if (model == null || functions == null || functions.isEmpty()) {
			return model;
		}
		return model.cloneWithOptions("", new ChatModelConfigOptions(null, null, null, functions));
	}
}
