/*
 * Copyright (c) 2025+ Gebo.ai
 * Licensed under the Mozilla Public License 2.0 (MPL 2.0) with Data Protection Clauses.
 * See LICENSE file in the project root for full license information.
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 */
package ai.gebo.llms.chat.pipelines.service;

import java.util.List;

import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedStateReaction;

/**
 * Records the answers a chat gives to a suspended network one condition at a
 * time, next to those other sources give: the network resumes once every
 * mandatory condition of its block has an answer, from whichever source. Where
 * no implementation is present a chat reaction answers the block as a whole,
 * as it always did.
 */
public interface IConditionReactionRecorder {
	/**
	 * The run origin of a respawn the recorder's own dispatcher starts: such a
	 * request already carries the answers of every source and is never recorded
	 * again.
	 */
	String RESPAWN_ORIGIN = "CONDITION_EVENT";

	/**
	 * Whether the block already holds answers other sources gave: a reaction from
	 * the chat then joins them instead of answering the block on its own.
	 */
	boolean holdsAnswersFor(GNetworkOfAgentsSuspension block);

	/**
	 * Records each answer of the reaction as its condition's solution. When the
	 * block is solved with them, the respawn is claimed for the request, which runs
	 * it with the reaction returned, holding the answers of every source; the
	 * request then says it ended (see {@link #concluded}).
	 *
	 * @param block     the open block of the conversation
	 * @param reaction  the chat's reaction to it
	 * @param username  the conversation's user, who answers
	 * @param requestId the chat request carrying it: the same request sent again
	 *                  changes nothing
	 * @return what became of each answer, what the block still waits for and
	 *         what the request does next
	 */
	RecordedConditionReaction record(GNetworkOfAgentsSuspension block, NetworkOfAgentsSuspendedStateReaction reaction,
			String username, String requestId);

	/**
	 * The respawn a request claimed (see {@link #record}) ended, however it went:
	 * the block tells, and its answers are marked used, or kept for another
	 * attempt when it is still open. Taken further apart from the caller.
	 */
	void concluded(GNetworkOfAgentsSuspension block, String requestId);

	/** The conditions of the block an answer already solves, from whichever source. */
	List<SolvedCondition> solvedConditionsOf(GNetworkOfAgentsSuspension block);
}
