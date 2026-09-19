/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openchat.pipeline.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.IDynamicAgentsNetworkDataSource;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactoryRepositoryPattern;
import ai.gebo.architecture.agents.services.IGDynamicAgentConfigDataSource;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkService;
import ai.gebo.llms.agent.chat.service.impl.ReportWriterReactiveAgentServiceImpl;
import ai.gebo.llms.agent.standard.config.StandardAgentsInitialization;
import ai.gebo.llms.agent.standard.services.ChatRuntimeDataQueryAdapterAgentService;
import ai.gebo.llms.agent.standard.services.DefaultControllerNetworkAgentService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.openchat.pipeline.OpenChatConstants;
import ai.gebo.openchat.pipeline.steps.OpenChatAgentsNetworkStreamingStepService;

/**
 * Wires the open-chat KB-free agents network. It reuses the standard chat network
 * topology (external searchers, tool-calling agent and fan-out) through
 * {@link StandardAgentsInitialization#createChatAgentsNetwork}, but passes a
 * {@code null} internal-knowledge-base config so the internal-KB searcher node is
 * never added - the network can only reach external systems. It substitutes two
 * nodes so the assistant answers freely: a coordinator run with the open-chat
 * coordinator prompt and an answer writer run with the open-chat free-response
 * prompt (no evidence/format discipline). The standard non-LLM input adapter is
 * reused unchanged.
 *
 * <p>
 * Requires both the open-chat module and the standard agents network to be enabled;
 * the network reuses the standard searcher/tool config data sources and the shared
 * controller/writer agent services, and {@code openChatAgentsNetworkDataSource(..)}
 * needs a {@link StandardAgentsInitialization} to call
 * {@code createChatAgentsNetwork(..)} on. Listing both properties makes the whole
 * chain back off when either is disabled (same contract as the office network).
 */
@ConditionalOnProperty(name = { "ai.gebo.openchat.enabled",
		"ai.gebo.agents.standard.enabled" }, havingValue = "true", matchIfMissing = true)
@Configuration
public class OpenChatAgentsInitialization {

	private static final String SUPERVISOR_AGENT_ROLE = "SUPERVISOR_AGENT";
	private static final String REPORT_WRITER_AGENT_ROLE = "REPORT_WRITER_AGENT";
	private static final String OPEN_CHAT_CONTROLLER_DESCRIPTION = "Open-chat coordinator/controller";
	private static final String OPEN_CHAT_ANSWER_WRITER_DESCRIPTION = "Open-chat free-response answer writer";

	@Bean
	public IGDynamicAgentConfigDataSource openChatControllerAgentConfigDataSource() {
		GAgentConfig config = new GAgentConfig();
		config.setCode(OpenChatConstants.OPEN_CHAT_CONTROLLER_AGENT_CONFIG);
		// Reuse the standard controller agent SERVICE; only the prompt differs.
		config.setAgentServiceId(DefaultControllerNetworkAgentService.CONTROLLER_AGENT);
		config.setMainLoopPromptUseCode(OpenChatPromptsLibraryConfig.OPEN_CHAT_COORDINATOR_AGENT_PROMPT);
		config.setDescription(OPEN_CHAT_CONTROLLER_DESCRIPTION);
		config.setAccessibleToAll(true);
		config.setUseDefaultChatModel(true);
		config.setAgentRoleCode(SUPERVISOR_AGENT_ROLE);
		return IGDynamicAgentConfigDataSource.of(config);
	}

	@Bean
	public IGDynamicAgentConfigDataSource openChatAnswerWriterConfigDataSource() {
		GAgentConfig config = new GAgentConfig();
		config.setCode(OpenChatConstants.OPEN_CHAT_ANSWER_WRITER_AGENT_CONFIG);
		// Reuse the standard writer SERVICE; the open-chat prompt drops the evidence /
		// deliverable-format discipline so the assistant answers freely.
		config.setAgentServiceId(ReportWriterReactiveAgentServiceImpl.REPORT_WRITER_NETWORK_AGENT_SERVICE);
		config.setMainLoopPromptUseCode(OpenChatPromptsLibraryConfig.OPEN_CHAT_ANSWER_WRITER_AGENT_PROMPT);
		config.setDescription(OPEN_CHAT_ANSWER_WRITER_DESCRIPTION);
		config.setAgentRoleCode(REPORT_WRITER_AGENT_ROLE);
		config.setAccessibleToAll(true);
		config.setUseDefaultChatModel(true);
		return IGDynamicAgentConfigDataSource.of(config);
	}

	@Bean
	@Qualifier(OpenChatConstants.OPEN_CHAT_AGENTS_NETWORK_QUALIFIER)
	public IDynamicAgentsNetworkDataSource openChatAgentsNetworkDataSource(StandardAgentsInitialization standardInit) {
		return new IDynamicAgentsNetworkDataSource() {

			@Override
			public List<GAgentsNetwork> getConfigurations() {
				GAgentsNetwork network = standardInit.createChatAgentsNetwork(OpenChatConstants.OPEN_CHAT_AGENTS_NETWORK,
						"Open-chat KB-free agents network (external search + free-response answering)",
						ChatRuntimeDataQueryAdapterAgentService.CHAT_RUNTIME_DATA_QUERY_ADAPTER,
						OpenChatConstants.OPEN_CHAT_CONTROLLER_AGENT_CONFIG,
						OpenChatConstants.OPEN_CHAT_ANSWER_WRITER_AGENT_CONFIG,
						// null internal-KB config => no internal knowledge-base searcher node.
						null);
				return List.of(network);
			}
		};
	}

	@Bean
	public OpenChatAgentsNetworkStreamingStepService openChatAgentsNetworkStreamingStep(
			@Qualifier(OpenChatConstants.OPEN_CHAT_AGENTS_NETWORK_QUALIFIER) IDynamicAgentsNetworkDataSource openChatNetworkDataSource,
			IGAgentsNetworkServiceFactoryRepositoryPattern agentsNetworkServiceFactory,
			IGChatSessionLifeCycleService lifeCycleService) {
		IGAgentsNetworkServiceFactory<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, IGReactiveChatAgentsNetworkService> factory = agentsNetworkServiceFactory
				.getFactory(IGReactiveChatAgentsNetworkService.class);
		return new OpenChatAgentsNetworkStreamingStepService(factory, openChatNetworkDataSource, lifeCycleService);
	}
}
