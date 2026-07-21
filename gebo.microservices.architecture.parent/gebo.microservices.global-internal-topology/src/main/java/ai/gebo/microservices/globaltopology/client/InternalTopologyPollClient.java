/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.globaltopology.client;

import java.net.URI;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.application.messaging.model.GModuleMetaInfo;
import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;

/**
 * Calls a single microservice's {@code InternalMessagingTopologyController}. The
 * base url is resolved per call from {@link GeboMicroserviceUrlResolver} (so it
 * follows the deployment's addressing strategy) and the caller's bearer token is
 * forwarded — on the background poll thread that falls back to the platform system
 * identity (APPLICATION), which the target accepts.
 */
public class InternalTopologyPollClient {

	private static final ParameterizedTypeReference<List<GModuleMetaInfo>> MODULE_LIST = new ParameterizedTypeReference<List<GModuleMetaInfo>>() {
	};

	private final WebClient webClient;
	private final GeboMicroserviceUrlResolver urlResolver;
	private final IGeboCallerTokenPropagator tokenPropagator;
	private final String basePath;

	public InternalTopologyPollClient(WebClient webClient, GeboMicroserviceUrlResolver urlResolver,
			IGeboCallerTokenPropagator tokenPropagator, String basePath) {
		this.webClient = webClient;
		this.urlResolver = urlResolver;
		this.tokenPropagator = tokenPropagator;
		this.basePath = trimSlashes(basePath);
	}

	/**
	 * The local-only modules reported by {@code microserviceId}. Throws when the
	 * microservice cannot be resolved or reached (the caller treats that as "down").
	 */
	public List<GModuleMetaInfo> getLocalTopology(String microserviceId) {
		String baseUrl = urlResolver.baseUrlForMicroserviceId(microserviceId)
				.orElseThrow(() -> new IllegalStateException("Cannot resolve the base url of microservice '"
						+ microserviceId + "': not a topology member and no 'direct' entry."));
		URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/" + basePath + "/getLocalTopology").build().encode()
				.toUri();
		return webClient.get().uri(uri).headers(this::applyCallerToken).accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToMono(MODULE_LIST).block();
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
