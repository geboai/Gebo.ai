/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.services.impl;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.EditableUser;
import ai.gebo.security.model.GeboLoginPolicy;
import ai.gebo.security.model.Oauth2SyncUsersData;
import ai.gebo.security.model.oauth2.Oauth2ClientRegistration;
import ai.gebo.security.model.oauth2.Oauth2ConfigurationType;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.services.IGOauth2ConfigurationService;
import ai.gebo.security.services.IGOauth2UserSyncService;
import ai.gebo.security.services.IGOauth2UserSyncServiceConditionedImplementationProvider;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.IGUsersAdminService;

/**
 * The decision {@code ai.gebo.security.loginPolicy} governs, which until now never
 * ran for a browser login: every configuration saved through the administration
 * form logs in with the standard openid scopes, and those logins went to Spring's
 * own user service.
 */
class GOauth2LoginPolicyEnforcerTest {

    private static final String REGISTRATION = "keycloak-1";
    private static final String EMAIL = "invited@demo-company.example";

    private IGOauth2ConfigurationService configurations;
    private IGUsersAdminService users;
    private GeboSecurityConfig securityConfig;
    private IGOauth2UserSyncServiceConditionedImplementationProvider syncProvider;
    private IGOauth2UserSyncService sync;
    private GOauth2LoginPolicyEnforcer enforcer;
    private OAuth2User identity;

    @BeforeEach
    void setUp() throws Exception {
        configurations = mock(IGOauth2ConfigurationService.class);
        users = mock(IGUsersAdminService.class);
        securityConfig = new GeboSecurityConfig();
        syncProvider = mock(IGOauth2UserSyncServiceConditionedImplementationProvider.class);
        sync = mock(IGOauth2UserSyncService.class);
        IGSecurityAuditLoggerService audit = mock(IGSecurityAuditLoggerService.class);
        SecurityEvent event = mock(SecurityEvent.class);
        when(event.getDetails()).thenReturn(new HashMap<>());
        when(audit.newSecurityEvent()).thenReturn(event);
        when(syncProvider.handlerOf(any(Oauth2SyncUsersData.class))).thenReturn(sync);

        Oauth2RuntimeConfiguration runtime = new Oauth2RuntimeConfiguration();
        runtime.setRegistrationId(REGISTRATION);
        runtime.setProvider(AuthProvider.keycloak);
        runtime.setConfigurationType(Oauth2ConfigurationType.AUTHENTICATION);
        when(configurations.findOauth2ClientRegistrationByRegistrationId(REGISTRATION))
                .thenReturn(new Oauth2ClientRegistration(null, runtime));

        identity = mock(OAuth2User.class);
        when(identity.getAttribute("email")).thenReturn(EMAIL);

        enforcer = new GOauth2LoginPolicyEnforcer(configurations, users, securityConfig, syncProvider, audit);
    }

    private static EditableUser userOf(AuthProvider provider) {
        EditableUser user = new EditableUser();
        user.setUsername(EMAIL);
        user.setAuthProvider(provider);
        return user;
    }

    @Test
    void anIdentityNobodyInvitedIsRefused() {
        securityConfig.setLoginPolicy(GeboLoginPolicy.REQUIRE_INVITATION);
        when(users.findUserByUsername(EMAIL)).thenReturn(null);

        assertThatThrownBy(() -> enforcer.enforce(REGISTRATION, identity))
                .isInstanceOf(OAuth2AuthenticationException.class);
        verify(sync, never()).createOrSyncUser(any());
    }

    @Test
    void anIdentityRegisteredAgainstAnotherProviderIsRefused() {
        securityConfig.setLoginPolicy(GeboLoginPolicy.REQUIRE_INVITATION);
        when(users.findUserByUsername(EMAIL)).thenReturn(userOf(AuthProvider.google));

        assertThatThrownBy(() -> enforcer.enforce(REGISTRATION, identity))
                .isInstanceOf(OAuth2AuthenticationException.class);
    }

    @Test
    void anInvitedIdentityIsLetInAndRefreshed() {
        securityConfig.setLoginPolicy(GeboLoginPolicy.REQUIRE_INVITATION);
        when(users.findUserByUsername(EMAIL)).thenReturn(userOf(AuthProvider.keycloak));

        assertThatCode(() -> enforcer.enforce(REGISTRATION, identity)).doesNotThrowAnyException();
        verify(sync).syncUser(any(Oauth2SyncUsersData.class));
    }

    @Test
    void whereEveryIdentityIsTrustedAnUnknownOneBecomesAUser() {
        securityConfig.setLoginPolicy(GeboLoginPolicy.TRUST_EVERY_OAUTH_IDENTITY);
        when(users.findUserByUsername(EMAIL)).thenReturn(null);

        assertThatCode(() -> enforcer.enforce(REGISTRATION, identity)).doesNotThrowAnyException();
        verify(sync).createOrSyncUser(any(Oauth2SyncUsersData.class));
    }

    @Test
    void anIdentityWithoutAnEmailIsRefused() {
        securityConfig.setLoginPolicy(GeboLoginPolicy.TRUST_EVERY_OAUTH_IDENTITY);
        when(identity.getAttribute("email")).thenReturn(null);

        assertThatThrownBy(() -> enforcer.enforce(REGISTRATION, identity))
                .isInstanceOf(OAuth2AuthenticationException.class);
    }
}
