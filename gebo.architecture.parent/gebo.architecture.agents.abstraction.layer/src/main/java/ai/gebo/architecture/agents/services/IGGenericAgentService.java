package ai.gebo.architecture.agents.services;

import java.util.List;
import java.util.Optional;

import ai.gebo.architecture.agents.model.GAgentConfig;

public interface IGGenericAgentService {
	public String getId();

	public String getDescription();
	public List<GAgentConfig> getAccessibleConfigurations();

	public default Optional<GAgentConfig> getDefaultConfiguration() {
		List<GAgentConfig> configs = getAccessibleConfigurations();
		return configs.stream().filter(x -> x.getDefaultConfiguration() != null && x.getDefaultConfiguration())
				.findFirst();
	}
}
