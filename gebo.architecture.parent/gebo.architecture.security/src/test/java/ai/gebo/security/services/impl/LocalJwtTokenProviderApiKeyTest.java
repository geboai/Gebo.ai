/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.SecurityHeaderUtil.XAuthType;
import ai.gebo.security.services.IGIssuedTokenRevocationChecker;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * An API key is a local JWT marked as such, carrying the id of its record; it
 * is accepted while no checker revokes it, and it is never renewed into a
 * session token.
 */
class LocalJwtTokenProviderApiKeyTest {

	/** jjwt base64-decodes the signing key: a hex string, as real deployments configure. */
	private static final String SECRET = "a3f1c9e7b5d2046f8e1c3a5b7d9f0e2c4a6b8d0f1e3c5a7b9d1f3e5c7a9b0d2fa3f1c9e7b5d2046f8e1c3a5b7d9f0e2c4a6b8d0f1e3c5a7b9d1f3e5c7a9b0d2f";
	private static final String USER = "keyholder@gebo.ai";
	private static final String TOKEN_ID = "0f2d3f4e-key-record";

	private GeboSecurityConfig securityConfig;
	/** The ids a fake key store knows: a token whose id is missing here is revoked. */
	private final Set<String> existingKeys = new CopyOnWriteArraySet<>();
	private final IGIssuedTokenRevocationChecker keyStoreChecker = (tokenId,
			claims) -> Boolean.TRUE.equals(claims.get(LocalJwtTokenProvider.API_KEY_CLAIM))
					&& !existingKeys.contains(tokenId);

	@BeforeEach
	void setUp() {
		securityConfig = new GeboSecurityConfig();
		securityConfig.getAuth().setTokenSecret(SECRET);
		securityConfig.getAuth().setTokenExpirationMsec(120_000L);
		existingKeys.clear();
	}

	private Date inOneHour() {
		return new Date(System.currentTimeMillis() + 3_600_000L);
	}

	private Claims claimsOf(String token) {
		return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
	}

	@Test
	void anApiKeyIsMarkedAndCarriesTheIdOfItsRecord() {
		LocalJwtTokenProvider provider = new LocalJwtTokenProvider(securityConfig);

		String key = provider.createApiKeyToken(USER, inOneHour(), TOKEN_ID, LocalJwtTokenProvider.API_KEY_KIND_USER);

		Claims claims = claimsOf(key);
		assertThat(claims.getSubject()).isEqualTo(USER);
		assertThat(claims.getId()).isEqualTo(TOKEN_ID);
		assertThat(claims.get(LocalJwtTokenProvider.API_KEY_CLAIM, Boolean.class)).isTrue();
		assertThat(claims.get(LocalJwtTokenProvider.API_KEY_KIND_CLAIM, String.class))
				.isEqualTo(LocalJwtTokenProvider.API_KEY_KIND_USER);
		assertThat(LocalJwtTokenProvider.isApiKey(claims)).isTrue();
		assertThat(provider.getUserIdFromToken(key)).isEqualTo(USER);
	}

	@Test
	void aSessionTokenIsNotAnApiKeyAndHasNoId() {
		LocalJwtTokenProvider provider = new LocalJwtTokenProvider(securityConfig);

		String session = provider.createToken(USER, inOneHour());

		Claims claims = claimsOf(session);
		assertThat(claims.getId()).isNull();
		assertThat(LocalJwtTokenProvider.isApiKey(claims)).isFalse();
	}

	@Test
	void anApiKeyIsAcceptedWhileItsRecordExistsAndRefusedOnceDeleted() {
		LocalJwtTokenProvider provider = new LocalJwtTokenProvider(securityConfig, List.of(keyStoreChecker));
		String key = provider.createApiKeyToken(USER, inOneHour(), TOKEN_ID, LocalJwtTokenProvider.API_KEY_KIND_ADMIN);

		existingKeys.add(TOKEN_ID);
		assertThat(provider.validateToken(key)).as("the key exists").isTrue();

		existingKeys.remove(TOKEN_ID);
		assertThat(provider.validateToken(key)).as("the key was deleted: revoked before its expiration").isFalse();
	}

	@Test
	void aCheckerIsNotAskedAboutSessionTokens() {
		Set<String> asked = new CopyOnWriteArraySet<>();
		IGIssuedTokenRevocationChecker spy = (tokenId, claims) -> {
			asked.add(tokenId);
			return true;
		};
		LocalJwtTokenProvider provider = new LocalJwtTokenProvider(securityConfig, List.of(spy));

		assertThat(provider.validateToken(provider.createToken(USER, inOneHour()))).isTrue();
		assertThat(asked).isEmpty();
	}

	@Test
	void aCheckerSeesTheClaimsAndCannotChangeThem() {
		IGIssuedTokenRevocationChecker tampering = (tokenId, claims) -> {
			assertThat(claims.get(LocalJwtTokenProvider.API_KEY_CLAIM)).isEqualTo(Boolean.TRUE);
			try {
				claims.put("role", "ADMIN");
			} catch (UnsupportedOperationException expected) {
				return false;
			}
			throw new AssertionError("the claims handed to a checker must be read-only");
		};
		LocalJwtTokenProvider provider = new LocalJwtTokenProvider(securityConfig, List.of(tampering));
		String key = provider.createApiKeyToken(USER, inOneHour(), TOKEN_ID, LocalJwtTokenProvider.API_KEY_KIND_USER);

		assertThat(provider.validateToken(key)).isTrue();
	}

	@Test
	void anApiKeyIsNeverRenewedIntoASessionToken() {
		LocalJwtTokenProvider provider = new LocalJwtTokenProvider(securityConfig);
		String key = provider.createApiKeyToken(USER, inOneHour(), TOKEN_ID, LocalJwtTokenProvider.API_KEY_KIND_USER);
		SecurityHeaderData presented = new SecurityHeaderData(key, XAuthType.LOCAL_JWT, "local", null, false);

		SecurityHeaderData renewed = provider.renew(presented);

		assertThat(renewed.getToken()).as("the very same key comes back").isEqualTo(key);
	}

	@Test
	void aSessionTokenIsStillRenewed() {
		LocalJwtTokenProvider provider = new LocalJwtTokenProvider(securityConfig);
		String session = provider.createToken(USER, inOneHour());
		SecurityHeaderData presented = new SecurityHeaderData(session, XAuthType.LOCAL_JWT, "local", null, false);

		SecurityHeaderData renewed = provider.renew(presented);

		assertThat(renewed.getToken()).isNotEqualTo(session);
		assertThat(provider.getUserIdFromToken(renewed.getToken())).isEqualTo(USER);
		assertThat(claimsOf(renewed.getToken()).getId()).isNull();
	}

	@Test
	void theCheckersAreCopiedAtConstruction() {
		List<IGIssuedTokenRevocationChecker> checkers = new java.util.ArrayList<>();
		LocalJwtTokenProvider provider = new LocalJwtTokenProvider(securityConfig, checkers);
		checkers.add((tokenId, claims) -> true);
		String key = provider.createApiKeyToken(USER, inOneHour(), TOKEN_ID, LocalJwtTokenProvider.API_KEY_KIND_USER);

		assertThat(provider.validateToken(key)).as("a checker added later is not consulted").isTrue();
	}
}
