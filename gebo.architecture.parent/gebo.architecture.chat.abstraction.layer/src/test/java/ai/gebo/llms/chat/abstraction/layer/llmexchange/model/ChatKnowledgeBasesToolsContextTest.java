/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ToolContext;

import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;

/**
 * Pins how the knowledge bases of the chat reach its tools: both request contexts
 * the tools are called through carry them in their tools context, next to the
 * request id, and an agent's context built on them keeps them.
 */
class ChatKnowledgeBasesToolsContextTest {

	private static GeboChatRequest request() {
		GeboChatRequest request = new GeboChatRequest();
		request.setId("request-1");
		request.setUserChatContextCode("chat-1");
		return request;
	}

	private static List<String> knowledgeBasesOf(IChatRequestContext context) {
		return ToolCallbackDeclarationUtil.chatKnowledgeBases(new ToolContext(context.getToolsContext()));
	}

	@Test
	void theRequestResourcesGiveTheirToolsTheChatsKnowledgeBases() {
		LLMChatRequestResources resources = new LLMChatRequestResources();
		resources.setCurrentRequest(request());
		resources.setAvailableKnowledgeBaseCodes(List.of("kb1", "kb1-child"));

		IChatRequestContext context = resources.createChatRequestContext();

		assertEquals(List.of("kb1", "kb1-child"), knowledgeBasesOf(context));
		assertEquals("request-1", context.getToolsContext().get(ToolCallbackDeclarationUtil.REQUEST_ID_CONTEXT_KEY));
		// an agent's context built on it keeps them
		assertEquals(List.of("kb1", "kb1-child"), knowledgeBasesOf(IChatRequestContext.forAgent(context, null)));
	}

	@Test
	void theMinimalContextGivesItsToolsTheChatsKnowledgeBases() {
		MinimalChatContext minimal = new MinimalChatContext();
		minimal.setCurrentRequest(request());
		minimal.setAvailableKnowledgeBaseCodes(List.of("kb1"));

		assertEquals(List.of("kb1"), knowledgeBasesOf(minimal.createChatRequestContext()));
	}

	@Test
	void withoutTheChatsKnowledgeBasesTheToolsGetNone() {
		LLMChatRequestResources resources = new LLMChatRequestResources();
		resources.setCurrentRequest(request());
		Map<String, Object> toolsContext = resources.createChatRequestContext().getToolsContext();

		assertFalse(toolsContext.containsKey(ToolCallbackDeclarationUtil.CHAT_KNOWLEDGE_BASES_CONTEXT_KEY));
		assertEquals(List.of(), knowledgeBasesOf(resources.createChatRequestContext()));
		assertEquals(List.of(), knowledgeBasesOf(new MinimalChatContext().createChatRequestContext()));
	}
}
