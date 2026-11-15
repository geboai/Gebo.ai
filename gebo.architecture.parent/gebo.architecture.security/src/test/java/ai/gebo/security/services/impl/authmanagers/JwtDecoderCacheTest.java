/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.services.impl.authmanagers;

import static ai.gebo.security.services.impl.authmanagers.MicrosoftTokenSigner.CLIENT_ID;
import static ai.gebo.security.services.impl.authmanagers.MicrosoftTokenSigner.CONTOSO;
import static ai.gebo.security.services.impl.authmanagers.MicrosoftTokenSigner.FABRIKAM;
import static ai.gebo.security.services.impl.authmanagers.MicrosoftTokenSigner.PERSONAL;
import static ai.gebo.security.services.impl.authmanagers.MicrosoftTokenSigner.claimsOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.JwtValidationException;

import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;

/**
 * Which bearer tokens the API accepts for a configuration, on tokens signed with a
 * key served as a provider serves its keys.
 *
 * <p>
 * The Microsoft accounts configuration has people of several tenants, each signing
 * under an issuer of its own: a token is accepted for it when it comes from a tenant
 * the configuration admits, exactly as the login admits the id token.
 * </p>
 */
class JwtDecoderCacheTest {

    private static MicrosoftTokenSigner signer;

    @BeforeAll
    static void serveTheKeys() throws Exception {
        signer = new MicrosoftTokenSigner();
    }

    @AfterAll
    static void stopServingTheKeys() {
        signer.close();
    }

    private static Oauth2RuntimeConfiguration admitting(String allowedTenants) {
        return signer.microsoftAccounts("microsoft-test", allowedTenants, null);
    }

    @Test
    void aTokenOfAnAdmittedTenantIsAccepted() throws Exception {
        JwtDecoderCache cache = new JwtDecoderCache();
        Oauth2RuntimeConfiguration configuration = admitting("consumers, " + CONTOSO);

        assertThat(cache.forConfiguration(configuration).decode(signer.sign(claimsOf(CONTOSO)))
                .getClaimAsString("tid")).isEqualTo(CONTOSO);
        assertThat(cache.forConfiguration(configuration).decode(signer.sign(claimsOf(PERSONAL)))
                .getClaimAsString("tid")).isEqualTo(PERSONAL);
    }

    @Test
    void aTokenOfATenantNotAdmittedIsRefused() throws Exception {
        JwtDecoderCache cache = new JwtDecoderCache();
        String token = signer.sign(claimsOf(FABRIKAM));

        assertThatThrownBy(() -> cache.forConfiguration(admitting("consumers, " + CONTOSO)).decode(token))
                .isInstanceOf(JwtValidationException.class).hasMessageContaining(FABRIKAM);
    }

    /**
     * A configuration saved before the tenants could be listed was meant for
     * personal accounts, and keeps accepting their tokens, and only theirs.
     */
    @Test
    void aConfigurationThatListsNoTenantAcceptsPersonalAccountsOnly() throws Exception {
        JwtDecoderCache cache = new JwtDecoderCache();
        Oauth2RuntimeConfiguration configuration = admitting(null);

        assertThat(cache.forConfiguration(configuration).decode(signer.sign(claimsOf(PERSONAL)))).isNotNull();
        String organization = signer.sign(claimsOf(CONTOSO));
        assertThatThrownBy(() -> cache.forConfiguration(configuration).decode(organization))
                .isInstanceOf(JwtValidationException.class);
    }

    /** The tenant is only worth checking if it is the one that signed. */
    @Test
    void aTokenWhoseIssuerIsNotTheOneOfItsTenantIsRefused() throws Exception {
        JwtDecoderCache cache = new JwtDecoderCache();
        Oauth2RuntimeConfiguration configuration = admitting("consumers, " + CONTOSO + ", " + FABRIKAM);
        String token = signer.sign(claimsOf(FABRIKAM).claim("tid", CONTOSO));

        assertThatThrownBy(() -> cache.forConfiguration(configuration).decode(token))
                .isInstanceOf(JwtValidationException.class);
    }

    /** Admitting the tenant replaces the issuer check only, not the others. */
    @Test
    void theSignatureTimesAndAudiencesAreStillChecked() throws Exception {
        JwtDecoderCache cache = new JwtDecoderCache();
        Oauth2RuntimeConfiguration configuration = admitting(CONTOSO);
        String forged = signer.signWithAnotherKey(claimsOf(CONTOSO));
        assertThatThrownBy(() -> cache.forConfiguration(configuration).decode(forged))
                .as("signed with a key the provider does not serve").isInstanceOf(BadJwtException.class);

        Instant now = Instant.now();
        String expired = signer.sign(claimsOf(CONTOSO).issueTime(Date.from(now.minusSeconds(7200)))
                .expirationTime(Date.from(now.minusSeconds(3600))));
        assertThatThrownBy(() -> cache.forConfiguration(configuration).decode(expired)).as("expired")
                .isInstanceOf(JwtValidationException.class);

        Oauth2RuntimeConfiguration pinned = signer.microsoftAccounts("microsoft-test", CONTOSO, List.of(CLIENT_ID));
        assertThat(cache.forConfiguration(pinned).decode(signer.sign(claimsOf(CONTOSO)))).isNotNull();
        String forAnotherApplication = signer.sign(claimsOf(CONTOSO).audience("another-client"));
        assertThatThrownBy(() -> cache.forConfiguration(pinned).decode(forAnotherApplication))
                .as("issued to another application").isInstanceOf(JwtValidationException.class);
    }

    /** The configuration is read on every request, and so is the list. */
    @Test
    void aTenantTakenOffTheListIsRefusedFromTheNextRequest() throws Exception {
        JwtDecoderCache cache = new JwtDecoderCache();
        String token = signer.sign(claimsOf(CONTOSO));
        assertThat(cache.forConfiguration(admitting("consumers, " + CONTOSO)).decode(token)).isNotNull();

        assertThatThrownBy(() -> cache.forConfiguration(admitting("consumers")).decode(token))
                .isInstanceOf(JwtValidationException.class);
    }

    /**
     * Only the Microsoft accounts configuration reads the list: any other keeps its
     * single issuer, whatever attribute it was saved with.
     */
    @Test
    void anotherProviderKeepsItsOneIssuer() throws Exception {
        JwtDecoderCache cache = new JwtDecoderCache();
        String issuer = signer.serveIssuer("/realms/company");
        Oauth2RuntimeConfiguration keycloak = MicrosoftTokenSigner.otherProvider("keycloak-test",
                AuthProvider.keycloak, issuer, CONTOSO);

        assertThat(cache.forConfiguration(keycloak).decode(signer.sign(claimsOf(CONTOSO).issuer(issuer))))
                .isNotNull();
        String microsoft = signer.sign(claimsOf(CONTOSO));
        assertThatThrownBy(() -> cache.forConfiguration(keycloak).decode(microsoft))
                .isInstanceOf(JwtValidationException.class);
    }

    @Test
    void theReactiveApiAcceptsTheSameTokens() throws Exception {
        ReactiveJwtDecoderCache cache = new ReactiveJwtDecoderCache();
        Oauth2RuntimeConfiguration configuration = admitting("consumers, " + CONTOSO);

        assertThat(cache.forConfiguration(configuration).decode(signer.sign(claimsOf(CONTOSO))).block()
                .getClaimAsString("tid")).isEqualTo(CONTOSO);
        String token = signer.sign(claimsOf(FABRIKAM));
        assertThatThrownBy(() -> cache.forConfiguration(configuration).decode(token).block())
                .isInstanceOf(JwtValidationException.class).hasMessageContaining(FABRIKAM);
    }
}
