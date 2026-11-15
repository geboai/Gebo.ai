/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.ClarificationAnswer;
import ai.gebo.architecture.agents.model.ClarificationOption;
import ai.gebo.architecture.agents.model.ClarificationOptions;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ClarificationConditionAttributes;

/**
 * Carries a question the agents ask the user (see
 * {@code GAbstractAskUserChoiceQuestionTool}): down, the question, the options
 * with their labels and descriptions, whether the user picks one or several and
 * whether an explanation of their own is allowed; up, the option chosen
 * ({@value #CHOSEN_OPTION}) or the options chosen ({@value #CHOSEN_OPTIONS}) and
 * the explanation given ({@value #CUSTOM_EXPLANATION}), as the
 * {@link ClarificationAnswer} the agent waits for. An option not among the ones
 * put to the user, more than one option on a single choice, or an explanation
 * where none is allowed, is refused.
 */
@Service
public class ClarificationConditionTransportTranslator extends GAbstractConditionTransportTranslator {
	public static final String ID = "clarification";
	/** The attributes of the DTO. */
	public static final String QUESTION = "question";
	/** The options, each a {@value #OPTION_LABEL} and, when it has one, a {@value #OPTION_DESCRIPTION}. */
	public static final String OPTIONS = "options";
	public static final String OPTION_LABEL = "label";
	public static final String OPTION_DESCRIPTION = "description";
	public static final String MULTIPLE_CHOICE = "multipleChoice";
	public static final String CUSTOM_ANSWER_ALLOWED = "customAnswerAllowed";
	/** The attributes of the reaction. */
	public static final String CHOSEN_OPTION = "chosenOption";
	public static final String CHOSEN_OPTIONS = "chosenOptions";
	public static final String CUSTOM_EXPLANATION = "customExplanation";
	static final String NO_ANSWER = "The reaction answers nothing: choose an option or give an explanation";
	static final String OPTION_NOT_OFFERED = "The option chosen is not among the ones put to the user: ";
	static final String ONE_OPTION_ONLY = "This question takes a single option, not: ";
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
		attributes.put(OPTIONS, options != null ? described(options) : List.of());
		attributes.put(MULTIPLE_CHOICE, options != null && options.isMultipleChoice());
		attributes.put(CUSTOM_ANSWER_ALLOWED, options != null && options.isCustomAnswerAllowed());
	}

	@Override
	protected void typed(AgentSuspendedWaitingConditionDto dto, LinkedHashMap<String, Object> attributes) {
		dto.setClarificationAttributes(ClarificationConditionAttributes.of(attributes));
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void resolve(AgentSuspendedWaitingConditionReactionDto reaction,
			AgentSuspendedWaitingCondition savedCondition) {
		final ClarificationOptions options = savedCondition.getLlmChosenOptions() instanceof ClarificationOptions o ? o
				: null;
		final List<String> chosen = chosenIn(reaction);
		final String explanation = text(reaction, CUSTOM_EXPLANATION);
		if (chosen.isEmpty() && explanation == null) {
			throw new IllegalArgumentException(NO_ANSWER);
		}
		if (options != null) {
			for (String option : chosen) {
				if (!options.labels().contains(option)) {
					throw new IllegalArgumentException(OPTION_NOT_OFFERED + option);
				}
			}
			if (chosen.size() > 1 && !options.isMultipleChoice()) {
				throw new IllegalArgumentException(ONE_OPTION_ONLY + chosen);
			}
			if (explanation != null && !options.isCustomAnswerAllowed()) {
				throw new IllegalArgumentException(EXPLANATION_NOT_ALLOWED);
			}
		}
		final ClarificationAnswer answer = ClarificationAnswer.options(chosen);
		answer.setCustomExplanation(explanation);
		savedCondition.setInstanceValue(answer);
	}

	private static List<Map<String, Object>> described(ClarificationOptions options) {
		final List<Map<String, Object>> described = new ArrayList<Map<String, Object>>();
		for (ClarificationOption option : options.getOptions()) {
			final LinkedHashMap<String, Object> entry = new LinkedHashMap<String, Object>();
			entry.put(OPTION_LABEL, option.getLabel());
			if (option.getDescription() != null) {
				entry.put(OPTION_DESCRIPTION, option.getDescription());
			}
			described.add(entry);
		}
		return described;
	}

	/** The labels the reaction chooses, each once, from either attribute. */
	private static List<String> chosenIn(AgentSuspendedWaitingConditionReactionDto reaction) {
		final LinkedHashSet<String> chosen = new LinkedHashSet<String>();
		final String single = text(reaction, CHOSEN_OPTION);
		if (single != null) {
			chosen.add(single);
		}
		if (attribute(reaction, CHOSEN_OPTIONS) instanceof Collection<?> several) {
			for (Object option : several) {
				if (option != null && !String.valueOf(option).isBlank()) {
					chosen.add(String.valueOf(option).strip());
				}
			}
		}
		return new ArrayList<String>(chosen);
	}
}
