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

import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;

/**
 * Reactive counterpart of {@link JwtDecoderCache}: a thread-safe cache of
 * {@link ReactiveJwtDecoder} instances keyed by OIDC issuer, so reactive
 * bearer-token requests reuse one decoder (and its JWKS cache) per issuer
 * instead of rebuilding it per request.
 */
public final class ReactiveJwtDecoderCache {

	private final ConcurrentHashMap<String, ReactiveJwtDecoder> decodersByIssuer = new ConcurrentHashMap<>();

	public ReactiveJwtDecoder forIssuerLocation(String issuerUri) {
		if (issuerUri == null) {
			throw new IllegalArgumentException("issuerUri must not be null");
		}
		return decodersByIssuer.computeIfAbsent(issuerUri, ReactiveJwtDecoders::fromIssuerLocation);
	}
}
