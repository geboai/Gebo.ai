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
 * Generic publish/subscribe abstraction used to propagate small control /
 * invalidation events between the instances of a Gebo.ai cluster.
 * <p>
 * The abstraction is intentionally decoupled from Hazelcast so callers can be
 * written once and behave correctly both when clustering is enabled
 * (Hazelcast-backed, see {@link GHazelcastClusterMessageBus}) and when it is
 * disabled (single instance, see {@link GNoOpClusterMessageBus}). A bean of this
 * type is <em>always</em> available in the Spring context, so consumers may
 * inject it unconditionally and guard cluster-only work with
 * {@link #isClustered()}.
 * <p>
 * Payloads must be {@link Serializable}; they are transported as-is over the
 * cluster and should therefore stay small and self-contained.
 */
public interface IGClusterMessageBus {

	/**
	 * @return {@code true} when a real cluster transport is active. When
	 *         {@code false} {@link #publish(String, Serializable)} is a no-op and
	 *         {@link #subscribe(String, IGClusterMessageHandler)} never delivers
	 *         anything.
	 */
	boolean isClustered();

	/**
	 * @return an identifier of the local member. Stable for the lifetime of the
	 *         instance; used to detect self-published messages.
	 */
	String localMemberId();

	/**
	 * Publishes a payload to the given logical channel. Delivered to every member
	 * subscribed to the same channel (including, on Hazelcast, the publisher
	 * itself — see {@link GClusterMessage#isLocalOrigin()}).
	 *
	 * @param channel logical channel name
	 * @param payload the (serializable) payload
	 */
	<T extends Serializable> void publish(String channel, T payload);

	/**
	 * Subscribes a handler to a logical channel.
	 *
	 * @param channel logical channel name
	 * @param handler the callback invoked for every received message
	 * @return a handle whose {@link AutoCloseable#close()} removes the
	 *         subscription. Never {@code null}.
	 */
	<T extends Serializable> AutoCloseable subscribe(String channel, IGClusterMessageHandler<T> handler);
}
