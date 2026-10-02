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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ai.gebo.security.config.GeboAISecurityConfig;
import ai.gebo.security.services.impl.LocalJwtTokenProvider;

// Test-only signing key: the test context configures none, and the tokens below must be verifiable.
@TestPropertySource(properties = "ai.gebo.security.auth.token-secret=dGVzdC1vbmx5LXNpZ25pbmcta2V5LWZvci10aGUtYXBpLXVybC1yb2xlLXJ1bGVzLXRlc3QtbmV2ZXItYS1yZWFsLXNlY3JldC0wMTIzNDU2Nzg5")
public class ApiUrlRoleRulesTest extends AbstractBaseTestLLmsIntegrationTests {

	private static final String USER_ONLY = "url-rules-user@gebo.ai";
	private static final String APPLICATION_ONLY = "url-rules-application@gebo.ai";
	private static final String UNKNOWN_ROLE = "url-rules-guest@gebo.ai";
	private static final String MY_CHATS = "/api/users/GeboUserChatsController/getMyChats";
	private static final String ADMIN_DASHBOARD = "/api/admin/GeboCoreAnalisysController/getTopLevelKnowledgeBaseCategory";
	private static final String TOPOLOGY = "/api/admin/InternalMessagingTopologyController/getLocalTopology";
	private static final String DATA_FLOW_TOPOLOGY = "/api/admin/DataFlowMetaInfoController/getLocalDataFlow";

	@Autowired
	private WebApplicationContext context;
	@Autowired
	private FilterChainProxy securityFilters;
	@Autowired
	private LocalJwtTokenProvider tokens;

	@Override
	protected void beforeEachCallback() throws Exception {
		if (getUser(USER_ONLY) == null) {
			createUser(USER_ONLY, List.of(GeboAISecurityConfig.USER_ROLE));
		}
		if (getUser(APPLICATION_ONLY) == null) {
			createUser(APPLICATION_ONLY, List.of(GeboAISecurityConfig.APPLICATION_ROLE));
		}
		if (getUser(UNKNOWN_ROLE) == null) {
			createUser(UNKNOWN_ROLE, List.of("GUEST"));
		}
	}

	@Test
	public void testUserUrlsNeedUserAdminOrApplication() throws Exception {
		assertEquals(200, status(USER_ONLY, MY_CHATS));
		assertEquals(200, status(APPLICATION_ONLY, MY_CHATS));
		assertEquals(200, status(DEFAULT_ALL_ROLES_USER, MY_CHATS));
		assertEquals(403, status(UNKNOWN_ROLE, MY_CHATS));
	}

	@Test
	public void testAdminUrlsNeedAdminEvenWithoutAnnotations() throws Exception {
		assertEquals(403, status(USER_ONLY, ADMIN_DASHBOARD));
		assertEquals(403, status(APPLICATION_ONLY, ADMIN_DASHBOARD));
		assertEquals(200, status(DEFAULT_ALL_ROLES_USER, ADMIN_DASHBOARD));
	}

	// The topology controllers ship with the microservices only: here an allowed request finds no handler
	// (404) while a refused one is stopped by the security filters (403).
	@Test
	public void testServiceTopologyStaysOpenToApplications() throws Exception {
		assertNotEquals(403, status(APPLICATION_ONLY, TOPOLOGY));
		assertNotEquals(403, status(DEFAULT_ALL_ROLES_USER, TOPOLOGY));
		assertEquals(403, status(USER_ONLY, TOPOLOGY));
		assertEquals(403, status(APPLICATION_ONLY, DATA_FLOW_TOPOLOGY),
				"The monolith's data flow topology stays for administrators only");
	}

	private int status(String username, String url) throws Exception {
		MockMvc mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(securityFilters).build();
		String token = tokens.createToken(username, new Date(System.currentTimeMillis() + 600_000));
		return mvc.perform(get(url).header("Authorization", "Bearer " + token)).andReturn().getResponse().getStatus();
	}
}
