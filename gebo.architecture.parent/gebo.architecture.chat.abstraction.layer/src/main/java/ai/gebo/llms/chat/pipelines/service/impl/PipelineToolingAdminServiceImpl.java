/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineConfiguration;
import ai.gebo.llms.chat.pipelines.model.GPipelineStepToolingConfig;
import ai.gebo.llms.chat.pipelines.model.ModelUseInfos;
import ai.gebo.llms.chat.pipelines.model.PipelineStepToolingInfo;
import ai.gebo.llms.chat.pipelines.model.PipelineStepToolingUpdate;
import ai.gebo.llms.chat.pipelines.model.PipelineToolingInfo;
import ai.gebo.llms.chat.pipelines.repository.GPipelineStepToolingConfigRepository;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineStepServiceRepositoryPattern;
import ai.gebo.llms.chat.pipelines.service.IPipelineToolingAdminService;
import ai.gebo.llms.chat.pipelines.service.IRoutingChatPipelineStepService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PipelineToolingAdminServiceImpl implements IPipelineToolingAdminService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PipelineToolingAdminServiceImpl.class);
	private final ChatPipelinesConfiguration pipelinesConfiguration;
	private final IChatPipelineStepServiceRepositoryPattern steps;
	private final GPipelineStepToolingConfigRepository repository;

	@Override
	public List<PipelineToolingInfo> getPipelinesTooling() {
		final List<PipelineToolingInfo> out = new ArrayList<>();
		for (ChatPipelineConfiguration pipeline : pipelinesConfiguration.getPipelines()) {
			final List<PipelineStepToolingInfo> infos = new ArrayList<>();
			for (IChatPipelineStepService step : stepsOf(pipeline)) {
				infos.add(infoOf(pipeline.getCode(), step));
			}
			out.add(new PipelineToolingInfo(pipeline.getCode(), pipeline.getDescription(),
					pipeline.isDefaultPipeline(), infos));
		}
		return out;
	}

	@Override
	public PipelineStepToolingInfo saveStepTooling(PipelineStepToolingUpdate update) {
		final ChatPipelineConfiguration pipeline = pipelinesConfiguration.getPipelines().stream()
				.filter(x -> x.getCode().equals(update.getPipelineCode())).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Unknown pipeline: " + update.getPipelineCode()));
		final IChatPipelineStepService step = stepsOf(pipeline).stream()
				.filter(x -> x.getStepId().equals(update.getStepId())).findFirst()
				.orElseThrow(() -> new IllegalArgumentException(
						"Pipeline " + update.getPipelineCode() + " runs no step " + update.getStepId()));
		final List<String> chatFunctions = functionsFor(step.getChatModelUseInfos(), update.getChatModelFunctions());
		final List<String> serviceFunctions = functionsFor(step.getServiceModelUseInfos(),
				update.getServiceModelFunctions());
		final GPipelineStepToolingConfig config = GPipelineStepToolingConfig.of(pipeline.getCode(), step.getStepId());
		if (chatFunctions.isEmpty() && serviceFunctions.isEmpty()) {
			repository.deleteById(config.getCode());
		} else {
			config.setChatModelFunctions(chatFunctions);
			config.setServiceModelFunctions(serviceFunctions);
			repository.save(config);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Tools of step " + config.getCode() + ": " + chatFunctions.size() + " on the chat model, "
					+ serviceFunctions.size() + " on the service model");
		}
		return infoOf(pipeline.getCode(), step);
	}

	/** The functions asked for a model, none when the step does not use it. */
	private static List<String> functionsFor(ModelUseInfos use, List<String> asked) {
		return use != null && use.isModelUsed() && asked != null ? List.copyOf(new LinkedHashSet<>(asked)) : List.of();
	}

	/** The registered steps a pipeline can run: its input, its router, where the router leads and its respawn. */
	private List<IChatPipelineStepService> stepsOf(ChatPipelineConfiguration pipeline) {
		final Set<String> ids = new LinkedHashSet<>();
		ids.add(pipeline.getStepInputId());
		ids.add(pipeline.getStepRouterId());
		if (steps.findByCode(pipeline.getStepRouterId()) instanceof IRoutingChatPipelineStepService router) {
			ids.addAll(router.getReachableStepIds());
		}
		ids.add(pipeline.getRespawnStepId());
		final List<IChatPipelineStepService> found = new ArrayList<>();
		for (String id : ids) {
			final IChatPipelineStepService step = id != null ? steps.findByCode(id) : null;
			if (step != null) {
				found.add(step);
			} else if (id != null && LOGGER.isDebugEnabled()) {
				LOGGER.debug("Pipeline " + pipeline.getCode() + " names step " + id + ", not part of this build");
			}
		}
		return found;
	}

	private PipelineStepToolingInfo infoOf(String pipelineCode, IChatPipelineStepService step) {
		final GPipelineStepToolingConfig config = repository
				.findById(GPipelineStepToolingConfig.codeOf(pipelineCode, step.getStepId())).orElse(null);
		return new PipelineStepToolingInfo(step.getStepId(), step.getChatModelUseInfos(),
				step.getServiceModelUseInfos(),
				config != null && config.getChatModelFunctions() != null ? config.getChatModelFunctions() : List.of(),
				config != null && config.getServiceModelFunctions() != null ? config.getServiceModelFunctions()
						: List.of());
	}
}
