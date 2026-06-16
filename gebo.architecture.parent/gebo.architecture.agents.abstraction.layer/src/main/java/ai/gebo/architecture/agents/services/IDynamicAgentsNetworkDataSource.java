package ai.gebo.architecture.agents.services;

import java.util.List;

import ai.gebo.architecture.agents.model.GAgentsNetwork;

public interface IDynamicAgentsNetworkDataSource {
	public List<GAgentsNetwork> getConfigurations();
}
