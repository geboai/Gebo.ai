/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.messages.rabbitmq.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.application.messaging.SystemComponentType;
import lombok.Data;

/**
 * External configuration bindings describing how this microservice bridges its
 * local in-memory messaging system and broker
 * ({@code ai.gebo.application.messaging}) with the rest of the architecture over
 * RabbitMQ.
 *
 * <h2>Routing model</h2>
 *
 * A message envelope already carries the full routing information it needs:
 * {@code sourceModule}/{@code sourceComponent} (the emitter) and
 * {@code targetModule}/{@code targetComponent} (the receiver). The in-memory
 * broker routes purely on those fields, and a single microservice hosts
 * <b>many</b> emitters and receivers spread across several modules and
 * components — so the bridge is <b>not</b> pinned to a fixed module/system.
 *
 * <p>
 * The unit of addressing over RabbitMQ is therefore the <b>microservice</b>, not
 * the individual endpoint:
 * </p>
 * <ul>
 * <li>Each microservice consumes exactly one inbound queue, bound to the
 * exchange with a routing key equal to its own {@link #localMicroserviceId}.</li>
 * <li>To deliver an outbound envelope, its {@code targetModule} is resolved —
 * through the configured {@link #microservices} map — to the microservice that
 * hosts that module, and the envelope is published with that microservice's id
 * as the routing key, landing in its inbound queue.</li>
 * <li>On the receiving side the envelope is injected into the local broker
 * unchanged; the broker routes it to the local
 * {@code targetModule}/{@code targetComponent} receiver.</li>
 * </ul>
 *
 * <p>
 * The whole RabbitMQ integration is inert unless {@code enabled} is set to
 * {@code true}; every RabbitMQ bean of this module is guarded by a
 * {@code @ConditionalOnProperty} on the {@code enabled} flag of this prefix.
 * </p>
 *
 * Gebo.ai comment agent
 */
@Configuration
@ConfigurationProperties(prefix = GeboRabbitMqMessagingProperties.PREFIX)
@Data
public class GeboRabbitMqMessagingProperties {

	/** Configuration prefix for all the RabbitMQ messaging bindings. */
	public static final String PREFIX = "ai.gebo.messaging.rabbitmq";

	/**
	 * Master switch. When {@code false} (default) no RabbitMQ bean is created and
	 * the local memory broker keeps working stand-alone.
	 */
	private boolean enabled = false;

	/** Broker connection coordinates. */
	private Connection connection = new Connection();

	/** Name of the AMQP exchange shared by the whole architecture. */
	private String exchange = "gebo.messaging";

	/**
	 * Exchange type. Defaults to {@code direct} because messages are addressed by
	 * an exact microservice id used as the routing key. May be {@code topic} or
	 * {@code fanout} for advanced topologies.
	 */
	private String exchangeType = "direct";

	/**
	 * When {@code true} (default) this microservice declares the exchange plus its
	 * own inbound queue and binding at startup through a {@code RabbitAdmin}.
	 */
	private boolean declareTopology = true;

	/**
	 * Identity of <b>this</b> microservice. It is used both as the routing key its
	 * inbound queue is bound with and, unless {@link #inboundQueue} overrides it, as
	 * the inbound queue name.
	 */
	private String localMicroserviceId;

	/**
	 * Optional explicit inbound queue name. Defaults to {@link #localMicroserviceId}
	 * when left unset.
	 */
	private String inboundQueue;

	/**
	 * The architecture map: every remote microservice reachable over RabbitMQ and
	 * the messaging modules it hosts. Used to resolve an outbound envelope's
	 * {@code targetModule} to the microservice (routing key) that owns it.
	 */
	private List<RemoteMicroservice> microservices = new ArrayList<>();

	/**
	 * Remote emitters (endpoints living in other microservices) that may send
	 * messages <b>into</b> this microservice. Each one is registered in the local
	 * broker as an {@code IGMessageEmitter} so inbound envelopes whose source
	 * matches are accepted and routed to their local target receiver.
	 */
	private List<RemoteEndpoint> remoteEmitters = new ArrayList<>();

	/**
	 * Remote receivers (endpoints living in other microservices) that this
	 * microservice may send messages <b>to</b>. Each one is registered in the local
	 * broker as an {@code IGMessageReceiver} that, when a local emitter targets it,
	 * serializes the envelope and publishes it to the owning microservice's queue.
	 */
	private List<RemoteEndpoint> remoteReceivers = new ArrayList<>();

	/**
	 * Effective inbound queue name (falls back to {@link #localMicroserviceId}).
	 *
	 * @return the queue this microservice consumes, or {@code null} if no identity
	 *         is configured
	 */
	public String effectiveInboundQueue() {
		if (inboundQueue != null && !inboundQueue.isBlank()) {
			return inboundQueue;
		}
		return localMicroserviceId;
	}

	/**
	 * Resolves which remote microservice hosts the given messaging module.
	 *
	 * @param moduleId the target module id carried by an envelope
	 * @return the owning microservice id, or {@code null} when no mapping is
	 *         configured for that module
	 */
	public String resolveMicroserviceIdForModule(String moduleId) {
		if (moduleId == null) {
			return null;
		}
		for (RemoteMicroservice microservice : microservices) {
			if (microservice.getModules() != null && microservice.getModules().contains(moduleId)) {
				return microservice.getMicroserviceId();
			}
		}
		return null;
	}

	/**
	 * Connection coordinates for the RabbitMQ broker. Kept independent from Spring
	 * Boot's own {@code spring.rabbitmq.*} so this integration can point at a
	 * dedicated broker.
	 */
	@Data
	public static class Connection {
		private String host = "localhost";
		private int port = 5672;
		private String virtualHost = "/";
		private String username = "guest";
		private String password = "guest";
	}

	/**
	 * A remote microservice and the messaging modules it hosts. This is the map
	 * used to address outbound envelopes by resolving their {@code targetModule} to
	 * the microservice that owns it.
	 */
	@Data
	public static class RemoteMicroservice {

		/** Remote microservice id; used as the AMQP routing key to reach it. */
		private String microserviceId;

		/** Messaging module ids hosted by this microservice. */
		private List<String> modules = new ArrayList<>();
	}

	/**
	 * A messaging endpoint (a module + component pair) living in a remote
	 * microservice. The same shape describes both a remote emitter and a remote
	 * receiver; only the payload-type semantics differ (see the field docs).
	 */
	@Data
	public static class RemoteEndpoint {

		/** Messaging module id of the remote endpoint. */
		private String moduleId;

		/** Messaging system (component) id of the remote endpoint. */
		private String componentId;

		/** Component type reported to the broker. */
		private SystemComponentType componentType = SystemComponentType.APPLICATION_COMPONENT;

		/**
		 * Payload types (fully qualified class names) this endpoint deals with. For a
		 * remote emitter these are the types it is allowed to emit (the broker rejects
		 * anything outside this list). For a remote receiver these are the accepted
		 * types, ignored when {@link #acceptEveryPayloadType} is {@code true}.
		 */
		private List<String> payloadTypes = new ArrayList<>();

		/**
		 * Remote-receiver only: accept every payload type regardless of
		 * {@link #payloadTypes}.
		 */
		private boolean acceptEveryPayloadType = false;

		/** Logical name used by the messaging identity, defaulting to module.component. */
		public String logicalName() {
			return moduleId + "." + componentId;
		}
	}
}
