/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.chat.service.impl;

import java.util.List;

import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkService;
import ai.gebo.llms.agent.standard.services.GConversationAgentsSessionFactory;
import ai.gebo.llms.agent.standard.services.GNetworkEnvironmentBuilder;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.impl.NetworkOfAgentsSuspensionTransport;

/**
 * The default pipeline's output step that respawns the suspended network of
 * agents of a conversation with the user's reaction (see
 * {@link NetworkRespawnPlanner}): the run is planned as a respawn and streams as
 * a run does (see the base), ending with the network's answer or suspending
 * again into a new block the client reacts to in turn. A pipeline with an
 * environment of its own has a respawner of its own, planning through the same
 * planner.
 */
public class ReactiveChatAgentsNetworkRespawnStreamingOutputChatPipelineService
		extends ReactiveChatAgentsNetworkStreamingOutputChatPipelineService {
	public static final String SERVICE_ID = "ReactiveChatAgentsNetworkRespawnStreamingOutputChatPipelineService";
	private final NetworkRespawnPlanner planner;

	public ReactiveChatAgentsNetworkRespawnStreamingOutputChatPipelineService(
			IGAgentsNetworkServiceFactory<String, GeboChatMessageEnvelope, IGReactiveChatAgentsNetworkService> factory,
			IGChatSessionLifeCycleService lifeCycleService, GNetworkOfAgentsSuspensionRepository suspensions,
			NetworkOfAgentsSuspensionTransport suspensionTransport, GNetworkEnvironmentBuilder environmentBuilder,
			GConversationAgentsSessionFactory conversationMemory) {
		this(factory, lifeCycleService, new NetworkRespawnPlanner(suspensions, suspensionTransport),
				suspensionTransport, environmentBuilder, conversationMemory);
	}

	public ReactiveChatAgentsNetworkRespawnStreamingOutputChatPipelineService(
			IGAgentsNetworkServiceFactory<String, GeboChatMessageEnvelope, IGReactiveChatAgentsNetworkService> factory,
			IGChatSessionLifeCycleService lifeCycleService, NetworkRespawnPlanner planner,
			NetworkOfAgentsSuspensionTransport suspensionTransport, GNetworkEnvironmentBuilder environmentBuilder,
			GConversationAgentsSessionFactory conversationMemory) {
		// the network is the block's: no data source, no pipeline type resolves it
		super(factory, () -> List.of(), lifeCycleService, SERVICE_ID, null, null, environmentBuilder,
				conversationMemory);
		this.planner = planner;
		setSuspensionTransport(suspensionTransport);
	}

	@Override
	protected RunPlan planRun(ChatPipelineExecutionRuntimeData runtimeData) throws ChatPipelineException {
		return planner.planRespawn(runtimeData);
	}
}
