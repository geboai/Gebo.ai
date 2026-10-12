/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGProgressNotifier;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;

/**
 * Pins the contexts the agentic loop calls its model with: wrapping the user
 * request's context (the agent's own tool listener, the collector of the found
 * documents, the progress notifier) keeps everything else it carries, the rules of
 * the chat included (read by the model call, empty by default when not delegated).
 */
class ToolsContextSharingTest {

	@Test
	void theLoopContextsKeepTheChatRules() {
		List<String> rules = List.of("Answer in Italian", "Cite the chapter");
		IChatRequestContext request = IChatRequestContext.builder().requestID("r1")
				.toolsContext(Map.of("key", "value")).rulesToFollow(rules).build();
		ToolCallsListener agentListener = new ToolCallsListener();

		IChatRequestContext loopContext = IChatRequestContext.forAgent(
				ToolsProgress.sharedThrough(new ToolsFoundDocuments().sharedThrough(request), IGProgressNotifier.NONE),
				agentListener);

		assertEquals(rules, loopContext.getRulesToFollow());
		assertEquals("r1", loopContext.getRequestID());
		assertSame(agentListener, loopContext.getToolCallListener());
		assertEquals("value", loopContext.getToolsContext().get("key"));
		assertSame(IGProgressNotifier.NONE, loopContext.getToolsContext().get(ToolsProgress.TOOLS_CONTEXT_KEY));
	}
}
