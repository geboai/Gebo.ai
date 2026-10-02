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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import ai.gebo.architecture.ai.model.ContextContentRequired;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.tests.TestChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.model.ChatAnswerFeedbackRating;
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
import ai.gebo.llms.chat.client.rest.controllers.GeboUserChatsController;
import ai.gebo.llms.chat.client.rest.controllers.GeboUserChatsController.AnswerFeedbackRequest;
import ai.gebo.security.config.GeboAISecurityConfig;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.repository.UsersGroupRepository;

public class ChatRulesTest extends AbstractBaseTestLLmsIntegrationTests {

	private static final String OTHER_USER = "rules-other-user@gebo.ai";
	private static final String THIRD_USER = "rules-third-user@gebo.ai";
	private static final String RULES_GROUP = "chat-rules-test-group";

	@Autowired
	private GeboChatRulesController rulesController;
	@Autowired
	private GeboAdminChatRulesController adminRulesController;
	@Autowired
	private IGChatRulesService rulesService;
	@Autowired
	private GeboUserChatsController answerFeedbackController;
	@Autowired
	private IGChatSessionLifeCycleService lifeCycleService;
	@Autowired
	private ChatRuleRepository ruleRepository;
	@Autowired
	private UsersGroupRepository groupRepository;
	@Autowired
	private ShrinkedChatSessionStateRepository shrinkedRepository;
	@Autowired
	private ChatFullSessionStateRepository fullRepository;
	@Autowired
	private MinimalChatContextCacheItemRepository minimalContextCache;

	@Override
	protected void afterEachCallback() throws Exception {
		TestChatModel.clearGlobalResponseLogic();
	}

	@Override
	protected void beforeEachCallback() throws Exception {
		// New chats of the same user get the same code once the base class wipes the sessions.
		fullRepository.deleteAll();
		shrinkedRepository.deleteAll();
		minimalContextCache.deleteAll();
		ruleRepository.deleteAll();
		groupRepository.deleteById(RULES_GROUP);
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
	public void testSharedRulesReachTheMembersOfTheirGroups() throws Exception {
		UsersGroup group = new UsersGroup();
		group.setCode(RULES_GROUP);
		group.setDescription("Chat rules test group");
		group.setUserIds(List.of(THIRD_USER));
		groupRepository.save(group);
		GChatRule shared = draft(null, null, "Answer formally");
		shared.setAccessibleGroups(List.of(RULES_GROUP));
		shared = adminRulesController.createSharedRule(shared);

		impersonate(THIRD_USER, GeboAISecurityConfig.USER_ROLE);
		assertEquals(List.of(shared.getId()),
				rulesController.getSharedRulesAppliedToMe().stream().map(GChatRule::getId).toList());
		impersonate(OTHER_USER, GeboAISecurityConfig.USER_ROLE);
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
	public void testApplicableRulesReachTheModelPrompt() throws Exception {
		GChatRule shared = draft(null, null, "Never disclose prices");
		shared.setAccessibleUsers(List.of(DEFAULT_ALL_ROLES_USER));
		adminRulesController.createSharedRule(shared);
		String chat = newSession();
		rulesController.createRule(draft(ChatRuleScope.USER, null, "Cite the sources"));
		rulesController.createRule(draft(ChatRuleScope.SESSION, chat, "Answer in Italian"));
		GChatRule disabled = draft(ChatRuleScope.USER, null, "Be verbose");
		disabled.setEnabled(false);
		rulesController.createRule(disabled);

		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		GeboChatRequest request = new GeboChatRequest();
		request.setId(UUID.randomUUID().toString());
		request.setUserChatContextCode(chat);
		request.setQuery("What is the capital of France?");
		LLMChatRequestResources resources = lifeCycleService.startRequest(request, model,
				LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		IChatRequestContext context = resources.createChatRequestContext();
		assertEquals(List.of("Never disclose prices", "Cite the sources", "Answer in Italian"),
				context.getRulesToFollow());

		List<String> prompts = new ArrayList<>();
		TestChatModel.setGlobalResponseLogic(prompt -> {
			prompts.add(prompt);
			return "Parigi.";
		});
		model.textResponse(testPrompt(), Map.of(IChatRequestContext.USER_QUESTION_PROMPT_PARAM, request.getQuery()),
				context);
		model.textResponse(testPrompt(), Map.of(IChatRequestContext.USER_QUESTION_PROMPT_PARAM, "hello"),
				IChatRequestContext.of("hello"));
		assertEquals(2, prompts.size());
		assertTrue(prompts.get(0).contains("RULES TO FOLLOW"), prompts.get(0));
		for (String rule : List.of("Never disclose prices", "Cite the sources", "Answer in Italian")) {
			assertTrue(prompts.get(0).contains("- " + rule), rule);
		}
		assertFalse(prompts.get(0).contains("Be verbose"));
		assertFalse(prompts.get(1).contains("RULES TO FOLLOW"), "A context without rules adds no rules section");

		GeboChatResponse response = lifeCycleService.createEmptyResponse(request);
		response.setQueryResponse("Parigi.");
		lifeCycleService.endRequest(request, response);
	}

	@Test
	public void testProposalsComeFromTheRatedExchangeAndBecomeRulesOnlyWhenChosen() throws Exception {
		String chat = newSession();
		exchange(chat, "What is the capital of France?", "Paris.");
		GeboChatRequest rated = exchange(chat, "And the capital of Italy?", "Rome is the capital of Italy.");
		answerFeedbackController.setAnswerFeedback(new AnswerFeedbackRequest(chat, rated.getId(),
				ChatAnswerFeedbackRating.NEGATIVE, "Answer in Italian and cite a source"));
		List<String> prompts = new ArrayList<>();
		TestChatModel.setGlobalResponseLogic(prompt -> {
			prompts.add(prompt);
			return "1. Always cite a source\n- Answer in Italian\nNONE\n\n* Always cite a source\nUse short paragraphs\nA fourth rule";
		});

		List<String> proposals = rulesController.proposeRules(chat, rated.getId());
		assertEquals(List.of("Always cite a source", "Answer in Italian", "Use short paragraphs"), proposals);
		assertEquals(1, prompts.size());
		String prompt = prompts.get(0);
		for (String expected : List.of("And the capital of Italy?", "Rome is the capital of Italy.",
				"not satisfying, commenting: Answer in Italian and cite a source", "What is the capital of France?")) {
			assertTrue(prompt.contains(expected), expected);
		}
		assertTrue(ruleRepository.findAll().isEmpty(), "Proposing saves nothing");

		GChatRule chosen = draft(ChatRuleScope.USER, null, proposals.get(1) + ", please");
		chosen.setSourceUserChatContextCode(chat);
		chosen.setSourceRequestId(rated.getId());
		GChatRule saved = rulesController.createRule(chosen);
		assertEquals("Answer in Italian, please", saved.getText());
		assertEquals(chat, saved.getSourceUserChatContextCode());
		assertEquals(rated.getId(), saved.getSourceRequestId());

		TestChatModel.setGlobalResponseLogic(any -> "NONE");
		assertTrue(rulesController.proposeRules(chat, rated.getId()).isEmpty());
		assertThrows(GeboChatSessionLifecycleException.class,
				() -> rulesController.proposeRules(chat, "not-a-request-of-this-chat"));
		impersonate(OTHER_USER, GeboAISecurityConfig.USER_ROLE);
		assertThrows(SecurityException.class, () -> rulesController.proposeRules(chat, rated.getId()));
	}

	private GeboChatRequest exchange(String chat, String question, String answer) throws Exception {
		IGConfigurableChatModel model = chatModelRuntimeDao.findByCode(DEFAULT_TEST_CHAT_MODEL_CODE);
		GeboChatRequest request = new GeboChatRequest();
		request.setId(UUID.randomUUID().toString());
		request.setUserChatContextCode(chat);
		request.setQuery(question);
		lifeCycleService.startRequest(request, model, LLMRequestGenerationPolicy.ADDING_RESOURCES_FIT_TOKENS_BUDGET);
		GeboChatResponse response = lifeCycleService.createEmptyResponse(request);
		response.setQueryResponse(answer);
		lifeCycleService.endRequest(request, response);
		return request;
	}

	private static GPromptTemplateConfig testPrompt() {
		GPromptTemplateConfig prompt = new GPromptTemplateConfig();
		prompt.setPromptUse("chat-rules-test");
		prompt.setSystemPromptTemplate("You are the assistant of a test.");
		prompt.setUserPromptTemplate("{" + IChatRequestContext.USER_QUESTION_PROMPT_PARAM + "}");
		prompt.setChatHistory(ContextContentRequired.NOT_REQUIRED);
		prompt.setContextDocuments(ContextContentRequired.NOT_REQUIRED);
		prompt.setToolsCalling(ContextContentRequired.NOT_REQUIRED);
		return prompt;
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
