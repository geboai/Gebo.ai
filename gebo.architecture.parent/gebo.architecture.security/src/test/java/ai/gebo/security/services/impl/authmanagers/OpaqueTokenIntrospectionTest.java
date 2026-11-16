/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl.authmanagers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.introspection.BadOpaqueTokenException;

import com.sun.net.httpserver.HttpServer;

import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.SecurityHeaderUtil.XAuthType;
import ai.gebo.security.model.oauth2.Oauth2ProviderConfig;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.secrets.model.GeboOauth2SecretContent;

/**
 * Pins the opaque token introspection of the single sign-on configurations, servlet
 * and reactive, on Spring Security 7.2's introspectors: the request a provider gets
 * (RFC 7662: a form encoded {@code token=} body with the client's basic
 * authentication), the principal read from an active answer, an inactive token
 * refused, and the cache giving one introspector per configuration until it changes.
 */
class OpaqueTokenIntrospectionTest {

	record Received(String method, String contentType, String authorization, String body) {
	}

	HttpServer provider;
	final List<Received> received = new CopyOnWriteArrayList<>();

	@BeforeEach
	void startIntrospectionEndpoint() throws IOException {
		provider = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
		provider.createContext("/introspect", exchange -> {
			String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
			received.add(new Received(exchange.getRequestMethod(),
					exchange.getRequestHeaders().getFirst("Content-Type"),
					exchange.getRequestHeaders().getFirst("Authorization"), body));
			String answer = body.contains("token=good-token")
					? "{\"active\":true,\"sub\":\"8c2e\",\"username\":\"maria@example.test\",\"scope\":\"openid email\","
							+ "\"client_id\":\"gebo\",\"exp\":4102444800}"
					: "{\"active\":false}";
			byte[] bytes = answer.getBytes(StandardCharsets.UTF_8);
			exchange.getResponseHeaders().add("Content-Type", "application/json");
			exchange.sendResponseHeaders(200, bytes.length);
			try (OutputStream out = exchange.getResponseBody()) {
				out.write(bytes);
			}
		});
		provider.start();
	}

	@AfterEach
	void stopIntrospectionEndpoint() {
		provider.stop(0);
	}

	Oauth2RuntimeConfiguration configuration(String registrationId, String secret) {
		Oauth2RuntimeConfiguration configuration = new Oauth2RuntimeConfiguration();
		configuration.setRegistrationId(registrationId);
		Oauth2ProviderConfig providerConfig = new Oauth2ProviderConfig();
		providerConfig.setIntrospectionUri("http://127.0.0.1:" + provider.getAddress().getPort() + "/introspect");
		configuration.setProviderConfig(providerConfig);
		GeboOauth2SecretContent client = new GeboOauth2SecretContent();
		client.setClientId("gebo");
		client.setSecret(secret);
		configuration.setClient(client);
		return configuration;
	}

	static final String BASIC = "Basic " + Base64.getEncoder().encodeToString("gebo:s3cret".getBytes(StandardCharsets.UTF_8));

	void assertRfc7662Request(Received request, String token) {
		assertEquals("POST", request.method());
		assertTrue(request.contentType().startsWith("application/x-www-form-urlencoded"), request.contentType());
		assertEquals(BASIC, request.authorization());
		assertEquals("token=" + token, request.body());
	}

	@Test
	void theServletIntrospectorAsksAsRfc7662SaysAndReadsTheActivePrincipal() {
		OAuth2AuthenticatedPrincipal principal = new OpaqueTokenIntrospectorCache()
				.forConfiguration(configuration("keycloak", "s3cret")).introspect("good-token");

		assertEquals("8c2e", principal.getName());
		assertEquals("maria@example.test", principal.getAttribute("username"));
		assertEquals(1, received.size());
		assertRfc7662Request(received.get(0), "good-token");
	}

	@Test
	void theReactiveIntrospectorAsksTheSameWay() {
		OAuth2AuthenticatedPrincipal principal = new OpaqueTokenIntrospectorCache()
				.reactiveForConfiguration(configuration("keycloak", "s3cret")).introspect("good-token").block();

		assertEquals("8c2e", principal.getName());
		assertEquals(1, received.size());
		assertRfc7662Request(received.get(0), "good-token");
	}

	@Test
	void anInactiveTokenIsRefusedByBoth() {
		OpaqueTokenIntrospectorCache cache = new OpaqueTokenIntrospectorCache();
		Oauth2RuntimeConfiguration configuration = configuration("keycloak", "s3cret");

		assertThrows(BadOpaqueTokenException.class, () -> cache.forConfiguration(configuration).introspect("stale"));
		assertThrows(BadOpaqueTokenException.class,
				() -> cache.reactiveForConfiguration(configuration).introspect("stale").block());
	}

	@Test
	void theManagerMapsTheIntrospectedPrincipalThroughItsConverter() {
		SingleOauth2ConfigOpaqueTokenAuthenticationManager manager = new SingleOauth2ConfigOpaqueTokenAuthenticationManager(
				new SecurityHeaderData("good-token", XAuthType.OAUTH2, "keycloak", null, false),
				configuration("keycloak", "s3cret"),
				(token, principal) -> new UsernamePasswordAuthenticationToken(principal.getAttribute("username"),
						token, List.of()),
				null, new OpaqueTokenIntrospectorCache());

		Authentication authenticated = manager.authenticate(new BearerTokenAuthenticationToken("good-token"));

		assertEquals("maria@example.test", authenticated.getName());
		assertThrows(AuthenticationException.class,
				() -> manager.authenticate(new BearerTokenAuthenticationToken("stale")));
	}

	@Test
	void oneIntrospectorPerConfigurationUntilItChanges() {
		OpaqueTokenIntrospectorCache cache = new OpaqueTokenIntrospectorCache();

		assertSame(cache.forConfiguration(configuration("keycloak", "s3cret")),
				cache.forConfiguration(configuration("keycloak", "s3cret")));
		assertSame(cache.reactiveForConfiguration(configuration("keycloak", "s3cret")),
				cache.reactiveForConfiguration(configuration("keycloak", "s3cret")));
		assertNotSame(cache.forConfiguration(configuration("keycloak", "s3cret")),
				cache.forConfiguration(configuration("keycloak", "rotated")),
				"an edited client secret gets its own introspector");
		assertTrue(!OpaqueTokenIntrospectorCache.cacheKey(configuration("keycloak", "s3cret")).contains("s3cret"),
				"the secret is not part of the key");
	}

	@Test
	void aConfigurationThatCannotIntrospectIsRefused() {
		Oauth2RuntimeConfiguration noEndpoint = configuration("keycloak", "s3cret");
		noEndpoint.getProviderConfig().setIntrospectionUri(null);

		assertThrows(IllegalArgumentException.class, () -> new OpaqueTokenIntrospectorCache().forConfiguration(noEndpoint));
	}
}
