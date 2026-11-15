/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.cluster.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import ai.gebo.microservices.cluster.GeboClusterParticipants;
import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.topology.GeboCurrentMicroservice;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;
import ai.gebo.microservices.topology.config.GeboMicroservicesTopologyAutoConfiguration;

/**
 * {@code gebo.microservices.topology.enabled}: on by default, and off it takes the
 * cluster membership with it, while the caller-token propagation the clients need
 * stays.
 */
class GeboTopologySwitchTest {

	private final ApplicationContextRunner runner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(GeboMicroservicesTopologyAutoConfiguration.class,
					GeboClusterCommonsAutoConfiguration.class))
			.withBean(DiscoveryClient.class, () -> mock(DiscoveryClient.class));

	@Test
	void theTopologyIsOnByDefault() {
		runner.run(context -> assertThat(context).hasNotFailed()
				.hasSingleBean(GeboMicroservicesTopology.class)
				.hasSingleBean(GeboMicroserviceUrlResolver.class)
				.hasSingleBean(GeboCurrentMicroservice.class)
				.hasSingleBean(GeboClusterParticipants.class)
				.hasSingleBean(IGeboCallerTokenPropagator.class));
	}

	@Test
	void switchedOffThereIsNoTopologyAndNoClusterMembership() {
		runner.withPropertyValues("gebo.microservices.topology.enabled=false")
				.run(context -> assertThat(context).hasNotFailed()
						.doesNotHaveBean(GeboMicroservicesTopology.class)
						.doesNotHaveBean(GeboMicroserviceUrlResolver.class)
						.doesNotHaveBean(GeboCurrentMicroservice.class)
						.doesNotHaveBean(GeboClusterParticipants.class)
						.hasSingleBean(IGeboCallerTokenPropagator.class));
	}
}
