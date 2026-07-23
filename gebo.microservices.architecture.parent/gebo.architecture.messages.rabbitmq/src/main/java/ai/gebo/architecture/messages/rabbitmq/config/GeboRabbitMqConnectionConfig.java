/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.messages.rabbitmq.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Creates the RabbitMQ infrastructure beans ({@link ConnectionFactory},
 * {@link RabbitTemplate} and {@link RabbitAdmin}) from the external
 * {@link GeboRabbitMqMessagingProperties} bindings.
 *
 * <p>
 * These beans are only created when
 * {@code ai.gebo.messaging.rabbitmq.enabled=true}. By exposing our own
 * {@link ConnectionFactory} we deliberately keep this integration independent
 * from Spring Boot's {@code spring.rabbitmq.*} auto-configuration (Boot's
 * {@code RabbitAutoConfiguration} backs off on {@code @ConditionalOnMissingBean}).
 * </p>
 *
 * Gebo.ai comment agent
 */
@Configuration
@ConditionalOnProperty(prefix = GeboRabbitMqMessagingProperties.PREFIX, name = "enabled", havingValue = "true")
public class GeboRabbitMqConnectionConfig {

	/**
	 * Builds the caching connection factory pointing at the externally configured
	 * broker.
	 *
	 * @param properties the RabbitMQ messaging bindings
	 * @return a configured {@link CachingConnectionFactory}
	 */
	@Bean
	@ConditionalOnMissingBean(ConnectionFactory.class)
	public CachingConnectionFactory geboRabbitMqConnectionFactory(GeboRabbitMqMessagingProperties properties) {
		GeboRabbitMqMessagingProperties.Connection connection = properties.getConnection();
		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setHost(connection.getHost());
		factory.setPort(connection.getPort());
		factory.setVirtualHost(connection.getVirtualHost());
		factory.setUsername(connection.getUsername());
		factory.setPassword(connection.getPassword());
		return factory;
	}

	/**
	 * Template used by the outbound (receiver) bridges to publish envelopes.
	 *
	 * @param connectionFactory the shared connection factory
	 * @return a {@link RabbitTemplate}
	 */
	@Bean
	@ConditionalOnMissingBean(RabbitTemplate.class)
	public RabbitTemplate geboRabbitMqTemplate(ConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}

	/**
	 * Admin used to declare the exchange, queues and bindings of the configured
	 * bridges.
	 *
	 * @param connectionFactory the shared connection factory
	 * @return a {@link RabbitAdmin}
	 */
	@Bean
	@ConditionalOnMissingBean(RabbitAdmin.class)
	public RabbitAdmin geboRabbitMqAdmin(ConnectionFactory connectionFactory) {
		RabbitAdmin admin = new RabbitAdmin(connectionFactory);
		// Topology is declared explicitly by GeboRabbitMqTopologyDeclarer after startup.
		admin.setAutoStartup(false);
		return admin;
	}
}
