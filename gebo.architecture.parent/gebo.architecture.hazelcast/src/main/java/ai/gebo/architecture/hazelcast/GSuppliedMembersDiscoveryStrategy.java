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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.hazelcast.cluster.Address;
import com.hazelcast.config.properties.PropertyDefinition;
import com.hazelcast.logging.ILogger;
import com.hazelcast.spi.discovery.AbstractDiscoveryStrategy;
import com.hazelcast.spi.discovery.DiscoveryNode;
import com.hazelcast.spi.discovery.DiscoveryStrategy;
import com.hazelcast.spi.discovery.DiscoveryStrategyFactory;
import com.hazelcast.spi.discovery.SimpleDiscoveryNode;

/**
 * Hazelcast discovery strategy that asks a {@link Supplier} for the member
 * addresses every time Hazelcast looks for peers.
 *
 * <h2>Why this exists rather than a TCP/IP member list</h2>
 * <p>
 * A TCP/IP member list is read ONCE, when the member starts. A participant that
 * starts before its peers are discoverable therefore seeds an empty list, never
 * dials anyone and stays a cluster of one - permanently, since multicast is
 * disabled and the list is never re-read. That was observed on a cold cluster:
 * all three models-replication participants came up with empty lists and sat at
 * {@code Members {size:1, ver:1}} indefinitely, so model changes stopped
 * propagating between them until one was restarted.
 * </p>
 * <p>
 * The discovery SPI removes the startup race instead of racing it. Hazelcast
 * calls {@link #discoverNodes()} on every join attempt AND on its periodic
 * split-brain merge cycles, so:
 * </p>
 * <ul>
 * <li>start order stops mattering - a member that came up alone rejoins as soon
 * as its peers become discoverable, with no restart and no startup wait;</li>
 * <li>the number of instances per participant stops mattering - whatever the
 * supplier reports is what the cluster uses, so one replica or five behave the
 * same.</li>
 * </ul>
 *
 * <p>
 * The supplier is called from Hazelcast's own threads, so it must be cheap and
 * must not block. Anything it cannot resolve is skipped rather than fatal: a
 * peer that is unreachable now may be reachable on the next cycle, and refusing
 * to form a cluster because one address does not parse would be worse than
 * forming one without it.
 * </p>
 *
 * Gebo.ai comment agent
 */
public class GSuppliedMembersDiscoveryStrategy extends AbstractDiscoveryStrategy {

	private final Supplier<List<String>> membersSupplier;
	private final int defaultPort;
	private final ILogger logger;

	public GSuppliedMembersDiscoveryStrategy(ILogger logger, Map<String, Comparable> properties,
			Supplier<List<String>> membersSupplier, int defaultPort) {
		super(logger, properties);
		this.logger = logger;
		this.membersSupplier = membersSupplier;
		this.defaultPort = defaultPort;
	}

	@Override
	public Iterable<DiscoveryNode> discoverNodes() {
		List<String> members;
		try {
			members = membersSupplier.get();
		} catch (RuntimeException ex) {
			// Never propagate: a failed lookup must degrade to "no peers this cycle",
			// not break the join or the merge cycle that would have healed the cluster.
			logger.warning("Models replication member lookup failed; no peers this discovery cycle: "
					+ ex.getMessage());
			return Collections.emptyList();
		}
		if (members == null || members.isEmpty()) {
			return Collections.emptyList();
		}

		List<DiscoveryNode> nodes = new ArrayList<>(members.size());
		for (String member : members) {
			DiscoveryNode node = toNode(member);
			if (node != null) {
				nodes.add(node);
			}
		}
		if (logger.isFineEnabled()) {
			logger.fine("Models replication discovery cycle resolved " + nodes.size() + " member(s): " + members);
		}
		return nodes;
	}

	private DiscoveryNode toNode(String member) {
		if (member == null || member.isBlank()) {
			return null;
		}
		String host = member.trim();
		int port = defaultPort;
		int sep = host.lastIndexOf(':');
		if (sep > -1) {
			String portPart = host.substring(sep + 1).trim();
			host = host.substring(0, sep).trim();
			try {
				port = Integer.parseInt(portPart);
			} catch (NumberFormatException ex) {
				logger.warning("Ignoring models replication member '" + member + "': port is not a number");
				return null;
			}
		}
		try {
			return new SimpleDiscoveryNode(new Address(host, port));
		} catch (Exception ex) {
			// Typically an unresolvable host: a peer that has not started yet, or one
			// whose DNS entry is not up. Expected during a rolling start - the next
			// discovery cycle asks again.
			if (logger.isFineEnabled()) {
				logger.fine("Models replication member '" + member + "' is not resolvable yet: " + ex.getMessage());
			}
			return null;
		}
	}

	/**
	 * Factory Hazelcast instantiates the strategy through. It carries the supplier
	 * because the strategy is created by Hazelcast, not by us.
	 */
	public static class Factory implements DiscoveryStrategyFactory {

		private final Supplier<List<String>> membersSupplier;
		private final int defaultPort;

		public Factory(Supplier<List<String>> membersSupplier, int defaultPort) {
			this.membersSupplier = membersSupplier;
			this.defaultPort = defaultPort;
		}

		@Override
		public Class<? extends DiscoveryStrategy> getDiscoveryStrategyType() {
			return GSuppliedMembersDiscoveryStrategy.class;
		}

		@Override
		public DiscoveryStrategy newDiscoveryStrategy(DiscoveryNode discoveryNode, ILogger logger,
				Map<String, Comparable> properties) {
			return new GSuppliedMembersDiscoveryStrategy(logger, properties, membersSupplier, defaultPort);
		}

		@Override
		public Collection<PropertyDefinition> getConfigurationProperties() {
			return Collections.emptyList();
		}

		/**
		 * Never auto-detected: this strategy is only ever used when a topology
		 * explicitly supplies a live member resolver.
		 */
		@Override
		public boolean isAutoDetectionApplicable() {
			return false;
		}
	}
}
