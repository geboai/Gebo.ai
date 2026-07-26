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
import ai.gebo.microservices.topology.GeboModelsReplicationParticipants;

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
 * {@link DiscoveryClient} for the live instances of every models-replication
 * participant ({@link GeboModelsReplicationParticipants}, not every topology
 * member - the rest of the topology has nothing to do with this cache) and seeds
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

	/**
	 * How many times an empty discovery snapshot is retried before giving up and
	 * seeding an isolated member. Hazelcast reads the TCP/IP member list once, at
	 * startup, and does not go back to discovery later - so a snapshot taken before
	 * this service's peers (which routinely take 100-200s to finish their own
	 * startup) have registered permanently splits the cluster: each isolated member
	 * falls back to Hazelcast's default multicast join, which only reunites members
	 * that also fell back to it, not one still on a - possibly stale - TCP/IP list.
	 * Restarting the isolated members together does not reliably fix this either:
	 * if they restart at the same time their own fresh registrations have not
	 * propagated to each other's discovery cache yet, so they can end up isolated
	 * again, simultaneously. Observed directly: two consecutive 5-second polls came
	 * back with the same 15-address list - "stable" - except that list was missing
	 * a participant that simply had not registered with Eureka yet, having taken
	 * well over 90s to reach that point in its own startup. Two things fix that:
	 * scoping the query to the actual participant set (see the constructor) instead
	 * of every topology member, and a retry budget generous enough to outlast a
	 * slow participant's registration rather than one merely long enough to dodge a
	 * momentary empty read.
	 */
	private static final int MAX_DISCOVERY_ATTEMPTS = 24;

	/** Delay between snapshot retries. 24 attempts * 10s = up to 240s. */
	private static final long RETRY_DELAY_MILLIS = 10000L;

	private final DiscoveryClient discoveryClient;
	private final GeboModelsReplicationParticipants participants;
	private final ModelsReplicationClusterProperties properties;
	private final String localApplicationName;

	/**
	 * @param discoveryClient live service registry
	 * @param participants the actual models-replication participant ids - not the
	 *            full {@code GeboMicroservicesTopology}, which would additionally
	 *            query, and make convergence depend on the registration timing of,
	 *            every other unrelated topology member
	 * @param properties deployment/network tuning
	 * @param localApplicationName this instance's {@code spring.application.name}
	 */
	public DiscoveryClientClusterTopologyProvider(DiscoveryClient discoveryClient,
			GeboModelsReplicationParticipants participants, ModelsReplicationClusterProperties properties,
			String localApplicationName) {
		this.discoveryClient = discoveryClient;
		this.participants = participants;
		this.properties = properties;
		this.localApplicationName = localApplicationName;
	}

	@Override
	public GModelsReplicationClusterTopology getModelsReplicationClusterTopology() {
		String instanceName = GeboMicroservice.normalizeName(localApplicationName);
		List<String> memberList = new ArrayList<>();
		List<String> previous = null;
		for (int attempt = 1; attempt <= MAX_DISCOVERY_ATTEMPTS; attempt++) {
			memberList = resolveMembers();
			// Non-empty AND unchanged since the previous poll: a single non-empty read is
			// not enough on its own - a peer that is slower to register than this one would
			// otherwise be silently left out of a list accepted on the very first sighting
			// of any member at all. Requiring one stable repeat gives stragglers one more
			// retry interval to show up before this snapshot is taken as final.
			if (!memberList.isEmpty() && memberList.equals(previous)) {
				break;
			}
			previous = memberList;
			if (attempt < MAX_DISCOVERY_ATTEMPTS) {
				LOGGER.info(
						"Service discovery member snapshot for '{}' not yet stable (attempt {}/{}, members so far={}); "
								+ "retrying in {} ms - peers routinely still be starting up.",
						instanceName, attempt, MAX_DISCOVERY_ATTEMPTS, memberList, RETRY_DELAY_MILLIS);
				sleep(RETRY_DELAY_MILLIS);
			}
		}

		if (memberList.isEmpty()) {
			LOGGER.warn("Service discovery still reports no instance of any topology member after {} attempts: '{}' "
					+ "will start an ISOLATED single-member cache and merge once it meets the others. Is the "
					+ "registry reachable?", MAX_DISCOVERY_ATTEMPTS, instanceName);
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

	private List<String> resolveMembers() {
		Set<String> members = new LinkedHashSet<>();

		for (String microserviceId : participants.microserviceIds()) {
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

		return new ArrayList<>(members);
	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
}
