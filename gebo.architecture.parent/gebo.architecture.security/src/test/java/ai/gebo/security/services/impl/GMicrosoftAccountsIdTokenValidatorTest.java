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

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * The id token check of the Microsoft accounts configuration, on tokens shaped as
 * Microsoft's v2.0 endpoints issue them.
 */
class GMicrosoftAccountsIdTokenValidatorTest {

    static final String CLIENT_ID = "11111111-2222-3333-4444-555555555555";
    static final String PERSONAL = GMicrosoftAccountsIdTokenValidator.PERSONAL_ACCOUNTS_TENANT;
    static final String CONTOSO = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee";
    static final String FABRIKAM = "ffffffff-bbbb-cccc-dddd-eeeeeeeeeeee";

    /** A registration as the library entry makes it, issuer included. */
    static ClientRegistration registration() {
        return ClientRegistration.withRegistrationId("microsoft-test").clientId(CLIENT_ID).clientSecret("a-secret")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}").scope("openid", "profile", "email")
                .authorizationUri("https://login.microsoftonline.com/common/oauth2/v2.0/authorize")
                .tokenUri("https://login.microsoftonline.com/common/oauth2/v2.0/token")
                .jwkSetUri("https://login.microsoftonline.com/common/discovery/v2.0/keys")
                .issuerUri("https://login.microsoftonline.com/" + PERSONAL + "/v2.0").build();
    }

    /** An id token issued to the client for a person of the tenant. */
    static Jwt.Builder idToken(String tenant) {
        Instant now = Instant.now();
        return Jwt.withTokenValue("id-token").header("alg", "RS256")
                .issuer("https://login.microsoftonline.com/" + tenant + "/v2.0")
                .subject("AAAAAAAAAAAAAAAAAAAAAIkzqFVrSaSaFHy782bbtaQ").audience(List.of(CLIENT_ID)).issuedAt(now)
                .expiresAt(now.plusSeconds(3600)).claim("tid", tenant).claim("email", "anna.bianchi@outlook.com")
                .claim("ver", "2.0");
    }

    private static OAuth2TokenValidatorResult check(String allowedTenants, Jwt idToken) {
        Map<String, String> attributes = allowedTenants == null ? null
                : Map.of(GMicrosoftAccountsIdTokenValidator.ALLOWED_TENANTS, allowedTenants);
        return new GMicrosoftAccountsIdTokenValidator(registration(),
                GMicrosoftAccountsIdTokenValidator.admittedTenants(attributes)).validate(idToken);
    }

    @Test
    void aPersonalAccountIsAdmittedWhenConsumersIsListed() {
        assertThat(check("consumers", idToken(PERSONAL).build()).getErrors()).isEmpty();
        assertThat(check(CONTOSO, idToken(PERSONAL).build()).hasErrors()).isTrue();
    }

    /**
     * A configuration saved before the tenants could be listed was meant for
     * personal accounts, and keeps admitting them, and only them.
     */
    @Test
    void aConfigurationThatListsNoTenantAdmitsPersonalAccountsOnly() {
        for (String nothing : new String[] { null, "", "  ", " , ;" }) {
            assertThat(check(nothing, idToken(PERSONAL).build()).getErrors()).as("[%s], personal", nothing).isEmpty();
            assertThat(check(nothing, idToken(CONTOSO).build()).hasErrors()).as("[%s], organization", nothing)
                    .isTrue();
        }
    }

    @Test
    void anOrganizationIsAdmittedOnlyWhenItsTenantIsListed() {
        assertThat(check("consumers, " + CONTOSO, idToken(CONTOSO).build()).getErrors()).isEmpty();

        OAuth2TokenValidatorResult refused = check("consumers, " + CONTOSO, idToken(FABRIKAM).build());
        assertThat(refused.getErrors()).singleElement().satisfies(error -> {
            assertThat(error.getErrorCode()).isEqualTo("invalid_id_token");
            assertThat(error.getDescription()).contains(FABRIKAM);
        });
    }

    /**
     * The tenant is only worth checking if it is the one that signed: a token must
     * carry the issuer of the tenant it names.
     */
    @Test
    void aTokenWhoseIssuerIsNotTheOneOfItsTenantIsRefused() {
        String both = "consumers, " + CONTOSO + ", " + FABRIKAM;
        assertThat(check(both, idToken(FABRIKAM).claim("tid", CONTOSO).build()).hasErrors()).isTrue();
        assertThat(check(both, idToken(CONTOSO).claims(claims -> claims.remove("tid")).build()).hasErrors())
                .isTrue();
        assertThat(check(both,
                idToken(CONTOSO).issuer("https://login.microsoftonline.com/common/v2.0").claim("tid", "common").build())
                .hasErrors()).as("the shared endpoints' own name as a tenant").isTrue();
    }

    /** Admitting the tenant replaces the issuer check only, not the others. */
    @Test
    void theOtherIdTokenChecksStillApply() {
        Instant now = Instant.now();
        assertThat(check("consumers", idToken(PERSONAL).audience(List.of("another-client")).build()).hasErrors())
                .as("issued to another client").isTrue();
        assertThat(check("consumers",
                idToken(PERSONAL).issuedAt(now.minusSeconds(7200)).expiresAt(now.minusSeconds(3600)).build())
                .hasErrors()).as("expired").isTrue();
        assertThat(check("consumers", idToken(PERSONAL).claims(claims -> claims.remove("sub")).build()).hasErrors())
                .as("no subject").isTrue();
    }

    @Test
    void theListIsReadWhateverItsSeparatorsAndCase() {
        assertThat(GMicrosoftAccountsIdTokenValidator.admittedTenants(Map.of(
                GMicrosoftAccountsIdTokenValidator.ALLOWED_TENANTS, " Consumers;" + CONTOSO.toUpperCase() + "\n" + FABRIKAM)))
                .isEqualTo(Set.of(PERSONAL, CONTOSO, FABRIKAM));
    }
}
