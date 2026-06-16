package ai.gebo.llms.agent.chat.service.impl;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.GAbstractReactiveOutputAgentsNetworkService;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactory.AdapterWithFlux;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.security.services.ReactiveIdentityUtil;

public class GReactiveChatAgentsNetworkService
		extends GAbstractReactiveOutputAgentsNetworkService<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope>
		implements IGReactiveChatAgentsNetworkService {

	private static final String NETWORK_SERVICE_DESCRIPTION = "Reactive Chat agents network service";
	public static final String REACTIVE_CHAT_AGENTS_NETWORK_SERVICE = "ReactiveChatAgentsNetworkService";

	public GReactiveChatAgentsNetworkService(IGAgentServiceRuntimeDao agentsServicesRepository, IAgentRoleDao rolesDao,
			IGeboThreadManager threadManager, GAgentsNetwork network, INotificationSink notificationSink,
			Class<GeboChatMessageEnvelope> outputType, ReactiveIdentityUtil runAs, IGAgentsNetworkRuntimeDao agentsDao,
			AdapterWithFlux<?, GeboChatMessageEnvelope> adapterWithFlux) {
		super(agentsServicesRepository, rolesDao, threadManager, network, notificationSink, outputType, runAs,
				agentsDao, adapterWithFlux);

	}

	@Override
	protected <OutputType> OutputType compose(OutputType actualOutput, OutputType incremental) {

		return null;
	}

	@Override
	public void dispose() {

	}

	@Override
	public String getId() {

		return REACTIVE_CHAT_AGENTS_NETWORK_SERVICE;
	}

	@Override
	public String getDescription() {

		return NETWORK_SERVICE_DESCRIPTION;
	}

}
