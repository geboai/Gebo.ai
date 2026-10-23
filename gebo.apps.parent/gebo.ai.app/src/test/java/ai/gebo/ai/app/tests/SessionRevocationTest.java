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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.security.model.AuthProvider;
import ai.gebo.security.model.AuthProviderType;
import ai.gebo.security.services.impl.LocalJwtTokenProvider;

/**
 * A login's tokens form a session that an administrator sees and revokes: the
 * revocation refuses the login token and every token renewed from it on the
 * next request, the logout revokes the user's own session, and revoking all
 * the sessions of a user reaches the administrator's own.
 * <p>
 * Everything goes through HTTP with the security filters, as a client does.
 */
// Test-only signing key and token lifetime: the test context configures neither, and the tokens
// below must be verifiable and a renewal must not be born expired.
@TestPropertySource(properties = {
		"ai.gebo.security.auth.token-secret=dGVzdC1vbmx5LXNpZ25pbmcta2V5LWZvci10aGUtc2Vzc2lvbi1yZXZvY2F0aW9uLXRlc3QtbmV2ZXItYS1yZWFsLXNlY3JldC0wMTIzNDU2Nzg5",
		"ai.gebo.security.auth.token-expiration-msec=600000" })
public class SessionRevocationTest extends AbstractBaseTestLLmsIntegrationTests {

	private static final String MY_CHATS = "/api/users/GeboUserChatsController/getMyChats";
	private static final String RENEW = "/api/users/TokenRenewController/renew";
	private static final String LOGOUT = "/api/users/SessionController/logout";
	private static final String SESSIONS = "/api/admin/SessionsAdminController/getSessionsPagedList";
	private static final String REVOKE = "/api/admin/SessionsAdminController/revokeSession";
	private static final String REVOKE_ALL = "/api/admin/SessionsAdminController/revokeUserSessions";

	@Autowired
	private WebApplicationContext context;
	@Autowired
	private FilterChainProxy securityFilters;
	@Autowired
	private LocalJwtTokenProvider tokens;
	/** A mapper of its own: the test context has no ObjectMapper bean. */
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void testARevokedSessionRefusesItsTokensAndTheirRenewals() throws Exception {
		MockMvc mvc = mvc();
		// the user's login opens session A; the administrator works from session T
		String loginToken = login();
		String sessionA = claims(loginToken).path(LocalJwtTokenProvider.SESSION_ID_CLAIM).asText();
		String adminToken = login();
		assertNotEquals(sessionA, claims(adminToken).path(LocalJwtTokenProvider.SESSION_ID_CLAIM).asText(),
				"Every login opens a session of its own");

		assertEquals(200, status(mvc, loginToken, MY_CHATS), "The login token authenticates");
		// renewing keeps the session
		MockHttpServletResponse renewed = mvc.perform(get(RENEW).header("Authorization", "Bearer " + loginToken)
				.header("X-AuthType", "LOCAL_JWT")).andReturn().getResponse();
		assertEquals(200, renewed.getStatus());
		String renewedToken = objectMapper.readTree(renewed.getContentAsString()).path("token").asText();
		assertNotEquals(loginToken, renewedToken, "The renewal is a new token");
		assertEquals(sessionA, claims(renewedToken).path(LocalJwtTokenProvider.SESSION_ID_CLAIM).asText(),
				"The renewed token stays in the session");
		assertEquals(200, status(mvc, renewedToken, MY_CHATS));

		// the administrator sees the session, with the user
		JsonNode listed = sessions(mvc, adminToken, DEFAULT_ALL_ROLES_USER);
		JsonNode sessionRow = null;
		for (JsonNode row : listed.path("content")) {
			if (sessionA.equals(row.path("code").asText())) {
				sessionRow = row;
			}
		}
		assertNotNull(sessionRow, "The session is listed: " + listed);
		assertEquals(DEFAULT_ALL_ROLES_USER, sessionRow.path("username").asText());
		assertEquals("SESSION", sessionRow.path("kind").asText());
		assertTrue(sessionRow.path("lastSeen").isNumber() || sessionRow.path("lastSeen").isTextual(),
				"The session records when it was last seen");

		// the administrator revokes it: both tokens are refused from the next request
		MockHttpServletResponse revoke = mvc.perform(post(REVOKE).param("code", sessionA)
				.header("Authorization", "Bearer " + adminToken)).andReturn().getResponse();
		assertEquals(200, revoke.getStatus());
		assertTrue(objectMapper.readTree(revoke.getContentAsString()).path("revoked").asBoolean());
		assertEquals(401, status(mvc, loginToken, MY_CHATS), "The login token is refused");
		assertEquals(401, status(mvc, renewedToken, MY_CHATS), "The token renewed from it is refused");
		assertEquals(401, mvc.perform(get(RENEW).header("Authorization", "Bearer " + renewedToken)
				.header("X-AuthType", "LOCAL_JWT")).andReturn().getResponse().getStatus(),
				"A revoked session cannot be renewed");
		assertEquals(200, status(mvc, adminToken, MY_CHATS), "The administrator's own session goes on");
	}

	@Test
	public void testTheLogoutRevokesTheOwnSession() throws Exception {
		MockMvc mvc = mvc();
		String token = login();
		assertEquals(200, status(mvc, token, MY_CHATS));

		MockHttpServletResponse logout = mvc.perform(post(LOGOUT).header("Authorization", "Bearer " + token))
				.andReturn().getResponse();
		assertEquals(200, logout.getStatus());
		assertEquals("true", logout.getContentAsString().trim(), "The session was found and revoked");
		assertEquals(401, status(mvc, token, MY_CHATS), "The token the browser forgot is refused");
	}

	@Test
	public void testRevokingAllTheSessionsOfAUserReachesTheAdministratorsOwn() throws Exception {
		MockMvc mvc = mvc();
		String first = login();
		String second = login();
		assertEquals(200, status(mvc, first, MY_CHATS));
		assertEquals(200, status(mvc, second, MY_CHATS));

		MockHttpServletResponse revokeAll = mvc.perform(post(REVOKE_ALL).param("username", DEFAULT_ALL_ROLES_USER)
				.header("Authorization", "Bearer " + second)).andReturn().getResponse();
		assertEquals(200, revokeAll.getStatus());
		assertTrue(Integer.parseInt(revokeAll.getContentAsString().trim()) >= 2,
				"Both live sessions of the user are revoked: " + revokeAll.getContentAsString());
		assertEquals(401, status(mvc, first, MY_CHATS));
		assertEquals(401, status(mvc, second, MY_CHATS), "The administrator revoked their own session too");
	}

	/** A token minted as a login mints it: a session of its own. */
	private String login() {
		return tokens.createToken(DEFAULT_ALL_ROLES_USER, AuthProviderType.LOCAL_JWT, AuthProvider.local, null,
				System.currentTimeMillis() + 600_000L);
	}

	private JsonNode sessions(MockMvc mvc, String adminToken, String username) throws Exception {
		String body = "{\"page\":{\"page\":0,\"pageSize\":50},\"query\":{\"username\":\"" + username
				+ "\",\"includeRevoked\":true,\"includeExpired\":true}}";
		MockHttpServletResponse response = mvc.perform(post(SESSIONS).header("Authorization", "Bearer " + adminToken)
				.contentType(MediaType.APPLICATION_JSON).content(body)).andReturn().getResponse();
		assertEquals(200, response.getStatus(), response.getContentAsString());
		return objectMapper.readTree(response.getContentAsString());
	}

	private JsonNode claims(String token) throws Exception {
		return objectMapper.readTree(Base64.getUrlDecoder().decode(token.split("\\.")[1]));
	}

	private MockMvc mvc() {
		return MockMvcBuilders.webAppContextSetup(context).addFilters(securityFilters).build();
	}

	private int status(MockMvc mvc, String token, String url) throws Exception {
		return mvc.perform(get(url).header("Authorization", "Bearer " + token)).andReturn().getResponse().getStatus();
	}
}
