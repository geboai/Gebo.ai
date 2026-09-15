package ai.gebo.architecture.agents.services;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.agents.model.RuntimeAgentInfos;

public interface IGAgentsNetworkRuntimeDao {
	Logger RUNTIME_DAO_LOGGER = LoggerFactory.getLogger(IGAgentsNetworkRuntimeDao.class);

	public RuntimeAgentInfos findAgentByCode(String agentName) throws AgentException;

	public static IGAgentsNetworkRuntimeDao of(final Map<String, RuntimeAgentInfos> agents) {
		if (RUNTIME_DAO_LOGGER.isDebugEnabled()) {
			RUNTIME_DAO_LOGGER.debug("Building a runtime agents DAO over " + (agents != null ? agents.size() : 0)
					+ " allocated agent(s)");
		}
		if (RUNTIME_DAO_LOGGER.isTraceEnabled()) {
			RUNTIME_DAO_LOGGER.trace("Allocated network agent names: " + (agents != null ? agents.keySet() : null));
		}
		return new IGAgentsNetworkRuntimeDao() {

			@Override
			public RuntimeAgentInfos findAgentByCode(String agentName) {
				RuntimeAgentInfos agent = agents.get(agentName);
				if (RUNTIME_DAO_LOGGER.isDebugEnabled()) {
					RUNTIME_DAO_LOGGER.debug("findAgentByCode(" + agentName + ") allocated:" + (agent != null)
							+ (agent != null ? " service:" + agent.getService().getId() : ""));
				}
				return agent;
			}
		};
	}
}
