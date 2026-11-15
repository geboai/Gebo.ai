/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Base64;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.security.services.impl.LocalJwtTokenProvider;

/**
 * A personal API key authenticates its user until it is deleted: from then on
 * the key is refused before its expiration, and it can never be renewed into a
 * session token that a deletion would not reach.
 * <p>
 * Everything goes through HTTP as a client does: the key is generated with the
 * user's session token, used as a bearer token, deleted, used again.
 */
// Test-only signing key: the test context configures none, and the tokens below must be verifiable.
@TestPropertySource(properties = "ai.gebo.security.auth.token-secret=dGVzdC1vbmx5LXNpZ25pbmcta2V5LWZvci10aGUtYXBpLWtleS1yZXZvY2F0aW9uLXRlc3QtbmV2ZXItYS1yZWFsLXNlY3JldC0wMTIzNDU2Nzg5")
public class ApiKeyRevocationTest extends AbstractBaseTestLLmsIntegrationTests {

	private static final String GENERATE = "/api/users/GeneratedUserApiKeyController/generateUserGeneratedApiKey";
	private static final String DELETE = "/api/users/GeneratedUserApiKeyController/deleteUserGeneratedApiKey";
	private static final String MY_CHATS = "/api/users/GeboUserChatsController/getMyChats";
	private static final String RENEW = "/api/users/TokenRenewController/renew";

	@Autowired
	private WebApplicationContext context;
	@Autowired
	private FilterChainProxy securityFilters;
	@Autowired
	private LocalJwtTokenProvider tokens;
	/** A mapper of its own: the test context has no ObjectMapper bean (dates go as epoch millis). */
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void testADeletedApiKeyIsRefusedBeforeItsExpiration() throws Exception {
		MockMvc mvc = mvc();
		String session = tokens.createToken(DEFAULT_ALL_ROLES_USER, new Date(System.currentTimeMillis() + 600_000));

		// the user generates a personal key, good for an hour
		String body = objectMapper.writeValueAsString(new GenerateParam("Revocation test key",
				new Date(System.currentTimeMillis() + 3_600_000)));
		MockHttpServletResponse generated = mvc.perform(post(GENERATE).header("Authorization", "Bearer " + session)
				.contentType(MediaType.APPLICATION_JSON).content(body)).andReturn().getResponse();
		assertEquals(200, generated.getStatus(), "The user can generate a personal API key");
		JsonNode key = objectMapper.readTree(generated.getContentAsString());
		String apiKey = key.path("apiKey").asText(null);
		String code = key.path("code").asText(null);
		assertNotNull(apiKey, "The generated key is returned to the user");
		assertNotNull(code, "The generated key has a code");
		assertTrue(key.path("tokenId").isMissingNode(), "The record's token id is not part of the REST representation");

		// the key is a JWT marked as an API key, carrying the id of its record (the
		// payload is read as a client would, without the signing secret)
		JsonNode claims = objectMapper.readTree(Base64.getUrlDecoder().decode(apiKey.split("\\.")[1]));
		assertEquals(DEFAULT_ALL_ROLES_USER, claims.path("sub").asText());
		assertTrue(claims.path(LocalJwtTokenProvider.API_KEY_CLAIM).asBoolean(), "The token carries the API key claim");
		assertEquals(LocalJwtTokenProvider.API_KEY_KIND_USER,
				claims.path(LocalJwtTokenProvider.API_KEY_KIND_CLAIM).asText());
		assertTrue(claims.path("jti").isTextual(), "The token carries the id of its record");

		// the key authenticates the user
		assertEquals(200, status(mvc, apiKey, MY_CHATS), "The key authenticates its user");

		// the key is not renewed into a session token
		MockHttpServletResponse renewed = mvc.perform(get(RENEW).header("Authorization", "Bearer " + apiKey)
				.header("X-AuthType", "LOCAL_JWT")).andReturn().getResponse();
		assertEquals(200, renewed.getStatus());
		assertEquals(apiKey, objectMapper.readTree(renewed.getContentAsString()).path("token").asText(),
				"Renewing with an API key gives back the very same key");

		// the user deletes the key: it is refused on the next request, before its expiration
		assertEquals(200, mvc.perform(post(DELETE).param("code", code).header("Authorization", "Bearer " + session))
				.andReturn().getResponse().getStatus(), "The user deletes the key");
		assertEquals(401, status(mvc, apiKey, MY_CHATS), "The deleted key is refused although not expired");
		assertEquals(200, status(mvc, session, MY_CHATS), "The user's session is untouched");
	}

	private MockMvc mvc() {
		return MockMvcBuilders.webAppContextSetup(context).addFilters(securityFilters).build();
	}

	private int status(MockMvc mvc, String token, String url) throws Exception {
		return mvc.perform(get(url).header("Authorization", "Bearer " + token)).andReturn().getResponse().getStatus();
	}

	/** The body of the generate request, as the user interface sends it. */
	public record GenerateParam(String description, Date expiration) {
	}
}
