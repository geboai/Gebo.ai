package ai.gebo.llms.agent.standard.services;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.impl.GBaseRoutingNetworkAgentService;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.security.services.IGSecurityService;

@Service
public class DefaultControllerNetworkAgentService extends GBaseRoutingNetworkAgentService<String, Void> {

	private static final String CONTROLLER_AND_COORDINATOR_AGENT = "Controller and coordinator agent";
	public static final String CONTROLLER_AGENT = "controllerAgent";

	public DefaultControllerNetworkAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder, IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				CONTROLLER_AGENT, CONTROLLER_AND_COORDINATOR_AGENT, String.class, Void.class, rendererFactory);

	}

}
