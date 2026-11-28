/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.impl;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import ai.gebo.architecture.search.model.SearchCallParameters;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.openproject.client.invoker.ApiClient;
import ai.gebo.openproject.handler.GOpenProjectSystem;
import ai.gebo.restintegration.abstraction.layer.GeboHttpClientObservations;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;
import tools.jackson.databind.cfg.EnumFeature;
import tools.jackson.databind.json.JsonMapper;

/**
 * Builds authenticated {@link OpenProjectConnection}s from a system and its stored
 * secret: a TOKEN secret holding an OpenProject API key, sent as the basic
 * authentication of the user "apikey" the API v3 expects. The credentials are
 * default headers of the RestClient, never URL parameters.
 */
@Service
public class OpenProjectApiClientFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenProjectApiClientFactory.class);
	/** The user name of the basic authentication carrying an API key. */
	public static final String API_KEY_USER = "apikey";
	private final IGeboSecretsAccessService secretService;
	private final OpenProjectSystemsConfigurationDao systemsDao;
	private final ObjectProvider<ObservationRegistry> observationRegistry;

	public OpenProjectApiClientFactory(IGeboSecretsAccessService secretService, OpenProjectSystemsConfigurationDao systemsDao,
			ObjectProvider<ObservationRegistry> observationRegistry) {
		this.secretService = secretService;
		this.systemsDao = systemsDao;
		this.observationRegistry = observationRegistry;
	}

	/**
	 * The connection to a system by code, declared or stored.
	 */
	public OpenProjectConnection getConnection(String systemCode) throws GeboCryptSecretException {
		GOpenProjectSystem system = systemsDao.findByCode(systemCode);
		if (system == null) {
			throw new IllegalArgumentException("No OpenProject system found: " + systemCode);
		}
		return getConnection(system, null);
	}

	public OpenProjectConnection getConnection(GOpenProjectSystem system) throws GeboCryptSecretException {
		return getConnection(system, null);
	}

	/**
	 * The connection to a system, with the timeouts of a search call when given.
	 */
	public OpenProjectConnection getConnection(GOpenProjectSystem system, SearchCallParameters searchParameters)
			throws GeboCryptSecretException {
		String baseUri = normalizeBaseUri(system.getBaseUri());
		if (baseUri == null) {
			throw new IllegalArgumentException("The OpenProject system " + system.getCode() + " has no base URI");
		}
		if (system.getSecretCode() == null || system.getSecretCode().isBlank()) {
			throw new IllegalArgumentException("The OpenProject system " + system.getCode() + " has no credentials");
		}
		AbstractGeboSecretContent secret = secretService.getSecretContentById(system.getSecretCode());
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		if (searchParameters != null) {
			requestFactory.setConnectTimeout(searchParameters.connectTimeout());
			requestFactory.setReadTimeout(searchParameters.readTimeout());
		}
		RestClient.Builder builder = ApiClient.buildRestClientBuilder(MAPPER).requestFactory(requestFactory);
		if (!(secret instanceof GeboTokenContent token) || token.getToken() == null || token.getToken().isBlank()) {
			throw new IllegalArgumentException(
					"OpenProject accepts an API key (token) only, not " + secret.type() + " credentials");
		}
		// the API v3 takes the API key as the password of the fixed user "apikey"
		builder = builder.defaultHeaders(
				headers -> headers.setBasicAuth(API_KEY_USER, token.getToken(), StandardCharsets.UTF_8));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("OpenProject connection to {} for the system {} with {} credentials{}", baseUri, system.getCode(),
					secret.type(), searchParameters != null ? " and search timeouts " + searchParameters : "");
		}
		builder = GeboHttpClientObservations.observed(builder,
				GeboHttpClientObservations.registryOf(observationRegistry));
		RestClient restClient = builder.build();
		return new OpenProjectConnection(restClient, baseUri);
	}

	/**
	 * The client's own mapper, tolerating enum values the OpenProject specification
	 * does not list: they read as null instead of failing the whole resource.
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
