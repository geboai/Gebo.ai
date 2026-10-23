/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.security.client;

import java.time.Duration;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import ai.gebo.microservices.cluster.auth.GeboInstallationBootstrapToken;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.services.IGInstallationIdProvider;

/**
 * The installation id of a microservice that does not own it: asked to
 * heimdall's installation controller the first time it is needed and kept for
 * the life of the process (the id never changes). The call is authenticated by
 * the installation channel's own token in the
 * {@value GeboInstallationBootstrapToken#HEADER} header, since no platform token
 * can be minted before the id is known. Until heimdall answers, no token can be
 * minted nor accepted here; the next need asks again.
 */
public class RestInstallationIdProvider implements IGInstallationIdProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(RestInstallationIdProvider.class);
	private static final Duration TIMEOUT = Duration.ofSeconds(15);

	private final WebClient webClient;
	private final GeboMicroserviceUrlResolver urlResolver;
	private final GeboSecurityConfig securityConfig;
	private final String microserviceId;
	private final String basePath;
	private final String callerName;
	private volatile String installationId = null;

	public RestInstallationIdProvider(WebClient webClient, GeboMicroserviceUrlResolver urlResolver,
			GeboSecurityConfig securityConfig, String microserviceId, String basePath, String callerName) {
		this.webClient = webClient;
		this.urlResolver = urlResolver;
		this.securityConfig = securityConfig;
		this.microserviceId = microserviceId;
		this.basePath = basePath;
		this.callerName = callerName;
	}

	@Override
	public String getInstallationId() {
		String id = installationId;
		if (id == null) {
			synchronized (this) {
				id = installationId;
				if (id == null) {
					id = fetch();
					installationId = id;
					LOGGER.info("Installation id obtained from {}: {}", microserviceId, id);
				}
			}
		}
		return id;
	}

	private String fetch() {
		String baseUrl = urlResolver.baseUrlForMicroserviceId(microserviceId)
				.orElseThrow(() -> new IllegalStateException("Cannot resolve the base url of '" + microserviceId
						+ "' to read the installation id"));
		String url = baseUrl + "/" + basePath.replaceAll("^/+", "").replaceAll("/+$", "") + "/getInstallationId";
		String header = GeboInstallationBootstrapToken.mint(securityConfig.getAuth().getTokenSecret(), callerName);
		Map<String, String> body;
		try {
			body = webClient.get().uri(url).header(GeboInstallationBootstrapToken.HEADER, header)
					.accept(MediaType.APPLICATION_JSON).retrieve()
					.bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {
					}).block(TIMEOUT);
		} catch (RuntimeException e) {
			throw new IllegalStateException("The installation id cannot be read from " + microserviceId + ": "
					+ e.getMessage(), e);
		}
		String id = body != null ? body.get("installationId") : null;
		if (id == null || id.isBlank()) {
			throw new IllegalStateException(microserviceId + " answered without an installation id");
		}
		return id;
	}
}
