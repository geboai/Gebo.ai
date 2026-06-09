package ai.gebo.architecture.agents.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.AgentsNetwork;
import ai.gebo.architecture.agents.model.AgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.architecture.agents.model.TargetAgentEnvelope;
import ai.gebo.architecture.agents.repository.GAgentConfigRepository;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGRoutingNetworkAgentService;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;

public class GBaseRoutingNetworkAgentService<InputType, OutputType>
		extends GAbstractGenericalNetworkAgentService<InputType, OutputType>
		implements IGRoutingNetworkAgentService<InputType, OutputType> {
	private final String id;
	private final String description;
	private final Class<InputType> inputType;
	private final Class<OutputType> outputType;

	public GBaseRoutingNetworkAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			GAgentConfigRepository configsRepository, IGSecurityService securityService, IAgentRoleDao agentRoleDao,
			String id, String description, Class<OutputType> outputType, Class<InputType> inputType) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, configsRepository, securityService, agentRoleDao);
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
	public Class<InputType> getInputType() {

		return inputType;
	}

	@Override
	public Class<OutputType> getOutputType() {

		return outputType;
	}

	@Override
	public List<AgentsExchangeMessage<OutputType>> onMessage(GAgentConfig config, AgentsExchangeMessage<InputType> msg,
			AgentsNetwork network, IGAgentsNetworkRuntimeDao agentsDao, AgentNetworkParticipant contextAgentPersona,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<InputType, OutputType> mySessionContext, ReactiveIdentityUtil runAs)
			throws LLMConfigException, AgentException {
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
		List<RuntimeAgentInfos> peers = new ArrayList<>();
		Map<String, Object> params = createAgentTemplateParams(network, agentRole, session, mySessionContext, msg);
		Map<String, Class<?>> checkTypesMap = new HashMap<>();
		Map<String, Class<?>> typesMap = new HashMap<>();
		for (String coordAgent : toCoordinate) {
			RuntimeAgentInfos agentData = agentsDao.findAgentByCode(coordAgent);
			peers.add(agentData);
			Class<?> outputType = agentData.getService().getOutputType();
			checkTypesMap.put(coordAgent, outputType);
			TypeDescription.Generic generic = TypeDescription.Generic.Builder
					.parameterizedType(TargetAgentEnvelope.class, outputType).build();
			Class<?> dynamicType = new ByteBuddy().subclass(generic).make().load(getClass().getClassLoader())
					.getLoaded();
			typesMap.put(coordAgent, dynamicType);
		}
		params.put("format", super.buildRootJsonSchema(typesMap));
		Map<String, Object> populated = (Map) agentModel.structuredResponse(prompt, params, IChatRequestContext.of(""),
				LinkedHashMap.class);
		TreeMap<Integer, List<TargetAgentEnvelope<?>>> messagesInOrder = new TreeMap<>();
		List<TargetAgentEnvelope<?>> toBeScheduled = new ArrayList<>();
		if (populated != null && !populated.isEmpty()) {
			for (Map.Entry<String, Object> entry : populated.entrySet()) {
				String targetAgent = entry.getKey();
				if (entry.getValue() instanceof TargetAgentEnvelope agentEnvelope
						&& agentEnvelope.getCommandData() != null) {
					if (checkTypesMap.containsKey(targetAgent) && checkTypesMap.get(targetAgent)
							.isAssignableFrom(agentEnvelope.getCommandData().getClass())) {
						agentEnvelope.setAgentId(targetAgent);
						toBeScheduled.add(agentEnvelope);
					} else {
						LOGGER.error("For agent:" + targetAgent + " the wrong type has been generated");
					}
				}

			}
		}
		List<AgentsExchangeMessage<?>> out = new ArrayList<>();
		TreeMap<Integer, List<TargetAgentEnvelope<?>>> scheduled = ScheduleTargetAgentEnvelope
				.normalizeDeliveryPlan(toBeScheduled);
		for (List<TargetAgentEnvelope<?>> row : scheduled.values()) {
			for (TargetAgentEnvelope<?> d : row) {
				AgentsExchangeMessage<?> _msg = new AgentsExchangeMessage(session.getId(),
						MessageSemantic.EXECUTE_AND_SHARE_RESULT, contextAgentPersona.getAgentContextualName(),
						agentRole, d.getAgentId(), d.getCommandData(), d.getDeliveryOrder());
				out.add(msg);
			}
		}
		return new ArrayList(out);
	}

}
