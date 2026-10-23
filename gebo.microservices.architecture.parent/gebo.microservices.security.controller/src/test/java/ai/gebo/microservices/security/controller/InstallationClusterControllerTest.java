/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.security.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;

import ai.gebo.microservices.cluster.GeboClusterParticipants;
import ai.gebo.microservices.cluster.auth.GeboInstallationBootstrapToken;
import ai.gebo.microservices.topology.GeboMicroservicesTopology;
import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.services.impl.LocalJwtTokenProvider;

/**
 * Heimdall hands the installation id only to a registered cluster participant
 * presenting the installation channel's token: a request the gateway forwards
 * comes from a non-participant address and is refused, a missing, forged or
 * platform token is refused.
 */
class InstallationClusterControllerTest {

	/** jjwt base64-decodes the signing key and HS512 needs 512 bits: a long hex string. */
	private static final String SECRET = "a3f1c9e7b5d2046f8e1c3a5b7d9f0e2c4a6b8d0f1e3c5a7b9d1f3e5c7a9b0d2fa3f1c9e7b5d2046f8e1c3a5b7d9f0e2c4a6b8d0f1e3c5a7b9d1f3e5c7a9b0d2f";
	private static final String OTHER_SECRET = "b4e2d0f8c6a3157e9f2d4b6c8e0a1f3d5b7c9e1a2f4d6b8c0e2a3d5f7b9c1e3ab4e2d0f8c6a3157e9f2d4b6c8e0a1f3d5b7c9e1a2f4d6b8c0e2a3d5f7b9c1e3a";
	private static final String INSTALLATION_ID = "0f3c2b1a-1111-2222-3333-444455556666@heimdall-host";
	private static final String BRAIN_IP = "10.0.0.5";
	/** The gateway's address: registered in discovery, but not a cluster participant. */
	private static final String GATEWAY_IP = "10.0.0.9";

	private InstallationClusterController controller;
	private GeboSecurityConfig securityConfig;

	@BeforeEach
	void setUp() {
		securityConfig = new GeboSecurityConfig();
		securityConfig.getAuth().setTokenSecret(SECRET);
		securityConfig.getAuth().setTokenExpirationMsec(120_000L);
		GeboClusterParticipants participants = new GeboClusterParticipants(
				discoveryClient(Map.of("brain-gebo-ai", List.of(BRAIN_IP), "gateway-gebo-ai", List.of(GATEWAY_IP))),
				GeboMicroservicesTopology.defaults(), List.of(), List.of(), Duration.ofSeconds(30));
		controller = new InstallationClusterController(() -> INSTALLATION_ID, participants, securityConfig);
	}

	@Test
	void aParticipantWithTheChannelTokenGetsTheId() {
		String header = GeboInstallationBootstrapToken.mint(SECRET, "brain_gebo_ai");

		Map<String, String> answer = controller.getInstallationId(header, request(BRAIN_IP));

		assertThat(answer).containsEntry("installationId", INSTALLATION_ID);
	}

	@Test
	void aRequestForwardedByTheGatewayIsRefusedEvenWithAValidToken() {
		String header = GeboInstallationBootstrapToken.mint(SECRET, "anyone");

		assertThatThrownBy(() -> controller.getInstallationId(header, request(GATEWAY_IP)))
				.isInstanceOf(ResponseStatusException.class)
				.satisfies(e -> assertThat(((ResponseStatusException) e).getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN));
	}

	@Test
	void aParticipantWithoutTheHeaderIsRefused() {
		assertUnauthorized(null);
	}

	@Test
	void aParticipantWithATokenOfAnotherSecretIsRefused() {
		assertUnauthorized(GeboInstallationBootstrapToken.mint(OTHER_SECRET, "brain_gebo_ai"));
	}

	@Test
	void aParticipantWithAPlatformTokenIsRefused() {
		LocalJwtTokenProvider provider = new LocalJwtTokenProvider(securityConfig, List.of(), INSTALLATION_ID);
		String platform = provider.createToken("someone@gebo.ai", new Date(System.currentTimeMillis() + 240_000L));

		assertUnauthorized("Bearer " + platform);
	}

	private void assertUnauthorized(String header) {
		assertThatThrownBy(() -> controller.getInstallationId(header, request(BRAIN_IP)))
				.isInstanceOf(ResponseStatusException.class).satisfies(
						e -> assertThat(((ResponseStatusException) e).getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED));
	}

	private static MockHttpServletRequest request(String remoteAddress) {
		MockHttpServletRequest request = new MockHttpServletRequest("GET",
				"/heimdall/" + InstallationClusterController.BASE_PATH + "/getInstallationId");
		request.setRemoteAddr(remoteAddress);
		return request;
	}

	/** A registry of {@code discoveryServiceId -> instance hosts}. */
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
