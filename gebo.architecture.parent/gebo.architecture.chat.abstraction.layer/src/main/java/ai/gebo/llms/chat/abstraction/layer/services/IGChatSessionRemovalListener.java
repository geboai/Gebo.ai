/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.services;

/**
 * Told when a conversation is removed, with everything it held, so that what
 * other modules keep of it - what a network of agents remembers of it, for one
 * - goes with it. Declared as a bean.
 */
public interface IGChatSessionRemovalListener {

	/** The conversation with this code is gone; whatever this listener keeps of it must go too. */
	public void chatSessionRemoved(String code);
}
