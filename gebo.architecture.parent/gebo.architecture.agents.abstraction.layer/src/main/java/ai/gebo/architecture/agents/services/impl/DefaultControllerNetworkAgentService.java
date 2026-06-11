package ai.gebo.architecture.agents.services.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.security.services.IGSecurityService;

@Service
public class DefaultControllerNetworkAgentService extends GBaseRoutingNetworkAgentService<String, Void> {

	private static final String CONTROLLER_AND_COORDINATOR_AGENT = "Controller and coordinator agent";
	public static final String CONTROLLER_AGENT = "controllerAgent";

	public DefaultControllerNetworkAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IAgentConfigDao configsRepository, IGSecurityService securityService, IAgentRoleDao agentRoleDao) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, configsRepository, securityService, agentRoleDao, CONTROLLER_AGENT,
				CONTROLLER_AND_COORDINATOR_AGENT, String.class, Void.class);

	}

}
