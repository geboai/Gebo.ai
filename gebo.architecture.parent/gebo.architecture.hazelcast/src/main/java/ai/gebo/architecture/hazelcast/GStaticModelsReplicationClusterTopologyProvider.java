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
 * Ready-to-use {@link IGModelsReplicationClusterTopologyProvider} that returns a
 * fixed, pre-computed {@link GModelsReplicationClusterTopology}.
 * <p>
 * A participating microservice registers it as a bean, sourcing the values from
 * wherever it likes (its own {@code @ConfigurationProperties}, a discovery
 * service, its microservice topology model, ...). The mere presence of the bean
 * starts the models cache; its absence leaves the cache off. Example:
 *
 * <pre>{@code
 * @Bean
 * IGModelsReplicationClusterTopologyProvider modelsReplicationTopology(MyServiceProps props) {
 *     return new GStaticModelsReplicationClusterTopologyProvider(
 *             GModelsReplicationClusterTopology.builder()
 *                     .clusterName("gebo-models")
 *                     .port(props.getHazelcastPort())
 *                     .members(props.getClusterMembers())
 *                     .build());
 * }
 * }</pre>
 */
public class GStaticModelsReplicationClusterTopologyProvider implements IGModelsReplicationClusterTopologyProvider {

	private final GModelsReplicationClusterTopology topology;

	public GStaticModelsReplicationClusterTopologyProvider(GModelsReplicationClusterTopology topology) {
		this.topology = topology;
	}

	@Override
	public GModelsReplicationClusterTopology getModelsReplicationClusterTopology() {
		return topology;
	}
}
