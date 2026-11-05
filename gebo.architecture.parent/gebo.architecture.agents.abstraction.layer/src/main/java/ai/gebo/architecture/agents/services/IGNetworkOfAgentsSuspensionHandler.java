package ai.gebo.architecture.agents.services;

import java.util.List;
import java.util.Map;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;

/****************************************************************************************************
 * This interface is used during the execution of a network of agents to permit
 * an agent itself or an agent tool, to command the freezing of the state of the
 * agent itself (memorizing some ad hoc informations to be respawned on next
 * execution) and collaboratively with the rest of agents with their status (not
 * yet executed, messages to be received or just executed with generated
 * messages not yet delivered or waiting for a condition asking suspension)
 * being saved. When conditions are satisfied, the network will be "respawn",
 * their agent collaboration session taken alive, their private state taken
 * alive, suspended agents being re-executed with verified conditions being
 * notified and messages not yet delivered are delivered to receiving agents
 * that will cause their execution.
 * 
 * THE ALGORITHM TO SUSPEND THE NETWORK IS A VERSION OF THE Chandy-Lamport
 * ALGORITHM FOR CUT ON DISTRIBUTED SYSTEMS
 */
public interface IGNetworkOfAgentsSuspensionHandler {
	/**********************************************************************************************************
	 * Initializes the handler for network of agent execution, with the input the
	 * network was given: it is part of the cut, since the suspension may be asked
	 * by the first agent to execute, before anything else of the run exists; and
	 * with the request context of the run, whose conversation and request the
	 * block is bound to (null outside a chat).
	 * 
	 * @param agentsNetwork
	 * @param networkInput       the input of executeNetwork(...)
	 * @param chatRequestContext the request context of executeNetwork(...)
	 * @throws AgentException
	 */
	public void initialize(GAgentsNetwork agentsNetwork, Object networkInput, IChatRequestContext chatRequestContext)
			throws AgentException;

	/********************************************************************************************************************
	 * Called by the agent asking for suspension, on the thread executing its turn
	 * 
	 * @param networkExecutionId
	 * @param agentsNetwork
	 * @param agentConfig
	 * @param participant
	 * @param privateContext
	 * @param waitForCondition
	 * @throws AgentException
	 */
	public void enterSuspensionState(GAgentsNetwork agentsNetwork, GAgentConfig agentConfig,
			AgentNetworkParticipant participant, AgentPrivateSessionContext privateContext,
			AgentSuspendedWaitingCondition waitForCondition) throws AgentException;

	/************************************************************************************************************
	 * When this handler is in suspensionState the network must wait for executing
	 * agent to produce their output, apply eventual modifications to the shared
	 * collaboration object, save messages to be freezed for later delivery in the
	 * mongo db.
	 * 
	 * @param agentsNetwork
	 * @param agentConfig
	 * @param participant
	 * @param privateContext
	 * @param messages
	 * @throws AgentException
	 */
	public void setSuspendingMessagesNotYetDelivered(GAgentsNetwork agentsNetwork, GAgentConfig agentConfig,
			AgentNetworkParticipant participant, AgentPrivateSessionContext privateContext,
			List<AgentsExchangeMessage> messages) throws AgentException;

	/************************************************************************
	 * This will be used inside the Network of agents controlling code to check if
	 * continuing work or wait for agents to complete and apply their output to
	 * shared state when they are contribution kind of messaging or freezing for
	 * later delivery if are input to other agents.
	 * 
	 * @return
	 */
	public boolean isSuspensionState();

	/*****************************************************************************************************************************************
	 * This method will be colled when suspension state is true after all freeze
	 * operations are done to save the shared state of the network elaboration.
	 * maybe it has to be called once, but not shure yet
	 * 
	 * @param sharedContext
	 * @throws AgentException
	 */
	public void setSuspendingNetworkOfAgentsSharedState(AgentsCollaborationSessionContext sharedContext)
			throws AgentException;

	/**********************************************************
	 * This causes the network execution to be declared finished
	 */
	public void networkExecutionFinished();

	public List<AgentSuspendedWaitingCondition> suspensionConditions() throws AgentException;

	/**
	 * Called by the network after an agent's turn, when the run is being suspended:
	 * the message the agent was executing and the number of that turn. The handler
	 * keeps it for the agents that asked to suspend the run, since the respawn
	 * re-executes them with that message; a turn that did not ask is ignored. An
	 * agent asking in a further turn has that message frozen, to be delivered after
	 * its re-execution. Called on the thread that executed the turn.
	 */
	public void setSuspendedAgentTurn(AgentNetworkParticipant participant, AgentsExchangeMessage inputMessage,
			int contributionNr) throws AgentException;

	/**
	 * Called by the network when the run is being suspended, after the agents that
	 * were running ended: the runtime of every agent of the network, its counters
	 * and its private state, which the respawn restores before delivering anything.
	 */
	public void setSuspendingAgentsRuntimeState(List<RuntimeAgentInfos> agentsRuntime) throws AgentException;

	/**
	 * Called by the network at the start of a respawn, after {@link #initialize}:
	 * the block the run is respawned from and the conditions resolved for it, by the
	 * name of the agent that waited for them. The handler is not in suspension
	 * state after this: the respawned run suspends again only when an agent asks.
	 */
	public void respawnFrom(GNetworkOfAgentsSuspension suspension,
			Map<String, List<AgentSuspendedWaitingCondition>> resolvedConditionsByAgent) throws AgentException;

	/**
	 * Asked by a respawned agent, in its own control code: the conditions it waited
	 * for, resolved; empty when it was not waiting or they are not resolved.
	 */
	public List<AgentSuspendedWaitingCondition> resolvedConditionsOf(AgentNetworkParticipant participant);

	/** The id of the suspension being taken, once an agent asked for it; null before. */
	public String suspensionId();
}
