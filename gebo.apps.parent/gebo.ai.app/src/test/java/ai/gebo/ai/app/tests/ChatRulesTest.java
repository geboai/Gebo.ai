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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.ChatRuleScope;
import ai.gebo.llms.chat.abstraction.layer.model.GChatRule;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatFullSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatRuleRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.MinimalChatContextCacheItemRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.ShrinkedChatSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatRulesService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.client.rest.controllers.GeboAdminChatRulesController;
import ai.gebo.llms.chat.client.rest.controllers.GeboChatRulesController;
import ai.gebo.security.config.GeboAISecurityConfig;

public class ChatRulesTest extends AbstractBaseTestLLmsIntegrationTests {

	private static final String OTHER_USER = "rules-other-user@gebo.ai";
	private static final String THIRD_USER = "rules-third-user@gebo.ai";

	@Autowired
	private GeboChatRulesController rulesController;
	@Autowired
	private GeboAdminChatRulesController adminRulesController;
	@Autowired
	private IGChatRulesService rulesService;
	@Autowired
	private IGChatSessionLifeCycleService lifeCycleService;
	@Autowired
	private ChatRuleRepository ruleRepository;
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
		ruleRepository.deleteAll();
		for (String user : List.of(OTHER_USER, THIRD_USER)) {
			if (getUser(user) == null) {
				createUser(user, List.of(GeboAISecurityConfig.USER_ROLE));
			}
		}
	}

	@Test
	public void testOwnRulesAreCreatedListedAndEditedWithHistory() throws Exception {
		String chat = newSession();
		GChatRule sessionRule = rulesController.createRule(draft(ChatRuleScope.SESSION, chat, "  Answer in Italian  "));
		GChatRule userRule = rulesController.createRule(draft(ChatRuleScope.USER, null, "Cite the sources"));
		assertEquals("Answer in Italian", sessionRule.getText());
		assertEquals(DEFAULT_ALL_ROLES_USER, sessionRule.getOwnerUsername());
		assertEquals(2, rulesController.getMyRules().size());
		assertEquals(List.of(sessionRule.getId()),
				rulesController.getChatRules(chat).stream().map(GChatRule::getId).toList());

		userRule.setText("Always cite the sources");
		userRule.setEnabled(false);
		GChatRule edited = rulesController.updateRule(userRule);
		assertEquals("Always cite the sources", edited.getText());
		assertFalse(edited.isEnabled());
		assertEquals(1, edited.getChanges().size());
		assertEquals("Cite the sources", edited.getChanges().get(0).getPreviousText());
		assertTrue(edited.getChanges().get(0).isPreviousEnabled());

		edited.setPipelineCode("open-chat");
		assertEquals(1, rulesController.updateRule(edited).getChanges().size(),
				"Changing only a restriction is not a text or enabled change");

		rulesController.deleteRule(sessionRule.getId());
		assertEquals(1, rulesController.getMyRules().size());
	}

	@Test
	public void testOtherUsersCannotTouchMyRules() throws Exception {
		String chat = newSession();
		GChatRule mine = rulesController.createRule(draft(ChatRuleScope.USER, null, "Cite the sources"));

		impersonate(OTHER_USER, GeboAISecurityConfig.USER_ROLE);
		assertThrows(SecurityException.class,
				() -> rulesController.createRule(draft(ChatRuleScope.SESSION, chat, "Hijack this chat")));
		assertThrows(SecurityException.class, () -> rulesController.getChatRules(chat));
		mine.setText("changed by someone else");
		assertThrows(SecurityException.class, () -> rulesController.updateRule(mine));
		assertThrows(SecurityException.class, () -> rulesController.deleteRule(mine.getId()));
		assertTrue(rulesController.getMyRules().isEmpty());
		assertEquals("Cite the sources", ruleRepository.findById(mine.getId()).orElseThrow().getText());
	}

	@Test
	public void testSharedRulesAreForAdministratorsAndApplyToTheirAudienceOnly() throws Exception {
		impersonate(OTHER_USER, GeboAISecurityConfig.USER_ROLE);
		assertThrows(SecurityException.class,
				() -> rulesController.createRule(draft(ChatRuleScope.SHARED, null, "Everybody does this")));
		assertThrows(SecurityException.class,
				() -> rulesService.createRule(draft(ChatRuleScope.SHARED, null, "Everybody does this")));
		assertThrows(AccessDeniedException.class,
				() -> adminRulesController.createSharedRule(draft(null, null, "Everybody does this")));

		impersonate(DEFAULT_ALL_ROLES_USER, GeboAISecurityConfig.ADMIN_ROLE, GeboAISecurityConfig.USER_ROLE);
		GChatRule shared = draft(null, null, "Never disclose prices");
		shared.setAccessibleUsers(List.of(OTHER_USER));
		shared = adminRulesController.createSharedRule(shared);
		assertEquals(ChatRuleScope.SHARED, shared.getScope());
		assertEquals(1, adminRulesController.getSharedRules().size());
		assertTrue(rulesController.getSharedRulesAppliedToMe().isEmpty(),
				"Being an administrator does not put a shared rule on one's own chats");

		impersonate(OTHER_USER, GeboAISecurityConfig.USER_ROLE);
		assertEquals(List.of(shared.getId()),
				rulesController.getSharedRulesAppliedToMe().stream().map(GChatRule::getId).toList());
		GChatRule tampered = shared;
		tampered.setText("Disclose prices");
		assertThrows(SecurityException.class, () -> rulesService.updateRule(tampered));

		impersonate(THIRD_USER, GeboAISecurityConfig.USER_ROLE);
		assertTrue(rulesController.getSharedRulesAppliedToMe().isEmpty());
	}

	@Test
	public void testApplicableRulesAreOrderedAndFiltered() throws Exception {
		GChatRule shared = draft(null, null, "Never disclose prices");
		shared.setAccessibleUsers(List.of(DEFAULT_ALL_ROLES_USER));
		shared = adminRulesController.createSharedRule(shared);
		String chat = newSession();
		String otherChat = newSessionNamed("another chat");
		GChatRule sessionRule = rulesController.createRule(draft(ChatRuleScope.SESSION, chat, "Answer in Italian"));
		rulesController.createRule(draft(ChatRuleScope.SESSION, otherChat, "Answer in French"));
		GChatRule userRule = rulesController.createRule(draft(ChatRuleScope.USER, null, "Cite the sources"));
		GChatRule disabled = draft(ChatRuleScope.USER, null, "Be verbose");
		disabled.setEnabled(false);
		rulesController.createRule(disabled);
		GChatRule otherProfile = draft(ChatRuleScope.USER, null, "Use legal wording");
		otherProfile.setChatProfileCode("legal-profile");
		rulesController.createRule(otherProfile);

		assertEquals(List.of(shared.getId(), userRule.getId(), sessionRule.getId()),
				rulesService.getApplicableRules(chat, "default-profile", null).stream().map(GChatRule::getId)
						.toList());
		assertEquals(4, rulesService.getApplicableRules(chat, "legal-profile", null).size());
	}

	@Test
	public void testRuleTextIsRequiredAndBounded() {
		assertThrows(GeboChatSessionLifecycleException.class,
				() -> rulesController.createRule(draft(ChatRuleScope.USER, null, "   ")));
		assertThrows(GeboChatSessionLifecycleException.class,
				() -> rulesController.createRule(draft(ChatRuleScope.USER, null, "x".repeat(501))));
		assertTrue(ruleRepository.findAll().isEmpty());
	}

	private static GChatRule draft(ChatRuleScope scope, String chat, String text) {
		GChatRule rule = new GChatRule();
		rule.setScope(scope);
		rule.setUserChatContextCode(chat);
		rule.setText(text);
		return rule;
	}

	private String newSession() throws Exception {
		return newSessionNamed("new chat");
	}

	private String newSessionNamed(String query) throws Exception {
		GeboChatRequest first = new GeboChatRequest();
		first.setQuery(query);
		lifeCycleService.createChatSession(first);
		return first.getUserChatContextCode();
	}

	private static void impersonate(String username, String... roles) {
		List<SimpleGrantedAuthority> authorities = List.of(roles).stream().map(SimpleGrantedAuthority::new).toList();
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(username, "NOPASSWORD", authorities));
	}
}
