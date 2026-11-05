/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openchat.pipeline.steps;

import ai.gebo.architecture.agents.services.IDynamicAgentsNetworkDataSource;
import ai.gebo.architecture.agents.services.IGAgenticChatDefaultNetworkOfAgentsService;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkService;
import ai.gebo.llms.agent.chat.service.impl.NetworkRespawnPlanner;
import ai.gebo.llms.agent.standard.services.GConversationAgentsSessionFactory;
import ai.gebo.llms.agent.standard.services.GNetworkEnvironmentBuilder;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.impl.NetworkOfAgentsSuspensionTransport;
import ai.gebo.openchat.pipeline.OpenChatConstants;

/**
 * The open chat's respawner: the suspended network of the conversation respawned
 * with the user's reaction (see {@link NetworkRespawnPlanner}), in the open
 * chat's environment, as the open chat's network step runs it.
 */
public class OpenChatAgentsNetworkRespawnStepService extends OpenChatAgentsNetworkStreamingStepService {
	private final NetworkRespawnPlanner planner;

	public OpenChatAgentsNetworkRespawnStepService(
			IGAgentsNetworkServiceFactory<String, GeboChatMessageEnvelope, IGReactiveChatAgentsNetworkService> factory,
			IDynamicAgentsNetworkDataSource agentsNetworkDataSource, IGChatSessionLifeCycleService lifeCycleService,
			IGAgenticChatDefaultNetworkOfAgentsService defaultNetworksService,
			GNetworkEnvironmentBuilder environmentBuilder, GConversationAgentsSessionFactory conversationMemory,
			NetworkRespawnPlanner planner, NetworkOfAgentsSuspensionTransport suspensionTransport) {
		super(factory, agentsNetworkDataSource, lifeCycleService, defaultNetworksService, environmentBuilder,
				conversationMemory);
		this.planner = planner;
		setSuspensionTransport(suspensionTransport);
	}

	@Override
	public String getStepId() {
		return OpenChatConstants.OPEN_CHAT_NETWORK_RESPAWN_STEP;
	}

	@Override
	protected RunPlan planRun(ChatPipelineExecutionRuntimeData runtimeData) throws ChatPipelineException {
		return planner.planRespawn(runtimeData);
	}
}
