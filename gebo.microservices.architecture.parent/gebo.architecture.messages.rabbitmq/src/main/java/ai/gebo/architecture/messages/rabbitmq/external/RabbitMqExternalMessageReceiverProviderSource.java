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

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.external.ExternalReceiverIfaceData;
import ai.gebo.application.messaging.external.IGExternalMessageReceiverProvider;
import ai.gebo.application.messaging.external.IGExternalMessageReceiverProviderSource;
import ai.gebo.architecture.messages.rabbitmq.codec.GMessageEnvelopeCodec;
import ai.gebo.architecture.messages.rabbitmq.config.GeboRabbitMqMessagingProperties;
import ai.gebo.architecture.messages.rabbitmq.config.GeboRabbitMqMessagingProperties.BridgeDefinition;

/**
 * Provides the outbound (local broker -&gt; RabbitMQ) receiver bridges declared
 * in {@link GeboRabbitMqMessagingProperties#getReceivers()}.
 *
 * <p>
 * Discovered by {@code MessageBrokeringAssembler} through the
 * {@code IGExternalMessageReceiverProviderSource} SPI: each returned provider is
 * registered in the in-memory broker as an {@code IGMessageReceiver}.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Component
@ConditionalOnProperty(prefix = GeboRabbitMqMessagingProperties.PREFIX, name = "enabled", havingValue = "true")
public class RabbitMqExternalMessageReceiverProviderSource implements IGExternalMessageReceiverProviderSource {

	private final List<IGExternalMessageReceiverProvider> receivers;

	/**
	 * @param properties     the RabbitMQ messaging bindings
	 * @param rabbitTemplate template used by the receivers to publish
	 * @param codec          envelope serializer
	 */
	public RabbitMqExternalMessageReceiverProviderSource(GeboRabbitMqMessagingProperties properties,
			RabbitTemplate rabbitTemplate, GMessageEnvelopeCodec codec) {
		this.receivers = new ArrayList<>();
		for (BridgeDefinition definition : properties.getReceivers()) {
			ExternalReceiverIfaceData config = new ExternalReceiverIfaceData();
			config.setMessagingModuleId(definition.getMessagingModuleId());
			config.setMessagingSystemId(definition.getMessagingSystemId());
			config.setComponentType(definition.getComponentType());
			config.setAcceptedPayloadTypes(definition.getPayloadTypes());
			config.setAcceptEveryPayloadType(definition.isAcceptEveryPayloadType());
			this.receivers.add(new RabbitMqExternalMessageReceiver(config, rabbitTemplate, codec,
					properties.getExchange(), definition.effectiveRoutingKey()));
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
