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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.sun.net.httpserver.HttpServer;

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
 * The reactive OAuth2 login makes the same decision as the servlet ones, through the
 * same enforcer: the user is described by a user info endpoint served on the
 * loopback interface, as a provider serves it.
 */
class ReactiveGOAuth2UserServiceTest {

    private static final String REGISTRATION = "keycloak-1";
    private static final String EMAIL = "invited@demo-company.example";

    private static HttpServer userInfo;

    private IGUsersAdminService users;
    private GeboSecurityConfig securityConfig;
    private IGOauth2UserSyncService sync;
    private Map<String, Object> details;
    private ReactiveGOAuth2UserService service;

    @BeforeAll
    static void serveTheUserInfo() throws Exception {
        byte[] body = ("{\"sub\":\"f81d4fae\",\"email\":\"" + EMAIL + "\"}").getBytes(StandardCharsets.UTF_8);
        userInfo = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 0);
        userInfo.createContext("/userinfo", exchange -> {
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(body);
            }
        });
        userInfo.start();
    }

    @AfterAll
    static void stopServingTheUserInfo() {
        userInfo.stop(0);
    }

    @BeforeEach
    void setUp() throws Exception {
        IGOauth2ConfigurationService configurations = mock(IGOauth2ConfigurationService.class);
        users = mock(IGUsersAdminService.class);
        securityConfig = new GeboSecurityConfig();
        IGOauth2UserSyncServiceConditionedImplementationProvider syncProvider = mock(
                IGOauth2UserSyncServiceConditionedImplementationProvider.class);
        sync = mock(IGOauth2UserSyncService.class);
        when(syncProvider.handlerOf(any(Oauth2SyncUsersData.class))).thenReturn(sync);
        IGSecurityAuditLoggerService audit = mock(IGSecurityAuditLoggerService.class);
        SecurityEvent event = mock(SecurityEvent.class);
        details = new HashMap<>();
        when(event.getDetails()).thenReturn(details);
        when(audit.newSecurityEvent()).thenReturn(event);

        Oauth2RuntimeConfiguration runtime = new Oauth2RuntimeConfiguration();
        runtime.setRegistrationId(REGISTRATION);
        runtime.setProvider(AuthProvider.keycloak);
        runtime.setConfigurationType(Oauth2ConfigurationType.AUTHENTICATION);
        when(configurations.findOauth2ClientRegistrationByRegistrationId(REGISTRATION))
                .thenReturn(new Oauth2ClientRegistration(null, runtime));

        service = new ReactiveGOAuth2UserService(configurations, users, securityConfig, syncProvider, audit);
    }

    private static OAuth2UserRequest request() {
        ClientRegistration registration = ClientRegistration.withRegistrationId(REGISTRATION).clientId("gebo-ai")
                .clientSecret("a-secret").authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .authorizationUri("https://sso.example.com/auth").tokenUri("https://sso.example.com/token")
                .userInfoUri("http://" + userInfo.getAddress().getHostString() + ":" + userInfo.getAddress().getPort()
                        + "/userinfo")
                .userNameAttributeName("sub").build();
        Instant now = Instant.now();
        return new OAuth2UserRequest(registration,
                new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "an-access-token", now, now.plusSeconds(300)));
    }

    private static EditableUser invited(boolean disabled) {
        EditableUser user = new EditableUser();
        user.setUsername(EMAIL);
        user.setAuthProvider(AuthProvider.keycloak);
        user.setDisabled(disabled);
        return user;
    }

    @Test
    void anInvitedIdentityIsLetInAndAuditedAsTheReactiveLogin() {
        securityConfig.setLoginPolicy(GeboLoginPolicy.REQUIRE_INVITATION);
        when(users.findUserByUsername(EMAIL)).thenReturn(invited(false));

        OAuth2User user = service.loadUser(request()).block();

        assertThat(user.<String>getAttribute("email")).isEqualTo(EMAIL);
        verify(sync).syncUser(any(Oauth2SyncUsersData.class));
        assertThat(details.get("flow")).isEqualTo("oauth2LoginReactive");
    }

    @Test
    void aDisabledAccountIsRefused() {
        securityConfig.setLoginPolicy(GeboLoginPolicy.REQUIRE_INVITATION);
        when(users.findUserByUsername(EMAIL)).thenReturn(invited(true));

        assertThatThrownBy(() -> service.loadUser(request()).block())
                .isInstanceOf(OAuth2AuthenticationException.class);
        verify(sync, never()).syncUser(any());
        assertThat(details.get("reason")).isEqualTo("userDisabled");
    }

    @Test
    void anIdentityNobodyInvitedIsRefused() {
        securityConfig.setLoginPolicy(GeboLoginPolicy.REQUIRE_INVITATION);
        when(users.findUserByUsername(EMAIL)).thenReturn(null);

        assertThatThrownBy(() -> service.loadUser(request()).block())
                .isInstanceOf(OAuth2AuthenticationException.class);
        assertThat(details.get("reason")).isEqualTo("unknownIdentity");
    }
}
