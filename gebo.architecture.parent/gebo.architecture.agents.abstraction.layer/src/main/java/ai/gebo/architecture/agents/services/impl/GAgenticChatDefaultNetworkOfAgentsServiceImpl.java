/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.GAgenticChatDefaultNetworkOfAgents;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.PipelineType;
import ai.gebo.architecture.agents.repository.GAgenticChatDefaultNetworkOfAgentsRepository;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.agents.services.IGAgenticChatDefaultNetworkOfAgentsService;
import ai.gebo.architecture.agents.services.IGConfiguredDefaultChatNetworksOfAgents;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GAgenticChatDefaultNetworkOfAgentsServiceImpl implements IGAgenticChatDefaultNetworkOfAgentsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GAgenticChatDefaultNetworkOfAgentsServiceImpl.class);
	// Resolved on use: the networks DAO aggregates the network data sources declared
	// by the agents configurations, which may need this service.
	private final ObjectProvider<IAgentsNetworkDao> networksDao;
	private final ObjectProvider<IGConfiguredDefaultChatNetworksOfAgents> configuredDefaults;
	private final GAgenticChatDefaultNetworkOfAgentsRepository repository;

	@Override
	public List<GAgentsNetwork> getChoosableNetworksOfAgents(PipelineType pipelineType) {
		List<GAgentsNetwork> choosable = new ArrayList<>();
		IAgentsNetworkDao dao = networksDao.getIfAvailable();
		if (dao != null && pipelineType != null) {
			for (GAgentsNetwork network : dao.getConfigurations()) {
				if (isChoosable(network, pipelineType)) {
					choosable.add(network);
				}
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getChoosableNetworksOfAgents(" + pipelineType + ") found " + choosable.size()
					+ " network(s): " + choosable.stream().map(GAgentsNetwork::getCode).toList());
		}
		return choosable;
	}

	@Override
	public String resolveChatNetworkOfAgents(PipelineType pipelineType, String chatProfileNetworkOfAgents) {
		String resolved = null;
		String from = null;
		if (usable(chatProfileNetworkOfAgents, pipelineType, "chat profile")) {
			resolved = chatProfileNetworkOfAgents;
			from = "chat profile";
		}
		if (resolved == null) {
			String chosen = administratorDefault(pipelineType);
			if (usable(chosen, pipelineType, "system default")) {
				resolved = chosen;
				from = "system default";
			}
		}
		if (resolved == null) {
			String configured = configuredDefault(pipelineType);
			if (usable(configured, pipelineType, "application configuration")) {
				resolved = configured;
				from = "application configuration";
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("resolveChatNetworkOfAgents(" + pipelineType + ", chatProfile:" + chatProfileNetworkOfAgents
					+ ") resolved:" + resolved + " from:" + from);
		}
		if (resolved == null) {
			LOGGER.warn("No usable chat network of agents for pipeline type " + pipelineType
					+ ": neither the chat profile, the system default nor the configuration name one");
		}
		return resolved;
	}

	@Override
	public GAgentsNetwork resolveChatNetwork(PipelineType pipelineType, String chatProfileNetworkOfAgents) {
		String code = resolveChatNetworkOfAgents(pipelineType, chatProfileNetworkOfAgents);
		return code != null ? findNetwork(code) : null;
	}

	@Override
	public List<AgenticChatDefaultNetworkInfo> getAgenticChatDefaultNetworks() {
		List<AgenticChatDefaultNetworkInfo> infos = new ArrayList<>();
		for (PipelineType pipelineType : PipelineType.values()) {
			infos.add(info(pipelineType));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Agentic chat default networks: " + infos);
		}
		return infos;
	}

	@Override
	public AgenticChatDefaultNetworkInfo setAgenticChatDefaultNetwork(PipelineType pipelineType, String networkCode) {
		if (pipelineType == null) {
			throw new IllegalArgumentException("The pipeline type is required");
		}
		GAgentsNetwork network = findNetwork(networkCode);
		if (network == null) {
			throw new IllegalArgumentException("No network of agents with code '" + networkCode + "'");
		}
		if (!isChoosable(network, pipelineType)) {
			throw new IllegalArgumentException(
					"The network of agents '" + networkCode + "' cannot be chosen for " + pipelineType);
		}
		GAgenticChatDefaultNetworkOfAgents chosen = new GAgenticChatDefaultNetworkOfAgents();
		chosen.setId(pipelineType.name());
		chosen.setPipelineType(pipelineType);
		chosen.setDefaultChatNetworkOfAgents(networkCode);
		chosen.setDateModified(new Date());
		repository.save(chosen);
		LOGGER.info("System default chat network of agents for " + pipelineType + " set to " + networkCode);
		return info(pipelineType);
	}

	@Override
	public AgenticChatDefaultNetworkInfo resetAgenticChatDefaultNetwork(PipelineType pipelineType) {
		if (pipelineType == null) {
			throw new IllegalArgumentException("The pipeline type is required");
		}
		repository.deleteById(pipelineType.name());
		LOGGER.info("System default chat network of agents for " + pipelineType
				+ " removed, the configured one applies");
		return info(pipelineType);
	}

	private AgenticChatDefaultNetworkInfo info(PipelineType pipelineType) {
		return new AgenticChatDefaultNetworkInfo(pipelineType, configuredDefault(pipelineType),
				administratorDefault(pipelineType), resolveChatNetworkOfAgents(pipelineType, null));
	}

	private String administratorDefault(PipelineType pipelineType) {
		if (pipelineType == null) {
			return null;
		}
		return repository.findById(pipelineType.name()).map(GAgenticChatDefaultNetworkOfAgents::getDefaultChatNetworkOfAgents)
				.orElse(null);
	}

	private String configuredDefault(PipelineType pipelineType) {
		IGConfiguredDefaultChatNetworksOfAgents configured = configuredDefaults.getIfAvailable();
		return configured != null && pipelineType != null
				? configured.getConfiguredDefaultChatNetworkOfAgents(pipelineType)
				: null;
	}

	/** Whether the option names an existing network choosable for the pipeline type. */
	private boolean usable(String networkCode, PipelineType pipelineType, String option) {
		if (networkCode == null || networkCode.isBlank()) {
			return false;
		}
		GAgentsNetwork network = findNetwork(networkCode);
		if (network == null) {
			LOGGER.warn("The " + option + " names the network of agents '" + networkCode
					+ "' that does not exist, the next option applies");
			return false;
		}
		if (!isChoosable(network, pipelineType)) {
			LOGGER.warn("The " + option + " names the network of agents '" + networkCode
					+ "' that cannot be chosen for " + pipelineType + ", the next option applies");
			return false;
		}
		return true;
	}

	private GAgentsNetwork findNetwork(String networkCode) {
		IAgentsNetworkDao dao = networksDao.getIfAvailable();
		return dao != null && networkCode != null ? dao.findByCode(networkCode) : null;
	}

	static boolean isChoosable(GAgentsNetwork network, PipelineType pipelineType) {
		return network != null && network.getChoosableForPipelineTypes() != null
				&& network.getChoosableForPipelineTypes().contains(pipelineType);
	}
}
