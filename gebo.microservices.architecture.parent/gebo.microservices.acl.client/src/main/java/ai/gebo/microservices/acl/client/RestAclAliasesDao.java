/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.acl.client;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.GAclEntry;
import ai.gebo.acl.IAclAliasesDao;
import ai.gebo.microservices.cluster.auth.IGeboCallerTokenPropagator;
import ai.gebo.microservices.cluster.cache.GeboTtlCache;
import ai.gebo.microservices.topology.GeboMicroserviceUrlResolver;

/**
 * The {@link IAclAliasesDao} of a service that does <b>not</b> own the ACL store:
 * it calls the owner, and caches what it gets.
 *
 * <h2>Cache instead of replication</h2>
 * <p>
 * The alias data is small, read constantly and written rarely - every
 * authorization decision reads it, almost nothing changes it. That is the profile a
 * cache serves well, and it is why this client makes a Hazelcast-replicated alias
 * map unnecessary: rather than every service holding and maintaining a full replica,
 * each holds the handful of entries it actually asks about, and forgets them.
 * </p>
 *
 * <p>
 * <b>The trade-off, stated plainly: reads are stale for up to the TTL.</b> A grant
 * made <i>elsewhere</i> - by an admin on heimdall, or by another service - becomes
 * visible here within {@code cache-ttl} (default 60s), not instantly. Two things
 * bound the damage:
 * </p>
 * <ul>
 * <li>a write made <i>through this client</i> invalidates the cache immediately, so
 * the service that granted access sees its own grant at once;</li>
 * <li>{@code findAcl(alias)} is cached without expiry - an alias, once allocated,
 * never changes meaning.</li>
 * </ul>
 * <p>
 * If a deployment cannot tolerate a stale <i>revocation</i> for that window, shorten
 * the TTL; that is the dial. Replication would trade this bounded staleness for the
 * cost of keeping every service's replica correct - a worse deal for data this small.
 * </p>
 *
 * <h2>Writes are not cached, and not local</h2>
 * <p>
 * {@link #addAcl(GAclEntry)} allocates an alias integer from a Mongo sequence that
 * lives with the owner. It is forwarded, never performed here: a second allocator
 * would hand out colliding integers, and two grants sharing an alias is a silent
 * authorization bug.
 * </p>
 *
 * Gebo.ai comment agent
 */
public class RestAclAliasesDao implements IAclAliasesDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestAclAliasesDao.class);

	private final WebClient webClient;
	private final GeboMicroserviceUrlResolver urlResolver;
	private final IGeboCallerTokenPropagator tokenPropagator;
	private final String microserviceId;
	private final String basePath;

	/** The shared cluster-client cache - one implementation across secrets, security and ACL. */
	private final GeboTtlCache cache;

	public RestAclAliasesDao(WebClient webClient, GeboMicroserviceUrlResolver urlResolver,
			IGeboCallerTokenPropagator tokenPropagator, String microserviceId, String basePath,
			GeboTtlCache cache) {
		this.webClient = webClient;
		this.urlResolver = urlResolver;
		this.tokenPropagator = tokenPropagator;
		this.microserviceId = microserviceId;
		this.basePath = trimSlashes(basePath);
		this.cache = cache;
	}

	// --- Reads (cached) -----------------------------------------------------

	@Override
	public List<Integer> findAliasesByAclGrantedUniqueId(String aclGrantedUniqueId) {
		return cachedList("byId:" + aclGrantedUniqueId,
				() -> getIntList(uri("findAliasesByAclGrantedUniqueId", "uniqueId", aclGrantedUniqueId)));
	}

	@Override
	public List<Integer> findAliasesByAclGrantedUniqueIdAndAclGrantType(String aclGrantedUniqueId,
			AclGrantType grantType) {
		return cachedList("byIdGrant:" + aclGrantedUniqueId + ":" + grantType,
				() -> getIntList(UriComponentsBuilder.fromUri(uri("findAliasesByAclGrantedUniqueIdAndAclGrantType"))
						.queryParam("uniqueId", aclGrantedUniqueId).queryParam("grantType", grantType).build()
						.encode().toUri()));
	}

	@Override
	public List<Integer> findAliasesByAclGrantedUniqueIdIn(List<String> aclGrantedUniqueId) {
		return cachedList("byIdIn:" + aclGrantedUniqueId,
				() -> postIntList(uri("findAliasesByAclGrantedUniqueIdIn"), aclGrantedUniqueId));
	}

	@Override
	public List<Integer> findAliasesByAclGrantedUniqueIdInAndAclGrantType(List<String> aclGrantedUniqueId,
			AclGrantType grantType) {
		return cachedList("byIdInGrant:" + aclGrantedUniqueId + ":" + grantType,
				() -> postIntList(UriComponentsBuilder
						.fromUri(uri("findAliasesByAclGrantedUniqueIdInAndAclGrantType"))
						.queryParam("grantType", grantType).build().encode().toUri(), aclGrantedUniqueId));
	}

	/**
	 * Cached like the rest, though it could safely be held forever: an alias, once
	 * allocated, always denotes the same grant, so there is nothing here to go stale. It
	 * shares the TTL only because one cache with one policy is easier to reason about
	 * than two, and re-reading an immutable value costs nothing but a round trip.
	 */
	@Override
	public GAclEntry findAcl(int alias) {
		return cache.get("acl:" + alias,
				() -> call("findAcl", () -> webClient.get().uri(uri("findAcl", "alias", String.valueOf(alias)))
						.headers(this::applyCallerToken).accept(MediaType.APPLICATION_JSON).retrieve()
						.bodyToMono(GAclEntry.class).block()));
	}

	@Override
	public Integer findAlias(GAclEntry entry) {
		String key = "alias:" + entry.getAclGrantedUniqueId() + ":" + entry.getGrant();
		return cache.get(key, () -> call("findAlias",
				() -> webClient.post().uri(uri("findAlias")).headers(this::applyCallerToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).bodyValue(entry)
						.retrieve().bodyToMono(Integer.class).block()));
	}

	// --- Writes (forwarded to the owner, never performed here) ----------------

	@Override
	public int addAcl(GAclEntry entry) {
		Integer alias = call("addAcl", () -> webClient.post().uri(uri("addAcl")).headers(this::applyCallerToken)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).bodyValue(entry)
				.retrieve().bodyToMono(Integer.class).block());
		// Our own write must be visible to us at once - waiting out the TTL to see a grant
		// we just made would be indefensible.
		invalidate();
		if (alias == null) {
			throw new IllegalStateException("The ACL owner returned no alias for " + entry.getAclGrantedUniqueId());
		}
		return alias;
	}

	@Override
	public void removeAcl(int alias) {
		call("removeAcl", () -> webClient.delete().uri(uri("removeAcl", "alias", String.valueOf(alias)))
				.headers(this::applyCallerToken).retrieve().toBodilessEntity().block());
		invalidate();
	}

	/** Drops the whole cache. The data is tiny; evicting precisely is not worth the bookkeeping. */
	public void invalidate() {
		cache.clear();
	}

	// --- Internals ----------------------------------------------------------

	private List<Integer> cachedList(String key, Supplier<List<Integer>> loader) {
		List<Integer> value = cache.get(key, loader);
		return value == null ? List.of() : value;
	}

	private List<Integer> getIntList(URI uri) {
		Integer[] aliases = call("findAliases", () -> webClient.get().uri(uri).headers(this::applyCallerToken)
				.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Integer[].class).block());
		return aliases == null ? List.of() : List.of(aliases);
	}

	private List<Integer> postIntList(URI uri, List<String> body) {
		Integer[] aliases = call("findAliases", () -> webClient.post().uri(uri).headers(this::applyCallerToken)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).bodyValue(body)
				.retrieve().bodyToMono(Integer[].class).block());
		return aliases == null ? List.of() : List.of(aliases);
	}

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
		return baseUrl.orElseThrow(() -> new IllegalStateException("Cannot resolve the base url of the ACL owner '"
				+ microserviceId + "': it is not a topology member and has no 'direct' entry."));
	}

	/**
	 * A transport failure must NOT be swallowed into "no aliases": an empty alias list
	 * is a valid answer meaning "this accessor was granted nothing", so turning an
	 * outage into one would silently deny access - or, on the other side of a check,
	 * silently grant it. Fail the request instead.
	 */
	private <T> T call(String operation, Supplier<T> remoteCall) {
		try {
			return remoteCall.get();
		} catch (WebClientResponseException ex) {
			LOGGER.error("Remote ACL {} failed: {} {}", operation, ex.getStatusCode(), ex.getResponseBodyAsString());
			throw new IllegalStateException("ACL call '" + operation + "' failed: " + ex.getStatusCode(), ex);
		} catch (RuntimeException ex) {
			LOGGER.error("Remote ACL {} failed", operation, ex);
			throw new IllegalStateException("ACL call '" + operation + "' failed", ex);
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
