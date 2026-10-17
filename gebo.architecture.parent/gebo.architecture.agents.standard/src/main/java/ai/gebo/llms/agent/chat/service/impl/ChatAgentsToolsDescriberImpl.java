/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.chat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.AgentMountedTools;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.PipelineType;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGAgenticChatDefaultNetworkOfAgentsService;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.chat.abstraction.layer.model.AgentToolsInfo;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatAgentsToolsDescriber;

/**
 * The agents of the network a chat is handed to, with the tools each mounts: the
 * network is the one the chat would run on (the profile's own, else the default of
 * the pipeline type), the tools are the ones its service resolves from the agent
 * configuration, listed from the catalogue so they carry their admin description.
 */
@Service
public class ChatAgentsToolsDescriberImpl implements IGChatAgentsToolsDescriber {
	private static final Logger LOGGER = LoggerFactory.getLogger(ChatAgentsToolsDescriberImpl.class);
	private final IGAgenticChatDefaultNetworkOfAgentsService defaultNetworksService;
	private final IAgentConfigDao agentConfigDao;
	private final IGAgentServiceRuntimeDao agentServicesDao;
	private final IGToolCallbackSourceRepositoryPattern toolsRepository;

	public ChatAgentsToolsDescriberImpl(IGAgenticChatDefaultNetworkOfAgentsService defaultNetworksService,
			IAgentConfigDao agentConfigDao, IGAgentServiceRuntimeDao agentServicesDao,
			IGToolCallbackSourceRepositoryPattern toolsRepository) {
		this.defaultNetworksService = defaultNetworksService;
		this.agentConfigDao = agentConfigDao;
		this.agentServicesDao = agentServicesDao;
		this.toolsRepository = toolsRepository;
	}

	@Override
	public List<AgentToolsInfo> describeAgentsTools(GChatProfileConfiguration profile) {
		final List<AgentToolsInfo> infos = new ArrayList<AgentToolsInfo>();
		if (!defaultNetworksService.isAgenticChatNetworksEnabled()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("describeAgentsTools(...) the agentic chats are off: no agent described");
			}
			return infos;
		}
		final PipelineType pipelineType = profile != null ? PipelineType.RAG_PIPELINE
				: PipelineType.PURE_CHAT_PIPELINE;
		final String profileNetwork = profile != null ? profile.getDefaultChatNetworkOfAgents() : null;
		final GAgentsNetwork network = defaultNetworksService.resolveChatNetwork(pipelineType, profileNetwork);
		if (network == null || network.getAgents() == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("describeAgentsTools(...) pipelineType:" + pipelineType + " profile:"
						+ (profile != null ? profile.getCode() : null) + " resolves no network");
			}
			return infos;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin describeAgentsTools(...) pipelineType:" + pipelineType + " profile:"
					+ (profile != null ? profile.getCode() : null) + " network:" + network.getCode() + " participants:"
					+ network.getAgents().size());
		}
		for (AgentNetworkParticipant participant : network.getAgents()) {
			if (participant == null) {
				continue;
			}
			final AgentToolsInfo info = new AgentToolsInfo();
			info.setNetworkCode(network.getCode());
			info.setAgentName(participant.getNetworkAgentName());
			final GAgentConfig config = participant.getAgentConfigCode() != null
					? agentConfigDao.findByCode(participant.getAgentConfigCode())
					: null;
			if (config == null) {
				LOGGER.warn("describeAgentsTools(...) network:" + network.getCode() + " participant:"
						+ participant.getNetworkAgentName() + " has no agent configuration:"
						+ participant.getAgentConfigCode());
				infos.add(info);
				continue;
			}
			info.setAgentDescription(config.getDescription());
			info.setAgentServiceId(config.getAgentServiceId());
			final IGGenericAgentService service = config.getAgentServiceId() != null
					? agentServicesDao.findByCode(config.getAgentServiceId())
					: null;
			if (service == null) {
				LOGGER.warn("describeAgentsTools(...) network:" + network.getCode() + " participant:"
						+ participant.getNetworkAgentName() + " has no agent service:" + config.getAgentServiceId());
				infos.add(info);
				continue;
			}
			final AgentMountedTools mounted = service.getMountedTools(config);
			final List<String> names = mounted != null && mounted.getTools() != null
					? mounted.getTools().stream().map(AgentMountedTools.MountedTool::getName).filter(x -> x != null)
							.toList()
					: List.of();
			info.setMountMode(mounted != null && mounted.getMountMode() != null ? mounted.getMountMode().name() : null);
			info.setTools(toolsRepository.getEnabledToolsTree(names));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("describeAgentsTools(...) agent:" + info.getAgentName() + " service:"
						+ info.getAgentServiceId() + " mountMode:" + info.getMountMode() + " tools:" + names.size());
			}
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("describeAgentsTools(...) agent:" + info.getAgentName() + " tool names:" + names);
			}
			infos.add(info);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End describeAgentsTools(...) network:" + network.getCode() + " described " + infos.size()
					+ " agent(s)");
		}
		return infos;
	}
}
