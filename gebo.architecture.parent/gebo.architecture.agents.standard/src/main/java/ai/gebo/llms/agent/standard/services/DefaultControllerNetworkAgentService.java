package ai.gebo.llms.agent.standard.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.GBaseRoutingNetworkAgentService;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;

@Service
public class DefaultControllerNetworkAgentService extends GBaseRoutingNetworkAgentService<String, Void> {

	public static final String REQUIRED_AGENT_COMPLETENESS_TEMPLATE_PARAM = "REQUIRED_AGENT_COMPLETENESS";
	public static final String CURRENT_CONTROLLER_CYCLE_TEMPLATE_PARAM = "CURRENT_CONTROLLER_CYCLE";
	public static final String MAX_CONTROLLER_CYCLES_TEMPLATE_PARAM = "MAX_CONTROLLER_CYCLES";
	/**
	 * Cycle cap used when the controller participant declares no invocation limit.
	 */
	static final int DEFAULT_MAX_CONTROLLER_CYCLES = 4;
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
		final int maxCycles = resolveMaxControllerCycles(contextAgentPersona);
		final int currentCycle = currentControllerCycle(mySessionContext);

		return output.stream().map(enrichWithControlSignals(actualUserIntent, currentCycle, maxCycles)).toList();
	}

	/**
	 * After the routing plan is produced, drive the controller's iteration: a turn
	 * that dispatches only search agents is a GATHER cycle and re-schedules the
	 * controller for another pass; a turn that activates the writer/reporter
	 * FINALIZES and ends the iteration. A self-addressed message rides the engine's
	 * delivery-order drain to re-invoke this controller after the gathered evidence
	 * has been added to the shared context.
	 */
	@Override
	public List<AgentsExchangeMessage<Void>> onMessage(IChatRequestContext chatRequestContext, GAgentConfig config,
			AgentsExchangeMessage<String> msg, int actualContributionNr, GAgentsNetwork network,
			AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink,
			AgentsCollaborationSessionContext session, AgentPrivateSessionContext<String, Void> mySessionContext,
			ReactiveIdentityUtil runAs, IGAgentsNetworkRuntimeDao agentsDao)
			throws LLMConfigException, AgentException {

		final List<AgentsExchangeMessage<Void>> out = super.onMessage(chatRequestContext, config, msg,
				actualContributionNr, network, contextAgentPersona, notificationSink, session, mySessionContext, runAs,
				agentsDao);

		final String writerName = network.getAgents().stream().filter(AgentNetworkParticipant::isOutputNode)
				.map(AgentNetworkParticipant::getNetworkAgentName).findFirst().orElse(null);
		final String selfName = contextAgentPersona.getNetworkAgentName();

		boolean hasWriter = false;
		boolean hasSearch = false;
		int maxOrder = 0;
		for (AgentsExchangeMessage<?> scheduled : out) {
			maxOrder = Math.max(maxOrder, scheduled.getExecutionOrder());
			if (writerName != null && writerName.equals(scheduled.getToAgent())) {
				hasWriter = true;
			} else {
				hasSearch = true;
			}
		}
		final int nextOrder = maxOrder + 1;
		final int maxCycles = resolveMaxControllerCycles(contextAgentPersona);
		final int currentCycle = currentControllerCycle(mySessionContext);
		final boolean budgetRemains = currentCycle < maxCycles;
		final GAgentRole agentRole = agentRoleDao.findByCode(config.getAgentRoleCode());

		if (hasWriter) {
			// FINALIZE: the writer/reporter in the plan will produce the user-facing answer;
			// iteration stops here (its RESPONSE does not re-schedule anyone).
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Controller finalizing at cycle " + currentCycle + "/" + maxCycles);
			}
		} else if (hasSearch && budgetRemains) {
			// GATHER: come back for another cycle once this cycle's searches have run and
			// added their evidence to the shared context, so completeness can be re-judged.
			scheduleRaw(out, new AgentsExchangeMessage(session.getId(), MessageSemantic.EXECUTE_AND_SHARE_RESULT,
					selfName, agentRole, selfName, msg.getPayload(), nextOrder));
			notificationSink.next("Controller is gathering more evidence (cycle " + currentCycle + " of " + maxCycles
					+ ")", INotificationSink.NotificationObject.NotificationType.DEBUG);
		} else if (writerName != null) {
			// Budget exhausted while still gathering, or an empty plan: guarantee the user
			// gets an answer by forcing a final writing pass over whatever was gathered.
			scheduleRaw(out, new AgentsExchangeMessage(session.getId(), MessageSemantic.EXECUTE_AND_SHARE_RESULT,
					selfName, agentRole, writerName, finalWriterFallbackInstruction(msg.getPayload()), nextOrder));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Controller forcing final writing pass at cycle " + currentCycle + "/" + maxCycles
						+ " (no writer in plan)");
			}
		}
		return out;
	}

	Function<Map<String, Object>, Map<String, Object>> enrichWithControlSignals(DeliverableIntent actualUserIntent,
			int currentCycle, int maxCycles) {
		return (map) -> {
			Map<String, Object> targetMap = new HashMap<String, Object>();
			if (map != null) {
				targetMap.putAll(map);
			}
			targetMap.put(REQUIRED_AGENT_COMPLETENESS_TEMPLATE_PARAM,
					actualUserIntent.name() + ": " + actualUserIntent.getAgentDeliverableCompleteness());
			targetMap.put(CURRENT_CONTROLLER_CYCLE_TEMPLATE_PARAM, currentCycle);
			targetMap.put(MAX_CONTROLLER_CYCLES_TEMPLATE_PARAM, maxCycles);
			return targetMap;
		};
	}

	/**
	 * 1-based index of the cycle being planned now. Each controller invocation runs
	 * at a distinct session contribution turn, so the count of turns already
	 * recorded in the controller's private memory is the number of completed cycles.
	 */
	private static int currentControllerCycle(AgentPrivateSessionContext<?, ?> mySessionContext) {
		return mySessionContext.getContributionTurnNumbers().size() + 1;
	}

	private static int resolveMaxControllerCycles(AgentNetworkParticipant persona) {
		if (persona != null && persona.getMaxInvocations() != null && persona.getMaxInvocations() > 0) {
			return persona.getMaxInvocations();
		}
		if (persona != null && persona.getMaxConsecutiveInvocations() != null
				&& persona.getMaxConsecutiveInvocations() > 0) {
			return persona.getMaxConsecutiveInvocations();
		}
		return DEFAULT_MAX_CONTROLLER_CYCLES;
	}

	private static String finalWriterFallbackInstruction(String userRequest) {
		return "Produce the best possible final answer to the user request using ALL the evidence already gathered in "
				+ "the shared context. Do not request further searches. User request: "
				+ (userRequest != null ? userRequest : "");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void scheduleRaw(List<AgentsExchangeMessage<Void>> out, AgentsExchangeMessage message) {
		out.add(message);
	}

}
