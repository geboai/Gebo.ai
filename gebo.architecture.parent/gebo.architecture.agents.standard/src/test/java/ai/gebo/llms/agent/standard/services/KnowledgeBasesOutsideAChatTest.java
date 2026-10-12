/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standard.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ToolContext;

import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.core.contents.security.services.IGKnowledgebaseVisibilityService;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;

/**
 * Pins where the knowledge bases come from: the network knowledge base agent reads
 * only the session environment (none when it has none), and a call from outside a
 * chat (A2A, MCP) gets all the ones its user can see, in the network environment
 * and in its tools context alike.
 */
class KnowledgeBasesOutsideAChatTest {

	private static GKnowledgeBase kb(String code) {
		GKnowledgeBase kb = new GKnowledgeBase();
		kb.setCode(code);
		return kb;
	}

	private static UserKnowledgeBasesExecutionEnvironment environmentSeeing(String... codes) {
		IGKnowledgebaseVisibilityService visibility = mock(IGKnowledgebaseVisibilityService.class);
		when(visibility.allVisibleKnowledgebases()).thenReturn(java.util.Arrays.stream(codes).map(c -> kb(c)).toList());
		return new UserKnowledgeBasesExecutionEnvironment(visibility);
	}

	@Test
	void theNetworkAgentSearchesOnlyTheKnowledgeBasesOfItsSessionEnvironment() {
		AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext();
		session.getEnvironment().put(StandardAgentsNetworkEnvironmentEntries.KNOWLEDGE_BASES_CODE, List.of("kb1"));
		assertEquals(List.of("kb1"), InternalKnowledgeBaseSearchNetworkAgentService.environmentKnowledgeBaseCodes(session));

		// a chat without knowledge bases, or no environment at all: none, never all the visible ones
		AgentsCollaborationSessionContext empty = new AgentsCollaborationSessionContext();
		empty.getEnvironment().put(StandardAgentsNetworkEnvironmentEntries.KNOWLEDGE_BASES_CODE, List.of());
		assertEquals(List.of(), InternalKnowledgeBaseSearchNetworkAgentService.environmentKnowledgeBaseCodes(empty));
		assertEquals(List.of(), InternalKnowledgeBaseSearchNetworkAgentService
				.environmentKnowledgeBaseCodes(new AgentsCollaborationSessionContext()));
		assertEquals(List.of(), InternalKnowledgeBaseSearchNetworkAgentService.environmentKnowledgeBaseCodes(null));
	}

	@Test
	void aCallFromOutsideAChatWorksOnAllTheKnowledgeBasesItsUserCanSee() {
		UserKnowledgeBasesExecutionEnvironment environment = environmentSeeing("kb1", "kb2", "kb1");

		List<String> codes = environment.knowledgeBaseCodes();
		assertEquals(List.of("kb1", "kb2"), codes);

		// the network's agents and its tools get the same knowledge bases
		Map<String, Object> networkEnvironment = environment.networkEnvironment(codes);
		assertEquals(List.of("kb1", "kb2"),
				networkEnvironment.get(StandardAgentsNetworkEnvironmentEntries.KNOWLEDGE_BASES_CODE));
		IChatRequestContext context = environment.requestContext("question", codes);
		assertEquals("question", context.getActualUserRequest());
		assertEquals(List.of("kb1", "kb2"),
				ToolCallbackDeclarationUtil.chatKnowledgeBases(new ToolContext(context.getToolsContext())));

		// an exported tool keeps what its context already carries
		Map<String, Object> toolsContext = environment.toolsContext(Map.of("exchange", "mcp"), codes);
		assertEquals("mcp", toolsContext.get("exchange"));
		assertEquals(List.of("kb1", "kb2"), ToolCallbackDeclarationUtil.chatKnowledgeBases(new ToolContext(toolsContext)));
	}

	@Test
	void whenTheVisibleKnowledgeBasesCannotBeReadTheCallReachesNone() {
		IGKnowledgebaseVisibilityService visibility = mock(IGKnowledgebaseVisibilityService.class);
		when(visibility.allVisibleKnowledgebases()).thenThrow(new IllegalStateException("not authenticated"));

		assertEquals(List.of(), new UserKnowledgeBasesExecutionEnvironment(visibility).knowledgeBaseCodes());
	}
}
