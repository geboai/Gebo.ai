package ai.gebo.architecture.mcpserver.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.services.IGAgentConfigUsagesProvider;
import ai.gebo.architecture.mcpserver.model.GeboMCPAgentTool;
import ai.gebo.architecture.mcpserver.model.GeboMCPServerConfig;
import ai.gebo.architecture.mcpserver.repository.GeboMCPServerConfigRepository;

/**
 * The agent configurations the MCP servers expose as tools: they are not used solely
 * by a network of agents.
 */
@Service
public class GeboMCPAgentConfigUsagesProvider implements IGAgentConfigUsagesProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeboMCPAgentConfigUsagesProvider.class);
	private final GeboMCPServerConfigRepository serverConfigRepository;

	public GeboMCPAgentConfigUsagesProvider(GeboMCPServerConfigRepository serverConfigRepository) {
		this.serverConfigRepository = serverConfigRepository;
	}

	@Override
	public List<String> findAgentConfigUsages(String agentConfigCode) {
		final List<String> usages = new ArrayList<>();
		if (agentConfigCode == null) {
			return usages;
		}
		for (GeboMCPServerConfig server : serverConfigRepository.findAll()) {
			if (server.getAgentAsTools() == null) {
				continue;
			}
			for (GeboMCPAgentTool tool : server.getAgentAsTools()) {
				if (tool != null && agentConfigCode.equals(tool.getAgentConfigCode())) {
					usages.add("exposed as the MCP tool '" + tool.getToolName() + "' by the server '" + server.getCode()
							+ "'");
				}
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("findAgentConfigUsages(" + agentConfigCode + ") MCP usages:" + usages);
		}
		return usages;
	}
}
