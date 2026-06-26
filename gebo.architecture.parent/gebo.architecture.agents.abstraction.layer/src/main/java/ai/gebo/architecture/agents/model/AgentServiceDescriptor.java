package ai.gebo.architecture.agents.model;

import ai.gebo.architecture.agents.services.IGAgentsNetworkToNetworkAgentAdapterService;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
import ai.gebo.architecture.agents.services.IGNetworkAgentService;
import ai.gebo.architecture.agents.services.IGRoutingNetworkAgentService;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Lightweight, serialization-friendly view of an agent service used by the
 * visual network composer. It exposes the information a UI needs to lay out and
 * catenate agent services: the service id/description, its input/output types
 * (as fully qualified class names) and the flags that drive composition rules
 * (whether the service is a network agent, a routing/coordinator agent, or a
 * {@link IGAgentsNetworkToNetworkAgentAdapterService network-of-agents adapter}
 * that can be dropped in place of a plain network agent).
 */
@Data
@NoArgsConstructor
public class AgentServiceDescriptor {

	private String serviceId;
	private String description;
	private String inputType;
	private String outputType;
	private boolean networkAgent;
	private boolean routingAgent;
	private boolean networkAdapter;

	/**
	 * Builds a descriptor from a runtime agent service. Input/output types are
	 * resolved only when the service is an {@link IGNetworkAgentService} (the only
	 * services that can be catenated in a network); for other services those fields
	 * stay {@code null}.
	 */
	public static AgentServiceDescriptor of(IGGenericAgentService service) {
		AgentServiceDescriptor descriptor = new AgentServiceDescriptor();
		descriptor.setServiceId(service.getId());
		descriptor.setDescription(service.getDescription());
		if (service instanceof IGNetworkAgentService<?, ?> networkAgent) {
			descriptor.setNetworkAgent(true);
			descriptor.setInputType(networkAgent.getInputType() != null ? networkAgent.getInputType().getName() : null);
			descriptor.setOutputType(
					networkAgent.getOutputType() != null ? networkAgent.getOutputType().getName() : null);
		}
		descriptor.setRoutingAgent(service instanceof IGRoutingNetworkAgentService);
		descriptor.setNetworkAdapter(service instanceof IGAgentsNetworkToNetworkAgentAdapterService);
		return descriptor;
	}
}
