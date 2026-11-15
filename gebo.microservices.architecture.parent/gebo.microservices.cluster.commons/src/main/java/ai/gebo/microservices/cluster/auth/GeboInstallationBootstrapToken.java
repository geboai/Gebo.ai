/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.cluster.auth;

import java.time.Duration;
import java.util.Date;
import java.util.List;

import ai.gebo.security.services.impl.LocalJwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * The token a microservice uses to ask heimdall for the installation id, and
 * only for that: a microservice needs the id before it can mint any platform
 * token, so this one is a plain JWT without the platform's features (no token
 * id, no installation id, no session, no authentication type), signed with the
 * shared token secret, valid five minutes, carried in the
 * {@value #HEADER} header instead of {@code Authorization}. Used only between
 * the installation-id client and heimdall's installation controller: the
 * platform's validation refuses it everywhere else (it has no installation id),
 * and the controller refuses a platform token.
 */
public final class GeboInstallationBootstrapToken {

	/** The header the token travels in, never {@code Authorization}. */
	public static final String HEADER = "GeboAuthorization";
	public static final Duration LIFETIME = Duration.ofMinutes(5);
	/** Clock skew tolerated between the minting service and heimdall. */
	private static final Duration SKEW = Duration.ofSeconds(30);
	private static final String BEARER = "Bearer ";
	/** Claims that mark a platform token, which this channel refuses. */
	private static final List<String> PLATFORM_CLAIMS = List.of(LocalJwtTokenProvider.AUTH_TYPE_CLAIM,
			LocalJwtTokenProvider.AUTH_PROVIDER_CLAIM, LocalJwtTokenProvider.INSTALLATION_ID_CLAIM,
			LocalJwtTokenProvider.SESSION_ID_CLAIM, LocalJwtTokenProvider.API_KEY_CLAIM);

	private GeboInstallationBootstrapToken() {
	}

	/**
	 * @param tokenSecret the shared token secret
	 * @param subject     the calling microservice (its application name)
	 * @return the header value: {@code Bearer <jwt>}
	 */
	public static String mint(String tokenSecret, String subject) {
		Date now = new Date();
		String jwt = Jwts.builder().setSubject(subject).setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + LIFETIME.toMillis()))
				.signWith(SignatureAlgorithm.HS512, tokenSecret).compact();
		return BEARER + jwt;
	}

	/**
	 * @param tokenSecret the shared token secret
	 * @param headerValue the {@value #HEADER} header, {@code Bearer <jwt>} or the bare jwt
	 * @return the calling microservice
	 * @throws IllegalArgumentException when the token is absent, forged, expired,
	 *                                  longer-lived than five minutes, or a
	 *                                  platform token
	 */
	public static String verify(String tokenSecret, String headerValue) {
		if (headerValue == null || headerValue.isBlank()) {
			throw new IllegalArgumentException("No " + HEADER + " header");
		}
		String jwt = headerValue.startsWith(BEARER) ? headerValue.substring(BEARER.length()).trim()
				: headerValue.trim();
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(jwt).getBody();
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("Invalid " + HEADER + " token: " + e.getMessage(), e);
		}
		if (claims.getIssuedAt() == null || claims.getExpiration() == null) {
			throw new IllegalArgumentException(HEADER + " token without issue or expiration time");
		}
		long lifetime = claims.getExpiration().getTime() - claims.getIssuedAt().getTime();
		if (lifetime > LIFETIME.plus(SKEW).toMillis()) {
			throw new IllegalArgumentException(HEADER + " token valid longer than " + LIFETIME);
		}
		if (claims.getId() != null || PLATFORM_CLAIMS.stream().anyMatch(claims::containsKey)) {
			throw new IllegalArgumentException("A platform token is not accepted in " + HEADER);
		}
		if (claims.getSubject() == null || claims.getSubject().isBlank()) {
			throw new IllegalArgumentException(HEADER + " token without subject");
		}
		return claims.getSubject();
	}
}
