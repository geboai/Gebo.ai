package ai.gebo.architecture.a2aserver.runtime;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.GAbstractReactiveOutputAgentsNetworkService;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactory.AdapterWithFlux;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.security.services.ReactiveIdentityUtil;

/**
 * A {@code String -> String} network-of-agents service used to run an exported
 * network (or a synthesized single-node agent network) behind an A2A endpoint.
 * <p>
 * It is the text-I/O counterpart of {@code GReactiveChatAgentsNetworkService}: the
 * only registered network service before this was chat-typed
 * ({@code ChatPipelineExecutionRuntimeData -> GeboChatMessageEnvelope}), which is
 * why exporting a network with plain text A2A messages needed an A2A-friendly
 * service/factory. Streaming partials are emitted to the reactive flux; the
 * returned value is the final text, so the latest non-null output wins.
 */
public class A2AStringNetworkService
		extends GAbstractReactiveOutputAgentsNetworkService<String, String> {

	static final String SERVICE_ID = "A2A_STRING_NETWORK_SERVICE";
	private static final String SERVICE_DESCRIPTION = "String-to-string network of agents served over A2A";

	public A2AStringNetworkService(IGAgentServiceRuntimeDao agentsServicesRepository, IAgentRoleDao rolesDao,
			IGeboThreadManager threadManager, GAgentsNetwork network, INotificationSink notificationSink,
			Class<String> inputType, Class<String> outputType, ReactiveIdentityUtil runAs,
			IGAgentsNetworkRuntimeDao agentsDao, AdapterWithFlux<?, String> adapterWithFlux) {
		super(agentsServicesRepository, rolesDao, threadManager, network, notificationSink, inputType, outputType,
				runAs, agentsDao, adapterWithFlux);
	}

	@Override
	protected <OutputType> OutputType compose(OutputType actualOutput, OutputType incremental) {
		return incremental != null ? incremental : actualOutput;
	}

	@Override
	public void dispose() {
	}

	@Override
	public String getId() {
		return SERVICE_ID;
	}

	@Override
	public String getDescription() {
		return SERVICE_DESCRIPTION;
	}
}
