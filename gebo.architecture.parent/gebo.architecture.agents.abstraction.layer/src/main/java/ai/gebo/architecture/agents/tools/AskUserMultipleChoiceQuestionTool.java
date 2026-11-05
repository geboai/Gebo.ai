/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.tools;

import org.springframework.ai.tool.ToolCallback;

/**
 * The tool a model calls to ask the user a question where more than one answer
 * may apply: the user picks every option that applies and, when the model allows
 * it, adds an explanation in their own words. See
 * {@link GAbstractAskUserChoiceQuestionTool}.
 */
public class AskUserMultipleChoiceQuestionTool extends GAbstractAskUserChoiceQuestionTool {
	public static final String TOOL_NAME = "askUserMultipleChoiceQuestion";
	public static final String DESCRIPTION = "Ask the user a question you cannot go on without, when several of the options you give may apply together and the user picks all those that do,"
			+ " each with a short label and, when the label alone is not enough, a description;"
			+ " allow an explanation of their own when the options may not cover their case."
			+ " The work stops and waits for the answer, so ask only what you really need, once, with everything in one question.";

	/** The tool, declared as returning directly: the call ends at it. */
	public static ToolCallback declare() {
		return new AskUserMultipleChoiceQuestionTool().callback();
	}

	@Override
	protected String toolName() {
		return TOOL_NAME;
	}

	@Override
	protected String toolDescription() {
		return DESCRIPTION;
	}

	@Override
	protected boolean multipleChoice() {
		return true;
	}
}
