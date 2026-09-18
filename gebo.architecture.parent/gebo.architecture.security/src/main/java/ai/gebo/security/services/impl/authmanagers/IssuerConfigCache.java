/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.services.impl.authmanagers;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;

/**
 * Thread-safe cache mapping a JWT {@code iss} claim to the
 * {@link Oauth2RuntimeConfiguration} that matches it.
 *
 * <p>
 * Without this, resolving which configured OAuth2 provider a token belongs to means
 * scanning every {@code AUTHENTICATION}-type {@code oauth2configs} entry on every
 * single request (both to auto-detect a token's type when {@code X-AuthType} is
 * absent, and to resolve the {@code AuthProvider} for auto-provisioning) - exactly
 * the same "rebuild it from scratch on every request" shape that
 * {@link JwtDecoderCache} exists to avoid for the decoder itself. Caching one
 * resolution per issuer turns that into a one-time cost per issuer instead of a
 * per-request cost.
 * </p>
 *
 * <p>
 * A "no match" result is cached too ({@code Optional.empty()}) - an issuer with no
 * matching configuration is an answer worth not re-deriving on every request from
 * that same unrecognized issuer either.
 * </p>
 *
 * <p>
 * <b>Staleness</b>: like {@link JwtDecoderCache}, entries live for the application's
 * lifetime with no TTL or invalidation hook - an admin adding/editing/deleting an
 * {@code oauth2configs} entry via {@code OAuth2AdminController} will not be reflected
 * here until restart. This mirrors the same tradeoff already accepted for
 * {@link JwtDecoderCache} (also never invalidated) rather than introducing a new,
 * inconsistent staleness policy for a sibling cache serving the same request path.
 * </p>
 */
public final class IssuerConfigCache {

	private final ConcurrentHashMap<String, Optional<Oauth2RuntimeConfiguration>> configsByIssuer = new ConcurrentHashMap<>();

	/**
	 * Returns the cached resolution for this issuer, computing and caching it (via
	 * {@code loader}) on first use.
	 *
	 * @param issuer the JWT {@code iss} claim; {@code null} always resolves to
	 *               {@link Optional#empty()} without invoking or caching anything
	 * @param loader supplies the matching configuration, or {@code null} if none
	 *               matches; invoked at most once per distinct issuer
	 * @return the matching configuration, or empty if none matches this issuer
	 */
	public Optional<Oauth2RuntimeConfiguration> forIssuer(String issuer,
			Supplier<Oauth2RuntimeConfiguration> loader) {
		if (issuer == null) {
			return Optional.empty();
		}
		return configsByIssuer.computeIfAbsent(issuer, key -> Optional.ofNullable(loader.get()));
	}
}
