/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.workflows.concentrator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.application.messaging.GAbstractTimedOutMessageReceiverFactory.TimedOutMessageReceiverFactoryConfig;

/**
 * Configuration for the externalized user-messages-concentrator-component,
 * bound from {@code ai.gebo.workflows-concentrator.config}. Carries only the
 * one receiver config {@code GeboCoreConfig} used to bundle alongside
 * core-module's own mongo-disposer config - the two no longer share a module,
 * so they no longer share a config class either.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.workflows-concentrator.config")
public class GeboWorkflowsConcentratorConfig {

	/** Configuration for the user messages receiver. */
	private TimedOutMessageReceiverFactoryConfig receiverConfig = new TimedOutMessageReceiverFactoryConfig();

	public GeboWorkflowsConcentratorConfig() {
		receiverConfig.setUseSenderThread(true);
		receiverConfig.setPoolCardinality(0);
		receiverConfig.setTimeout(10000l);
	}

	public TimedOutMessageReceiverFactoryConfig getReceiverConfig() {
		return receiverConfig;
	}

	public void setReceiverConfig(TimedOutMessageReceiverFactoryConfig receiverConfig) {
		this.receiverConfig = receiverConfig;
	}
}
