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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.external.ExternalEmitterIfaceData;
import ai.gebo.application.messaging.external.IGExternalMessageEmitterProvider;
import ai.gebo.application.messaging.external.IGExternalMessageEmitterProviderSource;
import ai.gebo.architecture.messages.rabbitmq.config.GeboRabbitMqMessagingProperties;
import ai.gebo.microservices.topology.GeboCurrentMicroservice;
import ai.gebo.microservices.topology.GeboMicroservice;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;

/**
 * Registers, as local broker emitters, every messaging component
 * ({@code messagingModuleId} / {@code messagingSystemId}) hosted by a
 * <b>remote</b> microservice - i.e. every microservice in the shared
 * {@link GeboMicroservicesTopology} that is not the local one.
 *
 * <p>
 * The set of remote endpoints is <b>deduced from the topology</b>, not
 * configured by hand: the local microservice is resolved (from
 * {@code ai.gebo.messaging.rabbitmq.local-microservice-id}, falling back to
 * {@code spring.application.name} via {@link GeboCurrentMicroservice}), every
 * other microservice is remote, and each {@code (moduleId, systemId)} it declares
 * becomes an external emitter under that same identity.
 * </p>
 *
 * <p>
 * Registering a remote component as an {@code IGMessageEmitter} is what allows the
 * local broker to accept an inbound envelope whose {@code sourceModule}/
 * {@code sourceComponent} matches it and route it, unchanged, to the local
 * {@code targetModule}/{@code targetComponent} receiver. Because the topology
 * carries no payload-type contracts, these emitters are marked
 * {@code emitEveryPayloadType} so the broker does not reject inbound envelopes on
 * the emitter allow-list (the mirror of the receiver's accept-every).
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

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqExternalMessageEmitterProviderSource.class);

	private final List<IGExternalMessageEmitterProvider> emitters;

	/**
	 * @param topology   the shared microservices topology
	 * @param current    the running service identity (from spring.application.name)
	 * @param properties the RabbitMQ messaging bindings
	 */
	public RabbitMqExternalMessageEmitterProviderSource(GeboMicroservicesTopology topology,
			GeboCurrentMicroservice current, GeboRabbitMqMessagingProperties properties) {
		this.emitters = new ArrayList<>();

		String localId = RabbitMqTopologyBridgeSupport.resolveLocalMicroserviceId(properties, current);
		if (localId == null || localId.isBlank()) {
			LOGGER.warn("No local microservice id resolved (set " + GeboRabbitMqMessagingProperties.PREFIX
					+ ".local-microservice-id or spring.application.name); no remote emitters deduced from topology");
			return;
		}

		for (GeboMicroservice microservice : topology.microservices()) {
			if (localId.equals(microservice.getMicroserviceId())) {
				continue; // skip the local microservice - its components are local, not external
			}
			microservice.getMessagingModules().forEach((moduleId, systemIds) -> {
				for (String systemId : systemIds) {
					ExternalEmitterIfaceData config = new ExternalEmitterIfaceData();
					config.setMessagingModuleId(moduleId);
					config.setMessagingSystemId(systemId);
					config.setComponentType(SystemComponentType.APPLICATION_COMPONENT);
					// Topology carries no payload types: let this remote source emit any type.
					config.setEmitEveryPayloadType(true);
					emitters.add(new RabbitMqExternalMessageEmitter(config));
				}
			});
		}
		LOGGER.info("Deduced " + emitters.size() + " remote external emitters from topology (local='" + localId + "')");
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
