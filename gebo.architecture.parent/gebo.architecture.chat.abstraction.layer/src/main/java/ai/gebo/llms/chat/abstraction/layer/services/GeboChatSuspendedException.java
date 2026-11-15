/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedState;
import lombok.Getter;

/**
 * The conversation waits for a mandatory condition of a suspended network of
 * agents: no normal interaction can take place in it until the block is
 * respawned with the reaction to that condition. Carries the pending state, so
 * that the client shows the question again and answers it.
 */
@Getter
public class GeboChatSuspendedException extends GeboChatException {
	private static final long serialVersionUID = 1L;
	private final String userChatContextCode;
	private final transient NetworkOfAgentsSuspendedState pendingState;

	public GeboChatSuspendedException(String userChatContextCode, NetworkOfAgentsSuspendedState pendingState) {
		super("The conversation " + userChatContextCode + " waits for the answer to a suspended network of agents"
				+ (pendingState != null ? " (suspension:" + pendingState.getNetworkOfAgentsBlockId() + ")" : "")
				+ ": only its respawn with the reaction is possible");
		this.userChatContextCode = userChatContextCode;
		this.pendingState = pendingState;
	}
}
