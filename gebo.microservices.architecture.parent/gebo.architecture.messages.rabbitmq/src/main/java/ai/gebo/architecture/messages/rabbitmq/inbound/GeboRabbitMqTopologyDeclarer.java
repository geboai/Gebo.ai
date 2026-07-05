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
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.messages.rabbitmq.config.GeboRabbitMqMessagingProperties;
import ai.gebo.architecture.messages.rabbitmq.config.GeboRabbitMqMessagingProperties.BridgeDefinition;

/**
 * Declares the AMQP topology (exchange, queues, bindings) required by the
 * configured bridges, using a {@link RabbitAdmin}.
 *
 * <p>
 * Declaration is a no-op when {@code declareTopology} is {@code false},
 * assuming the topology is provisioned externally.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Component
@ConditionalOnProperty(prefix = GeboRabbitMqMessagingProperties.PREFIX, name = "enabled", havingValue = "true")
public class GeboRabbitMqTopologyDeclarer {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeboRabbitMqTopologyDeclarer.class);

	private final GeboRabbitMqMessagingProperties properties;
	private final RabbitAdmin rabbitAdmin;

	public GeboRabbitMqTopologyDeclarer(GeboRabbitMqMessagingProperties properties, RabbitAdmin rabbitAdmin) {
		this.properties = properties;
		this.rabbitAdmin = rabbitAdmin;
	}

	/**
	 * Declares the exchange and, for every configured bridge that names a queue,
	 * the queue and its binding to the exchange.
	 */
	public void declareIfEnabled() {
		if (!properties.isDeclareTopology()) {
			LOGGER.info("RabbitMQ topology declaration disabled (declareTopology=false)");
			return;
		}
		Exchange exchange = buildExchange();
		rabbitAdmin.declareExchange(exchange);
		LOGGER.info("Declared RabbitMQ exchange '" + exchange.getName() + "' of type '" + exchange.getType() + "'");

		for (BridgeDefinition emitter : properties.getEmitters()) {
			declareBridge(exchange.getName(), emitter);
		}
		for (BridgeDefinition receiver : properties.getReceivers()) {
			declareBridge(exchange.getName(), receiver);
		}
	}

	private void declareBridge(String exchangeName, BridgeDefinition definition) {
		String queueName = definition.getQueue();
		if (queueName == null || queueName.isBlank()) {
			return;
		}
		rabbitAdmin.declareQueue(new Queue(queueName));
		rabbitAdmin.declareBinding(new Binding(queueName, DestinationType.QUEUE, exchangeName,
				definition.effectiveRoutingKey(), null));
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Declared queue '" + queueName + "' bound to '" + exchangeName + "' with key '"
					+ definition.effectiveRoutingKey() + "'");
		}
	}

	private Exchange buildExchange() {
		String name = properties.getExchange();
		String type = properties.getExchangeType() != null ? properties.getExchangeType().toLowerCase() : "topic";
		return switch (type) {
		case "direct" -> ExchangeBuilder.directExchange(name).durable(true).build();
		case "fanout" -> ExchangeBuilder.fanoutExchange(name).durable(true).build();
		default -> ExchangeBuilder.topicExchange(name).durable(true).build();
		};
	}
}
