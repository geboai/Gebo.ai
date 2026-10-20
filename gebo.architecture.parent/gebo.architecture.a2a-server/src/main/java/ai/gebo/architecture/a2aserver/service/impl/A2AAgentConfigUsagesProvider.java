package ai.gebo.architecture.a2aserver.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.a2aserver.model.A2AExportedAgent;
import ai.gebo.architecture.a2aserver.model.A2AServerConfig;
import ai.gebo.architecture.a2aserver.repository.A2AServerConfigRepository;
import ai.gebo.architecture.agents.services.IGAgentConfigUsagesProvider;

/**
 * The agent configurations the A2A servers export as single agents: they are not
 * used solely by a network of agents.
 */
@Service
public class A2AAgentConfigUsagesProvider implements IGAgentConfigUsagesProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(A2AAgentConfigUsagesProvider.class);
	private final A2AServerConfigRepository serverConfigRepository;

	public A2AAgentConfigUsagesProvider(A2AServerConfigRepository serverConfigRepository) {
		this.serverConfigRepository = serverConfigRepository;
	}

	@Override
	public List<String> findAgentConfigUsages(String agentConfigCode) {
		final List<String> usages = new ArrayList<>();
		if (agentConfigCode == null) {
			return usages;
		}
		for (A2AServerConfig server : serverConfigRepository.findAll()) {
			if (server.getExportedAgents() == null) {
				continue;
			}
			for (A2AExportedAgent exported : server.getExportedAgents()) {
				if (exported != null && exported.getKind() == A2AExportedAgent.Kind.AGENT
						&& agentConfigCode.equals(exported.getAgentConfigCode())) {
					usages.add("exported as A2A agent by the server '" + server.getCode() + "'");
				}
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("findAgentConfigUsages(" + agentConfigCode + ") A2A usages:" + usages);
		}
		return usages;
	}
}
