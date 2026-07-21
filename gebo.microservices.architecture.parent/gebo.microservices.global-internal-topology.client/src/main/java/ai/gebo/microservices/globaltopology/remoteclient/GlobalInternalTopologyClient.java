/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.globaltopology.remoteclient;

import java.net.URI;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.application.messaging.model.MicroserviceMetaInfo;
import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;

/**
 * Topology-aware client for the {@code GlobalInternalTopologyController} on the
 * coordinator (tyr). Resolves tyr's base url from {@link GeboMicroserviceUrlResolver}
 * and forwards the caller's bearer token (falling back to the platform system
 * identity on a background thread). Intended for a future consumer that adjusts each
 * local broker's external (rabbitmq) bindings from the network-wide topology.
 */
public class GlobalInternalTopologyClient {

	private static final ParameterizedTypeReference<List<MicroserviceMetaInfo>> TOPOLOGY = new ParameterizedTypeReference<List<MicroserviceMetaInfo>>() {
	};

	private final WebClient webClient;
	private final GeboMicroserviceUrlResolver urlResolver;
	private final IGeboCallerTokenPropagator tokenPropagator;
	private final String coordinatorMicroserviceId;
	private final String basePath;

	public GlobalInternalTopologyClient(WebClient webClient, GeboMicroserviceUrlResolver urlResolver,
			IGeboCallerTokenPropagator tokenPropagator, String coordinatorMicroserviceId, String basePath) {
		this.webClient = webClient;
		this.urlResolver = urlResolver;
		this.tokenPropagator = tokenPropagator;
		this.coordinatorMicroserviceId = coordinatorMicroserviceId;
		this.basePath = trimSlashes(basePath);
	}

	/** The cached network-wide internal messaging topology from the coordinator. */
	public List<MicroserviceMetaInfo> getGlobalTopology() {
		return webClient.get().uri(uri("getGlobalTopology")).headers(this::applyCallerToken)
				.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(TOPOLOGY).block();
	}

	/** Trigger an on-demand re-poll on the coordinator; {@code true} if it completed. */
	public boolean refresh() {
		Boolean refreshed = webClient.post().uri(uri("refresh")).headers(this::applyCallerToken)
				.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Boolean.class).block();
		return Boolean.TRUE.equals(refreshed);
	}

	private URI uri(String endpoint) {
		String baseUrl = urlResolver.baseUrlForMicroserviceId(coordinatorMicroserviceId)
				.orElseThrow(() -> new IllegalStateException("Cannot resolve the base url of the topology coordinator '"
						+ coordinatorMicroserviceId + "': not a topology member and no 'direct' entry."));
		return UriComponentsBuilder.fromUriString(baseUrl + "/" + basePath + "/" + endpoint).build().encode().toUri();
	}

	private void applyCallerToken(HttpHeaders headers) {
		String token = tokenPropagator.currentToken();
		if (StringUtils.hasText(token)) {
			headers.setBearerAuth(token);
		}
	}

	private static String trimSlashes(String path) {
		String trimmed = path == null ? "" : path.trim();
		while (trimmed.startsWith("/")) {
			trimmed = trimmed.substring(1);
		}
		while (trimmed.endsWith("/")) {
			trimmed = trimmed.substring(0, trimmed.length() - 1);
		}
		return trimmed;
	}
}
