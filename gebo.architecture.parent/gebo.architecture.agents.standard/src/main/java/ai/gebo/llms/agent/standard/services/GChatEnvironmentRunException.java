/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standard.services;

import ai.gebo.architecture.agents.services.AgentException;

/**
 * A run of a network in a chat environment that failed, naming the conversation
 * it failed in: a caller that keeps its conversations between runs goes on with
 * this one, instead of opening another next time.
 */
public class GChatEnvironmentRunException extends AgentException {
	private static final long serialVersionUID = 1L;
	private final String chatContextCode;

	public GChatEnvironmentRunException(String message, Throwable cause, String chatContextCode) {
		super(message, cause);
		this.chatContextCode = chatContextCode;
	}

	/** The conversation the run failed in, null when none was opened. */
	public String getChatContextCode() {
		return chatContextCode;
	}
}
