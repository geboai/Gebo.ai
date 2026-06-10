package ai.gebo.architecture.agents.services;

import java.util.List;

import ai.gebo.architecture.agents.model.GAgentConfig;

public interface IGDynamicAgentConfigDataSource {
	public List<GAgentConfig> getConfigurations();
}
