/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.messages.rabbitmq.inbound;

import java.util.ArrayList;
import java.util.List;

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
import ai.gebo.architecture.messages.rabbitmq.config.GeboRabbitMqMessagingProperties.BridgeDefinition;
import jakarta.annotation.PreDestroy;

/**
 * Drives the inbound side of the RabbitMQ integration: for every configured
 * emitter bridge it starts a {@link SimpleMessageListenerContainer} that
 * consumes the bound queue, deserializes each message into a
 * {@link GMessageEnvelope} and injects it into the local {@link IGMessageBroker}
 * for routing to a local receiver.
 *
 * <p>
 * Containers are started on {@link ApplicationReadyEvent} — i.e. after
 * {@code MessageBrokeringAssembler} has registered the external emitters in the
 * broker (which happens on {@code ContextRefreshedEvent}) — so inbound
 * envelopes always find their source emitter already registered.
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

	private final List<SimpleMessageListenerContainer> containers = new ArrayList<>();

	public RabbitMqInboundBridge(GeboRabbitMqMessagingProperties properties, ConnectionFactory connectionFactory,
			IGMessageBroker broker, GMessageEnvelopeCodec codec, GeboRabbitMqTopologyDeclarer topologyDeclarer) {
		this.properties = properties;
		this.connectionFactory = connectionFactory;
		this.broker = broker;
		this.codec = codec;
		this.topologyDeclarer = topologyDeclarer;
	}

	/**
	 * Declares the topology and starts one listener container per inbound bridge.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void start() {
		topologyDeclarer.declareIfEnabled();
		for (BridgeDefinition definition : properties.getEmitters()) {
			String queue = definition.getQueue();
			if (queue == null || queue.isBlank()) {
				LOGGER.warn("Inbound bridge for " + definition.getMessagingModuleId() + "."
						+ definition.getMessagingSystemId() + " has no queue configured, skipping");
				continue;
			}
			SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
			container.setQueueNames(queue);
			container.setMessageListener(message -> onMessage(definition, message.getBody()));
			container.start();
			containers.add(container);
			LOGGER.info("Started RabbitMQ inbound listener on queue '" + queue + "' -> broker as emitter "
					+ definition.getMessagingModuleId() + "." + definition.getMessagingSystemId());
		}
	}

	private void onMessage(BridgeDefinition definition, byte[] body) {
		try {
			GMessageEnvelope<?> envelope = codec.deserialize(body);
			// Ensure the source identity matches the registered emitter bridge so the
			// broker can resolve it, regardless of what the remote side set.
			envelope.setSourceModule(definition.getMessagingModuleId());
			envelope.setSourceComponent(definition.getMessagingSystemId());
			broker.accept(envelope);
		} catch (Throwable th) {
			LOGGER.error("Error handling inbound RabbitMQ message for emitter "
					+ definition.getMessagingModuleId() + "." + definition.getMessagingSystemId(), th);
		}
	}

	/**
	 * Stops all the listener containers on shutdown.
	 */
	@PreDestroy
	public void stop() {
		for (SimpleMessageListenerContainer container : containers) {
			try {
				container.stop();
			} catch (Throwable th) {
				LOGGER.warn("Error stopping RabbitMQ inbound listener container", th);
			}
		}
		containers.clear();
	}
}
