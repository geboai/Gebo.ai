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
import ai.gebo.architecture.agents.model.PipelineType;
import ai.gebo.architecture.agents.services.IDynamicAgentsNetworkDataSource;
import ai.gebo.architecture.agents.services.IGDynamicAgentConfigDataSource;
import ai.gebo.llms.agent.chat.service.impl.AgenticLoopPureChatReactiveAgentServiceImpl;
import ai.gebo.llms.agent.chat.service.impl.AgenticLoopReactiveAgentServiceImpl;
import ai.gebo.llms.agent.chat.service.impl.GReactiveChatAgentsNetworkServiceFactoryImpl;
import ai.gebo.llms.agent.standard.services.ChatRuntimeDataQueryAdapterAgentService;

/**
 * The single agent with tools working in a loop, as two networks the chats can be
 * handed to: one for the chats with a chat profile (RAG pipeline), whose agent also
 * searches the internal knowledge base, and one for the free chats (pure chat
 * pipeline), whose agent does not. Each network is the chat input adapter feeding
 * the agent, which is the output node.
 */
@ConditionalOnProperty(prefix = "ai.gebo.agents.standard", name = "enabled", havingValue = "true", matchIfMissing = true)
@Configuration
public class AgenticLoopAgentsInitialization {
	private static final Logger LOGGER = LoggerFactory.getLogger(AgenticLoopAgentsInitialization.class);
	public static final String AGENTIC_LOOP_AGENTS_NETWORK = "AGENTIC_LOOP_AGENTS_NETWORK";
	public static final String AGENTIC_LOOP_PURE_CHAT_AGENTS_NETWORK = "AGENTIC_LOOP_PURE_CHAT_AGENTS_NETWORK";
	public static final String AGENTIC_LOOP_AGENTS_NETWORK_QUALIFIER = "AGENTIC_LOOP_AGENTS_NETWORK_QUALIFIER";
	public static final String AGENTIC_LOOP_AGENT_CONFIG = "agenticLoopAgent";
	public static final String AGENTIC_LOOP_PURE_CHAT_AGENT_CONFIG = "agenticLoopPureChatAgent";
	private static final String REPORT_WRITER_AGENT_ROLE = "REPORT_WRITER_AGENT";
	private static final String AGENT_DESCRIPTION = "Single agent operating every available tool, the internal knowledge base search included, in a loop until the answer is complete";
	private static final String PURE_CHAT_AGENT_DESCRIPTION = "Single agent operating every available tool but the internal knowledge base search in a loop until the answer is complete";
	private static final String NETWORK_DESCRIPTION = "Single agent tool calling loop: one agent searches the knowledge bases, the web and the external systems, calls the tools as many times as it needs and writes the answer itself";
	private static final String PURE_CHAT_NETWORK_DESCRIPTION = "Single agent tool calling loop for free chats: one agent searches the web and the external systems, calls the tools as many times as it needs and writes the answer itself, without the internal knowledge bases";
	public static final String SUGGESTED_PURPOSE = "Fits most scenarios where a capable LLM, in the order of 100-1000B parameters, is used and paid per million tokens: fewer LLM calls, but the model must reason and call tools reliably.";
	private static final String NETWORK_SCENARIO = "A single agent answers the user: it operates the available tools (internal knowledge base, web and external searches, connectors) as many times as needed and decides by itself when the answer is complete.";
	private static final String PURE_CHAT_NETWORK_SCENARIO = "A single agent answers the user in a free chat: it operates the available tools (web and external searches, connectors) as many times as needed and decides by itself when the answer is complete.";

	@Bean
	public IGDynamicAgentConfigDataSource agenticLoopAgentConfigDataSource() {
		return IGDynamicAgentConfigDataSource.of(agentConfig(AGENTIC_LOOP_AGENT_CONFIG,
				AgenticLoopReactiveAgentServiceImpl.AGENTIC_LOOP_NETWORK_AGENT_SERVICE, AGENT_DESCRIPTION));
	}

	@Bean
	public IGDynamicAgentConfigDataSource agenticLoopPureChatAgentConfigDataSource() {
		return IGDynamicAgentConfigDataSource.of(agentConfig(AGENTIC_LOOP_PURE_CHAT_AGENT_CONFIG,
				AgenticLoopPureChatReactiveAgentServiceImpl.AGENTIC_LOOP_PURE_CHAT_NETWORK_AGENT_SERVICE,
				PURE_CHAT_AGENT_DESCRIPTION));
	}

	private static GAgentConfig agentConfig(String code, String serviceId, String description) {
		GAgentConfig config = new GAgentConfig();
		config.setCode(code);
		config.setAgentServiceId(serviceId);
		config.setMainLoopPromptUseCode(StandardAgentsPromptsLibraryConfig.DEFAULT_CHAT_AGENT_PROMPT);
		config.setDescription(description);
		config.setAgentRoleCode(REPORT_WRITER_AGENT_ROLE);
		config.setAccessibleToAll(true);
		config.setUseDefaultChatModel(true);
		config.setSubscribeAllTools(true);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Declaring the agentic loop agent config code:" + code + " service:" + serviceId
					+ " on prompt use:" + config.getMainLoopPromptUseCode());
		}
		return config;
	}

	@Bean
	@Qualifier(AGENTIC_LOOP_AGENTS_NETWORK_QUALIFIER)
	public IDynamicAgentsNetworkDataSource agenticLoopAgentsNetworkDataSource() {
		return new IDynamicAgentsNetworkDataSource() {
			@Override
			public List<GAgentsNetwork> getConfigurations() {
				return List.of(
						createAgenticLoopNetwork(AGENTIC_LOOP_AGENTS_NETWORK, NETWORK_DESCRIPTION, NETWORK_SCENARIO,
								AGENTIC_LOOP_AGENT_CONFIG, PipelineType.RAG_PIPELINE),
						createAgenticLoopNetwork(AGENTIC_LOOP_PURE_CHAT_AGENTS_NETWORK, PURE_CHAT_NETWORK_DESCRIPTION,
								PURE_CHAT_NETWORK_SCENARIO, AGENTIC_LOOP_PURE_CHAT_AGENT_CONFIG,
								PipelineType.PURE_CHAT_PIPELINE));
			}
		};
	}

	static GAgentsNetwork createAgenticLoopNetwork(String code, String description, String scenario,
			String agentConfigCode, PipelineType pipelineType) {
		GAgentsNetwork network = new GAgentsNetwork();
		network.setCode(code);
		network.setDescription(description);
		network.setSuggestedPurpose(SUGGESTED_PURPOSE);
		network.setReadOnly(true);
		network.setChoosableForPipelineTypes(List.of(pipelineType));
		network.setAgentsNetworkServiceFactoryId(
				GReactiveChatAgentsNetworkServiceFactoryImpl.REACTIVE_CHAT_AGENTS_NETWORK);
		// The loop runs inside the agent: the network only delivers the request to it.
		network.setMaxLoopIteration(4);
		network.setScenarioDescription(scenario);
		network.setAccessibleToAll(true);
		AgentNetworkParticipant input = new AgentNetworkParticipant();
		input.setAgentConfigCode(ChatRuntimeDataQueryAdapterAgentService.CHAT_RUNTIME_DATA_QUERY_ADAPTER);
		input.setInputNode(true);
		input.setOutputNode(false);
		AgentNetworkParticipant agent = new AgentNetworkParticipant();
		agent.setAgentConfigCode(agentConfigCode);
		agent.setInputNode(false);
		agent.setOutputNode(true);
		agent.setMaxInvocations(1);
		agent.setMaxConsecutiveInvocations(1);
		input.setCommunicationList(List.of(agent.getNetworkAgentName()));
		agent.setCommunicationList(List.of());
		network.setAgents(List.of(input, agent));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Created the agentic loop network code:" + network.getCode() + " for:" + pipelineType
					+ " input:" + input.getNetworkAgentName() + " -> agent:" + agent.getNetworkAgentName());
		}
		return network;
	}
}
