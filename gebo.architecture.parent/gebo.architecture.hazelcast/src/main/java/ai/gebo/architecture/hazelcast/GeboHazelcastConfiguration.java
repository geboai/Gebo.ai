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
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * Wires the {@link IGClusterMessageBus} used to keep the models-replication
 * cache in sync across instances.
 * <p>
 * <strong>Bean-driven, not YAML-driven.</strong> The embedded Hazelcast member
 * is started only when an {@link IGModelsReplicationClusterTopologyProvider} bean
 * is present: its {@link GModelsReplicationClusterTopology} supplies the
 * participants and ports. A participating microservice contributes that bean; a
 * service that does not participate omits it.
 * <ul>
 * <li>Provider present &rarr; a {@link HazelcastInstance} is built from the
 * topology and a Hazelcast-backed {@link GHazelcastClusterMessageBus} is
 * exposed.</li>
 * <li>Provider absent &rarr; no Hazelcast member is started and a
 * {@link GNoOpClusterMessageBus} is exposed, so cluster-aware code keeps working
 * on a single, standalone instance.</li>
 * </ul>
 * Picked up by the application-wide component scan of the {@code ai.gebo} base
 * package.
 */
@Configuration
public class GeboHazelcastConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeboHazelcastConfiguration.class);

	private static final String DEFAULT_CLUSTER_NAME = "gebo-models-cluster";
	private static final String DEFAULT_INSTANCE_NAME = "gebo-models-hazelcast";

	/**
	 * Exposes the cluster message bus. When a topology provider bean is present the
	 * bus is Hazelcast-backed (and owns the member it starts); otherwise it is the
	 * standalone no-op bus.
	 * <p>
	 * The inferred destroy method ({@code close()} on
	 * {@link GHazelcastClusterMessageBus}) shuts the Hazelcast member down with the
	 * application context.
	 */
	@Bean
	public IGClusterMessageBus modelsReplicationClusterMessageBus(
			ObjectProvider<IGModelsReplicationClusterTopologyProvider> topologyProviders) {
		IGModelsReplicationClusterTopologyProvider provider = topologyProviders.getIfAvailable();
		if (provider == null) {
			LOGGER.info("No IGModelsReplicationClusterTopologyProvider bean present: "
					+ "models replication cache disabled (standalone no-op bus)");
			return new GNoOpClusterMessageBus();
		}

		GModelsReplicationClusterTopology topology = provider.getModelsReplicationClusterTopology();
		if (topology == null) {
			LOGGER.warn("IGModelsReplicationClusterTopologyProvider {} returned a null topology: "
					+ "models replication cache disabled (standalone no-op bus)", provider.getClass().getName());
			return new GNoOpClusterMessageBus();
		}

		HazelcastInstance hazelcast = createHazelcastInstance(topology);
		LOGGER.info("Models replication cache started (cluster message bus backed by Hazelcast)");
		return new GHazelcastClusterMessageBus(hazelcast);
	}

	private HazelcastInstance createHazelcastInstance(GModelsReplicationClusterTopology topology) {
		Config config = new Config();
		config.setClusterName(StringUtils.hasText(topology.getClusterName()) ? topology.getClusterName()
				: DEFAULT_CLUSTER_NAME);
		config.setInstanceName(StringUtils.hasText(topology.getInstanceName()) ? topology.getInstanceName()
				: DEFAULT_INSTANCE_NAME);

		NetworkConfig network = config.getNetworkConfig();
		network.setPort(topology.getPort());
		network.setPortAutoIncrement(topology.isPortAutoIncrement());

		JoinConfig join = network.getJoin();
		// Multicast is never used: participation is explicit via the provided topology.
		join.getMulticastConfig().setEnabled(false);
		// TCP/IP join stays enabled even with an empty member list (unlike disabling
		// it outright, which was the previous behaviour here): Hazelcast requires
		// some join mechanism enabled, and disabling both multicast and TCP/IP makes
		// it silently fall back to its own default (multicast). A member whose own
		// discovery snapshot came back empty - because its peers hadn't registered
		// with Eureka yet, see DiscoveryClientClusterTopologyProvider's retry-budget
		// comment - would then be multicast-only forever, while a peer that started
		// later and DID see a full member list stays on TCP/IP and gets permanently
		// rejected trying to join it ("Incompatible joiners! expected: multicast,
		// found: tcp-ip"), crash-looping that later instance's whole Spring context.
		// Leaving TCP/IP enabled with no configured members doesn't make this node
		// dial out to anyone, but it keeps it listening for and accepting incoming
		// TCP/IP join requests from exactly such a later-arriving, fully-informed peer.
		join.getTcpIpConfig().setEnabled(true);
		if (topology.getMembers() != null && !topology.getMembers().isEmpty()) {
			topology.getMembers().forEach(join.getTcpIpConfig()::addMember);
			LOGGER.info("Models replication Hazelcast member '{}' on cluster '{}' with TCP/IP members {}",
					config.getInstanceName(), config.getClusterName(), topology.getMembers());
		} else {
			LOGGER.warn("Models replication topology declared no members for cluster '{}': "
					+ "the member will listen for TCP/IP joins but won't dial out to anyone until it "
					+ "discovers peers on a later attempt", config.getClusterName());
		}

		return Hazelcast.getOrCreateHazelcastInstance(config);
	}
}
