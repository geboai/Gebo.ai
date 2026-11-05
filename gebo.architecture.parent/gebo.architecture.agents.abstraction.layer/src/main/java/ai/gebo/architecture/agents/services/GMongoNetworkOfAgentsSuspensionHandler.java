/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.AgentRuntimeState;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.ExchangeMessageRecord;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.FrozenDeliveries;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.SharedState;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.SuspendedAgentState;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;

/**
 * The suspension handler of one run that keeps the cut in Mongo (see
 * {@link GNetworkOfAgentsSuspension}): one instance per run, since the
 * suspension state is the run's. The cut is consolidated in memory as the
 * handler is told it, from the first agent asking to suspend the run to the
 * shared state saved last, and written as one block when the network declares
 * the run finished; a run nobody asked to suspend writes nothing. A block that
 * cannot be written fails the run that was suspending: a suspension whose state
 * is lost must not look taken.
 * <p>
 * On a respawn the handler is given the block the run comes from and the
 * conditions resolved for it: the respawned agents ask it for theirs, and a
 * block this run suspends into says which block it was respawned from.
 * <p>
 * A block is bound to the conversation and the request of the run, and a
 * conversation holds one open block at most: writing a block drops the open ones
 * of its conversation.
 * <p>
 * The agents of a run call it from their own threads, a group of them at a time;
 * the handler is synchronized, so the cut is consistent whoever adds to it.
 */
public class GMongoNetworkOfAgentsSuspensionHandler implements IGNetworkOfAgentsSuspensionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GMongoNetworkOfAgentsSuspensionHandler.class);
	static final String NOT_INITIALIZED = "The suspension handler was not initialized for a network";
	static final String SAVE_FAILED = "The suspension of the run of network could not be saved: ";

	private final GNetworkOfAgentsSuspensionRepository repository;
	private volatile GAgentsNetwork network = null;
	/** The input the network was given, part of the cut. */
	private volatile Object networkInput = null;
	/** The conversation and the request of the run, from its request context; null outside a chat. */
	private volatile String conversationCode = null;
	private volatile String requestId = null;
	private volatile boolean suspensionState = false;
	/** The cut, consolidated from the first agent asking to suspend the run. */
	private GNetworkOfAgentsSuspension suspension = null;
	/** The conditions the suspended agents wait for, as they gave them. */
	private final List<AgentSuspendedWaitingCondition> conditions = new ArrayList<AgentSuspendedWaitingCondition>();
	/** On a respawn: the block the run comes from and the conditions resolved, by agent name. */
	private String respawnedFromSuspensionCode = null;
	private final Map<String, AgentSuspendedWaitingCondition> resolvedConditionsByAgent = new HashMap<String, AgentSuspendedWaitingCondition>();

	public GMongoNetworkOfAgentsSuspensionHandler(GNetworkOfAgentsSuspensionRepository repository) {
		this.repository = repository;
	}

	@Override
	public synchronized void initialize(GAgentsNetwork agentsNetwork, Object networkInput,
			IChatRequestContext chatRequestContext) throws AgentException {
		if (agentsNetwork == null) {
			throw new AgentException(NOT_INITIALIZED);
		}
		this.network = agentsNetwork;
		this.networkInput = networkInput;
		this.conversationCode = chatRequestContext != null ? chatRequestContext.getSessionID() : null;
		this.requestId = chatRequestContext != null ? chatRequestContext.getRequestID() : null;
		this.suspensionState = false;
		this.suspension = null;
		this.conditions.clear();
		this.respawnedFromSuspensionCode = null;
		this.resolvedConditionsByAgent.clear();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Suspension handler initialized for network:" + agentsNetwork.getCode() + " conversation:"
					+ conversationCode + " request:" + requestId);
		}
	}

	@Override
	public synchronized void respawnFrom(GNetworkOfAgentsSuspension suspension,
			Map<String, AgentSuspendedWaitingCondition> resolvedConditionsByAgent) throws AgentException {
		this.respawnedFromSuspensionCode = suspension != null ? suspension.getCode() : null;
		this.resolvedConditionsByAgent.clear();
		if (resolvedConditionsByAgent != null) {
			this.resolvedConditionsByAgent.putAll(resolvedConditionsByAgent);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Suspension handler of network:" + (network != null ? network.getCode() : null)
					+ " respawns from suspension:" + respawnedFromSuspensionCode + " with "
					+ this.resolvedConditionsByAgent.size() + " resolved condition(s) for agent(s): "
					+ this.resolvedConditionsByAgent.keySet());
		}
	}

	@Override
	public Optional<AgentSuspendedWaitingCondition> resolvedConditionOf(AgentNetworkParticipant participant) {
		final String name = participant != null ? participant.getNetworkAgentName() : null;
		if (name == null) {
			return Optional.empty();
		}
		synchronized (this) {
			return Optional.ofNullable(resolvedConditionsByAgent.get(name));
		}
	}

	@Override
	public synchronized void enterSuspensionState(GAgentsNetwork agentsNetwork, GAgentConfig agentConfig,
			AgentNetworkParticipant participant, AgentPrivateSessionContext privateContext,
			AgentSuspendedWaitingCondition waitForCondition) throws AgentException {
		final GNetworkOfAgentsSuspension cut = cutOf(agentsNetwork, privateContext);
		if (!suspensionState) {
			cut.setSuspendedAt(new Date());
			suspensionState = true;
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Network:" + cut.getNetworkCode() + " is being suspended, asked by agent:"
						+ (participant != null ? participant.getNetworkAgentName() : null) + " suspension:"
						+ cut.getCode());
			}
		}
		cut.getSuspendedAgents().add(SuspendedAgentState.of(agentConfig, participant, privateContext, waitForCondition));
		if (waitForCondition != null) {
			conditions.add(waitForCondition);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agent:" + (participant != null ? participant.getNetworkAgentName() : null)
					+ " suspended waiting for:"
					+ (waitForCondition != null ? waitForCondition.getWaitingConditionStatement() : null)
					+ " in suspension:" + cut.getCode());
		}
	}

	@Override
	public synchronized void setSuspendedAgentTurn(AgentNetworkParticipant participant,
			AgentsExchangeMessage inputMessage, int contributionNr) throws AgentException {
		if (!suspensionState || suspension == null || participant == null) {
			return;
		}
		final String name = participant.getNetworkAgentName();
		// the last state of that agent still without its turn: an agent asking twice
		// in the same run leaves two states, each with the turn it asked in
		SuspendedAgentState withoutTurn = null;
		for (SuspendedAgentState state : suspension.getSuspendedAgents()) {
			if (state.getParticipant() != null && name != null && name.equals(state.getParticipant().getNetworkAgentName())
					&& state.getInputMessage() == null) {
				withoutTurn = state;
			}
		}
		if (withoutTurn == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Agent:" + name + " did not ask to suspend the run: its turn is not kept");
			}
			return;
		}
		withoutTurn.setInputMessage(ExchangeMessageRecord.of(inputMessage));
		withoutTurn.setContributionNr(contributionNr);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Agent:" + name + " asked to suspend the run in turn " + contributionNr
					+ ": the message it was executing is kept for the respawn");
		}
	}

	@Override
	public synchronized void setSuspendingMessagesNotYetDelivered(GAgentsNetwork agentsNetwork,
			GAgentConfig agentConfig, AgentNetworkParticipant participant, AgentPrivateSessionContext privateContext,
			List<AgentsExchangeMessage> messages) throws AgentException {
		if (!suspensionState) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("No agent asked to suspend the run: nothing is frozen");
			}
			return;
		}
		final GNetworkOfAgentsSuspension cut = cutOf(agentsNetwork, privateContext);
		final List<AgentsExchangeMessage<?>> frozen = new ArrayList<AgentsExchangeMessage<?>>();
		if (messages != null) {
			for (AgentsExchangeMessage message : messages) {
				frozen.add(message);
			}
		}
		cut.getFrozenDeliveries().add(FrozenDeliveries.of(agentConfig, participant, privateContext, frozen));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Frozen " + frozen.size() + " undelivered message(s) produced by agent:"
					+ (participant != null ? participant.getNetworkAgentName() : null) + " in suspension:"
					+ cut.getCode());
		}
	}

	@Override
	public synchronized void setSuspendingAgentsRuntimeState(List<RuntimeAgentInfos> agentsRuntime)
			throws AgentException {
		if (!suspensionState) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("No agent asked to suspend the run: the agents' runtime is not kept");
			}
			return;
		}
		final GNetworkOfAgentsSuspension cut = cutOf(network, null);
		cut.getAgentsRuntime().clear();
		if (agentsRuntime != null) {
			for (RuntimeAgentInfos runtime : agentsRuntime) {
				final AgentRuntimeState state = AgentRuntimeState.of(runtime);
				if (state != null) {
					cut.getAgentsRuntime().add(state);
				}
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Kept the runtime of " + cut.getAgentsRuntime().size() + " agent(s) in suspension:"
					+ cut.getCode());
		}
	}

	@Override
	public boolean isSuspensionState() {
		return suspensionState;
	}

	@Override
	public synchronized void setSuspendingNetworkOfAgentsSharedState(AgentsCollaborationSessionContext sharedContext)
			throws AgentException {
		if (!suspensionState) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("No agent asked to suspend the run: the shared state is not kept");
			}
			return;
		}
		final GNetworkOfAgentsSuspension cut = cutOf(network, null);
		if (sharedContext != null) {
			cut.setCollaborationSessionId(sharedContext.getId());
		}
		cut.setSharedState(SharedState.of(sharedContext));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Kept the shared state of session:" + (sharedContext != null ? sharedContext.getId() : null)
					+ " with " + (cut.getSharedState() != null ? cut.getSharedState().getContributions().size() : 0)
					+ " contribution(s) in suspension:" + cut.getCode());
		}
	}

	/**
	 * Writes the cut as one block, when an agent asked to suspend the run; nothing
	 * otherwise. A block that cannot be written fails with an
	 * {@link IllegalStateException}, since this is the only write and the run must
	 * not look suspended: the network treats it as the failure of the run.
	 */
	@Override
	public synchronized void networkExecutionFinished() {
		if (!suspensionState || suspension == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The run of network:" + (network != null ? network.getCode() : null)
						+ " finished without suspension: nothing kept");
			}
			return;
		}
		suspension.setFinishedAt(new Date());
		suspension.setDateModified(suspension.getFinishedAt());
		try {
			dropOpenBlocksOf(suspension.getConversationCode(), suspension.getCode());
			repository.save(suspension);
		} catch (RuntimeException e) {
			LOGGER.error(SAVE_FAILED + suspension.getNetworkCode() + " suspension:" + suspension.getCode()
					+ "; values the mapping may not write: " + SuspensionBlockDiagnostics.foreignValues(suspension), e);
			throw new IllegalStateException(SAVE_FAILED + suspension.getNetworkCode(), e);
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Network:" + suspension.getNetworkCode() + " suspended, suspension:" + suspension.getCode()
					+ " saved with " + suspension.getSuspendedAgents().size() + " suspended agent(s), "
					+ suspension.getFrozenDeliveries().size() + " frozen deliver(y/ies)"
					+ (respawnedFromSuspensionCode != null ? ", respawned from:" + respawnedFromSuspensionCode : ""));
		}
	}

	/**
	 * A conversation holds one open block at most: the ones still open when a new
	 * block of the same conversation is written are dropped, the respawn of the
	 * block they belonged to being the one that led here, or a run that went on
	 * without them.
	 */
	private void dropOpenBlocksOf(String conversationCode, String exceptCode) {
		if (conversationCode == null) {
			return;
		}
		for (GNetworkOfAgentsSuspension open : repository
				.findByConversationCodeAndRespawnedAtIsNullAndDroppedAtIsNull(conversationCode)) {
			if (open == null || open.getCode() == null || open.getCode().equals(exceptCode)) {
				continue;
			}
			open.setDroppedAt(new Date());
			open.setDateModified(open.getDroppedAt());
			repository.save(open);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Suspension:" + open.getCode() + " of conversation:" + conversationCode
						+ " dropped: the conversation suspended again into suspension:" + exceptCode);
			}
		}
	}

	@Override
	public synchronized List<AgentSuspendedWaitingCondition> suspensionConditions() {
		return List.copyOf(conditions);
	}

	@Override
	public synchronized String suspensionId() {
		return suspension != null ? suspension.getCode() : null;
	}

	/**
	 * The cut, opened at the first call that adds to it: for the network given, or
	 * the one this handler was initialized for.
	 */
	private GNetworkOfAgentsSuspension cutOf(GAgentsNetwork agentsNetwork, AgentPrivateSessionContext privateContext)
			throws AgentException {
		final GAgentsNetwork of = agentsNetwork != null ? agentsNetwork : network;
		if (of == null) {
			throw new AgentException(NOT_INITIALIZED);
		}
		if (suspension == null) {
			suspension = new GNetworkOfAgentsSuspension();
			suspension.setCode(UUID.randomUUID().toString());
			suspension.setDateCreated(new Date());
			suspension.setNetworkCode(of.getCode());
			suspension.setNetwork(of);
			suspension.setNetworkInput(networkInput);
			suspension.setNetworkInputClass(networkInput != null ? networkInput.getClass().getName() : null);
			suspension.setConversationCode(conversationCode);
			suspension.setRequestId(requestId);
			suspension.setRespawnedFromSuspensionCode(respawnedFromSuspensionCode);
			suspension.setDescription("Suspension of the run of network: " + of.getCode());
		}
		if (suspension.getCollaborationSessionId() == null && privateContext != null) {
			suspension.setCollaborationSessionId(privateContext.getCollaborationContextId());
		}
		return suspension;
	}
}
