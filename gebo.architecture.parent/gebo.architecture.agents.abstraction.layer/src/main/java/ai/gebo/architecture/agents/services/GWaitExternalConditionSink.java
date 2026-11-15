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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.llms.abstraction.layer.services.toolcalling.IToolCallEndingControl;

/**
 * The implementation of {@link IGWaitExternalCondition} an agent loads for one
 * model call: it keeps what the tools declared, for the agent to read once the
 * call ended. One per call, since what is waited for is the call's.
 * <p>
 * It is the control ending the call: once a tool declared a condition the loop
 * stops at that round, the model is not called again and the agent suspends the
 * run ({@link IToolCallEndingControl}). A tool that declared nothing, refusing to
 * ask, leaves the call going on: the model reads the refusal as any tool result.
 */
public class GWaitExternalConditionSink implements IGWaitExternalCondition, IToolCallEndingControl {
	private static final Logger LOGGER = LoggerFactory.getLogger(GWaitExternalConditionSink.class);
	private final List<AgentSuspendedWaitingCondition<?, ?>> waited = new ArrayList<AgentSuspendedWaitingCondition<?, ?>>();

	@Override
	public synchronized void waitFor(AgentSuspendedWaitingCondition<?, ?> condition) {
		if (condition == null) {
			return;
		}
		waited.add(condition);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("A tool declared the run must wait for: " + condition.getWaitingConditionStatement()
					+ " (condition:" + condition.getSuspendedConditionId() + ")");
		}
	}

	@Override
	public synchronized List<AgentSuspendedWaitingCondition<?, ?>> waited() {
		return List.copyOf(waited);
	}

	/** Whether a tool declared something to wait for. */
	public synchronized boolean isWaiting() {
		return !waited.isEmpty();
	}

	/** The call ends once a tool declared something to wait for. */
	@Override
	public boolean endsTheCall() {
		return isWaiting();
	}
}
