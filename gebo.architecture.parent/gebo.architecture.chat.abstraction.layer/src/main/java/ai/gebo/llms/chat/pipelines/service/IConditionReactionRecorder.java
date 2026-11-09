/*
 * Copyright (c) 2025+ Gebo.ai
 * Licensed under the Mozilla Public License 2.0 (MPL 2.0) with Data Protection Clauses.
 * See LICENSE file in the project root for full license information.
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 */
package ai.gebo.llms.chat.pipelines.service;

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
	 * Whether the block already holds answers other sources gave: a reaction from
	 * the chat then joins them instead of answering the block on its own.
	 */
	boolean holdsAnswersFor(GNetworkOfAgentsSuspension block);

	/**
	 * Records each answer of the reaction as its condition's solution. The network
	 * resumes, apart from the chat request, once the block is solved.
	 *
	 * @param block     the open block of the conversation
	 * @param reaction  the chat's reaction to it
	 * @param username  the conversation's user, who answers
	 * @param requestId the chat request carrying it: the same request sent again
	 *                  changes nothing
	 * @return what became of each answer and what the block still waits for
	 */
	RecordedConditionReaction record(GNetworkOfAgentsSuspension block, NetworkOfAgentsSuspendedStateReaction reaction,
			String username, String requestId);
}
