/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.session.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * How a run from outside a chat wants the conversation it runs in opened: a
 * harness, an A2A skill or an MCP tool that runs a network of agents in a
 * conversation of its own (see
 * {@code IGChatSessionLifeCycleService#ensureChatSessionExists(GeboChatRequest, GChatSessionOpeningOptions)}).
 * A chat opens its conversations without any of this.
 */
@Getter
@Builder
public class GChatSessionOpeningOptions {
	/**
	 * Whether the conversation lives only as long as the run: never listed, dropped
	 * when the run is over (see {@link GUserChatSession#getTransientRun()}).
	 */
	private final boolean transientRun;
	/** Where the run comes from ({@code HARNESS}, {@code A2A}, {@code MCP}...), kept on the conversation. */
	private final String runOrigin;
	/**
	 * The people who may work in the conversation beside its owner: a run that
	 * happens as several identities over time (the account a harness runs as, the
	 * person who woke it, an administrator who tried it again) names them all here,
	 * so that each of them finds the conversation granted when it is their turn.
	 */
	private final List<String> sharedWithUsers;
	/** The groups whose members may work in the conversation beside its owner. */
	private final List<String> sharedWithGroups;

	/** A conversation opened as a chat opens one: nothing special about it. */
	public static GChatSessionOpeningOptions none() {
		return GChatSessionOpeningOptions.builder().build();
	}

	public boolean isShared() {
		return (sharedWithUsers != null && !sharedWithUsers.isEmpty())
				|| (sharedWithGroups != null && !sharedWithGroups.isEmpty());
	}
}
