/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatFullSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.MinimalChatContextCacheItemRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.ShrinkedChatSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.client.rest.controllers.GeboChatPipelinesController;
import ai.gebo.llms.chat.client.rest.controllers.GeboUserChatsController;
import ai.gebo.model.base.GLookupEntry;
import ai.gebo.security.config.GeboAISecurityConfig;

public class ChatControllersSecurityTest extends AbstractBaseTestLLmsIntegrationTests {

	private static final String OTHER_USER = "other-user@gebo.ai";
	private static final String APPLICATION_ACCOUNT = "application-account@gebo.ai";

	@Autowired
	private GeboUserChatsController userChatsController;
	@Autowired
	private GeboChatPipelinesController pipelinesController;
	@Autowired
	private IGChatSessionLifeCycleService lifeCycleService;
	@Autowired
	private GUserChatSessionRepository sessionRepository;
	@Autowired
	private ShrinkedChatSessionStateRepository shrinkedRepository;
	@Autowired
	private ChatFullSessionStateRepository fullRepository;
	@Autowired
	private MinimalChatContextCacheItemRepository minimalContextCache;

	@Override
	protected void beforeEachCallback() throws Exception {
		// New chats of the same user get the same code once the base class wipes the sessions.
		fullRepository.deleteAll();
		shrinkedRepository.deleteAll();
		minimalContextCache.deleteAll();
		if (getUser(OTHER_USER) == null) {
			createUser(OTHER_USER, List.of(GeboAISecurityConfig.USER_ROLE));
		}
		if (getUser(APPLICATION_ACCOUNT) == null) {
			createUser(APPLICATION_ACCOUNT, List.of(GeboAISecurityConfig.APPLICATION_ROLE));
		}
	}

	@Test
	public void testAnotherUserCannotRenameTheChat() throws Exception {
		String chat = ownersChat();
		String description = sessionRepository.findById(chat).orElseThrow().getDescription();

		impersonate(OTHER_USER, GeboAISecurityConfig.USER_ROLE);
		GLookupEntry rename = new GLookupEntry();
		rename.setCode(chat);
		rename.setDescription("renamed by someone else");
		assertThrows(SecurityException.class, () -> userChatsController.changeChatDescription(rename));
		assertEquals(description, sessionRepository.findById(chat).orElseThrow().getDescription());
	}

	@Test
	public void testAnotherUserCannotStopTheChat() throws Exception {
		String chat = ownersChat();

		impersonate(OTHER_USER, GeboAISecurityConfig.USER_ROLE);
		assertThrows(SecurityException.class, () -> pipelinesController.stopChatPipeline(chat));

		impersonate(DEFAULT_ALL_ROLES_USER, GeboAISecurityConfig.ADMIN_ROLE, GeboAISecurityConfig.USER_ROLE);
		assertDoesNotThrow(() -> pipelinesController.stopChatPipeline(chat));
	}

	@Test
	public void testAnotherUserCannotBranchTheChat() throws Exception {
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		String chat = ownersChat();
		GeboChatRequest exchange = new GeboChatRequest();
		exchange.setId(UUID.randomUUID().toString());
		exchange.setUserChatContextCode(chat);
		exchange.setQuery("What is the capital of France?");
		lifeCycleService.startRequest(exchange, model, LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		GeboChatResponse response = lifeCycleService.createEmptyResponse(exchange);
		response.setQueryResponse("Paris.");
		lifeCycleService.endRequest(exchange, response);

		impersonate(OTHER_USER, GeboAISecurityConfig.USER_ROLE);
		assertThrows(SecurityException.class, () -> userChatsController.branchChat(chat, exchange.getId()));
	}

	@Test
	public void testUserEndpointsAcceptUserAdminAndApplicationRolesOnly() {
		impersonate(APPLICATION_ACCOUNT, GeboAISecurityConfig.APPLICATION_ROLE);
		assertDoesNotThrow(() -> userChatsController.getMyChats());

		impersonate(OTHER_USER, GeboAISecurityConfig.USER_ROLE);
		assertDoesNotThrow(() -> userChatsController.getMyChats());

		impersonate(OTHER_USER);
		assertThrows(AccessDeniedException.class, () -> userChatsController.getMyChats());
		assertThrows(AccessDeniedException.class, () -> pipelinesController.stopChatPipeline("any-chat"));
	}

	private String ownersChat() throws Exception {
		GeboChatRequest first = new GeboChatRequest();
		first.setQuery("new chat");
		lifeCycleService.createChatSession(first);
		return first.getUserChatContextCode();
	}

	private static void impersonate(String username, String... roles) {
		List<SimpleGrantedAuthority> authorities = List.of(roles).stream().map(SimpleGrantedAuthority::new).toList();
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(username, "NOPASSWORD", authorities));
	}
}
