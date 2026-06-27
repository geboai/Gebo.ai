package ai.gebo.llms.agent.standard.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.GBaseRoutingNetworkAgentService;
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

/**
 * Default chat coordinator. The generic gather/finalize iteration lives in
 * {@link GBaseRoutingNetworkAgentService}; this class only contributes the
 * chat-specific pieces: the required-completeness signal derived from the shared
 * {@code USER_INTENT}, and a final writing pass forced onto the report writer
 * when the cycle budget is exhausted.
 */
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
	protected <I, O> List<Map<String, Object>> createAgentTemplateParams(GPromptTemplateConfig prompt,
			GAgentsNetwork network, GAgentRole agentRole, AgentNetworkParticipant contextAgentPersona,
			AgentsCollaborationSessionContext session, AgentPrivateSessionContext<I, O> mySessionContext, Object input,
			IGAgentsNetworkRuntimeDao agentsDao, int actualContributionNr, int tokenBudget, boolean splitByBudget) {
		// Base adds the routing-cycle signals; here we add the required deliverable
		// completeness derived from the shared USER_INTENT.
		List<Map<String, Object>> output = super.createAgentTemplateParams(prompt, network, agentRole,
				contextAgentPersona, session, mySessionContext, input, agentsDao, actualContributionNr, tokenBudget,
				splitByBudget);
		DeliverableIntent actualUserIntent = (DeliverableIntent) session.getEnvironment()
				.get(StandardAgentsNetworkEnvironmentEntries.USER_INTENT);
		if (actualUserIntent == null)
			actualUserIntent = DeliverableIntent.SUMMARY;
		final String completeness = actualUserIntent.name() + ": " + actualUserIntent.getAgentDeliverableCompleteness();
		for (Map<String, Object> window : output) {
			window.put(REQUIRED_AGENT_COMPLETENESS_TEMPLATE_PARAM, completeness);
		}
		return output;
	}

	/**
	 * When the cycle budget is exhausted (or the plan was empty) without the writer
	 * being activated, force a final writing pass so the user always gets an answer.
	 * The report writer consumes a plain-text writing instruction.
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected AgentsExchangeMessage<?> buildFinalizationFallback(AgentsCollaborationSessionContext session,
			AgentNetworkParticipant contextAgentPersona, GAgentRole agentRole, String outputNodeName,
			AgentsExchangeMessage<String> originalMessage, int deliveryOrder) {
		if (outputNodeName == null) {
			return null;
		}
		String userRequest = originalMessage != null ? originalMessage.getPayload() : null;
		return new AgentsExchangeMessage(session.getId(), MessageSemantic.EXECUTE_AND_SHARE_RESULT,
				contextAgentPersona.getNetworkAgentName(), agentRole, outputNodeName,
				finalWriterFallbackInstruction(userRequest), deliveryOrder);
	}

	private static String finalWriterFallbackInstruction(String userRequest) {
		return "Produce the best possible final answer to the user request using ALL the evidence already gathered in "
				+ "the shared context. Do not request further searches. User request: "
				+ (userRequest != null ? userRequest : "");
	}

}
