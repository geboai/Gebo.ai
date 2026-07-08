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
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;

/**
 * Hazelcast {@code ITopic}-backed implementation of {@link IGClusterMessageBus}.
 * Each logical channel maps to a distributed topic; payloads are wrapped in a
 * {@link GClusterEnvelope} carrying the local member id so subscribers can tell
 * their own echoed publications from genuine remote events.
 * <p>
 * The bus owns the {@link HazelcastInstance} it was built with and shuts it down
 * on {@link #close()} (invoked by Spring as the inferred bean destroy method).
 */
public class GHazelcastClusterMessageBus implements IGClusterMessageBus, AutoCloseable {

	private static final Logger LOGGER = LoggerFactory.getLogger(GHazelcastClusterMessageBus.class);

	private final HazelcastInstance hazelcast;

	public GHazelcastClusterMessageBus(HazelcastInstance hazelcast) {
		this.hazelcast = hazelcast;
	}

	@Override
	public boolean isClustered() {
		return true;
	}

	@Override
	public String localMemberId() {
		return hazelcast.getCluster().getLocalMember().getUuid().toString();
	}

	@Override
	public <T extends Serializable> void publish(String channel, T payload) {
		ITopic<GClusterEnvelope> topic = hazelcast.getTopic(channel);
		GClusterEnvelope envelope = new GClusterEnvelope(localMemberId(), System.currentTimeMillis(), payload);
		topic.publish(envelope);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Serializable> AutoCloseable subscribe(String channel, IGClusterMessageHandler<T> handler) {
		String localId = localMemberId();
		ITopic<GClusterEnvelope> topic = hazelcast.getTopic(channel);
		UUID registrationId = topic.addMessageListener(message -> {
			GClusterEnvelope envelope = message.getMessageObject();
			boolean local = localId.equals(envelope.getOriginMemberId());
			try {
				GClusterMessage<T> received = new GClusterMessage<>(envelope.getOriginMemberId(),
						envelope.getTimestamp(), local, (T) envelope.getPayload());
				handler.onMessage(received);
			} catch (Throwable t) {
				LOGGER.error("Error handling cluster message on channel " + channel, t);
			}
		});
		return () -> topic.removeMessageListener(registrationId);
	}

	@Override
	public void close() {
		try {
			hazelcast.shutdown();
		} catch (Throwable t) {
			LOGGER.warn("Error shutting down Hazelcast instance", t);
		}
	}
}
