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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel.ChatModelConfigOptions;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.GPipelineStepToolingConfig;
import ai.gebo.llms.chat.pipelines.repository.GPipelineStepToolingConfigRepository;
import ai.gebo.llms.chat.pipelines.service.IPipelineStepModelsService.StepModels;

@SuppressWarnings({ "rawtypes", "unchecked" })
class PipelineStepModelsServiceImplTest {
	private GPipelineStepToolingConfigRepository repository;
	private PipelineStepModelsServiceImpl service;
	private IGConfigurableChatModel chatModel;
	private IGConfigurableChatModel serviceModel;

	@BeforeEach
	void setUp() {
		repository = mock(GPipelineStepToolingConfigRepository.class);
		service = new PipelineStepModelsServiceImpl(repository);
		chatModel = mock(IGConfigurableChatModel.class);
		serviceModel = mock(IGConfigurableChatModel.class);
		when(repository.findById(any())).thenReturn(Optional.empty());
	}

	private void configured(String pipeline, List<String> chat, List<String> serviceFunctions) {
		final GPipelineStepToolingConfig config = GPipelineStepToolingConfig.of(pipeline, "a-step");
		config.setChatModelFunctions(chat);
		config.setServiceModelFunctions(serviceFunctions);
		when(repository.findById(config.getCode())).thenReturn(Optional.of(config));
	}

	@Test
	void aStepNotConfiguredRunsOnTheModelsGivenWithNoClone() throws Exception {
		final StepModels models = service.modelsFor("open-chat", "a-step", chatModel, serviceModel);

		assertSame(chatModel, models.getChatModel());
		assertSame(serviceModel, models.getServiceModel());
		verify(chatModel, never()).cloneWithOptions(any(), any());
		verify(serviceModel, never()).cloneWithOptions(any(), any());
	}

	@Test
	void onlyTheRoleWithToolsIsCloned() throws Exception {
		configured("open-chat", List.of("searchWeb"), List.of());
		final IGConfigurableChatModel clone = mock(IGConfigurableChatModel.class);
		when(chatModel.cloneWithOptions(eq(""), any())).thenReturn(clone);

		final StepModels models = service.modelsFor("open-chat", "a-step", chatModel, serviceModel);

		assertSame(clone, models.getChatModel());
		assertSame(serviceModel, models.getServiceModel());
		verify(serviceModel, never()).cloneWithOptions(any(), any());
	}

	@Test
	void theCloneGetsTheToolsOfItsRole() throws Exception {
		configured("open-chat", null, List.of("readPage", "searchWeb"));
		when(serviceModel.cloneWithOptions(eq(""), any())).thenReturn(mock(IGConfigurableChatModel.class));

		service.modelsFor("open-chat", "a-step", chatModel, serviceModel);

		final ArgumentCaptor<ChatModelConfigOptions> options = ArgumentCaptor.forClass(ChatModelConfigOptions.class);
		verify(serviceModel).cloneWithOptions(eq(""), options.capture());
		assertEquals(List.of("readPage", "searchWeb"), options.getValue().getToolsName());
		verify(chatModel, never()).cloneWithOptions(any(), any());
	}

	@Test
	void theConfigurationOfAnotherPipelineDoesNotApply() throws Exception {
		configured("open-chat", List.of("searchWeb"), null);

		final StepModels models = service.modelsFor("another-pipeline", "a-step", chatModel, serviceModel);

		assertSame(chatModel, models.getChatModel());
	}

	@Test
	void aNullPipelineReadsTheDefaultPipelineConfiguration() throws Exception {
		configured(null, List.of("searchWeb"), null);
		final IGConfigurableChatModel clone = mock(IGConfigurableChatModel.class);
		when(chatModel.cloneWithOptions(eq(""), any())).thenReturn(clone);

		assertSame(clone, service.modelsFor(null, "a-step", chatModel, serviceModel).getChatModel());
	}

	@Test
	void aMissingModelStaysMissing() throws Exception {
		configured("open-chat", List.of("searchWeb"), List.of("searchWeb"));
		when(chatModel.cloneWithOptions(eq(""), any())).thenReturn(mock(IGConfigurableChatModel.class));

		assertNull(service.modelsFor("open-chat", "a-step", chatModel, null).getServiceModel());
	}

	@Test
	void theFunctionsOfAStepAreThoseOfItsPipeline() {
		configured("open-chat", List.of("searchWeb"), List.of("readPage"));

		assertEquals(List.of("searchWeb"), service.chatModelFunctions("open-chat", "a-step"));
		assertEquals(List.of(), service.chatModelFunctions("another-pipeline", "a-step"));
	}

	@Test
	void theFunctionsOfAPipelineAreThoseOfAllItsStepsOnce() {
		final GPipelineStepToolingConfig first = GPipelineStepToolingConfig.of("open-chat", "a-step");
		first.setChatModelFunctions(List.of("searchWeb"));
		first.setServiceModelFunctions(List.of("readPage"));
		final GPipelineStepToolingConfig second = GPipelineStepToolingConfig.of("open-chat", "another-step");
		second.setChatModelFunctions(List.of("searchWeb", "getDate"));
		when(repository.findByPipelineCode("open-chat")).thenReturn(List.of(first, second));

		assertEquals(List.of("searchWeb", "readPage", "getDate"), service.pipelineFunctions("open-chat"));
	}

	@Test
	void noPipelineIsTheDefaultOne() {
		final GPipelineStepToolingConfig config = GPipelineStepToolingConfig.of(null, "a-step");
		config.setChatModelFunctions(List.of("searchWeb"));
		when(repository.findByPipelineCode(ChatPipelinesConfiguration.DEFAULT_PIPELINE)).thenReturn(List.of(config));

		assertEquals(List.of("searchWeb"), service.pipelineFunctions(null));
	}
}
