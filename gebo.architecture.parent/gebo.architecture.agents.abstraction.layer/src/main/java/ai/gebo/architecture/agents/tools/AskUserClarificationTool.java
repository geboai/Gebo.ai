/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.tools;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.services.IGWaitExternalCondition;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCallingManagerFactory;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The tool a model calls to ask the user a clarification it cannot go on
 * without: a question with the options the user chooses one of, and, when the
 * model allows it, an explanation of the user's own in place of an option. An
 * agent that may wait for the user adds it to the tool calling of its model
 * call ({@code GeboToolCalling.Builder#mandatoryTool(callback())}) with an
 * {@link IGWaitExternalCondition} loaded; the tool declares the condition to
 * it, as an {@link AgentSuspendedWaitingCondition} of a {@link ClarificationAnswer}
 * with the {@link ClarificationOptions} the model chose, and ends the call (it
 * returns directly), so that the agent suspends the run for the answer. Where
 * nothing is loaded the tool tells the model to decide by itself.
 * <p>
 * On a respawn the agent gives the model the answer (see
 * {@link #answered(AgentSuspendedWaitingCondition)}) rather than letting the
 * tool answer it, since the tool ends every call it is part of.
 */
public class AskUserClarificationTool {
	private static final Logger LOGGER = LoggerFactory.getLogger(AskUserClarificationTool.class);
	public static final String TOOL_NAME = "askUserClarification";
	public static final String DESCRIPTION = "Ask the user a clarification you cannot go on without, as a single choice among options you give,"
			+ " each a short label the user understands; allow an explanation of their own when the options may not cover their case."
			+ " The work stops and waits for the answer, so ask only what you really need, once, with everything in one question.";
	static final String NOT_AVAILABLE = "Asking the user is not available here: decide by yourself, choosing the most likely option, and say which you assumed.";
	static final String NO_QUESTION = "The question is missing: say plainly what you need the user to clarify.";
	static final String TOO_FEW_OPTIONS = "Give the user at least two options to choose from, or one option with an explanation of their own allowed.";
	static final String WAITING = "The question was put to the user: the work stops here and goes on when they answer.";

	/** What the model chose when it decided to ask: the question and the options it put to the user. */
	@Data
	@NoArgsConstructor
	public static class ClarificationOptions {
		private String question = null;
		private List<String> options = new ArrayList<String>();
		private boolean customAnswerAllowed = false;
	}

	/** The user's answer: the option chosen, or the explanation of their own when none fits. */
	@Data
	@NoArgsConstructor
	public static class ClarificationAnswer {
		private String chosenOption = null;
		private String customExplanation = null;

		public static ClarificationAnswer option(String chosenOption) {
			final ClarificationAnswer answer = new ClarificationAnswer();
			answer.setChosenOption(chosenOption);
			return answer;
		}

		public static ClarificationAnswer explanation(String customExplanation) {
			final ClarificationAnswer answer = new ClarificationAnswer();
			answer.setCustomExplanation(customExplanation);
			return answer;
		}
	}

	/** What the model gives the tool. */
	@Data
	@NoArgsConstructor
	@JsonClassDescription("A clarification to ask the user, as a single choice among options")
	public static class Param {
		@JsonPropertyDescription("The question you need answered before you can go on, asked plainly, with what the user needs to know to answer it")
		private String question = null;
		@JsonPropertyDescription("Why you need it: what you cannot decide without the answer")
		private String whyNeeded = null;
		@JsonPropertyDescription("The options the user chooses one of: two or more short labels the user understands, in the order to show them")
		private List<String> options = null;
		@JsonPropertyDescription("True when the user may answer with an explanation of their own instead of one of the options")
		private Boolean customAnswerAllowed = null;
		@JsonPropertyDescription("True when you cannot go on at all without the answer, so the conversation waits for it;"
				+ " false when the user may leave it unanswered and go on, you then proceeding without it")
		private Boolean mandatory = null;
	}

	/** The tool, declared as returning directly: the call ends at it. */
	public static ToolCallback declare() {
		return new AskUserClarificationTool().callback();
	}

	public ToolCallback callback() {
		return ToolCallbackDeclarationUtil.declare(this::ask, TOOL_NAME, DESCRIPTION, Param.class, String.class,
				ToolCallbackDeclarationUtil.returningDirect());
	}

	/**
	 * Declares the clarification to the loaded {@link IGWaitExternalCondition}: the
	 * condition waits for a {@link ClarificationAnswer}, with the
	 * {@link ClarificationOptions} the model chose.
	 */
	String ask(Param param, ToolContext toolContext) {
		final IGWaitExternalCondition wait = GeboToolCallingManagerFactory.control(toolContext,
				IGWaitExternalCondition.class);
		if (wait == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(TOOL_NAME + " called where nothing can wait for the user: the model decides by itself");
			}
			return NOT_AVAILABLE;
		}
		if (param == null || param.getQuestion() == null || param.getQuestion().isBlank()) {
			return NO_QUESTION;
		}
		final ClarificationOptions options = new ClarificationOptions();
		options.setQuestion(param.getQuestion().strip());
		if (param.getOptions() != null) {
			for (String option : param.getOptions()) {
				if (option != null && !option.isBlank()) {
					options.getOptions().add(option.strip());
				}
			}
		}
		options.setCustomAnswerAllowed(Boolean.TRUE.equals(param.getCustomAnswerAllowed()));
		if (options.getOptions().size() < 2 && !(options.getOptions().size() == 1 && options.isCustomAnswerAllowed())) {
			return TOO_FEW_OPTIONS;
		}
		// mandatory unless the model says the user may leave it unanswered
		final boolean mandatory = !Boolean.FALSE.equals(param.getMandatory());
		final AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions> condition = new AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions>(
				ClarificationAnswer.class, options.getQuestion(), param.getWhyNeeded(), null, mandatory,
				ClarificationOptions.class, options);
		wait.waitFor(condition);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(TOOL_NAME + " asked: " + options.getQuestion() + " among " + options.getOptions().size()
					+ " option(s)" + (options.isCustomAnswerAllowed() ? " or an explanation" : "")
					+ (mandatory ? ", mandatory" : ", may be left unanswered") + " (condition:"
					+ condition.getSuspendedConditionId() + ")");
		}
		return WAITING;
	}

	/**
	 * The user's answer to a clarification, as the agent gives it to the model on a
	 * respawn: the question as it was asked and what the user chose or explained.
	 * Null when the condition is not a resolved clarification.
	 */
	public static String answered(AgentSuspendedWaitingCondition<?, ?> condition) {
		if (condition == null || !(condition.getInstanceValue() instanceof ClarificationAnswer answer)) {
			return null;
		}
		final StringBuilder text = new StringBuilder();
		text.append("You asked the user: ").append(condition.getWaitingConditionStatement());
		if (answer.getChosenOption() != null && !answer.getChosenOption().isBlank()) {
			text.append("\nThe user chose: ").append(answer.getChosenOption().strip());
		}
		if (answer.getCustomExplanation() != null && !answer.getCustomExplanation().isBlank()) {
			text.append("\nThe user explained: ").append(answer.getCustomExplanation().strip());
		}
		return text.toString();
	}
}
