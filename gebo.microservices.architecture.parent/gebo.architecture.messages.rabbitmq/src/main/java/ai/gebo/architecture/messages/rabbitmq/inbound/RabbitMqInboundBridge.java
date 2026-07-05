/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.messages.rabbitmq.inbound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.architecture.messages.rabbitmq.codec.GMessageEnvelopeCodec;
import ai.gebo.architecture.messages.rabbitmq.config.GeboRabbitMqMessagingProperties;
import jakarta.annotation.PreDestroy;

/**
 * Drives the inbound side of the RabbitMQ integration.
 *
 * <p>
 * A single {@link SimpleMessageListenerContainer} consumes this microservice's
 * own inbound queue (see
 * {@link GeboRabbitMqMessagingProperties#effectiveInboundQueue()}). Every
 * message is deserialized into a {@link GMessageEnvelope} and injected into the
 * local {@link IGMessageBroker} <b>exactly as it arrived</b>: the envelope
 * already carries its {@code sourceModule}/{@code sourceComponent} (a remote
 * emitter registered by {@code RabbitMqExternalMessageEmitterProviderSource}) and
 * its {@code targetModule}/{@code targetComponent} (a real local receiver), which
 * is all the broker needs to route it. The bridge never rewrites those fields.
 * </p>
 *
 * <p>
 * The container is started on {@link ApplicationReadyEvent} — after
 * {@code MessageBrokeringAssembler} has registered the external emitters in the
 * broker (which happens on {@code ContextRefreshedEvent}) — so inbound envelopes
 * always find their source emitter already registered.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Component
@ConditionalOnProperty(prefix = GeboRabbitMqMessagingProperties.PREFIX, name = "enabled", havingValue = "true")
public class RabbitMqInboundBridge {

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqInboundBridge.class);

	private final GeboRabbitMqMessagingProperties properties;
	private final ConnectionFactory connectionFactory;
	private final IGMessageBroker broker;
	private final GMessageEnvelopeCodec codec;
	private final GeboRabbitMqTopologyDeclarer topologyDeclarer;

	private SimpleMessageListenerContainer container;

	public RabbitMqInboundBridge(GeboRabbitMqMessagingProperties properties, ConnectionFactory connectionFactory,
			IGMessageBroker broker, GMessageEnvelopeCodec codec, GeboRabbitMqTopologyDeclarer topologyDeclarer) {
		this.properties = properties;
		this.connectionFactory = connectionFactory;
		this.broker = broker;
		this.codec = codec;
		this.topologyDeclarer = topologyDeclarer;
	}

	/**
	 * Declares the topology and starts the listener on this microservice's inbound
	 * queue.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void start() {
		topologyDeclarer.declareIfEnabled();
		String queue = properties.effectiveInboundQueue();
		if (queue == null || queue.isBlank()) {
			LOGGER.warn("No inbound queue configured (localMicroserviceId is unset); RabbitMQ inbound bridge disabled");
			return;
		}
		container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueueNames(queue);
		container.setMessageListener(message -> onMessage(message.getBody()));
		container.start();
		LOGGER.info("Started RabbitMQ inbound listener on queue '" + queue + "'");
	}

	private void onMessage(byte[] body) {
		try {
			GMessageEnvelope<?> envelope = codec.deserialize(body);
			// Route as-is: the envelope's own source/target module and component drive
			// the in-memory broker; no field is rewritten here.
			broker.accept(envelope);
		} catch (Throwable th) {
			LOGGER.error("Error handling inbound RabbitMQ message", th);
		}
	}

	/**
	 * Stops the listener container on shutdown.
	 */
	@PreDestroy
	public void stop() {
		if (container != null) {
			try {
				container.stop();
			} catch (Throwable th) {
				LOGGER.warn("Error stopping RabbitMQ inbound listener container", th);
			}
			container = null;
		}
	}
}
