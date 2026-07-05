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

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.external.ExternalEmitterIfaceData;
import ai.gebo.application.messaging.external.IGExternalMessageEmitterProvider;
import ai.gebo.application.messaging.external.IGExternalMessageEmitterProviderSource;
import ai.gebo.architecture.messages.rabbitmq.config.GeboRabbitMqMessagingProperties;
import ai.gebo.architecture.messages.rabbitmq.config.GeboRabbitMqMessagingProperties.BridgeDefinition;

/**
 * Provides the inbound (RabbitMQ -&gt; local broker) emitter bridges declared in
 * {@link GeboRabbitMqMessagingProperties#getEmitters()}.
 *
 * <p>
 * Discovered by {@code MessageBrokeringAssembler} through the
 * {@code IGExternalMessageEmitterProviderSource} SPI: each returned provider is
 * registered in the in-memory broker as an {@code IGMessageEmitter}. The actual
 * consumption from RabbitMQ is driven by {@code RabbitMqInboundBridge}.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Component
@ConditionalOnProperty(prefix = GeboRabbitMqMessagingProperties.PREFIX, name = "enabled", havingValue = "true")
public class RabbitMqExternalMessageEmitterProviderSource implements IGExternalMessageEmitterProviderSource {

	private final List<IGExternalMessageEmitterProvider> emitters;

	/**
	 * @param properties the RabbitMQ messaging bindings
	 */
	public RabbitMqExternalMessageEmitterProviderSource(GeboRabbitMqMessagingProperties properties) {
		this.emitters = new ArrayList<>();
		for (BridgeDefinition definition : properties.getEmitters()) {
			ExternalEmitterIfaceData config = new ExternalEmitterIfaceData();
			config.setMessagingModuleId(definition.getMessagingModuleId());
			config.setMessagingSystemId(definition.getMessagingSystemId());
			config.setComponentType(definition.getComponentType());
			config.setEmittedPayloadTypes(definition.getPayloadTypes());
			this.emitters.add(new RabbitMqExternalMessageEmitter(config));
		}
	}

	@Override
	public String getId() {
		return "rabbitmq-external-emitter-source";
	}

	@Override
	public List<IGExternalMessageEmitterProvider> getExternalEmitters() {
		return emitters;
	}
}
