/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.deepsearch.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.gebo.architecture.ai.model.ContextContentRequired;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;

/**
 * The values a deep analysis call is filled with that are known before the call, for
 * the one budget formula of the consolidations
 * ({@link BaseLLMSInvokingService#computeFragmentBudget}): its parameters, the user's
 * request and language, the rules and, when the prompt takes it, the chat history. The
 * documents are what the budget is for, and the consolidation the call carries is given
 * to the formula on its own.
 */
public final class DeepSearchBudgets {

	private DeepSearchBudgets() {
	}

	/** The values known before a call of the prompt, by name. */
	public static Map<String, Object> knownValues(GPromptTemplateConfig prompt, Map<String, Object> params,
			IChatRequestContext context) {
		final Map<String, Object> known = params != null ? new HashMap<>(params) : new HashMap<>();
		known.remove(IChatRequestContext.DOCUMENTS_PROMPT_PARAM);
		known.remove(BaseLLMSInvokingService.CONSOLIDATED_TEMPLATE_VARIABLE);
		if (context == null) {
			return known;
		}
		putIfText(known, IChatRequestContext.USER_QUESTION_PROMPT_PARAM, context.getActualUserRequest());
		known.putIfAbsent(IChatRequestContext.USER_LANGUAGE_PROMPT_PARAM, IChatRequestContext.answerLanguage(context));
		final List<String> rules = context.getRulesToFollow();
		if (rules != null && !rules.isEmpty()) {
			known.put("rulesToFollow", String.join("\n", rules));
		}
		if (prompt == null || prompt.getChatHistory() == null
				|| prompt.getChatHistory() == ContextContentRequired.REQUIRED) {
			putIfText(known, IChatRequestContext.CONSOLIDATED_HISTORY_PROMPT_PARAM, context.getConsolidatedHistory());
			final List<IChatSessionEntry> interactions = context.getInteractions();
			if (interactions != null && !interactions.isEmpty()) {
				final StringBuilder history = new StringBuilder();
				for (IChatSessionEntry entry : interactions) {
					if (entry.getUser() != null) {
						history.append(entry.getUser()).append('\n');
					}
					if (entry.getAssistant() != null) {
						history.append(entry.getAssistant()).append('\n');
					}
				}
				known.put("chatHistory", history.toString());
			}
		}
		return known;
	}

	private static void putIfText(Map<String, Object> known, String name, String value) {
		if (value != null && !value.isBlank()) {
			known.put(name, value);
		}
	}
}
