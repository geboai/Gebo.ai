package ai.gebo.architecture.agents.services.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.services.GBaseAgentsNetworkToNetworkAgentAdapterService;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactoryRepositoryPattern;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.security.services.IGSecurityService;

@Service
public class GStringToStringAgentsNetworkToNetworkAgentAdapterServiceImpl
		extends GBaseAgentsNetworkToNetworkAgentAdapterService<String, String> {

	private static final String SERVICE_DESCRIPTION = "NetworkAgent adapting a NetworkOfAgents that gets a string and returns a string";
	private static final String SERVICE_ID = "stringToStringAgentsNetworkToNetworkAgentAdapterService";

	public GStringToStringAgentsNetworkToNetworkAgentAdapterServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			IGDocumentContentRendererProvider rendererFactory,
			IGAgentsNetworkServiceFactoryRepositoryPattern factoryRepository, IAgentsNetworkDao agentsNetworkDao) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				rendererFactory, String.class, String.class, SERVICE_ID, SERVICE_DESCRIPTION, factoryRepository,
				agentsNetworkDao);

	}

}
