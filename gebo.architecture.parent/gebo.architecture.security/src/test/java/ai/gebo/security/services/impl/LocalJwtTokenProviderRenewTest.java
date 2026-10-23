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

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.AuthProviderType;
import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.SecurityHeaderUtil;
import ai.gebo.security.model.SecurityHeaderUtil.XAuthType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Renewal is what keeps a session alive: the browser asks for a fresh token well
 * before the current one expires, and a renewal that fails ends the session there,
 * silently, because the failure is swallowed in the browser.
 *
 * <p>
 * A token minted by a single sign-on login used to record its provider as a name
 * that is not a provider, and renewal read that name strictly, so those sessions
 * could never be extended: they lasted exactly as long as the identity provider's
 * id token, five minutes on a stock Keycloak.
 * </p>
 */
class LocalJwtTokenProviderRenewTest {

    private static final String SECRET = "b7e2d4f6a8c0e2d4f6a8c0e2d4f6a8c0e2d4f6a8c0e2d4f6a8c0e2d4f6a8c0e2"
            + "b7e2d4f6a8c0e2d4f6a8c0e2d4f6a8c0e2d4f6a8c0e2d4f6a8c0e2d4f6a8c0e2";
    private static final String USER = "invited@demo-company.example";

    private GeboSecurityConfig securityConfig;
    private LocalJwtTokenProvider provider;

    @BeforeEach
    void setUp() {
        securityConfig = new GeboSecurityConfig();
        securityConfig.getAuth().setTokenSecret(SECRET);
        securityConfig.getAuth().setTokenExpirationMsec(1_800_000L);
        provider = new LocalJwtTokenProvider(securityConfig);
    }

    private Claims claimsOf(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    /** A token of the shape the installation accepts, with the claims under test. */
    private SecurityHeaderData tokenCarrying(Map<String, Object> extraClaims) {
        Map<String, Object> claims = new HashMap<>(extraClaims);
        claims.put(LocalJwtTokenProvider.INSTALLATION_ID_CLAIM, LocalJwtTokenProvider.STANDALONE_INSTALLATION_ID);
        claims.put(LocalJwtTokenProvider.SESSION_ID_CLAIM, "a-session");
        JwtBuilder builder = Jwts.builder();
        builder.addClaims(claims);
        String token = builder.setId("a-token").setSubject(USER).setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 60_000L))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        return new SecurityHeaderData(token, XAuthType.LOCAL_JWT, SecurityHeaderUtil.DEFAULT_PROVIDER_ID,
                SecurityHeaderUtil.DEFAULT_TENANT, false);
    }

    @Test
    void aSingleSignOnSessionCanBeExtended() {
        SecurityHeaderData data = tokenCarrying(Map.of(LocalJwtTokenProvider.AUTH_TYPE_CLAIM,
                AuthProviderType.OAUTH2.name(), LocalJwtTokenProvider.AUTH_PROVIDER_CLAIM,
                AuthProvider.keycloak.name(), LocalJwtTokenProvider.AUTH_PROVIDER_REGISTRATION_ID, "keycloak-1"));

        SecurityHeaderData renewed = provider.renew(data);

        assertThat(renewed.getToken()).isNotEqualTo(data.getToken());
        Claims claims = claimsOf(renewed.getToken());
        assertThat(claims.getSubject()).isEqualTo(USER);
        // the renewal stays in the session it renews, and keeps naming its provider
        assertThat(claims.get(LocalJwtTokenProvider.SESSION_ID_CLAIM, String.class)).isEqualTo("a-session");
        assertThat(claims.get(LocalJwtTokenProvider.AUTH_PROVIDER_CLAIM, String.class))
                .isEqualTo(AuthProvider.keycloak.name());
        // and it lasts as long as this installation says, not as long as the id token did
        assertThat(claims.getExpiration().getTime() - System.currentTimeMillis()).isGreaterThan(600_000L);
    }

    @Test
    void aTokenNamingAProviderThisInstallationDoesNotKnowIsStillRenewed() {
        SecurityHeaderData data = tokenCarrying(Map.of(LocalJwtTokenProvider.AUTH_TYPE_CLAIM,
                AuthProviderType.OAUTH2.name(), LocalJwtTokenProvider.AUTH_PROVIDER_CLAIM, "UNKNOWN"));

        SecurityHeaderData renewed = provider.renew(data);

        assertThat(renewed.getToken()).isNotEqualTo(data.getToken());
        Claims claims = claimsOf(renewed.getToken());
        assertThat(claims.getSubject()).isEqualTo(USER);
        // the name it could not make sense of is simply not carried over
        assertThat(claims.get(LocalJwtTokenProvider.AUTH_PROVIDER_CLAIM, String.class)).isNull();
    }

    @Test
    void aTokenNamingAProviderTypeThisInstallationDoesNotKnowIsStillRenewed() {
        SecurityHeaderData data = tokenCarrying(
                Map.of(LocalJwtTokenProvider.AUTH_TYPE_CLAIM, "SOMETHING_ELSE"));

        SecurityHeaderData renewed = provider.renew(data);

        assertThat(renewed.getToken()).isNotEqualTo(data.getToken());
        assertThat(claimsOf(renewed.getToken()).getSubject()).isEqualTo(USER);
    }
}
