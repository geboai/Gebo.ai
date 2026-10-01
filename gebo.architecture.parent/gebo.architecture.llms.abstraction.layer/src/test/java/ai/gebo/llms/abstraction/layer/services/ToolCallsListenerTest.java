/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

import ai.gebo.architecture.ai.model.LLMtInteractionContextThreadLocal.CalledFunction;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry;
import ai.gebo.security.services.ReactiveIdentityUtil;

/**
 * Pins the tool call recording: an agent's calls reach the user request's recorder
 * exactly once, the recorder fills the response's called functions as the calls
 * happen, and an agent context only replaces the listener.
 */
class ToolCallsListenerTest {

	@Test
	void aChildRecordsItsCallsAndForwardsEachOnceToItsParent() {
		ToolCallsListener request = new ToolCallsListener();
		List<String> notified = new ArrayList<>();
		ToolCallsListener agent = request.child(call -> notified.add(call.getName()));
		ToolCallsListener otherAgent = request.child(null);

		agent.addCall("searchWeb", "web", "{\"query\":\"q\"}", "result");
		otherAgent.addCall("getActualUser", "user", "{}", "me");

		assertEquals(1, agent.getCalls().size());
		assertEquals(1, otherAgent.getCalls().size());
		assertEquals(2, request.getCalls().size(), "the request collects the calls of every agent");
		assertEquals(List.of("searchWeb"), notified);
		assertSame(agent.getCalls().get(0), request.getCalls().get(0), "the very same call is forwarded");
	}

	@Test
	void withoutParentAChildIsAPlainListener() {
		ToolCallsListener orphan = ToolCallsListener.childOf(null, null);

		orphan.addCall("searchWeb", "web", "{}", "result");

		assertEquals(1, orphan.getCalls().size());
	}

	@Test
	void theRequestRecorderFillsTheResponseAsTheCallsHappen() {
		List<CalledFunction> responseCalledFunctions = new ArrayList<>();
		ToolCallsListener request = ToolCallsListener.appendingTo(responseCalledFunctions);
		ToolCallsListener agent = request.child(null);

		agent.addCall("searchWeb", "Search the web", "{\"query\":\"spring boot\"}", "a very long content");
		agent.addCall("searchWeb", "Search the web", "{\"query\":\"spring ai\"}", "another content");

		assertEquals(2, responseCalledFunctions.size());
		CalledFunction first = responseCalledFunctions.get(0);
		assertEquals("searchWeb", first.getFunctionName());
		assertEquals("Search the web", first.getFunctionDescription());
		assertEquals(List.of("{\"query\":\"spring boot\"}"), first.getParamsDescription(),
				"the input is shown to the user");
		assertTrue(first.getParams().isEmpty(), "the result is not carried");
	}

	@Test
	void theRunAsWrapperRecordsIntoItsListener() {
		ToolCallsListener listener = new ToolCallsListener();
		ToolCallback tool = new ToolCallback() {
			@Override
			public ToolDefinition getToolDefinition() {
				return ToolDefinition.builder().name("searchWeb").description("Search the web").inputSchema("{}")
						.build();
			}

			@Override
			public String call(String toolInput) {
				return "found";
			}
		};
		ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();

		new RunAsToolCallback(tool, runAs, listener).call("{\"query\":\"q\"}", null);

		assertEquals(1, listener.getCalls().size());
		assertEquals("{\"query\":\"q\"}", listener.getCalls().get(0).getToolInput());
	}

	@Test
	void anAgentContextOnlyReplacesTheListener() {
		ToolCallsListener request = new ToolCallsListener();
		List<Document> documents = new ArrayList<>();
		IChatRequestContext requestContext = IChatRequestContext.builder().requestID("r1").sessionID("s1")
				.actualUserRequest("question").consolidatedHistory("history").interactions(List.<IChatSessionEntry>of())
				.documents(documents).toolsContext(Map.of("k", "v")).pipelineInfos(Map.of("p", "i"))
				.toolCallListener(request).build();
		ToolCallsListener agent = request.child(null);

		IChatRequestContext agentContext = IChatRequestContext.forAgent(requestContext, agent);
		documents.add(new Document("added later"));

		assertSame(agent, agentContext.getToolCallListener());
		assertEquals("r1", agentContext.getRequestID());
		assertEquals("s1", agentContext.getSessionID());
		assertEquals("question", agentContext.getActualUserRequest());
		assertEquals("history", agentContext.getConsolidatedHistory());
		assertEquals(Map.of("k", "v"), agentContext.getToolsContext());
		assertEquals(Map.of("p", "i"), agentContext.getPipelineInfos());
		assertEquals(1, agentContext.getDocuments().size(), "documents are read live, not frozen");
	}
}
