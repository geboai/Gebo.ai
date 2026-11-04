package ai.gebo.architecture.agents.services;

import java.util.List;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;

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
public interface IGAgentsSuspensionHandler {
	/**********************************************************************************************************
	 * Initializes the handler for network of agent execution
	 * 
	 * @param networkExecutionId
	 * @param agentsNetwork
	 * @throws AgentException
	 */
	public void initialize(String networkExecutionId, GAgentsNetwork agentsNetwork) throws AgentException;

	/********************************************************************************************************************
	 * Called by the agent asking for suspension
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
}
