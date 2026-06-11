package ai.gebo.architecture.agents.services;

import java.util.List;

import ai.gebo.architecture.agents.model.GAgentConfig;

@FunctionalInterface
public interface IGDynamicAgentConfigDataSource {
	public List<GAgentConfig> getConfigurations();

	public static IGDynamicAgentConfigDataSource of(GAgentConfig cfg) {
		return () -> List.of(cfg);
	}
}
