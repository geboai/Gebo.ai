/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.tools.AskUserClarificationTool.ClarificationAnswer;
import ai.gebo.architecture.agents.tools.AskUserClarificationTool.ClarificationOptions;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;

/**
 * Carries a clarification the agents ask the user (see
 * {@code AskUserClarificationTool}): down, the question, the options to choose
 * one of and whether an explanation of the user's own is allowed; up, the option
 * chosen ({@value #CHOSEN_OPTION}) or the explanation given
 * ({@value #CUSTOM_EXPLANATION}), as the {@link ClarificationAnswer} the agent
 * waits for. An option not among the ones put to the user, or an explanation
 * where none is allowed, is refused.
 */
@Service
public class ClarificationConditionTransportTranslator extends GAbstractConditionTransportTranslator {
	public static final String ID = "clarification";
	/** The attributes of the DTO. */
	public static final String QUESTION = "question";
	public static final String OPTIONS = "options";
	public static final String CUSTOM_ANSWER_ALLOWED = "customAnswerAllowed";
	/** The attributes of the reaction. */
	public static final String CHOSEN_OPTION = "chosenOption";
	public static final String CUSTOM_EXPLANATION = "customExplanation";
	static final String NO_ANSWER = "The reaction answers nothing: choose an option or give an explanation";
	static final String OPTION_NOT_OFFERED = "The option chosen is not among the ones put to the user: ";
	static final String EXPLANATION_NOT_ALLOWED = "An explanation of the user's own is not allowed for this question";

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public boolean canTranslate(AgentSuspendedWaitingCondition condition) {
		return condition != null && (condition.getLlmChosenOptions() instanceof ClarificationOptions
				|| ClarificationAnswer.class.equals(condition.getType()));
	}

	@Override
	protected void describe(AgentSuspendedWaitingCondition condition, LinkedHashMap<String, Object> attributes) {
		final ClarificationOptions options = condition.getLlmChosenOptions() instanceof ClarificationOptions o ? o
				: null;
		attributes.put(QUESTION, options != null && options.getQuestion() != null ? options.getQuestion()
				: condition.getWaitingConditionStatement());
		attributes.put(OPTIONS, options != null ? List.copyOf(options.getOptions()) : List.of());
		attributes.put(CUSTOM_ANSWER_ALLOWED, options != null && options.isCustomAnswerAllowed());
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void resolve(AgentSuspendedWaitingConditionReactionDto reaction,
			AgentSuspendedWaitingCondition savedCondition) {
		final ClarificationOptions options = savedCondition.getLlmChosenOptions() instanceof ClarificationOptions o ? o
				: null;
		final String chosen = text(reaction, CHOSEN_OPTION);
		final String explanation = text(reaction, CUSTOM_EXPLANATION);
		if (chosen == null && explanation == null) {
			throw new IllegalArgumentException(NO_ANSWER);
		}
		if (chosen != null && options != null && !options.getOptions().contains(chosen)) {
			throw new IllegalArgumentException(OPTION_NOT_OFFERED + chosen);
		}
		if (chosen == null && options != null && !options.isCustomAnswerAllowed()) {
			throw new IllegalArgumentException(EXPLANATION_NOT_ALLOWED);
		}
		final ClarificationAnswer answer = new ClarificationAnswer();
		answer.setChosenOption(chosen);
		answer.setCustomExplanation(explanation);
		savedCondition.setInstanceValue(answer);
	}
}
