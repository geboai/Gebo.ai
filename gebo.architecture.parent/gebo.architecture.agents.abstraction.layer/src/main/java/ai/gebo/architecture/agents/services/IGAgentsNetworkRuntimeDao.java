package ai.gebo.architecture.agents.services;

import ai.gebo.architecture.agents.model.RuntimeAgentInfos;

public interface IGAgentsNetworkRuntimeDao {
	public RuntimeAgentInfos findAgentByCode(String agentName);
}
