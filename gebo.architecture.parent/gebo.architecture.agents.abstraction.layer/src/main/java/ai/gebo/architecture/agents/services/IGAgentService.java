package ai.gebo.architecture.agents.services;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.security.services.ReactiveIdentityUtil;

public interface IGAgentService<RequestType, ResponseType> extends IGGenericAgentService {
	

	public ResponseType execute(IChatRequestContext chatRequestContext, GAgentConfig agentConfig,
			RequestType request, GAgentsNetwork network, AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink, AgentsCollaborationSessionContext session, AgentPrivateSessionContext<RequestType, ResponseType> privateMemory, ReactiveIdentityUtil runAs) throws AgentException, LLMConfigException;

	

	
}
