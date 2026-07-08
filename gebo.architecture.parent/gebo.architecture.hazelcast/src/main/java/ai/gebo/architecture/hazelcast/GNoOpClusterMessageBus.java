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
 * Fallback {@link IGClusterMessageBus} used when clustering is disabled
 * ({@code gebo.hazelcast.enabled=false}) or Hazelcast is absent. Publications
 * are dropped and subscriptions never receive anything, so cluster-aware code
 * stays functionally correct on a single, standalone instance.
 */
public class GNoOpClusterMessageBus implements IGClusterMessageBus {

	private static final AutoCloseable NO_OP_SUBSCRIPTION = () -> {
		/* nothing to release */
	};

	@Override
	public boolean isClustered() {
		return false;
	}

	@Override
	public String localMemberId() {
		return "local";
	}

	@Override
	public <T extends Serializable> void publish(String channel, T payload) {
		// No cluster: nothing to propagate.
	}

	@Override
	public <T extends Serializable> AutoCloseable subscribe(String channel, IGClusterMessageHandler<T> handler) {
		return NO_OP_SUBSCRIPTION;
	}
}
