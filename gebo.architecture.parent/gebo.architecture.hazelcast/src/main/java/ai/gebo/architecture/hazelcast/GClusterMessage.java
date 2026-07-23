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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * A message delivered to a {@link IGClusterMessageHandler} through the
 * {@link IGClusterMessageBus}. It carries the application payload together with
 * the identity of the publishing cluster member so subscribers can distinguish
 * their own echoed publications ({@link #localOrigin}) from genuine remote
 * events.
 *
 * @param <T> payload type
 */
@Getter
@ToString
@AllArgsConstructor
public class GClusterMessage<T extends Serializable> {

	/** UUID (as string) of the cluster member that published the message. */
	private final String originMemberId;

	/** Publication timestamp (epoch millis) taken on the publishing member. */
	private final long timestamp;

	/**
	 * {@code true} when the message was published by the local member (Hazelcast
	 * topics also deliver a publication back to its publisher). Subscribers that
	 * already applied the change locally should ignore these.
	 */
	private final boolean localOrigin;

	/** The application payload. */
	private final T payload;
}
