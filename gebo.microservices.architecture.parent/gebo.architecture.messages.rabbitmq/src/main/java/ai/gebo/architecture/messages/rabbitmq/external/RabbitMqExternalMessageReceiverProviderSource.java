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

import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.external.ExternalReceiverIfaceData;
import ai.gebo.application.messaging.external.IGExternalMessageReceiverProvider;
import ai.gebo.application.messaging.external.IGExternalMessageReceiverProviderSource;
import ai.gebo.architecture.messages.rabbitmq.codec.GMessageEnvelopeCodec;
import ai.gebo.architecture.messages.rabbitmq.config.GeboRabbitMqMessagingProperties;
import ai.gebo.microservices.topology.GeboCurrentMicroservice;
import ai.gebo.microservices.topology.GeboMicroservice;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;

/**
 * Registers, as local broker receivers, every messaging component
 * ({@code messagingModuleId} / {@code messagingSystemId}) hosted by a
 * <b>remote</b> microservice - i.e. every microservice in the shared
 * {@link GeboMicroservicesTopology} that is not the local one.
 *
 * <p>
 * The set of remote endpoints is <b>deduced from the topology</b>, not
 * configured by hand: the local microservice is resolved (from
 * {@code ai.gebo.messaging.rabbitmq.local-microservice-id}, falling back to
 * {@code spring.application.name}), every other microservice is remote, and each
 * {@code (moduleId, systemId)} it declares becomes an external receiver under that
 * same identity, whose AMQP routing key is that remote microservice's id (which
 * its inbound queue is bound with).
 * </p>
 *
 * <p>
 * Registering a remote component as an {@code IGMessageReceiver} is what makes the
 * local broker route a matching outbound envelope to it; the receiver then
 * serializes the envelope and publishes it to the queue of the microservice that
 * hosts that module. Because the topology carries no payload-type contracts, these
 * receivers are marked {@code acceptEveryPayloadType} so any envelope targeted at
 * the remote endpoint is bridged.
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
	 * @param topology       the shared microservices topology
	 * @param current        the running service identity (from spring.application.name)
	 * @param properties     the RabbitMQ messaging bindings
	 * @param rabbitTemplate template used by the receivers to publish
	 * @param codec          envelope serializer
	 */
	public RabbitMqExternalMessageReceiverProviderSource(GeboMicroservicesTopology topology,
			GeboCurrentMicroservice current, GeboRabbitMqMessagingProperties properties, RabbitTemplate rabbitTemplate,
			GMessageEnvelopeCodec codec) {
		this.receivers = new ArrayList<>();

		String localId = RabbitMqTopologyBridgeSupport.resolveLocalMicroserviceId(properties, current);
		if (localId == null || localId.isBlank()) {
			LOGGER.warn("No local microservice id resolved (set " + GeboRabbitMqMessagingProperties.PREFIX
					+ ".local-microservice-id or spring.application.name); no remote receivers deduced from topology");
			return;
		}

		for (GeboMicroservice microservice : topology.microservices()) {
			if (localId.equals(microservice.getMicroserviceId())) {
				continue; // skip the local microservice - its components are local, not external
			}
			// The remote microservice id is the AMQP routing key its inbound queue is bound with.
			String routingKey = microservice.getMicroserviceId();
			microservice.getMessagingModules().forEach((moduleId, systemIds) -> {
				for (String systemId : systemIds) {
					ExternalReceiverIfaceData config = new ExternalReceiverIfaceData();
					config.setMessagingModuleId(moduleId);
					config.setMessagingSystemId(systemId);
					config.setComponentType(SystemComponentType.APPLICATION_COMPONENT);
					// Topology carries no payload types: accept anything targeted at this remote endpoint.
					config.setAcceptEveryPayloadType(true);
					receivers.add(new RabbitMqExternalMessageReceiver(config, rabbitTemplate, codec,
							properties.getExchange(), routingKey));
				}
			});
		}
		LOGGER.info("Deduced " + receivers.size() + " remote external receivers from topology (local='" + localId + "')");
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
