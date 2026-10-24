/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl.authmanagers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.oauth2.Oauth2ProviderConfig;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.services.impl.GMicrosoftAccountsIdTokenValidator;

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

	// Keyed by issuer plus the accepted-audience set, not issuer alone: two
	// registrations can share an issuer yet accept different audiences, and each
	// needs its own validator baked in. The key is built so audience order does not
	// matter and the no-audience case is distinct (see cacheKey). One decoder per
	// (issuer, audience-set) still keeps discovery/JWKS a one-time cost per
	// distinct configuration rather than a per-request one.
	private final ConcurrentHashMap<String, JwtDecoder> decodersByKey = new ConcurrentHashMap<>();

	/**
	 * Returns a cached {@link JwtDecoder} for the given issuer location that
	 * validates signature, issuer and expiry only, building and caching one on first
	 * use.
	 *
	 * @param issuerUri the OIDC issuer location (must not be {@code null})
	 * @return a reusable decoder for that issuer
	 */
	public JwtDecoder forIssuerLocation(String issuerUri) {
		return forIssuerLocation(issuerUri, null);
	}

	/**
	 * Returns a cached {@link JwtDecoder} for the given issuer location that, in
	 * addition to the default signature/issuer/expiry checks, rejects tokens whose
	 * audience is not one of {@code acceptedAudiences}.
	 *
	 * @param issuerUri         the OIDC issuer location (must not be {@code null})
	 * @param acceptedAudiences audiences (or client ids) to require; {@code null} or
	 *                          empty means no audience check (same decoder as
	 *                          {@link #forIssuerLocation(String)})
	 * @return a reusable decoder for that issuer and audience set
	 */
	public JwtDecoder forIssuerLocation(String issuerUri, Collection<String> acceptedAudiences) {
		if (issuerUri == null) {
			throw new IllegalArgumentException("issuerUri must not be null");
		}
		return decodersByKey.computeIfAbsent(cacheKey(issuerUri, acceptedAudiences),
				k -> build(issuerUri, acceptedAudiences));
	}

	/**
	 * Returns a cached {@link JwtDecoder} for the tokens of a configuration, checked
	 * as its provider signs them.
	 *
	 * <p>
	 * For every provider but one that is its single issuer, as
	 * {@link #forIssuerLocation(String, Collection)}. The Microsoft accounts
	 * configuration has no single issuer: its people belong to several tenants, each
	 * signing under an issuer of its own. Its decoder verifies the signature against
	 * the keys of its library entry, which every tenant signs with, and checks the
	 * tenant instead of an issuer ({@link GeboMicrosoftAccountsTenantValidator}).
	 * </p>
	 *
	 * <p>
	 * The admitted tenants are part of what the decoder is cached by, as the
	 * accepted audiences are, so an edit of either applies from the next request.
	 * </p>
	 *
	 * @param configuration a configuration as the store hands it out, its endpoints
	 *                      resolved and its client included
	 * @return a reusable decoder for it
	 */
	public JwtDecoder forConfiguration(Oauth2RuntimeConfiguration configuration) {
		Oauth2ProviderConfig provider = configuration.getProviderConfig();
		if (configuration.getProvider() == AuthProvider.microsoft && provider.getJwkSetUri() != null) {
			Set<String> tenants = GMicrosoftAccountsIdTokenValidator.admittedTenants(configuration);
			Collection<String> audiences = configuration.getResourceServerAudiences();
			return decodersByKey.computeIfAbsent(
					microsoftAccountsCacheKey(provider.getJwkSetUri(), configuration.getRegistrationId(), tenants,
							audiences),
					k -> {
						NimbusJwtDecoder decoder = NimbusJwtDecoder.withJwkSetUri(provider.getJwkSetUri()).build();
						decoder.setJwtValidator(
								microsoftAccountsValidator(tenants, configuration.getRegistrationId(), audiences));
						return decoder;
					});
		}
		return forIssuerLocation(provider.getIssuerUri(), configuration.getResourceServerAudiences());
	}

	/**
	 * The checks a token of the Microsoft accounts configuration passes after its
	 * signature: Spring's defaults without an issuer, the tenant, and the accepted
	 * audiences when the configuration has any. Shared with
	 * {@link ReactiveJwtDecoderCache}.
	 */
	static OAuth2TokenValidator<Jwt> microsoftAccountsValidator(Set<String> admittedTenants, String registrationId,
			Collection<String> acceptedAudiences) {
		List<OAuth2TokenValidator<Jwt>> validators = new ArrayList<>();
		validators.add(new GeboMicrosoftAccountsTenantValidator(admittedTenants, registrationId));
		if (acceptedAudiences != null && !acceptedAudiences.isEmpty()) {
			validators.add(new GeboOauth2AudienceValidator(acceptedAudiences));
		}
		return JwtValidators.createDefaultWithValidators(validators);
	}

	// Its own prefix, with a space in it, so it can never be taken for an issuer's
	// key; tenants and audiences sorted, as in cacheKey.
	static String microsoftAccountsCacheKey(String jwkSetUri, String registrationId, Set<String> admittedTenants,
			Collection<String> acceptedAudiences) {
		return "microsoft-accounts " + jwkSetUri + " " + registrationId + " "
				+ String.join(",", new TreeSet<>(admittedTenants)) + " "
				+ (acceptedAudiences == null ? "" : String.join(",", new TreeSet<>(acceptedAudiences)));
	}

	private static JwtDecoder build(String issuerUri, Collection<String> acceptedAudiences) {
		NimbusJwtDecoder decoder = (NimbusJwtDecoder) JwtDecoders.fromIssuerLocation(issuerUri);
		if (acceptedAudiences != null && !acceptedAudiences.isEmpty()) {
			OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
			decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(withIssuer,
					new GeboOauth2AudienceValidator(acceptedAudiences)));
		}
		return decoder;
	}

	// Space-separated so issuer and audiences cannot collide across the boundary
	// (a space is illegal in an issuer URI); sorting the audiences makes the key
	// order-insensitive.
	private static String cacheKey(String issuerUri, Collection<String> acceptedAudiences) {
		if (acceptedAudiences == null || acceptedAudiences.isEmpty()) {
			return issuerUri;
		}
		return issuerUri + " " + String.join(",", new TreeSet<>(acceptedAudiences));
	}
}
