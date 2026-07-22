/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.cluster.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.CommonsClientAutoConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClientAutoConfiguration;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration;
import org.springframework.context.annotation.Bean;

import ai.gebo.microservices.cluster.GeboClusterParticipants;
import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.cluster.auth.SecurityContextCallerTokenPropagator;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;
import ai.gebo.microservices.topology.config.GeboMicroservicesTopologyAutoConfiguration;
import ai.gebo.security.services.IGeboSystemUserService;

/**
 * The two halves of a cluster-internal call, published once for every
 * client/controller pair that needs them.
 *
 * Gebo.ai comment agent
 */
@AutoConfiguration(after = { GeboMicroservicesTopologyAutoConfiguration.class, SimpleDiscoveryClientAutoConfiguration.class,
		CompositeDiscoveryClientAutoConfiguration.class, CommonsClientAutoConfiguration.class })
@EnableConfigurationProperties(GeboClusterParticipantsProperties.class)
public class GeboClusterCommonsAutoConfiguration {

	/**
	 * The live cluster membership every cluster-internal guard checks against.
	 * Requires a {@link DiscoveryClient}: membership must be <i>discovered</i>, never
	 * merely configured. Absent one, no participants bean exists and the guards that
	 * depend on it never register - so their controllers do not either.
	 *
	 * <p>
	 * The class-level {@code after} above is load-bearing: without it, this
	 * auto-configuration can sort before the ones that actually publish a
	 * {@link DiscoveryClient} bean, so {@code @ConditionalOnBean(DiscoveryClient.class)}
	 * finds nothing even when one is registered (a stub in tests, Eureka's in
	 * production) - silently skipping this bean and every cluster surface downstream
	 * of it.
	 * </p>
	 */
	@Bean
	@ConditionalOnClass(DiscoveryClient.class)
	@ConditionalOnBean(DiscoveryClient.class)
	@ConditionalOnMissingBean
	public GeboClusterParticipants geboClusterParticipants(DiscoveryClient discoveryClient,
			GeboMicroservicesTopology topology, GeboClusterParticipantsProperties properties) {
		return new GeboClusterParticipants(discoveryClient, topology, properties.getExtraServiceIds(),
				properties.getAdditionalAllowedAddresses(), properties.getCacheTtl());
	}

	/**
	 * Forwards the caller's token, falling back to a freshly minted token for the
	 * platform's system identity when the calling thread has none (background work:
	 * startup, model replication, MCP reconnects, schedulers).
	 *
	 * <p>
	 * {@link IGeboSystemUserService} is taken through an {@link ObjectProvider} rather
	 * than injected: a service without the security implementation on its classpath
	 * still gets working request-thread calls, and its background calls fail loudly
	 * (with an explanatory warning) instead of the context refusing to start.
	 * </p>
	 */
	@Bean
	@ConditionalOnMissingBean(IGeboCallerTokenPropagator.class)
	public IGeboCallerTokenPropagator geboCallerTokenPropagator(
			ObjectProvider<IGeboSystemUserService> systemUserService) {
		return new SecurityContextCallerTokenPropagator(() -> {
			IGeboSystemUserService service = systemUserService.getIfAvailable();
			return service == null ? null : service.createToken();
		});
	}
}
