/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

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
import ai.gebo.llms.chat.pipelines.service.IRoutingChatPipelineStepService;

class PipelineToolingAdminServiceImplTest {
	private final IChatPipelineStepServiceRepositoryPattern steps = mock(IChatPipelineStepServiceRepositoryPattern.class);
	private final GPipelineStepToolingConfigRepository repository = mock(GPipelineStepToolingConfigRepository.class);
	private final ChatPipelinesConfiguration configuration = new ChatPipelinesConfiguration();
	private PipelineToolingAdminServiceImpl service;

	private IChatPipelineStepService step(String id, ModelUseInfos chat, ModelUseInfos serviceModel) {
		final IChatPipelineStepService step = mock(IChatPipelineStepService.class);
		when(step.getStepId()).thenReturn(id);
		when(step.getChatModelUseInfos()).thenReturn(chat);
		when(step.getServiceModelUseInfos()).thenReturn(serviceModel);
		when(steps.findByCode(id)).thenReturn(step);
		return step;
	}

	private static ChatPipelineConfiguration pipeline(String code, String router, String respawn) {
		final ChatPipelineConfiguration pipeline = new ChatPipelineConfiguration();
		pipeline.setCode(code);
		pipeline.setDescription(code + " description");
		pipeline.setStepInputId("input");
		pipeline.setStepRouterId(router);
		pipeline.setRespawnStepId(respawn);
		return pipeline;
	}

	private static PipelineStepToolingUpdate update(String pipeline, String step, List<String> chat,
			List<String> serviceModel) {
		final PipelineStepToolingUpdate update = new PipelineStepToolingUpdate();
		update.setPipelineCode(pipeline);
		update.setStepId(step);
		update.setChatModelFunctions(chat);
		update.setServiceModelFunctions(serviceModel);
		return update;
	}

	@BeforeEach
	void setUp() {
		configuration.getPipelines().clear();
		configuration.getPipelines().add(pipeline("first", "router", "respawn"));
		configuration.getPipelines().add(pipeline("second", "router", null));
		when(repository.findById(anyString())).thenReturn(Optional.empty());
		step("input", ModelUseInfos.notUsed(), ModelUseInfos.notUsed());
		final IRoutingChatPipelineStepService router = mock(IRoutingChatPipelineStepService.class);
		when(router.getStepId()).thenReturn("router");
		when(router.getChatModelUseInfos()).thenReturn(ModelUseInfos.notUsed());
		when(router.getServiceModelUseInfos()).thenReturn(ModelUseInfos.usedFor("routes"));
		when(router.getReachableStepIds()).thenReturn(List.of("answer", "not-in-this-build"));
		when(steps.findByCode("router")).thenReturn(router);
		step("answer", ModelUseInfos.usedFor("answers"), ModelUseInfos.notUsed());
		step("respawn", ModelUseInfos.notUsed(), ModelUseInfos.notUsed());
		service = new PipelineToolingAdminServiceImpl(configuration, steps, repository);
	}

	@Test
	void aPipelineShowsItsInputItsRouterWhereItLeadsAndItsRespawn() {
		final PipelineToolingInfo first = service.getPipelinesTooling().get(0);

		assertEquals("first", first.getPipelineCode());
		assertEquals(List.of("input", "router", "answer", "respawn"),
				first.getSteps().stream().map(PipelineStepToolingInfo::getStepId).toList());
	}

	@Test
	void aStepSharedByTwoPipelinesHasTheToolsOfEach() {
		final GPipelineStepToolingConfig config = GPipelineStepToolingConfig.of("first", "answer");
		config.setChatModelFunctions(List.of("searchWeb"));
		when(repository.findById(config.getCode())).thenReturn(Optional.of(config));

		final List<PipelineToolingInfo> pipelines = service.getPipelinesTooling();

		assertEquals(List.of("searchWeb"), pipelines.get(0).getSteps().get(2).getChatModelFunctions());
		assertEquals(List.of(), pipelines.get(1).getSteps().get(2).getChatModelFunctions());
	}

	@Test
	void aModelTheStepDoesNotUseGetsNoTools() {
		service.saveStepTooling(update("first", "answer", List.of("searchWeb"), List.of("readPage")));

		final ArgumentCaptor<GPipelineStepToolingConfig> saved = ArgumentCaptor
				.forClass(GPipelineStepToolingConfig.class);
		verify(repository).save(saved.capture());
		assertEquals(GPipelineStepToolingConfig.codeOf("first", "answer"), saved.getValue().getCode());
		assertEquals(List.of("searchWeb"), saved.getValue().getChatModelFunctions());
		assertEquals(List.of(), saved.getValue().getServiceModelFunctions());
	}

	@Test
	void aStepLeftWithNoToolsKeepsNoConfiguration() {
		service.saveStepTooling(update("first", "answer", List.of(), null));

		verify(repository).deleteById(GPipelineStepToolingConfig.codeOf("first", "answer"));
		verify(repository, never()).save(any());
	}

	@Test
	void aPipelineOrAStepNotRunByItIsRefused() {
		assertThrows(IllegalArgumentException.class,
				() -> service.saveStepTooling(update("unknown", "answer", List.of("searchWeb"), null)));
		assertThrows(IllegalArgumentException.class,
				() -> service.saveStepTooling(update("second", "respawn", List.of("searchWeb"), null)));
		verify(repository, never()).save(any());
	}
}
