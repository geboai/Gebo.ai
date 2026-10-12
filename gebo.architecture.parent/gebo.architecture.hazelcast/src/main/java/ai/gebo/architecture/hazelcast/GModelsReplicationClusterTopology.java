/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.hazelcast;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Topological description of the models-replication cache cluster: which
 * instances participate and on which ports. It is supplied at runtime by an
 * {@link IGModelsReplicationClusterTopologyProvider} bean and drives the
 * creation of the embedded Hazelcast member.
 * <p>
 * There is no static YAML for this: a participating microservice contributes the
 * provider bean (and hence this topology) programmatically. If no provider bean
 * is present, the models cache is not started at all.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GModelsReplicationClusterTopology {

	/**
	 * Logical cluster name. Only members sharing the same name join the same
	 * cluster. When blank a default is applied.
	 */
	private String clusterName;

	/**
	 * Name of the local Hazelcast member instance (informational / diagnostics).
	 * When blank a default is applied.
	 */
	private String instanceName;

	/** Local port the member binds to. */
	@Builder.Default
	private int port = 5701;

	/**
	 * When {@code true} the member tries the next port if {@link #port} is busy
	 * (useful when co-locating several members on one host or in tests).
	 */
	@Builder.Default
	private boolean portAutoIncrement = true;

	/**
	 * Addresses ({@code host} or {@code host:port}) of the instances participating
	 * in the models-replication cluster, used for TCP/IP discovery. Every
	 * participating instance should list the full set (including itself). May be
	 * empty for an isolated single-member cluster.
	 */
	@Builder.Default
	private List<String> members = new ArrayList<>();

	/**
	 * Optional LIVE resolver of member addresses ({@code host:port}), called again
	 * every time Hazelcast looks for peers rather than once at startup.
	 * <p>
	 * {@link #members} is a snapshot: Hazelcast reads a TCP/IP member list once,
	 * when the member starts, so a participant that starts before its peers are
	 * discoverable seeds an empty list, never dials anyone, and stays a cluster of
	 * one - permanently, because multicast is disabled and the list is never
	 * re-read. That is only safe where the full set of addresses is known up front
	 * and fixed.
	 * <p>
	 * When this supplier is set it is used instead, through Hazelcast's discovery
	 * SPI: Hazelcast calls it on every join attempt AND on its periodic
	 * split-brain merge cycles, so members find each other whatever order they
	 * start in, and a member that came up alone rejoins once its peers become
	 * discoverable. It also imposes no assumption about how many instances a
	 * participant has - the supplier returns however many the source reports, so
	 * the cluster works with any number of replicas per participant.
	 * <p>
	 * It must be cheap and non-blocking: it is called from Hazelcast's own threads.
	 */
	@Builder.Default
	private transient Supplier<List<String>> liveMembers = null;
}
