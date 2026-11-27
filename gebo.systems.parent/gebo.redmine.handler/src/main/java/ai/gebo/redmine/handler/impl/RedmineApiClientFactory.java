/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.impl;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import ai.gebo.architecture.search.model.SearchCallParameters;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.redmine.client.invoker.ApiClient;
import ai.gebo.redmine.handler.GRedmineSystem;
import ai.gebo.restintegration.abstraction.layer.GeboHttpClientObservations;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;
import tools.jackson.databind.cfg.EnumFeature;
import tools.jackson.databind.json.JsonMapper;

/**
 * Builds authenticated {@link RedmineConnection}s from a system and its stored
 * secret: a TOKEN secret is a Redmine API key, sent as the X-Redmine-API-Key
 * header; a USERNAME_PASSWORD secret is sent as basic authentication. The
 * credentials are default headers of the RestClient, never URL parameters.
 */
@Service
public class RedmineApiClientFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedmineApiClientFactory.class);
	public static final String API_KEY_HEADER = "X-Redmine-API-Key";
	private final IGeboSecretsAccessService secretService;
	private final RedmineSystemsConfigurationDao systemsDao;
	private final ObjectProvider<ObservationRegistry> observationRegistry;

	public RedmineApiClientFactory(IGeboSecretsAccessService secretService, RedmineSystemsConfigurationDao systemsDao,
			ObjectProvider<ObservationRegistry> observationRegistry) {
		this.secretService = secretService;
		this.systemsDao = systemsDao;
		this.observationRegistry = observationRegistry;
	}

	/**
	 * The connection to a system by code, declared or stored.
	 */
	public RedmineConnection getConnection(String systemCode) throws GeboCryptSecretException {
		GRedmineSystem system = systemsDao.findByCode(systemCode);
		if (system == null) {
			throw new IllegalArgumentException("No Redmine system found: " + systemCode);
		}
		return getConnection(system, null);
	}

	public RedmineConnection getConnection(GRedmineSystem system) throws GeboCryptSecretException {
		return getConnection(system, null);
	}

	/**
	 * The connection to a system, with the timeouts of a search call when given.
	 */
	public RedmineConnection getConnection(GRedmineSystem system, SearchCallParameters searchParameters)
			throws GeboCryptSecretException {
		String baseUri = normalizeBaseUri(system.getBaseUri());
		if (baseUri == null) {
			throw new IllegalArgumentException("The Redmine system " + system.getCode() + " has no base URI");
		}
		if (system.getSecretCode() == null || system.getSecretCode().isBlank()) {
			throw new IllegalArgumentException("The Redmine system " + system.getCode() + " has no credentials");
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
			String apiKey = ((GeboTokenContent) secret).getToken();
			builder = builder.defaultHeader(API_KEY_HEADER, apiKey);
		}
			break;
		case USERNAME_PASSWORD: {
			GeboUsernamePasswordContent credentials = (GeboUsernamePasswordContent) secret;
			builder = builder.defaultHeaders(headers -> headers.setBasicAuth(credentials.getUsername(),
					credentials.getPassword(), StandardCharsets.UTF_8));
		}
			break;
		default:
			throw new IllegalArgumentException("Redmine accepts an API key (token) or a username and password, not "
					+ secret.type() + " credentials");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Redmine connection to {} for the system {} with {} credentials{}", baseUri, system.getCode(),
					secret.type(), searchParameters != null ? " and search timeouts " + searchParameters : "");
		}
		builder = GeboHttpClientObservations.observed(builder,
				GeboHttpClientObservations.registryOf(observationRegistry));
		RestClient restClient = builder.build();
		ApiClient apiClient = new ApiClient(restClient);
		apiClient.setBasePath(baseUri);
		return new RedmineConnection(apiClient, restClient, baseUri);
	}

	/**
	 * The client's own mapper, tolerating enum values the Redmine specification
	 * does not list (a relation type added by a plugin, for instance): they read as
	 * null instead of failing the whole issue.
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
