/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.models.replication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Conditional;

import ai.gebo.architecture.hazelcast.IGModelsReplicationClusterTopologyProvider;
import ai.gebo.microservices.models.replication.DiscoveryClientClusterTopologyProvider;
import ai.gebo.microservices.models.replication.ModelsReplicationClusterParticipationCondition;
import ai.gebo.microservices.models.replication.ModelsReplicationClusterProperties;
import ai.gebo.microservices.models.replication.TopologyModelsReplicationClusterTopologyProvider;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;
import ai.gebo.microservices.topology.GeboModelsReplicationParticipants;
import ai.gebo.microservices.topology.config.GeboMicroservicesTopologyAutoConfiguration;

/**
 * Initialization structure for the LLM models-replication cache in a
 * microservice deployment.
 * <p>
 * When the running service is one of the configured
 * {@code gebo.models.replication.participants}
 * ({@link ModelsReplicationClusterParticipationCondition}), it contributes an
 * {@link IGModelsReplicationClusterTopologyProvider} bean built from the shared
 * {@link GeboMicroservicesTopology}. The presence of that bean is what starts the
 * Hazelcast-backed cache (see {@code ai.gebo.architecture.hazelcast}), so a
 * non-participating service — one absent from the list — registers no provider
 * and runs the models DAOs standalone.
 * <p>
 * Activated as a Spring Boot auto-configuration (its dependency being on the
 * classpath is enough); it composes on top of
 * {@code GeboMicroservicesTopologyAutoConfiguration}, which supplies the
 * {@link GeboMicroservicesTopology} bean.
 */
@AutoConfiguration(after = GeboMicroservicesTopologyAutoConfiguration.class)
@EnableConfigurationProperties(ModelsReplicationClusterProperties.class)
@Conditional(ModelsReplicationClusterParticipationCondition.class)
public class ModelsReplicationClusterAutoConfiguration {

	/**
	 * Seeds the cluster from <b>live service discovery</b> when a
	 * {@link DiscoveryClient} is available - which, in a real deployment, it always
	 * is. This sees every registered replica and its real address, where the
	 * name-based provider below can only guess one host per service from DNS.
	 */
	@Bean
	@ConditionalOnClass(DiscoveryClient.class)
	@ConditionalOnBean(DiscoveryClient.class)
	@ConditionalOnMissingBean(IGModelsReplicationClusterTopologyProvider.class)
	public IGModelsReplicationClusterTopologyProvider discoveryClientClusterTopologyProvider(
			DiscoveryClient discoveryClient, GeboMicroservicesTopology topology,
			ModelsReplicationClusterProperties properties,
			@Value("${spring.application.name:}") String applicationName) {
		return new DiscoveryClientClusterTopologyProvider(discoveryClient, topology, properties, applicationName);
	}

	/**
	 * Fallback for a deployment with no service discovery (a pinned run, a test): seeds
	 * from the configured names. Backs off whenever the discovery-backed provider above
	 * has already been published.
	 */
	@Bean
	@ConditionalOnMissingBean(IGModelsReplicationClusterTopologyProvider.class)
	public IGModelsReplicationClusterTopologyProvider modelsReplicationClusterTopologyProvider(
			GeboMicroservicesTopology topology, GeboModelsReplicationParticipants participants,
			ModelsReplicationClusterProperties properties,
			@Value("${spring.application.name:}") String applicationName) {
		return new TopologyModelsReplicationClusterTopologyProvider(topology, participants, properties, applicationName);
	}
}
