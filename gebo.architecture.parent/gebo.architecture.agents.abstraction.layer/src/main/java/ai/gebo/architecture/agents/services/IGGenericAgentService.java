package ai.gebo.architecture.agents.services;

import java.util.List;
import java.util.Optional;

import ai.gebo.architecture.agents.model.AgentCapabilities;
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

	/**
	 * Exports the capabilities, catalogs, resources and tools this agent makes
	 * available for the given configuration, so they can be rendered into the shared
	 * network-of-agents description used by coordinating agents to reason about their
	 * reachable peers. The default implementation only advertises the agent
	 * description; concrete agents override it to expose their specific capabilities.
	 *
	 * @param agentConfig the configuration the capabilities are evaluated against
	 *                    (may be {@code null} when no configuration is bound)
	 */
	public default AgentCapabilities getAgentCapabilities(GAgentConfig agentConfig) {
		return new AgentCapabilities(getDescription());
	}
}
