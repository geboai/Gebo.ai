/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.chat.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.PipelineType;
import ai.gebo.architecture.agents.services.IDynamicAgentsNetworkDataSource;
import ai.gebo.architecture.agents.services.IGAgenticChatDefaultNetworkOfAgentsService;
import ai.gebo.llms.agent.standard.config.AgenticLoopAgentsInitialization;
import ai.gebo.llms.agent.standard.config.StandardAgentsConfig;
import ai.gebo.llms.agent.standardtools.InternalKnowledgeBaseSearchToolSource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;

/**
 * Pins which network of agents a chat pipeline hands a chat to.
 */
class ChatNetworkOfAgentsChoiceTest {

	private static GAgentsNetwork network(String code) {
		GAgentsNetwork network = new GAgentsNetwork();
		network.setCode(code);
		return network;
	}

	private static ChatPipelineExecutionRuntimeData runtimeData() {
		ChatPipelineExecutionRuntimeData runtimeData = mock(ChatPipelineExecutionRuntimeData.class);
		LLMChatRequestResources resources = mock(LLMChatRequestResources.class);
		when(resources.getCurrentRequest()).thenReturn(new GeboChatRequest());
		when(runtimeData.getRequestResources()).thenReturn(resources);
		return runtimeData;
	}

	private static IDynamicAgentsNetworkDataSource fallback() {
		return () -> List.of(network("FALLBACK"));
	}

	@Test
	void theNetworkChosenByTheChatProfileIsPassedToTheResolution() throws Exception {
		IGChatSessionLifeCycleService lifeCycle = mock(IGChatSessionLifeCycleService.class);
		GChatProfileConfiguration profile = new GChatProfileConfiguration();
		profile.setDefaultChatNetworkOfAgents("PROFILE_NETWORK");
		when(lifeCycle.getSessionChatProfile(any())).thenReturn(profile);
		IGAgenticChatDefaultNetworkOfAgentsService defaults = mock(IGAgenticChatDefaultNetworkOfAgentsService.class);
		when(defaults.resolveChatNetwork(PipelineType.RAG_PIPELINE, "PROFILE_NETWORK"))
				.thenReturn(network("PROFILE_NETWORK"));
		ReactiveChatAgentsNetworkStreamingOutputChatPipelineService step = new ReactiveChatAgentsNetworkStreamingOutputChatPipelineService(
				null, fallback(), lifeCycle, "step", PipelineType.RAG_PIPELINE, defaults);

		assertEquals("PROFILE_NETWORK", step.chooseNetwork(runtimeData()).getCode());
	}

	@Test
	void aFreeChatWithoutProfileIsResolvedForItsPipelineType() throws Exception {
		IGChatSessionLifeCycleService lifeCycle = mock(IGChatSessionLifeCycleService.class);
		IGAgenticChatDefaultNetworkOfAgentsService defaults = mock(IGAgenticChatDefaultNetworkOfAgentsService.class);
		when(defaults.resolveChatNetwork(PipelineType.PURE_CHAT_PIPELINE, null)).thenReturn(network("LOOP_PURE"));
		ReactiveChatAgentsNetworkStreamingOutputChatPipelineService step = new ReactiveChatAgentsNetworkStreamingOutputChatPipelineService(
				null, fallback(), lifeCycle, "step", PipelineType.PURE_CHAT_PIPELINE, defaults);

		assertEquals("LOOP_PURE", step.chooseNetwork(runtimeData()).getCode());
	}

	@Test
	void theDataSourceIsTheFallback() throws Exception {
		IGChatSessionLifeCycleService lifeCycle = mock(IGChatSessionLifeCycleService.class);
		IGAgenticChatDefaultNetworkOfAgentsService defaults = mock(IGAgenticChatDefaultNetworkOfAgentsService.class);
		ReactiveChatAgentsNetworkStreamingOutputChatPipelineService resolving = new ReactiveChatAgentsNetworkStreamingOutputChatPipelineService(
				null, fallback(), lifeCycle, "step", PipelineType.RAG_PIPELINE, defaults);
		ReactiveChatAgentsNetworkStreamingOutputChatPipelineService fixed = new ReactiveChatAgentsNetworkStreamingOutputChatPipelineService(
				null, fallback(), lifeCycle, "step");

		assertEquals("FALLBACK", resolving.chooseNetwork(runtimeData()).getCode(), "nothing resolved");
		assertEquals("FALLBACK", fixed.chooseNetwork(runtimeData()).getCode(), "a step without pipeline type");
	}

	@Test
	void theConfiguredDefaultsDependOnThePipelineType() {
		StandardAgentsConfig config = new StandardAgentsConfig();

		assertEquals(AgenticLoopAgentsInitialization.AGENTIC_LOOP_AGENTS_NETWORK,
				config.getConfiguredDefaultChatNetworkOfAgents(PipelineType.RAG_PIPELINE));
		assertEquals(AgenticLoopAgentsInitialization.AGENTIC_LOOP_PURE_CHAT_AGENTS_NETWORK,
				config.getConfiguredDefaultChatNetworkOfAgents(PipelineType.PURE_CHAT_PIPELINE));
	}

	@Test
	void theFreeChatLoopAgentDoesNotMountTheKnowledgeBaseSearch() {
		AgenticLoopPureChatReactiveAgentServiceImpl pure = new AgenticLoopPureChatReactiveAgentServiceImpl(null, null,
				null, null, null, null, null);
		AgenticLoopReactiveAgentServiceImpl rag = new AgenticLoopReactiveAgentServiceImpl(null, null, null, null, null,
				null, null);
		List<String> tools = List.of("searchWeb", InternalKnowledgeBaseSearchToolSource.SEARCH_KNOWLEDGE_BASE_TOOL);

		assertEquals(List.of("searchWeb"), pure.filterAutoMountedTools(tools));
		assertEquals(tools, rag.filterAutoMountedTools(tools));
	}
}
