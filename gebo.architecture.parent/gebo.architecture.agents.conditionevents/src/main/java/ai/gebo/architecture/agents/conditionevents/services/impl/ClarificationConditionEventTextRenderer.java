/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.conditionevents.services.impl;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.conditionevents.model.GConditionEvent;
import ai.gebo.architecture.agents.conditionevents.services.IConditionEventTextRenderer;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionDto;
import ai.gebo.llms.chat.pipelines.service.impl.ClarificationConditionTransportTranslator;

/**
 * Renders a question the agents asked: the question, then the options chosen
 * and the explanation given, or that it was left unanswered, and the source.
 */
@Service
public class ClarificationConditionEventTextRenderer implements IConditionEventTextRenderer {
	static final String LEFT_UNANSWERED = "left unanswered";

	@Override
	public String getConditionType() {
		return ClarificationConditionTransportTranslator.ID;
	}

	@Override
	public String render(AgentSuspendedWaitingConditionDto condition, GConditionEvent event) {
		final String question = DefaultConditionEventTextRenderer.statementOf(condition,
				ClarificationConditionTransportTranslator.QUESTION);
		return question + "\n→ " + answerOf(event) + DefaultConditionEventTextRenderer.fromOf(event);
	}

	private static String answerOf(GConditionEvent event) {
		if (DefaultConditionEventTextRenderer.isDeclined(event)) {
			return LEFT_UNANSWERED;
		}
		final Map<String, Object> attributes = event.getAttributes();
		final LinkedHashSet<String> chosen = new LinkedHashSet<String>();
		if (attributes != null) {
			final Object single = attributes.get(ClarificationConditionTransportTranslator.CHOSEN_OPTION);
			if (!DefaultConditionEventTextRenderer.isBlank(single)) {
				chosen.add(String.valueOf(single).strip());
			}
			if (attributes.get(ClarificationConditionTransportTranslator.CHOSEN_OPTIONS) instanceof Collection<?> several) {
				for (Object option : several) {
					if (!DefaultConditionEventTextRenderer.isBlank(option)) {
						chosen.add(String.valueOf(option).strip());
					}
				}
			}
		}
		final Object explanation = attributes != null
				? attributes.get(ClarificationConditionTransportTranslator.CUSTOM_EXPLANATION)
				: null;
		final String options = String.join(", ", chosen);
		if (DefaultConditionEventTextRenderer.isBlank(explanation)) {
			return options.isEmpty() ? LEFT_UNANSWERED : options;
		}
		return options.isEmpty() ? String.valueOf(explanation).strip()
				: options + " — " + String.valueOf(explanation).strip();
	}
}
