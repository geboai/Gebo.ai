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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.ClarificationAnswer;
import ai.gebo.architecture.agents.model.ClarificationOption;
import ai.gebo.architecture.agents.model.ClarificationOptions;
import ai.gebo.architecture.agents.services.IGWaitExternalCondition;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCallingManagerFactory;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * What the tools asking the user a choice question share: a model calls one to
 * ask something it cannot go on without, giving the options, each a label and a
 * description, and, when it allows it, leaving the user room for an explanation
 * of their own. {@link AskUserSingleChoiceQuestionTool} takes one option,
 * {@link AskUserMultipleChoiceQuestionTool} any number of them.
 * <p>
 * An agent that may wait for the user adds a tool to the tool calling of its
 * model call ({@code GeboToolCalling.Builder#mandatoryTool(callback())}) with an
 * {@link IGWaitExternalCondition} loaded; the tool declares the condition to it,
 * as an {@link AgentSuspendedWaitingCondition} of a {@link ClarificationAnswer}
 * with the {@link ClarificationOptions} the model chose, and ends the call (it
 * returns directly), so that the agent suspends the run for the answer. Where
 * nothing is loaded the tool tells the model to decide by itself.
 * <p>
 * On a respawn the agent gives the model the answer (see
 * {@link #answered(AgentSuspendedWaitingCondition)}) rather than letting the
 * tool answer it, since the tool ends every call it is part of. The model never
 * reads what the tool returns, so what it needs to know of this comes with the
 * tool's description ({@link #STOPS_THE_WORK}), and what it wants to keep for the
 * respawn it writes in {@link Param#getNotesForLater()}: saved on the condition,
 * it comes back with the answer and the user does not see it.
 */
public abstract class GAbstractAskUserChoiceQuestionTool {
	private static final Logger LOGGER = LoggerFactory.getLogger(GAbstractAskUserChoiceQuestionTool.class);
	static final String NOT_AVAILABLE = "Asking the user is not available here: decide by yourself, choosing the most likely option, and say which you assumed.";
	static final String NO_QUESTION = "The question is missing: say plainly what you need the user to answer.";
	static final String TOO_FEW_OPTIONS = "Give the user at least two options to choose from, or one option with an explanation of their own allowed.";
	static final String WAITING = "The question was put to the user: the work stops here and goes on when they answer.";
	/** How every question tool's description ends: what calling it does to the model's work. */
	static final String STOPS_THE_WORK = " Ask only what you really need, once, with everything in one question."
			+ " You will not see what this tool returns: your work stops here, and when the user answers"
			+ " you are executed again from the start on the same message, told the question and the answer."
			+ " Do not count on what you worked out up to now being there then: write in notesForLater"
			+ " the very values and results you will need, not just that you have them.";

	/** What the model gives the tool. */
	@Data
	@NoArgsConstructor
	@JsonClassDescription("A question to ask the user, with the options to choose from")
	public static class Param {
		@JsonPropertyDescription("The question you need answered before you can go on, asked plainly, with what the user needs to know to answer it")
		private String question = null;
		@JsonPropertyDescription("Why you need it: what you cannot decide without the answer")
		private String whyNeeded = null;
		@JsonPropertyDescription("The options to choose from: two or more, in the order to show them, each with a short label and, when the label alone does not say what choosing it means, a description")
		private List<ClarificationOption> options = null;
		@JsonPropertyDescription("True when the user may answer with an explanation of their own, when the options may not cover their case")
		private Boolean customAnswerAllowed = null;
		@JsonPropertyDescription("True when you cannot go on at all without the answer, so the conversation waits for it;"
				+ " false when the user may leave it unanswered and go on, you then proceeding without it")
		private Boolean mandatory = null;
		@JsonProperty(required = false)
		@JsonPropertyDescription("Notes to yourself: the very values and results you worked out so far, not just that you have them,"
				+ " and what you plan to do with the answer."
				+ " The user does not see them; you get them back with the answer")
		private String notesForLater = null;
	}

	/** The name the model calls the tool by. */
	protected abstract String toolName();

	/** What the model reads to decide to call the tool. */
	protected abstract String toolDescription();

	/** Whether the user may choose more than one option. */
	protected abstract boolean multipleChoice();

	/** The tool, declared as returning directly: the call ends at it. */
	public ToolCallback callback() {
		return ToolCallbackDeclarationUtil.declare(this::ask, toolName(), toolDescription(), Param.class, String.class,
				ToolCallbackDeclarationUtil.returningDirect());
	}

	/**
	 * Declares the question to the loaded {@link IGWaitExternalCondition}: the
	 * condition waits for a {@link ClarificationAnswer}, with the
	 * {@link ClarificationOptions} the model chose.
	 */
	String ask(Param param, ToolContext toolContext) {
		final IGWaitExternalCondition wait = GeboToolCallingManagerFactory.control(toolContext,
				IGWaitExternalCondition.class);
		if (wait == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(toolName() + " called where nothing can wait for the user: the model decides by itself");
			}
			return NOT_AVAILABLE;
		}
		if (param == null || param.getQuestion() == null || param.getQuestion().isBlank()) {
			return NO_QUESTION;
		}
		final ClarificationOptions options = new ClarificationOptions();
		options.setQuestion(param.getQuestion().strip());
		options.setMultipleChoice(multipleChoice());
		if (param.getOptions() != null) {
			for (ClarificationOption option : param.getOptions()) {
				if (option != null && option.getLabel() != null && !option.getLabel().isBlank()) {
					options.getOptions().add(ClarificationOption.of(option.getLabel().strip(),
							option.getDescription() != null && !option.getDescription().isBlank()
									? option.getDescription().strip()
									: null));
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
		if (param.getNotesForLater() != null && !param.getNotesForLater().isBlank()) {
			condition.setNotesForLater(param.getNotesForLater().strip());
		}
		wait.waitFor(condition);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(toolName() + " asked: " + options.getQuestion() + " among " + options.getOptions().size()
					+ " option(s)" + (options.isCustomAnswerAllowed() ? " or an explanation" : "")
					+ (mandatory ? ", mandatory" : ", may be left unanswered")
					+ (condition.getNotesForLater() != null ? ", with notes for later" : "") + " (condition:"
					+ condition.getSuspendedConditionId() + ")");
		}
		return WAITING;
	}

	/**
	 * The user's answer to a question, as the agent gives it to the model on a
	 * respawn: the question as it was asked and what the user chose or explained.
	 * Null when the condition is not a resolved question.
	 */
	public static String answered(AgentSuspendedWaitingCondition<?, ?> condition) {
		final String answer = answerOf(condition);
		if (answer == null) {
			return null;
		}
		return (answeredByOther(condition) ? "You asked: " : "You asked the user: ")
				+ condition.getWaitingConditionStatement() + answer;
	}

	/**
	 * Whether a source other than the conversation's own user solved the
	 * condition: it is then named instead of "the user".
	 */
	public static boolean answeredByOther(AgentSuspendedWaitingCondition<?, ?> condition) {
		return condition != null && condition.getSolvedBy() != null && !condition.getSolvedBy().isConversationUser();
	}

	/**
	 * Who answered, at the start of a sentence: "The user", or the source that
	 * did, its name as it is (an id is never changed), only the words said of it
	 * capitalised ("Agent ...", "An unknown source").
	 */
	public static String whoAnswered(AgentSuspendedWaitingCondition<?, ?> condition) {
		if (!answeredByOther(condition)) {
			return "The user";
		}
		final String label = condition.getSolvedBy().label();
		for (String said : new String[] { "agent ", "an unknown source" }) {
			if (label.startsWith(said)) {
				return Character.toUpperCase(said.charAt(0)) + label.substring(1);
			}
		}
		return label;
	}

	/**
	 * What the user chose or explained, each on a line of its own after a line
	 * break, for whoever tells the question in its own words. Null when the
	 * condition is not a resolved question.
	 */
	public static String answerOf(AgentSuspendedWaitingCondition<?, ?> condition) {
		if (condition == null || !(condition.getInstanceValue() instanceof ClarificationAnswer answer)) {
			return null;
		}
		final StringBuilder text = new StringBuilder();
		final List<String> chosen = new ArrayList<String>();
		if (answer.getChosenOptions() != null) {
			for (String option : answer.getChosenOptions()) {
				if (option != null && !option.isBlank()) {
					chosen.add(option.strip());
				}
			}
		}
		final String who = whoAnswered(condition);
		if (chosen.size() == 1) {
			text.append("\n").append(who).append(" chose: ").append(chosen.get(0));
		} else if (!chosen.isEmpty()) {
			text.append("\n").append(who).append(" chose:");
			for (String option : chosen) {
				text.append("\n- ").append(option);
			}
		}
		if (answer.getCustomExplanation() != null && !answer.getCustomExplanation().isBlank()) {
			text.append("\n").append(who).append(" explained: ").append(answer.getCustomExplanation().strip());
		}
		return text.toString();
	}
}
