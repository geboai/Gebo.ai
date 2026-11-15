/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.security.services.impl.authmanagers;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

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
import ai.gebo.security.model.oauth2.Oauth2ConfigurationType;
import ai.gebo.security.model.oauth2.Oauth2ProviderConfig;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.services.impl.GMicrosoftAccountsIdTokenValidator;

/**
 * Signs tokens shaped as Microsoft's v2.0 endpoints issue them, with a key served on
 * the loopback interface the way a provider serves its keys, for the tests of which
 * tokens the API accepts.
 */
public final class MicrosoftTokenSigner implements AutoCloseable {

    public static final String CLIENT_ID = "11111111-2222-3333-4444-555555555555";
    public static final String PERSONAL = GMicrosoftAccountsIdTokenValidator.PERSONAL_ACCOUNTS_TENANT;
    public static final String CONTOSO = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee";
    public static final String FABRIKAM = "ffffffff-bbbb-cccc-dddd-eeeeeeeeeeee";

    private final RSAKey key;
    private final RSAKey anotherKey;
    private final HttpServer server;

    public MicrosoftTokenSigner() throws Exception {
        key = new RSAKeyGenerator(2048).keyID("test-key").generate();
        anotherKey = new RSAKeyGenerator(2048).keyID("test-key").generate();
        server = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 0);
        serve("/keys", new JWKSet(key.toPublicJWK()).toString());
        server.start();
    }

    private void serve(String path, String json) {
        byte[] body = json.getBytes(StandardCharsets.UTF_8);
        server.createContext(path, exchange -> {
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(body);
            }
        });
    }

    private String baseUrl() {
        return "http://" + server.getAddress().getHostString() + ":" + server.getAddress().getPort();
    }

    /** Where the keys are served, as a provider's jwks_uri. */
    public String jwkSetUri() {
        return baseUrl() + "/keys";
    }

    /**
     * Publishes the OpenID discovery document of an issuer of the given path signing
     * with the served key, as a Keycloak realm does.
     *
     * @return the issuer
     */
    public String serveIssuer(String path) {
        String issuer = baseUrl() + path;
        serve(path + "/.well-known/openid-configuration", "{\"issuer\":\"" + issuer + "\",\"jwks_uri\":\""
                + jwkSetUri() + "\",\"subject_types_supported\":[\"public\"]}");
        return issuer;
    }

    /** The claims of a token issued to the client for a person of the tenant. */
    public static JWTClaimsSet.Builder claimsOf(String tenant) {
        Instant now = Instant.now();
        return new JWTClaimsSet.Builder().issuer("https://login.microsoftonline.com/" + tenant + "/v2.0")
                .subject("AAAAAAAAAAAAAAAAAAAAAIkzqFVrSaSaFHy782bbtaQ").audience(CLIENT_ID).issueTime(Date.from(now))
                .expirationTime(Date.from(now.plusSeconds(3600))).claim("tid", tenant)
                .claim("email", "anna.bianchi@contoso.example").claim("ver", "2.0");
    }

    /** Signed with the key that is served. */
    public String sign(JWTClaimsSet.Builder claims) throws Exception {
        return sign(claims, key);
    }

    /** Signed with a key that is not served, under the same key id. */
    public String signWithAnotherKey(JWTClaimsSet.Builder claims) throws Exception {
        return sign(claims, anotherKey);
    }

    private static String sign(JWTClaimsSet.Builder claims, RSAKey with) throws Exception {
        SignedJWT token = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(with.getKeyID()).build(),
                claims.build());
        token.sign(new RSASSASigner(with));
        return token.serialize();
    }

    /**
     * A Microsoft accounts configuration as the store hands it out: the endpoints of
     * the library entry, except the keys, which are the served ones, and the client
     * it was saved with.
     */
    public Oauth2RuntimeConfiguration microsoftAccounts(String registrationId, String allowedTenants,
            List<String> audiences) {
        Oauth2ProviderConfig provider = new Oauth2ProviderConfig();
        provider.setProvider(AuthProvider.microsoft);
        provider.setIssuerUri("https://login.microsoftonline.com/" + PERSONAL + "/v2.0");
        provider.setJwkSetUri(jwkSetUri());
        provider.setUserNameAttribute("sub");
        return configuration(registrationId, AuthProvider.microsoft, provider, allowedTenants, audiences);
    }

    /** A configuration of another provider, signing under the issuer given. */
    public static Oauth2RuntimeConfiguration otherProvider(String registrationId, AuthProvider authProvider,
            String issuer, String allowedTenants) {
        Oauth2ProviderConfig provider = new Oauth2ProviderConfig();
        provider.setProvider(authProvider);
        provider.setIssuerUri(issuer);
        return configuration(registrationId, authProvider, provider, allowedTenants, null);
    }

    private static Oauth2RuntimeConfiguration configuration(String registrationId, AuthProvider authProvider,
            Oauth2ProviderConfig provider, String allowedTenants, List<String> audiences) {
        GeboOauth2SecretContent client = new GeboOauth2SecretContent();
        client.setClientId(CLIENT_ID);
        client.setSecret("a-client-secret");
        LinkedHashMap<String, String> attributes = new LinkedHashMap<>();
        if (allowedTenants != null) {
            attributes.put(GMicrosoftAccountsIdTokenValidator.ALLOWED_TENANTS, allowedTenants);
        }
        client.setCustomAttributes(attributes);
        Oauth2RuntimeConfiguration configuration = new Oauth2RuntimeConfiguration();
        configuration.setRegistrationId(registrationId);
        configuration.setProvider(authProvider);
        configuration.setConfigurationType(Oauth2ConfigurationType.AUTHENTICATION);
        configuration.setProviderConfig(provider);
        configuration.setClient(client);
        configuration.setResourceServerAudiences(audiences);
        return configuration;
    }

    @Override
    public void close() {
        server.stop(0);
    }
}
