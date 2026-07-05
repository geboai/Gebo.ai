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
import ai.gebo.architecture.messages.rabbitmq.config.GeboRabbitMqMessagingProperties.RemoteEndpoint;

/**
 * Registers, as local broker emitters, the remote endpoints declared in
 * {@link GeboRabbitMqMessagingProperties#getRemoteEmitters()}.
 *
 * <p>
 * Each entry is a component living in another microservice that may send
 * messages into this one. Registering it as an {@code IGMessageEmitter} — under
 * its own {@code moduleId}/{@code componentId} — is what allows the local broker
 * to accept an inbound envelope whose {@code sourceModule}/{@code sourceComponent}
 * matches it and route it, unchanged, to the local
 * {@code targetModule}/{@code targetComponent} receiver. There is one emitter per
 * remote endpoint, so a single remote microservice contributes as many emitters
 * as it exposes.
 * </p>
 *
 * <p>
 * Discovered by {@code MessageBrokeringAssembler} through the
 * {@code IGExternalMessageEmitterProviderSource} SPI; the actual consumption from
 * RabbitMQ is driven by {@code RabbitMqInboundBridge}.
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
		for (RemoteEndpoint endpoint : properties.getRemoteEmitters()) {
			ExternalEmitterIfaceData config = new ExternalEmitterIfaceData();
			config.setMessagingModuleId(endpoint.getModuleId());
			config.setMessagingSystemId(endpoint.getComponentId());
			config.setComponentType(endpoint.getComponentType());
			config.setEmittedPayloadTypes(endpoint.getPayloadTypes());
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
