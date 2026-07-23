/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.cluster.cache;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.HexFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * A small time-bounded cache for the <b>read phases</b> of the cluster clients.
 *
 * <h2>Why the cluster clients need one at all</h2>
 * <p>
 * Everything these clients fetch - who a user is, which groups they are in, which
 * ACL aliases they hold, what a secret contains - is read constantly and written
 * almost never. Without a cache a busy service calls the owner on <i>every request</i>
 * (worse: {@code filterCanDoAction} resolves the current user once per object in a
 * collection). With one, the same identity is resolved once per TTL.
 * </p>
 *
 * <h2>The trade-off, stated once</h2>
 * <p>
 * A change made <b>elsewhere</b> - a role revoked, a group removed, an ACL grant
 * withdrawn - becomes visible here only when the entry expires. That is the price of
 * not asking the owner every time, and the TTL is the dial. A write made <i>through
 * the caching client</i> must {@link #clear()} it, so a service always sees its own
 * changes immediately; only other services' changes are subject to the window.
 * </p>
 *
 * <p>
 * Deliberately not caching: anything that is an authentication <i>decision</i> rather
 * than a fact - a password check, a token introspection result. Those are about a
 * value the caller just presented, not about the state of the world.
 * </p>
 *
 * <p>
 * Bounded: past {@code maxEntries} the cache is emptied rather than grown. The data is
 * small and re-fetching is cheap, so a hard bound beats an eviction policy that has to
 * be tuned and can still leak.
 * </p>
 *
 * Gebo.ai comment agent
 */
public class GeboTtlCache {

	private final long ttlMillis;
	private final int maxEntries;
	private final Map<String, Entry> entries = new ConcurrentHashMap<>();

	/**
	 * @param ttl how long an entry stays valid; {@code null} or non-positive disables
	 *            caching entirely (every read goes to the owner)
	 * @param maxEntries the hard bound; past it the cache is cleared
	 */
	public GeboTtlCache(Duration ttl, int maxEntries) {
		this.ttlMillis = ttl == null ? 0L : Math.max(0L, ttl.toMillis());
		this.maxEntries = Math.max(1, maxEntries);
	}

	/**
	 * Returns the cached value for {@code key}, loading and caching it if absent or
	 * expired.
	 *
	 * <p>
	 * A {@code null} result is cached too: "there is no such user" is an answer, and
	 * re-asking the owner for it on every request is exactly the traffic this exists to
	 * remove.
	 * </p>
	 *
	 * @param key the cache key
	 * @param loader fetches the value when it is not cached
	 * @param <T> the value type
	 * @return the value, cached or freshly loaded
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String key, Supplier<T> loader) {
		if (ttlMillis <= 0) {
			return loader.get();
		}
		long now = System.currentTimeMillis();
		Entry entry = entries.get(key);
		if (entry != null && entry.expiresAt > now) {
			return (T) entry.value;
		}
		T value = loader.get();
		if (entries.size() >= maxEntries) {
			entries.clear();
		}
		entries.put(key, new Entry(value, now + ttlMillis));
		return value;
	}

	/**
	 * As {@link #get(String, Supplier)}, but for a loader that throws a checked
	 * exception - which the secrets client's does, since a failed fetch surfaces as
	 * {@code GeboCryptSecretException}. A plain {@link Supplier} cannot carry one.
	 *
	 * @param key the cache key
	 * @param loader fetches the value when it is not cached
	 * @param <T> the value type
	 * @param <E> the exception the loader may throw
	 * @return the value, cached or freshly loaded
	 * @throws E if the loader throws
	 */
	@SuppressWarnings("unchecked")
	public <T, E extends Exception> T getChecked(String key, ThrowingSupplier<T, E> loader) throws E {
		if (ttlMillis <= 0) {
			return loader.get();
		}
		long now = System.currentTimeMillis();
		Entry entry = entries.get(key);
		if (entry != null && entry.expiresAt > now) {
			return (T) entry.value;
		}
		T value = loader.get();
		if (entries.size() >= maxEntries) {
			entries.clear();
		}
		entries.put(key, new Entry(value, now + ttlMillis));
		return value;
	}

	/** A {@link Supplier} that may throw a checked exception. */
	@FunctionalInterface
	public interface ThrowingSupplier<T, E extends Exception> {
		T get() throws E;
	}

	/** Drops everything. Call after a write, so the writer sees its own change at once. */
	public void clear() {
		entries.clear();
	}

	/**
	 * A stable, non-reversible key for a bearer token.
	 *
	 * <p>
	 * Hashed rather than used raw so that a heap dump of a long-lived cache does not
	 * hand over live bearer tokens. Keying on the <b>token</b> (not merely the username)
	 * is what binds cached roles to the credential that produced them: present a
	 * different token and the lookup is fresh, so a reissued or revoked credential
	 * cannot ride on an entry cached for the old one.
	 * </p>
	 *
	 * @param token the bearer token; may be {@code null}
	 * @return a hex SHA-256 of the token, or {@code "anonymous"} when there is none
	 */
	public static String tokenKey(String token) {
		if (token == null || token.isBlank()) {
			return "anonymous";
		}
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			return HexFormat.of().formatHex(digest.digest(token.getBytes(StandardCharsets.UTF_8)));
		} catch (NoSuchAlgorithmException e) {
			// SHA-256 is mandatory on every JVM; if it is gone, something is very wrong.
			throw new IllegalStateException("SHA-256 is unavailable", e);
		}
	}

	private static final class Entry {
		final Object value;
		final long expiresAt;

		Entry(Object value, long expiresAt) {
			this.value = value;
			this.expiresAt = expiresAt;
		}
	}
}
