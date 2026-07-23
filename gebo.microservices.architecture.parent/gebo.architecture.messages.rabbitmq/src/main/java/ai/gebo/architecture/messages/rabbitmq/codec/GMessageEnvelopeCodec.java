/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.messages.rabbitmq.codec;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessagePayloadType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.architecture.messages.rabbitmq.config.GeboRabbitMqMessagingProperties;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

/**
 * Serializes and deserializes {@link GMessageEnvelope} instances to/from the
 * byte payload transported over RabbitMQ.
 *
 * <p>
 * {@link GMessageEnvelope} is generic over an {@link IGMessagePayloadType}, so
 * the concrete payload type is not statically known on the receiving side.
 * Rather than depending on Jackson polymorphic typing (and leaking transport
 * concerns into the shared model), the codec relies on the envelope's own
 * {@code payloadType} field — which already carries the fully qualified class
 * name of the payload — to rebuild the payload with the right concrete type.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Component
@ConditionalOnProperty(prefix = GeboRabbitMqMessagingProperties.PREFIX, name = "enabled", havingValue = "true")
public class GMessageEnvelopeCodec {

	private final ObjectMapper objectMapper;

	/**
	 * @param objectMapper the application-wide Jackson 3 mapper
	 */
	public GMessageEnvelopeCodec(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/**
	 * Serializes an envelope (payload included) to a JSON byte array.
	 *
	 * @param envelope the envelope to serialize
	 * @return the serialized bytes
	 */
	public byte[] serialize(GMessageEnvelope<?> envelope) {
		return objectMapper.writeValueAsBytes(envelope);
	}

	/**
	 * Rebuilds an envelope from its JSON byte representation, restoring the payload
	 * into the concrete type advertised by the {@code payloadType} field.
	 *
	 * @param body the serialized bytes
	 * @return the reconstructed envelope
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GMessageEnvelope<?> deserialize(byte[] body) {
		JsonNode root = objectMapper.readTree(body);
		if (!(root instanceof ObjectNode objectRoot)) {
			throw new IllegalArgumentException("Unexpected RabbitMQ message body, JSON object expected");
		}

		JsonNode payloadTypeNode = objectRoot.get("payloadType");
		String payloadType = payloadTypeNode != null && !payloadTypeNode.isNull() ? payloadTypeNode.asString() : null;

		// Detach the payload so the envelope itself can be materialized without Jackson
		// trying to instantiate the IGMessagePayloadType interface.
		JsonNode payloadNode = objectRoot.remove("payload");

		GMessageEnvelope envelope = objectMapper.treeToValue(objectRoot, GMessageEnvelope.class);

		if (payloadType != null && payloadNode != null && !payloadNode.isNull()) {
			try {
				Class<?> payloadClass = Class.forName(payloadType);
				IGMessagePayloadType payload = (IGMessagePayloadType) objectMapper.treeToValue(payloadNode,
						payloadClass);
				// setPayload also restores payloadType from the concrete class name.
				envelope.setPayload(payload);
			} catch (ClassNotFoundException ex) {
				throw new IllegalStateException("Unknown payload type received over RabbitMQ: " + payloadType, ex);
			}
		}
		return envelope;
	}
}
