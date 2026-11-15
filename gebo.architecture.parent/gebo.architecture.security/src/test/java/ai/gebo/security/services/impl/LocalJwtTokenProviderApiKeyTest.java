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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.AuthProviderType;
import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.SecurityHeaderUtil.XAuthType;
import ai.gebo.security.services.IGIssuedTokenRevocationChecker;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
	void aSystemTokenHasAnIdButNoSessionAndIsNotAnApiKey() {
		LocalJwtTokenProvider provider = new LocalJwtTokenProvider(securityConfig);

		String system = provider.createToken(USER, inOneHour());

		Claims claims = claimsOf(system);
		assertThat(claims.getId()).as("every token has an id now").isNotBlank();
		assertThat(claims.get(LocalJwtTokenProvider.SESSION_ID_CLAIM)).isNull();
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
	void theCheckersAreAskedAboutEveryToken() {
		Set<String> asked = new CopyOnWriteArraySet<>();
		IGIssuedTokenRevocationChecker spy = (tokenId, claims) -> {
			asked.add(tokenId);
			return false;
		};
		LocalJwtTokenProvider provider = new LocalJwtTokenProvider(securityConfig, List.of(spy));
		String token = provider.createToken(USER, inOneHour());

		assertThat(provider.validateToken(token)).isTrue();
		assertThat(asked).containsExactly(claimsOf(token).getId());
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
		assertThat(claimsOf(renewed.getToken()).getId()).as("a token of its own").isNotBlank();
		assertThat(LocalJwtTokenProvider.isApiKey(claimsOf(renewed.getToken()))).isFalse();
	}

	@Test
	void theCheckersAreCopiedAtConstruction() {
		List<IGIssuedTokenRevocationChecker> checkers = new java.util.ArrayList<>();
		LocalJwtTokenProvider provider = new LocalJwtTokenProvider(securityConfig, checkers);
		checkers.add((tokenId, claims) -> true);
		String key = provider.createApiKeyToken(USER, inOneHour(), TOKEN_ID, LocalJwtTokenProvider.API_KEY_KIND_USER);

		assertThat(provider.validateToken(key)).as("a checker added later is not consulted").isTrue();
	}

	@Test
	void aSessionTokenCarriesItsSessionAndItsOwnId() {
		LocalJwtTokenProvider provider = new LocalJwtTokenProvider(securityConfig);
		String first = provider.createToken(USER, AuthProviderType.LOCAL_JWT, AuthProvider.local, null,
				System.currentTimeMillis() + 3_600_000L);

		Claims claims = claimsOf(first);
		assertThat(claims.getId()).isNotBlank();
		assertThat(claims.get(LocalJwtTokenProvider.SESSION_ID_CLAIM, String.class)).isNotBlank();
		assertThat(LocalJwtTokenProvider.isApiKey(claims)).isFalse();
	}

	@Test
	void aRenewedTokenStaysInTheSessionOfTheTokenItRenews() {
		LocalJwtTokenProvider provider = new LocalJwtTokenProvider(securityConfig);
		String first = provider.createToken(USER, AuthProviderType.LOCAL_JWT, AuthProvider.local, null,
				System.currentTimeMillis() + 3_600_000L);
		String sessionId = claimsOf(first).get(LocalJwtTokenProvider.SESSION_ID_CLAIM, String.class);

		SecurityHeaderData renewed = provider
				.renew(new SecurityHeaderData(first, XAuthType.LOCAL_JWT, "local", null, false));

		Claims claims = claimsOf(renewed.getToken());
		assertThat(claims.get(LocalJwtTokenProvider.SESSION_ID_CLAIM, String.class)).isEqualTo(sessionId);
		assertThat(claims.getId()).isNotEqualTo(claimsOf(first).getId());
	}

	@Test
	void aRevokedSessionRefusesEveryTokenOfTheChain() {
		Set<String> revokedSessions = new CopyOnWriteArraySet<>();
		IGIssuedTokenRevocationChecker sessionsChecker = (tokenId, claims) -> revokedSessions
				.contains(claims.get(LocalJwtTokenProvider.SESSION_ID_CLAIM));
		LocalJwtTokenProvider provider = new LocalJwtTokenProvider(securityConfig, List.of(sessionsChecker));
		String first = provider.createToken(USER, AuthProviderType.LOCAL_JWT, AuthProvider.local, null,
				System.currentTimeMillis() + 3_600_000L);
		String renewed = provider.renew(new SecurityHeaderData(first, XAuthType.LOCAL_JWT, "local", null, false))
				.getToken();
		assertThat(provider.validateToken(first)).isTrue();
		assertThat(provider.validateToken(renewed)).isTrue();

		revokedSessions.add(claimsOf(first).get(LocalJwtTokenProvider.SESSION_ID_CLAIM, String.class));

		assertThat(provider.validateToken(first)).as("the login token").isFalse();
		assertThat(provider.validateToken(renewed)).as("the token renewed from it").isFalse();
	}

	@Test
	void anExternalTokenIsKnownByItsHash() {
		String token = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzb21lb25lIn0.signature";
		assertThat(LocalJwtTokenProvider.tokenHash(token)).hasSize(64).isEqualTo(LocalJwtTokenProvider.tokenHash(token));
		assertThat(LocalJwtTokenProvider.tokenHash(token + "x")).isNotEqualTo(LocalJwtTokenProvider.tokenHash(token));
	}

	@Test
	void theCheckersAreAskedAboutExternalTokensToo() {
		Set<String> asked = new CopyOnWriteArraySet<>();
		IGIssuedTokenRevocationChecker spy = (tokenId, claims) -> {
			asked.add(tokenId);
			return Boolean.TRUE.equals(claims.get(LocalJwtTokenProvider.OAUTH2_TOKEN_CLAIM));
		};
		LocalJwtTokenProvider provider = new LocalJwtTokenProvider(securityConfig, List.of(spy));
		Map<String, Object> claims = new java.util.HashMap<>();
		claims.put(LocalJwtTokenProvider.OAUTH2_TOKEN_CLAIM, Boolean.TRUE);

		assertThat(provider.isRevoked("hash-1", claims)).isTrue();
		assertThat(asked).containsExactly("hash-1");
	}

	@Test
	void everyMintedTokenCarriesAnIdAndTheInstallationId() {
		LocalJwtTokenProvider provider = new LocalJwtTokenProvider(securityConfig, List.of(), "install-A@host-a");

		for (String token : List.of(provider.createToken(USER, inOneHour()),
				provider.createApiKeyToken(USER, inOneHour(), TOKEN_ID, LocalJwtTokenProvider.API_KEY_KIND_USER),
				provider.createToken(USER, AuthProviderType.LOCAL_JWT, AuthProvider.local, null,
						System.currentTimeMillis() + 3_600_000L))) {
			Claims claims = claimsOf(token);
			assertThat(claims.getId()).isNotBlank();
			assertThat(claims.get(LocalJwtTokenProvider.INSTALLATION_ID_CLAIM, String.class)).isEqualTo("install-A@host-a");
			assertThat(provider.validateToken(token)).isTrue();
		}
	}

	@Test
	void aTokenOfTheOldShapeIsRefused() {
		LocalJwtTokenProvider provider = new LocalJwtTokenProvider(securityConfig, List.of(), "install-A@host-a");
		// signed with the same secret, the claims a token carried before: no id, no installation
		String old = Jwts.builder().claim(LocalJwtTokenProvider.AUTH_TYPE_CLAIM, "LOCAL_JWT")
				.claim(LocalJwtTokenProvider.AUTH_PROVIDER_CLAIM, "local").setSubject(USER).setIssuedAt(new Date())
				.setExpiration(inOneHour()).signWith(SignatureAlgorithm.HS512, SECRET).compact();
		assertThat(provider.validateToken(old)).isFalse();

		String idButNoInstallation = Jwts.builder().setId("some-id").setSubject(USER).setIssuedAt(new Date())
				.setExpiration(inOneHour()).signWith(SignatureAlgorithm.HS512, SECRET).compact();
		assertThat(provider.validateToken(idButNoInstallation)).isFalse();
	}

	@Test
	void aTokenOfAnotherInstallationWithTheSameSecretIsRefused() {
		LocalJwtTokenProvider here = new LocalJwtTokenProvider(securityConfig, List.of(), "install-A@host-a");
		LocalJwtTokenProvider elsewhere = new LocalJwtTokenProvider(securityConfig, List.of(), "install-B@host-b");

		String foreign = elsewhere.createToken(USER, inOneHour());

		assertThat(elsewhere.validateToken(foreign)).isTrue();
		assertThat(here.validateToken(foreign)).as("same secret, other installation").isFalse();
		assertThat(here.validateToken(elsewhere.createApiKeyToken(USER, inOneHour(), TOKEN_ID,
				LocalJwtTokenProvider.API_KEY_KIND_ADMIN))).as("an API key of another installation").isFalse();
	}
}
