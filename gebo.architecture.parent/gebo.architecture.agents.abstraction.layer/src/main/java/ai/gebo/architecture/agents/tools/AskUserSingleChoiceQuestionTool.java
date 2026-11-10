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
 * The tool a model calls to ask the user a question with exactly one answer:
 * the user picks one of the options, or, when the model allows it, explains in
 * their own words instead. See {@link GAbstractAskUserChoiceQuestionTool}.
 */
public class AskUserSingleChoiceQuestionTool extends GAbstractAskUserChoiceQuestionTool {
	public static final String TOOL_NAME = "askUserSingleChoiceQuestion";
	public static final String DESCRIPTION = "Ask the user a question you cannot go on without, when exactly one of the options you give can be the answer,"
			+ " each with a short label and, when the label alone is not enough, a description;"
			+ " allow an explanation of their own when the options may not cover their case."
			+ STOPS_THE_WORK;

	/** The tool: the call ends at it once it asked, and goes on with its answer otherwise. */
	public static ToolCallback declare() {
		return new AskUserSingleChoiceQuestionTool().callback();
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
		return false;
	}
}
