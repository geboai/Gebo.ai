package ai.gebo.architecture.agents.services;

import java.util.List;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.security.services.ReactiveIdentityUtil;

public interface IGNetworkAgentService<InputType, OutputType> extends IGGenericAgentService {
	public Class<InputType> getInputType();

	public Class<OutputType> getOutputType();

	public List<AgentsExchangeMessage<OutputType>> onMessage(IChatRequestContext chatRequestContext, GAgentConfig config,
			AgentsExchangeMessage<InputType> msg, GAgentsNetwork network,
			AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink, AgentsCollaborationSessionContext session, AgentPrivateSessionContext<InputType, OutputType> mySessionContext, ReactiveIdentityUtil runAs, IGAgentsNetworkRuntimeDao agentsDao) throws LLMConfigException, AgentException;
}
