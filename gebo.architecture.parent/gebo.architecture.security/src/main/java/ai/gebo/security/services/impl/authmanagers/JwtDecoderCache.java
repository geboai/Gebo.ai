/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl.authmanagers;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;

/**
 * Thread-safe cache of {@link JwtDecoder} instances keyed by OIDC issuer URI.
 *
 * <p>
 * Building a decoder via {@link JwtDecoders#fromIssuerLocation(String)} performs
 * blocking network IO (OIDC discovery on {@code /.well-known/openid-configuration}
 * plus the JWKS download). The per-request authentication managers used to build a
 * fresh decoder on every call, discarding the decoder's internal JWKS cache each
 * time and turning every bearer-token request into one or more round-trips to the
 * identity provider.
 * </p>
 *
 * <p>
 * Caching one decoder per issuer reuses that internal key cache. The
 * {@code NimbusJwtDecoder} returned by {@code fromIssuerLocation} refreshes the
 * JWKS on key rotation by itself, so a cached instance stays valid for the
 * lifetime of the application; the discovery/JWKS fetch becomes a one-time cost
 * per issuer instead of a per-request cost.
 * </p>
 *
 * <p>
 * A failed build is not cached (the exception propagates and the next request
 * retries), so a transiently unreachable issuer can recover without a restart.
 * </p>
 */
public final class JwtDecoderCache {

	private final ConcurrentHashMap<String, JwtDecoder> decodersByIssuer = new ConcurrentHashMap<>();

	/**
	 * Returns a cached {@link JwtDecoder} for the given issuer location, building and
	 * caching one on first use.
	 *
	 * @param issuerUri the OIDC issuer location (must not be {@code null})
	 * @return a reusable decoder for that issuer
	 */
	public JwtDecoder forIssuerLocation(String issuerUri) {
		if (issuerUri == null) {
			throw new IllegalArgumentException("issuerUri must not be null");
		}
		return decodersByIssuer.computeIfAbsent(issuerUri, JwtDecoders::fromIssuerLocation);
	}
}
