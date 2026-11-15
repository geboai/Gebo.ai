/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.services.impl;

import static ai.gebo.security.services.impl.authmanagers.MicrosoftTokenSigner.CONTOSO;
import static ai.gebo.security.services.impl.authmanagers.MicrosoftTokenSigner.FABRIKAM;
import static ai.gebo.security.services.impl.authmanagers.MicrosoftTokenSigner.claimsOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;

import ai.gebo.architecture.environment.ArchitectureType;
import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.GeboLoginPolicy;
import ai.gebo.security.model.oauth2.Oauth2ConfigurationType;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.services.IGOauth2RuntimeConfigurationDao;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.IGSecurityDirectory;
import ai.gebo.security.services.impl.authmanagers.MicrosoftTokenSigner;

/**
 * A bearer token of a person of an organization, sent to the API as a client sends
 * it, with no header of Gebo's own, while the only configuration that admits that
 * organization is the Microsoft accounts one.
 */
class GHttpRequestAuthenticationManagerResolverImplMicrosoftAccountsTest {

    private static final String EMAIL = "anna.bianchi@contoso.example";

    private static MicrosoftTokenSigner signer;

    private IGOauth2RuntimeConfigurationDao configurations;
    private UserDetailsService users;
    private IGSecurityDirectory directory;
    private GeboSecurityConfig securityConfig;

    @BeforeAll
    static void serveTheKeys() throws Exception {
        signer = new MicrosoftTokenSigner();
    }

    @AfterAll
    static void stopServingTheKeys() {
        signer.close();
    }

    @BeforeEach
    void setUp() {
        Oauth2RuntimeConfiguration microsoft = signer.microsoftAccounts("microsoft-test", "consumers, " + CONTOSO,
                null);
        configurations = mock(IGOauth2RuntimeConfigurationDao.class);
        when(configurations.findByConfigurationType(Oauth2ConfigurationType.AUTHENTICATION))
                .thenReturn(List.of(microsoft));
        when(configurations.findByCode("microsoft-test")).thenReturn(microsoft);
        users = mock(UserDetailsService.class);
        directory = mock(IGSecurityDirectory.class);
        securityConfig = new GeboSecurityConfig();
    }

    private Authentication authenticate(String token) {
        IGSecurityAuditLoggerService audit = mock(IGSecurityAuditLoggerService.class);
        when(audit.newSecurityEvent()).thenAnswer(invocation -> new SecurityEvent(ArchitectureType.MONOLITHIC, null,
                "203.0.113.7", "test", "test-correlation-id", Instant.now().toString(), "test", "GET", "/api"));
        GHttpRequestAuthenticationManagerResolverImpl resolver = new GHttpRequestAuthenticationManagerResolverImpl(
                users, mock(PasswordEncoder.class), configurations, mock(LocalJwtTokenProvider.class), users,
                securityConfig, directory, null, audit, null);
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/users/anything");
        request.addHeader("Authorization", "Bearer " + token);
        return resolver.resolve(request).authenticate(new BearerTokenAuthenticationToken(token));
    }

    private static UserDetails user() {
        return User.withUsername(EMAIL).password("").roles("USER").build();
    }

    @Test
    void aPersonOfAnAdmittedOrganizationIsAuthenticated() throws Exception {
        when(users.loadUserByUsername(EMAIL)).thenReturn(user());

        Authentication authenticated = authenticate(signer.sign(claimsOf(CONTOSO)));

        assertThat(authenticated.isAuthenticated()).isTrue();
        assertThat(authenticated.getName()).isEqualTo(EMAIL);
    }

    @Test
    void aPersonOfAnotherOrganizationIsRefused() throws Exception {
        when(users.loadUserByUsername(EMAIL)).thenReturn(user());
        String token = signer.sign(claimsOf(FABRIKAM));

        assertThatThrownBy(() -> authenticate(token)).isInstanceOf(AuthenticationException.class);
    }

    /**
     * Where the policy creates the people it does not know yet, the new account is
     * recorded against the provider that admitted the token, found from the
     * organization's issuer.
     */
    @Test
    void aPersonCreatedOnFirstSightIsRecordedAgainstTheMicrosoftAccountsProvider() throws Exception {
        securityConfig.setLoginPolicy(GeboLoginPolicy.TRUST_EVERY_OAUTH_IDENTITY);
        when(users.loadUserByUsername(EMAIL)).thenThrow(new UsernameNotFoundException(EMAIL)).thenReturn(user());

        assertThat(authenticate(signer.sign(claimsOf(CONTOSO))).getName()).isEqualTo(EMAIL);

        verify(directory).createUserIfNotExists(eq(EMAIL), any(), eq(AuthProvider.microsoft));
    }
}
