/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.sun.net.httpserver.HttpServer;

import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.SecurityHeaderUtil;
import ai.gebo.security.model.SecurityHeaderUtil.XAuthType;
import ai.gebo.security.model.oauth2.Oauth2ConfigurationType;
import ai.gebo.security.model.oauth2.Oauth2ProviderConfig;
import ai.gebo.security.model.oauth2.Oauth2RuntimeConfiguration;
import ai.gebo.security.services.IGOauth2RuntimeConfigurationDao;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.secrets.model.GeboOauth2SecretContent;

/**
 * Pins how the header completion tells which single sign-on configuration issued an
 * opaque token: each configuration's introspection endpoint is asked in turn, and the
 * one answering active names the registration.
 */
class SecurityHeaderDataCompletionControllerTest {

	HttpServer providers;
	final Map<String, AtomicInteger> asked = new ConcurrentHashMap<>();
	SecurityHeaderDataCompletionController controller;

	@BeforeEach
	void setUp() throws IOException {
		providers = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
		// the first provider knows no token, the second one knows only its own
		for (String name : List.of("first", "second")) {
			providers.createContext("/" + name + "/introspect", exchange -> {
				asked.computeIfAbsent(name, k -> new AtomicInteger()).incrementAndGet();
				String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
				boolean active = name.equals("second") && body.contains("token=opaque-from-second");
				byte[] answer = (active ? "{\"active\":true,\"sub\":\"u-2\"}" : "{\"active\":false}")
						.getBytes(StandardCharsets.UTF_8);
				exchange.getResponseHeaders().add("Content-Type", "application/json");
				exchange.sendResponseHeaders(200, answer.length);
				try (OutputStream out = exchange.getResponseBody()) {
					out.write(answer);
				}
			});
		}
		providers.start();
		IGOauth2RuntimeConfigurationDao dao = mock(IGOauth2RuntimeConfigurationDao.class);
		when(dao.findByConfigurationType(Oauth2ConfigurationType.AUTHENTICATION))
				.thenReturn(List.of(configuration("first"), configuration("second")));
		IGSecurityAuditLoggerService audit = mock(IGSecurityAuditLoggerService.class);
		when(audit.newSecurityEvent()).thenReturn(mock(SecurityEvent.class));
		controller = new SecurityHeaderDataCompletionController(dao, audit);
	}

	@AfterEach
	void tearDown() {
		providers.stop(0);
	}

	Oauth2RuntimeConfiguration configuration(String name) {
		Oauth2RuntimeConfiguration configuration = new Oauth2RuntimeConfiguration();
		configuration.setRegistrationId(name + "-sso");
		configuration.setConfigurationType(Oauth2ConfigurationType.AUTHENTICATION);
		Oauth2ProviderConfig provider = new Oauth2ProviderConfig();
		provider.setIntrospectionUri(
				"http://127.0.0.1:" + providers.getAddress().getPort() + "/" + name + "/introspect");
		configuration.setProviderConfig(provider);
		GeboOauth2SecretContent client = new GeboOauth2SecretContent();
		client.setClientId("gebo-" + name);
		client.setSecret("secret-" + name);
		configuration.setClient(client);
		return configuration;
	}

	static MockHttpServletRequest bearer(String token) {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(SecurityHeaderUtil.AUTHORIZATION, "Bearer " + token);
		return request;
	}

	@Test
	void theConfigurationWhoseProviderKnowsTheTokenNamesIt() {
		SecurityHeaderData completed = controller.complete(bearer("opaque-from-second"));

		assertEquals(XAuthType.OAUTH2, completed.getAuthType());
		assertEquals("second-sso", completed.getAuthProviderId());
		assertEquals(1, asked.get("first").get(), "the first provider was asked and said no");
		assertEquals(1, asked.get("second").get());
	}

	@Test
	void aTokenNoProviderKnowsKeepsTheDeclaredProvider() {
		SecurityHeaderData completed = controller.complete(bearer("unknown-opaque"));

		assertEquals(XAuthType.OAUTH2, completed.getAuthType());
		assertEquals(SecurityHeaderUtil.DEFAULT_PROVIDER_ID, completed.getAuthProviderId());
	}

	@Test
	void everyCallAsksTheProvidersAgainThroughTheSameIntrospectors() {
		controller.complete(bearer("opaque-from-second"));
		controller.complete(bearer("opaque-from-second"));

		assertEquals(2, asked.get("second").get(), "the answer is not cached, the introspectors are");
	}
}
