/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCallingManagerFactory;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatProfilesRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatFullSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineConfiguration;
import ai.gebo.llms.chat.pipelines.model.IChatPipelineStepRuntimeData;
import ai.gebo.llms.chat.pipelines.model.RoutingDecision;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineStepService.StepType;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineStepServiceRepositoryPattern;
import ai.gebo.llms.chat.pipelines.service.IGRequestUnderstandingService;
import ai.gebo.llms.chat.pipelines.service.IInputChatPipelineStepService;
import ai.gebo.llms.chat.pipelines.service.IOutputChatPipelineService;
import ai.gebo.llms.chat.pipelines.service.IPipelineStepModelsService;
import ai.gebo.llms.chat.pipelines.service.IPipelineStepModelsService.StepModels;
import ai.gebo.llms.chat.pipelines.service.IRoutingChatPipelineStepService;

/** Each step of a run gets the models of its own configuration in the running pipeline. */
@SuppressWarnings({ "rawtypes", "unchecked" })
class ChatPipelinesExecutorStepModelsTest {
	private static final String PIPELINE = "a-pipeline";

	private final IGConfigurableChatModel chatModel = mock(IGConfigurableChatModel.class);
	private final IGConfigurableChatModel serviceModel = mock(IGConfigurableChatModel.class);
	private final IPipelineStepModelsService stepModels = mock(IPipelineStepModelsService.class);
	private final IInputChatPipelineStepService input = mock(IInputChatPipelineStepService.class);
	private final IRoutingChatPipelineStepService router = mock(IRoutingChatPipelineStepService.class);
	private final IOutputChatPipelineService output = mock(IOutputChatPipelineService.class);
	private ChatPipelinesExecutorImpl executor;
	private final GeboChatResponse response = new GeboChatResponse();

	private static IChatPipelineStepRuntimeData stepData(String stepId) {
		final IChatPipelineStepRuntimeData data = mock(IChatPipelineStepRuntimeData.class);
		when(data.getStepId()).thenReturn(stepId);
		return data;
	}

	private StepModels configuredFor(String stepId) throws Exception {
		final StepModels models = new StepModels(mock(IGConfigurableChatModel.class),
				mock(IGConfigurableChatModel.class));
		when(stepModels.modelsFor(PIPELINE, stepId, chatModel, serviceModel)).thenReturn(models);
		return models;
	}

	@BeforeEach
	void setUp() throws Exception {
		when(input.getStepId()).thenReturn("input");
		when(input.getStepType()).thenReturn(StepType.INPUT);
		when(router.getStepId()).thenReturn("router");
		when(router.getStepType()).thenReturn(StepType.ROUTING);
		when(output.getStepId()).thenReturn("output");
		when(output.getStepType()).thenReturn(StepType.OUTPUT);

		final IChatPipelineStepServiceRepositoryPattern steps = mock(IChatPipelineStepServiceRepositoryPattern.class);
		when(steps.findByCode("input")).thenReturn(input);
		when(steps.findByCode("router")).thenReturn(router);
		when(steps.findByCode("output")).thenReturn(output);

		final ChatPipelineConfiguration pipeline = new ChatPipelineConfiguration();
		pipeline.setCode(PIPELINE);
		pipeline.setStepInputId("input");
		pipeline.setStepRouterId("router");
		final ChatPipelinesConfiguration configuration = new ChatPipelinesConfiguration();
		configuration.getPipelines().add(pipeline);

		final IGChatSessionLifeCycleService lifeCycle = mock(IGChatSessionLifeCycleService.class);
		when(lifeCycle.createEmptyResponse(any())).thenReturn(response);
		when(lifeCycle.startRequest(any(), any(), any())).thenReturn(mock(LLMChatRequestResources.class));
		when(lifeCycle.getMinimalChatContext(any(), any(Integer.class))).thenReturn(mock(MinimalChatContext.class));

		executor = new ChatPipelinesExecutorImpl(steps, configuration, mock(IGChatFullSessionStateService.class),
				lifeCycle, mock(ChatProfilesRepository.class), mock(IGeboThreadManager.class),
				mock(IGRequestUnderstandingService.class), new GeboToolCallingManagerFactory(), stepModels);
	}

	@Test
	void everyStepRunsOnTheModelsOfItsOwnConfiguration() throws Exception {
		final StepModels inputModels = configuredFor("input");
		final StepModels routerModels = configuredFor("router");
		final StepModels outputModels = configuredFor("output");
		final IChatPipelineStepRuntimeData inputData = stepData("input");
		final IChatPipelineStepRuntimeData routerData = stepData("router");
		when(input.execute(any(), any(), any(), any())).thenReturn(inputData);
		when(router.execute(any(), any(), any(), any()))
				.thenReturn(new RoutingDecision(List.of("output"), routerData, "a-route", Map.of()));
		when(output.execute(any(), any(), any())).thenReturn(response);

		final GeboChatResponse answer = executor.execute(new GeboChatRequest(), new LinkedHashMap<>(), chatModel,
				serviceModel, PIPELINE);

		assertSame(response, answer);
		verify(input).execute(any(), isNull(), eq(inputModels.getChatModel()), eq(inputModels.getServiceModel()));
		verify(router).execute(any(), isNull(), eq(routerModels.getChatModel()), eq(routerModels.getServiceModel()));
		verify(output).execute(any(), eq(outputModels.getChatModel()), eq(outputModels.getServiceModel()));
	}
}
