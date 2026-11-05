/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.tools;

import java.util.List;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.model.ToolsCategory;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;

/**
 * The source that mounts the clarification tool (see
 * {@link AskUserClarificationTool}) on an agent: only when asked, by an agent
 * configuration or a network participant naming it among its additional tool
 * sources. An agent with it mounted may wait for the user; one without never
 * suspends a run, since no tool of its can declare a condition to wait for.
 */
@Service
public class AskUserClarificationToolSource implements IGToolCallbackSource {
	public static final String SOURCE_ID = "ask-user-clarification-tool-source";
	private static final ToolsCategory CATEGORY = new ToolsCategory("USER_CLARIFICATION",
			"Asking the user a clarification the work waits for", false);
	private final AskUserClarificationTool tool = new AskUserClarificationTool();

	@Override
	public String getId() {
		return SOURCE_ID;
	}

	@Override
	public ToolsCategory getToolCategory() {
		return CATEGORY;
	}

	/** Mounted on the agents that may wait for the user, and on those only. */
	@Override
	public boolean isMountedOnlyWhenAsked() {
		return true;
	}

	@Override
	public List<ToolReference> getFullToolReferences() {
		final ToolReference ask = new ToolReference();
		ask.setName(AskUserClarificationTool.TOOL_NAME);
		ask.setDescription(AskUserClarificationTool.DESCRIPTION);
		ask.setUserUIfunctionDescription("Ask the user a clarification");
		return List.of(ask);
	}

	@Override
	public List<ToolCallback> getToolCallbacks() {
		return List.of(tool.callback());
	}
}
