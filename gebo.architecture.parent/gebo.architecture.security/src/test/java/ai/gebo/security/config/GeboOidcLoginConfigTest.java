/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.security.oauth2.jwt.JwtValidationException;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.sun.net.httpserver.HttpServer;

import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.oauth2.Oauth2ClientRegistration;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.services.IGOauth2ConfigurationService;
import ai.gebo.security.services.IGOauth2UserSyncServiceConditionedImplementationProvider;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGUsersAdminService;
import ai.gebo.security.services.impl.GMicrosoftAccountsIdTokenValidator;

/**
 * The id token decoding the OpenID Connect login gets from this configuration, found
 * the way Spring's {@code OAuth2LoginConfigurer} finds it and run on tokens signed
 * with a key served as a provider serves its keys.
 */
class GeboOidcLoginConfigTest {

    /** What {@code OAuth2LoginConfigurer} looks the decoding up by. */
    private static final ResolvableType DECODER_FACTORY = ResolvableType.forClassWithGenerics(JwtDecoderFactory.class,
            ClientRegistration.class);
    private static final String CLIENT_ID = "11111111-2222-3333-4444-555555555555";
    private static final String CONTOSO = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee";
    private static final String FABRIKAM = "ffffffff-bbbb-cccc-dddd-eeeeeeeeeeee";

    private static RSAKey key;
    private static HttpServer keys;

    private IGOauth2ConfigurationService configurations;

    @BeforeAll
    static void serveTheKeys() throws Exception {
        key = new RSAKeyGenerator(2048).keyID("test-key").generate();
        byte[] body = new JWKSet(key.toPublicJWK()).toString().getBytes(StandardCharsets.UTF_8);
        keys = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 0);
        keys.createContext("/keys", exchange -> {
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(body);
            }
        });
        keys.start();
    }

    @AfterAll
    static void stopServingTheKeys() {
        keys.stop(0);
    }

    @BeforeEach
    void setUp() {
        configurations = mock(IGOauth2ConfigurationService.class);
    }

    private AnnotationConfigApplicationContext context() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.registerBean(IGOauth2ConfigurationService.class, () -> configurations);
        context.registerBean(IGUsersAdminService.class, () -> mock(IGUsersAdminService.class));
        context.registerBean(GeboSecurityConfig.class, GeboSecurityConfig::new);
        context.registerBean(IGOauth2UserSyncServiceConditionedImplementationProvider.class,
                () -> mock(IGOauth2UserSyncServiceConditionedImplementationProvider.class));
        context.registerBean(IGSecurityAuditLoggerService.class, () -> mock(IGSecurityAuditLoggerService.class));
        context.register(GeboOidcLoginConfig.class);
        context.refresh();
        return context;
    }

    /**
     * {@code OAuth2LoginConfigurer} takes the bean only when it is the one of its
     * type, and silently uses Spring's own otherwise.
     */
    @Test
    void springFindsExactlyOneIdTokenDecoding() {
        try (AnnotationConfigApplicationContext context = context()) {
            assertThat(context.getBeanNamesForType(DECODER_FACTORY)).hasSize(1);
            assertThat(context.getBeanProvider(DECODER_FACTORY).getIfUnique()).isNotNull();
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void aSignedIdTokenOfAnAdmittedTenantDecodesAndOneOfAnotherIsRefused() throws Exception {
        Oauth2RuntimeConfiguration runtime = new Oauth2RuntimeConfiguration();
        runtime.setRegistrationId("microsoft-test");
        runtime.setProvider(AuthProvider.microsoft);
        GeboOauth2SecretContent secret = new GeboOauth2SecretContent();
        LinkedHashMap<String, String> attributes = new LinkedHashMap<>();
        attributes.put(GMicrosoftAccountsIdTokenValidator.ALLOWED_TENANTS, "consumers, " + CONTOSO);
        secret.setCustomAttributes(attributes);
        when(configurations.findOauth2ClientRegistrationByRegistrationId("microsoft-test"))
                .thenReturn(new Oauth2ClientRegistration(secret, runtime));

        ClientRegistration registration = ClientRegistration.withRegistrationId("microsoft-test").clientId(CLIENT_ID)
                .clientSecret("a-secret").authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}").scope("openid", "profile", "email")
                .authorizationUri("https://login.microsoftonline.com/common/oauth2/v2.0/authorize")
                .tokenUri("https://login.microsoftonline.com/common/oauth2/v2.0/token")
                .jwkSetUri("http://" + keys.getAddress().getHostString() + ":" + keys.getAddress().getPort() + "/keys")
                .issuerUri("https://login.microsoftonline.com/"
                        + GMicrosoftAccountsIdTokenValidator.PERSONAL_ACCOUNTS_TENANT + "/v2.0")
                .build();

        try (AnnotationConfigApplicationContext context = context()) {
            JwtDecoderFactory<ClientRegistration> decoding = (JwtDecoderFactory<ClientRegistration>) context
                    .getBeanProvider(DECODER_FACTORY).getIfUnique();

            Jwt admitted = decoding.createDecoder(registration).decode(signedIdToken(CONTOSO));
            assertThat(admitted.getClaimAsString("tid")).isEqualTo(CONTOSO);

            assertThatThrownBy(() -> decoding.createDecoder(registration).decode(signedIdToken(FABRIKAM)))
                    .isInstanceOf(JwtValidationException.class).hasMessageContaining(FABRIKAM);
        }
    }

    /** An id token as Microsoft's v2.0 endpoints sign it for a person of the tenant. */
    private static String signedIdToken(String tenant) throws Exception {
        Instant now = Instant.now();
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer("https://login.microsoftonline.com/" + tenant + "/v2.0")
                .subject("AAAAAAAAAAAAAAAAAAAAAIkzqFVrSaSaFHy782bbtaQ").audience(CLIENT_ID).issueTime(Date.from(now))
                .expirationTime(Date.from(now.plusSeconds(3600))).claim("tid", tenant)
                .claim("email", "anna.bianchi@contoso.example").claim("ver", "2.0").build();
        SignedJWT token = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(key.getKeyID()).build(),
                claims);
        token.sign(new RSASSASigner(key));
        return token.serialize();
    }
}
