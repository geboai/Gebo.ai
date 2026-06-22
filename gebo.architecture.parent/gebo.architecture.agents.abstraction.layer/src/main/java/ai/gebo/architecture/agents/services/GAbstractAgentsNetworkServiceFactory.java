package ai.gebo.architecture.agents.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class GAbstractAgentsNetworkServiceFactory<InputType, OutputType, ServiceType extends IGAgentsNetworkService>
		implements IGAgentsNetworkServiceFactory<InputType, OutputType, ServiceType> {
	private static final String NO_OUTPUT_NODES_COHERENT_WITH_OUTPUT_TYPE = "No output nodes coherent with outputType ";
	private static final String NO_INPUT_NODES_COHERENT_WITH_INPUT_TYPE = "No input nodes coherent with inputType ";
	private static final String THIS_NETWORK_NULL_AGENTS_OR_IS_NULL = "This network null agents or is null";
	private static final String THIS_NETWORK_HAS_ZERO_AGENTS = "This network has ZERO agents";
	protected final String id;
	protected final String description;
	protected final Class<ServiceType> serviceType;
	protected final IAgentRoleDao agentRoleDao;
	protected final IGAgentServiceRuntimeDao agentServiceRuntimeDao;
	protected final IAgentConfigDao agentConfigDao;

	@Override
	public boolean canHandle(Class<IGAgentsNetworkService> agentNetworkService) {

		return agentNetworkService.isAssignableFrom(serviceType) && serviceType.isAssignableFrom(agentNetworkService);
	}

	@Override
	public ServiceType create(GAgentsNetwork network, INotificationSink notificationSink, Class<InputType> inputType,
			Class<OutputType> outputType, ReactiveIdentityUtil runAs) throws NetworkOfAgentsException {
		if (network != null && network.getAgents() != null) {
			if (network.getAgents().isEmpty())
				throw new NetworkOfAgentsException(THIS_NETWORK_HAS_ZERO_AGENTS);
		} else
			throw new NetworkOfAgentsException(THIS_NETWORK_NULL_AGENTS_OR_IS_NULL);
		Map<String, RuntimeAgentInfos> agentsCache = new HashMap<String, RuntimeAgentInfos>();
		List<RuntimeAgentInfos> inputNode = new ArrayList<RuntimeAgentInfos>(),
				outputNode = new ArrayList<RuntimeAgentInfos>();
		for (AgentNetworkParticipant agent : network.getAgents()) {
			RuntimeAgentInfos agentInfos = this.createRuntimeAgentInfos(network, agent);
			agentsCache.put(agent.getNetworkAgentName(), agentInfos);
			if (agent.isInputNode()) {
				inputNode.add(agentInfos);
			}
			if (agent.isOutputNode()) {
				outputNode.add(agentInfos);
			}
		}
		// at least one input node must be coherent with the inputType
		inputNode.stream().filter(x -> x.getService().getInputType().isAssignableFrom(inputType)).findFirst()
				.orElseThrow(() -> new NetworkOfAgentsException(
						NO_INPUT_NODES_COHERENT_WITH_INPUT_TYPE + inputType.getName()));
		// at least one input node must be coherent with the outputType
		outputNode.stream().filter(x -> outputType.isAssignableFrom(x.getService().getOutputType())).findFirst()
				.orElseThrow(() -> new NetworkOfAgentsException(
						NO_OUTPUT_NODES_COHERENT_WITH_OUTPUT_TYPE + outputType.getName()));

		return createAgentsNetworkService(network, notificationSink, inputType, outputType, runAs, agentsCache);
	}

	protected abstract ServiceType createAgentsNetworkService(GAgentsNetwork network,
			INotificationSink notificationSink, Class<InputType> inputType, Class<OutputType> outputType,
			ReactiveIdentityUtil runAs, Map<String, RuntimeAgentInfos> agentsCache) throws NetworkOfAgentsException;

	protected RuntimeAgentInfos createRuntimeAgentInfos(GAgentsNetwork network, AgentNetworkParticipant agent)
			throws NetworkOfAgentsException {
		GAgentConfig config = agentConfigDao.findByCode(agent.getAgentConfigCode());
		if (config == null)
			throw new NetworkOfAgentsException(
					"The agent config code:" + agent.getAgentConfigCode() + " does not exist");
		IGGenericAgentService service = agentServiceRuntimeDao.findByCode(config.getAgentServiceId());
		if (service instanceof IGNetworkAgentService networkService) {
			return new RuntimeAgentInfos(networkService, config, agent, 0, 0);
		} else
			throw new NetworkOfAgentsException(
					"The service with code:" + config.getAgentServiceId() + " is not an IGAgentsNetworkService");

	}

}
