/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl.authmanagers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TTL cache of OAuth2 bearer tokens that have already had their user
 * create/sync run at the resource server.
 *
 * <p>
 * Under {@code TRUST_EVERY_OAUTH_IDENTITY} an accepted token would otherwise
 * drive a user create/sync on every single request. Recording the token here
 * after the first sync lets subsequent requests bearing the same token skip the
 * sync until the entry expires, bounding the sync to at most once per token per
 * TTL window.
 * </p>
 *
 * <p>
 * Tokens are never stored in clear: only a SHA-256 hash of the token value is
 * kept as the key, so the cache cannot leak reusable credentials. Expired
 * entries are evicted lazily on access and opportunistically when the map grows,
 * keeping the footprint bounded without a background thread.
 * </p>
 */
public final class Oauth2ResourceServerSyncTokenCache {

	private final ConcurrentHashMap<String, Long> expiryByTokenHash = new ConcurrentHashMap<>();
	private final long ttlMillis;
	// Above this size an access triggers a sweep of expired entries, so a churn of
	// short-lived tokens cannot grow the map without bound between hits.
	private static final int SWEEP_THRESHOLD = 4096;

	public Oauth2ResourceServerSyncTokenCache(long ttlSeconds) {
		this.ttlMillis = Math.max(0L, ttlSeconds) * 1000L;
	}

	/**
	 * @return {@code true} if the token was synced recently and its entry has not
	 *         expired.
	 */
	public boolean contains(String token) {
		if (token == null || ttlMillis <= 0L)
			return false;
		String key = hash(token);
		Long expiry = expiryByTokenHash.get(key);
		if (expiry == null)
			return false;
		if (expiry <= System.currentTimeMillis()) {
			expiryByTokenHash.remove(key, expiry);
			return false;
		}
		return true;
	}

	/**
	 * Records that the token has just been synced, valid for the configured TTL.
	 */
	public void put(String token) {
		if (token == null || ttlMillis <= 0L)
			return;
		if (expiryByTokenHash.size() > SWEEP_THRESHOLD)
			sweepExpired();
		expiryByTokenHash.put(hash(token), System.currentTimeMillis() + ttlMillis);
	}

	private void sweepExpired() {
		long now = System.currentTimeMillis();
		expiryByTokenHash.forEach((k, expiry) -> {
			if (expiry <= now)
				expiryByTokenHash.remove(k, expiry);
		});
	}

	private static String hash(String token) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] out = digest.digest(token.getBytes(StandardCharsets.UTF_8));
			return HexFormat.of().formatHex(out);
		} catch (NoSuchAlgorithmException e) {
			// SHA-256 is a mandated JCA algorithm; this cannot happen on a standard JRE.
			throw new IllegalStateException("SHA-256 not available", e);
		}
	}
}
