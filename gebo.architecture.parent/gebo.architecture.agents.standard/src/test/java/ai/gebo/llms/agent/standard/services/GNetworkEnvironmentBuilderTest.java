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
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;

class GNetworkEnvironmentBuilderTest {

	private static ChatPipelineExecutionRuntimeData runtimeData(GeboChatRequest request) {
		LLMChatRequestResources resources = new LLMChatRequestResources();
		resources.setCurrentRequest(request);
		ChatPipelineExecutionRuntimeData runtimeData = new ChatPipelineExecutionRuntimeData(null, 8192, resources, null,
				null, true);
		runtimeData.getSharedEnvironment().put("OF_THE_PIPELINE", "kept");
		return runtimeData;
	}

	@Test
	void theEnvironmentCarriesThePipelinesEntriesTheStandardOnesTheRequestAndTheCallersLast() {
		GeboChatRequest request = new GeboChatRequest();
		request.setUserIntent(DeliverableIntent.ANALISYS);
		request.setSearchRequested(true);
		request.setUserLanguage("Italian");
		ChatPipelineExecutionRuntimeData runtimeData = runtimeData(request);

		Map<String, Object> environment = GNetworkEnvironmentBuilder.plain().build(runtimeData, List.of("kb-1"),
				Map.of("OF_THE_CALLER", "last", "OF_THE_PIPELINE", "overridden"));

		assertEquals(List.of("kb-1"), environment.get(StandardAgentsNetworkEnvironmentEntries.KNOWLEDGE_BASES_CODE));
		assertEquals(DeliverableIntent.ANALISYS, environment.get(StandardAgentsNetworkEnvironmentEntries.USER_INTENT));
		assertEquals(Boolean.TRUE, environment.get(StandardAgentsNetworkEnvironmentEntries.SEARCH_REQUESTED));
		assertEquals("Italian", environment.get(StandardAgentsNetworkEnvironmentEntries.USER_LANGUAGE));
		assertSame(runtimeData, environment.get(StandardAgentsNetworkEnvironmentEntries.CHAT_RUNTIME_DATA));
		assertEquals("last", environment.get("OF_THE_CALLER"));
		assertEquals("overridden", environment.get("OF_THE_PIPELINE"), "the caller's entries come last");
	}

	@Test
	void aSearchIsNotAskedOfARunThatHasNothingToSearch() {
		GeboChatRequest request = new GeboChatRequest();
		request.setSearchRequested(true);

		Map<String, Object> environment = GNetworkEnvironmentBuilder.plain().build(runtimeData(request), List.of(),
				null);

		assertEquals(Boolean.FALSE, environment.get(StandardAgentsNetworkEnvironmentEntries.SEARCH_REQUESTED));
		assertEquals(DeliverableIntent.UNKNOWN, environment.get(StandardAgentsNetworkEnvironmentEntries.USER_INTENT),
				"a request not understood has the unknown intent, as the chat step always said");
	}
}
