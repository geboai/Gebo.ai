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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import ai.gebo.secrets.model.GeboOauth2TokenSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.SecretInfo;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

/**
 * The tokens an identity provider hands back are kept in the secret store, one
 * entry per user and registration. They used to be written under a fresh random id
 * at every login while the load looked them up by the stable one, so nothing was
 * ever read back and every login left another copy of a live access and refresh
 * token behind, for as long as the installation ran.
 */
class GOauth2AuthorizedClientServiceTest {

    private static final String REGISTRATION = "keycloak-1";
    private static final String USER = "invited@demo-company.example";
    private static final String CONTEXT = "oauth2-client-token-" + REGISTRATION + "-" + USER;

    private IGeboSecretsAccessService secrets;
    private ClientRegistrationRepository registrations;
    private GOauth2AuthorizedClientService service;

    @BeforeEach
    void setUp() {
        secrets = mock(IGeboSecretsAccessService.class);
        registrations = mock(ClientRegistrationRepository.class);
        service = new GOauth2AuthorizedClientService(registrations, secrets);
    }

    private static ClientRegistration registration() {
        return ClientRegistration.withRegistrationId(REGISTRATION)
                .clientId("gebo-ai").clientSecret("a-client-secret")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost:12999/login/oauth2/code/" + REGISTRATION)
                .authorizationUri("https://sso.example.com/realms/company/protocol/openid-connect/auth")
                .tokenUri("https://sso.example.com/realms/company/protocol/openid-connect/token")
                .build();
    }

    private static OAuth2AuthorizedClient authorizedClient() {
        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "an-access-token",
                Instant.now(), Instant.now().plusSeconds(300));
        return new OAuth2AuthorizedClient(registration(), USER, accessToken);
    }

    private static Authentication principal() {
        return new UsernamePasswordAuthenticationToken(USER, null, List.of());
    }

    private static SecretInfo storedAs(String code) {
        return new SecretInfo(code, "OAuth2 token", GeboSecretType.OAUTH2_AUTHORIZED_CLIENT, CONTEXT, false);
    }

    @Test
    void savingReplacesTheTokensOfThePreviousLoginInsteadOfLeavingThemBehind() throws Exception {
        when(secrets.getSecretInfoByContextCode(CONTEXT)).thenReturn(List.of(storedAs("an-older-random-id")));

        service.saveAuthorizedClient(authorizedClient(), principal());

        verify(secrets).deleteSecret("an-older-random-id");
        // filed under the id the load and the removal both ask for
        verify(secrets).storeSecret(any(GeboOauth2TokenSecretContent.class), anyString(), eq(CONTEXT), eq(CONTEXT));
    }

    @Test
    void theTokensOfALoginAreLoadedBackAgain() throws Exception {
        GeboOauth2TokenSecretContent content = new GeboOauth2TokenSecretContent();
        content.setPrincipalName(USER);
        content.setRegistrationId(REGISTRATION);
        content.setAccessToken(new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "an-access-token",
                Instant.now(), Instant.now().plusSeconds(300)));
        when(secrets.getSecretInfoByContextCode(CONTEXT)).thenReturn(List.of(storedAs(CONTEXT)));
        when(secrets.getSecretContentById(CONTEXT)).thenReturn(content);
        when(registrations.findByRegistrationId(REGISTRATION)).thenReturn(registration());

        OAuth2AuthorizedClient loaded = service.loadAuthorizedClient(REGISTRATION, USER);

        assertThat(loaded).isNotNull();
        assertThat(loaded.getPrincipalName()).isEqualTo(USER);
        assertThat(loaded.getAccessToken().getTokenValue()).isEqualTo("an-access-token");
    }

    @Test
    void noTokensYetIsAnAnswerAndNotAFailure() throws Exception {
        when(secrets.getSecretInfoByContextCode(CONTEXT)).thenReturn(List.of());

        OAuth2AuthorizedClient loaded = service.loadAuthorizedClient(REGISTRATION, USER);

        assertThat(loaded).isNull();
        verify(secrets, never()).getSecretContentById(anyString());
    }

    @Test
    void removingTakesAwayEveryCopyThatWasLeftBehind() throws Exception {
        when(secrets.getSecretInfoByContextCode(CONTEXT))
                .thenReturn(List.of(storedAs("one-copy"), storedAs("another-copy")));

        service.removeAuthorizedClient(REGISTRATION, USER);

        verify(secrets).deleteSecret("one-copy");
        verify(secrets).deleteSecret("another-copy");
    }
}
