/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.messages.rabbitmq.external;

import ai.gebo.architecture.messages.rabbitmq.config.GeboRabbitMqMessagingProperties;
import ai.gebo.microservices.topology.GeboCurrentMicroservice;
import ai.gebo.microservices.topology.GeboMicroservice;

/**
 * Shared helper for the topology-driven external emitter/receiver sources.
 *
 * Gebo.ai comment agent
 */
final class RabbitMqTopologyBridgeSupport {

	private RabbitMqTopologyBridgeSupport() {
		// utility
	}

	/**
	 * Resolves the normalised id of the <b>local</b> microservice, so it can be
	 * excluded when deducing remote endpoints from the topology.
	 *
	 * <p>
	 * Prefers the explicit {@code ai.gebo.messaging.rabbitmq.local-microservice-id}
	 * (normalised with the {@code '.'} &rarr; {@code '_'} rule), and falls back to
	 * the running service's {@code spring.application.name} resolved by
	 * {@link GeboCurrentMicroservice}.
	 * </p>
	 *
	 * @param properties the RabbitMQ messaging bindings
	 * @param current    the running service identity
	 * @return the normalised local microservice id, or {@code null} if none is set
	 */
	static String resolveLocalMicroserviceId(GeboRabbitMqMessagingProperties properties,
			GeboCurrentMicroservice current) {
		String configured = properties.getLocalMicroserviceId();
		if (configured != null && !configured.isBlank()) {
			return GeboMicroservice.normalizeName(configured);
		}
		return current != null ? current.getMicroserviceId() : null;
	}
}
