/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.services;

import java.util.List;

import ai.gebo.architecture.agents.conditionevents.model.WaitingBlockView;
import ai.gebo.architecture.agents.conditionevents.model.WaitingConditionsActionAck;

/**
 * The suspended runs waiting for a human in the loop or an external condition:
 * what the waiting conditions panel lists, and what it lets their owner, or an
 * administrator, do of them. Going on without a condition answers it with no
 * value, even a mandatory one, and the agent is told who chose so; abandoning a
 * run drops it, freeing its conversation. The expiries are applied by the sweep.
 */
public interface IWaitingConditionsService {

	/** The open blocks of the conversations of that user, the latest first. */
	List<WaitingBlockView> waitingFor(String username);

	/** Every open block, the latest first: what an administrator sees. */
	List<WaitingBlockView> allWaiting();

	/**
	 * Answers the named conditions of the open block with no value, so that the run
	 * goes on once nothing mandatory is left; all of the conditions still waiting
	 * when none is named.
	 *
	 * @param administrator whether the user is an administrator; else the user must
	 *                      own the conversation
	 */
	WaitingConditionsActionAck goOnWithout(String blockCode, List<String> conditionIds, String username,
			boolean administrator);

	/**
	 * Drops the open block, never to be respawned, freeing its conversation.
	 *
	 * @param administrator whether the user is an administrator; else the user must
	 *                      own the conversation
	 */
	WaitingConditionsActionAck abandon(String blockCode, String username, boolean administrator);

	/**
	 * Applies the expiries due: a condition whose expiry lets the run go on is
	 * answered with no value, the owner of one whose expiry does not is told once.
	 * Doing it again changes nothing.
	 */
	void applyExpiries();

	/**
	 * Tells the owners of the runs that suspended with nobody watching, once, and
	 * takes back the messages of the blocks that closed. Doing it again changes
	 * nothing.
	 */
	void tellOwners();
}
