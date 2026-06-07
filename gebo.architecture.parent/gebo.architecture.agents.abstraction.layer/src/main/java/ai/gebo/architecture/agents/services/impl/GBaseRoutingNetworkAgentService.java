package ai.gebo.architecture.agents.services.impl;

import java.util.List;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsNetwork;
import ai.gebo.architecture.agents.model.AgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.repository.GAgentConfigRepository;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGRoutingNetworkAgentService;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.Data;

public class GBaseRoutingNetworkAgentService<InputType, OutputType> extends GAbstractGenericalAgentService
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
			AgentsNetwork network, IGAgentsNetworkRuntimeDao agentsDao,
			AgentNetworkParticipant contextAgentPersona, AgentsCollaborationSessionContext session, AgentPrivateSessionContext mySessionContext, ReactiveIdentityUtil runAs)
			throws LLMConfigException, AgentException {
		final ToolCallsListener callsListener = new ToolCallsListener();
		final IGConfigurableChatModel agentModel = getAgentModel(config, callsListener, null);
		return null;
	}

}
