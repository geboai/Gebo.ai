package ai.gebo.architecture.agents.services;

import java.util.List;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
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
	 * The semantic of the messages this agent produces, the one it builds them with:
	 * <ul>
	 * <li>{@link MessageSemantic#EXECUTE_AND_SHARE_RESULT}: the network delivers each
	 * message to its target agent as its input. A router sends each agent it can talk
	 * to a command typed on that agent's input; a forwarder (e.g. an input adapter)
	 * sends its own output, which must then be consumable as the target's input.</li>
	 * <li>{@link MessageSemantic#RESPONSE}: the message is never delivered, it is
	 * shared in the session context every agent reads (a performer answering, a
	 * searcher returning evidence, a writer reporting); from an output node it is
	 * the network output.</li>
	 * </ul>
	 */
	public default MessageSemantic getProducedMessageSemantic() {
		return MessageSemantic.EXECUTE_AND_SHARE_RESULT;
	}

	public List<AgentsExchangeMessage<OutputType>> onMessage(IChatRequestContext chatRequestContext, GAgentConfig config,
			AgentsExchangeMessage<InputType> msg, int actualContributionNr,
			GAgentsNetwork network, AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink, AgentsCollaborationSessionContext session, AgentPrivateSessionContext<InputType, OutputType> mySessionContext, ReactiveIdentityUtil runAs, IGAgentsNetworkRuntimeDao agentsDao) throws LLMConfigException, AgentException;
}
