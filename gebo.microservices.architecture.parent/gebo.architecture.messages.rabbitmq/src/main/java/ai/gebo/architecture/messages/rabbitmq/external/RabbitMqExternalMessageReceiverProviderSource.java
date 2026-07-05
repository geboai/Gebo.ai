/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.messages.rabbitmq.external;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.external.ExternalReceiverIfaceData;
import ai.gebo.application.messaging.external.IGExternalMessageReceiverProvider;
import ai.gebo.application.messaging.external.IGExternalMessageReceiverProviderSource;
import ai.gebo.architecture.messages.rabbitmq.codec.GMessageEnvelopeCodec;
import ai.gebo.architecture.messages.rabbitmq.config.GeboRabbitMqMessagingProperties;
import ai.gebo.architecture.messages.rabbitmq.config.GeboRabbitMqMessagingProperties.RemoteEndpoint;

/**
 * Registers, as local broker receivers, the remote endpoints declared in
 * {@link GeboRabbitMqMessagingProperties#getRemoteReceivers()}.
 *
 * <p>
 * Each entry is a component living in another microservice this one may send
 * messages to. Registering it as an {@code IGMessageReceiver} — under its own
 * {@code moduleId}/{@code componentId} — is what makes the local broker route a
 * matching envelope to it; the receiver then serializes the envelope and
 * publishes it to the queue of the microservice that hosts that module. The
 * destination microservice is resolved once, at construction, from the
 * {@code microservices} map by the endpoint's module id.
 * </p>
 *
 * <p>
 * Discovered by {@code MessageBrokeringAssembler} through the
 * {@code IGExternalMessageReceiverProviderSource} SPI.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Component
@ConditionalOnProperty(prefix = GeboRabbitMqMessagingProperties.PREFIX, name = "enabled", havingValue = "true")
public class RabbitMqExternalMessageReceiverProviderSource implements IGExternalMessageReceiverProviderSource {

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqExternalMessageReceiverProviderSource.class);

	private final List<IGExternalMessageReceiverProvider> receivers;

	/**
	 * @param properties     the RabbitMQ messaging bindings
	 * @param rabbitTemplate template used by the receivers to publish
	 * @param codec          envelope serializer
	 */
	public RabbitMqExternalMessageReceiverProviderSource(GeboRabbitMqMessagingProperties properties,
			RabbitTemplate rabbitTemplate, GMessageEnvelopeCodec codec) {
		this.receivers = new ArrayList<>();
		for (RemoteEndpoint endpoint : properties.getRemoteReceivers()) {
			String microserviceId = properties.resolveMicroserviceIdForModule(endpoint.getModuleId());
			if (microserviceId == null) {
				// No mapping: fall back to the module id as routing key so the receiver is
				// still registered, but warn because delivery depends on external topology.
				microserviceId = endpoint.getModuleId();
				LOGGER.warn("No microservice mapping for module '" + endpoint.getModuleId()
						+ "'; using it as routing key for remote receiver " + endpoint.logicalName());
			}
			ExternalReceiverIfaceData config = new ExternalReceiverIfaceData();
			config.setMessagingModuleId(endpoint.getModuleId());
			config.setMessagingSystemId(endpoint.getComponentId());
			config.setComponentType(endpoint.getComponentType());
			config.setAcceptedPayloadTypes(endpoint.getPayloadTypes());
			config.setAcceptEveryPayloadType(endpoint.isAcceptEveryPayloadType());
			this.receivers.add(new RabbitMqExternalMessageReceiver(config, rabbitTemplate, codec,
					properties.getExchange(), microserviceId));
		}
	}

	@Override
	public String getId() {
		return "rabbitmq-external-receiver-source";
	}

	@Override
	public List<IGExternalMessageReceiverProvider> getExternalReceivers() {
		return receivers;
	}
}
