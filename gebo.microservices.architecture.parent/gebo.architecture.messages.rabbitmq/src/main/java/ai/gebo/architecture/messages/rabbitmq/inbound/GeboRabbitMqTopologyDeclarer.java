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

/**
 * Declares the AMQP topology this microservice needs: the shared exchange plus
 * its <b>own</b> inbound queue, bound to the exchange with a routing key equal to
 * its {@code localMicroserviceId}.
 *
 * <p>
 * Each microservice declares only the queue it consumes — peers declare theirs —
 * so the whole architecture's topology emerges from every instance declaring its
 * own inbound side. Declaration is a no-op when {@code declareTopology} is
 * {@code false}, assuming the topology is provisioned externally.
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
	 * Declares the exchange and this microservice's inbound queue and binding.
	 */
	public void declareIfEnabled() {
		if (!properties.isDeclareTopology()) {
			LOGGER.info("RabbitMQ topology declaration disabled (declareTopology=false)");
			return;
		}
		Exchange exchange = buildExchange();
		rabbitAdmin.declareExchange(exchange);
		LOGGER.info("Declared RabbitMQ exchange '" + exchange.getName() + "' of type '" + exchange.getType() + "'");

		String microserviceId = properties.getLocalMicroserviceId();
		String queueName = properties.effectiveInboundQueue();
		if (queueName == null || queueName.isBlank() || microserviceId == null || microserviceId.isBlank()) {
			LOGGER.warn("localMicroserviceId is unset; skipping inbound queue declaration");
			return;
		}
		rabbitAdmin.declareQueue(new Queue(queueName));
		rabbitAdmin.declareBinding(
				new Binding(queueName, DestinationType.QUEUE, exchange.getName(), microserviceId, null));
		LOGGER.info("Declared inbound queue '" + queueName + "' bound to '" + exchange.getName()
				+ "' with routing key '" + microserviceId + "'");
	}

	private Exchange buildExchange() {
		String name = properties.getExchange();
		String type = properties.getExchangeType() != null ? properties.getExchangeType().toLowerCase() : "direct";
		return switch (type) {
		case "topic" -> ExchangeBuilder.topicExchange(name).durable(true).build();
		case "fanout" -> ExchangeBuilder.fanoutExchange(name).durable(true).build();
		default -> ExchangeBuilder.directExchange(name).durable(true).build();
		};
	}
}
