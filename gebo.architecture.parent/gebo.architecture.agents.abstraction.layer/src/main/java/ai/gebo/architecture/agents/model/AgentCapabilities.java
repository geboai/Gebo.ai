package ai.gebo.architecture.agents.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Descriptor of what an agent can do and which resources it can reach, exported by
 * {@link ai.gebo.architecture.agents.services.IGGenericAgentService#getAgentCapabilities(GAgentConfig)}.
 * <p>
 * The descriptor is rendered into the shared network-of-agents description so that a
 * coordinator/controller agent (and any agent inspecting its reachable peers) can
 * reason about each agent's:
 * <ul>
 * <li>{@link #summary} – a one line description of the agent;</li>
 * <li>{@link #capabilities} – the high level abilities (what the agent can do);</li>
 * <li>{@link #catalogs} – the catalogs of content the agent can search/operate on
 * (e.g. knowledge bases, searchable systems);</li>
 * <li>{@link #resources} – other resources accessible through the agent;</li>
 * <li>{@link #tools} – the tools/functions the agent is allowed to call.</li>
 * </ul>
 */
@Getter
public class AgentCapabilities {

	@Setter
	private String summary;
	private final List<String> capabilities = new ArrayList<>();
	private final List<AgentCapabilityResource> catalogs = new ArrayList<>();
	private final List<AgentCapabilityResource> resources = new ArrayList<>();
	private final List<AgentCapabilityResource> tools = new ArrayList<>();

	public AgentCapabilities() {
	}

	public AgentCapabilities(String summary) {
		this.summary = summary;
	}

	public AgentCapabilities addCapability(String capability) {
		if (capability != null && !capability.isBlank()) {
			this.capabilities.add(capability);
		}
		return this;
	}

	public AgentCapabilities addCatalog(AgentCapabilityResource catalog) {
		if (catalog != null) {
			this.catalogs.add(catalog);
		}
		return this;
	}

	public AgentCapabilities addResource(AgentCapabilityResource resource) {
		if (resource != null) {
			this.resources.add(resource);
		}
		return this;
	}

	public AgentCapabilities addTool(AgentCapabilityResource tool) {
		if (tool != null) {
			this.tools.add(tool);
		}
		return this;
	}

	public boolean isEmpty() {
		return (summary == null || summary.isBlank()) && capabilities.isEmpty() && catalogs.isEmpty()
				&& resources.isEmpty() && tools.isEmpty();
	}
}
