package ai.gebo.architecture.agents.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.architecture.agents.model.TargetAgentEnvelope;
import ai.gebo.architecture.agents.services.impl.ScheduleTargetAgentEnvelope;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.Getter;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;

@Getter
public class GBaseRoutingNetworkAgentService<InputType, OutputType>
		extends GAbstractGenericalNetworkAgentService<InputType, OutputType>
		implements IGRoutingNetworkAgentService<InputType, OutputType> {

	private final String id;
	private final String description;
	private final Class<InputType> inputType;
	private final Class<OutputType> outputType;

	public GBaseRoutingNetworkAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder, String id,
			String description, Class<InputType> inputType, Class<OutputType> outputType,
			IGDocumentContentRendererProvider rendererFactory) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				rendererFactory);
		this.id = id;
		this.description = description;
		this.inputType = inputType;
		this.outputType = outputType;
	}

	@Override
	public String getId() {

		return id;
	}

	@Override
	public String getDescription() {

		return description;
	}

	@Override
	public AgentCapabilities getAgentCapabilities(GAgentConfig agentConfig) {
		AgentCapabilities capabilities = super.getAgentCapabilities(agentConfig);
		capabilities.addCapability(
				"Analyse the user request together with the current shared context and decide which of the reachable agents must act");
		capabilities.addCapability(
				"Send each selected agent a tailored command and keep iterating until the gathered contributions are sufficient to produce the answer");
		return capabilities;
	}

	@Override
	public Class<InputType> getInputType() {

		return inputType;
	}

	@Override
	public Class<OutputType> getOutputType() {

		return outputType;
	}

	@Override
	public List<AgentsExchangeMessage<OutputType>> onMessage(IChatRequestContext chatRequestContext,
			GAgentConfig config, AgentsExchangeMessage<InputType> msg, int actualContributionNr, GAgentsNetwork network,
			AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<InputType, OutputType> mySessionContext, ReactiveIdentityUtil runAs,
			IGAgentsNetworkRuntimeDao agentsDao) throws LLMConfigException, AgentException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin onMessage(...) routing agent id:" + getId() + " persona:"
					+ (contextAgentPersona != null ? contextAgentPersona.getAgentContextualName() : null)
					+ " contributionNr:" + actualContributionNr + " fromAgent:" + msg.getFromAgent());
		}
		final ToolCallsListener callsListener = new ToolCallsListener();
		final IGConfigurableChatModel agentModel = getAgentModel(config, callsListener, runAs);
		GAgentRole agentRole = this.agentRoleDao.findByCode(config.getAgentRoleCode());
		GPromptTemplateConfig prompt = resolvePrompt(config.getCustomLoopPrompt(), config.getMainLoopPromptUseCode(),
				false);
		List<String> toCoordinate = contextAgentPersona.getCommunicationList();
		if (toCoordinate == null || toCoordinate.isEmpty()) {
			throw new AgentException("The routing agent: " + contextAgentPersona.getAgentContextualName()
					+ " has no agents to communicate with");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Routing agent can coordinate " + toCoordinate.size() + " peer(s): " + toCoordinate);
		}
		List<RuntimeAgentInfos> peers = new ArrayList<>();
		int tokenBudget = (agentModel.getContextLength() - prompt.getTokensSize()) * 2 / 3;
		Map<String, Object> params = createAgentTemplateParams(prompt, network, agentRole, contextAgentPersona, session,
				mySessionContext, msg.getPayload(), agentsDao, actualContributionNr, tokenBudget);
		final boolean formatDeclared = isPlaceholderDeclared(prompt, AgentPromptTemplateParams.FORMAT_TEMPLATE_PARAM);
		Map<String, Class<?>> checkTypesMap = new HashMap<>();
		Map<String, Class<?>> typesMap = new HashMap<>();
		for (String coordAgent : toCoordinate) {
			RuntimeAgentInfos agentData = agentsDao.findAgentByCode(coordAgent);
			peers.add(agentData);
			// A routed message is the peer's input (the command it consumes), so the
			// envelope command data must be typed on the peer's input type.
			Class<?> peerInputType = agentData.getService().getInputType();
			checkTypesMap.put(coordAgent, peerInputType);
			if (formatDeclared) {
				TypeDescription.Generic generic = TypeDescription.Generic.Builder
						.parameterizedType(TargetAgentEnvelope.class, peerInputType).build();
				Class<?> dynamicType = new ByteBuddy().subclass(generic).make().load(getClass().getClassLoader())
						.getLoaded();
				typesMap.put(coordAgent, dynamicType);
			}
		}
		if (formatDeclared) {
			params.put(AgentPromptTemplateParams.FORMAT_TEMPLATE_PARAM, super.buildRootJsonSchema(typesMap));
		}
		Map<String, Object> populated = (Map) agentModel.structuredResponse(prompt, params, chatRequestContext,
				LinkedHashMap.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Routing LLM produced " + (populated != null ? populated.size() : 0)
					+ " target agent entr(ies): " + (populated != null ? populated.keySet() : null));
		}
		TreeMap<Integer, List<TargetAgentEnvelope<?>>> messagesInOrder = new TreeMap<>();
		List<TargetAgentEnvelope<?>> toBeScheduled = new ArrayList<>();
		if (populated != null && !populated.isEmpty()) {
			for (Map.Entry<String, Object> entry : populated.entrySet()) {
				String targetAgent = entry.getKey();
				// structuredResponse binds the map values as raw maps (the parse target is
				// LinkedHashMap and the per-agent envelope types live only in the JSON schema,
				// which Jackson does not consult). Re-bind each value to the reified
				// TargetAgentEnvelope<peerInputType> built earlier so commandData is typed and
				// the assignability check below is meaningful.
				Class<?> envelopeType = typesMap.get(targetAgent);
				if (envelopeType == null || entry.getValue() == null) {
					continue;
				}
				TargetAgentEnvelope<?> agentEnvelope = null;
				try {
					agentEnvelope = (TargetAgentEnvelope<?>) objectMapper.convertValue(entry.getValue(), envelopeType);
				} catch (IllegalArgumentException e) {
					LOGGER.error(
							"For agent:" + targetAgent + " the command could not be bound to " + envelopeType.getName(),
							e);
					continue;
				}
				if (agentEnvelope != null && agentEnvelope.getCommandData() != null) {
					if (checkTypesMap.containsKey(targetAgent) && checkTypesMap.get(targetAgent)
							.isAssignableFrom(agentEnvelope.getCommandData().getClass())) {
						agentEnvelope.setAgentId(targetAgent);
						toBeScheduled.add(agentEnvelope);
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Scheduling command for agent:" + targetAgent + " deliveryOrder:"
									+ agentEnvelope.getDeliveryOrder());
						}
					} else {
						LOGGER.error("For agent:" + targetAgent + " the wrong type has been generated");
					}
				}

			}
		}
		List<AgentsExchangeMessage<?>> out = new ArrayList<>();
		TreeMap<Integer, List<TargetAgentEnvelope<?>>> scheduled = ScheduleTargetAgentEnvelope
				.normalizeDeliveryPlan(toBeScheduled);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Normalized delivery plan into " + scheduled.size() + " delivery level(s) from "
					+ toBeScheduled.size() + " scheduled envelope(s)");
		}
		for (List<TargetAgentEnvelope<?>> row : scheduled.values()) {
			for (TargetAgentEnvelope<?> d : row) {
				AgentsExchangeMessage<?> _msg = new AgentsExchangeMessage(session.getId(),
						MessageSemantic.EXECUTE_AND_SHARE_RESULT, contextAgentPersona.getAgentContextualName(),
						agentRole, d.getAgentId(), d.getCommandData(), d.getDeliveryOrder());
				out.add(_msg);
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End onMessage(...) routing agent id:" + getId() + " emitting " + out.size()
					+ " outbound message(s)");
		}
		return new ArrayList(out);
	}

}
