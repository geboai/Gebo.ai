/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry;
import ai.gebo.llms.abstraction.layer.services.ClientChatCallUtil;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.model.ChatAnswerFeedbackRating;
import ai.gebo.llms.chat.abstraction.layer.model.ChatRuleConflict;
import ai.gebo.llms.chat.abstraction.layer.model.ChatRuleScope;
import ai.gebo.llms.chat.abstraction.layer.model.GChatRule;
import ai.gebo.llms.chat.abstraction.layer.model.GChatAnswerFeedback;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatAnswerFeedbackRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatRuleProposalService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatRulesService;
import ai.gebo.llms.chat.abstraction.layer.services.UserLanguageDetection;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.system.ingestion.IGLanguageDetector;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GChatRuleProposalServiceImpl implements IGChatRuleProposalService {
	static final int MAX_PROPOSALS = 3;
	static final int HISTORY_EXCHANGES = 6;
	private static final String NO_RULE = "NONE";
	private final GUserChatSessionRepository sessionRepository;
	private final ChatAnswerFeedbackRepository feedbackRepository;
	private final IGSecurityService securityService;
	private final IGPromptConfigDao promptsDao;
	private final IGChatModelRuntimeConfigurationDao chatModelsDao;
	private final IGChatRulesService rulesService;
	/** The platform's language detector: the advice is written in the language of the rule. */
	private final ObjectProvider<IGLanguageDetector> languageDetector;
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private static final Pattern CONFLICT_LINE = Pattern.compile("^\\s*(\\d+)\\s*[:.)-]\\s*(.*)$");

	@Override
	public List<ChatRuleConflict> checkRuleConflicts(GChatRule candidate)
			throws GeboChatSessionLifecycleException, LLMConfigException {
		if (candidate == null || candidate.getScope() == null || candidate.getText() == null
				|| candidate.getText().isBlank()) {
			throw new GeboChatSessionLifecycleException("The rule to check needs a scope and a text");
		}
		List<GChatRule> others = new ArrayList<GChatRule>();
		switch (candidate.getScope()) {
		case SHARED -> others.addAll(rulesService.getSharedRules());
		case USER -> {
			others.addAll(rulesService.getSharedRulesAppliedToMe());
			others.addAll(rulesService.getMyRules().stream().filter(x -> x.getScope() == ChatRuleScope.USER).toList());
		}
		case SESSION -> {
			others.addAll(rulesService.getSharedRulesAppliedToMe());
			others.addAll(rulesService.getMyRules().stream().filter(x -> x.getScope() == ChatRuleScope.USER).toList());
			others.addAll(rulesService.getChatRules(candidate.getUserChatContextCode()));
		}
		}
		others = others.stream().filter(GChatRule::isEnabled)
				.filter(x -> candidate.getId() == null || !candidate.getId().equals(x.getId())).toList();
		if (others.isEmpty()) {
			return List.of();
		}
		StringBuilder numbered = new StringBuilder();
		for (int i = 0; i < others.size(); i++) {
			numbered.append(i + 1).append(": ").append(others.get(i).getText()).append("\n");
		}
		IGConfigurableChatModel model = chatModelsDao.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
		if (model == null) {
			throw new LLMConfigException("No internal services or default chat model present");
		}
		GPromptTemplateConfig prompt = promptsDao.findByPromptUse(GeboPromptsLibrary.CHAT_RULE_CONFLICT_PROMPT);
		final String ruleLanguage = detectedLanguage(candidate.getText());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("checkRuleConflicts(...) against " + others.size() + " rule(s), advice language:"
					+ ruleLanguage);
		}
		String reply = ClientChatCallUtil.removeThinking(model.textResponse(prompt,
				Map.of(IChatRequestContext.USER_QUESTION_PROMPT_PARAM, candidate.getText().trim(),
						IChatRequestContext.DOCUMENTS_PROMPT_PARAM, numbered.toString()),
				IChatRequestContext.builder().actualUserRequest(candidate.getText().trim()).userLanguage(ruleLanguage)
						.build()));
		List<ChatRuleConflict> conflicts = new ArrayList<ChatRuleConflict>();
		if (reply != null) {
			for (String line : reply.split("\\R")) {
				Matcher matcher = CONFLICT_LINE.matcher(line);
				if (matcher.matches()) {
					int number = Integer.parseInt(matcher.group(1));
					if (number >= 1 && number <= others.size()) {
						GChatRule other = others.get(number - 1);
						if (conflicts.stream().noneMatch(x -> x.ruleId().equals(other.getId()))) {
							conflicts.add(new ChatRuleConflict(other.getId(), other.getScope(), other.getText(),
									matcher.group(2).trim()));
						}
					}
				}
			}
		}
		return conflicts;
	}

	@Override
	public List<String> proposeRules(String userChatContextCode, String requestId)
			throws GeboChatSessionLifecycleException, LLMConfigException {
		GUserChatSession session = sessionRepository.findById(userChatContextCode).orElseThrow(
				() -> new GeboChatSessionLifecycleException("Chat " + userChatContextCode + " not found"));
		securityService.checkBeingCreator(session);
		List<ChatInteractions> interactions = session.getInteractions() != null ? session.getInteractions()
				: List.of();
		int rated = -1;
		for (int i = 0; i < interactions.size() && rated < 0; i++) {
			ChatInteractions x = interactions.get(i);
			if (x.getRequest() != null && requestId != null && requestId.equals(x.getRequest().getId())
					&& x.getResponse() != null) {
				rated = i;
			}
		}
		if (rated < 0) {
			throw new GeboChatSessionLifecycleException(
					"Request " + requestId + " is not an answered request of the chat " + userChatContextCode);
		}
		IGConfigurableChatModel model = chatModelsDao.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
		if (model == null) {
			throw new LLMConfigException("No internal services or default chat model present");
		}
		GPromptTemplateConfig prompt = promptsDao.findByPromptUse(GeboPromptsLibrary.CHAT_RULE_PROPOSAL_PROMPT);
		ChatInteractions exchange = interactions.get(rated);
		String question = exchange.getRequest().getQuery() != null ? exchange.getRequest().getQuery() : "";
		String answer = exchange.getResponse().getQueryResponse() != null
				? exchange.getResponse().getQueryResponse().toString()
				: "";
		GChatAnswerFeedback feedback = feedbackRepository
				.findById(GChatAnswerFeedback.idOf(userChatContextCode, requestId)).orElse(null);
		// the rules in the language the rated question was answered in
		final String language = exchange.getRequest().getUserLanguage() != null
				? exchange.getRequest().getUserLanguage()
				: detectedLanguage(question);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("proposeRules(...) chat:" + userChatContextCode + " request:" + requestId + " rules language:"
					+ language);
		}
		String reply = model.textResponse(prompt,
				Map.of(IChatRequestContext.USER_QUESTION_PROMPT_PARAM, question, "answer", answer, "feedback",
						describe(feedback)),
				historyBefore(interactions, rated, userChatContextCode, question, language));
		return parse(ClientChatCallUtil.removeThinking(reply));
	}

	/** The English name of the text's language, null when it cannot be told. */
	private String detectedLanguage(String text) {
		final IGLanguageDetector detector = languageDetector != null ? languageDetector.getIfAvailable() : null;
		return UserLanguageDetection.of(detector, text);
	}

	private static IChatRequestContext historyBefore(List<ChatInteractions> interactions, int rated,
			String userChatContextCode, String question, String language) {
		List<IChatSessionEntry> history = new ArrayList<IChatSessionEntry>();
		for (int i = Math.max(0, rated - HISTORY_EXCHANGES); i < rated; i++) {
			ChatInteractions x = interactions.get(i);
			history.add(IChatSessionEntry.builder()
					.user(x.getRequest() != null && x.getRequest().getQuery() != null ? x.getRequest().getQuery() : "")
					.assistant(x.getResponse() != null && x.getResponse().getQueryResponse() != null
							? x.getResponse().getQueryResponse().toString()
							: "")
					.build());
		}
		return IChatRequestContext.builder().sessionID(userChatContextCode).actualUserRequest(question)
				.interactions(history).userLanguage(language).build();
	}

	private static String describe(GChatAnswerFeedback feedback) {
		if (feedback == null) {
			return "no explicit rating, the user asks for rules from this answer";
		}
		String rating = feedback.getRating() == ChatAnswerFeedbackRating.NEGATIVE ? "not satisfying" : "good";
		return feedback.getComment() != null ? rating + ", commenting: " + feedback.getComment() : rating;
	}

	static List<String> parse(String reply) {
		List<String> rules = new ArrayList<String>();
		if (reply == null) {
			return rules;
		}
		for (String line : reply.split("\\R")) {
			String rule = line.trim().replaceFirst("^([-*•]|\\d+[.)])\\s*", "").trim();
			if (rule.isEmpty() || rule.equalsIgnoreCase(NO_RULE)) {
				continue;
			}
			if (rule.length() > GChatRulesServiceImpl.MAX_RULE_LENGTH) {
				rule = rule.substring(0, GChatRulesServiceImpl.MAX_RULE_LENGTH);
			}
			if (!rules.contains(rule)) {
				rules.add(rule);
			}
			if (rules.size() == MAX_PROPOSALS) {
				break;
			}
		}
		return rules;
	}
}
