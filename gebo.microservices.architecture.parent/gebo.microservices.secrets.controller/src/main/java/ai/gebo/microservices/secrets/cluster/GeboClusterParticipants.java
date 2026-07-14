/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.secrets.cluster;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import ai.gebo.microservices.topology.GeboMicroservice;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;

/**
 * The set of network addresses the Gebo cluster is <b>currently</b> made of, as
 * reported by service discovery - i.e. who is allowed to ask the secrets
 * microservice for decrypted secret material.
 *
 * <p>
 * Membership is deliberately <b>dynamic</b>: it is not a configured allow-list
 * but the live registry. For every microservice of the
 * {@link GeboMicroservicesTopology} (plus any {@code extraServiceIds}, typically
 * the gateway, which is not a topology member but does forward calls) the
 * {@link DiscoveryClient} is asked for its registered instances, and each
 * instance's host contributes its IP address(es). A service that is not
 * registered contributes nothing, so a decommissioned or unknown host loses
 * access as soon as it leaves the registry.
 * </p>
 *
 * <p>
 * Instances register under the DNS-safe discovery id
 * ({@link GeboMicroservice#toDiscoveryServiceId(String)}, e.g.
 * {@code brain-gebo-ai}), never the canonical underscore id, so that is what is
 * looked up here.
 * </p>
 *
 * <p>
 * The snapshot is cached for a short TTL: the check runs on every request, while
 * the registry changes on the scale of Eureka's heartbeat. Resolution is
 * best-effort per service - a discovery failure for one service is logged and
 * skipped rather than failing the whole snapshot.
 * </p>
 *
 * <p>
 * <b>Fail-closed.</b> An empty snapshot grants nothing. If discovery is
 * unavailable, every call is denied rather than allowed - the safe direction for
 * an endpoint that hands out secrets.
 * </p>
 *
 * Gebo.ai comment agent
 */
public class GeboClusterParticipants {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeboClusterParticipants.class);

	private final DiscoveryClient discoveryClient;
	private final GeboMicroservicesTopology topology;
	/** Discovery ids beyond the topology members (e.g. the gateway). */
	private final Set<String> extraServiceIds;
	/** Statically pinned addresses, added to whatever discovery reports (dev / monolith / tests). */
	private final Set<String> additionalAllowedAddresses;
	private final long cacheTtlMillis;

	private volatile Set<String> cachedAddresses = Set.of();
	private volatile long cachedAtMillis = 0L;

	/**
	 * @param discoveryClient the live registry; required
	 * @param topology the topology whose members may call; required
	 * @param extraServiceIds additional service ids allowed to call, in any name form
	 *            (e.g. {@code gateway_gebo_ai}); may be {@code null}
	 * @param additionalAllowedAddresses IP addresses / hostnames always allowed on top
	 *            of the discovered ones; may be {@code null}
	 * @param cacheTtl how long a discovery snapshot is reused; {@code null} or
	 *            non-positive disables caching
	 */
	public GeboClusterParticipants(DiscoveryClient discoveryClient, GeboMicroservicesTopology topology,
			Collection<String> extraServiceIds, Collection<String> additionalAllowedAddresses, Duration cacheTtl) {
		this.discoveryClient = discoveryClient;
		this.topology = topology;
		this.extraServiceIds = extraServiceIds == null ? Set.of() : Set.copyOf(extraServiceIds);
		this.additionalAllowedAddresses = canonicalizeAll(
				additionalAllowedAddresses == null ? Set.of() : additionalAllowedAddresses);
		this.cacheTtlMillis = cacheTtl == null ? 0L : Math.max(0L, cacheTtl.toMillis());
	}

	/**
	 * Whether an address is that of a cluster member currently in the registry.
	 *
	 * @param remoteAddress the caller's address, as reported by the transport (never
	 *            a forwarded header - those are caller-controlled and would defeat
	 *            the check)
	 * @return {@code true} if the address belongs to a registered participant
	 */
	public boolean isParticipantAddress(String remoteAddress) {
		String canonical = canonicalize(remoteAddress);
		if (canonical == null) {
			return false;
		}
		Set<String> allowed = participantAddresses();
		if (allowed.contains(canonical)) {
			return true;
		}
		// A loopback caller is the service itself (or a co-located one): the registry
		// may list it under its LAN ip while the socket reports 127.0.0.1 / ::1, so any
		// loopback form matches any other.
		return isLoopback(canonical) && allowed.stream().anyMatch(GeboClusterParticipants::isLoopback);
	}

	/**
	 * The current snapshot of participant addresses (discovered + statically pinned),
	 * refreshed when the cache TTL has elapsed.
	 *
	 * @return the allowed addresses; empty when discovery reports nothing
	 */
	public Set<String> participantAddresses() {
		long now = System.currentTimeMillis();
		Set<String> snapshot = cachedAddresses;
		if (cacheTtlMillis > 0 && !snapshot.isEmpty() && (now - cachedAtMillis) < cacheTtlMillis) {
			return snapshot;
		}
		Set<String> resolved = resolveAddresses();
		cachedAddresses = resolved;
		cachedAtMillis = now;
		return resolved;
	}

	private Set<String> resolveAddresses() {
		Set<String> addresses = new LinkedHashSet<>(additionalAllowedAddresses);
		for (String serviceId : discoveryServiceIds()) {
			try {
				for (ServiceInstance instance : discoveryClient.getInstances(serviceId)) {
					addresses.addAll(canonicalizeAll(Set.of(instance.getHost())));
				}
			} catch (RuntimeException ex) {
				// One unreachable/unknown service must not blind the whole check.
				LOGGER.warn("Cannot resolve instances of '{}' from service discovery: {}", serviceId, ex.getMessage());
			}
		}
		if (addresses.isEmpty()) {
			LOGGER.warn("Service discovery reports no cluster participant: every call to the secrets cluster "
					+ "endpoints will be denied. Is the discovery registry reachable?");
		} else if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Cluster participants allowed to read secrets: {}", addresses);
		}
		return Set.copyOf(addresses);
	}

	private Set<String> discoveryServiceIds() {
		Set<String> serviceIds = new LinkedHashSet<>();
		for (GeboMicroservice microservice : topology.microservices()) {
			serviceIds.add(GeboMicroservice.toDiscoveryServiceId(microservice.getApplicationName()));
		}
		for (String extra : extraServiceIds) {
			serviceIds.add(GeboMicroservice.toDiscoveryServiceId(extra));
		}
		return serviceIds;
	}

	private static Set<String> canonicalizeAll(Collection<String> hosts) {
		Set<String> canonical = new LinkedHashSet<>();
		for (String host : hosts) {
			if (host == null || host.isBlank()) {
				continue;
			}
			try {
				// A registry host is normally already an ip (prefer-ip-address), but a
				// hostname must be expanded to every address it answers for.
				for (InetAddress address : InetAddress.getAllByName(host.trim())) {
					canonical.add(address.getHostAddress());
				}
			} catch (UnknownHostException ex) {
				LOGGER.warn("Cannot resolve cluster participant host '{}': {}", host, ex.getMessage());
			}
		}
		return canonical;
	}

	private static String canonicalize(String address) {
		if (address == null || address.isBlank()) {
			return null;
		}
		try {
			return InetAddress.getByName(address.trim()).getHostAddress();
		} catch (UnknownHostException ex) {
			return null;
		}
	}

	private static boolean isLoopback(String canonicalAddress) {
		try {
			return InetAddress.getByName(canonicalAddress).isLoopbackAddress();
		} catch (UnknownHostException ex) {
			return false;
		}
	}
}
