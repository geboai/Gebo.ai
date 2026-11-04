/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.chat.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.NetworkOfAgentsExecutionResult;
import ai.gebo.architecture.agents.services.IDynamicAgentsNetworkDataSource;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;

/**
 * The network step runs the network with what the chat added to the request's
 * model calls, when it added something; the plain run otherwise.
 */
class ChatNetworkStepToolCallingTest {

	private static ReactiveChatAgentsNetworkStreamingOutputChatPipelineService step() {
		IDynamicAgentsNetworkDataSource fallback = () -> List.of(new GAgentsNetwork());
		return new ReactiveChatAgentsNetworkStreamingOutputChatPipelineService(null, fallback,
				mock(IGChatSessionLifeCycleService.class), "step");
	}

	private static ChatPipelineExecutionRuntimeData runtimeData(GeboToolCalling toolCalling) {
		LLMChatRequestResources resources = new LLMChatRequestResources();
		GeboChatRequest request = new GeboChatRequest();
		request.setId("request-1");
		resources.setCurrentRequest(request);
		return new ChatPipelineExecutionRuntimeData(null, 8192, resources, null, null, true, toolCalling);
	}

	@Test
	void theRequestsToolCallingReachesTheNetwork() throws Exception {
		IGReactiveChatAgentsNetworkService network = mock(IGReactiveChatAgentsNetworkService.class);
		GeboToolCalling toolCalling = GeboToolCalling.builder().caller("chat:request-1").build();
		when(network.executeNetwork(any(), any(), any(), any())).thenReturn(NetworkOfAgentsExecutionResult.of(null));

		step().executeNetwork(network, runtimeData(toolCalling), "What is new?", Map.of());

		verify(network).executeNetwork(any(), eq("What is new?"), eq(Map.of()), same(toolCalling));
	}
}
