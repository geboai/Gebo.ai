/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;

/**
 * What an agent able to wait does around its model calls in one turn, in its own
 * control code: it loads a wait for the tools of its calls, tells the model what
 * it waited for when it is re-executed on a respawn, and, once the calls ended,
 * asks the network to suspend the run for what the tools declared.
 * <p>
 * Created at the start of a turn, for that turn only:
 * <ol>
 * <li>{@link #toolCalling()} is what the model calls of the turn run with: the
 * wait, merged into the run's container, whose caller, limits and mandatory tools
 * stay;</li>
 * <li>{@link #toldInput(Object)} is what the agent gives the model in place of
 * the message it received;</li>
 * <li>{@link #suspendIfWaiting(GAgentsNetwork, GAgentConfig, AgentPrivateSessionContext)},
 * called when the calls ended; when it answers true the agent says nothing, and
 * is re-executed on this message once the conditions are resolved.</li>
 * </ol>
 */
public final class AgentSuspensionTurn {
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentSuspensionTurn.class);

	private final String agent;
	private final IGNetworkOfAgentsSuspensionHandler suspensionHandler;
	private final AgentNetworkParticipant participant;
	private final GWaitExternalConditionSink wait = new GWaitExternalConditionSink();
	private final GeboToolCalling toolCalling;
	@SuppressWarnings("rawtypes")
	private final List<AgentSuspendedWaitingCondition> resolved;

	private AgentSuspensionTurn(String agent, GeboToolCalling runToolCalling,
			IGNetworkOfAgentsSuspensionHandler suspensionHandler, AgentNetworkParticipant participant) {
		this.agent = agent;
		this.suspensionHandler = suspensionHandler;
		this.participant = participant;
		this.toolCalling = GeboToolCalling
				.merged(GeboToolCalling.builder().provide(IGWaitExternalCondition.class, wait).build(), runToolCalling);
		this.resolved = suspensionHandler != null ? suspensionHandler.takeResolvedConditionsOf(participant) : List.of();
	}

	/**
	 * The turn of an agent.
	 *
	 * @param agent             how the agent is named in logs and failures
	 * @param runToolCalling    what the run adds to the model calls of the agent
	 * @param suspensionHandler the run's handler; null in a run that cannot suspend
	 * @param participant       the agent in the network
	 */
	public static AgentSuspensionTurn of(String agent, GeboToolCalling runToolCalling,
			IGNetworkOfAgentsSuspensionHandler suspensionHandler, AgentNetworkParticipant participant) {
		return new AgentSuspensionTurn(agent, runToolCalling, suspensionHandler, participant);
	}

	/** What the model calls of the turn run with. */
	public GeboToolCalling toolCalling() {
		return toolCalling;
	}

	/**
	 * Whether a tool of the turn's calls declared what the run must wait for, readable
	 * while the calls are still streaming: from then on what the call answers is the
	 * waiting tool's own result, for the model and not for the user, since the agent
	 * says nothing when it waits.
	 */
	public boolean isWaiting() {
		return wait.isWaiting();
	}

	/**
	 * The input of the turn: the message received, with what the agent waited for
	 * told first when it is re-executed on a respawn. An input that is not text
	 * stays as it is: there is no place in it for the answers.
	 */
	@SuppressWarnings("unchecked")
	public <T> T toldInput(T input) {
		if (resolved.isEmpty()) {
			return input;
		}
		if (input instanceof String text) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(agent + " respawned with the answers to " + resolved.size() + " condition(s)");
			}
			return (T) ResolvedConditionText.prepend(resolved, text);
		}
		LOGGER.warn(agent + " respawned with the answers to " + resolved.size()
				+ " condition(s) but its input is not text (" + (input != null ? input.getClass().getName() : null)
				+ "): the model is not told the answer");
		return input;
	}

	/**
	 * The request context of the turn, for an agent whose model reads the request from
	 * it rather than from its input: on a respawn what the user wrote last is only the
	 * answer, the request is the told input, with the message the agent was executing.
	 * An input that is not text leaves the context as it is.
	 */
	public IChatRequestContext toldContext(IChatRequestContext context, Object toldInput) {
		return !resolved.isEmpty() && context != null && toldInput instanceof String text
				? context.withActualUserRequest(text)
				: context;
	}

	/**
	 * Asks the network to suspend the run for each condition a tool of the turn
	 * declared.
	 *
	 * @return true when the agent waits, and says nothing to the network
	 * @throws AgentException when a tool declared a condition in a run with no
	 *                        suspension handler, or the handler refuses it
	 */
	@SuppressWarnings("rawtypes")
	public boolean suspendIfWaiting(GAgentsNetwork network, GAgentConfig config, AgentPrivateSessionContext privateContext)
			throws AgentException {
		if (!wait.isWaiting()) {
			return false;
		}
		if (suspensionHandler == null) {
			throw new AgentException(
					agent + " must wait for " + wait.waited().size() + " condition(s) but the run has no suspension handler");
		}
		for (AgentSuspendedWaitingCondition<?, ?> condition : wait.waited()) {
			suspensionHandler.enterSuspensionState(network, config, participant, privateContext, condition);
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(agent + " asked to suspend the run for " + wait.waited().size()
					+ " condition(s), the call stopped by tool:" + toolCalling.isStoppedByTool());
		}
		return true;
	}
}
