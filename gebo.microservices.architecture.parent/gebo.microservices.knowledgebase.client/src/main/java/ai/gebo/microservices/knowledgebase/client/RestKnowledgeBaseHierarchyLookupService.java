/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.knowledgebase.client;

import java.net.URI;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.cluster.cache.GeboTtlCache;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.systems.abstraction.layer.IGKnowledgeBaseHierarchyLookupService;

/**
 * {@link IGKnowledgeBaseHierarchyLookupService} backed by brain.gebo.ai: every
 * call becomes a request to gebo.core's own {@code ProjectsController} or
 * {@code KnowledgeBaseController} - the same admin endpoints a human admin UI
 * would call, not a dedicated cluster controller. Consumers keep depending on the
 * interface and cannot tell whether the hierarchy lives in-process or behind the
 * network.
 *
 * <p>
 * <b>Addressing.</b> The base url is resolved per call through the
 * {@link GeboMicroserviceUrlResolver} rather than configured, so the same build
 * works whether the deployment addresses services through the load balancer, the
 * gateway or a pinned direct url.
 * </p>
 *
 * <p>
 * <b>Authentication.</b> Both endpoints are {@code @PreAuthorize("hasRole('ADMIN')")};
 * the caller's own token is forwarded (see {@link IGeboCallerTokenPropagator}), so
 * brain authorises the request against the identity that originated it - the same
 * identity that was already required to reach the admin/ingestion operation this
 * lookup happens inside of.
 * </p>
 *
 * Gebo.ai comment agent
 */
public class RestKnowledgeBaseHierarchyLookupService implements IGKnowledgeBaseHierarchyLookupService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestKnowledgeBaseHierarchyLookupService.class);

	private final WebClient webClient;
	private final GeboMicroserviceUrlResolver urlResolver;
	private final IGeboCallerTokenPropagator tokenPropagator;
	private final String microserviceId;
	private final String projectsBasePath;
	private final String knowledgeBaseBasePath;
	private final GeboTtlCache cache;

	public RestKnowledgeBaseHierarchyLookupService(WebClient webClient, GeboMicroserviceUrlResolver urlResolver,
			IGeboCallerTokenPropagator tokenPropagator, String microserviceId, String projectsBasePath,
			String knowledgeBaseBasePath, GeboTtlCache cache) {
		this.webClient = webClient;
		this.urlResolver = urlResolver;
		this.tokenPropagator = tokenPropagator;
		this.microserviceId = microserviceId;
		this.projectsBasePath = trimSlashes(projectsBasePath);
		this.knowledgeBaseBasePath = trimSlashes(knowledgeBaseBasePath);
		this.cache = cache;
	}

	@Override
	public GProject findProjectByCode(String code) throws GeboPersistenceException {
		if (code == null) {
			return null;
		}
		return cache.getChecked("project:" + code,
				() -> call("findProjectByCode",
						() -> webClient.get().uri(uri(projectsBasePath, "findProjectByCode", code))
								.headers(this::applyCallerToken).accept(MediaType.APPLICATION_JSON).retrieve()
								.bodyToMono(GProject.class).block()));
	}

	@Override
	public GKnowledgeBase findKnowledgeBaseByCode(String code) throws GeboPersistenceException {
		if (code == null) {
			return null;
		}
		return cache.getChecked("knowledgeBase:" + code,
				() -> call("findKnowledgeBaseByCode",
						() -> webClient.get().uri(uri(knowledgeBaseBasePath, "findKnowledgeBaseByCode", code))
								.headers(this::applyCallerToken).accept(MediaType.APPLICATION_JSON).retrieve()
								.bodyToMono(GKnowledgeBase.class).block()));
	}

	// --- Internals ----------------------------------------------------------

	/** Adds the caller's bearer token, read on this - the calling - thread. */
	private void applyCallerToken(HttpHeaders headers) {
		String token = tokenPropagator.currentToken();
		if (StringUtils.hasText(token)) {
			headers.setBearerAuth(token);
		} else {
			LOGGER.debug(
					"No caller token to forward to the knowledge-base microservice; the call goes out unauthenticated");
		}
	}

	private URI uri(String basePath, String endpoint, String code) {
		return UriComponentsBuilder.fromUriString(baseUrl() + "/" + basePath + "/" + endpoint)
				.queryParam("code", code).build().encode().toUri();
	}

	private String baseUrl() {
		Optional<String> baseUrl = urlResolver.baseUrlForMicroserviceId(microserviceId);
		return baseUrl.orElseThrow(() -> new IllegalStateException("Cannot resolve the base url of the "
				+ "knowledge-base microservice '" + microserviceId + "': it is not a member of the topology and "
				+ "has no 'direct' entry (gebo.microservices.topology.url.direct)."));
	}

	/**
	 * Runs a remote call, turning any transport or remote failure into the
	 * {@link GeboPersistenceException} the interface declares, so a consumer
	 * written against the local implementation needs no change.
	 */
	private <T> T call(String operation, RemoteCall<T> remoteCall) throws GeboPersistenceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("REST {} on knowledge-base microservice '{}'", operation, microserviceId);
		}
		try {
			return remoteCall.execute();
		} catch (WebClientResponseException ex) {
			throw new GeboPersistenceException(
					"Remote " + operation + " failed: " + ex.getStatusCode() + " " + ex.getResponseBodyAsString(),
					ex);
		} catch (RuntimeException ex) {
			throw new GeboPersistenceException("Remote " + operation + " failed", ex);
		}
	}

	@FunctionalInterface
	private interface RemoteCall<T> {
		T execute();
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
