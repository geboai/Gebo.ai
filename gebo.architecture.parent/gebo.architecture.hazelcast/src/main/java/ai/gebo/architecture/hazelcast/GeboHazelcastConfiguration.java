/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.hazelcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * Generic wiring of the embedded Hazelcast member and of the
 * {@link IGClusterMessageBus} abstraction.
 * <p>
 * <ul>
 * <li>When {@code gebo.hazelcast.enabled=true} a {@link HazelcastInstance} is
 * created from {@link GeboHazelcastProperties} and a Hazelcast-backed
 * {@link GHazelcastClusterMessageBus} is exposed.</li>
 * <li>Otherwise (default) no Hazelcast member is started and a
 * {@link GNoOpClusterMessageBus} is exposed so cluster-aware code keeps working
 * on a single instance.</li>
 * </ul>
 * This class is picked up by the application-wide component scan of the
 * {@code ai.gebo} base package; there is no dependency on the specific feature
 * (LLMs, etc.) using the bus.
 */
@Configuration
@EnableConfigurationProperties(GeboHazelcastProperties.class)
public class GeboHazelcastConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeboHazelcastConfiguration.class);

	/**
	 * Builds the embedded Hazelcast member only when clustering is enabled.
	 */
	@Bean(destroyMethod = "shutdown")
	@ConditionalOnProperty(prefix = "gebo.hazelcast", name = "enabled", havingValue = "true")
	public HazelcastInstance geboHazelcastInstance(GeboHazelcastProperties properties) {
		Config config = new Config();
		config.setClusterName(properties.getClusterName());
		config.setInstanceName(properties.getInstanceName());

		NetworkConfig network = config.getNetworkConfig();
		network.setPort(properties.getPort());
		network.setPortAutoIncrement(properties.isPortAutoIncrement());

		JoinConfig join = network.getJoin();
		if (properties.getMembers() != null && !properties.getMembers().isEmpty()) {
			// Explicit TCP/IP discovery: deterministic, multicast off.
			join.getMulticastConfig().setEnabled(false);
			join.getTcpIpConfig().setEnabled(true);
			properties.getMembers().forEach(join.getTcpIpConfig()::addMember);
			LOGGER.info("Starting Hazelcast member '{}' on cluster '{}' with TCP/IP members {}",
					properties.getInstanceName(), properties.getClusterName(), properties.getMembers());
		} else if (properties.isMulticastEnabled()) {
			join.getTcpIpConfig().setEnabled(false);
			join.getMulticastConfig().setEnabled(true);
			join.getMulticastConfig().setMulticastGroup(properties.getMulticastGroup());
			join.getMulticastConfig().setMulticastPort(properties.getMulticastPort());
			LOGGER.info("Starting Hazelcast member '{}' on cluster '{}' with multicast discovery {}:{}",
					properties.getInstanceName(), properties.getClusterName(), properties.getMulticastGroup(),
					properties.getMulticastPort());
		} else {
			// No discovery configured: single-member cluster (still a valid, usable bus).
			join.getMulticastConfig().setEnabled(false);
			join.getTcpIpConfig().setEnabled(false);
			LOGGER.warn("Hazelcast enabled but no members/multicast configured for cluster '{}': "
					+ "the member will run isolated (single-member cluster)", properties.getClusterName());
		}

		return Hazelcast.getOrCreateHazelcastInstance(config);
	}

	/**
	 * Hazelcast-backed cluster message bus (only when a {@link HazelcastInstance}
	 * exists).
	 */
	@Bean
	@ConditionalOnProperty(prefix = "gebo.hazelcast", name = "enabled", havingValue = "true")
	public IGClusterMessageBus hazelcastClusterMessageBus(HazelcastInstance hazelcastInstance) {
		LOGGER.info("Cluster message bus backed by Hazelcast is active");
		return new GHazelcastClusterMessageBus(hazelcastInstance);
	}

	/**
	 * Standalone fallback used whenever no Hazelcast-backed bus was created.
	 */
	@Bean
	@ConditionalOnMissingBean(IGClusterMessageBus.class)
	public IGClusterMessageBus noOpClusterMessageBus() {
		LOGGER.info("Cluster message bus disabled: using standalone (no-op) local bus");
		return new GNoOpClusterMessageBus();
	}
}
