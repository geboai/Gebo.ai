/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.impl;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import ai.gebo.architecture.search.model.SearchCallParameters;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.zammad.client.invoker.ApiClient;
import ai.gebo.zammad.handler.GZammadSystem;
import ai.gebo.restintegration.abstraction.layer.GeboHttpClientObservations;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;
import tools.jackson.databind.cfg.EnumFeature;
import tools.jackson.databind.json.JsonMapper;

/**
 * Builds authenticated {@link ZammadConnection}s from a system and its stored
 * secret: a TOKEN secret is a Zammad access token, sent as
 * {@code Authorization: Token token=<token>}; a USERNAME_PASSWORD secret is
 * sent as basic authentication. The credentials are default headers of the
 * RestClient, never URL parameters.
 */
@Service
public class ZammadApiClientFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZammadApiClientFactory.class);
	/** The scheme of the Authorization header carrying an access token. */
	static final String TOKEN_SCHEME = "Token token=";
	private final IGeboSecretsAccessService secretService;
	private final ZammadSystemsConfigurationDao systemsDao;
	private final ObjectProvider<ObservationRegistry> observationRegistry;

	public ZammadApiClientFactory(IGeboSecretsAccessService secretService, ZammadSystemsConfigurationDao systemsDao,
			ObjectProvider<ObservationRegistry> observationRegistry) {
		this.secretService = secretService;
		this.systemsDao = systemsDao;
		this.observationRegistry = observationRegistry;
	}

	/**
	 * The connection to a system by code, declared or stored.
	 */
	public ZammadConnection getConnection(String systemCode) throws GeboCryptSecretException {
		GZammadSystem system = systemsDao.findByCode(systemCode);
		if (system == null) {
			throw new IllegalArgumentException("No Zammad system found: " + systemCode);
		}
		return getConnection(system, null);
	}

	public ZammadConnection getConnection(GZammadSystem system) throws GeboCryptSecretException {
		return getConnection(system, null);
	}

	/**
	 * The connection to a system, with the timeouts of a search call when given.
	 */
	public ZammadConnection getConnection(GZammadSystem system, SearchCallParameters searchParameters)
			throws GeboCryptSecretException {
		String baseUri = normalizeBaseUri(system.getBaseUri());
		if (baseUri == null) {
			throw new IllegalArgumentException("The Zammad system " + system.getCode() + " has no base URI");
		}
		if (system.getSecretCode() == null || system.getSecretCode().isBlank()) {
			throw new IllegalArgumentException("The Zammad system " + system.getCode() + " has no credentials");
		}
		AbstractGeboSecretContent secret = secretService.getSecretContentById(system.getSecretCode());
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		if (searchParameters != null) {
			requestFactory.setConnectTimeout(searchParameters.connectTimeout());
			requestFactory.setReadTimeout(searchParameters.readTimeout());
		}
		RestClient.Builder builder = ApiClient.buildRestClientBuilder(MAPPER).requestFactory(requestFactory);
		switch (secret.type()) {
		case TOKEN: {
			String token = ((GeboTokenContent) secret).getToken();
			if (token == null || token.isBlank()) {
				throw new IllegalArgumentException("The Zammad system " + system.getCode() + " has an empty token");
			}
			builder = builder.defaultHeader(HttpHeaders.AUTHORIZATION, TOKEN_SCHEME + token.trim());
		}
			break;
		case USERNAME_PASSWORD: {
			GeboUsernamePasswordContent credentials = (GeboUsernamePasswordContent) secret;
			builder = builder.defaultHeaders(headers -> headers.setBasicAuth(credentials.getUsername(),
					credentials.getPassword(), StandardCharsets.UTF_8));
		}
			break;
		default:
			throw new IllegalArgumentException("Zammad accepts an access token or a username and password, not "
					+ secret.type() + " credentials");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Zammad connection to {} for the system {} with {} credentials{}", baseUri, system.getCode(),
					secret.type(), searchParameters != null ? " and search timeouts " + searchParameters : "");
		}
		builder = GeboHttpClientObservations.observed(builder,
				GeboHttpClientObservations.registryOf(observationRegistry));
		RestClient restClient = builder.build();
		ApiClient apiClient = new ApiClient(restClient);
		apiClient.setBasePath(baseUri);
		return new ZammadConnection(apiClient, restClient, baseUri);
	}

	/**
	 * The client's own mapper, tolerating enum values the Zammad specification
	 * does not list (a group setting added by a later release, for instance): they
	 * read as null instead of failing the whole resource.
	 */
	static final JsonMapper MAPPER = ApiClient.createDefaultMapper(null).rebuild()
			.enable(EnumFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL).build();

	static String normalizeBaseUri(String baseUri) {
		if (baseUri == null || baseUri.isBlank()) {
			return null;
		}
		String normalized = baseUri.trim();
		while (normalized.endsWith("/")) {
			normalized = normalized.substring(0, normalized.length() - 1);
		}
		return normalized;
	}
}
