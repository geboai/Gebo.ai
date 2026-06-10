package ai.gebo.architecture.agents.services.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.repository.AgentConfigRepository;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.security.services.IGSecurityService;

@Service
public class TextProcessingRoutingNetworkAgentService extends GBaseRoutingNetworkAgentService<String, Void> {

	public TextProcessingRoutingNetworkAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IAgentConfigDao configsRepository, IGSecurityService securityService, IAgentRoleDao agentRoleDao) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, configsRepository, securityService, agentRoleDao,
				"textProcessingRoutingNetworkAgentService", "Routing agent that processes text as an input", Void.class,
				String.class);

	}

}
