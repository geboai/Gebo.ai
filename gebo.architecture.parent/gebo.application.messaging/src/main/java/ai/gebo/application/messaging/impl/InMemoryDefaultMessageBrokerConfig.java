/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.application.messaging.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import ai.gebo.application.messaging.GeboCurrentApplication;
import ai.gebo.application.messaging.IGMessageBroker;
import lombok.AllArgsConstructor;

/**
 * Gebo.ai comment agent
 *
 * Configuration class for setting up the default message broker. If no other
 * message broker bean of type {@link IGMessageBroker} is defined, this
 * configuration provides an in-memory implementation.
 */
@Configuration
public class InMemoryDefaultMessageBrokerConfig {
	GeboCurrentApplication geboCurrentApp;
	final Environment environment;

	@AllArgsConstructor
	static class EnvironmenBasedGeboCurrentApplication implements GeboCurrentApplication {
		final Environment env;

		@Override
		public String getId() {

			return env.getProperty("spring.application.name", "local-app");
		}

		@Override
		public String getDescription() {

			return this.getId();
		}

		@Override
		public Integer getWebPort() {
			String inNumbers = env.getProperty("local.server.port", env.getProperty("server.port", ""));
			return inNumbers != null && inNumbers.trim().length() > 0 ? Integer.parseInt(inNumbers) : null;
		}

	}

	/**
	 * Default constructor for the configuration class.
	 */
	public InMemoryDefaultMessageBrokerConfig(@Autowired(required = false) GeboCurrentApplication geboCurrentApp,
			Environment environment) {
		this.geboCurrentApp = geboCurrentApp;
		this.environment = environment;
		if (this.geboCurrentApp == null) {
			this.geboCurrentApp = new EnvironmenBasedGeboCurrentApplication(environment);
		}

	}

	/**
	 * Creates a singleton bean of type {@link IGMessageBroker}. The bean will only
	 * be created if no other bean of type IGMessageBroker is present in the Spring
	 * context. Utilizes an in-memory implementation for message brokering.
	 * 
	 * @return An instance of {@link InMemoryDefaultMessageBrokerImpl}
	 */
	@Bean
	@Scope("singleton")
	@ConditionalOnMissingBean(IGMessageBroker.class)
	public IGMessageBroker inMemoryDefaultMessageBrokerBean() {
		return new InMemoryDefaultMessageBrokerImpl(geboCurrentApp);
	}

}