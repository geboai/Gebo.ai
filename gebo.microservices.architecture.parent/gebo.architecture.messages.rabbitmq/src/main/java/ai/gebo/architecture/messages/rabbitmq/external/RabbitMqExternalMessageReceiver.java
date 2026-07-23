/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.messages.rabbitmq.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import ai.gebo.application.messaging.external.ExternalReceiverIfaceData;
import ai.gebo.application.messaging.external.GAbstractExternalMessageReceiver;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.architecture.messages.rabbitmq.codec.GMessageEnvelopeCodec;

/**
 * Outbound bridge (local broker -&gt; RabbitMQ).
 *
 * <p>
 * Registered in the in-memory broker as a receiver. When a local emitter routes
 * an envelope to this component, {@link #accept(GMessageEnvelope)} serializes it
 * and publishes it to the configured exchange with the configured routing key,
 * handing the message over to the remote side.
 * </p>
 *
 * Gebo.ai comment agent
 */
public class RabbitMqExternalMessageReceiver extends GAbstractExternalMessageReceiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqExternalMessageReceiver.class);

	private final RabbitTemplate rabbitTemplate;
	private final GMessageEnvelopeCodec codec;
	private final String exchange;
	private final String routingKey;

	/**
	 * @param config         the messaging identity this receiver is registered with
	 * @param rabbitTemplate template used to publish
	 * @param codec          envelope serializer
	 * @param exchange       target exchange
	 * @param routingKey     routing key used when publishing
	 */
	public RabbitMqExternalMessageReceiver(ExternalReceiverIfaceData config, RabbitTemplate rabbitTemplate,
			GMessageEnvelopeCodec codec, String exchange, String routingKey) {
		super(config);
		this.rabbitTemplate = rabbitTemplate;
		this.codec = codec;
		this.exchange = exchange;
		this.routingKey = routingKey;
	}

	@Override
	public void accept(GMessageEnvelope message) {
		try {
			byte[] body = codec.serialize(message);
			rabbitTemplate.convertAndSend(exchange, routingKey, (Object) body);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Published envelope " + message + " to exchange '" + exchange + "' routingKey '"
						+ routingKey + "'");
			}
		} catch (Throwable th) {
			LOGGER.error("Error publishing envelope " + message + " to RabbitMQ exchange '" + exchange
					+ "' routingKey '" + routingKey + "'", th);
		}
	}
}
