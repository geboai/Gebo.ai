/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.secrets.cluster;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import ai.gebo.microservices.topology.GeboMicroservicesTopology;
import ai.gebo.microservices.topology.GeboStandardMicroservices;

/**
 * The access decision that stands between a caller and decrypted secret material.
 *
 * Gebo.ai comment agent
 */
class GeboClusterParticipantsTest {

	private static final String BRAIN_IP = "10.1.0.7";
	private static final String GATEWAY_IP = "10.1.0.2";
	private static final String STRANGER_IP = "10.9.9.9";

	@Test
	void allowsAnInstanceRegisteredForATopologyMicroservice() {
		GeboClusterParticipants participants = participants(Map.of("brain-gebo-ai", List.of(BRAIN_IP)));

		assertThat(participants.isParticipantAddress(BRAIN_IP)).isTrue();
	}

	@Test
	void deniesAnAddressThatIsNotRegistered() {
		GeboClusterParticipants participants = participants(Map.of("brain-gebo-ai", List.of(BRAIN_IP)));

		assertThat(participants.isParticipantAddress(STRANGER_IP)).isFalse();
	}

	/**
	 * The gateway is not a topology member, but a call routed through it arrives from
	 * its address, so it has to be allowed explicitly.
	 */
	@Test
	void allowsTheGatewayThroughTheExtraServiceIds() {
		GeboClusterParticipants participants = participants(Map.of("gateway-gebo-ai", List.of(GATEWAY_IP)));

		assertThat(participants.isParticipantAddress(GATEWAY_IP)).isTrue();
	}

	/**
	 * A service that has left the registry loses access - membership is the live
	 * registry, not a static list.
	 */
	@Test
	void deniesAnInstanceOnceItLeavesTheRegistry() {
		Map<String, List<String>> registry = new LinkedHashMap<>();
		registry.put("brain-gebo-ai", new ArrayList<>(List.of(BRAIN_IP)));
		// No cache, so the next check re-reads the registry.
		GeboClusterParticipants participants = participants(registry, Duration.ZERO);
		assertThat(participants.isParticipantAddress(BRAIN_IP)).isTrue();

		registry.get("brain-gebo-ai").clear();

		assertThat(participants.isParticipantAddress(BRAIN_IP)).isFalse();
	}

	/**
	 * The whole point of the guard: when discovery says nothing, nobody gets secrets.
	 * Failing open here would expose every secret to any host that can reach the port.
	 */
	@Test
	void deniesEveryoneWhenDiscoveryReportsNothing() {
		GeboClusterParticipants participants = participants(Map.of());

		assertThat(participants.isParticipantAddress(BRAIN_IP)).isFalse();
		assertThat(participants.isParticipantAddress(GATEWAY_IP)).isFalse();
		assertThat(participants.participantAddresses()).isEmpty();
	}

	@Test
	void deniesAMalformedOrMissingAddress() {
		GeboClusterParticipants participants = participants(Map.of("brain-gebo-ai", List.of(BRAIN_IP)));

		assertThat(participants.isParticipantAddress(null)).isFalse();
		assertThat(participants.isParticipantAddress("  ")).isFalse();
		assertThat(participants.isParticipantAddress("not an address")).isFalse();
	}

	/**
	 * A pinned address (dev / monolith) is allowed even though nothing is registered,
	 * and is the only way to get in while the registry is empty.
	 */
	@Test
	void allowsStaticallyPinnedAddresses() {
		GeboClusterParticipants participants = new GeboClusterParticipants(discoveryClient(Map.of()),
				GeboMicroservicesTopology.defaults(),
				List.of(GeboStandardMicroservices.GATEWAY_MICROSERVICE_ID), List.of(STRANGER_IP),
				Duration.ofSeconds(30));

		assertThat(participants.isParticipantAddress(STRANGER_IP)).isTrue();
		assertThat(participants.isParticipantAddress(BRAIN_IP)).isFalse();
	}

	/**
	 * A co-located caller reaches the service over loopback while the registry lists
	 * it under its LAN ip, so the two forms have to match.
	 */
	@Test
	void matchesAnyLoopbackFormAgainstARegisteredLoopbackInstance() {
		GeboClusterParticipants participants = participants(Map.of("brain-gebo-ai", List.of("127.0.0.1")));

		assertThat(participants.isParticipantAddress("127.0.0.1")).isTrue();
		assertThat(participants.isParticipantAddress("::1")).isTrue();
	}

	// --- Fixtures -----------------------------------------------------------

	private static GeboClusterParticipants participants(Map<String, List<String>> registry) {
		return participants(registry, Duration.ofSeconds(30));
	}

	private static GeboClusterParticipants participants(Map<String, List<String>> registry, Duration cacheTtl) {
		return new GeboClusterParticipants(discoveryClient(registry), GeboMicroservicesTopology.defaults(),
				List.of(GeboStandardMicroservices.GATEWAY_MICROSERVICE_ID), List.of(), cacheTtl);
	}

	/** A registry of {@code discoveryServiceId -> instance hosts}, read live on each call. */
	private static DiscoveryClient discoveryClient(Map<String, List<String>> registry) {
		return new DiscoveryClient() {

			@Override
			public String description() {
				return "test";
			}

			@Override
			public List<String> getServices() {
				return List.copyOf(registry.keySet());
			}

			@Override
			public List<ServiceInstance> getInstances(String serviceId) {
				List<ServiceInstance> instances = new ArrayList<>();
				for (String host : registry.getOrDefault(serviceId, List.of())) {
					instances.add(new DefaultServiceInstance(serviceId + "#" + host, serviceId, host, 13000, false));
				}
				return instances;
			}
		};
	}
}
