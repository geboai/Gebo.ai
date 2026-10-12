package ai.gebo.architecture.agents.services;

import java.util.List;
import java.util.Optional;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentMountedTools;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;

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

	/**
	 * Whether the agent sends what it receives to a chat model when it runs, the one
	 * its configuration resolves ({@code GAbstractGenericalAgentService#configuredChatModel}).
	 * True for every agent but the ones only moving data between their peers, as the
	 * chat input adapter: the compliance data-flow register reports the model of the
	 * agents calling one.
	 */
	public default boolean isCallingChatModel() {
		return true;
	}

	/**
	 * The chat model this agent runs with the given configuration, resolved by the
	 * same rule the agent applies when it runs; null when it cannot tell.
	 *
	 * @param agentConfig the configuration the model is resolved for
	 */
	public default IGConfigurableChatModel resolveAgentChatModel(GAgentConfig agentConfig) {
		return null;
	}

	/**
	 * The tools the given configuration mounts on this agent's model, as the agent
	 * mounts them when it runs. The default implementation mounts none: an agent
	 * that mounts the registered tools overrides it.
	 *
	 * @param agentConfig the configuration the tools are evaluated against
	 */
	public default AgentMountedTools getMountedTools(GAgentConfig agentConfig) {
		AgentMountedTools mounted = new AgentMountedTools();
		mounted.setAgentConfigCode(agentConfig != null ? agentConfig.getCode() : null);
		mounted.setAgentServiceId(getId());
		mounted.setMountMode(AgentMountedTools.MountMode.SELECTED);
		return mounted;
	}
}
