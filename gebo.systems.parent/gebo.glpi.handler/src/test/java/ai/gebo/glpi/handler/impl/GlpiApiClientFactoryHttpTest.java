/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import ai.gebo.glpi.handler.GGlpiSystem;
import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

/**
 * The calls a GLPI client makes, against a local server standing for GLPI: the
 * password grant form, then the bearer header sent once (the generated client
 * would send it twice, which GLPI reads as an invalid token) with the pinned
 * API version.
 */
class GlpiApiClientFactoryHttpTest {

	private HttpServer server;
	private final List<String> tokenForms = new ArrayList<String>();
	private final List<List<String>> sessionAuthorizations = new ArrayList<List<String>>();
	private final List<String> sessionApiVersions = new ArrayList<String>();

	private static void answer(HttpExchange exchange, String json) throws IOException {
		byte[] body = json.getBytes(StandardCharsets.UTF_8);
		exchange.getResponseHeaders().add("Content-Type", "application/json");
		exchange.sendResponseHeaders(200, body.length);
		exchange.getResponseBody().write(body);
		exchange.close();
	}

	@BeforeEach
	void startGlpi() throws IOException {
		server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
		server.createContext("/glpi/api.php/token", exchange -> {
			tokenForms.add(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
			answer(exchange, "{\"token_type\":\"Bearer\",\"expires_in\":3600,\"access_token\":\"a.b.c\"}");
		});
		server.createContext("/glpi/api.php/session", exchange -> {
			sessionAuthorizations.add(exchange.getRequestHeaders().get("Authorization"));
			sessionApiVersions.add(exchange.getRequestHeaders().getFirst("GLPI-API-Version"));
			answer(exchange, "{\"user_id\":2,\"name\":\"glpi\",\"profiles\":{\"4\":{\"name\":\"Super-Admin\"}}}");
		});
		server.start();
	}

	@AfterEach
	void stopGlpi() {
		server.stop(0);
	}

	@SuppressWarnings("unchecked")
	private GlpiApiClientFactory factory() throws Exception {
		IGeboSecretsAccessService secrets = mock(IGeboSecretsAccessService.class);
		GeboOauth2SecretContent client = new GeboOauth2SecretContent();
		client.setClientId("client-id");
		client.setSecret("client-secret");
		GeboUsernamePasswordContent user = new GeboUsernamePasswordContent();
		user.setUsername("glpi");
		user.setPassword("p&ss word");
		when(secrets.getSecretContentById("client-secret-code")).thenReturn(client);
		when(secrets.getSecretContentById("user-secret-code")).thenReturn(user);
		return new GlpiApiClientFactory(secrets, null, mock(ObjectProvider.class));
	}

	private GGlpiSystem system() {
		GGlpiSystem system = new GGlpiSystem();
		system.setCode("glpi1");
		system.setBaseUri("http://127.0.0.1:" + server.getAddress().getPort() + "/glpi/");
		system.setOauthClientSecretCode("client-secret-code");
		system.setSecretCode("user-secret-code");
		return system;
	}

	@Test
	void aCallCarriesOneBearerHeaderAndThePinnedApiVersion() throws Exception {
		Map<String, Object> session = new GlpiRemoteAccess(factory().getApiClient(system())).session();

		assertThat(session).containsEntry("name", "glpi");
		assertThat(tokenForms).hasSize(1);
		assertThat(tokenForms.get(0)).contains("grant_type=password").contains("client_id=client-id")
				.contains("client_secret=client-secret").contains("username=glpi").contains("password=p%26ss+word")
				.contains("scope=api");
		assertThat(sessionAuthorizations).containsExactly(List.of("Bearer a.b.c"));
		assertThat(sessionApiVersions).containsExactly(GlpiApiClientFactory.GLPI_API_VERSION);
	}

	@Test
	void theTokenIsReusedUntilItExpires() throws Exception {
		GlpiApiClientFactory factory = factory();
		new GlpiRemoteAccess(factory.getApiClient(system())).session();
		new GlpiRemoteAccess(factory.getApiClient(system())).session();
		assertThat(tokenForms).hasSize(1);
		assertThat(sessionAuthorizations).hasSize(2);
	}

	@Test
	void aSystemTestAsksANewToken() throws Exception {
		GlpiApiClientFactory factory = factory();
		new GlpiRemoteAccess(factory.getApiClient(system())).session();
		new GlpiRemoteAccess(factory.getApiClientWithNewToken(system())).session();
		assertThat(tokenForms).hasSize(2);
		assertThat(sessionAuthorizations).allMatch(values -> values.size() == 1);
	}

}
