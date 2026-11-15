/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standard.services;

/**
 * Which knowledge bases a run of a network of agents from outside a chat works
 * on. A chat works on its chat profile's; a run from outside one may have no
 * profile, and is told here what it works on then.
 */
public enum GKnowledgeBaseScope {
	/** The chat profile's knowledge bases, as a chat: none without a profile. */
	PROFILE,
	/**
	 * The chat profile's; without a profile, every knowledge base the identity of
	 * the run can see. The default of a run from outside a chat.
	 */
	PROFILE_OR_ALL_VISIBLE,
	/** The knowledge bases the caller stated on the request, and those only. */
	STATED
}
