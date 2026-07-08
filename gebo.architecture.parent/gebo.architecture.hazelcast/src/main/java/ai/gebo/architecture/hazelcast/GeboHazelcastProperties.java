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

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Configuration properties driving the embedded Hazelcast member used to keep
 * Gebo.ai runtime state in sync across a cluster of application instances.
 * <p>
 * All clustering is opt-in: when {@code gebo.hazelcast.enabled} is {@code false}
 * (the default) no Hazelcast member is started and the application behaves as a
 * standalone single instance. This keeps the monolithic / single-node
 * deployments unaffected while allowing multi-instance deployments (monolith
 * replicas or the {@code brain}/{@code vectorizator}/{@code graphsearch}
 * microservices) to form a cluster.
 */
@Data
@ConfigurationProperties(prefix = "gebo.hazelcast")
public class GeboHazelcastProperties {

	/**
	 * Master switch. When {@code false} (default) no Hazelcast instance is created
	 * and the cluster message bus degrades to a no-op local implementation.
	 */
	private boolean enabled = false;

	/**
	 * Logical cluster name. Only members sharing the same cluster name join the
	 * same cluster.
	 */
	private String clusterName = "gebo-cluster";

	/** Name assigned to the local Hazelcast member instance. */
	private String instanceName = "gebo-hazelcast";

	/** Network port the local member binds to. */
	private int port = 5701;

	/**
	 * When {@code true} the member tries the next port if {@link #port} is already
	 * in use (useful when co-locating several members on one host / in tests).
	 */
	private boolean portAutoIncrement = true;

	/**
	 * Explicit list of {@code host} or {@code host:port} cluster members used for
	 * TCP/IP discovery. When non-empty, TCP/IP join is used and multicast is
	 * disabled regardless of {@link #multicastEnabled}.
	 */
	private List<String> members = new ArrayList<>();

	/**
	 * Enables multicast discovery. Only honoured when {@link #members} is empty.
	 */
	private boolean multicastEnabled = false;

	/** Multicast group address (only used when {@link #multicastEnabled}). */
	private String multicastGroup = "224.2.2.3";

	/** Multicast port (only used when {@link #multicastEnabled}). */
	private int multicastPort = 54327;
}
