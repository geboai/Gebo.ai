package ai.gebo.architecture.agents.services.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.services.GBaseTaskPerformerNetworkAgentService;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.security.services.IGSecurityService;

@Service
public class TextProcessingTaskPerformerAgentService extends GBaseTaskPerformerNetworkAgentService<String, String> {

	private static final String GENERICAL_AGENT_THAT_RECEIVE_TEXT_MESSAGES_AND_RESPONDES_BACK_WITH_TEXT_MESSAGES = "Generical agent that receive text messages and respondes back with text messages";
	public static final String TEXT_PROCESSING_AGENT_SERVICE = "textProcessingAgentService";

	public TextProcessingTaskPerformerAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				String.class, String.class, TEXT_PROCESSING_AGENT_SERVICE,
				GENERICAL_AGENT_THAT_RECEIVE_TEXT_MESSAGES_AND_RESPONDES_BACK_WITH_TEXT_MESSAGES, rendererFactory);

	}

}
