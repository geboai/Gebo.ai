package ai.gebo.architecture.agents.services;

import java.util.List;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsNetwork;
import ai.gebo.architecture.agents.model.AgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.security.services.ReactiveIdentityUtil;

public interface IGNetworkAgentService<InputType, OutputType> extends IGGenericAgentService {
	public Class<InputType> getInputType();

	public Class<OutputType> getOutputType();

	public List<AgentsExchangeMessage<OutputType>> onMessage(GAgentConfig config, AgentsExchangeMessage<InputType> msg,
			AgentsNetwork network, AgentNetworkParticipant contextAgentPersona,
			AgentsCollaborationSessionContext session, AgentPrivateSessionContext mySessionContext) throws LLMConfigException, AgentException;
}
