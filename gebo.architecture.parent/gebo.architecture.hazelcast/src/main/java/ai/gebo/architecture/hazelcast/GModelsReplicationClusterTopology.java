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
}
