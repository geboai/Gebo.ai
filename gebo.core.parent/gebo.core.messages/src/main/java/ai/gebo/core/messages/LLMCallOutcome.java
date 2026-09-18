/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) - With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.core.messages;

/**
 * How an LLM call ended. Recorded on every usage detail so that the slow tail of the
 * latency distribution stays visible: a call that times out mid stream used to emit no
 * usage record at all, which censored exactly the calls worth looking at.
 */
public enum LLMCallOutcome {
	/** The call completed normally. */
	SUCCESS,
	/** The call failed, typically a transport timeout or a provider error. */
	ERROR,
	/** The subscription was cancelled before the stream completed. */
	CANCELLED
}
