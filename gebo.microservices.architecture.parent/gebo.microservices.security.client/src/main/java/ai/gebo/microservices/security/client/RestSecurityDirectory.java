/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.security.client;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;
import ai.gebo.security.model.UserInfosImpl;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.model.UserInfos;
import ai.gebo.security.services.IGSecurityDirectory;

/**
 * The {@link IGSecurityDirectory} of a service that does <b>not</b> own the user
 * store: it asks heimdall.
 *
 * <p>
 * This is the whole of the security client-proxy. The authorization services
 * ({@code GSecurityServiceImpl}, {@code AclGrantedAccessorServiceImpl}) are
 * <i>not</i> reimplemented here - they ship unchanged and keep running their
 * algorithm locally, over the caller's own objects and a locally replicated
 * {@code IAclAliasesDao}. Only the directory moved. One copy of the rules that
 * decide who may read what, not two.
 * </p>
 *
 * <h2>The cache is not an optimisation</h2>
 * <p>
 * {@code filterCanDoAction(...)} calls {@code getCurrentUser()} once per object in
 * the collection, and {@code isCanAccess(...)} looks the user's groups up every
 * time. Without caching, filtering a page of 50 documents would issue ~100 HTTP
 * calls to heimdall. The lookups are therefore memoised for the duration of the
 * <b>current request</b> (or, off a request thread, for the duration of a single
 * call chain via a thread-local): one request resolves the identity once. A
 * request-scoped lifetime is also what keeps it correct - a user's groups changing
 * takes effect on their next request, and nothing is cached across users.
 * </p>
 *
 * Gebo.ai comment agent
 */
public class RestSecurityDirectory implements IGSecurityDirectory {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestSecurityDirectory.class);

	/** Cache key prefix in the request scope; the suffix is the argument. */
	private static final String CACHE_ATTRIBUTE = RestSecurityDirectory.class.getName() + ".cache";

	/** Used when there is no request bound to the thread (background work). */
	private static final ThreadLocal<Map<String, Object>> OFF_REQUEST_CACHE = new ThreadLocal<>();

	private final WebClient webClient;
	private final GeboMicroserviceUrlResolver urlResolver;
	private final IGeboCallerTokenPropagator tokenPropagator;
	private final String microserviceId;
	private final String basePath;

	public RestSecurityDirectory(WebClient webClient, GeboMicroserviceUrlResolver urlResolver,
			IGeboCallerTokenPropagator tokenPropagator, String microserviceId, String basePath) {
		this.webClient = webClient;
		this.urlResolver = urlResolver;
		this.tokenPropagator = tokenPropagator;
		this.microserviceId = microserviceId;
		this.basePath = trimSlashes(basePath);
	}

	@Override
	public UserInfos findUserByUsername(String username) {
		return cached("user:" + username, () -> call("findUserByUsername",
				() -> webClient.get().uri(uri("findUserByUsername", "username", username))
						.headers(this::applyCallerToken).accept(MediaType.APPLICATION_JSON).retrieve()
						.bodyToMono(UserInfosImpl.class).block()));
	}

	@Override
	public List<UsersGroup> findGroupsOfUser(String username) {
		UsersGroup[] groups = cached("groupsOf:" + username,
				() -> call("findGroupsOfUser", () -> webClient.get()
						.uri(uri("findGroupsOfUser", "username", username)).headers(this::applyCallerToken)
						.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(UsersGroup[].class).block()));
		return groups == null ? List.of() : List.of(groups);
	}

	@Override
	public List<UsersGroup> findAllGroups() {
		UsersGroup[] groups = cached("allGroups", () -> call("findAllGroups",
				() -> webClient.get().uri(uri("findAllGroups")).headers(this::applyCallerToken)
						.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(UsersGroup[].class).block()));
		return groups == null ? List.of() : List.of(groups);
	}

	@Override
	public boolean checkPassword(String username, String rawPassword) {
		// NOT cached, and never will be: a password check is an authentication decision
		// about a value the caller just supplied, not a fact about the user.
		Map<String, String> request = new LinkedHashMap<>();
		request.put("username", username);
		request.put("password", rawPassword);
		Boolean matches = call("checkPassword",
				() -> webClient.post().uri(uri("checkPassword")).headers(this::applyCallerToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
						.bodyValue(request).retrieve().bodyToMono(Boolean.class).block());
		return Boolean.TRUE.equals(matches);
	}

	// --- Internals ----------------------------------------------------------

	/**
	 * Memoises a lookup for the lifetime of the current request (or call chain, off a
	 * request thread). {@code null} results are cached too - "this user does not
	 * exist" is an answer worth not asking twice.
	 */
	@SuppressWarnings("unchecked")
	private <T> T cached(String key, Supplier<T> loader) {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		if (attributes != null) {
			Map<String, Object> cache = (Map<String, Object>) attributes.getAttribute(CACHE_ATTRIBUTE,
					RequestAttributes.SCOPE_REQUEST);
			if (cache == null) {
				cache = new LinkedHashMap<>();
				attributes.setAttribute(CACHE_ATTRIBUTE, cache, RequestAttributes.SCOPE_REQUEST);
			}
			if (cache.containsKey(key)) {
				return (T) cache.get(key);
			}
			T value = loader.get();
			cache.put(key, value);
			return value;
		}
		Map<String, Object> cache = OFF_REQUEST_CACHE.get();
		if (cache == null) {
			// No request and no chain started: a single lookup, uncached. Correct, just not
			// memoised - see runCached(...) to memoise a background call chain.
			return loader.get();
		}
		if (cache.containsKey(key)) {
			return (T) cache.get(key);
		}
		T value = loader.get();
		cache.put(key, value);
		return value;
	}

	/**
	 * Memoises directory lookups for the duration of {@code work} on a thread that has
	 * no request bound to it - the background paths (startup, model replication, MCP
	 * reconnects, schedulers) that would otherwise re-ask heimdall per object.
	 *
	 * @param work the work to run
	 */
	public static void runCached(Runnable work) {
		boolean owner = OFF_REQUEST_CACHE.get() == null;
		if (owner) {
			OFF_REQUEST_CACHE.set(new LinkedHashMap<>());
		}
		try {
			work.run();
		} finally {
			if (owner) {
				OFF_REQUEST_CACHE.remove();
			}
		}
	}

	/** Adds the caller's bearer token, read on this - the calling - thread. */
	private void applyCallerToken(HttpHeaders headers) {
		String token = tokenPropagator.currentToken();
		if (StringUtils.hasText(token)) {
			headers.setBearerAuth(token);
		}
	}

	private URI uri(String endpoint) {
		return UriComponentsBuilder.fromUriString(baseUrl() + "/" + basePath + "/" + endpoint).build().encode()
				.toUri();
	}

	private URI uri(String endpoint, String paramName, String paramValue) {
		return UriComponentsBuilder.fromUriString(baseUrl() + "/" + basePath + "/" + endpoint)
				.queryParam(paramName, paramValue).build().encode().toUri();
	}

	private String baseUrl() {
		Optional<String> baseUrl = urlResolver.baseUrlForMicroserviceId(microserviceId);
		return baseUrl.orElseThrow(() -> new IllegalStateException("Cannot resolve the base url of the security "
				+ "microservice '" + microserviceId + "': it is not a member of the topology and has no 'direct' "
				+ "entry (gebo.microservices.topology.url.direct)."));
	}

	/**
	 * The directory methods do not declare checked exceptions, so a transport failure
	 * surfaces as an unchecked one. It must NOT be swallowed into "no such user" /
	 * "no groups": that would silently turn an outage into an authorization decision,
	 * and the safe direction here is to fail the request, not to quietly deny (or
	 * grant) access on stale emptiness.
	 */
	private <T> T call(String operation, Supplier<T> remoteCall) {
		try {
			return remoteCall.get();
		} catch (WebClientResponseException ex) {
			LOGGER.error("Remote {} failed: {} {}", operation, ex.getStatusCode(), ex.getResponseBodyAsString());
			throw new IllegalStateException("Security directory call '" + operation + "' failed: "
					+ ex.getStatusCode(), ex);
		} catch (RuntimeException ex) {
			LOGGER.error("Remote {} failed", operation, ex);
			throw new IllegalStateException("Security directory call '" + operation + "' failed", ex);
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
