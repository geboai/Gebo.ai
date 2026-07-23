package ai.gebo.architecture.agents.services;

import java.util.List;

import ai.gebo.architecture.agents.model.GAgentsNetwork;

public interface IDynamicAgentsNetworkDataSource {
	public static final String DEFAULT_CHAT_AGENTS_NETWORK_QUALIFIER = "DEFAULT_CHAT_AGENTS_NETWORK_QUALIFIER";

	public List<GAgentsNetwork> getConfigurations();
}
