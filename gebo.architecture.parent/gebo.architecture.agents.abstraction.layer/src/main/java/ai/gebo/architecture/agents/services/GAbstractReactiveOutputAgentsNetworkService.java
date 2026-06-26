package ai.gebo.architecture.agents.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactory.AdapterWithFlux;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;

public abstract class GAbstractReactiveOutputAgentsNetworkService<InputType, OutputType>
		extends GAbstractAgentsNetworkService<InputType, OutputType>
		implements IGReactiveOutputAgentsNetworkService<InputType, OutputType> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GAbstractReactiveOutputAgentsNetworkService.class);
	private final AdapterWithFlux<?, OutputType> adapterWithFlux;

	public GAbstractReactiveOutputAgentsNetworkService(IGAgentServiceRuntimeDao agentsServicesRepository,
			IAgentRoleDao rolesDao, IGeboThreadManager threadManager, GAgentsNetwork network,
			INotificationSink notificationSink, Class<InputType> inputType, Class<OutputType> outputType,
			ReactiveIdentityUtil runAs, IGAgentsNetworkRuntimeDao agentsDao,
			AdapterWithFlux<?, OutputType> adapterWithFlux) {
		super(agentsServicesRepository, rolesDao, threadManager, network, notificationSink, inputType, outputType,
				runAs, agentsDao);
		this.adapterWithFlux = adapterWithFlux;

	}

	@Override
	public OutputType executeNetwork(IChatRequestContext chatRequestContext, InputType input)
			throws AgentException, LLMConfigException {
		try {
			return super.executeNetwork(chatRequestContext, input);
		} finally {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Completing reactive output sink after executeNetwork(...)");
			}
			this.adapterWithFlux.getSink().tryEmitComplete();
		}
	}

	@Override
	public Flux<OutputType> getFlux() {

		return adapterWithFlux.getFlux();
	}

}
