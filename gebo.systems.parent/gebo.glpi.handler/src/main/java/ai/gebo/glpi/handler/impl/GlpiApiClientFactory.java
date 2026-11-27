/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import ai.gebo.architecture.search.model.SearchCallParameters;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.glpi.client.invoker.ApiClient;
import ai.gebo.glpi.handler.GGlpiSystem;
import ai.gebo.restintegration.abstraction.layer.GeboHttpClientObservations;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;
import tools.jackson.databind.cfg.EnumFeature;
import tools.jackson.databind.json.JsonMapper;

/**
 * Builds the generated GLPI client for a system. The GLPI high-level API v2
 * accepts only OAuth2 bearer tokens on its data endpoints: the factory obtains
 * them itself with the password grant (the generated token call carries no
 * form fields) and keeps them until they expire.
 */
@Service
public class GlpiApiClientFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlpiApiClientFactory.class);
	/**
	 * The API version the client in gebo.integrations.clients was generated from:
	 * GLPI picks the version from this header, the latest one when it is missing
	 */
	public static final String GLPI_API_VERSION = "2.3.0";
	public static final String GLPI_API_VERSION_HEADER = "GLPI-API-Version";
	public static final String API_PATH = "/api.php";
	public static final String TOKEN_PATH = API_PATH + "/token";
	/** The scope giving access to every endpoint not handled by its own scope */
	public static final String OAUTH_SCOPE = "api";
	/** A token is renewed this long before GLPI declares it expired */
	private static final long EXPIRY_MARGIN_MILLIS = 60_000L;

	private final IGeboSecretsAccessService secretService;
	private final GlpiSystemsConfigurationDao systemsDao;
	private final ObjectProvider<ObservationRegistry> observationRegistry;
	private final Map<String, CachedToken> tokens = new ConcurrentHashMap<String, CachedToken>();

	public GlpiApiClientFactory(IGeboSecretsAccessService secretService, GlpiSystemsConfigurationDao systemsDao,
			ObjectProvider<ObservationRegistry> observationRegistry) {
		this.secretService = secretService;
		this.systemsDao = systemsDao;
		this.observationRegistry = observationRegistry;
	}

	/** The credentials of the password grant, read from the two secrets of a system */
	record GlpiCredentials(String clientId, String clientSecret, String username, String password) {
	}

	private record CachedToken(String accessToken, long expiresAtMillis) {
		boolean valid() {
			return System.currentTimeMillis() < expiresAtMillis;
		}
	}

	/** The system declared in configuration or stored, by its code */
	public GGlpiSystem findSystem(String systemCode) {
		GGlpiSystem system = systemsDao.findByCode(systemCode);
		if (system == null) {
			throw new IllegalStateException("No such GLPI system: " + systemCode);
		}
		return system;
	}

	public ApiClient getApiClient(String systemCode) throws GeboCryptSecretException {
		return getApiClient(findSystem(systemCode));
	}

	public ApiClient getApiClient(GGlpiSystem system) throws GeboCryptSecretException {
		return getApiClient(system, null);
	}

	public ApiClient getApiClient(GGlpiSystem system, SearchCallParameters parameters)
			throws GeboCryptSecretException {
		final String root = rootUrlOf(system.getBaseUri());
		if (root == null) {
			throw new IllegalStateException("The GLPI system " + system.getCode() + " has no base URI");
		}
		final GlpiCredentials credentials = readCredentials(system);
		final JsonMapper mapper = createMapper();
		final RestClient restClient = restClient(mapper, parameters);
		final ApiClient apiClient = new ApiClient(restClient, mapper, ApiClient.createDefaultDateFormat());
		apiClient.setBasePath(root + API_PATH);
		apiClient.addDefaultHeader(GLPI_API_VERSION_HEADER, GLPI_API_VERSION);
		final String cacheKey = cacheKeyOf(system);
		apiClient.setAccessToken(() -> token(cacheKey, root, credentials, restClient));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GLPI client for system " + system.getCode() + " on " + root + API_PATH + " (api version "
					+ GLPI_API_VERSION + ", user " + credentials.username() + ")");
		}
		return apiClient;
	}

	/**
	 * Asks GLPI a new token for the system, bypassing the cache: the test of a
	 * system being inserted or modified must use its current credentials
	 */
	public String requestNewToken(GGlpiSystem system) throws GeboCryptSecretException {
		final String root = rootUrlOf(system.getBaseUri());
		if (root == null) {
			throw new IllegalStateException("The GLPI system has no base URI");
		}
		CachedToken token = requestToken(root, readCredentials(system), restClient(createMapper(), null));
		return token.accessToken();
	}

	/**
	 * The jackson mapper of the generated client, tolerant of enum values the
	 * GLPI specification does not list (e.g. ticket priority 6, "major"): an
	 * unknown value is read as null instead of failing the whole page
	 */
	static JsonMapper createMapper() {
		return ApiClient.createDefaultMapper(null).rebuild().enable(EnumFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
				.build();
	}

	private RestClient restClient(JsonMapper mapper, SearchCallParameters parameters) {
		final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		if (parameters != null) {
			requestFactory.setConnectTimeout(parameters.connectTimeout());
			requestFactory.setReadTimeout(parameters.readTimeout());
		}
		RestClient.Builder builder = ApiClient.buildRestClientBuilder(mapper).requestFactory(requestFactory);
		return GeboHttpClientObservations.observed(builder, GeboHttpClientObservations.registryOf(observationRegistry))
				.build();
	}

	GlpiCredentials readCredentials(GGlpiSystem system) throws GeboCryptSecretException {
		if (system.getOauthClientSecretCode() == null || system.getOauthClientSecretCode().isBlank()) {
			throw new IllegalStateException("The GLPI system has no OAuth client credentials");
		}
		if (system.getSecretCode() == null || system.getSecretCode().isBlank()) {
			throw new IllegalStateException("The GLPI system has no user credentials");
		}
		AbstractGeboSecretContent clientSecret = secretService.getSecretContentById(system.getOauthClientSecretCode());
		AbstractGeboSecretContent userSecret = secretService.getSecretContentById(system.getSecretCode());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GLPI system " + system.getCode() + " secrets: client=" + typeOf(clientSecret) + ", user="
					+ typeOf(userSecret));
		}
		if (!(clientSecret instanceof GeboOauth2SecretContent oauth)) {
			throw new IllegalStateException(
					"The GLPI OAuth client secret must be an OAUTH2_STANDARD secret, found " + typeOf(clientSecret));
		}
		if (!(userSecret instanceof GeboUsernamePasswordContent user)) {
			throw new IllegalStateException(
					"The GLPI user secret must be a USERNAME_PASSWORD secret, found " + typeOf(userSecret));
		}
		return new GlpiCredentials(oauth.getClientId(), oauth.getSecret(), user.getUsername(), user.getPassword());
	}

	private static String typeOf(AbstractGeboSecretContent content) {
		return content != null ? content.type().name() : "none";
	}

	private String token(String cacheKey, String root, GlpiCredentials credentials, RestClient restClient) {
		if (cacheKey != null) {
			CachedToken cached = tokens.get(cacheKey);
			if (cached != null && cached.valid()) {
				return cached.accessToken();
			}
		}
		CachedToken fresh = requestToken(root, credentials, restClient);
		if (cacheKey != null) {
			tokens.put(cacheKey, fresh);
		}
		return fresh.accessToken();
	}

	private CachedToken requestToken(String root, GlpiCredentials credentials, RestClient restClient) {
		final String tokenUri = root + TOKEN_PATH;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Requesting a GLPI token (password grant) at " + tokenUri + " for user "
					+ credentials.username());
		}
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("grant_type", "password");
		form.add("client_id", credentials.clientId());
		form.add("client_secret", credentials.clientSecret());
		form.add("username", credentials.username());
		form.add("password", credentials.password());
		form.add("scope", OAUTH_SCOPE);
		Map<?, ?> response = restClient.post().uri(tokenUri).contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON).body(form).retrieve().body(Map.class);
		Object accessToken = response != null ? response.get("access_token") : null;
		if (accessToken == null) {
			throw new IllegalStateException("GLPI returned no access token from " + tokenUri);
		}
		long expiresIn = response.get("expires_in") instanceof Number n ? n.longValue() : 0L;
		long expiresAt = System.currentTimeMillis() + Math.max(0L, expiresIn * 1000L - EXPIRY_MARGIN_MILLIS);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("GLPI token obtained for user " + credentials.username() + ", expires in " + expiresIn + "s");
		}
		return new CachedToken(accessToken.toString(), expiresAt);
	}

	private static String cacheKeyOf(GGlpiSystem system) {
		if (system.getCode() == null) {
			return null;
		}
		return system.getCode() + "|" + system.getOauthClientSecretCode() + "|" + system.getSecretCode();
	}

	/**
	 * The GLPI root url (the web interface one) of a system base URI: accepts the
	 * root itself or any url under /api.php, with or without trailing slash
	 */
	public static String rootUrlOf(String baseUri) {
		if (baseUri == null || baseUri.isBlank()) {
			return null;
		}
		String root = baseUri.trim();
		int api = root.indexOf(API_PATH);
		if (api >= 0) {
			root = root.substring(0, api);
		}
		while (root.endsWith("/")) {
			root = root.substring(0, root.length() - 1);
		}
		return root.isEmpty() ? null : root;
	}
}
