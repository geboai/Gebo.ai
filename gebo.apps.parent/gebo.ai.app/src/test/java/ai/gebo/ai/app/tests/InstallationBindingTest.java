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

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ai.gebo.security.config.GeboSecurityConfig;
import ai.gebo.security.model.GeboInstallation;
import ai.gebo.security.services.IGInstallationIdProvider;
import ai.gebo.security.services.impl.LocalJwtTokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Every token is bound to this installation: the installation record is created
 * once in the database (a UUID and the host name, never "localhost"), the
 * platform's tokens carry its id, and a token signed with the same secret but
 * minted elsewhere, or of the shape tokens had before, is refused.
 */
// Test-only signing key and token lifetime: the test context configures neither.
@TestPropertySource(properties = {
		"ai.gebo.security.auth.token-secret=dGVzdC1vbmx5LXNpZ25pbmcta2V5LWZvci10aGUtaW5zdGFsbGF0aW9uLWJpbmRpbmctdGVzdC1uZXZlci1hLXJlYWwtc2VjcmV0LTAxMjM0NTY3ODk=",
		"ai.gebo.security.auth.token-expiration-msec=600000" })
public class InstallationBindingTest extends AbstractBaseTestLLmsIntegrationTests {

	private static final String MY_CHATS = "/api/users/GeboUserChatsController/getMyChats";

	@Autowired
	private WebApplicationContext context;
	@Autowired
	private FilterChainProxy securityFilters;
	@Autowired
	private LocalJwtTokenProvider tokens;
	@Autowired
	private IGInstallationIdProvider installation;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private GeboSecurityConfig securityConfig;

	@Test
	public void testTokensAreBoundToThisInstallation() throws Exception {
		MockMvc mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(securityFilters).build();
		Date inTenMinutes = new Date(System.currentTimeMillis() + 600_000L);

		// the record: created once, a UUID joined to a real host name
		String id = installation.getInstallationId();
		assertNotNull(id);
		GeboInstallation record = mongoTemplate.findById(GeboInstallation.CODE, GeboInstallation.class);
		assertNotNull(record, "The installation record is in the database");
		assertEquals(id, record.getInstallationId());
		assertTrue(id.matches("[0-9a-f-]{36}@.+"), "uuid@host: " + id);
		assertNotEquals("localhost", record.getHostName());
		assertEquals(id, installation.getInstallationId(), "The id is stable");

		// the platform's own token carries it and is accepted
		String own = tokens.createToken(DEFAULT_ALL_ROLES_USER, inTenMinutes);
		assertEquals(200, status(mvc, own));

		// same secret, another installation: refused
		LocalJwtTokenProvider elsewhere = new LocalJwtTokenProvider(securityConfig, List.of(),
				"00000000-0000-0000-0000-000000000000@another-host");
		assertEquals(401, status(mvc, elsewhere.createToken(DEFAULT_ALL_ROLES_USER, inTenMinutes)),
				"A token of another installation is refused");
		assertEquals(401, status(mvc, elsewhere.createApiKeyToken(DEFAULT_ALL_ROLES_USER, inTenMinutes, "k-1",
				LocalJwtTokenProvider.API_KEY_KIND_ADMIN)), "An API key of another installation is refused");

		// same secret, the shape tokens had before (no id, no installation): refused
		String old = Jwts.builder().claim(LocalJwtTokenProvider.AUTH_TYPE_CLAIM, "LOCAL_JWT")
				.claim(LocalJwtTokenProvider.AUTH_PROVIDER_CLAIM, "local").setSubject(DEFAULT_ALL_ROLES_USER)
				.setIssuedAt(new Date()).setExpiration(inTenMinutes)
				.signWith(SignatureAlgorithm.HS512, securityConfig.getAuth().getTokenSecret()).compact();
		assertEquals(401, status(mvc, old), "A token of the old shape is refused");
	}

	private int status(MockMvc mvc, String token) throws Exception {
		return mvc.perform(get(MY_CHATS).header("Authorization", "Bearer " + token)).andReturn().getResponse()
				.getStatus();
	}
}
