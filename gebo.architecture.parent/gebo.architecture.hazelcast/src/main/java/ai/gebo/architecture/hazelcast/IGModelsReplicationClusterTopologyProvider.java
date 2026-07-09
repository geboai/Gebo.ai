/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.hazelcast;

/**
 * Supplies the topology of the models-replication cache cluster (participants
 * and ports) to the Hazelcast infrastructure.
 * <p>
 * <strong>Presence gates the cache.</strong> The embedded Hazelcast member and
 * the Hazelcast-backed {@link IGClusterMessageBus} are started <em>only</em> when
 * a bean of this type is present in the application context. A microservice that
 * participates in models replication (e.g. brain / vectorizator / graphsearch)
 * declares such a bean; a service that does not participate simply omits it, and
 * the models cache is not started (a standalone no-op bus is used instead).
 * <p>
 * This replaces the previous static YAML-driven configuration: participation and
 * topology are now contributed programmatically, so they can be derived from the
 * service's own deployment/topology model rather than duplicated in properties.
 *
 * @see GStaticModelsReplicationClusterTopologyProvider for a ready-to-use
 *      implementation a microservice can register as a bean.
 */
public interface IGModelsReplicationClusterTopologyProvider {

	/**
	 * @return the current topology of the models-replication cluster. Must not be
	 *         {@code null}; return a topology with an empty member list to run an
	 *         isolated single-member cache.
	 */
	GModelsReplicationClusterTopology getModelsReplicationClusterTopology();
}
