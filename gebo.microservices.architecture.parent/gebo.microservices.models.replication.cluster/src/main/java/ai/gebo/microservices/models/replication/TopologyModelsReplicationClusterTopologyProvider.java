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
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.hazelcast.GModelsReplicationClusterTopology;
import ai.gebo.architecture.hazelcast.IGModelsReplicationClusterTopologyProvider;
import ai.gebo.microservices.topology.GeboMicroservice;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;
import ai.gebo.microservices.topology.GeboModelsReplicationParticipants;

/**
 * {@link IGModelsReplicationClusterTopologyProvider} that derives the
 * models-replication Hazelcast topology from the shared
 * {@link GeboMicroservicesTopology} and the shared
 * {@link GeboModelsReplicationParticipants} set (both sourced from the single
 * shared microservices topology configuration).
 * <p>
 * Each participant is looked up in the microservices topology (by its
 * microservice id). Known participants become Hazelcast TCP/IP members
 * ({@code host:port}); the host defaults to the participant's dotted application
 * name and can be overridden via
 * {@link ModelsReplicationClusterProperties#getHostOverrides()}. Unknown
 * participants are skipped with a warning, so a mis-typed id cannot silently add
 * an unreachable member.
 */
public class TopologyModelsReplicationClusterTopologyProvider implements IGModelsReplicationClusterTopologyProvider {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TopologyModelsReplicationClusterTopologyProvider.class);

	private final GeboMicroservicesTopology topology;
	private final GeboModelsReplicationParticipants participants;
	private final ModelsReplicationClusterProperties properties;
	private final String localApplicationName;

	public TopologyModelsReplicationClusterTopologyProvider(GeboMicroservicesTopology topology,
			GeboModelsReplicationParticipants participants, ModelsReplicationClusterProperties properties,
			String localApplicationName) {
		this.topology = topology;
		this.participants = participants;
		this.properties = properties;
		this.localApplicationName = localApplicationName;
	}

	@Override
	public GModelsReplicationClusterTopology getModelsReplicationClusterTopology() {
		List<String> members = new ArrayList<>();
		for (String participant : participants.microserviceIds()) {
			Optional<GeboMicroservice> resolved = topology.forApplicationName(participant);
			if (resolved.isEmpty()) {
				LOGGER.warn("Models-replication participant '{}' is not present in the microservices topology; "
						+ "skipping it as a cache member", participant);
				continue;
			}
			String microserviceId = resolved.get().getMicroserviceId();
			String host = hostFor(microserviceId);
			String member = host + ":" + properties.getPort();
			if (!members.contains(member)) {
				members.add(member);
			}
		}

		String instanceName = GeboMicroservice.normalizeName(localApplicationName);

		GModelsReplicationClusterTopology clusterTopology = GModelsReplicationClusterTopology.builder()
				.clusterName(properties.getClusterName())
				.instanceName(instanceName)
				.port(properties.getPort())
				.portAutoIncrement(properties.isPortAutoIncrement())
				.members(members)
				.build();

		LOGGER.info("Models-replication cluster topology resolved for '{}': cluster='{}', port={}, members={}",
				instanceName, properties.getClusterName(), properties.getPort(), members);
		return clusterTopology;
	}

	/**
	 * Resolves the reachable host for a participant: an explicit override if
	 * configured, otherwise the participant's dotted application name derived from
	 * its (underscore) microservice id.
	 */
	private String hostFor(String microserviceId) {
		String override = properties.getHostOverrides().get(microserviceId);
		if (override != null && !override.isBlank()) {
			return override;
		}
		return microserviceId.replace('_', '.');
	}
}
