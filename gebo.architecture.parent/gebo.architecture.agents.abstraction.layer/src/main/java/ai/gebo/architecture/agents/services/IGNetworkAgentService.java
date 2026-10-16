package ai.gebo.architecture.agents.services;

import java.util.List;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.security.services.ReactiveIdentityUtil;

public interface IGNetworkAgentService<InputType, OutputType> extends IGGenericAgentService {
	public Class<InputType> getInputType();

	public Class<OutputType> getOutputType();

	/**
	 * Whether the messages this agent sends to the agents of its communication list
	 * carry its output as their input ({@code EXECUTE_AND_SHARE_RESULT}: a router
	 * dispatching commands, an input adapter forwarding the request), or only share
	 * its output in the session context the other agents read ({@code RESPONSE}: a
	 * performer answering, a searcher returning evidence, a writer reporting). Only in
	 * the first case its output type must be consumable as the receiver's input type.
	 */
	public default boolean isOutputDeliveredAsInput() {
		return true;
	}

	public List<AgentsExchangeMessage<OutputType>> onMessage(IChatRequestContext chatRequestContext, GAgentConfig config,
			AgentsExchangeMessage<InputType> msg, int actualContributionNr,
			GAgentsNetwork network, AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink, AgentsCollaborationSessionContext session, AgentPrivateSessionContext<InputType, OutputType> mySessionContext, ReactiveIdentityUtil runAs, IGAgentsNetworkRuntimeDao agentsDao) throws LLMConfigException, AgentException;
}
