package ai.gebo.llms.agent.standard.services;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.security.services.IGSecurityService;

/**
 * String-in/String-out {@link GBaseToolCallingNetworkAgent} exposed as a Spring
 * bean: a text-driven agent that receives a text instruction, runs the
 * tool-calling loop over the tools enabled by its configuration, and answers
 * back with text.
 */
@Service
public class StringToStringToolCallingNetworkAgent extends GBaseToolCallingNetworkAgent<String, String> {

	private static final String TOOL_CALLING_AGENT_THAT_RECEIVES_TEXT_INSTRUCTIONS_CALLS_TOOLS_AND_RESPONDS_WITH_TEXT = "Tool-calling agent that receives text instructions, invokes the enabled tools and responds back with text";
	public static final String STRING_TO_STRING_TOOL_CALLING_AGENT_SERVICE = "toolCallingAgentService";

	public StringToStringToolCallingNetworkAgent(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				String.class, String.class, STRING_TO_STRING_TOOL_CALLING_AGENT_SERVICE,
				TOOL_CALLING_AGENT_THAT_RECEIVES_TEXT_INSTRUCTIONS_CALLS_TOOLS_AND_RESPONDS_WITH_TEXT, rendererFactory);
	}

}
