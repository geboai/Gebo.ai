package ai.gebo.architecture.agents.services.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.security.services.IGSecurityService;

@Service
public class TextProcessingRoutingNetworkAgentService extends GBaseRoutingNetworkAgentService<String, Void> {

	private static final String ROUTING_AGENT_THAT_PROCESSES_TEXT_AS_AN_INPUT = "Routing agent that processes text as an input";
	public static final String TEXT_PROCESSING_ROUTING_NETWORK_AGENT_SERVICE = "textProcessingRoutingNetworkAgentService";

	
	public TextProcessingRoutingNetworkAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				TEXT_PROCESSING_ROUTING_NETWORK_AGENT_SERVICE, ROUTING_AGENT_THAT_PROCESSES_TEXT_AS_AN_INPUT,
				String.class, Void.class);

	}

}
