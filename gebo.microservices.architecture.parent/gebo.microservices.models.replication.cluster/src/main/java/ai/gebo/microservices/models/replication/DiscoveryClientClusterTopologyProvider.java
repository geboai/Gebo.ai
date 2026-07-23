/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.models.replication;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import ai.gebo.architecture.hazelcast.GModelsReplicationClusterTopology;
import ai.gebo.architecture.hazelcast.IGModelsReplicationClusterTopologyProvider;
import ai.gebo.microservices.topology.GeboMicroservice;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;

/**
 * Seeds the replication cluster from <b>live service discovery</b> instead of from
 * configured hostnames.
 *
 * <h2>Why not the name-based seeding</h2>
 * <p>
 * {@link TopologyModelsReplicationClusterTopologyProvider} builds each member
 * address by turning a microservice id into a dotted hostname
 * ({@code brain_gebo_ai} &rarr; {@code brain.gebo.ai}) and trusting DNS to resolve
 * it. That holds only while every service is a single instance reachable under its
 * own name. It cannot see a <i>second</i> replica of a service, and it breaks
 * wherever addresses are assigned dynamically - which is the normal case the moment
 * anything scales.
 * </p>
 *
 * <p>
 * Eureka already knows the answer: every instance registers, with its real address
 * ({@code prefer-ip-address: true}). This provider therefore asks the
 * {@link DiscoveryClient} for the live instances of every topology member and seeds
 * the cluster with those - so a replica that exists is a member, and one that does
 * not exist is not.
 * </p>
 *
 * <h2>Seeding, not membership</h2>
 * <p>
 * Hazelcast reads its TCP/IP member list <b>once, when the member starts</b>. So what
 * discovery provides is the <i>seed</i>: after that, Hazelcast's own gossip maintains
 * membership, and instances that come and go later are handled by the cluster itself,
 * not by re-reading Eureka. Two consequences worth knowing:
 * </p>
 * <ul>
 * <li>a service that starts when no peer is yet registered forms a cluster of one and
 * merges when it later meets the others - inherent to seeding, not to Eureka;</li>
 * <li>the port in each seed address is the <b>Hazelcast</b> port, not the instance's
 * HTTP port: the member listens on its own port on the same host.</li>
 * </ul>
 *
 * Gebo.ai comment agent
 */
public class DiscoveryClientClusterTopologyProvider implements IGModelsReplicationClusterTopologyProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(DiscoveryClientClusterTopologyProvider.class);

	private final DiscoveryClient discoveryClient;
	private final GeboMicroservicesTopology topology;
	private final ModelsReplicationClusterProperties properties;
	private final String localApplicationName;

	public DiscoveryClientClusterTopologyProvider(DiscoveryClient discoveryClient,
			GeboMicroservicesTopology topology, ModelsReplicationClusterProperties properties,
			String localApplicationName) {
		this.discoveryClient = discoveryClient;
		this.topology = topology;
		this.properties = properties;
		this.localApplicationName = localApplicationName;
	}

	@Override
	public GModelsReplicationClusterTopology getModelsReplicationClusterTopology() {
		Set<String> members = new LinkedHashSet<>();

		for (GeboMicroservice microservice : topology.microservices()) {
			String microserviceId = microservice.getMicroserviceId();

			// An explicit override still wins - a pinned address is how a deployment
			// addresses a member discovery cannot see (or must not be asked about).
			String override = properties.getHostOverrides().get(microserviceId);
			if (override != null && !override.isBlank()) {
				members.add(override + ":" + properties.getPort());
				continue;
			}

			// Instances register under the DNS-safe discovery id, never the canonical
			// underscore one.
			String serviceId = GeboMicroservice.toDiscoveryServiceId(microserviceId);
			try {
				for (ServiceInstance instance : discoveryClient.getInstances(serviceId)) {
					members.add(instance.getHost() + ":" + properties.getPort());
				}
			} catch (RuntimeException ex) {
				// One unreachable service must not stop the cluster from forming with the rest.
				LOGGER.warn("Cannot resolve instances of '{}' from service discovery: {}", serviceId,
						ex.getMessage());
			}
		}

		String instanceName = GeboMicroservice.normalizeName(localApplicationName);
		List<String> memberList = new ArrayList<>(members);

		if (memberList.isEmpty()) {
			LOGGER.warn("Service discovery reports no instance of any topology member: '{}' will start an ISOLATED "
					+ "single-member cache and merge once it meets the others. Is the registry reachable?",
					instanceName);
		} else {
			LOGGER.info("Replication cluster seeded from discovery for '{}': cluster='{}', port={}, members={}",
					instanceName, properties.getClusterName(), properties.getPort(), memberList);
		}

		return GModelsReplicationClusterTopology.builder()
				.clusterName(properties.getClusterName())
				.instanceName(instanceName)
				.port(properties.getPort())
				.portAutoIncrement(properties.isPortAutoIncrement())
				.members(memberList)
				.build();
	}
}
