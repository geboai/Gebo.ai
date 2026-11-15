/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl.authmanagers;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;

import ai.gebo.security.services.IGAuthenticatedTokenObserver;
import ai.gebo.security.services.impl.LocalJwtTokenProvider;

/**
 * What the sessions store needs from a token accepted on the OAuth2
 * resource-server path, which is not minted here: an id (the hash of the token),
 * the claims it is listed under, the revocation check and the observers' call.
 * Used by both the servlet and the reactive decorators.
 */
public final class SessionTracking {
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionTracking.class);

	private SessionTracking() {
	}

	/** The claims the store is handed for an accepted external token. */
	public static Map<String, Object> claimsOf(Authentication authentication, String authProviderId) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(LocalJwtTokenProvider.OAUTH2_TOKEN_CLAIM, Boolean.TRUE);
		claims.put(LocalJwtTokenProvider.AUTH_TYPE_CLAIM, "OAUTH2");
		if (authProviderId != null) {
			claims.put(LocalJwtTokenProvider.AUTH_PROVIDER_REGISTRATION_ID, authProviderId);
		}
		if (authentication != null && authentication.getName() != null) {
			claims.put("sub", authentication.getName());
		}
		Instant expiresAt = expiryOf(authentication);
		if (expiresAt != null) {
			claims.put("exp", expiresAt.getEpochSecond());
		}
		return claims;
	}

	private static Instant expiryOf(Authentication authentication) {
		if (authentication instanceof AbstractOAuth2TokenAuthenticationToken<?> tokenAuth) {
			Object token = tokenAuth.getToken();
			if (token instanceof Jwt jwt) {
				return jwt.getExpiresAt();
			}
			if (tokenAuth.getPrincipal() instanceof OAuth2AuthenticatedPrincipal principal) {
				Object exp = principal.getAttribute("exp");
				if (exp instanceof Instant instant) {
					return instant;
				}
				if (exp instanceof Number number) {
					return Instant.ofEpochSecond(number.longValue());
				}
			}
		}
		return null;
	}

	/**
	 * After the delegate accepted the token: refuse it when revoked, tell the
	 * observers otherwise.
	 */
	public static Authentication afterAuthentication(Authentication authenticated, String token,
			LocalJwtTokenProvider tokenProvider, List<IGAuthenticatedTokenObserver> observers, String clientIp,
			String userAgent, String authProviderId) {
		if (authenticated == null || !authenticated.isAuthenticated() || token == null) {
			return authenticated;
		}
		String tokenId = LocalJwtTokenProvider.tokenHash(token);
		Map<String, Object> claims = claimsOf(authenticated, authProviderId);
		if (tokenProvider.isRevoked(tokenId, claims)) {
			throw new BadCredentialsException("revoked token");
		}
		if (observers != null) {
			for (IGAuthenticatedTokenObserver observer : observers) {
				try {
					observer.onAuthenticated(tokenId, claims, clientIp, userAgent);
				} catch (RuntimeException e) {
					LOGGER.warn("Token observer failed (ignored): {}", e.getMessage());
				}
			}
		}
		return authenticated;
	}
}
