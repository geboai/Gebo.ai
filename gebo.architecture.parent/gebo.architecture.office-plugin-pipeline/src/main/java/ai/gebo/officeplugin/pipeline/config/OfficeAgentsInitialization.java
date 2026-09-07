/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.officeplugin.pipeline.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import ai.gebo.llms.agent.standard.config.StandardAgentsInitialization;
import ai.gebo.llms.agent.standard.services.DefaultControllerNetworkAgentService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.officeplugin.pipeline.OfficeAssistantConstants;
import ai.gebo.officeplugin.pipeline.agents.OfficeChatRuntimeDataQueryAdapterAgentService;
import ai.gebo.officeplugin.pipeline.agents.OfficeReportWriterReactiveAgentService;
import ai.gebo.officeplugin.pipeline.steps.OfficeAgentsNetworkStreamingStepService;

/**
 * Wires the office-assistant agents network. It reuses the standard chat network
 * topology (searchers, tool-calling agent, internal knowledge-base searcher and
 * fan-out) through {@link StandardAgentsInitialization#createChatAgentsNetwork},
 * substituting only three nodes: the office input adapter (which seeds the shared
 * context with the document fragments the user is editing), the coordinator run
 * with the office coordinator prompt, and the office report writer (which isolates
 * the document part from the chat answer).
 *
 * <p>
 * Requires the standard agents network to be enabled
 * ({@code ai.gebo.agents.standard.enabled=true}); the office network reuses its
 * searcher/tool config data sources and the shared controller agent service.
 */
@ConditionalOnProperty(prefix = "ai.gebo.officeplugin", name = "enabled", havingValue = "true")
@Configuration
public class OfficeAgentsInitialization {

	private static final String SUPERVISOR_AGENT_ROLE = "SUPERVISOR_AGENT";
	private static final String REPORT_WRITER_AGENT_ROLE = "REPORT_WRITER_AGENT";
	private static final String OFFICE_CONTROLLER_DESCRIPTION = "Office assistant coordinator/controller";
	private static final String OFFICE_INPUT_ADAPTER_DESCRIPTION = "Office assistant input adapter (query + document fragments)";
	private static final String OFFICE_REPORT_WRITER_DESCRIPTION = "Office assistant report/answer writer";

	@Bean
	public IGDynamicAgentConfigDataSource officeInputAdapterConfigDataSource() {
		GAgentConfig config = new GAgentConfig();
		config.setCode(OfficeAssistantConstants.OFFICE_INPUT_ADAPTER_AGENT);
		config.setAgentServiceId(OfficeAssistantConstants.OFFICE_INPUT_ADAPTER_AGENT);
		config.setDescription(OFFICE_INPUT_ADAPTER_DESCRIPTION);
		config.setAccessibleToAll(true);
		config.setUseDefaultChatModel(false);
		return IGDynamicAgentConfigDataSource.of(config);
	}

	@Bean
	public IGDynamicAgentConfigDataSource officeControllerAgentConfigDataSource() {
		GAgentConfig config = new GAgentConfig();
		config.setCode(OfficeAssistantConstants.OFFICE_CONTROLLER_AGENT_CONFIG);
		// Reuse the standard controller agent SERVICE, only the prompt differs.
		config.setAgentServiceId(DefaultControllerNetworkAgentService.CONTROLLER_AGENT);
		config.setMainLoopPromptUseCode(OfficePluginPromptsLibraryConfig.OFFICE_COORDINATOR_AGENT_PROMPT);
		config.setDescription(OFFICE_CONTROLLER_DESCRIPTION);
		config.setAccessibleToAll(true);
		config.setUseDefaultChatModel(true);
		config.setAgentRoleCode(SUPERVISOR_AGENT_ROLE);
		return IGDynamicAgentConfigDataSource.of(config);
	}

	@Bean
	public IGDynamicAgentConfigDataSource officeReportWriterConfigDataSource() {
		GAgentConfig config = new GAgentConfig();
		config.setCode(OfficeAssistantConstants.OFFICE_REPORT_WRITER_AGENT_CONFIG);
		config.setAgentServiceId(OfficeReportWriterReactiveAgentService.OFFICE_REPORT_WRITER_NETWORK_AGENT_SERVICE);
		config.setMainLoopPromptUseCode(OfficePluginPromptsLibraryConfig.OFFICE_REPORT_WRITER_AGENT_PROMPT);
		config.setDescription(OFFICE_REPORT_WRITER_DESCRIPTION);
		config.setAgentRoleCode(REPORT_WRITER_AGENT_ROLE);
		config.setAccessibleToAll(true);
		config.setUseDefaultChatModel(true);
		return IGDynamicAgentConfigDataSource.of(config);
	}

	@Bean
	@Qualifier(OfficeAssistantConstants.OFFICE_CHAT_AGENTS_NETWORK_QUALIFIER)
	public IDynamicAgentsNetworkDataSource officeAgentsNetworkDataSource(StandardAgentsInitialization standardInit,
			@Autowired(required = false) @Qualifier(StandardAgentsInitialization.INTERNAL_KNOWLEDGE_BASE_SEARCH_QUALIFIER) IGDynamicAgentConfigDataSource internalKnowledgebaseAgentConfigDataSource) {
		return new IDynamicAgentsNetworkDataSource() {

			@Override
			public List<GAgentsNetwork> getConfigurations() {
				GAgentsNetwork network = standardInit.createChatAgentsNetwork(
						OfficeAssistantConstants.OFFICE_ASSISTANT_AGENTS_NETWORK,
						"Office assistant agents network for document editing",
						OfficeAssistantConstants.OFFICE_INPUT_ADAPTER_AGENT,
						OfficeAssistantConstants.OFFICE_CONTROLLER_AGENT_CONFIG,
						OfficeAssistantConstants.OFFICE_REPORT_WRITER_AGENT_CONFIG,
						internalKnowledgebaseAgentConfigDataSource);
				return List.of(network);
			}
		};
	}

	@Bean
	public OfficeAgentsNetworkStreamingStepService officeAgentsNetworkStreamingStep(
			@Qualifier(OfficeAssistantConstants.OFFICE_CHAT_AGENTS_NETWORK_QUALIFIER) IDynamicAgentsNetworkDataSource officeNetworkDataSource,
			IGAgentsNetworkServiceFactoryRepositoryPattern agentsNetworkServiceFactory,
			IGChatSessionLifeCycleService lifeCycleService) {
		IGAgentsNetworkServiceFactory<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, IGReactiveChatAgentsNetworkService> factory = agentsNetworkServiceFactory
				.getFactory(IGReactiveChatAgentsNetworkService.class);
		return new OfficeAgentsNetworkStreamingStepService(factory, officeNetworkDataSource, lifeCycleService);
	}
}
