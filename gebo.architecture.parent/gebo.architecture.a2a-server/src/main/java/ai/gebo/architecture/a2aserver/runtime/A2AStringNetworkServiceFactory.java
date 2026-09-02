package ai.gebo.architecture.a2aserver.runtime;

import java.util.Map;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.architecture.agents.services.GAbstractReactiveOutputAgentsNetworkServiceFactory;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactory.AdapterWithFlux;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.security.services.ReactiveIdentityUtil;

/**
 * Factory that allocates and runs an {@link A2AStringNetworkService}. Registered
 * as a bean so it is discoverable through
 * {@code IGAgentsNetworkServiceFactoryRepositoryPattern}; the base class performs
 * the participant &rarr; runtime-agent allocation, and this subclass only supplies
 * the concrete text-typed service (mirrors
 * {@code GReactiveChatAgentsNetworkServiceFactoryImpl}).
 */
@Service
public class A2AStringNetworkServiceFactory extends
		GAbstractReactiveOutputAgentsNetworkServiceFactory<String, String, A2AStringNetworkService> {

	public static final String FACTORY_ID = "A2A_STRING_NETWORK";
	private static final String FACTORY_DESCRIPTION = "String-to-string network of agents factory for A2A export";

	private final IGeboThreadManager threadManager;

	public A2AStringNetworkServiceFactory(
			IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern reactiveAgentAdapterFactoryRepo,
			IGeboThreadManager threadManager, IGRuntimeBinder runtimeBinder) {
		super(FACTORY_ID, FACTORY_DESCRIPTION, A2AStringNetworkService.class, reactiveAgentAdapterFactoryRepo,
				runtimeBinder);
		this.threadManager = threadManager;
	}

	@Override
	protected A2AStringNetworkService createAgentsNetworkService(GAgentsNetwork network,
			INotificationSink notificationSink, Class<String> inputType, Class<String> outputType,
			ReactiveIdentityUtil runAs, Map<String, RuntimeAgentInfos> agentsCache, AdapterWithFlux adapter) {
		IGAgentServiceRuntimeDao agentServiceRuntimeDao = runtimeBinder
				.getImplementationOf(IGAgentServiceRuntimeDao.class);
		IAgentRoleDao agentRoleDao = runtimeBinder.getImplementationOf(IAgentRoleDao.class);
		return new A2AStringNetworkService(agentServiceRuntimeDao, agentRoleDao, threadManager, network,
				notificationSink, inputType, outputType, runAs, IGAgentsNetworkRuntimeDao.of(agentsCache), adapter);
	}

	@Override
	public Class<String> getInputType() {
		return String.class;
	}

	@Override
	public Class<String> getOutputType() {
		return String.class;
	}
}
