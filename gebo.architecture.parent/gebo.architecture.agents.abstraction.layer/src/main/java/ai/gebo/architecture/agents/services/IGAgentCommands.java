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

/**
 * What a tool tells the agent running it when the model hands a piece of work to
 * another agent of the network: the agent loads its own implementation in the tool
 * calling of its model call (see {@code GeboToolCalling.Builder#provide}), a tool
 * handing work over finds it in its tool context and gives the command, which ends
 * the call: the implementation loaded is the control ending it (see
 * {@link GAgentCommandsSink}). The agent, once the call ended, gives the commands to
 * the network as its messages: the network delivers them, the agents commanded
 * answer, and the agent is executed again, told their answers.
 * <p>
 * A tool that finds nothing loaded is running where no agent can be commanded, and
 * tells the model so.
 */
public interface IGAgentCommands {
	/** A piece of work handed to an agent of the network: its network name and what it is given as input. */
	public static record Command(String toAgent, Object commandData) {
	}

	/**
	 * Given by a tool: the agent named is handed the work.
	 *
	 * @return null when the command is taken, else why it is not, written for the model
	 */
	String command(String toAgent, Object commandData);

	/** The commands the tools gave in the call, in the order they did; empty when none did. */
	List<Command> commanded();
}
