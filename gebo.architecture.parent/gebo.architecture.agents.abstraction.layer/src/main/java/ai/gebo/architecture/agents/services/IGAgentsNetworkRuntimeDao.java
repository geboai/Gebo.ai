package ai.gebo.architecture.agents.services;

import java.util.Map;

import ai.gebo.architecture.agents.model.RuntimeAgentInfos;

public interface IGAgentsNetworkRuntimeDao {
	public RuntimeAgentInfos findAgentByCode(String agentName) throws AgentException;

	public static IGAgentsNetworkRuntimeDao of(final Map<String, RuntimeAgentInfos> agents) {
		return new IGAgentsNetworkRuntimeDao() {

			@Override
			public RuntimeAgentInfos findAgentByCode(String agentName) {

				return agents.get(agentName);
			}
		};
	}
}
