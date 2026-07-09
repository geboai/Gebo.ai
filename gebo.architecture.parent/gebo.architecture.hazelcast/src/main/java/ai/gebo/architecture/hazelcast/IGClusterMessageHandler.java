/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.hazelcast;

import java.io.Serializable;

/**
 * Callback invoked for every message received on a subscribed cluster channel.
 *
 * @param <T> payload type
 */
@FunctionalInterface
public interface IGClusterMessageHandler<T extends Serializable> {

	/**
	 * Handles a single cluster message.
	 *
	 * @param message the received message (never {@code null})
	 */
	void onMessage(GClusterMessage<T> message);
}
