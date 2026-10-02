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
import ai.gebo.llms.chat.abstraction.layer.model.GChatAnswerFeedback;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatAnswerFeedbackRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.GUserChatSessionRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatRuleProposalService;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.security.services.IGSecurityService;
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
		String reply = model.textResponse(prompt,
				Map.of(IChatRequestContext.USER_QUESTION_PROMPT_PARAM, question, "answer", answer, "feedback",
						describe(feedback)),
				historyBefore(interactions, rated, userChatContextCode, question));
		return parse(ClientChatCallUtil.removeThinking(reply));
	}

	private static IChatRequestContext historyBefore(List<ChatInteractions> interactions, int rated,
			String userChatContextCode, String question) {
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
				.interactions(history).build();
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
