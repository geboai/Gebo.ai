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

/**
 * Wire envelope actually transported over a Hazelcast {@code ITopic}. It stamps
 * every payload with the publishing member id and a timestamp so receivers can
 * reconstruct a {@link GClusterMessage} and filter their own echoes.
 * <p>
 * Package-private: it is an implementation detail of
 * {@link GHazelcastClusterMessageBus}.
 */
@Getter
@AllArgsConstructor
class GClusterEnvelope implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String originMemberId;
	private final long timestamp;
	private final Serializable payload;
}
