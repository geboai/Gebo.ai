/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standard.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.IDynamicAgentsNetworkDataSource;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactoryRepositoryPattern;
import ai.gebo.architecture.agents.services.IGDynamicAgentConfigDataSource;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkService;
import ai.gebo.llms.agent.chat.service.impl.AgenticLoopReactiveAgentServiceImpl;
import ai.gebo.llms.agent.chat.service.impl.GReactiveChatAgentsNetworkServiceFactoryImpl;
import ai.gebo.llms.agent.chat.service.impl.ReactiveChatAgentsNetworkStreamingOutputChatPipelineService;
import ai.gebo.llms.agent.standard.services.ChatRuntimeDataQueryAdapterAgentService;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.DefaultRoutingChatPipelineStepServiceImpl;

/**
 * The single agent with tools working in a loop: its agent configuration, its
 * network (the chat input adapter feeding the agent, which is the output node) and
 * the chat pipeline step running that network, chosen from the chat menu.
 */
@ConditionalOnProperty(prefix = "ai.gebo.agents.standard", name = "enabled", havingValue = "true", matchIfMissing = true)
@Configuration
public class AgenticLoopAgentsInitialization {
	private static final Logger LOGGER = LoggerFactory.getLogger(AgenticLoopAgentsInitialization.class);
	public static final String AGENTIC_LOOP_AGENTS_NETWORK = "AGENTIC_LOOP_AGENTS_NETWORK";
	public static final String AGENTIC_LOOP_AGENTS_NETWORK_QUALIFIER = "AGENTIC_LOOP_AGENTS_NETWORK_QUALIFIER";
	public static final String AGENTIC_LOOP_AGENT_CONFIG = "agenticLoopAgent";
	private static final String REPORT_WRITER_AGENT_ROLE = "REPORT_WRITER_AGENT";
	private static final String AGENT_DESCRIPTION = "Single agent operating every available tool in a loop until the answer is complete";
	private static final String NETWORK_DESCRIPTION = "Single agent with tools working in a loop";
	private static final String NETWORK_SCENARIO = "A single agent answers the user: it operates the available tools (internal knowledge base, web and external searches, connectors) as many times as needed and decides by itself when the answer is complete.";

	@Bean
	public IGDynamicAgentConfigDataSource agenticLoopAgentConfigDataSource() {
		GAgentConfig config = new GAgentConfig();
		config.setCode(AGENTIC_LOOP_AGENT_CONFIG);
		config.setAgentServiceId(AgenticLoopReactiveAgentServiceImpl.AGENTIC_LOOP_NETWORK_AGENT_SERVICE);
		config.setMainLoopPromptUseCode(GeboPromptsLibrary.DEFAULT_CHAT_AGENT_PROMPT);
		config.setDescription(AGENT_DESCRIPTION);
		config.setAgentRoleCode(REPORT_WRITER_AGENT_ROLE);
		config.setAccessibleToAll(true);
		config.setUseDefaultChatModel(true);
		config.setSubscribeAllTools(true);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Declaring the agentic loop agent config code:" + config.getCode() + " on prompt use:"
					+ config.getMainLoopPromptUseCode());
		}
		return IGDynamicAgentConfigDataSource.of(config);
	}

	@Bean
	@Qualifier(AGENTIC_LOOP_AGENTS_NETWORK_QUALIFIER)
	public IDynamicAgentsNetworkDataSource agenticLoopAgentsNetworkDataSource() {
		return new IDynamicAgentsNetworkDataSource() {
			@Override
			public List<GAgentsNetwork> getConfigurations() {
				return List.of(createAgenticLoopNetwork());
			}
		};
	}

	static GAgentsNetwork createAgenticLoopNetwork() {
		GAgentsNetwork network = new GAgentsNetwork();
		network.setCode(AGENTIC_LOOP_AGENTS_NETWORK);
		network.setDescription(NETWORK_DESCRIPTION);
		network.setReadOnly(true);
		network.setDefaultUserInteractionNetwork(false);
		network.setAgentsNetworkServiceFactoryId(
				GReactiveChatAgentsNetworkServiceFactoryImpl.REACTIVE_CHAT_AGENTS_NETWORK);
		// The loop runs inside the agent: the network only delivers the request to it.
		network.setMaxLoopIteration(4);
		network.setScenarioDescription(NETWORK_SCENARIO);
		network.setAccessibleToAll(true);
		AgentNetworkParticipant input = new AgentNetworkParticipant();
		input.setAgentConfigCode(ChatRuntimeDataQueryAdapterAgentService.CHAT_RUNTIME_DATA_QUERY_ADAPTER);
		input.setInputNode(true);
		input.setOutputNode(false);
		AgentNetworkParticipant agent = new AgentNetworkParticipant();
		agent.setAgentConfigCode(AGENTIC_LOOP_AGENT_CONFIG);
		agent.setInputNode(false);
		agent.setOutputNode(true);
		agent.setMaxInvocations(1);
		agent.setMaxConsecutiveInvocations(1);
		input.setCommunicationList(List.of(agent.getNetworkAgentName()));
		agent.setCommunicationList(List.of());
		network.setAgents(List.of(input, agent));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Created the agentic loop network code:" + network.getCode() + " input:"
					+ input.getNetworkAgentName() + " -> agent:" + agent.getNetworkAgentName());
		}
		return network;
	}

	@Bean
	public IStreamingOutputChatPipelineService agenticLoopNetworkStreamingStep(
			@Qualifier(AGENTIC_LOOP_AGENTS_NETWORK_QUALIFIER) IDynamicAgentsNetworkDataSource networkDataSource,
			IGAgentsNetworkServiceFactoryRepositoryPattern agentsNetworkServiceFactory,
			IGChatSessionLifeCycleService lifeCycleService) {
		IGAgentsNetworkServiceFactory<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, IGReactiveChatAgentsNetworkService> factory = agentsNetworkServiceFactory
				.getFactory(IGReactiveChatAgentsNetworkService.class);
		LOGGER.info("Registered the agentic loop chat pipeline step '{}'",
				DefaultRoutingChatPipelineStepServiceImpl.AGENTIC_LOOP_STREAMING_STEP);
		return new ReactiveChatAgentsNetworkStreamingOutputChatPipelineService(factory, networkDataSource,
				lifeCycleService, DefaultRoutingChatPipelineStepServiceImpl.AGENTIC_LOOP_STREAMING_STEP);
	}
}
