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
 * External configuration bindings that describe how the RabbitMQ broker bridges
 * with the local in-memory messaging system and broker
 * ({@code ai.gebo.application.messaging}).
 *
 * <p>
 * The whole RabbitMQ integration is inert unless {@code enabled} is set to
 * {@code true}. All the RabbitMQ beans of this module are guarded by a
 * {@code @ConditionalOnProperty} on the {@code enabled} flag of this same
 * prefix.
 * </p>
 *
 * <p>
 * Two independent bridge lists are declared:
 * </p>
 * <ul>
 * <li>{@code emitters} — <b>inbound</b> bridges. Each entry represents a remote
 * component (living on the other side of RabbitMQ) that is exposed to the local
 * broker as an {@code IGMessageEmitter}. A listener consumes the bound queue,
 * deserializes the envelope and injects it into the local broker so it can be
 * routed to a local receiver.</li>
 * <li>{@code receivers} — <b>outbound</b> bridges. Each entry is registered in
 * the local broker as an {@code IGMessageReceiver}; when a local emitter targets
 * it, the envelope is serialized and published to the RabbitMQ exchange with the
 * configured routing key.</li>
 * </ul>
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

	/** Name of the AMQP exchange used for every bridged message. */
	private String exchange = "gebo.messaging";

	/** Exchange type: {@code topic} (default), {@code direct} or {@code fanout}. */
	private String exchangeType = "topic";

	/**
	 * When {@code true} (default) the module declares the exchange, the queues and
	 * the bindings of every configured bridge at startup through a
	 * {@code RabbitAdmin}.
	 */
	private boolean declareTopology = true;

	/** Inbound bridges (RabbitMQ -&gt; local broker). */
	private List<BridgeDefinition> emitters = new ArrayList<>();

	/** Outbound bridges (local broker -&gt; RabbitMQ). */
	private List<BridgeDefinition> receivers = new ArrayList<>();

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
	 * Describes a single bridged component: the local messaging identity it is
	 * exposed with, plus the queue / routing key it is wired to on RabbitMQ.
	 */
	@Data
	public static class BridgeDefinition {

		/** Messaging module id this bridge is registered under in the broker. */
		private String messagingModuleId;

		/** Messaging system (component) id this bridge is registered under. */
		private String messagingSystemId;

		/** Component type reported to the broker. */
		private SystemComponentType componentType = SystemComponentType.APPLICATION_COMPONENT;

		/** AMQP queue name. For outbound bridges it is only used for topology declaration. */
		private String queue;

		/**
		 * AMQP routing key. Inbound: binding key of the queue on the exchange.
		 * Outbound: routing key used when publishing. Defaults to {@link #queue}.
		 */
		private String routingKey;

		/**
		 * Payload types (fully qualified class names) handled by this bridge. For an
		 * inbound emitter these are the types it is allowed to emit; for an outbound
		 * receiver these are the accepted types (ignored when
		 * {@link #acceptEveryPayloadType} is {@code true}).
		 */
		private List<String> payloadTypes = new ArrayList<>();

		/** Outbound only: accept every payload type regardless of {@link #payloadTypes}. */
		private boolean acceptEveryPayloadType = false;

		/** Effective routing key, falling back to the queue name when unset. */
		public String effectiveRoutingKey() {
			return (routingKey != null && !routingKey.isBlank()) ? routingKey : queue;
		}
	}
}
