package ai.gebo.llms.agent.chat.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.architecture.agents.services.GAbstractReactiveOutputAgentsNetworkServiceFactory;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentServiceRuntimeDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactory.AdapterWithFlux;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkService;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkServiceFactory;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.security.services.ReactiveIdentityUtil;

@Service
public class GReactiveChatAgentsNetworkServiceFactoryImpl extends
		GAbstractReactiveOutputAgentsNetworkServiceFactory<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, IGReactiveChatAgentsNetworkService>
		implements IGReactiveChatAgentsNetworkServiceFactory {
	private static final String _REACTIVE_CHAT_AGENTS_NETWORK_DESCRIPTION = "Default reactive chat network of agents";
	public static final String REACTIVE_CHAT_AGENTS_NETWORK = "REACTIVE_CHAT_AGENTS_NETWORK";
	private final IGeboThreadManager threadManager;

	public GReactiveChatAgentsNetworkServiceFactoryImpl( IAgentRoleDao agentRoleDao,
			IGAgentServiceRuntimeDao agentServiceRuntimeDao, IAgentConfigDao agentConfigDao,
			IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern reactiveAgentAdapterFactoryRepo,
			IGeboThreadManager threadManager) {
		super(REACTIVE_CHAT_AGENTS_NETWORK, _REACTIVE_CHAT_AGENTS_NETWORK_DESCRIPTION,
				IGReactiveChatAgentsNetworkService.class, agentRoleDao, agentServiceRuntimeDao, agentConfigDao,
				reactiveAgentAdapterFactoryRepo);
		this.threadManager = threadManager;

	}

	@Override
	protected IGReactiveChatAgentsNetworkService createAgentsNetworkService(GAgentsNetwork network,
			INotificationSink notificationSink, Class<ChatPipelineExecutionRuntimeData> inputType,
			Class<GeboChatMessageEnvelope> outputType, ReactiveIdentityUtil runAs,
			Map<String, RuntimeAgentInfos> agentsCache, AdapterWithFlux adapter) {

		return new GReactiveChatAgentsNetworkService(agentServiceRuntimeDao, agentRoleDao, threadManager, network,
				notificationSink, inputType, outputType, runAs, IGAgentsNetworkRuntimeDao.of(agentsCache), adapter);

	}

}
