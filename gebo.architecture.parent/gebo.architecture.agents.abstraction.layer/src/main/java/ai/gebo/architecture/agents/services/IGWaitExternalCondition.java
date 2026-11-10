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

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;

/**
 * What a tool tells the agent running it when the work cannot go on without
 * something from outside the run: a person's answer, an external event. The
 * agent loads its own implementation in the tool calling of its model call
 * (see {@code GeboToolCalling.Builder#provide}); a tool that must wait finds it
 * in its tool context and declares the condition, which ends the call: the
 * implementation loaded is the control ending it (see
 * {@code GWaitExternalConditionSink}); the agent, seeing the call stopped, reads
 * the conditions declared and asks the network to suspend the run for them (see
 * {@link IGNetworkOfAgentsSuspensionHandler#enterSuspensionState}). A tool that
 * finds nothing loaded is running where nothing can wait, and decides by itself.
 */
public interface IGWaitExternalCondition {
	/** Declared by a tool: the run must wait for this before it can go on. */
	void waitFor(AgentSuspendedWaitingCondition<?, ?> condition);

	/** The conditions the tools declared in the call, in the order they did; empty when none did. */
	List<AgentSuspendedWaitingCondition<?, ?>> waited();
}
