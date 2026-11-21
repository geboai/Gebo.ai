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
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.llms.abstraction.layer.services.toolcalling.IToolCallEndingControl;

/**
 * The implementation of {@link IGAgentCommands} an agent loads for the model calls
 * of one of its turns: it keeps the commands the tools gave, for the agent to read
 * once the calls ended. One per turn, since what is commanded is the turn's.
 * <p>
 * It is the control ending the call: once a tool gave a command the tool calling
 * loop stops at that round and the model is not called again
 * ({@link IToolCallEndingControl}), the commands given in the same round all taken.
 * A command it refuses - an agent the one running may not reach, or the agent's
 * last step, see {@link #refuseWith(String)} - leaves the call going on: the model
 * reads the refusal as any tool result.
 */
public class GAgentCommandsSink implements IGAgentCommands, IToolCallEndingControl {
	private static final Logger LOGGER = LoggerFactory.getLogger(GAgentCommandsSink.class);
	static final String NOT_REACHABLE = "This agent cannot be reached from here: the agents you may hand work to are ";
	private final Set<String> reachable;
	private final List<Command> commanded = new ArrayList<Command>();
	private volatile String refusal = null;

	/**
	 * @param reachable the network names of the agents that may be commanded: the
	 *                  communication list of the agent running the tools
	 */
	public GAgentCommandsSink(Collection<String> reachable) {
		this.reachable = reachable != null ? new LinkedHashSet<String>(reachable) : Set.of();
	}

	/**
	 * From now on every command is refused with the given text, written for the model:
	 * what an agent does when the step it is running is one it cannot be executed again
	 * after. Null takes commands again.
	 */
	public void refuseWith(String why) {
		this.refusal = why;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(why != null ? "Commands to the other agents are refused from now on: " + why
					: "Commands to the other agents are taken again");
		}
	}

	@Override
	public synchronized String command(String toAgent, Object commandData) {
		final String refused = refusal;
		if (refused != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("A command to agent:" + toAgent + " is refused: " + refused);
			}
			return refused;
		}
		if (toAgent == null || !reachable.contains(toAgent)) {
			LOGGER.warn("A tool commanded the agent:" + toAgent + " which is not among the reachable ones " + reachable
					+ ": refused");
			return NOT_REACHABLE + reachable + ".";
		}
		commanded.add(new Command(toAgent, commandData));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("A tool handed work to agent:" + toAgent + ", " + commanded.size()
					+ " command(s) in this turn");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<AGENT_COMMAND to=" + toAgent + ">");
			LOGGER.trace(String.valueOf(commandData));
			LOGGER.trace("</AGENT_COMMAND>");
		}
		return null;
	}

	@Override
	public synchronized List<Command> commanded() {
		return List.copyOf(commanded);
	}

	/** Whether a tool handed work to another agent. */
	public synchronized boolean isCommanding() {
		return !commanded.isEmpty();
	}

	/** The call ends once a tool handed work to another agent. */
	@Override
	public boolean endsTheCall() {
		return isCommanding();
	}
}
