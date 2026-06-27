package ai.gebo.llms.agent.standard.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.GBaseRoutingNetworkAgentService;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.security.services.IGSecurityService;

@Service
public class DefaultControllerNetworkAgentService extends GBaseRoutingNetworkAgentService<String, Void> {

	public static final String REQUIRED_AGENT_COMPLETENESS_TEMPLATE_PARAM = "REQUIRED_AGENT_COMPLETENESS";
	private static final String CONTROLLER_AND_COORDINATOR_AGENT = "Controller and coordinator agent";
	public static final String CONTROLLER_AGENT = "controllerAgent";

	public DefaultControllerNetworkAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				CONTROLLER_AGENT, CONTROLLER_AND_COORDINATOR_AGENT, String.class, Void.class, rendererFactory);

	}

	@Override
	protected <InputType, OutputType> List<Map<String, Object>> createAgentTemplateParams(GPromptTemplateConfig prompt,
			GAgentsNetwork network, GAgentRole agentRole, AgentNetworkParticipant contextAgentPersona,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<InputType, OutputType> mySessionContext, Object input,
			IGAgentsNetworkRuntimeDao agentsDao, int actualContributionNr, int tokenBudget, boolean splitByBudget) {

		List<Map<String, Object>> output = super.createAgentTemplateParams(prompt, network, agentRole,
				contextAgentPersona, session, mySessionContext, input, agentsDao, actualContributionNr, tokenBudget,
				splitByBudget);
		DeliverableIntent actualUserIntent = (DeliverableIntent) session.getEnvironment()
				.get(StandardAgentsNetworkEnvironmentEntries.USER_INTENT);
		if (actualUserIntent == null)
			actualUserIntent = DeliverableIntent.SUMMARY;

		return output.stream().map(cloneWithIntent(actualUserIntent)).toList();
	}

	static Function<Map<String, Object>, Map<String, Object>> cloneWithIntent(DeliverableIntent actualUserIntent) {
		Function<Map<String, Object>, Map<String, Object>> clone = (map) -> {
			Map<String, Object> targetMap = new HashMap<String, Object>();
			if (map != null) {
				targetMap.putAll(map);
			}
			targetMap.put(REQUIRED_AGENT_COMPLETENESS_TEMPLATE_PARAM,
					actualUserIntent.name() + ": " + actualUserIntent.getAgentDeliverableCompleteness());
			return targetMap;
		};
		return clone;
	}

}
