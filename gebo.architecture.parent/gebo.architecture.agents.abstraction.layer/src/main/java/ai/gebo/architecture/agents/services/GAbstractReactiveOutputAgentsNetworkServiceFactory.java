package ai.gebo.architecture.agents.services;

import java.util.List;
import java.util.Map;

import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactory.AdapterWithFlux;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.Getter;

public abstract class GAbstractReactiveOutputAgentsNetworkServiceFactory<InputType, OutputType, ServiceType extends IGReactiveOutputAgentsNetworkService<InputType, OutputType>>
		extends GAbstractAgentsNetworkServiceFactory<InputType, OutputType, ServiceType>
		implements IGAgentsNetworkServiceFactory<InputType, OutputType, ServiceType> {
	private static final String WRONG_REACTIVE_OUTPUTS_NR_EXCEPTION_TEXT = "The GAbstractReactiveOutputAgentsNetworkServiceFactory is thought to work with agents network with a single output node being reactive";
	protected final IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern reactiveAgentAdapterFactoryRepo;

	public GAbstractReactiveOutputAgentsNetworkServiceFactory(String id, String description,
			Class<ServiceType> serviceType,
			IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern reactiveAgentAdapterFactoryRepo,
			IGRuntimeBinder runtimeBinder) {
		super(id, description, serviceType, runtimeBinder);
		this.reactiveAgentAdapterFactoryRepo = reactiveAgentAdapterFactoryRepo;
	}

	@Getter
	protected static class AdapterRuntimeAgentInfos extends RuntimeAgentInfos {
		private final AdapterWithFlux adapterWithFlux;

		public AdapterRuntimeAgentInfos(IGNetworkAgentService service, GAgentConfig config,
				AgentNetworkParticipant networkParticipantConfig, int turnOfExecution,
				int sessionSharedContributionsReadIndex, AdapterWithFlux adapterWithFlux) {
			super(service, config, networkParticipantConfig, turnOfExecution, sessionSharedContributionsReadIndex);
			this.adapterWithFlux = adapterWithFlux;
		}

	}

	@Override
	protected RuntimeAgentInfos createRuntimeAgentInfos(GAgentsNetwork network, AgentNetworkParticipant agent)
			throws NetworkOfAgentsException {
		IGAgentServiceRuntimeDao agentServiceRuntimeDao = runtimeBinder
				.getImplementationOf(IGAgentServiceRuntimeDao.class);
		IAgentConfigDao agentConfigDao = runtimeBinder.getImplementationOf(IAgentConfigDao.class);
		GAgentConfig config = agentConfigDao.findByCode(agent.getAgentConfigCode());
		IGGenericAgentService service = agentServiceRuntimeDao.findByCode(config.getAgentServiceId());
		if (service instanceof IGReactiveAgentService reactiveService) {
			if (!agent.isOutputNode())
				throw new NetworkOfAgentsException("A reactive service can be used only on an outputNode");
			IGReactiveToNetworkAgentAdapterFactory factory = reactiveAgentAdapterFactoryRepo
					.getFactory(reactiveService);
			if (factory == null)
				throw new NetworkOfAgentsException("No IGReactiveToNetworkAgentAdapterFactory found for service id:"
						+ reactiveService.getId() + " of type " + reactiveService.getClass().getName());
			AdapterWithFlux adapter = factory.create(reactiveService);
			AdapterRuntimeAgentInfos agentInfos = new AdapterRuntimeAgentInfos(adapter.getAdapter(), config, agent, 0,
					0, adapter);
			return agentInfos;
		} else
			return super.createRuntimeAgentInfos(network, agent);
	}

	@Override
	protected final ServiceType createAgentsNetworkService(GAgentsNetwork network, INotificationSink notificationSink,
			Class<InputType> inputType, Class<OutputType> outputType, ReactiveIdentityUtil runAs,
			Map<String, RuntimeAgentInfos> agentsCache) throws NetworkOfAgentsException {
		List<AdapterWithFlux> adapters = agentsCache.values().stream()
				.filter(x -> x instanceof AdapterRuntimeAgentInfos)
				.map(x -> ((AdapterRuntimeAgentInfos) x).getAdapterWithFlux()).toList();
		if (adapters.isEmpty() || adapters.size() > 1)
			throw new NetworkOfAgentsException(WRONG_REACTIVE_OUTPUTS_NR_EXCEPTION_TEXT);
		return createAgentsNetworkService(network, notificationSink, inputType, outputType, runAs, agentsCache,
				adapters.get(0));
	}

	protected abstract ServiceType createAgentsNetworkService(GAgentsNetwork network,
			INotificationSink notificationSink, Class<InputType> inputType, Class<OutputType> outputType,
			ReactiveIdentityUtil runAs, Map<String, RuntimeAgentInfos> agentsCache, AdapterWithFlux adapter);

}
