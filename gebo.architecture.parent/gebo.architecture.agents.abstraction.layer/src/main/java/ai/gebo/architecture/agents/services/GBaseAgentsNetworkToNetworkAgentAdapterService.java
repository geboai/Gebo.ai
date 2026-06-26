package ai.gebo.architecture.agents.services;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.Getter;

@Getter
public class GBaseAgentsNetworkToNetworkAgentAdapterService<InputType, OutputType>
		extends GAbstractGenericalNetworkAgentService<InputType, OutputType>
		implements IGAgentsNetworkToNetworkAgentAdapterService<InputType, OutputType> {
	private static final String EMPTY_ADAPTED_AGENT_NETWORK_CODE_MSG = "A network adapter configuration cannot have an empty adaptedAgentNetworkCode";
	private static final String EMPTY_NETWORK_SERVICE_CODE_MSG = "A network adapter configuration cannot have an empty agentNetworkServiceCode";
	private static final String OR_OUTPUT_TYPE = " or output type: ";
	private static final String NOT_COHERENT_WITH_INPUT_TYPE = " not coherent with input type:";
	private static final String NETWORK_OF_AGENT = "Network of agent ";
	private static final String THE_NETWORK_OF_AGENT_IS_NOT_ALLOCABLE = "The network of agent is not allocable";
	protected final Class<InputType> inputType;
	protected final Class<OutputType> outputType;
	protected final String id;
	protected final String description;
	protected final IGAgentsNetworkServiceFactoryRepositoryPattern factoryRepository;
	protected final IAgentsNetworkDao agentsNetworkDao;

	public GBaseAgentsNetworkToNetworkAgentAdapterService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			IGDocumentContentRendererProvider rendererFactory, Class<InputType> inputType, Class<OutputType> outputType,
			String id, String description, IGAgentsNetworkServiceFactoryRepositoryPattern factoryRepository,
			IAgentsNetworkDao agentsNetworkDao) {

		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				rendererFactory);
		this.inputType = inputType;
		this.outputType = outputType;
		this.id = id;
		this.description = description;
		this.factoryRepository = factoryRepository;
		this.agentsNetworkDao = agentsNetworkDao;

	}

	@Override
	public List<AgentsExchangeMessage<OutputType>> onMessage(IChatRequestContext chatRequestContext,
			GAgentConfig config, AgentsExchangeMessage<InputType> msg, int actualContributionNr, GAgentsNetwork network,
			AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<InputType, OutputType> mySessionContext, ReactiveIdentityUtil runAs,
			IGAgentsNetworkRuntimeDao agentsDao) throws LLMConfigException, AgentException {
		if (config.getAgentNetworkServiceCode() == null || config.getAgentNetworkServiceCode().trim().length() == 0)
			throw new AgentException(EMPTY_NETWORK_SERVICE_CODE_MSG);
		if (config.getAdaptedAgentNetworkCode() == null || config.getAdaptedAgentNetworkCode().trim().length() == 0)
			throw new AgentException(EMPTY_ADAPTED_AGENT_NETWORK_CODE_MSG);
		IGAgentsNetworkServiceFactory factory = this.factoryRepository.findByCode(config.getAgentNetworkServiceCode());
		GAgentsNetwork adaptedNetwork = agentsNetworkDao.findByCode(config.getAdaptedAgentNetworkCode());
		try {
			IGAgentsNetworkService agentsNetworkService = factory.create(adaptedNetwork, notificationSink, inputType,
					outputType, runAs);
			if (agentsNetworkService.getInputType().isAssignableFrom(inputType)
					&& outputType.isAssignableFrom(agentsNetworkService.getOutputType())) {
				OutputType output = (OutputType) agentsNetworkService.executeNetwork(chatRequestContext,
						msg.getPayload());
				List<AgentsExchangeMessage<OutputType>> outputMessages = new ArrayList<AgentsExchangeMessage<OutputType>>();
				List<String> targetAgents = contextAgentPersona.getCommunicationList();
				for (String target : targetAgents) {
					AgentsExchangeMessage<OutputType> newMsg = AgentsExchangeMessage.of(session, target, output,
							MessageSemantic.RESPONSE);
					outputMessages.add(newMsg);
				}
				return outputMessages;
			} else
				throw new AgentException(NETWORK_OF_AGENT + config.getAdaptedAgentNetworkCode()
						+ NOT_COHERENT_WITH_INPUT_TYPE + inputType.getName() + OR_OUTPUT_TYPE + outputType.getName());

		} catch (NetworkOfAgentsException e) {
			LOGGER.error(THE_NETWORK_OF_AGENT_IS_NOT_ALLOCABLE, e);
			throw new AgentException(THE_NETWORK_OF_AGENT_IS_NOT_ALLOCABLE, e);
		}

	}

}
