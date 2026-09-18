/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl.authmanagers;

import java.util.Collection;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;

/**
 * Reactive counterpart of {@link JwtDecoderCache}: a thread-safe cache of
 * {@link ReactiveJwtDecoder} instances keyed by OIDC issuer plus accepted-audience
 * set, so reactive bearer-token requests reuse one decoder (and its JWKS cache)
 * per distinct configuration instead of rebuilding it per request, and enforce the
 * same audience check as the servlet path.
 */
public final class ReactiveJwtDecoderCache {

	private final ConcurrentHashMap<String, ReactiveJwtDecoder> decodersByKey = new ConcurrentHashMap<>();

	/**
	 * Returns a cached decoder validating signature, issuer and expiry only.
	 *
	 * @param issuerUri the OIDC issuer location (must not be {@code null})
	 */
	public ReactiveJwtDecoder forIssuerLocation(String issuerUri) {
		return forIssuerLocation(issuerUri, null);
	}

	/**
	 * Returns a cached decoder that also rejects tokens whose audience is not one of
	 * {@code acceptedAudiences}.
	 *
	 * @param issuerUri         the OIDC issuer location (must not be {@code null})
	 * @param acceptedAudiences audiences (or client ids) to require; {@code null} or
	 *                          empty means no audience check
	 */
	public ReactiveJwtDecoder forIssuerLocation(String issuerUri, Collection<String> acceptedAudiences) {
		if (issuerUri == null) {
			throw new IllegalArgumentException("issuerUri must not be null");
		}
		return decodersByKey.computeIfAbsent(cacheKey(issuerUri, acceptedAudiences),
				k -> build(issuerUri, acceptedAudiences));
	}

	private static ReactiveJwtDecoder build(String issuerUri, Collection<String> acceptedAudiences) {
		NimbusReactiveJwtDecoder decoder = (NimbusReactiveJwtDecoder) ReactiveJwtDecoders
				.fromIssuerLocation(issuerUri);
		if (acceptedAudiences != null && !acceptedAudiences.isEmpty()) {
			OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
			decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(withIssuer,
					new GeboOauth2AudienceValidator(acceptedAudiences)));
		}
		return decoder;
	}

	private static String cacheKey(String issuerUri, Collection<String> acceptedAudiences) {
		if (acceptedAudiences == null || acceptedAudiences.isEmpty()) {
			return issuerUri;
		}
		return issuerUri + " " + String.join(",", new TreeSet<>(acceptedAudiences));
	}
}
