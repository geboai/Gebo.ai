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
	 * <p>
	 * <b>What was actually measured, which limits what the budget can buy.</b> On
	 * the docker-compose cluster the loop never observes a registration made after
	 * it starts: the list returned by {@link DiscoveryClient#getInstances(String)}
	 * stays at whatever the Eureka client held when it initialised, for the whole
	 * loop. On a cold start all three participants registered with the right VIPs
	 * about 100s in, and the local Eureka cache logged them being added
	 * ("Added instance ...:brain_gebo_ai:13001 to the existing apps in region
	 * null") - yet all 24 polls still read an empty list and the member ended
	 * isolated after 240s, starting in 296s. Disabling delta fetching
	 * ({@code eureka.client.disable-delta=true}) changed nothing. The same service
	 * restarted against an already-populated registry converged on its SECOND poll
	 * and started in 20s.
	 * <p>
	 * So on a cold cluster the retries are dead time before an outcome that is
	 * identical to the one the first poll already implied; the budget only pays for
	 * itself when the registry is populated at discovery-client init (a rolling
	 * restart), where two polls suffice. Both knobs are therefore configuration
	 * ({@code gebo.models.replication.discovery-attempts} /
	 * {@code ...discovery-retry-interval-millis}) rather than constants, so a
	 * deployment that measures differently can raise them and one that does not
	 * want to pay the startup delay can cut them - see the microservices compose
	 * overlay, which does exactly that.
	 */

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
		List<String> initialMembers = resolveMembers();

		if (initialMembers.isEmpty()) {
			LOGGER.info("Service discovery reports no instance of any models-replication participant yet for '{}'; "
					+ "starting anyway - discovery is re-asked on every Hazelcast join and merge cycle, so peers "
					+ "are picked up as they register.", instanceName);
		} else {
			LOGGER.info("Replication cluster seeded from discovery for '{}': cluster='{}', port={}, members={}",
					instanceName, properties.getClusterName(), properties.getPort(), initialMembers);
		}

		return GModelsReplicationClusterTopology.builder()
				.clusterName(properties.getClusterName())
				.instanceName(instanceName)
				.port(properties.getPort())
				.portAutoIncrement(properties.isPortAutoIncrement())
				.members(initialMembers)
				// The live resolver is the point: it is re-asked by Hazelcast on every
				// join attempt and merge cycle, so this snapshot being empty is no
				// longer terminal and no startup wait is needed to avoid it.
				.liveMembers(this::resolveMembers)
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
}
