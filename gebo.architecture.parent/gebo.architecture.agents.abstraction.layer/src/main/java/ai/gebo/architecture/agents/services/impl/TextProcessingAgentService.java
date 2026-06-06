package ai.gebo.architecture.agents.services.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.repository.GAgentConfigRepository;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.security.services.IGSecurityService;

@Service
public class TextProcessingAgentService extends GBaseNetworkAgentService<String, String> {

	public TextProcessingAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			GAgentConfigRepository configsRepository, IGSecurityService securityService, IAgentRoleDao agentRoleDao) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, configsRepository, securityService, agentRoleDao,
				String.class, String.class, "textProcessingAgentService",
				"Generical agent that receive text messages and respondes back with text messages");
	}
}
